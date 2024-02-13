package fun.wich.client.render.entity.renderer;

import fun.wich.client.render.entity.ModEntityModelLayers;
import fun.wich.client.render.entity.model.MountaineerEntityModel;
import fun.wich.entity.hostile.illager.MountaineerEntity;
import fun.wich.entity.variants.MountaineerVariant;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.IllagerEntityRenderer;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.util.Identifier;

@Environment(value= EnvType.CLIENT)
public class MountaineerEntityRenderer extends IllagerEntityRenderer<MountaineerEntity> {
	public MountaineerEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new MountaineerEntityModel(context.getPart(ModEntityModelLayers.MOUNTAINEER)), 0.5f);
		this.addFeature(new HeldItemFeatureRenderer<>(this));
	}
	@Override
	public Identifier getTexture(MountaineerEntity entity) { return MountaineerVariant.get(entity).texture; }
}
