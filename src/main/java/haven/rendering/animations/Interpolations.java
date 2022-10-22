package haven.rendering.animations;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;

@Environment(value= EnvType.CLIENT)
public class Interpolations {
	public static final Interpolation field_37884 = (vec3f, delta, keyframes, start, end, f) -> {
		Vec3f vec3f2 = keyframes[start].target();
		Vec3f vec3f3 = keyframes[end].target();
		vec3f.set(MathHelper.lerp(delta, vec3f2.getX(), vec3f3.getX()) * f, MathHelper.lerp(delta, vec3f2.getY(), vec3f3.getY()) * f, MathHelper.lerp(delta, vec3f2.getZ(), vec3f3.getZ()) * f);
		return vec3f;
	};
	public static final Interpolation field_37885 = (vec3f, delta, keyframes, start, end, f) -> {
		Vec3f vec3f2 = keyframes[Math.max(0, start - 1)].target();
		Vec3f vec3f3 = keyframes[start].target();
		Vec3f vec3f4 = keyframes[end].target();
		Vec3f vec3f5 = keyframes[Math.min(keyframes.length - 1, end + 1)].target();
		vec3f.set(method_41303(delta, vec3f2.getX(), vec3f3.getX(), vec3f4.getX(), vec3f5.getX()) * f, method_41303(delta, vec3f2.getY(), vec3f3.getY(), vec3f4.getY(), vec3f5.getY()) * f, method_41303(delta, vec3f2.getZ(), vec3f3.getZ(), vec3f4.getZ(), vec3f5.getZ()) * f);
		return vec3f;
	};

	public static float method_41303(float f, float g, float h, float i, float j) {
		return 0.5f * (2.0f * h + (i - g) * f + (2.0f * g - 5.0f * h + 4.0f * i - j) * f * f + (3.0f * h - g - 3.0f * i + j) * f * f * f);
	}
}