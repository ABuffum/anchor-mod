package fun.mousewich.block;

import fun.mousewich.mixins.entity.hostile.SlimeEntityInvoker;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public class SlimeLanternBlock extends HorizontalFacingBlock {
	protected static final VoxelShape SHAPE = VoxelShapes.union(Block.createCuboidShape(2, 0, 2, 14, 12, 14), Block.createCuboidShape(6.0, 12, 6.0, 10.0, 16, 10.0));

	public EntityType<? extends SlimeEntity> slime;
	public SlimeLanternBlock(Settings settings, EntityType<? extends SlimeEntity> slime) {
		super(settings);
		this.slime = slime;
		this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH));
	}

	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) { builder.add(FACING); }

	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) { return SHAPE; }

	@Override
	public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
		return Block.sideCoversSmallSquare(world, pos.offset(Direction.UP), Direction.DOWN);
	}

	@Override
	public PistonBehavior getPistonBehavior(BlockState state) { return PistonBehavior.DESTROY; }

	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		if (Direction.UP == direction && !state.canPlaceAt(world, pos)) return Blocks.AIR.getDefaultState();
		return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
	}

	@Override
	public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) { return false; }

	@Override
	public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack stack) {
		super.afterBreak(world, player, pos, state, blockEntity, stack);
		SlimeEntity slime = this.slime.create(world);
		if (slime == null) return;
		slime.refreshPositionAndAngles(pos, state.get(FACING).asRotation(), 0);
		((SlimeEntityInvoker)slime).SetSize(1, true);
		slime.setPersistent();
		world.spawnEntity(slime);
	}
}
