package fun.mousewich.sound;

import fun.mousewich.mixins.client.gui.SubtitlesHudAccessor;
import fun.mousewich.mixins.client.sound.SoundSystemAccessor;
import net.minecraft.SharedConstants;
import net.minecraft.client.gui.hud.SubtitlesHud;
import net.minecraft.client.sound.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class SoundUtil {
	public static void playIdentifiedStepSound(Entity entity) {
		if (entity.isSilent()) return;
		SoundEvent stepSound = IdentifiedSounds.getStepSound(entity);
		if (stepSound != null) {
			if (entity instanceof PlayerEntity player){
				entity.world.playSound(player, entity.getBlockPos(), stepSound, entity.getSoundCategory(), 0.15f, 1);
			}
			else entity.playSound(stepSound, 0.15f, 1);
		}
	}
	private static float getSoundVolume(SoundSystem system, SoundCategory category) {
		if (category == null || category == SoundCategory.MASTER) return 1.0f;
		return ((SoundSystemAccessor)system).getSettings().getSoundVolume(category);
	}

	public static void showSubtitle(SubtitlesHud hud, String subtitle, Vec3d pos) {
		showSubtitle(hud, Text.of(subtitle), pos);
	}
	public static void showTranslatableSubtitle(SubtitlesHud hud, String subtitle, Vec3d pos) {
		showSubtitle(hud, new TranslatableText(subtitle), pos);
	}
	public static void showSubtitle(SubtitlesHud hud, Text text, Vec3d pos) {
		List<SubtitlesHud.SubtitleEntry> entries = ((SubtitlesHudAccessor)hud).getEntries();
		if (!entries.isEmpty()) {
			for (SubtitlesHud.SubtitleEntry subtitleEntry : entries) {
				if (!subtitleEntry.getText().equals(text)) continue;
				subtitleEntry.reset(pos);
				return;
			}
		}
		entries.add(new SubtitlesHud.SubtitleEntry(text, pos));
	}

	public static void playWithSubtitle(SoundSystem system, SoundInstance sound2, String subtitle) {
		boolean bl2;
		SoundSystemAccessor accessor = (SoundSystemAccessor)system;
		if (!accessor.getStarted()) return;
		if (!sound2.canPlay()) return;
		Marker MARKER = SoundSystemAccessor.getMarker();
		Set<Identifier> UNKNOWN_SOUNDS = SoundSystemAccessor.getUnknownSounds();
		Logger LOGGER = SoundSystemAccessor.getLogger();
		WeightedSoundSet weightedSoundSet = sound2.getSoundSet(accessor.getLoader());
		Identifier identifier = sound2.getId();
		if (weightedSoundSet == null) {
			if (UNKNOWN_SOUNDS.add(identifier)) {
				LOGGER.warn(MARKER, "Unable to play unknown soundEvent: {}", identifier);
			}
			return;
		}
		Sound sound22 = sound2.getSound();
		if (sound22 == SoundManager.MISSING_SOUND) {
			if (UNKNOWN_SOUNDS.add(identifier)) {
				LOGGER.warn(MARKER, "Unable to play empty soundEvent: {}", identifier);
			}
			return;
		}
		float f = sound2.getVolume();
		float g = Math.max(f, 1.0f) * (float)sound22.getAttenuation();
		SoundCategory soundCategory = sound2.getCategory();
		float h = MathHelper.clamp(sound2.getVolume() * getSoundVolume(system, sound2.getCategory()), 0.0f, 1.0f);
		float i = MathHelper.clamp(sound2.getPitch(), 0.5f, 2.0f);
		SoundInstance.AttenuationType attenuationType = sound2.getAttenuationType();
		boolean bl = sound2.isLooping();
		if (h == 0.0f && !sound2.shouldAlwaysPlay()) {
			LOGGER.debug(MARKER, "Skipped playing sound {}, volume was zero.", sound22.getIdentifier());
			return;
		}
		Vec3d vec3d = new Vec3d(sound2.getX(), sound2.getY(), sound2.getZ());
		if (!accessor.getListeners().isEmpty()) {
			bl2 = bl || attenuationType == SoundInstance.AttenuationType.NONE || accessor.getListener().getPos().squaredDistanceTo(vec3d) < (double)(g * g);
			if (bl2) {
				for (SoundInstanceListener soundInstanceListener : accessor.getListeners()) {
					if (soundInstanceListener instanceof SubtitlesHud hud) {
						if (subtitle != null) showSubtitle(hud, subtitle, vec3d);
						continue;
					}
					soundInstanceListener.onSoundPlayed(sound2, weightedSoundSet);
				}
			}
			else LOGGER.debug(MARKER, "Did not notify listeners of soundEvent: {}, it is too far away to hear", identifier);
		}
		if (accessor.getListener().getVolume() <= 0.0f) {
			LOGGER.debug(MARKER, "Skipped playing soundEvent: {}, master volume was zero", identifier);
			return;
		}
		bl2 = sound2.isRepeatable() && sound2.getRepeatDelay() <= 0;
		boolean bl3 = sound22.isStreamed();
		CompletableFuture<Channel.SourceManager> completableFuture = accessor.getChannel().createSource(sound22.isStreamed() ? SoundEngine.RunMode.STREAMING : SoundEngine.RunMode.STATIC);
		Channel.SourceManager sourceManager = completableFuture.join();
		if (sourceManager == null) {
			if (SharedConstants.isDevelopment) LOGGER.warn("Failed to create new sound handle");
			return;
		}
		LOGGER.debug(MARKER, "Playing sound {} for event {}", sound22.getIdentifier(), identifier);
		accessor.getSoundEndTicks().put(sound2, accessor.getTicks() + 20);
		accessor.getSources().put(sound2, sourceManager);
		accessor.getSounds().put(soundCategory, sound2);
		boolean finalBl = bl2;
		sourceManager.run(source -> {
			source.setPitch(i);
			source.setVolume(h);
			if (attenuationType == SoundInstance.AttenuationType.LINEAR) source.setAttenuation(g);
			else source.disableAttenuation();
			source.setLooping(finalBl && !bl3);
			source.setPosition(vec3d);
			source.setRelative(bl);
		});
		if (!bl3) {
			accessor.getSoundLoader().loadStatic(sound22.getLocation()).thenAccept(sound -> sourceManager.run(source -> {
				source.setBuffer(sound);
				source.play();
			}));
		}
		else {
			accessor.getSoundLoader().loadStreamed(sound22.getLocation(), bl2).thenAccept(stream -> sourceManager.run(source -> {
				source.setStream(stream);
				source.play();
			}));
		}
		if (sound2 instanceof TickableSoundInstance) accessor.getTickingSounds().add((TickableSoundInstance)sound2);
	}
}
