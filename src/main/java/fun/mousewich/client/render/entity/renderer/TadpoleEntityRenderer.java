package fun.mousewich.client.render.entity.renderer;

import fun.mousewich.ModClient;
import fun.mousewich.client.render.entity.model.TadpoleEntityModel;
import fun.mousewich.entity.passive.frog.TadpoleEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(value= EnvType.CLIENT)
public class TadpoleEntityRenderer extends MobEntityRenderer<TadpoleEntity, TadpoleEntityModel<TadpoleEntity>> {
	private static final Identifier TEXTURE = new Identifier("textures/entity/tadpole/tadpole.png");
	public TadpoleEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new TadpoleEntityModel<>(context.getPart(ModClient.TADPOLE_MODEL_LAYER)), 0.14f);
	}
	@Override
	public Identifier getTexture(TadpoleEntity tadpoleEntity) { return TEXTURE; }
}
