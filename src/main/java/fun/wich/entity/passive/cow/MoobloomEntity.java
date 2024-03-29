package fun.wich.entity.passive.cow;

import fun.wich.ModBase;
import fun.wich.entity.ModEntityType;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.item.Item;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

public class MoobloomEntity extends FlowerCowEntity {
	public MoobloomEntity(EntityType<? extends MoobloomEntity> entityType, World world) { super(entityType, world); }
	@Override public Block getFlowerBlock() { return ModBase.BUTTERCUP.asBlock(); }
	@Override public Item getFlowerItem() { return ModBase.BUTTERCUP.asItem(); }

	public FlowerCowEntity createChild(ServerWorld serverWorld, PassiveEntity passiveEntity) {
		return ModEntityType.MOOBLOOM_ENTITY.create(serverWorld);
	}
}