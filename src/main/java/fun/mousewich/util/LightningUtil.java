package fun.mousewich.util;

import fun.mousewich.gen.data.tag.ModItemTags;
import fun.mousewich.origins.power.LightningRodPower;
import fun.mousewich.origins.power.PowersUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.Vec3d;

public class LightningUtil {
	public static boolean ShouldLightningRod(Vec3d origin, Entity entity) {
		Vec3d pos = entity.getPos();
		int radius = LightningRodRadius(entity);
		if (!origin.isInRange(new Vec3d(pos.getX(), origin.getY(), pos.getZ()), radius)) return false; //Don't apply to entities out of range
		if (!entity.world.isSkyVisible(entity.getBlockPos())) return false; //Don't get struck indoors
		return radius > 0;
	}
	public static int LightningRodRadius(Entity entity) {
		int radius = 0;
		if (PowersUtil.Active(entity, LightningRodPower.class)) radius += 32;
		if (entity instanceof LivingEntity living) {
			ItemStack headStack = living.getEquippedStack(EquipmentSlot.HEAD);
			if (headStack.isIn(ModItemTags.COPPER_HELMETS) || headStack.isOf(Items.LIGHTNING_ROD)) radius += 16;
			if (living.getEquippedStack(EquipmentSlot.CHEST).isIn(ModItemTags.COPPER_CHESTPLATES)) radius += 8;
			if (living.getEquippedStack(EquipmentSlot.LEGS).isIn(ModItemTags.COPPER_LEGGINGS)) radius += 6;
			if (living.getEquippedStack(EquipmentSlot.FEET).isIn(ModItemTags.COPPER_BOOTS)) radius += 2;
		}
		return radius;
	}
}
