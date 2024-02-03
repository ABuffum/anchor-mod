package fun.wich.block;

import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

import java.util.Random;

public class ModWartBlock extends PlantBlock {
	public static final IntProperty AGE = Properties.AGE_3;
	private static final VoxelShape[] AGE_TO_SHAPE = new VoxelShape[] {
			Block.createCuboidShape(0, 0, 0, 16, 5, 16),
			Block.createCuboidShape(0, 0, 0, 16, 8, 16),
			Block.createCuboidShape(0, 0, 0, 16, 11, 16),
			Block.createCuboidShape(0, 0, 0, 16, 14, 16)
	};
	public ModWartBlock(Settings settings) {
		super(settings);
		this.setDefaultState(this.stateManager.getDefaultState().with(AGE, 0));
	}
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) { return AGE_TO_SHAPE[state.get(AGE)]; }
	@Override
	protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) { return floor.isOf(Blocks.SOUL_SAND); }
	@Override
	public boolean hasRandomTicks(BlockState state) { return state.get(AGE) < 3; }
	@Override
	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		int i = state.get(AGE);
		if (i < 3 && random.nextInt(10) == 0) {
			state = state.with(AGE, i + 1);
			world.setBlockState(pos, state, Block.NOTIFY_LISTENERS);
		}
	}
	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) { builder.add(AGE); }
}
