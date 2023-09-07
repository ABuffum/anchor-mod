package fun.mousewich.item.tool;

import fun.mousewich.enchantment.ThunderingEnchantment;
import fun.mousewich.material.ModToolMaterials;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;

public class ThunderingHammerItem extends HammerItem {
	protected final int factor;
	public ThunderingHammerItem(int factor, ModToolMaterials material) { this(factor, material, material.getHammerDamage(), material.getHammerSpeed()); }
	public ThunderingHammerItem(int factor, ToolMaterial material, float attackDamage, float attackSpeed) {
		super(material, attackDamage, attackSpeed);
		this.factor = factor;
	}
	@Override
	public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		ThunderingEnchantment.Strike(attacker.world, target.getBlockPos(), attacker, factor);
		return super.postHit(stack, target, attacker);
	}
}
