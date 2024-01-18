package fun.mousewich.mixins.entity.hostile;

import fun.mousewich.ModBase;
import fun.mousewich.entity.blood.BloodType;
import fun.mousewich.entity.blood.EntityWithBloodType;
import fun.mousewich.event.ModGameEvent;
import fun.mousewich.gen.data.tag.ModItemTags;
import fun.mousewich.origins.power.MobHostilityPower;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EndermanEntity.class)
public abstract class EndermanEntityMixin extends HostileEntity implements Angerable, EntityWithBloodType {
	protected EndermanEntityMixin(EntityType<? extends HostileEntity> entityType, World world) { super(entityType, world); }

	@Inject(method="isPlayerStaring", at = @At("HEAD"), cancellable = true)
	void IsPlayerStaring(PlayerEntity player, CallbackInfoReturnable<Boolean> cir) {
		ItemStack itemStack = player.getInventory().armor.get(3);
		if (itemStack.isIn(ModItemTags.CARVED_PUMPKINS)) cir.setReturnValue(false);
		if (itemStack.isOf(ModBase.TINTED_GOGGLES) || itemStack.isOf(ModBase.RUBY_GOGGLES) || itemStack.isOf(ModBase.SAPPHIRE_GOGGLES) || ModBase.STARE_AT_ENDERMEN_POWER.isActive(player)) {
			if (!MobHostilityPower.shouldBeHostile(player, EntityType.ENDERMAN)) {
				cir.setReturnValue(false);
			}
		}
	}

	@Inject(method="teleportTo(DDD)Z", at = @At(value="INVOKE", target="Lnet/minecraft/entity/mob/EndermanEntity;isSilent()Z"))
	private void TeleportTo(double x, double y, double z, CallbackInfoReturnable<Boolean> cir) {
		this.world.emitGameEvent(this, ModGameEvent.TELEPORT, getBlockPos());
	}

	@Override public BloodType GetDefaultBloodType() { return ModBase.ENDERMAN_BLOOD_TYPE; }
}
