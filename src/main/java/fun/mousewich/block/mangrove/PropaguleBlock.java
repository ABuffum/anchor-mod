package fun.mousewich.block.mangrove;

import fun.mousewich.ModBase;
import fun.mousewich.block.ModProperties;
import fun.mousewich.gen.feature.MangroveSaplingGenerator;
import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

import java.util.Random;

public class PropaguleBlock extends PlantBlock implements Fertilizable, Waterloggable {
	public static final IntProperty STAGE = Properties.STAGE;
	private final MangroveSaplingGenerator generator;
	public static final IntProperty AGE = ModProperties.AGE_4;
	private static final VoxelShape[] SHAPES = new VoxelShape[] {
			Block.createCuboidShape(7.0, 13.0, 7.0, 9.0, 16.0, 9.0),
			Block.createCuboidShape(7.0, 10.0, 7.0, 9.0, 16.0, 9.0),
			Block.createCuboidShape(7.0, 7.0, 7.0, 9.0, 16.0, 9.0),
			Block.createCuboidShape(7.0, 3.0, 7.0, 9.0, 16.0, 9.0),
			Block.createCuboidShape(7.0, 0.0, 7.0, 9.0, 16.0, 9.0)
	};
	private static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
	public static final BooleanProperty HANGING = Properties.HANGING;

	public PropaguleBlock(Settings settings) {
		super(settings);
		this.generator = new MangroveSaplingGenerator(0.85f);
		this.setDefaultState(this.stateManager.getDefaultState().with(STAGE, 0).with(AGE, 0).with(WATERLOGGED, false).with(HANGING, false));
	}

	public void generate(ServerWorld world, BlockPos pos, BlockState state, Random random) {
		if (state.get(STAGE) == 0) world.setBlockState(pos, state.cycle(STAGE), Block.NO_REDRAW);
		else this.generator.generate(world, world.getChunkManager().getChunkGenerator(), pos, state, random);
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(STAGE).add(AGE).add(WATERLOGGED).add(HANGING);
	}

	@Override
	protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
		return super.canPlantOnTop(floor, world, pos) || floor.isOf(Blocks.CLAY);
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
		boolean bl = fluidState.getFluid() == Fluids.WATER;
		return super.getPlacementState(ctx).with(WATERLOGGED, bl).with(AGE, 4);
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		Vec3d vec3d = state.getModelOffset(world, pos);
		VoxelShape voxelShape = !state.get(HANGING) ? SHAPES[4] : SHAPES[state.get(AGE)];
		return voxelShape.offset(vec3d.x, vec3d.y, vec3d.z);
	}

	@Override
	public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
		if (PropaguleBlock.isHanging(state)) return world.getBlockState(pos.up()).isOf(ModBase.MANGROVE_LEAVES.asBlock());
		return super.canPlaceAt(state, world, pos);
	}

	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		if (state.get(WATERLOGGED)) world.getFluidTickScheduler().schedule(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
		if (direction == Direction.UP && !state.canPlaceAt(world, pos)) return Blocks.AIR.getDefaultState();
		return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
	}

	@Override
	public FluidState getFluidState(BlockState state) {
		if (state.get(WATERLOGGED)) return Fluids.WATER.getStill(false);
		return super.getFluidState(state);
	}

	@Override
	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		if (!PropaguleBlock.isHanging(state)) {
			if (random.nextInt(7) == 0) this.generate(world, pos, state, random);
			return;
		}
		if (PropaguleBlock.isNotFullyGrown(state)) world.setBlockState(pos, state.cycle(AGE), Block.NOTIFY_LISTENERS);
	}

	@Override
	public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
		return !PropaguleBlock.isHanging(state) || PropaguleBlock.isNotFullyGrown(state);
	}

	@Override
	public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
		return PropaguleBlock.isHanging(state) ? PropaguleBlock.isNotFullyGrown(state) : (double)world.random.nextFloat() < 0.45D;
	}

	@Override
	public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
		if (PropaguleBlock.isHanging(state) && PropaguleBlock.isNotFullyGrown(state)) {
			world.setBlockState(pos, state.cycle(AGE), Block.NOTIFY_LISTENERS);
		}
		else this.generate(world, pos, state, random);
	}
	private static boolean isHanging(BlockState state) { return state.get(HANGING); }
	private static boolean isNotFullyGrown(BlockState state) { return state.get(AGE) < 4; }
	public static BlockState getDefaultHangingState() { return PropaguleBlock.getHangingState(0); }

	public static BlockState getHangingState(int age) {
		return ModBase.MANGROVE_PROPAGULE.asBlock().getDefaultState().with(HANGING, true).with(AGE, age);
	}
}

