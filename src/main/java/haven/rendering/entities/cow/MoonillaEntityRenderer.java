package haven.rendering.entities.cow;

import haven.HavenMod;
import haven.entities.passive.cow.MoonillaEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.CowEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.util.Identifier;

public class MoonillaEntityRenderer extends MobEntityRenderer<MoonillaEntity, CowEntityModel<MoonillaEntity>> {
	private static final Identifier TEXTURE = HavenMod.ID("textures/entity/cow/moonilla.png");

	public MoonillaEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new CowEntityModel(context.getPart(EntityModelLayers.COW)), 0.7F);
	}

	public Identifier getTexture(MoonillaEntity cowEntity) {
		return TEXTURE;
	}
}
