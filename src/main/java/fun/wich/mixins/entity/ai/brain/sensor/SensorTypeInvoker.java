package fun.wich.mixins.entity.ai.brain.sensor;

import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.function.Supplier;

@Mixin(SensorType.class)
public interface SensorTypeInvoker {
	@Invoker("register")
	static <U extends Sensor<?>> SensorType<U> Register(String id, Supplier<U> factory) { return null; }
}
