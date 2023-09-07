package fun.mousewich.util;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.registry.Registry;

public class ItemUtil {
	public static ItemStack getConsumableRemainder(ItemStack stack, LivingEntity user, Item remainder) {
		ItemStack newStack = new ItemStack(remainder);
		if (user instanceof PlayerEntity player) {
			if (stack.isFood()) user.eatFood(user.world, stack);
			else if (player.getAbilities().creativeMode) stack.decrement(1);
			if (stack.isEmpty()) return newStack;
			else if (player.getInventory().getEmptySlot() > 0) player.getInventory().insertStack(newStack);
			else player.dropItem(newStack, false);
		}
		return stack.isEmpty() ? newStack : stack;
	}

	public static ItemStack swapItem(ItemStack stack, Item target) {
		if (stack.isEmpty()) return ItemStack.EMPTY;
		ItemStack itemStack = new ItemStack(target, stack.getCount());
		//itemStack.setBobbingAnimationTime(stack.getBobbingAnimationTime());
		if (stack.hasNbt()) {
			NbtCompound nbt = stack.getNbt().copy();
			nbt.putString("id", Registry.ITEM.getId(target).toString());
			itemStack.setNbt(nbt);
		}
		return itemStack;
	}
}
