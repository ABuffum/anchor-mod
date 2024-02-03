package fun.wich.mixins.block;

import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AnvilBlock.class)
public class AnvilBlockMixin extends FallingBlock implements Waterloggable {
	@Shadow @Final public static DirectionProperty FACING;

	public AnvilBlockMixin(Settings settings) {
		super(settings);
		this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(Properties.WATERLOGGED, false));
	}
	@Inject(method = "<init>", at = @At("TAIL"))
	public void AnvilBlock(Settings settings, CallbackInfo ci) {
		this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(Properties.WATERLOGGED, false));
	}
	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		if (state.get(Properties.WATERLOGGED)) world.getFluidTickScheduler().schedule(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
		return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
	}
	@Override
	public FluidState getFluidState(BlockState state) {
		return state.get(Properties.WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
	}
	@Inject(method = "appendProperties", at = @At("TAIL"))
	protected void AppendProperties(StateManager.Builder<Block, BlockState> builder, CallbackInfo ci) {
		builder.add(Properties.WATERLOGGED);
	}
	@Inject(method = "getPlacementState", at = @At("RETURN"), cancellable = true)
	public void GetPlacementState(ItemPlacementContext ctx, CallbackInfoReturnable<BlockState> cir) {
		FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
		cir.setReturnValue(cir.getReturnValue().with(Properties.WATERLOGGED, fluidState.getFluid() == Fluids.WATER));
	}
}
