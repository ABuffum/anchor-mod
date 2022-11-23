package haven.origins.powers;

import haven.ModBase;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import net.minecraft.entity.LivingEntity;

public class AttackedByHedgehogsPower extends Power {
	public AttackedByHedgehogsPower(PowerType<?> type, LivingEntity entity) {
		super(type, entity);
	}

	public static PowerFactory createFactory() {
		return new PowerFactory<>(ModBase.ID("attacked_by_hedgehogs"), new SerializableData(),
				data -> (type, player) -> new AttackedByHedgehogsPower(type, player)).allowCondition();
	}
}
