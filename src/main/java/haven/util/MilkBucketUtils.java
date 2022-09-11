package haven.util;

import haven.HavenMod;
import haven.mixins.entities.LivingEntityAccessor;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.world.World;

import java.util.Iterator;

public class MilkBucketUtils {
	public static boolean ClearStatusEffects(World world, LivingEntity entity) {
		if (world.isClient) return false;
		Iterator<StatusEffectInstance> iterator = entity.getActiveStatusEffects().values().iterator();
		boolean bl;
		LivingEntityAccessor lea = (LivingEntityAccessor)entity;
		for(bl = false; iterator.hasNext(); bl = true) {
			StatusEffectInstance effect = (StatusEffectInstance)iterator.next();
			if (!HavenMod.MILK_IMMUNE_EFFECTS.contains(effect.getEffectType())) {
				lea.OnStatusEffectRemoved(effect);
			}
			iterator.remove();
		}
		return bl;
	}
}
