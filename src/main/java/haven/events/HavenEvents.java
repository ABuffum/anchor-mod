package haven.events;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.event.GameEvent;

import static haven.HavenMod.ID;

public class HavenEvents {
	public static GameEvent INSTRUMENT_PLAY = RegisterGameEvent("instrument_play");
	public static GameEvent RegisterGameEvent(String id) { return RegisterGameEvent(id, 16); }
	public static GameEvent RegisterGameEvent(String id, int range) {
		return Registry.register(Registry.GAME_EVENT, ID(id), new GameEvent(id, 16));
	}
}
