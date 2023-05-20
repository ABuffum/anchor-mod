package fun.mousewich.gen.data;

import fun.mousewich.block.BlockConvertible;
import fun.mousewich.container.*;
import fun.mousewich.gen.data.advancement.AdvancementsGenerator;
import fun.mousewich.gen.data.language.EnglishLanguageProvider;
import fun.mousewich.gen.data.loot.BlockLootGenerator;
import fun.mousewich.gen.data.model.ModelGenerator;
import fun.mousewich.gen.data.recipe.RecipeGenerator;
import fun.mousewich.gen.data.tag.*;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.tag.Tag;
import net.minecraft.util.Pair;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.event.GameEvent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ModDatagen implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		fabricDataGenerator.addProvider(ModelGenerator::new);
		fabricDataGenerator.addProvider(BlockLootGenerator::new);
		fabricDataGenerator.addProvider(BiomeTagGenerator::new);
		fabricDataGenerator.addProvider(BlockTagGenerator::new);
		fabricDataGenerator.addProvider(EntityTypeTagGenerator::new);
		fabricDataGenerator.addProvider(GameEventTagGenerator::new);
		fabricDataGenerator.addProvider(ItemTagGenerator::new);
		fabricDataGenerator.addProvider(RecipeGenerator::new);
		fabricDataGenerator.addProvider(AdvancementsGenerator::new);
		//Language
		fabricDataGenerator.addProvider(EnglishLanguageProvider::new);
	}

	public static class Cache {
		public static class Tags {
			public static Map<Tag.Identified<Biome>, Set<Biome>> BIOME_TAGS = new HashMap<>();
			public static void Register(Tag.Identified<Biome> tag, Biome biome) { Register(BIOME_TAGS, tag, biome); }
			public static Map<Tag.Identified<Block>, Set<Block>> BLOCK_TAGS = new HashMap<>();
			public static void Register(Tag.Identified<Block> tag, BlockConvertible block) { Register(tag, block.asBlock()); }
			public static void Register(Tag.Identified<Block> tag, Block block) { Register(BLOCK_TAGS, tag, block); }
			public static Map<Tag.Identified<EntityType<?>>, Set<EntityType<?>>> ENTITY_TYPE_TAGS = new HashMap<>();
			public static void Register(Tag.Identified<EntityType<?>> tag, EntityType<?> entityType) { Register(ENTITY_TYPE_TAGS, tag, entityType); }
			public static Map<Tag.Identified<GameEvent>, Set<GameEvent>> GAME_EVENT_TAGS = new HashMap<>();
			public static void Register(Tag.Identified<GameEvent> tag, GameEvent gameEvent) { Register(GAME_EVENT_TAGS, tag, gameEvent); }
			public static Map<Tag.Identified<Item>, Set<Item>> ITEM_TAGS = new HashMap<>();
			public static void Register(Tag.Identified<Item> tag, ItemConvertible item) { Register(tag, item.asItem()); }
			public static void Register(Tag.Identified<Item> tag, Item item) { Register(ITEM_TAGS, tag, item); }

			private static <T> void Register(Map<Tag.Identified<T>, Set<T>> map, Tag.Identified<T> tag, T value) {
				Set<T> set = map.containsKey(tag) ? map.get(tag) : new HashSet<T>();
				set.add(value);
				map.put(tag, set);
			}
		}

		public static class Models {
			//Block - Standalone
			public static Set<IBlockItemContainer> BAMBOO_FENCE = new HashSet<>();
			public static Set<IBlockItemContainer> BAMBOO_FENCE_GATE = new HashSet<>();
			public static Set<IBlockItemContainer> BARREL = new HashSet<>();
			public static Set<IBlockItemContainer> BARS = new HashSet<>();
			public static Set<IBlockItemContainer> BEEHIVE = new HashSet<>();
			public static Set<IBlockItemContainer> CAMPFIRE = new HashSet<>();
			public static Set<IBlockItemContainer> CHAIN = new HashSet<>();
			public static Set<IBlockItemContainer> CHISELED_BOOKSHELF = new HashSet<>();
			public static Set<IBlockItemContainer> CUBE_ALL = new HashSet<>();
			public static Set<IBlockItemContainer> DOOR = new HashSet<>();
			public static Set<IBlockItemContainer> HEAVY_CHAIN = new HashSet<>();
			public static Set<IBlockItemContainer> LADDER = new HashSet<>();
			public static Set<IBlockItemContainer> LEAVES = new HashSet<>();
			public static Set<IBlockItemContainer> PLUSHIE = new HashSet<>();
			public static Set<PottedBlockContainer> POTTED = new HashSet<>();
			public static Set<IBlockItemContainer> THIN_TRAPDOOR = new HashSet<>();
			public static Set<TorchContainer> TORCH = new HashSet<>();
			public static Set<Pair<IBlockItemContainer, Boolean>> TRAPDOOR = new HashSet<>();
			//Block - with base
			public static Set<Pair<IBlockItemContainer, Block>> BARK_SLAB = new HashSet<>();
			public static Set<Pair<IBlockItemContainer, Block>> BOOKSHELF = new HashSet<>();
			public static Set<Pair<IBlockItemContainer, Block>> BUTTON = new HashSet<>();
			public static Set<Pair<IBlockItemContainer, Block>> CAMPFIRE_SOUL = new HashSet<>();
			public static Set<Pair<IBlockItemContainer, Block>> CAMPFIRE_ENDER = new HashSet<>();
			public static Set<Pair<IBlockItemContainer, Block>> CRAFTING_TABLE = new HashSet<>();
			public static Set<Pair<IBlockItemContainer, Block>> FENCE = new HashSet<>();
			public static Set<Pair<IBlockItemContainer, Block>> FENCE_GATE = new HashSet<>();
			public static Set<Pair<WallBlockContainer, Block>> HANGING_SIGN = new HashSet<>();
			public static Set<Pair<IBlockItemContainer, Block>> LECTERN = new HashSet<>();
			public static Set<Pair<IBlockItemContainer, Block>> LOG_SLAB = new HashSet<>();
			public static Set<Pair<IBlockItemContainer, Block>> PANE = new HashSet<>();
			public static Set<Pair<IBlockItemContainer, Block>> POWDER_KEG = new HashSet<>();
			public static Set<Pair<IBlockItemContainer, Block>> PRESSURE_PLATE = new HashSet<>();
			public static Set<Pair<WallBlockContainer, Block>> SIGN = new HashSet<>();
			public static Set<Pair<IBlockItemContainer, Block>> SLAB = new HashSet<>();
			public static Set<Pair<IBlockItemContainer, Block>> STAIRS = new HashSet<>();
			public static Set<Pair<TorchContainer, TorchContainer>> TORCH_CHILD = new HashSet<>();
			public static Set<Pair<IBlockItemContainer, Block>> WALL = new HashSet<>();
			public static Set<Pair<IBlockItemContainer, Block>> WEIGHTED_PRESSURE_PLATE = new HashSet<>();
			public static Set<Pair<IBlockItemContainer, Block>> WOODCUTTER = new HashSet<>();
			//Items - Standalone
			public static Set<Item> GENERATED = new HashSet<>();
			public static Set<Item> HAMMER = new HashSet<>();
			public static Set<Item> HANDHELD = new HashSet<>();
			public static Set<Item> SPAWN_EGG = new HashSet<>();
			//Items - with base
			public static Set<Pair<Item, Item>> PARENTED = new HashSet<>();
			//Misc Containers
			public static Set<FlowerPartContainer> FLOWER_PART = new HashSet<>();

			public static void Register(Set<Item> set, ItemConvertible item) { Register(set, item.asItem()); }
			public static void Register(Set<Item> set, Item item) { set.add(item); }
		}
	}
}
