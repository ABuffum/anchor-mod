package haven.dispenser;

import haven.entities.vehicle.ModBoatEntity;
import haven.entities.vehicle.ModBoatType;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.*;
import net.minecraft.world.*;

public class ModBoatDispenserBehavior extends ItemDispenserBehavior {
	private final ItemDispenserBehavior itemDispenser = new ItemDispenserBehavior();
	private final ModBoatType boatType;

	public ModBoatDispenserBehavior(ModBoatType type) {
		this.boatType = type;
	}

	public ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
		Direction direction = (Direction)pointer.getBlockState().get(DispenserBlock.FACING);
		World world = pointer.getWorld();
		double d = pointer.getX() + (double)((float)direction.getOffsetX() * 1.125F);
		double e = pointer.getY() + (double)((float)direction.getOffsetY() * 1.125F);
		double f = pointer.getZ() + (double)((float)direction.getOffsetZ() * 1.125F);
		BlockPos blockPos = pointer.getPos().offset(direction);
		double i;
		if (world.getFluidState(blockPos).isIn(FluidTags.WATER)) {
			i = 1.0D;
		} else {
			if (!world.getBlockState(blockPos).isAir() || !world.getFluidState(blockPos.down()).isIn(FluidTags.WATER)) {
				return this.itemDispenser.dispense(pointer, stack);
			}

			i = 0.0D;
		}

		ModBoatEntity boatEntity = new ModBoatEntity(world, d, e + i, f);
		boatEntity.setHavenBoatType(this.boatType);
		boatEntity.setYaw(direction.asRotation());
		world.spawnEntity(boatEntity);
		stack.decrement(1);
		return stack;
	}

	protected void playSound(BlockPointer pointer) {
		pointer.getWorld().syncWorldEvent(WorldEvents.DISPENSER_DISPENSES, pointer.getPos(), 0);
	}
}
