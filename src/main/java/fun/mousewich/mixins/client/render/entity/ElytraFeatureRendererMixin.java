package fun.mousewich.mixins.client.render.entity;

import fun.mousewich.origins.power.ElytraTexturePower;
import io.github.apace100.apoli.component.PowerHolderComponent;
import net.minecraft.client.render.entity.feature.ElytraFeatureRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(ElytraFeatureRenderer.class)
public class ElytraFeatureRendererMixin {
	@Unique
	private LivingEntity livingEntity;
	@ModifyArg(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/LivingEntity;FFFFFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/RenderLayer;getArmorCutoutNoCull(Lnet/minecraft/util/Identifier;)Lnet/minecraft/client/render/RenderLayer;"))
	private Identifier setTexture(Identifier identifier) {
		for (ElytraTexturePower power : PowerHolderComponent.getPowers(this.livingEntity, ElytraTexturePower.class)) {
			if (power.isActive() && power.getTextureLocation() != null) return power.getTextureLocation();
		}
		return identifier;
	}
}
