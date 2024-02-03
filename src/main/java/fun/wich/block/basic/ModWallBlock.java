package fun.wich.block.basic;

import fun.wich.block.BlockConvertible;
import net.minecraft.block.Block;
import net.minecraft.block.WallBlock;

public class ModWallBlock extends WallBlock {
	public ModWallBlock(BlockConvertible block) { this(block.asBlock()); }
	public ModWallBlock(Block block) { this(Settings.copy(block)); }
	public ModWallBlock(Settings settings) { super(settings); }
}
