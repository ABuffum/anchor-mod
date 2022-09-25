package haven.containers;

import haven.HavenMod;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

public class BlockContainer {
	public final Block BLOCK;
	public final Item ITEM;

	public BlockContainer(Block block) {
		BLOCK = block;
		ITEM = new BlockItem(block, HavenMod.ItemSettings());
	}
	public BlockContainer(Block block, Item.Settings settings) {
		BLOCK = block;
		ITEM = new BlockItem(block, settings);
	}
	public BlockContainer(Block block, Item item) {
		BLOCK = block;
		ITEM = item;
	}

	public boolean contains(Block block) { return block == BLOCK; }
	public boolean contains(Item item) { return item == ITEM; }
}
