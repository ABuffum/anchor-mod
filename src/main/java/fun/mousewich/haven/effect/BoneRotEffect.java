package fun.mousewich.haven.effect;

import fun.mousewich.damage.ModDamageSource;
import fun.mousewich.ModBase;
import fun.mousewich.effect.ModStatusEffect;
import fun.mousewich.haven.HavenMod;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.world.World;

public class BoneRotEffect extends ModStatusEffect {
	public BoneRotEffect() { super(StatusEffectType.HARMFUL, 0x000000); }

	@Override
	public boolean canApplyUpdateEffect(int duration, int amplifier) {
		return switch (amplifier) {
			case 0 -> duration % 7000 == 0;     //300 seconds (5 minutes)
			case 1 -> duration % 3600 == 0;     //180 seconds (3 minutes)
			case 2 -> duration % 2400 == 0;     //120 seconds (2 minutes)
			case 3 -> duration % 1200 == 0;     //60 seconds (1 minute)
			case 4 -> duration % 600 == 0;      //30 seconds
			case 5 -> duration % 300 == 0;      //15 seconds
			case 6 -> duration % 200 == 0;      //10 seconds
			case 7 -> duration % 100 == 0;      //5 seconds
			case 8 -> duration % 20 == 0;       //1 second
			case 9 -> duration % 10 == 0;       //half second
			case 10 -> duration % 5 == 0;       //quarter second
			default -> true;                    //eat shit and die
		};
	}

	@Override
	public void applyUpdateEffect(LivingEntity entity, int i) {
		if (!entity.hasStatusEffect(HavenMod.RELIEVED_EFFECT)) {
			entity.damage(ModDamageSource.BONE_ROT,1);
		}
		if (entity.isSprinting()) {
			entity.setSprinting(false);
			increase(entity.world, entity);
		}
	}

	public static void reduce(World world, LivingEntity entity) {
		if (world.isClient) return;
		if (entity.hasStatusEffect(HavenMod.BONE_ROT_EFFECT)) {
			StatusEffectInstance effect = entity.getStatusEffect(HavenMod.BONE_ROT_EFFECT);
			int amplifier = effect.getAmplifier();
			if (amplifier > 1) {
				entity.removeStatusEffect(HavenMod.BONE_ROT_EFFECT);
				entity.addStatusEffect(new StatusEffectInstance(HavenMod.BONE_ROT_EFFECT, effect.getDuration(), amplifier - 1));
			}
		}
	}

	public static void increase(World world, LivingEntity entity) {
		if (world.isClient) return;
		if (entity.hasStatusEffect(HavenMod.BONE_ROT_EFFECT)) {
			StatusEffectInstance effect = entity.getStatusEffect(HavenMod.BONE_ROT_EFFECT);
			entity.removeStatusEffect(HavenMod.BONE_ROT_EFFECT);
			entity.addStatusEffect(new StatusEffectInstance(HavenMod.BONE_ROT_EFFECT, effect.getDuration(), effect.getAmplifier() + 1));
		}
	}
}
