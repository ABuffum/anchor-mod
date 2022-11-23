package haven;

import com.nhoryzon.mc.farmersdelight.registry.ItemsRegistry;
import com.nhoryzon.mc.farmersdelight.tag.Tags;
import haven.containers.IBlockItemContainer;
import haven.containers.WallBlockContainer;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.BlockLootTableGenerator;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.MatchToolLootCondition;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.function.BiConsumer;

import static haven.ModBase.*;

public class ModDatagen implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		fabricDataGenerator.addProvider(BlockLootGenerator::new);
		fabricDataGenerator.addProvider(BlockTagGenerator::new);
		fabricDataGenerator.addProvider(ItemTagGenerator::new);
	}

	private static class BlockLootGenerator extends SimpleFabricLootTableProvider {
		public BlockLootGenerator(FabricDataGenerator dataGenerator) {
			super(dataGenerator, LootContextTypes.BLOCK);
		}

		private void drops(BiConsumer<Identifier, LootTable.Builder> ibbc, IBlockItemContainer container) {
			ibbc.accept(container.getBlock().getLootTableId(), BlockLootTableGenerator.drops(container.getItem()));
		}
		private void drops(BiConsumer<Identifier, LootTable.Builder> ibbc, WallBlockContainer container) {
			drops(ibbc, (IBlockItemContainer)container);
			ibbc.accept(container.getWallBlock().getLootTableId(), BlockLootTableGenerator.drops(container.getItem()));
		}
		private void dropsNothing(BiConsumer<Identifier, LootTable.Builder> ibbc, Block block) {
			ibbc.accept(block.getLootTableId(), BlockLootTableGenerator.dropsNothing());
		}
		private void silkTouch(BiConsumer<Identifier, LootTable.Builder> ibbc, IBlockItemContainer container) {
			ibbc.accept(container.getBlock().getLootTableId(), BlockLootTableGenerator.dropsWithSilkTouch(container.getItem()));
		}
		private void silkTouch(BiConsumer<Identifier, LootTable.Builder> ibbc, IBlockItemContainer container, Item withoutSilkTouch) {
			Block block = container.getBlock();
			ibbc.accept(block.getLootTableId(), BlockLootTableGenerator.drops(block, withoutSilkTouch));
		}

		@Override
		public void accept(BiConsumer<Identifier, LootTable.Builder> ibbc) {
			//Extended Echo
			drops(ibbc, ECHO_MATERIAL.getBlock());
			//TODO: ECHO_SLAB
			drops(ibbc, ECHO_MATERIAL.getStairs());
			drops(ibbc, ECHO_MATERIAL.getWall());
			drops(ibbc, ECHO_MATERIAL.getCrystalBlock());
			//TODO: ECHO_CRYSTAL_SLAB
			drops(ibbc, ECHO_MATERIAL.getCrystalStairs());
			drops(ibbc, ECHO_MATERIAL.getCrystalWall());
			dropsNothing(ibbc, BUDDING_ECHO.getBlock());
			//TODO: ECHO_CLUSTER
			ibbc.accept(ECHO_CLUSTER.getBlock().getLootTableId(), BlockLootTableGenerator.dropsWithSilkTouch(ECHO_CLUSTER.getBlock(),
					ItemEntry.builder(ECHO_SHARD)
							.apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(4.0f)))
							.apply(ApplyBonusLootFunction.oreDrops(Enchantments.FORTUNE))
							.conditionally(MatchToolLootCondition.builder(ItemPredicate.Builder.create().tag(ItemTags.CLUSTER_MAX_HARVESTABLES)))
							.alternatively(BlockLootTableGenerator.applyExplosionDecay(ECHO_CLUSTER.getBlock(), ItemEntry.builder(ECHO_SHARD).apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(2.0f)))))));
			silkTouch(ibbc, LARGE_ECHO_BUD);
			silkTouch(ibbc, MEDIUM_ECHO_BUD);
			silkTouch(ibbc, SMALL_ECHO_BUD);
			//Mud
			drops(ibbc, MUD);
			drops(ibbc, PACKED_MUD);
			drops(ibbc, MUD_BRICKS);
			//TODO: MUD_SLAB
			drops(ibbc, MUD_BRICK_STAIRS);
			drops(ibbc, MUD_BRICK_WALL);
			//Mangrove
			drops(ibbc, MANGROVE_MATERIAL.getButton());
			//TODO: MANGROVE_DOOR
			drops(ibbc, MANGROVE_MATERIAL.getFence());
			drops(ibbc, MANGROVE_MATERIAL.getFenceGate());
			//TODO: MANGROVE_HANGING_SIGN
			drops(ibbc, MANGROVE_MATERIAL.getLog());
			drops(ibbc, MANGROVE_MATERIAL.getPlanks());
			drops(ibbc, MANGROVE_MATERIAL.getPressurePlate());
			//TODO: MANGROVE_PROPAGULE, POTTED_MANGROVE_PROPAGULE
			drops(ibbc, MANGROVE_ROOTS);
			drops(ibbc, MANGROVE_MATERIAL.getSign()); //Includes Wall Sign
			//TODO: MANGROVE_SLAB
			drops(ibbc, MANGROVE_MATERIAL.getStairs());
			drops(ibbc, MANGROVE_MATERIAL.getTrapdoor());
			drops(ibbc, MANGROVE_MATERIAL.getWood());
			drops(ibbc, MANGROVE_MATERIAL.getStrippedLog());
			drops(ibbc, MANGROVE_MATERIAL.getStrippedWood());
			//Frogs
			drops(ibbc, OCHRE_FROGLIGHT);
			drops(ibbc, VERDANT_FROGLIGHT);
			drops(ibbc, PEARLESCENT_FROGLIGHT);
			//Sculk & Deep Dark
			silkTouch(ibbc, SCULK_SENSOR);
			silkTouch(ibbc, SCULK);
			//TODO: SCULK_VEIN
			silkTouch(ibbc, SCULK_CATALYST);
			silkTouch(ibbc, SCULK_SHRIEKER);
			//Extended Sculk
			silkTouch(ibbc, CALCITE_SCULK_TURF, Items.CALCITE);
			silkTouch(ibbc, DEEPSLATE_SCULK_TURF, Items.DEEPSLATE);
			silkTouch(ibbc, DRIPSTONE_SCULK_TURF, Items.DRIPSTONE_BLOCK);
			silkTouch(ibbc, SMOOTH_BASALT_SCULK_TURF, Items.SMOOTH_BASALT);
			silkTouch(ibbc, TUFF_SCULK_TURF, Items.TUFF);
			silkTouch(ibbc, SCULK_STONE_MATERIAL.get());
			//TODO: SCULK_STONE_SLAB
			drops(ibbc, SCULK_STONE_MATERIAL.getStairs());
			drops(ibbc, SCULK_STONE_MATERIAL.getWall());
			drops(ibbc, SCULK_STONE_MATERIAL.getBricks());
			//TODO: SCULK_STONE_BRICK_SLAB
			drops(ibbc, SCULK_STONE_MATERIAL.getBrickStairs());
			drops(ibbc, SCULK_STONE_MATERIAL.getBrickWall());
		}
		/*
		public static final SculkStoneMaterial SCULK_STONE_MATERIAL = new SculkStoneMaterial();

		 */
	}

	private static class BlockTagGenerator extends FabricTagProvider<Block> {
		public BlockTagGenerator(FabricDataGenerator dataGenerator) {
			super(dataGenerator, Registry.BLOCK, "blocks", NAMESPACE + ":block_tag_generator");
		}

		@Override
		protected void generateTags() {
			getOrCreateTagBuilder(ModTags.Blocks.ANCIENT_CITY_REPLACEABLE)
					.add(Blocks.DEEPSLATE, Blocks.DEEPSLATE_BRICKS, Blocks.DEEPSLATE_TILES, Blocks.DEEPSLATE_BRICK_SLAB,
							Blocks.DEEPSLATE_TILE_SLAB, Blocks.DEEPSLATE_BRICK_STAIRS, Blocks.DEEPSLATE_TILE_WALL,
							Blocks.DEEPSLATE_BRICK_WALL, Blocks.COBBLED_DEEPSLATE, Blocks.CRACKED_DEEPSLATE_BRICKS,
							Blocks.CRACKED_DEEPSLATE_TILES)
					.addTag(ModTags.Blocks.SCULK_TURFS);

			getOrCreateTagBuilder(ModTags.Blocks.SCULK_TURFS)
					.add(CALCITE_SCULK_TURF.getBlock(), DEEPSLATE_SCULK_TURF.getBlock(), DRIPSTONE_SCULK_TURF.getBlock(),
							SMOOTH_BASALT_SCULK_TURF.getBlock(), TUFF_SCULK_TURF.getBlock());

			getOrCreateTagBuilder(ModTags.Blocks.PUMPKINS)
					.add(Blocks.PUMPKIN, WHITE_PUMPKIN.getGourd().getBlock(), ROTTEN_PUMPKIN.getBlock());
			getOrCreateTagBuilder(ModTags.Blocks.GOURDS).add(Blocks.MELON).addTag(ModTags.Blocks.PUMPKINS);
			getOrCreateTagBuilder(ModTags.Blocks.CARVED_MELONS)
					.add(CARVED_MELON.getBlock(), SOLEIL_CARVED_MELON.getBlock());
			getOrCreateTagBuilder(ModTags.Blocks.CARVED_PUMPKINS)
					.add(Blocks.CARVED_PUMPKIN, SOLEIL_CARVED_PUMPKIN.getBlock(),
							WHITE_PUMPKIN.getCarved().getBlock(), SOLEIL_CARVED_WHITE_PUMPKIN.getBlock(),
							CARVED_ROTTEN_PUMPKIN.getBlock(), SOLEIL_CARVED_ROTTEN_PUMPKIN.getBlock());
			getOrCreateTagBuilder(ModTags.Blocks.CARVED_GOURDS).addTag(ModTags.Blocks.CARVED_MELONS).addTag(ModTags.Blocks.CARVED_PUMPKINS);
			getOrCreateTagBuilder(ModTags.Blocks.SOUL_MELON_LANTERNS).add(SOUL_MELON_LANTERN.getBlock(), SOLEIL_SOUL_MELON_LANTERN.getBlock());
			getOrCreateTagBuilder(ModTags.Blocks.ENDER_MELON_LANTERNS).add(ENDER_MELON_LANTERN.getBlock(), SOLEIL_ENDER_MELON_LANTERN.getBlock());
			getOrCreateTagBuilder(ModTags.Blocks.MELON_LANTERNS)
					.add(MELON_LANTERN.getBlock(), SOLEIL_MELON_LANTERN.getBlock())
					.addTag(ModTags.Blocks.SOUL_MELON_LANTERNS)
					.addTag(ModTags.Blocks.ENDER_MELON_LANTERNS);
			getOrCreateTagBuilder(ModTags.Blocks.SOUL_JACK_O_LANTERNS)
					.add(SOUL_JACK_O_LANTERN.getBlock(), SOLEIL_SOUL_JACK_O_LANTERN.getBlock(),
							WHITE_SOUL_JACK_O_LANTERN.getBlock(), SOLEIL_WHITE_SOUL_JACK_O_LANTERN.getBlock(),
							ROTTEN_SOUL_JACK_O_LANTERN.getBlock(), SOLEIL_ROTTEN_SOUL_JACK_O_LANTERN.getBlock());
			getOrCreateTagBuilder(ModTags.Blocks.ENDER_JACK_O_LANTERNS)
					.add(ENDER_JACK_O_LANTERN.getBlock(), SOLEIL_ENDER_JACK_O_LANTERN.getBlock(),
							WHITE_ENDER_JACK_O_LANTERN.getBlock(), SOLEIL_WHITE_ENDER_JACK_O_LANTERN.getBlock(),
							ROTTEN_ENDER_JACK_O_LANTERN.getBlock(), SOLEIL_ROTTEN_ENDER_JACK_O_LANTERN.getBlock());
			getOrCreateTagBuilder(ModTags.Blocks.JACK_O_LANTERNS)
					.add(Blocks.JACK_O_LANTERN, SOLEIL_JACK_O_LANTERN.getBlock(),
							WHITE_JACK_O_LANTERN.getBlock(), SOLEIL_WHITE_JACK_O_LANTERN.getBlock(),
							ROTTEN_JACK_O_LANTERN.getBlock(), SOLEIL_ROTTEN_JACK_O_LANTERN.getBlock())
					.addTag(ModTags.Blocks.SOUL_JACK_O_LANTERNS)
					.addTag(ModTags.Blocks.ENDER_JACK_O_LANTERNS);
			getOrCreateTagBuilder(ModTags.Blocks.GOURD_LANTERNS).addTag(ModTags.Blocks.MELON_LANTERNS).addTag(ModTags.Blocks.JACK_O_LANTERNS);
		}
	}

	private static class ItemTagGenerator extends FabricTagProvider<Item> {
		public ItemTagGenerator(FabricDataGenerator dataGenerator) {
			super(dataGenerator, Registry.ITEM, "items", NAMESPACE + ":item_tag_generator");
		}

		@Override
		protected void generateTags() {
			getOrCreateTagBuilder(ItemTags.BEDS)
					.add(MOSS_BED.getItem(), RAINBOW_BED.getItem());

			getOrCreateTagBuilder(ModTags.Items.BOOKSHELF_BOOKS)
					.add(Items.BOOK, Items.ENCHANTED_BOOK, Items.WRITABLE_BOOK, Items.WRITTEN_BOOK);

			getOrCreateTagBuilder(ModTags.Items.HEAD_WEARABLE_BLOCKS).addTag(ModTags.Items.CARVED_GOURDS);

			getOrCreateTagBuilder(ItemTags.MUSIC_DISCS).add(MUSIC_DISC_OTHERSIDE, MUSIC_DISC_5);

			getOrCreateTagBuilder(ModTags.Items.AXES)
					.add(Items.WOODEN_AXE, Items.STONE_AXE, Items.IRON_AXE, Items.GOLDEN_AXE, Items.DIAMOND_AXE, Items.NETHERITE_AXE)
					.add(AMETHYST_MATERIAL.getAxe(), ECHO_MATERIAL.getAxe(), EMERALD_MATERIAL.getAxe(), QUARTZ_MATERIAL.getAxe(),
							COPPER_MATERIAL.getAxe(), DARK_IRON_MATERIAL.getAxe());
			getOrCreateTagBuilder(ModTags.Items.HOES)
					.add(Items.WOODEN_HOE, Items.STONE_HOE, Items.IRON_HOE, Items.GOLDEN_HOE, Items.DIAMOND_HOE, Items.NETHERITE_HOE)
					.add(AMETHYST_MATERIAL.getHoe(), ECHO_MATERIAL.getHoe(), EMERALD_MATERIAL.getHoe(), QUARTZ_MATERIAL.getHoe(),
							COPPER_MATERIAL.getHoe(), DARK_IRON_MATERIAL.getHoe());
			getOrCreateTagBuilder(ModTags.Items.PICKAXES)
					.add(Items.WOODEN_PICKAXE, Items.STONE_PICKAXE, Items.IRON_PICKAXE, Items.GOLDEN_PICKAXE, Items.DIAMOND_PICKAXE, Items.NETHERITE_PICKAXE)
					.add(AMETHYST_MATERIAL.getPickaxe(), ECHO_MATERIAL.getPickaxe(), EMERALD_MATERIAL.getPickaxe(), QUARTZ_MATERIAL.getPickaxe(),
							COPPER_MATERIAL.getPickaxe(), DARK_IRON_MATERIAL.getPickaxe());
			getOrCreateTagBuilder(ModTags.Items.SHOVELS)
					.add(Items.WOODEN_SHOVEL, Items.STONE_SHOVEL, Items.IRON_SHOVEL, Items.GOLDEN_SHOVEL, Items.DIAMOND_SHOVEL, Items.NETHERITE_SHOVEL)
					.add(AMETHYST_MATERIAL.getShovel(), ECHO_MATERIAL.getShovel(), EMERALD_MATERIAL.getShovel(), QUARTZ_MATERIAL.getShovel(),
							COPPER_MATERIAL.getShovel(), DARK_IRON_MATERIAL.getShovel());
			getOrCreateTagBuilder(ModTags.Items.SWORDS)
					.add(Items.WOODEN_SWORD, Items.STONE_SWORD, Items.IRON_SWORD, Items.GOLDEN_SWORD, Items.DIAMOND_SWORD, Items.NETHERITE_SWORD)
					.add(AMETHYST_MATERIAL.getSword(), ECHO_MATERIAL.getSword(), EMERALD_MATERIAL.getSword(), QUARTZ_MATERIAL.getSword(),
							COPPER_MATERIAL.getSword(), DARK_IRON_MATERIAL.getSword());
			getOrCreateTagBuilder(ModTags.Items.KNIVES)
					.add(ItemsRegistry.FLINT_KNIFE.get(), ItemsRegistry.IRON_KNIFE.get(), ItemsRegistry.GOLDEN_KNIFE.get(), ItemsRegistry.DIAMOND_KNIFE.get(), ItemsRegistry.NETHERITE_KNIFE.get())
					.add(AMETHYST_MATERIAL.getKnife(), ECHO_MATERIAL.getKnife(), EMERALD_MATERIAL.getKnife(), QUARTZ_MATERIAL.getKnife(),
							COPPER_MATERIAL.getKnife(), DARK_IRON_MATERIAL.getKnife());
			getOrCreateTagBuilder(Tags.KNIVES).addTag(ModTags.Items.KNIVES);
			getOrCreateTagBuilder(ModTags.Items.SHEARS)
					.add(Items.SHEARS)
					.add(COPPER_MATERIAL.getShears(), DARK_IRON_MATERIAL.getShears(), GOLD_MATERIAL.getShears(), NETHERITE_MATERIAL.getShears());

			getOrCreateTagBuilder(ModTags.Items.PUMPKINS)
					.add(Items.PUMPKIN, WHITE_PUMPKIN.getGourd().getItem(), ROTTEN_PUMPKIN.getItem());
			getOrCreateTagBuilder(ModTags.Items.GOURDS).add(Items.MELON).addTag(ModTags.Items.PUMPKINS);
			getOrCreateTagBuilder(ModTags.Items.CARVED_MELONS)
					.add(CARVED_MELON.getItem(), SOLEIL_CARVED_MELON.getItem());
			getOrCreateTagBuilder(ModTags.Items.CARVED_PUMPKINS)
					.add(Items.CARVED_PUMPKIN, SOLEIL_CARVED_PUMPKIN.getItem(),
							WHITE_PUMPKIN.getCarved().getItem(), SOLEIL_CARVED_WHITE_PUMPKIN.getItem(),
							CARVED_ROTTEN_PUMPKIN.getItem(), SOLEIL_CARVED_ROTTEN_PUMPKIN.getItem());
			getOrCreateTagBuilder(ModTags.Items.CARVED_GOURDS).addTag(ModTags.Items.CARVED_MELONS).addTag(ModTags.Items.CARVED_PUMPKINS);
			getOrCreateTagBuilder(ModTags.Items.SOUL_MELON_LANTERNS).add(SOUL_MELON_LANTERN.getItem(), SOLEIL_SOUL_MELON_LANTERN.getItem());
			getOrCreateTagBuilder(ModTags.Items.ENDER_MELON_LANTERNS).add(ENDER_MELON_LANTERN.getItem(), SOLEIL_ENDER_MELON_LANTERN.getItem());
			getOrCreateTagBuilder(ModTags.Items.MELON_LANTERNS)
					.add(MELON_LANTERN.getItem(), SOLEIL_MELON_LANTERN.getItem())
					.addTag(ModTags.Items.SOUL_MELON_LANTERNS)
					.addTag(ModTags.Items.ENDER_MELON_LANTERNS);
			getOrCreateTagBuilder(ModTags.Items.SOUL_JACK_O_LANTERNS)
					.add(SOUL_JACK_O_LANTERN.getItem(), SOLEIL_SOUL_JACK_O_LANTERN.getItem(),
							WHITE_SOUL_JACK_O_LANTERN.getItem(), SOLEIL_WHITE_SOUL_JACK_O_LANTERN.getItem(),
							ROTTEN_SOUL_JACK_O_LANTERN.getItem(), SOLEIL_ROTTEN_SOUL_JACK_O_LANTERN.getItem());
			getOrCreateTagBuilder(ModTags.Items.ENDER_JACK_O_LANTERNS)
					.add(ENDER_JACK_O_LANTERN.getItem(), SOLEIL_ENDER_JACK_O_LANTERN.getItem(),
							WHITE_ENDER_JACK_O_LANTERN.getItem(), SOLEIL_WHITE_ENDER_JACK_O_LANTERN.getItem(),
							ROTTEN_ENDER_JACK_O_LANTERN.getItem(), SOLEIL_ROTTEN_ENDER_JACK_O_LANTERN.getItem());
			getOrCreateTagBuilder(ModTags.Items.JACK_O_LANTERNS)
					.add(Items.JACK_O_LANTERN, SOLEIL_JACK_O_LANTERN.getItem(),
							WHITE_JACK_O_LANTERN.getItem(), SOLEIL_WHITE_JACK_O_LANTERN.getItem(),
							ROTTEN_JACK_O_LANTERN.getItem(), SOLEIL_ROTTEN_JACK_O_LANTERN.getItem())
					.addTag(ModTags.Items.SOUL_JACK_O_LANTERNS)
					.addTag(ModTags.Items.ENDER_JACK_O_LANTERNS);
			getOrCreateTagBuilder(ModTags.Items.GOURD_LANTERNS).addTag(ModTags.Items.MELON_LANTERNS).addTag(ModTags.Items.JACK_O_LANTERNS);
		}
	}
}
