package fun.wich.client.render.entity.animation;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;

@Environment(value = EnvType.CLIENT)
public final class Transformation {
	public final Target target;
	public final Keyframe[] keyframes;

	public Transformation(Target target, Keyframe... keyframes) {
		this.target = target;
		this.keyframes = keyframes;
	}

	@Environment(value = EnvType.CLIENT)
	public interface Target { void apply(ModelPart var1, Vec3f var2); }

	@Environment(value = EnvType.CLIENT)
	public static class Interpolations {
		public static final Interpolation LINEAR = (vec3f, delta, keyframes, start, end, f) -> {
			Vec3f vec3f2 = keyframes[start].target;
			Vec3f vec3f3 = keyframes[end].target;
			vec3f.set(MathHelper.lerp(delta, vec3f2.getX(), vec3f3.getX()) * f, MathHelper.lerp(delta, vec3f2.getY(), vec3f3.getY()) * f, MathHelper.lerp(delta, vec3f2.getZ(), vec3f3.getZ()) * f);
			return vec3f;
		};
		public static final Interpolation CUBIC = (vec3f, delta, keyframes, start, end, f) -> {
			Vec3f vec3f2 = keyframes[Math.max(0, start - 1)].target;
			Vec3f vec3f3 = keyframes[start].target;
			Vec3f vec3f4 = keyframes[end].target;
			Vec3f vec3f5 = keyframes[Math.min(keyframes.length - 1, end + 1)].target;
			vec3f.set(catMullRom(delta, vec3f2.getX(), vec3f3.getX(), vec3f4.getX(), vec3f5.getX()) * f, catMullRom(delta, vec3f2.getY(), vec3f3.getY(), vec3f4.getY(), vec3f5.getY()) * f, catMullRom(delta, vec3f2.getZ(), vec3f3.getZ(), vec3f4.getZ(), vec3f5.getZ()) * f);
			return vec3f;
		};
	}
	public static float catMullRom(float f, float g, float h, float i, float j) {
		return 0.5f * (2.0f * h + (i - g) * f + (2.0f * g - 5.0f * h + 4.0f * i - j) * f * f + (3.0f * h - g - 3.0f * i + j) * f * f * f);
	}

	@Environment(value = EnvType.CLIENT)
	public static class Targets {
		public static final Target TRANSLATE = (part, vec3f) -> {
			part.pivotX += vec3f.getX();
			part.pivotY += vec3f.getY();
			part.pivotZ += vec3f.getZ();
		};
		public static final Target ROTATE = (part, vec3f) -> {
			part.pitch += vec3f.getX();
			part.yaw += vec3f.getY();
			part.roll += vec3f.getZ();
		};
		//public static final Target SCALE = (part, vec3f) -> {
		//  part.xScale += vec3f.getX();
		//  part.yScale += vec3f.getY();
		//  part.zScale += vec3f.getZ();
		//};
	}

	@Environment(value = EnvType.CLIENT)
	public interface Interpolation {
		Vec3f apply(Vec3f var1, float var2, Keyframe[] var3, int var4, int var5, float var6);
	}
}