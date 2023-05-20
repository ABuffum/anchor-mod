package fun.mousewich.gen.feature;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import fun.mousewich.ModBase;
import fun.mousewich.block.mangrove.PropaguleBlock;
import fun.mousewich.gen.decorator.RandomLeavesVineTreeDecorator;
import fun.mousewich.gen.feature.config.MangroveTreeFeatureConfig;
import fun.mousewich.gen.provider.ModSimpleBlockStateProvider;
import fun.mousewich.util.CollectionUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.shape.BitSetVoxelSet;
import net.minecraft.util.shape.VoxelSet;
import net.minecraft.world.ModifiableWorld;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.RandomizedIntBlockStateProvider;
import net.minecraft.world.gen.treedecorator.BeehiveTreeDecorator;

import java.util.*;
import java.util.function.BiConsumer;

public class MangroveTreeFeature extends Feature<MangroveTreeFeatureConfig> {
	public MangroveTreeFeature(Codec<MangroveTreeFeatureConfig> codec) {
		super(codec);
	}

	private boolean generate(StructureWorldAccess world, Random random, BlockPos pos, BiConsumer<BlockPos, BlockState> rootPlacerReplacer, BiConsumer<BlockPos, BlockState> trunkPlacerReplacer, BiConsumer<BlockPos, BlockState> foliagePlacerReplacer, MangroveTreeFeatureConfig config) {
		int i = config.trunkPlacer.getHeight(random);
		BlockPos blockPos = pos.up(config.rootPlacer.trunkOffsetY.get(random));
		int m = Math.min(pos.getY(), blockPos.getY());
		int n = Math.max(pos.getY(), blockPos.getY()) + i + 1;
		if (m < world.getBottomY() + 1 || n > world.getTopY()) return false;
		OptionalInt optionalInt = config.minimumSize.getMinClippedHeight();
		int o = this.getTopPosition(world, i, blockPos, config);
		if (o < i && (optionalInt.isEmpty() || o < optionalInt.getAsInt())) return false;
		if (!config.rootPlacer.generate(world, rootPlacerReplacer, random, pos, blockPos, config)) return false;
		List<FoliagePlacer.TreeNode> list = config.trunkPlacer.generate(world, trunkPlacerReplacer, random, o, blockPos);
		list.forEach(node -> generateFoliage(world, foliagePlacerReplacer, random, node));
		return true;
	}

	public static void generateFoliage(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, FoliagePlacer.TreeNode treeNode) {
		BlockPos blockPos = treeNode.getCenter();
		BlockPos.Mutable mutable = blockPos.mutableCopy();
		for(int i = 0; i < 70; ++i) {
			mutable.set(blockPos, random.nextInt(3) - random.nextInt(3), random.nextInt(2) - random.nextInt(2), random.nextInt(3) - random.nextInt(3));
			if (TreeFeature.canReplace(world, mutable)) replacer.accept(mutable, ModBase.MANGROVE_LEAVES.asBlock().getDefaultState());
		}
	}

	private int getTopPosition(TestableWorld world, int height, BlockPos pos, MangroveTreeFeatureConfig config) {
		BlockPos.Mutable mutable = new BlockPos.Mutable();
		for (int i = 0; i <= height + 1; ++i) {
			int j = config.minimumSize.getRadius(height, i);
			for (int k = -j; k <= j; ++k) {
				for (int l = -j; l <= j; ++l) {
					mutable.set(pos, k, i, l);
					if (config.trunkPlacer.canReplaceOrIsLog(world, mutable)) continue;
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

	@Override
	public final boolean generate(FeatureContext<MangroveTreeFeatureConfig> context) {
		StructureWorldAccess structureWorldAccess = context.getWorld();
		Random random = context.getRandom();
		BlockPos blockPos = context.getOrigin();
		MangroveTreeFeatureConfig treeFeatureConfig = context.getConfig();
		List<BlockPos> set = new ArrayList<>();
		BiConsumer<BlockPos, BlockState> biConsumer = (pos, state) -> {
			set.add(pos.toImmutable());
			structureWorldAccess.setBlockState(pos, state, Block.NOTIFY_ALL | Block.FORCE_STATE);
		};
		List<BlockPos> set2 = new ArrayList<>();
		BiConsumer<BlockPos, BlockState> biConsumer2 = (pos, state) -> {
			set2.add(pos.toImmutable());
			structureWorldAccess.setBlockState(pos, state, Block.NOTIFY_ALL | Block.FORCE_STATE);
		};
		List<BlockPos> set3 = new ArrayList<>();
		BiConsumer<BlockPos, BlockState> biConsumer3 = (pos, state) -> {
			set3.add(pos.toImmutable());
			structureWorldAccess.setBlockState(pos, state, Block.NOTIFY_ALL | Block.FORCE_STATE);
		};
		List<BlockPos> set4 = new ArrayList<>();
		BiConsumer<BlockPos, BlockState> biConsumer4 = (pos, state) -> {
			set4.add(pos.toImmutable());
			structureWorldAccess.setBlockState(pos, state, Block.NOTIFY_ALL | Block.FORCE_STATE);
		};
		boolean bl = this.generate(structureWorldAccess, random, blockPos, biConsumer, biConsumer2, biConsumer3, treeFeatureConfig);
		if (!bl || set2.isEmpty() && set3.isEmpty()) return false;
		//Decorators
		new RandomLeavesVineTreeDecorator(0.125f).generate(structureWorldAccess, biConsumer4, random, set2, set3);
		generatePropagules(structureWorldAccess, biConsumer4, random, set3);
		new BeehiveTreeDecorator(0.01f).generate(structureWorldAccess, biConsumer4, random, set2, set3);
		//Final
		return BlockBox.encompassPositions(Iterables.concat(set, set2, set3, set4)).map(blockBox -> {
			VoxelSet voxelSet = placeLogsAndLeaves(structureWorldAccess, blockBox, set2, set4, set);
			updateCorner(structureWorldAccess, 3, voxelSet, blockBox.getMinX(), blockBox.getMinY(), blockBox.getMinZ());
			return true;
		}).orElse(false);
	}

	public static final BlockStateProvider propaguleBlockProvider = new RandomizedIntBlockStateProvider(
			new ModSimpleBlockStateProvider(ModBase.MANGROVE_PROPAGULE.asBlock().getDefaultState().with(PropaguleBlock.HANGING, true)),
			PropaguleBlock.AGE, UniformIntProvider.create(0, 4));

	public static void generatePropagules(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, List<BlockPos> leavesPositions) {
		HashSet<BlockPos> set = new HashSet<BlockPos>();
		for (BlockPos blockPos : CollectionUtil.copyShuffled(leavesPositions.stream(), random)) {
			BlockPos blockPos2 = blockPos.offset(Direction.DOWN);
			if (set.contains(blockPos2)
					|| !(random.nextFloat() < 0.14f)
					|| !Feature.isAir(world, blockPos.offset(Direction.DOWN, 1))
					|| !Feature.isAir(world, blockPos.offset(Direction.DOWN, 2))) continue;
			for (BlockPos blockPos5 : BlockPos.iterate(blockPos2.add(-1, 0, -1), blockPos2.add(1, 0, 1))) {
				set.add(blockPos5.toImmutable());
			}
			replacer.accept(blockPos2, propaguleBlockProvider.getBlockState(random, blockPos2));
		}
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

	private static VoxelSet placeLogsAndLeaves(WorldAccess world, BlockBox box, List<BlockPos> trunkPositions, List<BlockPos> decorationPositions, List<BlockPos> set) {
		ArrayList<HashSet<BlockPos>> list = Lists.newArrayList();
		BitSetVoxelSet voxelSet = new BitSetVoxelSet(box.getBlockCountX(), box.getBlockCountY(), box.getBlockCountZ());
		for (int j = 0; j < 6; ++j) {
			list.add(new HashSet<>());
		}
		BlockPos.Mutable mutable = new BlockPos.Mutable();
		List<BlockPos> union = new ArrayList<>(decorationPositions);
		for(BlockPos pos : set) {
			if (!union.contains(pos)) union.add(pos);
		}
		for (BlockPos blockPos : Lists.newArrayList(union)) {
			if (!box.contains(blockPos)) continue;
			((VoxelSet)voxelSet).set(blockPos.getX() - box.getMinX(), blockPos.getY() - box.getMinY(), blockPos.getZ() - box.getMinZ());
		}
		for (BlockPos blockPos : Lists.newArrayList(trunkPositions)) {
			if (box.contains(blockPos)) {
				((VoxelSet)voxelSet).set(blockPos.getX() - box.getMinX(), blockPos.getY() - box.getMinY(), blockPos.getZ() - box.getMinZ());
			}
			for (Direction direction : Direction.values()) {
				BlockState blockState;
				mutable.set(blockPos, direction);
				if (trunkPositions.contains(mutable) || !(blockState = world.getBlockState(mutable)).contains(Properties.DISTANCE_1_7)) continue;
				list.get(0).add(mutable.toImmutable());
				world.setBlockState(mutable, blockState.with(Properties.DISTANCE_1_7, 1), Block.NOTIFY_ALL | Block.FORCE_STATE);
				if (!box.contains(mutable)) continue;
				((VoxelSet)voxelSet).set(mutable.getX() - box.getMinX(), mutable.getY() - box.getMinY(), mutable.getZ() - box.getMinZ());
			}
		}
		for (int k = 1; k < 6; ++k) {
			Set<BlockPos> set2 = list.get(k - 1);
			Set<BlockPos> set3 = list.get(k);
			for (BlockPos blockPos2 : set2) {
				if (box.contains(blockPos2)) {
					((VoxelSet)voxelSet).set(blockPos2.getX() - box.getMinX(), blockPos2.getY() - box.getMinY(), blockPos2.getZ() - box.getMinZ());
				}
				for (Direction direction2 : Direction.values()) {
					BlockState blockState2;
					mutable.set(blockPos2, direction2);
					if (set2.contains(mutable) || set3.contains(mutable) || !(blockState2 = world.getBlockState(mutable)).contains(Properties.DISTANCE_1_7) || blockState2.get(Properties.DISTANCE_1_7) <= k + 1) continue;
					world.setBlockState(mutable, blockState2.with(Properties.DISTANCE_1_7, k + 1), Block.NOTIFY_ALL | Block.FORCE_STATE);
					if (box.contains(mutable)) {
						((VoxelSet)voxelSet).set(mutable.getX() - box.getMinX(), mutable.getY() - box.getMinY(), mutable.getZ() - box.getMinZ());
					}
					set3.add(mutable.toImmutable());
				}
			}
		}
		return voxelSet;
	}
}