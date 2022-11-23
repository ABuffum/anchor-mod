package haven.origins.powers;

import haven.ModBase;
import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.LivingEntity;

import java.util.Optional;

public class AttackedByAxolotlsPower extends Power {
	private boolean hostile;
	public AttackedByAxolotlsPower(PowerType<?> type, LivingEntity entity, boolean hostile) {
		super(type, entity);
		this.hostile = hostile;
	}

	public static PowerFactory createFactory() {
		return new PowerFactory<>(ModBase.ID("attacked_by_axolotls"), new SerializableData()
				.add("hostile", SerializableDataTypes.BOOLEAN, false),
				data -> (type, player) -> new AttackedByAxolotlsPower(type, player, data.getBoolean("hostile"))
		).allowCondition();
	}

	public static Optional<Boolean> getHostility(LivingEntity entity) {
		boolean hunted = false;
		for(AttackedByAxolotlsPower power : PowerHolderComponent.getPowers(entity, AttackedByAxolotlsPower.class)) {
			if (power.isActive()) {
				if (power.hostile) return Optional.of(true);
				else hunted = true;
			}
		}
		return hunted ? Optional.of(false) : Optional.empty();
	}
}
