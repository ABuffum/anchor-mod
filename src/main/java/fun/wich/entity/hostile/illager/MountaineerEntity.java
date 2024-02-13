package fun.wich.entity.hostile.illager;

import com.google.common.collect.Maps;
import fun.wich.entity.ModEntityType;
import fun.wich.entity.ModNbtKeys;
import fun.wich.entity.variants.MountaineerVariant;
import fun.wich.sound.ModSoundEvents;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.raid.RaiderEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.village.raid.Raid;
import net.minecraft.world.*;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public class MountaineerEntity extends IllagerEntity {
	private static final TrackedData<Integer> VARIANT = DataTracker.registerData(MountaineerEntity.class, TrackedDataHandlerRegistry.INTEGER);

	public MountaineerEntity(World world) { this(ModEntityType.MOUNTAINEER_ENTITY, world); }
	public MountaineerEntity(EntityType<MountaineerEntity> type, World world) { super(type, world); }

	@Override
	protected void initGoals() {
		super.initGoals();
		this.goalSelector.add(0, new SwimGoal(this));
		this.goalSelector.add(2, new RaiderEntity.PatrolApproachGoal(this, 10.0f));
		this.goalSelector.add(4, new AttackGoal(this));
		this.goalSelector.add(8, new WanderAroundGoal(this, 0.6));
		this.goalSelector.add(9, new LookAtEntityGoal(this, PlayerEntity.class, 3.0f, 1.0f));
		this.goalSelector.add(10, new LookAtEntityGoal(this, MobEntity.class, 8.0f));
		this.targetSelector.add(1, new RevengeGoal(this, RaiderEntity.class).setGroupRevenge());
		this.targetSelector.add(2, new FollowTargetGoal<>(this, PlayerEntity.class, true));
		this.targetSelector.add(3, new FollowTargetGoal<>(this, MerchantEntity.class, false));
		this.targetSelector.add(3, new FollowTargetGoal<>(this, IronGolemEntity.class, true));
	}

	public static DefaultAttributeContainer.Builder createMountaineerAttributes() {
		return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.35f).add(EntityAttributes.GENERIC_FOLLOW_RANGE, 12.0).add(EntityAttributes.GENERIC_MAX_HEALTH, 24.0).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 5.0);
	}

	@Override
	public IllagerEntity.State getState() {
		if (this.isAttacking()) return IllagerEntity.State.ATTACKING;
		if (this.isCelebrating()) return IllagerEntity.State.CELEBRATING;
		return IllagerEntity.State.NEUTRAL;
	}

	@Override
	@Nullable
	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
		this.setVariant(this.random.nextInt(MountaineerVariant.values().length));
		EntityData entityData2 = super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
		((MobNavigation)this.getNavigation()).setCanPathThroughDoors(true);
		this.initEquipment(difficulty);
		this.updateEnchantments(difficulty);
		return entityData2;
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
	protected void initEquipment(LocalDifficulty difficulty) {
		super.initEquipment(difficulty);
		MountaineerVariant variant = MountaineerVariant.get(this);
		this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(variant.stoneaxe));
	}

	@Override
	public boolean isTeammate(Entity other) {
		if (super.isTeammate(other)) return true;
		if (other instanceof LivingEntity && ((LivingEntity) other).getGroup() == EntityGroup.ILLAGER) {
			return this.getScoreboardTeam() == null && other.getScoreboardTeam() == null;
		}
		return false;
	}

	@Override
	protected SoundEvent getAmbientSound() { return ModSoundEvents.ENTITY_MOUNTAINEER_AMBIENT; }
	@Override
	protected SoundEvent getDeathSound() { return ModSoundEvents.ENTITY_MOUNTAINEER_DEATH; }
	@Override
	protected SoundEvent getHurtSound(DamageSource source) { return ModSoundEvents.ENTITY_MOUNTAINEER_HURT; }
	@Override
	public SoundEvent getCelebratingSound() { return ModSoundEvents.ENTITY_MOUNTAINEER_CELEBRATE; }

	@Override
	public void addBonusForWave(int wave, boolean unused) {
		MountaineerVariant variant = MountaineerVariant.get(this);
		ItemStack itemStack = new ItemStack(variant.stoneaxe);
		Raid raid = this.getRaid();
		int i = 1;
		if (wave > raid.getMaxWaves(Difficulty.NORMAL)) i = 2;
		boolean bl = this.random.nextFloat() <= raid.getEnchantmentChance();
		if (bl) {
			HashMap<Enchantment, Integer> map = Maps.newHashMap();
			map.put(Enchantments.SHARPNESS, i);
			EnchantmentHelper.set(map, itemStack);
		}
		this.equipStack(EquipmentSlot.MAINHAND, itemStack);
	}
}