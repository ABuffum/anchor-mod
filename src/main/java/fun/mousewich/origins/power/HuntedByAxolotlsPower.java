package fun.mousewich.origins.power;

import fun.mousewich.ModId;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import net.minecraft.entity.LivingEntity;

public class HuntedByAxolotlsPower extends Power {
	public HuntedByAxolotlsPower(PowerType<?> type, LivingEntity entity) { super(type, entity); }
	public static PowerFactory<?> createFactory() {
		return new PowerFactory<>(ModId.ID("hunted_by_axolotls"), new SerializableData(), data -> HuntedByAxolotlsPower::new).allowCondition();
	}
}
