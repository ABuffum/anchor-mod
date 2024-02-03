package fun.wich.block;

import fun.wich.block.basic.ModSlabBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Fertilizable;
import net.minecraft.block.enums.SlabType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.Random;

public class MossSlabBlock extends ModSlabBlock implements Fertilizable {
	public MossSlabBlock(Block block) { super(block); }

	@Override
	public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
		return world.getBlockState(pos.up()).isAir() && state.get(TYPE) == SlabType.DOUBLE;
	}

	@Override
	public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) { return state.get(TYPE) == SlabType.DOUBLE; }

	@Override
	public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
		Feature.VEGETATION_PATCH.generate(new FeatureContext<>(world, world.getChunkManager().getChunkGenerator(), random, pos.up(), ConfiguredFeatures.MOSS_PATCH_BONEMEAL.getConfig()));
	}
}
