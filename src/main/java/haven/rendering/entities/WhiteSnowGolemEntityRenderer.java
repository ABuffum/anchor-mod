package haven.rendering.entities;

import haven.entities.passive.WhiteSnowGolemEntity;
import haven.rendering.features.WhiteSnowGolemPumpkinFeature;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.SnowGolemEntityModel;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class WhiteSnowGolemEntityRenderer extends MobEntityRenderer<WhiteSnowGolemEntity, SnowGolemEntityModel<WhiteSnowGolemEntity>> {
	private static final Identifier TEXTURE = new Identifier("textures/entity/snow_golem.png");

	public WhiteSnowGolemEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new SnowGolemEntityModel(context.getPart(EntityModelLayers.SNOW_GOLEM)), 0.5F);
		this.addFeature(new WhiteSnowGolemPumpkinFeature(this));
	}

	public Identifier getTexture(WhiteSnowGolemEntity snowGolemEntity) {
		return TEXTURE;
	}
}