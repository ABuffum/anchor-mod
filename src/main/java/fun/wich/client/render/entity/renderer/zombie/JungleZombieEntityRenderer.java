package fun.wich.client.render.entity.renderer.zombie;

import fun.wich.ModId;
import fun.wich.entity.hostile.zombie.JungleZombieEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.util.Identifier;

public class JungleZombieEntityRenderer extends LayeredZombieEntityRenderer<JungleZombieEntity> {
	private static final Identifier TEXTURE = ModId.ID("textures/entity/zombie/jungle.png");
	private static final Identifier SKIN = ModId.ID("textures/entity/zombie/jungle_outer_layer.png");
	public JungleZombieEntityRenderer(EntityRendererFactory.Context context) { super(context, SKIN); }
	@Override
	public Identifier getTexture(ZombieEntity zombieEntity) { return TEXTURE; }
}
