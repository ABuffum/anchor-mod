package fun.mousewich.mixins.client.gui;

import fun.mousewich.sound.IdentifiedSounds;
import fun.mousewich.sound.SoundUtil;
import fun.mousewich.sound.SubtitleOverrides;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.SubtitlesHud;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundInstanceListener;
import net.minecraft.client.sound.WeightedSoundSet;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SubtitlesHud.class)
public abstract class SubtitlesHudMixin extends DrawableHelper implements SoundInstanceListener {
	@Final
	@Shadow
	private MinecraftClient client;

	@Inject(method="onSoundPlayed", at = @At("HEAD"), cancellable = true)
	public void OnSoundPlayed(SoundInstance sound, WeightedSoundSet soundSet, CallbackInfo ci) {
		boolean identify_sounds = IdentifiedSounds.Active(client.player);
		if (identify_sounds) {
			String subtitle = SubtitleOverrides.getSubtitle(soundSet);
			if (subtitle != null) {
				SoundUtil.showTranslatableSubtitle((SubtitlesHud)(Object)this, subtitle, new Vec3d(sound.getX(), sound.getY(), sound.getZ()));
				ci.cancel();
			}
		}
		else if (soundSet.getSubtitle() instanceof TranslatableText translatable) {
			if (translatable.getKey().startsWith(IdentifiedSounds.IDENTIFY)) {
				ci.cancel();
			}
		}
	}
}
