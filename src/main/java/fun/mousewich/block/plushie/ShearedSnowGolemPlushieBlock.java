package fun.mousewich.block.plushie;

import fun.mousewich.gen.data.model.ModModels;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.data.client.model.Model;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class ShearedSnowGolemPlushieBlock extends PlushieBlock {
	public static final VoxelShape VOXEL_SHAPE = VoxelShapes.cuboid(0.25, 0, 0.25, 0.75, 1.2, 0.75);
	public ShearedSnowGolemPlushieBlock(Settings settings) { super(settings); }
	@Override
	public Model getModel() { return ModModels.TEMPLATE_PLUSHIE_SHEARED_SNOW_GOLEM; }
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext ctx) { return VOXEL_SHAPE; }
}
