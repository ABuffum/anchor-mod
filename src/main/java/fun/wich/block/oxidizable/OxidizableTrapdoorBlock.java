package fun.wich.block.oxidizable;

import fun.wich.block.basic.ModTrapdoorBlock;
import fun.wich.sound.ModSoundEvents;
import fun.wich.util.OxidationScale;
import net.minecraft.block.BlockState;
import net.minecraft.block.Oxidizable;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import java.util.Optional;
import java.util.Random;

public class OxidizableTrapdoorBlock extends ModTrapdoorBlock implements Oxidizable {
	private final OxidizationLevel level;
	public OxidizableTrapdoorBlock(OxidizationLevel level, Settings settings) {
		super(settings, ModSoundEvents.BLOCK_COPPER_TRAPDOOR_OPEN, ModSoundEvents.BLOCK_COPPER_TRAPDOOR_CLOSE);
		this.level = level;
	}
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
