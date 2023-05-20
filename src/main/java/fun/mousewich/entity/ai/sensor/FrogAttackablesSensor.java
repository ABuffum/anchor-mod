package fun.mousewich.entity.ai.sensor;

import fun.mousewich.entity.ai.ModMemoryModules;
import fun.mousewich.entity.passive.frog.FrogEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.NearestVisibleLivingEntitySensor;
import net.minecraft.entity.ai.brain.sensor.Sensor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FrogAttackablesSensor extends NearestVisibleLivingEntitySensor {
	public static final float RANGE = 10.0f;
	@Override
	protected boolean matches(LivingEntity entity, LivingEntity target) {
		if (!entity.getBrain().hasMemoryModule(MemoryModuleType.HAS_HUNTING_COOLDOWN) && Sensor.testAttackableTargetPredicate(entity, target) && FrogEntity.isValidFrogFood(target) && !this.isTargetUnreachable(entity, target)) {
			return target.isInRange(entity, RANGE);
		}
		return false;
	}
	private boolean isTargetUnreachable(LivingEntity entity, LivingEntity target) {
		List<UUID> list = entity.getBrain().getOptionalMemory(ModMemoryModules.UNREACHABLE_TONGUE_TARGETS).orElseGet(ArrayList::new);
		return list.contains(target.getUuid());
	}
	@Override
	protected MemoryModuleType<LivingEntity> getOutputMemoryModule() { return MemoryModuleType.NEAREST_ATTACKABLE; }
}
