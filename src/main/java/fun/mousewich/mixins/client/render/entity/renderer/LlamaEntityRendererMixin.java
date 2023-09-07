package fun.mousewich.mixins.client.render.entity.renderer;

import fun.mousewich.entity.variants.LlamaVariant;
import net.minecraft.client.render.entity.LlamaEntityRenderer;
import net.minecraft.entity.passive.LlamaEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LlamaEntityRenderer.class)
public class LlamaEntityRendererMixin {
	@Inject(method="getTexture(Lnet/minecraft/entity/passive/LlamaEntity;)Lnet/minecraft/util/Identifier;", at=@At("HEAD"), cancellable=true)
	public void GetExtraVariantTexture(LlamaEntity llamaEntity, CallbackInfoReturnable<Identifier> cir) {
		cir.setReturnValue(LlamaVariant.get(llamaEntity).texture);
	}
}
