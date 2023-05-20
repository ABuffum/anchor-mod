package fun.mousewich.block.mangrove;

import fun.mousewich.block.basic.ModLeavesBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Fertilizable;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Random;

public class MangroveLeavesBlock extends ModLeavesBlock implements Fertilizable {
	public MangroveLeavesBlock(Settings settings) { super(settings); }
	@Override
	public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
		return world.getBlockState(pos.down()).isAir();
	}
	@Override
	public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) { return true; }
	@Override
	public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
		world.setBlockState(pos.down(), PropaguleBlock.getDefaultHangingState(), Block.NOTIFY_LISTENERS);
	}
}
