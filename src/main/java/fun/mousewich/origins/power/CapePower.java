package fun.mousewich.origins.power;

import fun.mousewich.ModId;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

public class CapePower extends Power {
	private final Identifier textureLocation;

	public CapePower(PowerType<?> type, LivingEntity entity, Identifier textureLocation) {
		super(type, entity);
		this.textureLocation = textureLocation;
	}

	public Identifier getTextureLocation() { return textureLocation; }

	public static PowerFactory<CapePower> createFactory() {
		return new PowerFactory<CapePower>(ModId.ID("cape"), new SerializableData()
				.add("texture_location", SerializableDataTypes.IDENTIFIER),
				data -> (type, player) -> new CapePower(type, player, data.getId("texture_location"))
		).allowCondition();
	}
}