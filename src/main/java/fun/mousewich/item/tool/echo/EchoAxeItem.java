package fun.mousewich.item.tool.echo;

import fun.mousewich.item.tool.ExtraKnockbackItem;
import fun.mousewich.item.tool.ModAxeItem;
import net.minecraft.item.ToolMaterial;

public class EchoAxeItem extends ModAxeItem implements ExtraKnockbackItem {
	private final int knockback;
	public int getExtraKnockback() { return this.knockback; }
	public EchoAxeItem(ToolMaterial material, float attackDamage, float attackSpeed, int knockback) {
		super(material, attackDamage, attackSpeed);
		this.knockback = knockback;
	}
}
