package fun.mousewich.client.render.entity.renderer.spider;

import fun.mousewich.ModBase;
import fun.mousewich.ModClient;
import fun.mousewich.entity.hostile.spider.JumpingSpiderEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.SpiderEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(value= EnvType.CLIENT)
public class JumpingSpiderEntityRenderer extends SpiderEntityRenderer<JumpingSpiderEntity> {
	private static final Identifier TEXTURE = ModBase.ID("textures/entity/spider/jumping_spider.png");
	private static final float SCALE = 0.7f;
	public JumpingSpiderEntityRenderer(EntityRendererFactory.Context context) {
		super(context, ModClient.JUMPING_SPIDER_MODEL_LAYER);
		this.shadowRadius *= 0.7f;
	}
	@Override
	protected void scale(JumpingSpiderEntity spiderEntity, MatrixStack matrixStack, float f) { matrixStack.scale(SCALE, SCALE, SCALE); }
	@Override
	public Identifier getTexture(JumpingSpiderEntity spiderEntity) { return TEXTURE; }
}