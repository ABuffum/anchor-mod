package fun.wich.gen.placer;

import com.google.common.collect.Lists;
import fun.wich.ModBase;
import fun.wich.gen.data.tag.ModBlockTags;
import net.minecraft.block.BlockState;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.foliage.FoliagePlacer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

public class MangroveTrunkPlacer {
	protected final int baseHeight;
	protected final int firstRandomHeight;
	protected final int secondRandomHeight;

	private final IntProvider extraBranchSteps;
	private final IntProvider extraBranchLength;

	public MangroveTrunkPlacer(int baseHeight, int firstRandomHeight, int secondRandomHeight) {
		this.baseHeight = baseHeight;
		this.firstRandomHeight = firstRandomHeight;
		this.secondRandomHeight = secondRandomHeight;
		this.extraBranchSteps = secondRandomHeight == 4 ? UniformIntProvider.create(1, 4) : UniformIntProvider.create(1, 6);
		this.extraBranchLength = UniformIntProvider.create(0, 1);
	}

	public List<FoliagePlacer.TreeNode> generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, int height, BlockPos startPos) {
		ArrayList<FoliagePlacer.TreeNode> list = Lists.newArrayList();
		BlockPos.Mutable mutable = new BlockPos.Mutable();
		for (int i = 0; i < height; ++i) {
			int j = startPos.getY() + i;

			if (this.canReplace(world, mutable.set(startPos.getX(), j, startPos.getZ()))) {
				replacer.accept(mutable, ModBase.MANGROVE_LOG.asBlock().getDefaultState());
				if (i < height - 1 && random.nextFloat() < 0.5f) {
					Direction direction = Direction.Type.HORIZONTAL.random(random);
					int k = this.extraBranchLength.get(random);
					int l = Math.max(0, k - this.extraBranchLength.get(random) - 1);
					int m = this.extraBranchSteps.get(random);
					this.generateExtraBranch(world, replacer, height, list, mutable, j, direction, l, m);
				}
			}
			if (i != height - 1) continue;
			list.add(new FoliagePlacer.TreeNode(mutable.set(startPos.getX(), j + 1, startPos.getZ()), 0, false));
		}
		return list;
	}

	private void generateExtraBranch(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, int height, List<FoliagePlacer.TreeNode> nodes, BlockPos.Mutable pos, int yOffset, Direction direction, int length, int steps) {
		int i = yOffset + length;
		int j = pos.getX();
		int k = pos.getZ();
		for (int l = length; l < height && steps > 0; ++l, --steps) {
			if (l < 1) continue;
			int m = yOffset + l;
			i = m;
			if (this.canReplace(world, pos.set(j += direction.getOffsetX(), m, k += direction.getOffsetZ()))) {
				replacer.accept(pos, ModBase.MANGROVE_LOG.asBlock().getDefaultState());
				++i;
			}
			nodes.add(new FoliagePlacer.TreeNode(pos.toImmutable(), 0, false));
		}
		if (i - yOffset > 1) {
			BlockPos blockPos = new BlockPos(j, i, k);
			nodes.add(new FoliagePlacer.TreeNode(blockPos, 0, false));
			nodes.add(new FoliagePlacer.TreeNode(blockPos.down(2), 0, false));
		}
	}

	protected boolean canReplace(TestableWorld world, BlockPos pos) {
		return TreeFeature.canReplace(world, pos) || world.testBlockState(pos, state -> state.isIn(ModBlockTags.MANGROVE_LOGS_CAN_GROW_THROUGH));
	}

	public boolean canReplaceOrIsLog(TestableWorld world, BlockPos pos) {
		return this.canReplace(world, pos) || world.testBlockState(pos, state -> state.isIn(BlockTags.LOGS));
	}

	public int getHeight(Random random) {
		return this.baseHeight + random.nextInt(this.firstRandomHeight + 1) + random.nextInt(this.secondRandomHeight + 1);
	}
}