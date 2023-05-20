package fun.mousewich.dispenser;

import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.FallibleItemDispenserBehavior;
import net.minecraft.entity.passive.HorseBaseEntity;
import net.minecraft.item.HorseArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

import java.util.Iterator;
import java.util.List;

public class HorseArmorDispenserBehavior extends FallibleItemDispenserBehavior {
	@Override
	public ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
		BlockPos blockPos = pointer.getPos().offset(pointer.getBlockState().get(DispenserBlock.FACING));
		List<HorseBaseEntity> list = pointer.getWorld().getEntitiesByClass(HorseBaseEntity.class, new Box(blockPos),
				(horseBaseEntityx) -> horseBaseEntityx.isAlive() && horseBaseEntityx.hasArmorSlot());
		Iterator<HorseBaseEntity> var5 = list.iterator();
		HorseBaseEntity horseBaseEntity;
		do {
			if (!var5.hasNext()) return super.dispenseSilently(pointer, stack);
			horseBaseEntity = var5.next();
		} while(!horseBaseEntity.isHorseArmor(stack) || horseBaseEntity.hasArmorInSlot() || !horseBaseEntity.isTame());
		horseBaseEntity.getStackReference(401).set(stack.split(1));
		this.setSuccess(true);
		return stack;
	}

	public static HorseArmorItem Dispensible(HorseArmorItem item) {
		DispenserBlock.registerBehavior(item, new HorseArmorDispenserBehavior());
		return item;
	}
}
