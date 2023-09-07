package fun.mousewich.client.render.entity.renderer.feature;

import fun.mousewich.ModBase;
import fun.mousewich.client.render.entity.model.SlimeSpiderEntityModel;
import fun.mousewich.entity.hostile.spider.SlimeSpiderEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.feature.EyesFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;

@Environment(value= EnvType.CLIENT)
public class SlimeSpiderEyesFeatureRenderer extends EyesFeatureRenderer<SlimeSpiderEntity, SlimeSpiderEntityModel> {
	private static final RenderLayer SKIN = RenderLayer.getEyes(ModBase.ID("textures/entity/spider/slime_spider_eyes.png"));
	public SlimeSpiderEyesFeatureRenderer(FeatureRendererContext<SlimeSpiderEntity, SlimeSpiderEntityModel> featureRendererContext) {
		super(featureRendererContext);
	}
	@Override
	public RenderLayer getEyesTexture() { return SKIN; }
}