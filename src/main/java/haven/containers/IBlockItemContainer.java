package haven.containers;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

public interface IBlockItemContainer extends IContainer {
	public Block getBlock();
	public Item getItem();
}
