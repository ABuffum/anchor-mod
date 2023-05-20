package fun.mousewich.util;

import fun.mousewich.ModBase;
import fun.mousewich.enchantment.WeakeningEnchantment;
import fun.mousewich.entity.projectile.GravityEnchantmentCarrier;
import fun.mousewich.entity.projectile.WaterDragControllable;
import fun.mousewich.entity.projectile.WeakeningEnchantmentCarrier;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;

public class ModProjectileUtil {
	public static void ModifyProjectile(PersistentProjectileEntity projectile, ItemStack stackInHand) {
		//Add the status effect directly to the arrow instead of relying on the enchantment carrier
		if (projectile instanceof ArrowEntity arrowEntity) {
			int level = EnchantmentHelper.getLevel(ModBase.WEAKENING_ENCHANTMENT, stackInHand);
			if (level > 0) arrowEntity.addEffect(WeakeningEnchantment.getEffect(level));
		}
		else if (projectile instanceof WeakeningEnchantmentCarrier weakeningCarrier) {
			int level = EnchantmentHelper.getLevel(ModBase.WEAKENING_ENCHANTMENT, stackInHand);
			if (level > 0) weakeningCarrier.setWeakeningLevel(level);
		}
		if (projectile instanceof WaterDragControllable dragControllable) {
			int level = EnchantmentHelper.getLevel(ModBase.WATER_SHOT_ENCHANTMENT, stackInHand);
			if (level > 0) dragControllable.setDragInWater(0.99f);
		}
		if (projectile instanceof GravityEnchantmentCarrier gravityCarrier) {
			int level = EnchantmentHelper.getLevel(ModBase.GRAVITY_ENCHANTMENT, stackInHand);
			if (level > 0) gravityCarrier.setGravityLevel(level);
		}
	}
}
