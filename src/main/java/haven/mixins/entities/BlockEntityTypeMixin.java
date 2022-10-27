package haven.mixins.entities;

import haven.HavenMod;
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
				if (HavenMod.SIGN_TYPES.contains(sign.getSignType())) cir.setReturnValue(true);
			}
			else if (block instanceof WallSignBlock sign) {
				if (HavenMod.SIGN_TYPES.contains(sign.getSignType())) cir.setReturnValue(true);
			}
		}
		else if (BlockEntityType.BED.equals(this)) {
			if (block instanceof BedBlock) {
				for (BedContainer bedContainer : HavenMod.BEDS) {
					if (bedContainer.contains(block)) cir.setReturnValue(true);
				}
			}
		}
		else if (BlockEntityType.CAMPFIRE.equals(this)) {
			if (block instanceof CampfireBlock && HavenMod.CAMPFIRES.contains(block)) cir.setReturnValue(true);
		}
		else if (HavenMod.CHISELED_BOOKSHELF_ENTITY.equals(this)) {
			if (block instanceof ChiseledBookshelfBlock) cir.setReturnValue(true);
		}
	}
}
