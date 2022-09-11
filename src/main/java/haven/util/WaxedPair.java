package haven.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class WaxedPair extends HavenPair {
	public final HavenPair UNWAXED;

	public WaxedPair(HavenPair unwaxed, Block block) {
		super(block);
		UNWAXED = unwaxed;
	}
	public WaxedPair(HavenPair unwaxed, Block block, Item.Settings settings) {
		super(block, settings);
		UNWAXED = unwaxed;
	}
	public WaxedPair(HavenPair unwaxed, Block block, Item item) {
		super(block, item);
		UNWAXED = unwaxed;
	}
}
