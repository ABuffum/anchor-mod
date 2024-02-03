package fun.wich.mixins.entity.ai;

import net.minecraft.entity.ai.brain.Activity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Activity.class)
public interface ActivityInvoker {
	@Invoker("register")
	static Activity Register(String id) { return null; }
}
