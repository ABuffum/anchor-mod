package fun.wich.mixins.item;

import fun.wich.sound.IdentifiedSounds;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItem;
import net.minecraft.sound.SoundEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockItem.class)
public abstract class BlockItemMixin {
	@Inject(method = "getPlaceSound", at = @At("HEAD"), cancellable = true)
	protected void getPlaceSound(BlockState state, CallbackInfoReturnable<SoundEvent> cir) {
		SoundEvent placeSound = IdentifiedSounds.getPlaceSound(state);
		if (placeSound != null) cir.setReturnValue(placeSound);
	}
}
