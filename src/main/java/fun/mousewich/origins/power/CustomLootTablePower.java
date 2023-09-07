package fun.mousewich.origins.power;

import fun.mousewich.ModBase;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

public class CustomLootTablePower extends Power {
	private final Identifier lootTable;

	public CustomLootTablePower(PowerType<?> type, LivingEntity entity, Identifier lootTable) {
		super(type, entity);
		this.lootTable = lootTable;
	}

	public Identifier getLootTable() { return lootTable; }

	public static PowerFactory<CustomLootTablePower> createFactory() {
		return new PowerFactory<CustomLootTablePower>(ModBase.ID("custom_loot_table"), new SerializableData()
				.add("loot_table", SerializableDataTypes.IDENTIFIER),
				data -> (type, player) -> new CustomLootTablePower(type, player, data.getId("loot_table"))
		).allowCondition();
	}
}
