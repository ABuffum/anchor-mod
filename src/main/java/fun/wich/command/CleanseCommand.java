package fun.wich.command;

import com.google.common.collect.ImmutableList;
import com.mojang.brigadier.CommandDispatcher;
import fun.wich.util.MilkUtil;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

import java.util.Collection;

public class CleanseCommand {
	public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean dedicated) {
		dispatcher.register(CommandManager.literal("cleanse")
				.requires((source) -> source.hasPermissionLevel(2))
				.executes((context) -> execute(ImmutableList.of(context.getSource().getEntityOrThrow())))
				.then(CommandManager.argument("targets", EntityArgumentType.entities())
						.executes((context) -> execute(EntityArgumentType.getEntities(context, "targets")))));
	}

	private static int execute(Collection<? extends Entity> targets) {
		for (Entity entity : targets) {
			if (entity instanceof LivingEntity livingEntity) MilkUtil.ClearStatusEffects(livingEntity.world, livingEntity);
		}
		return targets.size();
	}
}
