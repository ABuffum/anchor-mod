package fun.mousewich.client.render.entity.renderer.spider;

import fun.mousewich.ModBase;
import fun.mousewich.client.render.entity.ModEntityModelLayers;
import fun.mousewich.client.render.entity.model.SlimeSpiderEntityModel;
import fun.mousewich.client.render.entity.renderer.feature.SlimeSpiderEyesFeatureRenderer;
import fun.mousewich.client.render.entity.renderer.feature.SlimeSpiderOverlayFeatureRenderer;
import fun.mousewich.entity.hostile.spider.SlimeSpiderEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

@Environment(value= EnvType.CLIENT)
public class SlimeSpiderEntityRenderer extends MobEntityRenderer<SlimeSpiderEntity, SlimeSpiderEntityModel> {
	private static final Identifier TEXTURE = ModBase.ID("textures/entity/spider/slime_spider.png");
	public SlimeSpiderEntityRenderer(EntityRendererFactory.Context context) { this(context, ModEntityModelLayers.SLIME_SPIDER); }
	public SlimeSpiderEntityRenderer(EntityRendererFactory.Context ctx, EntityModelLayer layer) {
		super(ctx, new SlimeSpiderEntityModel(ctx.getPart(layer)), 0.8f);
		this.addFeature(new SlimeSpiderOverlayFeatureRenderer(this, ctx.getModelLoader()));
		this.addFeature(new SlimeSpiderEyesFeatureRenderer(this));
	}
	@Override
	protected float getLyingAngle(SlimeSpiderEntity entity) { return 180.0f; }
	@Override
	public Identifier getTexture(SlimeSpiderEntity entity) { return TEXTURE; }
}
