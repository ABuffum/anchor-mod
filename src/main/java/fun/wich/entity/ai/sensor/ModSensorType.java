package fun.wich.entity.ai.sensor;

import fun.wich.ModId;
import fun.wich.mixins.entity.ai.brain.sensor.SensorTypeInvoker;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.util.Identifier;

import java.util.function.Supplier;

public class ModSensorType <U extends Sensor<?>> {
	public final Identifier identifier;
	public final Supplier<U> factory;
	private SensorType<U> sensorType;
	public ModSensorType(String id, Supplier<U> factory) {
		this.identifier = ModId.ID(id);
		this.factory = factory;
	}
	public SensorType<U> get() {
		if (sensorType == null) Initialize();
		return sensorType;
	}
	public void Initialize() {
		if (sensorType != null) return;
		sensorType = SensorTypeInvoker.Register(identifier.toString(), factory);
	}
}
