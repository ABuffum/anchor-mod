package haven.containers;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class WallBlockContainer {
	public final Block BLOCK;
	public final Block WALL_BLOCK;
	public final Item ITEM;

	public WallBlockContainer(Block block, Block wallBlock, Item item) {
		BLOCK = block;
		ITEM = item;
		WALL_BLOCK = wallBlock;
	}

	public boolean contains(Block block) {
		return block == BLOCK || block == WALL_BLOCK;
	}
	public boolean contains(Item item) {
		return item == ITEM;
	}
}
