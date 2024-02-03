package fun.wich.mixins.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.function.Consumer;

@Mixin(HoeItem.class)
public class HoeItemMixin {

	/**
	 * @author mousewich
	 * @reason Adding the BLOCK_CHANGE GameEvent. Injecting into lambda functions is not something I know how to do
	 */
	@Overwrite
	public static Consumer<ItemUsageContext> getTillingConsumer(BlockState result) {
		return context -> {
			context.getWorld().setBlockState(context.getBlockPos(), result, Block.NOTIFY_ALL | Block.REDRAW_ON_MAIN_THREAD);
			context.getWorld().emitGameEvent(context.getPlayer(), GameEvent.BLOCK_CHANGE, context.getBlockPos());
		};
	}
	/**
	 * @author mousewich
	 * @reason Adding the BLOCK_CHANGE GameEvent. Injecting into lambda functions is not something I know how to do
	 */
	@Overwrite
	public static Consumer<ItemUsageContext> getTillingConsumer(BlockState result, ItemConvertible droppedItem) {
		return context -> {
			context.getWorld().setBlockState(context.getBlockPos(), result, Block.NOTIFY_ALL | Block.REDRAW_ON_MAIN_THREAD);
			context.getWorld().emitGameEvent(context.getPlayer(), GameEvent.BLOCK_CHANGE, context.getBlockPos());
			Block.dropStack(context.getWorld(), context.getBlockPos(), context.getSide(), new ItemStack(droppedItem));
		};
	}
}
