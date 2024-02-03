package fun.wich.util;

import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

public class VectorUtil {
	public static Vec3d withBias(Vec3d vec3d, Direction direction, double value) {
		Vec3i vec3i = direction.getVector();
		return new Vec3d(vec3d.x + value * (double)vec3i.getX(), vec3d.y + value * (double)vec3i.getY(), vec3d.z + value * (double)vec3i.getZ());
	}

	public static double distance(double x1, double y1, double z1, double x2, double y2, double z2) {
		double distancex;
		double distancey;
		double distancez;

		if(x1 > x2) {distancex = x1 - x2;} else {distancex = x2 -x1;}
		if(y1 > y2) {distancey = y1 - y2;} else {distancey = y2 -y1;}
		if(z1 > z2) {distancez = z1 - z2;} else {distancez = z2 -z1;}

		return Math.sqrt(Math.pow(distancex, 2) + Math.pow(distancey, 2) + Math.pow(distancez, 2));
	}
}
