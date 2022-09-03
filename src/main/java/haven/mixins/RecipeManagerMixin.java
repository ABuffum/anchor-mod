package haven.mixins;

import haven.HavenMod;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(RecipeManager.class)
public class RecipeManagerMixin {
	@Inject(method = "getRemainingStacks", at = @At("HEAD"), cancellable = true)
	private <C extends Inventory, T extends Recipe<C>> void GetRemainder(RecipeType<T> type, C inventory, World world, CallbackInfoReturnable<DefaultedList<ItemStack>> cir) {
		Optional<T> optional = ((RecipeManager)(Object)this).getFirstMatch(type, inventory, world);
		if (optional.isPresent()) {
			Item output = ((Recipe)optional.get()).getOutput().getItem();
			if (output == HavenMod.CHOCOLATE_MILK_BUCKET || output == HavenMod.STRAWBERRY_MILK_BUCKET || output == HavenMod.COFFEE_MILK_BUCKET) {
				DefaultedList<ItemStack> defaultedList = DefaultedList.ofSize(inventory.size(), ItemStack.EMPTY);
				for(int i = 0; i < defaultedList.size(); ++i) {
					defaultedList.set(i, ItemStack.EMPTY);
				}
				cir.setReturnValue(defaultedList);
			}
		}
	}
}
