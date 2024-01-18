package fun.mousewich.origins.power;

import fun.mousewich.ModId;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

public class ElytraTexturePower extends Power {
	private final Identifier textureLocation;

	public ElytraTexturePower(PowerType<?> type, LivingEntity entity, Identifier textureLocation) {
		super(type, entity);
		this.textureLocation = textureLocation;
	}

	public Identifier getTextureLocation() { return textureLocation; }

	public static PowerFactory<ElytraTexturePower> createFactory() {
		return new PowerFactory<ElytraTexturePower>(ModId.ID("elytra_texture"), new SerializableData()
				.add("texture_location", SerializableDataTypes.IDENTIFIER),
				data -> (type, player) -> new ElytraTexturePower(type, player, data.getId("texture_location"))
		).allowCondition();
	}
}
