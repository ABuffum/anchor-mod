package fun.wich.mixins.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.TripwireBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ShearsItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TripwireBlock.class)
public abstract class TripwireBlockMixin extends Block {
	public TripwireBlockMixin(Settings settings) { super(settings); }

	@Inject(method="onBreak", at = @At("HEAD"), cancellable = true)
	public void OnBreak(World world, BlockPos pos, BlockState state, PlayerEntity player, CallbackInfo ci) {
		if (!world.isClient && !player.getMainHandStack().isEmpty() && player.getMainHandStack().getItem() instanceof ShearsItem) {
			world.setBlockState(pos, state.with(TripwireBlock.DISARMED, true), Block.NO_REDRAW);
			world.emitGameEvent(player, GameEvent.SHEAR, pos);
		}
		super.onBreak(world, pos, state, player);
		ci.cancel();
	}
}
