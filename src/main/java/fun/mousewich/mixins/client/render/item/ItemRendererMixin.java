package fun.mousewich.mixins.client.render.item;

import fun.mousewich.item.JavelinItem;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.client.render.item.ItemModels;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.resource.SynchronousResourceReloader;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin implements SynchronousResourceReloader {
	@Shadow @Final private ItemModels models;
	@Shadow @Final private BuiltinModelItemRenderer builtinModelItemRenderer;

	@Shadow protected abstract void renderBakedItemModel(BakedModel model, ItemStack stack, int light, int overlay, MatrixStack matrices, VertexConsumer vertices);

	@Inject(method="renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformation$Mode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V", at=@At("HEAD"), cancellable=true)
	private void RenderJavelin(ItemStack stack, ModelTransformation.Mode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, BakedModel model, CallbackInfo ci) {
		if (!(stack.getItem() instanceof JavelinItem item)) return;
		matrices.push();
		boolean bl = renderMode == ModelTransformation.Mode.GUI || renderMode == ModelTransformation.Mode.GROUND || renderMode == ModelTransformation.Mode.FIXED;
		if (bl) model = this.models.getModelManager().getModel(new ModelIdentifier(Registry.ITEM.getId(item) + "#inventory"));
		model.getTransformation().getTransformation(renderMode).apply(leftHanded, matrices);
		matrices.translate(-0.5, -0.5, -0.5);
		if (model.isBuiltin() || !bl) this.builtinModelItemRenderer.render(stack, renderMode, matrices, vertexConsumers, light, overlay);
		else {
			VertexConsumer vertexConsumer;
			boolean bl22 = renderMode != ModelTransformation.Mode.GUI && !renderMode.isFirstPerson();
			RenderLayer renderLayer = RenderLayers.getItemLayer(stack, bl22);
			vertexConsumer = bl22 ? ItemRenderer.getDirectItemGlintConsumer(vertexConsumers, renderLayer, true, stack.hasGlint()) : ItemRenderer.getItemGlintConsumer(vertexConsumers, renderLayer, true, stack.hasGlint());
			this.renderBakedItemModel(model, stack, light, overlay, matrices, vertexConsumer);
		}
		matrices.pop();
		ci.cancel();
	}

	@Inject(method="getHeldItemModel", at=@At("HEAD"), cancellable=true)
	private void GetJavelinModel(ItemStack stack, World world, LivingEntity entity, int seed, CallbackInfoReturnable<BakedModel> cir) {
		if (!(stack.getItem() instanceof JavelinItem)) return;
		BakedModel bakedModel = this.models.getModelManager().getModel(new ModelIdentifier("minecraft:trident_in_hand#inventory"));
		ClientWorld clientWorld = world instanceof ClientWorld ? (ClientWorld)world : null;
		BakedModel bakedModel2 = bakedModel.getOverrides().apply(bakedModel, stack, clientWorld, entity, seed);
		cir.setReturnValue(bakedModel2 == null ? this.models.getModelManager().getMissingModel() : bakedModel2);
	}
}
