package haven.util;

import haven.origins.powers.PulsingSkinGlowPower;
import haven.origins.powers.SkinGlowPower;
import io.github.apace100.apoli.component.PowerHolderComponent;
import net.minecraft.entity.Entity;

import java.util.List;

public class EntitySkinGlowPowerUtil {
	public static int getGlow(Entity entity) {
		List<SkinGlowPower> skinGlowPowers = PowerHolderComponent.getPowers(entity, SkinGlowPower.class);
		int glow = 0;
		for(SkinGlowPower power : skinGlowPowers) {
			if (power.isActive()) glow = Math.max(glow, power.light_level);
			if (glow > 14) return 15;
		}
		List<PulsingSkinGlowPower> pulsingSkinGlowPowers = PowerHolderComponent.getPowers(entity, PulsingSkinGlowPower.class);
		for (PulsingSkinGlowPower power : pulsingSkinGlowPowers) {
			if (power.isActive()) glow = Math.max(glow, power.getGlow());
			if (glow > 14) return 15;
		}
		return glow;
	}
}
