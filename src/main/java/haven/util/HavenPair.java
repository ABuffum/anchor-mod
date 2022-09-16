package haven.util;

import haven.HavenMod;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

public class HavenPair {
	public final Block BLOCK;
	public final Item ITEM;

	public HavenPair(Block block) {
		BLOCK = block;
		ITEM = new BlockItem(block, HavenMod.ItemSettings());
	}
	public HavenPair(Block block, Item.Settings settings) {
		BLOCK = block;
		ITEM = new BlockItem(block, settings);
	}
	public HavenPair(Block block, Item item) {
		BLOCK = block;
		ITEM = item;
	}
}
