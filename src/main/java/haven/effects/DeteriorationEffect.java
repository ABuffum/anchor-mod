package haven.effects;

import io.github.apace100.origins.origin.Origin;
import io.github.apace100.origins.origin.OriginLayer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;

import java.util.HashMap;

public class DeteriorationEffect extends StatusEffect {
	public static final DamageSource DAMAGE_SOURCE = new HavenDamageSource("Deterioration");

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
			entity.damage(DAMAGE_SOURCE,1);
		}
	}
}
