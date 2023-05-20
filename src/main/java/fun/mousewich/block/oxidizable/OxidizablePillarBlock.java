package fun.mousewich.block.oxidizable;

import fun.mousewich.block.BlockConvertible;
import fun.mousewich.block.basic.ModPillarBlock;
import fun.mousewich.util.OxidationScale;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Oxidizable;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import java.util.Optional;
import java.util.Random;

public class OxidizablePillarBlock extends ModPillarBlock implements Oxidizable {
	private final OxidizationLevel level;
	public OxidizablePillarBlock(OxidizationLevel level, BlockConvertible block) { super(block); this.level = level; }
	public OxidizablePillarBlock(OxidizationLevel level, Block block) { super(block); this.level = level; }
	public OxidizablePillarBlock(OxidizationLevel level, Settings settings) { super(settings); this.level = level; }
	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		this.tickDegradation(state, world, pos, random);
	}
	public boolean hasRandomTicks(BlockState state) {
		return OxidationScale.getIncreasedBlock(state.getBlock()).isPresent();
	}
	public OxidizationLevel getDegradationLevel() { return this.level; }
	@Override
	public Optional<BlockState> getDegradationResult(BlockState state) {
		return OxidationScale.getIncreasedBlock(state.getBlock()).map((block) -> block.getStateWithProperties(state));
	}
}
