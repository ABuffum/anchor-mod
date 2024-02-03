package fun.wich.client.render.entity.renderer.cow;

import fun.wich.ModId;
import fun.wich.entity.passive.cow.FlowerCowEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class MoobloomEntityRenderer extends FlowerCowEntityRenderer<FlowerCowEntity> {
	private static final Identifier TEXTURE = ModId.ID("textures/entity/cow/moobloom.png");
	public MoobloomEntityRenderer(EntityRendererFactory.Context context) { super(context); }
	@Override public Identifier getTexture(FlowerCowEntity entity) { return TEXTURE; }
}

