package fun.wich.command;

import com.google.common.collect.ImmutableList;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.Collection;

public class ChorusCommand {
	public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean dedicated) {
		dispatcher.register(CommandManager.literal("chorus")
				.requires((source) -> source.hasPermissionLevel(2))
				.executes((context) -> execute(ImmutableList.of(context.getSource().getEntityOrThrow())))
				.then(CommandManager.argument("targets", EntityArgumentType.entities())
						.executes(context -> execute(EntityArgumentType.getEntities(context, "targets"))))
				.then(CommandManager.argument("chance", FloatArgumentType.floatArg(0, 1))
						.executes(context -> execute(EntityArgumentType.getEntities(context, "targets"), FloatArgumentType.getFloat(context, "chance")))));
	}

	private static int execute(Collection<? extends Entity> targets) {
		return execute(targets, 1);
	}
	private static int execute(Collection<? extends Entity> targets, float chance) {
		for (Entity entity : targets) {
			if (entity instanceof LivingEntity livingEntity) {
				if (livingEntity.getRandom().nextFloat() <= chance) TeleportEntity(livingEntity);
			}
		}
		return targets.size();
	}

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
