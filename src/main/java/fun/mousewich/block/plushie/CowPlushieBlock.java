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

public class CowPlushieBlock extends PlushieBlock {
	public static final VoxelShape NORTH = VoxelShapes.cuboid(0.25, 0, 0f, 0.75, 0.85, 0.7);
	public static final VoxelShape SOUTH = VoxelShapes.cuboid(0.25, 0, 0.3, 0.75, 0.85, 1);
	public static final VoxelShape EAST = VoxelShapes.cuboid(0, 0, 0.25, 1, 0.85, 0.75);
	public static final VoxelShape WEST = VoxelShapes.cuboid(0.3, 0, 0.25, 0.75, 0.85, 0.75);
	public CowPlushieBlock(Settings settings) { super(settings); }
	@Override
	public Model getModel() { return ModModels.TEMPLATE_PLUSHIE_COW; }
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext ctx) {
		Direction dir = state.get(FACING);
		if (dir == Direction.NORTH) return NORTH;
		else if (dir == Direction.SOUTH) return SOUTH;
		else if (dir == Direction.EAST) return EAST;
		else if (dir == Direction.WEST) return WEST;
		else return VoxelShapes.fullCube();
	}
}
