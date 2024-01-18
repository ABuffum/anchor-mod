package fun.mousewich.client.render.entity.renderer;

import fun.mousewich.ModId;
import fun.mousewich.client.render.entity.ModEntityModelLayers;
import fun.mousewich.client.render.entity.model.RedPandaEntityModel;
import fun.mousewich.entity.passive.RedPandaEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class RedPandaEntityRenderer extends MobEntityRenderer<RedPandaEntity, RedPandaEntityModel> {
	private static final Identifier TEXTURE = ModId.ID("textures/entity/red_panda.png");
	public RedPandaEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new RedPandaEntityModel(context.getPart(ModEntityModelLayers.RED_PANDA)), 0.3F);
	}
	public Identifier getTexture(RedPandaEntity entity) { return TEXTURE; }
}
