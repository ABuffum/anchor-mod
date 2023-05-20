package fun.mousewich.client.render.entity.renderer;

import fun.mousewich.entity.projectile.ModArrowEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(value= EnvType.CLIENT)
public class ModArrowEntityRenderer extends ProjectileEntityRenderer<ModArrowEntity> {
	public ModArrowEntityRenderer(EntityRendererFactory.Context context) { super(context); }
	@Override
	public Identifier getTexture(ModArrowEntity entity) { return entity.getTexture(); }
}