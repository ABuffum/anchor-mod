package haven.rendering.entities.tnt;

import haven.ModBase;
import haven.entities.tnt.ChunkeaterTntEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;

@Environment(EnvType.CLIENT)
public class ChunkeaterTntEntityRenderer extends ModTntEntityRenderer<ChunkeaterTntEntity> {
	public ChunkeaterTntEntityRenderer(EntityRendererFactory.Context context) {
		super(context, ModBase.ID("textures/entity/tnt/chunkeater.png"));
	}
}
