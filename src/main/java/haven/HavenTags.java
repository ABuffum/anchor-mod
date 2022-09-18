package haven;

import net.fabricmc.fabric.api.tag.TagFactory;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

public class HavenTags {
	public static class Blocks {
		public static final Tag.Identified<Block> BAMBOO_LOGS = createTag("bamboo_logs");
		public static final Tag.Identified<Block> BARS = createTag("bars");
		public static final Tag.Identified<Block> BLOOD = createTag("blood");
		public static final Tag.Identified<Block> CASSIA_LEAVES = createTag("cassia_leaves");
		public static final Tag.Identified<Block> CASSIA_LOGS = createTag("cassia_logs");
		public static final Tag.Identified<Block> CHAINS = createTag("chains");
		public static final Tag.Identified<Block> CHERRY_LEAVES = createTag("cherry_leaves");
		public static final Tag.Identified<Block> CHERRY_LOGS = createTag("cherry_logs");
		public static final Tag.Identified<Block> COPPER_BARS = createTag("copper_bars");
		public static final Tag.Identified<Block> COPPER_CHAINS = createTag("copper_chains");
		public static final Tag.Identified<Block> DRIED_BAMBOO_LOGS = createTag("dried_bamboo_logs");
		public static final Tag.Identified<Block> GLASS_PANES = createTag("glass_panes");
		public static final Tag.Identified<Block> IRON_BARS = createTag("iron_bars");
		public static final Tag.Identified<Block> IRON_CHAINS = createTag("iron_chains");
		public static final Tag.Identified<Block> MANGROVE_LOGS = createTag("mangrove_logs");

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
		public static final Tag.Identified<Item> BAMBOO_ITEM = createTag("bamboo_item");
		public static final Tag.Identified<Item> BAMBOO_LOGS = createTag("bamboo_logs");
		public static final Tag.Identified<Item> BARS = createTag("bars");
		public static final Tag.Identified<Item> BLOOD = createTag("blood");
		public static final Tag.Identified<Item> BLOOD_BUCKETS = createTag("blood_buckets");
		public static final Tag.Identified<Item> BLOOD_SYRINGES = createTag("blood_syringes");
		public static final Tag.Identified<Item> BUCKETS = createTag("buckets");
		public static final Tag.Identified<Item> CANDY = createTag("candy");
		public static final Tag.Identified<Item> CASSIA_LEAVES = createTag("cassia_leaves");
		public static final Tag.Identified<Item> CASSIA_LOGS = createTag("cassia_logs");
		public static final Tag.Identified<Item> CHAINS = createTag("chains");
		public static final Tag.Identified<Item> CHERRY_LEAVES = createTag("cherry_leaves");
		public static final Tag.Identified<Item> CHERRY_LOGS = createTag("cherry_logs");
		public static final Tag.Identified<Item> CHOCOLATE_MILK_BUCKETS = createTag("chocolate_milk_buckets");
		public static final Tag.Identified<Item> COFFEE_MILK_BUCKETS = createTag("coffee_milk_buckets");
		public static final Tag.Identified<Item> COPPER_BARS = createTag("copper_bars");
		public static final Tag.Identified<Item> COPPER_CHAINS = createTag("copper_chains");
		public static final Tag.Identified<Item> COTTAGE_CHEESE_BUCKETS = createTag("cottage_cheese_buckets");
		public static final Tag.Identified<Item> DRIED_BAMBOO_LOGS = createTag("dried_bamboo_logs");
		public static final Tag.Identified<Item> DYES = createTag("dyes");
		public static final Tag.Identified<Item> FEATHERS = createTag("feathers");
		public static final Tag.Identified<Item> GLASS_PANES = createTag("glass_panes");
		public static final Tag.Identified<Item> IRON_BARS = createTag("iron_bars");
		public static final Tag.Identified<Item> IRON_CHAINS = createTag("iron_chains");
		public static final Tag.Identified<Item> JUICES = createTag("juices");
		public static final Tag.Identified<Item> LAVA_BUCKETS = createTag("lava_buckets");
		public static final Tag.Identified<Item> MANGROVE_LOGS = createTag("mangrove_logs");
		public static final Tag.Identified<Item> MILK_BUCKETS = createTag("milk_buckets");
		public static final Tag.Identified<Item> MUD_BUCKETS = createTag("mud_buckets");
		public static final Tag.Identified<Item> POWDER_SNOW_BUCKETS = createTag("powder_snow_buckets");
		public static final Tag.Identified<Item> SHEARS = createTag("shears");
		public static final Tag.Identified<Item> SMOOTHIES = createTag("smoothies");
		public static final Tag.Identified<Item> STRAWBERRY_MILK_BUCKETS = createTag("strawberry_milk_buckets");
		public static final Tag.Identified<Item> SYRINGES = createTag("syringes");
		public static final Tag.Identified<Item> WATER_BUCKETS = createTag("water_buckets");

		private static Tag.Identified<Item> createTag(String name) {
			return TagFactory.ITEM.create(HavenMod.ID(name));
		}
		private static Tag.Identified<Item> createCommonTag(String name) {
			return TagFactory.ITEM.create(new Identifier("c", name));
		}
	}
}
