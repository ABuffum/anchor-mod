package fun.mousewich.gen.data.recipe;

import fun.mousewich.ModBase;
import fun.mousewich.container.ArrowContainer;
import fun.mousewich.gen.data.tag.ModItemTags;
import net.minecraft.data.server.RecipesProvider;
import net.minecraft.data.server.recipe.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.CookingRecipeSerializer;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.tag.ItemTags;
import net.minecraft.tag.Tag;

import java.util.Arrays;

import static fun.mousewich.ModBase.WOODCUTTING_RECIPE_SERIALIZER;

public class Recipes {
	private static CraftingRecipeJsonFactory make(CraftingRecipeJsonFactory recipe) {
		return recipe.criterion("automatic", RecipesProvider.conditionsFromItem(Items.STICK));
	}

	private static CookingRecipeJsonFactory makeCooking(ItemConvertible output, Ingredient ingredient, int cookingTime, double experience, CookingRecipeSerializer<?> serializer) {
		return CookingRecipeJsonFactory.create(ingredient, output, (float)experience, cookingTime, serializer)
				.criterion("automatic", RecipesProvider.conditionsFromItem(Items.STICK));
	}
	private static CookingRecipeJsonFactory makeCooking(ItemConvertible output, ItemConvertible ingredient, int cookingTime, double experience, CookingRecipeSerializer<?> serializer) {
		return CookingRecipeJsonFactory.create(Ingredient.ofItems(ingredient), output, (float)experience, cookingTime, serializer)
				.criterion(RecipesProvider.hasItem(ingredient), RecipesProvider.conditionsFromItem(ingredient));
	}
	public static CookingRecipeJsonFactory MakeSmelting(ItemConvertible output, ItemConvertible ingredient, int cookingTime, double experience) {
		return makeCooking(output, ingredient, cookingTime, experience, RecipeSerializer.SMELTING);
	}
	public static CookingRecipeJsonFactory MakeSmelting(ItemConvertible output, Ingredient ingredient, int cookingTime, double experience) {
		return makeCooking(output, ingredient, cookingTime, experience, RecipeSerializer.SMELTING);
	}
	public static CookingRecipeJsonFactory MakeBlasting(ItemConvertible output, ItemConvertible ingredient, int cookingTime, double experience) {
		return makeCooking(output, ingredient, cookingTime, experience, RecipeSerializer.BLASTING);
	}
	public static CookingRecipeJsonFactory MakeBlasting(ItemConvertible output, Ingredient ingredient, int cookingTime, double experience) {
		return makeCooking(output, ingredient, cookingTime, experience, RecipeSerializer.BLASTING);
	}
	public static CookingRecipeJsonFactory MakeSmoking(ItemConvertible output, ItemConvertible ingredient, int cookingTime, double experience) {
		return makeCooking(output, ingredient, cookingTime, experience, RecipeSerializer.SMOKING);
	}
	public static CookingRecipeJsonFactory MakeSmoking(ItemConvertible output, Ingredient ingredient, int cookingTime, double experience) {
		return makeCooking(output, ingredient, cookingTime, experience, RecipeSerializer.SMOKING);
	}
	public static CookingRecipeJsonFactory MakeCampfireRecipe(ItemConvertible output, ItemConvertible ingredient, int cookingTime, double experience) {
		return makeCooking(output, ingredient, cookingTime, experience, RecipeSerializer.CAMPFIRE_COOKING);
	}
	public static CookingRecipeJsonFactory MakeCampfireRecipe(ItemConvertible output, Tag.Identified<Item> ingredient, int cookingTime, double experience) {
		return MakeCampfireRecipe(output, Ingredient.fromTag(ingredient), cookingTime, experience);
	}
	public static CookingRecipeJsonFactory MakeCampfireRecipe(ItemConvertible output, Ingredient ingredient, int cookingTime, double experience) {
		return makeCooking(output, ingredient, cookingTime, experience, RecipeSerializer.CAMPFIRE_COOKING);
	}

	public static SingleItemRecipeJsonFactory MakeStonecutting(ItemConvertible output, ItemConvertible ingredient) { return MakeStonecutting(output, ingredient, 1); }
	public static SingleItemRecipeJsonFactory MakeStonecutting(ItemConvertible output, ItemConvertible ingredient, int count) {
		return SingleItemRecipeJsonFactory.createStonecutting(Ingredient.ofItems(ingredient), output, count)
				.criterion(RecipesProvider.hasItem(ingredient), RecipesProvider.conditionsFromItem(ingredient));
	}
	public static SingleItemRecipeJsonFactory MakeWoodcutting(ItemConvertible output, ItemConvertible ingredient) { return MakeWoodcutting(output, ingredient, 1); }
	public static SingleItemRecipeJsonFactory MakeWoodcutting(ItemConvertible output, ItemConvertible ingredient, int count) {
		return new SingleItemRecipeJsonFactory(WOODCUTTING_RECIPE_SERIALIZER, Ingredient.ofItems(ingredient), output, count)
				.criterion(RecipesProvider.hasItem(ingredient), RecipesProvider.conditionsFromItem(ingredient));
	}

	public static ShapelessRecipeJsonFactory MakeShapeless(ItemConvertible output, ItemConvertible ingredient) { return MakeShapeless(output, ingredient, 1); }
	public static ShapelessRecipeJsonFactory MakeShapeless(ItemConvertible output, ItemConvertible ingredient, int count) {
		return MakeShapeless(output, Ingredient.ofItems(ingredient), count);
	}
	public static ShapelessRecipeJsonFactory MakeShapeless(ItemConvertible output, Ingredient ingredient) { return MakeShapeless(output, ingredient, 1); }
	public static ShapelessRecipeJsonFactory MakeShapeless(ItemConvertible output, Ingredient ingredient, int count) {
		return ShapelessRecipeJsonFactory.create(output, count).input(ingredient)
				.criterion("automatic", RecipesProvider.conditionsFromItem(Items.STICK));
	}

	public static ShapedRecipeJsonFactory MakeShaped(ItemConvertible output, ItemConvertible ingredient) { return MakeShaped(output, ingredient, 1); }
	public static ShapedRecipeJsonFactory MakeShaped(ItemConvertible output, ItemConvertible ingredient, int count) {
		return MakeShaped(output, Ingredient.ofItems(ingredient), count);
	}
	public static ShapedRecipeJsonFactory MakeShaped(ItemConvertible output, Tag.Identified<Item> ingredient) { return MakeShaped(output, Ingredient.fromTag(ingredient)); }
	public static ShapedRecipeJsonFactory MakeShaped(ItemConvertible output, Tag.Identified<Item> ingredient, int count) { return MakeShaped(output, Ingredient.fromTag(ingredient), count); }
	public static ShapedRecipeJsonFactory MakeShaped(ItemConvertible output, Ingredient ingredient) { return MakeShaped(output, ingredient, 1); }
	public static ShapedRecipeJsonFactory MakeShaped(ItemConvertible output, Ingredient ingredient, int count) {
		return ShapedRecipeJsonFactory.create(output, count).input('#', ingredient)
				.criterion("automatic", RecipesProvider.conditionsFromItem(Items.STICK));
	}
	public static ShapedRecipeJsonFactory Make1x2(ItemConvertible output, ItemConvertible ingredient) { return Make1x2(output, ingredient, 1); }
	public static ShapedRecipeJsonFactory Make1x2(ItemConvertible output, ItemConvertible ingredient, int count) { return MakeShaped(output, ingredient, count).pattern("#").pattern("#"); }
	public static ShapedRecipeJsonFactory Make1x2(ItemConvertible output, Ingredient ingredient, int count) { return MakeShaped(output, ingredient, count).pattern("#").pattern("#"); }
	public static ShapedRecipeJsonFactory Make1x3(ItemConvertible output, ItemConvertible ingredient) { return Make1x3(output, ingredient, 1); }
	public static ShapedRecipeJsonFactory Make1x3(ItemConvertible output, ItemConvertible ingredient, int count) { return Make1x2(output, ingredient, count).pattern("#"); }
	public static ShapedRecipeJsonFactory Make1x3(ItemConvertible output, Ingredient ingredient, int count) { return Make1x2(output, ingredient, count).pattern("#"); }
	public static ShapedRecipeJsonFactory Make2x1(ItemConvertible output, ItemConvertible ingredient) { return Make2x1(output, ingredient, 1); }
	public static ShapedRecipeJsonFactory Make2x1(ItemConvertible output, ItemConvertible ingredient, int count) { return MakeShaped(output, ingredient, count).pattern("##"); }
	public static ShapedRecipeJsonFactory Make2x1(ItemConvertible output, Ingredient ingredient) { return Make2x1(output, ingredient, 1); }
	public static ShapedRecipeJsonFactory Make2x1(ItemConvertible output, Ingredient ingredient, int count) { return MakeShaped(output, ingredient, count).pattern("##"); }
	public static ShapedRecipeJsonFactory Make2x2(ItemConvertible output, Ingredient ingredient) { return Make2x2(output, ingredient, 1); }
	public static ShapedRecipeJsonFactory Make2x2(ItemConvertible output, Ingredient ingredient, int count) { return Make2x1(output, ingredient, count).pattern("##"); }
	public static ShapedRecipeJsonFactory Make2x2(ItemConvertible output, ItemConvertible ingredient) { return Make2x2(output, ingredient, 1); }
	public static ShapedRecipeJsonFactory Make2x2(ItemConvertible output, ItemConvertible ingredient, int count) { return Make2x1(output, ingredient, count).pattern("##"); }
	public static ShapedRecipeJsonFactory Make2x3(ItemConvertible output, ItemConvertible ingredient) { return Make2x3(output, ingredient, 1); }
	public static ShapedRecipeJsonFactory Make2x3(ItemConvertible output, ItemConvertible ingredient, int count) { return Make2x2(output, ingredient, count).pattern("##"); }
	public static ShapedRecipeJsonFactory Make3x1(ItemConvertible output, ItemConvertible ingredient) { return Make3x1(output, ingredient, 1); }
	public static ShapedRecipeJsonFactory Make3x1(ItemConvertible output, Ingredient ingredient) { return Make3x1(output, ingredient, 1); }
	public static ShapedRecipeJsonFactory Make3x1(ItemConvertible output, ItemConvertible ingredient, int count) { return MakeShaped(output, ingredient, count).pattern("###"); }
	public static ShapedRecipeJsonFactory Make3x1(ItemConvertible output, Ingredient ingredient, int count) { return MakeShaped(output, ingredient, count).pattern("###"); }
	public static ShapedRecipeJsonFactory Make3x2(ItemConvertible output, ItemConvertible ingredient) { return Make3x2(output, ingredient, 1); }
	public static ShapedRecipeJsonFactory Make3x2(ItemConvertible output, ItemConvertible ingredient, int count) { return Make3x2(output, Ingredient.ofItems(ingredient), count); }
	public static ShapedRecipeJsonFactory Make3x2(ItemConvertible output, Ingredient ingredient) { return Make3x2(output, ingredient, 1); }
	public static ShapedRecipeJsonFactory Make3x2(ItemConvertible output, Ingredient ingredient, int count) { return Make3x1(output, ingredient, count).pattern("###"); }
	public static ShapedRecipeJsonFactory Make3x3(ItemConvertible output, ItemConvertible ingredient) { return Make3x3(output, ingredient, 1); }
	public static ShapedRecipeJsonFactory Make3x3(ItemConvertible output, ItemConvertible ingredient, int count) { return Make3x2(output, ingredient, count).pattern("###"); }

	public static CraftingRecipeJsonFactory MakeCuttingBoard(ItemConvertible input, ItemConvertible... output) { return MakeCuttingBoard(ModItemTags.KNIVES, input, output); }
	public static CraftingRecipeJsonFactory MakeCuttingBoard(ItemConvertible tool, ItemConvertible input, ItemConvertible... output) { return MakeCuttingBoard(Ingredient.ofItems(tool), input, output); }
	public static CraftingRecipeJsonFactory MakeCuttingBoard(Tag.Identified<Item> tool, ItemConvertible input, ItemConvertible... output) { return MakeCuttingBoard(Ingredient.fromTag(tool), input, output); }
	public static CraftingRecipeJsonFactory MakeCuttingBoard(Ingredient tool, ItemConvertible input, ItemConvertible... output) {
		return new CuttingBoardRecipeJsonBuilder(tool, Arrays.stream(output).map(ItemConvertible::asItem).toArray(Item[]::new)).input(input)
				.criterion(RecipesProvider.hasItem(input), RecipesProvider.conditionsFromItem(input));
	}

	public static ShapedRecipeJsonFactory MakeArmorTrim(ItemConvertible output, ItemConvertible ingredient) {
		return MakeShaped(output, Items.DIAMOND, 2).input('S', output).input('C', ingredient).pattern("#S#").pattern("#C#").pattern("###");
	}
	public static CraftingRecipeJsonFactory MakeAxe(ItemConvertible output, ItemConvertible ingredient) { return MakeAxe(output, ingredient, Items.STICK); }
	public static CraftingRecipeJsonFactory MakeAxe(ItemConvertible output, ItemConvertible ingredient, ItemConvertible stick) {
		return MakeShaped(output, ingredient).input('|', stick).pattern("##").pattern("#|").pattern(" |");
	}
	public static CraftingRecipeJsonFactory MakeBarrel(ItemConvertible output, ItemConvertible planks, ItemConvertible slabs) {
		return MakeShaped(output, planks).input('S', slabs).pattern("#S#").pattern("# #").pattern("#S#");
	}
	public static CraftingRecipeJsonFactory MakeBeehive(ItemConvertible output, ItemConvertible planks) {
		return Make3x1(output, Items.HONEYCOMB).input('P', planks).pattern("PPP").pattern("###");
	}
	public static CraftingRecipeJsonFactory MakeBed(ItemConvertible output, ItemConvertible ingredient) {
		return Make3x1(output, ingredient).input('X', ItemTags.PLANKS).pattern("XXX");
	}
	public static CraftingRecipeJsonFactory MakeBed(ItemConvertible output, Ingredient ingredient) {
		return Make3x1(output, ingredient).input('X', ItemTags.PLANKS).pattern("XXX");
	}
	public static CraftingRecipeJsonFactory MakeBoat(ItemConvertible output, ItemConvertible ingredient) {
		return MakeShaped(output, ingredient).pattern("# #").pattern("###");
	}
	public static CraftingRecipeJsonFactory MakeChain(ItemConvertible output, ItemConvertible ingot, ItemConvertible nugget) {
		return MakeShaped(output, ingot).input('N', nugget).pattern("N").pattern("#").pattern("N");
	}
	public static CraftingRecipeJsonFactory MakeBookshelf(ItemConvertible output, ItemConvertible ingredient) {
		return Make3x1(output, ingredient).input('X', ModItemTags.BOOKS).pattern("XXX").pattern("###");
	}
	public static CraftingRecipeJsonFactory MakeChiseledBookshelf(ItemConvertible output, ItemConvertible ingredient) {
		return Make3x1(output, ingredient).input('X', ItemTags.WOODEN_SLABS).pattern("XXX").pattern("###");
	}
	public static CraftingRecipeJsonFactory MakeBoots(ItemConvertible output, ItemConvertible ingredient) {
		return MakeBoots(output, Ingredient.ofItems(ingredient));
	}
	public static CraftingRecipeJsonFactory MakeBoots(ItemConvertible output, Ingredient ingredient) {
		return MakeShaped(output, ingredient).pattern("# #").pattern("# #");
	}
	public static CraftingRecipeJsonFactory MakeCampfire(ItemConvertible output, Tag.Identified<Item> logs) { return MakeCampfire(output, logs, Ingredient.fromTag(ItemTags.COALS)); }
	public static CraftingRecipeJsonFactory MakeSoulCampfire(ItemConvertible output, Tag.Identified<Item> logs) { return MakeCampfire(output, logs, Ingredient.fromTag(ItemTags.SOUL_FIRE_BASE_BLOCKS)); }
	public static CraftingRecipeJsonFactory MakeEnderCampfire(ItemConvertible output, Tag.Identified<Item> logs) { return MakeCampfire(output, logs, Ingredient.ofItems(Items.POPPED_CHORUS_FRUIT)); }
	public static CraftingRecipeJsonFactory MakeCampfire(ItemConvertible output, Tag.Identified<Item> logs, Ingredient coal) {
		return make(ShapedRecipeJsonFactory.create(output).input('C', coal).input('L', logs).input('S', Items.STICK)
				.pattern(" S ").pattern("SCS").pattern("LLL"));
	}
	public static CraftingRecipeJsonFactory MakeCarpet(ItemConvertible output, ItemConvertible ingredient) { return Make2x1(output, ingredient, 3); }
	public static CraftingRecipeJsonFactory MakeChestplate(ItemConvertible output, ItemConvertible ingredient) {
		return MakeChestplate(output, Ingredient.ofItems(ingredient));
	}
	public static CraftingRecipeJsonFactory MakeChestplate(ItemConvertible output, Ingredient ingredient) {
		return MakeShaped(output, ingredient).pattern("# #").pattern("###").pattern("###");
	}
	public static CraftingRecipeJsonFactory MakeDoor(ItemConvertible output, ItemConvertible ingredient) {
		return Make2x3(output, ingredient, 3);
	}
	public static CraftingRecipeJsonFactory MakeGoldenFood(ItemConvertible output, ItemConvertible ingredient) {
		return Make3x1(output, Items.GOLD_NUGGET).input('*', ingredient).pattern("#*#").pattern("###");
	}
	public static CraftingRecipeJsonFactory MakeGourdLantern(ItemConvertible output, ItemConvertible gourd, Tag.Identified<Item> torches) {
		return MakeShaped(output, gourd).input('B', torches).pattern("#").pattern("B");
	}
	public static CraftingRecipeJsonFactory MakeGourdLantern(ItemConvertible output, ItemConvertible gourd) { return MakeGourdLantern(output, gourd, ModItemTags.TORCHES); }
	public static CraftingRecipeJsonFactory MakeSoulGourdLantern(ItemConvertible output, ItemConvertible gourd) { return MakeGourdLantern(output, gourd, ModItemTags.SOUL_TORCHES); }
	public static CraftingRecipeJsonFactory MakeEnderGourdLantern(ItemConvertible output, ItemConvertible gourd) { return MakeGourdLantern(output, gourd, ModItemTags.ENDER_TORCHES); }
	public static CraftingRecipeJsonFactory MakeHammer(ItemConvertible output, ItemConvertible ingredient) { return MakeHammer(output, ingredient, Items.STICK); }
	public static CraftingRecipeJsonFactory MakeHammer(ItemConvertible output, ItemConvertible ingredient, ItemConvertible stick) {
		return MakeShaped(output, ingredient).input('|', Items.STICK).pattern("#|#").pattern(" | ").pattern(" | ");
	}
	public static CraftingRecipeJsonFactory MakeHelmet(ItemConvertible output, ItemConvertible ingredient) {
		return Make3x1(output, ingredient).pattern("# #");
	}
	public static CraftingRecipeJsonFactory MakeHelmet(ItemConvertible output, Ingredient ingredient) {
		return Make3x1(output, ingredient).pattern("# #");
	}
	public static CraftingRecipeJsonFactory MakeHoe(ItemConvertible output, ItemConvertible ingredient) { return MakeHoe(output, ingredient, Items.STICK); }
	public static CraftingRecipeJsonFactory MakeHoe(ItemConvertible output, ItemConvertible ingredient, ItemConvertible stick) {
		return MakeShaped(output, ingredient).input('|', stick).pattern("##").pattern(" |").pattern(" |");
	}
	public static CraftingRecipeJsonFactory MakeHorseArmor(ItemConvertible output, ItemConvertible ingredient) {
		return MakeHorseArmor(output, Ingredient.ofItems(ingredient));
	}
	public static CraftingRecipeJsonFactory MakeHorseArmor(ItemConvertible output, Ingredient ingredient) {
		return MakeShaped(output, ingredient).pattern("# #").pattern("###").pattern("# #");
	}
	public static CraftingRecipeJsonFactory MakeKnife(ItemConvertible output, ItemConvertible ingredient) { return MakeKnife(output, ingredient, Items.STICK); }
	public static CraftingRecipeJsonFactory MakeKnife(ItemConvertible output, ItemConvertible ingredient, ItemConvertible stick) {
		return MakeShaped(output, ingredient).input('|', stick).pattern(" #").pattern("| ");
	}
	public static CraftingRecipeJsonFactory MakeLantern(ItemConvertible output, ItemConvertible ingredient, Tag.Identified<Item> torch) {
		return Make3x1(output, ingredient).input('T', torch).pattern("#T#").pattern("###");
	}
	public static CraftingRecipeJsonFactory MakeLadder(ItemConvertible output, ItemConvertible ingredient) {
		return MakeShaped(output, ingredient, 3).pattern("# #").pattern("###").pattern("# #");
	}
	public static CraftingRecipeJsonFactory MakeLeggings(ItemConvertible output, ItemConvertible ingredient) {
		return MakeLeggings(output, Ingredient.ofItems(ingredient));
	}
	public static CraftingRecipeJsonFactory MakeLeggings(ItemConvertible output, Ingredient ingredient) {
		return Make3x1(output, ingredient).pattern("# #").pattern("# #");
	}
	public static CraftingRecipeJsonFactory MakePickaxe(ItemConvertible output, ItemConvertible ingredient) { return MakePickaxe(output, ingredient, Items.STICK); }
	public static CraftingRecipeJsonFactory MakePickaxe(ItemConvertible output, ItemConvertible ingredient, ItemConvertible stick) {
		return MakeShaped(output, ingredient).input('|', stick).pattern("###").pattern(" | ").pattern(" | ");
	}
	public static CraftingRecipeJsonFactory MakePlanksLadder(ItemConvertible output, ItemConvertible ingredient) {
		return MakeShaped(output, ingredient, 3).input('|', Items.STICK).pattern("| |").pattern("|#|").pattern("| |");
	}
	public static CraftingRecipeJsonFactory MakePressurePlate(ItemConvertible output, ItemConvertible ingredient) {
		return Make2x1(output, ingredient);
	}
	public static CraftingRecipeJsonFactory MakeSandy(ItemConvertible output, ItemConvertible input) { return MakeShapeless(output, Items.SAND).input(input); }
	public static CraftingRecipeJsonFactory MakeShears(ItemConvertible output, ItemConvertible ingredient) {
		return MakeShaped(output, ingredient).pattern(" #").pattern("# ");
	}
	public static CraftingRecipeJsonFactory MakeShovel(ItemConvertible output, ItemConvertible ingredient) { return MakeShovel(output, ingredient, Items.STICK); }
	public static CraftingRecipeJsonFactory MakeShovel(ItemConvertible output, ItemConvertible ingredient, ItemConvertible stick) {
		return MakeShaped(output, ingredient).input('|', stick).pattern("#").pattern("|").pattern("|");
	}
	public static CraftingRecipeJsonFactory MakeSign(ItemConvertible output, ItemConvertible ingredient) { return MakeSign(output, Ingredient.ofItems(ingredient)); }
	public static CraftingRecipeJsonFactory MakeSign(ItemConvertible output, Ingredient ingredient) {
		return Make3x2(output, ingredient).input('|', Items.STICK).pattern(" | ");
	}
	public static CraftingRecipeJsonFactory MakeHangingSign(ItemConvertible output, ItemConvertible ingredient) { return MakeHangingSign(output, Ingredient.ofItems(ingredient)); }
	public static CraftingRecipeJsonFactory MakeHangingSign(ItemConvertible output, Ingredient ingredient) {
		return MakeShaped(output, ingredient).input('|', Items.CHAIN).pattern("| |").pattern("###").pattern("###");
	}
	public static CraftingRecipeJsonFactory MakeLectern(ItemConvertible output, ItemConvertible slabs) {
		return MakeShaped(output, slabs).input('B', ModItemTags.BOOKSHELVES).pattern("###").pattern(" B ").pattern(" # ");
	}
	public static CraftingRecipeJsonFactory MakePowderKeg(ItemConvertible output, ItemConvertible barrel) {
		return MakeShapeless(output, barrel).input(ModBase.GUNPOWDER_BLOCK);
	}
	public static CraftingRecipeJsonFactory MakeSlab(ItemConvertible output, ItemConvertible ingredient) { return Make3x1(output, ingredient, 6); }
	public static CraftingRecipeJsonFactory MakeStairs(ItemConvertible output, ItemConvertible ingredient) {
		return MakeShaped(output, ingredient, 4).pattern("#  ").pattern("## ").pattern("###");
	}
	public static CraftingRecipeJsonFactory MakeSword(ItemConvertible output, ItemConvertible ingredient) { return MakeSword(output, ingredient, Items.STICK); }
	public static CraftingRecipeJsonFactory MakeSword(ItemConvertible output, ItemConvertible ingredient, ItemConvertible stick) {
		return MakeShaped(output, ingredient).input('|', stick).pattern("#").pattern("#").pattern("|");
	}
	public static CraftingRecipeJsonFactory MakeSummoningArrow(ArrowContainer container, Item spawnEgg) {
		return MakeShapeless(container.asItem(), spawnEgg).input(ItemTags.ARROWS);
	}

	public static ShapedRecipeJsonFactory MakeTorch(ItemConvertible output, ItemConvertible stick) { return MakeTorch(output, ItemTags.COALS, stick); }
	public static ShapedRecipeJsonFactory MakeTorch(ItemConvertible output, ItemConvertible coal, ItemConvertible stick) { return MakeTorch(output, Ingredient.ofItems(coal), stick); }
	public static ShapedRecipeJsonFactory MakeTorch(ItemConvertible output, Tag.Identified<Item> coal, ItemConvertible stick) { return MakeTorch(output, Ingredient.fromTag(coal), stick); }
	public static ShapedRecipeJsonFactory MakeTorch(ItemConvertible output, Ingredient coal, ItemConvertible stick) { return MakeTorch(output, coal, stick, 1); }
	public static ShapedRecipeJsonFactory MakeTorch(ItemConvertible output, ItemConvertible stick, int count) { return MakeTorch(output, ItemTags.COALS, stick, count); }
	public static ShapedRecipeJsonFactory MakeTorch(ItemConvertible output, ItemConvertible coal, ItemConvertible stick, int count) { return MakeTorch(output, Ingredient.ofItems(coal), stick, count); }
	public static ShapedRecipeJsonFactory MakeTorch(ItemConvertible output, Tag.Identified<Item> coal, ItemConvertible stick, int count) { return MakeTorch(output, Ingredient.fromTag(coal), stick, count); }
	public static ShapedRecipeJsonFactory MakeTorch(ItemConvertible output, Ingredient coal, ItemConvertible stick, int count) {
		return MakeShaped(output, coal, count).input('|', stick).pattern("#").pattern("|");
	}

	public static ShapedRecipeJsonFactory MakeEnderTorch(ItemConvertible output, ItemConvertible stick) { return MakeEnderTorch(output, ItemTags.COALS, stick); }
	public static ShapedRecipeJsonFactory MakeEnderTorch(ItemConvertible output, ItemConvertible coal, ItemConvertible stick) { return MakeEnderTorch(output, Ingredient.ofItems(coal), stick); }
	public static ShapedRecipeJsonFactory MakeEnderTorch(ItemConvertible output, Tag.Identified<Item> coal, ItemConvertible stick) { return MakeEnderTorch(output, Ingredient.fromTag(coal), stick); }
	public static ShapedRecipeJsonFactory MakeEnderTorch(ItemConvertible output, Ingredient coal, ItemConvertible stick) { return MakeEnderTorch(output, coal, stick, 1); }
	public static ShapedRecipeJsonFactory MakeEnderTorch(ItemConvertible output, ItemConvertible stick, int count) { return MakeEnderTorch(output, ItemTags.COALS, stick, count); }
	public static ShapedRecipeJsonFactory MakeEnderTorch(ItemConvertible output, ItemConvertible coal, ItemConvertible stick, int count) { return MakeEnderTorch(output, Ingredient.ofItems(coal), stick, count); }
	public static ShapedRecipeJsonFactory MakeEnderTorch(ItemConvertible output, Tag.Identified<Item> coal, ItemConvertible stick, int count) { return MakeEnderTorch(output, Ingredient.fromTag(coal), stick, count); }
	public static ShapedRecipeJsonFactory MakeEnderTorch(ItemConvertible output, Ingredient coal, ItemConvertible stick, int count) {
		return MakeTorch(output, coal, stick, count).input('*', Items.POPPED_CHORUS_FRUIT).pattern("*");
	}

	public static ShapedRecipeJsonFactory MakeSoulTorch(ItemConvertible output, ItemConvertible stick) { return MakeSoulTorch(output, ItemTags.COALS, stick); }
	public static ShapedRecipeJsonFactory MakeSoulTorch(ItemConvertible output, ItemConvertible coal, ItemConvertible stick) { return MakeSoulTorch(output, Ingredient.ofItems(coal), stick); }
	public static ShapedRecipeJsonFactory MakeSoulTorch(ItemConvertible output, Tag.Identified<Item> coal, ItemConvertible stick) { return MakeSoulTorch(output, Ingredient.fromTag(coal), stick); }
	public static ShapedRecipeJsonFactory MakeSoulTorch(ItemConvertible output, Ingredient coal, ItemConvertible stick) { return MakeSoulTorch(output, coal, stick, 1); }
	public static ShapedRecipeJsonFactory MakeSoulTorch(ItemConvertible output, ItemConvertible stick, int count) { return MakeSoulTorch(output, ItemTags.COALS, stick, count); }
	public static ShapedRecipeJsonFactory MakeSoulTorch(ItemConvertible output, ItemConvertible coal, ItemConvertible stick, int count) { return MakeSoulTorch(output, Ingredient.ofItems(coal), stick, count); }
	public static ShapedRecipeJsonFactory MakeSoulTorch(ItemConvertible output, Tag.Identified<Item> coal, ItemConvertible stick, int count) { return MakeSoulTorch(output, Ingredient.fromTag(coal), stick, count); }
	public static ShapedRecipeJsonFactory MakeSoulTorch(ItemConvertible output, Ingredient coal, ItemConvertible stick, int count) {
		return MakeTorch(output, coal, stick, count).input('*', ItemTags.SOUL_FIRE_BASE_BLOCKS).pattern("*");
	}

	public static ShapedRecipeJsonFactory MakeUnderwaterTorch(ItemConvertible output, ItemConvertible stick) { return MakeTorch(output, stick, 1); }
	public static ShapedRecipeJsonFactory MakeUnderwaterTorch(ItemConvertible output, ItemConvertible stick, int count) {
		return MakeShaped(output, Items.INK_SAC, count).input('|', stick).pattern("#").pattern("|");
	}

	public static CraftingRecipeJsonFactory MakeWall(ItemConvertible output, ItemConvertible ingredient) { return Make3x2(output, ingredient, 6); }
	public static CraftingRecipeJsonFactory MakeWoodcutter(ItemConvertible output, ItemConvertible ingredient) {
		return MakeShaped(output, ingredient).input('I', Items.IRON_INGOT).pattern(" I ").pattern("###");
	}
	public static CraftingRecipeJsonFactory MakeWoodenFence(ItemConvertible output, ItemConvertible ingredient) {
		return MakeShaped(output, ingredient).input('|', Items.STICK).pattern("#|#").pattern("#|#");
	}
	public static CraftingRecipeJsonFactory MakeWoodenFenceGate(ItemConvertible output, ItemConvertible ingredient) {
		return MakeShaped(output, ingredient).input('|', Items.STICK).pattern("|#|").pattern("|#|");
	}
	public static CraftingRecipeJsonFactory MakeWoodenTrapdoor(ItemConvertible output, ItemConvertible ingredient) {
		return Make3x2(output, ingredient, 3);
	}


}
