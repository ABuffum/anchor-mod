package haven.events;

import haven.ModBase;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.event.GameEvent;

public class HavenGameEvent {
	public static final GameEvent BLOCK_ACTIVATE = RegisterGameEvent("minecraft:block_activate");
	public static final GameEvent BLOCK_DEACTIVATE = RegisterGameEvent("minecraft:block_deactivate");
	public static final GameEvent DRINK = RegisterGameEvent("minecraft:drink");
	public static final GameEvent ELYTRA_GLIDE = RegisterGameEvent("minecraft:elytra_glide");
	public static final GameEvent ENTITY_DAMAGE = RegisterGameEvent("minecraft:entity_damage");
	public static final GameEvent ENTITY_DIE = RegisterGameEvent("minecraft:entity_die");
	public static final GameEvent ENTITY_INTERACT = RegisterGameEvent("minecraft:entity_interact");
	public static final GameEvent ENTITY_ROAR = RegisterGameEvent("minecraft:entity_roar");
	public static final GameEvent ENTITY_SHAKE = RegisterGameEvent("minecraft:entity_shake");
	public static final GameEvent INSTRUMENT_PLAY = RegisterGameEvent("minecraft:instrument_play");
	public static final GameEvent ITEM_INTERACT_FINISH = RegisterGameEvent("minecraft:item_interact_finish");
	public static final GameEvent ITEM_INTERACT_START = RegisterGameEvent("minecraft:item_interact_start");
	public static final GameEvent NOTE_BLOCK_PLAY = RegisterGameEvent("minecraft:note_block_play");
	public static final GameEvent SCULK_SENSOR_TENDRILS_CLICKING = RegisterGameEvent("minecraft:sculk_sensor_tendrils_clicking");
	public static final GameEvent SHRIEK = RegisterGameEvent("minecraft:shriek", 32);
	public static final GameEvent TELEPORT = RegisterGameEvent("minecraft:teleport");
	public static GameEvent RegisterGameEvent(String id) { return RegisterGameEvent(id, 16); }
	public static GameEvent RegisterGameEvent(String id, int range) {
		return Registry.register(Registry.GAME_EVENT, ModBase.ID(id), new GameEvent(id, range));
	}
}
