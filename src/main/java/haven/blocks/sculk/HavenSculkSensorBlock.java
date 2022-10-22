package haven.blocks.sculk;

import haven.HavenMod;
import haven.events.HavenGameEvent;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.enums.SculkSensorPhase;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.DustColorTransitionParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.event.listener.GameEventListener;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class HavenSculkSensorBlock extends BlockWithEntity implements Waterloggable {
	public static final Object2IntMap<GameEvent> FREQUENCIES = Object2IntMaps.unmodifiable(Util.make(new Object2IntOpenHashMap(), map -> {
		map.put(GameEvent.STEP, 1);
		map.put(GameEvent.FLAP, 2);
		map.put(GameEvent.SWIM, 3);
		map.put(HavenGameEvent.ELYTRA_GLIDE, 4);
		map.put(GameEvent.HIT_GROUND, 5);
		map.put(HavenGameEvent.TELEPORT, 5);
		map.put(GameEvent.SPLASH, 6);
		map.put(HavenGameEvent.ENTITY_SHAKE, 6);
		map.put(GameEvent.BLOCK_CHANGE, 6);
		map.put(HavenGameEvent.NOTE_BLOCK_PLAY, 6);
		map.put(GameEvent.PROJECTILE_SHOOT, 7);
		map.put(HavenGameEvent.DRINK, 7);
		map.put(GameEvent.PRIME_FUSE, 7);
		map.put(GameEvent.PROJECTILE_LAND, 8);
		map.put(GameEvent.EAT, 8);
		map.put(HavenGameEvent.ENTITY_INTERACT, 8);
		map.put(HavenGameEvent.ENTITY_DAMAGE, 8);
		map.put(GameEvent.EQUIP, 9);
		map.put(GameEvent.SHEAR, 9);
		map.put(HavenGameEvent.ENTITY_ROAR, 9);
		map.put(GameEvent.BLOCK_CLOSE, 10);
		map.put(HavenGameEvent.BLOCK_DEACTIVATE, 10);
		map.put(GameEvent.BLOCK_DETACH, 10);
		map.put(GameEvent.DISPENSE_FAIL, 10);
		map.put(GameEvent.BLOCK_OPEN, 11);
		map.put(HavenGameEvent.BLOCK_ACTIVATE, 11);
		map.put(GameEvent.BLOCK_ATTACH, 11);
		map.put(GameEvent.ENTITY_PLACE, 12);
		map.put(GameEvent.BLOCK_PLACE, 12);
		map.put(GameEvent.FLUID_PLACE, 12);
		map.put(HavenGameEvent.ENTITY_DIE, 13); //TODO: Consolidate Killed & Die events
		map.put(GameEvent.ENTITY_KILLED, 13);
		map.put(GameEvent.BLOCK_DESTROY, 13);
		map.put(GameEvent.FLUID_PICKUP, 13);
		map.put(HavenGameEvent.ITEM_INTERACT_FINISH, 14);
		map.put(GameEvent.CONTAINER_CLOSE, 14);
		map.put(GameEvent.PISTON_CONTRACT, 14);
		map.put(GameEvent.PISTON_EXTEND, 15);
		map.put(GameEvent.CONTAINER_OPEN, 15);
		map.put(HavenGameEvent.ITEM_INTERACT_START, 15);
		map.put(GameEvent.EXPLODE, 15);
		map.put(GameEvent.LIGHTNING_STRIKE, 15);
		map.put(HavenGameEvent.INSTRUMENT_PLAY, 15);
	}));
	public static final EnumProperty<SculkSensorPhase> SCULK_SENSOR_PHASE = Properties.SCULK_SENSOR_PHASE;
	public static final IntProperty POWER = Properties.POWER;
	public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
	protected static final VoxelShape OUTLINE_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 8.0, 16.0);
	private final int range;

	public HavenSculkSensorBlock(AbstractBlock.Settings settings, int range) {
		super(settings);
		this.setDefaultState(this.stateManager.getDefaultState().with(SCULK_SENSOR_PHASE, SculkSensorPhase.INACTIVE).with(POWER, 0).with(WATERLOGGED, false));
		this.range = range;
	}

	public int getRange() {
		return this.range;
	}

	@Override
	@Nullable
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		BlockPos blockPos = ctx.getBlockPos();
		FluidState fluidState = ctx.getWorld().getFluidState(blockPos);
		return this.getDefaultState().with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
	}

	@Override
	public FluidState getFluidState(BlockState state) {
		if (state.get(WATERLOGGED)) return Fluids.WATER.getStill(false);
		return super.getFluidState(state);
	}

	@Override
	public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		if (getPhase(state) != SculkSensorPhase.ACTIVE) {
			if (getPhase(state) == SculkSensorPhase.COOLDOWN) {
				world.setBlockState(pos, state.with(SCULK_SENSOR_PHASE, SculkSensorPhase.INACTIVE), Block.NOTIFY_ALL);
			}
			return;
		}
		setCooldown(world, pos, state);
	}

	@Override
	public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
		if (!world.isClient() && isInactive(state) && entity.getType() != HavenMod.WARDEN_ENTITY) {
			BlockEntity blockEntity = world.getBlockEntity(pos);
			if (blockEntity instanceof HavenSculkSensorBlockEntity sculk) sculk.setLastVibrationFrequency(FREQUENCIES.get(GameEvent.STEP));
			setActive(entity, world, pos, state, 15);
		}
		super.onSteppedOn(world, pos, state, entity);
	}

	@Override
	public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
		if (world.isClient() || state.isOf(oldState.getBlock())) return;
		if (state.get(POWER) > 0 && !world.getBlockTickScheduler().isScheduled(pos, this)) {
			world.setBlockState(pos, state.with(POWER, 0), Block.NOTIFY_LISTENERS | Block.FORCE_STATE);
		}
		world.getBlockTickScheduler().schedule(new BlockPos(pos), state.getBlock(), 1);
	}

	@Override
	public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
		if (state.isOf(newState.getBlock())) return;
		if (getPhase(state) == SculkSensorPhase.ACTIVE) updateNeighbors(world, pos);
		super.onStateReplaced(state, world, pos, newState, moved);
	}

	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		if (state.get(WATERLOGGED)) world.getFluidTickScheduler().schedule(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
		return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
	}

	private static void updateNeighbors(World world, BlockPos pos) {
		world.updateNeighborsAlways(pos, HavenMod.SCULK_SENSOR.BLOCK);
		world.updateNeighborsAlways(pos.offset(Direction.UP.getOpposite()), HavenMod.SCULK_SENSOR.BLOCK);
	}

	@Override
	@Nullable
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new HavenSculkSensorBlockEntity(pos, state);
	}

	@Nullable
	public <T extends BlockEntity> GameEventListener getGameEventListener(World world, T blockEntity) {
		if (blockEntity instanceof HavenSculkSensorBlockEntity) return ((HavenSculkSensorBlockEntity)blockEntity).getEventListener();
		return null;
	}

	@Override
	@Nullable
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world2, BlockState state2, BlockEntityType<T> type) {
		if (!world2.isClient) {
			return checkType(type, HavenMod.SCULK_SENSOR_ENTITY, (world, pos, state, blockEntity) -> blockEntity.getEventListener().tick(world));
		}
		return null;
	}

	@Override
	public BlockRenderType getRenderType(BlockState state) { return BlockRenderType.MODEL; }

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) { return OUTLINE_SHAPE; }

	@Override
	public boolean emitsRedstonePower(BlockState state) { return true; }

	@Override
	public int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) { return state.get(POWER); }

	public static SculkSensorPhase getPhase(BlockState state) { return state.get(SCULK_SENSOR_PHASE); }

	public static boolean isInactive(BlockState state) { return getPhase(state) == SculkSensorPhase.INACTIVE; }

	public static void setCooldown(World world, BlockPos pos, BlockState state) {
		world.setBlockState(pos, state.with(SCULK_SENSOR_PHASE, SculkSensorPhase.COOLDOWN).with(POWER, 0), Block.NOTIFY_ALL);
		world.getBlockTickScheduler().schedule(pos, state.getBlock(), 1);
		if (!state.get(WATERLOGGED)) {
			world.playSound(null, pos, SoundEvents.BLOCK_SCULK_SENSOR_CLICKING_STOP, SoundCategory.BLOCKS, 1.0f, world.random.nextFloat() * 0.2f + 0.8f);
		}
		HavenSculkSensorBlock.updateNeighbors(world, pos);
	}

	public static void setActive(@Nullable Entity entity, World world, BlockPos pos, BlockState state, int power) {
		world.setBlockState(pos, state.with(SCULK_SENSOR_PHASE, SculkSensorPhase.ACTIVE).with(POWER, power), Block.NOTIFY_ALL);
		world.getBlockTickScheduler().schedule(pos, state.getBlock(), 40);
		updateNeighbors(world, pos);
		world.emitGameEvent(entity, HavenGameEvent.SCULK_SENSOR_TENDRILS_CLICKING, pos);
		if (!state.get(WATERLOGGED)) {
			world.playSound(null, (double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, SoundEvents.BLOCK_SCULK_SENSOR_CLICKING, SoundCategory.BLOCKS, 1.0f, world.random.nextFloat() * 0.2f + 0.8f);
		}
	}

	@Override
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
		if (getPhase(state) != SculkSensorPhase.ACTIVE) return;
		Direction direction = Direction.random(random);
		if (direction == Direction.UP || direction == Direction.DOWN) return;
		double d = (double)pos.getX() + 0.5 + (direction.getOffsetX() == 0 ? 0.5 - random.nextDouble() : (double)direction.getOffsetX() * 0.6);
		double e = (double)pos.getY() + 0.25;
		double f = (double)pos.getZ() + 0.5 + (direction.getOffsetZ() == 0 ? 0.5 - random.nextDouble() : (double)direction.getOffsetZ() * 0.6);
		double g = (double)random.nextFloat() * 0.04;
		world.addParticle(DustColorTransitionParticleEffect.DEFAULT, d, e, f, 0.0, g, 0.0);
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(SCULK_SENSOR_PHASE, POWER, WATERLOGGED);
	}

	@Override
	public boolean hasComparatorOutput(BlockState state) { return true; }

	@Override
	public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity instanceof HavenSculkSensorBlockEntity sculk) {
			return getPhase(state) == SculkSensorPhase.ACTIVE ? sculk.getLastVibrationFrequency() : 0;
		}
		return 0;
	}

	@Override
	public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) { return false; }

	@Override
	public boolean hasSidedTransparency(BlockState state) { return true; }

	@Override
	public void onStacksDropped(BlockState state, ServerWorld world, BlockPos pos, ItemStack stack) {
		super.onStacksDropped(state, world, pos, stack);
		if (EnchantmentHelper.getLevel(Enchantments.SILK_TOUCH, stack) == 0) this.dropExperience(world, pos, 5);
	}
}
