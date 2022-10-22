package haven.effects;

import haven.HavenMod;
import haven.damage.HavenDamageSource;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.world.World;

public class WitheringEffect extends StatusEffect {
	public WitheringEffect() {
		super(StatusEffectType.HARMFUL, 0x6B7750);
	}

	@Override
	public boolean canApplyUpdateEffect(int duration, int amplifier) {
		switch (amplifier) {
			case 0: return duration % 19000 == 0;	//900 seconds (15 minutes)
			case 1: return duration % 12000 == 0;	//600 seconds (10 minutes)
			case 2: return duration % 7000 == 0;	//300 seconds (5 minutes)
			case 3: return duration % 3600 == 0;	//180 seconds (3 minutes)
			case 4: return duration % 2400 == 0;	//120 seconds (2 minutes)
			case 5: return duration % 1200 == 0;	//60 seconds (1 minute)
			case 6: return duration % 600 == 0; 	//30 seconds
			case 7: return duration % 200 == 0; 	//10 seconds
			case 8: return duration % 100 == 0; 	//5 seconds
			default: return duration % 20 == 0; 	//1 second
		}
	}

	@Override
	public void applyUpdateEffect(LivingEntity entity, int i) {
		if (entity.hasStatusEffect(HavenMod.PROTECTED_EFFECT)) return;
		if (!entity.hasStatusEffect(HavenMod.RELIEVED_EFFECT)) {
			entity.damage(HavenDamageSource.WITHERING,1);
		}
		increase(entity.world, entity);
	}

	public static void reduce(World world, LivingEntity entity) {
		if (world.isClient) return;
		if (entity.hasStatusEffect(HavenMod.WITHERING_EFFECT)) {
			StatusEffectInstance effect = entity.getStatusEffect(HavenMod.WITHERING_EFFECT);
			int amplifier = effect.getAmplifier();
			if (amplifier > 1) {
				entity.removeStatusEffect(HavenMod.WITHERING_EFFECT);
				entity.addStatusEffect(new StatusEffectInstance(HavenMod.WITHERING_EFFECT, effect.getDuration(), amplifier - 1));
			}
		}
	}

	public static void increase(World world, LivingEntity entity) {
		if (world.isClient) return;
		if (entity.hasStatusEffect(HavenMod.WITHERING_EFFECT)) {
			StatusEffectInstance effect = entity.getStatusEffect(HavenMod.WITHERING_EFFECT);
			entity.removeStatusEffect(HavenMod.WITHERING_EFFECT);
			int amplifier = effect.getAmplifier();
			entity.addStatusEffect(new StatusEffectInstance(HavenMod.WITHERING_EFFECT, effect.getDuration(), amplifier < 7 ? amplifier + 1 : amplifier, true, false));
		}
	}
}
