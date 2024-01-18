package fun.mousewich.origins.power;

import fun.mousewich.ModId;
import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;

public class SoulSpeedPower extends Power {
	public final int level; //Must be >= 1
	public SoulSpeedPower(PowerType<?> type, LivingEntity entity, int level) {
		super(type, entity);
		this.level = Math.max(1, level);
	}

	public static PowerFactory<SoulSpeedPower> createFactory() {
		return new PowerFactory<SoulSpeedPower>(ModId.ID("soul_speed"), new SerializableData()
				.add("level", SerializableDataTypes.INT, 1),
				data -> (type, player) -> new SoulSpeedPower(type, player, data.getInt("level"))
		).allowCondition();
	}

	public static int getLevel(Entity entity) {
		int level = 0;
		for (SoulSpeedPower power : PowerHolderComponent.getPowers(entity, SoulSpeedPower.class)) {
			if (power.isActive()) level = Math.max(level, power.level);
		}
		return level + (entity instanceof LivingEntity living ? EnchantmentHelper.getEquipmentLevel(Enchantments.SOUL_SPEED, living) : 0);
	}
}