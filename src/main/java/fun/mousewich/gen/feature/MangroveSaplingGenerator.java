package fun.mousewich.gen.feature;

import fun.mousewich.ModBase;
import fun.mousewich.gen.feature.config.MangroveTreeFeatureConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.Random;

public class MangroveSaplingGenerator {
	private final float tallChance;

	public MangroveSaplingGenerator(float tallChance) { this.tallChance = tallChance; }

	@Nullable
	protected ConfiguredFeature<MangroveTreeFeatureConfig, ?> getTreeFeature(Random random, boolean bees) {
		if (random.nextFloat() < this.tallChance) return ModBase.TALL_MANGROVE_CONFIGURED.getFeature();
		return ModBase.MANGROVE_CONFIGURED.getFeature();
	}

	public boolean generate(ServerWorld world, ChunkGenerator chunkGenerator, BlockPos pos, BlockState state, Random random) {
		ConfiguredFeature<MangroveTreeFeatureConfig, ?> configuredFeature = this.getTreeFeature(random, this.areFlowersNearby(world, pos));
		if (configuredFeature == null) return false;
		else {
			world.setBlockState(pos, Blocks.AIR.getDefaultState(), Block.NO_REDRAW);
			if (configuredFeature.generate(world, chunkGenerator, random, pos)) return true;
			else {
				world.setBlockState(pos, state, Block.NO_REDRAW);
				return false;
			}
		}
	}

	private boolean areFlowersNearby(WorldAccess world, BlockPos pos) {
		Iterator<BlockPos> var3 = BlockPos.Mutable.iterate(pos.down().north(2).west(2), pos.up().south(2).east(2)).iterator();
		BlockPos blockPos;
		do {
			if (!var3.hasNext()) {
				return false;
			}
			blockPos = var3.next();
		}
		while(!world.getBlockState(blockPos).isIn(BlockTags.FLOWERS));
		return true;
	}
}