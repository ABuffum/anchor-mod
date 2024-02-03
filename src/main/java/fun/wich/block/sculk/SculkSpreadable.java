package fun.wich.block.sculk;

import fun.wich.ModBase;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;

import java.util.Collection;
import java.util.Random;

public interface SculkSpreadable {
	SculkSpreadable VEIN_ONLY_SPREADER = new SculkSpreadable(){
		@Override
		public boolean spread(WorldAccess world, BlockPos pos, BlockState state, Collection<Direction> directions, boolean markForPostProcessing) {
			if (directions == null) {
				return ((SculkVeinBlock)ModBase.SCULK_VEIN.asBlock()).getSamePositionOnlyGrower().grow(world.getBlockState(pos), world, pos, markForPostProcessing) > 0L;
			}
			if (!directions.isEmpty()) {
				if (state.isAir() || state.getFluidState().getFluid() == Fluids.WATER) return SculkVeinBlock.place(world, pos, state, directions);
				return false;
			}
			return SculkSpreadable.super.spread(world, pos, state, directions, markForPostProcessing);
		}
		@Override
		public int spread(SculkSpreadManager.Cursor cursor, WorldAccess world, BlockPos catalystPos, Random random, SculkSpreadManager spreadManager, boolean shouldConvertToBlock) {
			return cursor.getDecay() > 0 ? cursor.getCharge() : 0;
		}
		@Override
		public int getDecay(int oldDecay) { return Math.max(oldDecay - 1, 0); }
	};
	default void spreadAtSamePosition(WorldAccess world, BlockState state, BlockPos pos, Random random) { }
	default boolean spread(WorldAccess world, BlockPos pos, BlockState state, Collection<Direction> directions, boolean markForPostProcessing) {
		return ((SculkVeinBlock)ModBase.SCULK_VEIN.asBlock()).getGrower().grow(state, world, pos, markForPostProcessing) > 0L;
	}
	default boolean shouldConvertToSpreadable() { return true; }
	default int getDecay(int oldDecay) { return 1; }
	default byte getUpdate() { return 1; }
	int spread(SculkSpreadManager.Cursor cursor, WorldAccess world, BlockPos catalystPos, Random random, SculkSpreadManager spreadManager, boolean shouldConvertToBlock);
}
