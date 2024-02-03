package fun.wich.block.sculk;

import fun.wich.ModBase;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SculkSensorBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class CalibratedSculkSensorBlock extends SculkSensorBlock {
	public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
	public CalibratedSculkSensorBlock(Settings settings, int range) {
		super(settings, range);
		this.setDefaultState(this.stateManager.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.NORTH));
	}
	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) { return new CalibratedSculkSensorBlockEntity(pos, state); }
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world2, BlockState state, BlockEntityType<T> type) {
		if (!world2.isClient) {
			if (type != ModBase.CALIBRATED_SCULK_SENSOR_ENTITY) return null;
			return (world, blockPos, blockState, arg) -> ((CalibratedSculkSensorBlockEntity)arg).getEventListener();
		}
		return null;
	}
	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		PlayerEntity player = ctx.getPlayer();
		Direction horizontalPlayerFacing = player == null ? Direction.NORTH : player.getHorizontalFacing();
		return super.getPlacementState(ctx).with(FACING, horizontalPlayerFacing);
	}
	@Override
	public int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
		if (direction != state.get(FACING)) return super.getWeakRedstonePower(state, world, pos, direction);
		return 0;
	}
	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		super.appendProperties(builder);
		builder.add(FACING);
	}
	@Override
	public BlockState rotate(BlockState state, BlockRotation rotation) { return state.with(FACING, rotation.rotate(state.get(FACING))); }
	@Override
	public BlockState mirror(BlockState state, BlockMirror mirror) { return state.rotate(mirror.getRotation(state.get(FACING))); }
}
