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

public class ClownPacifistPower extends Power {
	public ClownPacifistPower(PowerType<?> type, LivingEntity entity) {
		super(type, entity);
	}

	public static PowerFactory createFactory() {
		return new PowerFactory<>(HavenMod.ID("clown_pacifist"), new SerializableData(),
				data -> (type, player) -> new ClownPacifistPower(type, player)).allowCondition();
	}

	public static boolean HasActivePower(Entity entity) {
		List<ClownPacifistPower> powers = PowerHolderComponent.KEY.get(entity).getPowers(ClownPacifistPower.class);
		if (powers.isEmpty()) return false;
		for (ClownPacifistPower power : powers) {
			if (power.isActive()) return true;
		}
		return false;
	}
}
