package haven.rendering.entities;

import haven.HavenMod;
import haven.entities.tnt.SoftTntEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;

import java.util.Arrays;
import java.util.Collections;

@Environment(EnvType.CLIENT)
public class SoftTntEntityRenderer extends EntityRenderer<SoftTntEntity> {
	private final ModelPart base;
	public SoftTntEntityRenderer(EntityRendererFactory.Context context) {
		super(context);
		this.shadowRadius = 0.5F;
		this.base = new ModelPart(Arrays.asList(new ModelPart.Cuboid(0, 0, -8F, 16F, -8F, 16F, 16F, 16F, 0, 0, 0, false, 64, 64)), Collections.emptyMap());
	}

	public void render(SoftTntEntity tntEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
		matrixStack.push();
		// Fetch the appropriate texture
		Identifier textureId = getTexture();
		//layer = RenderLayer.getSolid();
		matrixStack.translate(0.0D, 2D, 0.0D);
		matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(180));
		this.base.render(matrixStack, vertexConsumerProvider.getBuffer(RenderLayer.getEntitySolid(textureId)), i, OverlayTexture.DEFAULT_UV, 1f, 1f, 1f, 1f);

		matrixStack.translate(0.0D, 0.5D, 0.0D);
		int j = tntEntity.getFuse();
		if ((float)j - g + 1.0F < 10.0F) {
			float h = 1.0F - ((float)j - g + 1.0F) / 10.0F;
			h = MathHelper.clamp(h, 0.0F, 1.0F);
			h *= h;
			h *= h;
			float k = 1.0F + h * 0.3F;
			matrixStack.scale(k, k, k);
		}
		matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(-90.0F));
		matrixStack.translate(-0.5D, -0.5D, 0.5D);
		matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(90.0F));
		matrixStack.pop();
		super.render(tntEntity, f, g, matrixStack, vertexConsumerProvider, i);
	}
	public Identifier getTexture() {
		return new Identifier(HavenMod.NAMESPACE, "textures/entity/soft_tnt.png");
	}
	@Override
	public Identifier getTexture(SoftTntEntity tntEntity) {
		return getTexture();
	}
}
