package haven.mixins.blocks;

import haven.blocks.RowBlock;
import net.minecraft.block.*;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WallBlock.class)
public class WallBlockMixin {
	@Inject(method="shouldConnectTo", at = @At("HEAD"), cancellable = true)
	private void shouldConnectTo(BlockState state, boolean faceFullSquare, Direction side, CallbackInfoReturnable<Boolean> cir) {
		if (state.getBlock() instanceof RowBlock) cir.setReturnValue(true);
	}
}
