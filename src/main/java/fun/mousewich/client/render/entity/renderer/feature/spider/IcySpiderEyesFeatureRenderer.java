package fun.mousewich.client.render.entity.renderer.feature.spider;

import fun.mousewich.ModId;
import fun.mousewich.entity.hostile.spider.IcySpiderEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.feature.EyesFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.SpiderEntityModel;

@Environment(value= EnvType.CLIENT)
public class IcySpiderEyesFeatureRenderer extends EyesFeatureRenderer<IcySpiderEntity, SpiderEntityModel<IcySpiderEntity>> {
	private static final RenderLayer SKIN = RenderLayer.getEyes(ModId.ID("textures/entity/spider/icy_spider_eyes.png"));
	public IcySpiderEyesFeatureRenderer(FeatureRendererContext<IcySpiderEntity, SpiderEntityModel<IcySpiderEntity>> featureRendererContext) {
		super(featureRendererContext);
	}
	@Override
	public RenderLayer getEyesTexture() { return SKIN; }
}