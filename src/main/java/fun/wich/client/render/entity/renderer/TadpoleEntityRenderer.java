package fun.wich.client.render.entity.renderer;

import fun.wich.client.render.entity.ModEntityModelLayers;
import fun.wich.client.render.entity.model.TadpoleEntityModel;
import fun.wich.entity.passive.frog.TadpoleEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(value= EnvType.CLIENT)
public class TadpoleEntityRenderer extends MobEntityRenderer<TadpoleEntity, TadpoleEntityModel<TadpoleEntity>> {
	private static final Identifier TEXTURE = new Identifier("textures/entity/tadpole/tadpole.png");
	public TadpoleEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new TadpoleEntityModel<>(context.getPart(ModEntityModelLayers.TADPOLE)), 0.14f);
	}
	@Override
	public Identifier getTexture(TadpoleEntity tadpoleEntity) { return TEXTURE; }
}
