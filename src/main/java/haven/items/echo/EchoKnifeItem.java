package haven.items.echo;

import com.nhoryzon.mc.farmersdelight.item.KnifeItem;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;

public class EchoKnifeItem extends KnifeItem {
	private float knockback = 0;

	public EchoKnifeItem(ToolMaterial material, float knockback, Settings settings) {
		super(material, settings);
		this.knockback = knockback;
	}

	@Override
	public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		stack.damage(1, attacker, (e) -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
		if (knockback > 0) target.takeKnockback(0.5D + knockback, attacker.getX() - target.getX(), attacker.getZ() - target.getZ());
		return true;
	}
}
