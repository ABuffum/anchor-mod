package fun.mousewich.item.tool.echo;

import fun.mousewich.item.tool.ExtraKnockbackItem;
import fun.mousewich.item.tool.ModPickaxeItem;
import net.minecraft.item.ToolMaterial;

public class EchoPickaxeItem extends ModPickaxeItem implements ExtraKnockbackItem {
	private final int knockback;
	public int getExtraKnockback() { return this.knockback; }
	public EchoPickaxeItem(ToolMaterial material, int attackDamage, float attackSpeed, int knockback) {
		super(material, attackDamage, attackSpeed);
		this.knockback = knockback;
	}
}
