package fun.mousewich.origins.power;

import fun.mousewich.ModBase;
import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;

public class DragonNeutralityPower extends Power {
	public final boolean launch;
	public final boolean damage;
	public DragonNeutralityPower(PowerType<?> type, LivingEntity entity, boolean launch, boolean damage) {
		super(type, entity);
		this.launch = launch;
		this.damage = damage;
	}
	public static PowerFactory<DragonNeutralityPower> createFactory() {
		return new PowerFactory<DragonNeutralityPower>(ModBase.ID("dragon_neutrality"), new SerializableData()
				.add("launch", SerializableDataTypes.BOOLEAN, true)
				.add("damage", SerializableDataTypes.BOOLEAN, true),
				data -> (type, entity) -> new DragonNeutralityPower(type, entity, data.getBoolean("launch"), data.getBoolean("damage"))).allowCondition();
	}
	public static boolean ShouldNotLaunch(Entity entity) {
		return PowerHolderComponent.getPowers(entity, DragonNeutralityPower.class).stream().anyMatch(power -> !power.launch);
	}
	public static boolean ShouldNotDamage(Entity entity) {
		return PowerHolderComponent.getPowers(entity, DragonNeutralityPower.class).stream().anyMatch(power -> !power.damage);
	}
}