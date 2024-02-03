package fun.wich.gen.data.tag;

import fun.wich.ModConfig;
import fun.wich.ModId;
import fun.wich.container.BlockContainer;
import fun.wich.gen.data.ModDatagen;
import fun.wich.haven.HavenMod;
import fun.wich.util.ColorUtil;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.Tag;
import net.minecraft.util.DyeColor;
import net.minecraft.util.registry.Registry;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import static fun.wich.ModBase.*;
import static fun.wich.registry.ModBambooRegistry.*;

public class BlockTagGenerator extends FabricTagProvider<Block> {
	public BlockTagGenerator(FabricDataGenerator dataGenerator) {
		super(dataGenerator, Registry.BLOCK, "blocks", ModId.NAMESPACE + ":block_tag_generator");
	}

	@Override
	protected void generateTags() {
		//Datagen Cache
		for (Map.Entry<Tag.Identified<Block>, Set<Block>> entry : ModDatagen.Cache.Tags.BLOCK_TAGS.entrySet()) {
			getOrCreateTagBuilder(entry.getKey()).add(entry.getValue().toArray(Block[]::new));
			entry.getValue().clear();
		}
		ModDatagen.Cache.Tags.BLOCK_TAGS.clear();

		getOrCreateTagBuilder(ModBlockTags.TERRACOTTA)
				.add(Blocks.TERRACOTTA, Blocks.WHITE_TERRACOTTA, Blocks.ORANGE_TERRACOTTA)
				.add(Blocks.MAGENTA_TERRACOTTA, Blocks.LIGHT_BLUE_TERRACOTTA, Blocks.YELLOW_TERRACOTTA)
				.add(Blocks.LIME_TERRACOTTA, Blocks.PINK_TERRACOTTA, Blocks.GRAY_TERRACOTTA)
				.add(Blocks.LIGHT_GRAY_TERRACOTTA, Blocks.CYAN_TERRACOTTA, Blocks.PURPLE_TERRACOTTA)
				.add(Blocks.BLUE_TERRACOTTA, Blocks.BROWN_TERRACOTTA, Blocks.GREEN_TERRACOTTA)
				.add(Blocks.RED_TERRACOTTA, Blocks.BLACK_TERRACOTTA);

		getOrCreateTagBuilder(ModBlockTags.ALL_HANGING_SIGNS).addTag(ModBlockTags.CEILING_HANGING_SIGNS).addTag(ModBlockTags.WALL_HANGING_SIGNS);
		getOrCreateTagBuilder(ModBlockTags.ALL_SIGNS).addTag(BlockTags.SIGNS).addTag(ModBlockTags.ALL_HANGING_SIGNS);
		getOrCreateTagBuilder(ModBlockTags.ANCIENT_CITY_REPLACEABLE).addTag(ModBlockTags.SCULK_TURFS)
				.add(Blocks.DEEPSLATE, Blocks.DEEPSLATE_BRICKS, Blocks.DEEPSLATE_TILES, Blocks.DEEPSLATE_BRICK_SLAB)
				.add(Blocks.DEEPSLATE_TILE_SLAB, Blocks.DEEPSLATE_BRICK_STAIRS, Blocks.DEEPSLATE_TILE_WALL)
				.add(Blocks.DEEPSLATE_BRICK_WALL, Blocks.COBBLED_DEEPSLATE, Blocks.CRACKED_DEEPSLATE_BRICKS)
				.add(Blocks.CRACKED_DEEPSLATE_TILES);
		getOrCreateTagBuilder(BlockTags.AXE_MINEABLE)
				.add(WHITE_PUMPKIN_STEM, ATTACHED_WHITE_PUMPKIN_STEM)
				.add(GRAPE_VINES, GRAPE_VINES_PLANT)
				.addTag(ModBlockTags.ALL_HANGING_SIGNS)
				.addTag(ModBlockTags.BARRELS).addTag(ModBlockTags.POWDER_KEGS)
				.addTag(ModBlockTags.WOODEN_BEEHIVES)
				.addTag(ModBlockTags.BOOKSHELVES)
				.addTag(ModBlockTags.CHISELED_BOOKSHELVES)
				.addTag(ModBlockTags.CRAFTING_TABLES)
				.addTag(ModBlockTags.LECTERNS)
				.addTag(ModBlockTags.GOURDS).addTag(ModBlockTags.CARVED_GOURDS).addTag(ModBlockTags.GOURD_LANTERNS)
				.add(RAINBOW_BED.asBlock());
		getOrCreateTagBuilder(BlockTags.BASE_STONE_NETHER);
		getOrCreateTagBuilder(BlockTags.BASE_STONE_OVERWORLD);
		getOrCreateTagBuilder(BlockTags.BEACON_BASE_BLOCKS)
				.add(NETHERITE_BRICKS.asBlock(), CUT_NETHERITE.asBlock(), CUT_NETHERITE_PILLAR.asBlock())
				.add(CUT_EMERALD.asBlock(), EMERALD_BRICKS.asBlock());
		getOrCreateTagBuilder(BlockTags.BEE_GROWABLES).add(STRAWBERRY_BUSH);
		getOrCreateTagBuilder(BlockTags.BEEHIVES).addTag(ModBlockTags.WOODEN_BEEHIVES);
		getOrCreateTagBuilder(BlockTags.CANDLE_CAKES).add(SOUL_CANDLE_CAKE, ENDER_CANDLE_CAKE, NETHERRACK_CANDLE_CAKE);
		getOrCreateTagBuilder(BlockTags.CARPETS).addTag(ModBlockTags.WOOL_CARPETS).addTag(ModBlockTags.FLEECE_CARPETS);
		getOrCreateTagBuilder(ModBlockTags.COMBINATION_STEP_SOUND_BLOCKS).addTag(BlockTags.CARPETS)
				.add(Blocks.MOSS_BLOCK, GLOW_LICHEN_CARPET.asBlock())
				.add(Blocks.CRIMSON_ROOTS, Blocks.WARPED_ROOTS, Blocks.NETHER_SPROUTS, Blocks.SNOW);
		getOrCreateTagBuilder(BlockTags.CAULDRONS).add(MILK_CAULDRON, CHEESE_CAULDRON, BLOOD_CAULDRON, MUD_CAULDRON);
		getOrCreateTagBuilder(BlockTags.CLIMBABLE).add(GRAPE_VINES, GRAPE_VINES_PLANT);
		getOrCreateTagBuilder(ModBlockTags.COMPLETES_FIND_TREE_TUTORIAL).addTag(BlockTags.LOGS).addTag(BlockTags.LEAVES).addTag(BlockTags.WART_BLOCKS);
		getOrCreateTagBuilder(ModBlockTags.CONVERTIBLE_TO_MUD).add(Blocks.DIRT, Blocks.COARSE_DIRT, Blocks.ROOTED_DIRT);
		getOrCreateTagBuilder(ModBlockTags.DAMPENS_VIBRATIONS)
				.addTag(BlockTags.WOOL).addTag(ModBlockTags.WOOL_SLABS).addTag(ModBlockTags.WOOL_CARPETS)
				.addTag(ModBlockTags.FLEECE).addTag(ModBlockTags.FLEECE_SLABS).addTag(ModBlockTags.FLEECE_CARPETS);
		getOrCreateTagBuilder(ModBlockTags.FROG_PREFER_JUMP_TO).add(Blocks.LILY_PAD, Blocks.BIG_DRIPLEAF);
		getOrCreateTagBuilder(ModBlockTags.FROGS_SPAWNABLE_ON).add(Blocks.GRASS_BLOCK);
		getOrCreateTagBuilder(BlockTags.GEODE_INVALID_BLOCKS).add(BLOOD_FLUID_BLOCK, MUD_FLUID_BLOCK);
		getOrCreateTagBuilder(BlockTags.GUARDED_BY_PIGLINS).addTag(ModBlockTags.BARRELS)
				.add(GOLD_TORCH.asBlock(), GOLD_TORCH.getWallBlock())
				.add(GOLD_SOUL_TORCH.asBlock(), GOLD_SOUL_TORCH.getWallBlock())
				.add(GOLD_ENDER_TORCH.asBlock(), GOLD_ENDER_TORCH.getWallBlock())
				.add(UNDERWATER_GOLD_TORCH.asBlock(), UNDERWATER_GOLD_TORCH.getWallBlock());
		getOrCreateTagBuilder(BlockTags.HOE_MINEABLE).add(MOSS_BED.asBlock(), GLOW_LICHEN_BED.asBlock());
		getOrCreateTagBuilder(BlockTags.ICE);
		getOrCreateTagBuilder(BlockTags.LOGS).addTag(ModBlockTags.CHARRED_LOGS).addTag(ModBlockTags.GILDED_STEMS);
		getOrCreateTagBuilder(BlockTags.LOGS_THAT_BURN)
				.addTag(ModBlockTags.MANGROVE_LOGS).addTag(ModBlockTags.CHERRY_LOGS)
				.addTag(ModBlockTags.CASSIA_LOGS).addTag(ModBlockTags.DOGWOOD_LOGS);
		getOrCreateTagBuilder(ModBlockTags.MANGROVE_LOGS_CAN_GROW_THROUGH).add(Blocks.MOSS_CARPET, Blocks.VINE);
		getOrCreateTagBuilder(ModBlockTags.MANGROVE_ROOTS_CAN_GROW_THROUGH).add(Blocks.MOSS_CARPET, Blocks.VINE, Blocks.SNOW);
		if (ModConfig.REGISTER_HAVEN_MOD) getOrCreateTagBuilder(BlockTags.NEEDS_DIAMOND_TOOL).add(HavenMod.SUBSTITUTE_ANCHOR_BLOCK);
		getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL)
				.add(UNLIT_LANTERN, UNLIT_SOUL_LANTERN)
				.add(MILK_CAULDRON, CHEESE_CAULDRON, MUD_CAULDRON, BLOOD_CAULDRON);
		getOrCreateTagBuilder(ModBlockTags.OCCLUDES_VIBRATION_SIGNALS).addTag(BlockTags.WOOL).addTag(ModBlockTags.FLEECE);
		getOrCreateTagBuilder(ModBlockTags.OVERWORLD_NATURAL_LOGS).add(Blocks.ACACIA_LOG, Blocks.BIRCH_LOG, Blocks.DARK_OAK_LOG, Blocks.JUNGLE_LOG, Blocks.OAK_LOG, Blocks.SPRUCE_LOG);
		getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
				.add(UNLIT_LANTERN, UNLIT_SOUL_LANTERN)
				.add(MILK_CAULDRON, CHEESE_CAULDRON, MUD_CAULDRON, BLOOD_CAULDRON);
		if (ModConfig.REGISTER_HAVEN_MOD) getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(HavenMod.SUBSTITUTE_ANCHOR_BLOCK);
		getOrCreateTagBuilder(BlockTags.PIGLIN_REPELLENTS).add(UNLIT_SOUL_TORCH.asBlock(), UNLIT_SOUL_TORCH.getWallBlock(), UNLIT_SOUL_LANTERN, BLAZE_SOUL_TORCH.asBlock(), BLAZE_SOUL_TORCH.getWallBlock());
		getOrCreateTagBuilder(ModBlockTags.SCULK_REPLACEABLE)
				.addTag(BlockTags.BASE_STONE_OVERWORLD).addTag(BlockTags.DIRT).addTag(ModBlockTags.TERRACOTTA)
				.addTag(BlockTags.NYLIUM).addTag(BlockTags.BASE_STONE_NETHER).addTag(BlockTags.SAND)
				.add(Blocks.GRAVEL, Blocks.SOUL_SAND, Blocks.SOUL_SOIL, Blocks.CALCITE, Blocks.SMOOTH_BASALT)
				.add(Blocks.CLAY, Blocks.DRIPSTONE_BLOCK, Blocks.END_STONE, Blocks.SANDSTONE, Blocks.RED_SANDSTONE)
				.addTag(ModBlockTags.SCULK_TURFS);
		getOrCreateTagBuilder(ModBlockTags.SCULK_REPLACEABLE_WORLD_GEN).addTag(ModBlockTags.SCULK_REPLACEABLE)
				.add(Blocks.DEEPSLATE_BRICKS, Blocks.DEEPSLATE_TILES, Blocks.COBBLED_DEEPSLATE)
				.add(Blocks.CRACKED_DEEPSLATE_BRICKS, Blocks.CRACKED_DEEPSLATE_TILES, Blocks.POLISHED_DEEPSLATE);
		getOrCreateTagBuilder(ModBlockTags.SCULK_VEIN_CAN_PLACE_ON)
				.add(Blocks.STONE, Blocks.ANDESITE, Blocks.DIORITE, Blocks.GRANITE, Blocks.DRIPSTONE_BLOCK)
				.add(Blocks.CALCITE, Blocks.TUFF, Blocks.DEEPSLATE);
		getOrCreateTagBuilder(BlockTags.SIGNS);
		getOrCreateTagBuilder(BlockTags.SHOVEL_MINEABLE).add(MUD.asBlock(), MUDDY_MANGROVE_ROOTS.asBlock())
				.add(COARSE_DIRT_SLAB.asBlock(), BLAZE_POWDER_BLOCK.asBlock())
				.add(DYE_BLOCKS.values().stream().map(BlockContainer::asBlock).toArray(Block[]::new));
		getOrCreateTagBuilder(ModBlockTags.SMELTS_TO_GLASS).add(Blocks.SAND, Blocks.RED_SAND);
		getOrCreateTagBuilder(ModBlockTags.SNAPS_GOAT_HORN).addTag(ModBlockTags.OVERWORLD_NATURAL_LOGS)
				.add(Blocks.STONE, Blocks.PACKED_ICE, Blocks.IRON_ORE, Blocks.COAL_ORE, Blocks.COPPER_ORE, Blocks.EMERALD_ORE);
		getOrCreateTagBuilder(ModBlockTags.SNIFFER_DIGGABLE_BLOCK).add(Blocks.DIRT, Blocks.GRASS_BLOCK, Blocks.PODZOL, Blocks.COARSE_DIRT, Blocks.ROOTED_DIRT, Blocks.MOSS_BLOCK);
		getOrCreateTagBuilder(ModBlockTags.SNIFFER_EGG_HATCH_BOOST).add(Blocks.MOSS_BLOCK);
		getOrCreateTagBuilder(BlockTags.SNOW);
		getOrCreateTagBuilder(ModBlockTags.TRAIL_RUINS_REPLACEABLE).add(Blocks.GRAVEL);
		getOrCreateTagBuilder(ModBlockTags.VIBRATION_RESONATORS).add(Blocks.AMETHYST_BLOCK);
		getOrCreateTagBuilder(BlockTags.WALL_POST_OVERRIDE).add(UNLIT_TORCH.asBlock(), UNLIT_SOUL_TORCH.asBlock());
		getOrCreateTagBuilder(ModBlockTags.WOOL_CARPETS).add(Arrays.stream(DyeColor.values()).map(ColorUtil::GetWoolCarpetBlock).toArray(Block[]::new));

		getOrCreateTagBuilder(ModBlockTags.BAMBOO).add(Blocks.BAMBOO, Blocks.BAMBOO_SAPLING).add(DRIED_BAMBOO.asBlock());
		getOrCreateTagBuilder(ModBlockTags.BARRELS).add(Blocks.BARREL);
		getOrCreateTagBuilder(ModBlockTags.BOOKSHELVES).add(Blocks.BOOKSHELF);
		getOrCreateTagBuilder(ModBlockTags.BROOM_SWEEPS).addTag(BlockTags.LEAVES);
		getOrCreateTagBuilder(ModBlockTags.CARVED_GOURDS).addTag(ModBlockTags.CARVED_MELONS).addTag(ModBlockTags.CARVED_PUMPKINS);
		getOrCreateTagBuilder(ModBlockTags.CARVED_PUMPKINS).add(Blocks.CARVED_PUMPKIN);
		getOrCreateTagBuilder(ModBlockTags.COLD_BLOCKS).addTag(BlockTags.ICE).addTag(BlockTags.SNOW).add(Blocks.POWDER_SNOW_CAULDRON);
		getOrCreateTagBuilder(ModBlockTags.CRAFTING_TABLES).add(Blocks.CRAFTING_TABLE);
		getOrCreateTagBuilder(ModBlockTags.GOURD_LANTERNS).add(Blocks.JACK_O_LANTERN);
		getOrCreateTagBuilder(ModBlockTags.GOURDS).add(Blocks.MELON).addTag(ModBlockTags.PUMPKINS);
		getOrCreateTagBuilder(ModBlockTags.HAMMER_MINEABLE);
		getOrCreateTagBuilder(ModBlockTags.GRAPE_GROWABLE).add(GRAPE_VINES, GRAPE_VINES_PLANT);
		getOrCreateTagBuilder(ModBlockTags.INFLICTS_FIRE_DAMAGE).add(Blocks.FIRE, Blocks.LAVA, Blocks.LAVA_CAULDRON, Blocks.MAGMA_BLOCK);
		getOrCreateTagBuilder(ModBlockTags.LECTERNS).add(Blocks.LECTERN);
		getOrCreateTagBuilder(ModBlockTags.PISTON_IMMOVABLE).add(Blocks.OBSIDIAN, Blocks.CRYING_OBSIDIAN, Blocks.RESPAWN_ANCHOR);
		if (ModConfig.REGISTER_HAVEN_MOD) getOrCreateTagBuilder(ModBlockTags.PISTON_IMMOVABLE).add(HavenMod.SUBSTITUTE_ANCHOR_BLOCK);
		getOrCreateTagBuilder(ModBlockTags.PUMPKINS).add(Blocks.PUMPKIN);
		getOrCreateTagBuilder(ModBlockTags.SIZZLE_RAIN_BLOCKS).add(Blocks.MAGMA_BLOCK);
		getOrCreateTagBuilder(ModBlockTags.SLIME_BLOCKS).add(Blocks.SLIME_BLOCK);
		getOrCreateTagBuilder(ModBlockTags.STICKY).addTag(ModBlockTags.SLIME_BLOCKS).add(Blocks.HONEY_BLOCK);
		getOrCreateTagBuilder(ModBlockTags.WOODEN_BEEHIVES).add(Blocks.BEEHIVE);

		getOrCreateTagBuilder(ModBlockTags.ORIGINS_UNPHASEABLE).addTag(ModBlockTags.PISTON_IMMOVABLE);
	}
}