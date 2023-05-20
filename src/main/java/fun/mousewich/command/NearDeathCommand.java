package fun.mousewich.command;

import com.google.common.collect.ImmutableList;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

import java.util.Collection;

public class NearDeathCommand {
	public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean dedicated) {
		dispatcher.register(CommandManager.literal("neardeath")
				.requires(source -> source.hasPermissionLevel(2))
				.executes(context -> execute(ImmutableList.of(context.getSource().getEntityOrThrow())))
				.then(CommandManager.argument("targets", EntityArgumentType.entities())
						.executes(context -> execute(EntityArgumentType.getEntities(context, "targets")))));
	}

	private static int execute(Collection<? extends Entity> targets) {
		for (Entity entity : targets) Apply(entity);
		return targets.size();
	}
	public static void Apply(Entity entity) {
		if (entity instanceof LivingEntity livingEntity) {
			livingEntity.setHealth(1);
			if (livingEntity instanceof PlayerEntity player) {
				HungerManager hunger = player.getHungerManager();
				hunger.setFoodLevel(1);
				hunger.setExhaustion(0);
			}
		}
	}
}
