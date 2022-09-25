package haven.containers;

import haven.HavenMod;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.TallBlockItem;

public class TallBlockContainer extends BlockContainer {
	public TallBlockContainer(Block block) { this(block, HavenMod.ItemSettings()); }
	public TallBlockContainer(Block block, Item.Settings settings) { super(block, new TallBlockItem(block, settings)); }
}
