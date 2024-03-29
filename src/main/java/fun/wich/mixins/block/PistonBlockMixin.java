package fun.wich.mixins.block;

import fun.wich.gen.data.tag.ModBlockTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.PistonBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PistonBlock.class)
public class PistonBlockMixin {
	@Inject(method="isMovable", at = @At("HEAD"), cancellable = true)
	private static void IsMovable(BlockState state, World world, BlockPos pos, Direction direction, boolean canBreak, Direction pistonDir, CallbackInfoReturnable<Boolean> cir) {
		if (state.isIn(ModBlockTags.PISTON_IMMOVABLE)) cir.setReturnValue(false);
	}
}
