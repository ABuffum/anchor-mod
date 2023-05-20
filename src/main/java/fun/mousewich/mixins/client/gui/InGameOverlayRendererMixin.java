package fun.mousewich.mixins.client.gui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameOverlayRenderer;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(InGameOverlayRenderer.class)
public class InGameOverlayRendererMixin {
	@Inject(method = "renderUnderwaterOverlay", at = @At("HEAD"), cancellable = true)
	private static void preventWaterOverlayRenderingInCauldrons(MinecraftClient client, MatrixStack matrices, CallbackInfo ci) {
		ClientPlayerEntity player = client.player;
		if (player != null) {
			BlockPos pos = player.getBlockPos();
			if (player.world.getBlockState(pos).isOf(Blocks.WATER_CAULDRON)) {
				if (!player.world.getFluidState(pos.up()).isIn(FluidTags.WATER)) {
					ci.cancel();
				}
			}
		}
	}
}
