package fun.wich.recipe;

import fun.wich.ModBase;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.CuttingRecipe;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class WoodcuttingRecipe extends CuttingRecipe {
	public WoodcuttingRecipe(Identifier id, String group, Ingredient input, ItemStack output) {
		super(ModBase.WOODCUTTING_RECIPE_TYPE, ModBase.WOODCUTTING_RECIPE_SERIALIZER, id, group, input, output);
	}
	public boolean matches(Inventory inventory, World world) { return this.input.test(inventory.getStack(0)); }
	public ItemStack createIcon() { return new ItemStack(ModBase.WOODCUTTER.asItem()); }
}
