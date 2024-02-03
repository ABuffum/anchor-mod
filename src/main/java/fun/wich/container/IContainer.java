package fun.wich.container;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

public interface IContainer {
	boolean contains(Block block);
	boolean contains(Item item);
}
