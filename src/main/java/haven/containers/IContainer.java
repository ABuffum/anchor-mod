package haven.containers;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

public interface IContainer {
	public boolean contains(Block block);
	public boolean contains(Item item);
}
