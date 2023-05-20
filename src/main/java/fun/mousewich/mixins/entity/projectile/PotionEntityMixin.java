package fun.mousewich.mixins.entity.projectile;

import fun.mousewich.origins.power.FluidBreatherPower;
import io.github.apace100.apoli.component.PowerHolderComponent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FlyingItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.PotionEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(PotionEntity.class)
public abstract class PotionEntityMixin extends ThrownItemEntity implements FlyingItemEntity {
	public PotionEntityMixin(EntityType<? extends ThrownItemEntity> entityType, World world) { super(entityType, world); }
	public PotionEntityMixin(EntityType<? extends ThrownItemEntity> entityType, double d, double e, double f, World world) { super(entityType, d, e, f, world); }
	public PotionEntityMixin(EntityType<? extends ThrownItemEntity> entityType, LivingEntity livingEntity, World world) { super(entityType, livingEntity, world); }

	@Inject(method="damageEntitiesHurtByWater", at=@At("TAIL"))
	private void HydrateEntities(CallbackInfo ci) {
		Box box = this.getBoundingBox().expand(4.0, 2.0, 4.0);
		List<LivingEntity> list = this.world.getNonSpectatingEntities(LivingEntity.class, box);
		for (LivingEntity entity : list) {
			for (FluidBreatherPower power : PowerHolderComponent.getPowers(entity, FluidBreatherPower.class)) {
				if (power.isActive() && power.touch &&  Fluids.FLOWING_WATER.isIn(power.fluid)) {
					int i = entity.getAir() + 1800;
					entity.setAir(Math.min(i, entity.getMaxAir()));
					break;
				}
			}
		}
	}
}
