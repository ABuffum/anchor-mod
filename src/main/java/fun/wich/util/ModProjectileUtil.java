package fun.wich.util;

import fun.wich.enchantment.ModEnchantments;
import fun.wich.enchantment.WeakeningEnchantment;
import fun.wich.entity.projectile.GravityEnchantmentCarrier;
import fun.wich.entity.projectile.WaterDragControllable;
import fun.wich.entity.projectile.WeakeningEnchantmentCarrier;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;

public class ModProjectileUtil {
	public static void ModifyProjectile(PersistentProjectileEntity projectile, ItemStack stackInHand) {
		//Add the status effect directly to the arrow instead of relying on the enchantment carrier
		if (projectile instanceof ArrowEntity arrowEntity) {
			int level = EnchantmentHelper.getLevel(ModEnchantments.WEAKENING, stackInHand);
			if (level > 0) arrowEntity.addEffect(WeakeningEnchantment.getEffect(level));
		}
		else if (projectile instanceof WeakeningEnchantmentCarrier weakeningCarrier) {
			int level = EnchantmentHelper.getLevel(ModEnchantments.WEAKENING, stackInHand);
			if (level > 0) weakeningCarrier.setWeakeningLevel(level);
		}
		if (projectile instanceof WaterDragControllable dragControllable) {
			int level = EnchantmentHelper.getLevel(ModEnchantments.WATER_SHOT, stackInHand);
			if (level > 0) dragControllable.setDragInWater(0.99f);
		}
		if (projectile instanceof GravityEnchantmentCarrier gravityCarrier) {
			int level = EnchantmentHelper.getLevel(ModEnchantments.GRAVITY, stackInHand);
			if (level > 0) gravityCarrier.setGravityLevel(level);
		}
	}
}
