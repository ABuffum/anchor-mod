package haven.items.goat;

import haven.sounds.ModSoundEvents;

import java.util.*;

public class GoatHornInstruments {
	public static final int GOAT_HORN_RANGE = 256;
	public static final int GOAT_HORN_USE_DURATION = 140;
	public static final GoatHornInstrument PONDER = new GoatHornInstrument("ponder_goat_horn", ModSoundEvents.GOAT_HORN_SOUNDS[0], 140, 256.0F);
	public static final GoatHornInstrument SING = new GoatHornInstrument("sing_goat_horn", ModSoundEvents.GOAT_HORN_SOUNDS[1], 140, 256.0F);
	public static final GoatHornInstrument SEEK = new GoatHornInstrument("seek_goat_horn", ModSoundEvents.GOAT_HORN_SOUNDS[2], 140, 256.0F);
	public static final GoatHornInstrument FEEL = new GoatHornInstrument("feel_goat_horn", ModSoundEvents.GOAT_HORN_SOUNDS[3], 140, 256.0F);
	public static final GoatHornInstrument ADMIRE = new GoatHornInstrument("admire_goat_horn", ModSoundEvents.GOAT_HORN_SOUNDS[4], 140, 256.0F);
	public static final GoatHornInstrument CALL = new GoatHornInstrument("call_goat_horn", ModSoundEvents.GOAT_HORN_SOUNDS[5], 140, 256.0F);
	public static final GoatHornInstrument YEARN = new GoatHornInstrument("yearn_goat_horn", ModSoundEvents.GOAT_HORN_SOUNDS[6], 140, 256.0F);
	public static final GoatHornInstrument DREAM = new GoatHornInstrument("dream_goat_horn", ModSoundEvents.GOAT_HORN_SOUNDS[7], 140, 256.0F);


	public static List<GoatHornInstrument> INSTRUMENTS = new ArrayList<>();
	public static List<GoatHornInstrument> REGULAR_INSTRUMENTS = new ArrayList<>(List.of(ADMIRE, CALL, YEARN, DREAM));
	public static List<GoatHornInstrument> SCREAMING_INSTRUMENTS = new ArrayList<>(List.of(PONDER, SING, SEEK, FEEL));

	public static GoatHornInstrument Get(String name) {
		if (name == null) return null; else name = name.trim();
		for(GoatHornInstrument instrument : INSTRUMENTS) {
			if (instrument.getName().equalsIgnoreCase(name)) return instrument;
		}
		return null;
	}

	static {
		INSTRUMENTS.addAll(SCREAMING_INSTRUMENTS);
		INSTRUMENTS.addAll(REGULAR_INSTRUMENTS);
	}
}