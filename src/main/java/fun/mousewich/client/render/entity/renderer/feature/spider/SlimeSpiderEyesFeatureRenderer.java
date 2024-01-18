package fun.mousewich.client.render.entity.renderer.feature.spider;

import fun.mousewich.ModId;
import fun.mousewich.client.render.entity.model.spider.SlimeSpiderEntityModel;
import fun.mousewich.entity.hostile.spider.SlimeSpiderEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.feature.EyesFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;

@Environment(value= EnvType.CLIENT)
public class SlimeSpiderEyesFeatureRenderer extends EyesFeatureRenderer<SlimeSpiderEntity, SlimeSpiderEntityModel> {
	private static final RenderLayer SKIN = RenderLayer.getEyes(ModId.ID("textures/entity/spider/slime_spider_eyes.png"));
	public SlimeSpiderEyesFeatureRenderer(FeatureRendererContext<SlimeSpiderEntity, SlimeSpiderEntityModel> featureRendererContext) {
		super(featureRendererContext);
	}
	@Override
	public RenderLayer getEyesTexture() { return SKIN; }
}