package fun.wich.mixins.block;

import fun.wich.gen.data.tag.ModBlockTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public class BlockMixin {
	@Inject(method="cannotConnect", at = @At("HEAD"), cancellable = true)
	private static void CannotConnect(BlockState state, CallbackInfoReturnable<Boolean> cir) {
		if (state.isIn(ModBlockTags.GOURD_LANTERNS)
				|| state.isIn(ModBlockTags.CARVED_GOURDS)
				|| state.isIn(ModBlockTags.GOURDS)) cir.setReturnValue(true);
	}
}
