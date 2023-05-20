package fun.mousewich.command;

import com.google.common.collect.ImmutableList;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import fun.mousewich.block.sculk.SculkShriekerWarningManager;
import fun.mousewich.util.CommandUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.TranslatableText;

import java.util.Collection;

public class WardenSpawnTrackerCommand {
	public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean dedicated) {
		dispatcher.register(CommandManager.literal("warden_spawn_tracker")
				.requires(source -> source.hasPermissionLevel(2))
				.then(CommandManager.literal("clear").executes(context ->
						WardenSpawnTrackerCommand.clearTracker(
								context.getSource(),
								ImmutableList.of(CommandUtil.getPlayerOrThrow(context.getSource())))))
				.then(CommandManager.literal("set")
						.then(CommandManager.argument("warning_level", IntegerArgumentType.integer(0, 4))
								.executes(context -> WardenSpawnTrackerCommand.setWarningLevel(context.getSource(),
										ImmutableList.of(CommandUtil.getPlayerOrThrow(context.getSource())),
										IntegerArgumentType.getInteger(context, "warning_level"))))));
	}

	private static int setWarningLevel(ServerCommandSource source, Collection<? extends PlayerEntity> players, int warningCount) {
		for (PlayerEntity playerEntity : players) SculkShriekerWarningManager.getSculkShriekerWarningManager(playerEntity).setWarningLevel(warningCount);
		if (players.size() == 1) {
			source.sendFeedback(new TranslatableText("commands.warden_spawn_tracker.set.success.single", players.iterator().next().getDisplayName()), true);
		}
		else source.sendFeedback(new TranslatableText("commands.warden_spawn_tracker.set.success.multiple", players.size()), true);
		return players.size();
	}

	private static int clearTracker(ServerCommandSource source, Collection<? extends PlayerEntity> players) {
		for (PlayerEntity playerEntity : players) SculkShriekerWarningManager.getSculkShriekerWarningManager(playerEntity).reset();
		if (players.size() == 1) {
			source.sendFeedback(new TranslatableText("commands.warden_spawn_tracker.clear.success.single", players.iterator().next().getDisplayName()), true);
		}
		else source.sendFeedback(new TranslatableText("commands.warden_spawn_tracker.clear.success.multiple", players.size()), true);
		return players.size();
	}
}