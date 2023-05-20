package fun.mousewich.mixins.entity.passive;

import net.minecraft.entity.passive.FoxEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(FoxEntity.class)
public interface FoxEntityInvoker {
	@Invoker("setWalking")
	void invokeSetWalking(boolean value);
}
