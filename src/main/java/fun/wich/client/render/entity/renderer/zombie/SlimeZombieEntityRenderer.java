package fun.wich.client.render.entity.renderer.zombie;

import fun.wich.ModId;
import fun.wich.client.render.entity.ModEntityModelLayers;
import fun.wich.client.render.entity.model.SlimeZombieEntityModel;
import fun.wich.client.render.entity.renderer.feature.SlimeZombieOverlayFeatureRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ZombieBaseEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;

public class SlimeZombieEntityRenderer<T extends ZombieEntity> extends ZombieBaseEntityRenderer<T, SlimeZombieEntityModel<T>> {
	public SlimeZombieEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new SlimeZombieEntityModel<>(context.getPart(ModEntityModelLayers.SLIME_ZOMBIE)),
				new SlimeZombieEntityModel<>(context.getPart(ModEntityModelLayers.SLIME_ZOMBIE_INNER_ARMOR)),
				new SlimeZombieEntityModel<>(context.getPart(ModEntityModelLayers.SLIME_ZOMBIE_OUTER_ARMOR)));
		this.addFeature(new SlimeZombieOverlayFeatureRenderer<>(this, context.getModelLoader(), ModEntityModelLayers.SLIME_ZOMBIE_OUTER, TEXTURE));
	}
	private static final Identifier TEXTURE = ModId.ID("textures/entity/zombie/slime.png");
	@Override
	public Identifier getTexture(ZombieEntity zombieEntity) { return TEXTURE; }

	@Override
	protected void setupTransforms(T entity, MatrixStack matrixStack, float f, float g, float h) {
		super.setupTransforms(entity, matrixStack, f, g, h);
		float i = entity.getLeaningPitch(h);
		if (i > 0) matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(MathHelper.lerp(i, entity.getPitch(), -10 - entity.getPitch())));
	}
}
