package fun.wich.mixins.server;

import fun.wich.entity.LastDeathPositionStoring;
import net.minecraft.network.packet.s2c.play.PlayerRespawnS2CPacket;
import net.minecraft.util.dynamic.GlobalPos;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Optional;

@Mixin(PlayerRespawnS2CPacket.class)
public class PlayerRespawnS2CPacketMixin implements LastDeathPositionStoring {
	private Optional<GlobalPos> lastDeathPos = Optional.empty();
	@Override public Optional<GlobalPos> getLastDeathPos() { return this.lastDeathPos; }
	@Override public void setLastDeathPos(Optional<GlobalPos> lastDeathPos) { this.lastDeathPos = lastDeathPos; }
}
