package fun.mousewich.client.render.entity.renderer;

import fun.mousewich.client.render.entity.renderer.feature.SlimeHorseOverlayFeatureRenderer;
import fun.mousewich.entity.passive.SlimeHorseEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.HorseBaseEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.HorseEntityModel;
import net.minecraft.util.Identifier;

@Environment(value= EnvType.CLIENT)
public class SlimeHorseEntityRenderer extends HorseBaseEntityRenderer<SlimeHorseEntity, HorseEntityModel<SlimeHorseEntity>> {
	public static final Identifier TEXTURE = new Identifier("textures/entity/horse/horse_skeleton.png");

	public SlimeHorseEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new HorseEntityModel<>(context.getPart(EntityModelLayers.HORSE)), 1.1f);
		this.addFeature(new SlimeHorseOverlayFeatureRenderer(this, context.getModelLoader()));
	}

	@Override
	public Identifier getTexture(SlimeHorseEntity horseEntity) { return TEXTURE; }
}