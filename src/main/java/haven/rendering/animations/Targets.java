package haven.rendering.animations;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.util.math.Vec3f;

@Environment(value= EnvType.CLIENT)
public class Targets {
	public static final Target TRANSLATE = Targets::translate;
	public static final Target ROTATE = Targets::rotate;

	public static void translate(ModelPart modelPart, Vec3f vec3f) {
		modelPart.pivotX += vec3f.getX();
		modelPart.pivotY += vec3f.getY();
		modelPart.pivotZ += vec3f.getZ();
	}
	public static void rotate(ModelPart modelPart, Vec3f vec3f) {
		modelPart.pitch += vec3f.getX();
		modelPart.yaw += vec3f.getY();
		modelPart.roll += vec3f.getZ();
	}
}