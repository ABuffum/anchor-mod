package fun.wich.enchantment;

import fun.wich.ModBase;
import fun.wich.util.EnchantmentUtil;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class GravityEnchantment extends Enchantment {
	public GravityEnchantment(Rarity weight, EquipmentSlot... slots) { super(weight, EnchantmentTarget.WEAPON, slots); }
	@Override
	public int getMaxLevel() { return 3; }
	@Override
	public boolean isAcceptableItem(ItemStack stack) {
		Item item = stack.getItem();
		return item instanceof AxeItem || EnchantmentUtil.isBow(item) || super.isAcceptableItem(stack);
	}
	@Override
	public void onTargetDamaged(LivingEntity user, Entity target, int level) {
		if (target instanceof LivingEntity livingEntity && !EnchantmentUtil.isBow(user.getMainHandStack())) {
			pullInNearbyEntities(user.world, livingEntity.getPos(), level, List.of(user, livingEntity));
		}
	}
	@Override
	public boolean isTreasure() { return true; }
	@Override
	public boolean isAvailableForEnchantedBookOffer() { return false; }
	@Override
	public boolean isAvailableForRandomSelection() { return false; }

	public static boolean wearingShulkerHelmet(LivingEntity entity) { return entity.getEquippedStack(EquipmentSlot.HEAD).isOf(ModBase.SHULKER_HELMET); }
	public static boolean wearingShulkerChestplate(LivingEntity entity) { return entity.getEquippedStack(EquipmentSlot.CHEST).isOf(ModBase.SHULKER_CHESTPLATE); }
	public static boolean wearingShulkerLeggings(LivingEntity entity) { return entity.getEquippedStack(EquipmentSlot.LEGS).isOf(ModBase.SHULKER_LEGGINGS); }
	public static boolean wearingShulkerBoots(LivingEntity entity) { return entity.getEquippedStack(EquipmentSlot.FEET).isOf(ModBase.SHULKER_BOOTS); }
	public static boolean wearingShulkerArmor(LivingEntity entity) {
		return wearingShulkerHelmet(entity) || wearingShulkerChestplate(entity) || wearingShulkerLeggings(entity) || wearingShulkerBoots(entity);
	}

	public static void pullInNearbyEntities(World world, Vec3d pos, int level, List<LivingEntity> exclude) {
		double distance = level * 3;
		double force = (1 + (Math.abs(level) * 0.5)) * (level < 0 ? -1 : 1);
		List<LivingEntity> entities = world.getEntitiesByClass(LivingEntity.class,
				new Box(new BlockPos(pos)).expand(distance),
				entity -> !exclude.contains(entity)
						//Don't move shulkers (or shulker-armored entities)
						&& !wearingShulkerArmor(entity) && !(entity instanceof ShulkerEntity)
						//Don't move entities in spectator mode
						&& !entity.isSpectator()
						//Don't move players in creative mode
						&& !(entity instanceof PlayerEntity player && player.isCreative()));
		for (LivingEntity entity : entities) {
			Vec3d delta = new Vec3d(pos.x - entity.getX(), pos.y - entity.getY(), pos.z - entity.getZ());
			Vec3d normal = delta.normalize();
			double X = MathHelper.clamp(normal.x * force, -8, 8);
			double Y = MathHelper.clamp(normal.y * force, -3, -3);
			double Z = MathHelper.clamp(normal.z * force, -8, 8);
			entity.addVelocity(X, Y, Z);
			entity.velocityModified = true;
		}
	}
}
