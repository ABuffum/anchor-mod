package fun.mousewich.client.render.entity.renderer.spider;

import fun.mousewich.ModId;
import fun.mousewich.client.render.entity.ModEntityModelLayers;
import fun.mousewich.client.render.entity.renderer.feature.spider.IcySpiderEyesFeatureRenderer;
import fun.mousewich.entity.hostile.spider.IcySpiderEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.SpiderEntityModel;
import net.minecraft.util.Identifier;

@Environment(value= EnvType.CLIENT)
public class IcySpiderEntityRenderer extends MobEntityRenderer<IcySpiderEntity, SpiderEntityModel<IcySpiderEntity>> {
	private static final Identifier TEXTURE = ModId.ID("textures/entity/spider/icy_spider.png");
	public IcySpiderEntityRenderer(EntityRendererFactory.Context context) { this(context, ModEntityModelLayers.ICY_SPIDER); }
	public IcySpiderEntityRenderer(EntityRendererFactory.Context ctx, EntityModelLayer layer) {
		super(ctx, new SpiderEntityModel<>(ctx.getPart(layer)), 0.8f);
		this.addFeature(new IcySpiderEyesFeatureRenderer(this));
	}
	@Override
	protected float getLyingAngle(IcySpiderEntity entity) { return 180.0f; }
	@Override
	public Identifier getTexture(IcySpiderEntity entity) { return TEXTURE; }
}
