package fun.mousewich.util;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.TranslatableText;

public class CommandUtil {
	public static ServerPlayerEntity getPlayerOrThrow(ServerCommandSource source) throws CommandSyntaxException {
		if (source.getEntity() instanceof ServerPlayerEntity serverPlayerEntity) return serverPlayerEntity;
		throw new SimpleCommandExceptionType(new TranslatableText("permissions.requires.player")).create();
	}
}
