package haven.rendering.animations;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.util.math.Vec3f;

@Environment(value= EnvType.CLIENT)
public interface Target {
	public void apply(ModelPart var1, Vec3f var2);
}