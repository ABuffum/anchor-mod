package fun.mousewich.item.consumable;

import fun.mousewich.ModBase;
import fun.mousewich.damage.ModDamageSource;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;

public class BottledSlimeItem extends BottledDrinkItem {
	public BottledSlimeItem(Settings settings) { super(settings); }

	@Override
	public void OnDrink(ItemStack stack, LivingEntity user) {
		user.addStatusEffect(new StatusEffectInstance(ModBase.STICKY_EFFECT, 600));
		user.damage(ModDamageSource.DRANK_SLIME, 4);
	}
}

