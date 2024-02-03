package fun.wich.origins.power;

import fun.wich.ModId;
import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.apoli.power.factory.condition.ConditionFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

import java.util.function.Predicate;

public class IncreaseDurabilityPower extends Power {
	public final Predicate<ItemStack> item_condition;
	public final float factor; //Must be >= 0
	public IncreaseDurabilityPower(PowerType<?> type, LivingEntity entity, Predicate<ItemStack> item_condition, float factor) {
		super(type, entity);
		this.item_condition = item_condition;
		this.factor = Math.max(0, factor);
	}
	public static PowerFactory<IncreaseDurabilityPower> createFactory() {
		return new PowerFactory<IncreaseDurabilityPower>(ModId.ID("increase_durability"), new SerializableData()
				.add("item_condition", ApoliDataTypes.ITEM_CONDITION, null)
				.add("factor", SerializableDataTypes.FLOAT, 0.75F),
				data -> (type, entity) -> new IncreaseDurabilityPower(type, entity,
						(data.isPresent("item_condition") ? (ConditionFactory<ItemStack>.Instance)data.get("item_condition") : item -> false),
						data.getFloat("factor")))
				.allowCondition();
	}
	public static float GetFactor(Entity entity, ItemStack stack) {
		float factor = -1;
		for (IncreaseDurabilityPower power : PowerHolderComponent.getPowers(entity, IncreaseDurabilityPower.class)) {
			if (power.isActive()) {
				if (power.item_condition.test(stack)) factor = Math.max(factor, power.factor);
			}
		}
		return factor;
	}
}
