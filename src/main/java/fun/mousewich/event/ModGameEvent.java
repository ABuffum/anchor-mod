package fun.mousewich.event;

import fun.mousewich.ModId;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.event.GameEvent;

public class ModGameEvent {
	public static final GameEvent BLOCK_ACTIVATE = RegisterGameEvent("minecraft:block_activate");
	public static final GameEvent BLOCK_DEACTIVATE = RegisterGameEvent("minecraft:block_deactivate");
	public static final GameEvent DRINK = RegisterGameEvent("minecraft:drink");
	public static final GameEvent ELYTRA_GLIDE = RegisterGameEvent("minecraft:elytra_glide");
	public static final GameEvent ENTITY_DIE = RegisterGameEvent("minecraft:entity_die");
	public static final GameEvent ENTITY_DISMOUNT = RegisterGameEvent("entity_dismount");
	public static final GameEvent ENTITY_INTERACT = RegisterGameEvent("minecraft:entity_interact");
	public static final GameEvent ENTITY_MOUNT = RegisterGameEvent("entity_mount");
	public static final GameEvent ENTITY_ROAR = RegisterGameEvent("minecraft:entity_roar");
	public static final GameEvent ENTITY_SHAKE = RegisterGameEvent("minecraft:entity_shake");
	public static final GameEvent INSTRUMENT_PLAY = RegisterGameEvent("minecraft:instrument_play");
	public static final GameEvent ITEM_INTERACT_FINISH = RegisterGameEvent("minecraft:item_interact_finish");
	public static final GameEvent ITEM_INTERACT_START = RegisterGameEvent("minecraft:item_interact_start");
	public static final GameEvent JUKEBOX_PLAY = RegisterGameEvent("minecraft:jukebox_play", 10);
	public static final GameEvent JUKEBOX_STOP_PLAY = RegisterGameEvent("minecraft:jukebox_stop_play", 10);
	public static final GameEvent NOTE_BLOCK_PLAY = RegisterGameEvent("minecraft:note_block_play");
	public static final GameEvent SCULK_SENSOR_TENDRILS_CLICKING = RegisterGameEvent("minecraft:sculk_sensor_tendrils_clicking");
	public static final GameEvent SHRIEK = RegisterGameEvent("minecraft:shriek", 32);
	public static final GameEvent TELEPORT = RegisterGameEvent("minecraft:teleport");
	public static final GameEvent RESONATE_1 = RegisterGameEvent("resonate_1");
	public static final GameEvent RESONATE_2 = RegisterGameEvent("resonate_2");
	public static final GameEvent RESONATE_3 = RegisterGameEvent("resonate_3");
	public static final GameEvent RESONATE_4 = RegisterGameEvent("resonate_4");
	public static final GameEvent RESONATE_5 = RegisterGameEvent("resonate_5");
	public static final GameEvent RESONATE_6 = RegisterGameEvent("resonate_6");
	public static final GameEvent RESONATE_7 = RegisterGameEvent("resonate_7");
	public static final GameEvent RESONATE_8 = RegisterGameEvent("resonate_8");
	public static final GameEvent RESONATE_9 = RegisterGameEvent("resonate_9");
	public static final GameEvent RESONATE_10 = RegisterGameEvent("resonate_10");
	public static final GameEvent RESONATE_11 = RegisterGameEvent("resonate_11");
	public static final GameEvent RESONATE_12 = RegisterGameEvent("resonate_12");
	public static final GameEvent RESONATE_13 = RegisterGameEvent("resonate_13");
	public static final GameEvent RESONATE_14 = RegisterGameEvent("resonate_14");
	public static final GameEvent RESONATE_15 = RegisterGameEvent("resonate_15");
	public static GameEvent RegisterGameEvent(String id) { return RegisterGameEvent(id, 16); }
	public static GameEvent RegisterGameEvent(String id, int range) {
		return Registry.register(Registry.GAME_EVENT, ModId.ID(id), new GameEvent(id, range));
	}
	public static void RegisterAll() {
		GameEvent[] events = new GameEvent[] {
				BLOCK_ACTIVATE, BLOCK_DEACTIVATE, DRINK, ELYTRA_GLIDE, ENTITY_DIE, ENTITY_INTERACT,
				ENTITY_ROAR, ENTITY_SHAKE, INSTRUMENT_PLAY, ITEM_INTERACT_FINISH, ITEM_INTERACT_START, JUKEBOX_PLAY,
				JUKEBOX_STOP_PLAY, NOTE_BLOCK_PLAY, SCULK_SENSOR_TENDRILS_CLICKING, SHRIEK, TELEPORT,
				//Resonate Events
				RESONATE_1, RESONATE_2, RESONATE_3, RESONATE_4, RESONATE_5, RESONATE_6, RESONATE_7, RESONATE_8,
				RESONATE_9, RESONATE_10, RESONATE_11, RESONATE_12, RESONATE_13, RESONATE_14, RESONATE_15
		};
	}
}
