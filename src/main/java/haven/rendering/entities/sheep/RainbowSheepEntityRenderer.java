package haven.rendering.entities.sheep;

import haven.ModBase;
import haven.entities.passive.sheep.RainbowSheepEntity;
import haven.rendering.features.sheep.RainbowSheepWoolFeatureRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.SheepEntityModel;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class RainbowSheepEntityRenderer extends MobEntityRenderer<RainbowSheepEntity, SheepEntityModel<RainbowSheepEntity>> {
	public static final Identifier TEXTURE = ModBase.ID("textures/entity/sheep/rainbow_sheep.png");

	public RainbowSheepEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new SheepEntityModel(context.getPart(EntityModelLayers.SHEEP)), 0.7F);
		this.addFeature(new RainbowSheepWoolFeatureRenderer(this, context.getModelLoader()));
	}

	@Override
	public Identifier getTexture(RainbowSheepEntity entity) {
		return TEXTURE;
	}
}