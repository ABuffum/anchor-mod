package fun.wich.client.render.entity.renderer.skeleton;

import fun.wich.ModId;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.SkeletonEntityRenderer;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.util.Identifier;

@Environment(value= EnvType.CLIENT)
public class MossySkeletonEntityRenderer extends SkeletonEntityRenderer {
	private static final Identifier TEXTURE = ModId.ID("textures/entity/skeleton/mossy.png");

	public MossySkeletonEntityRenderer(EntityRendererFactory.Context context) { super(context); }

	@Override
	public Identifier getTexture(AbstractSkeletonEntity abstractSkeletonEntity) { return TEXTURE; }
}
