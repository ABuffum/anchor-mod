package fun.mousewich.mixins.client.render.entity.feature;

import fun.mousewich.entity.variants.SnowGolemVariant;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.SnowmanPumpkinFeatureRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(SnowmanPumpkinFeatureRenderer.class)
public abstract class SnowmanPumpkinFeatureRendererMixin {
	@Redirect(at=@At(value="INVOKE", target = "Lnet/minecraft/client/render/item/ItemRenderer;renderItem(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformation$Mode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/world/World;III)V"), method="render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/passive/SnowGolemEntity;FFFFFF)V")
	private void RenderVariantPumpkin(ItemRenderer instance, LivingEntity entity, ItemStack item, ModelTransformation.Mode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, World world, int light, int overlay, int seed) {
		if (entity instanceof SnowGolemEntity snowGolemEntity) {
			SnowGolemVariant variant = SnowGolemVariant.get(snowGolemEntity);
			if (variant != SnowGolemVariant.DEFAULT) {
				instance.renderItem(entity, new ItemStack(variant.item), renderMode, leftHanded, matrices, vertexConsumers, world, light, overlay, seed);
				return;
			}
		}
		instance.renderItem(entity, item, renderMode, leftHanded, matrices, vertexConsumers, world, light, overlay, seed);
	}
}
