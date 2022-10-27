package haven.effects;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;

public class FlashbangedEffect extends StatusEffect {
	public FlashbangedEffect() {
		super(StatusEffectType.HARMFUL, 0xFFFFFF);
	}
}
