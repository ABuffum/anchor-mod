package fun.wich.block.sign;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import fun.wich.gen.data.tag.ModBlockTags;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

import java.util.Map;

public class WallHangingSignBlock extends AbstractSignBlock {
	public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
	public static final VoxelShape NORTH_SOUTH_COLLISION_SHAPE = Block.createCuboidShape(0.0, 14.0, 6.0, 16.0, 16.0, 10.0);
	public static final VoxelShape EAST_WEST_COLLISION_SHAPE = Block.createCuboidShape(6.0, 14.0, 0.0, 10.0, 16.0, 16.0);
	public static final VoxelShape NORTH_SOUTH_SHAPE = VoxelShapes.union(NORTH_SOUTH_COLLISION_SHAPE, Block.createCuboidShape(1.0, 0.0, 7.0, 15.0, 10.0, 9.0));
	public static final VoxelShape EAST_WEST_SHAPE = VoxelShapes.union(EAST_WEST_COLLISION_SHAPE, Block.createCuboidShape(7.0, 0.0, 1.0, 9.0, 10.0, 15.0));
	private static final Map<Direction, VoxelShape> OUTLINE_SHAPES = Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, NORTH_SOUTH_SHAPE, Direction.SOUTH, NORTH_SOUTH_SHAPE, Direction.EAST, EAST_WEST_SHAPE, Direction.WEST, EAST_WEST_SHAPE));

	public WallHangingSignBlock(Settings settings, SignType signType) {
		super(settings, signType);
		this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(WATERLOGGED, false));
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity instanceof SignBlockEntity signBlockEntity) {
			ItemStack itemStack = player.getStackInHand(hand);
			if (!HangingSignBlock.shouldRunCommand(signBlockEntity, player) && itemStack.getItem() instanceof BlockItem) {
				return ActionResult.PASS;
			}
		}
		return super.onUse(state, world, pos, player, hand, hit);
	}
	@Override
	public String getTranslationKey() { return this.asItem().getTranslationKey(); }
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return OUTLINE_SHAPES.get(state.get(FACING));
	}
	@Override
	public VoxelShape getSidesShape(BlockState state, BlockView world, BlockPos pos) {
		return this.getOutlineShape(state, world, pos, ShapeContext.absent());
	}
	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		Direction facing = state.get(FACING);
		if (facing == Direction.EAST || facing == Direction.WEST) return EAST_WEST_COLLISION_SHAPE;
		return NORTH_SOUTH_COLLISION_SHAPE;
	}
	public boolean canAttachAt(BlockState state, WorldView world, BlockPos pos) {
		Direction direction = state.get(FACING).rotateYClockwise();
		Direction direction2 = state.get(FACING).rotateYCounterclockwise();
		return this.canAttachTo(world, state, pos.offset(direction), direction2) || this.canAttachTo(world, state, pos.offset(direction2), direction);
	}
	public boolean canAttachTo(WorldView world, BlockState state, BlockPos toPos, Direction direction) {
		BlockState blockState = world.getBlockState(toPos);
		if (blockState.isIn(ModBlockTags.WALL_HANGING_SIGNS)) return blockState.get(FACING).getAxis().test(state.get(FACING));
		return blockState.isSideSolid(world, toPos, direction, SideShapeType.FULL);
	}
	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		BlockState blockState = this.getDefaultState();
		FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
		World worldView = ctx.getWorld();
		BlockPos blockPos = ctx.getBlockPos();
		for (Direction direction : ctx.getPlacementDirections()) {
			if (!direction.getAxis().isHorizontal() || direction.getAxis().test(ctx.getSide()) || !(blockState = blockState.with(FACING, direction.getOpposite())).canPlaceAt(worldView, blockPos) || !this.canAttachAt(blockState, worldView, blockPos)) continue;
			return blockState.with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
		}
		return null;
	}
	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		if (direction.getAxis() == state.get(FACING).rotateYClockwise().getAxis() && !state.canPlaceAt(world, pos)) return Blocks.AIR.getDefaultState();
		return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
	}
	@Override
	public BlockState rotate(BlockState state, BlockRotation rotation) { return state.with(FACING, rotation.rotate(state.get(FACING))); }
	@Override
	public BlockState mirror(BlockState state, BlockMirror mirror) { return state.rotate(mirror.getRotation(state.get(FACING))); }
	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) { builder.add(FACING, WATERLOGGED); }
	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) { return new SignBlockEntity(pos, state); }
	@Override
	public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) { return false; }
}
