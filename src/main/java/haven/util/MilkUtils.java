package haven.util;

import com.mojang.datafixers.types.templates.Check;
import haven.HavenMod;
import haven.damage.HavenDamageSource;
import haven.effects.BoneRotEffect;
import haven.mixins.entities.LivingEntityAccessor;
import haven.origins.powers.LactoseIntolerantPower;
import io.github.apace100.apoli.component.PowerHolderComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;

public class MilkUtils {
	public static void ApplyMilk(World world, LivingEntity entity) {
		ApplyMilk(world, entity, false);
	}
	public static void ApplyMilk(World world, LivingEntity entity, boolean coffeeMilk) {
		ClearStatusEffects(world, entity);
		if (coffeeMilk) {
			entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 200, 0));
			entity.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 200, 0));
		}
		CheckLactosIntolerance(world, entity);
	}
	public static boolean ClearStatusEffects(World world, LivingEntity entity) {
		if (world.isClient) return false;
		Iterator<StatusEffectInstance> iterator = entity.getActiveStatusEffects().values().iterator();
		boolean bl;
		LivingEntityAccessor lea = (LivingEntityAccessor)entity;
		for(bl = false; iterator.hasNext(); bl = true) {
			StatusEffectInstance effect = iterator.next();
			if (!HavenMod.MILK_IMMUNE_EFFECTS.contains(effect.getEffectType())){
				lea.OnStatusEffectRemoved(effect);
				iterator.remove();
			}
		}
		BoneRotEffect.reduce(world, entity);
		return bl;
	}

	public static void CheckLactosIntolerance(World world, LivingEntity entity) {
		List<LactoseIntolerantPower> powers = PowerHolderComponent.KEY.get(entity).getPowers(LactoseIntolerantPower.class);
		if (!powers.isEmpty()) {
			boolean active = false;
			int damage = 0, duration = 0, amplifier = 0;
			for (LactoseIntolerantPower power : powers) {
				if (power.isActive()) {
					active = true;
					damage = Math.max(damage, power.damage);
					duration = Math.max(duration, power.hungerDuration);
					amplifier = Math.max(amplifier, power.hungerAmplifier);
				}
			}
			if (active) {
				if (duration > 0 && amplifier > 0) entity.addStatusEffect(new StatusEffectInstance(StatusEffects.HUNGER, duration, amplifier));
				if (damage > 0) entity.damage(HavenDamageSource.DRANK_MILK, damage);
			}
		}
	}
}
