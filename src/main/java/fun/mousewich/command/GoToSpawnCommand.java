package fun.mousewich.command;

import com.google.common.collect.ImmutableList;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.Collection;
import java.util.Optional;

public class GoToSpawnCommand {
	public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean dedicated) {
		dispatcher.register(CommandManager.literal("gotospawn")
				.requires(source -> source.hasPermissionLevel(2))
				.executes(context -> execute(ImmutableList.of(context.getSource().getEntityOrThrow())))
				.then(CommandManager.argument("targets", EntityArgumentType.entities())
						.executes(context -> execute(EntityArgumentType.getEntities(context, "targets")))));
		dispatcher.register(CommandManager.literal("gotoworldspawn")
				.requires(source -> source.hasPermissionLevel(2))
				.executes(context -> executeWorldSpawn(ImmutableList.of(context.getSource().getEntityOrThrow())))
				.then(CommandManager.argument("targets", EntityArgumentType.entities())
						.executes(context -> executeWorldSpawn(EntityArgumentType.getEntities(context, "targets")))));
	}
	private static int execute(Collection<? extends Entity> targets) {
		for (Entity entity : targets) if (entity instanceof ServerPlayerEntity player) GoToSpawn(player);
		return targets.size();
	}
	private static int executeWorldSpawn(Collection<? extends Entity> targets) {
		for (Entity entity : targets) if (entity instanceof ServerPlayerEntity player) GoToWorldSpawn(player);
		return targets.size();
	}
	public static void GoToSpawn(ServerPlayerEntity player) {
		ServerWorld world = player.server.getWorld(player.getSpawnPointDimension());
		BlockPos pos = player.getSpawnPointPosition();
		Optional<Vec3d> optional = world != null && pos != null ? PlayerEntity.findRespawnPosition(world, pos, player.getSpawnAngle(), player.isSpawnPointSet(), true) : Optional.empty();
		if (optional.isPresent()) {
			Vec3d vec3d = optional.get();
			player.teleport(world, vec3d.getX(), vec3d.getY(), vec3d.getZ(), player.prevYaw, player.prevPitch);
		}
		else {
			player.sendMessage(new TranslatableText("block.minecraft.spawn.not_valid"), false);
			GoToWorldSpawn(player);
		}
	}
	public static void GoToWorldSpawn(ServerPlayerEntity player) {
		ServerWorld world = player.server.getOverworld();
		BlockPos pos = world.getSpawnPos();
		player.teleport(world, pos.getX(), pos.getY(), pos.getZ(), player.prevYaw, player.prevPitch);
	}
}
