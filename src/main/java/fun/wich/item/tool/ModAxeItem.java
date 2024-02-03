package fun.wich.item.tool;

import fun.wich.ModFactory;
import fun.wich.material.ModToolMaterials;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ToolMaterial;

public class ModAxeItem extends AxeItem {
	public ModAxeItem(ModToolMaterials material) { this(material, ModFactory.ItemSettings()); }
	public ModAxeItem(ToolMaterial material, float attackDamage, float attackSpeed) { this(material, attackDamage, attackSpeed, ModFactory.ItemSettings()); }
	public ModAxeItem(ModToolMaterials material, Settings settings) { this(material, material.getAxeDamage(), material.getAxeSpeed(), settings); }
	public ModAxeItem(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) { super(material, attackDamage, attackSpeed, settings); }
}
