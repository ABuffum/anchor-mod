package haven.blocks.lighting;

import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;

import java.util.Random;

public class LitAirBlock extends AirBlock implements DynamicLitBlock {
	private final Block unlitBlock;
	public Block getUnlitBlock() { return unlitBlock; }
	public LitAirBlock(Block unlitBlock) {
		super(AbstractBlock.Settings.of(Material.AIR).noCollision().ticksRandomly().luminance(DynamicLitBlock::GetLuminance).dropsNothing().air());
		this.unlitBlock = unlitBlock;
		this.setDefaultState(this.stateManager.getDefaultState().with(LUMINANCE, 1));
	}
	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) { builder.add(LUMINANCE); }
	@Override
	public boolean hasRandomTicks(BlockState state) { return true; }
	@Override
	public void randomTick(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, Random random) {
		if (DynamicLightManager.isUnknownLitPosition(serverWorld, blockPos)) {
			serverWorld.setBlockState(blockPos, getUnlitBlock().getDefaultState(), 3);
		}
	}
}
