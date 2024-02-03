package fun.wich.gen.feature;

import fun.wich.ModBase;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;

import java.util.Random;

public class CherrySaplingGenerator {

	protected ConfiguredFeature<?, ?> getTreeFeature(Random random, boolean bees) {
		return (bees ? ModBase.CHERRY_BEES_005_CONFIGURED : ModBase.CHERRY_CONFIGURED).getFeature();
	}

	public boolean generate(ServerWorld world, ChunkGenerator chunkGenerator, BlockPos pos, BlockState state, Random random) {
		ConfiguredFeature<?, ?> configuredFeature = this.getTreeFeature(random, this.areFlowersNearby(world, pos));
		if (configuredFeature == null) {
			return false;
		}
		world.setBlockState(pos, Blocks.AIR.getDefaultState(), Block.NO_REDRAW);
		if (configuredFeature.generate(world, chunkGenerator, random, pos)) {
			return true;
		}
		world.setBlockState(pos, state, Block.NO_REDRAW);
		return false;
	}

	private boolean areFlowersNearby(WorldAccess world, BlockPos pos) {
		for (BlockPos blockPos : BlockPos.Mutable.iterate(pos.down().north(2).west(2), pos.up().south(2).east(2))) {
			if (!world.getBlockState(blockPos).isIn(BlockTags.FLOWERS)) continue;
			return true;
		}
		return false;
	}
}