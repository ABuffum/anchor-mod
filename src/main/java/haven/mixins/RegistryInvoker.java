package haven.mixins;

import com.mojang.serialization.Lifecycle;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Map;
import java.util.function.Supplier;

@Mixin(net.minecraft.util.registry.Registry.class)
public interface RegistryInvoker<T> {
	@Invoker("getEntryLifecycle")
	public Lifecycle InvokeGetEntryLifecycle(T entry);
}
