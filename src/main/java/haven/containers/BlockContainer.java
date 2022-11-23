package haven.containers;

import haven.ModBase;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

public class BlockContainer implements IBlockItemContainer {
	private final Block block;
	public Block getBlock() { return block; }
	private final Item item;
	public Item getItem() { return item; }

	public BlockContainer(Block block) {
		this.block = block;
		item = new BlockItem(block, ModBase.ItemSettings());
	}
	public BlockContainer(Block block, Item.Settings settings) {
		this.block = block;
		item = new BlockItem(block, settings);
	}
	public BlockContainer(Block block, Item item) {
		this.block = block;
		this.item = item;
	}

	public boolean contains(Block block) { return block == this.getBlock(); }
	public boolean contains(Item item) { return item == this.getItem(); }
}
