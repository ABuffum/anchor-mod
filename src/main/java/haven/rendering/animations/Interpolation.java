package haven.rendering.animations;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.math.Vec3f;

@Environment(value= EnvType.CLIENT)
public interface Interpolation {
	public Vec3f apply(Vec3f var1, float var2, Keyframe[] var3, int var4, int var5, float var6);
}
