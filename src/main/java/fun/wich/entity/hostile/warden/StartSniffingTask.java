package fun.wich.entity.hostile.warden;

import com.google.common.collect.ImmutableMap;
import fun.wich.entity.ModEntityPose;
import fun.wich.entity.ai.ModMemoryModules;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Unit;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;

public class StartSniffingTask extends Task<WardenEntity> {
	private static final IntProvider COOLDOWN = UniformIntProvider.create(100, 200);

	public StartSniffingTask() {
		super(ImmutableMap.of(ModMemoryModules.SNIFF_COOLDOWN, MemoryModuleState.VALUE_ABSENT, MemoryModuleType.NEAREST_ATTACKABLE, MemoryModuleState.VALUE_PRESENT, ModMemoryModules.DISTURBANCE_LOCATION, MemoryModuleState.VALUE_ABSENT));
	}

	@Override
	protected void run(ServerWorld serverWorld, WardenEntity wardenEntity, long l) {
		Brain<WardenEntity> brain = wardenEntity.getBrain();
		brain.remember(ModMemoryModules.IS_SNIFFING, Unit.INSTANCE);
		brain.remember(ModMemoryModules.SNIFF_COOLDOWN, Unit.INSTANCE, COOLDOWN.get(serverWorld.getRandom()));
		brain.forget(MemoryModuleType.WALK_TARGET);
		wardenEntity.SetPose(ModEntityPose.SNIFFING);
	}
}
