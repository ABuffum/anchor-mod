package fun.wich.entity.passive.cow;

import fun.wich.ModBase;
import fun.wich.entity.ModEntityType;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.item.Item;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

public class MoolipEntity extends FlowerCowEntity {
	public MoolipEntity(EntityType<? extends MoolipEntity> entityType, World world) { super(entityType, world); }
	@Override public Block getFlowerBlock() { return ModBase.PINK_DAISY.asBlock(); }
	@Override public Item getFlowerItem() { return ModBase.PINK_DAISY.asItem(); }

	public FlowerCowEntity createChild(ServerWorld serverWorld, PassiveEntity passiveEntity) {
		return ModEntityType.MOOLIP_ENTITY.create(serverWorld);
	}
}