package fun.mousewich.mixins.client.sound;

import com.google.common.collect.Multimap;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.sound.*;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Mixin(SoundSystem.class)
public interface SoundSystemAccessor {
	@Accessor("MARKER")
	static Marker getMarker() { return null; }
	@Accessor("LOGGER")
	static Logger getLogger() { return null; }
	@Accessor("UNKNOWN_SOUNDS")
	static Set<Identifier> getUnknownSounds() { return null; }
	@Accessor("loader")
	SoundManager getLoader();
	@Accessor("settings")
	GameOptions getSettings();
	@Accessor("started")
	boolean getStarted();
	@Accessor("listener")
	SoundListener getListener();
	@Accessor("listeners")
	List<SoundInstanceListener> getListeners();
	@Accessor("soundLoader")
	SoundLoader getSoundLoader();
	@Accessor("ticks")
	int getTicks();
	@Accessor("tickingSounds")
	List<TickableSoundInstance> getTickingSounds();
	@Accessor("soundEndTicks")
	Map<SoundInstance, Integer> getSoundEndTicks();
	@Accessor("channel")
	Channel getChannel();
	@Accessor("sources")
	Map<SoundInstance, Channel.SourceManager> getSources();
	@Accessor("sounds")
	Multimap<SoundCategory, SoundInstance> getSounds();
}
