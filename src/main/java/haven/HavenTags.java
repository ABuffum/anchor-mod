package haven;

import net.fabricmc.fabric.api.tag.TagFactory;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

public class HavenTags {
	public static class Blocks {
		public static final Tag.Identified<Block> CARVED_GOURDS = createTag("carved_gourds");
		public static final Tag.Identified<Block> CONVERTIBLE_TO_MUD = createTag("convertible_to_mud");
		public static final Tag.Identified<Block> GOURDS = createTag("gourds");
		public static final Tag.Identified<Block> GOURD_LANTERNS = createTag("gourd_lanterns");
		public static final Tag.Identified<Block> PUMPKINS = createTag("pumpkins");
		public static final Tag.Identified<Block> SNAPS_GOAT_HORN = createTag("snaps_goat_horn");

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

		private static Tag.Identified<Item> createTag(String name) {
			return TagFactory.ITEM.create(HavenMod.ID(name));
		}
		private static Tag.Identified<Item> createCommonTag(String name) {
			return TagFactory.ITEM.create(new Identifier("c", name));
		}
	}
}
