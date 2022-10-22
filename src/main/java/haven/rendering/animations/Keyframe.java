package haven.rendering.animations;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.math.Vec3f;

@Environment(value= EnvType.CLIENT)
public record Keyframe(float timestamp, Vec3f target, Interpolation interpolation) { }
