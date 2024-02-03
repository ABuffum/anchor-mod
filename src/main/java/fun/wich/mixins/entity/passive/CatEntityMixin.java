package fun.wich.mixins.entity.passive;

import fun.wich.ModBase;
import fun.wich.entity.ModNbtKeys;
import fun.wich.entity.blood.BloodType;
import fun.wich.entity.blood.EntityWithBloodType;
import fun.wich.item.basic.ModDyeItem;
import fun.wich.util.dye.ModDyeColor;
import fun.wich.util.dye.ModDyedCollar;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CatEntity.class)
public abstract class CatEntityMixin extends TameableEntity implements ModDyedCollar, EntityWithBloodType {
	@Shadow @Final private static TrackedData<Integer> COLLAR_COLOR;

	protected CatEntityMixin(EntityType<? extends TameableEntity> entityType, World world) { super(entityType, world); }

	public ModDyeColor GetModCollarColor() { return ModDyeColor.byId(this.dataTracker.get(COLLAR_COLOR)); }
	public void SetModCollarColor(ModDyeColor color) { this.dataTracker.set(COLLAR_COLOR, color.getId()); }

	@Redirect(method="writeCustomDataToNbt", at=@At(value="INVOKE", target="Lnet/minecraft/nbt/NbtCompound;putByte(Ljava/lang/String;B)V"))
	private void OverrideCollarColorWrite(NbtCompound instance, String key, byte value) {
		if (key.equals(ModNbtKeys.COLLAR_COLOR)) instance.putByte(key, (byte)this.GetModCollarColor().getId());
		else instance.putByte(key, value);
	}

	@Redirect(method="readCustomDataFromNbt", at=@At(value="INVOKE", target="Lnet/minecraft/nbt/NbtCompound;contains(Ljava/lang/String;I)Z"))
	private boolean OverrideCollarColorRead(NbtCompound instance, String key, int type) {
		if (key.equals(ModNbtKeys.COLLAR_COLOR)) {
			if (instance.contains(key, type)) {
				this.SetModCollarColor(ModDyeColor.byId(instance.getInt(key)));
				return false;
			}
		}
		return instance.contains(key, type);
	}

	@Inject(method="interactMob", at=@At("HEAD"), cancellable=true)
	public void DyeCollarWithModDye(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
		ItemStack itemStack = player.getStackInHand(hand);
		if (this.world.isClient) return;
		if (this.isTamed()) {
			if (this.isOwner(player)) {
				if (itemStack.getItem() instanceof ModDyeItem dye) {
					ModDyeColor dyeColor = dye.getColor();
					if (dyeColor != this.GetModCollarColor()) {
						this.SetModCollarColor(dyeColor);
						if (!player.getAbilities().creativeMode) itemStack.decrement(1);
						this.setPersistent();
						cir.setReturnValue(ActionResult.CONSUME);
					}
				}
			}
		}
	}

	@Inject(method="createChild(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/passive/PassiveEntity;)Lnet/minecraft/entity/passive/CatEntity;", at=@At("TAIL"))
	private void SetChildCollarColor(ServerWorld serverWorld, PassiveEntity passiveEntity, CallbackInfoReturnable<CatEntity> cir) {
		CatEntity child = cir.getReturnValue();
		if (passiveEntity instanceof CatEntity && child instanceof ModDyedCollar dyed) {
			if (this.isTamed()) {
				if (this.random.nextBoolean() || !(passiveEntity instanceof ModDyedCollar)) {
					dyed.SetModCollarColor(this.GetModCollarColor());
				}
				else dyed.SetModCollarColor(((ModDyedCollar)passiveEntity).GetModCollarColor());
			}
		}
	}

	@Override public BloodType GetDefaultBloodType() { return ModBase.CAT_BLOOD_TYPE; }
}
