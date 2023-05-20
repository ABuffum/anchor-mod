package fun.mousewich.item.armor;

import fun.mousewich.ModFactory;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.DyeableArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

public class ModDyeableArmorItem extends DyeableArmorItem {
	public final int baseColor;
	public ModDyeableArmorItem(int baseColor, ArmorMaterial material, EquipmentSlot slot) { this(baseColor, material, slot, ModFactory.ItemSettings()); }
	public ModDyeableArmorItem(int baseColor, ArmorMaterial material, EquipmentSlot slot, Settings settings) {
		super(material, slot, settings);
		this.baseColor = baseColor;
	}
	public int getColor(ItemStack stack) {
		NbtCompound nbtCompound = stack.getSubNbt(DISPLAY_KEY);
		if (nbtCompound != null && nbtCompound.contains(COLOR_KEY, 99)) return nbtCompound.getInt(COLOR_KEY);
		return this.baseColor;
	}
}
