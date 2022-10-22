package haven.mixins;

import com.mojang.serialization.Lifecycle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(net.minecraft.util.registry.Registry.class)
public interface RegistryInvoker<T> {
	@Invoker("getEntryLifecycle")
	public Lifecycle InvokeGetEntryLifecycle(T entry);
}
