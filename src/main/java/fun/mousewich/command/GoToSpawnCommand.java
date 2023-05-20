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
	}

	private static int execute(Collection<? extends Entity> targets) {
		for (Entity entity : targets) {
			if (entity instanceof ServerPlayerEntity player) GoToSpawn(player);
		}
		return targets.size();
	}

	public static void GoToSpawn(ServerPlayerEntity player) {
		ServerWorld world = player.server.getWorld(player.getSpawnPointDimension());
		BlockPos pos = player.getSpawnPointPosition();
		Optional<Vec3d> optional = world != null && pos != null ? PlayerEntity.findRespawnPosition(world, pos, player.getSpawnAngle(), player.isSpawnPointSet(), true) : Optional.empty();
		double x, y, z;
		if (optional.isPresent()) {
			Vec3d vec3d = optional.get();
			x = vec3d.getX();
			y = vec3d.getY();
			z = vec3d.getZ();
		}
		else {
			world = player.server.getOverworld();
			pos = world.getSpawnPos();
			x = pos.getX();
			y = pos.getY();
			z = pos.getZ();
			player.sendMessage(new TranslatableText("block.minecraft.spawn.not_valid"), false);
		}
		player.teleport(world, x, y, z, player.prevYaw, player.prevPitch);
	}
}
