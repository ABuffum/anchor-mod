package fun.wich.item.tool;

import fun.wich.ModFactory;
import fun.wich.material.ModToolMaterials;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.ShearsDispenserBehavior;
import net.minecraft.item.ShearsItem;
import net.minecraft.item.ToolMaterials;

public class ModShearsItem extends ShearsItem {
	public ModShearsItem(ToolMaterials material) { this(material, ModFactory.ItemSettings()); }
	public ModShearsItem(ToolMaterials material, Settings settings) { super(settings.maxDamage(material.getDurability())); }
	public ModShearsItem(ModToolMaterials material) { this(material, ModFactory.ItemSettings()); }
	public ModShearsItem(ModToolMaterials material, Settings settings) { super(settings.maxDamage(material.getDurability())); }

	public ModShearsItem dispensable() {
		DispenserBlock.registerBehavior(this, new ShearsDispenserBehavior());
		return this;
	}
}
