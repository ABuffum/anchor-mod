package haven.rendering.entities;

import haven.HavenMod;
import haven.entities.passive.OrangeMooblossomEntity;
import haven.rendering.features.OrangeMooblossomFlowerFeatureRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.CowEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class OrangeMooblossomEntityRenderer extends MobEntityRenderer<OrangeMooblossomEntity, CowEntityModel<OrangeMooblossomEntity>> {
	private static final Identifier TEXTURE = HavenMod.ID("textures/entity/cow/orange_mooblossom.png");

	public OrangeMooblossomEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new CowEntityModel(context.getPart(EntityModelLayers.MOOSHROOM)), 0.7F);
		this.addFeature(new OrangeMooblossomFlowerFeatureRenderer(this));
	}

	public Identifier getTexture(OrangeMooblossomEntity mooblossomEntity) {
		return TEXTURE;
	}
}
