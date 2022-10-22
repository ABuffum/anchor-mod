package haven.entities.hostile.warden;

import com.google.common.collect.ImmutableMap;
import haven.entities.Poses;
import haven.entities.ai.MemoryModules;
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
		super(ImmutableMap.of(MemoryModules.SNIFF_COOLDOWN, MemoryModuleState.VALUE_ABSENT, MemoryModuleType.NEAREST_ATTACKABLE, MemoryModuleState.VALUE_PRESENT, MemoryModules.DISTURBANCE_LOCATION, MemoryModuleState.VALUE_ABSENT));
	}

	@Override
	protected void run(ServerWorld serverWorld, WardenEntity wardenEntity, long l) {
		Brain<WardenEntity> brain = wardenEntity.getBrain();
		brain.remember(MemoryModules.IS_SNIFFING, Unit.INSTANCE);
		brain.remember(MemoryModules.SNIFF_COOLDOWN, Unit.INSTANCE, COOLDOWN.get(serverWorld.getRandom()));
		brain.forget(MemoryModuleType.WALK_TARGET);
		wardenEntity.SetPose(Poses.SNIFFING);
	}
}