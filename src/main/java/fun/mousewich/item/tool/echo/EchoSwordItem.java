package fun.mousewich.item.tool.echo;

import fun.mousewich.item.tool.ExtraKnockbackItem;
import fun.mousewich.item.tool.ModSwordItem;
import net.minecraft.item.ToolMaterial;

public class EchoSwordItem extends ModSwordItem implements ExtraKnockbackItem {
	private final int knockback;
	public int getExtraKnockback() { return this.knockback; }
	public EchoSwordItem(ToolMaterial material, int attackDamage, float attackSpeed, int knockback) {
		super(material, attackDamage, attackSpeed);
		this.knockback = knockback;
	}
}
