package fun.wich.client.render.entity.renderer;

import fun.wich.ModId;
import fun.wich.client.render.entity.ModEntityModelLayers;
import fun.wich.client.render.entity.model.RaccoonEntityModel;
import fun.wich.entity.passive.RaccoonEntity;
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
