package fun.mousewich.origins.power;

import fun.mousewich.ModId;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import net.minecraft.entity.LivingEntity;

public class ClownPacifistPower extends Power {
	public ClownPacifistPower(PowerType<?> type, LivingEntity entity) { super(type, entity); }

	public static PowerFactory<ClownPacifistPower> createFactory() {
		return new PowerFactory<ClownPacifistPower>(ModId.ID("clown_pacifist"), new SerializableData(), data -> ClownPacifistPower::new).allowCondition();
	}
}
