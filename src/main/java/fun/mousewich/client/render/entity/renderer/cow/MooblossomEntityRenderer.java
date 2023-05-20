package fun.mousewich.client.render.entity.renderer.cow;

import fun.mousewich.entity.passive.cow.MooblossomEntity;
import fun.mousewich.entity.variants.MooblossomVariant;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class MooblossomEntityRenderer extends FlowerCowEntityRenderer<MooblossomEntity> {
	public MooblossomEntityRenderer(EntityRendererFactory.Context context) { super(context); }
	@Override public Identifier getTexture(MooblossomEntity entity) { return MooblossomVariant.get(entity).texture; }
}

