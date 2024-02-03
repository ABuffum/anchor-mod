package fun.wich.mixins.block.entity;

import fun.wich.entity.HopperTransferable;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.Hopper;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HopperBlockEntity.class)
public abstract class HopperBlockEntityMixin extends LootableContainerBlockEntity implements Hopper {
	protected HopperBlockEntityMixin(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) { super(blockEntityType, blockPos, blockState); }

	@Inject(method="extract(Lnet/minecraft/block/entity/Hopper;Lnet/minecraft/inventory/Inventory;ILnet/minecraft/util/math/Direction;)Z", at=@At("HEAD"), cancellable=true)
	private static void Extract(Hopper hopper, Inventory inventory, int slot, Direction side, CallbackInfoReturnable<Boolean> cir) {
		if (!(inventory instanceof HopperTransferable hopperTransferable)) return;
		ItemStack itemStack = inventory.getStack(slot);
		if (!itemStack.isEmpty() && CanExtract(hopper, hopperTransferable, itemStack, slot, side)) {
			ItemStack itemStack2 = itemStack.copy();
			ItemStack itemStack3 = HopperBlockEntity.transfer(inventory, hopper, inventory.removeStack(slot, 1), null);
			if (itemStack3.isEmpty()) {
				inventory.markDirty();
				cir.setReturnValue(true);
			}
			inventory.setStack(slot, itemStack2);
		}
		cir.setReturnValue(false);
	}

	private static boolean CanExtract(Hopper hopper, HopperTransferable hopperTransferable, ItemStack stack, int slot, Direction facing) {
		if (!hopperTransferable.canTransferTo(hopper, slot, stack)) return false;
		return !(hopperTransferable instanceof SidedInventory sidedInventory) || sidedInventory.canExtract(slot, stack, facing);
	}
}
