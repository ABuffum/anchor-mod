package fun.mousewich.origins.power;

import fun.mousewich.ModBase;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.LivingEntity;

public class VillagersFleePower extends Power {
	public final float distance;
	public VillagersFleePower(PowerType<?> type, LivingEntity entity, float distance) {
		super(type, entity);
		this.distance = distance;
	}

	public static PowerFactory<VillagersFleePower> createFactory() {
		return new PowerFactory<VillagersFleePower>(ModBase.ID("villagers_flee"), new SerializableData()
				.add("distance", SerializableDataTypes.FLOAT, 8f),
				data -> (type, player) -> new VillagersFleePower(type, player, data.getFloat("distance"))
		).allowCondition();
	}
}
