package fun.mousewich.block.basic;

import fun.mousewich.block.BlockConvertible;
import net.minecraft.block.Block;
import net.minecraft.block.FenceBlock;

public class ModFenceBlock extends FenceBlock {
	public ModFenceBlock(BlockConvertible block) { this(block.asBlock()); }
	public ModFenceBlock(Block block) { this(Settings.copy(block)); }
	public ModFenceBlock(Settings settings) { super(settings); }
}
