package fun.wich.origins.power;

import fun.wich.ModId;
import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataType;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EnableTradePower extends Power {
	private final Set<EntityType<?>> entityTypes = new HashSet<>();

	public EnableTradePower(PowerType<?> type, LivingEntity entity, List<EntityType<?>> entityTypes) {
		super(type, entity);
		this.entityTypes.addAll(entityTypes);
	}
	public static PowerFactory<EnableTradePower> createFactory() {
		return new PowerFactory<EnableTradePower>(ModId.ID("enable_trade"), new SerializableData()
				.add("entity_types", SerializableDataType.list(SerializableDataTypes.ENTITY_TYPE), Collections.emptyList()),
				data -> (type, entity) -> new EnableTradePower(type, entity, (List<EntityType<?>>)data.get("entity_types")))
				.allowCondition();
	}

	public boolean canTrade(EntityType<?> entityType) { return entityTypes.contains(entityType); }

	public static boolean canTrade(LivingEntity target, EntityType<?> entityType) {
		for(EnableTradePower power : PowerHolderComponent.getPowers(target, EnableTradePower.class)) {
			if (power.isActive() && power.canTrade(entityType)) return true;
		}
		return false;
	}
}
