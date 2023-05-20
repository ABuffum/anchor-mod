package fun.mousewich.gen.data.tag;

import com.nhoryzon.mc.farmersdelight.registry.ItemsRegistry;
import com.nhoryzon.mc.farmersdelight.tag.Tags;
import fun.mousewich.container.ArrowContainer;
import fun.mousewich.container.BlockContainer;
import fun.mousewich.gen.data.ModDatagen;
import fun.mousewich.trim.ArmorTrimPattern;
import fun.mousewich.util.ColorUtil;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.tag.ItemTags;
import net.minecraft.tag.Tag;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import static fun.mousewich.ModBase.*;

public class ItemTagGenerator extends FabricTagProvider<Item> {
	public ItemTagGenerator(FabricDataGenerator dataGenerator) {
		super(dataGenerator, Registry.ITEM, "items", NAMESPACE + ":item_tag_generator");
	}

	private static Identifier betterend(String path) { return new Identifier("betterend", path); }
	private static Identifier betternether(String path) { return new Identifier("betternether", path); }
	private static Identifier croptopia(String path) { return new Identifier("croptopia", path); }

	@Override
	protected void generateTags() {
		for (Map.Entry<Tag.Identified<Item>, Set<Item>> entry : ModDatagen.Cache.Tags.ITEM_TAGS.entrySet()) {
			getOrCreateTagBuilder(entry.getKey()).add(entry.getValue().toArray(Item[]::new));
			entry.getValue().clear();
		}
		ModDatagen.Cache.Tags.ITEM_TAGS.clear();

		getOrCreateTagBuilder(ItemTags.ARROWS)
				.add(AMETHYST_ARROW.asItem(), BLINDING_ARROW.asItem(), BONE_ARROW.asItem(), CHORUS_ARROW.asItem())
				.add(ArrowContainer.ARROW_CONTAINERS.stream().map(ArrowContainer::asItem).toArray(Item[]::new));
//		getOrCreateTagBuilder(ItemTags.BEDS).add(GLOW_LICHEN_BED.asItem(), MOSS_BED.asItem(), RAINBOW_BED.asItem());
		getOrCreateTagBuilder(ItemTags.BOATS).addTag(ModItemTags.CHEST_BOATS);
		getOrCreateTagBuilder(ItemTags.BUTTONS)
				.add(NETHERITE_BUTTON.asItem());
		getOrCreateTagBuilder(ItemTags.CANDLES).add(SOUL_CANDLE.asItem(), ENDER_CANDLE.asItem(), NETHERRACK_CANDLE.asItem());
		getOrCreateTagBuilder(ItemTags.CARPETS).addTag(ModItemTags.WOOL_CARPETS).addTag(ModItemTags.FLEECE_CARPETS);
		getOrCreateTagBuilder(ItemTags.CLUSTER_MAX_HARVESTABLES).addTag(ModItemTags.PICKAXES);
		getOrCreateTagBuilder(ItemTags.FISHES).addTag(ModItemTags.RAW_FISH).addTag(ModItemTags.COOKED_FISH);
		getOrCreateTagBuilder(ItemTags.LEAVES)
				.add(MANGROVE_LEAVES.asItem(), CHERRY_LEAVES.asItem())
				.add(WHITE_SPRUCE_LEAVES.asItem(), SWEET_BERRY_LEAVES.asItem())
				.add(LIGHT_GREEN_ACACIA_LEAVES.asItem(), RED_ACACIA_LEAVES.asItem(), YELLOW_ACACIA_LEAVES.asItem())
				.add(LIGHT_GREEN_BIRCH_LEAVES.asItem(), RED_BIRCH_LEAVES.asItem(), YELLOW_BIRCH_LEAVES.asItem(), WHITE_BIRCH_LEAVES.asItem())
				.add(BLUE_GREEN_OAK_LEAVES.asItem(), LIGHT_GREEN_OAK_LEAVES.asItem(), RED_OAK_LEAVES.asItem(), YELLOW_OAK_LEAVES.asItem(), WHITE_OAK_LEAVES.asItem())
				.add(CASSIA_LEAVES.asItem(), FLOWERING_CASSIA_LEAVES.asItem())
				.add(PINK_DOGWOOD_LEAVES.asItem(), PALE_DOGWOOD_LEAVES.asItem(), WHITE_DOGWOOD_LEAVES.asItem());
		getOrCreateTagBuilder(ItemTags.LOGS).addTag(ModItemTags.CHARRED_LOGS);
		getOrCreateTagBuilder(ItemTags.LOGS_THAT_BURN)
				.addTag(ModItemTags.MANGROVE_LOGS)
				.addTag(ModItemTags.CHERRY_LOGS)
				.addTag(ModItemTags.CASSIA_LOGS)
				.addTag(ModItemTags.DOGWOOD_LOGS);
		getOrCreateTagBuilder(ItemTags.MUSIC_DISCS).add(MUSIC_DISC_OTHERSIDE).add(MUSIC_DISC_5).add(MUSIC_DISC_RELIC);
		getOrCreateTagBuilder(ItemTags.NON_FLAMMABLE_WOOD).add(CRIMSON_HANGING_SIGN.asItem(), WARPED_HANGING_SIGN.asItem());
		getOrCreateTagBuilder(ItemTags.PIGLIN_LOVED)
				.add(POLISHED_GILDED_BLACKSTONE.asItem(), POLISHED_GILDED_BLACKSTONE_BRICKS.asItem())
				.add(CHISELED_POLISHED_GILDED_BLACKSTONE.asItem(), CRACKED_POLISHED_GILDED_BLACKSTONE_BRICKS.asItem())
				.add(GILDED_BLACKSTONE_SLAB.asItem(), POLISHED_GILDED_BLACKSTONE_SLAB.asItem(), POLISHED_GILDED_BLACKSTONE_BRICK_SLAB.asItem())
				.add(GILDED_BLACKSTONE_STAIRS.asItem(), POLISHED_GILDED_BLACKSTONE_STAIRS.asItem(), POLISHED_GILDED_BLACKSTONE_BRICK_STAIRS.asItem())
				.add(GILDED_BLACKSTONE_WALL.asItem(), POLISHED_GILDED_BLACKSTONE_WALL.asItem(), POLISHED_GILDED_BLACKSTONE_BRICK_WALL.asItem())
				.add(GOLD_BARS.asItem(), GOLD_CHAIN.asItem(), HEAVY_GOLD_CHAIN.asItem())
				.add(GOLD_LANTERN.asItem(), GOLD_ENDER_LANTERN.asItem())
				.add(GOLD_BUTTON.asItem())
				.add(GOLD_STAIRS.asItem(), GOLD_SLAB.asItem(), GOLD_WALL.asItem(), GOLD_BRICKS.asItem(), GOLD_BRICK_SLAB.asItem())
				.add(GOLD_BRICK_STAIRS.asItem(), GOLD_BRICK_WALL.asItem(), CUT_GOLD.asItem())
				.add(CUT_GOLD_PILLAR.asItem(), CUT_GOLD_SLAB.asItem(), CUT_GOLD_STAIRS.asItem())
				.add(CUT_GOLD_WALL.asItem(), GOLD_ROD);
		getOrCreateTagBuilder(ItemTags.PIGLIN_REPELLENTS).addTag(ModItemTags.SOUL_TORCHES).addTag(ModItemTags.SOUL_LANTERNS);
		getOrCreateTagBuilder(ItemTags.SMALL_FLOWERS)
				.add(BUTTERCUP.asItem(), PINK_DAISY.asItem(), ROSE.asItem(), BLUE_ROSE.asItem())
				.add(MAGENTA_TULIP.asItem(), MARIGOLD.asItem(), INDIGO_ORCHID.asItem(), MAGENTA_ORCHID.asItem())
				.add(ORANGE_ORCHID.asItem(), PURPLE_ORCHID.asItem(), RED_ORCHID.asItem(), WHITE_ORCHID.asItem())
				.add(YELLOW_ORCHID.asItem(), PINK_ALLIUM.asItem(), LAVENDER.asItem(), HYDRANGEA.asItem())
				.add(PAEONIA.asItem(), ASTER.asItem(), VANILLA_FLOWER.asItem());
		getOrCreateTagBuilder(ItemTags.TALL_FLOWERS)
				.add(AMARANTH.asItem(), BLUE_ROSE_BUSH.asItem(), TALL_ALLIUM.asItem(), TALL_PINK_ALLIUM.asItem(), TALL_VANILLA.asItem());
		getOrCreateTagBuilder(ItemTags.TRAPDOORS)
				.add(STAINED_GLASS_TRAPDOORS.values().stream().map(BlockContainer::asItem).toArray(Item[]::new))
				.add(GLASS_TRAPDOOR.asItem(), TINTED_GLASS_TRAPDOOR.asItem(), RUBY_GLASS_TRAPDOOR.asItem());
		getOrCreateTagBuilder(ItemTags.WOODEN_BUTTONS)
				.add(CHARRED_BUTTON.asItem())
				.add(MANGROVE_BUTTON.asItem(), BAMBOO_BUTTON.asItem(), CHERRY_BUTTON.asItem())
				.add(CASSIA_BUTTON.asItem(), DOGWOOD_BUTTON.asItem());
		getOrCreateTagBuilder(ItemTags.WOOL).add(RAINBOW_WOOL.asItem());

		getOrCreateTagBuilder(Tags.KNIVES).addTag(ModItemTags.KNIVES);

		getOrCreateTagBuilder(ModItemTags.AXES).add(ECHO_AXE)
				.add(Items.WOODEN_AXE, Items.STONE_AXE, Items.IRON_AXE, Items.GOLDEN_AXE, Items.DIAMOND_AXE, Items.NETHERITE_AXE)
				.addOptional(betternether("cincinnasite_axe")).addOptional(betternether("cincinnasite_axe_diamond"))
				.addOptional(betternether("nether_ruby_axe")).addOptional(betterend("aeternium_axe"))
				.addOptional(betterend("thallasium_axe")).addOptional(betterend("terminite_axe"));
		getOrCreateTagBuilder(ModItemTags.BEEHIVES).add(Items.BEEHIVE);
		getOrCreateTagBuilder(ModItemTags.BOOKS)
				.add(Items.BOOK, RED_BOOK, ORANGE_BOOK, YELLOW_BOOK, GREEN_BOOK, BLUE_BOOK, GRAY_BOOK);
		getOrCreateTagBuilder(ModItemTags.BOOKSHELF_BOOKS)
				.addTag(ModItemTags.BOOKS).add(UNREADABLE_BOOK)
				.add(Items.ENCHANTED_BOOK, Items.WRITABLE_BOOK, Items.WRITTEN_BOOK);
		getOrCreateTagBuilder(ModItemTags.BOOKSHELVES).add(Items.BOOKSHELF);
		getOrCreateTagBuilder(ModItemTags.BOOTS)
				.add(Items.LEATHER_BOOTS, Items.CHAINMAIL_BOOTS, Items.IRON_BOOTS, Items.GOLDEN_BOOTS, Items.DIAMOND_BOOTS, Items.NETHERITE_BOOTS)
				.add(AMETHYST_BOOTS, EMERALD_BOOTS, FLEECE_BOOTS, QUARTZ_BOOTS, SHULKER_BOOTS, STUDDED_LEATHER_BOOTS, TURTLE_BOOTS)
				.addTag(ModItemTags.COPPER_BOOTS)
				.addOptional(betternether("cincinnasite_boots")).addOptional(betternether("nether_ruby_boots"))
				.addOptional(betterend("aeternium_boots")).addOptional(betterend("crystalite_boots"))
				.addOptional(betterend("thallasium_boots")).addOptional(betterend("terminite_boots"));
		getOrCreateTagBuilder(ModItemTags.BREAKS_DECORATED_POTS).addTag(ModItemTags.TOOLS);
		getOrCreateTagBuilder(ModItemTags.CAMPFIRES).add(Items.CAMPFIRE);
		getOrCreateTagBuilder(ModItemTags.CANDY);
		getOrCreateTagBuilder(ModItemTags.CARVED_GOURDS).addTag(ModItemTags.CARVED_MELONS).addTag(ModItemTags.CARVED_PUMPKINS);
		getOrCreateTagBuilder(ModItemTags.CARVED_MELONS).add(CARVED_MELON.asItem());
		getOrCreateTagBuilder(ModItemTags.CARVED_PUMPKINS).add(Items.CARVED_PUMPKIN).add(CARVED_WHITE_PUMPKIN.asItem(), CARVED_ROTTEN_PUMPKIN.asItem());
		getOrCreateTagBuilder(ModItemTags.CASSIA_LOGS).add(CASSIA_LOG.asItem(), CASSIA_WOOD.asItem(), STRIPPED_CASSIA_LOG.asItem(), STRIPPED_CASSIA_WOOD.asItem());
		getOrCreateTagBuilder(ModItemTags.CHAINMAIL_ARMOR).add(Items.CHAINMAIL_HELMET, Items.CHAINMAIL_CHESTPLATE, Items.CHAINMAIL_LEGGINGS, Items.CHAINMAIL_BOOTS);
		getOrCreateTagBuilder(ModItemTags.CHARRABLE_LOG_SLABS)
				.add(ACACIA_LOG_SLAB.asItem(), BIRCH_LOG_SLAB.asItem(), DARK_OAK_LOG_SLAB.asItem())
				.add(JUNGLE_LOG_SLAB.asItem(), OAK_LOG_SLAB.asItem(), SPRUCE_LOG_SLAB.asItem())
				.add(CHERRY_LOG_SLAB.asItem(), MANGROVE_LOG_SLAB.asItem())
				.add(CASSIA_LOG_SLAB.asItem(), DOGWOOD_LOG_SLAB.asItem());
		getOrCreateTagBuilder(ModItemTags.CHARRABLE_LOGS)
				.add(Items.ACACIA_LOG, Items.BIRCH_LOG, Items.DARK_OAK_LOG)
				.add(Items.JUNGLE_LOG, Items.OAK_LOG, Items.SPRUCE_LOG)
				.add(CHERRY_LOG.asItem(), MANGROVE_LOG.asItem())
				.add(CASSIA_LOG.asItem(), DOGWOOD_LOG.asItem());
		getOrCreateTagBuilder(ModItemTags.CHARRABLE_STRIPPED_LOG_SLABS)
				.add(STRIPPED_ACACIA_LOG_SLAB.asItem(), STRIPPED_BIRCH_LOG_SLAB.asItem(), STRIPPED_DARK_OAK_LOG_SLAB.asItem())
				.add(STRIPPED_JUNGLE_LOG_SLAB.asItem(), STRIPPED_OAK_LOG_SLAB.asItem(), STRIPPED_SPRUCE_LOG_SLAB.asItem())
				.add(STRIPPED_CHERRY_LOG_SLAB.asItem(), STRIPPED_MANGROVE_LOG_SLAB.asItem())
				.add(STRIPPED_CASSIA_LOG_SLAB.asItem(), STRIPPED_DOGWOOD_LOG_SLAB.asItem());
		getOrCreateTagBuilder(ModItemTags.CHARRABLE_STRIPPED_LOGS)
				.add(Items.STRIPPED_ACACIA_LOG, Items.STRIPPED_BIRCH_LOG, Items.STRIPPED_DARK_OAK_LOG)
				.add(Items.STRIPPED_JUNGLE_LOG, Items.STRIPPED_OAK_LOG, Items.STRIPPED_SPRUCE_LOG)
				.add(STRIPPED_CHERRY_LOG.asItem(), STRIPPED_MANGROVE_LOG.asItem())
				.add(STRIPPED_CASSIA_LOG.asItem(), STRIPPED_DOGWOOD_LOG.asItem());
		getOrCreateTagBuilder(ModItemTags.CHARRABLE_STRIPPED_WOOD_SLABS)
				.add(STRIPPED_ACACIA_WOOD_SLAB.asItem(), STRIPPED_BIRCH_WOOD_SLAB.asItem(), STRIPPED_DARK_OAK_WOOD_SLAB.asItem())
				.add(STRIPPED_JUNGLE_WOOD_SLAB.asItem(), STRIPPED_OAK_WOOD_SLAB.asItem(), STRIPPED_SPRUCE_WOOD_SLAB.asItem())
				.add(STRIPPED_CHERRY_WOOD_SLAB.asItem(), STRIPPED_MANGROVE_WOOD_SLAB.asItem())
				.add(STRIPPED_CASSIA_WOOD_SLAB.asItem(), STRIPPED_DOGWOOD_WOOD_SLAB.asItem());
		getOrCreateTagBuilder(ModItemTags.CHARRABLE_STRIPPED_WOODS)
				.add(Items.STRIPPED_ACACIA_WOOD, Items.STRIPPED_BIRCH_WOOD, Items.STRIPPED_DARK_OAK_WOOD)
				.add(Items.STRIPPED_JUNGLE_WOOD, Items.STRIPPED_OAK_WOOD, Items.STRIPPED_SPRUCE_WOOD)
				.add(STRIPPED_CHERRY_WOOD.asItem(), STRIPPED_MANGROVE_WOOD.asItem())
				.add(STRIPPED_CASSIA_WOOD.asItem(), STRIPPED_DOGWOOD_WOOD.asItem());
		getOrCreateTagBuilder(ModItemTags.CHARRABLE_WOOD_SLABS)
				.add(ACACIA_WOOD_SLAB.asItem(), BIRCH_WOOD_SLAB.asItem(), DARK_OAK_WOOD_SLAB.asItem())
				.add(JUNGLE_WOOD_SLAB.asItem(), OAK_WOOD_SLAB.asItem(), SPRUCE_WOOD_SLAB.asItem())
				.add(CHERRY_WOOD_SLAB.asItem(), MANGROVE_WOOD_SLAB.asItem())
				.add(CASSIA_WOOD_SLAB.asItem(), DOGWOOD_WOOD_SLAB.asItem());
		getOrCreateTagBuilder(ModItemTags.CHARRABLE_WOODS)
				.add(Items.ACACIA_WOOD, Items.BIRCH_WOOD, Items.DARK_OAK_WOOD)
				.add(Items.JUNGLE_WOOD, Items.OAK_WOOD, Items.SPRUCE_WOOD)
				.add(CHERRY_WOOD.asItem(), MANGROVE_WOOD.asItem())
				.add(CASSIA_WOOD.asItem(), DOGWOOD_WOOD.asItem());
		getOrCreateTagBuilder(ModItemTags.CHARRED_LOGS).add(CHARRED_LOG.asItem(), CHARRED_WOOD.asItem(), STRIPPED_CHARRED_LOG.asItem(), STRIPPED_CHARRED_WOOD.asItem());
		getOrCreateTagBuilder(ModItemTags.CHERRY_LOGS).add(CHERRY_LOG.asItem(), CHERRY_WOOD.asItem(), STRIPPED_CHERRY_LOG.asItem(), STRIPPED_CHERRY_WOOD.asItem());
		getOrCreateTagBuilder(ModItemTags.CHEST_BOATS);
		getOrCreateTagBuilder(ModItemTags.CHESTPLATES)
				.add(Items.LEATHER_CHESTPLATE, Items.CHAINMAIL_CHESTPLATE, Items.IRON_CHESTPLATE, Items.GOLDEN_CHESTPLATE, Items.DIAMOND_CHESTPLATE, Items.NETHERITE_CHESTPLATE)
				.add(AMETHYST_CHESTPLATE, EMERALD_CHESTPLATE, FLEECE_CHESTPLATE, QUARTZ_CHESTPLATE, SHULKER_CHESTPLATE, STUDDED_LEATHER_CHESTPLATE, TURTLE_CHESTPLATE)
				.addTag(ModItemTags.COPPER_CHESTPLATES)
				.addOptional(betternether("cincinnasite_chestplate")).addOptional(betternether("nether_ruby_chestplate"))
				.addOptional(betterend("aeternium_chestplate")).addOptional(betterend("crystalite_chestplate"))
				.addOptional(betterend("thallasium_chestplate")).addOptional(betterend("terminite_chestplate"));
		getOrCreateTagBuilder(ModItemTags.COMPASSES).add(Items.COMPASS).add(RECOVERY_COMPASS);
		getOrCreateTagBuilder(ModItemTags.COOKED_BEEF)
				.add(Items.COOKED_BEEF)
				.add(ItemsRegistry.BEEF_PATTY.get(), ItemsRegistry.HAMBURGER.get(), ItemsRegistry.BEEF_STEW.get(), ItemsRegistry.STEAK_AND_POTATOES.get())
				.addOptional(croptopia("beef_jerky")).addOptional(croptopia("beef_stew"))
				.addOptional(croptopia("beef_stir_fry")).addOptional(croptopia("beef_wellington"))
				.addOptional(croptopia("cheeseburger")).addOptional(croptopia("hamburger"));
		getOrCreateTagBuilder(ModItemTags.COOKED_BIRD)
				.add(Items.COOKED_CHICKEN)
				.add(ItemsRegistry.COOKED_CHICKEN_CUTS.get(), ItemsRegistry.ROAST_CHICKEN.get(), ItemsRegistry.CHICKEN_SOUP.get())
				.addOptional(croptopia("lemon_chicken")).addOptional(croptopia("cashew_chicken"))
				.addOptional(croptopia("chicken_and_dumplings")).addOptional(croptopia("chicken_and_noodles"))
				.addOptional(croptopia("chicken_and_rice")).addOptional(croptopia("fried_chicken"));
		getOrCreateTagBuilder(ModItemTags.COOKED_FISH)
				.add(Items.COOKED_COD, Items.COOKED_SALMON).add(COOKED_PIRANHA)
				.add(ItemsRegistry.COOKED_COD_SLICE.get(), ItemsRegistry.COOKED_SALMON_SLICE.get())
				.add(ItemsRegistry.FISH_STEW.get(), ItemsRegistry.BAKED_COD_STEW.get(), ItemsRegistry.GRILLED_SALMON.get())
				.addOptional(croptopia("cooked_anchovy")).addOptional(croptopia("cooked_tuna"))
				.addOptional(croptopia("fish_and_chips")).addOptional(betterend("end_fish_cooked"));
		getOrCreateTagBuilder(ModItemTags.COOKED_MEAT)
				.addTag(ModItemTags.COOKED_BEEF).addTag(ModItemTags.COOKED_BIRD).addTag(ModItemTags.COOKED_FISH)
				.addTag(ModItemTags.COOKED_PORK).addTag(ModItemTags.COOKED_RABBIT).addTag(ModItemTags.COOKED_SEAFOOD)
				.addTag(ModItemTags.COOKED_SHEEP);
		getOrCreateTagBuilder(ModItemTags.COOKED_PORK)
				.add(Items.COOKED_PORKCHOP)
				.add(ItemsRegistry.COOKED_BACON.get(), ItemsRegistry.COOKED_BACON.get(), ItemsRegistry.SMOKED_HAM.get())
				.addOptional(croptopia("blt")).addOptional(croptopia("cooked_bacon"))
				.addOptional(croptopia("ham_sandwich")).addOptional(croptopia("pork_jerky"))
				.addOptional(croptopia("carnitas")).addOptional(croptopia("pork_and_beans"));
		getOrCreateTagBuilder(ModItemTags.COOKED_RABBIT).add(Items.COOKED_RABBIT, Items.RABBIT_STEW);
		getOrCreateTagBuilder(ModItemTags.COOKED_SEAFOOD)
				.addTag(ModItemTags.COOKED_FISH).add(ItemsRegistry.SQUID_INK_PASTA.get())
				.addOptional(croptopia("cooked_calamari")).addOptional(croptopia("cooked_shrimp"))
				.addOptional(croptopia("crab_legs")).addOptional(croptopia("deep_fried_shrimp"))
				.addOptional(croptopia("grilled_oysters")).addOptional(croptopia("steamed_clams"))
				.addOptional(croptopia("steamed_crab")).addOptional(croptopia("tuna_roll"))
				.addOptional(croptopia("tuna_sandwich")).addOptional(croptopia("anchovy_pizza"))
				.addOptional(croptopia("fried_calamari"));
		getOrCreateTagBuilder(ModItemTags.COOKED_SHEEP)
				.add(Items.COOKED_MUTTON).add(COOKED_CHEVON)
				.add(ItemsRegistry.COOKED_MUTTON_CHOPS.get(), ItemsRegistry.ROASTED_MUTTON_CHOPS.get())
				.add(ItemsRegistry.MUTTON_WRAP.get(), ItemsRegistry.PASTA_WITH_MUTTON_CHOP.get());
		getOrCreateTagBuilder(ModItemTags.COPPER_BOOTS)
				.add(COPPER_BOOTS, EXPOSED_COPPER_BOOTS, WEATHERED_COPPER_BOOTS, OXIDIZED_COPPER_BOOTS)
				.add(WAXED_COPPER_BOOTS, WAXED_EXPOSED_COPPER_BOOTS, WAXED_WEATHERED_COPPER_BOOTS, WAXED_OXIDIZED_COPPER_BOOTS);
		getOrCreateTagBuilder(ModItemTags.COPPER_CHESTPLATES)
				.add(COPPER_CHESTPLATE, EXPOSED_COPPER_CHESTPLATE, WEATHERED_COPPER_CHESTPLATE, OXIDIZED_COPPER_CHESTPLATE)
				.add(WAXED_COPPER_CHESTPLATE, WAXED_EXPOSED_COPPER_CHESTPLATE, WAXED_WEATHERED_COPPER_CHESTPLATE, WAXED_OXIDIZED_COPPER_CHESTPLATE);
		getOrCreateTagBuilder(ModItemTags.COPPER_HELMETS)
				.add(COPPER_HELMET, EXPOSED_COPPER_HELMET, WEATHERED_COPPER_HELMET, OXIDIZED_COPPER_HELMET)
				.add(WAXED_COPPER_HELMET, WAXED_EXPOSED_COPPER_HELMET, WAXED_WEATHERED_COPPER_HELMET, WAXED_OXIDIZED_COPPER_HELMET);
		getOrCreateTagBuilder(ModItemTags.COPPER_LEGGINGS)
				.add(COPPER_LEGGINGS, EXPOSED_COPPER_LEGGINGS, WEATHERED_COPPER_LEGGINGS, OXIDIZED_COPPER_LEGGINGS)
				.add(WAXED_COPPER_LEGGINGS, WAXED_EXPOSED_COPPER_LEGGINGS, WAXED_WEATHERED_COPPER_LEGGINGS, WAXED_OXIDIZED_COPPER_LEGGINGS);
		getOrCreateTagBuilder(ModItemTags.DECORATED_POT_SHARDS).add(Items.BRICK)
				.add(ANGLER_POTTERY_SHERD, ARCHER_POTTERY_SHERD, ARMS_UP_POTTERY_SHERD, BLADE_POTTERY_SHERD)
				.add(BREWER_POTTERY_SHERD, BURN_POTTERY_SHERD, DANGER_POTTERY_SHERD, EXPLORER_POTTERY_SHERD)
				.add(FRIEND_POTTERY_SHERD, HEART_POTTERY_SHERD, HEARTBREAK_POTTERY_SHERD, HOWL_POTTERY_SHERD)
				.add(MINER_POTTERY_SHERD, MOURNER_POTTERY_SHERD, PLENTY_POTTERY_SHERD, PRIZE_POTTERY_SHERD)
				.add(SHEAF_POTTERY_SHERD, SHELTER_POTTERY_SHERD, SKULL_POTTERY_SHERD, SNORT_POTTERY_SHERD);
		getOrCreateTagBuilder(ModItemTags.DOGWOOD_LOGS).add(DOGWOOD_LOG.asItem(), DOGWOOD_WOOD.asItem(), STRIPPED_DOGWOOD_LOG.asItem(), STRIPPED_DOGWOOD_WOOD.asItem());
		getOrCreateTagBuilder(ModItemTags.EDIBLE_BEEF).addTag(ModItemTags.RAW_BEEF).addTag(ModItemTags.COOKED_BEEF);
		getOrCreateTagBuilder(ModItemTags.EDIBLE_BIRD).addTag(ModItemTags.RAW_BIRD).addTag(ModItemTags.COOKED_BIRD);
		getOrCreateTagBuilder(ModItemTags.EDIBLE_EGG)
				.add(ItemsRegistry.EGG_SANDWICH.get(), ItemsRegistry.FRIED_EGG.get(), ItemsRegistry.FRIED_RICE.get())
				.addOptional(croptopia("egg_roll"));
		getOrCreateTagBuilder(ModItemTags.EDIBLE_FISH).addTag(ModItemTags.RAW_FISH).addTag(ModItemTags.COOKED_FISH);
		getOrCreateTagBuilder(ModItemTags.EDIBLE_MEAT)
				.addTag(ModItemTags.EDIBLE_BEEF).addTag(ModItemTags.EDIBLE_BIRD).addTag(ModItemTags.EDIBLE_FISH)
				.addTag(ModItemTags.EDIBLE_PORK).addTag(ModItemTags.EDIBLE_RABBIT).addTag(ModItemTags.EDIBLE_SEAFOOD)
				.addTag(ModItemTags.EDIBLE_SHEEP);
		getOrCreateTagBuilder(ModItemTags.EDIBLE_MUSHROOMS).add(Items.MUSHROOM_STEW)
				.add(ItemsRegistry.NETHER_SALAD.get())
				.addOptional(betternether("stalagnate_bowl_wart")).addOptional(betternether("stalagnate_bowl_mushroom"))
				.addOptional(betterend("chorus_mushroom_raw")).addOptional(betterend("chorus_mushroom_cooked"))
				.addOptional(betterend("bolux_mushroom_cooked"));
		getOrCreateTagBuilder(ModItemTags.EDIBLE_PORK).addTag(ModItemTags.RAW_PORK).addTag(ModItemTags.COOKED_PORK);
		getOrCreateTagBuilder(ModItemTags.EDIBLE_RABBIT).addTag(ModItemTags.RAW_RABBIT).addTag(ModItemTags.COOKED_RABBIT);
		getOrCreateTagBuilder(ModItemTags.EDIBLE_SEAFOOD).addTag(ModItemTags.RAW_SEAFOOD).addTag(ModItemTags.COOKED_SEAFOOD);
		getOrCreateTagBuilder(ModItemTags.EDIBLE_SHEEP).addTag(ModItemTags.RAW_SHEEP).addTag(ModItemTags.COOKED_SHEEP);
		getOrCreateTagBuilder(ModItemTags.ELYTRA).add(Items.ELYTRA);
		getOrCreateTagBuilder(ModItemTags.ENDER_TORCHES).add(ENDER_TORCH.asItem())
				.add(COPPER_ENDER_TORCH.asItem(), EXPOSED_COPPER_ENDER_TORCH.asItem(),
						WEATHERED_COPPER_ENDER_TORCH.asItem(), OXIDIZED_COPPER_ENDER_TORCH.asItem(),
						WAXED_COPPER_ENDER_TORCH.asItem(), WAXED_EXPOSED_COPPER_ENDER_TORCH.asItem(),
						WAXED_WEATHERED_COPPER_ENDER_TORCH.asItem(), WAXED_OXIDIZED_COPPER_ENDER_TORCH.asItem())
				.add(BAMBOO_ENDER_TORCH.asItem(), DRIED_BAMBOO_ENDER_TORCH.asItem(), BONE_ENDER_TORCH.asItem(), BLAZE_ENDER_TORCH.asItem())
				.add(IRON_ENDER_TORCH.asItem(), GOLD_ENDER_TORCH.asItem(), NETHERITE_ENDER_TORCH.asItem(), PRISMARINE_ENDER_TORCH.asItem());
		getOrCreateTagBuilder(ModItemTags.FEATHERS).add(Items.FEATHER, FANCY_FEATHER).add(BLACK_FEATHER, RED_FEATHER);
		getOrCreateTagBuilder(ModItemTags.FLAVORED_MILK)
				.add(CHOCOLATE_MILK_BUCKET, COFFEE_MILK_BUCKET, STRAWBERRY_MILK_BUCKET, VANILLA_MILK_BUCKET)
				.add(GOLDEN_CHOCOLATE_MILK_BUCKET, GOLDEN_COFFEE_MILK_BUCKET, GOLDEN_STRAWBERRY_MILK_BUCKET, GOLDEN_VANILLA_MILK_BUCKET)
				.add(COPPER_CHOCOLATE_MILK_BUCKET, COPPER_COFFEE_MILK_BUCKET, COPPER_STRAWBERRY_MILK_BUCKET, COPPER_VANILLA_MILK_BUCKET)
				.add(NETHERITE_CHOCOLATE_MILK_BUCKET, NETHERITE_COFFEE_MILK_BUCKET, NETHERITE_STRAWBERRY_MILK_BUCKET, NETHERITE_VANILLA_MILK_BUCKET)
				.add(WOOD_CHOCOLATE_MILK_BUCKET, WOOD_COFFEE_MILK_BUCKET, WOOD_STRAWBERRY_MILK_BUCKET, WOOD_VANILLA_MILK_BUCKET)
				.add(DARK_IRON_CHOCOLATE_MILK_BUCKET, DARK_IRON_COFFEE_MILK_BUCKET, DARK_IRON_STRAWBERRY_MILK_BUCKET, DARK_IRON_VANILLA_MILK_BUCKET);
		getOrCreateTagBuilder(ModItemTags.FRUITS).addTag(ModItemTags.GOLDEN_FRUIT)
				.add(Items.APPLE).add(Items.MELON_SLICE, Items.GLISTERING_MELON_SLICE, Items.SWEET_BERRIES, Items.GLOW_BERRIES)
				.add(Items.PUMPKIN_PIE, Items.CHORUS_FRUIT, Items.POPPED_CHORUS_FRUIT)
				.add(ItemsRegistry.TOMATO.get(), ItemsRegistry.TOMATO_SAUCE.get(), ItemsRegistry.PUMPKIN_SLICE.get())
				.add(ItemsRegistry.APPLE_PIE.get(), ItemsRegistry.APPLE_PIE_SLICE.get(), ItemsRegistry.SWEET_BERRY_COOKIE.get())
				.add(ItemsRegistry.SWEET_BERRY_CHEESECAKE.get(), ItemsRegistry.SWEET_BERRY_CHEESECAKE_SLICE.get())
				.add(ItemsRegistry.MELON_POPSICLE.get(), ItemsRegistry.FRUIT_SALAD.get(), ItemsRegistry.PUMPKIN_SOUP.get())
				.add(ItemsRegistry.RATATOUILLE.get())
				.addOptional(betternether("black_apple")).addOptional(betternether("stalagnate_bowl_apple"))
				.addOptional(betterend("shadow_berry_raw")).addOptional(betterend("shadow_berry_cooked"))
				.addOptional(betterend("cave_pumpkin_pie")).addOptional(betterend("blossom_berry"))
				.addOptional(croptopia("chile_pepper"))
				.addOptionalTag(ModItemTags.NOURISH_FRUIT.getId());
		getOrCreateTagBuilder(ModItemTags.GOLDEN_ARMOR)
				.add(Items.GOLDEN_HELMET, Items.GOLDEN_CHESTPLATE, Items.GOLDEN_LEGGINGS, Items.GOLDEN_BOOTS)
				.addTag(ModItemTags.MEDIEVAL_ORIGINS_GOLDEN_ARMOUR);
		getOrCreateTagBuilder(ModItemTags.GOLDEN_FOOD).addTag(ModItemTags.GOLDEN_FRUIT);
		getOrCreateTagBuilder(ModItemTags.GOLDEN_FRUIT).add(Items.GOLDEN_APPLE, Items.ENCHANTED_GOLDEN_APPLE);
		getOrCreateTagBuilder(ModItemTags.GOLDEN_VEGETABLES).add(Items.GOLDEN_CARROT);
		getOrCreateTagBuilder(ModItemTags.GOLDEN_TOOLS)
				.add(Items.GOLDEN_AXE, Items.GOLDEN_HOE, Items.GOLDEN_PICKAXE, Items.GOLDEN_SHOVEL, Items.GOLDEN_SWORD)
				.add(GOLDEN_SHEARS, GOLDEN_HAMMER)
				.add(GOLDEN_BUCKET, GOLDEN_WATER_BUCKET, GOLDEN_LAVA_BUCKET, GOLDEN_POWDER_SNOW_BUCKET)
				.add(GOLDEN_MUD_BUCKET, GOLDEN_BLOOD_BUCKET)
				.add(GOLDEN_MILK_BUCKET, GOLDEN_CHOCOLATE_MILK_BUCKET, GOLDEN_COFFEE_MILK_BUCKET, GOLDEN_STRAWBERRY_MILK_BUCKET, GOLDEN_VANILLA_MILK_BUCKET)
				.add(ItemsRegistry.GOLDEN_KNIFE.get())
				.addOptional(betterend("golden_hammer"))
				.addTag(ModItemTags.MEDIEVAL_ORIGINS_GOLDEN_TOOLS);
		getOrCreateTagBuilder(ModItemTags.GRAINS).add(Items.BREAD)
				.add(ItemsRegistry.RICE.get(), ItemsRegistry.WHEAT_DOUGH.get(), ItemsRegistry.RAW_PASTA.get())
				.add(ItemsRegistry.COOKED_RICE.get(), ItemsRegistry.FRIED_RICE.get())
				.addOptional(croptopia("oats")).addOptional(croptopia("rice")).addOptional(croptopia("steamed_rice"))
				.addOptional(croptopia("tortilla")).addOptional(croptopia("buttered_toast")).addOptional(croptopia("grilled_cheese"))
				.addOptional(croptopia("nougat")).addOptional(croptopia("pizza")).addOptional(croptopia("cheese_pizza"))
				.addOptional(croptopia("toast_sandwich")).addOptional(croptopia("corn_bread"));
		getOrCreateTagBuilder(ModItemTags.HAMMERS).add(GRAVITY_HAMMER, REPULSION_HAMMER);
		getOrCreateTagBuilder(ModItemTags.HEAD_WEARABLE_BLOCKS).add(PIGLIN_HEAD.asItem(), ZOMBIFIED_PIGLIN_HEAD.asItem()).addTag(ModItemTags.CARVED_GOURDS);
		getOrCreateTagBuilder(ModItemTags.HELMETS)
				.add(Items.LEATHER_HELMET, Items.CHAINMAIL_HELMET, Items.IRON_HELMET, Items.GOLDEN_HELMET, Items.DIAMOND_HELMET, Items.NETHERITE_HELMET)
				.add(AMETHYST_HELMET, EMERALD_HELMET, FLEECE_HELMET, QUARTZ_HELMET, SHULKER_HELMET, STUDDED_LEATHER_HELMET).addTag(ModItemTags.COPPER_HELMETS)
				.addOptional(betternether("cincinnasite_helmet")).addOptional(betternether("nether_ruby_helmet"))
				.addOptional(betterend("aeternium_helmet")).addOptional(betterend("crystalite_helmet"))
				.addOptional(betterend("thallasium_helmet")).addOptional(betterend("terminite_helmet"));
		getOrCreateTagBuilder(ModItemTags.HOES).add(ECHO_HOE)
				.add(Items.WOODEN_HOE, Items.STONE_HOE, Items.IRON_HOE, Items.GOLDEN_HOE, Items.DIAMOND_HOE, Items.NETHERITE_HOE)
				.addOptional(betternether("cincinnasite_hoe")).addOptional(betternether("cincinnasite_hoe_diamond"))
				.addOptional(betternether("nether_ruby_hoe")).addOptional(betterend("aeternium_hoe"))
				.addOptional(betterend("thallasium_hoe")).addOptional(betterend("terminite_hoe"));
		getOrCreateTagBuilder(ModItemTags.IRON_ARMOR).add(Items.IRON_HELMET, Items.IRON_CHESTPLATE, Items.IRON_LEGGINGS, Items.IRON_BOOTS);
		getOrCreateTagBuilder(ModItemTags.IRON_BASE_ARMOR).addTag(ModItemTags.IRON_ARMOR).addTag(ModItemTags.CHAINMAIL_ARMOR);
		getOrCreateTagBuilder(ModItemTags.KNIVES)
				.add(ItemsRegistry.FLINT_KNIFE.get(), ItemsRegistry.IRON_KNIFE.get(), ItemsRegistry.GOLDEN_KNIFE.get(), ItemsRegistry.DIAMOND_KNIFE.get(), ItemsRegistry.NETHERITE_KNIFE.get());
		getOrCreateTagBuilder(ModItemTags.LEGGINGS)
				.add(Items.LEATHER_LEGGINGS, Items.CHAINMAIL_LEGGINGS, Items.IRON_LEGGINGS, Items.GOLDEN_LEGGINGS, Items.DIAMOND_LEGGINGS, Items.NETHERITE_LEGGINGS)
				.add(AMETHYST_LEGGINGS, EMERALD_LEGGINGS, FLEECE_LEGGINGS, QUARTZ_LEGGINGS, SHULKER_LEGGINGS, STUDDED_LEATHER_LEGGINGS).addTag(ModItemTags.COPPER_LEGGINGS)
				.addOptional(betternether("cincinnasite_leggings")).addOptional(betternether("nether_ruby_leggings"))
				.addOptional(betterend("aeternium_leggings")).addOptional(betterend("crystalite_leggings"))
				.addOptional(betterend("thallasium_leggings")).addOptional(betterend("terminite_leggings"));
		getOrCreateTagBuilder(ModItemTags.LIGHTS_FLINT).add(Items.IRON_SWORD, ItemsRegistry.IRON_KNIFE.get());
		getOrCreateTagBuilder(ModItemTags.MANGROVE_LOGS).add(MANGROVE_LOG.asItem(), MANGROVE_WOOD.asItem(), STRIPPED_MANGROVE_LOG.asItem(), STRIPPED_MANGROVE_WOOD.asItem());
		getOrCreateTagBuilder(ModItemTags.PICKAXES).add(ECHO_PICKAXE)
				.add(Items.WOODEN_PICKAXE, Items.STONE_PICKAXE, Items.IRON_PICKAXE, Items.GOLDEN_PICKAXE, Items.DIAMOND_PICKAXE, Items.NETHERITE_PICKAXE)
				.addOptional(betternether("cincinnasite_pickaxe")).addOptional(betternether("cincinnasite_pickaxe_diamond"))
				.addOptional(betternether("nether_ruby_pickaxe")).addOptional(betterend("aeternium_pickaxe"))
				.addOptional(betterend("thallasium_pickaxe")).addOptional(betterend("terminite_pickaxe"));
		getOrCreateTagBuilder(ModItemTags.RAW_BEEF).add(Items.BEEF)
				.add(ItemsRegistry.MINCED_BEEF.get());
		getOrCreateTagBuilder(ModItemTags.RAW_BIRD).add(Items.CHICKEN)
				.add(ItemsRegistry.CHICKEN_CUTS.get());
		getOrCreateTagBuilder(ModItemTags.RAW_FISH).add(Items.COD, Items.PUFFERFISH, Items.SALMON, Items.TROPICAL_FISH)
				.add(PIRANHA)
				.add(ItemsRegistry.COD_SLICE.get(), ItemsRegistry.SALMON_SLICE.get())
				.addOptional(croptopia("anchovy")).addOptional(croptopia("tuna"))
				.addOptional(betterend("end_fish_raw"));
		getOrCreateTagBuilder(ModItemTags.RAW_MEAT)
				.addTag(ModItemTags.RAW_BEEF).addTag(ModItemTags.RAW_BIRD).addTag(ModItemTags.RAW_FISH)
				.addTag(ModItemTags.RAW_PORK).addTag(ModItemTags.RAW_RABBIT).addTag(ModItemTags.RAW_SEAFOOD)
				.addTag(ModItemTags.RAW_SHEEP);
		getOrCreateTagBuilder(ModItemTags.RAW_PORK).add(Items.PORKCHOP)
				.add(ItemsRegistry.BACON.get(), ItemsRegistry.HAM.get())
				.addOptional(croptopia("bacon"));
		getOrCreateTagBuilder(ModItemTags.RAW_RABBIT).add(Items.RABBIT);
		getOrCreateTagBuilder(ModItemTags.RAW_SEAFOOD)
				.addOptional(croptopia("clam")).addOptional(croptopia("calamari"))
				.addOptional(croptopia("crab")).addOptional(croptopia("glowing_calamari"))
				.addOptional(croptopia("oyster")).addOptional(croptopia("shrimp"));
		getOrCreateTagBuilder(ModItemTags.RAW_SHEEP).add(Items.MUTTON).add(CHEVON)
				.add(ItemsRegistry.MUTTON_CHOPS.get());
		getOrCreateTagBuilder(ModItemTags.ROWS).add(BAMBOO_ROW.asItem(), DRIED_BAMBOO_ROW.asItem(), BONE_ROW.asItem(), SUGAR_CANE_ROW.asItem());
		getOrCreateTagBuilder(ModItemTags.SEEDS)
				.add(Items.BEETROOT_SEEDS, Items.MELON_SEEDS, Items.PUMPKIN_SEEDS, Items.WHEAT_SEEDS)
				.add(TORCHFLOWER_CROP.asItem())
				.add(ItemsRegistry.CABBAGE_SEEDS.get(), ItemsRegistry.TOMATO_SEED.get())
				.addOptional(croptopia("artichoke_seed")).addOptional(croptopia("asparagus_seed"))
				.addOptional(croptopia("barley_seed")).addOptional(croptopia("basil_seed"))
				.addOptional(croptopia("bellpepper_seed")).addOptional(croptopia("blackbean_seed"))
				.addOptional(croptopia("blackberry_seed")).addOptional(croptopia("blueberry_seed"))
				.addOptional(croptopia("broccoli_seed")).addOptional(croptopia("cabbage_seed"))
				.addOptional(croptopia("cantaloupe_seed")).addOptional(croptopia("cauliflower_seed"))
				.addOptional(croptopia("celery_seed")).addOptional(croptopia("chilepepper_seed"))
				.addOptional(croptopia("coffee_beans_seed")).addOptional(croptopia("corn_seed"))
				.addOptional(croptopia("cranberry_seed")).addOptional(croptopia("cucumber_seed"))
				.addOptional(croptopia("currant_seed")).addOptional(croptopia("eggplant_seed"))
				.addOptional(croptopia("elderberry_seed")).addOptional(croptopia("garlic_seed"))
				.addOptional(croptopia("ginger_seed")).addOptional(croptopia("grape_seed"))
				.addOptional(croptopia("greenbean_seed")).addOptional(croptopia("greenonion_seed"))
				.addOptional(croptopia("honeydew_seed")).addOptional(croptopia("hops_seed"))
				.addOptional(croptopia("kale_seed")).addOptional(croptopia("kiwi_seed"))
				.addOptional(croptopia("leek_seed")).addOptional(croptopia("lettuce_seed"))
				.addOptional(croptopia("mustard_seed")).addOptional(croptopia("oat_seed"))
				.addOptional(croptopia("olive_seed")).addOptional(croptopia("onion_seed"))
				.addOptional(croptopia("peanut_seed")).addOptional(croptopia("pepper_seed"))
				.addOptional(croptopia("pineapple_seed")).addOptional(croptopia("radish_seed"))
				.addOptional(croptopia("raspberry_seed")).addOptional(croptopia("rhubarb_seed"))
				.addOptional(croptopia("rice_seed")).addOptional(croptopia("rutabaga_seed"))
				.addOptional(croptopia("saguaro_seed")).addOptional(croptopia("soybean_seed"))
				.addOptional(croptopia("spinach_seed")).addOptional(croptopia("squash_seed"))
				.addOptional(croptopia("strawberry_seed")).addOptional(croptopia("sweetpotato_seed"))
				.addOptional(croptopia("tea_leaves_seed")).addOptional(croptopia("tomatillo_seed"))
				.addOptional(croptopia("tomato_seed")).addOptional(croptopia("turmeric_seed"))
				.addOptional(croptopia("turnip_seed")).addOptional(croptopia("vanilla_seed"))
				.addOptional(croptopia("yam_seed")).addOptional(croptopia("zucchini"));
		getOrCreateTagBuilder(ModItemTags.SHEARS).add(Items.SHEARS).addOptional(betternether("cincinnasite_shears"));
		getOrCreateTagBuilder(ModItemTags.SHOVELS).add(ECHO_SHOVEL)
				.add(Items.WOODEN_SHOVEL, Items.STONE_SHOVEL, Items.IRON_SHOVEL, Items.GOLDEN_SHOVEL, Items.DIAMOND_SHOVEL, Items.NETHERITE_SHOVEL)
				.addOptional(betternether("cincinnasite_shovel")).addOptional(betternether("cincinnasite_shovel_diamond"))
				.addOptional(betternether("nether_ruby_shovel")).addOptional(betterend("aeternium_shovel"))
				.addOptional(betterend("thallasium_shovel")).addOptional(betterend("terminite_shovel"));
		getOrCreateTagBuilder(ModItemTags.SNIFFER_FOOD).add(TORCHFLOWER_CROP.asItem());
		getOrCreateTagBuilder(ModItemTags.SOUL_LANTERNS).add(Items.SOUL_LANTERN)
				.add(IRON_SOUL_LANTERN.asItem(), GOLD_SOUL_LANTERN.asItem(), NETHERITE_SOUL_LANTERN.asItem())
				.add(COPPER_SOUL_LANTERN.asItem(), EXPOSED_COPPER_SOUL_LANTERN.asItem())
				.add(WEATHERED_COPPER_SOUL_LANTERN.asItem(), OXIDIZED_COPPER_SOUL_LANTERN.asItem())
				.add(WAXED_COPPER_SOUL_LANTERN.asItem(), WAXED_EXPOSED_COPPER_SOUL_LANTERN.asItem())
				.add(WAXED_WEATHERED_COPPER_SOUL_LANTERN.asItem(), WAXED_OXIDIZED_COPPER_SOUL_LANTERN.asItem());
		getOrCreateTagBuilder(ModItemTags.SOUL_TORCHES).add(Items.SOUL_TORCH)
				.add(COPPER_SOUL_TORCH.asItem(), EXPOSED_COPPER_SOUL_TORCH.asItem(),
						WEATHERED_COPPER_SOUL_TORCH.asItem(), OXIDIZED_COPPER_SOUL_TORCH.asItem(),
						WAXED_COPPER_SOUL_TORCH.asItem(), WAXED_EXPOSED_COPPER_SOUL_TORCH.asItem(),
						WAXED_WEATHERED_COPPER_SOUL_TORCH.asItem(), WAXED_OXIDIZED_COPPER_SOUL_TORCH.asItem())
				.add(BAMBOO_SOUL_TORCH.asItem(), DRIED_BAMBOO_SOUL_TORCH.asItem(), BONE_SOUL_TORCH.asItem(), BLAZE_SOUL_TORCH.asItem())
				.add(IRON_SOUL_TORCH.asItem(), GOLD_SOUL_TORCH.asItem(), NETHERITE_SOUL_TORCH.asItem(), PRISMARINE_SOUL_TORCH.asItem());
		getOrCreateTagBuilder(ModItemTags.SWEETS).add(Items.CAKE, Items.COOKIE, Items.PUMPKIN_PIE)
				.add(ItemsRegistry.CHOCOLATE_PIE.get(), ItemsRegistry.CHOCOLATE_PIE_SLICE.get(), ItemsRegistry.HONEY_COOKIE.get())
				.add(ItemsRegistry.PIE_CRUST.get(), ItemsRegistry.CAKE_SLICE.get(), ItemsRegistry.MELON_POPSICLE.get())
				.add(ItemsRegistry.APPLE_PIE.get(), ItemsRegistry.APPLE_PIE_SLICE.get(), ItemsRegistry.SWEET_BERRY_COOKIE.get())
				.add(ItemsRegistry.SWEET_BERRY_CHEESECAKE.get(), ItemsRegistry.SWEET_BERRY_CHEESECAKE_SLICE.get())
				.addOptional(betterend("cave_pumpkin_pie")).addOptional(croptopia("pecan_pie")).addOptional(croptopia("rhubarb_pie"))
				.addOptional(croptopia("donut")).addOptional(croptopia("candied_nuts")).addOptional(croptopia("almond_brittle"))
				.addOptional(croptopia("raisin_oatmeal_cookie")).addOptional(croptopia("nutty_cookie")).addOptional(croptopia("whipping_cream"))
				.addOptional(croptopia("pumpkin_bars")).addOptional(croptopia("meringue")).addOptional(croptopia("cinnamon_roll"))
				.addOptional(croptopia("baked_crepes")).addOptional(croptopia("macaron")).addOptional(croptopia("quiche"))
				.addOptional(croptopia("sweet_crepes")).addOptional(croptopia("trail_mix")).addOptional(croptopia("protein_bar"))
				.addOptional(croptopia("tofu_and_dumplings"))
				.addTag(ModItemTags.NOURISH_SWEETS);
		getOrCreateTagBuilder(ModItemTags.SWORDS).add(ECHO_SWORD)
				.add(Items.WOODEN_SWORD, Items.STONE_SWORD, Items.IRON_SWORD, Items.GOLDEN_SWORD, Items.DIAMOND_SWORD, Items.NETHERITE_SWORD)
				.addOptional(betternether("cincinnasite_sword")).addOptional(betternether("cincinnasite_sword_diamond"))
				.addOptional(betternether("nether_ruby_sword")).addOptional(betterend("aeternium_sword"))
				.addOptional(betterend("thallasium_sword")).addOptional(betterend("terminite_sword"));
		getOrCreateTagBuilder(ModItemTags.TOOLS)
				.addTag(ModItemTags.AXES)
				.addTag(ModItemTags.HOES)
				.addTag(ModItemTags.PICKAXES)
				.addTag(ModItemTags.SHOVELS)
				.addTag(ModItemTags.SWORDS)
				.add(Items.TRIDENT);
		getOrCreateTagBuilder(ModItemTags.TORCHES).add(Items.TORCH)
				.add(COPPER_TORCH.asItem(), EXPOSED_COPPER_TORCH.asItem(),
						WEATHERED_COPPER_TORCH.asItem(), OXIDIZED_COPPER_TORCH.asItem(),
						WAXED_COPPER_TORCH.asItem(), WAXED_EXPOSED_COPPER_TORCH.asItem(),
						WAXED_WEATHERED_COPPER_TORCH.asItem(), WAXED_OXIDIZED_COPPER_TORCH.asItem())
				.add(BAMBOO_TORCH.asItem(), DRIED_BAMBOO_TORCH.asItem(), PRISMARINE_TORCH.asItem(), BLAZE_TORCH.asItem())
				.add(BONE_TORCH.asItem(), IRON_TORCH.asItem(), GOLD_TORCH.asItem(), NETHERITE_TORCH.asItem());
		getOrCreateTagBuilder(ModItemTags.UNDERWATER_TORCHES).add(UNDERWATER_TORCH.asItem())
				.add(UNDERWATER_COPPER_TORCH.asItem(), EXPOSED_UNDERWATER_COPPER_TORCH.asItem(),
						WEATHERED_UNDERWATER_COPPER_TORCH.asItem(), OXIDIZED_UNDERWATER_COPPER_TORCH.asItem(),
						WAXED_UNDERWATER_COPPER_TORCH.asItem(), WAXED_EXPOSED_UNDERWATER_COPPER_TORCH.asItem(),
						WAXED_WEATHERED_UNDERWATER_COPPER_TORCH.asItem(), WAXED_OXIDIZED_UNDERWATER_COPPER_TORCH.asItem())
				.add(UNDERWATER_BAMBOO_TORCH.asItem(), UNDERWATER_DRIED_BAMBOO_TORCH.asItem(), UNDERWATER_BONE_TORCH.asItem(), UNDERWATER_IRON_TORCH.asItem())
				.add(UNDERWATER_GOLD_TORCH.asItem(), UNDERWATER_NETHERITE_TORCH.asItem(), UNDERWATER_PRISMARINE_TORCH.asItem(), UNDERWATER_BLAZE_TORCH.asItem());
		getOrCreateTagBuilder(ModItemTags.TRIMMABLE_ARMOR)
				.add(Items.LEATHER_HELMET, Items.LEATHER_CHESTPLATE, Items.LEATHER_LEGGINGS, Items.LEATHER_BOOTS)
				.add(Items.CHAINMAIL_HELMET, Items.CHAINMAIL_CHESTPLATE, Items.CHAINMAIL_LEGGINGS, Items.CHAINMAIL_BOOTS)
				.add(Items.IRON_HELMET, Items.IRON_CHESTPLATE, Items.IRON_LEGGINGS, Items.IRON_BOOTS)
				.add(Items.GOLDEN_HELMET, Items.GOLDEN_CHESTPLATE, Items.GOLDEN_LEGGINGS, Items.GOLDEN_BOOTS)
				.add(Items.DIAMOND_HELMET, Items.DIAMOND_CHESTPLATE, Items.DIAMOND_LEGGINGS, Items.DIAMOND_BOOTS)
				.add(Items.NETHERITE_HELMET, Items.NETHERITE_CHESTPLATE, Items.NETHERITE_LEGGINGS, Items.NETHERITE_BOOTS)
				.add(Items.TURTLE_HELMET);
		getOrCreateTagBuilder(ModItemTags.TRIM_MATERIALS)
				.add(Items.QUARTZ, Items.IRON_INGOT, Items.NETHERITE_INGOT, Items.REDSTONE, Items.COPPER_INGOT)
				.add(Items.GOLD_INGOT, Items.EMERALD, Items.DIAMOND, Items.LAPIS_LAZULI, Items.AMETHYST_SHARD);
		getOrCreateTagBuilder(ModItemTags.TRIM_TEMPLATES)
				.add(Arrays.stream(ArmorTrimPattern.values()).map(ArmorTrimPattern::asItem).toArray(Item[]::new));
		getOrCreateTagBuilder(ModItemTags.VEGETABLES).addTag(ModItemTags.GOLDEN_VEGETABLES)
				.add(Items.CARROT, Items.POTATO, Items.BAKED_POTATO, Items.POISONOUS_POTATO, Items.BEETROOT, Items.DRIED_KELP)
				.add(ItemsRegistry.CABBAGE.get(), ItemsRegistry.CABBAGE_LEAF.get(), ItemsRegistry.ONION.get(), ItemsRegistry.RICE.get())
				.add(Items.BEETROOT_SOUP)
				.add(ItemsRegistry.VEGETABLE_SOUP.get(), ItemsRegistry.VEGETABLE_NOODLES.get(), ItemsRegistry.RATATOUILLE.get())
				.add(ItemsRegistry.HORSE_FEED.get())
				.addOptional(betterend("amber_root_raw"))
				.addTag(ModItemTags.FORGE_VEGETABLES)
				.addTag(ModItemTags.NOURISH_VEGETABLES)
				.addOptional(croptopia("barley")).addOptional(croptopia("basil")).addOptional(croptopia("blackbean"))
				.addOptional(croptopia("coffee_beans")).addOptional(croptopia("ginger")).addOptional(croptopia("hops"))
				.addOptional(croptopia("mustard")).addOptional(croptopia("potato_soup")).addOptional(croptopia("beetroot_salad"))
				.addOptional(croptopia("candied_kumquats")).addOptional(croptopia("sea_lettuce")).addOptional(croptopia("dauphine_potatoes"))
				.addOptional(croptopia("mashed_potatoes")).addOptional(croptopia("hashed_brown")).addOptional(croptopia("saucy_chips"))
				.addOptional(croptopia("roasted_nuts")).addOptional(croptopia("pumpkin_soup"));
		getOrCreateTagBuilder(ModItemTags.WOOL_SLABS)
				.add(WOOL_SLABS.values().stream().map(BlockContainer::asItem).toArray(Item[]::new))
				.add(RAINBOW_WOOL_SLAB.asItem());
		getOrCreateTagBuilder(ModItemTags.WOOL_CARPETS)
				.add(Arrays.stream(DyeColor.values()).map(ColorUtil::GetWoolCarpetItem).toArray(Item[]::new))
				.add(RAINBOW_CARPET.asItem());

		getOrCreateTagBuilder(ModItemTags.CROPTOPIA_NUTS);
		getOrCreateTagBuilder(ModItemTags.FORGE_VEGETABLES);
		getOrCreateTagBuilder(ModItemTags.NOURISH_FRUIT);
		getOrCreateTagBuilder(ModItemTags.NOURISH_SWEETS);
		getOrCreateTagBuilder(ModItemTags.NOURISH_VEGETABLES);

		getOrCreateTagBuilder(ModItemTags.ORIGINS_MEAT).addTag(ModItemTags.EDIBLE_MEAT);

		getOrCreateTagBuilder(ModItemTags.MEDIEVAL_ORIGINS_AXES).addTag(ModItemTags.AXES);
		getOrCreateTagBuilder(ModItemTags.MEDIEVAL_ORIGINS_BOWS);
		getOrCreateTagBuilder(ModItemTags.MEDIEVAL_ORIGINS_CAKES);
		getOrCreateTagBuilder(ModItemTags.MEDIEVAL_ORIGINS_CROPS)
				.add(Items.CARROT, Items.POTATO, Items.BEETROOT, Items.WHEAT, Items.PUMPKIN, Items.MELON)
				.add(Items.SUGAR_CANE, Items.COCOA_BEANS, Items.SWEET_BERRIES, Items.GLOW_LICHEN);
		getOrCreateTagBuilder(ModItemTags.MEDIEVAL_ORIGINS_GOLDEN_ARMOUR);
		getOrCreateTagBuilder(ModItemTags.MEDIEVAL_ORIGINS_GOLDEN_TOOLS);
		getOrCreateTagBuilder(ModItemTags.MEDIEVAL_ORIGINS_HELMETS).addTag(ModItemTags.HELMETS);
		getOrCreateTagBuilder(ModItemTags.MEDIEVAL_ORIGINS_IRON_ARMOUR);
		getOrCreateTagBuilder(ModItemTags.MEDIEVAL_ORIGINS_IRON_TOOLS);
		getOrCreateTagBuilder(ModItemTags.MEDIEVAL_ORIGINS_MEAT).addTag(ModItemTags.EDIBLE_MEAT);
		getOrCreateTagBuilder(ModItemTags.MEDIEVAL_ORIGINS_NONMEAT);
		getOrCreateTagBuilder(ModItemTags.MEDIEVAL_ORIGINS_OGRE_ARMOUR)
				.add(STUDDED_LEATHER_HELMET, STUDDED_LEATHER_CHESTPLATE, STUDDED_LEATHER_LEGGINGS, STUDDED_LEATHER_BOOTS);
		getOrCreateTagBuilder(ModItemTags.MEDIEVAL_ORIGINS_SWORDS);
	}
}