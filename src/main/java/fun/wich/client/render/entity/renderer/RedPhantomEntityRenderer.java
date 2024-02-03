package fun.wich.client.render.entity.renderer;

import fun.wich.ModId;
import fun.wich.client.render.entity.renderer.feature.RedPhantomEyesFeatureRenderer;
import fun.wich.entity.hostile.RedPhantomEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.PhantomEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;

@Environment(value= EnvType.CLIENT)
public class RedPhantomEntityRenderer extends MobEntityRenderer<RedPhantomEntity, PhantomEntityModel<RedPhantomEntity>> {
	private static final Identifier TEXTURE = ModId.ID("textures/entity/phantom/red.png");

	public RedPhantomEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new PhantomEntityModel<>(context.getPart(EntityModelLayers.PHANTOM)), 0.75f);
		this.addFeature(new RedPhantomEyesFeatureRenderer(this));
	}
	@Override
	public Identifier getTexture(RedPhantomEntity phantomEntity) { return TEXTURE; }
	@Override
	protected void scale(RedPhantomEntity phantomEntity, MatrixStack matrixStack, float f) {
		float g = 1.0f + 0.15f * (float)phantomEntity.getPhantomSize();
		matrixStack.scale(g, g, g);
		matrixStack.translate(0.0, 1.3125, 0.1875);
	}
	@Override
	protected void setupTransforms(RedPhantomEntity phantomEntity, MatrixStack matrixStack, float f, float g, float h) {
		super.setupTransforms(phantomEntity, matrixStack, f, g, h);
		matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(phantomEntity.getPitch()));
	}
}
