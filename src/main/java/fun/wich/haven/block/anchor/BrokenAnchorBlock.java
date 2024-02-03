package fun.wich.haven.block.anchor;

import fun.wich.haven.HavenMod;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class BrokenAnchorBlock extends BlockWithEntity {
	public BrokenAnchorBlock(Settings settings) {
		super(settings.nonOpaque());
		setDefaultState(getStateManager().getDefaultState().with(AnchorBlock.OWNER, 0));
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) { builder.add(AnchorBlock.OWNER); }

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
		return VoxelShapes.cuboid(0f, 0f, 0f, 1f, 1.5f, 1f);
	}
	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
		return VoxelShapes.cuboid(0f, 0f, 0f, 1f, 1.5f, 1f);
	}

	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) { return new BrokenAnchorBlockEntity(pos, state); }

	@Override
	public BlockRenderType getRenderType(BlockState blockState) { return BlockRenderType.INVISIBLE; }

	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
		return checkType(type, HavenMod.BROKEN_ANCHOR_BLOCK_ENTITY, BrokenAnchorBlockEntity::tick);
	}

	@Override
	public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, BlockEntity blockEntity, ItemStack stack) {
		super.afterBreak(world, player, pos, state, blockEntity, stack);
		int owner = state.get(AnchorBlock.OWNER);
		if (HavenMod.ANCHOR_MAP.containsKey(owner)) {
			ItemStack otherStack = new ItemStack(HavenMod.BROKEN_ANCHOR_CORES.get(owner), 1);
			ItemEntity itemEntity = new ItemEntity(player.world, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, otherStack);
			player.world.spawnEntity(itemEntity);
		}
	}
}
