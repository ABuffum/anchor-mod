package fun.mousewich.block.basic;

import fun.mousewich.block.BlockConvertible;
import net.minecraft.block.Block;
import net.minecraft.block.PillarBlock;

public class ModPillarBlock extends PillarBlock {
	public ModPillarBlock(BlockConvertible block) { this(block.asBlock()); }
	public ModPillarBlock(Block block) { this(Settings.copy(block)); }
	public ModPillarBlock(Settings settings) { super(settings); }
}
