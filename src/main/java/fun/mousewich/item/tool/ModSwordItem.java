package fun.mousewich.item.tool;

import fun.mousewich.ModFactory;
import fun.mousewich.material.ModToolMaterials;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;

public class ModSwordItem extends SwordItem {
	public ModSwordItem(ModToolMaterials material) { this(material, ModFactory.ItemSettings()); }
	public ModSwordItem(ToolMaterial material, int attackDamage, float attackSpeed) { this(material, attackDamage, attackSpeed, ModFactory.ItemSettings()); }
	public ModSwordItem(ModToolMaterials material, Settings settings) { this(material, material.getSwordDamage(), material.getSwordSpeed(), settings); }
	public ModSwordItem(ToolMaterial material, int attackDamage, float attackSpeed, Settings settings) { super(material, attackDamage, attackSpeed, settings); }
}
