package fun.wich.block;

import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class MyceliumRootsBlock extends PlantBlock {
	protected static final VoxelShape SHAPE = Block.createCuboidShape(2.0, 0.0, 2.0, 14.0, 13.0, 14.0);
	public MyceliumRootsBlock(BlockConvertible block) { this(block.asBlock()); }
	public MyceliumRootsBlock(Block block) { this(Settings.copy(block)); }
	public MyceliumRootsBlock(Settings settings) { super(settings); }
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) { return SHAPE; }
	@Override
	protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
		return floor.isOf(Blocks.MYCELIUM) || super.canPlantOnTop(floor, world, pos);
	}
	@Override
	public OffsetType getOffsetType() { return OffsetType.XZ; }
}