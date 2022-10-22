package haven.blocks.lighting;

import haven.blocks.basic.HavenLadderBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;

import java.util.Random;

public class LitLadderBlock extends HavenLadderBlock implements DynamicLitBlock {
	private final Block unlitBlock;
	public Block getUnlitBlock() { return unlitBlock; }
	public LitLadderBlock(Block unlitBlock) {
		super(AbstractBlock.Settings.copy(unlitBlock).luminance(DynamicLitBlock::GetLuminance));
		this.unlitBlock = unlitBlock;
		this.setDefaultState(this.stateManager.getDefaultState().with(LUMINANCE, 1));
	}
	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		super.appendProperties(builder);
		builder.add(LUMINANCE);
	}
	@Override
	public boolean hasRandomTicks(BlockState state) { return true; }
	@Override
	public void randomTick(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, Random random) {
		if (DynamicLightManager.isUnknownLitPosition(serverWorld, blockPos)) {
			serverWorld.setBlockState(blockPos, getUnlitBlock().getDefaultState(), 3);
		}
	}
}
