package fun.wich.mixins.server;

import fun.wich.entity.RideableInventory;
import fun.wich.entity.passive.camel.CamelEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.JumpingMount;
import net.minecraft.network.NetworkThreadUtils;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayNetworkHandler.class)
public class ServerPlayNetworkHandlerMixin {
	@Shadow
	public ServerPlayerEntity player;

	@Inject(method = "onClientCommand", at = @At("HEAD"), cancellable = true)
	private void onClientCommand(ClientCommandC2SPacket packet, CallbackInfo ci) {
		if (packet.getMode() == ClientCommandC2SPacket.Mode.OPEN_INVENTORY) {
			Entity entity = this.player.getVehicle();
			if (entity instanceof RideableInventory rideableInventory) {
				rideableInventory.openInventory(this.player);
			}
		}
		else if (packet.getMode() == ClientCommandC2SPacket.Mode.START_RIDING_JUMP) {
			if (this.player.getVehicle() instanceof CamelEntity camel) {
				NetworkThreadUtils.forceMainThread(packet, (ServerPlayNetworkHandler)(Object)this, this.player.getServerWorld());
				this.player.updateLastActionTime();
				JumpingMount jumpingMount = (JumpingMount)(this.player.getVehicle());
				int i = packet.getMountJumpHeight();
				if (camel.canJump(this.player) || i > 0) jumpingMount.startJumping(i);
				ci.cancel();
			}
		}
	}
}
