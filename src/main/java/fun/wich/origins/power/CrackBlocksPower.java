package fun.wich.origins.power;

import fun.wich.ModId;
import fun.wich.entity.hostile.warden.WardenEntity;
import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.RavagerEntity;
import net.minecraft.entity.passive.IronGolemEntity;

public class CrackBlocksPower extends Power {
	public final int distance; //Must be >= 1
	public CrackBlocksPower(PowerType<?> type, LivingEntity entity, int distance) {
		super(type, entity);
		this.distance = Math.max(1, distance);
	}

	public static PowerFactory<CrackBlocksPower> createFactory() {
		return new PowerFactory<CrackBlocksPower>(ModId.ID("crack_blocks"), new SerializableData()
				.add("distance", SerializableDataTypes.INT, 1),
				data -> (type, player) -> new CrackBlocksPower(type, player, data.getInt("distance"))
		).allowCondition();
	}

	public static int getDistance(Entity entity) {
		if (entity.isSneaking()) return 0;
		int distance = 0;
		for (CrackBlocksPower power : PowerHolderComponent.getPowers(entity, CrackBlocksPower.class)) {
			if (power.isActive()) distance = Math.max(distance, power.distance);
		}
		if (distance == 0) {
			if (entity instanceof RavagerEntity) distance = 12;
			else if (entity instanceof WardenEntity) distance = 12;
			else if (entity instanceof IronGolemEntity) distance = 12;
		}
		return distance;
	}
}