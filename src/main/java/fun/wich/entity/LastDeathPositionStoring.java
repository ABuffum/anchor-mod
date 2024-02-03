package fun.wich.entity;

import net.minecraft.util.dynamic.GlobalPos;

import java.util.Optional;

public interface LastDeathPositionStoring {
	Optional<GlobalPos> getLastDeathPos();
	void setLastDeathPos(Optional<GlobalPos> lastDeathPos);
}
