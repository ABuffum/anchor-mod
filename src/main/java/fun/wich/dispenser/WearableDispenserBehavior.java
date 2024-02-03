package fun.wich.dispenser;

import net.minecraft.block.dispenser.FallibleItemDispenserBehavior;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPointer;

public class WearableDispenserBehavior extends FallibleItemDispenserBehavior {
	@Override
	public ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
		this.setSuccess(ArmorItem.dispenseArmor(pointer, stack));
		return stack;
	}
}
