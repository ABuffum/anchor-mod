package haven.origins.powers;

import haven.ModBase;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;

public class ScareRedPandasPower extends Power {
	public ScareRedPandasPower(PowerType<?> type, LivingEntity entity) {
		super(type, entity);
	}

	public static PowerFactory createFactory() {
		return new PowerFactory<>(ModBase.ID("scare_red_pandas"), new SerializableData(),
				data -> (type, player) -> new ScareRedPandasPower(type, player)).allowCondition();
	}

	public static boolean Active(Entity entity) { return PowersUtil.Active(entity, ScareRedPandasPower.class); }
}
