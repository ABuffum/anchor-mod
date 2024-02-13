package fun.wich.item.tool;

import fun.wich.ModFactory;
import fun.wich.gen.data.tag.ModBlockTags;
import fun.wich.material.ModToolMaterials;
import net.minecraft.item.MiningToolItem;
import net.minecraft.item.ToolMaterial;

public class StoneaxeItem extends MiningToolItem {
	public StoneaxeItem(ModToolMaterials material) { this(material, ModFactory.ItemSettings()); }
	public StoneaxeItem(ToolMaterial material, float attackDamage, float attackSpeed) { this(material, attackDamage, attackSpeed, ModFactory.ItemSettings()); }
	public StoneaxeItem(ModToolMaterials material, Settings settings) { this(material, material.getStoneaxeDamage(), material.getStoneaxeSpeed(), settings); }
	public StoneaxeItem(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) { super(attackDamage, attackSpeed, material, ModBlockTags.STONEAXE_MINEABLE, settings); }
}