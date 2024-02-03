package fun.wich.haven.effect;

import fun.wich.damage.ModDamageSource;
import fun.wich.effect.ModStatusEffect;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectType;

public class DeteriorationEffect extends ModStatusEffect {
	public DeteriorationEffect() { super(StatusEffectType.HARMFUL, 0x000000); }

	@Override
	public boolean canApplyUpdateEffect(int duration, int amplifier) {
		int k = 40 >> amplifier;
		if (k > 0) return duration % k == 0;
		else return true;
	}

	@Override
	public void applyUpdateEffect(LivingEntity entity, int amplifier) {
		entity.damage(ModDamageSource.DETERIORATION,1);
	}
}
