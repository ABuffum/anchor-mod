package fun.wich.entity.passive.chicken;

import fun.wich.ModBase;
import fun.wich.entity.ModEntityType;
import fun.wich.entity.blood.BloodType;
import fun.wich.entity.blood.EntityWithBloodType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

public class SlimeChickenEntity extends ChickenEntity implements EntityWithBloodType {
	private static final Ingredient BREEDING_INGREDIENT = Ingredient.ofItems(Items.EGG);

	public SlimeChickenEntity(EntityType<? extends ChickenEntity> entityType, World world) { super(entityType, world); }

	public SlimeChickenEntity createChild(ServerWorld serverWorld, PassiveEntity passiveEntity) {
		return ModEntityType.SLIME_CHICKEN_ENTITY.create(serverWorld);
	}

	@Override
	protected void initGoals() {
		this.goalSelector.add(0, new SwimGoal(this));
		this.goalSelector.add(1, new EscapeDangerGoal(this, 1.4));
		this.goalSelector.add(2, new AnimalMateGoal(this, 1.0));
		this.goalSelector.add(3, new TemptGoal(this, 1.0, BREEDING_INGREDIENT, false));
		this.goalSelector.add(4, new FollowParentGoal(this, 1.1));
		this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0));
		this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 6.0f));
		this.goalSelector.add(7, new LookAroundGoal(this));
	}

	@Override public BloodType GetDefaultBloodType() { return ModBase.SLIME_BLOOD_TYPE; }

	@Override
	public boolean isBreedingItem(ItemStack stack) { return BREEDING_INGREDIENT.test(stack); }
}
