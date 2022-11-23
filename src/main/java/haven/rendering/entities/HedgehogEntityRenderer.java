package haven.rendering.entities;

import haven.ModBase;
import haven.ModClient;
import haven.entities.passive.HedgehogEntity;
import haven.rendering.models.HedgehogEntityModel;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class HedgehogEntityRenderer extends MobEntityRenderer<HedgehogEntity, HedgehogEntityModel> {
	private static final Identifier TEXTURE = ModBase.ID("textures/entity/hedgehog/hedgehog.png");
	private static final Identifier SONIC_TEXTURE = ModBase.ID("textures/entity/hedgehog/sonic.png");
	private static final Identifier SHADOW_TEXTURE = ModBase.ID("textures/entity/hedgehog/shadow.png");

	public HedgehogEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new HedgehogEntityModel(context.getPart(ModClient.HEDGEHOG_ENTITY_MODEL_LAYER)), 0.3F);
	}

	public Identifier getTexture(HedgehogEntity entity) {
		String name = entity.getName().getString();
		if (name.equals("Sonic")) return SONIC_TEXTURE;
		else if (name.equals("Shadow")) return SHADOW_TEXTURE;
		return TEXTURE;
	}
}
