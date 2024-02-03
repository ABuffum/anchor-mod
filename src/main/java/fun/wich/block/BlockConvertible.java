package fun.wich.block;

import net.minecraft.block.Block;

/** Represents an object that has a block form. */
public interface BlockConvertible {
	/** Gets this object in its block form. */
	Block asBlock();
}
