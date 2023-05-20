package fun.mousewich.mixins.entity.projectile;

import fun.mousewich.enchantment.GravityEnchantment;
import fun.mousewich.enchantment.WeakeningEnchantment;
import fun.mousewich.entity.projectile.GravityEnchantmentCarrier;
import fun.mousewich.entity.projectile.WeakeningEnchantmentCarrier;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(ProjectileEntity.class)
public abstract class ProjectileEntityMixin extends Entity {
	@Shadow public abstract Entity getOwner();

	public ProjectileEntityMixin(EntityType<?> type, World world) { super(type, world); }

	@Inject(method="onEntityHit", at=@At("TAIL"))
	protected void AddEnchantmentEffectsOnEntityHit(EntityHitResult entityHitResult, CallbackInfo ci) {
		Entity entity = entityHitResult.getEntity();
		TryPullInNearbyEntitiesExcluding(entityHitResult.getPos(), entity);
		if (this instanceof WeakeningEnchantmentCarrier weakeningCarrier) {
			int level = weakeningCarrier.getWeakeningLevel();
			if (level > 0) {
				if (!this.world.isClient && entity instanceof LivingEntity livingEntity) {
					livingEntity.addStatusEffect(WeakeningEnchantment.getEffect(level));
				}
			}
		}
	}

	@Inject(method="onBlockHit", at=@At("TAIL"))
	private void AddEnchantmentEffectsOnBlockHit(BlockHitResult blockHitResult, CallbackInfo ci) {
		TryPullInNearbyEntitiesExcluding(blockHitResult.getPos(), null);
	}

	private void TryPullInNearbyEntitiesExcluding(Vec3d pos, Entity excluded) {
		if (this instanceof GravityEnchantmentCarrier gravityCarrier) {
			int level = gravityCarrier.getGravityLevel();
			if (level > 0) {
				List<LivingEntity> exclude = new ArrayList<>();
				if (this.getOwner() instanceof LivingEntity living) exclude.add(living);
				if (excluded instanceof LivingEntity living) exclude.add(living);
				GravityEnchantment.pullInNearbyEntities(this.world, pos, level, exclude);
			}
		}
	}
}
