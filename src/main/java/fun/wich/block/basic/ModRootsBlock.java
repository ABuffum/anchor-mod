package fun.wich.block.basic;

import fun.wich.block.BlockConvertible;
import net.minecraft.block.Block;
import net.minecraft.block.RootsBlock;

public class ModRootsBlock extends RootsBlock {
	public ModRootsBlock(BlockConvertible block) { this(block.asBlock()); }
	public ModRootsBlock(Block block) { this(Settings.copy(block)); }
	public ModRootsBlock(Settings settings) { super(settings); }
}
