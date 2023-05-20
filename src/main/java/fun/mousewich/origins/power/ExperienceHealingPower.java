package fun.mousewich.origins.power;

import fun.mousewich.ModBase;
import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;

public class ExperienceHealingPower extends Power {
	public final int threshold; //Must be >= 1
	public ExperienceHealingPower(PowerType<?> type, LivingEntity entity, int threshold) {
		super(type, entity);
		this.threshold = Math.max(1, threshold);
	}

	public static PowerFactory<ExperienceHealingPower> createFactory() {
		return new PowerFactory<ExperienceHealingPower>(ModBase.ID("experience_healing"), new SerializableData()
				.add("threshold", SerializableDataTypes.INT, 1),
				data -> (type, player) -> new ExperienceHealingPower(type, player, data.getInt("threshold"))
		).allowCondition();
	}

	public static int getThreshold(Entity entity) {
		int threshold = Integer.MAX_VALUE;
		for (ExperienceHealingPower power : PowerHolderComponent.getPowers(entity, ExperienceHealingPower.class)) {
			if (power.isActive()) threshold = Math.min(threshold, power.threshold);
		}
		return threshold == Integer.MAX_VALUE ? -1 : threshold;
	}
}