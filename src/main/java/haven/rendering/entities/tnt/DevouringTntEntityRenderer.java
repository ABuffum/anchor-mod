package haven.rendering.entities.tnt;

import haven.ModBase;
import haven.entities.tnt.DevouringTntEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;

@Environment(EnvType.CLIENT)
public class DevouringTntEntityRenderer extends ModTntEntityRenderer<DevouringTntEntity> {
	public DevouringTntEntityRenderer(EntityRendererFactory.Context context) {
		super(context, ModBase.ID("textures/entity/tnt/devouring.png"));
	}
}
