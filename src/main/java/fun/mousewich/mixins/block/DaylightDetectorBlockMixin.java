package fun.mousewich.mixins.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.DaylightDetectorBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DaylightDetectorBlock.class)
public class DaylightDetectorBlockMixin {
	@Inject(method="onUse", at=@At(value="INVOKE", shift=At.Shift.AFTER, target="Lnet/minecraft/world/World;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z"))
	private void InjectBlockChangeOnUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir) {
		world.emitGameEvent(player, GameEvent.BLOCK_CHANGE, pos);
	}
}
