package fun.wich.client.render.entity.renderer.cow;

import fun.wich.ModId;
import fun.wich.entity.passive.cow.MoolipEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class MoolipEntityRenderer extends FlowerCowEntityRenderer<MoolipEntity> {
	private static final Identifier TEXTURE = ModId.ID("textures/entity/cow/moolip.png");
	public MoolipEntityRenderer(EntityRendererFactory.Context context) { super(context); }
	@Override public Identifier getTexture(MoolipEntity entity) { return TEXTURE; }
}

