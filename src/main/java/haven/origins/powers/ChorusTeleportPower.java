package haven.origins.powers;

import haven.HavenMod;
import haven.command.ChorusCommand;
import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.*;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.apoli.util.HudRender;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.LivingEntity;

public class ChorusTeleportPower extends CooldownPower implements Active {
	public ChorusTeleportPower(PowerType<?> type, LivingEntity entity, int cooldownDuration, HudRender hudRender) {
		super(type, entity, cooldownDuration, hudRender);
	}

	public static PowerFactory createFactory() {
		return new PowerFactory<>(HavenMod.ID("chorus_teleport"),
			new SerializableData()
					.add("key", ApoliDataTypes.BACKWARDS_COMPATIBLE_KEY, new Active.Key())
					.add("cooldown", SerializableDataTypes.INT, 1)
					.add("hud_render", ApoliDataTypes.HUD_RENDER, HudRender.DONT_RENDER),
				data -> (type, player) -> {
					ChorusTeleportPower power = new ChorusTeleportPower(type, player, data.getInt("cooldown"), (HudRender)data.get("hud_render"));
					power.setKey((Active.Key)data.get("key"));
					return power;
				}
			).allowCondition();
	}

	@Override
	public void onUse() {
		if(canUse()) {
			ChorusCommand.TeleportEntity(entity);
			use();
		}
	}

	private Key key;

	@Override
	public Key getKey() {
		return key;
	}

	@Override
	public void setKey(Key key) {
		this.key = key;
	}
}
