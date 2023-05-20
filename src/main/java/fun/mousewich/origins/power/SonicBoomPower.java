package fun.mousewich.origins.power;

import fun.mousewich.ModBase;
import fun.mousewich.entity.hostile.warden.SonicBoomTask;
import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.Active;
import io.github.apace100.apoli.power.CooldownPower;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.apoli.util.HudRender;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

public class SonicBoomPower extends CooldownPower implements Active {
	private final float damage;
	public float getDamage() { return damage; }
	public SonicBoomPower(PowerType<?> type, LivingEntity entity, int cooldownDuration, HudRender hudRender, float damage) {
		super(type, entity, cooldownDuration, hudRender);
		this.damage = damage;
	}

	public static PowerFactory<SonicBoomPower> createFactory() {
		return new PowerFactory<SonicBoomPower>(ModBase.ID("sonic_boom"),
			new SerializableData()
					.add("key", ApoliDataTypes.BACKWARDS_COMPATIBLE_KEY, new Key())
					.add("cooldown", SerializableDataTypes.INT, 1)
					.add("hud_render", ApoliDataTypes.HUD_RENDER, HudRender.DONT_RENDER)
					.add("damage", SerializableDataTypes.FLOAT, 10f),
				data -> (type, player) -> {
					SonicBoomPower power = new SonicBoomPower(type, player, data.getInt("cooldown"), (HudRender)data.get("hud_render"), data.getFloat("damage"));
					power.setKey((Active.Key)data.get("key"));
					return power;
				}
			).allowCondition();
	}

	@Override
	public void onUse() {
		if (canUse()) {
			ServerWorld serverWorld = null;
			World world = entity.getEntityWorld();
			if (world instanceof ServerWorld sw) serverWorld = sw;
			else {
				try { //noinspection ConstantConditions
					serverWorld = entity.getServer().getWorld(world.getRegistryKey()); }
				catch(Exception ignored) { }
			}
			if (serverWorld != null) SonicBoomTask.SonicBoom(entity, serverWorld, damage);
			use();
		}
	}
	private Key key;
	@Override
	public Key getKey() { return key; }
	@Override
	public void setKey(Key key) { this.key = key; }
}
