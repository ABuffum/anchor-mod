package fun.wich.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import fun.wich.ModBase;
import fun.wich.block.tnt.ModTntBlock;
import net.minecraft.block.*;
import net.minecraft.block.enums.WireConnection;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

import java.util.HashSet;
import java.util.Map;

public class GunpowderFuseBlock extends Block {
	public static final EnumProperty<WireConnection> WIRE_CONNECTION_NORTH = Properties.NORTH_WIRE_CONNECTION;
	public static final EnumProperty<WireConnection> WIRE_CONNECTION_EAST = Properties.EAST_WIRE_CONNECTION;
	public static final EnumProperty<WireConnection> WIRE_CONNECTION_SOUTH = Properties.SOUTH_WIRE_CONNECTION;
	public static final EnumProperty<WireConnection> WIRE_CONNECTION_WEST = Properties.WEST_WIRE_CONNECTION;
	public static final BooleanProperty LIT = Properties.LIT;
	public static final Map<Direction, EnumProperty<WireConnection>> DIRECTION_TO_WIRE_CONNECTION_PROPERTY = Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, WIRE_CONNECTION_NORTH, Direction.EAST, WIRE_CONNECTION_EAST, Direction.SOUTH, WIRE_CONNECTION_SOUTH, Direction.WEST, WIRE_CONNECTION_WEST));
	private static final VoxelShape DOT_SHAPE = Block.createCuboidShape(3.0, 0.0, 3.0, 13.0, 1.0, 13.0);
	private static final Map<Direction, VoxelShape> field_24414 = Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, Block.createCuboidShape(3.0, 0.0, 0.0, 13.0, 1.0, 13.0), Direction.SOUTH, Block.createCuboidShape(3.0, 0.0, 3.0, 13.0, 1.0, 16.0), Direction.EAST, Block.createCuboidShape(3.0, 0.0, 3.0, 16.0, 1.0, 13.0), Direction.WEST, Block.createCuboidShape(0.0, 0.0, 3.0, 13.0, 1.0, 13.0)));
	private static final Map<Direction, VoxelShape> field_24415 = Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, VoxelShapes.union(field_24414.get(Direction.NORTH), Block.createCuboidShape(3.0, 0.0, 0.0, 13.0, 16.0, 1.0)), Direction.SOUTH, VoxelShapes.union(field_24414.get(Direction.SOUTH), Block.createCuboidShape(3.0, 0.0, 15.0, 13.0, 16.0, 16.0)), Direction.EAST, VoxelShapes.union(field_24414.get(Direction.EAST), Block.createCuboidShape(15.0, 0.0, 3.0, 16.0, 16.0, 13.0)), Direction.WEST, VoxelShapes.union(field_24414.get(Direction.WEST), Block.createCuboidShape(0.0, 0.0, 3.0, 1.0, 16.0, 13.0))));
	private static final Map<BlockState, VoxelShape> SHAPES = Maps.newHashMap();
	private final BlockState dotState;
	public GunpowderFuseBlock(Settings settings) {
		super(settings);
		this.setDefaultState(this.stateManager.getDefaultState().with(WIRE_CONNECTION_NORTH, WireConnection.NONE).with(WIRE_CONNECTION_EAST, WireConnection.NONE).with(WIRE_CONNECTION_SOUTH, WireConnection.NONE).with(WIRE_CONNECTION_WEST, WireConnection.NONE).with(LIT, false));
		this.dotState = this.getDefaultState().with(WIRE_CONNECTION_NORTH, WireConnection.SIDE).with(WIRE_CONNECTION_EAST, WireConnection.SIDE).with(WIRE_CONNECTION_SOUTH, WireConnection.SIDE).with(WIRE_CONNECTION_WEST, WireConnection.SIDE);
		for (BlockState blockState : this.getStateManager().getStates()) {
			if (blockState.get(LIT)) continue;
			SHAPES.put(blockState, this.getShapeForState(blockState));
		}
	}

	private VoxelShape getShapeForState(BlockState state) {
		VoxelShape voxelShape = DOT_SHAPE;
		for (Direction direction : Direction.Type.HORIZONTAL) {
			WireConnection wireConnection = state.get(DIRECTION_TO_WIRE_CONNECTION_PROPERTY.get(direction));
			if (wireConnection == WireConnection.SIDE) {
				voxelShape = VoxelShapes.union(voxelShape, field_24414.get(direction));
				continue;
			}
			if (wireConnection != WireConnection.UP) continue;
			voxelShape = VoxelShapes.union(voxelShape, field_24415.get(direction));
		}
		return voxelShape;
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return SHAPES.get(state.with(LIT, false));
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return this.getPlacementState(ctx.getWorld(), this.dotState, ctx.getBlockPos());
	}

	private BlockState getPlacementState(BlockView world, BlockState state, BlockPos pos) {
		boolean bl = isNotConnected(state);
		state = this.getDefaultWireState(world, this.getDefaultState().with(LIT, state.get(LIT)), pos);
		if (bl && isNotConnected(state)) return state;
		boolean bl2 = state.get(WIRE_CONNECTION_NORTH).isConnected();
		boolean bl3 = state.get(WIRE_CONNECTION_SOUTH).isConnected();
		boolean bl4 = state.get(WIRE_CONNECTION_EAST).isConnected();
		boolean bl5 = state.get(WIRE_CONNECTION_WEST).isConnected();
		boolean bl6 = !bl2 && !bl3;
		boolean bl7 = !bl4 && !bl5;
		if (!bl5 && bl6) state = state.with(WIRE_CONNECTION_WEST, WireConnection.SIDE);
		if (!bl4 && bl6) state = state.with(WIRE_CONNECTION_EAST, WireConnection.SIDE);
		if (!bl2 && bl7) state = state.with(WIRE_CONNECTION_NORTH, WireConnection.SIDE);
		if (!bl3 && bl7) state = state.with(WIRE_CONNECTION_SOUTH, WireConnection.SIDE);
		return state;
	}

	private BlockState getDefaultWireState(BlockView world, BlockState state, BlockPos pos) {
		boolean bl = !world.getBlockState(pos.up()).isSolidBlock(world, pos);
		for (Direction direction : Direction.Type.HORIZONTAL) {
			if (state.get(DIRECTION_TO_WIRE_CONNECTION_PROPERTY.get(direction)).isConnected()) continue;
			WireConnection wireConnection = this.getRenderConnectionType(world, pos, direction, bl);
			state = state.with(DIRECTION_TO_WIRE_CONNECTION_PROPERTY.get(direction), wireConnection);
		}
		return state;
	}

	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		if (direction == Direction.DOWN) return state;
		if (direction == Direction.UP) return this.getPlacementState(world, state, pos);
		WireConnection wireConnection = this.getRenderConnectionType(world, pos, direction);
		if (wireConnection.isConnected() == state.get(DIRECTION_TO_WIRE_CONNECTION_PROPERTY.get(direction)).isConnected() && !isFullyConnected(state)) {
			return state.with(DIRECTION_TO_WIRE_CONNECTION_PROPERTY.get(direction), wireConnection);
		}
		return this.getPlacementState(world, this.dotState.with(LIT, state.get(LIT)).with(DIRECTION_TO_WIRE_CONNECTION_PROPERTY.get(direction), wireConnection), pos);
	}

	private static boolean isFullyConnected(BlockState state) {
		return state.get(WIRE_CONNECTION_NORTH).isConnected() && state.get(WIRE_CONNECTION_SOUTH).isConnected() && state.get(WIRE_CONNECTION_EAST).isConnected() && state.get(WIRE_CONNECTION_WEST).isConnected();
	}

	private static boolean isNotConnected(BlockState state) {
		return !state.get(WIRE_CONNECTION_NORTH).isConnected() && !state.get(WIRE_CONNECTION_SOUTH).isConnected() && !state.get(WIRE_CONNECTION_EAST).isConnected() && !state.get(WIRE_CONNECTION_WEST).isConnected();
	}

	@Override
	public void prepare(BlockState state, WorldAccess world, BlockPos pos, int flags, int maxUpdateDepth) {
		BlockPos.Mutable mutable = new BlockPos.Mutable();
		for (Direction direction : Direction.Type.HORIZONTAL) {
			WireConnection wireConnection = state.get(DIRECTION_TO_WIRE_CONNECTION_PROPERTY.get(direction));
			if (wireConnection == WireConnection.NONE || world.getBlockState(mutable.set(pos, direction)).isOf(this)) continue;
			mutable.move(Direction.DOWN);
			BlockState blockState = world.getBlockState(mutable);
			if (!blockState.isOf(Blocks.OBSERVER)) {
				BlockPos blockPos = mutable.offset(direction.getOpposite());
				BlockState blockState2 = blockState.getStateForNeighborUpdate(direction.getOpposite(), world.getBlockState(blockPos), world, mutable, blockPos);
				replace(blockState, blockState2, world, mutable, flags, maxUpdateDepth);
			}
			mutable.set(pos, direction).move(Direction.UP);
			BlockState blockState3 = world.getBlockState(mutable);
			if (blockState3.isOf(Blocks.OBSERVER)) continue;
			BlockPos blockPos2 = mutable.offset(direction.getOpposite());
			BlockState blockState4 = blockState3.getStateForNeighborUpdate(direction.getOpposite(), world.getBlockState(blockPos2), world, mutable, blockPos2);
			replace(blockState3, blockState4, world, mutable, flags, maxUpdateDepth);
		}
	}

	private WireConnection getRenderConnectionType(BlockView world, BlockPos pos, Direction direction) {
		return this.getRenderConnectionType(world, pos, direction, !world.getBlockState(pos.up()).isSolidBlock(world, pos));
	}

	private WireConnection getRenderConnectionType(BlockView world, BlockPos pos, Direction direction, boolean bl) {
		BlockPos blockPos = pos.offset(direction);
		BlockState blockState = world.getBlockState(blockPos);
		if (bl && this.canRunOnTop(world, blockPos, blockState) && connectsTo(world.getBlockState(blockPos.up()))) {
			if (blockState.isSideSolidFullSquare(world, blockPos, direction.getOpposite())) return WireConnection.UP;
			return WireConnection.SIDE;
		}
		if (connectsTo(blockState, direction) || !blockState.isSolidBlock(world, blockPos) && connectsTo(world.getBlockState(blockPos.down()))) return WireConnection.SIDE;
		return WireConnection.NONE;
	}

	@Override
	public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
		BlockPos blockPos = pos.down();
		BlockState blockState = world.getBlockState(blockPos);
		return this.canRunOnTop(world, blockPos, blockState);
	}

	private boolean canRunOnTop(BlockView world, BlockPos pos, BlockState floor) {
		return floor.isSideSolidFullSquare(world, pos, Direction.UP) || floor.isOf(Blocks.HOPPER);
	}

	private void update(World world, BlockPos pos, BlockState state) {
		boolean otherLit = this.getReceivedLit(world, pos);
		boolean lit = state.get(LIT);
		if (!lit && otherLit) {
			if (world.getBlockState(pos) == state) world.setBlockState(pos, state.with(LIT, true), Block.NOTIFY_LISTENERS);
			HashSet<BlockPos> set = Sets.newHashSet();
			set.add(pos);
			for (Direction direction : Direction.values()) set.add(pos.offset(direction));
			for (BlockPos blockPos : set) world.updateNeighborsAlways(blockPos, this);
		}
		if (lit) Light(world, pos);
	}

	private boolean getReceivedLit(World world, BlockPos pos) {
		BlockState state = world.getBlockState(pos);
		if (state.isOf(this)) {
			if (state.get(LIT)) return true;
		}
		else if (state.getBlock() instanceof FireBlock) return true;
		for (Direction direction : Direction.Type.HORIZONTAL) {
			BlockPos blockPos = pos.offset(direction);
			BlockState blockState = world.getBlockState(blockPos);
			if (this.checkLit(blockState)) return true;
			BlockPos blockPos2 = pos.up();
			if (blockState.isSolidBlock(world, blockPos) && !world.getBlockState(blockPos2).isSolidBlock(world, blockPos2)) {
				if (this.checkLit(world.getBlockState(blockPos.up()))) return true;
				continue;
			}
			if (blockState.isSolidBlock(world, blockPos)) continue;
			if (this.checkLit(world.getBlockState(blockPos.down()))) return true;
		}
		return false;
	}

	private boolean checkLit(BlockState state) {
		return state.isOf(this) && state.get(LIT) || state.getBlock() instanceof FireBlock;
	}

	private void updateNeighbors(World world, BlockPos pos) {
		if (!world.getBlockState(pos).isOf(this)) return;
		world.updateNeighborsAlways(pos, this);
		for (Direction direction : Direction.values()) world.updateNeighborsAlways(pos.offset(direction), this);
	}

	@Override
	public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
		if (oldState.isOf(state.getBlock()) || world.isClient) return;
		this.update(world, pos, state);
		for (Direction direction : Direction.Type.VERTICAL) world.updateNeighborsAlways(pos.offset(direction), this);
		this.updateOffsetNeighbors(world, pos);
	}

	@Override
	public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
		if (moved || state.isOf(newState.getBlock())) return;
		super.onStateReplaced(state, world, pos, newState, moved);
		if (world.isClient) return;
		for (Direction direction : Direction.values()) {
			world.updateNeighborsAlways(pos.offset(direction), this);
		}
		this.update(world, pos, state);
		this.updateOffsetNeighbors(world, pos);
	}

	private void updateOffsetNeighbors(World world, BlockPos pos) {
		for (Direction direction : Direction.Type.HORIZONTAL) {
			this.updateNeighbors(world, pos.offset(direction));
		}
		for (Direction direction : Direction.Type.HORIZONTAL) {
			BlockPos blockPos = pos.offset(direction);
			if (world.getBlockState(blockPos).isSolidBlock(world, blockPos)) {
				this.updateNeighbors(world, blockPos.up());
				continue;
			}
			this.updateNeighbors(world, blockPos.down());
		}
	}

	@Override
	public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
		if (world.isClient) return;
		if (state.canPlaceAt(world, pos)) this.update(world, pos, state);
		else {
			dropStacks(state, world, pos);
			world.removeBlock(pos, false);
		}
	}

	protected static boolean connectsTo(BlockState state) { return connectsTo(state, null); }

	protected static boolean connectsTo(BlockState state, Direction dir) {
		if (state.isOf(ModBase.GUNPOWDER_FUSE.asBlock())) return true;
		if (state.isOf(ModBase.GUNPOWDER_BLOCK.asBlock())) return true;
		Block block = state.getBlock();
		if (block instanceof FireBlock) return true;
		if (block instanceof TntBlock || block instanceof ModTntBlock) return true;
		return false /* && dir != null */;
	}

	@Override
	public BlockState rotate(BlockState state, BlockRotation rotation) {
		switch (rotation) {
			case CLOCKWISE_180 -> {
				return state.with(WIRE_CONNECTION_NORTH, state.get(WIRE_CONNECTION_SOUTH)).with(WIRE_CONNECTION_EAST, state.get(WIRE_CONNECTION_WEST)).with(WIRE_CONNECTION_SOUTH, state.get(WIRE_CONNECTION_NORTH)).with(WIRE_CONNECTION_WEST, state.get(WIRE_CONNECTION_EAST));
			}
			case COUNTERCLOCKWISE_90 -> {
				return state.with(WIRE_CONNECTION_NORTH, state.get(WIRE_CONNECTION_EAST)).with(WIRE_CONNECTION_EAST, state.get(WIRE_CONNECTION_SOUTH)).with(WIRE_CONNECTION_SOUTH, state.get(WIRE_CONNECTION_WEST)).with(WIRE_CONNECTION_WEST, state.get(WIRE_CONNECTION_NORTH));
			}
			case CLOCKWISE_90 -> {
				return state.with(WIRE_CONNECTION_NORTH, state.get(WIRE_CONNECTION_WEST)).with(WIRE_CONNECTION_EAST, state.get(WIRE_CONNECTION_NORTH)).with(WIRE_CONNECTION_SOUTH, state.get(WIRE_CONNECTION_EAST)).with(WIRE_CONNECTION_WEST, state.get(WIRE_CONNECTION_SOUTH));
			}
		}
		return state;
	}

	@Override
	public BlockState mirror(BlockState state, BlockMirror mirror) {
		switch (mirror) {
			case LEFT_RIGHT -> {
				return state.with(WIRE_CONNECTION_NORTH, state.get(WIRE_CONNECTION_SOUTH)).with(WIRE_CONNECTION_SOUTH, state.get(WIRE_CONNECTION_NORTH));
			}
			case FRONT_BACK -> {
				return state.with(WIRE_CONNECTION_EAST, state.get(WIRE_CONNECTION_WEST)).with(WIRE_CONNECTION_WEST, state.get(WIRE_CONNECTION_EAST));
			}
		}
		return super.mirror(state, mirror);
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(WIRE_CONNECTION_NORTH, WIRE_CONNECTION_EAST, WIRE_CONNECTION_SOUTH, WIRE_CONNECTION_WEST, LIT);
	}

	public static void Light(World world, BlockPos pos) {
		BlockState state = world.getBlockState(pos);
		Block block = state.getBlock();
		if (!(block instanceof GunpowderFuseBlock) && block != ModBase.GUNPOWDER_BLOCK.asBlock()) return;
		world.setBlockState(pos, Blocks.FIRE.getDefaultState(), Block.NOTIFY_ALL);
		for (Direction direction : DIRECTIONS) {
			BlockPos offsetPos = pos.offset(direction);
			BlockState offsetState = world.getBlockState(offsetPos);
			Block offsetBlock = offsetState.getBlock();
			if (offsetBlock instanceof GunpowderFuseBlock) Light(world, offsetPos);
			else if (offsetBlock instanceof TntBlock) LightTnt(world, offsetPos);
			else if (offsetBlock instanceof ModTntBlock tnt) LightTnt(world, offsetPos, tnt);
			else if (offsetBlock == ModBase.GUNPOWDER_BLOCK.asBlock()) Light(world, offsetPos);
		}
	}
	private static void LightTnt(World world, BlockPos pos) {
		TntBlock.primeTnt(world, pos);
		world.setBlockState(pos, Blocks.AIR.getDefaultState(), Block.NOTIFY_ALL | Block.REDRAW_ON_MAIN_THREAD);
	}
	private static void LightTnt(World world, BlockPos pos, ModTntBlock tnt) {
		tnt.primeTnt(world, pos);
		world.setBlockState(pos, Blocks.AIR.getDefaultState(), Block.NOTIFY_ALL | Block.REDRAW_ON_MAIN_THREAD);
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (!player.getAbilities().allowModifyWorld) return ActionResult.PASS;
		ItemStack itemStack = player.getStackInHand(hand);
		boolean lava;
		if ((lava = itemStack.isOf(ModBase.LAVA_BOTTLE)) || itemStack.isOf(Items.FLINT_AND_STEEL) || itemStack.isOf(Items.FIRE_CHARGE)) {
			Light(world, pos);
			if (!player.isCreative()) {
				if (itemStack.isOf(Items.FLINT_AND_STEEL)) itemStack.damage(1, player, p -> p.sendToolBreakStatus(hand));
				else {
					itemStack.decrement(1);
					if (lava) player.giveItemStack(new ItemStack(ModBase.LAVA_BOTTLE.getRecipeRemainder()));
				}
			}
			player.incrementStat(Stats.USED.getOrCreateStat(itemStack.getItem()));
			return ActionResult.success(world.isClient);
		}
		if (isFullyConnected(state) || isNotConnected(state)) {
			BlockState blockState = isFullyConnected(state) ? this.getDefaultState() : this.dotState;
			blockState = blockState.with(LIT, state.get(LIT));
			if ((blockState = this.getPlacementState(world, blockState, pos)) != state) {
				world.setBlockState(pos, blockState, Block.NOTIFY_ALL);
				this.updateForNewState(world, pos, state, blockState);
				return ActionResult.SUCCESS;
			}
		}
		return ActionResult.PASS;
	}

	private void updateForNewState(World world, BlockPos pos, BlockState oldState, BlockState newState) {
		for (Direction direction : Direction.Type.HORIZONTAL) {
			BlockPos blockPos = pos.offset(direction);
			if (oldState.get(DIRECTION_TO_WIRE_CONNECTION_PROPERTY.get(direction)).isConnected() == newState.get(DIRECTION_TO_WIRE_CONNECTION_PROPERTY.get(direction)).isConnected() || !world.getBlockState(blockPos).isSolidBlock(world, blockPos)) continue;
			world.updateNeighborsExcept(blockPos, newState.getBlock(), direction.getOpposite());
		}
	}
}