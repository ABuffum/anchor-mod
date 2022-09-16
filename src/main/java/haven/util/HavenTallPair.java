package haven.util;

import haven.HavenMod;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.TallBlockItem;

public class HavenTallPair extends HavenPair {

	public HavenTallPair(Block block) {

		this(block, HavenMod.ItemSettings());
	}
	public HavenTallPair(Block block, Item.Settings settings) {
		super(block, new TallBlockItem(block, settings));
	}
}
