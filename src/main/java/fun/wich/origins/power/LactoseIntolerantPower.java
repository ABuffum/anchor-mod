package fun.wich.origins.power;

import fun.wich.ModId;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.LivingEntity;

public class LactoseIntolerantPower extends Power {
	public final int hungerDuration;
	public final int hungerAmplifier;
	public final int damage;
	public LactoseIntolerantPower(PowerType<?> type, LivingEntity entity, int hungerDuration, int hungerAmplifier, int damage) {
		super(type, entity);
		this.hungerDuration = hungerDuration;
		this.hungerAmplifier = hungerAmplifier;
		this.damage = damage;
	}

	public static PowerFactory<LactoseIntolerantPower> createFactory() {
		return new PowerFactory<LactoseIntolerantPower>(ModId.ID("lactose_intolerant"), new SerializableData()
				.add("hunger_duration", SerializableDataTypes.INT, 400)
				.add("hunger_amplifier", SerializableDataTypes.INT, 0)
				.add("damage", SerializableDataTypes.INT, 0),
			data -> (type, player) -> new LactoseIntolerantPower(type, player,
					data.getInt("hunger_duration"),
					data.getInt("hunger_amplifier"),
					data.getInt("damage"))
		).allowCondition();
	}
}
