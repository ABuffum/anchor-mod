package fun.wich.gen.placer;

import fun.wich.ModBase;
import fun.wich.gen.provider.WeightedListIntProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PillarBlock;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.foliage.FoliagePlacer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class CherryTrunkPlacer {
	protected final int baseHeight;
	protected final int firstRandomHeight;
	protected final int secondRandomHeight;
	private final IntProvider branchCount;
	private final IntProvider branchHorizontalLength;
	private final UniformIntProvider branchStartOffsetFromTop;
	private final IntProvider branchEndOffsetFromTop;

	public CherryTrunkPlacer() {
		this.baseHeight = 7;
		this.firstRandomHeight = 1;
		this.secondRandomHeight = 0;
		this.branchCount = new WeightedListIntProvider(DataPool.<IntProvider>builder()
				.add(ConstantIntProvider.create(1), 1)
				.add(ConstantIntProvider.create(2), 1)
				.add(ConstantIntProvider.create(3), 1)
				.build());
		this.branchHorizontalLength = UniformIntProvider.create(2, 4);
		this.branchStartOffsetFromTop = UniformIntProvider.create(-4, -3);
		this.branchEndOffsetFromTop = UniformIntProvider.create(-1, 0);
	}

	protected void getAndSetState(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, BlockPos pos, Function<BlockState, BlockState> function) {
		if (TreeFeature.canReplace(world, pos)) {
			replacer.accept(pos, function.apply(ModBase.CHERRY_LOG.asBlock().getDefaultState()));
		}
	}

	public List<FoliagePlacer.TreeNode> generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, int height, BlockPos startPos) {
		int k;
		BlockPos down = startPos.down();
		if (!world.testBlockState(down, state -> Feature.isSoil(state) && !state.isOf(Blocks.GRASS_BLOCK) && !state.isOf(Blocks.MYCELIUM))) {
			replacer.accept(down, Blocks.DIRT.getDefaultState());
		}
		int i = Math.max(0, height - 1 + this.branchStartOffsetFromTop.get(random));
		int j = Math.max(0, height - 5);
		if (j >= i) ++j;
		boolean bl = (k = this.branchCount.get(random)) == 3;
		boolean bl2 = k >= 2;
		int l = bl ? height : (bl2 ? Math.max(i, j) + 1 : i + 1);
		for (int m = 0; m < l; ++m) {
			this.getAndSetState(world, replacer, startPos.up(m), Function.identity());
		}
		ArrayList<FoliagePlacer.TreeNode> list = new ArrayList<>();
		if (bl) list.add(new FoliagePlacer.TreeNode(startPos.up(l), 0, false));
		BlockPos.Mutable mutable = new BlockPos.Mutable();
		Direction direction = Direction.Type.HORIZONTAL.random(random);
		Function<BlockState, BlockState> function = state -> {
			if (state.contains(PillarBlock.AXIS)) return state.with(PillarBlock.AXIS, direction.getAxis());
			return state;
		};
		list.add(this.generateBranch(world, replacer, random, height, startPos, function, direction, i, i < l - 1, mutable));
		if (bl2) {
			list.add(this.generateBranch(world, replacer, random, height, startPos, function, direction.getOpposite(), j, j < l - 1, mutable));
		}
		return list;
	}

	private FoliagePlacer.TreeNode generateBranch(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, int height, BlockPos startPos, Function<BlockState, BlockState> withAxisFunction, Direction direction, int branchStartOffset, boolean branchBelowHeight, BlockPos.Mutable mutablePos) {
		int m;
		mutablePos.set(startPos).move(Direction.UP, branchStartOffset);
		int i = height - 1 + this.branchEndOffsetFromTop.get(random);
		boolean bl = branchBelowHeight || i < branchStartOffset;
		int j = this.branchHorizontalLength.get(random) + (bl ? 1 : 0);
		BlockPos blockPos = startPos.offset(direction, j).up(i);
		int k = bl ? 2 : 1;
		for (int l = 0; l < k; ++l) this.getAndSetState(world, replacer, mutablePos.move(direction), withAxisFunction);
		Direction direction2 = blockPos.getY() > mutablePos.getY() ? Direction.UP : Direction.DOWN;
		while ((m = mutablePos.getManhattanDistance(blockPos)) != 0) {
			float f = (float)Math.abs(blockPos.getY() - mutablePos.getY()) / (float)m;
			boolean bl2 = random.nextFloat() < f;
			mutablePos.move(bl2 ? direction2 : direction);
			this.getAndSetState(world, replacer, mutablePos, bl2 ? Function.identity() : withAxisFunction);
		}
		return new FoliagePlacer.TreeNode(blockPos.up(), 0, false);
	}

	public int getHeight(Random random) {
		return this.baseHeight + random.nextInt(this.firstRandomHeight + 1) + random.nextInt(this.secondRandomHeight + 1);
	}
}

