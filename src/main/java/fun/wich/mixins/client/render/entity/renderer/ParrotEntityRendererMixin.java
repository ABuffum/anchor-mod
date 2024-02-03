package fun.wich.mixins.client.render.entity.renderer;

import fun.wich.entity.variants.ParrotVariant;
import net.minecraft.client.render.entity.ParrotEntityRenderer;
import net.minecraft.entity.passive.ParrotEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ParrotEntityRenderer.class)
public class ParrotEntityRendererMixin {
	@Inject(method="getTexture(Lnet/minecraft/entity/passive/ParrotEntity;)Lnet/minecraft/util/Identifier;", at=@At("HEAD"), cancellable=true)
	public void GetExtraVariantTexture(ParrotEntity parrotEntity, CallbackInfoReturnable<Identifier> cir) {
		cir.setReturnValue(ParrotVariant.get(parrotEntity).texture);
	}
}
