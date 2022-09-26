package haven.mixins.entities;

import haven.items.GrapplingRodItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FishingBobberEntity.class)
public abstract class FishingBobberEntityMixin {
	@Shadow public abstract @Nullable PlayerEntity getPlayerOwner();

	@Inject(method="pullHookedEntity", at = @At("HEAD"), cancellable = true)
	protected void PullHookedEntity(Entity entity, CallbackInfo ci) {
		PlayerEntity player = getPlayerOwner();
		GrapplingRodItem.PullHookedEntity(player, entity, ((FishingBobberEntity)(Object)this));
		ci.cancel();
	}
}
