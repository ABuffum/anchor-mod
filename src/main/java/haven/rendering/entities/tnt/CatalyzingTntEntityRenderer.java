package haven.rendering.entities.tnt;

import haven.ModBase;
import haven.entities.tnt.CatalyzingTntEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;

@Environment(EnvType.CLIENT)
public class CatalyzingTntEntityRenderer extends ModTntEntityRenderer<CatalyzingTntEntity> {
	public CatalyzingTntEntityRenderer(EntityRendererFactory.Context context) {
		super(context, ModBase.ID("textures/entity/tnt/catalyzing.png"));
	}
}
