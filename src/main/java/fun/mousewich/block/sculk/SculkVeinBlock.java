package fun.mousewich.block.sculk;

import fun.mousewich.ModBase;
import fun.mousewich.block.LichenGrower;
import fun.mousewich.block.MultifaceGrowthBlock;
import fun.mousewich.gen.data.tag.ModBlockTags;
import fun.mousewich.sound.ModSoundEvents;
import fun.mousewich.util.CollectionUtil;
import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.Tag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;

import java.util.Collection;
import java.util.Random;

public class SculkVeinBlock extends MultifaceGrowthBlock implements SculkSpreadable, Waterloggable {
	public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
	private final LichenGrower allGrowTypeGrower = new LichenGrower(new SculkVeinGrowChecker(LichenGrower.GROW_TYPES));
	private final LichenGrower samePositionOnlyGrower = new LichenGrower(new SculkVeinGrowChecker(LichenGrower.GrowType.SAME_POSITION));
	public SculkVeinBlock(Settings settings) {
		super(settings);
		this.setDefaultState(this.getDefaultState().with(WATERLOGGED, false));
	}
	@Override
	public LichenGrower getGrower() { return this.allGrowTypeGrower; }
	public LichenGrower getSamePositionOnlyGrower() { return this.samePositionOnlyGrower; }
	public static boolean place(WorldAccess world, BlockPos pos, BlockState state, Collection<Direction> directions) {
		boolean bl = false;
		BlockState blockState = ModBase.SCULK_VEIN.asBlock().getDefaultState();
		for (Direction direction : directions) {
			BlockPos blockPos;
			if (!SculkVeinBlock.canGrowOn(world, direction, blockPos = pos.offset(direction), world.getBlockState(blockPos))) continue;
			blockState = blockState.with(SculkVeinBlock.getProperty(direction), true);
			bl = true;
		}
		if (!bl) return false;
		if (!state.getFluidState().isEmpty()) blockState = blockState.with(WATERLOGGED, true);
		world.setBlockState(pos, blockState, Block.NOTIFY_ALL);
		return true;
	}
	@Override
	public void spreadAtSamePosition(WorldAccess world, BlockState state, BlockPos pos, Random random) {
		if (!state.isOf(this)) return;
		for (Direction direction : DIRECTIONS) {
			BooleanProperty booleanProperty = SculkVeinBlock.getProperty(direction);
			if (!state.get(booleanProperty) || !world.getBlockState(pos.offset(direction)).isOf(ModBase.SCULK.asBlock())) continue;
			state = state.with(booleanProperty, false);
		}
		if (!SculkVeinBlock.hasAnyDirection(state)) {
			FluidState fluidState = world.getFluidState(pos);
			state = (fluidState.isEmpty() ? Blocks.AIR : Blocks.WATER).getDefaultState();
		}
		world.setBlockState(pos, state, Block.NOTIFY_ALL);
		SculkSpreadable.super.spreadAtSamePosition(world, state, pos, random);
	}
	@Override
	public int spread(SculkSpreadManager.Cursor cursor, WorldAccess world, BlockPos catalystPos, Random random, SculkSpreadManager spreadManager, boolean shouldConvertToBlock) {
		if (shouldConvertToBlock && this.convertToBlock(spreadManager, world, cursor.getPos(), random)) return cursor.getCharge() - 1;
		return random.nextInt(spreadManager.getSpreadChance()) == 0 ? MathHelper.floor((float)cursor.getCharge() * 0.5f) : cursor.getCharge();
	}
	private boolean convertToBlock(SculkSpreadManager spreadManager, WorldAccess world, BlockPos pos, Random random) {
		BlockState blockState = world.getBlockState(pos);
		Tag.Identified<Block> tagKey = spreadManager.getReplaceableTag();
		for (Direction direction : CollectionUtil.copyShuffled(Direction.values(), random)) {
			BlockPos blockPos = pos.offset(direction);
			BlockState blockState2 = world.getBlockState(blockPos);
			if (!SculkVeinBlock.hasDirection(blockState, direction) || !blockState2.isIn(tagKey)) continue;
			BlockState blockState3 = ModBase.SCULK.asBlock().getDefaultState();
			world.setBlockState(blockPos, blockState3, Block.NOTIFY_ALL);
			//Block.pushEntitiesUpBeforeBlockChange(blockState2, blockState3, world, blockPos);
			world.playSound(null, blockPos, ModSoundEvents.BLOCK_SCULK_SPREAD, SoundCategory.BLOCKS, 1.0f, 1.0f);
			this.allGrowTypeGrower.grow(blockState3, world, blockPos, spreadManager.isWorldGen());
			Direction direction2 = direction.getOpposite();
			for (Direction direction3 : DIRECTIONS) {
				BlockPos blockPos2;
				BlockState blockState4;
				if (direction3 == direction2 || !(blockState4 = world.getBlockState(blockPos2 = blockPos.offset(direction3))).isOf(this)) continue;
				this.spreadAtSamePosition(world, blockState4, blockPos2, random);
			}
			return true;
		}
		return false;
	}
	public static boolean veinCoversSculkReplaceable(WorldAccess world, BlockState state, BlockPos pos) {
		if (!state.isOf(ModBase.SCULK_VEIN.asBlock())) return false;
		else {
			for (Direction direction : DIRECTIONS) {
				if (hasDirection(state, direction) && world.getBlockState(pos.offset(direction)).isIn(ModBlockTags.SCULK_REPLACEABLE)) return true;
			}
			return false;
		}
	}
	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		if (state.get(WATERLOGGED)) world.getFluidTickScheduler().schedule(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
		return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
	}
	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		super.appendProperties(builder);
		builder.add(WATERLOGGED);
	}
	@Override
	public boolean canReplace(BlockState state, ItemPlacementContext context) {
		return !context.getStack().isOf(ModBase.SCULK_VEIN.asItem()) || super.canReplace(state, context);
	}
	@Override
	public FluidState getFluidState(BlockState state) {
		if (state.get(WATERLOGGED)) return Fluids.WATER.getStill(false);
		return super.getFluidState(state);
	}
	@Override
	public PistonBehavior getPistonBehavior(BlockState state) { return PistonBehavior.DESTROY; }
	class SculkVeinGrowChecker extends LichenGrower.LichenGrowChecker {
		private final LichenGrower.GrowType[] growTypes;
		public SculkVeinGrowChecker(LichenGrower.GrowType ... growTypes) {
			super(SculkVeinBlock.this);
			this.growTypes = growTypes;
		}
		@Override
		public boolean canGrow(BlockView world, BlockPos pos, BlockPos growPos, Direction direction, BlockState state) {
			BlockPos blockPos;
			BlockState blockState = world.getBlockState(growPos.offset(direction));
			if (blockState.isOf(ModBase.SCULK.asBlock()) || blockState.isOf(ModBase.SCULK_CATALYST.asBlock()) || blockState.isOf(Blocks.MOVING_PISTON)) return false;
			if (pos.getManhattanDistance(growPos) == 2 && world.getBlockState(blockPos = pos.offset(direction.getOpposite())).isSideSolidFullSquare(world, blockPos, direction)) {
				return false;
			}
			FluidState fluidState = state.getFluidState();
			if (!fluidState.isEmpty() && fluidState.getFluid() != Fluids.WATER) return false;
			Material material = state.getMaterial();
			if (material == Material.FIRE) return false;
			return material.isReplaceable() || super.canGrow(world, pos, growPos, direction, state);
		}
		@Override
		public LichenGrower.GrowType[] getGrowTypes() { return this.growTypes; }
		@Override
		public boolean canGrow(BlockState state) { return !state.isOf(ModBase.SCULK_VEIN.asBlock()); }
	}
}
