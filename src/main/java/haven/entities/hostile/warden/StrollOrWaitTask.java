package haven.entities.hostile.warden;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.CompositeTask;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.server.world.ServerWorld;

import java.util.List;
import java.util.Map;

public class StrollOrWaitTask extends CompositeTask<WardenEntity> {
	public StrollOrWaitTask(List<Pair<Task<? super WardenEntity>, Integer>> tasks) {
		this(ImmutableMap.of(), tasks);
	}

	public StrollOrWaitTask(Map<MemoryModuleType<?>, MemoryModuleState> requiredMemoryState, List<Pair<Task<? super WardenEntity>, Integer>> tasks) {
		super(requiredMemoryState, ImmutableSet.of(), CompositeTask.Order.SHUFFLED, CompositeTask.RunMode.RUN_ONE, tasks);
	}

	@Override
	protected boolean shouldRun(ServerWorld serverWorld, WardenEntity entity) {
		return !entity.isSniffing();
	}
}