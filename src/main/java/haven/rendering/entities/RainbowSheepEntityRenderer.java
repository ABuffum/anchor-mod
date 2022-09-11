package haven.rendering.entities;

import haven.HavenMod;
import haven.entities.RainbowSheepEntity;
import haven.rendering.features.RainbowSheepWoolFeatureRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.SheepEntityModel;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class RainbowSheepEntityRenderer extends MobEntityRenderer<RainbowSheepEntity, SheepEntityModel<RainbowSheepEntity>> {
	public static final Identifier TEXTURE = HavenMod.ID("textures/entity/sheep/rainbow_sheep.png");

	public RainbowSheepEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new SheepEntityModel(context.getPart(EntityModelLayers.SHEEP)), 0.7F);
		this.addFeature(new RainbowSheepWoolFeatureRenderer(this, context.getModelLoader()));
	}

	@Override
	public Identifier getTexture(RainbowSheepEntity entity) {
		return TEXTURE;
	}
}