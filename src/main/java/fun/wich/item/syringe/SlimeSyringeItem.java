package fun.wich.item.syringe;

import fun.wich.ModBase;
import fun.wich.ModConfig;
import fun.wich.damage.ModDamageSource;
import fun.wich.effect.ModStatusEffects;
import fun.wich.entity.blood.BloodType;
import fun.wich.haven.HavenMod;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class SlimeSyringeItem extends BaseSyringeItem {
	public SlimeSyringeItem() { super(); }
	@Override
	public void ApplyEffect(PlayerEntity user, ItemStack stack, LivingEntity entity) {
		BloodType bloodType = BloodType.Get(entity);
		if (bloodType == ModBase.SLIME_BLOOD_TYPE || bloodType == ModBase.BLUE_SLIME_BLOOD_TYPE || bloodType == ModBase.PINK_SLIME_BLOOD_TYPE) {
			BloodSyringeItem.heal(entity, 1);
		}
		else if (bloodType == ModBase.MAGMA_CREAM_BLOOD_TYPE) BloodSyringeItem.heal(entity, 1);
		else if (ModConfig.REGISTER_HAVEN_MOD && bloodType == HavenMod.SLUDGE_BLOOD_TYPE) BloodSyringeItem.heal(entity, 1);
		else {
			entity.damage(ModDamageSource.Injected("slime", user), 1);
			user.addStatusEffect(new StatusEffectInstance(ModStatusEffects.STICKY, 600));
		}
	}
}
