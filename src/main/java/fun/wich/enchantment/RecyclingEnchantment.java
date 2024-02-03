package fun.wich.enchantment;

import fun.wich.entity.projectile.ModArrowEntity;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShieldItem;

public class RecyclingEnchantment extends Enchantment {
	public RecyclingEnchantment(Rarity weight, EquipmentSlot... slots) { super(weight, EnchantmentTarget.BREAKABLE, slots); }
	@Override
	public boolean isAcceptableItem(ItemStack stack) { return stack.getItem() instanceof ShieldItem; }
	@Override
	public int getMaxLevel() { return 3; }

	public static void Recycle(PersistentProjectileEntity projectile, PlayerEntity player) {
		ItemStack stack = ItemStack.EMPTY;
		if (projectile instanceof ModArrowEntity arrow) stack = arrow.asItemStack();
		else if (projectile instanceof ArrowEntity) stack = new ItemStack(Items.ARROW);
		if (!stack.isEmpty()) {
			PlayerInventory inventory = player.getInventory();
			if (inventory.getEmptySlot() > 0) inventory.insertStack(stack);
			else player.dropItem(stack, false);
		}
	}
}
