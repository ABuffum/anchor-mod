package fun.wich.item.tool.echo;

import fun.wich.item.tool.ExtraKnockbackItem;
import fun.wich.item.tool.ModSwordItem;
import net.minecraft.item.ToolMaterial;

public class EchoSwordItem extends ModSwordItem implements ExtraKnockbackItem {
	private final int knockback;
	public int getExtraKnockback() { return this.knockback; }
	public EchoSwordItem(ToolMaterial material, int attackDamage, float attackSpeed, int knockback) {
		super(material, attackDamage, attackSpeed);
		this.knockback = knockback;
	}
}
