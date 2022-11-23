package haven.rendering.entities.cow;

import haven.ModBase;
import haven.entities.passive.cow.MoobloomEntity;
import haven.rendering.features.cow.MoobloomFlowerFeatureRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.CowEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class MoobloomEntityRenderer extends MobEntityRenderer<MoobloomEntity, CowEntityModel<MoobloomEntity>> {
	private static final Identifier TEXTURE = ModBase.ID("textures/entity/cow/moobloom.png");

	public MoobloomEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new CowEntityModel(context.getPart(EntityModelLayers.MOOSHROOM)), 0.7F);
		this.addFeature(new MoobloomFlowerFeatureRenderer(this));
	}

	public Identifier getTexture(MoobloomEntity moobloomEntity) {
		return TEXTURE;
	}
}
