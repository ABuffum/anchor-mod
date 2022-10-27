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
	//Sculk
	public static final SoundEvent BLOCK_SCULK_SPREAD = registerSoundEvent("block.sculk.spread");
	public static final SoundEvent BLOCK_SCULK_CHARGE = registerSoundEvent("block.sculk.charge");
	public static final SoundEvent BLOCK_SCULK_BREAK = registerSoundEvent("block.sculk.break");
	public static final SoundEvent BLOCK_SCULK_FALL = registerSoundEvent("block.sculk.fall");
	public static final SoundEvent BLOCK_SCULK_HIT = registerSoundEvent("block.sculk.hit");
	public static final SoundEvent BLOCK_SCULK_PLACE = registerSoundEvent("block.sculk.place");
	public static final SoundEvent BLOCK_SCULK_STEP = registerSoundEvent("block.sculk.step");
	public static final SoundEvent BLOCK_SCULK_CATALYST_BLOOM = registerSoundEvent("block.sculk_catalyst.bloom");
	public static final SoundEvent BLOCK_SCULK_CATALYST_BREAK = registerSoundEvent("block.sculk_catalyst.break");
	public static final SoundEvent BLOCK_SCULK_CATALYST_FALL = registerSoundEvent("block.sculk_catalyst.fall");
	public static final SoundEvent BLOCK_SCULK_CATALYST_HIT = registerSoundEvent("block.sculk_catalyst.hit");
	public static final SoundEvent BLOCK_SCULK_CATALYST_PLACE = registerSoundEvent("block.sculk_catalyst.place");
	public static final SoundEvent BLOCK_SCULK_CATALYST_STEP = registerSoundEvent("block.sculk_catalyst.step");
	public static final SoundEvent BLOCK_SCULK_SHRIEKER_BREAK = registerSoundEvent("block.sculk_shrieker.break");
	public static final SoundEvent BLOCK_SCULK_SHRIEKER_FALL = registerSoundEvent("block.sculk_shrieker.fall");
	public static final SoundEvent BLOCK_SCULK_SHRIEKER_HIT = registerSoundEvent("block.sculk_shrieker.hit");
	public static final SoundEvent BLOCK_SCULK_SHRIEKER_PLACE = registerSoundEvent("block.sculk_shrieker.place");
	public static final SoundEvent BLOCK_SCULK_SHRIEKER_SHRIEK = registerSoundEvent("block.sculk_shrieker.shriek");
	public static final SoundEvent BLOCK_SCULK_SHRIEKER_STEP = registerSoundEvent("block.sculk_shrieker.step");
	public static final SoundEvent BLOCK_SCULK_VEIN_BREAK = registerSoundEvent("block.sculk_vein.break");
	public static final SoundEvent BLOCK_SCULK_VEIN_FALL = registerSoundEvent("block.sculk_vein.fall");
	public static final SoundEvent BLOCK_SCULK_VEIN_HIT = registerSoundEvent("block.sculk_vein.hit");
	public static final SoundEvent BLOCK_SCULK_VEIN_PLACE = registerSoundEvent("block.sculk_vein.place");
	public static final SoundEvent BLOCK_SCULK_VEIN_STEP = registerSoundEvent("block.sculk_vein.step");
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
	//Warden
	public static final SoundEvent ENTITY_WARDEN_AGITATED = registerSoundEvent("entity.warden.agitated");
	public static final SoundEvent ENTITY_WARDEN_AMBIENT = registerSoundEvent("entity.warden.ambient");
	public static final SoundEvent ENTITY_WARDEN_ANGRY = registerSoundEvent("entity.warden.angry");
	public static final SoundEvent ENTITY_WARDEN_ATTACK_IMPACT = registerSoundEvent("entity.warden.attack_impact");
	public static final SoundEvent ENTITY_WARDEN_DEATH = registerSoundEvent("entity.warden.death");
	public static final SoundEvent ENTITY_WARDEN_DIG = registerSoundEvent("entity.warden.dig");
	public static final SoundEvent ENTITY_WARDEN_EMERGE = registerSoundEvent("entity.warden.emerge");
	public static final SoundEvent ENTITY_WARDEN_HEARTBEAT = registerSoundEvent("entity.warden.heartbeat");
	public static final SoundEvent ENTITY_WARDEN_HURT = registerSoundEvent("entity.warden.hurt");
	public static final SoundEvent ENTITY_WARDEN_LISTENING = registerSoundEvent("entity.warden.listening");
	public static final SoundEvent ENTITY_WARDEN_LISTENING_ANGRY = registerSoundEvent("entity.warden.listening_angry");
	public static final SoundEvent ENTITY_WARDEN_NEARBY_CLOSE = registerSoundEvent("entity.warden.nearby_close");
	public static final SoundEvent ENTITY_WARDEN_NEARBY_CLOSER = registerSoundEvent("entity.warden.nearby_closer");
	public static final SoundEvent ENTITY_WARDEN_NEARBY_CLOSEST = registerSoundEvent("entity.warden.nearby_closest");
	public static final SoundEvent ENTITY_WARDEN_ROAR = registerSoundEvent("entity.warden.roar");
	public static final SoundEvent ENTITY_WARDEN_SNIFF = registerSoundEvent("entity.warden.sniff");
	public static final SoundEvent ENTITY_WARDEN_SONIC_BOOM = registerSoundEvent("entity.warden.sonic_boom");
	public static final SoundEvent ENTITY_WARDEN_SONIC_CHARGE = registerSoundEvent("entity.warden.sonic_charge");
	public static final SoundEvent ENTITY_WARDEN_STEP = registerSoundEvent("entity.warden.step");
	public static final SoundEvent ENTITY_WARDEN_TENDRIL_CLICKS = registerSoundEvent("entity.warden.tendril_clicks");
	//Music
	public static final SoundEvent MUSIC_OVERWORLD_DEEP_DARK = registerSoundEvent("music.overworld.deep_dark");
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
	//Hanging Signs
	public static final SoundEvent BLOCK_HANGING_SIGN_STEP = registerSoundEvent("block.hanging_sign.step");
	public static final SoundEvent BLOCK_HANGING_SIGN_BREAK = registerSoundEvent("block.hanging_sign.break");
	public static final SoundEvent BLOCK_HANGING_SIGN_FALL = registerSoundEvent("block.hanging_sign.fall");
	public static final SoundEvent BLOCK_HANGING_SIGN_HIT = registerSoundEvent("block.hanging_sign.hit");
	public static final SoundEvent BLOCK_HANGING_SIGN_PLACE = registerSoundEvent("block.hanging_sign.place");
	//Bamboo Wood
	public static final SoundEvent BLOCK_BAMBOO_WOOD_BREAK = registerSoundEvent("block.bamboo_wood.break");
	public static final SoundEvent BLOCK_BAMBOO_WOOD_FALL = registerSoundEvent("block.bamboo_wood.fall");
	public static final SoundEvent BLOCK_BAMBOO_WOOD_HIT = registerSoundEvent("block.bamboo_wood.hit");
	public static final SoundEvent BLOCK_BAMBOO_WOOD_PLACE = registerSoundEvent("block.bamboo_wood.place");
	public static final SoundEvent BLOCK_BAMBOO_WOOD_STEP = registerSoundEvent("block.bamboo_wood.step");
	public static final SoundEvent BLOCK_BAMBOO_WOOD_DOOR_CLOSE = registerSoundEvent("block.bamboo_wood_door.close");
	public static final SoundEvent BLOCK_BAMBOO_WOOD_DOOR_OPEN = registerSoundEvent("block.bamboo_wood_door.open");
	public static final SoundEvent BLOCK_BAMBOO_WOOD_TRAPDOOR_CLOSE = registerSoundEvent("block.bamboo_wood_trapdoor.close");
	public static final SoundEvent BLOCK_BAMBOO_WOOD_TRAPDOOR_OPEN = registerSoundEvent("block.bamboo_wood_trapdoor.open");
	public static final SoundEvent BLOCK_BAMBOO_WOOD_BUTTON_CLICK_OFF = registerSoundEvent("block.bamboo_wood_button.click_off");
	public static final SoundEvent BLOCK_BAMBOO_WOOD_BUTTON_CLICK_ON = registerSoundEvent("block.bamboo_wood_button.click_on");
	public static final SoundEvent BLOCK_BAMBOO_WOOD_PRESSURE_PLATE_CLICK_OFF = registerSoundEvent("block.bamboo_wood_pressure_plate.click_off");
	public static final SoundEvent BLOCK_BAMBOO_WOOD_PRESSURE_PLATE_CLICK_ON = registerSoundEvent("block.bamboo_wood_pressure_plate.click_on");
	public static final SoundEvent BLOCK_BAMBOO_WOOD_FENCE_GATE_CLOSE = registerSoundEvent("block.bamboo_wood_fence_gate.close");
	public static final SoundEvent BLOCK_BAMBOO_WOOD_FENCE_GATE_OPEN = registerSoundEvent("block.bamboo_wood_fence_gate.open");
	//Nether Wood
	public static final SoundEvent BLOCK_NETHER_WOOD_BREAK = registerSoundEvent("block.nether_wood.break");
	public static final SoundEvent BLOCK_NETHER_WOOD_FALL = registerSoundEvent("block.nether_wood.fall");
	public static final SoundEvent BLOCK_NETHER_WOOD_HIT = registerSoundEvent("block.nether_wood.hit");
	public static final SoundEvent BLOCK_NETHER_WOOD_PLACE = registerSoundEvent("block.nether_wood.place");
	public static final SoundEvent BLOCK_NETHER_WOOD_STEP = registerSoundEvent("block.nether_wood.step");
	public static final SoundEvent BLOCK_NETHER_WOOD_DOOR_CLOSE = registerSoundEvent("block.nether_wood_door.close");
	public static final SoundEvent BLOCK_NETHER_WOOD_DOOR_OPEN = registerSoundEvent("block.nether_wood_door.open");
	public static final SoundEvent BLOCK_NETHER_WOOD_TRAPDOOR_CLOSE = registerSoundEvent("block.nether_wood_trapdoor.close");
	public static final SoundEvent BLOCK_NETHER_WOOD_TRAPDOOR_OPEN = registerSoundEvent("block.nether_wood_trapdoor.open");
	public static final SoundEvent BLOCK_NETHER_WOOD_BUTTON_CLICK_OFF = registerSoundEvent("block.nether_wood_button.click_off");
	public static final SoundEvent BLOCK_NETHER_WOOD_BUTTON_CLICK_ON = registerSoundEvent("block.nether_wood_button.click_on");
	public static final SoundEvent BLOCK_NETHER_WOOD_PRESSURE_PLATE_CLICK_OFF = registerSoundEvent("block.nether_wood_pressure_plate.click_off");
	public static final SoundEvent BLOCK_NETHER_WOOD_PRESSURE_PLATE_CLICK_ON = registerSoundEvent("block.nether_wood_pressure_plate.click_on");
	public static final SoundEvent BLOCK_NETHER_WOOD_FENCE_GATE_CLOSE = registerSoundEvent("block.nether_wood_fence_gate.close");
	public static final SoundEvent BLOCK_NETHER_WOOD_FENCE_GATE_OPEN = registerSoundEvent("block.nether_wood_fence_gate.open");

	//Salves
	public static final SoundEvent SALVE_APPLIED = registerSoundEvent("item.salve.apply");
	//Syringes
	public static final SoundEvent SYRINGE_INJECTED = registerSoundEvent("item.syringe.inject");

	//TNT
	public static final SoundEvent SOFT_TNT_PRIMED = registerSoundEvent("entity.soft_tnt.primed");

	//Misc
	public static final SoundEvent MIASMA_COMING = registerSoundEvent("ambient.miasma_coming");


	public static SoundEvent registerSoundEvent(String name) {
		Identifier id = HavenMod.ID(name);
		return Registry.register(Registry.SOUND_EVENT, id, new SoundEvent(id));
	}
}
