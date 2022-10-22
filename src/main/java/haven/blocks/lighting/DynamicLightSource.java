package haven.blocks.lighting;

import net.minecraft.entity.Entity;

public interface DynamicLightSource {
	public Entity getEntity();
	public int getLightLevel();
}
