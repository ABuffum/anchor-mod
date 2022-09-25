package haven.containers;

import haven.containers.BlockContainer;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class WaxedBlockContainer extends BlockContainer {
	public final BlockContainer UNWAXED;

	public WaxedBlockContainer(BlockContainer unwaxed, Block block) {
		super(block);
		UNWAXED = unwaxed;
	}
	public WaxedBlockContainer(BlockContainer unwaxed, Block block, Item.Settings settings) {
		super(block, settings);
		UNWAXED = unwaxed;
	}
	public WaxedBlockContainer(BlockContainer unwaxed, Block block, Item item) {
		super(block, item);
		UNWAXED = unwaxed;
	}
}
