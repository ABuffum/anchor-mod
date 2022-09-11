package haven.mixins.client.rendering;

import haven.HavenMod;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.util.Identifier;
import net.minecraft.util.SignType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityModelLayers.class)
public class EntityModelLayersMixin {
	@Inject(method = "createSign", at = @At("HEAD"), cancellable = true)
	private static void RetextureSigns(SignType type, CallbackInfoReturnable<EntityModelLayer> cir) {
		if (HavenMod.SIGN_TYPES.contains(type)) {
			cir.setReturnValue(new EntityModelLayer(HavenMod.ID("sign/" + type.getName()), "main"));
		}
	}
}
