package fun.mousewich.mixins.entity.ai;

import net.minecraft.entity.ai.brain.MemoryModuleType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(MemoryModuleType.class)
public interface MemoryModuleTypeInvoker {
	@Invoker("register")
	static <U> MemoryModuleType<U> RegisterID(String id) { return  null; }
}
