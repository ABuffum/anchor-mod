package fun.wich.client.render.entity.animation;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Environment(value= EnvType.CLIENT)
public class AnimationHelper {
	public static void animate(SinglePartEntityModel<?> model, Animation animation, long runningTime, float f, Vec3f vec3f) {
		float g = getRunningSeconds(animation, runningTime);
		for (Map.Entry<String, List<Transformation>> entry : animation.boneAnimations.entrySet()) {
			Optional<ModelPart> optional = getChild(model, entry.getKey());
			List<Transformation> list = entry.getValue();
			optional.ifPresent(part -> list.forEach(transformation -> {
				Keyframe[] keyframes = transformation.keyframes;
				int i = Math.max(0, MathHelper.binarySearch(0, keyframes.length, index -> g <= keyframes[index].timestamp) - 1);
				int j = Math.min(keyframes.length - 1, i + 1);
				Keyframe keyframe = keyframes[i];
				Keyframe keyframe2 = keyframes[j];
				float h = g - keyframe.timestamp;
				float k = MathHelper.clamp(h / (keyframe2.timestamp - keyframe.timestamp), 0.0f, 1.0f);
				keyframe2.interpolation.apply(vec3f, k, keyframes, i, j, f);
				transformation.target.apply(part, vec3f);
			}));
		}
	}
	private static boolean hasChild(ModelPart part, String child) {
		try {
			ModelPart modelPart = part.getChild(child);
			return modelPart != null;
		}
		catch (Exception e) { return false; }
	}
	private static Optional<ModelPart> getChild(SinglePartEntityModel<?> model, String name) {
		return model.getPart().traverse().filter(part -> hasChild(part, name)).findFirst().map(part -> part.getChild(name));
	}
	private static float getRunningSeconds(Animation animation, long runningTime) {
		float f = (float)runningTime / 1000.0f;
		return animation.looping ? f % animation.lengthInSeconds : f;
	}
	private static final float rad = (float)Math.PI / 180;
	public static Vec3f createTranslationalVector(float f, float g, float h) { return new Vec3f(f, -g, h); }
	public static Vec3f createRotationalVector(float f, float g, float h) { return new Vec3f(f * rad, g * rad, h * rad); }
	public static Vec3f createScalingVector(double d, double e, double f) { return new Vec3f((float)(d - 1.0), (float)(e - 1.0), (float)(f - 1.0)); }
}
