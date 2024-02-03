package fun.wich.trim;

import com.google.gson.JsonObject;
import fun.wich.ModBase;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

public class TrimmingRecipe implements Recipe<Inventory> {
	private final Identifier id;
	final Ingredient template;
	final Ingredient base;
	final Ingredient addition;

	public TrimmingRecipe(Identifier id, Ingredient template, Ingredient base, Ingredient addition) {
		this.id = id;
		this.template = template;
		this.base = base;
		this.addition = addition;
	}
	@Override
	public boolean matches(Inventory inventory, World world) {
		return this.template.test(inventory.getStack(0)) && this.base.test(inventory.getStack(1)) && this.addition.test(inventory.getStack(2));
	}
	@Override
	public ItemStack craft(Inventory inventory) {
		ItemStack itemStack = inventory.getStack(1);
		if (this.base.test(itemStack)) {
			Optional<ArmorTrimMaterial> optional = ArmorTrimMaterial.get(inventory.getStack(2));
			Optional<ArmorTrimPattern> optional2 = ArmorTrimPattern.get(inventory.getStack(0));
			if (optional.isPresent() && optional2.isPresent()) {
				Optional<ArmorTrim> optional3 = ArmorTrim.getTrim(itemStack);
				if (optional3.isPresent() && optional3.get().equals(optional2.get(), optional.get())) return ItemStack.EMPTY;
				ItemStack itemStack2 = itemStack.copy();
				itemStack2.setCount(1);
				ArmorTrim armorTrim = new ArmorTrim(optional.get(), optional2.get());
				if (ArmorTrim.apply(itemStack2, armorTrim)) return itemStack2;
			}
		}
		return ItemStack.EMPTY;
	}
	@Override
	public ItemStack getOutput() {
		ItemStack itemStack = new ItemStack(Items.IRON_CHESTPLATE);
		Optional<ArmorTrimPattern> optional = Arrays.stream(ArmorTrimPattern.values()).findFirst();
		if (optional.isPresent()) {
			ArmorTrim armorTrim = new ArmorTrim(ArmorTrimMaterial.REDSTONE, optional.get());
			ArmorTrim.apply(itemStack, armorTrim);
		}
		return itemStack;
	}
	@Override
	public RecipeType<?> getType() { return ModBase.TRIMMING_RECIPE_TYPE; }
	@Override
	public boolean fits(int width, int height) { return width >= 3 && height >= 1; }
	@Override
	public ItemStack createIcon() { return new ItemStack(ModBase.TRIMMING_TABLE); }
	public boolean testTemplate(ItemStack stack) { return this.template.test(stack); }
	public boolean testBase(ItemStack stack) { return this.base.test(stack); }
	public boolean testAddition(ItemStack stack) { return this.addition.test(stack); }
	@Override
	public Identifier getId() { return this.id; }
	@Override
	public RecipeSerializer<?> getSerializer() { return ModBase.TRIMMING_RECIPE_SERIALIZER; }
	@Override
	public boolean isEmpty() { return Stream.of(this.template, this.base, this.addition).anyMatch(Ingredient::isEmpty); }

	public static class Serializer implements RecipeSerializer<TrimmingRecipe> {
		@Override
		public TrimmingRecipe read(Identifier identifier, JsonObject jsonObject) {
			Ingredient ingredient = Ingredient.fromJson(JsonHelper.getObject(jsonObject, "template"));
			Ingredient ingredient2 = Ingredient.fromJson(JsonHelper.getObject(jsonObject, "base"));
			Ingredient ingredient3 = Ingredient.fromJson(JsonHelper.getObject(jsonObject, "addition"));
			return new TrimmingRecipe(identifier, ingredient, ingredient2, ingredient3);
		}
		@Override
		public TrimmingRecipe read(Identifier identifier, PacketByteBuf packetByteBuf) {
			Ingredient ingredient = Ingredient.fromPacket(packetByteBuf);
			Ingredient ingredient2 = Ingredient.fromPacket(packetByteBuf);
			Ingredient ingredient3 = Ingredient.fromPacket(packetByteBuf);
			return new TrimmingRecipe(identifier, ingredient, ingredient2, ingredient3);
		}
		@Override
		public void write(PacketByteBuf packetByteBuf, TrimmingRecipe trimmingRecipe) {
			trimmingRecipe.template.write(packetByteBuf);
			trimmingRecipe.base.write(packetByteBuf);
			trimmingRecipe.addition.write(packetByteBuf);
		}
	}
}
