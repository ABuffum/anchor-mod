package haven;

import net.fabricmc.fabric.api.tag.TagFactory;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.tag.GameEventTags;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.event.GameEvent;

public class HavenTags {
	public static class Blocks {
		public static final Tag.Identified<Block> ANCIENT_CITY_REPLACEABLE = createTag("ancient_city_replaceable");
		public static final Tag.Identified<Block> CARVED_GOURDS = createTag("carved_gourds");
		public static final Tag.Identified<Block> CONVERTIBLE_TO_MUD = createTag("convertible_to_mud");
		public static final Tag.Identified<Block> DAMPENS_VIBRATIONS = createTag("dampens_vibrations");
		public static final Tag.Identified<Block> FLEECE = createTag("fleece");
		public static final Tag.Identified<Block> GOURDS = createTag("gourds");
		public static final Tag.Identified<Block> GOURD_LANTERNS = createTag("gourd_lanterns");
		public static final Tag.Identified<Block> OCCLUDES_VIBRATION_SIGNALS = createTag("occludes_vibration_signals");
		public static final Tag.Identified<Block> PUMPKINS = createTag("pumpkins");
		public static final Tag.Identified<Block> SNAPS_GOAT_HORN = createTag("snaps_goat_horn");

		public static final Tag.Identified<Block> SCULK_REPLACEABLE = createTag("sculk_replaceable");
		public static final Tag.Identified<Block> SCULK_REPLACEABLE_WORLD_GEN = createTag("sculk_replaceable");

		private static Tag.Identified<Block> createTag(String name) {
			return TagFactory.BLOCK.create(HavenMod.ID(name));
		}
		private static Tag.Identified<Block> createCommonTag(String name) {
			return TagFactory.BLOCK.create(new Identifier("c", name));
		}
	}
	public static class Fluids {
		public static final Tag.Identified<Fluid> BLOOD = createTag("blood");
		public static final Tag.Identified<Fluid> MUD = createTag("mud");

		private static Tag.Identified<Fluid> createTag(String name) {
			return TagFactory.FLUID.create(HavenMod.ID(name));
		}
		private static Tag.Identified<Fluid> createCommonTag(String name) {
			return TagFactory.FLUID.create(new Identifier("c", name));
		}
	}
	public static class Items {
		public static final Tag.Identified<Item> CARVED_PUMPKINS = createTag("carved_pumpkins");
		public static final Tag.Identified<Item> HEAD_WEARABLE_BLOCKS = createTag("head_wearable_blocks");
		public static final Tag.Identified<Item> FLAVORED_MILK = createTag("flavored_milk");
		public static final Tag.Identified<Item> FLEECE = createTag("fleece");

		private static Tag.Identified<Item> createTag(String name) {
			return TagFactory.ITEM.create(HavenMod.ID(name));
		}
		private static Tag.Identified<Item> createCommonTag(String name) {
			return TagFactory.ITEM.create(new Identifier("c", name));
		}
	}
	public static class Events {
		public static final Tag.Identified<GameEvent> WARDEN_CAN_LISTEN = createTag("warden_can_listen");
		public static final Tag.Identified<GameEvent> SHRIEKER_CAN_LISTEN = createTag("shrieker_can_listen");

		private static Tag.Identified<GameEvent> createTag(String name) {
			return TagFactory.GAME_EVENT.create(HavenMod.ID(name));
		}
		private static Tag.Identified<GameEvent> createCommonTag(String name) {
			return TagFactory.GAME_EVENT.create(new Identifier("c", name));
		}
	}
	public static class Biomes {
		public static final Tag.Identified<Biome> ANCIENT_CITY_HAS_STRUCTURE = createTag("has_structure/ancient_city");
		public static final Tag.Identified<Biome> MINESHAFT_BLOCKING = createTag("mineshaft_blocking");
		public static final Tag.Identified<Biome> SPAWNS_COLD_VARIANT_FROGS = createTag("spawns_cold_variant_frogs");
		public static final Tag.Identified<Biome> SPAWNS_WARM_VARIANT_FROGS = createTag("spawns_warm_variant_frogs");

		private static Tag.Identified<Biome> createTag(String name) {
			return TagFactory.BIOME.create(HavenMod.ID(name));
		}
		private static Tag.Identified<Biome> createCommonTag(String name) {
			return TagFactory.BIOME.create(new Identifier("c", name));
		}
	}
}
