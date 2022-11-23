package haven.rendering.entities;

import haven.ModBase;
import haven.ModClient;
import haven.entities.passive.RaccoonEntity;
import haven.rendering.models.RaccoonEntityModel;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class RaccoonEntityRenderer extends MobEntityRenderer<RaccoonEntity, RaccoonEntityModel> {
	private static final Identifier TEXTURE = ModBase.ID("textures/entity/raccoon.png");

	public RaccoonEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new RaccoonEntityModel(context.getPart(ModClient.RACCOON_ENTITY_MODEL_LAYER)), 0.3F);
	}

	public Identifier getTexture(RaccoonEntity entity) { return TEXTURE; }
}
