package fun.wich.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class FacetingTableBlock extends Block {
	public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
	protected static final VoxelShape SHAPE = Block.createCuboidShape(0, 0, 0, 16, 9, 16);
	protected static final VoxelShape NORTH = VoxelShapes.union(SHAPE, Block.createCuboidShape(13, 9, 0, 16, 15, 16));
	protected static final VoxelShape EAST = VoxelShapes.union(SHAPE, Block.createCuboidShape(0, 9, 13, 16, 15, 16));
	protected static final VoxelShape SOUTH = VoxelShapes.union(SHAPE, Block.createCuboidShape(0, 9, 0, 3, 15, 16));
	protected static final VoxelShape WEST = VoxelShapes.union(SHAPE, Block.createCuboidShape(0, 9, 0, 16, 15, 3));

	public FacetingTableBlock(Settings settings) {
		super(settings);
		this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH));
	}
	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
	}
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext ctx) {
		return switch (state.get(FACING)) {
			case NORTH -> NORTH;
			case EAST -> EAST;
			case SOUTH -> SOUTH;
			case WEST -> WEST;
			default -> VoxelShapes.fullCube();
		};
	}
	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) { builder.add(FACING); }
	@Override
	public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) { return false; }
}
