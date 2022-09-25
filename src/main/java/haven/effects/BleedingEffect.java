package haven.effects;

import haven.damage.HavenDamageSource;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;

public class BleedingEffect extends StatusEffect {
	public BleedingEffect() {
		super(StatusEffectType.HARMFUL, 0xFF0000);
	}

	@Override
	public boolean canApplyUpdateEffect(int duration, int amplifier) {
		int k = 40 >> amplifier;
		if (k > 0) return duration % k == 0;
		else return true;
	}

	@Override
	public void applyUpdateEffect(LivingEntity entity, int i) {
		entity.damage(HavenDamageSource.BLEEDING,1);
	}
}
