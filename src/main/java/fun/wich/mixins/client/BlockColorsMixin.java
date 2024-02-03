package fun.wich.mixins.client;

import fun.wich.ModClient;
import net.minecraft.client.color.block.BlockColors;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockColors.class)
public class BlockColorsMixin {
	@Inject(method="create", at = @At("RETURN"))
	private static void create(CallbackInfoReturnable<BlockColors> cir) {
		ModClient.RegisterBlockColors(cir.getReturnValue());
	}
}