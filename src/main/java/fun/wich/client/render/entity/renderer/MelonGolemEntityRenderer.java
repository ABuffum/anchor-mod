package fun.wich.client.render.entity.renderer;

import fun.wich.ModId;
import fun.wich.client.render.entity.renderer.feature.MelonGolemMelonFeature;
import fun.wich.entity.neutral.golem.MelonGolemEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.SnowGolemEntityModel;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class MelonGolemEntityRenderer extends MobEntityRenderer<MelonGolemEntity, SnowGolemEntityModel<MelonGolemEntity>> {
	private static final Identifier TEXTURE = ModId.ID("textures/entity/melon_golem.png");

	public MelonGolemEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new SnowGolemEntityModel<>(context.getPart(EntityModelLayers.SNOW_GOLEM)), 0.5F);
		this.addFeature(new MelonGolemMelonFeature(this));
	}

	public Identifier getTexture(MelonGolemEntity snowGolemEntity) {
		return TEXTURE;
	}
}