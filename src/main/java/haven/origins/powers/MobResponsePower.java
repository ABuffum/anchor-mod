package haven.origins.powers;

import haven.ModBase;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataType;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;

import java.util.List;

public class MobResponsePower extends Power {
	public final List<EntityType<?>> mobs;
	public final EntityResponse response;

	public static enum EntityResponse {
		HOSTILE,
		NEUTRAL,
		PASSIVE
	}

	public MobResponsePower(PowerType<?> type, LivingEntity entity, List<EntityType<?>> mobs, EntityResponse response) {
		super(type, entity);
		this.mobs = mobs;
		this.response = response;
	}

	public static PowerFactory createFactory() {
		return new PowerFactory<>(ModBase.ID("mob_response"), new SerializableData()
				.add("mobs", SerializableDataType.list(SerializableDataTypes.ENTITY_TYPE))
				.add("response", SerializableDataType.enumValue(EntityResponse.class)),
				data -> (type, player) -> new MobResponsePower(type, player, (List<EntityType<?>>)data.get("mobs"), (EntityResponse)data.get("behavior"))
		).allowCondition();
	}

	public static boolean Active(Entity entity) { return PowersUtil.Active(entity, MobResponsePower.class); }
}
