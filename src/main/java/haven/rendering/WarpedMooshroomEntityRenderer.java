package haven.rendering;

import haven.HavenMod;
import haven.entities.WarpedMooshroomEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.CowEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class WarpedMooshroomEntityRenderer extends MobEntityRenderer<WarpedMooshroomEntity, CowEntityModel<WarpedMooshroomEntity>> {
	private static final Identifier TEXTURE = HavenMod.ID("textures/entity/cow/warped_mooshroom.png");

	public WarpedMooshroomEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new CowEntityModel(context.getPart(EntityModelLayers.MOOSHROOM)), 0.7F);
		this.addFeature(new WarpedMooshroomMushroomFeatureRenderer(this));
	}

	public Identifier getTexture(WarpedMooshroomEntity moobloomEntity) {
		return TEXTURE;
	}
}
