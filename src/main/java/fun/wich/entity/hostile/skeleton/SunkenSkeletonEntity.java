package fun.wich.entity.hostile.skeleton;

import fun.wich.entity.ModNbtKeys;
import fun.wich.entity.WaterConversionEntity;
import fun.wich.entity.projectile.WaterDragControllable;
import fun.wich.entity.variants.SunkenSkeletonVariant;
import fun.wich.sound.ModSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.DrownedEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.*;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;

import java.util.Optional;
import java.util.Random;

public class SunkenSkeletonEntity extends SkeletonEntity implements WaterConversionEntity {
	private static final TrackedData<Integer> VARIANT = DataTracker.registerData(SunkenSkeletonEntity.class, TrackedDataHandlerRegistry.INTEGER);
	public SunkenSkeletonEntity(EntityType<? extends SunkenSkeletonEntity> entityType, World world) {
		super(entityType, world);
	}

	@Override
	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, EntityData entityData, NbtCompound entityNbt) {
		this.setVariant(this.random.nextInt(SunkenSkeletonVariant.values().length));
		return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
	}
	public int getVariant() { return this.dataTracker.get(VARIANT); }
	public void setVariant(int variant) { this.dataTracker.set(VARIANT, variant); }
	@Override
	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(VARIANT, 0);
	}
	@Override
	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		nbt.putInt(ModNbtKeys.VARIANT, this.getVariant());
	}
	@Override
	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		if (nbt.contains(ModNbtKeys.VARIANT)) this.setVariant(nbt.getInt(ModNbtKeys.VARIANT));
	}

	@Override
	protected SoundEvent getAmbientSound() { return ModSoundEvents.ENTITY_SUNKEN_SKELETON_AMBIENT; }
	@Override
	protected SoundEvent getDeathSound() { return ModSoundEvents.ENTITY_SUNKEN_SKELETON_DEATH; }
	@Override
	protected SoundEvent getHurtSound(DamageSource source) { return ModSoundEvents.ENTITY_SUNKEN_SKELETON_HURT; }
	@Override
	protected void playStepSound(BlockPos pos, BlockState state) { this.playSound(ModSoundEvents.ENTITY_SUNKEN_SKELETON_STEP, 0.15f, 1.0f); }

	@Override
	protected PersistentProjectileEntity createArrowProjectile(ItemStack arrow, float damageModifier) {
		PersistentProjectileEntity persistentProjectileEntity = super.createArrowProjectile(arrow, damageModifier);
		if (persistentProjectileEntity instanceof WaterDragControllable dragControllable) dragControllable.setDragInWater(0.99f);
		return persistentProjectileEntity;
	}

	public static boolean canSpawn(EntityType<SunkenSkeletonEntity> type, ServerWorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
		if (!world.getFluidState(pos.down()).isIn(FluidTags.WATER)) return false;
		Optional<RegistryKey<Biome>> registryEntry = world.getBiomeKey(pos);
		Identifier id = registryEntry.map(RegistryKey::getValue).orElse(null);
		boolean  bl = world.getDifficulty() != Difficulty.PEACEFUL && DrownedEntity.isSpawnDark(world, pos, random) && (spawnReason == SpawnReason.SPAWNER || world.getFluidState(pos).isIn(FluidTags.WATER));
		if (id == BiomeKeys.LUKEWARM_OCEAN.getValue() || id == BiomeKeys.WARM_OCEAN.getValue() || id == BiomeKeys.JUNGLE.getValue()) {
			return random.nextInt(15) == 0 && bl;
		}
		return random.nextInt(40) == 0 && isValidSpawnDepth(world, pos) && bl;
	}
	public static boolean isValidSpawnDepth(WorldAccess world, BlockPos pos) { return pos.getY() < world.getSeaLevel() - 5; }

	@Override
	public boolean isPushedByFluids() { return !this.isSwimming(); }
	@Override
	public boolean canBreatheInWater() { return true; }
	@Override
	public boolean canConvertInWater() { return false; }
	@Override
	public void convertInWater() { }
	@Override
	public boolean isConvertingInWater() { return false; }

	@Override
	public void onDeath(DamageSource source) {
		super.onDeath(source);
		SunkenSkeletonVariant variant = SunkenSkeletonVariant.get(this);
		int i = random.nextInt(16);
		if (i == 0) this.dropItem(variant.coral);
		else if (i < 4) this.dropItem(variant.coral_fan);
	}
}
