package haven.blocks.basic;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.StairsBlock;

public class HavenStairsBlock extends StairsBlock {
	public HavenStairsBlock(Block block) {
		this(block.getDefaultState(), Settings.copy(block));
	}
	public HavenStairsBlock(BlockState blockState, Settings settings) {
		super(blockState, settings);
	}
}
