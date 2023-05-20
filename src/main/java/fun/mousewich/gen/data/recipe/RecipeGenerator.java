package fun.mousewich.gen.data.recipe;

import com.nhoryzon.mc.farmersdelight.registry.ItemsRegistry;
import fun.mousewich.ModConfig;
import fun.mousewich.container.BlockContainer;
import fun.mousewich.container.FlowerPartContainer;
import fun.mousewich.gen.data.tag.ModItemTags;
import fun.mousewich.haven.HavenMod;
import fun.mousewich.trim.ArmorTrimPattern;
import fun.mousewich.util.ColorUtil;
import fun.mousewich.util.OxidationScale;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipesProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.RecipesProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonFactory;
import net.minecraft.data.server.recipe.SmithingRecipeJsonFactory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static fun.mousewich.ModBase.*;

public class RecipeGenerator extends FabricRecipesProvider {
	public RecipeGenerator(FabricDataGenerator dataGenerator) { super(dataGenerator); }

	@Override
	protected Identifier getRecipeIdentifier(Identifier identifier) { return identifier; }

	private static void OfferCuttingBoardRecipe(Consumer<RecipeJsonProvider> exporter, ItemConvertible input, FlowerPartContainer container) {
		OfferCuttingBoardRecipe(exporter, input, container.petalsItem(), container.asItem());
	}
	private static void OfferCuttingBoardRecipe(Consumer<RecipeJsonProvider> exporter, ItemConvertible input, ItemConvertible... output) {
		Recipes.MakeCuttingBoard(input, output).offerTo(exporter, ID("cutting_board_" + getItemPath(input.asItem())));
	}

	private static void OfferSmeltingRecipe(Consumer<RecipeJsonProvider> exporter, ItemConvertible output, ItemConvertible ingredient) {
		OfferSmeltingRecipe(exporter, output, ingredient, 0.1);
	}
	private static void OfferSmeltingRecipe(Consumer<RecipeJsonProvider> exporter, ItemConvertible output, ItemConvertible ingredient, double experience) {
		OfferSmeltingRecipe(exporter, output, ingredient, 200, experience);
	}
	private static void OfferSmeltingRecipe(Consumer<RecipeJsonProvider> exporter, ItemConvertible output, ItemConvertible ingredient, int cookingTime, double experience) {
		OfferSmeltingRecipe(exporter, output, List.of(ingredient), cookingTime, experience);
	}
	private static void OfferSmeltingRecipe(Consumer<RecipeJsonProvider> exporter, ItemConvertible output, List<ItemConvertible> ingredients, int cookingTime, double experience) {
		offerSmelting(exporter, ingredients, output, (float)experience, cookingTime, null);
	}

	private static void OfferSmithingRecipe(Consumer<RecipeJsonProvider> exporter, ItemConvertible output, ItemConvertible ingredient, ItemConvertible input) {
		OfferSmithingRecipe(exporter, output, ingredient, input, ID(RecipesProvider.getItemPath(output) + "_smithing"));
	}
	private static void OfferSmithingRecipe(Consumer<RecipeJsonProvider> exporter, ItemConvertible output, ItemConvertible ingredient, ItemConvertible input, Identifier path) {
		SmithingRecipeJsonFactory.create(Ingredient.ofItems(ingredient), Ingredient.ofItems(input), output.asItem())
				.criterion(RecipesProvider.hasItem(ingredient), RecipesProvider.conditionsFromItem(ingredient))
				.offerTo(exporter, path);
	}
	private static void OfferStonecuttingRecipe(Consumer<RecipeJsonProvider> exporter, ItemConvertible output, ItemConvertible ingredient) {
		Recipes.MakeStonecutting(output, ingredient).offerTo(exporter,  ID(RecipesProvider.convertBetween(output, ingredient) + "_stonecutting"));
	}
	private static void OfferStonecuttingRecipe(Consumer<RecipeJsonProvider> exporter, ItemConvertible output, ItemConvertible ingredient, int count) {
		Recipes.MakeStonecutting(output, ingredient, count).offerTo(exporter, ID(RecipesProvider.convertBetween(output, ingredient) + "_stonecutting"));
	}
	private static void OfferWoodcuttingRecipe(Consumer<RecipeJsonProvider> exporter, ItemConvertible output, ItemConvertible ingredient) {
		Recipes.MakeWoodcutting(output, ingredient).offerTo(exporter,  ID(RecipesProvider.convertBetween(output, ingredient) + "_woodcutting"));
	}
	private static void OfferWoodcuttingRecipe(Consumer<RecipeJsonProvider> exporter, ItemConvertible output, ItemConvertible ingredient, int count) {
		Recipes.MakeWoodcutting(output, ingredient, count).offerTo(exporter,  ID(RecipesProvider.convertBetween(output, ingredient) + "_woodcutting"));
	}

	private static String RecipeName(ItemConvertible item, String postfix) { return RecipesProvider.getItemPath(item) + postfix; }
	private static String _from_campfire(ItemConvertible item) { return RecipeName(item, "_from_campfire_cooking"); }
	private static String _from_smoking(ItemConvertible item) { return RecipeName(item, "_from_smoking"); }

	//TODO: Several irregular recipes (stonecutting, smelting, etc.) are going to the minecraft data folder instead
	@Override
	protected void generateRecipes(Consumer<RecipeJsonProvider> exporter) {
		//Overrides
		Recipes.MakeShaped(Items.ARROW, Items.STICK, 4).input('X', Items.FLINT).input('Y', ModItemTags.FEATHERS)
				.pattern("X").pattern("#").pattern("Y").offerTo(exporter);
		for (DyeColor color : DyeColor.values()) { //Banners & Beds
			Ingredient ingredient = Ingredient.ofItems(ColorUtil.GetWoolItem(color), FLEECE.get(color).asItem());
			Recipes.MakeSign(ColorUtil.GetBannerItem(color), ingredient).offerTo(exporter);
			Recipes.MakeBed(ColorUtil.GetBedItem(color), ingredient).offerTo(exporter);
		}
		Recipes.MakeShapeless(Items.WRITABLE_BOOK, Items.INK_SAC).input(ModItemTags.BOOKS).input(ModItemTags.FEATHERS).offerTo(exporter);
		Recipes.Make1x2(Items.STICK, Ingredient.ofItems(Items.BAMBOO, DRIED_BAMBOO.asItem()), 1).group("sticks").offerTo(exporter, "stick_from_bamboo_item");
		Recipes.MakeShaped(Items.SCAFFOLDING, Ingredient.ofItems(Items.BAMBOO, DRIED_BAMBOO.asItem()), 6).input('~', Items.STRING)
				.pattern("#~#").pattern("# #").pattern("# #").offerTo(exporter);
		//Utility Recipes
		Recipes.MakeShapeless(Items.GLOW_INK_SAC, Items.INK_SAC).input(Items.GLOW_BERRIES).offerTo(exporter);
		Recipes.MakeShapeless(Items.NAME_TAG, Items.PAPER).input(Items.PAPER).input(Items.STRING).offerTo(exporter);
		Ingredient ironChain = Ingredient.ofItems(Items.CHAIN, IRON_CHAIN);
		Recipes.MakeShaped(Items.CHAINMAIL_HELMET, ironChain).input('N', Items.IRON_NUGGET).pattern("N#N").pattern("# #").offerTo(exporter);
		Recipes.MakeShaped(Items.CHAINMAIL_CHESTPLATE, ironChain).input('N', Items.IRON_NUGGET).pattern("# #").pattern("NNN").pattern("###").offerTo(exporter);
		Recipes.MakeShaped(Items.CHAINMAIL_LEGGINGS, ironChain).input('N', Items.IRON_NUGGET).pattern("N#N").pattern("# #").pattern("# #").offerTo(exporter);
		Recipes.MakeShaped(Items.CHAINMAIL_BOOTS, ironChain).input('N', Items.IRON_NUGGET).pattern("# #").pattern("N N").offerTo(exporter);
		Recipes.MakeShaped(Items.GRAVEL, Items.FLINT).input('S', Items.SAND).pattern("S#").pattern("#S").offerTo(exporter, ID("gravel_from_flint_and_sand"));
		Recipes.MakeSmelting(Items.DEAD_BUSH, Ingredient.fromTag(ItemTags.SAPLINGS), 200, 0.1).offerTo(exporter, ID("dead_bush_from_smelting_saplings"));
		Recipes.Make3x1(Items.TRIDENT, Items.IRON_INGOT).input('|', PRISMARINE_ROD).pattern(" | ").pattern(" | ").offerTo(exporter);
		//Coral Block Recipes
		Recipes.Make3x3(Items.BRAIN_CORAL_BLOCK, Items.BRAIN_CORAL).offerTo(exporter);
		Recipes.Make3x3(Items.BUBBLE_CORAL_BLOCK, Items.BUBBLE_CORAL).offerTo(exporter);
		Recipes.Make3x3(Items.FIRE_CORAL_BLOCK, Items.FIRE_CORAL).offerTo(exporter);
		Recipes.Make3x3(Items.HORN_CORAL_BLOCK, Items.HORN_CORAL).offerTo(exporter);
		Recipes.Make3x3(Items.TUBE_CORAL_BLOCK, Items.TUBE_CORAL).offerTo(exporter);
		Recipes.Make3x3(Items.DEAD_BRAIN_CORAL_BLOCK, Items.DEAD_BRAIN_CORAL).offerTo(exporter);
		Recipes.Make3x3(Items.DEAD_BUBBLE_CORAL_BLOCK, Items.DEAD_BUBBLE_CORAL).offerTo(exporter);
		Recipes.Make3x3(Items.DEAD_FIRE_CORAL_BLOCK, Items.DEAD_FIRE_CORAL).offerTo(exporter);
		Recipes.Make3x3(Items.DEAD_HORN_CORAL_BLOCK, Items.DEAD_HORN_CORAL).offerTo(exporter);
		Recipes.Make3x3(Items.DEAD_TUBE_CORAL_BLOCK, Items.DEAD_TUBE_CORAL).offerTo(exporter);
		//Smithing Templates
		Recipes.MakeArmorTrim(NETHERITE_UPGRADE_SMITHING_TEMPLATE, Items.NETHERRACK).offerTo(exporter);
		Recipes.MakeArmorTrim(ArmorTrimPattern.COAST, Items.COBBLESTONE).offerTo(exporter);
		Recipes.MakeArmorTrim(ArmorTrimPattern.DUNE, Items.SANDSTONE).offerTo(exporter);
		Recipes.MakeArmorTrim(ArmorTrimPattern.EYE, Items.END_STONE).offerTo(exporter);
		Recipes.MakeArmorTrim(ArmorTrimPattern.HOST, Items.TERRACOTTA).offerTo(exporter);
		Recipes.MakeArmorTrim(ArmorTrimPattern.RAISER, Items.TERRACOTTA).offerTo(exporter);
		Recipes.MakeArmorTrim(ArmorTrimPattern.RIB, Items.NETHERRACK).offerTo(exporter);
		Recipes.MakeArmorTrim(ArmorTrimPattern.SENTRY, Items.COBBLESTONE).offerTo(exporter);
		Recipes.MakeArmorTrim(ArmorTrimPattern.SHAPER, Items.TERRACOTTA).offerTo(exporter);
		Recipes.MakeArmorTrim(ArmorTrimPattern.SILENCE, Items.COBBLED_DEEPSLATE).offerTo(exporter);
		Recipes.MakeArmorTrim(ArmorTrimPattern.SNOUT, Items.BLACKSTONE).offerTo(exporter);
		Recipes.MakeArmorTrim(ArmorTrimPattern.SPIRE, Items.PURPUR_BLOCK).offerTo(exporter);
		Recipes.MakeArmorTrim(ArmorTrimPattern.TIDE, Items.PRISMARINE).offerTo(exporter);
		Recipes.MakeArmorTrim(ArmorTrimPattern.VEX, Items.COBBLESTONE).offerTo(exporter);
		Recipes.MakeArmorTrim(ArmorTrimPattern.WARD, Items.COBBLED_DEEPSLATE).offerTo(exporter);
		Recipes.MakeArmorTrim(ArmorTrimPattern.WAYFINDER, Items.TERRACOTTA).offerTo(exporter);
		Recipes.MakeArmorTrim(ArmorTrimPattern.WILD, Items.MOSSY_COBBLESTONE).offerTo(exporter);
		//Lanterns
		Recipes.MakeLantern(Items.LANTERN, Items.IRON_NUGGET, ModItemTags.TORCHES).offerTo(exporter);
		Recipes.MakeLantern(Items.SOUL_LANTERN, Items.IRON_NUGGET, ModItemTags.SOUL_TORCHES).offerTo(exporter);
		//<editor-fold desc="Gourds">
		Recipes.MakeGourdLantern(Items.JACK_O_LANTERN, Items.CARVED_PUMPKIN).offerTo(exporter);
		Recipes.MakeSoulGourdLantern(SOUL_JACK_O_LANTERN, Items.CARVED_PUMPKIN).offerTo(exporter);
		Recipes.MakeEnderGourdLantern(ENDER_JACK_O_LANTERN, Items.CARVED_PUMPKIN).offerTo(exporter);
		Recipes.MakeGourdLantern(WHITE_JACK_O_LANTERN, CARVED_WHITE_PUMPKIN).offerTo(exporter);
		Recipes.MakeSoulGourdLantern(WHITE_SOUL_JACK_O_LANTERN, CARVED_WHITE_PUMPKIN).offerTo(exporter);
		Recipes.MakeEnderGourdLantern(WHITE_ENDER_JACK_O_LANTERN, CARVED_WHITE_PUMPKIN).offerTo(exporter);
		Recipes.MakeGourdLantern(ROTTEN_JACK_O_LANTERN, CARVED_ROTTEN_PUMPKIN).offerTo(exporter);
		Recipes.MakeSoulGourdLantern(ROTTEN_SOUL_JACK_O_LANTERN, CARVED_ROTTEN_PUMPKIN).offerTo(exporter);
		Recipes.MakeEnderGourdLantern(ROTTEN_ENDER_JACK_O_LANTERN, CARVED_ROTTEN_PUMPKIN).offerTo(exporter);
		Recipes.MakeGourdLantern(MELON_LANTERN, CARVED_MELON).offerTo(exporter);
		Recipes.MakeSoulGourdLantern(SOUL_MELON_LANTERN, CARVED_MELON).offerTo(exporter);
		Recipes.MakeEnderGourdLantern(ENDER_MELON_LANTERN, CARVED_MELON).offerTo(exporter);
		//</editor-fold>
		//<editor-fold desc="Acacia">
		Recipes.MakeBeehive(ACACIA_BEEHIVE, Items.ACACIA_PLANKS).offerTo(exporter);
		Recipes.MakeBookshelf(ACACIA_BOOKSHELF, Items.ACACIA_PLANKS).offerTo(exporter);
		Recipes.MakeChiseledBookshelf(ACACIA_CHISELED_BOOKSHELF, Items.ACACIA_PLANKS).offerTo(exporter);
		Recipes.Make2x2(ACACIA_CRAFTING_TABLE, Items.ACACIA_PLANKS).offerTo(exporter);
		Recipes.MakePlanksLadder(ACACIA_LADDER, Items.ACACIA_PLANKS).offerTo(exporter);
		Recipes.MakeWoodcutter(ACACIA_WOODCUTTER, Items.ACACIA_PLANKS).offerTo(exporter);
		Recipes.MakeBarrel(ACACIA_BARREL, Items.ACACIA_PLANKS, Items.ACACIA_SLAB).offerTo(exporter);
		Recipes.MakeLectern(ACACIA_LECTERN, Items.ACACIA_SLAB).offerTo(exporter);
		Recipes.MakeHangingSign(ACACIA_HANGING_SIGN, Items.STRIPPED_ACACIA_LOG).offerTo(exporter);
		Recipes.MakeShapeless(ACACIA_CHEST_BOAT, Items.ACACIA_BOAT).input(Items.CHEST).offerTo(exporter);
		OfferWoodcuttingRecipe(exporter, Items.ACACIA_SLAB, Items.ACACIA_PLANKS, 2);
		OfferWoodcuttingRecipe(exporter, Items.ACACIA_STAIRS, Items.ACACIA_PLANKS);
		OfferWoodcuttingRecipe(exporter, Items.ACACIA_FENCE, Items.ACACIA_PLANKS);
		Recipes.MakePowderKeg(ACACIA_POWDER_KEG, ACACIA_BARREL).offerTo(exporter);
		Recipes.MakeSlab(ACACIA_LOG_SLAB, Items.ACACIA_LOG).offerTo(exporter);
		OfferWoodcuttingRecipe(exporter, ACACIA_LOG_SLAB, Items.ACACIA_LOG, 2);
		Recipes.MakeSlab(STRIPPED_ACACIA_LOG_SLAB, Items.STRIPPED_ACACIA_LOG).offerTo(exporter);
		OfferWoodcuttingRecipe(exporter, STRIPPED_ACACIA_LOG_SLAB, Items.STRIPPED_ACACIA_LOG, 2);
		Recipes.MakeSlab(ACACIA_WOOD_SLAB, Items.ACACIA_WOOD).offerTo(exporter);
		OfferWoodcuttingRecipe(exporter, ACACIA_WOOD_SLAB, Items.ACACIA_WOOD, 2);
		Recipes.MakeSlab(STRIPPED_ACACIA_WOOD_SLAB, Items.STRIPPED_ACACIA_WOOD).offerTo(exporter);
		OfferWoodcuttingRecipe(exporter, STRIPPED_ACACIA_WOOD_SLAB, Items.STRIPPED_ACACIA_WOOD, 2);
		Recipes.MakeHammer(ACACIA_HAMMER, Items.ACACIA_LOG).offerTo(exporter);
		//</editor-fold>
		//<editor-fold desc="Birch">
		Recipes.MakeBeehive(BIRCH_BEEHIVE, Items.BIRCH_PLANKS).offerTo(exporter);
		Recipes.MakeBookshelf(BIRCH_BOOKSHELF, Items.BIRCH_PLANKS).offerTo(exporter);
		Recipes.MakeChiseledBookshelf(BIRCH_CHISELED_BOOKSHELF, Items.BIRCH_PLANKS).offerTo(exporter);
		Recipes.Make2x2(BIRCH_CRAFTING_TABLE, Items.BIRCH_PLANKS).offerTo(exporter);
		Recipes.MakePlanksLadder(BIRCH_LADDER, Items.BIRCH_PLANKS).offerTo(exporter);
		Recipes.MakeWoodcutter(BIRCH_WOODCUTTER, Items.BIRCH_PLANKS).offerTo(exporter);
		Recipes.MakeBarrel(BIRCH_BARREL, Items.BIRCH_PLANKS, Items.BIRCH_SLAB).offerTo(exporter);
		Recipes.MakeLectern(BIRCH_LECTERN, Items.BIRCH_SLAB).offerTo(exporter);
		Recipes.MakeHangingSign(BIRCH_HANGING_SIGN, Items.STRIPPED_BIRCH_LOG).offerTo(exporter);
		Recipes.MakeShapeless(BIRCH_CHEST_BOAT, Items.BIRCH_BOAT).input(Items.CHEST).offerTo(exporter);
		OfferWoodcuttingRecipe(exporter, Items.BIRCH_SLAB, Items.BIRCH_PLANKS, 2);
		OfferWoodcuttingRecipe(exporter, Items.BIRCH_STAIRS, Items.BIRCH_PLANKS);
		OfferWoodcuttingRecipe(exporter, Items.BIRCH_FENCE, Items.BIRCH_PLANKS);
		Recipes.MakePowderKeg(BIRCH_POWDER_KEG, BIRCH_BARREL).offerTo(exporter);
		Recipes.MakeSlab(BIRCH_LOG_SLAB, Items.BIRCH_LOG).offerTo(exporter);
		OfferWoodcuttingRecipe(exporter, BIRCH_LOG_SLAB, Items.BIRCH_LOG, 2);
		Recipes.MakeSlab(STRIPPED_BIRCH_LOG_SLAB, Items.STRIPPED_BIRCH_LOG).offerTo(exporter);
		OfferWoodcuttingRecipe(exporter, STRIPPED_BIRCH_LOG_SLAB, Items.STRIPPED_BIRCH_LOG, 2);
		Recipes.MakeSlab(BIRCH_WOOD_SLAB, Items.BIRCH_WOOD).offerTo(exporter);
		OfferWoodcuttingRecipe(exporter, BIRCH_WOOD_SLAB, Items.BIRCH_WOOD, 2);
		Recipes.MakeSlab(STRIPPED_BIRCH_WOOD_SLAB, Items.STRIPPED_BIRCH_WOOD).offerTo(exporter);
		OfferWoodcuttingRecipe(exporter, STRIPPED_BIRCH_WOOD_SLAB, Items.STRIPPED_BIRCH_WOOD, 2);
		Recipes.MakeHammer(BIRCH_HAMMER, Items.BIRCH_LOG).offerTo(exporter);
		//</editor-fold>
		//<editor-fold desc="Dark Oak">
		Recipes.MakeBeehive(DARK_OAK_BEEHIVE, Items.DARK_OAK_PLANKS).offerTo(exporter);
		Recipes.MakeBookshelf(DARK_OAK_BOOKSHELF, Items.DARK_OAK_PLANKS).offerTo(exporter);
		Recipes.MakeChiseledBookshelf(DARK_OAK_CHISELED_BOOKSHELF, Items.DARK_OAK_PLANKS).offerTo(exporter);
		Recipes.Make2x2(DARK_OAK_CRAFTING_TABLE, Items.DARK_OAK_PLANKS).offerTo(exporter);
		Recipes.MakePlanksLadder(DARK_OAK_LADDER, Items.DARK_OAK_PLANKS).offerTo(exporter);
		Recipes.MakeWoodcutter(DARK_OAK_WOODCUTTER, Items.DARK_OAK_PLANKS).offerTo(exporter);
		Recipes.MakeBarrel(DARK_OAK_BARREL, Items.DARK_OAK_PLANKS, Items.DARK_OAK_SLAB).offerTo(exporter);
		Recipes.MakeLectern(DARK_OAK_LECTERN, Items.DARK_OAK_SLAB).offerTo(exporter);
		Recipes.MakeHangingSign(DARK_OAK_HANGING_SIGN, Items.STRIPPED_DARK_OAK_LOG).offerTo(exporter);
		Recipes.MakeShapeless(DARK_OAK_CHEST_BOAT, Items.DARK_OAK_BOAT).input(Items.CHEST).offerTo(exporter);
		OfferWoodcuttingRecipe(exporter, Items.DARK_OAK_SLAB, Items.DARK_OAK_PLANKS, 2);
		OfferWoodcuttingRecipe(exporter, Items.DARK_OAK_STAIRS, Items.DARK_OAK_PLANKS);
		OfferWoodcuttingRecipe(exporter, Items.DARK_OAK_FENCE, Items.DARK_OAK_PLANKS);
		Recipes.MakePowderKeg(DARK_OAK_POWDER_KEG, DARK_OAK_BARREL).offerTo(exporter);
		Recipes.MakeSlab(DARK_OAK_LOG_SLAB, Items.DARK_OAK_LOG).offerTo(exporter);
		OfferWoodcuttingRecipe(exporter, DARK_OAK_LOG_SLAB, Items.DARK_OAK_LOG, 2);
		Recipes.MakeSlab(STRIPPED_DARK_OAK_LOG_SLAB, Items.STRIPPED_DARK_OAK_LOG).offerTo(exporter);
		OfferWoodcuttingRecipe(exporter, STRIPPED_DARK_OAK_LOG_SLAB, Items.STRIPPED_DARK_OAK_LOG, 2);
		Recipes.MakeSlab(DARK_OAK_WOOD_SLAB, Items.DARK_OAK_WOOD).offerTo(exporter);
		OfferWoodcuttingRecipe(exporter, DARK_OAK_WOOD_SLAB, Items.DARK_OAK_WOOD, 2);
		Recipes.MakeSlab(STRIPPED_DARK_OAK_WOOD_SLAB, Items.STRIPPED_DARK_OAK_WOOD).offerTo(exporter);
		OfferWoodcuttingRecipe(exporter, STRIPPED_DARK_OAK_WOOD_SLAB, Items.STRIPPED_DARK_OAK_WOOD, 2);
		Recipes.MakeHammer(DARK_OAK_HAMMER, Items.DARK_OAK_LOG).offerTo(exporter);
		//</editor-fold>
		//<editor-fold desc="Jungle">
		Recipes.MakeBeehive(JUNGLE_BEEHIVE, Items.JUNGLE_PLANKS).offerTo(exporter);
		Recipes.MakeBookshelf(JUNGLE_BOOKSHELF, Items.JUNGLE_PLANKS).offerTo(exporter);
		Recipes.MakeChiseledBookshelf(JUNGLE_CHISELED_BOOKSHELF, Items.JUNGLE_PLANKS).offerTo(exporter);
		Recipes.Make2x2(JUNGLE_CRAFTING_TABLE, Items.JUNGLE_PLANKS).offerTo(exporter);
		Recipes.MakePlanksLadder(JUNGLE_LADDER, Items.JUNGLE_PLANKS).offerTo(exporter);
		Recipes.MakeWoodcutter(JUNGLE_WOODCUTTER, Items.JUNGLE_PLANKS).offerTo(exporter);
		Recipes.MakeBarrel(JUNGLE_BARREL, Items.JUNGLE_PLANKS, Items.JUNGLE_SLAB).offerTo(exporter);
		Recipes.MakeLectern(JUNGLE_LECTERN, Items.JUNGLE_SLAB).offerTo(exporter);
		Recipes.MakeHangingSign(JUNGLE_HANGING_SIGN, Items.STRIPPED_JUNGLE_LOG).offerTo(exporter);
		Recipes.MakeShapeless(JUNGLE_CHEST_BOAT, Items.JUNGLE_BOAT).input(Items.CHEST).offerTo(exporter);
		OfferWoodcuttingRecipe(exporter, Items.JUNGLE_SLAB, Items.JUNGLE_PLANKS, 2);
		OfferWoodcuttingRecipe(exporter, Items.JUNGLE_STAIRS, Items.JUNGLE_PLANKS);
		OfferWoodcuttingRecipe(exporter, Items.JUNGLE_FENCE, Items.JUNGLE_PLANKS);
		Recipes.MakePowderKeg(JUNGLE_POWDER_KEG, JUNGLE_BARREL).offerTo(exporter);
		Recipes.MakeSlab(JUNGLE_LOG_SLAB, Items.JUNGLE_LOG).offerTo(exporter);
		OfferWoodcuttingRecipe(exporter, JUNGLE_LOG_SLAB, Items.JUNGLE_LOG, 2);
		Recipes.MakeSlab(STRIPPED_JUNGLE_LOG_SLAB, Items.STRIPPED_JUNGLE_LOG).offerTo(exporter);
		OfferWoodcuttingRecipe(exporter, STRIPPED_JUNGLE_LOG_SLAB, Items.STRIPPED_JUNGLE_LOG, 2);
		Recipes.MakeSlab(JUNGLE_WOOD_SLAB, Items.JUNGLE_WOOD).offerTo(exporter);
		OfferWoodcuttingRecipe(exporter, JUNGLE_WOOD_SLAB, Items.JUNGLE_WOOD, 2);
		Recipes.MakeSlab(STRIPPED_JUNGLE_WOOD_SLAB, Items.STRIPPED_JUNGLE_WOOD).offerTo(exporter);
		OfferWoodcuttingRecipe(exporter, STRIPPED_JUNGLE_WOOD_SLAB, Items.STRIPPED_JUNGLE_WOOD, 2);
		Recipes.MakeHammer(JUNGLE_HAMMER, Items.JUNGLE_LOG).offerTo(exporter);
		//</editor-fold>
		//<editor-fold desc="Oak">
		Recipes.MakeBeehive(Items.BEEHIVE, Items.OAK_PLANKS).offerTo(exporter, ID("beehive_from_oak_planks"));
		Recipes.MakeBookshelf(Items.BOOKSHELF, Items.OAK_PLANKS).offerTo(exporter); //Override
		Recipes.MakeChiseledBookshelf(CHISELED_BOOKSHELF, Items.OAK_PLANKS).offerTo(exporter);
		Recipes.Make2x2(Items.CRAFTING_TABLE, Items.OAK_PLANKS).offerTo(exporter, ID("crafting_table_from_oak_planks"));
		Recipes.MakeWoodcutter(WOODCUTTER, Items.OAK_PLANKS).offerTo(exporter);
		Recipes.MakeBarrel(OAK_BARREL, Items.OAK_PLANKS, Items.OAK_SLAB).offerTo(exporter);
		Recipes.MakeLectern(Items.LECTERN, Items.OAK_SLAB).offerTo(exporter); //Override
		Recipes.MakeHangingSign(OAK_HANGING_SIGN, Items.STRIPPED_OAK_LOG).offerTo(exporter);
		Recipes.MakeShapeless(OAK_CHEST_BOAT, Items.OAK_BOAT).input(Items.CHEST).offerTo(exporter);
		OfferWoodcuttingRecipe(exporter, Items.OAK_SLAB, Items.OAK_PLANKS, 2);
		OfferWoodcuttingRecipe(exporter, Items.OAK_STAIRS, Items.OAK_PLANKS);
		OfferWoodcuttingRecipe(exporter, Items.OAK_FENCE, Items.OAK_PLANKS);
		Recipes.MakePowderKeg(OAK_POWDER_KEG, OAK_BARREL).offerTo(exporter);
		Recipes.MakeSlab(OAK_LOG_SLAB, Items.OAK_LOG).offerTo(exporter);
		OfferWoodcuttingRecipe(exporter, OAK_LOG_SLAB, Items.OAK_LOG, 2);
		Recipes.MakeSlab(STRIPPED_OAK_LOG_SLAB, Items.STRIPPED_OAK_LOG).offerTo(exporter);
		OfferWoodcuttingRecipe(exporter, STRIPPED_OAK_LOG_SLAB, Items.STRIPPED_OAK_LOG, 2);
		Recipes.MakeSlab(OAK_WOOD_SLAB, Items.OAK_WOOD).offerTo(exporter);
		OfferWoodcuttingRecipe(exporter, OAK_WOOD_SLAB, Items.OAK_WOOD, 2);
		Recipes.MakeSlab(STRIPPED_OAK_WOOD_SLAB, Items.STRIPPED_OAK_WOOD).offerTo(exporter);
		OfferWoodcuttingRecipe(exporter, STRIPPED_OAK_WOOD_SLAB, Items.STRIPPED_OAK_WOOD, 2);
		Recipes.MakeHammer(OAK_HAMMER, Items.OAK_LOG).offerTo(exporter);
		//</editor-fold>
		//<editor-fold desc="Spruce">
		Recipes.MakeBeehive(SPRUCE_BEEHIVE, Items.SPRUCE_PLANKS).offerTo(exporter);
		Recipes.MakeBookshelf(SPRUCE_BOOKSHELF, Items.SPRUCE_PLANKS).offerTo(exporter);
		Recipes.MakeChiseledBookshelf(SPRUCE_CHISELED_BOOKSHELF, Items.SPRUCE_PLANKS).offerTo(exporter);
		Recipes.Make2x2(SPRUCE_CRAFTING_TABLE, Items.SPRUCE_PLANKS).offerTo(exporter);
		Recipes.MakePlanksLadder(SPRUCE_LADDER, Items.SPRUCE_PLANKS).offerTo(exporter);
		Recipes.MakeWoodcutter(SPRUCE_WOODCUTTER, Items.SPRUCE_PLANKS).offerTo(exporter);
		Recipes.MakeBarrel(Items.BARREL, Items.SPRUCE_PLANKS, Items.SPRUCE_SLAB).offerTo(exporter); //Override
		Recipes.MakeLectern(SPRUCE_LECTERN, Items.SPRUCE_SLAB).offerTo(exporter);
		Recipes.MakeHangingSign(SPRUCE_HANGING_SIGN, Items.STRIPPED_SPRUCE_LOG).offerTo(exporter);
		Recipes.MakeShapeless(SPRUCE_CHEST_BOAT, Items.SPRUCE_BOAT).input(Items.CHEST).offerTo(exporter);
		OfferWoodcuttingRecipe(exporter, Items.SPRUCE_SLAB, Items.SPRUCE_PLANKS, 2);
		OfferWoodcuttingRecipe(exporter, Items.SPRUCE_STAIRS, Items.SPRUCE_PLANKS);
		OfferWoodcuttingRecipe(exporter, Items.SPRUCE_FENCE, Items.SPRUCE_PLANKS);
		Recipes.MakePowderKeg(SPRUCE_POWDER_KEG, Items.BARREL).offerTo(exporter);
		Recipes.MakeSlab(SPRUCE_LOG_SLAB, Items.SPRUCE_LOG).offerTo(exporter);
		OfferWoodcuttingRecipe(exporter, SPRUCE_LOG_SLAB, Items.SPRUCE_LOG, 2);
		Recipes.MakeSlab(STRIPPED_SPRUCE_LOG_SLAB, Items.STRIPPED_SPRUCE_LOG).offerTo(exporter);
		OfferWoodcuttingRecipe(exporter, STRIPPED_SPRUCE_LOG_SLAB, Items.STRIPPED_SPRUCE_LOG, 2);
		Recipes.MakeSlab(SPRUCE_WOOD_SLAB, Items.SPRUCE_WOOD).offerTo(exporter);
		OfferWoodcuttingRecipe(exporter, SPRUCE_WOOD_SLAB, Items.SPRUCE_WOOD, 2);
		Recipes.MakeSlab(STRIPPED_SPRUCE_WOOD_SLAB, Items.STRIPPED_SPRUCE_WOOD).offerTo(exporter);
		OfferWoodcuttingRecipe(exporter, STRIPPED_SPRUCE_WOOD_SLAB, Items.STRIPPED_SPRUCE_WOOD, 2);
		Recipes.MakeHammer(SPRUCE_HAMMER, Items.SPRUCE_LOG).offerTo(exporter);
		//</editor-fold>
		//<editor-fold desc="Crimson">
		Recipes.MakeBeehive(CRIMSON_BEEHIVE, Items.CRIMSON_PLANKS).offerTo(exporter);
		Recipes.MakeBookshelf(CRIMSON_BOOKSHELF, Items.CRIMSON_PLANKS).offerTo(exporter);
		Recipes.MakeChiseledBookshelf(CRIMSON_CHISELED_BOOKSHELF, Items.CRIMSON_PLANKS).offerTo(exporter);
		Recipes.Make2x2(CRIMSON_CRAFTING_TABLE, Items.CRIMSON_PLANKS).offerTo(exporter);
		Recipes.MakePlanksLadder(CRIMSON_LADDER, Items.CRIMSON_PLANKS).offerTo(exporter);
		Recipes.MakeWoodcutter(CRIMSON_WOODCUTTER, Items.CRIMSON_PLANKS).offerTo(exporter);
		Recipes.MakeBarrel(CRIMSON_BARREL, Items.CRIMSON_PLANKS, Items.CRIMSON_SLAB).offerTo(exporter);
		Recipes.MakeLectern(CRIMSON_LECTERN, Items.CRIMSON_SLAB).offerTo(exporter);
		Recipes.MakeHangingSign(CRIMSON_HANGING_SIGN, Items.STRIPPED_CRIMSON_STEM).offerTo(exporter);
		OfferWoodcuttingRecipe(exporter, Items.CRIMSON_SLAB, Items.CRIMSON_PLANKS, 2);
		OfferWoodcuttingRecipe(exporter, Items.CRIMSON_STAIRS, Items.CRIMSON_PLANKS);
		OfferWoodcuttingRecipe(exporter, Items.CRIMSON_FENCE, Items.CRIMSON_PLANKS);
		Recipes.MakePowderKeg(CRIMSON_POWDER_KEG, CRIMSON_BARREL).offerTo(exporter);
		Recipes.MakeSlab(CRIMSON_STEM_SLAB, Items.CRIMSON_STEM).offerTo(exporter);
		OfferWoodcuttingRecipe(exporter, CRIMSON_STEM_SLAB, Items.CRIMSON_STEM, 2);
		Recipes.MakeSlab(STRIPPED_CRIMSON_STEM_SLAB, Items.STRIPPED_CRIMSON_STEM).offerTo(exporter);
		OfferWoodcuttingRecipe(exporter, STRIPPED_CRIMSON_STEM_SLAB, Items.STRIPPED_CRIMSON_STEM, 2);
		Recipes.MakeSlab(CRIMSON_HYPHAE_SLAB, Items.CRIMSON_HYPHAE).offerTo(exporter);
		OfferWoodcuttingRecipe(exporter, CRIMSON_HYPHAE_SLAB, Items.CRIMSON_HYPHAE, 2);
		Recipes.MakeSlab(STRIPPED_CRIMSON_HYPHAE_SLAB, Items.STRIPPED_CRIMSON_HYPHAE).offerTo(exporter);
		OfferWoodcuttingRecipe(exporter, STRIPPED_CRIMSON_HYPHAE_SLAB, Items.STRIPPED_CRIMSON_HYPHAE, 2);
		Recipes.MakeHammer(CRIMSON_HAMMER, Items.CRIMSON_HYPHAE).offerTo(exporter);
		//</editor-fold>
		//<editor-fold desc="Warped">
		Recipes.MakeBeehive(WARPED_BEEHIVE, Items.WARPED_PLANKS).offerTo(exporter);
		Recipes.MakeBookshelf(WARPED_BOOKSHELF, Items.WARPED_PLANKS).offerTo(exporter);
		Recipes.MakeChiseledBookshelf(WARPED_CHISELED_BOOKSHELF, Items.WARPED_PLANKS).offerTo(exporter);
		Recipes.Make2x2(WARPED_CRAFTING_TABLE, Items.WARPED_PLANKS).offerTo(exporter);
		Recipes.MakePlanksLadder(WARPED_LADDER, Items.WARPED_PLANKS).offerTo(exporter);
		Recipes.MakeWoodcutter(WARPED_WOODCUTTER, Items.WARPED_PLANKS).offerTo(exporter);
		Recipes.MakeBarrel(WARPED_BARREL, Items.WARPED_PLANKS, Items.WARPED_SLAB).offerTo(exporter);
		Recipes.MakeLectern(WARPED_LECTERN, Items.WARPED_SLAB).offerTo(exporter);
		Recipes.MakeHangingSign(WARPED_HANGING_SIGN, Items.STRIPPED_WARPED_STEM).offerTo(exporter);
		OfferWoodcuttingRecipe(exporter, Items.WARPED_SLAB, Items.WARPED_PLANKS, 2);
		OfferWoodcuttingRecipe(exporter, Items.WARPED_STAIRS, Items.WARPED_PLANKS);
		OfferWoodcuttingRecipe(exporter, Items.WARPED_FENCE, Items.WARPED_PLANKS);
		Recipes.MakePowderKeg(WARPED_POWDER_KEG, WARPED_BARREL).offerTo(exporter);
		Recipes.MakeSlab(WARPED_STEM_SLAB, Items.WARPED_STEM).offerTo(exporter);
		OfferWoodcuttingRecipe(exporter, WARPED_STEM_SLAB, Items.WARPED_STEM, 2);
		Recipes.MakeSlab(STRIPPED_WARPED_STEM_SLAB, Items.STRIPPED_WARPED_STEM).offerTo(exporter);
		OfferWoodcuttingRecipe(exporter, STRIPPED_WARPED_STEM_SLAB, Items.STRIPPED_WARPED_STEM, 2);
		Recipes.MakeSlab(WARPED_HYPHAE_SLAB, Items.WARPED_HYPHAE).offerTo(exporter);
		OfferWoodcuttingRecipe(exporter, WARPED_HYPHAE_SLAB, Items.WARPED_HYPHAE, 2);
		Recipes.MakeSlab(STRIPPED_WARPED_HYPHAE_SLAB, Items.STRIPPED_WARPED_HYPHAE).offerTo(exporter);
		OfferWoodcuttingRecipe(exporter, STRIPPED_WARPED_HYPHAE_SLAB, Items.STRIPPED_WARPED_HYPHAE, 2);
		Recipes.MakeHammer(WARPED_HAMMER, Items.WARPED_HYPHAE).offerTo(exporter);
		//</editor-fold>
		//<editor-fold desc="Light Sources">
		Recipes.MakeUnderwaterTorch(UNDERWATER_TORCH, Items.STICK, 4).offerTo(exporter);
		//</editor-fold>
		//<editor-fold desc="Soul & Ender Fire">
		Recipes.MakeShaped(SOUL_CANDLE, Items.STRING).input('S', ItemTags.SOUL_FIRE_BASE_BLOCKS).pattern("#").pattern("S").offerTo(exporter);
		Recipes.MakeShaped(ENDER_CANDLE, Items.STRING).input('S', Items.POPPED_CHORUS_FRUIT).pattern("#").pattern("S").offerTo(exporter);
		Recipes.MakeShaped(NETHERRACK_CANDLE, Items.STRING).input('S', Items.NETHERRACK).pattern("#").pattern("S").offerTo(exporter);
		Recipes.MakeEnderTorch(ENDER_TORCH.asItem(), Items.STICK, 4).offerTo(exporter);
		Recipes.MakeLantern(ENDER_LANTERN, Items.IRON_NUGGET, ModItemTags.ENDER_TORCHES).offerTo(exporter);
		Recipes.MakeEnderCampfire(ENDER_CAMPFIRE, ItemTags.LOGS).offerTo(exporter);
		//</editor-fold>
		//<editor-fold desc="Andesite">
		Recipes.Make2x2(ANDESITE_BRICKS, Items.ANDESITE).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, ANDESITE_BRICKS, Items.ANDESITE);
		Recipes.MakeStairs(ANDESITE_BRICK_STAIRS, ANDESITE_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, ANDESITE_BRICK_STAIRS, ANDESITE_BRICKS);
		Recipes.MakeSlab(ANDESITE_BRICK_SLAB, ANDESITE_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, ANDESITE_BRICK_SLAB, ANDESITE_BRICKS, 2);
		Recipes.MakeWall(ANDESITE_BRICK_WALL, ANDESITE_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, ANDESITE_BRICK_WALL, ANDESITE_BRICKS);
		OfferStonecuttingRecipe(exporter, CHISELED_ANDESITE_BRICKS, Items.ANDESITE);
		OfferStonecuttingRecipe(exporter, CHISELED_ANDESITE_BRICKS, ANDESITE_BRICKS);
		//Tiles
		Recipes.Make2x2(ANDESITE_TILES, ANDESITE_BRICKS, 4).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, ANDESITE_TILES, Items.ANDESITE);
		OfferStonecuttingRecipe(exporter, ANDESITE_TILES, ANDESITE_BRICKS);
		Recipes.MakeStairs(ANDESITE_TILE_STAIRS, ANDESITE_TILES).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, ANDESITE_TILE_STAIRS, Items.ANDESITE);
		OfferStonecuttingRecipe(exporter, ANDESITE_TILE_STAIRS, ANDESITE_BRICKS);
		OfferStonecuttingRecipe(exporter, ANDESITE_TILE_STAIRS, ANDESITE_TILES);
		Recipes.MakeSlab(ANDESITE_TILE_SLAB, ANDESITE_TILES).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, ANDESITE_TILE_SLAB, Items.ANDESITE, 2);
		OfferStonecuttingRecipe(exporter, ANDESITE_TILE_SLAB, ANDESITE_BRICKS, 2);
		OfferStonecuttingRecipe(exporter, ANDESITE_TILE_SLAB, ANDESITE_TILES, 2);
		Recipes.MakeWall(ANDESITE_TILE_WALL, ANDESITE_TILES).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, ANDESITE_TILE_WALL, Items.ANDESITE);
		OfferStonecuttingRecipe(exporter, ANDESITE_TILE_WALL, ANDESITE_BRICKS);
		OfferStonecuttingRecipe(exporter, ANDESITE_TILE_WALL, ANDESITE_TILES);
		//Polished
		Recipes.MakeWall(POLISHED_ANDESITE_WALL, Items.POLISHED_ANDESITE).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, POLISHED_ANDESITE_WALL, Items.POLISHED_ANDESITE);
		//Cut Polished
		Recipes.Make2x2(CUT_POLISHED_ANDESITE, Items.POLISHED_ANDESITE, 4).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, CUT_POLISHED_ANDESITE, Items.POLISHED_ANDESITE);
		Recipes.MakeStairs(CUT_POLISHED_ANDESITE_STAIRS, CUT_POLISHED_ANDESITE).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, CUT_POLISHED_ANDESITE_STAIRS, Items.POLISHED_ANDESITE);
		OfferStonecuttingRecipe(exporter, CUT_POLISHED_ANDESITE_STAIRS, CUT_POLISHED_ANDESITE);
		Recipes.MakeSlab(CUT_POLISHED_ANDESITE_SLAB, CUT_POLISHED_ANDESITE).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, CUT_POLISHED_ANDESITE_SLAB, Items.POLISHED_ANDESITE, 2);
		OfferStonecuttingRecipe(exporter, CUT_POLISHED_ANDESITE_SLAB, CUT_POLISHED_ANDESITE, 2);
		Recipes.MakeWall(CUT_POLISHED_ANDESITE_WALL, CUT_POLISHED_ANDESITE).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, CUT_POLISHED_ANDESITE_WALL, Items.POLISHED_ANDESITE);
		OfferStonecuttingRecipe(exporter, CUT_POLISHED_ANDESITE_WALL, CUT_POLISHED_ANDESITE);
		//</editor-fold>
		//<editor-fold desc="Deepslate">
		//Mossy Cobbled
		Recipes.MakeShapeless(MOSSY_COBBLED_DEEPSLATE, Items.COBBLED_DEEPSLATE).input(Ingredient.ofItems(Items.VINE, Items.MOSS_BLOCK)).offerTo(exporter);
		Recipes.MakeSlab(MOSSY_COBBLED_DEEPSLATE_SLAB, MOSSY_COBBLED_DEEPSLATE).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, MOSSY_COBBLED_DEEPSLATE_SLAB, MOSSY_COBBLED_DEEPSLATE, 2);
		Recipes.MakeStairs(MOSSY_COBBLED_DEEPSLATE_STAIRS, MOSSY_COBBLED_DEEPSLATE).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, MOSSY_COBBLED_DEEPSLATE_STAIRS, MOSSY_COBBLED_DEEPSLATE);
		Recipes.MakeWall(MOSSY_COBBLED_DEEPSLATE_WALL, MOSSY_COBBLED_DEEPSLATE).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, MOSSY_COBBLED_DEEPSLATE_WALL, MOSSY_COBBLED_DEEPSLATE);
		//Mossy Bricks
		Recipes.MakeShapeless(MOSSY_DEEPSLATE_BRICKS, Items.DEEPSLATE_BRICKS).input(Ingredient.ofItems(Items.VINE, Items.MOSS_BLOCK)).offerTo(exporter);
		Recipes.MakeSlab(MOSSY_DEEPSLATE_BRICK_SLAB, MOSSY_DEEPSLATE_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, MOSSY_DEEPSLATE_BRICK_SLAB, MOSSY_DEEPSLATE_BRICKS, 2);
		Recipes.MakeStairs(MOSSY_DEEPSLATE_BRICK_STAIRS, MOSSY_DEEPSLATE_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, MOSSY_DEEPSLATE_BRICK_STAIRS, MOSSY_DEEPSLATE_BRICKS);
		Recipes.MakeWall(MOSSY_DEEPSLATE_BRICK_WALL, MOSSY_DEEPSLATE_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, MOSSY_DEEPSLATE_BRICK_WALL, MOSSY_DEEPSLATE_BRICKS);
		//</editor-fold>
		//<editor-fold desc="Shale">
		OfferSmeltingRecipe(exporter, SHALE, COBBLED_SHALE);
		//Cobbled
		Recipes.MakeSlab(COBBLED_SHALE_SLAB, COBBLED_SHALE).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, COBBLED_SHALE_SLAB, COBBLED_SHALE, 2);
		Recipes.MakeStairs(COBBLED_SHALE_STAIRS, COBBLED_SHALE).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, COBBLED_SHALE_STAIRS, COBBLED_SHALE);
		Recipes.MakeWall(COBBLED_SHALE_WALL, COBBLED_SHALE).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, COBBLED_SHALE_WALL, COBBLED_SHALE);
		//Bricks
		Recipes.Make2x2(SHALE_BRICKS, SHALE, 4).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, SHALE_BRICKS, SHALE);
		Recipes.MakeSlab(SHALE_BRICK_SLAB, SHALE_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, SHALE_BRICK_SLAB, SHALE, 2);
		OfferStonecuttingRecipe(exporter, SHALE_BRICK_SLAB, SHALE_BRICKS, 2);
		Recipes.MakeStairs(SHALE_BRICK_STAIRS, SHALE_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, SHALE_BRICK_STAIRS, SHALE);
		OfferStonecuttingRecipe(exporter, SHALE_BRICK_STAIRS, SHALE_BRICKS);
		Recipes.MakeWall(SHALE_BRICK_WALL, SHALE_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, SHALE_BRICK_WALL, SHALE);
		OfferStonecuttingRecipe(exporter, SHALE_BRICK_WALL, SHALE_BRICKS);
		//Tiles
		Recipes.Make2x2(SHALE_TILES, SHALE_BRICKS, 4).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, SHALE_TILES, SHALE);
		OfferStonecuttingRecipe(exporter, SHALE_TILES, SHALE_BRICKS);
		Recipes.MakeStairs(SHALE_TILE_STAIRS, SHALE_TILES).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, SHALE_TILE_STAIRS, SHALE);
		OfferStonecuttingRecipe(exporter, SHALE_TILE_STAIRS, SHALE_BRICKS);
		OfferStonecuttingRecipe(exporter, SHALE_TILE_STAIRS, SHALE_TILES);
		Recipes.MakeSlab(SHALE_TILE_SLAB, SHALE_TILES).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, SHALE_TILE_SLAB, SHALE, 2);
		OfferStonecuttingRecipe(exporter, SHALE_TILE_SLAB, SHALE_BRICKS, 2);
		OfferStonecuttingRecipe(exporter, SHALE_TILE_SLAB, SHALE_TILES, 2);
		Recipes.MakeWall(SHALE_TILE_WALL, SHALE_TILES).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, SHALE_TILE_WALL, SHALE);
		OfferStonecuttingRecipe(exporter, SHALE_TILE_WALL, SHALE_BRICKS);
		OfferStonecuttingRecipe(exporter, SHALE_TILE_WALL, SHALE_TILES);
		//</editor-fold>
		//<editor-fold desc="End Stone">
		Recipes.MakeSlab(END_STONE_SLAB, Items.END_STONE).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, END_STONE_SLAB, Items.END_STONE, 2);
		Recipes.Make1x2(END_STONE_PILLAR, Items.END_STONE_BRICK_SLAB).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, END_STONE_PILLAR, Items.END_STONE);
		OfferStonecuttingRecipe(exporter, END_STONE_PILLAR, Items.END_STONE_BRICKS);
		Recipes.Make2x2(END_STONE_TILES, Items.END_STONE_BRICKS, 4).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, END_STONE_TILES, Items.END_STONE);
		OfferStonecuttingRecipe(exporter, END_STONE_TILES, Items.END_STONE_BRICKS);
		Recipes.MakeStairs(END_STONE_TILE_STAIRS, END_STONE_TILES).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, END_STONE_TILE_STAIRS, Items.END_STONE);
		OfferStonecuttingRecipe(exporter, END_STONE_TILE_STAIRS, Items.END_STONE_BRICKS);
		OfferStonecuttingRecipe(exporter, END_STONE_TILE_STAIRS, END_STONE_TILES);
		Recipes.MakeSlab(END_STONE_TILE_SLAB, END_STONE_TILES).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, END_STONE_TILE_SLAB, Items.END_STONE, 2);
		OfferStonecuttingRecipe(exporter, END_STONE_TILE_SLAB, Items.END_STONE_BRICKS, 2);
		OfferStonecuttingRecipe(exporter, END_STONE_TILE_SLAB, END_STONE_TILES, 2);
		Recipes.MakeWall(END_STONE_TILE_WALL, END_STONE_TILES).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, END_STONE_TILE_WALL, Items.END_STONE);
		OfferStonecuttingRecipe(exporter, END_STONE_TILE_WALL, Items.END_STONE_BRICKS);
		OfferStonecuttingRecipe(exporter, END_STONE_TILE_WALL, END_STONE_TILES);
		//</editor-fold>
		//<editor-fold desc="End Shale">
		OfferSmeltingRecipe(exporter, END_SHALE, COBBLED_END_SHALE);
		//Cobbled
		Recipes.MakeSlab(COBBLED_END_SHALE_SLAB, COBBLED_END_SHALE).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, COBBLED_END_SHALE_SLAB, COBBLED_END_SHALE, 2);
		Recipes.MakeStairs(COBBLED_END_SHALE_STAIRS, COBBLED_END_SHALE).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, COBBLED_END_SHALE_STAIRS, COBBLED_END_SHALE);
		Recipes.MakeWall(COBBLED_END_SHALE_WALL, COBBLED_END_SHALE).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, COBBLED_END_SHALE_WALL, COBBLED_END_SHALE);
		//Bricks
		Recipes.Make2x2(END_SHALE_BRICKS, END_SHALE, 4).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, END_SHALE_BRICKS, END_SHALE);
		Recipes.MakeSlab(END_SHALE_BRICK_SLAB, END_SHALE_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, END_SHALE_BRICK_SLAB, END_SHALE, 2);
		OfferStonecuttingRecipe(exporter, END_SHALE_BRICK_SLAB, END_SHALE_BRICKS, 2);
		Recipes.MakeStairs(END_SHALE_BRICK_STAIRS, END_SHALE_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, END_SHALE_BRICK_STAIRS, END_SHALE);
		OfferStonecuttingRecipe(exporter, END_SHALE_BRICK_STAIRS, END_SHALE_BRICKS);
		Recipes.MakeWall(END_SHALE_BRICK_WALL, END_SHALE_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, END_SHALE_BRICK_WALL, END_SHALE);
		OfferStonecuttingRecipe(exporter, END_SHALE_BRICK_WALL, END_SHALE_BRICKS);
		//Tiles
		Recipes.Make2x2(END_SHALE_TILES, END_SHALE_BRICKS, 4).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, END_SHALE_TILES, END_SHALE);
		OfferStonecuttingRecipe(exporter, END_SHALE_TILES, END_SHALE_BRICKS);
		Recipes.MakeStairs(END_SHALE_TILE_STAIRS, END_SHALE_TILES).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, END_SHALE_TILE_STAIRS, END_SHALE);
		OfferStonecuttingRecipe(exporter, END_SHALE_TILE_STAIRS, END_SHALE_BRICKS);
		OfferStonecuttingRecipe(exporter, END_SHALE_TILE_STAIRS, END_SHALE_TILES);
		Recipes.MakeSlab(END_SHALE_TILE_SLAB, END_SHALE_TILES).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, END_SHALE_TILE_SLAB, END_SHALE, 2);
		OfferStonecuttingRecipe(exporter, END_SHALE_TILE_SLAB, END_SHALE_BRICKS, 2);
		OfferStonecuttingRecipe(exporter, END_SHALE_TILE_SLAB, END_SHALE_TILES, 2);
		Recipes.MakeWall(END_SHALE_TILE_WALL, END_SHALE_TILES).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, END_SHALE_TILE_WALL, END_SHALE);
		OfferStonecuttingRecipe(exporter, END_SHALE_TILE_WALL, END_SHALE_BRICKS);
		OfferStonecuttingRecipe(exporter, END_SHALE_TILE_WALL, END_SHALE_TILES);
		//</editor-fold>
		//<editor-fold desc="Diorite">
		Recipes.Make2x2(DIORITE_BRICKS, Items.DIORITE).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, DIORITE_BRICKS, Items.DIORITE);
		Recipes.MakeStairs(DIORITE_BRICK_STAIRS, DIORITE_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, DIORITE_BRICK_STAIRS, DIORITE_BRICKS);
		Recipes.MakeSlab(DIORITE_BRICK_SLAB, DIORITE_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, DIORITE_BRICK_SLAB, DIORITE_BRICKS, 2);
		Recipes.MakeWall(DIORITE_BRICK_WALL, DIORITE_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, DIORITE_BRICK_WALL, DIORITE_BRICKS);
		OfferStonecuttingRecipe(exporter, CHISELED_DIORITE_BRICKS, Items.DIORITE);
		OfferStonecuttingRecipe(exporter, CHISELED_DIORITE_BRICKS, DIORITE_BRICKS);
		//Tiles
		Recipes.Make2x2(DIORITE_TILES, DIORITE_BRICKS, 4).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, DIORITE_TILES, Items.DIORITE);
		OfferStonecuttingRecipe(exporter, DIORITE_TILES, DIORITE_BRICKS);
		Recipes.MakeStairs(DIORITE_TILE_STAIRS, DIORITE_TILES).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, DIORITE_TILE_STAIRS, Items.DIORITE);
		OfferStonecuttingRecipe(exporter, DIORITE_TILE_STAIRS, DIORITE_BRICKS);
		OfferStonecuttingRecipe(exporter, DIORITE_TILE_STAIRS, DIORITE_TILES);
		Recipes.MakeSlab(DIORITE_TILE_SLAB, DIORITE_TILES).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, DIORITE_TILE_SLAB, Items.DIORITE, 2);
		OfferStonecuttingRecipe(exporter, DIORITE_TILE_SLAB, DIORITE_BRICKS, 2);
		OfferStonecuttingRecipe(exporter, DIORITE_TILE_SLAB, DIORITE_TILES, 2);
		Recipes.MakeWall(DIORITE_TILE_WALL, DIORITE_TILES).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, DIORITE_TILE_WALL, Items.DIORITE);
		OfferStonecuttingRecipe(exporter, DIORITE_TILE_WALL, DIORITE_BRICKS);
		OfferStonecuttingRecipe(exporter, DIORITE_TILE_WALL, DIORITE_TILES);
		//Polished
		Recipes.MakeWall(POLISHED_DIORITE_WALL, Items.POLISHED_DIORITE).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, POLISHED_DIORITE_WALL, Items.POLISHED_DIORITE);
		//Cut Polished
		Recipes.Make2x2(CUT_POLISHED_DIORITE, Items.POLISHED_DIORITE, 4).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, CUT_POLISHED_DIORITE, Items.POLISHED_DIORITE);
		Recipes.MakeStairs(CUT_POLISHED_DIORITE_STAIRS, CUT_POLISHED_DIORITE).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, CUT_POLISHED_DIORITE_STAIRS, Items.POLISHED_DIORITE);
		OfferStonecuttingRecipe(exporter, CUT_POLISHED_DIORITE_STAIRS, CUT_POLISHED_DIORITE);
		Recipes.MakeSlab(CUT_POLISHED_DIORITE_SLAB, CUT_POLISHED_DIORITE).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, CUT_POLISHED_DIORITE_SLAB, Items.POLISHED_DIORITE, 2);
		OfferStonecuttingRecipe(exporter, CUT_POLISHED_DIORITE_SLAB, CUT_POLISHED_DIORITE, 2);
		Recipes.MakeWall(CUT_POLISHED_DIORITE_WALL, CUT_POLISHED_DIORITE).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, CUT_POLISHED_DIORITE_WALL, Items.POLISHED_DIORITE);
		OfferStonecuttingRecipe(exporter, CUT_POLISHED_DIORITE_WALL, CUT_POLISHED_DIORITE);
		//</editor-fold>
		//<editor-fold desc="Granite">
		Recipes.Make2x2(GRANITE_BRICKS, Items.GRANITE).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, GRANITE_BRICKS, Items.GRANITE);
		Recipes.MakeStairs(GRANITE_BRICK_STAIRS, GRANITE_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, GRANITE_BRICK_STAIRS, GRANITE_BRICKS);
		Recipes.MakeSlab(GRANITE_BRICK_SLAB, GRANITE_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, GRANITE_BRICK_SLAB, GRANITE_BRICKS, 2);
		Recipes.MakeWall(GRANITE_BRICK_WALL, GRANITE_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, GRANITE_BRICK_WALL, GRANITE_BRICKS);
		OfferStonecuttingRecipe(exporter, CHISELED_GRANITE_BRICKS, Items.GRANITE);
		OfferStonecuttingRecipe(exporter, CHISELED_GRANITE_BRICKS, GRANITE_BRICKS);
		//Tiles
		Recipes.Make2x2(GRANITE_TILES, GRANITE_BRICKS, 4).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, GRANITE_TILES, Items.GRANITE);
		OfferStonecuttingRecipe(exporter, GRANITE_TILES, GRANITE_BRICKS);
		Recipes.MakeStairs(GRANITE_TILE_STAIRS, GRANITE_TILES).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, GRANITE_TILE_STAIRS, Items.GRANITE);
		OfferStonecuttingRecipe(exporter, GRANITE_TILE_STAIRS, GRANITE_BRICKS);
		OfferStonecuttingRecipe(exporter, GRANITE_TILE_STAIRS, GRANITE_TILES);
		Recipes.MakeSlab(GRANITE_TILE_SLAB, GRANITE_TILES).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, GRANITE_TILE_SLAB, Items.GRANITE, 2);
		OfferStonecuttingRecipe(exporter, GRANITE_TILE_SLAB, GRANITE_BRICKS, 2);
		OfferStonecuttingRecipe(exporter, GRANITE_TILE_SLAB, GRANITE_TILES, 2);
		Recipes.MakeWall(GRANITE_TILE_WALL, GRANITE_TILES).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, GRANITE_TILE_WALL, Items.GRANITE);
		OfferStonecuttingRecipe(exporter, GRANITE_TILE_WALL, GRANITE_BRICKS);
		OfferStonecuttingRecipe(exporter, GRANITE_TILE_WALL, GRANITE_TILES);
		//Polished
		Recipes.MakeWall(POLISHED_GRANITE_WALL, Items.POLISHED_GRANITE).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, POLISHED_GRANITE_WALL, Items.POLISHED_GRANITE);
		//Cut Polished
		Recipes.Make2x2(CUT_POLISHED_GRANITE, Items.POLISHED_GRANITE, 4).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, CUT_POLISHED_GRANITE, Items.POLISHED_GRANITE);
		Recipes.MakeStairs(CUT_POLISHED_GRANITE_STAIRS, CUT_POLISHED_GRANITE).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, CUT_POLISHED_GRANITE_STAIRS, Items.POLISHED_GRANITE);
		OfferStonecuttingRecipe(exporter, CUT_POLISHED_GRANITE_STAIRS, CUT_POLISHED_GRANITE);
		Recipes.MakeSlab(CUT_POLISHED_GRANITE_SLAB, CUT_POLISHED_GRANITE).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, CUT_POLISHED_GRANITE_SLAB, Items.POLISHED_GRANITE, 2);
		OfferStonecuttingRecipe(exporter, CUT_POLISHED_GRANITE_SLAB, CUT_POLISHED_GRANITE, 2);
		Recipes.MakeWall(CUT_POLISHED_GRANITE_WALL, CUT_POLISHED_GRANITE).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, CUT_POLISHED_GRANITE_WALL, Items.POLISHED_GRANITE);
		OfferStonecuttingRecipe(exporter, CUT_POLISHED_GRANITE_WALL, CUT_POLISHED_GRANITE);
		//Chiseled
		OfferStonecuttingRecipe(exporter, CHISELED_GRANITE, Items.GRANITE);
		OfferStonecuttingRecipe(exporter, CHISELED_GRANITE, Items.POLISHED_GRANITE);
		Recipes.MakeSlab(CHISELED_GRANITE_SLAB, CHISELED_GRANITE).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, CHISELED_GRANITE_SLAB, Items.GRANITE, 2);
		OfferStonecuttingRecipe(exporter, CHISELED_GRANITE_SLAB, Items.POLISHED_GRANITE, 2);
		//</editor-fold>
		//<editor-fold desc="Sandstone">
		Recipes.MakeWall(SMOOTH_SANDSTONE_WALL, Items.SMOOTH_SANDSTONE).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, SMOOTH_SANDSTONE_WALL, Items.SMOOTH_SANDSTONE);
		Recipes.MakeWall(SMOOTH_RED_SANDSTONE_WALL, Items.SMOOTH_RED_SANDSTONE).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, SMOOTH_RED_SANDSTONE_WALL, Items.SMOOTH_RED_SANDSTONE);
		//</editor-fold>
		//<editor-fold desc="Prismarine">
		OfferSmithingRecipe(exporter, PRISMARINE_ROD, COPPER_ROD, Items.PRISMARINE_CRYSTALS, ID("prismarine_rod_from_smithing_with_prismarine_crystals"));
		OfferSmithingRecipe(exporter, PRISMARINE_ROD, COPPER_ROD, Items.PRISMARINE_SHARD, ID("prismarine_rod_from_smithing_with_prismarine_shard"));
		Recipes.MakeTorch(PRISMARINE_TORCH, PRISMARINE_ROD, 8).offerTo(exporter);
		Recipes.MakeSoulTorch(PRISMARINE_SOUL_TORCH, PRISMARINE_ROD, 8).offerTo(exporter);
		Recipes.MakeEnderTorch(PRISMARINE_ENDER_TORCH, PRISMARINE_ROD, 8).offerTo(exporter);
		Recipes.MakeUnderwaterTorch(UNDERWATER_PRISMARINE_TORCH, PRISMARINE_ROD, 8).offerTo(exporter);
		//Chiseled Bricks
		Recipes.Make1x2(CHISELED_PRISMARINE_BRICKS, Items.PRISMARINE_BRICK_SLAB).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, CHISELED_PRISMARINE_BRICKS, Items.PRISMARINE);
		OfferStonecuttingRecipe(exporter, CHISELED_PRISMARINE_BRICKS, Items.PRISMARINE_BRICKS);
		OfferStonecuttingRecipe(exporter, SMOOTH_CHISELED_PRISMARINE_BRICKS, CHISELED_PRISMARINE_BRICKS);
		//Chiseled
		OfferStonecuttingRecipe(exporter, CHISELED_PRISMARINE, Items.PRISMARINE);
		Recipes.MakeStairs(CHISELED_PRISMARINE_STAIRS, CHISELED_PRISMARINE).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, CHISELED_PRISMARINE_STAIRS, Items.PRISMARINE_STAIRS);
		OfferStonecuttingRecipe(exporter, CHISELED_PRISMARINE_STAIRS, CHISELED_PRISMARINE);
		OfferStonecuttingRecipe(exporter, CHISELED_PRISMARINE_SLAB, Items.PRISMARINE_SLAB);
		OfferStonecuttingRecipe(exporter, CHISELED_PRISMARINE_SLAB, CHISELED_PRISMARINE, 2);
		//Cut Bricks
		OfferStonecuttingRecipe(exporter, CUT_PRISMARINE_BRICKS, Items.PRISMARINE_BRICKS);
		Recipes.MakeStairs(CUT_PRISMARINE_BRICK_STAIRS, CUT_PRISMARINE_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, CUT_PRISMARINE_BRICK_STAIRS, Items.PRISMARINE_BRICK_STAIRS);
		OfferStonecuttingRecipe(exporter, CUT_PRISMARINE_BRICK_STAIRS, CUT_PRISMARINE_BRICKS);
		OfferStonecuttingRecipe(exporter, CUT_PRISMARINE_BRICK_SLAB, Items.PRISMARINE_BRICK_SLAB);
		OfferStonecuttingRecipe(exporter, CUT_PRISMARINE_BRICK_SLAB, CUT_PRISMARINE_BRICKS, 2);
		//Tiles
		Recipes.Make2x2(PRISMARINE_TILES, Items.PRISMARINE_BRICKS, 4).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, PRISMARINE_TILES, Items.PRISMARINE);
		OfferStonecuttingRecipe(exporter, PRISMARINE_TILES, Items.PRISMARINE_BRICKS);
		OfferStonecuttingRecipe(exporter, PRISMARINE_TILES, CHISELED_PRISMARINE_BRICKS);
		Recipes.MakeStairs(PRISMARINE_TILE_STAIRS, PRISMARINE_TILES).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, PRISMARINE_TILE_STAIRS, Items.PRISMARINE);
		OfferStonecuttingRecipe(exporter, PRISMARINE_TILE_STAIRS, Items.PRISMARINE_BRICKS);
		OfferStonecuttingRecipe(exporter, PRISMARINE_TILE_STAIRS, CHISELED_PRISMARINE_BRICKS);
		OfferStonecuttingRecipe(exporter, PRISMARINE_TILE_STAIRS, PRISMARINE_TILES);
		Recipes.MakeSlab(PRISMARINE_TILE_SLAB, PRISMARINE_TILES).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, PRISMARINE_TILE_SLAB, Items.PRISMARINE, 2);
		OfferStonecuttingRecipe(exporter, PRISMARINE_TILE_SLAB, Items.PRISMARINE_BRICKS, 2);
		OfferStonecuttingRecipe(exporter, PRISMARINE_TILE_SLAB, CHISELED_PRISMARINE_BRICKS, 2);
		OfferStonecuttingRecipe(exporter, PRISMARINE_TILE_SLAB, PRISMARINE_TILES, 2);
		Recipes.MakeWall(PRISMARINE_TILE_WALL, PRISMARINE_TILES).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, PRISMARINE_TILE_WALL, Items.PRISMARINE);
		OfferStonecuttingRecipe(exporter, PRISMARINE_TILE_WALL, Items.PRISMARINE_BRICKS);
		OfferStonecuttingRecipe(exporter, PRISMARINE_TILE_WALL, CHISELED_PRISMARINE_BRICKS);
		OfferStonecuttingRecipe(exporter, PRISMARINE_TILE_WALL, PRISMARINE_TILES);
		//Dark Prismarine
		Recipes.MakeWall(DARK_PRISMARINE_WALL, Items.DARK_PRISMARINE).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, DARK_PRISMARINE_WALL, Items.DARK_PRISMARINE);
		//</editor-fold>
		//<editor-fold desc="Blaze">
		Recipes.Make3x3(BLAZE_POWDER_BLOCK, Items.BLAZE_POWDER).offerTo(exporter);
		Recipes.MakeShapeless(Items.BLAZE_POWDER, BLAZE_POWDER_BLOCK, 9).offerTo(exporter, ID("blaze_powder_from_blaze_powder_block"));
		Recipes.MakeTorch(BLAZE_TORCH, Items.BLAZE_ROD, 4).offerTo(exporter);
		Recipes.MakeSoulTorch(BLAZE_SOUL_TORCH, Items.BLAZE_ROD, 4).offerTo(exporter);
		Recipes.MakeEnderTorch(BLAZE_ENDER_TORCH, Items.BLAZE_ROD, 4).offerTo(exporter);
		Recipes.MakeUnderwaterTorch(UNDERWATER_BLAZE_TORCH, Items.BLAZE_ROD, 4).offerTo(exporter);
		//</editor-fold>
		//<editor-fold desc="Purpur">
		Recipes.MakeWall(PURPUR_WALL, Items.PURPUR_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, PURPUR_WALL, Items.PURPUR_BLOCK);
		OfferSmeltingRecipe(exporter, CRACKED_PURPUR_BLOCK, Items.PURPUR_BLOCK);
		OfferStonecuttingRecipe(exporter, CRACKED_PURPUR_BLOCK, Items.PURPUR_BLOCK);
		//Chiseled
		Recipes.Make2x2(CHISELED_PURPUR, Items.PURPUR_PILLAR, 4).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, CHISELED_PURPUR, Items.PURPUR_BLOCK);
		OfferStonecuttingRecipe(exporter, CHISELED_PURPUR, Items.PURPUR_PILLAR);
		Recipes.MakeStairs(CHISELED_PURPUR_STAIRS, CHISELED_PURPUR).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, CHISELED_PURPUR_STAIRS, CHISELED_PURPUR);
		OfferStonecuttingRecipe(exporter, CHISELED_PURPUR_STAIRS, Items.PURPUR_BLOCK);
		OfferStonecuttingRecipe(exporter, CHISELED_PURPUR_STAIRS, Items.PURPUR_PILLAR);
		Recipes.MakeSlab(CHISELED_PURPUR_SLAB, CHISELED_PURPUR).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, CHISELED_PURPUR_SLAB, CHISELED_PURPUR, 2);
		OfferStonecuttingRecipe(exporter, CHISELED_PURPUR_SLAB, Items.PURPUR_BLOCK, 2);
		OfferStonecuttingRecipe(exporter, CHISELED_PURPUR_SLAB, Items.PURPUR_PILLAR, 2);
		Recipes.MakeWall(CHISELED_PURPUR_WALL, CHISELED_PURPUR).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, CHISELED_PURPUR_WALL, CHISELED_PURPUR);
		OfferStonecuttingRecipe(exporter, CHISELED_PURPUR_WALL, Items.PURPUR_BLOCK);
		OfferStonecuttingRecipe(exporter, CHISELED_PURPUR_WALL, Items.PURPUR_PILLAR);
		//Mosaic
		Recipes.MakeShaped(PURPUR_MOSAIC, CHISELED_PURPUR).input('B', Items.PURPUR_BLOCK).pattern("#B").pattern("B#").offerTo(exporter);
		Recipes.MakeShaped(PURPUR_MOSAIC, CHISELED_PURPUR_SLAB).input('B', Items.PURPUR_SLAB).pattern("#").pattern("B").offerTo(exporter, ID("purpur_mosaic_from_slabs0"));
		Recipes.MakeShaped(PURPUR_MOSAIC, CHISELED_PURPUR_SLAB).input('B', Items.PURPUR_SLAB).pattern("B").pattern("#").offerTo(exporter, ID("purpur_mosaic_from_slabs1"));
		Recipes.MakeStairs(PURPUR_MOSAIC_STAIRS, PURPUR_MOSAIC).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, PURPUR_MOSAIC_STAIRS, PURPUR_MOSAIC);
		Recipes.MakeSlab(PURPUR_MOSAIC_SLAB, PURPUR_MOSAIC).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, PURPUR_MOSAIC_SLAB, PURPUR_MOSAIC, 2);
		Recipes.MakeWall(PURPUR_MOSAIC_WALL, PURPUR_MOSAIC).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, PURPUR_MOSAIC_WALL, PURPUR_MOSAIC);
		//Bricks
		Recipes.Make2x2(PURPUR_BRICKS, Items.PURPUR_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, PURPUR_BRICKS, Items.PURPUR_BLOCK);
		Recipes.MakeStairs(PURPUR_BRICK_STAIRS, PURPUR_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, PURPUR_BRICK_STAIRS, PURPUR_BRICKS);
		Recipes.MakeSlab(PURPUR_BRICK_SLAB, PURPUR_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, PURPUR_BRICK_SLAB, PURPUR_BRICKS, 2);
		Recipes.MakeWall(PURPUR_BRICK_WALL, PURPUR_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, PURPUR_BRICK_WALL, PURPUR_BRICKS);
		//Chiseled Bricks
		Recipes.Make1x2(CHISELED_PURPUR_BRICKS, PURPUR_BRICK_SLAB).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, CHISELED_PURPUR_BRICKS, Items.PURPUR_BLOCK);
		OfferStonecuttingRecipe(exporter, CHISELED_PURPUR_BRICKS, PURPUR_BRICKS);
		OfferStonecuttingRecipe(exporter, SMOOTH_CHISELED_PURPUR_BRICKS, CHISELED_PURPUR_BRICKS);
		//Tiles
		Recipes.Make2x2(PURPUR_TILES, PURPUR_BRICKS, 4).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, PURPUR_TILES, Items.PURPUR_BLOCK);
		OfferStonecuttingRecipe(exporter, PURPUR_TILES, PURPUR_BRICKS);
		Recipes.MakeStairs(PURPUR_TILE_STAIRS, PURPUR_TILES).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, PURPUR_TILE_STAIRS, Items.PURPUR_BLOCK);
		OfferStonecuttingRecipe(exporter, PURPUR_TILE_STAIRS, PURPUR_BRICKS);
		OfferStonecuttingRecipe(exporter, PURPUR_TILE_STAIRS, PURPUR_TILES);
		Recipes.MakeSlab(PURPUR_TILE_SLAB, PURPUR_TILES).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, PURPUR_TILE_SLAB, Items.PURPUR_BLOCK, 2);
		OfferStonecuttingRecipe(exporter, PURPUR_TILE_SLAB, PURPUR_BRICKS, 2);
		OfferStonecuttingRecipe(exporter, PURPUR_TILE_SLAB, PURPUR_TILES, 2);
		Recipes.MakeWall(PURPUR_TILE_WALL, PURPUR_TILES).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, PURPUR_TILE_WALL, Items.PURPUR_BLOCK);
		OfferStonecuttingRecipe(exporter, PURPUR_TILE_WALL, PURPUR_BRICKS);
		OfferStonecuttingRecipe(exporter, PURPUR_TILE_WALL, PURPUR_TILES);
		//Smooth
		OfferSmeltingRecipe(exporter, SMOOTH_PURPUR, Items.PURPUR_BLOCK);
		OfferStonecuttingRecipe(exporter, SMOOTH_PURPUR, Items.PURPUR_BLOCK);
		OfferSmeltingRecipe(exporter, SMOOTH_PURPUR_STAIRS, Items.PURPUR_STAIRS);
		OfferStonecuttingRecipe(exporter, SMOOTH_PURPUR_STAIRS, Items.PURPUR_BLOCK);
		OfferStonecuttingRecipe(exporter, SMOOTH_PURPUR_STAIRS, SMOOTH_PURPUR);
		OfferSmeltingRecipe(exporter, SMOOTH_PURPUR_SLAB, Items.PURPUR_SLAB);
		OfferStonecuttingRecipe(exporter, SMOOTH_PURPUR_SLAB, Items.PURPUR_BLOCK, 2);
		OfferStonecuttingRecipe(exporter, SMOOTH_PURPUR_SLAB, SMOOTH_PURPUR, 2);
		OfferSmeltingRecipe(exporter, SMOOTH_PURPUR_WALL, PURPUR_WALL);
		OfferStonecuttingRecipe(exporter, SMOOTH_PURPUR_WALL, Items.PURPUR_BLOCK);
		OfferStonecuttingRecipe(exporter, SMOOTH_PURPUR_WALL, SMOOTH_PURPUR);
		//</editor-fold>
		//<editor-fold desc="Shulker">
		Recipes.MakeHelmet(SHULKER_HELMET, Items.SHULKER_SHELL).offerTo(exporter);
		Recipes.MakeChestplate(SHULKER_CHESTPLATE, Items.SHULKER_SHELL).offerTo(exporter);
		Recipes.MakeLeggings(SHULKER_LEGGINGS, Items.SHULKER_SHELL).offerTo(exporter);
		Recipes.MakeBoots(SHULKER_BOOTS, Items.SHULKER_SHELL).offerTo(exporter);
		//</editor-fold>
		//<editor-fold desc="Calcite">
		Recipes.MakeSlab(CALCITE_SLAB, Items.CALCITE).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, CALCITE_SLAB, Items.CALCITE, 2);
		Recipes.MakeStairs(CALCITE_STAIRS, Items.CALCITE).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, CALCITE_STAIRS, Items.CALCITE);
		Recipes.MakeWall(CALCITE_WALL, Items.CALCITE).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, CALCITE_WALL, Items.CALCITE);
		//Smooth
		OfferSmeltingRecipe(exporter, SMOOTH_CALCITE, Items.CALCITE);
		OfferStonecuttingRecipe(exporter, SMOOTH_CALCITE, Items.CALCITE);
		Recipes.MakeSlab(SMOOTH_CALCITE_SLAB, SMOOTH_CALCITE).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, SMOOTH_CALCITE_SLAB, SMOOTH_CALCITE, 2);
		Recipes.MakeStairs(SMOOTH_CALCITE_STAIRS, SMOOTH_CALCITE).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, SMOOTH_CALCITE_STAIRS, SMOOTH_CALCITE);
		Recipes.MakeWall(SMOOTH_CALCITE_WALL, SMOOTH_CALCITE).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, SMOOTH_CALCITE_WALL, SMOOTH_CALCITE);
		//Bricks
		Recipes.Make2x2(CALCITE_BRICKS, Items.CALCITE, 4).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, CALCITE_BRICKS, Items.CALCITE);
		Recipes.MakeSlab(CALCITE_BRICK_SLAB, CALCITE_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, CALCITE_BRICK_SLAB, Items.CALCITE, 2);
		OfferStonecuttingRecipe(exporter, CALCITE_BRICK_SLAB, CALCITE_BRICKS, 2);
		Recipes.MakeStairs(CALCITE_BRICK_STAIRS, CALCITE_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, CALCITE_BRICK_STAIRS, Items.CALCITE);
		OfferStonecuttingRecipe(exporter, CALCITE_BRICK_STAIRS, CALCITE_BRICKS);
		Recipes.MakeWall(CALCITE_BRICK_WALL, CALCITE_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, CALCITE_BRICK_WALL, Items.CALCITE);
		OfferStonecuttingRecipe(exporter, CALCITE_BRICK_WALL, CALCITE_BRICKS);
		//Tiles
		Recipes.Make2x2(CALCITE_TILES, CALCITE_BRICKS, 4).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, CALCITE_TILES, Items.CALCITE);
		OfferStonecuttingRecipe(exporter, CALCITE_TILES, CALCITE_BRICKS);
		Recipes.MakeStairs(CALCITE_TILE_STAIRS, CALCITE_TILES).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, CALCITE_TILE_STAIRS, Items.CALCITE);
		OfferStonecuttingRecipe(exporter, CALCITE_TILE_STAIRS, CALCITE_BRICKS);
		OfferStonecuttingRecipe(exporter, CALCITE_TILE_STAIRS, CALCITE_TILES);
		Recipes.MakeSlab(CALCITE_TILE_SLAB, CALCITE_TILES).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, CALCITE_TILE_SLAB, Items.CALCITE, 2);
		OfferStonecuttingRecipe(exporter, CALCITE_TILE_SLAB, CALCITE_BRICKS, 2);
		OfferStonecuttingRecipe(exporter, CALCITE_TILE_SLAB, CALCITE_TILES, 2);
		Recipes.MakeWall(CALCITE_TILE_WALL, CALCITE_TILES).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, CALCITE_TILE_WALL, Items.CALCITE);
		OfferStonecuttingRecipe(exporter, CALCITE_TILE_WALL, CALCITE_BRICKS);
		OfferStonecuttingRecipe(exporter, CALCITE_TILE_WALL, CALCITE_TILES);
		//</editor-fold>
		//<editor-fold desc="Dripstone">
		Recipes.MakeSlab(DRIPSTONE_SLAB, Items.DRIPSTONE_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, DRIPSTONE_SLAB, Items.DRIPSTONE_BLOCK, 2);
		Recipes.MakeStairs(DRIPSTONE_STAIRS, Items.DRIPSTONE_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, DRIPSTONE_STAIRS, Items.DRIPSTONE_BLOCK);
		Recipes.MakeWall(DRIPSTONE_WALL, Items.DRIPSTONE_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, DRIPSTONE_WALL, Items.DRIPSTONE_BLOCK);
		//Chiseled
		OfferStonecuttingRecipe(exporter, CHISELED_DRIPSTONE_BRICKS, Items.DRIPSTONE_BLOCK);
		OfferStonecuttingRecipe(exporter, CHISELED_DRIPSTONE_BRICKS, DRIPSTONE_BRICKS);
		Recipes.Make1x2(CHISELED_DRIPSTONE_BRICKS, DRIPSTONE_BRICK_SLAB).offerTo(exporter);
		//Smooth
		OfferSmeltingRecipe(exporter, SMOOTH_DRIPSTONE, Items.DRIPSTONE_BLOCK);
		OfferStonecuttingRecipe(exporter, SMOOTH_DRIPSTONE, Items.DRIPSTONE_BLOCK);
		Recipes.MakeSlab(SMOOTH_DRIPSTONE_SLAB, SMOOTH_DRIPSTONE).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, SMOOTH_DRIPSTONE_SLAB, SMOOTH_DRIPSTONE, 2);
		Recipes.MakeStairs(SMOOTH_DRIPSTONE_STAIRS, SMOOTH_DRIPSTONE).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, SMOOTH_DRIPSTONE_STAIRS, SMOOTH_DRIPSTONE);
		Recipes.MakeWall(SMOOTH_DRIPSTONE_WALL, SMOOTH_DRIPSTONE).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, SMOOTH_DRIPSTONE_WALL, SMOOTH_DRIPSTONE);
		//Bricks
		Recipes.Make2x2(DRIPSTONE_BRICKS, Items.DRIPSTONE_BLOCK, 4).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, DRIPSTONE_BRICKS, Items.DRIPSTONE_BLOCK);
		Recipes.MakeSlab(DRIPSTONE_BRICK_SLAB, DRIPSTONE_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, DRIPSTONE_BRICK_SLAB, Items.DRIPSTONE_BLOCK, 2);
		OfferStonecuttingRecipe(exporter, DRIPSTONE_BRICK_SLAB, DRIPSTONE_BRICKS, 2);
		Recipes.MakeStairs(DRIPSTONE_BRICK_STAIRS, DRIPSTONE_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, DRIPSTONE_BRICK_STAIRS, Items.DRIPSTONE_BLOCK);
		OfferStonecuttingRecipe(exporter, DRIPSTONE_BRICK_STAIRS, DRIPSTONE_BRICKS);
		Recipes.MakeWall(DRIPSTONE_BRICK_WALL, DRIPSTONE_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, DRIPSTONE_BRICK_WALL, Items.DRIPSTONE_BLOCK);
		OfferStonecuttingRecipe(exporter, DRIPSTONE_BRICK_WALL, DRIPSTONE_BRICKS);
		//Tiles
		Recipes.Make2x2(DRIPSTONE_TILES, DRIPSTONE_BRICKS, 4).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, DRIPSTONE_TILES, Items.DRIPSTONE_BLOCK);
		OfferStonecuttingRecipe(exporter, DRIPSTONE_TILES, DRIPSTONE_BRICKS);
		Recipes.MakeStairs(DRIPSTONE_TILE_STAIRS, DRIPSTONE_TILES).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, DRIPSTONE_TILE_STAIRS, Items.DRIPSTONE_BLOCK);
		OfferStonecuttingRecipe(exporter, DRIPSTONE_TILE_STAIRS, DRIPSTONE_BRICKS);
		OfferStonecuttingRecipe(exporter, DRIPSTONE_TILE_STAIRS, DRIPSTONE_TILES);
		Recipes.MakeSlab(DRIPSTONE_TILE_SLAB, DRIPSTONE_TILES).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, DRIPSTONE_TILE_SLAB, Items.DRIPSTONE_BLOCK, 2);
		OfferStonecuttingRecipe(exporter, DRIPSTONE_TILE_SLAB, DRIPSTONE_BRICKS, 2);
		OfferStonecuttingRecipe(exporter, DRIPSTONE_TILE_SLAB, DRIPSTONE_TILES, 2);
		Recipes.MakeWall(DRIPSTONE_TILE_WALL, DRIPSTONE_TILES).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, DRIPSTONE_TILE_WALL, Items.DRIPSTONE_BLOCK);
		OfferStonecuttingRecipe(exporter, DRIPSTONE_TILE_WALL, DRIPSTONE_BRICKS);
		OfferStonecuttingRecipe(exporter, DRIPSTONE_TILE_WALL, DRIPSTONE_TILES);
		//</editor-fold>
		//<editor-fold desc="Tuff">
		Recipes.MakeSlab(TUFF_SLAB, Items.TUFF).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, TUFF_SLAB, Items.TUFF, 2);
		Recipes.MakeStairs(TUFF_STAIRS, Items.TUFF).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, TUFF_STAIRS, Items.TUFF);
		Recipes.MakeWall(TUFF_WALL, Items.TUFF).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, TUFF_WALL, Items.TUFF);
		//Smooth
		OfferSmeltingRecipe(exporter, SMOOTH_TUFF, Items.TUFF);
		OfferStonecuttingRecipe(exporter, SMOOTH_TUFF, Items.TUFF);
		Recipes.MakeSlab(SMOOTH_TUFF_SLAB, SMOOTH_TUFF).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, SMOOTH_TUFF_SLAB, SMOOTH_TUFF, 2);
		Recipes.MakeStairs(SMOOTH_TUFF_STAIRS, SMOOTH_TUFF).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, SMOOTH_TUFF_STAIRS, SMOOTH_TUFF);
		Recipes.MakeWall(SMOOTH_TUFF_WALL, SMOOTH_TUFF).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, SMOOTH_TUFF_WALL, SMOOTH_TUFF);
		//Bricks
		Recipes.Make2x2(TUFF_BRICKS, Items.TUFF, 4).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, TUFF_BRICKS, Items.TUFF);
		Recipes.MakeSlab(TUFF_BRICK_SLAB, TUFF_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, TUFF_BRICK_SLAB, Items.TUFF, 2);
		OfferStonecuttingRecipe(exporter, TUFF_BRICK_SLAB, TUFF_BRICKS, 2);
		Recipes.MakeStairs(TUFF_BRICK_STAIRS, TUFF_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, TUFF_BRICK_STAIRS, Items.TUFF);
		OfferStonecuttingRecipe(exporter, TUFF_BRICK_STAIRS, TUFF_BRICKS);
		Recipes.MakeWall(TUFF_BRICK_WALL, TUFF_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, TUFF_BRICK_WALL, Items.TUFF);
		OfferStonecuttingRecipe(exporter, TUFF_BRICK_WALL, TUFF_BRICKS);
		//Tiles
		Recipes.Make2x2(TUFF_TILES, TUFF_BRICKS, 4).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, TUFF_TILES, Items.TUFF);
		OfferStonecuttingRecipe(exporter, TUFF_TILES, TUFF_BRICKS);
		Recipes.MakeStairs(TUFF_TILE_STAIRS, TUFF_TILES).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, TUFF_TILE_STAIRS, Items.TUFF);
		OfferStonecuttingRecipe(exporter, TUFF_TILE_STAIRS, TUFF_BRICKS);
		OfferStonecuttingRecipe(exporter, TUFF_TILE_STAIRS, TUFF_TILES);
		Recipes.MakeSlab(TUFF_TILE_SLAB, TUFF_TILES).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, TUFF_TILE_SLAB, Items.TUFF, 2);
		OfferStonecuttingRecipe(exporter, TUFF_TILE_SLAB, TUFF_BRICKS, 2);
		OfferStonecuttingRecipe(exporter, TUFF_TILE_SLAB, TUFF_TILES, 2);
		Recipes.MakeWall(TUFF_TILE_WALL, TUFF_TILES).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, TUFF_TILE_WALL, Items.TUFF);
		OfferStonecuttingRecipe(exporter, TUFF_TILE_WALL, TUFF_BRICKS);
		OfferStonecuttingRecipe(exporter, TUFF_TILE_WALL, TUFF_TILES);
		//</editor-fold>
		//<editor-fold desc="Stone">
		Recipes.MakeShapeless(MOSSY_CHISELED_STONE_BRICKS, Items.CHISELED_STONE_BRICKS).input(Ingredient.ofItems(Items.VINE, Items.MOSS_BLOCK)).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, MOSSY_CHISELED_STONE_BRICKS, Items.MOSSY_STONE_BRICKS);
		Recipes.Make2x2(STONE_TILES, Items.STONE_BRICKS, 4).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, STONE_TILES, Items.STONE);
		OfferStonecuttingRecipe(exporter, STONE_TILES, Items.STONE_BRICKS);
		Recipes.MakeStairs(STONE_TILE_STAIRS, STONE_TILES).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, STONE_TILE_STAIRS, Items.STONE);
		OfferStonecuttingRecipe(exporter, STONE_TILE_STAIRS, Items.STONE_BRICKS);
		OfferStonecuttingRecipe(exporter, STONE_TILE_STAIRS, STONE_TILES);
		Recipes.MakeSlab(STONE_TILE_SLAB, STONE_TILES).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, STONE_TILE_SLAB, Items.STONE, 2);
		OfferStonecuttingRecipe(exporter, STONE_TILE_SLAB, Items.STONE_BRICKS, 2);
		OfferStonecuttingRecipe(exporter, STONE_TILE_SLAB, STONE_TILES, 2);
		Recipes.MakeWall(STONE_TILE_WALL, STONE_TILES).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, STONE_TILE_WALL, Items.STONE);
		OfferStonecuttingRecipe(exporter, STONE_TILE_WALL, Items.STONE_BRICKS);
		OfferStonecuttingRecipe(exporter, STONE_TILE_WALL, STONE_TILES);
		//</editor-fold>
		//<editor-fold desc="Amethyst">
		Recipes.MakeShapeless(Items.AMETHYST_SHARD, Items.AMETHYST_BLOCK, 4).offerTo(exporter, ID("amethyst_shard_from_amethyst_block"));
		Recipes.MakeSlab(AMETHYST_SLAB, Items.AMETHYST_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, AMETHYST_SLAB, Items.AMETHYST_BLOCK, 2);
		Recipes.MakeStairs(AMETHYST_STAIRS, Items.AMETHYST_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, AMETHYST_STAIRS, Items.AMETHYST_BLOCK);
		Recipes.MakeWall(AMETHYST_WALL, Items.AMETHYST_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, AMETHYST_WALL, Items.AMETHYST_BLOCK);
		//Crystal Block
		Recipes.MakeShapeless(Items.AMETHYST_SHARD, AMETHYST_CRYSTAL_BLOCK, 9).offerTo(exporter, ID("amethyst_shard_from_amethyst_crystal_block"));
		Recipes.Make3x3(AMETHYST_CRYSTAL_BLOCK, Items.AMETHYST_SHARD).offerTo(exporter);
		Recipes.MakeSlab(AMETHYST_CRYSTAL_SLAB, AMETHYST_CRYSTAL_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, AMETHYST_CRYSTAL_SLAB, AMETHYST_CRYSTAL_BLOCK, 2);
		Recipes.MakeStairs(AMETHYST_CRYSTAL_STAIRS, AMETHYST_CRYSTAL_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, AMETHYST_CRYSTAL_STAIRS, AMETHYST_CRYSTAL_BLOCK);
		Recipes.MakeWall(AMETHYST_CRYSTAL_WALL, AMETHYST_CRYSTAL_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, AMETHYST_CRYSTAL_WALL, AMETHYST_CRYSTAL_BLOCK);
		//Bricks
		Recipes.Make2x2(AMETHYST_BRICKS, Items.AMETHYST_BLOCK, 4).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, AMETHYST_BRICKS, Items.AMETHYST_BLOCK);
		Recipes.MakeSlab(AMETHYST_BRICK_SLAB, AMETHYST_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, AMETHYST_BRICK_SLAB, Items.AMETHYST_BLOCK, 2);
		OfferStonecuttingRecipe(exporter, AMETHYST_BRICK_SLAB, AMETHYST_BRICKS, 2);
		Recipes.MakeStairs(AMETHYST_BRICK_STAIRS, AMETHYST_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, AMETHYST_BRICK_STAIRS, Items.AMETHYST_BLOCK);
		OfferStonecuttingRecipe(exporter, AMETHYST_BRICK_STAIRS, AMETHYST_BRICKS);
		Recipes.MakeWall(AMETHYST_BRICK_WALL, AMETHYST_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, AMETHYST_BRICK_WALL, Items.AMETHYST_BLOCK);
		OfferStonecuttingRecipe(exporter, AMETHYST_BRICK_WALL, AMETHYST_BRICKS);
		//Armor & Tools
		Recipes.MakeAxe(AMETHYST_AXE, Items.AMETHYST_SHARD).offerTo(exporter);
		Recipes.MakeHammer(AMETHYST_HAMMER, AMETHYST_CRYSTAL_BLOCK).offerTo(exporter);
		Recipes.MakeHoe(AMETHYST_HOE, Items.AMETHYST_SHARD).offerTo(exporter);
		Recipes.MakeKnife(AMETHYST_KNIFE, Items.AMETHYST_SHARD).offerTo(exporter);
		Recipes.MakePickaxe(AMETHYST_PICKAXE, Items.AMETHYST_SHARD).offerTo(exporter);
		Recipes.MakeShovel(AMETHYST_SHOVEL, Items.AMETHYST_SHARD).offerTo(exporter);
		Recipes.MakeSword(AMETHYST_SWORD, Items.AMETHYST_SHARD).offerTo(exporter);
		Recipes.MakeHelmet(AMETHYST_HELMET, Items.AMETHYST_SHARD).offerTo(exporter);
		Recipes.MakeChestplate(AMETHYST_CHESTPLATE, Items.AMETHYST_SHARD).offerTo(exporter);
		Recipes.MakeLeggings(AMETHYST_LEGGINGS, Items.AMETHYST_SHARD).offerTo(exporter);
		Recipes.MakeBoots(AMETHYST_BOOTS, Items.AMETHYST_SHARD).offerTo(exporter);
		Recipes.MakeHorseArmor(AMETHYST_HORSE_ARMOR, Items.AMETHYST_SHARD).offerTo(exporter);
		//</editor-fold>
		//<editor-fold desc="Arrows">
		Recipes.MakeShaped(AMETHYST_ARROW, Items.AMETHYST_SHARD).input('|', Items.STICK).input('F', ModItemTags.FEATHERS).pattern("  #").pattern(" | ").pattern("F  ").offerTo(exporter);
		Recipes.MakeShapeless(CHORUS_ARROW, Items.CHORUS_FRUIT).input(ItemTags.ARROWS).offerTo(exporter);
		Recipes.MakeShaped(BONE_ARROW, BONE_SHARD).input('|', Items.STICK).input('F', ModItemTags.FEATHERS).pattern("  #").pattern(" | ").pattern("F  ").offerTo(exporter);
		//</editor-fold>
		//<editor-fold desc="Summoning Arrows">
		Recipes.MakeSummoningArrow(AXOLOTL_SUMMONING_ARROW, Items.AXOLOTL_SPAWN_EGG).offerTo(exporter);
		Recipes.MakeSummoningArrow(BAT_SUMMONING_ARROW, Items.BAT_SPAWN_EGG).offerTo(exporter);
		Recipes.MakeSummoningArrow(BEE_SUMMONING_ARROW, Items.BEE_SPAWN_EGG).offerTo(exporter);
		Recipes.MakeSummoningArrow(BLAZE_SUMMONING_ARROW, Items.BLAZE_SPAWN_EGG).offerTo(exporter);
		Recipes.MakeSummoningArrow(CAT_SUMMONING_ARROW, Items.CAT_SPAWN_EGG).offerTo(exporter);
		Recipes.MakeSummoningArrow(CAMEL_SUMMONING_ARROW, CAMEL_SPAWN_EGG).offerTo(exporter);
		Recipes.MakeSummoningArrow(CAVE_SPIDER_SUMMONING_ARROW, Items.CAVE_SPIDER_SPAWN_EGG).offerTo(exporter);
		Recipes.MakeSummoningArrow(CHICKEN_SUMMONING_ARROW, Items.CHICKEN_SPAWN_EGG).offerTo(exporter);
		Recipes.MakeSummoningArrow(COD_SUMMONING_ARROW, Items.COD_SPAWN_EGG).offerTo(exporter);
		Recipes.MakeSummoningArrow(COW_SUMMONING_ARROW, Items.COW_SPAWN_EGG).offerTo(exporter);
		Recipes.MakeSummoningArrow(CREEPER_SUMMONING_ARROW, Items.CREEPER_SPAWN_EGG).offerTo(exporter);
		Recipes.MakeSummoningArrow(DOLPHIN_SUMMONING_ARROW, Items.DOLPHIN_SPAWN_EGG).offerTo(exporter);
		Recipes.MakeSummoningArrow(DONKEY_SUMMONING_ARROW, Items.DONKEY_SPAWN_EGG).offerTo(exporter);
		Recipes.MakeSummoningArrow(DROWNED_SUMMONING_ARROW, Items.DROWNED_SPAWN_EGG).offerTo(exporter);
		Recipes.MakeSummoningArrow(ELDER_GUARDIAN_SUMMONING_ARROW, Items.ELDER_GUARDIAN_SPAWN_EGG).offerTo(exporter);
		Recipes.MakeSummoningArrow(ENDERMAN_SUMMONING_ARROW, Items.ENDERMAN_SPAWN_EGG).offerTo(exporter);
		Recipes.MakeSummoningArrow(ENDERMITE_SUMMONING_ARROW, Items.ENDERMITE_SPAWN_EGG).offerTo(exporter);
		Recipes.MakeSummoningArrow(EVOKER_SUMMONING_ARROW, Items.EVOKER_SPAWN_EGG).offerTo(exporter);
		Recipes.MakeSummoningArrow(FOX_SUMMONING_ARROW, Items.FOX_SPAWN_EGG).offerTo(exporter);
		Recipes.MakeSummoningArrow(FROG_SUMMONING_ARROW, FROG_SPAWN_EGG).offerTo(exporter);
		Recipes.MakeSummoningArrow(GHAST_SUMMONING_ARROW, Items.GHAST_SPAWN_EGG).offerTo(exporter);
		Recipes.MakeSummoningArrow(GLOW_SQUID_SUMMONING_ARROW, Items.GLOW_SQUID_SPAWN_EGG).offerTo(exporter);
		Recipes.MakeSummoningArrow(GOAT_SUMMONING_ARROW, Items.GOAT_SPAWN_EGG).offerTo(exporter);
		Recipes.MakeSummoningArrow(GUARDIAN_SUMMONING_ARROW, Items.GUARDIAN_SPAWN_EGG).offerTo(exporter);
		Recipes.MakeSummoningArrow(HOGLIN_SUMMONING_ARROW, Items.HOGLIN_SPAWN_EGG).offerTo(exporter);
		Recipes.MakeSummoningArrow(HORSE_SUMMONING_ARROW, Items.HORSE_SPAWN_EGG).offerTo(exporter);
		Recipes.MakeSummoningArrow(HUSK_SUMMONING_ARROW, Items.HUSK_SPAWN_EGG).offerTo(exporter);
		Recipes.MakeShaped(IRON_GOLEM_SUMMONING_ARROW.asItem(), Ingredient.ofItems(Items.CARVED_PUMPKIN, Items.JACK_O_LANTERN))
				.input('/', ItemTags.ARROWS).input('S', Items.IRON_BLOCK)
				.pattern(" # ").pattern("SSS").pattern("/S ").offerTo(exporter);
		Recipes.MakeSummoningArrow(LLAMA_SUMMONING_ARROW, Items.LLAMA_SPAWN_EGG).offerTo(exporter);
		Recipes.MakeSummoningArrow(MAGMA_CUBE_SUMMONING_ARROW, Items.MAGMA_CUBE_SPAWN_EGG).offerTo(exporter);
		Recipes.MakeSummoningArrow(MOOSHROOM_SUMMONING_ARROW, Items.MOOSHROOM_SPAWN_EGG).offerTo(exporter);
		Recipes.MakeSummoningArrow(MULE_SUMMONING_ARROW, Items.MULE_SPAWN_EGG).offerTo(exporter);
		Recipes.MakeSummoningArrow(OCELOT_SUMMONING_ARROW, Items.OCELOT_SPAWN_EGG).offerTo(exporter);
		Recipes.MakeSummoningArrow(PANDA_SUMMONING_ARROW, Items.PANDA_SPAWN_EGG).offerTo(exporter);
		Recipes.MakeSummoningArrow(PARROT_SUMMONING_ARROW, Items.PARROT_SPAWN_EGG).offerTo(exporter);
		Recipes.MakeSummoningArrow(PHANTOM_SUMMONING_ARROW, Items.PHANTOM_SPAWN_EGG).offerTo(exporter);
		Recipes.MakeSummoningArrow(PIG_SUMMONING_ARROW, Items.PIG_SPAWN_EGG).offerTo(exporter);
		Recipes.MakeSummoningArrow(PIGLIN_SUMMONING_ARROW, Items.PIGLIN_SPAWN_EGG).offerTo(exporter);
		Recipes.MakeSummoningArrow(PIGLIN_BRUTE_SUMMONING_ARROW, Items.PIGLIN_BRUTE_SPAWN_EGG).offerTo(exporter);
		Recipes.MakeSummoningArrow(PILLAGER_SUMMONING_ARROW, Items.PILLAGER_SPAWN_EGG).offerTo(exporter);
		Recipes.MakeSummoningArrow(POLAR_BEAR_SUMMONING_ARROW, Items.POLAR_BEAR_SPAWN_EGG).offerTo(exporter);
		Recipes.MakeSummoningArrow(PUFFERFISH_SUMMONING_ARROW, Items.PUFFERFISH_SPAWN_EGG).offerTo(exporter);
		Recipes.MakeSummoningArrow(RABBIT_SUMMONING_ARROW, Items.RABBIT_SPAWN_EGG).offerTo(exporter);
		Recipes.MakeSummoningArrow(RAVAGER_SUMMONING_ARROW, Items.RAVAGER_SPAWN_EGG).offerTo(exporter);
		Recipes.MakeSummoningArrow(SALMON_SUMMONING_ARROW, Items.SALMON_SPAWN_EGG).offerTo(exporter);
		Recipes.MakeSummoningArrow(SHEEP_SUMMONING_ARROW, Items.SHEEP_SPAWN_EGG).offerTo(exporter);
		Recipes.MakeSummoningArrow(SHULKER_SUMMONING_ARROW, Items.SHULKER_SPAWN_EGG).offerTo(exporter);
		Recipes.MakeSummoningArrow(SILVERFISH_SUMMONING_ARROW, Items.SILVERFISH_SPAWN_EGG).offerTo(exporter);
		Recipes.MakeSummoningArrow(SKELETON_SUMMONING_ARROW, Items.SKELETON_SPAWN_EGG).offerTo(exporter);
		Recipes.MakeSummoningArrow(SKELETON_HORSE_SUMMONING_ARROW, Items.SKELETON_HORSE_SPAWN_EGG).offerTo(exporter);
		Recipes.MakeSummoningArrow(SLIME_SUMMONING_ARROW, Items.SLIME_SPAWN_EGG).offerTo(exporter);
		Recipes.MakeSummoningArrow(SNIFFER_SUMMONING_ARROW, SNIFFER_SPAWN_EGG).offerTo(exporter);
		Recipes.MakeShaped(SNOW_GOLEM_SUMMONING_ARROW.asItem(), Ingredient.ofItems(Items.CARVED_PUMPKIN, Items.JACK_O_LANTERN))
				.input('/', ItemTags.ARROWS).input('S', Items.SNOW_BLOCK)
				.pattern(" #").pattern(" S").pattern("/S").offerTo(exporter);
		Recipes.MakeSummoningArrow(SPIDER_SUMMONING_ARROW, Items.SPIDER_SPAWN_EGG).offerTo(exporter);
		Recipes.MakeSummoningArrow(SQUID_SUMMONING_ARROW, Items.SQUID_SPAWN_EGG).offerTo(exporter);
		Recipes.MakeSummoningArrow(STRAY_SUMMONING_ARROW, Items.STRAY_SPAWN_EGG).offerTo(exporter);
		Recipes.MakeSummoningArrow(STRIDER_SUMMONING_ARROW, Items.STRIDER_SPAWN_EGG).offerTo(exporter);
		Recipes.MakeSummoningArrow(TADPOLE_SUMMONING_ARROW, TADPOLE_SPAWN_EGG).offerTo(exporter);
		Recipes.MakeSummoningArrow(TRADER_LLAMA_SUMMONING_ARROW, Items.TRADER_LLAMA_SPAWN_EGG).offerTo(exporter);
		Recipes.MakeSummoningArrow(TROPICAL_FISH_SUMMONING_ARROW, Items.TROPICAL_FISH_SPAWN_EGG).offerTo(exporter);
		Recipes.MakeSummoningArrow(TURTLE_SUMMONING_ARROW, Items.TURTLE_SPAWN_EGG).offerTo(exporter);
		Recipes.MakeSummoningArrow(VEX_SUMMONING_ARROW, Items.VEX_SPAWN_EGG).offerTo(exporter);
		Recipes.MakeSummoningArrow(VILLAGER_SUMMONING_ARROW, Items.VILLAGER_SPAWN_EGG).offerTo(exporter);
		Recipes.MakeSummoningArrow(VINDICATOR_SUMMONING_ARROW, Items.VINDICATOR_SPAWN_EGG).offerTo(exporter);
		Recipes.MakeSummoningArrow(WANDERING_TRADER_SUMMONING_ARROW, Items.WANDERING_TRADER_SPAWN_EGG).offerTo(exporter);
		Recipes.MakeSummoningArrow(WARDEN_SUMMONING_ARROW, WARDEN_SPAWN_EGG).offerTo(exporter);
		Recipes.MakeSummoningArrow(WITCH_SUMMONING_ARROW, Items.WITCH_SPAWN_EGG).offerTo(exporter);
		Recipes.MakeShaped(WITHER_SUMMONING_ARROW.asItem(), Items.WITHER_SKELETON_SKULL).input('/', ItemTags.ARROWS)
				.input('S', Ingredient.ofItems(Items.SOUL_SAND, Items.SOUL_SOIL))
				.pattern("###").pattern("SSS").pattern("/S ").offerTo(exporter);
		Recipes.MakeSummoningArrow(WITHER_SKELETON_SUMMONING_ARROW, Items.WITHER_SKELETON_SPAWN_EGG).offerTo(exporter);
		Recipes.MakeSummoningArrow(WOLF_SUMMONING_ARROW, Items.WOLF_SPAWN_EGG).offerTo(exporter);
		Recipes.MakeSummoningArrow(ZOGLIN_SUMMONING_ARROW, Items.ZOGLIN_SPAWN_EGG).offerTo(exporter);
		Recipes.MakeSummoningArrow(ZOMBIE_SUMMONING_ARROW, Items.ZOMBIE_SPAWN_EGG).offerTo(exporter);
		Recipes.MakeSummoningArrow(ZOMBIE_HORSE_SUMMONING_ARROW, Items.ZOMBIE_HORSE_SPAWN_EGG).offerTo(exporter);
		Recipes.MakeSummoningArrow(ZOMBIE_VILLAGER_SUMMONING_ARROW, Items.ZOMBIE_VILLAGER_SPAWN_EGG).offerTo(exporter);
		Recipes.MakeSummoningArrow(ZOMBIFIED_PIGLIN_SUMMONING_ARROW, Items.ZOMBIFIED_PIGLIN_SPAWN_EGG).offerTo(exporter);
		//</editor-fold>
		//<editor-fold desc="Mod Mob Summoning Arrows">
		Recipes.MakeShaped(MELON_GOLEM_SUMMONING_ARROW.asItem(), Ingredient.ofItems(CARVED_MELON, MELON_LANTERN))
				.input('/', ItemTags.ARROWS).input('S', Items.SNOW_BLOCK)
				.pattern(" #").pattern(" S").pattern("/S").offerTo(exporter);
		Recipes.MakeSummoningArrow(BONE_SPIDER_SUMMONING_ARROW, BONE_SPIDER_SPAWN_EGG).offerTo(exporter);
		Recipes.MakeSummoningArrow(HEDGEHOG_SUMMONING_ARROW, HEDGEHOG_SPAWN_EGG).offerTo(exporter);
		Recipes.MakeSummoningArrow(RACCOON_SUMMONING_ARROW, RACCOON_SPAWN_EGG).offerTo(exporter);
		Recipes.MakeSummoningArrow(RED_PANDA_SUMMONING_ARROW, RED_PANDA_SPAWN_EGG).offerTo(exporter);
		Recipes.MakeSummoningArrow(JUMPING_SPIDER_SUMMONING_ARROW, JUMPING_SPIDER_SPAWN_EGG).offerTo(exporter);
		Recipes.MakeSummoningArrow(RED_PHANTOM_SUMMONING_ARROW, RED_PHANTOM_SPAWN_EGG).offerTo(exporter);
		Recipes.MakeSummoningArrow(PIRANHA_SUMMONING_ARROW, PIRANHA_SPAWN_EGG).offerTo(exporter);
		Recipes.MakeSummoningArrow(FANCY_CHICKEN_SUMMONING_ARROW, FANCY_CHICKEN_SPAWN_EGG).offerTo(exporter);
		Recipes.MakeSummoningArrow(BLUE_MOOSHROOM_SUMMONING_ARROW, BLUE_MOOSHROOM_SPAWN_EGG).offerTo(exporter);
		Recipes.MakeSummoningArrow(NETHER_MOOSHROOM_SUMMONING_ARROW, NETHER_MOOSHROOM_SPAWN_EGG).offerTo(exporter);
		Recipes.MakeSummoningArrow(MOOBLOOM_SUMMONING_ARROW, MOOBLOOM_SPAWN_EGG).offerTo(exporter);
		Recipes.MakeSummoningArrow(MOOLIP_SUMMONING_ARROW, MOOLIP_SPAWN_EGG).offerTo(exporter);
		Recipes.MakeSummoningArrow(MOOBLOSSOM_SUMMONING_ARROW, MOOBLOSSOM_SPAWN_EGG).offerTo(exporter);
		Recipes.MakeSummoningArrow(MOSSY_SHEEP_SUMMONING_ARROW, MOSSY_SHEEP_SPAWN_EGG).offerTo(exporter);
		Recipes.MakeSummoningArrow(RAINBOW_SHEEP_SUMMONING_ARROW, RAINBOW_SHEEP_SPAWN_EGG).offerTo(exporter);
		Recipes.MakeSummoningArrow(MOSSY_SKELETON_SUMMONING_ARROW, MOSSY_SKELETON_SPAWN_EGG).offerTo(exporter);
		Recipes.MakeSummoningArrow(SUNKEN_SKELETON_SUMMONING_ARROW, SUNKEN_SKELETON_SPAWN_EGG).offerTo(exporter);
		Recipes.MakeSummoningArrow(TROPICAL_SLIME_SUMMONING_ARROW, TROPICAL_SLIME_SPAWN_EGG).offerTo(exporter);
		Recipes.MakeSummoningArrow(PINK_SLIME_SUMMONING_ARROW, PINK_SLIME_SPAWN_EGG).offerTo(exporter);
		Recipes.MakeSummoningArrow(FROZEN_ZOMBIE_SUMMONING_ARROW, FROZEN_ZOMBIE_SPAWN_EGG).offerTo(exporter);
		//</editor-fold>
		//<editor-fold desc="Piranha">
		OfferSmeltingRecipe(exporter, COOKED_PIRANHA, PIRANHA, 0.35);
		Recipes.MakeSmoking(COOKED_PIRANHA, PIRANHA, 100, 0.35).offerTo(exporter, _from_smoking(COOKED_PIRANHA));
		Recipes.MakeCampfireRecipe(COOKED_PIRANHA, PIRANHA, 600, 0.35).offerTo(exporter, _from_campfire(COOKED_PIRANHA));
		//</editor-fold>
		//<editor-fold desc="Obsidian">
		Recipes.MakeSlab(OBSIDIAN_SLAB, Items.OBSIDIAN).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, OBSIDIAN_SLAB, Items.OBSIDIAN, 2);
		Recipes.MakeStairs(OBSIDIAN_STAIRS, Items.OBSIDIAN).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, OBSIDIAN_STAIRS, Items.OBSIDIAN);
		Recipes.MakeWall(OBSIDIAN_WALL, Items.OBSIDIAN).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, OBSIDIAN_WALL, Items.OBSIDIAN);
		//Crying
		Recipes.MakeSlab(CRYING_OBSIDIAN_SLAB, Items.CRYING_OBSIDIAN).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, CRYING_OBSIDIAN_SLAB, Items.CRYING_OBSIDIAN, 2);
		Recipes.MakeStairs(CRYING_OBSIDIAN_STAIRS, Items.CRYING_OBSIDIAN).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, CRYING_OBSIDIAN_STAIRS, Items.CRYING_OBSIDIAN);
		Recipes.MakeWall(CRYING_OBSIDIAN_WALL, Items.CRYING_OBSIDIAN).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, CRYING_OBSIDIAN_WALL, Items.CRYING_OBSIDIAN);
		//Bleeding
		Recipes.MakeSlab(BLEEDING_OBSIDIAN_SLAB, BLEEDING_OBSIDIAN).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, BLEEDING_OBSIDIAN_SLAB, BLEEDING_OBSIDIAN, 2);
		Recipes.MakeStairs(BLEEDING_OBSIDIAN_STAIRS, BLEEDING_OBSIDIAN).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, BLEEDING_OBSIDIAN_STAIRS, BLEEDING_OBSIDIAN);
		Recipes.MakeWall(BLEEDING_OBSIDIAN_WALL, BLEEDING_OBSIDIAN).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, BLEEDING_OBSIDIAN_WALL, BLEEDING_OBSIDIAN);
		//Tools
		Recipes.MakeAxe(OBSIDIAN_AXE, Items.OBSIDIAN).offerTo(exporter);
		Recipes.MakeHammer(OBSIDIAN_HAMMER, Items.OBSIDIAN).offerTo(exporter);
		Recipes.MakeHoe(OBSIDIAN_HOE, Items.OBSIDIAN).offerTo(exporter);
		Recipes.MakeKnife(OBSIDIAN_KNIFE, Items.OBSIDIAN).offerTo(exporter);
		Recipes.MakePickaxe(OBSIDIAN_PICKAXE, Items.OBSIDIAN).offerTo(exporter);
		Recipes.MakeShovel(OBSIDIAN_SHOVEL, Items.OBSIDIAN).offerTo(exporter);
		Recipes.MakeSword(OBSIDIAN_SWORD, Items.OBSIDIAN).offerTo(exporter);
		//</editor-fold>
		//<editor-fold desc="Purple Ender Eye">
		Recipes.MakeShaped(GRAVITY_HAMMER, Items.CRYING_OBSIDIAN).input('|', NETHERITE_ROD).input('E', PURPLE_ENDER_EYE).pattern("#E#").pattern(" | ").pattern(" | ").offerTo(exporter);
		//</editor-fold>
		//<editor-fold desc="Glass">
		Recipes.Make3x2(TINTED_GLASS_PANE, Items.TINTED_GLASS).offerTo(exporter);
		Recipes.MakeSlab(TINTED_GLASS_SLAB, Items.TINTED_GLASS).offerTo(exporter);
		Recipes.Make3x2(TINTED_GLASS_TRAPDOOR, TINTED_GLASS_PANE).offerTo(exporter);
		Recipes.MakeShaped(TINTED_GOGGLES, Items.TINTED_GLASS).input('X', Items.LEATHER).pattern("#X#").offerTo(exporter);
		Recipes.MakeShaped(RUBY_GLASS, RUBY).input('G', Items.GLASS).pattern(" # ").pattern("#G#").pattern(" # ").offerTo(exporter);
		Recipes.Make3x2(RUBY_GLASS_PANE, RUBY_GLASS).offerTo(exporter);
		Recipes.MakeSlab(RUBY_GLASS_SLAB, RUBY_GLASS).offerTo(exporter);
		Recipes.Make3x2(RUBY_GLASS_TRAPDOOR, RUBY_GLASS_PANE).offerTo(exporter);
		Recipes.MakeShaped(RUBY_GOGGLES, RUBY).input('X', Items.LEATHER).pattern("#X#").offerTo(exporter);
		Recipes.MakeSlab(GLASS_SLAB, Items.GLASS).offerTo(exporter);
		Recipes.Make3x2(GLASS_TRAPDOOR, Items.GLASS_PANE).offerTo(exporter);
		for (DyeColor color : DyeColor.values()){
			Recipes.MakeSlab(STAINED_GLASS_SLABS.get(color), ColorUtil.GetStainedGlassItem(color));
			Recipes.Make2x2(STAINED_GLASS_TRAPDOORS.get(color), ColorUtil.GetStainedGlassPaneItem(color));
		}
		//</editor-fold>
		//<editor-fold desc="Emerald">
		Recipes.MakeStairs(EMERALD_STAIRS, Items.EMERALD_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, EMERALD_STAIRS, Items.EMERALD_BLOCK);
		Recipes.MakeSlab(EMERALD_SLAB, Items.EMERALD_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, EMERALD_SLAB, Items.EMERALD_BLOCK, 2);
		Recipes.MakeWall(EMERALD_WALL, Items.EMERALD_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, EMERALD_WALL, Items.EMERALD_BLOCK);
		//Brick
		Recipes.Make2x2(EMERALD_BRICKS, Items.EMERALD_BLOCK, 4).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, EMERALD_BRICKS, Items.EMERALD_BLOCK);
		Recipes.MakeStairs(EMERALD_BRICK_STAIRS, EMERALD_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, EMERALD_BRICK_STAIRS, Items.EMERALD_BLOCK);
		OfferStonecuttingRecipe(exporter, EMERALD_BRICK_STAIRS, EMERALD_BRICKS);
		Recipes.MakeSlab(EMERALD_BRICK_SLAB, EMERALD_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, EMERALD_BRICK_SLAB, Items.EMERALD_BLOCK, 2);
		OfferStonecuttingRecipe(exporter, EMERALD_BRICK_SLAB, EMERALD_BRICKS, 2);
		Recipes.MakeWall(EMERALD_BRICK_WALL, EMERALD_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, EMERALD_BRICK_WALL, Items.EMERALD_BLOCK);
		OfferStonecuttingRecipe(exporter, EMERALD_BRICK_WALL, EMERALD_BRICKS);
		//Cut
		OfferStonecuttingRecipe(exporter, CUT_EMERALD, Items.EMERALD_BLOCK);
		Recipes.MakeSlab(CUT_EMERALD_SLAB, CUT_EMERALD).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, CUT_EMERALD_SLAB, Items.EMERALD_BLOCK, 2);
		OfferStonecuttingRecipe(exporter, CUT_EMERALD_SLAB, CUT_EMERALD, 2);
		Recipes.MakeStairs(CUT_EMERALD_STAIRS, CUT_EMERALD).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, CUT_EMERALD_STAIRS, Items.EMERALD_BLOCK);
		OfferStonecuttingRecipe(exporter, CUT_EMERALD_STAIRS, CUT_EMERALD);
		Recipes.MakeWall(CUT_EMERALD_WALL, CUT_EMERALD).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, CUT_EMERALD_WALL, Items.EMERALD_BLOCK);
		OfferStonecuttingRecipe(exporter, CUT_EMERALD_WALL, CUT_EMERALD);
		//Armor & Tools
		Recipes.MakeAxe(EMERALD_AXE, Items.EMERALD).offerTo(exporter);
		Recipes.MakeHammer(EMERALD_HAMMER, Items.EMERALD_BLOCK).offerTo(exporter);
		Recipes.MakeHoe(EMERALD_HOE, Items.EMERALD).offerTo(exporter);
		Recipes.MakeKnife(EMERALD_KNIFE, Items.EMERALD).offerTo(exporter);
		Recipes.MakePickaxe(EMERALD_PICKAXE, Items.EMERALD).offerTo(exporter);
		Recipes.MakeShovel(EMERALD_SHOVEL, Items.EMERALD).offerTo(exporter);
		Recipes.MakeSword(EMERALD_SWORD, Items.EMERALD).offerTo(exporter);
		Recipes.MakeHelmet(EMERALD_HELMET, Items.EMERALD).offerTo(exporter);
		Recipes.MakeChestplate(EMERALD_CHESTPLATE, Items.EMERALD).offerTo(exporter);
		Recipes.MakeLeggings(EMERALD_LEGGINGS, Items.EMERALD).offerTo(exporter);
		Recipes.MakeBoots(EMERALD_BOOTS, Items.EMERALD).offerTo(exporter);
		Recipes.MakeHorseArmor(EMERALD_HORSE_ARMOR, Items.EMERALD).offerTo(exporter);
		//</editor-fold>
		//<editor-fold desc="Ruby">
		Recipes.MakeSmelting(RUBY, Ingredient.ofItems(RUBY_ORE, DEEPSLATE_RUBY_ORE), 200, 1.0).group("ruby").offerTo(exporter, ID("ruby_from_smelting"));
		Recipes.MakeBlasting(RUBY, Ingredient.ofItems(RUBY_ORE, DEEPSLATE_RUBY_ORE), 100, 1.0).group("ruby").offerTo(exporter, ID("ruby_from_blasting"));
		Recipes.MakeShapeless(RUBY, RUBY_BLOCK, 9).offerTo(exporter, ID("ruby_from_ruby_block"));
		Recipes.Make3x3(RUBY_BLOCK, RUBY).offerTo(exporter);
		Recipes.MakeStairs(RUBY_STAIRS, RUBY_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, RUBY_STAIRS, RUBY_BLOCK);
		Recipes.MakeSlab(RUBY_SLAB, RUBY_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, RUBY_SLAB, RUBY_BLOCK, 2);
		Recipes.MakeWall(RUBY_WALL, RUBY_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, RUBY_WALL, RUBY_BLOCK);
		//Brick
		Recipes.Make2x2(RUBY_BRICKS, RUBY_BLOCK, 4).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, RUBY_BRICKS, RUBY_BLOCK);
		Recipes.MakeStairs(RUBY_BRICK_STAIRS, RUBY_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, RUBY_BRICK_STAIRS, RUBY_BLOCK);
		OfferStonecuttingRecipe(exporter, RUBY_BRICK_STAIRS, RUBY_BRICKS);
		Recipes.MakeSlab(RUBY_BRICK_SLAB, RUBY_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, RUBY_BRICK_SLAB, RUBY_BLOCK, 2);
		OfferStonecuttingRecipe(exporter, RUBY_BRICK_SLAB, RUBY_BRICKS, 2);
		Recipes.MakeWall(RUBY_BRICK_WALL, RUBY_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, RUBY_BRICK_WALL, RUBY_BLOCK);
		OfferStonecuttingRecipe(exporter, RUBY_BRICK_WALL, RUBY_BRICKS);
		//</editor-fold>
		//<editor-fold desc="Diamond">
		Recipes.MakeSlab(DIAMOND_SLAB, Items.DIAMOND_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, DIAMOND_SLAB, Items.DIAMOND_BLOCK, 2);
		Recipes.MakeStairs(DIAMOND_STAIRS, Items.DIAMOND_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, DIAMOND_STAIRS, Items.DIAMOND_BLOCK);
		Recipes.MakeWall(DIAMOND_WALL, Items.DIAMOND_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, DIAMOND_WALL, Items.DIAMOND_BLOCK);
		//Bricks
		Recipes.Make2x2(DIAMOND_BRICKS, Items.DIAMOND_BLOCK, 4).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, DIAMOND_BRICKS, Items.DIAMOND_BLOCK);
		Recipes.MakeSlab(DIAMOND_BRICK_SLAB, DIAMOND_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, DIAMOND_BRICK_SLAB, Items.DIAMOND_BLOCK, 2);
		OfferStonecuttingRecipe(exporter, DIAMOND_BRICK_SLAB, DIAMOND_BRICKS, 2);
		Recipes.MakeStairs(DIAMOND_BRICK_STAIRS, DIAMOND_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, DIAMOND_BRICK_STAIRS, Items.DIAMOND_BLOCK);
		OfferStonecuttingRecipe(exporter, DIAMOND_BRICK_STAIRS, DIAMOND_BRICKS);
		Recipes.MakeWall(DIAMOND_BRICK_WALL, DIAMOND_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, DIAMOND_BRICK_WALL, Items.DIAMOND_BLOCK);
		OfferStonecuttingRecipe(exporter, DIAMOND_BRICK_WALL, DIAMOND_BRICKS);
		//</editor-fold>
		//<editor-fold desc="Quartz">
		Recipes.MakeShapeless(Items.QUARTZ, Items.QUARTZ_BLOCK, 4).offerTo(exporter, ID("quartz_from_quartz_block"));
		Recipes.MakeWall(SMOOTH_QUARTZ_WALL, Items.SMOOTH_QUARTZ).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, SMOOTH_QUARTZ_WALL, Items.SMOOTH_QUARTZ);
		Recipes.MakeWall(QUARTZ_WALL, Items.QUARTZ_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, QUARTZ_WALL, Items.QUARTZ_BLOCK);
		//Crystal Block
		Recipes.MakeShapeless(Items.QUARTZ, QUARTZ_CRYSTAL_BLOCK, 9).offerTo(exporter, ID("quartz_from_quartz_crystal_block"));
		Recipes.Make3x3(QUARTZ_CRYSTAL_BLOCK, Items.QUARTZ).offerTo(exporter);
		Recipes.MakeSlab(QUARTZ_CRYSTAL_SLAB, QUARTZ_CRYSTAL_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, QUARTZ_CRYSTAL_SLAB, QUARTZ_CRYSTAL_BLOCK, 2);
		Recipes.MakeStairs(QUARTZ_CRYSTAL_STAIRS, QUARTZ_CRYSTAL_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, QUARTZ_CRYSTAL_STAIRS, QUARTZ_CRYSTAL_BLOCK);
		Recipes.MakeWall(QUARTZ_CRYSTAL_WALL, QUARTZ_CRYSTAL_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, QUARTZ_CRYSTAL_WALL, QUARTZ_CRYSTAL_BLOCK);
		//Bricks
		Recipes.MakeSlab(QUARTZ_BRICK_SLAB, Items.QUARTZ_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, QUARTZ_BRICK_SLAB, Items.QUARTZ_BLOCK, 2);
		OfferStonecuttingRecipe(exporter, QUARTZ_BRICK_SLAB, Items.QUARTZ_BRICKS, 2);
		Recipes.MakeStairs(QUARTZ_BRICK_STAIRS, Items.QUARTZ_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, QUARTZ_BRICK_STAIRS, Items.QUARTZ_BLOCK);
		OfferStonecuttingRecipe(exporter, QUARTZ_BRICK_STAIRS, Items.QUARTZ_BRICKS);
		Recipes.MakeWall(QUARTZ_BRICK_WALL, Items.QUARTZ_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, QUARTZ_BRICK_WALL, Items.QUARTZ_BLOCK);
		OfferStonecuttingRecipe(exporter, QUARTZ_BRICK_WALL, Items.QUARTZ_BRICKS);
		//Armor & Tools
		Recipes.MakeAxe(QUARTZ_AXE, Items.QUARTZ).offerTo(exporter);
		Recipes.MakeHammer(QUARTZ_HAMMER, QUARTZ_CRYSTAL_BLOCK).offerTo(exporter);
		Recipes.MakeHoe(QUARTZ_HOE, Items.QUARTZ).offerTo(exporter);
		Recipes.MakeKnife(QUARTZ_KNIFE, Items.QUARTZ).offerTo(exporter);
		Recipes.MakePickaxe(QUARTZ_PICKAXE, Items.QUARTZ).offerTo(exporter);
		Recipes.MakeShovel(QUARTZ_SHOVEL, Items.QUARTZ).offerTo(exporter);
		Recipes.MakeSword(QUARTZ_SWORD, Items.QUARTZ).offerTo(exporter);
		Recipes.MakeHelmet(QUARTZ_HELMET, Items.QUARTZ).offerTo(exporter);
		Recipes.MakeChestplate(QUARTZ_CHESTPLATE, Items.QUARTZ).offerTo(exporter);
		Recipes.MakeLeggings(QUARTZ_LEGGINGS, Items.QUARTZ).offerTo(exporter);
		Recipes.MakeBoots(QUARTZ_BOOTS, Items.QUARTZ).offerTo(exporter);
		Recipes.MakeHorseArmor(QUARTZ_HORSE_ARMOR, Items.QUARTZ).offerTo(exporter);
		//</editor-fold>
		//<editor-fold desc="Iron">
		Recipes.Make1x3(IRON_ROD, Items.IRON_NUGGET).offerTo(exporter);
		Recipes.MakeTorch(IRON_TORCH, IRON_ROD, 8).offerTo(exporter);
		Recipes.MakeSoulTorch(IRON_SOUL_TORCH, IRON_ROD, 8).offerTo(exporter);
		Recipes.MakeEnderTorch(IRON_ENDER_TORCH, IRON_ROD, 8).offerTo(exporter);
		Recipes.MakeUnderwaterTorch(UNDERWATER_IRON_TORCH, IRON_ROD, 8).offerTo(exporter);
		Recipes.MakeShapeless(IRON_LANTERN, Items.LANTERN).input(Items.WHITE_DYE).offerTo(exporter, ID("white_iron_lantern_from_lantern"));
		Recipes.MakeShapeless(Items.LANTERN, IRON_LANTERN).input(Items.BLACK_DYE).offerTo(exporter, ID("lantern_from_white_iron_lantern"));
		Recipes.MakeShapeless(IRON_SOUL_LANTERN, Items.SOUL_LANTERN).input(Items.WHITE_DYE).offerTo(exporter, ID("white_iron_soul_lantern_from_soul_lantern"));
		Recipes.MakeShapeless(Items.SOUL_LANTERN, IRON_SOUL_LANTERN).input(Items.BLACK_DYE).offerTo(exporter, ID("soul_lantern_from_white_iron_soul_lantern"));
		Recipes.MakeShapeless(IRON_ENDER_LANTERN, ENDER_LANTERN).input(Items.WHITE_DYE).offerTo(exporter, ID("white_iron_ender_lantern_from_ender_lantern"));
		Recipes.MakeShapeless(ENDER_LANTERN, IRON_ENDER_LANTERN).input(Items.BLACK_DYE).offerTo(exporter, ID("ender_lantern_from_white_iron_ender_lantern"));
		Recipes.Make2x2(IRON_BUTTON, Items.IRON_NUGGET).offerTo(exporter);
		Recipes.MakeShapeless(IRON_CHAIN, Items.CHAIN).input(Items.WHITE_DYE).offerTo(exporter, ID("white_iron_chain_from_chain"));
		Recipes.MakeShapeless(Items.CHAIN, IRON_CHAIN).input(Items.BLACK_DYE).offerTo(exporter, ID("chain_from_white_iron_chain"));
		Recipes.Make1x3(HEAVY_IRON_CHAIN, IRON_CHAIN).offerTo(exporter);
		Recipes.MakeStairs(IRON_STAIRS, Items.IRON_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, IRON_STAIRS, Items.IRON_BLOCK);
		Recipes.MakeSlab(IRON_SLAB, Items.IRON_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, IRON_SLAB, Items.IRON_BLOCK, 2);
		Recipes.MakeWall(IRON_WALL, Items.IRON_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, IRON_WALL, Items.IRON_BLOCK);
		//Bricks
		Recipes.Make2x2(IRON_BRICKS, Items.IRON_BLOCK, 4).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, IRON_BRICKS, Items.IRON_BLOCK);
		Recipes.MakeSlab(IRON_BRICK_SLAB, IRON_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, IRON_BRICK_SLAB, Items.IRON_BLOCK, 2);
		OfferStonecuttingRecipe(exporter, IRON_BRICK_SLAB, IRON_BRICKS, 2);
		Recipes.MakeStairs(IRON_BRICK_STAIRS, IRON_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, IRON_BRICK_STAIRS, Items.IRON_BLOCK);
		OfferStonecuttingRecipe(exporter, IRON_BRICK_STAIRS, IRON_BRICKS);
		Recipes.MakeWall(IRON_BRICK_WALL, IRON_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, IRON_BRICK_WALL, Items.IRON_BLOCK);
		OfferStonecuttingRecipe(exporter, IRON_BRICK_WALL, IRON_BRICKS);
		Recipes.Make2x2(CUT_IRON, Items.IRON_BLOCK, 4).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, CUT_IRON, Items.IRON_BLOCK, 4);
		Recipes.Make2x2(CUT_IRON_PILLAR, CUT_IRON, 4).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, CUT_IRON_PILLAR, CUT_IRON);
		Recipes.MakeSlab(CUT_IRON_SLAB, CUT_IRON).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, CUT_IRON_SLAB, CUT_IRON, 2);
		OfferStonecuttingRecipe(exporter, CUT_IRON_SLAB, Items.IRON_BLOCK, 8);
		Recipes.MakeStairs(CUT_IRON_STAIRS, CUT_IRON).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, CUT_IRON_STAIRS, CUT_IRON);
		OfferStonecuttingRecipe(exporter, CUT_IRON_STAIRS, Items.IRON_BLOCK, 4);
		Recipes.MakeWall(CUT_IRON_WALL, CUT_IRON).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, CUT_IRON_WALL, CUT_IRON);
		OfferStonecuttingRecipe(exporter, CUT_IRON_WALL, Items.IRON_BLOCK, 4);
		//</editor-fold>
		//<editor-fold desc="Gold">
		Recipes.Make1x3(GOLD_ROD, Items.GOLD_NUGGET).offerTo(exporter);
		Recipes.MakeTorch(GOLD_TORCH, GOLD_ROD, 8).offerTo(exporter);
		Recipes.MakeSoulTorch(GOLD_SOUL_TORCH, GOLD_ROD, 8).offerTo(exporter);
		Recipes.MakeEnderTorch(GOLD_ENDER_TORCH, GOLD_ROD, 8).offerTo(exporter);
		Recipes.MakeUnderwaterTorch(UNDERWATER_GOLD_TORCH, GOLD_ROD, 8).offerTo(exporter);
		Recipes.MakeLantern(GOLD_LANTERN, Items.GOLD_NUGGET, ModItemTags.TORCHES).offerTo(exporter);
		Recipes.MakeLantern(GOLD_SOUL_LANTERN, Items.GOLD_NUGGET, ModItemTags.SOUL_TORCHES).offerTo(exporter);
		Recipes.MakeLantern(GOLD_ENDER_LANTERN, Items.GOLD_NUGGET, ModItemTags.ENDER_TORCHES).offerTo(exporter);
		Recipes.Make2x2(GOLD_BUTTON, Items.GOLD_NUGGET).offerTo(exporter);
		Recipes.MakeChain(GOLD_CHAIN, Items.GOLD_INGOT, Items.GOLD_NUGGET).offerTo(exporter);
		Recipes.Make1x3(HEAVY_GOLD_CHAIN, GOLD_CHAIN).offerTo(exporter);
		Recipes.Make3x2(GOLD_BARS, Items.GOLD_INGOT).offerTo(exporter);
		Recipes.MakeStairs(GOLD_STAIRS, Items.GOLD_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, GOLD_STAIRS, Items.GOLD_BLOCK);
		Recipes.MakeSlab(GOLD_SLAB, Items.GOLD_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, GOLD_SLAB, Items.GOLD_BLOCK, 2);
		Recipes.MakeWall(GOLD_WALL, Items.GOLD_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, GOLD_WALL, Items.GOLD_BLOCK);
		//Bricks
		Recipes.Make2x2(GOLD_BRICKS, Items.GOLD_BLOCK, 4).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, GOLD_BRICKS, Items.GOLD_BLOCK);
		Recipes.MakeSlab(GOLD_BRICK_SLAB, GOLD_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, GOLD_BRICK_SLAB, Items.GOLD_BLOCK, 2);
		OfferStonecuttingRecipe(exporter, GOLD_BRICK_SLAB, GOLD_BRICKS, 2);
		Recipes.MakeStairs(GOLD_BRICK_STAIRS, GOLD_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, GOLD_BRICK_STAIRS, Items.GOLD_BLOCK);
		OfferStonecuttingRecipe(exporter, GOLD_BRICK_STAIRS, GOLD_BRICKS);
		Recipes.MakeWall(GOLD_BRICK_WALL, GOLD_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, GOLD_BRICK_WALL, Items.GOLD_BLOCK);
		OfferStonecuttingRecipe(exporter, GOLD_BRICK_WALL, GOLD_BRICKS);
		Recipes.Make2x2(CUT_GOLD, Items.GOLD_BLOCK, 4).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, CUT_GOLD, Items.GOLD_BLOCK, 4);
		Recipes.Make2x2(CUT_GOLD_PILLAR, CUT_GOLD, 4).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, CUT_GOLD_PILLAR, CUT_GOLD);
		Recipes.MakeSlab(CUT_GOLD_SLAB, CUT_GOLD).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, CUT_GOLD_SLAB, CUT_GOLD, 2);
		OfferStonecuttingRecipe(exporter, CUT_GOLD_SLAB, Items.GOLD_BLOCK, 8);
		Recipes.MakeStairs(CUT_GOLD_STAIRS, CUT_GOLD).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, CUT_GOLD_STAIRS, CUT_GOLD);
		OfferStonecuttingRecipe(exporter, CUT_GOLD_STAIRS, Items.GOLD_BLOCK, 4);
		Recipes.MakeWall(CUT_GOLD_WALL, CUT_GOLD).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, CUT_GOLD_WALL, CUT_GOLD);
		OfferStonecuttingRecipe(exporter, CUT_GOLD_WALL, Items.GOLD_BLOCK, 4);
		Recipes.Make2x2(GOLD_TRAPDOOR, Items.GOLD_INGOT).offerTo(exporter);
		Recipes.MakeShears(GOLDEN_SHEARS, Items.GOLD_INGOT).offerTo(exporter);
		Recipes.MakeShaped(GOLDEN_BUCKET, Items.GOLD_INGOT).pattern("# #").pattern(" # ").offerTo(exporter);
		//</editor-fold>
		//<editor-fold desc="Copper">
		for (Map.Entry<Item, Item> item : OxidationScale.WAXED_ITEMS.entrySet()) {
			Item unwaxed = item.getValue(), waxed = item.getKey();
			Recipes.MakeShapeless(unwaxed, waxed).input(Items.HONEYCOMB)
					.offerTo(exporter, ID(RecipeGenerator.convertBetween(unwaxed, waxed) + "_waxing"));
		}
		Recipes.MakeShapeless(COPPER_NUGGET, Items.COPPER_INGOT, 9).offerTo(exporter, ID("copper_nugget_from_copper_ingot"));
		Recipes.Make3x3(Items.COPPER_INGOT, COPPER_NUGGET).offerTo(exporter);
		Recipes.Make1x3(COPPER_ROD, COPPER_NUGGET).offerTo(exporter);
		Recipes.MakeTorch(COPPER_TORCH, COPPER_ROD, 8).offerTo(exporter);
		Recipes.MakeSoulTorch(COPPER_SOUL_TORCH, COPPER_ROD, 8).offerTo(exporter);
		Recipes.MakeEnderTorch(COPPER_ENDER_TORCH, COPPER_ROD, 8).offerTo(exporter);
		Recipes.MakeUnderwaterTorch(UNDERWATER_COPPER_TORCH, COPPER_ROD, 8).offerTo(exporter);
		Recipes.MakeLantern(COPPER_LANTERN, COPPER_NUGGET, ModItemTags.TORCHES).offerTo(exporter);
		Recipes.MakeLantern(COPPER_SOUL_LANTERN, COPPER_NUGGET, ModItemTags.SOUL_TORCHES).offerTo(exporter);
		Recipes.MakeLantern(COPPER_ENDER_LANTERN, COPPER_NUGGET, ModItemTags.ENDER_TORCHES).offerTo(exporter);
		Recipes.Make2x2(COPPER_BUTTON, COPPER_NUGGET).offerTo(exporter);
		Recipes.MakeChain(COPPER_CHAIN, Items.COPPER_INGOT, COPPER_NUGGET).offerTo(exporter);
		Recipes.Make1x3(HEAVY_COPPER_CHAIN, COPPER_CHAIN).offerTo(exporter);
		Recipes.Make1x3(EXPOSED_HEAVY_COPPER_CHAIN, EXPOSED_COPPER_CHAIN).offerTo(exporter);
		Recipes.Make1x3(WEATHERED_HEAVY_COPPER_CHAIN, WEATHERED_COPPER_CHAIN).offerTo(exporter);
		Recipes.Make1x3(OXIDIZED_HEAVY_COPPER_CHAIN, OXIDIZED_COPPER_CHAIN).offerTo(exporter);
		Recipes.Make1x3(WAXED_HEAVY_COPPER_CHAIN, WAXED_COPPER_CHAIN).offerTo(exporter);
		Recipes.Make1x3(WAXED_EXPOSED_HEAVY_COPPER_CHAIN, WAXED_EXPOSED_COPPER_CHAIN).offerTo(exporter);
		Recipes.Make1x3(WAXED_WEATHERED_HEAVY_COPPER_CHAIN, WAXED_WEATHERED_COPPER_CHAIN).offerTo(exporter);
		Recipes.Make1x3(WAXED_OXIDIZED_HEAVY_COPPER_CHAIN, WAXED_OXIDIZED_COPPER_CHAIN).offerTo(exporter);
		Recipes.Make3x2(COPPER_BARS, Items.COPPER_INGOT).offerTo(exporter);

		Recipes.Make2x2(CUT_COPPER_PILLAR, Items.CUT_COPPER, 4).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, CUT_COPPER_PILLAR, Items.COPPER_BLOCK, 4);
		OfferStonecuttingRecipe(exporter, CUT_COPPER_PILLAR, Items.CUT_COPPER);
		Recipes.Make2x2(EXPOSED_CUT_COPPER_PILLAR, Items.EXPOSED_CUT_COPPER, 4).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, EXPOSED_CUT_COPPER_PILLAR, Items.EXPOSED_COPPER, 4);
		OfferStonecuttingRecipe(exporter, EXPOSED_CUT_COPPER_PILLAR, Items.EXPOSED_CUT_COPPER);
		Recipes.Make2x2(WEATHERED_CUT_COPPER_PILLAR, Items.WEATHERED_CUT_COPPER, 4).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, WEATHERED_CUT_COPPER_PILLAR, Items.WEATHERED_COPPER, 4);
		OfferStonecuttingRecipe(exporter, WEATHERED_CUT_COPPER_PILLAR, Items.WEATHERED_CUT_COPPER);
		Recipes.Make2x2(OXIDIZED_CUT_COPPER_PILLAR, Items.OXIDIZED_CUT_COPPER, 4).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, OXIDIZED_CUT_COPPER_PILLAR, Items.OXIDIZED_COPPER, 4);
		OfferStonecuttingRecipe(exporter, OXIDIZED_CUT_COPPER_PILLAR, Items.OXIDIZED_CUT_COPPER);
		Recipes.Make2x2(WAXED_CUT_COPPER_PILLAR, Items.WAXED_CUT_COPPER, 4).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, WAXED_CUT_COPPER_PILLAR, Items.WAXED_COPPER, 4);
		OfferStonecuttingRecipe(exporter, WAXED_CUT_COPPER_PILLAR, Items.WAXED_CUT_COPPER);
		Recipes.Make2x2(WAXED_EXPOSED_CUT_COPPER_PILLAR, Items.EXPOSED_CUT_COPPER, 4).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, WAXED_EXPOSED_CUT_COPPER_PILLAR, Items.WAXED_EXPOSED_COPPER, 4);
		OfferStonecuttingRecipe(exporter, WAXED_EXPOSED_CUT_COPPER_PILLAR, Items.WAXED_EXPOSED_CUT_COPPER);
		Recipes.Make2x2(WAXED_WEATHERED_CUT_COPPER_PILLAR, Items.WEATHERED_CUT_COPPER, 4).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, WAXED_WEATHERED_CUT_COPPER_PILLAR, Items.WAXED_WEATHERED_COPPER, 4);
		OfferStonecuttingRecipe(exporter, WAXED_WEATHERED_CUT_COPPER_PILLAR, Items.WAXED_WEATHERED_CUT_COPPER);
		Recipes.Make2x2(WAXED_OXIDIZED_CUT_COPPER_PILLAR, Items.WAXED_OXIDIZED_CUT_COPPER, 4).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, WAXED_OXIDIZED_CUT_COPPER_PILLAR, Items.WAXED_OXIDIZED_COPPER, 4);
		OfferStonecuttingRecipe(exporter, WAXED_OXIDIZED_CUT_COPPER_PILLAR, Items.WAXED_OXIDIZED_CUT_COPPER);

		Recipes.MakeStairs(COPPER_STAIRS, Items.COPPER_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, COPPER_STAIRS, Items.COPPER_BLOCK);
		Recipes.MakeStairs(EXPOSED_COPPER_STAIRS, Items.EXPOSED_COPPER).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, EXPOSED_COPPER_STAIRS, Items.EXPOSED_COPPER);
		Recipes.MakeStairs(WEATHERED_COPPER_STAIRS, Items.WEATHERED_COPPER).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, WEATHERED_COPPER_STAIRS, Items.WEATHERED_COPPER);
		Recipes.MakeStairs(OXIDIZED_COPPER_STAIRS, Items.OXIDIZED_COPPER).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, OXIDIZED_COPPER_STAIRS, Items.OXIDIZED_COPPER);
		Recipes.MakeStairs(WAXED_COPPER_STAIRS, Items.WAXED_COPPER).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, WAXED_COPPER_STAIRS, Items.WAXED_COPPER);
		Recipes.MakeStairs(WAXED_EXPOSED_COPPER_STAIRS, Items.WAXED_EXPOSED_COPPER).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, WAXED_EXPOSED_COPPER_STAIRS, Items.WAXED_EXPOSED_COPPER);
		Recipes.MakeStairs(WAXED_WEATHERED_COPPER_STAIRS, Items.WAXED_WEATHERED_COPPER).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, WAXED_WEATHERED_COPPER_STAIRS, Items.WAXED_WEATHERED_COPPER);
		Recipes.MakeStairs(WAXED_OXIDIZED_COPPER_STAIRS, Items.WAXED_OXIDIZED_COPPER).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, WAXED_OXIDIZED_COPPER_STAIRS, Items.WAXED_OXIDIZED_COPPER);
		Recipes.MakeSlab(COPPER_SLAB, Items.COPPER_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, EXPOSED_COPPER_SLAB, Items.COPPER_BLOCK, 2);
		Recipes.MakeSlab(EXPOSED_COPPER_SLAB, Items.EXPOSED_COPPER).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, EXPOSED_COPPER_SLAB, Items.EXPOSED_COPPER, 2);
		Recipes.MakeSlab(WEATHERED_COPPER_SLAB, Items.WEATHERED_COPPER).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, EXPOSED_COPPER_SLAB, Items.WEATHERED_COPPER, 2);
		Recipes.MakeSlab(OXIDIZED_COPPER_SLAB, Items.OXIDIZED_COPPER).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, EXPOSED_COPPER_SLAB, Items.OXIDIZED_COPPER, 2);
		Recipes.MakeSlab(WAXED_COPPER_SLAB, Items.WAXED_COPPER).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, EXPOSED_COPPER_SLAB, Items.WAXED_COPPER, 2);
		Recipes.MakeSlab(WAXED_EXPOSED_COPPER_SLAB, Items.WAXED_EXPOSED_COPPER).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, EXPOSED_COPPER_SLAB, Items.WAXED_EXPOSED_COPPER, 2);
		Recipes.MakeSlab(WAXED_WEATHERED_COPPER_SLAB, Items.WAXED_WEATHERED_COPPER).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, EXPOSED_COPPER_SLAB, Items.WAXED_WEATHERED_COPPER, 2);
		Recipes.MakeSlab(WAXED_OXIDIZED_COPPER_SLAB, Items.WAXED_OXIDIZED_COPPER).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, EXPOSED_COPPER_SLAB, Items.WAXED_OXIDIZED_COPPER, 2);
		Recipes.MakeWall(COPPER_WALL, Items.COPPER_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, COPPER_WALL, Items.COPPER_BLOCK);
		Recipes.MakeWall(EXPOSED_COPPER_WALL, Items.EXPOSED_COPPER).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, EXPOSED_COPPER_WALL, Items.EXPOSED_COPPER);
		Recipes.MakeWall(WEATHERED_COPPER_WALL, Items.WEATHERED_COPPER).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, WEATHERED_COPPER_WALL, Items.WEATHERED_COPPER);
		Recipes.MakeWall(OXIDIZED_COPPER_WALL, Items.OXIDIZED_COPPER).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, OXIDIZED_COPPER_WALL, Items.OXIDIZED_COPPER);
		Recipes.MakeWall(WAXED_COPPER_WALL, Items.WAXED_COPPER).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, WAXED_COPPER_WALL, Items.WAXED_COPPER);
		Recipes.MakeWall(WAXED_EXPOSED_COPPER_WALL, Items.WAXED_EXPOSED_COPPER).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, WAXED_EXPOSED_COPPER_WALL, Items.WAXED_EXPOSED_COPPER);
		Recipes.MakeWall(WAXED_WEATHERED_COPPER_WALL, Items.WAXED_WEATHERED_COPPER).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, WAXED_WEATHERED_COPPER_WALL, Items.WAXED_WEATHERED_COPPER);
		Recipes.MakeWall(WAXED_OXIDIZED_COPPER_WALL, Items.WAXED_OXIDIZED_COPPER).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, WAXED_OXIDIZED_COPPER_WALL, Items.WAXED_OXIDIZED_COPPER);

		Recipes.Make2x2(COPPER_BRICKS, Items.COPPER_BLOCK, 4).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, COPPER_BRICKS, Items.COPPER_BLOCK, 4);
		Recipes.Make2x2(EXPOSED_COPPER_BRICKS, Items.EXPOSED_COPPER, 4).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, EXPOSED_COPPER_BRICKS, Items.EXPOSED_COPPER, 4);
		Recipes.Make2x2(WEATHERED_COPPER_BRICKS, Items.WEATHERED_COPPER, 4).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, WEATHERED_COPPER_BRICKS, Items.WEATHERED_COPPER, 4);
		Recipes.Make2x2(OXIDIZED_COPPER_BRICKS, Items.OXIDIZED_COPPER, 4).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, OXIDIZED_COPPER_BRICKS, Items.OXIDIZED_COPPER, 4);
		Recipes.Make2x2(WAXED_COPPER_BRICKS, Items.WAXED_COPPER, 4).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, WAXED_COPPER_BRICKS, Items.WAXED_COPPER, 4);
		Recipes.Make2x2(WAXED_EXPOSED_COPPER_BRICKS, Items.WAXED_EXPOSED_COPPER, 4).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, WAXED_EXPOSED_COPPER_BRICKS, Items.WAXED_EXPOSED_COPPER, 4);
		Recipes.Make2x2(WAXED_WEATHERED_COPPER_BRICKS, Items.WAXED_WEATHERED_COPPER, 4).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, WAXED_WEATHERED_COPPER_BRICKS, Items.WAXED_WEATHERED_COPPER, 4);
		Recipes.Make2x2(WAXED_OXIDIZED_COPPER_BRICKS, Items.WAXED_OXIDIZED_COPPER, 4).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, WAXED_OXIDIZED_COPPER_BRICKS, Items.WAXED_OXIDIZED_COPPER, 4);
		Recipes.MakeStairs(COPPER_BRICK_STAIRS, COPPER_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, COPPER_BRICK_STAIRS, Items.COPPER_BLOCK, 4);
		OfferStonecuttingRecipe(exporter, COPPER_BRICK_STAIRS, COPPER_BRICKS);
		Recipes.MakeStairs(EXPOSED_COPPER_BRICK_STAIRS, EXPOSED_COPPER_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, EXPOSED_COPPER_BRICK_STAIRS, Items.EXPOSED_COPPER, 4);
		OfferStonecuttingRecipe(exporter, EXPOSED_COPPER_BRICK_STAIRS, EXPOSED_COPPER_BRICKS);
		Recipes.MakeStairs(WEATHERED_COPPER_BRICK_STAIRS, WEATHERED_COPPER_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, WEATHERED_COPPER_BRICK_STAIRS, Items.WEATHERED_COPPER, 4);
		OfferStonecuttingRecipe(exporter, WEATHERED_COPPER_BRICK_STAIRS, WEATHERED_COPPER_BRICKS);
		Recipes.MakeStairs(OXIDIZED_COPPER_BRICK_STAIRS, OXIDIZED_COPPER_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, OXIDIZED_COPPER_BRICK_STAIRS, Items.OXIDIZED_COPPER, 4);
		OfferStonecuttingRecipe(exporter, OXIDIZED_COPPER_BRICK_STAIRS, OXIDIZED_COPPER_BRICKS);
		Recipes.MakeStairs(WAXED_COPPER_BRICK_STAIRS, WAXED_COPPER_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, WAXED_COPPER_BRICK_STAIRS, Items.WAXED_COPPER, 4);
		OfferStonecuttingRecipe(exporter, WAXED_COPPER_BRICK_STAIRS, WAXED_COPPER_BRICKS);
		Recipes.MakeStairs(WAXED_EXPOSED_COPPER_BRICK_STAIRS, WAXED_EXPOSED_COPPER_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, WAXED_EXPOSED_COPPER_BRICK_STAIRS, Items.WAXED_EXPOSED_COPPER, 4);
		OfferStonecuttingRecipe(exporter, WAXED_EXPOSED_COPPER_BRICK_STAIRS, WAXED_EXPOSED_COPPER_BRICKS);
		Recipes.MakeStairs(WAXED_WEATHERED_COPPER_BRICK_STAIRS, WAXED_WEATHERED_COPPER_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, WAXED_WEATHERED_COPPER_BRICK_STAIRS, Items.WAXED_WEATHERED_COPPER, 4);
		OfferStonecuttingRecipe(exporter, WAXED_WEATHERED_COPPER_BRICK_STAIRS, WAXED_WEATHERED_COPPER_BRICKS);
		Recipes.MakeStairs(WAXED_OXIDIZED_COPPER_BRICK_STAIRS, WAXED_OXIDIZED_COPPER_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, WAXED_OXIDIZED_COPPER_BRICK_STAIRS, Items.WAXED_OXIDIZED_COPPER, 4);
		OfferStonecuttingRecipe(exporter, WAXED_OXIDIZED_COPPER_BRICK_STAIRS, WAXED_OXIDIZED_COPPER_BRICKS);
		Recipes.MakeSlab(COPPER_BRICK_SLAB, COPPER_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, EXPOSED_COPPER_BRICK_SLAB, Items.COPPER_BLOCK, 8);
		OfferStonecuttingRecipe(exporter, COPPER_BRICK_SLAB, COPPER_BRICKS, 2);
		Recipes.MakeSlab(EXPOSED_COPPER_BRICK_SLAB, EXPOSED_COPPER_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, EXPOSED_COPPER_BRICK_SLAB, Items.EXPOSED_COPPER, 8);
		OfferStonecuttingRecipe(exporter, EXPOSED_COPPER_BRICK_SLAB, EXPOSED_COPPER_BRICKS, 2);
		Recipes.MakeSlab(WEATHERED_COPPER_BRICK_SLAB, WEATHERED_COPPER_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, EXPOSED_COPPER_BRICK_SLAB, Items.WEATHERED_COPPER, 8);
		OfferStonecuttingRecipe(exporter, WEATHERED_COPPER_BRICK_SLAB, WEATHERED_COPPER_BRICKS, 2);
		Recipes.MakeSlab(OXIDIZED_COPPER_BRICK_SLAB, OXIDIZED_COPPER_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, EXPOSED_COPPER_BRICK_SLAB, Items.OXIDIZED_COPPER, 8);
		OfferStonecuttingRecipe(exporter, OXIDIZED_COPPER_BRICK_SLAB, OXIDIZED_COPPER_BRICKS, 2);
		Recipes.MakeSlab(WAXED_COPPER_BRICK_SLAB, WAXED_COPPER_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, EXPOSED_COPPER_BRICK_SLAB, Items.WAXED_COPPER, 8);
		OfferStonecuttingRecipe(exporter, WAXED_COPPER_BRICK_SLAB, WAXED_COPPER_BRICKS, 2);
		Recipes.MakeSlab(WAXED_EXPOSED_COPPER_BRICK_SLAB, WAXED_EXPOSED_COPPER_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, EXPOSED_COPPER_BRICK_SLAB, Items.WAXED_EXPOSED_COPPER, 8);
		OfferStonecuttingRecipe(exporter, WAXED_EXPOSED_COPPER_BRICK_SLAB, WAXED_EXPOSED_COPPER_BRICKS, 2);
		Recipes.MakeSlab(WAXED_WEATHERED_COPPER_BRICK_SLAB, WAXED_WEATHERED_COPPER_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, EXPOSED_COPPER_BRICK_SLAB, Items.WAXED_WEATHERED_COPPER, 8);
		OfferStonecuttingRecipe(exporter, WAXED_WEATHERED_COPPER_BRICK_SLAB, WAXED_WEATHERED_COPPER_BRICKS, 2);
		Recipes.MakeSlab(WAXED_OXIDIZED_COPPER_BRICK_SLAB, WAXED_OXIDIZED_COPPER_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, EXPOSED_COPPER_BRICK_SLAB, Items.WAXED_OXIDIZED_COPPER, 8);
		OfferStonecuttingRecipe(exporter, WAXED_OXIDIZED_COPPER_BRICK_SLAB, WAXED_OXIDIZED_COPPER_BRICKS, 2);
		Recipes.MakeWall(COPPER_BRICK_WALL, COPPER_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, COPPER_BRICK_WALL, Items.COPPER_BLOCK, 4);
		OfferStonecuttingRecipe(exporter, COPPER_BRICK_WALL, COPPER_BRICKS);
		Recipes.MakeWall(EXPOSED_COPPER_BRICK_WALL, EXPOSED_COPPER_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, EXPOSED_COPPER_BRICK_WALL, Items.EXPOSED_COPPER, 4);
		OfferStonecuttingRecipe(exporter, EXPOSED_COPPER_BRICK_WALL, EXPOSED_COPPER_BRICKS);
		Recipes.MakeWall(WEATHERED_COPPER_BRICK_WALL, WEATHERED_COPPER_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, WEATHERED_COPPER_BRICK_WALL, Items.WEATHERED_COPPER, 4);
		OfferStonecuttingRecipe(exporter, WEATHERED_COPPER_BRICK_WALL, WEATHERED_COPPER_BRICKS);
		Recipes.MakeWall(OXIDIZED_COPPER_BRICK_WALL, OXIDIZED_COPPER_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, OXIDIZED_COPPER_BRICK_WALL, Items.OXIDIZED_COPPER, 4);
		OfferStonecuttingRecipe(exporter, OXIDIZED_COPPER_BRICK_WALL, OXIDIZED_COPPER_BRICKS);
		Recipes.MakeWall(WAXED_COPPER_BRICK_WALL, WAXED_COPPER_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, WAXED_COPPER_BRICK_WALL, Items.WAXED_COPPER, 4);
		OfferStonecuttingRecipe(exporter, WAXED_COPPER_BRICK_WALL, WAXED_COPPER_BRICKS);
		Recipes.MakeWall(WAXED_EXPOSED_COPPER_BRICK_WALL, WAXED_EXPOSED_COPPER_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, WAXED_EXPOSED_COPPER_BRICK_WALL, Items.WAXED_EXPOSED_COPPER, 4);
		OfferStonecuttingRecipe(exporter, WAXED_EXPOSED_COPPER_BRICK_WALL, WAXED_EXPOSED_COPPER_BRICKS);
		Recipes.MakeWall(WAXED_WEATHERED_COPPER_BRICK_WALL, WAXED_WEATHERED_COPPER_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, WAXED_WEATHERED_COPPER_BRICK_WALL, Items.WAXED_WEATHERED_COPPER, 4);
		OfferStonecuttingRecipe(exporter, WAXED_WEATHERED_COPPER_BRICK_WALL, WAXED_WEATHERED_COPPER_BRICKS);
		Recipes.MakeWall(WAXED_OXIDIZED_COPPER_BRICK_WALL, WAXED_OXIDIZED_COPPER_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, WAXED_OXIDIZED_COPPER_BRICK_WALL, Items.WAXED_OXIDIZED_COPPER, 4);
		OfferStonecuttingRecipe(exporter, WAXED_OXIDIZED_COPPER_BRICK_WALL, WAXED_OXIDIZED_COPPER_BRICKS);
		Recipes.MakePressurePlate(MEDIUM_WEIGHTED_PRESSURE_PLATE, Items.COPPER_INGOT).offerTo(exporter);

		Recipes.Make2x2(COPPER_TRAPDOOR, Items.COPPER_INGOT).offerTo(exporter);
		//Tools
		Recipes.MakeAxe(COPPER_AXE, Items.COPPER_INGOT).offerTo(exporter);
		Recipes.MakeHammer(COPPER_HAMMER, Items.COPPER_BLOCK).offerTo(exporter);
		Recipes.MakeHammer(EXPOSED_COPPER_HAMMER, Items.EXPOSED_COPPER).offerTo(exporter);
		Recipes.MakeHammer(WEATHERED_COPPER_HAMMER, Items.WEATHERED_COPPER).offerTo(exporter);
		Recipes.MakeHammer(OXIDIZED_COPPER_HAMMER, Items.OXIDIZED_COPPER).offerTo(exporter);
		Recipes.MakeHoe(COPPER_HOE, Items.COPPER_INGOT).offerTo(exporter);
		Recipes.MakeKnife(COPPER_KNIFE, Items.COPPER_INGOT).offerTo(exporter);
		Recipes.MakePickaxe(COPPER_PICKAXE, Items.COPPER_INGOT).offerTo(exporter);
		Recipes.MakeShears(COPPER_SHEARS, Items.COPPER_INGOT).offerTo(exporter);
		Recipes.MakeShovel(COPPER_SHOVEL, Items.COPPER_INGOT).offerTo(exporter);
		Recipes.MakeSword(COPPER_SWORD, Items.COPPER_INGOT).offerTo(exporter);
		Recipes.MakeHelmet(COPPER_HELMET, Items.COPPER_INGOT).offerTo(exporter);
		Recipes.MakeChestplate(COPPER_CHESTPLATE, Items.COPPER_INGOT).offerTo(exporter);
		Recipes.MakeLeggings(COPPER_LEGGINGS, Items.COPPER_INGOT).offerTo(exporter);
		Recipes.MakeBoots(COPPER_BOOTS, Items.COPPER_INGOT).offerTo(exporter);
		//Recipes.MakeHorseArmor(COPPER_HORSE_ARMOR, Items.COPPER_INGOT).offerTo(exporter);
		Recipes.MakeShaped(COPPER_BUCKET, Items.COPPER_INGOT).pattern("# #").pattern(" # ").offerTo(exporter);
		//</editor-fold>
		//<editor-fold desc="Raw Metal">
		Recipes.MakeSlab(RAW_COPPER_SLAB, Items.RAW_COPPER_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, RAW_COPPER_SLAB, Items.COPPER_BLOCK, 2);
		Recipes.Make1x2(Items.RAW_COPPER_BLOCK, RAW_COPPER_SLAB).offerTo(exporter, ID("raw_copper_block_from_raw_copper_slabs"));
		Recipes.MakeSlab(RAW_GOLD_SLAB, Items.RAW_GOLD_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, RAW_GOLD_SLAB, Items.GOLD_BLOCK, 2);
		Recipes.Make1x2(Items.RAW_GOLD_BLOCK, RAW_GOLD_SLAB).offerTo(exporter, ID("raw_gold_block_from_raw_gold_slabs"));
		Recipes.MakeSlab(RAW_IRON_SLAB, Items.RAW_IRON_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, RAW_IRON_SLAB, Items.IRON_BLOCK, 2);
		Recipes.Make1x2(Items.RAW_IRON_BLOCK, RAW_IRON_SLAB).offerTo(exporter, ID("raw_iron_block_from_raw_iron_slabs"));
		//</editor-fold>
		//<editor-fold desc="Netherite">
		Recipes.MakeShapeless(NETHERITE_NUGGET, Items.NETHERITE_INGOT, 9).offerTo(exporter, ID("netherite_nugget_from_netherite_ingot"));
		Recipes.Make3x3(Items.NETHERITE_INGOT, NETHERITE_NUGGET).offerTo(exporter);
		Recipes.Make1x3(NETHERITE_ROD, NETHERITE_NUGGET).offerTo(exporter);
		Recipes.MakeTorch(NETHERITE_TORCH, NETHERITE_ROD, 8).offerTo(exporter);
		Recipes.MakeSoulTorch(NETHERITE_SOUL_TORCH, NETHERITE_ROD, 8).offerTo(exporter);
		Recipes.MakeEnderTorch(NETHERITE_ENDER_TORCH, NETHERITE_ROD, 8).offerTo(exporter);
		Recipes.MakeUnderwaterTorch(UNDERWATER_NETHERITE_TORCH, NETHERITE_ROD, 8).offerTo(exporter);
		Recipes.MakeLantern(NETHERITE_LANTERN, NETHERITE_NUGGET, ModItemTags.TORCHES).offerTo(exporter);
		Recipes.MakeLantern(NETHERITE_SOUL_LANTERN, NETHERITE_NUGGET, ModItemTags.SOUL_TORCHES).offerTo(exporter);
		Recipes.MakeLantern(NETHERITE_ENDER_LANTERN, NETHERITE_NUGGET, ModItemTags.ENDER_TORCHES).offerTo(exporter);
		Recipes.Make2x2(NETHERITE_BUTTON, NETHERITE_NUGGET).offerTo(exporter);
		Recipes.MakeChain(NETHERITE_CHAIN, Items.NETHERITE_INGOT, NETHERITE_NUGGET).offerTo(exporter);
		Recipes.Make1x3(HEAVY_NETHERITE_CHAIN, NETHERITE_CHAIN).offerTo(exporter);
		Recipes.Make3x2(NETHERITE_BARS, Items.NETHERITE_INGOT).offerTo(exporter);
		Recipes.MakeStairs(NETHERITE_STAIRS, Items.NETHERITE_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, NETHERITE_STAIRS, Items.NETHERITE_BLOCK);
		Recipes.MakeSlab(NETHERITE_SLAB, Items.NETHERITE_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, NETHERITE_SLAB, Items.NETHERITE_BLOCK, 2);
		Recipes.MakeWall(NETHERITE_WALL, Items.NETHERITE_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, NETHERITE_WALL, Items.NETHERITE_BLOCK);
		//Bricks
		Recipes.Make2x2(NETHERITE_BRICKS, Items.NETHERITE_BLOCK, 4).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, NETHERITE_BRICKS, Items.NETHERITE_BLOCK);
		Recipes.MakeSlab(NETHERITE_BRICK_SLAB, NETHERITE_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, NETHERITE_BRICK_SLAB, Items.NETHERITE_BLOCK, 2);
		OfferStonecuttingRecipe(exporter, NETHERITE_BRICK_SLAB, NETHERITE_BRICKS, 2);
		Recipes.MakeStairs(NETHERITE_BRICK_STAIRS, NETHERITE_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, NETHERITE_BRICK_STAIRS, Items.NETHERITE_BLOCK);
		OfferStonecuttingRecipe(exporter, NETHERITE_BRICK_STAIRS, NETHERITE_BRICKS);
		Recipes.MakeWall(NETHERITE_BRICK_WALL, NETHERITE_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, NETHERITE_BRICK_WALL, Items.NETHERITE_BLOCK);
		OfferStonecuttingRecipe(exporter, NETHERITE_BRICK_WALL, NETHERITE_BRICKS);
		Recipes.Make2x2(CUT_NETHERITE, Items.NETHERITE_BLOCK, 4).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, CUT_NETHERITE, Items.NETHERITE_BLOCK, 4);
		Recipes.Make2x2(CUT_NETHERITE_PILLAR, CUT_NETHERITE, 4).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, CUT_NETHERITE_PILLAR, CUT_NETHERITE);
		Recipes.MakeSlab(CUT_NETHERITE_SLAB, CUT_NETHERITE).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, CUT_NETHERITE_SLAB, CUT_NETHERITE, 2);
		OfferStonecuttingRecipe(exporter, CUT_NETHERITE_SLAB, Items.NETHERITE_BLOCK, 8);
		Recipes.MakeStairs(CUT_NETHERITE_STAIRS, CUT_NETHERITE).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, CUT_NETHERITE_STAIRS, CUT_NETHERITE);
		OfferStonecuttingRecipe(exporter, CUT_NETHERITE_STAIRS, Items.NETHERITE_BLOCK, 4);
		Recipes.MakeWall(CUT_NETHERITE_WALL, CUT_NETHERITE).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, CUT_NETHERITE_WALL, CUT_NETHERITE);
		OfferStonecuttingRecipe(exporter, CUT_NETHERITE_WALL, Items.NETHERITE_BLOCK, 4);
		Recipes.Make2x2(NETHERITE_TRAPDOOR, Items.NETHERITE_INGOT).offerTo(exporter);
		Recipes.MakeShears(NETHERITE_SHEARS, Items.NETHERITE_INGOT).offerTo(exporter);
		Recipes.MakeHorseArmor(NETHERITE_HORSE_ARMOR, Items.NETHERITE_INGOT).offerTo(exporter);
		Recipes.MakePressurePlate(CRUSHING_WEIGHTED_PRESSURE_PLATE, Items.NETHERITE_INGOT).offerTo(exporter);
		Recipes.MakeShaped(NETHERITE_BUCKET, Items.NETHERITE_INGOT).pattern("# #").pattern(" # ").offerTo(exporter);
		//</editor-fold>
		//<editor-fold desc="Bone">
		Recipes.MakeTorch(BONE_TORCH, Items.BONE, 4).offerTo(exporter);
		Recipes.MakeSoulTorch(BONE_SOUL_TORCH, Items.BONE, 4).offerTo(exporter);
		Recipes.MakeEnderTorch(BONE_ENDER_TORCH, Items.BONE, 4).offerTo(exporter);
		Recipes.MakeUnderwaterTorch(UNDERWATER_BONE_TORCH, Items.BONE, 4).offerTo(exporter);
		Recipes.MakeLadder(BONE_LADDER, Items.BONE).offerTo(exporter);
		Recipes.MakeSlab(BONE_SLAB, Items.BONE_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, BONE_SLAB, Items.BONE_BLOCK, 2);
		Recipes.Make3x2(BONE_ROW, Items.BONE_BLOCK, 16).offerTo(exporter);
		Recipes.Make2x2(Items.BONE_MEAL, BONE_SHARD).offerTo(exporter, ID("bone_meal_from_bone_shards"));
		Recipes.MakeHammer(BONE_HAMMER, Items.BONE_BLOCK).offerTo(exporter);
		Recipes.MakeKnife(BONE_KNIFE, BONE_SHARD).offerTo(exporter);
		//</editor-fold>
		//<editor-fold desc="Blackstone">
		OfferStonecuttingRecipe(exporter, SMOOTH_CHISELED_POLISHED_BLACKSTONE, Items.CHISELED_POLISHED_BLACKSTONE);
		Recipes.Make2x2(POLISHED_BLACKSTONE_TILES, Items.POLISHED_BLACKSTONE_BRICKS, 4).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, POLISHED_BLACKSTONE_TILES, Items.POLISHED_BLACKSTONE);
		OfferStonecuttingRecipe(exporter, POLISHED_BLACKSTONE_TILES, Items.POLISHED_BLACKSTONE_BRICKS);
		Recipes.MakeStairs(POLISHED_BLACKSTONE_TILE_STAIRS, POLISHED_BLACKSTONE_TILES).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, POLISHED_BLACKSTONE_TILE_STAIRS, Items.POLISHED_BLACKSTONE);
		OfferStonecuttingRecipe(exporter, POLISHED_BLACKSTONE_TILE_STAIRS, Items.POLISHED_BLACKSTONE_BRICKS);
		OfferStonecuttingRecipe(exporter, POLISHED_BLACKSTONE_TILE_STAIRS, POLISHED_BLACKSTONE_TILES);
		Recipes.MakeSlab(POLISHED_BLACKSTONE_TILE_SLAB, POLISHED_BLACKSTONE_TILES).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, POLISHED_BLACKSTONE_TILE_SLAB, Items.POLISHED_BLACKSTONE, 2);
		OfferStonecuttingRecipe(exporter, POLISHED_BLACKSTONE_TILE_SLAB, Items.POLISHED_BLACKSTONE_BRICKS, 2);
		OfferStonecuttingRecipe(exporter, POLISHED_BLACKSTONE_TILE_SLAB, POLISHED_BLACKSTONE_TILES, 2);
		Recipes.MakeWall(POLISHED_BLACKSTONE_TILE_WALL, POLISHED_BLACKSTONE_TILES).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, POLISHED_BLACKSTONE_TILE_WALL, Items.POLISHED_BLACKSTONE);
		OfferStonecuttingRecipe(exporter, POLISHED_BLACKSTONE_TILE_WALL, Items.POLISHED_BLACKSTONE_BRICKS);
		OfferStonecuttingRecipe(exporter, POLISHED_BLACKSTONE_TILE_WALL, POLISHED_BLACKSTONE_TILES);
		//</editor-fold>
		//<editor-fold desc="Gilded Blackstone">
		OfferSmithingRecipe(exporter, Items.GILDED_BLACKSTONE, Items.BLACKSTONE, Items.GOLD_INGOT);
		Recipes.MakeSlab(GILDED_BLACKSTONE_SLAB, Items.GILDED_BLACKSTONE).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, GILDED_BLACKSTONE_SLAB, Items.GILDED_BLACKSTONE, 2);
		OfferSmithingRecipe(exporter, GILDED_BLACKSTONE_SLAB, Items.BLACKSTONE_SLAB, Items.GOLD_INGOT);
		Recipes.MakeStairs(GILDED_BLACKSTONE_STAIRS, Items.GILDED_BLACKSTONE).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, GILDED_BLACKSTONE_STAIRS, Items.GILDED_BLACKSTONE);
		OfferSmithingRecipe(exporter, GILDED_BLACKSTONE_STAIRS, Items.BLACKSTONE_STAIRS, Items.GOLD_INGOT);
		Recipes.MakeWall(GILDED_BLACKSTONE_WALL, Items.GILDED_BLACKSTONE).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, GILDED_BLACKSTONE_WALL, Items.GILDED_BLACKSTONE);
		OfferSmithingRecipe(exporter, GILDED_BLACKSTONE_WALL, Items.BLACKSTONE_WALL, Items.GOLD_INGOT);
		//Polished
		Recipes.Make2x2(POLISHED_GILDED_BLACKSTONE, Items.GILDED_BLACKSTONE, 4).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, POLISHED_GILDED_BLACKSTONE, Items.GILDED_BLACKSTONE);
		OfferSmithingRecipe(exporter, POLISHED_GILDED_BLACKSTONE, Items.POLISHED_BLACKSTONE, Items.GOLD_INGOT);
		Recipes.MakeSlab(POLISHED_GILDED_BLACKSTONE_SLAB, POLISHED_GILDED_BLACKSTONE).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, POLISHED_GILDED_BLACKSTONE_SLAB, POLISHED_GILDED_BLACKSTONE, 2);
		OfferSmithingRecipe(exporter, POLISHED_GILDED_BLACKSTONE_SLAB, Items.POLISHED_BLACKSTONE_SLAB, Items.GOLD_INGOT);
		Recipes.MakeStairs(POLISHED_GILDED_BLACKSTONE_STAIRS, POLISHED_GILDED_BLACKSTONE).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, POLISHED_GILDED_BLACKSTONE_STAIRS, POLISHED_GILDED_BLACKSTONE);
		OfferSmithingRecipe(exporter, POLISHED_GILDED_BLACKSTONE_STAIRS, Items.POLISHED_BLACKSTONE_STAIRS, Items.GOLD_INGOT);
		Recipes.MakeWall(POLISHED_GILDED_BLACKSTONE_WALL, POLISHED_GILDED_BLACKSTONE).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, POLISHED_GILDED_BLACKSTONE_WALL, POLISHED_GILDED_BLACKSTONE);
		OfferSmithingRecipe(exporter, POLISHED_GILDED_BLACKSTONE_WALL, Items.POLISHED_BLACKSTONE_WALL, Items.GOLD_INGOT);
		OfferStonecuttingRecipe(exporter, CHISELED_POLISHED_GILDED_BLACKSTONE, POLISHED_GILDED_BLACKSTONE);
		OfferSmithingRecipe(exporter, CHISELED_POLISHED_GILDED_BLACKSTONE, Items.CHISELED_POLISHED_BLACKSTONE, Items.GOLD_INGOT);
		OfferStonecuttingRecipe(exporter, SMOOTH_CHISELED_POLISHED_GILDED_BLACKSTONE, CHISELED_POLISHED_GILDED_BLACKSTONE);
		//Bricks
		Recipes.Make2x2(POLISHED_GILDED_BLACKSTONE_BRICKS, POLISHED_GILDED_BLACKSTONE, 4).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, POLISHED_GILDED_BLACKSTONE_BRICKS, POLISHED_GILDED_BLACKSTONE);
		OfferSmithingRecipe(exporter, POLISHED_GILDED_BLACKSTONE_BRICKS, Items.POLISHED_BLACKSTONE_BRICKS, Items.GOLD_INGOT);
		Recipes.MakeSlab(POLISHED_GILDED_BLACKSTONE_BRICK_SLAB, POLISHED_GILDED_BLACKSTONE_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, POLISHED_GILDED_BLACKSTONE_BRICK_SLAB, POLISHED_GILDED_BLACKSTONE, 2);
		OfferStonecuttingRecipe(exporter, POLISHED_GILDED_BLACKSTONE_BRICK_SLAB, POLISHED_GILDED_BLACKSTONE_BRICKS, 2);
		OfferSmithingRecipe(exporter, POLISHED_GILDED_BLACKSTONE_BRICK_SLAB, Items.POLISHED_BLACKSTONE_BRICK_SLAB, Items.GOLD_INGOT);
		Recipes.MakeStairs(POLISHED_GILDED_BLACKSTONE_BRICK_STAIRS, POLISHED_GILDED_BLACKSTONE_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, POLISHED_GILDED_BLACKSTONE_BRICK_STAIRS, POLISHED_GILDED_BLACKSTONE);
		OfferStonecuttingRecipe(exporter, POLISHED_GILDED_BLACKSTONE_BRICK_STAIRS, POLISHED_GILDED_BLACKSTONE_BRICKS);
		OfferSmithingRecipe(exporter, POLISHED_GILDED_BLACKSTONE_BRICK_STAIRS, Items.POLISHED_BLACKSTONE_BRICK_STAIRS, Items.GOLD_INGOT);
		Recipes.MakeWall(POLISHED_GILDED_BLACKSTONE_BRICK_WALL, POLISHED_GILDED_BLACKSTONE_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, POLISHED_GILDED_BLACKSTONE_BRICK_WALL, POLISHED_GILDED_BLACKSTONE);
		OfferStonecuttingRecipe(exporter, POLISHED_GILDED_BLACKSTONE_BRICK_WALL, POLISHED_GILDED_BLACKSTONE_BRICKS);
		OfferSmithingRecipe(exporter, POLISHED_GILDED_BLACKSTONE_BRICK_WALL, Items.POLISHED_BLACKSTONE_BRICK_WALL, Items.GOLD_INGOT);
		OfferSmeltingRecipe(exporter, CRACKED_POLISHED_GILDED_BLACKSTONE_BRICKS, POLISHED_GILDED_BLACKSTONE_BRICKS);
		OfferSmithingRecipe(exporter, CRACKED_POLISHED_GILDED_BLACKSTONE_BRICKS, Items.CRACKED_POLISHED_BLACKSTONE_BRICKS, Items.GOLD_INGOT);
		//</editor-fold>
		//<editor-fold desc="Misc Stuff">
		Recipes.MakeShaped(WOOD_BUCKET, ItemTags.LOGS).pattern("# #").pattern(" # ").offerTo(exporter);
		Recipes.MakeShapeless(LIGHT_BLUE_TARGET, Items.TARGET).input(Ingredient.ofItems(Items.LIGHT_BLUE_DYE)).offerTo(exporter);
		Recipes.MakeStairs(COAL_STAIRS, Items.COAL_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, COAL_STAIRS, Items.COAL_BLOCK);
		Recipes.MakeSlab(COAL_SLAB, Items.COAL_BLOCK).offerTo(exporter);
		Recipes.Make1x2(Items.COAL_BLOCK, COAL_SLAB).offerTo(exporter, ID("coal_block_from_coal_slabs"));
		OfferStonecuttingRecipe(exporter, COAL_SLAB, Items.COAL_BLOCK, 2);
		Recipes.MakeShapeless(Items.CHARCOAL, CHARCOAL_BLOCK, 9).offerTo(exporter, ID("charcoal_from_charcoal_block"));
		Recipes.Make3x3(CHARCOAL_BLOCK, Items.CHARCOAL).offerTo(exporter);
		Recipes.MakeStairs(CHARCOAL_STAIRS, CHARCOAL_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, CHARCOAL_STAIRS, CHARCOAL_BLOCK);
		Recipes.MakeSlab(CHARCOAL_SLAB, CHARCOAL_BLOCK).offerTo(exporter);
		Recipes.Make1x2(CHARCOAL_BLOCK, CHARCOAL_SLAB).offerTo(exporter, ID("charcoal_block_from_charcoal_slabs"));
		OfferStonecuttingRecipe(exporter, CHARCOAL_SLAB, CHARCOAL_BLOCK, 2);
		Recipes.MakeSlab(COARSE_DIRT_SLAB, Items.COARSE_DIRT).offerTo(exporter);
		Recipes.MakeShapeless(Items.COCOA_BEANS, COCOA_BLOCK, 9).offerTo(exporter, ID("cocoa_beans_from_cocoa_block"));
		Recipes.Make3x3(COCOA_BLOCK, Items.COCOA_BEANS).offerTo(exporter);
		for (DyeColor color : DyeColor.values()) {
			Item dye = ColorUtil.GetDye(color);
			BlockContainer block = DYE_BLOCKS.get(color);
			Recipes.MakeShapeless(dye, block, 9).offerTo(exporter, ID(color.getName() + "_dye_from_" + color.getName() + "_dye_block"));
			Recipes.Make3x3(block, dye).offerTo(exporter);
		}
		Recipes.MakeShapeless(Items.SUGAR, SUGAR_BLOCK, 9).offerTo(exporter, ID("sugar_from_sugar_block"));
		Recipes.Make3x3(SUGAR_BLOCK, Items.SUGAR).offerTo(exporter);
		Recipes.MakeShapeless(CARAMEL, CARAMEL_BLOCK, 9).offerTo(exporter, ID("caramel_from_caramel_block"));
		Recipes.Make3x3(CARAMEL_BLOCK, CARAMEL).offerTo(exporter);
		Recipes.MakeShapeless(Items.FLINT, FLINT_BLOCK, 9).offerTo(exporter, ID("flint_from_flint_block"));
		Recipes.Make3x3(FLINT_BLOCK, Items.FLINT).offerTo(exporter);
		Recipes.MakeSlab(FLINT_SLAB, FLINT_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, FLINT_SLAB, FLINT_BLOCK, 2);
		Recipes.Make1x2(FLINT_BLOCK, FLINT_SLAB).offerTo(exporter, ID("flint_block_from_flint_slabs"));
		Recipes.Make2x2(FLINT_BRICKS, FLINT_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, FLINT_BRICKS, FLINT_BLOCK);
		Recipes.MakeStairs(FLINT_BRICK_STAIRS, FLINT_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, FLINT_BRICK_STAIRS, FLINT_BLOCK);
		Recipes.MakeSlab(FLINT_BRICK_SLAB, FLINT_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, FLINT_BRICK_SLAB, FLINT_BLOCK, 2);
		Recipes.MakeWall(FLINT_BRICK_WALL, FLINT_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, FLINT_BRICK_WALL, FLINT_BLOCK);
		Recipes.Make3x3(GUNPOWDER_BLOCK, Items.GUNPOWDER).offerTo(exporter);
		Recipes.MakeShapeless(CARAMEL, CARAMEL_BLOCK, 9).offerTo(exporter, ID("gunpowder_from_gunpowder_block"));
		Recipes.MakeShapeless(GUNPOWDER_FUSE, Items.GUNPOWDER).input(Items.STRING).offerTo(exporter);
		Recipes.MakeShapeless(Items.HONEYCOMB, WAX_BLOCK, 9).offerTo(exporter, ID("honeycomb_from_wax_block"));
		Recipes.Make3x3(WAX_BLOCK, Items.HONEYCOMB).offerTo(exporter);
		Recipes.Make3x3(SUGAR_CANE_BLOCK, Items.SUGAR_CANE).offerTo(exporter);
		Recipes.MakeShapeless(Items.SUGAR_CANE, SUGAR_CANE_BLOCK, 9).offerTo(exporter, ID("sugar_cane_from_sugar_cane_block"));
		Recipes.MakeSlab(SUGAR_CANE_BLOCK_SLAB, SUGAR_CANE_BLOCK).offerTo(exporter);
		Recipes.MakeShapeless(Items.SUGAR_CANE, SUGAR_CANE_BLOCK_SLAB, 4).offerTo(exporter, ID("sugar_cane_from_sugar_cane_block_slab"));
		Recipes.Make3x2(SUGAR_CANE_ROW, SUGAR_CANE_BLOCK, 16).offerTo(exporter);
		OfferSmeltingRecipe(exporter, GLAZED_TERRACOTTA, Items.TERRACOTTA);
		Recipes.MakeStairs(TERRACOTTA_STAIRS, Items.TERRACOTTA).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, TERRACOTTA_STAIRS, Items.TERRACOTTA);
		Recipes.MakeSlab(TERRACOTTA_SLAB, Items.TERRACOTTA).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, TERRACOTTA_SLAB, Items.TERRACOTTA, 2);
		OfferSmeltingRecipe(exporter, GLAZED_TERRACOTTA_SLAB, TERRACOTTA_SLAB);
		Recipes.MakeWall(TERRACOTTA_WALL, Items.TERRACOTTA).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, TERRACOTTA_WALL, Items.TERRACOTTA);
		for (DyeColor color : DyeColor.values()) {
			Item terracottaItem = ColorUtil.GetTerracottaItem(color);
			Recipes.MakeStairs(TERRACOTTA_STAIRS_MAP.get(color), terracottaItem).offerTo(exporter);
			OfferStonecuttingRecipe(exporter, TERRACOTTA_STAIRS_MAP.get(color), terracottaItem);
			Recipes.MakeSlab(TERRACOTTA_SLABS.get(color), terracottaItem).offerTo(exporter);
			OfferStonecuttingRecipe(exporter, TERRACOTTA_SLABS.get(color), terracottaItem, 2);
			Recipes.MakeWall(TERRACOTTA_WALLS.get(color), terracottaItem).offerTo(exporter);
			OfferStonecuttingRecipe(exporter, TERRACOTTA_WALLS.get(color), terracottaItem);
			//Glazed
			Item glazedTerracottaItem = ColorUtil.GetGlazedTerracottaItem(color);
			Recipes.MakeSlab(GLAZED_TERRACOTTA_SLABS.get(color), glazedTerracottaItem).offerTo(exporter);
			OfferStonecuttingRecipe(exporter, GLAZED_TERRACOTTA_SLABS.get(color), glazedTerracottaItem, 2);
			OfferSmeltingRecipe(exporter, GLAZED_TERRACOTTA_SLABS.get(color), TERRACOTTA_SLABS.get(color));
		}
		for (DyeColor color : DyeColor.values()) {
			Item concreteItem = ColorUtil.GetConcreteItem(color);
			Recipes.MakeStairs(CONCRETE_STAIRS.get(color), concreteItem).offerTo(exporter);
			OfferStonecuttingRecipe(exporter, CONCRETE_STAIRS.get(color), concreteItem);
			Recipes.MakeSlab(CONCRETE_SLABS.get(color), concreteItem).offerTo(exporter);
			OfferStonecuttingRecipe(exporter, CONCRETE_SLABS.get(color), concreteItem, 2);
			Recipes.MakeWall(CONCRETE_WALLS.get(color), concreteItem).offerTo(exporter);
			OfferStonecuttingRecipe(exporter, CONCRETE_WALLS.get(color), concreteItem);
		}
		ShapelessRecipeJsonFactory.create(SEED_BLOCK).input(ModItemTags.SEEDS).criterion("automatic", RecipesProvider.conditionsFromItem(Items.STICK)).offerTo(exporter);
		Recipes.MakeShapeless(LAVA_BOTTLE, Items.GLASS_BOTTLE, 3).input(Items.LAVA_BUCKET).offerTo(exporter);
		Recipes.MakeSlab(LAPIS_SLAB, Items.LAPIS_BLOCK).offerTo(exporter);
		Recipes.MakeShapeless(Items.LAPIS_LAZULI, LAPIS_SLAB, 4).offerTo(exporter, ID("lapis_lazuli_from_lapis_slab"));
		Recipes.MakeShaped(Items.LAVA_BUCKET, LAVA_BOTTLE).input('B', Items.BUCKET).pattern("##").pattern("#B").offerTo(exporter);
		Recipes.MakeShaped(COPPER_LAVA_BUCKET, LAVA_BOTTLE).input('B', COPPER_BUCKET).pattern("##").pattern("#B").offerTo(exporter);
		Recipes.MakeShaped(GOLDEN_LAVA_BUCKET, LAVA_BOTTLE).input('B', GOLDEN_BUCKET).pattern("##").pattern("#B").offerTo(exporter);
		Recipes.MakeShaped(NETHERITE_LAVA_BUCKET, LAVA_BOTTLE).input('B', NETHERITE_BUCKET).pattern("##").pattern("#B").offerTo(exporter);
		Recipes.MakeShapeless(SUGAR_WATER_BOTTLE, Items.SUGAR).input(Ingredient.ofItems(Items.POTION, DISTILLED_WATER_BOTTLE)).offerTo(exporter);
		OfferSmeltingRecipe(exporter, SYRUP_BOTTLE, SAP_BOTTLE);
		Recipes.MakeShapeless(Items.MAGMA_CREAM, MAGMA_CREAM_BOTTLE).offerTo(exporter);
		Recipes.MakeShapeless(MAGMA_CREAM_BOTTLE, Items.MAGMA_CREAM).input(Items.GLASS_BOTTLE).offerTo(exporter);
		Recipes.MakeShapeless(COOLED_MAGMA_BLOCK, Items.MAGMA_BLOCK).input(Items.ICE).offerTo(exporter);
		Recipes.MakeShapeless(PUMICE, COOLED_MAGMA_BLOCK).input(Items.ICE).offerTo(exporter);
		//</editor-fold>
		//<editor-fold desc="Hammers">
		//Vanilla Materials
		Recipes.MakeHammer(STONE_HAMMER, Items.SMOOTH_STONE).offerTo(exporter);
		Recipes.MakeHammer(IRON_HAMMER, Items.IRON_BLOCK).offerTo(exporter);
		Recipes.MakeHammer(GOLDEN_HAMMER, Items.GOLD_BLOCK).offerTo(exporter);
		Recipes.MakeHammer(DIAMOND_HAMMER, Items.DIAMOND_BLOCK).offerTo(exporter);
		Recipes.MakeHammer(NETHERITE_HAMMER, Items.NETHERITE_BLOCK).offerTo(exporter);
		//</editor-fold>
		Recipes.MakeChestplate(TURTLE_CHESTPLATE, Items.SCUTE).offerTo(exporter);
		Recipes.MakeBoots(TURTLE_BOOTS, Items.SCUTE).offerTo(exporter);
		//<editor-fold desc="Slime">
		Recipes.MakeShapeless(Items.SLIME_BALL, SLIME_BOTTLE).offerTo(exporter, ID("slime_from_slime_bottle"));
		Recipes.MakeShapeless(SLIME_BOTTLE, Items.SLIME_BALL).input(Items.GLASS_BOTTLE).offerTo(exporter);
		Recipes.MakeShapeless(BLUE_SLIME_BALL, BLUE_SLIME_BOTTLE).offerTo(exporter, ID("blue_slime_from_blue_slime_bottle"));
		Recipes.MakeShapeless(BLUE_SLIME_BOTTLE, BLUE_SLIME_BALL).input(Items.GLASS_BOTTLE).offerTo(exporter);
		Recipes.Make3x3(BLUE_SLIME_BLOCK, BLUE_SLIME_BALL).offerTo(exporter);
		Recipes.MakeShapeless(BLUE_SLIME_BALL, BLUE_SLIME_BLOCK, 9).offerTo(exporter, ID("blue_slime_from_block"));
		Recipes.MakeShapeless(PINK_SLIME_BALL, PINK_SLIME_BOTTLE).offerTo(exporter, ID("pink_slime_from_pink_slime_bottle"));
		Recipes.MakeShapeless(PINK_SLIME_BOTTLE, PINK_SLIME_BALL).input(Items.GLASS_BOTTLE).offerTo(exporter);
		//</editor-fold>
		//<editor-fold desc="Food">
		Recipes.MakeGoldenFood(GOLDEN_POTATO, Items.POTATO).offerTo(exporter);
		Recipes.MakeGoldenFood(GOLDEN_BAKED_POTATO, Items.BAKED_POTATO).offerTo(exporter);
		Recipes.MakeGoldenFood(GOLDEN_BEETROOT, Items.BEETROOT).offerTo(exporter);
		Recipes.MakeGoldenFood(GOLDEN_CHORUS_FRUIT, Items.CHORUS_FRUIT).offerTo(exporter);
		Recipes.MakeGoldenFood(GOLDEN_TOMATO, ItemsRegistry.TOMATO.get()).offerTo(exporter);
		Recipes.MakeGoldenFood(GOLDEN_ONION, ItemsRegistry.ONION.get()).offerTo(exporter);
		Recipes.MakeGoldenFood(GOLDEN_EGG, Items.EGG).offerTo(exporter);
		Recipes.MakeShapeless(TOMATO_SOUP, Items.BOWL).input(ItemsRegistry.TOMATO.get(), 6).offerTo(exporter);
		//</editor-fold>
		Recipes.MakeShapeless(THROWABLE_TOMATO_ITEM, Items.SNOWBALL).input(ItemsRegistry.TOMATO.get()).offerTo(exporter);
		//<editor-fold desc="Books">
		Recipes.MakeShapeless(UNREADABLE_BOOK, Items.BOOK).offerTo(exporter);
		Recipes.MakeShapeless(RED_BOOK, Items.BOOK).input(Items.RED_DYE).offerTo(exporter);
		Recipes.MakeShapeless(ORANGE_BOOK, Items.BOOK).input(Items.ORANGE_DYE).offerTo(exporter);
		Recipes.MakeShapeless(YELLOW_BOOK, Items.BOOK).input(Items.YELLOW_DYE).offerTo(exporter);
		Recipes.MakeShapeless(GREEN_BOOK, Items.BOOK).input(Items.GREEN_DYE).offerTo(exporter);
		Recipes.MakeShapeless(BLUE_BOOK, Items.BOOK).input(Items.BLUE_DYE).offerTo(exporter);
		Recipes.MakeShapeless(PURPLE_BOOK, Items.BOOK).input(Items.PURPLE_DYE).offerTo(exporter);
		Recipes.MakeShapeless(GRAY_BOOK, Items.BOOK).input(Items.GRAY_DYE).offerTo(exporter);
		//</editor-fold>
		//<editor-fold desc="Wool">
		for (DyeColor color : DyeColor.values()) Recipes.MakeSlab(WOOL_SLABS.get(color), ColorUtil.GetWoolItem(color));
		Recipes.MakeSlab(RAINBOW_WOOL_SLAB, RAINBOW_WOOL).offerTo(exporter);
		Recipes.MakeCarpet(RAINBOW_CARPET, RAINBOW_WOOL).offerTo(exporter);
//		Recipes.MakeBed(RAINBOW_BED, Ingredient.ofItems(RAINBOW_WOOL, RAINBOW_FLEECE)).offerTo(exporter);
		//</editor-fold>
		//<editor-fold desc="Glow Lichen">
		Recipes.Make3x3(GLOW_LICHEN_BLOCK, Items.GLOW_LICHEN).offerTo(exporter);
		Recipes.MakeShapeless(Items.GLOW_LICHEN, GLOW_LICHEN_BLOCK, 9).offerTo(exporter, ID("glow_lichen_from_glow_lichen_block"));
		Recipes.MakeSlab(GLOW_LICHEN_SLAB, GLOW_LICHEN_BLOCK).offerTo(exporter);
		Recipes.MakeCarpet(GLOW_LICHEN_CARPET, GLOW_LICHEN_BLOCK).offerTo(exporter);
//		Recipes.MakeBed(GLOW_LICHEN_BED, GLOW_LICHEN_BLOCK).offerTo(exporter);
		//</editor-fold>
		//<editor-fold desc="Moss">
		Recipes.MakeSlab(MOSS_SLAB, Items.MOSS_BLOCK).offerTo(exporter);
//		Recipes.MakeBed(MOSS_BED, Items.MOSS_BLOCK).offerTo(exporter);
		//</editor-fold>
		//<editor-fold desc="Goat">
		OfferSmeltingRecipe(exporter, COOKED_CHEVON, CHEVON, 0.35);
		Recipes.MakeSmoking(COOKED_CHEVON, CHEVON, 100, 0.35).offerTo(exporter, _from_smoking(COOKED_CHEVON));
		Recipes.MakeCampfireRecipe(COOKED_CHEVON, CHEVON, 600, 0.35).offerTo(exporter, _from_campfire(COOKED_CHEVON));
		//</editor-fold>
		//<editor-fold desc="Fleece">
		for (DyeColor color : DyeColor.values()) Recipes.Make2x2(FLEECE.get(color), ColorUtil.GetWoolItem(color)).offerTo(exporter);
		for (DyeColor color : DyeColor.values()) Recipes.MakeSlab(FLEECE_SLABS.get(color), FLEECE.get(color)).offerTo(exporter);
		for (DyeColor color : DyeColor.values()) Recipes.MakeCarpet(FLEECE_CARPETS.get(color), FLEECE.get(color)).offerTo(exporter);
		Recipes.MakeSlab(RAINBOW_FLEECE_SLAB, RAINBOW_FLEECE).offerTo(exporter);
		Recipes.MakeCarpet(RAINBOW_FLEECE_CARPET, RAINBOW_FLEECE).offerTo(exporter);
		Recipes.MakeHelmet(FLEECE_HELMET, Ingredient.fromTag(ModItemTags.FLEECE));
		Recipes.MakeChestplate(FLEECE_CHESTPLATE, Ingredient.fromTag(ModItemTags.FLEECE));
		Recipes.MakeLeggings(FLEECE_LEGGINGS, Ingredient.fromTag(ModItemTags.FLEECE));
		Recipes.MakeBoots(FLEECE_BOOTS, Ingredient.fromTag(ModItemTags.FLEECE));
		Recipes.MakeHorseArmor(FLEECE_HORSE_ARMOR, Ingredient.fromTag(ModItemTags.FLEECE));
		//</editor-fold>
		//<editor-fold desc="Studded Leather">
		Recipes.MakeShaped(STUDDED_LEATHER_HELMET, Items.LEATHER).input('C', Items.CHAIN).pattern("C#C").pattern("# #").offerTo(exporter);
		Recipes.MakeShaped(STUDDED_LEATHER_CHESTPLATE, Items.LEATHER).input('C', Items.CHAIN).pattern("C C").pattern("C#C").pattern("###").offerTo(exporter);
		Recipes.MakeShaped(STUDDED_LEATHER_LEGGINGS, Items.LEATHER).input('C', Items.CHAIN).pattern("C#C").pattern("C C").pattern("# #").offerTo(exporter);
		Recipes.MakeShaped(STUDDED_LEATHER_BOOTS, Items.LEATHER).input('C', Items.CHAIN).pattern("C C").pattern("# #").offerTo(exporter);
		Recipes.MakeShaped(STUDDED_LEATHER_HORSE_ARMOR, Items.LEATHER).input('C', Items.CHAIN).pattern("# #").pattern("C#C").pattern("# #").offerTo(exporter);
		//</editor-fold>
		//<editor-fold desc="Charred Wood">
		Recipes.MakeCampfireRecipe(CHARRED_LOG, ModItemTags.CHARRABLE_LOGS, 600, 0.35f).offerTo(exporter, RecipeName(CHARRED_LOG, "_from_charrable_log"));
		Recipes.MakeCampfireRecipe(STRIPPED_CHARRED_LOG, ModItemTags.CHARRABLE_STRIPPED_LOGS, 600, 0.35f).offerTo(exporter, RecipeName(STRIPPED_CHARRED_LOG, "_from_charrable_stripped_log"));
		Recipes.Make2x2(CHARRED_WOOD, CHARRED_LOG, 3).offerTo(exporter);
		Recipes.MakeCampfireRecipe(CHARRED_WOOD, ModItemTags.CHARRABLE_WOODS, 600, 0.35f).offerTo(exporter, RecipeName(CHARRED_WOOD, "_from_charrable_wood"));
		Recipes.Make2x2(STRIPPED_CHARRED_WOOD, STRIPPED_CHARRED_LOG, 3).offerTo(exporter);
		Recipes.MakeCampfireRecipe(STRIPPED_CHARRED_WOOD, ModItemTags.CHARRABLE_STRIPPED_WOODS, 600, 0.35f).offerTo(exporter, RecipeName(STRIPPED_CHARRED_WOOD, "_from_charrable_stripped_wood"));
		Recipes.MakeShapeless(CHARRED_PLANKS, Ingredient.fromTag(ModItemTags.CHARRED_LOGS), 4).offerTo(exporter);
		Recipes.MakeSlab(CHARRED_SLAB, CHARRED_PLANKS).offerTo(exporter);
		OfferWoodcuttingRecipe(exporter, CHARRED_SLAB, CHARRED_PLANKS, 2);
		Recipes.MakeStairs(CHARRED_STAIRS, CHARRED_PLANKS).offerTo(exporter);
		OfferWoodcuttingRecipe(exporter, CHARRED_STAIRS, CHARRED_PLANKS);
		Recipes.MakeWoodenFence(CHARRED_FENCE, CHARRED_PLANKS).offerTo(exporter);
		Recipes.MakeWoodenFenceGate(CHARRED_FENCE_GATE, CHARRED_PLANKS).offerTo(exporter);
		Recipes.MakeDoor(CHARRED_DOOR, CHARRED_PLANKS).offerTo(exporter);
		Recipes.MakeWoodenTrapdoor(CHARRED_TRAPDOOR, CHARRED_PLANKS).offerTo(exporter);
		Recipes.MakePressurePlate(CHARRED_PRESSURE_PLATE, CHARRED_PLANKS).offerTo(exporter);
		Recipes.MakeShapeless(CHARRED_BUTTON, CHARRED_PLANKS).offerTo(exporter);
		Recipes.MakeSign(CHARRED_SIGN, CHARRED_PLANKS).offerTo(exporter);
		Recipes.MakeHangingSign(CHARRED_SIGN.getHanging(), STRIPPED_CHARRED_LOG).offerTo(exporter);
		Recipes.MakeBoat(CHARRED_BOAT, CHARRED_PLANKS).offerTo(exporter);
		Recipes.MakeShapeless(CHARRED_BOAT.getChestBoat(), CHARRED_BOAT).input(Items.CHEST).offerTo(exporter);
		Recipes.MakeBeehive(CHARRED_BEEHIVE, CHARRED_PLANKS).offerTo(exporter);
		Recipes.MakeBookshelf(CHARRED_BOOKSHELF, CHARRED_PLANKS).offerTo(exporter);
		Recipes.MakeChiseledBookshelf(CHARRED_CHISELED_BOOKSHELF, CHARRED_PLANKS).offerTo(exporter);
		Recipes.Make2x2(CHARRED_CRAFTING_TABLE, CHARRED_PLANKS).offerTo(exporter);
		Recipes.MakePlanksLadder(CHARRED_LADDER, CHARRED_PLANKS).offerTo(exporter);
		Recipes.MakeWoodcutter(CHARRED_WOODCUTTER, CHARRED_PLANKS).offerTo(exporter);
		Recipes.MakeBarrel(CHARRED_BARREL, CHARRED_PLANKS, CHARRED_SLAB).offerTo(exporter);
		Recipes.MakeLectern(CHARRED_LECTERN, CHARRED_SLAB).offerTo(exporter);
		Recipes.MakePowderKeg(CHARRED_POWDER_KEG, CHARRED_BARREL).offerTo(exporter);
		Recipes.MakeSlab(CHARRED_LOG_SLAB, CHARRED_LOG).offerTo(exporter);
		OfferWoodcuttingRecipe(exporter, CHARRED_LOG_SLAB, CHARRED_LOG, 2);
		Recipes.MakeCampfireRecipe(CHARRED_LOG_SLAB, ModItemTags.CHARRABLE_LOG_SLABS, 600, 0.35f).offerTo(exporter, RecipeName(CHARRED_LOG_SLAB, "_from_charrable_log_slab"));
		Recipes.MakeSlab(STRIPPED_CHARRED_LOG_SLAB, STRIPPED_CHARRED_LOG).offerTo(exporter);
		OfferWoodcuttingRecipe(exporter, STRIPPED_CHARRED_LOG_SLAB, STRIPPED_CHARRED_LOG, 2);
		Recipes.MakeCampfireRecipe(STRIPPED_CHARRED_LOG_SLAB, ModItemTags.CHARRABLE_STRIPPED_LOG_SLABS, 600, 0.35f).offerTo(exporter, RecipeName(STRIPPED_CHARRED_LOG_SLAB, "_from_charrable_stripped_log_slab"));
		Recipes.MakeSlab(CHARRED_WOOD_SLAB, CHARRED_WOOD).offerTo(exporter);
		OfferWoodcuttingRecipe(exporter, CHARRED_WOOD_SLAB, CHARRED_WOOD, 2);
		Recipes.MakeCampfireRecipe(CHARRED_WOOD_SLAB, ModItemTags.CHARRABLE_WOOD_SLABS, 600, 0.35f).offerTo(exporter, RecipeName(CHARRED_WOOD_SLAB, "_from_charrable_wood_slab"));
		Recipes.MakeSlab(STRIPPED_CHARRED_WOOD_SLAB, STRIPPED_CHARRED_WOOD).offerTo(exporter);
		OfferWoodcuttingRecipe(exporter, STRIPPED_CHARRED_WOOD_SLAB, STRIPPED_CHARRED_WOOD, 2);
		Recipes.MakeCampfireRecipe(STRIPPED_CHARRED_WOOD_SLAB, ModItemTags.CHARRABLE_STRIPPED_WOOD_SLABS, 600, 0.35f).offerTo(exporter, RecipeName(STRIPPED_CHARRED_WOOD_SLAB, "_from_charrable_stripped_wood_slab"));
		Recipes.MakeHammer(CHARRED_HAMMER, CHARRED_LOG).offerTo(exporter);
		//</editor-fold>
		//<editor-fold desc="Music Discs">
		Recipes.Make3x3(MUSIC_DISC_5, DISC_FRAGMENT_5).offerTo(exporter);
		//</editor-fold>
		//<editor-fold desc="Mud">
		//Packed
		Recipes.MakeShapeless(PACKED_MUD, MUD).input(Items.WHEAT).offerTo(exporter);
		Recipes.MakeSlab(PACKED_MUD_SLAB, PACKED_MUD).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, PACKED_MUD_SLAB, PACKED_MUD, 2);
		Recipes.MakeStairs(PACKED_MUD_STAIRS, PACKED_MUD).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, PACKED_MUD_STAIRS, PACKED_MUD);
		Recipes.MakeWall(PACKED_MUD_WALL, PACKED_MUD).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, PACKED_MUD_WALL, PACKED_MUD);
		//Bricks
		Recipes.Make2x2(MUD_BRICKS, PACKED_MUD, 4).offerTo(exporter);
		Recipes.MakeSlab(MUD_BRICK_SLAB, MUD_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, MUD_BRICK_SLAB, MUD_BRICKS, 2);
		Recipes.MakeStairs(MUD_BRICK_STAIRS, MUD_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, MUD_BRICK_STAIRS, MUD_BRICKS);
		Recipes.MakeWall(MUD_BRICK_WALL, MUD_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, MUD_BRICK_WALL, MUD_BRICKS);
		//Chiseled Bricks
		Recipes.Make1x2(CHISELED_MUD_BRICKS, MUD_BRICK_SLAB).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, CHISELED_MUD_BRICKS, MUD);
		OfferStonecuttingRecipe(exporter, CHISELED_MUD_BRICKS, MUD_BRICKS);
		OfferStonecuttingRecipe(exporter, SMOOTH_CHISELED_MUD_BRICKS, CHISELED_MUD_BRICKS);
		//</editor-fold>
		//<editor-fold desc="Mangrove">
		Recipes.Make2x2(MANGROVE_WOOD, MANGROVE_LOG, 3).offerTo(exporter);
		Recipes.Make2x2(STRIPPED_MANGROVE_WOOD, STRIPPED_MANGROVE_LOG, 3).offerTo(exporter);
		Recipes.MakeShapeless(MANGROVE_PLANKS, Ingredient.fromTag(ModItemTags.MANGROVE_LOGS), 4).offerTo(exporter);
		Recipes.MakeSlab(MANGROVE_SLAB, MANGROVE_PLANKS).offerTo(exporter);
		OfferWoodcuttingRecipe(exporter, MANGROVE_SLAB, MANGROVE_PLANKS, 2);
		Recipes.MakeStairs(MANGROVE_STAIRS, MANGROVE_PLANKS).offerTo(exporter);
		OfferWoodcuttingRecipe(exporter, MANGROVE_STAIRS, MANGROVE_PLANKS);
		Recipes.MakeWoodenFence(MANGROVE_FENCE, MANGROVE_PLANKS).offerTo(exporter);
		Recipes.MakeWoodenFenceGate(MANGROVE_FENCE_GATE, MANGROVE_PLANKS).offerTo(exporter);
		Recipes.MakeDoor(MANGROVE_DOOR, MANGROVE_PLANKS).offerTo(exporter);
		Recipes.MakeWoodenTrapdoor(MANGROVE_TRAPDOOR, MANGROVE_PLANKS).offerTo(exporter);
		Recipes.MakePressurePlate(MANGROVE_PRESSURE_PLATE, MANGROVE_PLANKS).offerTo(exporter);
		Recipes.MakeShapeless(MANGROVE_BUTTON, MANGROVE_PLANKS).offerTo(exporter);
		Recipes.MakeSign(MANGROVE_SIGN.getHanging(), MANGROVE_PLANKS).offerTo(exporter);
		Recipes.MakeHangingSign(MANGROVE_SIGN, STRIPPED_MANGROVE_LOG).offerTo(exporter);
		Recipes.MakeBoat(MANGROVE_BOAT, MANGROVE_PLANKS).offerTo(exporter);
		Recipes.MakeShapeless(MANGROVE_BOAT.getChestBoat(), MANGROVE_BOAT).input(Items.CHEST).offerTo(exporter);
		Recipes.MakeShapeless(MUDDY_MANGROVE_ROOTS, MUD).input(MANGROVE_ROOTS).offerTo(exporter);
		//Extended
		Recipes.MakeBeehive(MANGROVE_BEEHIVE, MANGROVE_PLANKS).offerTo(exporter);
		Recipes.MakeBookshelf(MANGROVE_BOOKSHELF, MANGROVE_PLANKS).offerTo(exporter);
		Recipes.MakeChiseledBookshelf(MANGROVE_CHISELED_BOOKSHELF, MANGROVE_PLANKS).offerTo(exporter);
		Recipes.Make2x2(MANGROVE_CRAFTING_TABLE, MANGROVE_PLANKS).offerTo(exporter);
		Recipes.MakePlanksLadder(MANGROVE_LADDER, MANGROVE_PLANKS).offerTo(exporter);
		Recipes.MakeWoodcutter(MANGROVE_WOODCUTTER, MANGROVE_PLANKS).offerTo(exporter);
		Recipes.MakeBarrel(MANGROVE_BARREL, MANGROVE_PLANKS, MANGROVE_SLAB).offerTo(exporter);
		Recipes.MakePowderKeg(MANGROVE_POWDER_KEG, MANGROVE_BARREL).offerTo(exporter);
		Recipes.MakeSlab(MANGROVE_LOG_SLAB, MANGROVE_LOG).offerTo(exporter);
		OfferWoodcuttingRecipe(exporter, MANGROVE_LOG_SLAB, MANGROVE_LOG, 2);
		Recipes.MakeSlab(STRIPPED_MANGROVE_LOG_SLAB, STRIPPED_MANGROVE_LOG).offerTo(exporter);
		OfferWoodcuttingRecipe(exporter, STRIPPED_MANGROVE_LOG_SLAB, STRIPPED_MANGROVE_LOG, 2);
		Recipes.MakeSlab(MANGROVE_WOOD_SLAB, MANGROVE_WOOD).offerTo(exporter);
		OfferWoodcuttingRecipe(exporter, MANGROVE_WOOD_SLAB, MANGROVE_WOOD, 2);
		Recipes.MakeSlab(STRIPPED_MANGROVE_WOOD_SLAB, STRIPPED_MANGROVE_WOOD).offerTo(exporter);
		OfferWoodcuttingRecipe(exporter, STRIPPED_MANGROVE_WOOD_SLAB, STRIPPED_MANGROVE_WOOD, 2);
		Recipes.MakeHammer(MANGROVE_HAMMER, MANGROVE_LOG).offerTo(exporter);
		//</editor-fold>
		//<editor-fold desc="Cherry">
		Recipes.Make2x2(CHERRY_WOOD, CHERRY_LOG, 3).offerTo(exporter);
		Recipes.Make2x2(STRIPPED_CHERRY_WOOD, STRIPPED_CHERRY_LOG, 3).offerTo(exporter);
		Recipes.MakeShapeless(CHERRY_PLANKS, Ingredient.fromTag(ModItemTags.CHERRY_LOGS), 4).offerTo(exporter);
		Recipes.MakeSlab(CHERRY_SLAB, CHERRY_PLANKS).offerTo(exporter);
		OfferWoodcuttingRecipe(exporter, CHERRY_SLAB, CHERRY_PLANKS, 2);
		Recipes.MakeStairs(CHERRY_STAIRS, CHERRY_PLANKS).offerTo(exporter);
		OfferWoodcuttingRecipe(exporter, CHERRY_STAIRS, CHERRY_PLANKS);
		Recipes.MakeWoodenFence(CHERRY_FENCE, CHERRY_PLANKS).offerTo(exporter);
		Recipes.MakeWoodenFenceGate(CHERRY_FENCE_GATE, CHERRY_PLANKS).offerTo(exporter);
		Recipes.MakeDoor(CHERRY_DOOR, CHERRY_PLANKS).offerTo(exporter);
		Recipes.MakeWoodenTrapdoor(CHERRY_TRAPDOOR, CHERRY_PLANKS).offerTo(exporter);
		Recipes.MakePressurePlate(CHERRY_PRESSURE_PLATE, CHERRY_PLANKS).offerTo(exporter);
		Recipes.MakeShapeless(CHERRY_BUTTON, CHERRY_PLANKS).offerTo(exporter);
		Recipes.MakeSign(CHERRY_SIGN.getHanging(), CHERRY_PLANKS).offerTo(exporter);
		Recipes.MakeHangingSign(CHERRY_SIGN, STRIPPED_CHERRY_LOG).offerTo(exporter);
		Recipes.MakeBoat(CHERRY_BOAT, CHERRY_PLANKS).offerTo(exporter);
		Recipes.MakeShapeless(CHERRY_BOAT.getChestBoat(), CHERRY_BOAT).input(Items.CHEST).offerTo(exporter);
		//Extended
		Recipes.MakeBeehive(CHERRY_BEEHIVE, CHERRY_PLANKS).offerTo(exporter);
		Recipes.MakeBookshelf(CHERRY_BOOKSHELF, CHERRY_PLANKS).offerTo(exporter);
		Recipes.MakeChiseledBookshelf(CHERRY_CHISELED_BOOKSHELF, CHERRY_PLANKS).offerTo(exporter);
		Recipes.Make2x2(CHERRY_CRAFTING_TABLE, CHERRY_PLANKS).offerTo(exporter);
		Recipes.MakePlanksLadder(CHERRY_LADDER, CHERRY_PLANKS).offerTo(exporter);
		Recipes.MakeWoodcutter(CHERRY_WOODCUTTER, CHERRY_PLANKS).offerTo(exporter);
		Recipes.MakeBarrel(CHERRY_BARREL, CHERRY_PLANKS, CHERRY_SLAB).offerTo(exporter);
		Recipes.MakeLectern(CHERRY_LECTERN, CHERRY_SLAB).offerTo(exporter);
		Recipes.MakePowderKeg(CHERRY_POWDER_KEG, CHERRY_BARREL).offerTo(exporter);
		Recipes.MakeSlab(CHERRY_LOG_SLAB, CHERRY_LOG).offerTo(exporter);
		OfferWoodcuttingRecipe(exporter, CHERRY_LOG_SLAB, CHERRY_LOG, 2);
		Recipes.MakeSlab(STRIPPED_CHERRY_LOG_SLAB, STRIPPED_CHERRY_LOG).offerTo(exporter);
		OfferWoodcuttingRecipe(exporter, STRIPPED_CHERRY_LOG_SLAB, STRIPPED_CHERRY_LOG, 2);
		Recipes.MakeSlab(CHERRY_WOOD_SLAB, CHERRY_WOOD).offerTo(exporter);
		OfferWoodcuttingRecipe(exporter, CHERRY_WOOD_SLAB, CHERRY_WOOD, 2);
		Recipes.MakeSlab(STRIPPED_CHERRY_WOOD_SLAB, STRIPPED_CHERRY_WOOD).offerTo(exporter);
		OfferWoodcuttingRecipe(exporter, STRIPPED_CHERRY_WOOD_SLAB, STRIPPED_CHERRY_WOOD, 2);
		Recipes.MakeHammer(CHERRY_HAMMER, CHERRY_LOG).offerTo(exporter);
		//</editor-fold>
		Recipes.MakeShapeless(Items.PINK_DYE, PINK_PETALS, 1).offerTo(exporter, ID("pink_dye_from_pink_petals"));
		Recipes.MakeShapeless(Items.ORANGE_DYE, TORCHFLOWER, 1).offerTo(exporter, ID("orange_dye_from_torchflower"));
		Recipes.MakeShapeless(Items.CYAN_DYE, PITCHER_PLANT, 1).offerTo(exporter, ID("cyan_dye_from_pitcher_plant"));
		//<editor-fold desc="Cassia">
		Recipes.Make2x2(CASSIA_WOOD, CASSIA_LOG, 3).offerTo(exporter);
		Recipes.Make2x2(STRIPPED_CASSIA_WOOD, STRIPPED_CASSIA_LOG, 3).offerTo(exporter);
		Recipes.MakeShapeless(CASSIA_PLANKS, Ingredient.fromTag(ModItemTags.CASSIA_LOGS), 4).offerTo(exporter);
		Recipes.MakeSlab(CASSIA_SLAB, CASSIA_PLANKS).offerTo(exporter);
		OfferWoodcuttingRecipe(exporter, CASSIA_SLAB, CASSIA_PLANKS, 2);
		Recipes.MakeStairs(CASSIA_STAIRS, CASSIA_PLANKS).offerTo(exporter);
		OfferWoodcuttingRecipe(exporter, CASSIA_STAIRS, CASSIA_PLANKS);
		Recipes.MakeWoodenFence(CASSIA_FENCE, CASSIA_PLANKS).offerTo(exporter);
		Recipes.MakeWoodenFenceGate(CASSIA_FENCE_GATE, CASSIA_PLANKS).offerTo(exporter);
		Recipes.MakeDoor(CASSIA_DOOR, CASSIA_PLANKS).offerTo(exporter);
		Recipes.MakeWoodenTrapdoor(CASSIA_TRAPDOOR, CASSIA_PLANKS).offerTo(exporter);
		Recipes.MakePressurePlate(CASSIA_PRESSURE_PLATE, CASSIA_PLANKS).offerTo(exporter);
		Recipes.MakeShapeless(CASSIA_BUTTON, CASSIA_PLANKS).offerTo(exporter);
		Recipes.MakeSign(CASSIA_SIGN.getHanging(), CASSIA_PLANKS).offerTo(exporter);
		Recipes.MakeHangingSign(CASSIA_SIGN, STRIPPED_CASSIA_LOG).offerTo(exporter);
		Recipes.MakeBoat(CASSIA_BOAT, CASSIA_PLANKS).offerTo(exporter);
		Recipes.MakeShapeless(CASSIA_BOAT.getChestBoat(), CASSIA_BOAT).input(Items.CHEST).offerTo(exporter);
		Recipes.MakeBeehive(CASSIA_BEEHIVE, CASSIA_PLANKS).offerTo(exporter);
		Recipes.MakeBookshelf(CASSIA_BOOKSHELF, CASSIA_PLANKS).offerTo(exporter);
		Recipes.MakeChiseledBookshelf(CASSIA_CHISELED_BOOKSHELF, CASSIA_PLANKS).offerTo(exporter);
		Recipes.Make2x2(CASSIA_CRAFTING_TABLE, CASSIA_PLANKS).offerTo(exporter);
		Recipes.MakePlanksLadder(CASSIA_LADDER, CASSIA_PLANKS).offerTo(exporter);
		Recipes.MakeWoodcutter(CASSIA_WOODCUTTER, CASSIA_PLANKS).offerTo(exporter);
		Recipes.MakeBarrel(CASSIA_BARREL, CASSIA_PLANKS, CASSIA_SLAB).offerTo(exporter);
		Recipes.MakeLectern(CASSIA_LECTERN, CASSIA_SLAB).offerTo(exporter);
		Recipes.MakePowderKeg(CASSIA_POWDER_KEG, CASSIA_BARREL).offerTo(exporter);
		Recipes.MakeSlab(CASSIA_LOG_SLAB, CASSIA_LOG).offerTo(exporter);
		OfferWoodcuttingRecipe(exporter, CASSIA_LOG_SLAB, CASSIA_LOG, 2);
		Recipes.MakeSlab(STRIPPED_CASSIA_LOG_SLAB, STRIPPED_CASSIA_LOG).offerTo(exporter);
		OfferWoodcuttingRecipe(exporter, STRIPPED_CASSIA_LOG_SLAB, STRIPPED_CASSIA_LOG, 2);
		Recipes.MakeSlab(CASSIA_WOOD_SLAB, CASSIA_WOOD).offerTo(exporter);
		OfferWoodcuttingRecipe(exporter, CASSIA_WOOD_SLAB, CASSIA_WOOD, 2);
		Recipes.MakeSlab(STRIPPED_CASSIA_WOOD_SLAB, STRIPPED_CASSIA_WOOD).offerTo(exporter);
		OfferWoodcuttingRecipe(exporter, STRIPPED_CASSIA_WOOD_SLAB, STRIPPED_CASSIA_WOOD, 2);
		Recipes.MakeHammer(CASSIA_HAMMER, CASSIA_LOG).offerTo(exporter);
		//</editor-fold>
		//<editor-fold desc="Dogwood">
		Recipes.Make2x2(DOGWOOD_WOOD, DOGWOOD_LOG, 3).offerTo(exporter);
		Recipes.Make2x2(STRIPPED_DOGWOOD_WOOD, STRIPPED_DOGWOOD_LOG, 3).offerTo(exporter);
		Recipes.MakeShapeless(DOGWOOD_PLANKS, Ingredient.fromTag(ModItemTags.DOGWOOD_LOGS), 4).offerTo(exporter);
		Recipes.MakeSlab(DOGWOOD_SLAB, DOGWOOD_PLANKS).offerTo(exporter);
		OfferWoodcuttingRecipe(exporter, DOGWOOD_SLAB, DOGWOOD_PLANKS, 2);
		Recipes.MakeStairs(DOGWOOD_STAIRS, DOGWOOD_PLANKS).offerTo(exporter);
		OfferWoodcuttingRecipe(exporter, DOGWOOD_STAIRS, DOGWOOD_PLANKS);
		Recipes.MakeWoodenFence(DOGWOOD_FENCE, DOGWOOD_PLANKS).offerTo(exporter);
		Recipes.MakeWoodenFenceGate(DOGWOOD_FENCE_GATE, DOGWOOD_PLANKS).offerTo(exporter);
		Recipes.MakeDoor(DOGWOOD_DOOR, DOGWOOD_PLANKS).offerTo(exporter);
		Recipes.MakeWoodenTrapdoor(DOGWOOD_TRAPDOOR, DOGWOOD_PLANKS).offerTo(exporter);
		Recipes.MakePressurePlate(DOGWOOD_PRESSURE_PLATE, DOGWOOD_PLANKS).offerTo(exporter);
		Recipes.MakeShapeless(DOGWOOD_BUTTON, DOGWOOD_PLANKS).offerTo(exporter);
		Recipes.MakeSign(DOGWOOD_SIGN.getHanging(), DOGWOOD_PLANKS).offerTo(exporter);
		Recipes.MakeHangingSign(DOGWOOD_SIGN, STRIPPED_DOGWOOD_LOG).offerTo(exporter);
		Recipes.MakeBoat(DOGWOOD_BOAT, DOGWOOD_PLANKS).offerTo(exporter);
		Recipes.MakeShapeless(DOGWOOD_BOAT.getChestBoat(), DOGWOOD_BOAT).input(Items.CHEST).offerTo(exporter);
		Recipes.MakeBeehive(DOGWOOD_BEEHIVE, DOGWOOD_PLANKS).offerTo(exporter);
		Recipes.MakeBookshelf(DOGWOOD_BOOKSHELF, DOGWOOD_PLANKS).offerTo(exporter);
		Recipes.MakeChiseledBookshelf(DOGWOOD_CHISELED_BOOKSHELF, DOGWOOD_PLANKS).offerTo(exporter);
		Recipes.Make2x2(DOGWOOD_CRAFTING_TABLE, DOGWOOD_PLANKS).offerTo(exporter);
		Recipes.MakePlanksLadder(DOGWOOD_LADDER, DOGWOOD_PLANKS).offerTo(exporter);
		Recipes.MakeWoodcutter(DOGWOOD_WOODCUTTER, DOGWOOD_PLANKS).offerTo(exporter);
		Recipes.MakeBarrel(DOGWOOD_BARREL, DOGWOOD_PLANKS, DOGWOOD_SLAB).offerTo(exporter);
		Recipes.MakeLectern(DOGWOOD_LECTERN, DOGWOOD_SLAB).offerTo(exporter);
		Recipes.MakePowderKeg(DOGWOOD_POWDER_KEG, DOGWOOD_BARREL).offerTo(exporter);
		Recipes.MakeSlab(DOGWOOD_LOG_SLAB, DOGWOOD_LOG).offerTo(exporter);
		OfferWoodcuttingRecipe(exporter, DOGWOOD_LOG_SLAB, DOGWOOD_LOG, 2);
		Recipes.MakeSlab(STRIPPED_DOGWOOD_LOG_SLAB, STRIPPED_DOGWOOD_LOG).offerTo(exporter);
		OfferWoodcuttingRecipe(exporter, STRIPPED_DOGWOOD_LOG_SLAB, STRIPPED_DOGWOOD_LOG, 2);
		Recipes.MakeSlab(DOGWOOD_WOOD_SLAB, DOGWOOD_WOOD).offerTo(exporter);
		OfferWoodcuttingRecipe(exporter, DOGWOOD_WOOD_SLAB, DOGWOOD_WOOD, 2);
		Recipes.MakeSlab(STRIPPED_DOGWOOD_WOOD_SLAB, STRIPPED_DOGWOOD_WOOD).offerTo(exporter);
		OfferWoodcuttingRecipe(exporter, STRIPPED_DOGWOOD_WOOD_SLAB, STRIPPED_DOGWOOD_WOOD, 2);
		Recipes.MakeHammer(DOGWOOD_HAMMER, DOGWOOD_LOG).offerTo(exporter);
		//</editor-fold>
		//<editor-fold desc="Bamboo">
		Recipes.Make3x3(BAMBOO_BLOCK, Items.BAMBOO).offerTo(exporter);
		Recipes.MakeShapeless(BAMBOO_PLANKS, Ingredient.ofItems(BAMBOO_BLOCK, STRIPPED_BAMBOO_BLOCK, DRIED_BAMBOO_BLOCK, STRIPPED_DRIED_BAMBOO_BLOCK), 2).offerTo(exporter, ID("bamboo_planks_from_bamboo_block"));
		Recipes.Make2x2(BAMBOO_PLANKS, Ingredient.ofItems(Items.BAMBOO, DRIED_BAMBOO.asItem())).offerTo(exporter);
		Recipes.MakeSlab(BAMBOO_SLAB, BAMBOO_PLANKS).offerTo(exporter);
		OfferWoodcuttingRecipe(exporter, BAMBOO_SLAB, BAMBOO_PLANKS, 2);
		Recipes.MakeStairs(BAMBOO_STAIRS, BAMBOO_PLANKS).offerTo(exporter);
		OfferWoodcuttingRecipe(exporter, BAMBOO_STAIRS, BAMBOO_PLANKS);
		Recipes.MakeWoodenFence(BAMBOO_FENCE, BAMBOO_PLANKS).offerTo(exporter);
		Recipes.MakeWoodenFenceGate(BAMBOO_FENCE_GATE, BAMBOO_PLANKS).offerTo(exporter);
		Recipes.MakeDoor(BAMBOO_DOOR, BAMBOO_PLANKS).offerTo(exporter);
		Recipes.MakeWoodenTrapdoor(BAMBOO_TRAPDOOR, BAMBOO_PLANKS).offerTo(exporter);
		Recipes.MakePressurePlate(BAMBOO_PRESSURE_PLATE, BAMBOO_PLANKS).offerTo(exporter);
		Recipes.MakeShapeless(BAMBOO_BUTTON, BAMBOO_PLANKS).offerTo(exporter);
		Recipes.MakeSign(BAMBOO_SIGN.getHanging(), BAMBOO_PLANKS).offerTo(exporter);
		Recipes.MakeHangingSign(BAMBOO_SIGN, STRIPPED_BAMBOO_BLOCK).offerTo(exporter);
		Recipes.MakeBoat(BAMBOO_RAFT, BAMBOO_PLANKS).offerTo(exporter);
		Recipes.MakeShapeless(BAMBOO_RAFT.getChestBoat(), BAMBOO_RAFT).input(Items.CHEST).offerTo(exporter);
		//Mosaic
		Recipes.Make2x1(BAMBOO_MOSAIC, BAMBOO_SLAB, 1).offerTo(exporter);
		Recipes.MakeSlab(BAMBOO_MOSAIC_SLAB, BAMBOO_MOSAIC).offerTo(exporter);
		OfferWoodcuttingRecipe(exporter, BAMBOO_MOSAIC_SLAB, BAMBOO_MOSAIC, 2);
		Recipes.MakeStairs(BAMBOO_MOSAIC_STAIRS, BAMBOO_MOSAIC).offerTo(exporter);
		OfferWoodcuttingRecipe(exporter, BAMBOO_MOSAIC_STAIRS, BAMBOO_MOSAIC);
		//Extended
		Recipes.MakeTorch(BAMBOO_TORCH, Items.BAMBOO, 4).offerTo(exporter);
		Recipes.MakeSoulTorch(BAMBOO_SOUL_TORCH, Items.BAMBOO, 4).offerTo(exporter);
		Recipes.MakeEnderTorch(BAMBOO_ENDER_TORCH, Items.BAMBOO, 4).offerTo(exporter);
		Recipes.MakeUnderwaterTorch(UNDERWATER_BAMBOO_TORCH, Items.BAMBOO, 4).offerTo(exporter);
		Recipes.MakeBeehive(BAMBOO_BEEHIVE, BAMBOO_PLANKS).offerTo(exporter);
		Recipes.MakeBookshelf(BAMBOO_BOOKSHELF, BAMBOO_PLANKS).offerTo(exporter);
		Recipes.MakeChiseledBookshelf(BAMBOO_CHISELED_BOOKSHELF, BAMBOO_PLANKS).offerTo(exporter);
		Recipes.Make2x2(BAMBOO_CRAFTING_TABLE, BAMBOO_PLANKS).offerTo(exporter);
		Recipes.MakeLadder(BAMBOO_LADDER, Items.BAMBOO).offerTo(exporter);
		Recipes.MakeWoodcutter(BAMBOO_WOODCUTTER, BAMBOO_PLANKS).offerTo(exporter);
		Recipes.MakeBarrel(BAMBOO_BARREL, BAMBOO_PLANKS, BAMBOO_SLAB).offerTo(exporter);
		Recipes.MakeLectern(BAMBOO_LECTERN, BAMBOO_SLAB).offerTo(exporter);
		Recipes.MakePowderKeg(BAMBOO_POWDER_KEG, BAMBOO_BARREL).offerTo(exporter);
		Recipes.MakeSlab(BAMBOO_BLOCK_SLAB, BAMBOO_BLOCK).offerTo(exporter);
		OfferWoodcuttingRecipe(exporter, BAMBOO_BLOCK_SLAB, BAMBOO_BLOCK, 2);
		Recipes.MakeSlab(STRIPPED_BAMBOO_BLOCK_SLAB, STRIPPED_BAMBOO_BLOCK).offerTo(exporter);
		OfferWoodcuttingRecipe(exporter, STRIPPED_BAMBOO_BLOCK_SLAB, STRIPPED_BAMBOO_BLOCK, 2);
		Recipes.Make3x2(BAMBOO_ROW, BAMBOO_BLOCK, 16).offerTo(exporter);
		//Dried
		Recipes.MakeCampfireRecipe(DRIED_BAMBOO, Items.BAMBOO, 600, 0.35f).offerTo(exporter);
		Recipes.Make3x3(DRIED_BAMBOO_BLOCK, DRIED_BAMBOO).offerTo(exporter);
		Recipes.MakeTorch(DRIED_BAMBOO_TORCH, DRIED_BAMBOO, 4).offerTo(exporter);
		Recipes.MakeSoulTorch(DRIED_BAMBOO_SOUL_TORCH, DRIED_BAMBOO, 4).offerTo(exporter);
		Recipes.MakeEnderTorch(DRIED_BAMBOO_ENDER_TORCH, DRIED_BAMBOO, 4).offerTo(exporter);
		Recipes.MakeUnderwaterTorch(UNDERWATER_DRIED_BAMBOO_TORCH, DRIED_BAMBOO, 4).offerTo(exporter);
		Recipes.MakeLadder(DRIED_BAMBOO_LADDER, DRIED_BAMBOO).offerTo(exporter);
		Recipes.MakeSlab(DRIED_BAMBOO_BLOCK_SLAB, DRIED_BAMBOO_BLOCK).offerTo(exporter);
		OfferWoodcuttingRecipe(exporter, DRIED_BAMBOO_BLOCK_SLAB, DRIED_BAMBOO_BLOCK, 2);
		Recipes.MakeSlab(STRIPPED_DRIED_BAMBOO_BLOCK_SLAB, STRIPPED_DRIED_BAMBOO_BLOCK).offerTo(exporter);
		OfferWoodcuttingRecipe(exporter, STRIPPED_DRIED_BAMBOO_BLOCK_SLAB, STRIPPED_DRIED_BAMBOO_BLOCK, 2);
		Recipes.Make3x2(DRIED_BAMBOO_ROW, DRIED_BAMBOO_BLOCK, 16).offerTo(exporter);
		Recipes.MakeHammer(BAMBOO_HAMMER, BAMBOO_BLOCK).offerTo(exporter);
		Recipes.MakeHammer(DRIED_BAMBOO_HAMMER, DRIED_BAMBOO_BLOCK).offerTo(exporter);
		//</editor-fold>
		//<editor-fold desc="Backport 1.19">
		Recipes.Make3x1(RECOVERY_COMPASS, ECHO_SHARD).input('C', Items.COMPASS).pattern("#C#").pattern("###").offerTo(exporter);
		//</editor-fold>
		//<editor-fold desc="Extended Echo">
		Recipes.MakeShapeless(ECHO_SHARD, ECHO_BLOCK, 4).offerTo(exporter, ID("echo_shard_from_echo_block"));
		Recipes.MakeSlab(ECHO_SLAB, ECHO_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, ECHO_SLAB, ECHO_BLOCK, 2);
		Recipes.MakeStairs(ECHO_STAIRS, ECHO_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, ECHO_STAIRS, ECHO_BLOCK);
		Recipes.MakeWall(ECHO_WALL, ECHO_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, ECHO_WALL, ECHO_BLOCK);
		//Crystal Block
		Recipes.MakeShapeless(ECHO_SHARD, ECHO_CRYSTAL_BLOCK, 9).offerTo(exporter, ID("echo_shard_from_echo_crystal_block"));
		Recipes.Make3x3(ECHO_CRYSTAL_BLOCK, ECHO_SHARD).offerTo(exporter);
		Recipes.MakeSlab(ECHO_CRYSTAL_SLAB, ECHO_CRYSTAL_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, ECHO_CRYSTAL_SLAB, ECHO_CRYSTAL_BLOCK, 2);
		Recipes.MakeStairs(ECHO_CRYSTAL_STAIRS, ECHO_CRYSTAL_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, ECHO_CRYSTAL_STAIRS, ECHO_CRYSTAL_BLOCK);
		Recipes.MakeWall(ECHO_CRYSTAL_WALL, ECHO_CRYSTAL_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, ECHO_CRYSTAL_WALL, ECHO_CRYSTAL_BLOCK);
		//Bricks
		Recipes.Make2x2(ECHO_BRICKS, ECHO_BLOCK, 4).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, ECHO_BRICKS, ECHO_BLOCK);
		Recipes.MakeSlab(ECHO_BRICK_SLAB, ECHO_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, ECHO_BRICK_SLAB, ECHO_BLOCK, 2);
		OfferStonecuttingRecipe(exporter, ECHO_BRICK_SLAB, ECHO_BRICKS, 2);
		Recipes.MakeStairs(ECHO_BRICK_STAIRS, ECHO_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, ECHO_BRICK_STAIRS, ECHO_BLOCK);
		OfferStonecuttingRecipe(exporter, ECHO_BRICK_STAIRS, ECHO_BRICKS);
		Recipes.MakeWall(ECHO_BRICK_WALL, ECHO_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, ECHO_BRICK_WALL, ECHO_BLOCK);
		OfferStonecuttingRecipe(exporter, ECHO_BRICK_WALL, ECHO_BRICKS);
		//Armor & Tools
		Recipes.MakeAxe(ECHO_AXE, ECHO_SHARD).offerTo(exporter);
		Recipes.MakeHammer(ECHO_HAMMER, ECHO_CRYSTAL_BLOCK).offerTo(exporter);
		Recipes.MakeHoe(ECHO_HOE, ECHO_SHARD).offerTo(exporter);
		Recipes.MakeKnife(ECHO_KNIFE, ECHO_SHARD).offerTo(exporter);
		Recipes.MakePickaxe(ECHO_PICKAXE, ECHO_SHARD).offerTo(exporter);
		Recipes.MakeShovel(ECHO_SHOVEL, ECHO_SHARD).offerTo(exporter);
		Recipes.MakeSword(ECHO_SWORD, ECHO_SHARD).offerTo(exporter);
		//</editor-fold>
		Recipes.MakeShaped(CHUM, Items.ROTTEN_FLESH, 3).input('B', Items.BONE_MEAL).pattern("###").pattern("#B#").pattern("###").offerTo(exporter);
		//<editor-fold desc="Sculk Stone">
		OfferSmeltingRecipe(exporter, SCULK_STONE, COBBLED_SCULK_STONE);
		Recipes.MakeStairs(SCULK_STONE_STAIRS, SCULK_STONE).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, SCULK_STONE_STAIRS, SCULK_STONE);
		OfferSmeltingRecipe(exporter, SCULK_STONE_STAIRS, COBBLED_SCULK_STONE_STAIRS);
		Recipes.MakeSlab(SCULK_STONE_SLAB, SCULK_STONE).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, SCULK_STONE_SLAB, SCULK_STONE, 2);
		OfferSmeltingRecipe(exporter, SCULK_STONE_SLAB, COBBLED_SCULK_STONE_SLAB);
		Recipes.MakeWall(SCULK_STONE_WALL, SCULK_STONE).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, SCULK_STONE_WALL, SCULK_STONE);
		OfferSmeltingRecipe(exporter, SCULK_STONE_WALL, COBBLED_SCULK_STONE_WALL);
		//Cobbled
		Recipes.MakeSlab(COBBLED_SCULK_STONE_SLAB, COBBLED_SCULK_STONE).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, COBBLED_SCULK_STONE_SLAB, SCULK_STONE, 2);
		Recipes.MakeStairs(COBBLED_SCULK_STONE_STAIRS, COBBLED_SCULK_STONE).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, COBBLED_SCULK_STONE_STAIRS, SCULK_STONE);
		Recipes.MakeWall(COBBLED_SCULK_STONE_WALL, COBBLED_SCULK_STONE).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, COBBLED_SCULK_STONE_WALL, SCULK_STONE);
		//Bricks
		Recipes.Make2x2(SCULK_STONE_BRICKS, SCULK_STONE, 4).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, SCULK_STONE_BRICKS, SCULK_STONE);
		Recipes.MakeSlab(SCULK_STONE_BRICK_SLAB, SCULK_STONE_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, SCULK_STONE_BRICK_SLAB, SCULK_STONE, 2);
		OfferStonecuttingRecipe(exporter, SCULK_STONE_BRICK_SLAB, SCULK_STONE_BRICKS, 2);
		Recipes.MakeStairs(SCULK_STONE_BRICK_STAIRS, SCULK_STONE_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, SCULK_STONE_BRICK_STAIRS, SCULK_STONE);
		OfferStonecuttingRecipe(exporter, SCULK_STONE_BRICK_STAIRS, SCULK_STONE_BRICKS);
		Recipes.MakeWall(SCULK_STONE_BRICK_WALL, SCULK_STONE_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, SCULK_STONE_BRICK_WALL, SCULK_STONE);
		OfferStonecuttingRecipe(exporter, SCULK_STONE_BRICK_WALL, SCULK_STONE_BRICKS);
		//Chiseled Bricks
		Recipes.Make1x2(CHISELED_SCULK_STONE_BRICKS, SCULK_STONE_BRICK_SLAB).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, CHISELED_SCULK_STONE_BRICKS, SCULK_STONE);
		OfferStonecuttingRecipe(exporter, CHISELED_SCULK_STONE_BRICKS, SCULK_STONE_BRICKS);
		OfferStonecuttingRecipe(exporter, SMOOTH_CHISELED_SCULK_STONE_BRICKS, CHISELED_SCULK_STONE_BRICKS);
		//Tiles
		Recipes.Make2x2(SCULK_STONE_TILES, SCULK_STONE_BRICKS, 4).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, SCULK_STONE_TILES, SCULK_STONE);
		OfferStonecuttingRecipe(exporter, SCULK_STONE_TILES, SCULK_STONE_BRICKS);
		Recipes.MakeSlab(SCULK_STONE_TILE_SLAB, SCULK_STONE_TILES).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, SCULK_STONE_TILE_SLAB, SCULK_STONE, 2);
		OfferStonecuttingRecipe(exporter, SCULK_STONE_TILE_SLAB, SCULK_STONE_BRICKS, 2);
		OfferStonecuttingRecipe(exporter, SCULK_STONE_TILE_SLAB, SCULK_STONE_TILES, 2);
		Recipes.MakeStairs(SCULK_STONE_TILE_STAIRS, SCULK_STONE_TILES).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, SCULK_STONE_TILE_STAIRS, SCULK_STONE);
		OfferStonecuttingRecipe(exporter, SCULK_STONE_TILE_STAIRS, SCULK_STONE_BRICKS);
		OfferStonecuttingRecipe(exporter, SCULK_STONE_TILE_STAIRS, SCULK_STONE_TILES);
		Recipes.MakeWall(SCULK_STONE_TILE_WALL, SCULK_STONE_TILES).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, SCULK_STONE_TILE_WALL, SCULK_STONE);
		OfferStonecuttingRecipe(exporter, SCULK_STONE_TILE_WALL, SCULK_STONE_BRICKS);
		OfferStonecuttingRecipe(exporter, SCULK_STONE_TILE_WALL, SCULK_STONE_TILES);
		//Smooth
		OfferSmeltingRecipe(exporter, Items.SMOOTH_STONE_SLAB, Items.STONE_SLAB);
		Recipes.MakeStairs(SMOOTH_STONE_STAIRS, Items.SMOOTH_STONE).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, SMOOTH_STONE_STAIRS, Items.SMOOTH_STONE);
		OfferSmeltingRecipe(exporter, SMOOTH_STONE_STAIRS, Items.STONE_STAIRS);
		//</editor-fold>
		//<editor-fold desc="Flowers">
		Recipes.MakeShapeless(Items.MAGENTA_DYE, AMARANTH, 2).offerTo(exporter, ID("amaranth"));
		Recipes.MakeShapeless(Items.LIGHT_BLUE_DYE, BLUE_ROSE_BUSH, 2).offerTo(exporter, ID("blue_rose_bush"));
		Recipes.MakeShapeless(Items.MAGENTA_DYE, TALL_ALLIUM, 2).offerTo(exporter, ID("tall_allium"));
		Recipes.MakeShapeless(Items.PINK_DYE, TALL_PINK_ALLIUM, 2).offerTo(exporter, ID("tall_pink_allium"));
		Recipes.MakeShapeless(Items.YELLOW_DYE, BUTTERCUP).offerTo(exporter, ID("buttercup"));
		Recipes.MakeShapeless(Items.PINK_DYE, PINK_DAISY).offerTo(exporter, ID("pink_daisy"));
		Recipes.MakeShapeless(Items.RED_DYE, ROSE).offerTo(exporter, ID("rose"));
		Recipes.MakeShapeless(Items.LIGHT_BLUE_DYE, BLUE_ROSE).offerTo(exporter, ID("blue_rose"));
		Recipes.MakeShapeless(Items.MAGENTA_DYE, MAGENTA_TULIP).offerTo(exporter, ID("magenta_tulip"));
		Recipes.MakeShapeless(Items.ORANGE_DYE, MARIGOLD).offerTo(exporter, ID("marigold"));
		Recipes.MakeShapeless(Items.MAGENTA_DYE, INDIGO_ORCHID).offerTo(exporter, ID("indigo_orchid"));
		Recipes.MakeShapeless(Items.MAGENTA_DYE, MAGENTA_ORCHID).offerTo(exporter, ID("magenta_orchid"));
		Recipes.MakeShapeless(Items.ORANGE_DYE, ORANGE_ORCHID).offerTo(exporter, ID("orange_orchid"));
		Recipes.MakeShapeless(Items.PURPLE_DYE, PURPLE_ORCHID).offerTo(exporter, ID("purple_orchid"));
		Recipes.MakeShapeless(Items.WHITE_DYE, WHITE_ORCHID).offerTo(exporter, ID("white_orchid"));
		Recipes.MakeShapeless(Items.YELLOW_DYE, YELLOW_ORCHID).offerTo(exporter, ID("yellow_orchid"));
		Recipes.MakeShapeless(Items.PINK_DYE, PINK_ALLIUM).offerTo(exporter, ID("pink_allium"));
		Recipes.MakeShapeless(Items.MAGENTA_DYE, HYDRANGEA).offerTo(exporter, ID("hydrangea"));
		Recipes.MakeShapeless(Items.PURPLE_DYE, LAVENDER).offerTo(exporter, ID("lavender"));
		Recipes.MakeShapeless(Items.PINK_DYE, PAEONIA).offerTo(exporter, ID("paeonia"));
		Recipes.MakeShapeless(Items.PINK_DYE, ASTER).offerTo(exporter, ID("aster"));
		Recipes.MakeShapeless(Items.LIGHT_GRAY_DYE, VANILLA_FLOWER).offerTo(exporter, ID("vanilla_flower"));
		//</editor-fold>
		//<editor-fold desc="Flower Parts">
		//<editor-fold desc="Minecraft Earth">
		OfferCuttingBoardRecipe(exporter, BUTTERCUP, BUTTERCUP_PARTS);
		OfferCuttingBoardRecipe(exporter, PINK_DAISY, PINK_DAISY_PARTS);
		//</editor-fold>
		OfferCuttingBoardRecipe(exporter, ROSE, ROSE_PARTS);
		OfferCuttingBoardRecipe(exporter, BLUE_ROSE, BLUE_ROSE_PARTS);
		OfferCuttingBoardRecipe(exporter, MAGENTA_TULIP, MAGENTA_TULIP_PARTS);
		OfferCuttingBoardRecipe(exporter, MARIGOLD, MARIGOLD_PARTS);
		//<editor-fold desc="Orchids">
		OfferCuttingBoardRecipe(exporter, INDIGO_ORCHID, INDIGO_ORCHID_PARTS);
		OfferCuttingBoardRecipe(exporter, MAGENTA_ORCHID, MAGENTA_ORCHID_PARTS);
		OfferCuttingBoardRecipe(exporter, ORANGE_ORCHID, ORANGE_ORCHID_PARTS);
		OfferCuttingBoardRecipe(exporter, PURPLE_ORCHID, PURPLE_ORCHID_PARTS);
		OfferCuttingBoardRecipe(exporter, WHITE_ORCHID, WHITE_ORCHID_PARTS);
		OfferCuttingBoardRecipe(exporter, YELLOW_ORCHID, YELLOW_ORCHID_PARTS);
		//</editor-fold>
		OfferCuttingBoardRecipe(exporter, PINK_ALLIUM, PINK_ALLIUM_PARTS);
		OfferCuttingBoardRecipe(exporter, HYDRANGEA, HYDRANGEA_PARTS);
		OfferCuttingBoardRecipe(exporter, LAVENDER, LAVENDER_PARTS);
		OfferCuttingBoardRecipe(exporter, PAEONIA, PAEONIA_PARTS);
		OfferCuttingBoardRecipe(exporter, ASTER, ASTER_PARTS);
		//<editor-fold desc="Tall">
		OfferCuttingBoardRecipe(exporter, AMARANTH, AMARANTH_PARTS, AMARANTH_PARTS.petalsItem());
		OfferCuttingBoardRecipe(exporter, BLUE_ROSE_BUSH, BLUE_ROSE_PARTS, BLUE_ROSE_PARTS);
		OfferCuttingBoardRecipe(exporter, TALL_ALLIUM, ALLIUM_PARTS, ALLIUM_PARTS);
		OfferCuttingBoardRecipe(exporter, TALL_PINK_ALLIUM, PINK_ALLIUM_PARTS, PINK_ALLIUM_PARTS);
		//</editor-fold>
		OfferCuttingBoardRecipe(exporter, VANILLA_FLOWER, VANILLA_PARTS, VANILLA);
		//<editor-fold desc="Vanilla Minecraft">
		OfferCuttingBoardRecipe(exporter, Items.ALLIUM, ALLIUM_PARTS);
		OfferCuttingBoardRecipe(exporter, Items.AZURE_BLUET, AZURE_BLUET_PARTS);
		OfferCuttingBoardRecipe(exporter, Items.BLUE_ORCHID, BLUE_ORCHID_PARTS);
		OfferCuttingBoardRecipe(exporter, Items.CORNFLOWER, CORNFLOWER_PARTS);
		OfferCuttingBoardRecipe(exporter, Items.DANDELION, DANDELION_PARTS);
		OfferCuttingBoardRecipe(exporter, Items.LILAC, LILAC_PARTS);
		OfferCuttingBoardRecipe(exporter, Items.LILY_OF_THE_VALLEY, LILY_OF_THE_VALLEY_PARTS);
		OfferCuttingBoardRecipe(exporter, Items.ORANGE_TULIP, ORANGE_TULIP_PARTS);
		OfferCuttingBoardRecipe(exporter, Items.OXEYE_DAISY, OXEYE_DAISY_PARTS);
		OfferCuttingBoardRecipe(exporter, Items.PEONY, PEONY_PARTS);
		OfferCuttingBoardRecipe(exporter, Items.PINK_TULIP, PINK_TULIP_PARTS);
		OfferCuttingBoardRecipe(exporter, Items.POPPY, POPPY_PARTS);
		OfferCuttingBoardRecipe(exporter, Items.RED_TULIP, RED_TULIP_PARTS);
		OfferCuttingBoardRecipe(exporter, Items.SUNFLOWER, SUNFLOWER_PARTS);
		OfferCuttingBoardRecipe(exporter, Items.WHITE_TULIP, WHITE_TULIP_PARTS);
		OfferCuttingBoardRecipe(exporter, Items.WITHER_ROSE, WITHER_ROSE_PARTS);
		//</editor-fold>
		//Special Petals
		OfferCuttingBoardRecipe(exporter, CHERRY_LEAVES, PINK_PETALS, PINK_PETALS);
		OfferCuttingBoardRecipe(exporter, Items.AZALEA_LEAVES_FLOWERS, Items.AZALEA_LEAVES, AZALEA_PETALS);
		OfferCuttingBoardRecipe(exporter, Items.SPORE_BLOSSOM, SPORE_BLOSSOM_PETAL, SPORE_BLOSSOM_PETAL, SPORE_BLOSSOM_PETAL, SPORE_BLOSSOM_PETAL);
		OfferCuttingBoardRecipe(exporter, FLOWERING_CASSIA_LEAVES, CASSIA_LEAVES, CASSIA_PETALS);
		OfferCuttingBoardRecipe(exporter, PINK_DOGWOOD_LEAVES, PINK_DOGWOOD_PETALS, PINK_DOGWOOD_PETALS);
		OfferCuttingBoardRecipe(exporter, PALE_DOGWOOD_LEAVES, PALE_DOGWOOD_PETALS, PALE_DOGWOOD_PETALS);
		OfferCuttingBoardRecipe(exporter, WHITE_DOGWOOD_LEAVES, WHITE_DOGWOOD_PETALS, WHITE_DOGWOOD_PETALS);
		//Seed-only
		OfferCuttingBoardRecipe(exporter, TORCHFLOWER, TORCHFLOWER_CROP.asItem(), TORCHFLOWER_CROP.asItem());
		OfferCuttingBoardRecipe(exporter, PITCHER_PLANT, PITCHER_CROP.asItem(), PITCHER_CROP.asItem());
		//</editor-fold>
		//<editor-fold desc="Flower Parts (Petals to Dye)">
		//<editor-fold desc="Minecraft Earth">
		Recipes.MakeShapeless(Items.YELLOW_DYE, BUTTERCUP_PARTS.petalsItem()).offerTo(exporter, ID("buttercup_petals"));
		Recipes.MakeShapeless(Items.PINK_DYE, PINK_DAISY_PARTS.petalsItem()).offerTo(exporter, ID("pink_daisy_petals"));
		//</editor-fold>
		Recipes.MakeShapeless(Items.RED_DYE, ROSE_PARTS.petalsItem()).offerTo(exporter, ID("rose_petals"));
		Recipes.MakeShapeless(Items.LIGHT_BLUE_DYE, BLUE_ROSE_PARTS.petalsItem()).offerTo(exporter, ID("blue_rose_petals"));
		Recipes.MakeShapeless(Items.MAGENTA_DYE, MAGENTA_TULIP_PARTS.petalsItem()).offerTo(exporter, ID("magenta_tulip_petals"));
		Recipes.MakeShapeless(Items.ORANGE_DYE, MARIGOLD_PARTS.petalsItem()).offerTo(exporter, ID("marigold_petals"));
		//<editor-fold desc="Orchids">
		Recipes.MakeShapeless(Items.MAGENTA_DYE, INDIGO_ORCHID_PARTS.petalsItem()).offerTo(exporter, ID("indigo_orchid_petals"));
		Recipes.MakeShapeless(Items.MAGENTA_DYE, MAGENTA_ORCHID_PARTS.petalsItem()).offerTo(exporter, ID("magenta_orchid_petals"));
		Recipes.MakeShapeless(Items.ORANGE_DYE, ORANGE_ORCHID_PARTS.petalsItem()).offerTo(exporter, ID("orange_orchid_petals"));
		Recipes.MakeShapeless(Items.PURPLE_DYE, PURPLE_ORCHID_PARTS.petalsItem()).offerTo(exporter, ID("purple_orchid_petals"));
		Recipes.MakeShapeless(Items.WHITE_DYE, WHITE_ORCHID_PARTS.petalsItem()).offerTo(exporter, ID("white_orchid_petals"));
		Recipes.MakeShapeless(Items.YELLOW_DYE, YELLOW_ORCHID_PARTS.petalsItem()).offerTo(exporter, ID("yellow_orchid_petals"));
		//</editor-fold>
		Recipes.MakeShapeless(Items.PINK_DYE, PINK_ALLIUM_PARTS.petalsItem()).offerTo(exporter, ID("pink_allium_petals"));
		Recipes.MakeShapeless(Items.MAGENTA_DYE, HYDRANGEA_PARTS.petalsItem()).offerTo(exporter, ID("hydrangea_petals"));
		Recipes.MakeShapeless(Items.PURPLE_DYE, LAVENDER_PARTS.petalsItem()).offerTo(exporter, ID("lavender_petals"));
		Recipes.MakeShapeless(Items.PINK_DYE, PAEONIA_PARTS.petalsItem()).offerTo(exporter, ID("paeonia_petals"));
		Recipes.MakeShapeless(Items.PINK_DYE, ASTER_PARTS.petalsItem()).offerTo(exporter, ID("aster_petals"));
		//<editor-fold desc="Tall">
		Recipes.MakeShapeless(Items.MAGENTA_DYE, AMARANTH_PARTS.petalsItem(), 2).offerTo(exporter, ID("amaranth_petals"));
		//</editor-fold>
		//<editor-fold desc="Vanilla Minecraft">
		Recipes.MakeShapeless(Items.MAGENTA_DYE, ALLIUM_PARTS.petalsItem()).offerTo(exporter, ID("allium_petals"));
		Recipes.MakeShapeless(Items.LIGHT_GRAY_DYE, AZURE_BLUET_PARTS.petalsItem()).offerTo(exporter, ID("azure_bluet_petals"));
		Recipes.MakeShapeless(Items.LIGHT_BLUE_DYE, BLUE_ORCHID_PARTS.petalsItem()).offerTo(exporter, ID("blue_orchid"));
		Recipes.MakeShapeless(Items.BLUE_DYE, CORNFLOWER_PARTS.petalsItem()).offerTo(exporter, ID("cornflower"));
		Recipes.MakeShapeless(Items.YELLOW_DYE, DANDELION_PARTS.petalsItem()).offerTo(exporter, ID("dandelion"));
		Recipes.MakeShapeless(Items.MAGENTA_DYE, LILAC_PARTS.petalsItem()).offerTo(exporter, ID("lilac"));
		Recipes.MakeShapeless(Items.WHITE_DYE, LILY_OF_THE_VALLEY_PARTS.petalsItem()).offerTo(exporter, ID("lily_of_the_valley"));
		Recipes.MakeShapeless(Items.ORANGE_DYE, ORANGE_TULIP_PARTS.petalsItem()).offerTo(exporter, ID("orange_tulip"));
		Recipes.MakeShapeless(Items.LIGHT_GRAY_DYE, OXEYE_DAISY_PARTS.petalsItem()).offerTo(exporter, ID("oxeye_daisy_petals"));
		Recipes.MakeShapeless(Items.PINK_DYE, PEONY_PARTS.petalsItem()).offerTo(exporter, ID("peony_petals"));
		Recipes.MakeShapeless(Items.PINK_DYE, PINK_TULIP_PARTS.petalsItem()).offerTo(exporter, ID("pink_tulip_petals"));
		Recipes.MakeShapeless(Items.RED_DYE, POPPY_PARTS.petalsItem()).offerTo(exporter, ID("poppy_petals"));
		Recipes.MakeShapeless(Items.RED_DYE, RED_TULIP_PARTS.petalsItem()).offerTo(exporter, ID("red_tulip_petals"));
		Recipes.MakeShapeless(Items.YELLOW_DYE, SUNFLOWER_PARTS.petalsItem()).offerTo(exporter, ID("sunflower_petals"));
		Recipes.MakeShapeless(Items.LIGHT_GRAY_DYE, WHITE_TULIP_PARTS.petalsItem()).offerTo(exporter, ID("white_tulip_petals"));
		Recipes.MakeShapeless(Items.BLACK_DYE, WITHER_ROSE_PARTS.petalsItem()).offerTo(exporter, ID("wither_rose_petals"));
		//</editor-fold>
		//Special Petals
		Recipes.MakeShapeless(Items.MAGENTA_DYE, AZALEA_PETALS).offerTo(exporter, ID("azalea_petals"));
		Recipes.MakeShapeless(Items.PINK_DYE, SPORE_BLOSSOM_PETAL).offerTo(exporter, ID("spore_blossom_petal"));
		Recipes.MakeShapeless(Items.YELLOW_DYE, CASSIA_PETALS).offerTo(exporter, ID("cassia_petals"));
		Recipes.MakeShapeless(Items.PINK_DYE, PINK_DOGWOOD_PETALS).offerTo(exporter, ID("pink_dogwood_petals"));
		Recipes.MakeShapeless(Items.PINK_DYE, PALE_DOGWOOD_PETALS).offerTo(exporter, ID("pale_dogwood_petals"));
		Recipes.MakeShapeless(Items.WHITE_DYE, WHITE_DOGWOOD_PETALS).offerTo(exporter, ID("white_dogwood_petals"));
		//</editor-fold>
		Recipes.MakeShaped(POUCH, Items.LEATHER).input('S', Items.STRING).pattern("SSS").pattern("# #").pattern("###").offerTo(exporter);
		//<editor-fold desc="Sandy Blocks">
		Recipes.MakeSandy(SANDY_COBBLESTONE, Items.COBBLESTONE).offerTo(exporter);
		Recipes.MakeSandy(SANDY_POLISHED_ANDESITE, Items.POLISHED_ANDESITE).offerTo(exporter);
		Recipes.MakeSandy(SANDY_ANDESITE_BRICKS, ANDESITE_BRICKS).offerTo(exporter);
		Recipes.MakeSandy(SANDY_CUT_POLISHED_ANDESITE, CUT_POLISHED_ANDESITE).offerTo(exporter);
		Recipes.MakeSandy(SANDY_POLISHED_DIORITE, Items.POLISHED_DIORITE).offerTo(exporter);
		Recipes.MakeSandy(SANDY_DIORITE_BRICKS, DIORITE_BRICKS).offerTo(exporter);
		Recipes.MakeSandy(SANDY_CUT_POLISHED_DIORITE, CUT_POLISHED_DIORITE).offerTo(exporter);
		Recipes.MakeSandy(SANDY_POLISHED_GRANITE, Items.POLISHED_GRANITE).offerTo(exporter);
		Recipes.MakeSandy(SANDY_GRANITE_BRICKS, GRANITE_BRICKS).offerTo(exporter);
		Recipes.MakeSandy(SANDY_CUT_POLISHED_GRANITE, CUT_POLISHED_GRANITE).offerTo(exporter);
		//Planks
		Recipes.MakeSandy(SANDY_ACACIA_PLANKS, Items.ACACIA_PLANKS).offerTo(exporter);
		Recipes.MakeSandy(SANDY_BIRCH_PLANKS, Items.BIRCH_PLANKS).offerTo(exporter);
		Recipes.MakeSandy(SANDY_DARK_OAK_PLANKS, Items.DARK_OAK_PLANKS).offerTo(exporter);
		Recipes.MakeSandy(SANDY_JUNGLE_PLANKS, Items.JUNGLE_PLANKS).offerTo(exporter);
		Recipes.MakeSandy(SANDY_OAK_PLANKS, Items.OAK_PLANKS).offerTo(exporter);
		Recipes.MakeSandy(SANDY_SPRUCE_PLANKS, Items.SPRUCE_PLANKS).offerTo(exporter);
		Recipes.MakeSandy(SANDY_CRIMSON_PLANKS, Items.CRIMSON_PLANKS).offerTo(exporter);
		Recipes.MakeSandy(SANDY_WARPED_PLANKS, Items.WARPED_PLANKS).offerTo(exporter);
		Recipes.MakeSandy(SANDY_MANGROVE_PLANKS, MANGROVE_PLANKS).offerTo(exporter);
		Recipes.MakeSandy(SANDY_CHERRY_PLANKS, CHERRY_PLANKS).offerTo(exporter);
		Recipes.MakeSandy(SANDY_CHARRED_PLANKS, CHARRED_PLANKS).offerTo(exporter);
		//Prismarine
		Recipes.MakeSandy(SANDY_PRISMARINE, Items.PRISMARINE).offerTo(exporter);
		Recipes.MakeSandy(SANDY_SANDY_PRISMARINE, SANDY_PRISMARINE).offerTo(exporter);
		Recipes.MakeSandy(SANDY_PRISMARINE_BRICKS, Items.PRISMARINE_BRICKS).offerTo(exporter);
		Recipes.MakeSandy(SANDY_CUT_PRISMARINE_BRICKS, CUT_PRISMARINE_BRICKS).offerTo(exporter);
		Recipes.MakeSandy(SANDY_DARK_PRISMARINE, Items.DARK_PRISMARINE).offerTo(exporter);
		Recipes.MakeSandy(SANDY_SANDY_DARK_PRISMARINE, SANDY_DARK_PRISMARINE).offerTo(exporter);
		//Purpur
		Recipes.MakeSandy(SANDY_PURPUR_BLOCK, Items.PURPUR_BLOCK).offerTo(exporter);
		Recipes.MakeSandy(SANDY_CHISELED_PURPUR, CHISELED_PURPUR).offerTo(exporter);
		Recipes.MakeSandy(SANDY_SANDY_CHISELED_PURPUR, SANDY_CHISELED_PURPUR).offerTo(exporter);
		Recipes.MakeSandy(SANDY_SMOOTH_PURPUR, SMOOTH_PURPUR).offerTo(exporter);
		Recipes.MakeSandy(SANDY_PURPUR_BRICKS, PURPUR_BRICKS).offerTo(exporter);
		//</editor-fold>

		if (ModConfig.REGISTER_HAVEN_MOD) {
			for (DyeColor color : DyeColor.values()) {
				Recipes.MakeShapeless(ColorUtil.GetDye(color), HavenMod.CARNATIONS.get(color))
						.offerTo(exporter, ID(color.getName() + "_dye_from_" + color.getName() + "_carnation"));
			}
		}
	}
}
