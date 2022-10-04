package haven.origins.powers;

import haven.HavenMod;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
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

	public static PowerFactory createFactory() {
		return new PowerFactory<>(HavenMod.ID("lactose_intolerant"), new SerializableData(),
			data -> (type, player) -> {
				int hungerDuration = data.isPresent("hungerDuration") ? (int)data.getDouble("hungerDuration") : 400;
				int hungerAmplifier = data.isPresent("hungerAmplifier") ? (int)data.getDouble("hungerAmplifier") : 1;
				int damage = data.isPresent("damage") ? (int)data.getDouble("damage") : 0;
				return new LactoseIntolerantPower(type, player, hungerDuration, hungerAmplifier, damage);
			}
		).allowCondition();
	}
}
