package haven.blocks.plushie;

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

public class AxolotlPlushie extends HorizontalFacingBlock {
	public AxolotlPlushie(Settings settings) {
		super(settings);
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
		stateManager.add(Properties.HORIZONTAL_FACING);
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext ctx) {
		Direction dir = state.get(FACING);
		switch(dir) {
			case NORTH:
				return VoxelShapes.cuboid(0.15f, 0f, 0f, 0.85f, 0.39f, 1f);
			case SOUTH:
				return VoxelShapes.cuboid(0.15f, 0f, 0f, 0.85f, 0.391f, 1f);
			case EAST:
				return VoxelShapes.cuboid(0f, 0f, 0.15f, 1f, 0.39f, 0.85f);
			case WEST:
				return VoxelShapes.cuboid(0f, 0f, 0.15f, 1f, 0.391f, 0.85f);
			default:
				return VoxelShapes.fullCube();
		}
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return this.getDefaultState().with(Properties.HORIZONTAL_FACING, ctx.getPlayerFacing().getOpposite());
	}
}
