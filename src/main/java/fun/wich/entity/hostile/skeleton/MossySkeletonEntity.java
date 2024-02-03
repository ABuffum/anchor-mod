package fun.wich.entity.hostile.skeleton;

import fun.wich.sound.ModSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MossySkeletonEntity extends SkeletonEntity {

	public MossySkeletonEntity(EntityType<? extends SkeletonEntity> entityType, World world) { super(entityType, world); }

	@Override
	protected SoundEvent getAmbientSound() { return ModSoundEvents.ENTITY_MOSSY_SKELETON_AMBIENT; }
	@Override
	protected SoundEvent getDeathSound() { return ModSoundEvents.ENTITY_MOSSY_SKELETON_DEATH; }
	@Override
	protected SoundEvent getHurtSound(DamageSource source) { return ModSoundEvents.ENTITY_MOSSY_SKELETON_HURT; }
	@Override
	protected void playStepSound(BlockPos pos, BlockState state) { this.playSound(ModSoundEvents.ENTITY_MOSSY_SKELETON_STEP, 0.15f, 1.0f); }

	@Override
	protected PersistentProjectileEntity createArrowProjectile(ItemStack arrow, float damageModifier) {
		PersistentProjectileEntity persistentProjectileEntity = super.createArrowProjectile(arrow, damageModifier);
		if (persistentProjectileEntity instanceof ArrowEntity arrowEntity) arrowEntity.addEffect(new StatusEffectInstance(StatusEffects.POISON, 600));
		return persistentProjectileEntity;
	}
}
