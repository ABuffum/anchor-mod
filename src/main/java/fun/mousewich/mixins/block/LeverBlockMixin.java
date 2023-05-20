package fun.mousewich.mixins.block;

import fun.mousewich.event.ModGameEvent;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeverBlock;
import net.minecraft.block.WallMountedBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LeverBlock.class)
public abstract class LeverBlockMixin extends WallMountedBlock {
	@Shadow public abstract BlockState togglePower(BlockState state, World world, BlockPos pos);
	protected LeverBlockMixin(Settings settings) { super(settings); }
	@Inject(method="onUse", at = @At("TAIL"))
	public void onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir) {
		if (!world.isClient) {
			BlockState blockState = this.togglePower(state, world, pos);
			world.emitGameEvent(player, blockState.get(LeverBlock.POWERED) ? ModGameEvent.BLOCK_ACTIVATE : ModGameEvent.BLOCK_DEACTIVATE, pos);
		}
	}
}
