package fun.mousewich.entity.passive.chicken;

import fun.mousewich.ModBase;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

public class FancyChickenEntity extends ChickenEntity {
	public FancyChickenEntity(EntityType<? extends ChickenEntity> entityType, World world) { super(entityType, world); }

	protected void initGoals() {
		super.initGoals();
		this.goalSelector.add(2, new AnimalMateGoal(this, 1.0D, ChickenEntity.class));
		this.goalSelector.add(2, new AnimalMateGoal(this, 1.0D, FancyChickenEntity.class));
	}

	@Override
	public void tickMovement() {
		super.tickMovement();
		this.eggLayTime = 6000;
	}

	public FancyChickenEntity createChild(ServerWorld serverWorld, PassiveEntity passiveEntity) {
		return ModBase.FANCY_CHICKEN_ENTITY.create(serverWorld);
	}
}
