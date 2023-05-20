package fun.mousewich.block.sign;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import fun.mousewich.gen.data.tag.ModBlockTags;
import fun.mousewich.mixins.entity.SignBlockEntityAccessor;
import fun.mousewich.util.RotationUtil;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

import java.util.Map;
import java.util.Optional;

public class HangingSignBlock extends AbstractSignBlock {
	public static final IntProperty ROTATION = Properties.ROTATION;
	public static final BooleanProperty ATTACHED = Properties.ATTACHED;
	protected static final VoxelShape DEFAULT_SHAPE = Block.createCuboidShape(3.0, 0.0, 3.0, 13.0, 16.0, 13.0);
	private static final Map<Integer, VoxelShape> SHAPES_FOR_ROTATION = Maps.newHashMap(ImmutableMap.of(0, Block.createCuboidShape(1.0, 0.0, 7.0, 15.0, 10.0, 9.0), 4, Block.createCuboidShape(7.0, 0.0, 1.0, 9.0, 10.0, 15.0), 8, Block.createCuboidShape(1.0, 0.0, 7.0, 15.0, 10.0, 9.0), 12, Block.createCuboidShape(7.0, 0.0, 1.0, 9.0, 10.0, 15.0)));

	public HangingSignBlock(Settings settings, SignType signType) {
		super(settings, signType);
		this.setDefaultState(this.stateManager.getDefaultState().with(ROTATION, 0).with(ATTACHED, false).with(WATERLOGGED, false));
	}
	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity instanceof SignBlockEntity signBlockEntity) {
			ItemStack itemStack = player.getStackInHand(hand);
			if (!shouldRunCommand(signBlockEntity, player) && itemStack.getItem() instanceof BlockItem) return ActionResult.PASS;
		}
		return super.onUse(state, world, pos, player, hand, hit);
	}
	public static boolean shouldFilterText(PlayerEntity player) {
		if (player instanceof ServerPlayerEntity serverPlayer) return serverPlayer.shouldFilterText();
		else if (player instanceof ClientPlayerEntity) {
			MinecraftClient client = MinecraftClient.getInstance();
			if (client != null) return client.shouldFilterText();
		}
		return false;
	}
	public static boolean shouldRunCommand(SignBlockEntity signBlockEntity, PlayerEntity player) {
		for (Text text : ((SignBlockEntityAccessor)signBlockEntity).GetTexts(shouldFilterText(player))) {
			Style style = text.getStyle();
			ClickEvent clickEvent = style.getClickEvent();
			if (clickEvent == null || clickEvent.getAction() != ClickEvent.Action.RUN_COMMAND) continue;
			return true;
		}
		return false;
	}
	@Override
	public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
		return world.getBlockState(pos.up()).isSideSolid(world, pos.up(), Direction.DOWN, SideShapeType.CENTER);
	}
	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		World world = ctx.getWorld();
		FluidState fluidState = world.getFluidState(ctx.getBlockPos());
		BlockPos blockPos = ctx.getBlockPos().up();
		BlockState blockState = world.getBlockState(blockPos);
		boolean bl = blockState.isIn(ModBlockTags.ALL_HANGING_SIGNS);
		Direction direction = Direction.fromRotation(ctx.getPlayerYaw());
		boolean bl2 = !Block.isFaceFullSquare(blockState.getCollisionShape(world, blockPos), Direction.DOWN) || ctx.shouldCancelInteraction();
		if (bl && !ctx.shouldCancelInteraction()) {
			Optional<Direction> optional;
			if (blockState.contains(WallHangingSignBlock.FACING)) {
				Direction direction2 = blockState.get(WallHangingSignBlock.FACING);
				if (direction2.getAxis().test(direction)) bl2 = false;
			}
			else if (blockState.contains(ROTATION) && (optional = RotationUtil.toDirection(blockState.get(ROTATION))).isPresent() && optional.get().getAxis().test(direction)) {
				bl2 = false;
			}
		}
		int i = !bl2 ? RotationUtil.fromDirection(direction) : MathHelper.floor((double)((180.0f + ctx.getPlayerYaw()) * 16.0f / 360.0f) + 0.5) & 0xF;
		return this.getDefaultState().with(ATTACHED, bl2).with(ROTATION, i).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
	}
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		VoxelShape voxelShape = SHAPES_FOR_ROTATION.get(state.get(ROTATION));
		return voxelShape == null ? DEFAULT_SHAPE : voxelShape;
	}
	@Override
	public VoxelShape getSidesShape(BlockState state, BlockView world, BlockPos pos) {
		return this.getOutlineShape(state, world, pos, ShapeContext.absent());
	}
	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		if (direction == Direction.UP && !this.canPlaceAt(state, world, pos)) return Blocks.AIR.getDefaultState();
		return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
	}
	@Override
	public BlockState rotate(BlockState state, BlockRotation rotation) {
		return state.with(ROTATION, rotation.rotate(state.get(ROTATION), 16));
	}
	@Override
	public BlockState mirror(BlockState state, BlockMirror mirror) {
		return state.with(ROTATION, mirror.mirror(state.get(ROTATION), 16));
	}
	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) { builder.add(ROTATION, ATTACHED, WATERLOGGED); }
	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) { return new SignBlockEntity(pos, state); }
}
