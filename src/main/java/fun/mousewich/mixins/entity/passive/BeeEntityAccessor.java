package fun.mousewich.mixins.entity.passive;

import net.minecraft.entity.passive.BeeEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(BeeEntity.class)
public interface BeeEntityAccessor {
	@Invoker("setHasNectar")
	void InvokeSetHasNectar(boolean value);
	@Accessor("ticksUntilCanPollinate")
	int GetTicksUntilCanPollinate();
	@Accessor("ticksUntilCanPollinate")
	void SetTicksUntilCanPollinate(int value);
	@Accessor("pollinateGoal")
	BeeEntity.PollinateGoal GetPollinateGoal();
}
