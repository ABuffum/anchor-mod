package haven.blocks.sculk;

import haven.ModBase;
import haven.blocks.MultifaceGrowthBlock;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Random;

public interface SculkSpreadable {
	public static final SculkSpreadable VEIN_ONLY_SPREADER = new SculkSpreadable(){
		@Override
		public boolean spread(World world, BlockPos pos, BlockState state, @Nullable Collection<Direction> directions, boolean markForPostProcessing) {
			if (directions == null) {
				return ((SculkVeinBlock) ModBase.SCULK_VEIN.getBlock()).getSamePositionOnlyGrower().grow(world.getBlockState(pos), world, pos, markForPostProcessing) > 0L;
			}
			if (!directions.isEmpty()) {
				if (state.isAir() || state.getFluidState().getFluid() == Fluids.WATER) {
					return SculkVeinBlock.place(world, pos, state, directions);
				}
				return false;
			}
			return SculkSpreadable.super.spread(world, pos, state, directions, markForPostProcessing);
		}

		@Override
		public int spread(SculkSpreadManager.Cursor cursor, World world, BlockPos catalystPos, Random random, SculkSpreadManager spreadManager, boolean shouldConvertToBlock) {
			return cursor.getDecay() > 0 ? cursor.getCharge() : 0;
		}

		@Override
		public int getDecay(int oldDecay) { return Math.max(oldDecay - 1, 0); }
	};

	default public byte getUpdate() { return 1; }

	default public void spreadAtSamePosition(World world, BlockState state, BlockPos pos, Random random) { }

	default public boolean method_41470(WorldAccess world, BlockPos pos, Random random) {
		return false;
	}

	default public boolean spread(World world, BlockPos pos, BlockState state, @Nullable Collection<Direction> directions, boolean markForPostProcessing) {
		return ((MultifaceGrowthBlock) ModBase.SCULK_VEIN.getBlock()).getGrower().grow(state, world, pos, markForPostProcessing) > 0L;
	}

	default public boolean shouldConvertToSpreadable() { return true; }

	default public int getDecay(int oldDecay) { return 1; }

	public int spread(SculkSpreadManager.Cursor var1, World var2, BlockPos var3, Random var4, SculkSpreadManager var5, boolean var6);
}
