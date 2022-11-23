package haven.util;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;

public class ItemUtils {
	public static ItemStack getConsumableRemainder(ItemStack stack, LivingEntity user, Item remainder) {
		ItemStack newStack = new ItemStack(remainder);
		if (user instanceof PlayerEntity player) {
			if (player.getAbilities().creativeMode) stack.decrement(1);
			if (stack.isEmpty()) return newStack;
			else if (player.getInventory().getEmptySlot() > 0) player.getInventory().insertStack(newStack);
			else player.dropItem(newStack, false);
		}
		return stack.isEmpty() ? newStack : stack;
	}
}
