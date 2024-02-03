package fun.wich.item.tool.echo;

import fun.wich.item.tool.ExtraKnockbackItem;
import fun.wich.item.tool.ModShovelItem;
import net.minecraft.item.ToolMaterial;

public class EchoShovelItem extends ModShovelItem implements ExtraKnockbackItem {
	private final int knockback;
	public int getExtraKnockback() { return this.knockback; }
	public EchoShovelItem(ToolMaterial material, float attackDamage, float attackSpeed, int knockback) {
		super(material, attackDamage, attackSpeed);
		this.knockback = knockback;
	}
}
