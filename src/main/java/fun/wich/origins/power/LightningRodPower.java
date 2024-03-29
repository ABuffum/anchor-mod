package fun.wich.origins.power;

import fun.wich.ModId;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import net.minecraft.entity.LivingEntity;

public class LightningRodPower extends Power {
	public LightningRodPower(PowerType<?> type, LivingEntity entity) { super(type, entity); }
	public static PowerFactory<LightningRodPower> createFactory() {
		return new PowerFactory<LightningRodPower>(ModId.ID("lightning_rod"), new SerializableData(), data -> LightningRodPower::new).allowCondition();
	}
}