package fun.wich.item.tool.echo;

import fun.wich.item.tool.ExtraKnockbackItem;
import fun.wich.item.tool.ModHoeItem;
import net.minecraft.item.ToolMaterial;

public class EchoHoeItem extends ModHoeItem implements ExtraKnockbackItem {
	private final int knockback;
	public int getExtraKnockback() { return this.knockback; }
	public EchoHoeItem(ToolMaterial material, int attackDamage, float attackSpeed, int knockback) {
		super(material, attackDamage, attackSpeed);
		this.knockback = knockback;
	}
}
