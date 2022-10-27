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

public class ScareHedgehogsPower extends Power {
	public ScareHedgehogsPower(PowerType<?> type, LivingEntity entity) {
		super(type, entity);
	}

	public static PowerFactory createFactory() {
		return new PowerFactory<>(HavenMod.ID("scare_hedgehogs"), new SerializableData(),
				data -> (type, player) -> new ScareHedgehogsPower(type, player)).allowCondition();
	}

	public static boolean HasActivePower(Entity entity) {
		List<ScareHedgehogsPower> powers = PowerHolderComponent.KEY.get(entity).getPowers(ScareHedgehogsPower.class);
		for (ScareHedgehogsPower power : powers) {
			if (power.isActive()) return true;
		}
		return false;
	}
}
