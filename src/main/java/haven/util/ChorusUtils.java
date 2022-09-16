package haven.util;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ChorusUtils {
	public static void TeleportEntity(LivingEntity entity) {
		World world = entity.getEntityWorld();
		double d = entity.getX(), e = entity.getY(), f = entity.getZ();
		for(int i = 0; i < 16; ++i) {
			double g = entity.getX() + (entity.getRandom().nextDouble() - 0.5D) * 16.0D;
			double h = MathHelper.clamp(entity.getY() + (double)(entity.getRandom().nextInt(16) - 8), world.getBottomY(), (world.getBottomY() + world.getLogicalHeight() - 1));
			double j = entity.getZ() + (entity.getRandom().nextDouble() - 0.5D) * 16.0D;
			if (entity.hasVehicle()) entity.stopRiding();
			if (entity.teleport(g, h, j, true)) {
				SoundEvent soundEvent = entity instanceof FoxEntity ? SoundEvents.ENTITY_FOX_TELEPORT : SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT;
				world.playSound((PlayerEntity)null, d, e, f, soundEvent, SoundCategory.PLAYERS, 1.0F, 1.0F);
				entity.playSound(soundEvent, 1.0F, 1.0F);
				break;
			}
		}
	}
}
