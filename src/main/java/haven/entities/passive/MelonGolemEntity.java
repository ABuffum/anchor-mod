package haven.entities.passive;

import java.util.function.Consumer;

import haven.HavenMod;
import haven.entities.projectiles.MelonSeedProjectileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Shearable;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.FollowTargetGoal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.ProjectileAttackGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
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
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

public class MelonGolemEntity extends GolemEntity implements Shearable, RangedAttackMob {
	private static final TrackedData<Byte> MELON_GOLEM_FLAGS;
	private static final byte HAS_PUMPKIN_FLAG = 16;
	private static final float field_30374 = 1.7F;

	public MelonGolemEntity(EntityType<? extends MelonGolemEntity> entityType, World world) {
		super(entityType, world);
	}

	protected void initGoals() {
		this.goalSelector.add(1, new ProjectileAttackGoal(this, 1.25D, 20, 10.0F));
		this.goalSelector.add(2, new WanderAroundFarGoal(this, 1.0D, 1.0000001E-5F));
		this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
		this.goalSelector.add(4, new LookAroundGoal(this));
		this.targetSelector.add(1, new FollowTargetGoal(this, MobEntity.class, 10, true, false, (livingEntity) -> {
			return livingEntity instanceof Monster;
		}));
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
		nbt.putBoolean("Pumpkin", this.hasMelon());
	}

	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		if (nbt.contains("Pumpkin")) {
			this.setHasPumpkin(nbt.getBoolean("Pumpkin"));
		}

	}

	public boolean hurtByWater() {
		return true;
	}

	public void tickMovement() {
		super.tickMovement();
		if (!this.world.isClient) {
			int i = MathHelper.floor(this.getX());
			int j = MathHelper.floor(this.getY());
			int k = MathHelper.floor(this.getZ());
			if (this.world.getBiome(new BlockPos(i, 0, k)).getTemperature(new BlockPos(i, j, k)) > 1.0F) {
				this.damage(DamageSource.ON_FIRE, 1.0F);
			}

			if (!this.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
				return;
			}

			BlockState blockState = Blocks.SNOW.getDefaultState();

			for(int l = 0; l < 4; ++l) {
				i = MathHelper.floor(this.getX() + (double)((float)(l % 2 * 2 - 1) * 0.25F));
				j = MathHelper.floor(this.getY());
				k = MathHelper.floor(this.getZ() + (double)((float)(l / 2 % 2 * 2 - 1) * 0.25F));
				BlockPos blockPos = new BlockPos(i, j, k);
				if (this.world.getBlockState(blockPos).isAir() && this.world.getBiome(blockPos).getTemperature(blockPos) < 0.8F && blockState.canPlaceAt(this.world, blockPos)) {
					this.world.setBlockState(blockPos, blockState);
				}
			}
		}

	}

	public void attack(LivingEntity target, float pullProgress) {
		MelonSeedProjectileEntity melonSeedProjectileEntity = new MelonSeedProjectileEntity(this.world, this);
		double d = target.getEyeY() - 1.100000023841858D;
		double e = target.getX() - this.getX();
		double f = d - melonSeedProjectileEntity.getY();
		double g = target.getZ() - this.getZ();
		double h = Math.sqrt(e * e + g * g) * 0.20000000298023224D;
		melonSeedProjectileEntity.setVelocity(e, f + h, g, 1.6F, 12.0F);
		this.playSound(SoundEvents.ENTITY_SNOW_GOLEM_SHOOT, 1.0F, 0.4F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
		this.world.spawnEntity(melonSeedProjectileEntity);
	}

	protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
		return 1.7F;
	}

	protected ActionResult interactMob(PlayerEntity player, Hand hand) {
		ItemStack itemStack = player.getStackInHand(hand);
		if (itemStack.getItem() instanceof ShearsItem && this.isShearable()) {
			this.sheared(SoundCategory.PLAYERS);
			this.emitGameEvent(GameEvent.SHEAR, player);
			if (!this.world.isClient) {
				itemStack.damage(1, (LivingEntity)player, (Consumer)((playerx) -> {
					((LivingEntity)playerx).sendToolBreakStatus(hand);
				}));
			}

			return ActionResult.success(this.world.isClient);
		} else {
			return ActionResult.PASS;
		}
	}

	public void sheared(SoundCategory shearedSoundCategory) {
		this.world.playSoundFromEntity((PlayerEntity)null, this, SoundEvents.ENTITY_SNOW_GOLEM_SHEAR, shearedSoundCategory, 1.0F, 1.0F);
		if (!this.world.isClient()) {
			this.setHasPumpkin(false);
			this.dropStack(new ItemStack(HavenMod.CARVED_MELON.ITEM), 1.7F);
		}

	}

	public boolean isShearable() {
		return this.isAlive() && this.hasMelon();
	}

	public boolean hasMelon() {
		return ((Byte)this.dataTracker.get(MELON_GOLEM_FLAGS) & 16) != 0;
	}

	public void setHasPumpkin(boolean hasPumpkin) {
		byte b = (Byte)this.dataTracker.get(MELON_GOLEM_FLAGS);
		if (hasPumpkin) {
			this.dataTracker.set(MELON_GOLEM_FLAGS, (byte)(b | 16));
		} else {
			this.dataTracker.set(MELON_GOLEM_FLAGS, (byte)(b & -17));
		}

	}

	@Nullable
	protected SoundEvent getAmbientSound() {
		return SoundEvents.ENTITY_SNOW_GOLEM_AMBIENT;
	}

	@Nullable
	protected SoundEvent getHurtSound(DamageSource source) {
		return SoundEvents.ENTITY_SNOW_GOLEM_HURT;
	}

	@Nullable
	protected SoundEvent getDeathSound() {
		return SoundEvents.ENTITY_SNOW_GOLEM_DEATH;
	}

	public Vec3d getLeashOffset() {
		return new Vec3d(0.0D, (double)(0.75F * this.getStandingEyeHeight()), (double)(this.getWidth() * 0.4F));
	}

	static {
		MELON_GOLEM_FLAGS = DataTracker.registerData(MelonGolemEntity.class, TrackedDataHandlerRegistry.BYTE);
	}
}
