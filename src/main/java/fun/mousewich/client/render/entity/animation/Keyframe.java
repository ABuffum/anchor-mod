package fun.mousewich.client.render.entity.animation;

import net.minecraft.util.math.Vec3f;

public class Keyframe {
	public float timestamp;
	public Vec3f target;
	public Transformation.Interpolation interpolation;

	public Keyframe(float timestamp, Vec3f target, Transformation.Interpolation interpolation) {
		this.timestamp = timestamp;
		this.target = target;
		this.interpolation = interpolation;
	}
}
