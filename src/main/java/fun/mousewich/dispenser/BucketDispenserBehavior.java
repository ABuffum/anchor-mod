package fun.mousewich.dispenser;

import fun.mousewich.item.bucket.BucketProvider;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.FluidDrainable;
import net.minecraft.block.dispenser.FallibleItemDispenserBehavior;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.block.entity.DispenserBlockEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.event.GameEvent;

public class BucketDispenserBehavior extends FallibleItemDispenserBehavior {
	private final BucketProvider provider;
	public BucketDispenserBehavior(BucketProvider provider) { this.provider = provider; }
	private final ItemDispenserBehavior fallbackBehavior = new ItemDispenserBehavior();
	public ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
		WorldAccess worldAccess = pointer.getWorld();
		BlockPos blockPos = pointer.getPos().offset(pointer.getBlockState().get(DispenserBlock.FACING));
		BlockState blockState = worldAccess.getBlockState(blockPos);
		Block block = blockState.getBlock();
		if (block instanceof FluidDrainable) {
			ItemStack itemStack = ((FluidDrainable)block).tryDrainFluid(worldAccess, blockPos, blockState);
			itemStack = provider.bucketInMaterial(itemStack);
			if (itemStack.isEmpty()) return super.dispenseSilently(pointer, stack);
			else {
				worldAccess.emitGameEvent(null, GameEvent.FLUID_PICKUP, blockPos);
				Item item2 = itemStack.getItem();
				stack.decrement(1);
				if (stack.isEmpty()) return new ItemStack(item2);
				else {
					if (((DispenserBlockEntity)pointer.getBlockEntity()).addToFirstFreeSlot(new ItemStack(item2)) < 0) {
						this.fallbackBehavior.dispense(pointer, new ItemStack(item2));
					}
					return stack;
				}
			}
		}
		else return super.dispenseSilently(pointer, stack);
	}
}
