package haven.recipes;

import haven.HavenMod;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.CuttingRecipe;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class WoodcuttingRecipe extends CuttingRecipe {
	public WoodcuttingRecipe(Identifier id, String group, Ingredient input, ItemStack output) {
		super(HavenMod.WOODCUTTING_RECIPE_TYPE, HavenMod.WOODCUTTING_RECIPE_SERIALIZER, id, group, input, output);
	}
	public boolean matches(Inventory inventory, World world) { return this.input.test(inventory.getStack(0)); }
	public ItemStack createIcon() { return new ItemStack(HavenMod.DARK_OAK_MATERIAL.getWoodcutter().ITEM); }
}
