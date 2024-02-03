package fun.wich.gen.placer;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import fun.wich.ModBase;
import fun.wich.gen.data.tag.ModBlockTags;
import fun.wich.gen.feature.config.MangroveTreeFeatureConfig;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeature;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

public class MangroveRootPlacer {
	public final IntProvider trunkOffsetY;

	public static final Codec<MangroveRootPlacer> CODEC = RecordCodecBuilder.create(instance -> instance.group(
					IntProvider.VALUE_CODEC.fieldOf("trunk_offset_y").forGetter(rootPlacer -> rootPlacer.trunkOffsetY))
			.apply(instance, MangroveRootPlacer::new));
	public MangroveRootPlacer(IntProvider trunkOffsetY) { this.trunkOffsetY = trunkOffsetY; }
	public boolean generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, BlockPos pos, BlockPos trunkPos, MangroveTreeFeatureConfig config) {
		ArrayList<BlockPos> list = Lists.newArrayList();
		BlockPos.Mutable mutable = pos.mutableCopy();
		while (mutable.getY() < trunkPos.getY()) {
			if (this.cannotGrowThrough(world, mutable)) return false;
			mutable.move(Direction.UP);
		}
		list.add(trunkPos.down());
		for (Direction direction : Direction.Type.HORIZONTAL) {
			ArrayList<BlockPos> list2;
			BlockPos blockPos = trunkPos.offset(direction);
			if (!this.canGrow(world, random, blockPos, direction, trunkPos, list2 = Lists.newArrayList(), 0)) {
				return false;
			}
			list.addAll(list2);
			list.add(trunkPos.offset(direction));
		}
		for (BlockPos blockPos2 : list) this.placeRoots(world, replacer, random, blockPos2, config);
		return true;
	}
	private boolean canGrow(TestableWorld world, Random random, BlockPos pos, Direction direction, BlockPos origin, List<BlockPos> offshootPositions, int rootLength) {
		if (rootLength == 15 || offshootPositions.size() > 15) return false;
		List<BlockPos> list;// = this.getOffshootPositions(pos, direction, random, origin);

		BlockPos down = pos.down();
		BlockPos blockPos2 = pos.offset(direction);
		int i = pos.getManhattanDistance(origin);
		if (i > 5 && i <= 8) list = random.nextFloat() < 0.2f ? List.of(down, blockPos2.down()) : List.of(down);
		else if (i > 8) list = List.of(down);
		else if (random.nextFloat() < 0.2f) list = List.of(down);
		else list = random.nextBoolean() ? List.of(blockPos2) : List.of(down);

		for (BlockPos blockPos : list) {
			if (this.cannotGrowThrough(world, blockPos)) continue;
			offshootPositions.add(blockPos);
			if (this.canGrow(world, random, blockPos, direction, origin, offshootPositions, rootLength + 1)) continue;
			return false;
		}
		return true;
	}
	public boolean cannotGrowThrough(TestableWorld world, BlockPos pos) {
		return !TreeFeature.canReplace(world, pos) && !world.testBlockState(pos, state -> state.isIn(ModBlockTags.MANGROVE_ROOTS_CAN_GROW_THROUGH));
	}
	public void placeRoots(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, BlockPos pos, MangroveTreeFeatureConfig config) {
		if (world.testBlockState(pos, state -> state.isOf(ModBase.MUD.asBlock()) || state.isOf(ModBase.MUDDY_MANGROVE_ROOTS.asBlock()))) {
			replacer.accept(pos, this.applyWaterlogging(world, pos, ModBase.MUDDY_MANGROVE_ROOTS.asBlock().getDefaultState()));
		}
		else {
			if (this.cannotGrowThrough(world, pos)) return;
			replacer.accept(pos, this.applyWaterlogging(world, pos, ModBase.MANGROVE_ROOTS.asBlock().getDefaultState()));
			BlockPos blockPos = pos.up();
			if (random.nextFloat() < 0.5f && world.testBlockState(blockPos, AbstractBlock.AbstractBlockState::isAir)) {
				replacer.accept(blockPos, this.applyWaterlogging(world, blockPos, Blocks.MOSS_CARPET.getDefaultState()));
			}
		}
	}
	public BlockState applyWaterlogging(TestableWorld world, BlockPos pos, BlockState state) {
		if (state.contains(Properties.WATERLOGGED)) {
			boolean bl = world.testFluidState(pos, fluidState -> fluidState.isIn(FluidTags.WATER));
			return state.with(Properties.WATERLOGGED, bl);
		}
		return state;
	}
}