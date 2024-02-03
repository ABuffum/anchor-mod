package fun.wich.block;

import fun.wich.ModBase;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Random;

public class GrapeLeavesBlock extends BerryLeavesBlock {
	public GrapeLeavesBlock(Block block) { super(ModBase.GRAPES, block); }
	public GrapeLeavesBlock(Settings settings) { super(ModBase.GRAPES, settings); }
	@Override
	public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
		return !state.get(Properties.BERRIES) || world.getBlockState(pos.down()).isAir();
	}
	@Override
	public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
		return !state.get(Properties.BERRIES) || world.getBlockState(pos.down()).isAir();
	}
	@Override
	public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
		if (state.get(Properties.BERRIES)) world.setBlockState(pos.down(), ModBase.GRAPE_VINES.getDefaultState(), NOTIFY_LISTENERS);
		else world.setBlockState(pos, state.with(Properties.BERRIES, true), NOTIFY_LISTENERS);
	}
}
