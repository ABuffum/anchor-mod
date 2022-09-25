package haven.rendering.entities;

import haven.HavenMod;
import haven.entities.passive.cow.RedMooblossomEntity;
import haven.rendering.features.RedMooblossomFlowerFeatureRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.CowEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class RedMooblossomEntityRenderer extends MobEntityRenderer<RedMooblossomEntity, CowEntityModel<RedMooblossomEntity>> {
	private static final Identifier TEXTURE = HavenMod.ID("textures/entity/cow/red_mooblossom.png");

	public RedMooblossomEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new CowEntityModel(context.getPart(EntityModelLayers.MOOSHROOM)), 0.7F);
		this.addFeature(new RedMooblossomFlowerFeatureRenderer(this));
	}

	public Identifier getTexture(RedMooblossomEntity mooblossomEntity) {
		return TEXTURE;
	}
}
