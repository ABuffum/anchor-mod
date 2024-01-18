package fun.mousewich.client.render.entity.renderer.slime;

import fun.mousewich.ModId;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.SlimeEntityRenderer;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.util.Identifier;

public class PinkSlimeEntityRenderer extends SlimeEntityRenderer {
	private static final Identifier TEXTURE = ModId.ID("textures/entity/slime/pink.png");
	public PinkSlimeEntityRenderer(EntityRendererFactory.Context context) { super(context); }
	@Override
	public Identifier getTexture(SlimeEntity slimeEntity) { return TEXTURE; }
}
