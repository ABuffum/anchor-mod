package haven.origins.powers;

import haven.ModBase;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import net.minecraft.entity.LivingEntity;

public class AttackedByFoxesPower extends Power {
	public AttackedByFoxesPower(PowerType<?> type, LivingEntity entity) {
		super(type, entity);
	}

	public static PowerFactory createFactory() {
		return new PowerFactory<>(ModBase.ID("attacked_by_foxes"), new SerializableData(),
				data -> (type, player) -> new AttackedByFoxesPower(type, player)).allowCondition();
	}
}
