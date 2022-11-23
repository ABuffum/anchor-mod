package haven.containers;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class WallBlockContainer implements IBlockItemContainer {
	private final Block block;
	public Block getBlock() { return block; }
	private final Block wallBlock;
	public Block getWallBlock() { return wallBlock; }
	private final Item item;
	public Item getItem() { return item; }

	public WallBlockContainer(Block block, Block wallBlock, Item item) {
		this.block = block;
		this.item = item;
		this.wallBlock = wallBlock;
	}

	public boolean contains(Block block) {
		return block == this.getBlock() || block == getWallBlock();
	}
	public boolean contains(Item item) {
		return item == this.getItem();
	}
}
