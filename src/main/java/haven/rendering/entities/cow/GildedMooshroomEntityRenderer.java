package haven.rendering.entities.cow;

import haven.ModBase;
import haven.entities.passive.cow.GildedMooshroomEntity;
import haven.rendering.features.cow.GildedMooshroomMushroomFeatureRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.CowEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class GildedMooshroomEntityRenderer extends MobEntityRenderer<GildedMooshroomEntity, CowEntityModel<GildedMooshroomEntity>> {
	private static final Identifier TEXTURE = ModBase.ID("textures/entity/cow/gilded_mooshroom.png");

	public GildedMooshroomEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new CowEntityModel(context.getPart(EntityModelLayers.MOOSHROOM)), 0.7F);
		this.addFeature(new GildedMooshroomMushroomFeatureRenderer(this));
	}

	public Identifier getTexture(GildedMooshroomEntity moobloomEntity) {
		return TEXTURE;
	}
}
