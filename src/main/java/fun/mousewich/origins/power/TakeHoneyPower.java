package fun.mousewich.origins.power;

import fun.mousewich.ModBase;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import net.minecraft.entity.LivingEntity;

public class TakeHoneyPower extends Power {
	public TakeHoneyPower(PowerType<?> type, LivingEntity entity) { super(type, entity); }
	public static PowerFactory<TakeHoneyPower> createFactory() {
		return new PowerFactory<TakeHoneyPower>(ModBase.ID("take_honey"), new SerializableData(),
				data -> TakeHoneyPower::new).allowCondition();
	}
}