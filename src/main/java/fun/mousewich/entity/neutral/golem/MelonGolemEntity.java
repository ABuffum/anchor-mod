package fun.mousewich.entity.neutral.golem;

import fun.mousewich.ModBase;
import fun.mousewich.entity.blood.BloodType;
import fun.mousewich.entity.blood.EntityWithBloodType;
import fun.mousewich.entity.projectile.MelonSeedProjectileEntity;
import fun.mousewich.origins.power.MobHostilityPower;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShearsItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.event.GameEvent;

public class MelonGolemEntity extends GolemEntity implements Shearable, RangedAttackMob, EntityWithBloodType {
	private static final TrackedData<Byte> MELON_GOLEM_FLAGS = DataTracker.registerData(MelonGolemEntity.class, TrackedDataHandlerRegistry.BYTE);
	public MelonGolemEntity(EntityType<? extends MelonGolemEntity> entityType, World world) {
		super(entityType, world);
	}
	protected void initGoals() {
		this.goalSelector.add(1, new ProjectileAttackGoal(this, 1.25D, 20, 10.0F));
		this.goalSelector.add(2, new WanderAroundFarGoal(this, 1.0D, 1.0000001E-5F));
		this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
		this.goalSelector.add(4, new LookAroundGoal(this));
		this.targetSelector.add(1, new FollowTargetGoal<>(this, MobEntity.class, 10, true, false, entity -> entity instanceof Monster));
		this.targetSelector.add(1, MobHostilityPower.makeHostilityGoal(this, ModBase.MELON_GOLEM_ENTITY));
	}
	public static DefaultAttributeContainer.Builder createMelonGolemAttributes() {
		return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 4.0D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.20000000298023224D);
	}
	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(MELON_GOLEM_FLAGS, (byte)16);
	}
	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		nbt.putBoolean("Melon", this.hasMelon());
	}
	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		if (nbt.contains("Melon")) this.setHasMelon(nbt.getBoolean("Melon"));
	}
	public boolean hurtByWater() { return true; }
	public void tickMovement() {
		super.tickMovement();
		if (!this.world.isClient) {
			int i = MathHelper.floor(this.getX());
			int j = MathHelper.floor(this.getY());
			int k = MathHelper.floor(this.getZ());
			BlockPos blockPos = new BlockPos(i, j, k);
			Biome biome = this.world.getBiome(blockPos);
			if (biome.getTemperature(blockPos) > 1) this.damage(DamageSource.ON_FIRE, 1.0F);
			if (!this.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) return;
			BlockState blockState = Blocks.SNOW.getDefaultState();
			for(int l = 0; l < 4; ++l) {
				i = MathHelper.floor(this.getX() + (double)((float)(l % 2 * 2 - 1) * 0.25F));
				j = MathHelper.floor(this.getY());
				k = MathHelper.floor(this.getZ() + (double)((float)(l / 2 % 2 * 2 - 1) * 0.25F));
				blockPos = new BlockPos(i, j, k);
				if (!this.world.getBlockState(blockPos).isAir() || !blockState.canPlaceAt(this.world, blockPos)) continue;
				this.world.setBlockState(blockPos, blockState);
			}
		}
	}
	public void attack(LivingEntity target, float pullProgress) {
		MelonSeedProjectileEntity projectile = new MelonSeedProjectileEntity(this.world, this);
		double d = target.getEyeY() - 1.100000023841858D;
		double e = target.getX() - this.getX();
		double f = d - projectile.getY();
		double g = target.getZ() - this.getZ();
		double h = Math.sqrt(e * e + g * g) * 0.20000000298023224D;
		projectile.setVelocity(e, f + h, g, 1.6F, 12.0F);
		this.playSound(SoundEvents.ENTITY_SNOW_GOLEM_SHOOT, 1.0F, 0.4F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
		this.world.spawnEntity(projectile);
	}
	protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) { return 1.7F; }
	protected ActionResult interactMob(PlayerEntity player, Hand hand) {
		ItemStack itemStack = player.getStackInHand(hand);
		if (itemStack.getItem() instanceof ShearsItem && this.isShearable()) {
			this.sheared(SoundCategory.PLAYERS);
			this.emitGameEvent(GameEvent.SHEAR, player);
			if (!this.world.isClient) itemStack.damage(1, player, (p) -> p.sendToolBreakStatus(hand));
			return ActionResult.success(this.world.isClient);
		}
		else return ActionResult.PASS;
	}
	public void sheared(SoundCategory shearedSoundCategory) {
		this.world.playSoundFromEntity(null, this, SoundEvents.ENTITY_SNOW_GOLEM_SHEAR, shearedSoundCategory, 1.0F, 1.0F);
		if (!this.world.isClient()) {
			this.setHasMelon(false);
			this.dropStack(new ItemStack(ModBase.CARVED_MELON.asItem()), 1.7F);
		}
	}
	public boolean isShearable() { return this.isAlive() && this.hasMelon(); }
	public boolean hasMelon() { return (this.dataTracker.get(MELON_GOLEM_FLAGS) & 16) != 0; }
	public void setHasMelon(boolean hasMelon) {
		byte b = this.dataTracker.get(MELON_GOLEM_FLAGS);
		if (hasMelon) this.dataTracker.set(MELON_GOLEM_FLAGS, (byte)(b | 16));
		else this.dataTracker.set(MELON_GOLEM_FLAGS, (byte)(b & -17));
	}
	protected SoundEvent getAmbientSound() { return SoundEvents.ENTITY_SNOW_GOLEM_AMBIENT; }
	protected SoundEvent getHurtSound(DamageSource source) { return SoundEvents.ENTITY_SNOW_GOLEM_HURT; }
	protected SoundEvent getDeathSound() { return SoundEvents.ENTITY_SNOW_GOLEM_DEATH; }
	public Vec3d getLeashOffset() { return new Vec3d(0.0, 0.75 * this.getStandingEyeHeight(), this.getWidth() * 0.4); }

	@Override public BloodType GetDefaultBloodType() { return BloodType.WATER; }
}
