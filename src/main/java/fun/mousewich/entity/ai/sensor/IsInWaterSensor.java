package fun.mousewich.entity.ai.sensor;

import com.google.common.collect.ImmutableSet;
import fun.mousewich.entity.ai.ModMemoryModules;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Unit;

import java.util.Set;

public class IsInWaterSensor extends Sensor<LivingEntity> {
	@Override
	public Set<MemoryModuleType<?>> getOutputMemoryModules() { return ImmutableSet.of(ModMemoryModules.IS_IN_WATER); }
	@Override
	protected void sense(ServerWorld world, LivingEntity entity) {
		if (entity.isTouchingWater()) entity.getBrain().remember(ModMemoryModules.IS_IN_WATER, Unit.INSTANCE);
		else entity.getBrain().forget(ModMemoryModules.IS_IN_WATER);
	}
}
