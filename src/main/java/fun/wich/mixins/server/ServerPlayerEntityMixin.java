package fun.wich.mixins.server;

import com.mojang.authlib.GameProfile;
import fun.wich.entity.LastDeathPositionStoring;
import fun.wich.event.ModGameEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.PlayerRespawnS2CPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.dynamic.GlobalPos;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.source.BiomeAccess;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends PlayerEntity implements LastDeathPositionStoring {
	@Shadow @Final public ServerPlayerInteractionManager interactionManager;

	public ServerPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile profile) { super(world, pos, yaw, profile); }

	@Inject(method="onDeath", at=@At("HEAD"))
	public void OnDeathHead(DamageSource source, CallbackInfo ci) { this.emitGameEvent(ModGameEvent.ENTITY_DIE); }
	@Inject(method="onDeath", at=@At("TAIL"))
	public void OnDeathTail(DamageSource source, CallbackInfo ci) {
		this.setLastDeathPos(Optional.of(GlobalPos.create(this.world.getRegistryKey(), this.getBlockPos())));
	}

	@Inject(method="copyFrom", at=@At("TAIL"))
	public void CopyLastDeathPos(ServerPlayerEntity oldPlayer, boolean alive, CallbackInfo ci) {
		this.setLastDeathPos(((LastDeathPositionStoring)oldPlayer).getLastDeathPos());
	}

	private Packet<?> pendingWorldMovePacket = null;
	@Inject(method="moveToWorld", at=@At(value="INVOKE", target="Lnet/minecraft/server/network/ServerPlayNetworkHandler;sendPacket(Lnet/minecraft/network/Packet;)V"))
	public void SaveLastDeathPositionOnMoveWorld(ServerWorld destination, CallbackInfoReturnable<Entity> cir) {
		pendingWorldMovePacket = new PlayerRespawnS2CPacket(destination.getDimension(),
				destination.getRegistryKey(), BiomeAccess.hashSeed(destination.getSeed()),
				this.interactionManager.getGameMode(), this.interactionManager.getPreviousGameMode(),
				destination.isDebugWorld(), destination.isFlat(), true);
		((LastDeathPositionStoring)pendingWorldMovePacket).setLastDeathPos(getLastDeathPos());
	}
	@Redirect(method="moveToWorld", at=@At(value="INVOKE", target="Lnet/minecraft/server/network/ServerPlayNetworkHandler;sendPacket(Lnet/minecraft/network/Packet;)V"))
	public void RedirectPendingWorldMovePacketWithLastDeathPos(ServerPlayNetworkHandler instance, Packet<?> packet) {
		if (packet instanceof PlayerRespawnS2CPacket && pendingWorldMovePacket != null) {
			instance.sendPacket(pendingWorldMovePacket);
			pendingWorldMovePacket = null;
		}
		else instance.sendPacket(packet);
	}


	private Packet<?> pendingTeleportPacket = null;
	@Inject(method="teleport", at=@At(value="INVOKE", target="Lnet/minecraft/server/network/ServerPlayNetworkHandler;sendPacket(Lnet/minecraft/network/Packet;)V"))
	public void SaveLastDeathPositionOnPlayerTeleport(ServerWorld targetWorld, double x, double y, double z, float yaw, float pitch, CallbackInfo ci) {
		pendingTeleportPacket = new PlayerRespawnS2CPacket(targetWorld.getDimension(), targetWorld.getRegistryKey(),
				BiomeAccess.hashSeed(targetWorld.getSeed()), this.interactionManager.getGameMode(),
				this.interactionManager.getPreviousGameMode(), targetWorld.isDebugWorld(),
				targetWorld.isFlat(), true);
		((LastDeathPositionStoring)pendingTeleportPacket).setLastDeathPos(getLastDeathPos());
	}
	@Redirect(method="teleport", at=@At(value="INVOKE", target="Lnet/minecraft/server/network/ServerPlayNetworkHandler;sendPacket(Lnet/minecraft/network/Packet;)V"))
	public void RedirectPendingTeleportPacketWithLastDeathPos(ServerPlayNetworkHandler instance, Packet<?> packet) {
		if (packet instanceof PlayerRespawnS2CPacket && pendingTeleportPacket != null) {
			instance.sendPacket(pendingTeleportPacket);
			pendingTeleportPacket = null;
		}
		else instance.sendPacket(packet);
	}
}
