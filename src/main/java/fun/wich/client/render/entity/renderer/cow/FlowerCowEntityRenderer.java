package fun.wich.client.render.entity.renderer.cow;

import fun.wich.client.render.entity.renderer.feature.cow.CowFlowerFeatureRenderer;
import fun.wich.entity.passive.cow.FlowerCowEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.CowEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public abstract class FlowerCowEntityRenderer<T extends FlowerCowEntity> extends MobEntityRenderer<T, CowEntityModel<T>> {
	public FlowerCowEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new CowEntityModel<>(context.getPart(EntityModelLayers.MOOSHROOM)), 0.7F);
		this.addFeature(new CowFlowerFeatureRenderer<>(this));
	}
	public abstract Identifier getTexture(T entity);
}

