package fun.mousewich.client.render.entity.renderer.sheep;

import fun.mousewich.ModBase;
import fun.mousewich.client.render.entity.ModEntityModelLayers;
import fun.mousewich.client.render.entity.model.RainbowSheepEntityModel;
import fun.mousewich.client.render.entity.renderer.feature.RainbowSheepWoolFeatureRenderer;
import fun.mousewich.entity.passive.sheep.RainbowSheepEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(value= EnvType.CLIENT)
public class RainbowSheepEntityRenderer extends MobEntityRenderer<RainbowSheepEntity, RainbowSheepEntityModel> {
	private static final Identifier TEXTURE = ModBase.ID("textures/entity/sheep/rainbow_sheep.png");
	public RainbowSheepEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new RainbowSheepEntityModel(context.getPart(ModEntityModelLayers.RAINBOW_SHEEP)), 0.7f);
		this.addFeature(new RainbowSheepWoolFeatureRenderer(this, context.getModelLoader()));
	}
	@Override
	public Identifier getTexture(RainbowSheepEntity entity) { return TEXTURE; }
}
