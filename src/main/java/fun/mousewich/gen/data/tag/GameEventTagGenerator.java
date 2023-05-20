package fun.mousewich.gen.data.tag;

import fun.mousewich.event.ModGameEvent;
import fun.mousewich.gen.data.ModDatagen;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.tag.GameEventTags;
import net.minecraft.tag.Tag;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.event.GameEvent;

import java.util.Map;
import java.util.Set;

import static fun.mousewich.ModBase.NAMESPACE;

public class GameEventTagGenerator extends FabricTagProvider<GameEvent> {
	public GameEventTagGenerator(FabricDataGenerator dataGenerator) {
		super(dataGenerator, Registry.GAME_EVENT, "game_events", NAMESPACE + ":game_event_tag_generator");
	}
	@Override
	protected void generateTags() {
		for (Map.Entry<Tag.Identified<GameEvent>, Set<GameEvent>> entry : ModDatagen.Cache.Tags.GAME_EVENT_TAGS.entrySet()) {
			getOrCreateTagBuilder(entry.getKey()).add(entry.getValue().toArray(GameEvent[]::new));
			entry.getValue().clear();
		}
		ModDatagen.Cache.Tags.GAME_EVENT_TAGS.clear();

		getOrCreateTagBuilder(ModGameEventTags.ALLAY_CAN_LISTEN).add(ModGameEvent.NOTE_BLOCK_PLAY);
		getOrCreateTagBuilder(GameEventTags.IGNORE_VIBRATIONS_SNEAKING)
				.add(GameEvent.HIT_GROUND, GameEvent.PROJECTILE_SHOOT, GameEvent.STEP, GameEvent.SWIM)
				.add(ModGameEvent.ITEM_INTERACT_START, ModGameEvent.ITEM_INTERACT_FINISH);
		getOrCreateTagBuilder(ModGameEventTags.SHRIEKER_CAN_LISTEN).add(ModGameEvent.SCULK_SENSOR_TENDRILS_CLICKING);
		getOrCreateTagBuilder(GameEventTags.VIBRATIONS)
				.add(GameEvent.BLOCK_ATTACH, GameEvent.BLOCK_CHANGE, GameEvent.BLOCK_CLOSE, GameEvent.BLOCK_DESTROY)
				.add(GameEvent.BLOCK_DETACH, GameEvent.BLOCK_OPEN, GameEvent.BLOCK_PLACE, ModGameEvent.BLOCK_ACTIVATE)
				.add(ModGameEvent.BLOCK_DEACTIVATE, GameEvent.CONTAINER_CLOSE, GameEvent.CONTAINER_OPEN)
				.add(GameEvent.DISPENSE_FAIL, ModGameEvent.DRINK, GameEvent.EAT, ModGameEvent.ELYTRA_GLIDE)
				.add(GameEvent.ENTITY_DAMAGED, ModGameEvent.ENTITY_DIE, GameEvent.ENTITY_KILLED)
				.add(ModGameEvent.ENTITY_DISMOUNT, ModGameEvent.ENTITY_INTERACT, ModGameEvent.ENTITY_MOUNT)
				.add(GameEvent.ENTITY_PLACE, ModGameEvent.ENTITY_ROAR, ModGameEvent.ENTITY_SHAKE, GameEvent.EQUIP)
				.add(GameEvent.FLUID_PICKUP, GameEvent.FLUID_PLACE, GameEvent.HIT_GROUND, ModGameEvent.INSTRUMENT_PLAY)
				.add(ModGameEvent.ITEM_INTERACT_FINISH, GameEvent.LIGHTNING_STRIKE, ModGameEvent.NOTE_BLOCK_PLAY)
				.add(GameEvent.PISTON_CONTRACT, GameEvent.PISTON_EXTEND, GameEvent.PRIME_FUSE)
				.add(GameEvent.PROJECTILE_LAND, GameEvent.PROJECTILE_SHOOT, GameEvent.SHEAR, GameEvent.SPLASH)
				.add(GameEvent.STEP, GameEvent.SWIM, ModGameEvent.TELEPORT, GameEvent.FLAP)
				.add(ModGameEvent.RESONATE_1, ModGameEvent.RESONATE_2, ModGameEvent.RESONATE_3, ModGameEvent.RESONATE_4)
				.add(ModGameEvent.RESONATE_5, ModGameEvent.RESONATE_6, ModGameEvent.RESONATE_7, ModGameEvent.RESONATE_8)
				.add(ModGameEvent.RESONATE_9, ModGameEvent.RESONATE_10, ModGameEvent.RESONATE_11, ModGameEvent.RESONATE_12)
				.add(ModGameEvent.RESONATE_13, ModGameEvent.RESONATE_14, ModGameEvent.RESONATE_15);
		getOrCreateTagBuilder(ModGameEventTags.WARDEN_CAN_LISTEN)
				.add(GameEvent.BLOCK_ATTACH, GameEvent.BLOCK_CHANGE, GameEvent.BLOCK_CLOSE, GameEvent.BLOCK_DESTROY)
				.add(GameEvent.BLOCK_DETACH, GameEvent.BLOCK_OPEN, GameEvent.BLOCK_PLACE, ModGameEvent.BLOCK_ACTIVATE)
				.add(ModGameEvent.BLOCK_DEACTIVATE, GameEvent.CONTAINER_CLOSE, GameEvent.CONTAINER_OPEN)
				.add(GameEvent.DISPENSE_FAIL, ModGameEvent.DRINK, GameEvent.EAT, ModGameEvent.ELYTRA_GLIDE)
				.add(GameEvent.ENTITY_DAMAGED, ModGameEvent.ENTITY_DIE, GameEvent.ENTITY_KILLED)
				.add(ModGameEvent.ENTITY_INTERACT, GameEvent.ENTITY_PLACE, ModGameEvent.ENTITY_ROAR)
				.add(ModGameEvent.ENTITY_SHAKE, GameEvent.EQUIP, GameEvent.EXPLODE, GameEvent.FLUID_PICKUP, GameEvent.FLUID_PLACE)
				.add(GameEvent.HIT_GROUND, ModGameEvent.INSTRUMENT_PLAY, ModGameEvent.ITEM_INTERACT_FINISH)
				.add(GameEvent.LIGHTNING_STRIKE, ModGameEvent.NOTE_BLOCK_PLAY, GameEvent.PISTON_CONTRACT)
				.add(GameEvent.PISTON_EXTEND, GameEvent.PRIME_FUSE, GameEvent.PROJECTILE_LAND)
				.add(GameEvent.PROJECTILE_SHOOT, GameEvent.SHEAR, GameEvent.SPLASH, GameEvent.STEP, GameEvent.SWIM)
				.add(ModGameEvent.TELEPORT, ModGameEvent.SHRIEK)
				.add(ModGameEvent.RESONATE_1, ModGameEvent.RESONATE_2, ModGameEvent.RESONATE_3, ModGameEvent.RESONATE_4)
				.add(ModGameEvent.RESONATE_5, ModGameEvent.RESONATE_6, ModGameEvent.RESONATE_7, ModGameEvent.RESONATE_8)
				.add(ModGameEvent.RESONATE_9, ModGameEvent.RESONATE_10, ModGameEvent.RESONATE_11, ModGameEvent.RESONATE_12)
				.add(ModGameEvent.RESONATE_13, ModGameEvent.RESONATE_14, ModGameEvent.RESONATE_15)
				.addTag(ModGameEventTags.SHRIEKER_CAN_LISTEN);
	}
}
