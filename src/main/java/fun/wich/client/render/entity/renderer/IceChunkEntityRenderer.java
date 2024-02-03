package fun.wich.client.render.entity.renderer;

import fun.wich.ModId;
import fun.wich.client.render.entity.ModEntityModelLayers;
import fun.wich.client.render.entity.model.IceChunkEntityModel;
import fun.wich.entity.IceChunkEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(value= EnvType.CLIENT)
public class IceChunkEntityRenderer extends EntityRenderer<IceChunkEntity> {
	public static final Identifier TEXTURE = ModId.ID("textures/entity/ice_chunk.png");
	public final IceChunkEntityModel model;

	public IceChunkEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx);
		this.model = new IceChunkEntityModel(ctx.getPart(ModEntityModelLayers.ICE_CHUNK));
	}

	@Override
	public void render(IceChunkEntity entity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
		VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getEntitySolid(TEXTURE));
		this.model.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, 1f, 1f, 1f, 1f);
	}

	@Override
	public Identifier getTexture(IceChunkEntity entity) { return TEXTURE; }
}
