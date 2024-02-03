package fun.wich.gen.data.recipe;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.nhoryzon.mc.farmersdelight.registry.RecipeTypesRegistry;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementRewards;
import net.minecraft.advancement.CriterionMerger;
import net.minecraft.advancement.criterion.CriterionConditions;
import net.minecraft.advancement.criterion.RecipeUnlockedCriterion;
import net.minecraft.data.server.recipe.CraftingRecipeJsonFactory;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.List;
import java.util.function.Consumer;

public class CuttingBoardRecipeJsonBuilder implements CraftingRecipeJsonFactory {
	private final Item[] output;
	private final List<Ingredient> inputs = Lists.newArrayList();
	private final Ingredient tool;
	private final Advancement.Task advancementBuilder = Advancement.Task.create();
	private String group;

	public CuttingBoardRecipeJsonBuilder(Ingredient tool, Item... output) {
		this.output = output;
		this.tool = tool;
	}

	public CuttingBoardRecipeJsonBuilder input(Tag.Identified<Item> tag) { return this.input(Ingredient.fromTag(tag)); }
	public CuttingBoardRecipeJsonBuilder input(ItemConvertible itemProvider) { return this.input(itemProvider, 1); }
	public CuttingBoardRecipeJsonBuilder input(ItemConvertible itemProvider, int size) {
		for (int i = 0; i < size; ++i) this.input(Ingredient.ofItems(itemProvider));
		return this;
	}
	public CuttingBoardRecipeJsonBuilder input(Ingredient ingredient) { return this.input(ingredient, 1); }
	public CuttingBoardRecipeJsonBuilder input(Ingredient ingredient, int size) {
		for (int i = 0; i < size; ++i) this.inputs.add(ingredient);
		return this;
	}

	@Override
	public CraftingRecipeJsonFactory criterion(String string, CriterionConditions criterionConditions) {
		this.advancementBuilder.criterion(string, criterionConditions);
		return this;
	}

	@Override
	public CraftingRecipeJsonFactory group(String group) {
		this.group = group;
		return this;
	}

	@Override
	public Item getOutputItem() { return this.output[0]; }

	@Override
	public void offerTo(Consumer<RecipeJsonProvider> exporter, Identifier recipeId) {
		this.validate(recipeId);
		this.advancementBuilder.parent(new Identifier("recipes/root")).criterion("has_the_recipe", RecipeUnlockedCriterion.create(recipeId)).rewards(AdvancementRewards.Builder.recipe(recipeId)).criteriaMerger(CriterionMerger.OR);
		exporter.accept(new CuttingBoardRecipeJsonProvider(recipeId, this.output, this.group == null ? "" : this.group, this.inputs, this.tool, this.advancementBuilder, new Identifier(recipeId.getNamespace(), "recipes/" + this.output[0].getGroup().getName() + "/" + recipeId.getPath())));
	}

	private void validate(Identifier recipeId) {
		if (this.advancementBuilder.getCriteria().isEmpty()) {
			throw new IllegalStateException("No way of obtaining recipe " + recipeId);
		}
	}

	public static class CuttingBoardRecipeJsonProvider implements RecipeJsonProvider {
		private final Identifier recipeId;
		private final Item[] output;
		private final String group;
		private final List<Ingredient> inputs;
		private final Ingredient tool;
		private final Advancement.Task advancementBuilder;
		private final Identifier advancementId;

		public CuttingBoardRecipeJsonProvider(Identifier recipeId, Item[] output, String group, List<Ingredient> inputs, Ingredient tool, Advancement.Task advancementBuilder, Identifier advancementId) {
			this.recipeId = recipeId;
			this.output = output;
			this.group = group;
			this.inputs = inputs;
			this.tool = tool;
			this.advancementBuilder = advancementBuilder;
			this.advancementId = advancementId;
		}

		@Override
		public void serialize(JsonObject json) {
			if (!this.group.isEmpty()) json.addProperty("group", this.group);
			JsonArray jsonArray = new JsonArray();
			for (Ingredient ingredient : this.inputs) jsonArray.add(ingredient.toJson());
			json.add("ingredients", jsonArray);
			json.add("tool", tool.toJson());
			jsonArray = new JsonArray();
			JsonObject jsonObject;
			for (Item item : this.output) {
				jsonObject = new JsonObject();
				jsonObject.addProperty("item", Registry.ITEM.getId(item).toString());
				jsonArray.add(jsonObject);
			}
			json.add("result", jsonArray);
		}

		@Override
		public RecipeSerializer<?> getSerializer() { return RecipeTypesRegistry.CUTTING_RECIPE_SERIALIZER.serializer(); }
		@Override
		public Identifier getRecipeId() { return this.recipeId; }
		@Override
		public JsonObject toAdvancementJson() { return this.advancementBuilder.toJson(); }
		@Override
		public Identifier getAdvancementId() { return this.advancementId; }
	}
}
