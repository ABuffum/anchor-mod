package fun.mousewich.mixins.block.enums;

import fun.mousewich.ModBase;
import fun.mousewich.gen.data.tag.ModBlockTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.enums.Instrument;
import net.minecraft.block.enums.SlabType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Instrument.class)
public class InstrumentMixin {
	@Inject(method="fromBlockState", at = @At("HEAD"), cancellable = true)
	private static void fromBlockState(BlockState state, CallbackInfoReturnable<Instrument> cir) {
		if (state.isIn(ModBlockTags.FLEECE)) cir.setReturnValue(Instrument.GUITAR);
		else if (state.isIn(ModBlockTags.PUMPKINS)) cir.setReturnValue(Instrument.DIDGERIDOO);
		else if (state.isOf(ModBase.BONE_SLAB.asBlock()) && state.get(SlabBlock.TYPE) != SlabType.BOTTOM) cir.setReturnValue(Instrument.XYLOPHONE);
		//TODO: Other instruments (gold -> bell, iron -> iron xylophone, emerald -> bit, bale -> banjo, etc.
	}
}
