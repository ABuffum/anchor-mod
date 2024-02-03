package fun.wich.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class CreeperAnatomyStatueBlock extends HorizontalFacingBlock {
	private static final VoxelShape BASE_SHAPE = VoxelShapes.union(
			VoxelShapes.cuboid(0, 0, 0, 1, 0.125, 1),
			VoxelShapes.cuboid(0.25, 0.125, 0.25, 0.75, 1.875, 0.75)
	);
	public static final VoxelShape NORTH_SOUTH = VoxelShapes.union(BASE_SHAPE, VoxelShapes.cuboid(0.25, 0.125, 0.125, 0.75, 0.625, 0.875));
	public static final VoxelShape EAST_WEST = VoxelShapes.union(BASE_SHAPE, VoxelShapes.cuboid(0.125, 0.125, 0.25, 0.875, 0.625, 0.75));
	public CreeperAnatomyStatueBlock(Settings settings) {
		super(settings);
		this.setDefaultState(this.getDefaultState().with(FACING, Direction.NORTH));
	}
	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) { stateManager.add(FACING); }
	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return this.getDefaultState().with(Properties.HORIZONTAL_FACING, ctx.getPlayerFacing().getOpposite());
	}
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext ctx) {
		Direction dir = state.get(FACING);
		if (dir == Direction.NORTH || dir == Direction.SOUTH) return NORTH_SOUTH;
		else if (dir == Direction.EAST || dir == Direction.WEST) return EAST_WEST;
		else return VoxelShapes.fullCube();
	}
}
