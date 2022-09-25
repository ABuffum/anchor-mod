package haven.effects;

import haven.blood.BloodType;
import haven.damage.HavenDamageSource;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.entity.player.PlayerEntity;

public class IchoredEffect extends StatusEffect {
	public IchoredEffect() {
		super(StatusEffectType.HARMFUL, 0x000000);
	}

	@Override
	public boolean canApplyUpdateEffect(int duration, int amplifier)  {
		int k = 20 >> amplifier;
		if (k > 0) return duration % k == 0;
		else return true;
	}

	@Override
	public void applyUpdateEffect(LivingEntity entity, int i) {
		if (entity instanceof PlayerEntity player) {
			BloodType type = BloodType.Get(player);
			entity.damage(HavenDamageSource.ICHOR, i);
		}
	}
}
