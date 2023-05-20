package fun.mousewich.gen.data.recipe;

import com.google.gson.JsonObject;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementRewards;
import net.minecraft.advancement.CriterionMerger;
import net.minecraft.advancement.criterion.CriterionConditions;
import net.minecraft.advancement.criterion.RecipeUnlockedCriterion;
import net.minecraft.data.server.recipe.CraftingRecipeJsonFactory;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.function.Consumer;

public class SingleItemIdentifierRecipeJsonBuilder implements CraftingRecipeJsonFactory {
	private final Item output;
	private final Identifier input;
	private final int count;
	private final Advancement.Task advancementBuilder = Advancement.Task.create();
	private String group;
	private final RecipeSerializer<?> serializer;

	public SingleItemIdentifierRecipeJsonBuilder(RecipeSerializer<?> serializer, Identifier input, ItemConvertible output, int outputCount) {
		this.serializer = serializer;
		this.output = output.asItem();
		this.input = input;
		this.count = outputCount;
	}

	@Override
	public SingleItemIdentifierRecipeJsonBuilder criterion(String string, CriterionConditions criterionConditions) {
		this.advancementBuilder.criterion(string, criterionConditions);
		return this;
	}

	@Override
	public SingleItemIdentifierRecipeJsonBuilder group(String string) {
		this.group = string;
		return this;
	}

	@Override
	public Item getOutputItem() { return this.output; }

	@Override
	public void offerTo(Consumer<RecipeJsonProvider> exporter, Identifier recipeId) {
		this.advancementBuilder.parent(new Identifier("recipes/root")).criterion("has_the_recipe", RecipeUnlockedCriterion.create(recipeId)).rewards(AdvancementRewards.Builder.recipe(recipeId)).criteriaMerger(CriterionMerger.OR);
		exporter.accept(new SingleItemIdentifierRecipeJsonProvider(recipeId, this.serializer, this.group == null ? "" : this.group, this.input, this.output, this.count, this.advancementBuilder, new Identifier(recipeId.getNamespace(), "recipes/" + this.output.getGroup().getName() + "/" + recipeId.getPath())));
	}

	public static class SingleItemIdentifierRecipeJsonProvider implements RecipeJsonProvider {
		private final Identifier recipeId;
		private final String group;
		private final Identifier input;
		private final Item output;
		private final int count;
		private final Advancement.Task advancementBuilder;
		private final Identifier advancementId;
		private final RecipeSerializer<?> serializer;

		public SingleItemIdentifierRecipeJsonProvider(Identifier recipeId, RecipeSerializer<?> serializer, String group, Identifier input, Item output, int outputCount, Advancement.Task advancementBuilder, Identifier advancementId) {
			this.recipeId = recipeId;
			this.serializer = serializer;
			this.group = group;
			this.input = input;
			this.output = output;
			this.count = outputCount;
			this.advancementBuilder = advancementBuilder;
			this.advancementId = advancementId;
		}
		@Override
		public void serialize(JsonObject json) {
			if (!this.group.isEmpty()) json.addProperty("group", this.group);
			JsonObject ingredient = new JsonObject();
			ingredient.addProperty("item", this.input.toString());
			json.add("ingredient", ingredient);
			json.addProperty("result", Registry.ITEM.getId(this.output).toString());
			json.addProperty("count", this.count);
		}
		@Override
		public Identifier getRecipeId() {
			return this.recipeId;
		}
		@Override
		public RecipeSerializer<?> getSerializer() { return this.serializer; }
		@Override
		public JsonObject toAdvancementJson() { return this.advancementBuilder.toJson(); }
		@Override
		public Identifier getAdvancementId() { return this.advancementId; }
	}
}
