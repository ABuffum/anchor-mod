package fun.wich.item.horn.goat;

import net.minecraft.sound.SoundEvent;

public class GoatHornInstrument {
	private final String name;
	public String getName() { return name; }
	private final SoundEvent soundEvent;
	public SoundEvent getSoundEvent() { return soundEvent; }
	private final int useDuration;
	public int getUseDuration() { return useDuration; }
	private final float range;
	public float getRange() { return range; }

	public GoatHornInstrument(String name, SoundEvent soundEvent, int useDuration, float range) {
		this.name = name;
		this.soundEvent = soundEvent;
		this.useDuration = useDuration;
		this.range = range;
	}
}
