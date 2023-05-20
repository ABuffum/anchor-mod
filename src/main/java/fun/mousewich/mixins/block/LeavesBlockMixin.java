package fun.mousewich.mixins.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.Waterloggable;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(LeavesBlock.class)
public class LeavesBlockMixin extends Block implements Waterloggable {
	@Shadow @Final public static IntProperty DISTANCE;
	@Shadow @Final public static BooleanProperty PERSISTENT;

	public LeavesBlockMixin(Settings settings) {
		super(settings);
		this.setDefaultState(this.stateManager.getDefaultState().with(DISTANCE, 7).with(PERSISTENT, false).with(Properties.WATERLOGGED, false));
	}
	@Inject(method = "<init>", at = @At("TAIL"))
	public void LeavesBlock(Settings settings, CallbackInfo ci) {
		this.setDefaultState(this.stateManager.getDefaultState().with(DISTANCE, 7).with(PERSISTENT, false).with(Properties.WATERLOGGED, false));
	}
	@Inject(method="scheduledTick", at = @At("HEAD"), cancellable = true)
	public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {
		world.setBlockState(pos, UpdateDistanceFromLogs(state, world, pos), Block.NOTIFY_ALL);
		ci.cancel();
	}
	private static BlockState UpdateDistanceFromLogs(BlockState state, WorldAccess world, BlockPos pos) {
		int i = 7;
		BlockPos.Mutable mutable = new BlockPos.Mutable();
		for (Direction direction : Direction.values()) {
			mutable.set(pos, direction);
			i = Math.min(i, GetDistanceFromLog(world.getBlockState(mutable)) + 1);
			if (i == 1) break;
		}
		return state.with(DISTANCE, i);
	}
	private static int GetDistanceFromLog(BlockState state) {
		if (state.isIn(BlockTags.LOGS)) return 0;
		else return state.getBlock() instanceof LeavesBlock ? state.get(DISTANCE) : 7;
	}
	@Inject(method = "getStateForNeighborUpdate", at = @At("TAIL"), cancellable = true)
	public void getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos, CallbackInfoReturnable<BlockState> cir) {
		if (state.get(Properties.WATERLOGGED)) world.getFluidTickScheduler().schedule(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
		int i = GetDistanceFromLog(neighborState) + 1;
		if (i != 1 || state.get(Properties.DISTANCE_1_7) != i) world.getBlockTickScheduler().schedule(pos, this, 1);
		cir.setReturnValue(state);
	}
	@Override
	public FluidState getFluidState(BlockState state) {
		return state.get(Properties.WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
	}
	@Shadow
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
		if (world.hasRain(pos.up())) {
			if (random.nextInt(15) == 1) {
				BlockPos blockPos = pos.down();
				BlockState blockState = world.getBlockState(blockPos);
				if (!blockState.isOpaque() || !blockState.isSideSolidFullSquare(world, blockPos, Direction.UP)) {
					double d = (double) pos.getX() + random.nextDouble();
					double e = (double) pos.getY() - 0.05;
					double f = (double) pos.getZ() + random.nextDouble();
					world.addParticle(ParticleTypes.DRIPPING_WATER, d, e, f, 0.0, 0.0, 0.0);
				}
			}
		}
	}
	@Inject(method = "appendProperties", at = @At("TAIL"))
	protected void AppendProperties(StateManager.Builder<Block, BlockState> builder, CallbackInfo ci) {
		builder.add(Properties.WATERLOGGED);
	}
	@Inject(method = "getPlacementState", at = @At("RETURN"), cancellable = true)
	public void GetPlacementState(ItemPlacementContext ctx, CallbackInfoReturnable<BlockState> cir) {
		FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
		BlockState blockState = this.getDefaultState().with(PERSISTENT, true).with(Properties.WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
		cir.setReturnValue(UpdateDistanceFromLogs(blockState, ctx.getWorld(), ctx.getBlockPos()));
	}
}
