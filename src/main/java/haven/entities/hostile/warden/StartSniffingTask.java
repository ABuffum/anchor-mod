package haven.entities.hostile.warden;

import com.google.common.collect.ImmutableMap;
import haven.entities.Poses;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;

public class StartSniffingTask extends Task<WardenEntity> {
	private static final IntProvider COOLDOWN = UniformIntProvider.create(100, 200);

	public StartSniffingTask() {
		super(ImmutableMap.of(MemoryModuleType.NEAREST_ATTACKABLE, MemoryModuleState.VALUE_PRESENT));
	}

	@Override
	protected boolean shouldRun(ServerWorld world, WardenEntity entity) {
		return entity.getSniffingCooldown() <= 0 && entity.getDisturbanceLocation() == null;
	}

	@Override
	protected void run(ServerWorld serverWorld, WardenEntity entity, long l) {
		Brain<WardenEntity> brain = entity.getBrain();
		entity.setSniffing(true);
		entity.setSniffingCooldown(COOLDOWN.get(serverWorld.getRandom()));
		brain.forget(MemoryModuleType.WALK_TARGET);
		entity.SetPose(Poses.SNIFFING);
	}
}