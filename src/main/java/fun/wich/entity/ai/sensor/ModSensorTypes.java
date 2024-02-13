package fun.wich.entity.ai.sensor;

import fun.wich.entity.passive.camel.CamelBrain;
import fun.wich.entity.passive.frog.FrogBrain;
import net.minecraft.entity.ai.brain.sensor.TemptationsSensor;

public class ModSensorTypes {
	//Camel
	public static final ModSensorType<TemptationsSensor> CAMEL_TEMPTATIONS_SENSOR = new ModSensorType<>("camel_temptations", () -> new TemptationsSensor(CamelBrain.getBreedingIngredient()));
	//Frog
	public static final ModSensorType<TemptationsSensor> FROG_TEMPTATIONS_SENSOR = new ModSensorType<>("frog_temptations", () -> new TemptationsSensor(FrogBrain.getTemptItems()));
	public static final ModSensorType<FrogAttackablesSensor> FROG_ATTACKABLES_SENSOR = new ModSensorType<>("frog_attackables", FrogAttackablesSensor::new);
	public static final ModSensorType<IsInWaterSensor> IS_IN_WATER_SENSOR = new ModSensorType<>("is_in_water", IsInWaterSensor::new);
	//Warden
	public static final ModSensorType<WardenAttackablesSensor> WARDEN_ENTITY_SENSOR = new ModSensorType<>("minecraft:warden_entity_sensor", WardenAttackablesSensor::new);

	public static void RegisterAll() {
		//Camels
		CAMEL_TEMPTATIONS_SENSOR.Initialize();
		//Frogs
		FROG_TEMPTATIONS_SENSOR.Initialize();
		FROG_ATTACKABLES_SENSOR.Initialize();
		IS_IN_WATER_SENSOR.Initialize();
		//Wardens
		WARDEN_ENTITY_SENSOR.Initialize();
	}
}
