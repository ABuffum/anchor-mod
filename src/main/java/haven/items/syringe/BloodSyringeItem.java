package haven.items.syringe;

import haven.HavenMod;
import haven.blood.BloodType;
import haven.damage.HavenDamageSource;
import haven.mixins.entities.LivingEntityAccessor;
import haven.sounds.HavenSoundEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.Iterator;

public class BloodSyringeItem extends BaseSyringeItem {
	public final BloodType bloodType;
	private SyringeEffect applyEffect = null;
	public BloodSyringeItem(BloodType bloodType) {
		this(bloodType, (SyringeEffect)null);
	}
	public BloodSyringeItem(BloodType bloodType, SyringeEffect applyEffect) {
		this(bloodType, new Item.Settings().group(HavenMod.BLOOD_ITEM_GROUP).recipeRemainder(HavenMod.DIRTY_SYRINGE).maxCount(1));
		this.applyEffect = applyEffect;
	}
	public BloodSyringeItem(BloodType bloodType, Settings settings) {
		this(bloodType, settings, null);
	}
	public BloodSyringeItem(BloodType bloodType, Settings settings, SyringeEffect applyEffect) {
		super(settings);
		this.bloodType = bloodType;
	}

	@Override
	public void ApplyEffect(PlayerEntity user, LivingEntity entity) {
		if (applyEffect != null) applyEffect.ApplyEffect(user, entity);
		else {
			BloodType entityType = BloodType.Get(entity);
			if (bloodType == entityType) heal(entity,1);
			else entity.damage(HavenDamageSource.Injected((entity == user) ? null : user, bloodType), 1);
		}
	}

	public static void heal(LivingEntity entity, float amount) {
		entity.heal(amount);
		if (!entity.getEntityWorld().isClient) {
			Iterator<StatusEffectInstance> iterator = entity.getActiveStatusEffects().values().iterator();
			boolean bl;
			LivingEntityAccessor lea = (LivingEntityAccessor)entity;
			for(bl = false; iterator.hasNext(); bl = true) {
				StatusEffectInstance effect = iterator.next();
				StatusEffect type = effect.getEffectType();
				if (type == HavenMod.BLEEDING_EFFECT) lea.OnStatusEffectRemoved(effect);
				iterator.remove();
			}
		}
	}
}
