package fun.wich.gen.feature;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import fun.wich.gen.feature.config.CherryTreeFeatureConfig;
import fun.wich.gen.placer.CherryFoliagePlacer;
import fun.wich.gen.placer.CherryTrunkPlacer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.BitSetVoxelSet;
import net.minecraft.util.shape.VoxelSet;
import net.minecraft.world.ModifiableWorld;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.treedecorator.BeehiveTreeDecorator;

import java.util.*;
import java.util.function.BiConsumer;

public class CherryTreeFeature extends Feature<CherryTreeFeatureConfig> {
	public CherryTreeFeature(Codec<CherryTreeFeatureConfig> codec) { super(codec); }

	private boolean generate(StructureWorldAccess world, Random random, BlockPos pos, BiConsumer<BlockPos, BlockState> rootPlacerReplacer, BiConsumer<BlockPos, BlockState> trunkPlacerReplacer, CherryFoliagePlacer.BlockPlacer blockPlacer) {
		CherryTrunkPlacer trunkPlacer = new CherryTrunkPlacer();
		CherryFoliagePlacer foliagePlacer = new CherryFoliagePlacer();
		int i = trunkPlacer.getHeight(random);
		int j = 5;
		int l = 0;//4;
		int m = pos.getY();
		int n = m + i + 1;
		if (m < world.getBottomY() + 1 || n > world.getTopY()) return false;
		OptionalInt optionalInt = new TwoLayersFeatureSize(1, 0, 2).getMinClippedHeight();
		int o = this.getTopPosition(world, i, pos);
		if (o < i && (optionalInt.isEmpty() || o < optionalInt.getAsInt())) {
			return false;
		}
		List<FoliagePlacer.TreeNode> list = trunkPlacer.generate(world, trunkPlacerReplacer, random, o, pos);
		list.forEach(node -> foliagePlacer.generate(world, blockPlacer, random, node, o, j, l));
		return true;
	}

	private int getTopPosition(TestableWorld world, int height, BlockPos pos) {
		BlockPos.Mutable mutable = new BlockPos.Mutable();
		TwoLayersFeatureSize minimumSize = new TwoLayersFeatureSize(1, 0, 2);
		for (int i = 0; i <= height + 1; ++i) {
			int j = minimumSize.getRadius(height, i);
			for (int k = -j; k <= j; ++k) {
				for (int l = -j; l <= j; ++l) {
					mutable.set(pos, k, i, l);
					if (TreeFeature.canReplace(world, mutable) || world.testBlockState(mutable, state -> state.isIn(BlockTags.LOGS))) continue;
					return i - 2;
				}
			}
		}
		return height;
	}

	@Override
	protected void setBlockState(ModifiableWorld world, BlockPos pos, BlockState state) {
		world.setBlockState(pos, state, Block.NOTIFY_ALL | Block.FORCE_STATE);
	}
	private static void setBlockStateWithoutUpdatingNeighbors(ModifiableWorld world, BlockPos pos, BlockState state) {
		world.setBlockState(pos, state, Block.NOTIFY_ALL | Block.FORCE_STATE);
	}
	private static VoxelSet placeLogsAndLeaves(WorldAccess world, BlockBox box, Set<BlockPos> trunkPositions, Set<BlockPos> decorationPositions, Set<BlockPos> set) {
		ArrayList<Set<BlockPos>> list = Lists.newArrayList();
		BitSetVoxelSet voxelSet = new BitSetVoxelSet(box.getBlockCountX(), box.getBlockCountY(), box.getBlockCountZ());
		for (int j = 0; j < 6; ++j) list.add(Sets.newHashSet());
		BlockPos.Mutable mutable = new BlockPos.Mutable();
		for (BlockPos blockPos : Lists.newArrayList(Sets.union(decorationPositions, set))) {
			if (!box.contains(blockPos)) continue;
			voxelSet.set(blockPos.getX() - box.getMinX(), blockPos.getY() - box.getMinY(), blockPos.getZ() - box.getMinZ());
		}
		for (BlockPos blockPos : Lists.newArrayList(trunkPositions)) {
			if (box.contains(blockPos)) {
				voxelSet.set(blockPos.getX() - box.getMinX(), blockPos.getY() - box.getMinY(), blockPos.getZ() - box.getMinZ());
			}
			for (Direction direction : Direction.values()) {
				BlockState blockState;
				mutable.set(blockPos, direction);
				if (trunkPositions.contains(mutable) || !(blockState = world.getBlockState(mutable)).contains(Properties.DISTANCE_1_7)) continue;
				list.get(0).add(mutable.toImmutable());
				setBlockStateWithoutUpdatingNeighbors(world, mutable, blockState.with(Properties.DISTANCE_1_7, 1));
				if (!box.contains(mutable)) continue;
				voxelSet.set(mutable.getX() - box.getMinX(), mutable.getY() - box.getMinY(), mutable.getZ() - box.getMinZ());
			}
		}
		for (int k = 1; k < 6; ++k) {
			Set<BlockPos> set2 = list.get(k - 1);
			Set<BlockPos> set3 = list.get(k);
			for (BlockPos blockPos2 : set2) {
				if (box.contains(blockPos2)) {
					voxelSet.set(blockPos2.getX() - box.getMinX(), blockPos2.getY() - box.getMinY(), blockPos2.getZ() - box.getMinZ());
				}
				for (Direction direction2 : Direction.values()) {
					BlockState blockState2;
					mutable.set(blockPos2, direction2);
					if (set2.contains(mutable) || set3.contains(mutable) || !(blockState2 = world.getBlockState(mutable)).contains(Properties.DISTANCE_1_7) || blockState2.get(Properties.DISTANCE_1_7) <= k + 1) continue;
					BlockState blockState3 = blockState2.with(Properties.DISTANCE_1_7, k + 1);
					setBlockStateWithoutUpdatingNeighbors(world, mutable, blockState3);
					if (box.contains(mutable)) {
						voxelSet.set(mutable.getX() - box.getMinX(), mutable.getY() - box.getMinY(), mutable.getZ() - box.getMinZ());
					}
					set3.add(mutable.toImmutable());
				}
			}
		}
		return voxelSet;
	}

	@Override
	public final boolean generate(FeatureContext<CherryTreeFeatureConfig> context) {
		final StructureWorldAccess structureWorldAccess = context.getWorld();
		Random random = context.getRandom();
		BlockPos blockPos = context.getOrigin();
		ArrayList<BlockPos> set = new ArrayList<>();
		ArrayList<BlockPos> set2 = new ArrayList<>();
		final ArrayList<BlockPos> set3 = new ArrayList<>();
		ArrayList<BlockPos> set4 = new ArrayList<>();
		BiConsumer<BlockPos, BlockState> biConsumer = (pos, state) -> {
			set.add(pos.toImmutable());
			structureWorldAccess.setBlockState(pos, state, Block.NOTIFY_ALL | Block.FORCE_STATE);
		};
		BiConsumer<BlockPos, BlockState> biConsumer2 = (pos, state) -> {
			set2.add(pos.toImmutable());
			structureWorldAccess.setBlockState(pos, state, Block.NOTIFY_ALL | Block.FORCE_STATE);
		};
		CherryFoliagePlacer.BlockPlacer blockPlacer = new CherryFoliagePlacer.BlockPlacer(){
			@Override
			public void placeBlock(BlockPos pos, BlockState state) {
				set3.add(pos.toImmutable());
				structureWorldAccess.setBlockState(pos, state, Block.NOTIFY_ALL | Block.FORCE_STATE);
			}
			@Override
			public boolean hasPlacedBlock(BlockPos pos) { return set3.contains(pos); }
		};
		BiConsumer<BlockPos, BlockState> biConsumer3 = (pos, state) -> {
			set4.add(pos.toImmutable());
			structureWorldAccess.setBlockState(pos, state, Block.NOTIFY_ALL | Block.FORCE_STATE);
		};
		boolean bl = this.generate(structureWorldAccess, random, blockPos, biConsumer, biConsumer2, blockPlacer);
		if (!bl || set2.isEmpty() && set3.isEmpty()) return false;
		//Decorators
		if (context.getConfig().bees) {
			new BeehiveTreeDecorator(0.05f).generate(structureWorldAccess, biConsumer3, random, set2, set3);
		}
		//Final
		return BlockBox.encompassPositions(Iterables.concat(set, set2, set3, set4)).map(box -> {
			VoxelSet voxelSet = placeLogsAndLeaves(structureWorldAccess, box, new HashSet<>(set2), new HashSet<>(set4), new HashSet<>(set));
			updateCorner(structureWorldAccess, 3, voxelSet, box.getMinX(), box.getMinY(), box.getMinZ());
			return true;
		}).orElse(false);
	}

	public static void updateCorner(WorldAccess world, int flags, VoxelSet set, int startX, int startY, int startZ) {
		set.forEachDirection((direction, x, y, z) -> {
			BlockState blockState4;
			BlockState blockState2;
			BlockState blockState3;
			BlockPos blockPos = new BlockPos(startX + x, startY + y, startZ + z);
			BlockPos blockPos2 = blockPos.offset(direction);
			BlockState blockState = world.getBlockState(blockPos);
			if (blockState != (blockState3 = blockState.getStateForNeighborUpdate(direction, blockState2 = world.getBlockState(blockPos2), world, blockPos, blockPos2))) {
				world.setBlockState(blockPos, blockState3, flags & ~Block.NOTIFY_NEIGHBORS);
			}
			if (blockState2 != (blockState4 = blockState2.getStateForNeighborUpdate(direction.getOpposite(), blockState3, world, blockPos2, blockPos))) {
				world.setBlockState(blockPos2, blockState4, flags & ~Block.NOTIFY_NEIGHBORS);
			}
		});
	}
}