package fun.wich.mixins.entity.passive;

import net.minecraft.entity.passive.HorseBaseEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(HorseBaseEntity.class)
public interface HorseBaseEntityInvoker {
	@Invoker("playEatingAnimation")
	void InvokePlayEatingAnimation();
}
