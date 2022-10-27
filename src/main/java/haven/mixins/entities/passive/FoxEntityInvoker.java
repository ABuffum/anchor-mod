package haven.mixins.entities.passive;

import net.minecraft.entity.passive.FoxEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(FoxEntity.class)
public interface FoxEntityInvoker {
	@Invoker("setWalking")
	public void invokeSetWalking(boolean value);
}
