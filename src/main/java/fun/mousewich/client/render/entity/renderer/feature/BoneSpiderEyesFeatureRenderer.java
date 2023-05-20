package fun.mousewich.client.render.entity.renderer.feature;

import fun.mousewich.ModBase;
import fun.mousewich.client.render.entity.model.BoneSpiderEntityModel;
import fun.mousewich.entity.hostile.spider.BoneSpiderEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.feature.EyesFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;

@Environment(value= EnvType.CLIENT)
public class BoneSpiderEyesFeatureRenderer extends EyesFeatureRenderer<BoneSpiderEntity, BoneSpiderEntityModel> {
	private static final RenderLayer SKIN = RenderLayer.getEyes(ModBase.ID("textures/entity/spider/bone_spider_eyes.png"));
	public BoneSpiderEyesFeatureRenderer(FeatureRendererContext<BoneSpiderEntity, BoneSpiderEntityModel> featureRendererContext) {
		super(featureRendererContext);
	}
	@Override
	public RenderLayer getEyesTexture() { return SKIN; }
}