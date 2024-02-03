package fun.wich.mixins.block;

import fun.wich.ModBase;
import net.minecraft.block.BlockState;
import net.minecraft.block.SeagrassBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SeagrassBlock.class)
public class SeagrassBlockMixin {
	@Inject(method="canPlantOnTop",at=@At("HEAD"),cancellable = true)
	protected void CanPlantOnTop(BlockState floor, BlockView world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
		if (floor.isOf(ModBase.BLAZE_POWDER_BLOCK.asBlock())) cir.setReturnValue(false);
	}
}
