package fun.mousewich.client.render.entity.renderer;

import fun.mousewich.ModId;
import fun.mousewich.client.render.entity.ModEntityModelLayers;
import fun.mousewich.client.render.entity.model.RaccoonEntityModel;
import fun.mousewich.entity.passive.RaccoonEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class RaccoonEntityRenderer extends MobEntityRenderer<RaccoonEntity, RaccoonEntityModel> {
	private static final Identifier TEXTURE = ModId.ID("textures/entity/raccoon.png");
	public RaccoonEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new RaccoonEntityModel(context.getPart(ModEntityModelLayers.RACCOON)), 0.3F);
	}
	public Identifier getTexture(RaccoonEntity entity) { return TEXTURE; }
}
