package fun.wich.block.plushie;

import fun.wich.gen.data.model.ModModels;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.data.client.model.Model;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class WardenPlushieBlock extends PlushieBlock {
	public static final VoxelShape NORTH_SOUTH = VoxelShapes.cuboid(0.15, 0f, 0.25, 0.85, 1.15, 0.75);
	public static final VoxelShape EAST_WEST = VoxelShapes.cuboid(0.25, 0, 0.15, 0.75, 1.15, 0.85);
	public WardenPlushieBlock(Settings settings) { super(settings); }
	@Override
	public Model getModel() { return ModModels.TEMPLATE_PLUSHIE_WARDEN; }
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext ctx) {
		Direction dir = state.get(FACING);
		if (dir == Direction.NORTH || dir == Direction.SOUTH) return NORTH_SOUTH;
		else if (dir == Direction.EAST || dir == Direction.WEST) return EAST_WEST;
		else return VoxelShapes.fullCube();
	}
}
