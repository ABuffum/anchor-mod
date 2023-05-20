package fun.mousewich.entity.hostile.warden;

import com.google.common.collect.ImmutableMap;
import fun.mousewich.entity.ai.ModMemoryModules;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.brain.BlockPosLookTarget;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public class LookAtDisturbanceTask extends Task<WardenEntity> {
	public LookAtDisturbanceTask() {
		super(ImmutableMap.of(ModMemoryModules.DISTURBANCE_LOCATION, MemoryModuleState.REGISTERED, ModMemoryModules.ROAR_TARGET, MemoryModuleState.REGISTERED, MemoryModuleType.ATTACK_TARGET, MemoryModuleState.VALUE_ABSENT));
	}
	@Override
	protected boolean shouldRun(ServerWorld serverWorld, WardenEntity wardenEntity) {
		return wardenEntity.getBrain().hasMemoryModule(ModMemoryModules.DISTURBANCE_LOCATION) || wardenEntity.getBrain().hasMemoryModule(ModMemoryModules.ROAR_TARGET);
	}
	@Override
	protected void run(ServerWorld serverWorld, WardenEntity wardenEntity, long l) {
		BlockPos blockPos = wardenEntity.getBrain().getOptionalMemory(ModMemoryModules.ROAR_TARGET).map(Entity::getBlockPos).or(() -> wardenEntity.getBrain().getOptionalMemory(ModMemoryModules.DISTURBANCE_LOCATION)).get();
		wardenEntity.getBrain().remember(MemoryModuleType.LOOK_TARGET, new BlockPosLookTarget(blockPos));
	}
}
