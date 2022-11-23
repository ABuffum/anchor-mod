package haven.blocks.oxidizable;

import haven.blocks.UnlitWallTorchBlock;
import haven.util.OxidationScale;
import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import java.util.Optional;
import java.util.Random;

public class OxidizableUnlitWallTorchBlock extends UnlitWallTorchBlock implements Oxidizable {
	private final OxidizationLevel oxidizationLevel;

	public OxidizableUnlitWallTorchBlock(OxidizationLevel oxidizationLevel, Block lit, Block notWall) {
		super(lit, notWall);
		this.oxidizationLevel = oxidizationLevel;
	}

	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		this.tickDegradation(state, world, pos, random);
	}

	public boolean hasRandomTicks(BlockState state) {
		return OxidationScale.getIncreasedOxidationBlock(state.getBlock()).isPresent();
	}

	public OxidizationLevel getDegradationLevel() {
		return this.oxidizationLevel;
	}

	@Override
	public Optional<BlockState> getDegradationResult(BlockState state) {
		return OxidationScale.getIncreasedOxidationBlock(state.getBlock()).map((block) -> {
			return block.getStateWithProperties(state);
		});
	}
}
