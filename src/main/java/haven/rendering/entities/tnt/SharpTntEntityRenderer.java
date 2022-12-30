package haven.rendering.entities.tnt;

import haven.ModBase;
import haven.entities.tnt.SharpTntEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;

@Environment(EnvType.CLIENT)
public class SharpTntEntityRenderer extends ModTntEntityRenderer<SharpTntEntity> {
	public SharpTntEntityRenderer(EntityRendererFactory.Context context) {
		super(context, ModBase.ID("textures/entity/tnt/sharp.png"));
	}
}
