package fun.wich.block;

import fun.wich.registry.ModBambooRegistry;
import net.minecraft.block.*;
import net.minecraft.block.enums.BambooLeaves;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.SwordItem;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

import java.util.Random;

import static net.minecraft.block.BambooBlock.AGE;
import static net.minecraft.block.BambooBlock.STAGE;
import static net.minecraft.block.BambooBlock.LEAVES;

public class DriedBambooBlock extends Block {
	protected static final VoxelShape SMALL_LEAVES_SHAPE = Block.createCuboidShape(5.0, 0.0, 5.0, 11.0, 16.0, 11.0);
	protected static final VoxelShape LARGE_LEAVES_SHAPE = Block.createCuboidShape(3.0, 0.0, 3.0, 13.0, 16.0, 13.0);
	protected static final VoxelShape NO_LEAVES_SHAPE = Block.createCuboidShape(6.5, 0.0, 6.5, 9.5, 16.0, 9.5);

	public DriedBambooBlock(AbstractBlock.Settings settings) {
		super(settings);
		this.setDefaultState(this.stateManager.getDefaultState().with(AGE, 0).with(LEAVES, BambooLeaves.NONE).with(STAGE, 0));
	}

	public static boolean AllowDriedBamboo(BlockState instance, Block block) {
		return ((block == Blocks.BAMBOO || block == ModBambooRegistry.DRIED_BAMBOO.asBlock()) && instance.isOf(ModBambooRegistry.DRIED_BAMBOO.asBlock())) || instance.isOf(block);
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) { builder.add(AGE, LEAVES, STAGE); }
	@Override
	public AbstractBlock.OffsetType getOffsetType() { return AbstractBlock.OffsetType.XZ; }
	@Override
	public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) { return true; }

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		VoxelShape voxelShape = state.get(LEAVES) == BambooLeaves.LARGE ? LARGE_LEAVES_SHAPE : SMALL_LEAVES_SHAPE;
		Vec3d vec3d = state.getModelOffset(world, pos);
		return voxelShape.offset(vec3d.x, vec3d.y, vec3d.z);
	}

	@Override
	public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) { return false; }

	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		Vec3d vec3d = state.getModelOffset(world, pos);
		return NO_LEAVES_SHAPE.offset(vec3d.x, vec3d.y, vec3d.z);
	}

	@Override
	public boolean isShapeFullCube(BlockState state, BlockView world, BlockPos pos) { return false; }

	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
		if (!fluidState.isEmpty()) return null;
		BlockState blockState = ctx.getWorld().getBlockState(ctx.getBlockPos().down());
		if (blockState.isIn(BlockTags.BAMBOO_PLANTABLE_ON)) {
			if (blockState.isOf(Blocks.BAMBOO_SAPLING)) return this.getDefaultState().with(AGE, 0);
			if (AllowDriedBamboo(blockState, Blocks.BAMBOO)) return this.getDefaultState().with(AGE, blockState.get(AGE) > 0 ? 1 : 0);
			BlockState blockState2 = ctx.getWorld().getBlockState(ctx.getBlockPos().up());
			if (AllowDriedBamboo(blockState2, Blocks.BAMBOO)) return this.getDefaultState().with(AGE, blockState2.get(AGE));
			return this.getDefaultState();
		}
		return null;
	}

	@Override
	public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		if (!state.canPlaceAt(world, pos)) world.breakBlock(pos, true);
	}

	@Override
	public boolean hasRandomTicks(BlockState state) { return state.get(STAGE) == 0; }

	@Override
	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		int i;
		if (state.get(STAGE) != 0) return;
		if (random.nextInt(3) == 0 && world.isAir(pos.up()) && world.getBaseLightLevel(pos.up(), 0) >= 9 && (i = this.countBambooBelow(world, pos) + 1) < 16) {
			this.updateLeaves(state, world, pos, random, i);
		}
	}

	@Override
	public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
		return world.getBlockState(pos.down()).isIn(BlockTags.BAMBOO_PLANTABLE_ON);
	}

	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		if (!state.canPlaceAt(world, pos)) world.getBlockTickScheduler().schedule(pos, this, 1);
		if (direction == Direction.UP && AllowDriedBamboo(neighborState, Blocks.BAMBOO) && neighborState.get(AGE) > state.get(AGE)) {
			world.setBlockState(pos, state.cycle(AGE), Block.NOTIFY_LISTENERS);
		}
		return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
	}

	@Override
	public float calcBlockBreakingDelta(BlockState state, PlayerEntity player, BlockView world, BlockPos pos) {
		if (player.getMainHandStack().getItem() instanceof SwordItem) return 1.0f;
		return super.calcBlockBreakingDelta(state, player, world, pos);
	}

	protected void updateLeaves(BlockState state, World world, BlockPos pos, Random random, int height) {
		BlockState blockState = world.getBlockState(pos.down());
		BlockPos blockPos = pos.down(2);
		BlockState blockState2 = world.getBlockState(blockPos);
		BambooLeaves bambooLeaves = BambooLeaves.NONE;
		if (height >= 1) {
			if (!AllowDriedBamboo(blockState, Blocks.BAMBOO) || blockState.get(LEAVES) == BambooLeaves.NONE) bambooLeaves = BambooLeaves.SMALL;
			else if (AllowDriedBamboo(blockState, Blocks.BAMBOO) && blockState.get(LEAVES) != BambooLeaves.NONE) {
				bambooLeaves = BambooLeaves.LARGE;
				if (AllowDriedBamboo(blockState2, Blocks.BAMBOO)) {
					world.setBlockState(pos.down(), blockState.with(LEAVES, BambooLeaves.SMALL), Block.NOTIFY_ALL);
					world.setBlockState(blockPos, blockState2.with(LEAVES, BambooLeaves.NONE), Block.NOTIFY_ALL);
				}
			}
		}
		int i = state.get(AGE) == 1 || AllowDriedBamboo(blockState2, Blocks.BAMBOO) ? 1 : 0;
		int j = height >= 11 && random.nextFloat() < 0.25f || height == 15 ? 1 : 0;
		world.setBlockState(pos.up(), this.getDefaultState().with(AGE, i).with(LEAVES, bambooLeaves).with(STAGE, j), Block.NOTIFY_ALL);
	}

	protected int countBambooBelow(BlockView world, BlockPos pos) {
		int i = 0;
		while (i < 16 && AllowDriedBamboo(world.getBlockState(pos.down(i + 1)), Blocks.BAMBOO)) { ++i; }
		return i;
	}
}
