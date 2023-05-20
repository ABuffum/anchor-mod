package fun.mousewich.mixins.block;

import fun.mousewich.block.TransparentSlabBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.TransparentBlock;
import net.minecraft.block.enums.SlabType;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TransparentBlock.class)
public abstract class TransparentBlockMixin extends Block {
	public TransparentBlockMixin(Settings settings) { super(settings); }

	@Inject(method = "isSideInvisible", at = @At("HEAD"), cancellable = true)
	public void IsSideInvisible(BlockState state, BlockState stateFrom, Direction direction, CallbackInfoReturnable<Boolean> cir) {
		if (stateFrom.getBlock() instanceof TransparentSlabBlock slab && slab.getBaseBlock() == this) {
			SlabType from = stateFrom.get(TransparentSlabBlock.TYPE);
			if (from == SlabType.DOUBLE) cir.setReturnValue(true);
			else if (from == SlabType.TOP) {
				if (direction == Direction.DOWN) cir.setReturnValue(true);
			}
			else if (direction == Direction.UP) cir.setReturnValue(true);
		}
	}
}