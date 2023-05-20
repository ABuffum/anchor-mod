package fun.mousewich.origins.power;

import fun.mousewich.ModBase;
import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;

public class SkinGlowPower extends Power {
	public final int light_level; //Must be [0,15]
	public SkinGlowPower(PowerType<?> type, LivingEntity entity, int light_level) {
		super(type, entity);
		this.light_level = Math.max(0, Math.min(light_level, 15));
	}

	public static PowerFactory<SkinGlowPower> createFactory() {
		return new PowerFactory<SkinGlowPower>(ModBase.ID("skin_glow"), new SerializableData()
				.add("light_level", SerializableDataTypes.INT, 1),
				data -> (type, player) -> new SkinGlowPower(type, player, data.getInt("light_level"))
		).allowCondition();
	}

	public static int getGlow(Entity entity) {
		int glow = 0;
		for (SkinGlowPower power : PowerHolderComponent.getPowers(entity, SkinGlowPower.class)) {
			if (power.isActive()) glow = Math.max(glow, power.light_level);
			if (glow > 14) return 15;
		}
		for (PulsingSkinGlowPower power : PowerHolderComponent.getPowers(entity, PulsingSkinGlowPower.class)) {
			if (power.isActive()) glow = Math.max(glow, power.getGlow());
			if (glow > 14) return 15;
		}
		return glow;
	}
}