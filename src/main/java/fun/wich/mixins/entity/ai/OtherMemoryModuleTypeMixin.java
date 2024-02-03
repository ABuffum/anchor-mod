package fun.wich.mixins.entity.ai;

import net.minecraft.entity.ai.brain.MemoryModuleType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(MemoryModuleType.class)
public interface OtherMemoryModuleTypeMixin {
	@Invoker("register")
	static <U> MemoryModuleType<U> Register(String id) { return null; }
}
