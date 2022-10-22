package haven.util;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.entity.Entity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.TranslatableText;

public class CommandUtils {
	public static ServerPlayerEntity getPlayerOrThrow(ServerCommandSource source) throws CommandSyntaxException {
		Entity entity = source.getEntity();
		if (entity instanceof ServerPlayerEntity) {
			ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)entity;
			return serverPlayerEntity;
		}
		throw new SimpleCommandExceptionType(new TranslatableText("permissions.requires.player")).create();
	}
}
