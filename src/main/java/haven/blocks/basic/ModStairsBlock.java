package haven.blocks.basic;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.StairsBlock;

public class ModStairsBlock extends StairsBlock {
	public ModStairsBlock(Block block) {
		this(block.getDefaultState(), Settings.copy(block));
	}
	public ModStairsBlock(BlockState blockState, Settings settings) {
		super(blockState, settings);
	}
}
