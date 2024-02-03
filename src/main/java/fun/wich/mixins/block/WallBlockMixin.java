package fun.wich.mixins.block;

import fun.wich.gen.data.tag.ModBlockTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.WallBlock;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WallBlock.class)
public class WallBlockMixin {
	@Inject(method="shouldConnectTo", at = @At("HEAD"), cancellable = true)
	private void ShouldConnectTo(BlockState state, boolean faceFullSquare, Direction side, CallbackInfoReturnable<Boolean> cir) {
		if (state.isIn(ModBlockTags.ROWS)) cir.setReturnValue(true);
	}
}
