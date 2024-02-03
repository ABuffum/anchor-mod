package fun.wich.origins.power;

import fun.wich.ModId;
import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.LivingEntity;

import java.util.Optional;

public class SneakSpeedPower extends Power {
	public final float multiplier;
	public SneakSpeedPower(PowerType<?> type, LivingEntity entity, float multiplier) {
		super(type, entity);
		this.multiplier = Math.max(0, multiplier);
	}

	public static PowerFactory<SneakSpeedPower> createFactory() {
		return new PowerFactory<SneakSpeedPower>(ModId.ID("sneak_speed"), new SerializableData()
				.add("multiplier", SerializableDataTypes.FLOAT, 0.3F),
				data -> (type, player) -> new SneakSpeedPower(type, player, data.getFloat("multiplier"))
		).allowCondition();
	}

	public static Optional<Float> getSpeedMultiplier(LivingEntity entity) {
		float max = Float.NaN;
		for (SneakSpeedPower power : PowerHolderComponent.getPowers(entity, SneakSpeedPower.class)) {
			if (power.isActive()) max = Float.isNaN(max) ? power.multiplier : Math.max(max, power.multiplier);
		}
		return Float.isNaN(max) ? Optional.empty() : Optional.of(max);
	}
}