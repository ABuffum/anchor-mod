package fun.wich.item.consumable;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;

public class BottledPoisonItem extends BottledDrinkItem {
	public final int duration;
	public final int amplifier;
	public BottledPoisonItem(int duration, int amplifier, Settings settings) {
		super(settings);
		this.duration = duration;
		this.amplifier = amplifier;
	}
	@Override
	public void OnDrink(ItemStack stack, LivingEntity user) {
		user.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, duration, amplifier));
	}
}

