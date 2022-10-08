package haven.rendering.entities.cow;

import haven.HavenMod;
import haven.entities.passive.cow.PinkMooblossomEntity;
import haven.rendering.features.PinkMooblossomFlowerFeatureRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.CowEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class PinkMooblossomEntityRenderer extends MobEntityRenderer<PinkMooblossomEntity, CowEntityModel<PinkMooblossomEntity>> {
	private static final Identifier TEXTURE = HavenMod.ID("textures/entity/cow/pink_mooblossom.png");

	public PinkMooblossomEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new CowEntityModel(context.getPart(EntityModelLayers.MOOSHROOM)), 0.7F);
		this.addFeature(new PinkMooblossomFlowerFeatureRenderer(this));
	}

	public Identifier getTexture(PinkMooblossomEntity mooblossomEntity) {
		return TEXTURE;
	}
}
