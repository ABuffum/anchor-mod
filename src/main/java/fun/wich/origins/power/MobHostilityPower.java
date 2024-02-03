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
import net.minecraft.entity.ai.goal.FollowTargetGoal;
import net.minecraft.entity.mob.MobEntity;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MobHostilityPower extends Power {
	private final Set<EntityType<?>> entityTypes = new HashSet<>();
	private final boolean inverted;

	public MobHostilityPower(PowerType<?> type, LivingEntity entity, List<EntityType<?>> entityTypes, boolean inverted) {
		super(type, entity);
		this.entityTypes.addAll(entityTypes);
		this.inverted = inverted;
	}
	public static PowerFactory<MobHostilityPower> createFactory() {
		return new PowerFactory<MobHostilityPower>(ModId.ID("mob_hostility"), new SerializableData()
				.add("entity_types", SerializableDataType.list(SerializableDataTypes.ENTITY_TYPE), Collections.emptyList())
				.add("inverted", SerializableDataTypes.BOOLEAN, false),
				data -> (type, entity) -> new MobHostilityPower(type, entity, (List<EntityType<?>>)data.get("entity_types"), data.getBoolean("inverted")))
				.allowCondition();
	}

	public boolean shouldBeHostile(EntityType<?> entityType) {
		if (inverted) return !entityTypes.contains(entityType);
		return entityTypes.contains(entityType);
	}

	public static boolean shouldBeHostile(LivingEntity target, EntityType<?> entityType) {
		for(MobHostilityPower power : PowerHolderComponent.getPowers(target, MobHostilityPower.class)) {
			if (power.shouldBeHostile(entityType)) return true;
		}
		return false;
	}
	public static FollowTargetGoal<LivingEntity> makeHostilityGoal(MobEntity attacker, EntityType<?> entityType) {
		return makeHostilityGoal(attacker, 10, true, false, entityType);
	}
	public static FollowTargetGoal<LivingEntity> makeHostilityGoal(MobEntity attacker, int reciprocalChance, boolean checkVisibility, boolean checkCanNavigate, EntityType<?> entityType) {
		return new FollowTargetGoal<>(attacker, LivingEntity.class, reciprocalChance, checkVisibility, checkCanNavigate, entity -> shouldBeHostile(entity, entityType));
	}
}
