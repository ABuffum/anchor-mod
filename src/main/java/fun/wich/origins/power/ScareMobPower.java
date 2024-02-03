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
import net.minecraft.entity.ai.goal.FleeEntityGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.predicate.entity.EntityPredicates;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ScareMobPower extends Power {
	private final Set<EntityType<?>> entityTypes = new HashSet<>();
	private final boolean inverted;

	public ScareMobPower(PowerType<?> type, LivingEntity entity, List<EntityType<?>> entityTypes, boolean inverted) {
		super(type, entity);
		this.entityTypes.addAll(entityTypes);
		this.inverted = inverted;
	}
	public static PowerFactory<ScareMobPower> createFactory() {
		return new PowerFactory<ScareMobPower>(ModId.ID("scare_mob"), new SerializableData()
				.add("entity_types", SerializableDataType.list(SerializableDataTypes.ENTITY_TYPE), Collections.emptyList())
				.add("inverted", SerializableDataTypes.BOOLEAN, false),
				data -> (type, entity) -> new ScareMobPower(type, entity, (List<EntityType<?>>)data.get("entity_types"), data.getBoolean("inverted")))
				.allowCondition();
	}

	public boolean shouldBeScared(EntityType<?> entityType) {
		if (inverted) return !entityTypes.contains(entityType);
		return entityTypes.contains(entityType);
	}

	public static boolean shouldBeScared(LivingEntity target, EntityType<?> entityType) {
		for(ScareMobPower power : PowerHolderComponent.getPowers(target, ScareMobPower.class)) {
			if (power.shouldBeScared(entityType)) return true;
		}
		return false;
	}

	public static FleeEntityGoal<LivingEntity> makeFleeGoal(PathAwareEntity fleeer, float distance, double slow, double fast, EntityType<?> entityType) {
		return new FleeEntityGoal<>(fleeer, LivingEntity.class, entity -> shouldBeScared(entity, entityType), distance, 1.6D, 1.4D, EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR::test);
	}
}
