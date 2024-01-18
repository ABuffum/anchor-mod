package fun.mousewich.mixins.block;

import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.DispenserBehavior;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(DispenserBlock.class)
public interface DispenserBlockInvoker {
	@Invoker("getBehaviorForItem")
	DispenserBehavior InvokeGetBehaviorForItem(ItemStack stack);
}
