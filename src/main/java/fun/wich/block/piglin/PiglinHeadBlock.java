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
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class PiglinHeadBlock extends BlockWithEntity implements Wearable, PiglinHeadParent {
	private static final int MAX_ROTATIONS = 16;
	protected static final VoxelShape SHAPE = Block.createCuboidShape(3.0, 0.0, 3.0, 13.0, 8.0, 13.0);
	private final boolean zombified;
	public boolean isZombified() { return this.zombified; }
	public PiglinHeadBlock(Settings settings, boolean zombified) {
		super(settings);
		this.zombified = zombified;
		this.setDefaultState(this.stateManager.getDefaultState().with(Properties.ROTATION, 0));
	}
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) { return SHAPE; }
	@Override
	public VoxelShape getCullingShape(BlockState state, BlockView world, BlockPos pos) { return VoxelShapes.empty(); }
	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		int yaw = MathHelper.floor((ctx.getPlayerYaw() * 16 / 360.0) + 0.5) & 0xF;
		return this.getDefaultState().with(Properties.ROTATION, yaw);
	}
	@Override
	public BlockState rotate(BlockState state, BlockRotation rotation) {
		return state.with(Properties.ROTATION, rotation.rotate(state.get(Properties.ROTATION), MAX_ROTATIONS));
	}
	@Override
	public BlockState mirror(BlockState state, BlockMirror mirror) {
		return state.with(Properties.ROTATION, mirror.mirror(state.get(Properties.ROTATION), MAX_ROTATIONS));
	}
	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) { builder.add(Properties.ROTATION); }
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
