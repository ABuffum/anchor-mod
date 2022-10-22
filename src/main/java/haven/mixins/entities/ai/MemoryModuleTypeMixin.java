package haven.mixins.entities.ai;

import com.mojang.serialization.Codec;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(MemoryModuleType.class)
public interface MemoryModuleTypeMixin {
	@Invoker("register")
	public static <U> MemoryModuleType<U> Register(String id, Codec<U> codec) { return null; }
}
