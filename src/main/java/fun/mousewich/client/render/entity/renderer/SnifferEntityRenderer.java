package fun.mousewich.client.render.entity.renderer;

import fun.mousewich.client.render.entity.ModEntityModelLayers;
import fun.mousewich.client.render.entity.model.SnifferEntityModel;
import fun.mousewich.entity.passive.sniffer.SnifferEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(value= EnvType.CLIENT)
public class SnifferEntityRenderer extends MobEntityRenderer<SnifferEntity, SnifferEntityModel<SnifferEntity>> {
	private static final Identifier TEXTURE = new Identifier("textures/entity/sniffer/sniffer.png");
	public SnifferEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new SnifferEntityModel<>(context.getPart(ModEntityModelLayers.SNIFFER)), 1.1f);
	}
	@Override
	public Identifier getTexture(SnifferEntity snifferEntity) { return TEXTURE; }
}
