package haven.effects;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;

public class MarkedEffect extends StatusEffect {
	public MarkedEffect() {
		super(StatusEffectType.NEUTRAL,0xFF00FF);
	}
}
