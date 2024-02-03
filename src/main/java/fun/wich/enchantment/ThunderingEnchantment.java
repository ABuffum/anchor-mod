package fun.wich.enchantment;

import fun.wich.util.EnchantmentUtil;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.*;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ThunderingEnchantment extends Enchantment {
	public ThunderingEnchantment(Rarity weight, EquipmentSlot... slots) { super(weight, EnchantmentTarget.WEAPON, slots); }
	@Override
	public int getMaxLevel() { return 3; }
	@Override
	public boolean isAcceptableItem(ItemStack stack) {
		Item item = stack.getItem();
		return item instanceof AxeItem || super.isAcceptableItem(stack);
	}
	@Override
	public void onTargetDamaged(LivingEntity user, Entity target, int level) {
		if (target instanceof LivingEntity livingEntity && !EnchantmentUtil.isBow(user.getMainHandStack())) {
			Strike(user.world, livingEntity.getBlockPos(), user, level);
		}
	}
	@Override
	public boolean isTreasure() { return true; }
	@Override
	public boolean isAvailableForEnchantedBookOffer() { return false; }
	@Override
	public boolean isAvailableForRandomSelection() { return false; }

	public static void Strike(World world, BlockPos pos, Entity source, int level) {
		if (!world.isClient && world.random.nextFloat() < (0.1F * Math.abs(level)) && world.isSkyVisible(pos)) {
			LightningEntity lightningEntity = EntityType.LIGHTNING_BOLT.create(world);
			if (lightningEntity != null) {
				lightningEntity.refreshPositionAfterTeleport(Vec3d.ofBottomCenter(pos));
				lightningEntity.setChanneler(source instanceof ServerPlayerEntity ? (ServerPlayerEntity)source : null);
				world.spawnEntity(lightningEntity);
			}
			world.playSound(null, pos, SoundEvents.ENTITY_LIGHTNING_BOLT_THUNDER, SoundCategory.WEATHER, 5.0f, 1.0f);
		}
	}
}
