package haven.blocks.oxidizable;

import haven.util.OxidationScale;
import net.minecraft.block.BlockState;
import net.minecraft.block.Oxidizable;
import net.minecraft.block.TorchBlock;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import java.util.Optional;
import java.util.Random;

public class OxidizableTorchBlock extends TorchBlock implements Oxidizable {
	private final Oxidizable.OxidizationLevel oxidizationLevel;

	public OxidizableTorchBlock(Oxidizable.OxidizationLevel oxidizationLevel, Settings settings, ParticleEffect particle) {
		super(settings, particle);
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
