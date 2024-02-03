package fun.wich.mixins.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.RespawnAnchorBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RespawnAnchorBlock.class)
public class RespawnAnchorBlockMixin {
	@Inject(method="charge", at=@At("TAIL"))
	private static void InjectBlockChangeOnCharge(World world, BlockPos pos, BlockState state, CallbackInfo ci) {
		world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos);
	}
}
