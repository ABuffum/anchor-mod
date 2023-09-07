package fun.mousewich.block.glass;

import fun.mousewich.block.BlockConvertible;
import net.minecraft.block.Block;

public class ModStainedGlassTrapdoorBlock extends AbstractGlassTrapdoorBlock {
	public ModStainedGlassTrapdoorBlock(BlockConvertible block, BlockConvertible slab) { this(block.asBlock(), slab.asBlock()); }
	public ModStainedGlassTrapdoorBlock(Block block, Block slab) { super(block, slab); }
}
