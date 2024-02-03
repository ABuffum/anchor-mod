package fun.wich.origins.power;

import fun.wich.ModId;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.LivingEntity;

public class WalkOnPowderSnowPower extends Power {
	public final boolean inverted;
	public WalkOnPowderSnowPower(PowerType<?> type, LivingEntity entity, boolean inverted) {
		super(type, entity);
		this.inverted = inverted;
	}
	public static PowerFactory<WalkOnPowderSnowPower> createFactory() {
		return new PowerFactory<WalkOnPowderSnowPower>(ModId.ID("walk_on_powder_snow"), new SerializableData()
				.add("inverted", SerializableDataTypes.BOOLEAN, false),
				data -> (type, entity) -> new WalkOnPowderSnowPower(type, entity, data.getBoolean("inverted")))
				.allowCondition();
	}
}