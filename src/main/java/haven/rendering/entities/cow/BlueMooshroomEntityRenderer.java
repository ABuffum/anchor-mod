package haven.rendering.entities.cow;

import haven.ModBase;
import haven.entities.passive.cow.BlueMooshroomEntity;
import haven.rendering.features.cow.BlueMooshroomMushroomFeatureRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.CowEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class BlueMooshroomEntityRenderer extends MobEntityRenderer<BlueMooshroomEntity, CowEntityModel<BlueMooshroomEntity>> {
	private static final Identifier TEXTURE = ModBase.ID("textures/entity/cow/blue_mooshroom.png");

	public BlueMooshroomEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new CowEntityModel(context.getPart(EntityModelLayers.MOOSHROOM)), 0.7F);
		this.addFeature(new BlueMooshroomMushroomFeatureRenderer(this));
	}

	public Identifier getTexture(BlueMooshroomEntity moobloomEntity) {
		return TEXTURE;
	}
}
