package fun.mousewich.client.render.entity.renderer.cow;

import fun.mousewich.ModBase;
import fun.mousewich.client.render.entity.renderer.feature.cow.BlueMooshroomMushroomFeatureRenderer;
import fun.mousewich.entity.passive.cow.BlueMooshroomEntity;
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
		super(context, new CowEntityModel<>(context.getPart(EntityModelLayers.MOOSHROOM)), 0.7F);
		this.addFeature(new BlueMooshroomMushroomFeatureRenderer<>(this));
	}

	public Identifier getTexture(BlueMooshroomEntity moobloomEntity) {
		return TEXTURE;
	}
}
