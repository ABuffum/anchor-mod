package haven.blocks.oxidizable;

import haven.blocks.UnlitLanternBlock;
import haven.containers.BlockContainer;
import haven.util.OxidationScale;
import net.minecraft.block.BlockState;
import net.minecraft.block.Oxidizable;
import net.minecraft.item.Item;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import java.util.Optional;
import java.util.Random;
import java.util.function.Supplier;

public class OxidizableUnlitLanternBlock extends UnlitLanternBlock implements Oxidizable {
	private final OxidizationLevel oxidizationLevel;

	public OxidizableUnlitLanternBlock(Supplier<BlockContainer> getBlock, OxidizationLevel oxidizationLevel, Settings settings) {
		super(getBlock, settings);
		this.oxidizationLevel = oxidizationLevel;
	}
	public OxidizableUnlitLanternBlock(LanternSupplier getPickStack, OxidizationLevel oxidizationLevel, Settings settings) {
		super(getPickStack, settings);
		this.oxidizationLevel = oxidizationLevel;
	}
	public OxidizableUnlitLanternBlock(Item pickStack, OxidizationLevel oxidizationLevel, Settings settings) {
		super(pickStack, settings);
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
