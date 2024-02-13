package fun.wich.entity.passive;

import fun.wich.ModBase;
import fun.wich.entity.ModEntityType;
import fun.wich.entity.blood.BloodType;
import fun.wich.entity.blood.EntityWithBloodType;
import fun.wich.origins.power.ScareMobPower;
import fun.wich.sound.ModSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.TimeHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class RedPandaEntity extends AnimalEntity implements EntityWithBloodType {
	private static final TrackedData<Integer> ANGER = DataTracker.registerData(RedPandaEntity.class, TrackedDataHandlerRegistry.INTEGER);

	public static final Ingredient BREEDING_INGREDIENT = Ingredient.ofItems(Items.BAMBOO);
	public RedPandaEntity(EntityType<? extends RedPandaEntity> entityType, World world) { super(entityType, world); }

	private static final UniformIntProvider ANGER_TIME_RANGE = TimeHelper.betweenSeconds(20, 39);
	@Nullable
	private UUID angryAt;

	protected void initGoals() {
		this.goalSelector.add(0, new SwimGoal(this));
		this.goalSelector.add(1, new EscapeDangerGoal(this, 1.4D));
		this.goalSelector.add(2, new AnimalMateGoal(this, 1.0D));
		this.goalSelector.add(3, new TemptGoal(this, 1.0D, BREEDING_INGREDIENT, false));
		this.goalSelector.add(4, new FleeEntityGoal<>(this, OcelotEntity.class, 8.0F, 1.6D, 1.4D));
		this.goalSelector.add(4, ScareMobPower.makeFleeGoal(this, 8, 1.6, 1.4, ModEntityType.RED_PANDA_ENTITY));
		this.goalSelector.add(5, new FollowParentGoal(this, 1.1D));
		this.goalSelector.add(6, new WanderAroundFarGoal(this, 1.0D));
		this.goalSelector.add(7, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
		this.goalSelector.add(8, new LookAtEntityGoal(this, RedPandaEntity.class, 6.0F));
		this.goalSelector.add(9, new LookAroundGoal(this));

		this.targetSelector.add(2, new FollowTargetGoal<>(this, PlayerEntity.class, false, false));
		this.targetSelector.add(2, new FollowTargetGoal<>(this, RedPandaEntity.class, false, false));
	}

	protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
		return this.isBaby() ? dimensions.height * 0.6F : dimensions.height * 0.6875F;
	}

	public static DefaultAttributeContainer.Builder createRedPandaAttributes() {
		return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25D);
	}

	@Override
	public RedPandaEntity createChild(ServerWorld serverWorld, PassiveEntity passiveEntity) {
		return ModEntityType.RED_PANDA_ENTITY.create(serverWorld);
	}

	public boolean isBreedingItem(ItemStack stack) { return BREEDING_INGREDIENT.test(stack); }

	@Override public BloodType GetDefaultBloodType() { return ModBase.RED_PANDA_BLOOD_TYPE; }

	@Override
	protected SoundEvent getAmbientSound() { return ModSoundEvents.ENTITY_RED_PANDA_AMBIENT; }
	@Override
	protected SoundEvent getHurtSound(DamageSource source) { return ModSoundEvents.ENTITY_RED_PANDA_HURT; }
	@Override
	protected SoundEvent getDeathSound() { return ModSoundEvents.ENTITY_RED_PANDA_DEATH; }
	@Override
	protected void playStepSound(BlockPos pos, BlockState state) { this.playSound(ModSoundEvents.ENTITY_RED_PANDA_STEP, 0.15f, 1.0f); }
}
