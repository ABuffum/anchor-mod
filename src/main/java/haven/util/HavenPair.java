package haven.util;

import haven.HavenMod;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

public class HavenPair {
	public final Block BLOCK;
	public final Item ITEM;

	public HavenPair(Block block) {
		this(block, HavenMod.ITEM_SETTINGS);
	}
	public HavenPair(Block block, Item.Settings settings) {
		this(block, new BlockItem(block, settings));
	}
	public HavenPair(Block block, Item item) {
		BLOCK = block;
		ITEM = item;
	}
}
