package fun.mousewich.client.render.entity.renderer.feature;

import fun.mousewich.client.render.entity.model.SlimeCreeperEntityModel;
import fun.mousewich.entity.hostile.SlimeCreeperEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.feature.EnergySwirlOverlayFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.util.Identifier;

@Environment(value= EnvType.CLIENT)
public class SlimeCreeperChargeFeatureRenderer extends EnergySwirlOverlayFeatureRenderer<SlimeCreeperEntity, SlimeCreeperEntityModel<SlimeCreeperEntity>> {
	private static final Identifier SKIN = new Identifier("textures/entity/creeper/creeper_armor.png");
	private final SlimeCreeperEntityModel<SlimeCreeperEntity> model;

	public SlimeCreeperChargeFeatureRenderer(FeatureRendererContext<SlimeCreeperEntity, SlimeCreeperEntityModel<SlimeCreeperEntity>> context, EntityModelLoader loader) {
		super(context);
		this.model = new SlimeCreeperEntityModel<>(loader.getModelPart(EntityModelLayers.CREEPER_ARMOR));
	}
	@Override
	protected float getEnergySwirlX(float partialAge) { return partialAge * 0.01f; }
	@Override
	protected Identifier getEnergySwirlTexture() { return SKIN; }
	@Override
	protected EntityModel<SlimeCreeperEntity> getEnergySwirlModel() { return this.model; }
}