package fun.wich.item.tool;

import fun.wich.ModFactory;
import fun.wich.material.ModToolMaterials;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.ToolMaterial;

public class ModShovelItem extends ShovelItem {
	public ModShovelItem(ModToolMaterials material) { this(material, ModFactory.ItemSettings()); }
	public ModShovelItem(ToolMaterial material, float attackDamage, float attackSpeed) { this(material, attackDamage, attackSpeed, ModFactory.ItemSettings()); }
	public ModShovelItem(ModToolMaterials material, Settings settings) { this(material, material.getShovelDamage(), material.getShovelSpeed(), settings); }
	public ModShovelItem(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) { super(material, attackDamage, attackSpeed, settings); }
}
