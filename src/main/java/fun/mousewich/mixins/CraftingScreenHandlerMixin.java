package fun.mousewich.mixins;

import fun.mousewich.block.basic.ModCraftingTableBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.screen.AbstractRecipeScreenHandler;
import net.minecraft.screen.CraftingScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CraftingScreenHandler.class)
public abstract class CraftingScreenHandlerMixin extends AbstractRecipeScreenHandler<CraftingInventory> {
	@Shadow @Final private ScreenHandlerContext context;

	public CraftingScreenHandlerMixin(ScreenHandlerType<?> screenHandlerType, int i) { super(screenHandlerType, i); }

	@Inject(method="canUse", at=@At("HEAD"), cancellable=true)
	public void CanUseModCraftingTable(PlayerEntity player, CallbackInfoReturnable<Boolean> cir) {
		if (context.get((world, pos) -> world.getBlockState(pos).getBlock() instanceof ModCraftingTableBlock && player.squaredDistanceTo(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) <= 64, true)) {
			cir.setReturnValue(true);
		}
	}
}
