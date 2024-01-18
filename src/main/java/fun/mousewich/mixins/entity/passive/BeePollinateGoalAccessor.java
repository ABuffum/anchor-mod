package fun.mousewich.mixins.entity.passive;

import net.minecraft.entity.passive.BeeEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(BeeEntity.PollinateGoal.class)
public interface BeePollinateGoalAccessor {
	@Invoker("isRunning")
	boolean InvokeIsRunning();
}