package fun.mousewich.mixins;

import fun.mousewich.ModBase;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.recipe.BrewingRecipeRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BrewingRecipeRegistry.class)
public abstract class BrewingRecipeRegistryMixin {
	@Shadow private static void registerPotionRecipe(Potion input, Item item, Potion output) { }

	@Inject(method="registerDefaults", at=@At("TAIL"))
	private static void RegisterPotionRecipes(CallbackInfo ci) {
		registerPotionRecipe(Potions.WATER, ModBase.GILDED_WART.asItem(), Potions.AWKWARD);
		registerPotionRecipe(Potions.WATER, ModBase.WARPED_WART.asItem(), Potions.AWKWARD);
	}
}
