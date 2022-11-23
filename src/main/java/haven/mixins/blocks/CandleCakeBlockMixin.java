package haven.mixins.blocks;

import haven.ModBase;
import haven.blocks.cake.HavenCandleCakeBlock;
import net.minecraft.block.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CandleCakeBlock.class)
public class CandleCakeBlockMixin {
	@Inject(method="getCandleCakeFromCandle", at = @At("HEAD"), cancellable = true)
	private static void GetCandleCakeFromCandle(Block candle, CallbackInfoReturnable<BlockState> cir) {
		if (candle == ModBase.SOUL_CANDLE.getBlock()) cir.setReturnValue(ModBase.SOUL_CANDLE_CAKE.getDefaultState());
		else if (candle == ModBase.ENDER_CANDLE.getBlock()) cir.setReturnValue(ModBase.ENDER_CANDLE_CAKE.getDefaultState());
	}

	@Inject(method="canBeLit", at = @At("HEAD"), cancellable = true)
	private static void canBeLit(BlockState state, CallbackInfoReturnable<Boolean> cir) {
		Block block = state.getBlock();
		if (block instanceof HavenCandleCakeBlock) {
			cir.setReturnValue(state.contains(HavenCandleCakeBlock.LIT) && !state.get(HavenCandleCakeBlock.LIT));
		}
	}
}
