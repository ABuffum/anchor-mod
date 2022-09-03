package haven.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.world.World;

public class FancyChickenEntity extends ChickenEntity {
	public FancyChickenEntity(EntityType<? extends ChickenEntity> entityType, World world) {
		super(entityType, world);
	}

	@Override
	public void tickMovement() {
		super.tickMovement();
		this.eggLayTime = 6000;
	}
}
