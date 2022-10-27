package haven.origins.powers;

import haven.HavenMod;
import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;

import java.util.List;

public class ScareRedPandasPower extends Power {
	public ScareRedPandasPower(PowerType<?> type, LivingEntity entity) {
		super(type, entity);
	}

	public static PowerFactory createFactory() {
		return new PowerFactory<>(HavenMod.ID("scare_red_pandas"), new SerializableData(),
				data -> (type, player) -> new ScareRedPandasPower(type, player)).allowCondition();
	}

	public static boolean HasActivePower(Entity entity) {
		List<ScareRedPandasPower> powers = PowerHolderComponent.KEY.get(entity).getPowers(ScareRedPandasPower.class);
		for (ScareRedPandasPower power : powers) {
			if (power.isActive()) return true;
		}
		return false;
	}
}
