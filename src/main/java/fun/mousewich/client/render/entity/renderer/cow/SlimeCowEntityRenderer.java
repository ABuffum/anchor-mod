package fun.mousewich.client.render.entity.renderer.cow;

import fun.mousewich.ModId;
import fun.mousewich.client.render.entity.ModEntityModelLayers;
import fun.mousewich.client.render.entity.model.SlimeCowEntityModel;
import fun.mousewich.client.render.entity.renderer.feature.cow.SlimeCowOverlayFeatureRenderer;
import fun.mousewich.entity.passive.cow.SlimeCowEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class SlimeCowEntityRenderer extends MobEntityRenderer<SlimeCowEntity, SlimeCowEntityModel> {
	private static final Identifier TEXTURE = ModId.ID("textures/entity/cow/slime.png");

	public SlimeCowEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new SlimeCowEntityModel(context.getPart(ModEntityModelLayers.SLIME_COW)), 0.7f);
		this.addFeature(new SlimeCowOverlayFeatureRenderer(this, context.getModelLoader()));
	}

	public Identifier getTexture(SlimeCowEntity entity) { return TEXTURE; }
}