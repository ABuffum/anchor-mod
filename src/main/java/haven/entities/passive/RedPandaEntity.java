package haven.entities.passive;

import haven.HavenMod;
import haven.origins.powers.ScareRedPandasPower;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class RedPandaEntity extends AnimalEntity {
	public static final Ingredient BREEDING_INGREDIENT = Ingredient.ofItems(Items.BAMBOO);
	public RedPandaEntity(EntityType<? extends RedPandaEntity> entityType, World world) {
		super(entityType, world);
	}

	protected void initGoals() {
		this.goalSelector.add(0, new SwimGoal(this));
		this.goalSelector.add(1, new EscapeDangerGoal(this, 1.4D));
		this.goalSelector.add(2, new AnimalMateGoal(this, 1.0D));
		this.goalSelector.add(3, new TemptGoal(this, 1.0D, BREEDING_INGREDIENT, false));
		this.goalSelector.add(4, new FleeEntityGoal<>(this, PlayerEntity.class, ScareRedPandasPower::HasActivePower, 8.0F, 1.6D, 1.4D, EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR::test));
		this.goalSelector.add(5, new FollowParentGoal(this, 1.1D));
		this.goalSelector.add(6, new WanderAroundFarGoal(this, 1.0D));
		this.goalSelector.add(7, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
		this.goalSelector.add(8, new LookAroundGoal(this));
	}

	protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
		return this.isBaby() ? dimensions.height * 0.6F : dimensions.height * 0.6875F;
	}

	public static DefaultAttributeContainer.Builder createRedPandaAttributes() {
		return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25D);
	}

	@Nullable
	@Override
	public RedPandaEntity createChild(ServerWorld serverWorld, PassiveEntity passiveEntity) {
		return HavenMod.RED_PANDA_ENTITY.create(serverWorld);
	}

	public boolean isBreedingItem(ItemStack stack) { return BREEDING_INGREDIENT.test(stack); }
}
