package fun.wich.client.render.entity.renderer;

import fun.wich.client.render.entity.ModEntityModelLayers;
import fun.wich.client.render.entity.model.CamelEntityModel;
import fun.wich.entity.passive.camel.CamelEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(value= EnvType.CLIENT)
public class CamelEntityRenderer extends MobEntityRenderer<CamelEntity, CamelEntityModel<CamelEntity>> {
	private static final Identifier TEXTURE = new Identifier("textures/entity/camel/camel.png");
	public CamelEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new CamelEntityModel<>(ctx.getPart(ModEntityModelLayers.CAMEL)), 0.7f);
	}
	@Override
	public Identifier getTexture(CamelEntity camelEntity) { return TEXTURE; }
}
