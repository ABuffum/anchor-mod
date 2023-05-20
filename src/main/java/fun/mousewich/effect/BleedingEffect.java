package fun.mousewich.effect;

import fun.mousewich.damage.ModDamageSource;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.*;

public class BleedingEffect extends ModStatusEffect {
	public BleedingEffect() { super(StatusEffectType.HARMFUL, 0xFF0000); }
	@Override
	public boolean canApplyUpdateEffect(int duration, int amplifier) {
		int k = 40 >> amplifier;
		return k <= 1 || duration % k == 0;
	}
	@Override
	public void applyUpdateEffect(LivingEntity entity, int i) { entity.damage(ModDamageSource.BLEEDING,1); }
}
