package fun.mousewich.block;

import fun.mousewich.ModBase;
import net.minecraft.block.*;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

import java.util.Random;

public class PitcherCropBlock extends TallPlantBlock implements Fertilizable {
	public static final IntProperty AGE = ModProperties.AGE_4;
	private static final VoxelShape UPPER_OUTLINE_SHAPE = Block.createCuboidShape(3.0, 0.0, 3.0, 13.0, 15.0, 13.0);
	private static final VoxelShape LOWER_OUTLINE_SHAPE = Block.createCuboidShape(3.0, -1.0, 3.0, 13.0, 16.0, 13.0);
	private static final VoxelShape AGE_O_COLLISION_SHAPE = Block.createCuboidShape(5.0, -1.0, 5.0, 11.0, 3.0, 11.0);
	private static final VoxelShape LOWER_COLLISION_SHAPE = Block.createCuboidShape(3.0, -1.0, 3.0, 13.0, 5.0, 13.0);
	public PitcherCropBlock(Settings settings) { super(settings); }

	@Override
	public boolean hasRandomTicks(BlockState state) { return state.get(HALF) == DoubleBlockHalf.LOWER && state.get(AGE) < 4; }

	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) { return this.getDefaultState(); }

	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		if (state.get(AGE) == 0) return AGE_O_COLLISION_SHAPE;
		if (state.get(HALF) == DoubleBlockHalf.LOWER) return LOWER_COLLISION_SHAPE;
		return super.getCollisionShape(state, world, pos, context);
	}

	@Override
	public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
		if (state.get(HALF) == DoubleBlockHalf.LOWER && state.get(AGE) >= 3) {
			BlockState blockState = world.getBlockState(pos.up());
			return blockState.isOf(this) && blockState.get(HALF) == DoubleBlockHalf.UPPER;
		}
		return (world.getBaseLightLevel(pos, 0) >= 8 || world.isSkyVisible(pos)) && super.canPlaceAt(state, world, pos);
	}

	@Override
	protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) { return floor.isOf(Blocks.FARMLAND); }

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) { builder.add(AGE); super.appendProperties(builder); }

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return state.get(HALF) == DoubleBlockHalf.UPPER ? UPPER_OUTLINE_SHAPE : LOWER_OUTLINE_SHAPE;
	}

	@Override
	public boolean canReplace(BlockState state, ItemPlacementContext context) { return false; }

	@Override
	public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) { }

	@Override
	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		float f = TorchflowerBlock.GetAvailableMoisture(this, world, pos);
		if (random.nextInt((int)(25.0f / f) + 1) == 0) this.method_49819(world, state, pos, 1);
	}

	private void method_49819(ServerWorld serverWorld, BlockState blockState, BlockPos blockPos, int i) {
		int j = Math.min(blockState.get(AGE) + i, 4);
		if (j >= 3 && !method_49820(serverWorld, blockPos.up())) return;
		serverWorld.setBlockState(blockPos, blockState.with(AGE, j), Block.NOTIFY_LISTENERS);
		if (j < 3) return;
		if (blockState.get(HALF) == DoubleBlockHalf.LOWER) {
			BlockPos blockPos2 = blockPos.up();
			serverWorld.setBlockState(blockPos2, withWaterloggedState(serverWorld, blockPos, this.getDefaultState().with(AGE, j).with(HALF, DoubleBlockHalf.UPPER)), Block.NOTIFY_ALL);
		}
	}

	private static boolean method_49820(ServerWorld serverWorld, BlockPos blockPos) {
		BlockState blockState = serverWorld.getBlockState(blockPos);
		return blockState.isAir() || blockState.isOf(ModBase.PITCHER_CROP.asBlock());
	}

	@Override
	public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) { return state.get(AGE) < 4; }
	@Override
	public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) { return true; }
	@Override
	public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
		this.method_49819(world, state, pos, MathHelper.nextInt(world.random, 2, 5));
	}
}