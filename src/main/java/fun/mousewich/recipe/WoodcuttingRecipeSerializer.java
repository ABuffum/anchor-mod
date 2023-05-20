package fun.mousewich.recipe;

import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.CuttingRecipe;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.registry.Registry;

public class WoodcuttingRecipeSerializer<T extends CuttingRecipe> implements RecipeSerializer<T> {
	public final RecipeFactory<T> recipeFactory;

	public WoodcuttingRecipeSerializer(RecipeFactory<T> recipeFactory) { this.recipeFactory = recipeFactory; }

	public T read(Identifier identifier, JsonObject jsonObject) {
		String string = JsonHelper.getString(jsonObject, "group", "");
		Ingredient ingredient2;
		if (JsonHelper.hasArray(jsonObject, "ingredient")) {
			ingredient2 = Ingredient.fromJson(JsonHelper.getArray(jsonObject, "ingredient"));
		}
		else ingredient2 = Ingredient.fromJson(JsonHelper.getObject(jsonObject, "ingredient"));
		String string2 = JsonHelper.getString(jsonObject, "result");
		int i = JsonHelper.getInt(jsonObject, "count");
		ItemStack itemStack = new ItemStack(Registry.ITEM.get(new Identifier(string2)), i);
		return this.recipeFactory.create(identifier, string, ingredient2, itemStack);
	}

	public T read(Identifier identifier, PacketByteBuf packetByteBuf) {
		String string = packetByteBuf.readString();
		Ingredient ingredient = Ingredient.fromPacket(packetByteBuf);
		ItemStack itemStack = packetByteBuf.readItemStack();
		return this.recipeFactory.create(identifier, string, ingredient, itemStack);
	}

	public void write(PacketByteBuf packetByteBuf, T cuttingRecipe) {
		packetByteBuf.writeString(cuttingRecipe.getGroup());
		cuttingRecipe.getIngredients().get(0).write(packetByteBuf);
		packetByteBuf.writeItemStack(cuttingRecipe.getOutput());
	}

	public interface RecipeFactory<T extends CuttingRecipe> { T create(Identifier id, String group, Ingredient input, ItemStack output); }
}