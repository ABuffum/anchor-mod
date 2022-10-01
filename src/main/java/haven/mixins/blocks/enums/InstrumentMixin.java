package haven.mixins.blocks.enums;

import haven.HavenMod;
import haven.HavenTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.enums.Instrument;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Instrument.class)
public class InstrumentMixin {
	@Inject(method="fromBlockState", at = @At("HEAD"), cancellable = true)
	private static void fromBlockState(BlockState state, CallbackInfoReturnable<Instrument> cir) {
		Block block = state.getBlock();
		if (HavenTags.Blocks.FLEECE.contains(block)) cir.setReturnValue(Instrument.GUITAR);
		else if (HavenTags.Blocks.PUMPKINS.contains(block)) cir.setReturnValue(Instrument.DIDGERIDOO);
		else if (HavenMod.GOLD_MATERIAL.contains(block)) cir.setReturnValue(Instrument.BELL);
		else if (HavenMod.IRON_MATERIAL.contains(block)) cir.setReturnValue(Instrument.IRON_XYLOPHONE);
		else if (HavenMod.EMERALD_MATERIAL.contains(block)) cir.setReturnValue(Instrument.BIT);
	}
}
