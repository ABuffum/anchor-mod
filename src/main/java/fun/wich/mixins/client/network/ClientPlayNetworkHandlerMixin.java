package fun.wich.mixins.client.network;

import fun.wich.entity.LastDeathPositionStoring;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.GameJoinS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerRespawnS2CPacket;
import net.minecraft.util.dynamic.GlobalPos;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class ClientPlayNetworkHandlerMixin implements ClientPlayPacketListener {
	@Shadow @Final private MinecraftClient client;

	@Inject(method="onGameJoin", at=@At("TAIL"))
	public void StoreLastDeathOnPlayerJoin(GameJoinS2CPacket packet, CallbackInfo ci) {
		Optional<GlobalPos> pos = ((LastDeathPositionStoring)(Object)packet).getLastDeathPos();
		if (pos.isPresent()) ((LastDeathPositionStoring)this.client.player).setLastDeathPos(pos);
	}
	@Inject(method="onPlayerRespawn", at=@At("TAIL"))
	public void StoreLastDeathOnPlayerJoin(PlayerRespawnS2CPacket packet, CallbackInfo ci) {
		Optional<GlobalPos> pos = ((LastDeathPositionStoring)(Object)packet).getLastDeathPos();
		if (pos.isPresent()) ((LastDeathPositionStoring)this.client.player).setLastDeathPos(pos);
	}
}
