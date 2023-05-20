package fun.mousewich.block.decoration;

import net.minecraft.block.*;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class DecorativeSaplingBlock extends Block {
	public DecorativeSaplingBlock() { this(Settings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GRASS)); }
	public DecorativeSaplingBlock(Settings settings) { super(settings); }

	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		if (!state.canPlaceAt(world, pos)) return Blocks.AIR.getDefaultState();
		return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
	}

	@Override
	public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
		BlockState floor = world.getBlockState(pos.down());
		return floor.isIn(BlockTags.DIRT) || floor.isOf(Blocks.FARMLAND);
	}

	@Override
	public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) {
		return state.getFluidState().isEmpty();
	}

	@Override
	public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
		if (type == NavigationType.AIR && !this.collidable) return true;
		return super.canPathfindThrough(state, world, pos, type);
	}
	protected static final VoxelShape SHAPE = Block.createCuboidShape(2.0, 0.0, 2.0, 14.0, 12.0, 14.0);

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) { return SHAPE; }
}
