package fun.wich.gen.placer;

import fun.wich.ModBase;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluids;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.foliage.FoliagePlacer;

import java.util.Random;

public class CherryFoliagePlacer {

	public CherryFoliagePlacer() { }

	public interface BlockPlacer {
		void placeBlock(BlockPos var1, BlockState var2);
		boolean hasPlacedBlock(BlockPos var1);
	}

	protected boolean isPositionInvalid(Random random, int dx, int y, int dz, int radius, boolean giantTrunk) {
		if (giantTrunk) {
			int i = Math.min(Math.abs(dx), Math.abs(dx - 1));
			int j = Math.min(Math.abs(dz), Math.abs(dz - 1));
			return this.isInvalidForLeaves(random, i, y, j, radius);
		}
		else return this.isInvalidForLeaves(random, Math.abs(dx), y, Math.abs(dz), radius);
	}
	protected void generateSquare(TestableWorld world, BlockPlacer placer, Random random, BlockPos centerPos, int radius, int y, boolean giantTrunk) {
		int i = giantTrunk ? 1 : 0;
		BlockPos.Mutable mutable = new BlockPos.Mutable();
		for (int j = -radius; j <= radius + i; ++j) {
			for (int k = -radius; k <= radius + i; ++k) {
				if (this.isPositionInvalid(random, j, y, k, radius, giantTrunk)) continue;
				mutable.set(centerPos, j, y, k);
				placeFoliageBlock(world, placer, mutable);
			}
		}
	}
	protected final void generateSquareWithHangingLeaves(TestableWorld world, BlockPlacer placer, Random random, BlockPos centerPos, int radius, int y, boolean giantTrunk) {
		this.generateSquare(world, placer, random, centerPos, radius, y, giantTrunk);
		int i = giantTrunk ? 1 : 0;
		BlockPos.Mutable mutable = new BlockPos.Mutable();
		for (Direction direction : Direction.Type.HORIZONTAL) {
			Direction direction2 = direction.rotateYClockwise();
			int j = direction2.getDirection() == Direction.AxisDirection.POSITIVE ? radius + i : radius;
			mutable.set(centerPos, 0, y - 1, 0).move(direction2, j).move(direction, -radius);
			for (int k = -radius; k < radius + i; ++k) {
				boolean bl = placer.hasPlacedBlock(mutable.move(Direction.UP));
				mutable.move(Direction.DOWN);
				if (bl && !(random.nextFloat() > (float) 0.16666667) && placeFoliageBlock(world, placer, mutable) && !(random.nextFloat() > (float) 0.33333334)) {
					placeFoliageBlock(world, placer, mutable.move(Direction.DOWN));
					mutable.move(Direction.UP);
				}
				mutable.move(direction);
			}
		}
	}
	protected static boolean placeFoliageBlock(TestableWorld world, BlockPlacer placer, BlockPos pos) {
		if (!TreeFeature.canReplace(world, pos)) return false;
		BlockState blockState = ModBase.CHERRY_LEAVES.asBlock().getDefaultState();
		if (blockState.contains(Properties.WATERLOGGED)) {
			blockState = blockState.with(Properties.WATERLOGGED, world.testFluidState(pos, fluidState -> fluidState.isEqualAndStill(Fluids.WATER)));
		}
		placer.placeBlock(pos, blockState);
		return true;
	}

	public void generate(TestableWorld world, BlockPlacer placer, Random random, FoliagePlacer.TreeNode treeNode, int foliageHeight, int radius, int offset) {
		boolean bl = treeNode.isGiantTrunk();
		BlockPos blockPos = treeNode.getCenter().up(offset);
		int i = radius + treeNode.getFoliageRadius() - 1;
		generateSquare(world, placer, random, blockPos, i - 2, foliageHeight - 3, bl);
		generateSquare(world, placer, random, blockPos, i - 1, foliageHeight - 4, bl);
		for (int j = foliageHeight - 5; j >= 0; --j) {
			this.generateSquare(world, placer, random, blockPos, i, j, bl);
		}
		generateSquareWithHangingLeaves(world, placer, random, blockPos, i, -1, bl);
		generateSquareWithHangingLeaves(world, placer, random, blockPos, i - 1, -2, bl);
	}

	protected boolean isInvalidForLeaves(Random random, int dx, int y, int dz, int radius) {
		if (y == -1 && (dx == radius || dz == radius) && random.nextFloat() < 0.25f) return true;
		boolean bl = dx == radius && dz == radius;
		if (radius > 2) return bl || dx + dz > radius * 2 - 2 && random.nextFloat() < 0.5f;
		return bl && random.nextFloat() < 0.5f;
	}
}
