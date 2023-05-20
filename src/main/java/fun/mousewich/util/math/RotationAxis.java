package fun.mousewich.util.math;

import net.minecraft.util.math.Quaternion;

@FunctionalInterface
public interface RotationAxis {
	RotationAxis NEGATIVE_X = rad -> rotationX(-rad);
	RotationAxis POSITIVE_X = RotationAxis::rotationX;
	RotationAxis NEGATIVE_Y = rad -> rotationY(-rad);
	RotationAxis POSITIVE_Y = RotationAxis::rotationY;
	RotationAxis NEGATIVE_Z = rad -> rotationZ(-rad);
	RotationAxis POSITIVE_Z = RotationAxis::rotationZ;

	float PI_f = (float) Math.PI;
	float PI2_f = PI_f * 2.0f;
	float PIHalf_f = (float) (Math.PI * 0.5);
	private static float cosFromSinInternal(float sin, float angle) {
		// sin(x)^2 + cos(x)^2 = 1
		float cos = (float)Math.sqrt(1.0f - sin * sin);
		float a = angle + PIHalf_f;
		float b = a - (int)(a / PI2_f) * PI2_f;
		if (b < 0.0) b = PI2_f + b;
		if (b >= PI_f) return -cos;
		return cos;
	}

	private static Quaternion rotationX(float angle) {
		float sin = (float)Math.sin(angle * 0.5f);
		float cos = cosFromSinInternal(sin, angle * 0.5f);
		Quaternion quat = new Quaternion(0, 0, 0, -1f);
		quat.set(sin, 0, 0, cos);
		return quat;
	}
	private static Quaternion rotationY(float angle) {
		float sin = (float)Math.sin(angle * 0.5f);
		float cos = cosFromSinInternal(sin, angle * 0.5f);
		Quaternion quat = new Quaternion(0, 0, 0, -1f);
		quat.set(0, sin, 0, cos);
		return quat;
	}
	private static Quaternion rotationZ(float angle) {
		float sin = (float)Math.sin(angle * 0.5f);
		float cos = cosFromSinInternal(sin, angle * 0.5f);
		Quaternion quat = new Quaternion(0, 0, 0, -1f);
		quat.set(0, 0, sin, cos);
		return quat;
	}

	Quaternion rotation(float var1);

	default Quaternion rotationDegrees(float deg) { return this.rotation(deg * ((float)Math.PI / 180)); }
}
