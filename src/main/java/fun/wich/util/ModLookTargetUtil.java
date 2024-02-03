package fun.wich.util;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.LookTarget;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.WalkTarget;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;

public class ModLookTargetUtil {
	public static void give(LivingEntity entity, ItemStack stack, Vec3d targetLocation) {
		give(entity, stack, targetLocation, new Vec3d(0.3f, 0.3f, 0.3f), 0.3f);
	}
	public static void give(LivingEntity entity, ItemStack stack, Vec3d targetLocation, Vec3d velocityFactor, float yOffset) {
		double d = entity.getEyeY() - (double)yOffset;
		ItemEntity itemEntity = new ItemEntity(entity.world, entity.getX(), d, entity.getZ(), stack);
		itemEntity.setThrower(entity.getUuid());
		Vec3d vec3d = targetLocation.subtract(entity.getPos());
		vec3d = vec3d.normalize().multiply(velocityFactor.x, velocityFactor.y, velocityFactor.z);
		itemEntity.setVelocity(vec3d);
		itemEntity.setToDefaultPickupDelay();
		entity.world.spawnEntity(itemEntity);
	}

	public static void walkTowards(LivingEntity entity, LookTarget target, float speed, int completionRange) {
		WalkTarget walkTarget = new WalkTarget(target, speed, completionRange);
		entity.getBrain().remember(MemoryModuleType.LOOK_TARGET, target);
		entity.getBrain().remember(MemoryModuleType.WALK_TARGET, walkTarget);
	}
}
