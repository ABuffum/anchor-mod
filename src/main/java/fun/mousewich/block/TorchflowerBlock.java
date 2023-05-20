package fun.mousewich.block;

import fun.mousewich.ModBase;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemConvertible;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Random;

public class TorchflowerBlock extends CropBlock {
	public static final IntProperty AGE_2 = Properties.AGE_2;
	private static final VoxelShape[] SHAPES = new VoxelShape[]{
			Block.createCuboidShape(4, 0, 4, 12, 10, 12),
			Block.createCuboidShape(4, 0, 4, 12, 10, 12),
			Block.createCuboidShape(4, 0, 4, 12, 10, 12)
	};
	public TorchflowerBlock(Settings settings) { super(settings); }
	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) { builder.add(AGE_2); }
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return SHAPES[state.get(this.getAgeProperty())];
	}
	@Override
	public IntProperty getAgeProperty() { return AGE_2; }
	@Override
	public int getMaxAge() { return 2; }
	@Override
	protected ItemConvertible getSeedsItem() { return ModBase.TORCHFLOWER_CROP; }
	@Override
	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		if (random.nextInt(3) != 0) super.randomTick(state, world, pos, random);
	}
	@Override
	protected int getGrowthAmount(World world) { return super.getGrowthAmount(world) / 3; }

	public static float GetAvailableMoisture(Block block, BlockView world, BlockPos pos) {
		return CropBlock.getAvailableMoisture(block, world, pos);
	}
}