package haven.rendering.features;

import haven.HavenMod;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.feature.EyesFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;

@Environment(EnvType.CLIENT)
public class WardenEntityOverlayFeatureRenderer <T extends LivingEntity, M extends EntityModel<T>> extends EyesFeatureRenderer<T, EntityModel<T>> {
	private static final RenderLayer SKIN = RenderLayer.getEyes(HavenMod.ID("textures/entity/warden/warden_overlay.png"));

	public WardenEntityOverlayFeatureRenderer(FeatureRendererContext<T, M> featureRendererContext) {
		super((FeatureRendererContext<T, EntityModel<T>>) featureRendererContext);
	}

	public RenderLayer getEyesTexture() {
		return SKIN;
	}
}
