package fun.mousewich.block.plushie;

import fun.mousewich.gen.data.model.ModModels;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.data.client.model.Model;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class BearPlushieBlock extends PlushieBlock {
	public static final VoxelShape NORTH_SOUTH = VoxelShapes.cuboid(0.25, 0f, 0, 0.75, 0.625, 1);
	public static final VoxelShape EAST_WEST = VoxelShapes.cuboid(0f, 0, 0.25, 1, 0.625, 0.75);
	public BearPlushieBlock(Settings settings) { super(settings); }
	@Override
	public Model getModel() { return ModModels.TEMPLATE_PLUSHIE_BEAR; }
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext ctx) {
		Direction dir = state.get(FACING);
		if (dir == Direction.NORTH || dir == Direction.SOUTH) return NORTH_SOUTH;
		else if (dir == Direction.EAST || dir == Direction.WEST) return EAST_WEST;
		else return VoxelShapes.fullCube();
	}
}
