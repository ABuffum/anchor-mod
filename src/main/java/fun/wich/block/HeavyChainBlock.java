package fun.wich.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChainBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class HeavyChainBlock extends ChainBlock {
	public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
	protected static final VoxelShape X_SHAPE = VoxelShapes.union(
			VoxelShapes.cuboid(0, 0.25, 0.4375, 1, 0.75, 0.5625),
			VoxelShapes.cuboid(0, 0.4375, 0.25, 1, 0.5625, 0.75));
	protected static final VoxelShape Y_SHAPE = VoxelShapes.union(
			VoxelShapes.cuboid(0.25, 0, 0.4375, 0.75, 1, 0.5625),
			VoxelShapes.cuboid(0.4375, 0, 0.25, 0.5625, 1, 0.75));
	protected static final VoxelShape Z_SHAPE = VoxelShapes.union(
			VoxelShapes.cuboid(0.4375, 0.25, 0, 0.5625, 0.75, 1),
			VoxelShapes.cuboid(0.25, 0.4375, 0, 0.75, 0.5625, 1));

	public HeavyChainBlock(BlockConvertible block) { this(block.asBlock()); }
	public HeavyChainBlock(Block block) { this(Settings.copy(block)); }
	public HeavyChainBlock(Settings settings) {
		super(settings);
		this.setDefaultState(this.stateManager.getDefaultState().with(WATERLOGGED, false).with(AXIS, Direction.Axis.Y));
	}
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		Direction.Axis axis = state.get(AXIS);
		return axis == Direction.Axis.X ? X_SHAPE : axis == Direction.Axis.Y ? Y_SHAPE : Z_SHAPE;
	}
}
