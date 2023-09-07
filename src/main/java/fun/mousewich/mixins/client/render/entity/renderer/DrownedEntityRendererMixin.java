package fun.mousewich.mixins.client.render.entity.renderer;

import fun.mousewich.entity.variants.DrownedVariant;
import net.minecraft.client.render.entity.DrownedEntityRenderer;
import net.minecraft.entity.mob.DrownedEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DrownedEntityRenderer.class)
public class DrownedEntityRendererMixin {
	@Inject(method="getTexture", at=@At("HEAD"), cancellable=true)
	public void GetVariantTexture(ZombieEntity zombieEntity, CallbackInfoReturnable<Identifier> cir) {
		if (zombieEntity instanceof DrownedEntity drownedEntity) {
			DrownedVariant variant = DrownedVariant.get(drownedEntity);
			if (variant != DrownedVariant.DEFAULT) cir.setReturnValue(variant.texture);
		}
	}
}
