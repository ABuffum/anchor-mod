package fun.wich.origins.power;

import fun.wich.ModId;
import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.LivingEntity;

public class RecycleArrowsPower extends Power {
	public final float chance;
	public RecycleArrowsPower(PowerType<?> type, LivingEntity entity, float chance) {
		super(type, entity);
		this.chance = chance;
	}
	public static PowerFactory<RecycleArrowsPower> createFactory() {
		return new PowerFactory<RecycleArrowsPower>(ModId.ID("recycle_arrows"), new SerializableData()
				.add("chance", SerializableDataTypes.FLOAT),
				data -> (type, entity) -> new RecycleArrowsPower(type, entity, data.getFloat("chance"))).allowCondition();
	}

	public static float GetChance(LivingEntity entity) {
		float chance = 0;
		for (RecycleArrowsPower power : PowerHolderComponent.getPowers(entity, RecycleArrowsPower.class)) {
			if (power.isActive()) chance = Math.max(chance, power.chance);
		}
		return chance;
	}
}