package haven.origins.powers;

import haven.HavenMod;
import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;

import java.util.List;

public class SkinGlowPower extends Power {
	public final int light_level; //Must be [0,15]
	public SkinGlowPower(PowerType<?> type, LivingEntity entity, int light_level) {
		super(type, entity);
		this.light_level = Math.max(0, Math.min(light_level, 15));
	}

	public static PowerFactory createFactory() {
		return new PowerFactory<>(HavenMod.ID("skin_glow"), new SerializableData()
				.add("light_level", SerializableDataTypes.INT, 1),
				data -> (type, player) -> new SkinGlowPower(type, player, data.getInt("light_level"))
		).allowCondition();
	}
}