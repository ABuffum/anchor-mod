package fun.mousewich.origins.power;

import fun.mousewich.ModId;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import net.minecraft.entity.LivingEntity;

public class SoftStepsPower extends Power {
	public SoftStepsPower(PowerType<?> type, LivingEntity entity) { super(type, entity); }

	public static PowerFactory<SoftStepsPower> createFactory() {
		return new PowerFactory<SoftStepsPower>(ModId.ID("soft_steps"), new SerializableData(), data -> SoftStepsPower::new).allowCondition();
	}
}
