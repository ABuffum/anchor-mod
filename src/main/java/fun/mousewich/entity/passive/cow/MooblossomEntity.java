package fun.mousewich.entity.passive.cow;

import fun.mousewich.ModBase;
import fun.mousewich.entity.variants.MooblossomVariant;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.item.Item;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

public class MooblossomEntity extends FlowerCowEntity {
	public MooblossomEntity(EntityType<? extends MooblossomEntity> entityType, World world) { super(entityType, world); }
	@Override public Block getFlowerBlock() { return MooblossomVariant.get(this).block; }
	@Override public Block getRenderedFlowerBlock() { return MooblossomVariant.get(this).backBlock; }
	@Override public Item getFlowerItem() { return MooblossomVariant.get(this).item; }
	public FlowerCowEntity createChild(ServerWorld serverWorld, PassiveEntity passiveEntity) {
		MooblossomEntity entity = ModBase.MOOBLOSSOM_ENTITY.create(serverWorld);
		MooblossomVariant.setVariant(entity, (this.random.nextBoolean() ? MooblossomVariant.get(this) : MooblossomVariant.get((MooblossomEntity)passiveEntity)).ordinal());
		return entity;
	}
}