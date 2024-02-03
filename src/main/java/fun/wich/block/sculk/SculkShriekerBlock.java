package fun.wich.block.sculk;

import fun.wich.ModBase;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.event.listener.GameEventListener;

import java.util.Random;

public class SculkShriekerBlock extends BlockWithEntity implements Waterloggable {
	public static final BooleanProperty SHRIEKING = BooleanProperty.of("shrieking");
	public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
	public static final BooleanProperty CAN_SUMMON = BooleanProperty.of("can_summon");
	protected static final VoxelShape SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 8.0, 16.0);
	public static final double TOP = SHAPE.getMax(Direction.Axis.Y);

	public SculkShriekerBlock(Settings settings) {
		super(settings);
		this.setDefaultState(this.stateManager.getDefaultState().with(SHRIEKING, false).with(WATERLOGGED, false).with(CAN_SUMMON, false));
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(SHRIEKING);
		builder.add(WATERLOGGED);
		builder.add(CAN_SUMMON);
	}

	@Override
	public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
		if (world instanceof ServerWorld serverWorld) {
			ServerPlayerEntity serverPlayerEntity = SculkShriekerBlockEntity.findResponsiblePlayerFromEntity(entity);
			if (serverPlayerEntity != null) {
				serverWorld.getBlockEntity(pos, ModBase.SCULK_SHRIEKER_ENTITY).ifPresent(blockEntity -> blockEntity.shriek(serverWorld, serverPlayerEntity));
			}
		}
		super.onSteppedOn(world, pos, state, entity);
	}

	@Override
	public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
		if (world instanceof ServerWorld serverWorld) {
			if (state.get(SHRIEKING) && !state.isOf(newState.getBlock())) {
				serverWorld.getBlockEntity(pos, ModBase.SCULK_SHRIEKER_ENTITY).ifPresent(blockEntity -> blockEntity.warn(serverWorld));
			}
		}
		super.onStateReplaced(state, world, pos, newState, moved);
	}

	@Override
	public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		if (state.get(SHRIEKING)) {
			world.setBlockState(pos, state.with(SHRIEKING, false), Block.NOTIFY_ALL);
			world.getBlockEntity(pos, ModBase.SCULK_SHRIEKER_ENTITY).ifPresent(blockEntity -> blockEntity.warn(world));
		}
	}

	@Override
	public BlockRenderType getRenderType(BlockState state) { return BlockRenderType.MODEL; }

	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) { return SHAPE; }

	@Override
	public VoxelShape getCullingShape(BlockState state, BlockView world, BlockPos pos) { return SHAPE; }

	@Override
	public boolean hasSidedTransparency(BlockState state) { return true; }

	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) { return new SculkShriekerBlockEntity(pos, state); }

	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		if (state.get(WATERLOGGED)) world.getFluidTickScheduler().schedule(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
		return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return this.getDefaultState().with(WATERLOGGED, ctx.getWorld().getFluidState(ctx.getBlockPos()).getFluid() == Fluids.WATER);
	}

	@Override
	public FluidState getFluidState(BlockState state) {
		if (state.get(WATERLOGGED)) return Fluids.WATER.getStill(false);
		return super.getFluidState(state);
	}

	@Override
	public void onStacksDropped(BlockState state, ServerWorld world, BlockPos pos, ItemStack stack) {
		super.onStacksDropped(state, world, pos, stack);
		this.dropExperience(world, pos, 5);
	}

	public <T extends BlockEntity> GameEventListener getGameEventListener(World world, T blockEntity) {
		if (blockEntity instanceof SculkShriekerBlockEntity sculk) return sculk.getVibrationListener();
		return null;
	}

	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world2, BlockState state2, BlockEntityType<T> type) {
		if (!world2.isClient) {
			return BlockWithEntity.checkType(type, ModBase.SCULK_SHRIEKER_ENTITY, (world, pos, state, blockEntity) -> blockEntity.getVibrationListener().tick(world));
		}
		return null;
	}
}
