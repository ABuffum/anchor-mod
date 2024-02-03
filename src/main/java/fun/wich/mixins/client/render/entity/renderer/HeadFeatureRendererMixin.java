package fun.wich.mixins.client.render.entity.renderer;

import fun.wich.block.piglin.PiglinHeadParent;
import fun.wich.client.render.block.model.PiglinHeadEntityModel;
import fun.wich.client.render.block.renderer.PiglinHeadEntityRenderer;
import net.minecraft.block.Block;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.feature.HeadFeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.entity.model.ModelWithHead;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.ZombieVillagerEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HeadFeatureRenderer.class)
public abstract class HeadFeatureRendererMixin<T extends LivingEntity, M extends EntityModel<T>> extends FeatureRenderer<T, M> {
	public HeadFeatureRendererMixin(FeatureRendererContext<T, M> context) { super(context); }
	@Shadow @Final private float scaleX;
	@Shadow @Final private float scaleY;
	@Shadow @Final private float scaleZ;
	private PiglinHeadEntityModel piglinHeadModel = null;

	@Inject(at = @At("TAIL"), method="<init>(Lnet/minecraft/client/render/entity/feature/FeatureRendererContext;Lnet/minecraft/client/render/entity/model/EntityModelLoader;FFF)V")
	private void constructorInject(FeatureRendererContext<T, M> context, EntityModelLoader loader, float scaleX, float scaleY, float scaleZ, CallbackInfo ci) {
		piglinHeadModel = PiglinHeadEntityRenderer.getModel(loader);
	}

	@Inject(at = @At("HEAD"), cancellable = true, method="render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/LivingEntity;FFFFFF)V")
	public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T livingEntity, float f, float g, float h, float j, float k, float l, CallbackInfo ci) {
		ItemStack itemStack = livingEntity.getEquippedStack(EquipmentSlot.HEAD);
		if (itemStack.isEmpty()) return;
		if (itemStack.getItem() instanceof BlockItem blockItem) {
			matrixStack.push();
			matrixStack.scale(this.scaleX, this.scaleY, this.scaleZ);
			boolean bl = livingEntity instanceof VillagerEntity || livingEntity instanceof ZombieVillagerEntity;
			if (livingEntity.isBaby() && !(livingEntity instanceof VillagerEntity)) {
				matrixStack.translate(0.0f, 0.03125f, 0.0f);
				matrixStack.scale(0.7f, 0.7f, 0.7f);
				matrixStack.translate(0.0f, 1.0f, 0.0f);
			}
			((ModelWithHead)this.getContextModel()).getHead().rotate(matrixStack);
			Block block = blockItem.getBlock();
			if (block instanceof PiglinHeadParent head) {
				matrixStack.scale(1.1875f, -1.1875f, -1.1875f);
				if (bl) matrixStack.translate(0.0f, 0.0625f, 0.0f);
				matrixStack.translate(-0.5, 0.0, -0.5);
				PiglinHeadEntityRenderer.renderSkull(null, 180.0f, f, matrixStack, vertexConsumerProvider, i, piglinHeadModel, head.isZombified());
				ci.cancel();
			}
			matrixStack.pop();
		}
	}
}
