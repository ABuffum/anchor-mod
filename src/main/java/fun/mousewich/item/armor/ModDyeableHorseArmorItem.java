package fun.mousewich.item.armor;

import fun.mousewich.ModFactory;
import fun.mousewich.material.ModArmorMaterials;
import net.minecraft.item.DyeableItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

public class ModDyeableHorseArmorItem extends ModHorseArmorItem implements DyeableItem {
	public final int baseColor;
	public ModDyeableHorseArmorItem(int baseColor, ModArmorMaterials material) { this(baseColor, material, ModFactory.ItemSettings().maxCount(1)); }
	public ModDyeableHorseArmorItem(int baseColor, ModArmorMaterials material, Settings settings) {
		super(material, settings);
		this.baseColor = baseColor;
	}
	public int getColor(ItemStack stack) {
		NbtCompound nbtCompound = stack.getSubNbt(DISPLAY_KEY);
		if (nbtCompound != null && nbtCompound.contains(COLOR_KEY, 99)) return nbtCompound.getInt(COLOR_KEY);
		return this.baseColor;
	}
}
