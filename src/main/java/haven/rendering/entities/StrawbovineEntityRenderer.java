package haven.rendering.entities;

import haven.HavenMod;
import haven.entities.passive.cow.StrawbovineEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.CowEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.util.Identifier;

public class StrawbovineEntityRenderer extends MobEntityRenderer<StrawbovineEntity, CowEntityModel<StrawbovineEntity>> {
	private static final Identifier TEXTURE = HavenMod.ID("textures/entity/cow/strawbovine.png");

	public StrawbovineEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new CowEntityModel(context.getPart(EntityModelLayers.COW)), 0.7F);
	}

	public Identifier getTexture(StrawbovineEntity cowEntity) {
		return TEXTURE;
	}
}
