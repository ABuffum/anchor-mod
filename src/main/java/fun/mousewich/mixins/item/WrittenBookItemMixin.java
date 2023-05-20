package fun.mousewich.mixins.item;

import fun.mousewich.block.basic.ModLecternBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.LecternBlock;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.WrittenBookItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WrittenBookItem.class)
public abstract class WrittenBookItemMixin {
	@Inject(method="useOnBlock", at = @At("HEAD"), cancellable = true)
	private void AddToLectern(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
		BlockPos blockPos = context.getBlockPos();
		World world = context.getWorld();
		BlockState blockState = world.getBlockState(blockPos);
		if (blockState.getBlock() instanceof ModLecternBlock) {
			cir.setReturnValue(LecternBlock.putBookIfAbsent(context.getPlayer(), world, blockPos, blockState, context.getStack()) ? ActionResult.success(world.isClient) : ActionResult.PASS);
		}
	}
}
