package fun.wich.origins.power;

import fun.wich.ModId;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import net.minecraft.entity.LivingEntity;

public class FireImmunePower extends Power {
	public FireImmunePower(PowerType<?> type, LivingEntity entity) { super(type, entity); }
	public static PowerFactory<FireImmunePower> createFactory() {
		return new PowerFactory<FireImmunePower>(ModId.ID("fire_immune"), new SerializableData(), data -> FireImmunePower::new).allowCondition();
	}
}