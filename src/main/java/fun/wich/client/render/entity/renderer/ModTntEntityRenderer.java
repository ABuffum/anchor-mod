package fun.wich.client.render.entity.renderer;

import fun.wich.entity.tnt.ModTntEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.TntMinecartEntityRenderer;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;

@Environment(EnvType.CLIENT)
public class ModTntEntityRenderer<T extends ModTntEntity> extends EntityRenderer<T> {
	public ModTntEntityRenderer(EntityRendererFactory.Context context) {
		super(context);
		this.shadowRadius = 0.5f;
	}

	@Override
	public void render(T tntEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
		matrixStack.push();
		matrixStack.translate(0.0, 0.5, 0.0);
		int j = tntEntity.getFuse();
		if ((float)j - g + 1.0f < 10.0f) {
			float h = 1.0f - ((float)j - g + 1.0f) / 10.0f;
			h = MathHelper.clamp(h, 0.0f, 1.0f);
			h *= h;
			h *= h;
			float k = 1.0f + h * 0.3f;
			matrixStack.scale(k, k, k);
		}
		matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(-90.0f));
		matrixStack.translate(-0.5, -0.5, 0.5);
		matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(90.0f));
		TntMinecartEntityRenderer.renderFlashingBlock(tntEntity.getBlockState(), matrixStack, vertexConsumerProvider, i, j / 5 % 2 == 0);
		matrixStack.pop();
		super.render(tntEntity, f, g, matrixStack, vertexConsumerProvider, i);
	}
	@Override
	public Identifier getTexture(T tntEntity) { return SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE; }
}
