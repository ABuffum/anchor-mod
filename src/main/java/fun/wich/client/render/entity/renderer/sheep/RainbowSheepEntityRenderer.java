package fun.wich.client.render.entity.renderer.sheep;

import fun.wich.ModId;
import fun.wich.client.render.entity.ModEntityModelLayers;
import fun.wich.client.render.entity.model.RainbowSheepEntityModel;
import fun.wich.client.render.entity.renderer.feature.RainbowSheepWoolFeatureRenderer;
import fun.wich.entity.passive.sheep.RainbowSheepEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(value= EnvType.CLIENT)
public class RainbowSheepEntityRenderer extends MobEntityRenderer<RainbowSheepEntity, RainbowSheepEntityModel> {
	private static final Identifier TEXTURE = ModId.ID("textures/entity/sheep/rainbow_sheep.png");
	public RainbowSheepEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new RainbowSheepEntityModel(context.getPart(ModEntityModelLayers.RAINBOW_SHEEP)), 0.7f);
		this.addFeature(new RainbowSheepWoolFeatureRenderer(this, context.getModelLoader()));
	}
	@Override
	public Identifier getTexture(RainbowSheepEntity entity) { return TEXTURE; }
}
