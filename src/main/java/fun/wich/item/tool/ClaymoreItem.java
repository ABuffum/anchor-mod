package fun.wich.item.tool;

import fun.wich.material.ModToolMaterials;
import net.minecraft.item.ToolMaterial;

public class ClaymoreItem extends ModSwordItem implements ExtraKnockbackItem {
	public ClaymoreItem(ModToolMaterials material) { super(material); }
	public ClaymoreItem(ToolMaterial material, int attackDamage, float attackSpeed) { super(material, attackDamage, attackSpeed); }
	public ClaymoreItem(ModToolMaterials material, Settings settings) { super(material, settings); }
	public ClaymoreItem(ToolMaterial material, int attackDamage, float attackSpeed, Settings settings) { super(material, attackDamage, attackSpeed, settings); }

	@Override
	public int getExtraKnockback() { return 1; }
}
