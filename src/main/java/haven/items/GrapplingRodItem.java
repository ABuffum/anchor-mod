package haven.items;

import haven.HavenMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.math.Vec3d;

public class GrapplingRodItem extends FishingRodItem {
	public GrapplingRodItem(Item.Settings settings) {
		super(settings);
	}

	public static void PullHookedEntity(PlayerEntity player, Entity entity, Entity fishingBobberEntity) {
		if (player != null) {
			ItemStack itemStack = player.getMainHandStack();
			ItemStack itemStack2 = player.getOffHandStack();
			boolean bl = itemStack.isOf(Items.FISHING_ROD) || itemStack2.isOf(Items.FISHING_ROD);
			if (bl){
				Vec3d vec3d = (new Vec3d(player.getX() - fishingBobberEntity.getX(), player.getY() - fishingBobberEntity.getY(), player.getZ() - fishingBobberEntity.getZ())).multiply(0.1D);
				entity.setVelocity(entity.getVelocity().add(vec3d));
			}
			else {
				Vec3d vec3d = (new Vec3d(fishingBobberEntity.getX() - player.getX(), fishingBobberEntity.getY() - player.getY(), fishingBobberEntity.getZ() - player.getZ()).multiply(0.5D));
				player.setVelocity(player.getVelocity().add(vec3d));
				player.velocityModified = true;
			}
		}
	}
	public static boolean RemoveIfInvalid(PlayerEntity player, Entity entity) {
		ItemStack itemStack = player.getMainHandStack();
		ItemStack itemStack2 = player.getOffHandStack();
		boolean bl = itemStack.getItem() instanceof FishingRodItem;//isOf(Items.FISHING_ROD) || itemStack.isOf(HavenMod.GRAPPLING_ROD);
		boolean bl2 = itemStack2.getItem() instanceof FishingRodItem;//isOf(Items.FISHING_ROD) || itemStack2.isOf(HavenMod.GRAPPLING_ROD);
		if (!player.isRemoved() && player.isAlive() && bl != bl2 && !(entity.squaredDistanceTo(player) > 1024.0D)) {
			return false;
		} else {
			entity.discard();
			return true;
		}
	}
}
