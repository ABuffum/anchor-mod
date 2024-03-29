package fun.wich.origins.power;

import fun.wich.ModId;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import net.minecraft.entity.LivingEntity;

public class DrinkBloodPower extends Power {
	public DrinkBloodPower(PowerType<?> type, LivingEntity entity) { super(type, entity); }
	public static PowerFactory<DrinkBloodPower> createFactory() {
		return new PowerFactory<DrinkBloodPower>(ModId.ID("drink_blood"), new SerializableData(), data -> DrinkBloodPower::new).allowCondition();
	}
}