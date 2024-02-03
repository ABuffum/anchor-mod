package fun.wich.block.piglin;

import fun.wich.ModBase;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.Wearable;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class WallPiglinHeadBlock extends BlockWithEntity implements Wearable, PiglinHeadParent {
	protected static final VoxelShape NORTH = Block.createCuboidShape(3.0, 4.0, 8.0, 13.0, 12.0, 16.0);
	protected static final VoxelShape SOUTH = Block.createCuboidShape(3.0, 4.0, 0.0, 13.0, 12.0, 8.0);
	protected static final VoxelShape EAST = Block.createCuboidShape(0.0, 4.0, 3.0, 8.0, 12.0, 13.0);
	protected static final VoxelShape WEST = Block.createCuboidShape(8.0, 4.0, 3.0, 16.0, 12.0, 13.0);
	private final boolean zombified;
	public boolean isZombified() { return this.zombified; }
	public WallPiglinHeadBlock(Settings settings, boolean zombified) {
		super(settings);
		this.zombified = zombified;
		this.setDefaultState(this.stateManager.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.NORTH));
	}
	@Override
	public String getTranslationKey() { return this.asItem().getTranslationKey(); }
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return switch(state.get(Properties.HORIZONTAL_FACING)) {
			case NORTH -> NORTH;
			case SOUTH -> SOUTH;
			case WEST -> WEST;
			case EAST -> EAST;
			default -> PiglinHeadBlock.SHAPE;
		};
	}
	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		BlockState blockState = this.getDefaultState();
		World blockView = ctx.getWorld();
		BlockPos blockPos = ctx.getBlockPos();
		for (Direction direction : ctx.getPlacementDirections()) {
			if (!direction.getAxis().isHorizontal()) continue;
			Direction direction2 = direction.getOpposite();
			blockState = blockState.with(Properties.HORIZONTAL_FACING, direction2);
			if (blockView.getBlockState(blockPos.offset(direction)).canReplace(ctx)) continue;
			return blockState;
		}
		return null;
	}
	@Override
	public BlockState rotate(BlockState state, BlockRotation rotation) {
		return state.with(Properties.HORIZONTAL_FACING, rotation.rotate(state.get(Properties.HORIZONTAL_FACING)));
	}
	@Override
	public BlockState mirror(BlockState state, BlockMirror mirror) {
		return state.rotate(mirror.getRotation(state.get(Properties.HORIZONTAL_FACING)));
	}
	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) { builder.add(Properties.HORIZONTAL_FACING); }
	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		PiglinHeadEntity entity = new PiglinHeadEntity(pos, state);
		entity.setZombified(this.zombified);
		return entity;
	}
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
		return world.isClient && (ModBase.PIGLIN_HEAD.contains(state.getBlock()) || ModBase.ZOMBIFIED_PIGLIN_HEAD.contains(state.getBlock()))
				? checkType(type, ModBase.PIGLIN_HEAD_BLOCK_ENTITY, PiglinHeadEntity::tick) : null;
	}
	@Override
	public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) { return false; }
}
