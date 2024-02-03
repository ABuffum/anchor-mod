package fun.wich.mixins.entity.passive;

import fun.wich.ModBase;
import fun.wich.entity.ModDataHandlers;
import fun.wich.entity.blood.BloodType;
import fun.wich.entity.blood.EntityWithBloodType;
import fun.wich.entity.variants.LlamaVariant;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.passive.AbstractDonkeyEntity;
import net.minecraft.entity.passive.LlamaEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LlamaEntity.class)
public abstract class LlamaEntityMixin extends AbstractDonkeyEntity implements RangedAttackMob, EntityWithBloodType {
	@Shadow public abstract void setVariant(int variant);
	@Shadow @Final private static TrackedData<Integer> VARIANT;

	protected LlamaEntityMixin(EntityType<? extends AbstractDonkeyEntity> entityType, World world) { super(entityType, world); }

	@Override public BloodType GetDefaultBloodType() { return ModBase.LLAMA_BLOOD_TYPE; }

	@Inject(method="initDataTracker", at=@At("TAIL"))
	protected void InitDataTracker(CallbackInfo ci) {
		this.dataTracker.startTracking(ModDataHandlers.MOD_CARPET_COLOR, -1);
	}
	@Inject(method="isHorseArmor", at=@At("HEAD"), cancellable=true)
	public void isHorseArmor(ItemStack item, CallbackInfoReturnable<Boolean> cir) {
		if (item.isOf(Items.MOSS_CARPET) || item.isOf(ModBase.GLOW_LICHEN_CARPET.asItem())) cir.setReturnValue(true);
	}
	@Inject(method="updateSaddle", at=@At(value="INVOKE", shift=At.Shift.AFTER, target="Lnet/minecraft/entity/passive/LlamaEntity;setCarpetColor(Lnet/minecraft/util/DyeColor;)V"))
	protected void UpdateSaddle(CallbackInfo ci) {
		if (this.world.isClient) return;
		this.dataTracker.set(ModDataHandlers.MOD_CARPET_COLOR, ModDataHandlers.GetModCarpetColor(this.items.getStack(1).getItem()));
	}

	@Redirect(method="initialize", at=@At(value="INVOKE", target="Lnet/minecraft/entity/passive/LlamaEntity;setVariant(I)V"))
	private void swapInVariant(LlamaEntity instance, int variant) { this.setVariant(this.random.nextInt(LlamaVariant.values().length)); }
	@Inject(method="getVariant", at=@At("HEAD"), cancellable=true)
	public void GetModVariant(CallbackInfoReturnable<Integer> cir) { cir.setReturnValue(this.dataTracker.get(VARIANT)); }
}
