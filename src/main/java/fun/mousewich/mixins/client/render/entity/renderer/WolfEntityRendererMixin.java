package fun.mousewich.mixins.client.render.entity.renderer;

import fun.mousewich.entity.variants.WolfVariant;
import net.minecraft.client.render.entity.WolfEntityRenderer;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WolfEntityRenderer.class)
public class WolfEntityRendererMixin {
	@Inject(method="getTexture(Lnet/minecraft/entity/passive/WolfEntity;)Lnet/minecraft/util/Identifier;", at=@At("HEAD"), cancellable=true)
	public void getTexture(WolfEntity entity, CallbackInfoReturnable<Identifier> cir) {
		Identifier texture = WolfVariant.getTexture(entity);
		if (texture != null) cir.setReturnValue(texture);
	}
}
