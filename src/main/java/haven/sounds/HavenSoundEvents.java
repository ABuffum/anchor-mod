package haven.sounds;

import haven.HavenMod;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class HavenSoundEvents {
	//Mangrove
	public static final SoundEvent BLOCK_MANGROVE_ROOTS_BREAK = registerSoundEvent("block.mangrove_roots.break");
	public static final SoundEvent BLOCK_MANGROVE_ROOTS_FALL = registerSoundEvent("block.mangrove_roots.fall");
	public static final SoundEvent BLOCK_MANGROVE_ROOTS_HIT = registerSoundEvent("block.mangrove_roots.hit");
	public static final SoundEvent BLOCK_MANGROVE_ROOTS_PLACE = registerSoundEvent("block.mangrove_roots.place");
	public static final SoundEvent BLOCK_MANGROVE_ROOTS_STEP = registerSoundEvent("block.mangrove_roots.step");
	public static final SoundEvent BLOCK_MUDDY_MANGROVE_ROOTS_BREAK = registerSoundEvent("block.muddy_mangrove_roots.break");
	public static final SoundEvent BLOCK_MUDDY_MANGROVE_ROOTS_FALL = registerSoundEvent("block.muddy_mangrove_roots.fall");
	public static final SoundEvent BLOCK_MUDDY_MANGROVE_ROOTS_HIT = registerSoundEvent("block.muddy_mangrove_roots.hit");
	public static final SoundEvent BLOCK_MUDDY_MANGROVE_ROOTS_PLACE = registerSoundEvent("block.muddy_mangrove_roots.place");
	public static final SoundEvent BLOCK_MUDDY_MANGROVE_ROOTS_STEP = registerSoundEvent("block.muddy_mangrove_roots.step");
	//Mud
	public static final SoundEvent BLOCK_MUD_BREAK = registerSoundEvent("block.mud.break");
	public static final SoundEvent BLOCK_MUD_FALL = registerSoundEvent("block.mud.fall");
	public static final SoundEvent BLOCK_MUD_HIT = registerSoundEvent("block.mud.hit");
	public static final SoundEvent BLOCK_MUD_PLACE = registerSoundEvent("block.mud.place");
	public static final SoundEvent BLOCK_MUD_STEP = registerSoundEvent("block.mud.step");
	public static final SoundEvent BLOCK_MUD_BRICKS_BREAK = registerSoundEvent("block.mud_bricks.break");
	public static final SoundEvent BLOCK_MUD_BRICKS_FALL = registerSoundEvent("block.mud_bricks.fall");
	public static final SoundEvent BLOCK_MUD_BRICKS_HIT = registerSoundEvent("block.mud_bricks.hit");
	public static final SoundEvent BLOCK_MUD_BRICKS_PLACE = registerSoundEvent("block.mud_bricks.place");
	public static final SoundEvent BLOCK_MUD_BRICKS_STEP = registerSoundEvent("block.mud_bricks.step");
	public static final SoundEvent BLOCK_PACKED_MUD_BREAK = registerSoundEvent("block.packed_mud.break");
	public static final SoundEvent BLOCK_PACKED_MUD_FALL = registerSoundEvent("block.packed_mud.fall");
	public static final SoundEvent BLOCK_PACKED_MUD_HIT = registerSoundEvent("block.packed_mud.hit");
	public static final SoundEvent BLOCK_PACKED_MUD_PLACE = registerSoundEvent("block.packed_mud.place");
	public static final SoundEvent BLOCK_PACKED_MUD_STEP = registerSoundEvent("block.packed_mud.step");
	//Froglight
	public static final SoundEvent BLOCK_FROGLIGHT_BREAK = registerSoundEvent("block.froglight.break");
	public static final SoundEvent BLOCK_FROGLIGHT_FALL = registerSoundEvent("block.froglight.fall");
	public static final SoundEvent BLOCK_FROGLIGHT_HIT = registerSoundEvent("block.froglight.hit");
	public static final SoundEvent BLOCK_FROGLIGHT_PLACE = registerSoundEvent("block.froglight.place");
	public static final SoundEvent BLOCK_FROGLIGHT_STEP = registerSoundEvent("block.froglight.step");
	//Frogspawn
	public static final SoundEvent BLOCK_FROGSPAWN_STEP = registerSoundEvent("block.frogspawn.step");
	public static final SoundEvent BLOCK_FROGSPAWN_BREAK = registerSoundEvent("block.frogspawn.break");
	public static final SoundEvent BLOCK_FROGSPAWN_FALL = registerSoundEvent("block.frogspawn.fall");
	public static final SoundEvent BLOCK_FROGSPAWN_HATCH = registerSoundEvent("block.frogspawn.hatch");
	public static final SoundEvent BLOCK_FROGSPAWN_HIT = registerSoundEvent("block.frogspawn.hit");
	public static final SoundEvent BLOCK_FROGSPAWN_PLACE = registerSoundEvent("block.frogspawn.place");
	//Frog
	public static final SoundEvent ENTITY_FROG_AMBIENT = registerSoundEvent("entity.frog.ambient");
	public static final SoundEvent ENTITY_FROG_DEATH = registerSoundEvent("entity.frog.death");
	public static final SoundEvent ENTITY_FROG_EAT = registerSoundEvent("entity.frog.eat");
	public static final SoundEvent ENTITY_FROG_HURT = registerSoundEvent("entity.frog.hurt");
	public static final SoundEvent ENTITY_FROG_LAY_SPAWN = registerSoundEvent("entity.frog.lay_spawn");
	public static final SoundEvent ENTITY_FROG_LONG_JUMP = registerSoundEvent("entity.frog.long_jump");
	public static final SoundEvent ENTITY_FROG_STEP = registerSoundEvent("entity.frog.step");
	public static final SoundEvent ENTITY_FROG_TONGUE = registerSoundEvent("entity.frog.tongue");
	//Music Discs
	public static final SoundEvent MUSIC_DISC_OTHERSIDE = registerSoundEvent("music_disc.otherside");
	public static final SoundEvent MUSIC_DISC_5 = registerSoundEvent("music_disc.5");
	//Goat Horns
	public static final SoundEvent[] GOAT_HORN_SOUNDS = new SoundEvent[] {
		registerSoundEvent("item.goat_horn.sound.0"),
		registerSoundEvent("item.goat_horn.sound.1"),
		registerSoundEvent("item.goat_horn.sound.2"),
		registerSoundEvent("item.goat_horn.sound.3"),
		registerSoundEvent("item.goat_horn.sound.4"),
		registerSoundEvent("item.goat_horn.sound.5"),
		registerSoundEvent("item.goat_horn.sound.6"),
		registerSoundEvent("item.goat_horn.sound.7")
	};
	public static final SoundEvent ENTITY_GOAT_HORN_BREAK = registerSoundEvent("entity.goat.horn_break");
	public static final SoundEvent ENTITY_GOAT_SCREAMING_HORN_BREAK = ENTITY_GOAT_HORN_BREAK;//registerSoundEvent("entity.goat.screaming.horn_break");




	//Server Blood
	public static final SoundEvent SYRINGE_INJECTED = registerSoundEvent("item.syringe.inject");

	public static SoundEvent registerSoundEvent(String name) {
		Identifier id = HavenMod.ID(name);
		return Registry.register(Registry.SOUND_EVENT, id, new SoundEvent(id));
	}
}
