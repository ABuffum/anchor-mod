package haven.origins.powers;

import haven.HavenMod;
import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

import java.util.List;

public class UnfreezingPower extends Power {
	public UnfreezingPower(PowerType<?> type, LivingEntity entity) {
		super(type, entity);
	}

	public static PowerFactory createFactory() {
		return new PowerFactory<>(HavenMod.ID("unfreezing"), new SerializableData(),
				data -> (type, player) -> new UnfreezingPower(type, player)).allowCondition();
	}

	public static boolean HasActivePower(Entity entity) {
		List<UnfreezingPower> powers = PowerHolderComponent.KEY.get(entity).getPowers(UnfreezingPower.class);
		for (UnfreezingPower power : powers) {
			if (power.isActive()) return true;
		}
		return false;
	}
}
