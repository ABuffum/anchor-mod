package fun.wich.mixins.server;

import com.mojang.authlib.GameProfile;
import fun.wich.entity.LastDeathPositionStoring;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.GameJoinS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerRespawnS2CPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.UserCache;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldProperties;
import net.minecraft.world.biome.source.BiomeAccess;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Optional;

@Mixin(PlayerManager.class)
public abstract class PlayerManagerMixin {
	@Shadow private int viewDistance;
	@Shadow @Final private MinecraftServer server;
	@Shadow public abstract int getMaxPlayerCount();

	@Shadow @Final private DynamicRegistryManager.Impl registryManager;
	private Packet<?> pendingJoinPacket = null;
	@Inject(method="onPlayerConnect", locals= LocalCapture.CAPTURE_FAILHARD, at=@At(value="INVOKE", shift=At.Shift.BEFORE, target="Lnet/minecraft/server/network/ServerPlayNetworkHandler;sendPacket(Lnet/minecraft/network/Packet;)V"))
	public void SaveLastDeathPositionOnPlayerConnect(ClientConnection connection, ServerPlayerEntity player, CallbackInfo ci, GameProfile gameProfile, UserCache userCache, Optional<GameProfile> optional, String string, NbtCompound nbtCompound, RegistryKey<World> registryKey, ServerWorld serverWorld, ServerWorld serverWorld2, String string2, WorldProperties worldProperties, ServerPlayNetworkHandler serverPlayNetworkHandler) {
		GameRules gameRules = serverWorld2.getGameRules();
		boolean bl = gameRules.getBoolean(GameRules.DO_IMMEDIATE_RESPAWN);
		boolean bl2 = gameRules.getBoolean(GameRules.REDUCED_DEBUG_INFO);
		pendingJoinPacket = new GameJoinS2CPacket(player.getId(), player.interactionManager.getGameMode(),
				player.interactionManager.getPreviousGameMode(), BiomeAccess.hashSeed(serverWorld2.getSeed()),
				worldProperties.isHardcore(), this.server.getWorldRegistryKeys(),
				this.registryManager, serverWorld2.getDimension(), serverWorld2.getRegistryKey(),
				this.getMaxPlayerCount(), this.viewDistance, bl2, !bl,
				serverWorld2.isDebugWorld(), serverWorld2.isFlat());
		((LastDeathPositionStoring)pendingJoinPacket).setLastDeathPos(((LastDeathPositionStoring)player).getLastDeathPos());
	}
	@Redirect(method="onPlayerConnect", at=@At(value="INVOKE", target="Lnet/minecraft/server/network/ServerPlayNetworkHandler;sendPacket(Lnet/minecraft/network/Packet;)V"))
	public void RedirectPendingJoinPacketWithLastDeathPos(ServerPlayNetworkHandler instance, Packet<?> packet) {
		if (packet instanceof GameJoinS2CPacket && pendingJoinPacket != null) {
			instance.sendPacket(pendingJoinPacket);
			pendingJoinPacket = null;
		}
		else instance.sendPacket(packet);
	}

	private Packet<?> pendingRespawnPacket = null;
	@Inject(method="respawnPlayer", locals=LocalCapture.CAPTURE_FAILHARD, at=@At(value="INVOKE", shift=At.Shift.BEFORE, target="Lnet/minecraft/server/network/ServerPlayNetworkHandler;sendPacket(Lnet/minecraft/network/Packet;)V"))
	public void SaveLastDeathPositionOnPlayerRespawn(ServerPlayerEntity player, boolean alive, CallbackInfoReturnable<ServerPlayerEntity> cir, BlockPos blockPos, float f, boolean bl, ServerWorld serverWorld, Optional<Object> optional, ServerWorld serverWorld2, ServerPlayerEntity serverPlayerEntity, boolean bl2) {
		pendingRespawnPacket = new PlayerRespawnS2CPacket(serverPlayerEntity.world.getDimension(),
				serverPlayerEntity.world.getRegistryKey(),
				BiomeAccess.hashSeed(serverPlayerEntity.getServerWorld().getSeed()),
				serverPlayerEntity.interactionManager.getGameMode(),
				serverPlayerEntity.interactionManager.getPreviousGameMode(),
				serverPlayerEntity.getServerWorld().isDebugWorld(),
				serverPlayerEntity.getServerWorld().isFlat(), alive);
		((LastDeathPositionStoring)pendingRespawnPacket).setLastDeathPos(((LastDeathPositionStoring)player).getLastDeathPos());
	}
	@Redirect(method="respawnPlayer", at=@At(value="INVOKE", target="Lnet/minecraft/server/network/ServerPlayNetworkHandler;sendPacket(Lnet/minecraft/network/Packet;)V"))
	public void RedirectPendingRespawnPacketWithLastDeathPos(ServerPlayNetworkHandler instance, Packet<?> packet) {
		if (packet instanceof PlayerRespawnS2CPacket && pendingRespawnPacket != null) {
			instance.sendPacket(pendingRespawnPacket);
			pendingRespawnPacket = null;
		}
		else instance.sendPacket(packet);
	}
}
