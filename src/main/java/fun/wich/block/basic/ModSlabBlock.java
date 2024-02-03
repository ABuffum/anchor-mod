package fun.wich.block.basic;

import fun.wich.block.BlockConvertible;
import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;

public class ModSlabBlock extends SlabBlock {
	public ModSlabBlock(BlockConvertible block) { this(block.asBlock()); }
	public ModSlabBlock(Block block) { this(Settings.copy(block)); }
	public ModSlabBlock(Settings settings) { super(settings); }
}
