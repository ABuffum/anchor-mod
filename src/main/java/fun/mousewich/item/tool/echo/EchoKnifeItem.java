package fun.mousewich.item.tool.echo;

import fun.mousewich.item.tool.ExtraKnockbackItem;
import fun.mousewich.item.tool.ModKnifeItem;
import net.minecraft.item.ToolMaterial;

public class EchoKnifeItem extends ModKnifeItem implements ExtraKnockbackItem {
	private final int knockback;
	public int getExtraKnockback() { return this.knockback; }
	public EchoKnifeItem(ToolMaterial material, int knockback) {
		super(material);
		this.knockback = knockback;
	}
}
