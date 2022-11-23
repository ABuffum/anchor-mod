package haven;

import net.fabricmc.fabric.api.tag.TagFactory;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.event.GameEvent;

public class ModTags {
	public static class Blocks {
		public static final Tag.Identified<Block> ANCIENT_CITY_REPLACEABLE = createMinecraftTag("ancient_city_replaceable");
		public static final Tag.Identified<Block> CONVERTIBLE_TO_MUD = createTag("convertible_to_mud");
		public static final Tag.Identified<Block> DAMPENS_VIBRATIONS = createTag("dampens_vibrations");
		public static final Tag.Identified<Block> FLEECE = createTag("fleece");
		public static final Tag.Identified<Block> GOURDS = createTag("gourds");
		public static final Tag.Identified<Block> PUMPKINS = createTag("pumpkins");
		public static final Tag.Identified<Block> MANGROVE_LOGS_CAN_GROW_THROUGH = createMinecraftTag("mangrove_logs_can_grow_through");
		public static final Tag.Identified<Block> MANGROVE_ROOTS_CAN_GROW_THROUGH = createMinecraftTag("mangrove_roots_can_grow_through");
		public static final Tag.Identified<Block> OCCLUDES_VIBRATION_SIGNALS = createMinecraftTag("occludes_vibration_signals");
		public static final Tag.Identified<Block> SNAPS_GOAT_HORN = createTag("snaps_goat_horn");

		public static final Tag.Identified<Block> SCULK_REPLACEABLE = createMinecraftTag("sculk_replaceable");
		public static final Tag.Identified<Block> SCULK_REPLACEABLE_WORLD_GEN = createMinecraftTag("sculk_replaceable");

		public static final Tag.Identified<Block> SCULK_TURFS = createTag("sculk_turfs");

		public static final Tag.Identified<Block> CARVED_GOURDS = createTag("carved_gourds");
		public static final Tag.Identified<Block> CARVED_MELONS = createTag("carved_melons");
		public static final Tag.Identified<Block> CARVED_PUMPKINS = createTag("carved_pumpkins");
		public static final Tag.Identified<Block> GOURD_LANTERNS = createTag("gourd_lanterns");
		public static final Tag.Identified<Block> SOUL_GOURD_LANTERNS = createTag("soul_gourd_lanterns");
		public static final Tag.Identified<Block> ENDER_GOURD_LANTERNS = createTag("ender_gourd_lanterns");
		public static final Tag.Identified<Block> MELON_LANTERNS = createTag("melon_lanterns");
		public static final Tag.Identified<Block> SOUL_MELON_LANTERNS = createTag("soul_melon_lanterns");
		public static final Tag.Identified<Block> ENDER_MELON_LANTERNS = createTag("ender_melon_lanterns");
		public static final Tag.Identified<Block> JACK_O_LANTERNS = createTag("jack_o_lanterns");
		public static final Tag.Identified<Block> SOUL_JACK_O_LANTERNS = createTag("soul_jack_o_lanterns");
		public static final Tag.Identified<Block> ENDER_JACK_O_LANTERNS = createTag("ender_jack_o_lanterns");

		private static Tag.Identified<Block> createTag(String name) {
			return TagFactory.BLOCK.create(ModBase.ID(name));
		}
		private static Tag.Identified<Block> createCommonTag(String name) {
			return TagFactory.BLOCK.create(new Identifier("c", name));
		}
		private static Tag.Identified<Block> createMinecraftTag(String name) {
			return TagFactory.BLOCK.create(new Identifier(name));
		}
	}
	public static class Fluids {
		public static final Tag.Identified<Fluid> BLOOD = createTag("blood");
		public static final Tag.Identified<Fluid> MUD = createTag("mud");

		private static Tag.Identified<Fluid> createTag(String name) {
			return TagFactory.FLUID.create(ModBase.ID(name));
		}
		private static Tag.Identified<Fluid> createCommonTag(String name) {
			return TagFactory.FLUID.create(new Identifier("c", name));
		}
		private static Tag.Identified<Fluid> createMinecraftTag(String name) {
			return TagFactory.FLUID.create(new Identifier(name));
		}
	}
	public static class Items {
		//Minecraft
		public static final Tag.Identified<Item> BOOKSHELF_BOOKS = createMinecraftTag("bookshelf_books");
		//Haven
		public static final Tag.Identified<Item> HEAD_WEARABLE_BLOCKS = createTag("head_wearable_blocks");
		public static final Tag.Identified<Item> FLAVORED_MILK = createTag("flavored_milk");
		public static final Tag.Identified<Item> FLEECE = createTag("fleece");
		public static final Tag.Identified<Item> SEEDS = createTag("seeds");

		public static final Tag.Identified<Item> AXES = createCommonTag("tools/axes");
		public static final Tag.Identified<Item> HOES = createCommonTag("tools/hoes");
		public static final Tag.Identified<Item> PICKAXES = createCommonTag("tools/pickaxes");
		public static final Tag.Identified<Item> SHOVELS = createCommonTag("tools/shovels");
		public static final Tag.Identified<Item> SWORDS = createCommonTag("tools/swords");
		public static final Tag.Identified<Item> KNIVES = createCommonTag("tools/knives");
		public static final Tag.Identified<Item> SHEARS = createCommonTag("tools/shears");

		public static final Tag.Identified<Item> GOURDS = createTag("gourds");
		public static final Tag.Identified<Item> PUMPKINS = createTag("pumpkins");
		public static final Tag.Identified<Item> CARVED_GOURDS = createTag("carved_gourds");
		public static final Tag.Identified<Item> CARVED_MELONS = createTag("carved_melons");
		public static final Tag.Identified<Item> CARVED_PUMPKINS = createTag("carved_pumpkins");
		public static final Tag.Identified<Item> GOURD_LANTERNS = createTag("gourd_lanterns");
		public static final Tag.Identified<Item> SOUL_GOURD_LANTERNS = createTag("soul_gourd_lanterns");
		public static final Tag.Identified<Item> ENDER_GOURD_LANTERNS = createTag("ender_gourd_lanterns");
		public static final Tag.Identified<Item> MELON_LANTERNS = createTag("melon_lanterns");
		public static final Tag.Identified<Item> SOUL_MELON_LANTERNS = createTag("soul_melon_lanterns");
		public static final Tag.Identified<Item> ENDER_MELON_LANTERNS = createTag("ender_melon_lanterns");
		public static final Tag.Identified<Item> JACK_O_LANTERNS = createTag("jack_o_lanterns");
		public static final Tag.Identified<Item> SOUL_JACK_O_LANTERNS = createTag("soul_jack_o_lanterns");
		public static final Tag.Identified<Item> ENDER_JACK_O_LANTERNS = createTag("ender_jack_o_lanterns");

		private static Tag.Identified<Item> createTag(String name) {
			return TagFactory.ITEM.create(ModBase.ID(name));
		}
		private static Tag.Identified<Item> createCommonTag(String name) {
			return TagFactory.ITEM.create(new Identifier("c", name));
		}
		private static Tag.Identified<Item> createMinecraftTag(String name) {
			return TagFactory.ITEM.create(new Identifier(name));
		}
	}
	public static class Events {
		public static final Tag.Identified<GameEvent> WARDEN_CAN_LISTEN = createMinecraftTag("warden_can_listen");
		public static final Tag.Identified<GameEvent> SHRIEKER_CAN_LISTEN = createMinecraftTag("shrieker_can_listen");

		private static Tag.Identified<GameEvent> createTag(String name) {
			return TagFactory.GAME_EVENT.create(ModBase.ID(name));
		}
		private static Tag.Identified<GameEvent> createCommonTag(String name) {
			return TagFactory.GAME_EVENT.create(new Identifier("c", name));
		}
		private static Tag.Identified<GameEvent> createMinecraftTag(String name) {
			return TagFactory.GAME_EVENT.create(new Identifier(name));
		}
	}
	public static class Biomes {
		public static final Tag.Identified<Biome> ANCIENT_CITY_HAS_STRUCTURE = createMinecraftTag("has_structure/ancient_city");
		public static final Tag.Identified<Biome> MINESHAFT_BLOCKING = createMinecraftTag("mineshaft_blocking");
		public static final Tag.Identified<Biome> SPAWNS_COLD_VARIANT_FROGS = createMinecraftTag("spawns_cold_variant_frogs");
		public static final Tag.Identified<Biome> SPAWNS_WARM_VARIANT_FROGS = createMinecraftTag("spawns_warm_variant_frogs");

		private static Tag.Identified<Biome> createTag(String name) {
			return TagFactory.BIOME.create(ModBase.ID(name));
		}
		private static Tag.Identified<Biome> createCommonTag(String name) {
			return TagFactory.BIOME.create(new Identifier("c", name));
		}
		private static Tag.Identified<Biome> createMinecraftTag(String name) {
			return TagFactory.BIOME.create(new Identifier(name));
		}
	}
}
