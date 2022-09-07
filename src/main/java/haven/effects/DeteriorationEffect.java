package haven.effects;

import haven.damage.HavenDamageSource;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.entity.player.PlayerEntity;

public class DeteriorationEffect extends StatusEffect {
	public DeteriorationEffect() {
		super(StatusEffectType.HARMFUL, 0x000000);
	}

	@Override
	public boolean canApplyUpdateEffect(int duration, int amplifier) {
		return true;
	}

	@Override
	public void applyUpdateEffect(LivingEntity entity, int i) {
		if (entity instanceof PlayerEntity player) {
			entity.damage(HavenDamageSource.DETERIORATION,1);
		}
	}
}
