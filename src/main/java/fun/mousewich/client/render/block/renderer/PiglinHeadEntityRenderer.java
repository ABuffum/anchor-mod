package fun.mousewich.client.render.block.renderer;

import fun.mousewich.ModClient;
import fun.mousewich.block.piglin.PiglinHeadEntity;
import fun.mousewich.block.piglin.WallPiglinHeadBlock;
import fun.mousewich.client.render.block.model.PiglinHeadEntityModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;

@Environment(value= EnvType.CLIENT)
public class PiglinHeadEntityRenderer implements BlockEntityRenderer<PiglinHeadEntity> {
	private final PiglinHeadEntityModel MODEL;
	public static PiglinHeadEntityModel getModel(EntityModelLoader modelLoader) {
		return new PiglinHeadEntityModel(modelLoader.getModelPart(ModClient.PIGLIN_HEAD_LAYER));
	}
	private static final Identifier TEXTURE = new Identifier("textures/entity/piglin/piglin.png");
	private static final Identifier TEXTURE_ZOMBIFIED = new Identifier("textures/entity/piglin/zombified_piglin.png");
	public PiglinHeadEntityRenderer(BlockEntityRendererFactory.Context ctx) {
		this.MODEL = new PiglinHeadEntityModel(ctx.getLayerModelPart(ModClient.PIGLIN_HEAD_LAYER));
	}
	@Override
	public void render(PiglinHeadEntity blockEntity, float f, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, int j) {
		BlockState blockState = blockEntity.getCachedState();
		float yaw;
		Direction direction = null;
		if (blockState.getBlock() instanceof WallPiglinHeadBlock) {
			direction = blockState.get(Properties.HORIZONTAL_FACING);
			yaw = direction.getAxis().isVertical() ? 0 : direction.getOpposite().getHorizontal() * 90;
		}
		else yaw = blockState.get(Properties.ROTATION) * 22.5f;
		renderSkull(direction, yaw, blockEntity.getPoweredTicks(f), matrixStack, vertexConsumerProvider, i, this.MODEL, blockEntity.isZombified());
	}
	public static void renderSkull(@Nullable Direction direction, float yaw, float animationProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, PiglinHeadEntityModel model, boolean zombified) {
		matrices.push();
		if (direction == null) matrices.translate(0.5f, 0.0f, 0.5f);
		else matrices.translate(0.5f - (float)direction.getOffsetX() * 0.25f, 0.25f, 0.5f - (float)direction.getOffsetZ() * 0.25f);
		matrices.scale(-1.0f, -1.0f, 1.0f);
		VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityCutoutNoCullZOffset(zombified ? TEXTURE : TEXTURE_ZOMBIFIED));
		model.setHeadRotation(animationProgress, yaw, 0.0f);
		model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f);
		matrices.pop();
	}
}