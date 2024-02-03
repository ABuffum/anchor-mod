package fun.wich.mixins.client.render.entity.renderer;

import fun.wich.entity.variants.RabbitVariant;
import net.minecraft.client.render.entity.RabbitEntityRenderer;
import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RabbitEntityRenderer.class)
public class RabbitEntityRendererMixin {
	@Inject(method="getTexture(Lnet/minecraft/entity/passive/RabbitEntity;)Lnet/minecraft/util/Identifier;", at=@At("HEAD"), cancellable=true)
	public void GetExtraVariantTexture(RabbitEntity rabbitEntity, CallbackInfoReturnable<Identifier> cir) {
		cir.setReturnValue(RabbitVariant.get(rabbitEntity).texture);
	}
}
