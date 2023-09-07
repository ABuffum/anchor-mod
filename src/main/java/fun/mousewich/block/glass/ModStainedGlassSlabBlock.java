package fun.mousewich.block.glass;

import fun.mousewich.block.BlockConvertible;
import net.minecraft.block.Block;

public class ModStainedGlassSlabBlock extends AbstractGlassSlabBlock {
	public ModStainedGlassSlabBlock(BlockConvertible block) { this(block.asBlock()); }
	public ModStainedGlassSlabBlock(Block block) { super(block); }
}