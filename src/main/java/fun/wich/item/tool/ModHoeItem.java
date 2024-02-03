package fun.wich.item.tool;

import fun.wich.ModFactory;
import fun.wich.material.ModToolMaterials;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ToolMaterial;

public class ModHoeItem extends HoeItem {
	public ModHoeItem(ModToolMaterials material) { this(material, ModFactory.ItemSettings()); }
	public ModHoeItem(ToolMaterial material, int attackDamage, float attackSpeed) { this(material, attackDamage, attackSpeed, ModFactory.ItemSettings()); }
	public ModHoeItem(ModToolMaterials material, Settings settings) { this(material, material.getHoeDamage(), material.getHoeSpeed(), settings); }
	public ModHoeItem(ToolMaterial material, int attackDamage, float attackSpeed, Settings settings) { super(material, attackDamage, attackSpeed, settings); }
}
