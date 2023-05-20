package fun.mousewich.block;

import fun.mousewich.ModBase;
import net.minecraft.block.*;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

import java.util.Random;

public class DeathCapMushroomBlock extends PlantBlock implements Fertilizable {
	public static final int MAX_COUNT = 4;
	public static final IntProperty COUNT = ModProperties.COUNT_4;
	protected static final VoxelShape ONE_SHAPE = Block.createCuboidShape(5, 0, 5, 11, 9, 11);
	protected static final VoxelShape TWO_SHAPE = Block.createCuboidShape(1, 0, 1, 15, 9, 15);
	protected static final VoxelShape THREE_SHAPE = Block.createCuboidShape(1, 0, 1, 15, 9, 15);
	protected static final VoxelShape FOUR_SHAPE = Block.createCuboidShape(0, 0, 0, 16, 9, 16);

	public DeathCapMushroomBlock(Settings settings) {
		super(settings);
		this.setDefaultState(this.stateManager.getDefaultState().with(COUNT, 1));
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		BlockState blockState = ctx.getWorld().getBlockState(ctx.getBlockPos());
		if (blockState.isOf(this)) {
			return blockState.with(COUNT, Math.min(MAX_COUNT, blockState.get(COUNT) + 1));
		}
		return super.getPlacementState(ctx);
	}

	@Override
	protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
		return floor.isOpaqueFullCube(world, pos);
	}

	@Override
	public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
		BlockPos blockPos = pos.down();
		BlockState blockState = world.getBlockState(blockPos);
		if (blockState.isIn(BlockTags.MUSHROOM_GROW_BLOCK)) return true;
		return world.getBaseLightLevel(pos, 0) < 13 && this.canPlantOnTop(blockState, world, blockPos);
	}

	@Override
	public boolean canReplace(BlockState state, ItemPlacementContext context) {
		if (!context.shouldCancelInteraction() && context.getStack().isOf(this.asItem()) && state.get(COUNT) < MAX_COUNT) return true;
		return super.canReplace(state, context);
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		Integer count = state.get(COUNT);
		if (count == 1) return ONE_SHAPE;
		else if (count == 2) return TWO_SHAPE;
		else if (count == 3) return THREE_SHAPE;
		else return FOUR_SHAPE;
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) { builder.add(COUNT); }

	@Override
	public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) { return true; }

	@Override
	public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) { return state.get(COUNT) < MAX_COUNT; }

	@Override
	public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
		int count = state.get(COUNT);
		if (count < MAX_COUNT) world.setBlockState(pos, state.with(COUNT, count + 1));
		else dropStack(world, pos, new ItemStack(ModBase.DEATH_CAP_MUSHROOM.asItem()));
	}

	@Override
	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		int count = state.get(COUNT);
		if (random.nextInt(25) == 0) {
			if (count < MAX_COUNT) {
				world.setBlockState(pos, state.with(COUNT, count + 1));
				return;
			}
			int i = 5;
			for (BlockPos blockPos : BlockPos.iterate(pos.add(-4, -1, -4), pos.add(4, 1, 4))) {
				if (!world.getBlockState(blockPos).isOf(this) || --i > 0) continue;
				return;
			}
			BlockPos blockPos2 = pos.add(random.nextInt(3) - 1, random.nextInt(2) - random.nextInt(2), random.nextInt(3) - 1);
			for (int k = 0; k < 4; ++k) {
				if (world.isAir(blockPos2) && state.canPlaceAt(world, blockPos2)) pos = blockPos2;
				blockPos2 = pos.add(random.nextInt(3) - 1, random.nextInt(2) - random.nextInt(2), random.nextInt(3) - 1);
			}
			if (world.isAir(blockPos2) && state.canPlaceAt(world, blockPos2)) world.setBlockState(blockPos2, state, Block.NOTIFY_LISTENERS);
		}
	}
}
