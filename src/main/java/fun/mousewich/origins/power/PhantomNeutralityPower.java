package fun.mousewich.origins.power;

import fun.mousewich.ModId;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import net.minecraft.entity.LivingEntity;

public class PhantomNeutralityPower extends Power {
	public PhantomNeutralityPower(PowerType<?> type, LivingEntity entity) { super(type, entity); }
	public static PowerFactory<PhantomNeutralityPower> createFactory() {
		return new PowerFactory<PhantomNeutralityPower>(ModId.ID("phantom_neutrality"), new SerializableData(), data -> PhantomNeutralityPower::new).allowCondition();
	}
}