package haven.mixins.blocks.enums;

import haven.HavenMod;
import haven.HavenTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.block.enums.Instrument;
import net.minecraft.tag.BlockTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Instrument.class)
public class InstrumentMixin {
	@Inject(method="fromBlockState", at = @At("HEAD"), cancellable = true)
	private static void fromBlockState(BlockState state, CallbackInfoReturnable<Instrument> cir) {
		if (state.isIn(HavenTags.Blocks.PUMPKINS)) cir.setReturnValue(Instrument.DIDGERIDOO);
	}
}
