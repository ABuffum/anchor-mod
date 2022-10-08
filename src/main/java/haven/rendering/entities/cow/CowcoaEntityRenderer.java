package haven.rendering.entities.cow;

import haven.HavenMod;
import haven.entities.passive.cow.CowcoaEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.CowEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.util.Identifier;

public class CowcoaEntityRenderer extends MobEntityRenderer<CowcoaEntity, CowEntityModel<CowcoaEntity>> {
	private static final Identifier TEXTURE = HavenMod.ID("textures/entity/cow/cowcoa.png");

	public CowcoaEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new CowEntityModel(context.getPart(EntityModelLayers.COW)), 0.7F);
	}

	public Identifier getTexture(CowcoaEntity cowEntity) {
		return TEXTURE;
	}
}
