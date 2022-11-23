package haven.mixins.entities;

import haven.ModBase;
import haven.blocks.ChiseledBookshelfBlock;
import haven.containers.BedContainer;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockEntityType.class)
public class BlockEntityTypeMixin {
	@Inject(method="supports", at = @At("HEAD"), cancellable = true)
	private void supports(BlockState state, CallbackInfoReturnable<Boolean> cir) {
		Block block = state.getBlock();
		if (BlockEntityType.SIGN.equals(this)) {
			if (block instanceof SignBlock sign) {
				if (ModBase.SIGN_TYPES.contains(sign.getSignType())) cir.setReturnValue(true);
			}
			else if (block instanceof WallSignBlock sign) {
				if (ModBase.SIGN_TYPES.contains(sign.getSignType())) cir.setReturnValue(true);
			}
		}
		else if (BlockEntityType.BED.equals(this)) {
			if (block instanceof BedBlock) {
				for (BedContainer bedContainer : ModBase.BEDS) {
					if (bedContainer.contains(block)) cir.setReturnValue(true);
				}
			}
		}
		else if (BlockEntityType.CAMPFIRE.equals(this)) {
			if (block instanceof CampfireBlock && ModBase.CAMPFIRES.contains(block)) cir.setReturnValue(true);
		}
		else if (ModBase.CHISELED_BOOKSHELF_ENTITY.equals(this)) {
			if (block instanceof ChiseledBookshelfBlock) cir.setReturnValue(true);
		}
	}
}
