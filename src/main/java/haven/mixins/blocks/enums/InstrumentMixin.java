package haven.mixins.blocks.enums;

import haven.ModBase;
import haven.ModTags;
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
		if (ModTags.Blocks.FLEECE.contains(block)) cir.setReturnValue(Instrument.GUITAR);
		else if (ModTags.Blocks.PUMPKINS.contains(block)) cir.setReturnValue(Instrument.DIDGERIDOO);
		else if (ModBase.GOLD_MATERIAL.contains(block)) cir.setReturnValue(Instrument.BELL);
		else if (ModBase.IRON_MATERIAL.contains(block) || ModBase.DARK_IRON_MATERIAL.contains(block)) cir.setReturnValue(Instrument.IRON_XYLOPHONE);
		else if (ModBase.EMERALD_MATERIAL.contains(block)) cir.setReturnValue(Instrument.BIT);
		else if (state.isOf(ModBase.SUGAR_CANE_MATERIAL.getBale().getBlock())) cir.setReturnValue(Instrument.BANJO);
		else if (ModBase.BONE_MATERIAL.contains(block)) cir.setReturnValue(Instrument.XYLOPHONE);
	}
}
