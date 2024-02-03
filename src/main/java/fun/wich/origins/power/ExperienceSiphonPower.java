package fun.wich.origins.power;

import fun.wich.ModId;
import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;

public class ExperienceSiphonPower extends Power {
	public final float modifier;
	public ExperienceSiphonPower(PowerType<?> type, LivingEntity entity, float modifier) {
		super(type, entity);
		this.modifier = Math.max(0, modifier);
	}

	public static PowerFactory<ExperienceSiphonPower> createFactory() {
		return new PowerFactory<ExperienceSiphonPower>(ModId.ID("experience_siphon"), new SerializableData()
				.add("modifier", SerializableDataTypes.FLOAT, 1F),
				data -> (type, player) -> new ExperienceSiphonPower(type, player, data.getFloat("modifier"))
		).allowCondition();
	}

	public static float getModifier(Entity entity) {
		float modifier = 1;
		for (ExperienceSiphonPower power : PowerHolderComponent.getPowers(entity, ExperienceSiphonPower.class)) {
			if (power.isActive()) modifier *= power.modifier;
		}
		return modifier;
	}
}