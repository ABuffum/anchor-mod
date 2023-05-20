package fun.mousewich.client.render.entity.renderer.cow;

import fun.mousewich.ModBase;
import fun.mousewich.entity.passive.cow.FlowerCowEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class MoobloomEntityRenderer extends FlowerCowEntityRenderer<FlowerCowEntity> {
	private static final Identifier TEXTURE = ModBase.ID("textures/entity/cow/moobloom.png");
	public MoobloomEntityRenderer(EntityRendererFactory.Context context) { super(context); }
	@Override public Identifier getTexture(FlowerCowEntity entity) { return TEXTURE; }
}

