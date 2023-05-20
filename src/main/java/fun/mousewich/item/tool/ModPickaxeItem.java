package fun.mousewich.item.tool;

import fun.mousewich.ModFactory;
import fun.mousewich.material.ModToolMaterials;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolMaterial;

public class ModPickaxeItem extends PickaxeItem {
	public ModPickaxeItem(ModToolMaterials material) { this(material, ModFactory.ItemSettings()); }
	public ModPickaxeItem(ToolMaterial material, int attackDamage, float attackSpeed) { this(material, attackDamage, attackSpeed, ModFactory.ItemSettings()); }
	public ModPickaxeItem(ModToolMaterials material, Settings settings) { this(material, material.getPickaxeDamage(), material.getPickaxeSpeed(), settings); }
	public ModPickaxeItem(ToolMaterial material, int attackDamage, float attackSpeed, Settings settings) { super(material, attackDamage, attackSpeed, settings); }
}
