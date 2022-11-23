package haven.origins.powers;

import haven.ModBase;
import haven.blocks.lighting.DynamicLightManager;
import haven.blocks.lighting.DynamicLightSource;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;

public class IlluminatedPower extends Power implements DynamicLightSource {
	public final int light_level;
	public IlluminatedPower(PowerType<?> type, LivingEntity entity, int light_level) {
		super(type, entity);
		this.light_level = light_level;
		this.setTicking(true);
	}

	public static PowerFactory createFactory() {
		return new PowerFactory<>(ModBase.ID("illuminated"), new SerializableData()
				.add("light_level", SerializableDataTypes.INT, 1),
				data -> (type, player) -> new IlluminatedPower(type, player, data.getInt("light_level"))
		).allowCondition();
	}

	private void addLight() {
		DynamicLightManager.addLight(this);
		lightAdded = true;
	}
	private void removeLight() { DynamicLightManager.removeLight(this); }
	private boolean lightAdded = false;

	public void tick() {
		if (isActive()){
			if (!lightAdded) addLight();
		}
		else {
			removeLight();
			lightAdded = false;
		}
	}

	public void onGained() { addLight(); }

	public void onLost() { removeLight(); }

	public void onRespawn() { addLight(); }

	@Override
	public Entity getEntity() { return entity; }

	@Override
	public int getLightLevel() { return light_level; }
}