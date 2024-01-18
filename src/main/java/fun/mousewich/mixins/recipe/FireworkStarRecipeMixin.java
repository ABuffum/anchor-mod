package fun.mousewich.mixins.recipe;

import com.google.common.collect.Lists;
import fun.mousewich.entity.ModNbtKeys;
import fun.mousewich.item.basic.ModDyeItem;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.FireworkStarRecipe;
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
import java.util.Map;

@Mixin(FireworkStarRecipe.class)
public abstract class FireworkStarRecipeMixin extends SpecialCraftingRecipe {
	@Shadow @Final private static Map<Item, FireworkRocketItem.Type> TYPE_MODIFIER_MAP;
	@Shadow @Final private static Ingredient TYPE_MODIFIER;
	@Shadow @Final private static Ingredient FLICKER_MODIFIER;
	@Shadow @Final private static Ingredient TRAIL_MODIFIER;

	public FireworkStarRecipeMixin(Identifier id) { super(id); }

	@Redirect(method="matches(Lnet/minecraft/inventory/CraftingInventory;Lnet/minecraft/world/World;)Z", at=@At(value="INVOKE", target="Lnet/minecraft/item/ItemStack;getItem()Lnet/minecraft/item/Item;"))
	private Item SpoofDyeItem_matches(ItemStack instance) {
		Item item = instance.getItem();
		return item instanceof ModDyeItem ? Items.WHITE_DYE : item;
	}

	@Inject(method="craft(Lnet/minecraft/inventory/CraftingInventory;)Lnet/minecraft/item/ItemStack;", at=@At(value="HEAD"), cancellable=true)
	public void CraftAllowingModDye(CraftingInventory craftingInventory, CallbackInfoReturnable<ItemStack> cir) {
		ItemStack itemStack = new ItemStack(Items.FIREWORK_STAR);
		NbtCompound nbtCompound = itemStack.getOrCreateSubNbt(ModNbtKeys.EXPLOSION);
		FireworkRocketItem.Type type = FireworkRocketItem.Type.SMALL_BALL;
		ArrayList<Integer> list = Lists.newArrayList();
		boolean shouldCancel = false;
		for (int i = 0; i < craftingInventory.size(); ++i) {
			ItemStack itemStack2 = craftingInventory.getStack(i);
			if (itemStack2.isEmpty()) continue;
			Item item = itemStack2.getItem();
			if (TYPE_MODIFIER.test(itemStack2)) type = TYPE_MODIFIER_MAP.get(itemStack2.getItem());
			else if (FLICKER_MODIFIER.test(itemStack2)) nbtCompound.putBoolean(ModNbtKeys.FLICKER, true);
			else if (TRAIL_MODIFIER.test(itemStack2)) nbtCompound.putBoolean(ModNbtKeys.TRAIL, true);
			else if (item instanceof DyeItem dye) list.add(dye.getColor().getFireworkColor());
			else if (item instanceof ModDyeItem modDye) {
				list.add(modDye.getColor().getFireworkColor());
				shouldCancel = true;
			}
		}
		if (shouldCancel) {
			nbtCompound.putIntArray(ModNbtKeys.COLORS, list);
			nbtCompound.putByte(ModNbtKeys.TYPE, (byte)type.getId());
			cir.setReturnValue(itemStack);
		}
	}
}
