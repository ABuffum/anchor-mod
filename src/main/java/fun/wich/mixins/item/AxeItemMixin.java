package fun.wich.mixins.item;

import fun.wich.util.StrippedBlockUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(AxeItem.class)
public class AxeItemMixin {

	@Inject(method="getStrippedState", at=@At("HEAD"), cancellable=true)
	private void GetStrippedState(BlockState state, CallbackInfoReturnable<Optional<BlockState>> cir) {
		Block stripped = StrippedBlockUtil.STRIPPED_BLOCKS.getOrDefault(state.getBlock(), null);
		if (stripped != null) {
			BlockState outState = stripped.getDefaultState();
			if (state.contains(Properties.AXIS) && outState.contains(Properties.AXIS)) outState = outState.with(Properties.AXIS, state.get(PillarBlock.AXIS));
			cir.setReturnValue(Optional.of(outState));
		}
	}

	@Inject(method="useOnBlock", at=@At("RETURN"))
	private void applyOnStripped(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
		if (cir.getReturnValue().isAccepted()) {
			StrippedBlockUtil.OnStripFunc onStrip = StrippedBlockUtil.ON_STRIP.getOrDefault(context.getWorld().getBlockState(context.getBlockPos()).getBlock(), null);
			if (onStrip != null) onStrip.execute(context);
		}
	}

	@Inject(method = "useOnBlock", at = @At(value="INVOKE", target="Lnet/minecraft/world/World;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z"))
	private void TriggerBlockChange(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
		context.getWorld().emitGameEvent(context.getPlayer(), GameEvent.BLOCK_CHANGE, context.getBlockPos());
	}
}
