package fun.wich.mixins.block;

import net.minecraft.block.*;
import net.minecraft.block.enums.Attachment;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
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

@Mixin(BellBlock.class)
public abstract class BellBlockMixin extends BlockWithEntity implements Waterloggable {
	@Shadow @Final public static DirectionProperty FACING;
	@Shadow @Final public static EnumProperty<Attachment> ATTACHMENT;
	@Shadow @Final public static BooleanProperty POWERED;

	public BellBlockMixin(Settings settings) {
		super(settings);
		this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(ATTACHMENT, Attachment.FLOOR).with(POWERED, false).with(Properties.WATERLOGGED, false));
	}
	@Inject(method = "<init>", at = @At("TAIL"))
	public void BellBlock(Settings settings, CallbackInfo ci) {
		this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(ATTACHMENT, Attachment.FLOOR).with(POWERED, false).with(Properties.WATERLOGGED, false));
	}
	@Inject(method = "getStateForNeighborUpdate", at = @At("HEAD"))
	public void getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos, CallbackInfoReturnable<BlockState> cir) {
		if (state.get(Properties.WATERLOGGED)) world.getFluidTickScheduler().schedule(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
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
		if (cir.getReturnValue() != null) {
			FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
			cir.setReturnValue(cir.getReturnValue().with(Properties.WATERLOGGED, fluidState.getFluid() == Fluids.WATER));
		}
	}
}
