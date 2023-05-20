package fun.mousewich.block.plushie;

import fun.mousewich.gen.data.model.ModModels;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.data.client.model.Model;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class BatPlushieBlock extends PlushieBlock {
	public static final VoxelShape VOXEL_SHAPE = VoxelShapes.cuboid(0.375, 0, 0.375, 0.625, 0.75, 0.625);
	public BatPlushieBlock(Settings settings) {
		super(settings);
		setDefaultState(this.stateManager.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.NORTH));
	}
	@Override
	public Model getModel() { return ModModels.TEMPLATE_PLUSHIE_BAT; }
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext ctx) { return VOXEL_SHAPE; }
}
