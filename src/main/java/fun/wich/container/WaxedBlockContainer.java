package fun.wich.container;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class WaxedBlockContainer extends BlockContainer implements IContainer {
	protected final BlockContainer unwaxed;
	public BlockContainer getUnwaxed() { return unwaxed; }

	public WaxedBlockContainer(BlockContainer unwaxed, Block block, Item.Settings settings) {
		super(block, settings);
		this.unwaxed = unwaxed;
	}
	public WaxedBlockContainer(BlockContainer unwaxed, Block block, Item item) {
		super(block, item);
		this.unwaxed = unwaxed;
	}
}
