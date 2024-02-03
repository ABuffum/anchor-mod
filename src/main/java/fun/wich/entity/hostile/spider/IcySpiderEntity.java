package fun.wich.entity.hostile.spider;

import fun.wich.ModBase;
import fun.wich.entity.blood.BloodType;
import fun.wich.entity.blood.EntityWithBloodType;
import fun.wich.sound.ModSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.entity.mob.StrayEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class IcySpiderEntity extends SpiderEntity implements EntityWithBloodType {
	public IcySpiderEntity(EntityType<? extends SpiderEntity> entityType, World world) { super(entityType, world); }
	public static DefaultAttributeContainer.Builder createIcySpiderAttributes() {
		return SpiderEntity.createSpiderAttributes().add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2f);
	}

	@Override
	@Nullable
	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
		StatusEffect statusEffect;
		entityData = super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
		if (world.getRandom().nextInt(100) == 0) {
			StrayEntity skeletonEntity = EntityType.STRAY.create(this.world);
			skeletonEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), 0.0f);
			skeletonEntity.initialize(world, difficulty, spawnReason, null, null);
			skeletonEntity.startRiding(this);
		}
		if (entityData == null) {
			entityData = new SpiderData();
			if (world.getDifficulty() == Difficulty.HARD && world.getRandom().nextFloat() < 0.1f * difficulty.getClampedLocalDifficulty()) {
				((SpiderData)entityData).setEffect(world.getRandom());
			}
		}
		if (entityData instanceof SpiderData && (statusEffect = ((SpiderData)entityData).effect) != null) {
			this.addStatusEffect(new StatusEffectInstance(statusEffect, Integer.MAX_VALUE));
		}
		return entityData;
	}
	
	@Override
	protected SoundEvent getAmbientSound() { return ModSoundEvents.ENTITY_ICY_SPIDER_AMBIENT; }
	@Override
	protected SoundEvent getHurtSound(DamageSource source) { return ModSoundEvents.ENTITY_ICY_SPIDER_HURT; }
	@Override
	protected SoundEvent getDeathSound() { return ModSoundEvents.ENTITY_ICY_SPIDER_DEATH; }
	@Override
	protected void playStepSound(BlockPos pos, BlockState state) { this.playSound(ModSoundEvents.ENTITY_ICY_SPIDER_STEP, 0.15f, 1.0f); }

	@Override
	public void applyDamageEffects(LivingEntity attacker, Entity target) {
		super.applyDamageEffects(attacker, target);
		if (target instanceof LivingEntity entity) {
			int i = 0;
			if (this.world.getDifficulty() == Difficulty.NORMAL) i = 7;
			else if (this.world.getDifficulty() == Difficulty.HARD) i = 15;
			if (i > 0) entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, i * 20, 0), this);
		}
	}

	@Override
	public BloodType GetDefaultBloodType() { return ModBase.SPIDER_BLOOD_TYPE; }
}
