package haven.rendering.entities;

import haven.HavenMod;
import haven.entities.MoobloomEntity;
import haven.rendering.features.MoobloomFlowerFeatureRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.CowEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class MoobloomEntityRenderer extends MobEntityRenderer<MoobloomEntity, CowEntityModel<MoobloomEntity>> {
	private static final Identifier TEXTURE = HavenMod.ID("textures/entity/cow/moobloom.png");

	public MoobloomEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new CowEntityModel(context.getPart(EntityModelLayers.MOOSHROOM)), 0.7F);
		this.addFeature(new MoobloomFlowerFeatureRenderer(this));
	}

	public Identifier getTexture(MoobloomEntity moobloomEntity) {
		return TEXTURE;
	}
}
