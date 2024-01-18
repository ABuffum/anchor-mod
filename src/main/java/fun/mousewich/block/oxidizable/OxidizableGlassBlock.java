package fun.mousewich.block.oxidizable;

import fun.mousewich.block.basic.ModGlassBlock;
import fun.mousewich.container.IBlockItemContainer;
import fun.mousewich.util.OxidationScale;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Oxidizable;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import java.util.Optional;
import java.util.Random;

public class OxidizableGlassBlock extends ModGlassBlock implements Oxidizable {
	private final OxidizationLevel level;
	public OxidizableGlassBlock(OxidizationLevel level, Settings settings) {
		super(settings);
		this.level = level;
	}
	public OxidizableGlassBlock(OxidizationLevel level, Block block) { this(level, Settings.copy(block)); }
	public OxidizableGlassBlock(OxidizationLevel level, IBlockItemContainer block) { this(level, block.asBlock()); }
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
