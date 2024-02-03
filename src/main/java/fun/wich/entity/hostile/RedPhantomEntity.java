package fun.wich.entity.hostile;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.PhantomEntity;
import net.minecraft.world.World;

public class RedPhantomEntity extends PhantomEntity {
	public RedPhantomEntity(EntityType<? extends PhantomEntity> entityType, World world) {
		super(entityType, world);
	}
	@Override
	protected boolean isAffectedByDaylight() { return false; }
}
