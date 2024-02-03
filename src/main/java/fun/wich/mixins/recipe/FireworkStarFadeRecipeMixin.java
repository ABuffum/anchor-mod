package fun.wich.mixins.recipe;

import com.google.common.collect.Lists;
import fun.wich.entity.ModNbtKeys;
import fun.wich.item.basic.ModDyeItem;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.FireworkStarFadeRecipe;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;

@Mixin(FireworkStarFadeRecipe.class)
public abstract class FireworkStarFadeRecipeMixin extends SpecialCraftingRecipe {
	@Shadow @Final private static Ingredient INPUT_STAR;

	public FireworkStarFadeRecipeMixin(Identifier id) { super(id); }

	@Redirect(method="matches(Lnet/minecraft/inventory/CraftingInventory;Lnet/minecraft/world/World;)Z", at=@At(value="INVOKE", target="Lnet/minecraft/item/ItemStack;getItem()Lnet/minecraft/item/Item;"))
	private Item SpoofDyeItem_matches(ItemStack instance) {
		Item item = instance.getItem();
		return item instanceof ModDyeItem ? Items.WHITE_DYE : item;
	}

	@Inject(method="craft(Lnet/minecraft/inventory/CraftingInventory;)Lnet/minecraft/item/ItemStack;", at=@At(value="HEAD"), cancellable=true)
	public void CraftAllowingModDye(CraftingInventory craftingInventory, CallbackInfoReturnable<ItemStack> cir) {
		ArrayList<Integer> list = Lists.newArrayList();
		ItemStack itemStack = null;
		boolean shouldCancel = false;
		for (int i = 0; i < craftingInventory.size(); ++i) {
			ItemStack itemStack2 = craftingInventory.getStack(i);
			Item item = itemStack2.getItem();
			if (item instanceof DyeItem dye) list.add(dye.getColor().getFireworkColor());
			else if (item instanceof ModDyeItem modDye) {
				list.add(modDye.getColor().getFireworkColor());
				shouldCancel = true;
			}
			else if (INPUT_STAR.test(itemStack2)) {
				itemStack = itemStack2.copy();
				itemStack.setCount(1);
			}
		}
		if (shouldCancel) {
			if (itemStack == null || list.isEmpty()) cir.setReturnValue(ItemStack.EMPTY);
			itemStack.getOrCreateSubNbt(ModNbtKeys.EXPLOSION).putIntArray(ModNbtKeys.FADE_COLORS, list);
			cir.setReturnValue(itemStack);
		}
	}
}
