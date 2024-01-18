package fun.mousewich.block.oxidizable;

import fun.mousewich.block.basic.ModDoorBlock;
import fun.mousewich.util.OxidationScale;
import net.minecraft.block.BlockState;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.Oxidizable;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;

import java.util.Optional;
import java.util.Random;

public class OxidizableDoorBlock extends ModDoorBlock implements Oxidizable {
	private final OxidizationLevel level;
	public OxidizableDoorBlock(OxidizationLevel level, Settings settings) {
		super(settings);
		this.level = level;
	}
	public OxidizableDoorBlock(OxidizationLevel level, Settings settings, SoundEvent openSound, SoundEvent closeSound) {
		super(settings, openSound, closeSound);
		this.level = level;
	}
	@Override
	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		if (state.get(DoorBlock.HALF) == DoubleBlockHalf.LOWER) {
			this.tickDegradation(state, world, pos, random);
		}
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
