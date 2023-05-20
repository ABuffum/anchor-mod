package fun.mousewich.mixins.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.ComposterBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ComposterBlock.class)
public class ComposterBlockMixin {
	@Inject(method="emptyComposter", at=@At("TAIL"))
	private static void InjectBlockChangeOnEmpty(BlockState state, WorldAccess world, BlockPos pos, CallbackInfoReturnable<BlockState> cir) {
		world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos);
	}
	@Inject(method="addToComposter", at=@At(value="INVOKE", shift=At.Shift.AFTER, target="Lnet/minecraft/world/WorldAccess;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z"))
	private static void InjectBlockChangeOnAdd(BlockState state, WorldAccess world, BlockPos pos, ItemStack item, CallbackInfoReturnable<BlockState> cir) {
		world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos);
	}
}
