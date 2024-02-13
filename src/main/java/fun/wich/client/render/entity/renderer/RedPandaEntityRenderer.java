package fun.wich.client.render.entity.renderer;

import fun.wich.ModId;
import fun.wich.client.render.entity.ModEntityModelLayers;
import fun.wich.client.render.entity.model.RedPandaEntityModel;
import fun.wich.entity.passive.RedPandaEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class RedPandaEntityRenderer extends MobEntityRenderer<RedPandaEntity, RedPandaEntityModel> {
	private static final Identifier TEXTURE = ModId.ID("textures/entity/red_panda.png");
	public RedPandaEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new RedPandaEntityModel(context.getPart(ModEntityModelLayers.RED_PANDA)), 0.3F);
	}
	public Identifier getTexture(RedPandaEntity entity) { return TEXTURE; }

	@Override
	public void render(RedPandaEntity mobEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
		if (mobEntity.getTarget() != null) {
			System.out.println(mobEntity.getTarget());
		}
		super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
	}
}
