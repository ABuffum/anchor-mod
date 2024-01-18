package fun.mousewich.client.render.entity.renderer.zombie;

import fun.mousewich.ModId;
import fun.mousewich.entity.hostile.zombie.FrozenZombieEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.util.Identifier;

public class FrozenZombieEntityRenderer extends LayeredZombieEntityRenderer<FrozenZombieEntity> {
	private static final Identifier TEXTURE = ModId.ID("textures/entity/zombie/frozen.png");
	private static final Identifier SKIN = ModId.ID("textures/entity/zombie/frozen_outer_layer.png");
	public FrozenZombieEntityRenderer(EntityRendererFactory.Context context) { super(context, SKIN); }
	@Override
	public Identifier getTexture(ZombieEntity zombieEntity) { return TEXTURE; }
}
