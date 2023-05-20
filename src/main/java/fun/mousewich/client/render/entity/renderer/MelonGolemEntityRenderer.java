package fun.mousewich.client.render.entity.renderer;

import fun.mousewich.ModBase;
import fun.mousewich.client.render.entity.renderer.feature.MelonGolemMelonFeature;
import fun.mousewich.entity.neutral.golem.MelonGolemEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.SnowGolemEntityModel;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class MelonGolemEntityRenderer extends MobEntityRenderer<MelonGolemEntity, SnowGolemEntityModel<MelonGolemEntity>> {
	private static final Identifier TEXTURE = ModBase.ID("textures/entity/melon_golem.png");

	public MelonGolemEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new SnowGolemEntityModel<>(context.getPart(EntityModelLayers.SNOW_GOLEM)), 0.5F);
		this.addFeature(new MelonGolemMelonFeature(this));
	}

	public Identifier getTexture(MelonGolemEntity snowGolemEntity) {
		return TEXTURE;
	}
}