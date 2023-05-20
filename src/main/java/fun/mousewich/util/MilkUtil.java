package fun.mousewich.util;

import fun.mousewich.damage.ModDamageSource;
import fun.mousewich.effect.ModStatusEffect;
import fun.mousewich.mixins.entity.LivingEntityAccessor;
import fun.mousewich.origins.power.LactoseIntolerantPower;
import io.github.apace100.apoli.component.PowerHolderComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;

public class MilkUtil {
	public static void ApplyMilk(World world, LivingEntity entity) { ApplyMilk(world, entity, false); }
	public static void ApplyMilk(World world, LivingEntity entity, boolean coffeeMilk) {
		ClearStatusEffects(world, entity);
		if (coffeeMilk) {
			entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 200, 0));
			entity.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 200, 0));
		}
		CheckLactosIntolerance(world, entity);
	}
	public static void ClearStatusEffects(World world, LivingEntity entity) {
		if (world.isClient) return;
		Iterator<StatusEffectInstance> iterator = entity.getActiveStatusEffects().values().iterator();
		LivingEntityAccessor lea = (LivingEntityAccessor)entity;
		for(boolean bl = false; iterator.hasNext(); bl = true) {
			StatusEffectInstance effect = iterator.next();
			if (!ModStatusEffect.MILK_IMMUNE_EFFECTS.contains(effect.getEffectType())){
				lea.OnStatusEffectRemoved(effect);
				iterator.remove();
			}
		}
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
				if (damage > 0) entity.damage(ModDamageSource.DRANK_MILK, damage);
			}
		}
	}
}
