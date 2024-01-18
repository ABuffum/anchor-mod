package fun.mousewich.client.render.entity.renderer.feature;

import fun.mousewich.ModId;
import fun.mousewich.entity.hostile.RedPhantomEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.feature.PhantomEyesFeatureRenderer;
import net.minecraft.client.render.entity.model.PhantomEntityModel;

public class RedPhantomEyesFeatureRenderer extends PhantomEyesFeatureRenderer<RedPhantomEntity> {
	private static final RenderLayer SKIN = RenderLayer.getEyes(ModId.ID("textures/entity/phantom/red_eyes.png"));
	public RedPhantomEyesFeatureRenderer(FeatureRendererContext<RedPhantomEntity, PhantomEntityModel<RedPhantomEntity>> featureRendererContext) {
		super(featureRendererContext);
	}
	@Override
	public RenderLayer getEyesTexture() { return SKIN; }
}
