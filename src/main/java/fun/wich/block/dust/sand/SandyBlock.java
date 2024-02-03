package fun.wich.block.dust.sand;

import fun.wich.block.BlockConvertible;
import fun.wich.block.ModProperties;
import fun.wich.block.dust.Brushable;
import fun.wich.block.dust.BrushableBlockEntity;
import fun.wich.sound.ModSoundEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import java.util.Random;

public class SandyBlock extends BlockWithEntity implements Brushable {
	protected final Block block;
	public SandyBlock(BlockConvertible block) { this(block.asBlock()); }
	public SandyBlock(Block block) { this(block, Settings.copy(block)); }
	public SandyBlock(Block block, Settings settings) {
		super(settings);
		this.block = block;
		this.setDefaultState(this.stateManager.getDefaultState().with(ModProperties.DUSTED, 0));
	}
	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) { builder.add(ModProperties.DUSTED); }
	@Override
	public BlockRenderType getRenderType(BlockState state) { return BlockRenderType.MODEL; }
	@Override
	public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
		world.getBlockTickScheduler().schedule(pos, this, 2);
	}
	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		world.getBlockTickScheduler().schedule(pos, this, 2);
		return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
	}
	@Override
	public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		if (world.getBlockEntity(pos) instanceof BrushableBlockEntity entity) entity.scheduledTick();
	}
	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) { return new SandyBlockEntity(pos, state); }
	@Override
	public BlockState getBrushedState(BlockState state) { return block.getDefaultState(); }
	@Override
	public SoundEvent getBrushSound() { return ModSoundEvents.ITEM_BRUSH_BRUSHING_SAND; }
	@Override
	public SoundEvent getBrushCompleteSound() { return ModSoundEvents.ITEM_BRUSH_BRUSHING_SAND_COMPLETE; }
}
