package haven.rendering.entities;

import haven.ModBase;
import haven.entities.passive.cow.WarpedMooshroomEntity;
import haven.rendering.features.cow.WarpedMooshroomMushroomFeatureRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.CowEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class WarpedMooshroomEntityRenderer extends MobEntityRenderer<WarpedMooshroomEntity, CowEntityModel<WarpedMooshroomEntity>> {
	private static final Identifier TEXTURE = ModBase.ID("textures/entity/cow/warped_mooshroom.png");

	public WarpedMooshroomEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new CowEntityModel(context.getPart(EntityModelLayers.MOOSHROOM)), 0.7F);
		this.addFeature(new WarpedMooshroomMushroomFeatureRenderer(this));
	}

	public Identifier getTexture(WarpedMooshroomEntity moobloomEntity) {
		return TEXTURE;
	}
}
