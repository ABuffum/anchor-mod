package net.minecraft.entity.projectile;

import haven.HavenMod;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FishingBobberEntity.class)
public abstract class FishingBobberEntityMixin extends ProjectileEntity {

	FishingBobberEntityMixin(EntityType<? extends ProjectileEntity> entityType, World world) {
		super(entityType, world);
	}

	@Inject(method="removeIfInvalid", at = @At("HEAD"), cancellable = true)
	private void RemoveIfInvalid(PlayerEntity player, CallbackInfoReturnable<Boolean> cir) {
		ItemStack itemStack = player.getMainHandStack();
		ItemStack itemStack2 = player.getOffHandStack();
		boolean bl = itemStack.isOf(Items.FISHING_ROD) || itemStack.isOf(HavenMod.GRAPPLING_ROD);
		boolean bl2 = itemStack2.isOf(Items.FISHING_ROD) || itemStack2.isOf(HavenMod.GRAPPLING_ROD);
		if (!player.isRemoved() && player.isAlive() && (bl || bl2) && !(this.squaredDistanceTo(player) > 1024.0D)) {
			cir.setReturnValue(false);
		} else {
			this.discard();
			cir.setReturnValue(true);
		}
	}
}
