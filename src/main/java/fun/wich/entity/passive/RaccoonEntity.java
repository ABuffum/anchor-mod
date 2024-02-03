package fun.wich.entity.passive;

import fun.wich.ModBase;
import fun.wich.origins.power.ScareMobPower;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

public class RaccoonEntity extends AnimalEntity {
	public static final Ingredient BREEDING_INGREDIENT = Ingredient.ofItems(Items.WHEAT);
	public RaccoonEntity(EntityType<? extends RaccoonEntity> entityType, World world) {
		super(entityType, world);
	}

	protected void initGoals() {
		this.goalSelector.add(0, new SwimGoal(this));
		this.goalSelector.add(1, new EscapeDangerGoal(this, 1.4D));
		this.goalSelector.add(2, new AnimalMateGoal(this, 1.0D));
		this.goalSelector.add(3, new TemptGoal(this, 1.0D, BREEDING_INGREDIENT, false));
		this.goalSelector.add(4, ScareMobPower.makeFleeGoal(this, 8, 1.6, 1.4, ModBase.RACCOON_ENTITY));
		this.goalSelector.add(5, new FollowParentGoal(this, 1.1D));
		this.goalSelector.add(6, new WanderAroundFarGoal(this, 1.0D));
		this.goalSelector.add(7, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
		this.goalSelector.add(8, new LookAroundGoal(this));
	}

	protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
		return this.isBaby() ? dimensions.height * 0.6F : dimensions.height * 0.6875F;
	}

	public static DefaultAttributeContainer.Builder createRaccoonAttributes() {
		return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25D);
	}

	@Override
	public RaccoonEntity createChild(ServerWorld serverWorld, PassiveEntity passiveEntity) {
		return ModBase.RACCOON_ENTITY.create(serverWorld);
	}

	public boolean isBreedingItem(ItemStack stack) { return BREEDING_INGREDIENT.test(stack); }
}
