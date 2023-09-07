package fun.mousewich.block.glass;

import fun.mousewich.block.BlockConvertible;
import net.minecraft.block.AbstractGlassBlock;
import net.minecraft.block.Block;

public class ModStainedGlassBlock extends AbstractGlassBlock {
	public ModStainedGlassBlock(BlockConvertible block) { this(block.asBlock()); }
	public ModStainedGlassBlock(Block block) { this(Settings.copy(block)); }
	public ModStainedGlassBlock(Settings settings) { super(settings); }
}
