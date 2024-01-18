package fun.mousewich.origins.power;

import fun.mousewich.ModId;
import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;

public class BiggerLungsPower extends Power {
	public final int respiration; //Must be >= 1
	public BiggerLungsPower(PowerType<?> type, LivingEntity entity, int respiration) {
		super(type, entity);
		this.respiration = Math.max(1, respiration);
	}

	public static PowerFactory<BiggerLungsPower> createFactory() {
		return new PowerFactory<BiggerLungsPower>(ModId.ID("bigger_lungs"), new SerializableData()
				.add("respiration", SerializableDataTypes.INT),
				data -> (type, player) -> new BiggerLungsPower(type, player, data.getInt("respiration"))
		).allowCondition();
	}

	public static int getRespiration(Entity entity) {
		int respiration = -1;
		if (entity instanceof LivingEntity && entity.isAlive()) {
			for (BiggerLungsPower power : PowerHolderComponent.KEY.get(entity).getPowers(BiggerLungsPower.class)) {
				if (power.isActive()) respiration = Math.max(respiration, power.respiration);
			}
		}
		return respiration;
	}
}