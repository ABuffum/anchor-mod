package fun.wich.origins.power;

import fun.wich.ModId;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import net.minecraft.entity.LivingEntity;

public class CobwebMovementPower extends Power {
	public CobwebMovementPower(PowerType<?> type, LivingEntity entity) { super(type, entity); }
	public static PowerFactory<CobwebMovementPower> createFactory() {
		return new PowerFactory<CobwebMovementPower>(ModId.ID("cobweb_movement"), new SerializableData(), data -> CobwebMovementPower::new).allowCondition();
	}
}