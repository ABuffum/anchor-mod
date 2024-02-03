package fun.wich.origins.power;

import fun.wich.ModId;
import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;

public class VillagerPriceModifierPower extends Power {
	public final int factor;
	public VillagerPriceModifierPower(PowerType<?> type, LivingEntity entity, int factor) {
		super(type, entity);
		this.factor = factor;
	}

	public static PowerFactory<VillagerPriceModifierPower> createFactory() {
		return new PowerFactory<VillagerPriceModifierPower>(ModId.ID("villager_price_modifier"), new SerializableData()
				.add("factor", SerializableDataTypes.INT, 1),
				data -> (type, player) -> new VillagerPriceModifierPower(type, player, data.getInt("factor"))
		).allowCondition();
	}

	public static int getFactor(Entity entity) {
		int factor = 0;
		for (VillagerPriceModifierPower power : PowerHolderComponent.getPowers(entity, VillagerPriceModifierPower.class)) factor += power.factor;
		return factor;
	}
}