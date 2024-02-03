package fun.wich.origins.power;

import fun.wich.ModId;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import net.minecraft.entity.LivingEntity;

public class UndeadPotionEffectsPower extends Power {
	public UndeadPotionEffectsPower(PowerType<?> type, LivingEntity entity) { super(type, entity); }
	public static PowerFactory<UndeadPotionEffectsPower> createFactory() {
		return new PowerFactory<UndeadPotionEffectsPower>(ModId.ID("undead_potion_effets"), new SerializableData(), data -> UndeadPotionEffectsPower::new).allowCondition();
	}
}
