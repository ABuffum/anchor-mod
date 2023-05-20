package fun.mousewich.client.render.entity.renderer.zombie;

import fun.mousewich.ModClient;
import fun.mousewich.client.render.entity.model.LayeredZombieEntityModel;
import fun.mousewich.client.render.entity.renderer.feature.LayeredZombieOverlayFeatureRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ZombieBaseEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;

public abstract class LayeredZombieEntityRenderer<T extends ZombieEntity> extends ZombieBaseEntityRenderer<T, LayeredZombieEntityModel<T>> {
	public LayeredZombieEntityRenderer(EntityRendererFactory.Context context, Identifier skin) {
		super(context, new LayeredZombieEntityModel<>(context.getPart(ModClient.LAYERED_ZOMBIE_LAYER)),
				new LayeredZombieEntityModel<>(context.getPart(ModClient.LAYERED_ZOMBIE_INNER_ARMOR_LAYER)),
				new LayeredZombieEntityModel<>(context.getPart(ModClient.LAYERED_ZOMBIE_OUTER_ARMOR_LAYER)));
		this.addFeature(new LayeredZombieOverlayFeatureRenderer<>(this, context.getModelLoader(), ModClient.LAYERED_ZOMBIE_LAYER, skin));
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
