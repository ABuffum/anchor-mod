package fun.mousewich.block;

import net.minecraft.block.*;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class HorziontalFacingCarpetBlock extends HorizontalFacingBlock {
	protected static final VoxelShape SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D);
	public HorziontalFacingCarpetBlock(BlockConvertible block) { this(block.asBlock()); }
	public HorziontalFacingCarpetBlock(Block block) { this(Settings.copy(block)); }
	public HorziontalFacingCarpetBlock(Settings settings) { super(settings); }

	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) { return SHAPE; }

	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) { builder.add(FACING); }

	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
	}

	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		return !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
	}

	public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
		return !world.isAir(pos.down());
	}
}
