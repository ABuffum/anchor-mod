package fun.mousewich.client.render.entity.renderer;

import fun.mousewich.ModId;
import fun.mousewich.client.render.entity.ModEntityModelLayers;
import fun.mousewich.client.render.entity.model.PiranhaEntityModel;
import fun.mousewich.entity.hostile.piranha.PiranhaEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;

@Environment(value= EnvType.CLIENT)
public class PiranhaEntityRenderer extends MobEntityRenderer<PiranhaEntity, PiranhaEntityModel> {
	private static final Identifier TEXTURE = ModId.ID("textures/entity/fish/piranha.png");

	public PiranhaEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new PiranhaEntityModel(context.getPart(ModEntityModelLayers.PIRANHA)), 0.3f);
	}

	@Override
	public Identifier getTexture(PiranhaEntity codEntity) { return TEXTURE; }

	@Override
	protected void setupTransforms(PiranhaEntity entity, MatrixStack matrixStack, float f, float g, float h) {
		super.setupTransforms(entity, matrixStack, f, g, h);
		float i = 4.3f * MathHelper.sin(0.6f * f);
		matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(i));
		if (!entity.isTouchingWater()) {
			matrixStack.translate(0.1f, 0.1f, -0.1f);
			matrixStack.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(90.0f));
		}
	}
}
