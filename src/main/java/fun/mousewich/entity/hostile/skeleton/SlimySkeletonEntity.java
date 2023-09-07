package fun.mousewich.entity.hostile.skeleton;

import fun.mousewich.effect.ModStatusEffects;
import fun.mousewich.entity.WaterConversionEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class SlimySkeletonEntity extends SkeletonEntity implements WaterConversionEntity {

	public SlimySkeletonEntity(EntityType<? extends SkeletonEntity> entityType, World world) { super(entityType, world); }

	@Override
	protected PersistentProjectileEntity createArrowProjectile(ItemStack arrow, float damageModifier) {
		PersistentProjectileEntity persistentProjectileEntity = super.createArrowProjectile(arrow, damageModifier);
		if (persistentProjectileEntity instanceof ArrowEntity arrowEntity) {
			arrowEntity.addEffect(new StatusEffectInstance(ModStatusEffects.STICKY, 600));
			arrowEntity.addEffect(new StatusEffectInstance(StatusEffects.POISON, 200));
		}
		return persistentProjectileEntity;
	}
	@Override
	public boolean isConvertingInWater() { return false; }
	@Override
	public boolean canConvertInWater() { return false; }
	@Override
	public void convertInWater() { }
}
