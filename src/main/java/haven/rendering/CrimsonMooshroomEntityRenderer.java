package haven.rendering;

import haven.HavenMod;
import haven.entities.CrimsonMooshroomEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.CowEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class CrimsonMooshroomEntityRenderer extends MobEntityRenderer<CrimsonMooshroomEntity, CowEntityModel<CrimsonMooshroomEntity>> {
	private static final Identifier TEXTURE = HavenMod.ID("textures/entity/cow/crimson_mooshroom.png");

	public CrimsonMooshroomEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new CowEntityModel(context.getPart(EntityModelLayers.MOOSHROOM)), 0.7F);
		this.addFeature(new CrimsonMooshroomMushroomFeatureRenderer(this));
	}

	public Identifier getTexture(CrimsonMooshroomEntity moobloomEntity) {
		return TEXTURE;
	}
}
