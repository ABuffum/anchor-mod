package haven.rendering.entities;

import haven.HavenMod;
import haven.HavenModClient;
import haven.entities.passive.RedPandaEntity;
import haven.rendering.models.RedPandaEntityModel;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class RedPandaEntityRenderer extends MobEntityRenderer<RedPandaEntity, RedPandaEntityModel> {
	private static final Identifier TEXTURE = HavenMod.ID("textures/entity/red_panda.png");

	public RedPandaEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new RedPandaEntityModel(context.getPart(HavenModClient.RED_PANDA_ENTITY_MODEL_LAYER)), 0.3F);
	}

	public Identifier getTexture(RedPandaEntity entity) { return TEXTURE; }
}
