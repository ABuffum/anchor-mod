package haven.events;

import haven.HavenMod;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.event.GameEvent;

public class HavenGameEvent {
	public static final GameEvent BLOCK_ACTIVATE = RegisterGameEvent("block_activate");
	public static final GameEvent BLOCK_DEACTIVATE = RegisterGameEvent("block_deactivate");
	public static final GameEvent DRINK = RegisterGameEvent("drink");
	public static final GameEvent ELYTRA_GLIDE = RegisterGameEvent("elytra_glide");
	public static final GameEvent ENTITY_DAMAGE = RegisterGameEvent("entity_damage");
	public static final GameEvent ENTITY_DIE = RegisterGameEvent("entity_die");
	public static final GameEvent ENTITY_INTERACT = RegisterGameEvent("entity_interact");
	public static final GameEvent ENTITY_ROAR = RegisterGameEvent("entity_roar");
	public static final GameEvent ENTITY_SHAKE = RegisterGameEvent("entity_shake");
	public static GameEvent INSTRUMENT_PLAY = RegisterGameEvent("instrument_play");
	public static final GameEvent ITEM_INTERACT_FINISH = RegisterGameEvent("item_interact_finish");
	public static final GameEvent ITEM_INTERACT_START = RegisterGameEvent("item_interact_start");
	public static final GameEvent NOTE_BLOCK_PLAY = RegisterGameEvent("note_block_play");
	public static final GameEvent SCULK_SENSOR_TENDRILS_CLICKING = RegisterGameEvent("sculk_sensor_tendrils_clicking");
	public static final GameEvent SHRIEK = RegisterGameEvent("shriek", 32);
	public static final GameEvent TELEPORT = RegisterGameEvent("teleport");
	public static GameEvent RegisterGameEvent(String id) { return RegisterGameEvent(id, 16); }
	public static GameEvent RegisterGameEvent(String id, int range) {
		return Registry.register(Registry.GAME_EVENT, HavenMod.ID(id), new GameEvent(id, 16));
	}
}
