package haven.mixins.entities;

import haven.items.GrapplingRodItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FishingBobberEntity.class)
public abstract class FishingBobberEntityMixin extends Entity {

	public FishingBobberEntityMixin(EntityType<?> type, World world) {
		super(type, world);
	}

	@Inject(method="removeIfInvalid", at = @At("HEAD"), cancellable = true)
	private void RemoveIfInvalid(PlayerEntity player, CallbackInfoReturnable<Boolean> cir) {
		cir.setReturnValue(GrapplingRodItem.RemoveIfInvalid(player, this));
	}
	@Inject(method="pullHookedEntity", at = @At("HEAD"), cancellable = true)
	private void PullHookedEntity(Entity entity, CallbackInfo ci) {
		Entity player = ((FishingBobberEntity)(Object)this).getOwner();
		GrapplingRodItem.PullHookedEntity((PlayerEntity)player, entity, this);
		ci.cancel();
	}
}
