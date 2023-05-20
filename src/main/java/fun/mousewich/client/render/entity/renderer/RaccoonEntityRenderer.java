package fun.mousewich.client.render.entity.renderer;

import fun.mousewich.ModBase;
import fun.mousewich.ModClient;
import fun.mousewich.client.render.entity.model.RaccoonEntityModel;
import fun.mousewich.entity.passive.RaccoonEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class RaccoonEntityRenderer extends MobEntityRenderer<RaccoonEntity, RaccoonEntityModel> {
	private static final Identifier TEXTURE = ModBase.ID("textures/entity/raccoon.png");
	public RaccoonEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new RaccoonEntityModel(context.getPart(ModClient.RACCOON_MODEL_LAYER)), 0.3F);
	}
	public Identifier getTexture(RaccoonEntity entity) { return TEXTURE; }
}
