package fun.wich.mixins.client.gui;

import net.minecraft.client.gui.hud.SubtitlesHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(SubtitlesHud.class)
public interface SubtitlesHudAccessor {
	@Accessor("entries")
	List<SubtitlesHud.SubtitleEntry> getEntries();
}
