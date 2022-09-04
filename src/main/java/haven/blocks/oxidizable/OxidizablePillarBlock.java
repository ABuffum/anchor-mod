package haven.blocks.oxidizable;

import haven.util.OxidationScale;
import net.minecraft.block.BlockState;
import net.minecraft.block.Oxidizable;
import net.minecraft.block.PillarBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import java.util.Optional;
import java.util.Random;

public class OxidizablePillarBlock extends PillarBlock implements Oxidizable {
	private final Oxidizable.OxidizationLevel oxidizationLevel;

	public OxidizablePillarBlock(Oxidizable.OxidizationLevel oxidizationLevel, Settings settings) {
		super(settings);
		this.oxidizationLevel = oxidizationLevel;
	}

	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		this.tickDegradation(state, world, pos, random);
	}

	public boolean hasRandomTicks(BlockState state) {
		return OxidationScale.getIncreasedOxidationBlock(state.getBlock()).isPresent();
	}

	public Oxidizable.OxidizationLevel getDegradationLevel() {
		return this.oxidizationLevel;
	}

	@Override
	public Optional<BlockState> getDegradationResult(BlockState state) {
		return OxidationScale.getIncreasedOxidationBlock(state.getBlock()).map((block) -> {
			return block.getStateWithProperties(state);
		});
	}
}
