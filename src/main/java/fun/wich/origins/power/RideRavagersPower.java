package fun.wich.origins.power;

import fun.wich.ModId;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import net.minecraft.entity.LivingEntity;

public class RideRavagersPower extends Power {
	public RideRavagersPower(PowerType<?> type, LivingEntity entity) { super(type, entity); }
	public static PowerFactory<RideRavagersPower> createFactory() {
		return new PowerFactory<RideRavagersPower>(ModId.ID("ride_ravagers"), new SerializableData(), data -> RideRavagersPower::new).allowCondition();
	}
}