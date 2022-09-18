package haven.mixins.entities;

import net.minecraft.entity.Entity;
import net.minecraft.sound.SoundEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Entity.class)
public interface EntityInvoker {
	@Invoker("getSplashSound")
	public SoundEvent getSplashSoundInvoker();
	@Invoker("getHighSpeedSplashSound")
	public SoundEvent getHighSpeedSplashSoundInvoker();
}
