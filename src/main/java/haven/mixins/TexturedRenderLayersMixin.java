package haven.mixins;

import haven.HavenMod;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;
import net.minecraft.util.SignType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TexturedRenderLayers.class)
public class TexturedRenderLayersMixin {
	@Inject(method = "createSignTextureId", at = @At("HEAD"), cancellable = true)
	private static void RetextureSigns(SignType type, CallbackInfoReturnable<SpriteIdentifier> cir) {
		if (HavenMod.SIGN_TYPES.contains(type)) {
			cir.setReturnValue(new SpriteIdentifier(new Identifier(HavenMod.NAMESPACE, "textures/atlas/signs.png"), new Identifier("entity/signs/" + type.getName())));
		}
	}
}
