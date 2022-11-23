package haven.origins.powers;

import haven.ModBase;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import net.minecraft.entity.LivingEntity;

public class ClownPacifistPower extends Power {
	public ClownPacifistPower(PowerType<?> type, LivingEntity entity) {
		super(type, entity);
	}

	public static PowerFactory createFactory() {
		return new PowerFactory<>(ModBase.ID("clown_pacifist"), new SerializableData(),
				data -> (type, player) -> new ClownPacifistPower(type, player)).allowCondition();
	}
}
