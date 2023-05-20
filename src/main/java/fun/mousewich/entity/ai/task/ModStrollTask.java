package fun.mousewich.entity.ai.task;

import net.minecraft.entity.ai.NoPenaltySolidTargeting;
import net.minecraft.entity.ai.brain.task.StrollTask;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.math.Vec3d;

public class ModStrollTask extends StrollTask {
	public ModStrollTask(float f) { this(f, true); }
	public ModStrollTask(float f, boolean bl) { super(f, bl); }
	protected Vec3d findWalkTarget(PathAwareEntity entity) {
		Vec3d vec3d = entity.getRotationVec(0.0F);
		return NoPenaltySolidTargeting.find(entity, this.horizontalRadius, this.verticalRadius, -2, vec3d.x, vec3d.z, 1.5707963705062866D);
	}
}
