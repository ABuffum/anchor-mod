package fun.wich.client.render.entity.renderer.feature;

import fun.wich.ModBase;
import fun.wich.entity.neutral.golem.MelonGolemEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.SnowGolemEntityModel;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3f;

@Environment(EnvType.CLIENT)
public class MelonGolemMelonFeature extends FeatureRenderer<MelonGolemEntity, SnowGolemEntityModel<MelonGolemEntity>> {
	public MelonGolemMelonFeature(FeatureRendererContext<MelonGolemEntity, SnowGolemEntityModel<MelonGolemEntity>> featureRendererContext) { super(featureRendererContext); }
	public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, MelonGolemEntity melonGolemEntity, float f, float g, float h, float j, float k, float l) {
		if (melonGolemEntity.hasMelon()) {
			MinecraftClient minecraftClient = MinecraftClient.getInstance();
			boolean bl = minecraftClient.hasOutline(melonGolemEntity) && melonGolemEntity.isInvisible();
			if (!melonGolemEntity.isInvisible() || bl) {
				matrixStack.push();
				this.getContextModel().getHead().rotate(matrixStack);
				matrixStack.translate(0.0D, -0.34375D, 0.0D);
				matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180.0F));
				matrixStack.scale(0.625F, -0.625F, -0.625F);
				ItemStack itemStack = new ItemStack(ModBase.CARVED_MELON.asBlock());
				if (bl) {
					BlockState blockState = ModBase.CARVED_MELON.asBlock().getDefaultState();
					BlockRenderManager blockRenderManager = minecraftClient.getBlockRenderManager();
					BakedModel bakedModel = blockRenderManager.getModel(blockState);
					int n = LivingEntityRenderer.getOverlay(melonGolemEntity, 0.0F);
					matrixStack.translate(-0.5D, -0.5D, -0.5D);
					blockRenderManager.getModelRenderer().render(matrixStack.peek(), vertexConsumerProvider.getBuffer(RenderLayer.getOutline(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE)), blockState, bakedModel, 0.0F, 0.0F, 0.0F, i, n);
				}
				else {
					minecraftClient.getItemRenderer().renderItem(melonGolemEntity, itemStack, ModelTransformation.Mode.HEAD, false, matrixStack, vertexConsumerProvider, melonGolemEntity.world, i, LivingEntityRenderer.getOverlay(melonGolemEntity, 0.0F), melonGolemEntity.getId());
				}
				matrixStack.pop();
			}
		}
	}
}
