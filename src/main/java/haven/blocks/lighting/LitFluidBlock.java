package haven.blocks.lighting;

import net.minecraft.block.*;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;

import java.util.Random;

public class LitFluidBlock extends FluidBlock implements DynamicLitBlock {
	private final Block unlitBlock;
	public Block getUnlitBlock() { return unlitBlock; }
	public LitFluidBlock(Block unlitBlock, FlowableFluid fluid) {
		super(fluid, AbstractBlock.Settings.of(Material.WATER).noCollision().strength(100.0F).luminance(DynamicLitBlock::GetLuminance).dropsNothing());
		this.unlitBlock = unlitBlock;
		this.setDefaultState(this.stateManager.getDefaultState().with(LEVEL, 0).with(LUMINANCE, 1));
	}
	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) { builder.add(LEVEL).add(LUMINANCE); }
	@Override
	public boolean hasRandomTicks(BlockState state) { return true; }
	@Override
	public void randomTick(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, Random random) {
		if (DynamicLightManager.isUnknownLitPosition(serverWorld, blockPos)) {
			serverWorld.setBlockState(blockPos, getUnlitBlock().getDefaultState(), 3);
		}
	}
}