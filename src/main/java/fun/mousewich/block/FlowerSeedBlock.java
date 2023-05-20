package fun.mousewich.block;

import net.minecraft.block.*;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Random;

public class FlowerSeedBlock extends PlantBlock implements Fertilizable {
	private final Block flower;
	public Block getFlower() { return flower; }

	private static final VoxelShape VOXEL_SHAPE = Block.createCuboidShape(0, 0, 0, 16, 2, 16);

	public FlowerSeedBlock(Block flower, Settings settings) {
		super(settings);
		this.flower = flower;
	}

	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) { return VOXEL_SHAPE; }

	@Override
	public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
		if (flower instanceof TallPlantBlock) return world.getBlockState(pos.up()).isAir();
		return true;
	}

	@Override
	public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) { return true; }

	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		if (world.getBaseLightLevel(pos, 0) >= 9) {
			if (random.nextInt(5) == 0) grow(world, random, pos, state);
		}
	}

	@Override
	public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
		world.setBlockState(pos, flower.getDefaultState(), Block.NOTIFY_ALL | Block.REDRAW_ON_MAIN_THREAD);
		if (flower instanceof TallPlantBlock) world.setBlockState(pos.up(), flower.getDefaultState().with(TallPlantBlock.HALF, DoubleBlockHalf.UPPER), Block.NOTIFY_ALL);
	}
}
