package fun.mousewich.origins.power;

import fun.mousewich.ModId;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import net.minecraft.entity.LivingEntity;

public class BurnForeverPower extends Power {
	public BurnForeverPower(PowerType<?> type, LivingEntity entity) { super(type, entity); }
	public static PowerFactory<BurnForeverPower> createFactory() {
		return new PowerFactory<BurnForeverPower>(ModId.ID("burn_forever"), new SerializableData(), data -> BurnForeverPower::new).allowCondition();
	}
}