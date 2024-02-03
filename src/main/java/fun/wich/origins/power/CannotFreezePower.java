package fun.wich.origins.power;

import fun.wich.ModId;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import net.minecraft.entity.LivingEntity;

public class CannotFreezePower extends Power {
	public CannotFreezePower(PowerType<?> type, LivingEntity entity) { super(type, entity); }

	public static PowerFactory<?> createFactory() {
		return new PowerFactory<>(ModId.ID("cannot_freeze"), new SerializableData(), data -> CannotFreezePower::new).allowCondition();
	}
}
