package haven.blood;

import haven.HavenMod;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

public class BloodTypePower extends Power {
	public final Identifier bloodType;

	public BloodTypePower(PowerType<?> type, LivingEntity entity, Identifier bloodType) {
		super(type, entity);
		this.bloodType = bloodType;
	}

	public static PowerFactory createFactory() {
		return new PowerFactory<>(HavenMod.ID("special_blood"), new SerializableData().add("blood_type", SerializableDataTypes.IDENTIFIER),
				data -> (type, player) -> new BloodTypePower(type, player, (Identifier)data.get("blood_type"))).allowCondition();
	}
}