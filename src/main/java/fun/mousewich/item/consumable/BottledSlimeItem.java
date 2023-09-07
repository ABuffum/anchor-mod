package fun.mousewich.item.consumable;

import fun.mousewich.ModBase;
import fun.mousewich.damage.ModDamageSource;
import fun.mousewich.effect.ModStatusEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;

public class BottledSlimeItem extends BottledDrinkItem {
	public BottledSlimeItem(Settings settings) { super(settings); }

	@Override
	public void OnDrink(ItemStack stack, LivingEntity user) {
		user.addStatusEffect(new StatusEffectInstance(ModStatusEffects.STICKY, 600));
		user.damage(ModDamageSource.DRANK_SLIME, 4);
	}
}

