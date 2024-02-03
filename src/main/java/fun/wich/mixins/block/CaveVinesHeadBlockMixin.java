package fun.wich.mixins.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.CaveVinesHeadBlock;
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

@Mixin(CaveVinesHeadBlock.class)
public class CaveVinesHeadBlockMixin {
	@Inject(method="onUse", at=@At("RETURN"))
	private void InjectBlockChangeOnUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir) {
		ActionResult result = cir.getReturnValue();
		if (result == ActionResult.SUCCESS || result == ActionResult.CONSUME) {
			world.emitGameEvent(player, GameEvent.BLOCK_CHANGE, pos);
		}
	}
}
