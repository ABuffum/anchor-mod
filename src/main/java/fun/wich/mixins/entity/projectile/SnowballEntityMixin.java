package fun.wich.mixins.entity.projectile;

import fun.wich.origins.power.ClownPacifistPower;
import fun.wich.origins.power.PowersUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SnowballEntity.class)
public abstract class SnowballEntityMixin extends ThrownItemEntity {
	public SnowballEntityMixin(EntityType<? extends ThrownItemEntity> entityType, World world) { super(entityType, world); }

	@Inject(method="onEntityHit", at = @At("HEAD"))
	protected void onEntityHit(EntityHitResult entityHitResult, CallbackInfo ci) {
		Entity entity = entityHitResult.getEntity();
		if (PowersUtil.Active(getOwner(), ClownPacifistPower.class) && entity instanceof PlayerEntity) {
			entity.addVelocity(-1, 0, 0);
			entity.velocityModified = true;
		}
	}
}
