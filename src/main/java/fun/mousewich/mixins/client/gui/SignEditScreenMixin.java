package fun.mousewich.mixins.client.gui;

import fun.mousewich.ModBase;
import net.minecraft.client.gui.screen.ingame.SignEditScreen;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.SignType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(SignEditScreen.class)
public class SignEditScreenMixin {
	@Redirect(method="render", at=@At(value="INVOKE", target="Lnet/minecraft/client/render/TexturedRenderLayers;getSignTextureId(Lnet/minecraft/util/SignType;)Lnet/minecraft/client/util/SpriteIdentifier;"))
	private SpriteIdentifier GetSignTextureIdForHangingSignSubtype(SignType type) {
		if (ModBase.HANGING_SIGN_SUBTYPES.containsKey(type)) return TexturedRenderLayers.getSignTextureId(ModBase.HANGING_SIGN_SUBTYPES.get(type));
		return TexturedRenderLayers.getSignTextureId(type);
	}
}
