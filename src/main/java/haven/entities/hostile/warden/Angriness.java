package haven.entities.hostile.warden;

import haven.sounds.HavenSoundEvents;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Util;

import java.util.Arrays;

public enum Angriness {
	CALM(0, HavenSoundEvents.ENTITY_WARDEN_AMBIENT, HavenSoundEvents.ENTITY_WARDEN_LISTENING),
	AGITATED(40, HavenSoundEvents.ENTITY_WARDEN_AGITATED, HavenSoundEvents.ENTITY_WARDEN_LISTENING_ANGRY),
	ANGRY(80, HavenSoundEvents.ENTITY_WARDEN_ANGRY, HavenSoundEvents.ENTITY_WARDEN_LISTENING_ANGRY);

	private static final Angriness[] VALUES;
	private final int threshold;
	private final SoundEvent sound;
	private final SoundEvent listeningSound;

	private Angriness(int threshold, SoundEvent sound, SoundEvent listeningSound) {
		this.threshold = threshold;
		this.sound = sound;
		this.listeningSound = listeningSound;
	}

	public int getThreshold() { return this.threshold; }

	public SoundEvent getSound() { return this.sound; }

	public SoundEvent getListeningSound() { return this.listeningSound; }

	public static Angriness getForAnger(int anger) {
		for (Angriness angriness : VALUES) {
			if (anger < angriness.threshold) continue;
			return angriness;
		}
		return CALM;
	}

	public boolean isAngry() { return this == ANGRY; }

	static {
		VALUES = Util.make(Angriness.values(), values -> Arrays.sort(values, (a, b) -> Integer.compare(b.threshold, a.threshold)));
	}
}
