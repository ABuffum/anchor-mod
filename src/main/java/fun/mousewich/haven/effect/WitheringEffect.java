package fun.mousewich.haven.effect;

import fun.mousewich.damage.ModDamageSource;
import fun.mousewich.effect.ModStatusEffect;
import fun.mousewich.haven.HavenMod;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.world.World;

public class WitheringEffect extends ModStatusEffect {
	public WitheringEffect() { super(StatusEffectType.HARMFUL, 0x6B7750); }
	@Override
	public boolean canApplyUpdateEffect(int duration, int amplifier) {
		return switch (amplifier) {
			case 0 -> duration % 18000 == 0;    //900 seconds (15 minutes)
			case 1 -> duration % 14400 == 0;    //720 seconds (12 minutes)
			case 2 -> duration % 12000 == 0;    //600 seconds (10 minutes)
			case 3 -> duration % 9600 == 0;    //480 seconds (8 minutes)
			case 4 -> duration % 6000 == 0;    //300 seconds (5 minutes)
			case 5 -> duration % 2400 == 0;    //120 seconds (2 minute)
			case 6 -> duration % 1200 == 0;    //60 seconds (1 minute)
			case 7 -> duration % 600 == 0;     //30 seconds
			case 8 -> duration % 300 == 0;     //15 seconds
			default -> duration % 100 == 0;    //5 seconds
		};
	}

	@Override
	public void applyUpdateEffect(LivingEntity entity, int amplifier) {
		if (entity.hasStatusEffect(HavenMod.PROTECTED_EFFECT)) {
			if (entity.world.isClient) return;
			if (entity.hasStatusEffect(HavenMod.PROTECTED_EFFECT)) entity.removeStatusEffect(HavenMod.WITHERING_EFFECT);
			return;
		}
		if (entity.hasStatusEffect(HavenMod.RELIEVED_EFFECT)) return;
		entity.damage(ModDamageSource.WITHERING,1);
		if (amplifier < 9) increase(entity.world, entity);
	}

	public static void reduce(World world, LivingEntity entity) {
		if (world.isClient) return;
		StatusEffectInstance effect;
		int amplifier;
		if (entity.hasStatusEffect(HavenMod.WITHERING_EFFECT) && (effect = entity.getStatusEffect(HavenMod.WITHERING_EFFECT)) != null && (amplifier = effect.getAmplifier()) > 0) {
			entity.removeStatusEffect(HavenMod.WITHERING_EFFECT);
			entity.addStatusEffect(new StatusEffectInstance(HavenMod.WITHERING_EFFECT, effect.getDuration(), amplifier - 1));
		}
	}

	public static void increase(World world, LivingEntity entity) {
		if (world.isClient) return;
		StatusEffectInstance effect;
		if (entity.hasStatusEffect(HavenMod.WITHERING_EFFECT) && (effect = entity.getStatusEffect(HavenMod.WITHERING_EFFECT)) != null) {
			entity.removeStatusEffect(HavenMod.WITHERING_EFFECT);
			entity.addStatusEffect(new StatusEffectInstance(HavenMod.WITHERING_EFFECT, effect.getDuration(), Math.max(Math.min(9, effect.getAmplifier()), 0), true, false, true));
		}
	}
}
