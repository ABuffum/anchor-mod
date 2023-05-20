package fun.mousewich.dispenser;

import fun.mousewich.block.BlockConvertible;
import fun.mousewich.block.gourd.ModCarvedPumpkinBlock;
import net.minecraft.block.Block;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.FallibleItemDispenserBehavior;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class ModCarvedPumpkinBlockDispenserBehavior extends FallibleItemDispenserBehavior {
	public final ModCarvedPumpkinBlock block;

	public ModCarvedPumpkinBlockDispenserBehavior(BlockConvertible block) { this(block.asBlock()); }
	public ModCarvedPumpkinBlockDispenserBehavior(Block block) { this((ModCarvedPumpkinBlock)block); }
	public ModCarvedPumpkinBlockDispenserBehavior(ModCarvedPumpkinBlock block) { this.block = block; }

	protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
		World world = pointer.getWorld();
		BlockPos blockPos = pointer.getPos().offset(pointer.getBlockState().get(DispenserBlock.FACING));
		ModCarvedPumpkinBlock carvedPumpkinBlock = this.block;
		if (world.isAir(blockPos) && carvedPumpkinBlock.canDispense(world, blockPos)) {
			if (!world.isClient) {
				world.setBlockState(blockPos, carvedPumpkinBlock.getDefaultState(), Block.NOTIFY_ALL);
				world.emitGameEvent(null, GameEvent.BLOCK_PLACE, blockPos);
			}
			stack.decrement(1);
			this.setSuccess(true);
		}
		else this.setSuccess(ArmorItem.dispenseArmor(pointer, stack));
		return stack;
	}
}
