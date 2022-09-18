package haven.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class WalledBlock {
	public final Block BLOCK;
	public final Block WALL_BLOCK;
	public final Item ITEM;

	public WalledBlock(Block block, Block wallBlock, Item item) {
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
