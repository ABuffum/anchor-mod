package fun.wich.client.render.entity.renderer.zombie;

import fun.wich.client.render.entity.ModEntityModelLayers;
import fun.wich.client.render.entity.model.LayeredZombieEntityModel;
import fun.wich.client.render.entity.renderer.feature.LayeredZombieOverlayFeatureRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ZombieBaseEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;

public abstract class LayeredZombieEntityRenderer<T extends ZombieEntity> extends ZombieBaseEntityRenderer<T, LayeredZombieEntityModel<T>> {
	public LayeredZombieEntityRenderer(EntityRendererFactory.Context context, Identifier skin) {
		super(context, new LayeredZombieEntityModel<>(context.getPart(ModEntityModelLayers.LAYERED_ZOMBIE)),
				new LayeredZombieEntityModel<>(context.getPart(ModEntityModelLayers.LAYERED_ZOMBIE_INNER_ARMOR)),
				new LayeredZombieEntityModel<>(context.getPart(ModEntityModelLayers.LAYERED_ZOMBIE_OUTER_ARMOR)));
		this.addFeature(new LayeredZombieOverlayFeatureRenderer<>(this, context.getModelLoader(), ModEntityModelLayers.LAYERED_ZOMBIE_OUTER, skin));
	}

	@Override
	public abstract Identifier getTexture(ZombieEntity zombieEntity);

	@Override
	protected void setupTransforms(T entity, MatrixStack matrixStack, float f, float g, float h) {
		super.setupTransforms(entity, matrixStack, f, g, h);
		float i = entity.getLeaningPitch(h);
		if (i > 0) matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(MathHelper.lerp(i, entity.getPitch(), -10 - entity.getPitch())));
	}
}
