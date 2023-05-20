package fun.mousewich.block.basic;

import fun.mousewich.block.BlockConvertible;
import net.minecraft.block.Block;
import net.minecraft.block.NyliumBlock;

public class ModNyliumBlock extends NyliumBlock {
	public ModNyliumBlock(BlockConvertible block) { this(block.asBlock()); }
	public ModNyliumBlock(Block block) { this(Settings.copy(block)); }
	public ModNyliumBlock(Settings settings) { super(settings); }
}
