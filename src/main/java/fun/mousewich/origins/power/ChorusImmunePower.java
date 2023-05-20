package fun.mousewich.origins.power;

import fun.mousewich.ModBase;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import net.minecraft.entity.LivingEntity;

public class ChorusImmunePower extends Power {
	public ChorusImmunePower(PowerType<?> type, LivingEntity entity) { super(type, entity); }
	public static PowerFactory<ChorusImmunePower> createFactory() {
		return new PowerFactory<ChorusImmunePower>(ModBase.ID("chorus_immune"), new SerializableData(),
				data -> ChorusImmunePower::new).allowCondition();
	}
}