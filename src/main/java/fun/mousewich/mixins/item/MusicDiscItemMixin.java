package fun.mousewich.mixins.item;

import fun.mousewich.event.ModGameEvent;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.JukeboxBlock;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MusicDiscItem.class)
public class MusicDiscItemMixin {
	@Inject(method="useOnBlock", at = @At("HEAD"))
	public void UseOnBlock(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
		BlockPos blockPos;
		World world = context.getWorld();
		BlockState blockState = world.getBlockState(blockPos = context.getBlockPos());
		if (!blockState.isOf(Blocks.JUKEBOX) || blockState.get(JukeboxBlock.HAS_RECORD)) return;
		if (!world.isClient) world.emitGameEvent(ModGameEvent.JUKEBOX_PLAY, blockPos);
	}
}
