package fun.mousewich.item.armor;

import fun.mousewich.ModFactory;
import fun.mousewich.dispenser.WearableDispenserBehavior;
import net.minecraft.block.DispenserBlock;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;

public class ModArmorItem extends ArmorItem {
	public ModArmorItem(ArmorMaterial material, EquipmentSlot slot) { this(material, slot, ModFactory.ItemSettings()); }
	public ModArmorItem(ArmorMaterial material, EquipmentSlot slot, Settings settings) { super(material, slot, settings); }

	public ModArmorItem dispensible() {
		DispenserBlock.registerBehavior(this, new WearableDispenserBehavior());
		return this;
	}
}
