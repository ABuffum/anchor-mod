package fun.mousewich.mixins.block;

import fun.mousewich.ModBase;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.BubbleColumnBlock;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BubbleColumnBlock.class)
public class BubbleColumnBlockMixin {
	@Shadow @Final public static BooleanProperty DRAG;

	@Inject(method="getBubbleState", at=@At("HEAD"),cancellable = true)
	private static void GetBubbleState(BlockState state, CallbackInfoReturnable<BlockState> cir) {
		if (state.isOf(ModBase.BLAZE_POWDER_BLOCK.asBlock())) cir.setReturnValue(Blocks.BUBBLE_COLUMN.getDefaultState().with(DRAG, true));
	}
	@Inject(method="canPlaceAt", at=@At("HEAD"),cancellable = true)
	public void CanPlaceAt(BlockState state, WorldView world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
		BlockState blockState = world.getBlockState(pos.down());
		if (blockState.isOf(ModBase.BLAZE_POWDER_BLOCK.asBlock())) cir.setReturnValue(true);
	}
}
