package haven;

import haven.blocks.*;
import haven.blocks.oxidizable.*;
import haven.boats.*;
import haven.effects.*;
import haven.entities.*;
import haven.features.*;
import haven.items.*;
import haven.materials.WoodMaterial;
import haven.mixins.SignTypeAccessor;
import haven.util.*;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.*;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.block.*;
import net.minecraft.block.entity.*;
import net.minecraft.entity.*;
import net.minecraft.entity.effect.*;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.*;
import net.minecraft.particle.*;
import net.minecraft.sound.*;
import net.minecraft.util.*;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.intprovider.*;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.*;
import net.minecraft.world.gen.stateprovider.*;
import net.minecraft.world.gen.trunk.*;

import java.util.*;
import java.util.function.ToIntFunction;

import static java.util.Map.entry;
import org.apache.logging.log4j.*;

@SuppressWarnings("deprecation")
public class HavenMod implements ModInitializer {
	public static Logger LOGGER = LogManager.getLogger();

	public static final String NAMESPACE = "haven";
	public static Identifier ID(String path) { return new Identifier(NAMESPACE, path); }

	public static final DyeColor[] COLORS = DyeColor.values();

	private static final Block ANCHOR_BLOCK = new AnchorBlock(FabricBlockSettings.of(Material.SOIL).hardness(1f));
	public static final ItemGroup ITEM_GROUP = FabricItemGroupBuilder.build(new Identifier(NAMESPACE, "general"), () -> new ItemStack(ANCHOR_BLOCK));
	public static final Item.Settings ITEM_SETTINGS = new Item.Settings().group(ITEM_GROUP);
	public static final HavenPair ANCHOR = new HavenPair(ANCHOR_BLOCK);
	public static BlockEntityType<AnchorBlockEntity> ANCHOR_BLOCK_ENTITY = FabricBlockEntityTypeBuilder.create(AnchorBlockEntity::new, ANCHOR.BLOCK).build(null);
	public static final Block SUBSTITUTE_ANCHOR_BLOCK = new SubstituteAnchorBlock(FabricBlockSettings.of(Material.SOIL).hardness(1f));
	public static BlockEntityType<SubstituteAnchorBlockEntity> SUBSTITUTE_ANCHOR_BLOCK_ENTITY = FabricBlockEntityTypeBuilder.create(SubstituteAnchorBlockEntity::new, SUBSTITUTE_ANCHOR_BLOCK).build(null);

	public static ToIntFunction<BlockState> luminance(int value) { return (state) -> value; }

	public static final HavenTorch BONE_TORCH = new HavenTorch("bone_torch", "bone_wall_torch", FabricBlockSettings.of(Material.DECORATION).noCollision().breakInstantly().nonOpaque().luminance(luminance(14)).sounds(BlockSoundGroup.BONE), ParticleTypes.FLAME);

	//More Copper
	public static final Item COPPER_NUGGET = new Item(ITEM_SETTINGS);
	public static final HavenPair MEDIUM_WEIGHTED_PRESSURE_PLATE = new HavenPair(new OxidizableWeightedPressurePlateBlock(Oxidizable.OxidizationLevel.UNAFFECTED, 75, AbstractBlock.Settings.of(Material.METAL).requiresTool().noCollision().strength(0.5F).sounds(BlockSoundGroup.WOOD)));
	public static final HavenPair EXPOSED_MEDIUM_WEIGHTED_PRESSURE_PLATE = new HavenPair(new OxidizableWeightedPressurePlateBlock(Oxidizable.OxidizationLevel.EXPOSED, 75, AbstractBlock.Settings.copy(MEDIUM_WEIGHTED_PRESSURE_PLATE.BLOCK)));
	public static final HavenPair WEATHERED_MEDIUM_WEIGHTED_PRESSURE_PLATE = new HavenPair(new OxidizableWeightedPressurePlateBlock(Oxidizable.OxidizationLevel.WEATHERED, 75, AbstractBlock.Settings.copy(MEDIUM_WEIGHTED_PRESSURE_PLATE.BLOCK)));
	public static final HavenPair OXIDIZED_MEDIUM_WEIGHTED_PRESSURE_PLATE = new HavenPair(new OxidizableWeightedPressurePlateBlock(Oxidizable.OxidizationLevel.OXIDIZED, 75, AbstractBlock.Settings.copy(MEDIUM_WEIGHTED_PRESSURE_PLATE.BLOCK)));
	public static final OxidationScale MEDIUM_WEIGHTED_PRESSURE_PLATE_OXIDATION = new OxidationScale(MEDIUM_WEIGHTED_PRESSURE_PLATE.BLOCK, EXPOSED_MEDIUM_WEIGHTED_PRESSURE_PLATE.BLOCK, WEATHERED_MEDIUM_WEIGHTED_PRESSURE_PLATE.BLOCK, OXIDIZED_MEDIUM_WEIGHTED_PRESSURE_PLATE.BLOCK);
	public static final HavenPair WAXED_MEDIUM_WEIGHTED_PRESSURE_PLATE = new HavenPair(new HavenWeightedPressurePlateBlock(75, AbstractBlock.Settings.copy(MEDIUM_WEIGHTED_PRESSURE_PLATE.BLOCK)));
	public static final HavenPair WAXED_EXPOSED_MEDIUM_WEIGHTED_PRESSURE_PLATE = new HavenPair(new HavenWeightedPressurePlateBlock(75, AbstractBlock.Settings.copy(WAXED_MEDIUM_WEIGHTED_PRESSURE_PLATE.BLOCK)));
	public static final HavenPair WAXED_WEATHERED_MEDIUM_WEIGHTED_PRESSURE_PLATE = new HavenPair(new HavenWeightedPressurePlateBlock(75, AbstractBlock.Settings.copy(WAXED_MEDIUM_WEIGHTED_PRESSURE_PLATE.BLOCK)));
	public static final HavenPair WAXED_OXIDIZED_MEDIUM_WEIGHTED_PRESSURE_PLATE = new HavenPair(new HavenWeightedPressurePlateBlock(75, AbstractBlock.Settings.copy(WAXED_MEDIUM_WEIGHTED_PRESSURE_PLATE.BLOCK)));

	public static final HavenPair COPPER_LANTERN = new HavenPair(new OxidizableLanternBlock(Oxidizable.OxidizationLevel.UNAFFECTED, AbstractBlock.Settings.of(Material.METAL).requiresTool().strength(3.5F).sounds(BlockSoundGroup.LANTERN).luminance(luminance(13)).nonOpaque()));
	public static final HavenPair EXPOSED_COPPER_LANTERN = new HavenPair(new OxidizableLanternBlock(Oxidizable.OxidizationLevel.EXPOSED, AbstractBlock.Settings.copy(COPPER_LANTERN.BLOCK)));
	public static final HavenPair WEATHERED_COPPER_LANTERN = new HavenPair(new OxidizableLanternBlock(Oxidizable.OxidizationLevel.WEATHERED, AbstractBlock.Settings.copy(COPPER_LANTERN.BLOCK)));
	public static final HavenPair OXIDIZED_COPPER_LANTERN = new HavenPair(new OxidizableLanternBlock(Oxidizable.OxidizationLevel.OXIDIZED, AbstractBlock.Settings.copy(COPPER_LANTERN.BLOCK)));
	public static final OxidationScale COPPER_LANTERN_OXIDATION = new OxidationScale(COPPER_LANTERN.BLOCK, EXPOSED_COPPER_LANTERN.BLOCK, WEATHERED_COPPER_LANTERN.BLOCK, OXIDIZED_COPPER_LANTERN.BLOCK);
	public static final HavenPair WAXED_COPPER_LANTERN = new HavenPair(new LanternBlock(AbstractBlock.Settings.copy(COPPER_LANTERN.BLOCK)));
	public static final HavenPair WAXED_EXPOSED_COPPER_LANTERN = new HavenPair(new LanternBlock(AbstractBlock.Settings.copy(WAXED_COPPER_LANTERN.BLOCK)));
	public static final HavenPair WAXED_WEATHERED_COPPER_LANTERN = new HavenPair(new LanternBlock(AbstractBlock.Settings.copy(WAXED_COPPER_LANTERN.BLOCK)));
	public static final HavenPair WAXED_OXIDIZED_COPPER_LANTERN = new HavenPair(new LanternBlock(AbstractBlock.Settings.copy(WAXED_COPPER_LANTERN.BLOCK)));

	public static final HavenPair COPPER_CHAIN = new HavenPair(new OxidizableChainBlock(Oxidizable.OxidizationLevel.UNAFFECTED, AbstractBlock.Settings.of(Material.METAL, MapColor.CLEAR).requiresTool().strength(5.0F, 6.0F).sounds(BlockSoundGroup.CHAIN).nonOpaque()));
	public static final HavenPair EXPOSED_COPPER_CHAIN = new HavenPair(new OxidizableChainBlock(Oxidizable.OxidizationLevel.EXPOSED, AbstractBlock.Settings.copy(COPPER_CHAIN.BLOCK)));
	public static final HavenPair WEATHERED_COPPER_CHAIN = new HavenPair(new OxidizableChainBlock(Oxidizable.OxidizationLevel.WEATHERED, AbstractBlock.Settings.copy(COPPER_CHAIN.BLOCK)));
	public static final HavenPair OXIDIZED_COPPER_CHAIN = new HavenPair(new OxidizableChainBlock(Oxidizable.OxidizationLevel.OXIDIZED, AbstractBlock.Settings.copy(COPPER_CHAIN.BLOCK)));
	public static final OxidationScale COPPER_CHAIN_OXIDATION = new OxidationScale(COPPER_CHAIN.BLOCK, EXPOSED_COPPER_CHAIN.BLOCK, WEATHERED_COPPER_CHAIN.BLOCK, OXIDIZED_COPPER_CHAIN.BLOCK);
	public static final HavenPair WAXED_COPPER_CHAIN = new HavenPair(new ChainBlock(AbstractBlock.Settings.copy(COPPER_CHAIN.BLOCK)));
	public static final HavenPair WAXED_EXPOSED_COPPER_CHAIN = new HavenPair(new ChainBlock(AbstractBlock.Settings.copy(WAXED_COPPER_CHAIN.BLOCK)));
	public static final HavenPair WAXED_WEATHERED_COPPER_CHAIN = new HavenPair(new ChainBlock(AbstractBlock.Settings.copy(WAXED_COPPER_CHAIN.BLOCK)));
	public static final HavenPair WAXED_OXIDIZED_COPPER_CHAIN = new HavenPair(new ChainBlock(AbstractBlock.Settings.copy(WAXED_COPPER_CHAIN.BLOCK)));

	public static final HavenPair COPPER_BARS = new HavenPair(new OxidizablePaneBlock(Oxidizable.OxidizationLevel.UNAFFECTED, AbstractBlock.Settings.of(Material.METAL, MapColor.CLEAR).requiresTool().strength(5.0F, 6.0F).sounds(BlockSoundGroup.METAL).nonOpaque()));
	public static final HavenPair EXPOSED_COPPER_BARS = new HavenPair(new OxidizablePaneBlock(Oxidizable.OxidizationLevel.EXPOSED, AbstractBlock.Settings.copy(COPPER_BARS.BLOCK)));
	public static final HavenPair WEATHERED_COPPER_BARS = new HavenPair(new OxidizablePaneBlock(Oxidizable.OxidizationLevel.WEATHERED, AbstractBlock.Settings.copy(COPPER_BARS.BLOCK)));
	public static final HavenPair OXIDIZED_COPPER_BARS = new HavenPair(new OxidizablePaneBlock(Oxidizable.OxidizationLevel.OXIDIZED, AbstractBlock.Settings.copy(COPPER_BARS.BLOCK)));
	public static final OxidationScale COPPER_BARS_OXIDATION = new OxidationScale(COPPER_BARS.BLOCK, EXPOSED_COPPER_BARS.BLOCK, WEATHERED_COPPER_BARS.BLOCK, OXIDIZED_COPPER_BARS.BLOCK);
	public static final HavenPair WAXED_COPPER_BARS = new HavenPair(new HavenPaneBlock(AbstractBlock.Settings.copy(COPPER_BARS.BLOCK)));
	public static final HavenPair WAXED_EXPOSED_COPPER_BARS = new HavenPair(new HavenPaneBlock(AbstractBlock.Settings.copy(WAXED_COPPER_BARS.BLOCK)));
	public static final HavenPair WAXED_WEATHERED_COPPER_BARS = new HavenPair(new HavenPaneBlock(AbstractBlock.Settings.copy(WAXED_COPPER_BARS.BLOCK)));
	public static final HavenPair WAXED_OXIDIZED_COPPER_BARS = new HavenPair(new HavenPaneBlock(AbstractBlock.Settings.copy(WAXED_COPPER_BARS.BLOCK)));

	public static final HavenPair CUT_COPPER_PILLAR = new HavenPair(new OxidizablePillarBlock(Oxidizable.OxidizationLevel.UNAFFECTED, AbstractBlock.Settings.copy(Blocks.CUT_COPPER)));
	public static final HavenPair EXPOSED_CUT_COPPER_PILLAR = new HavenPair(new OxidizablePillarBlock(Oxidizable.OxidizationLevel.EXPOSED, AbstractBlock.Settings.copy(Blocks.EXPOSED_CUT_COPPER)));
	public static final HavenPair WEATHERED_CUT_COPPER_PILLAR = new HavenPair(new OxidizablePillarBlock(Oxidizable.OxidizationLevel.WEATHERED, AbstractBlock.Settings.copy(Blocks.WEATHERED_CUT_COPPER)));
	public static final HavenPair OXIDIZED_CUT_COPPER_PILLAR = new HavenPair(new OxidizablePillarBlock(Oxidizable.OxidizationLevel.OXIDIZED, AbstractBlock.Settings.copy(Blocks.OXIDIZED_CUT_COPPER)));
	public static final OxidationScale COPPER_PILLAR_OXIDATION = new OxidationScale(CUT_COPPER_PILLAR.BLOCK, EXPOSED_CUT_COPPER_PILLAR.BLOCK, WEATHERED_CUT_COPPER_PILLAR.BLOCK, OXIDIZED_CUT_COPPER_PILLAR.BLOCK);
	public static final HavenPair WAXED_CUT_COPPER_PILLAR = new HavenPair(new PillarBlock(AbstractBlock.Settings.copy(Blocks.WAXED_CUT_COPPER)));
	public static final HavenPair WAXED_EXPOSED_CUT_COPPER_PILLAR = new HavenPair(new PillarBlock(AbstractBlock.Settings.copy(Blocks.WAXED_EXPOSED_CUT_COPPER)));
	public static final HavenPair WAXED_WEATHERED_CUT_COPPER_PILLAR = new HavenPair(new PillarBlock(AbstractBlock.Settings.copy(Blocks.WAXED_WEATHERED_CUT_COPPER)));
	public static final HavenPair WAXED_OXIDIZED_CUT_COPPER_PILLAR = new HavenPair(new PillarBlock(AbstractBlock.Settings.copy(Blocks.WAXED_OXIDIZED_CUT_COPPER)));

	//More Gold
	public static final HavenPair GOLD_LANTERN = new HavenPair(new LanternBlock(AbstractBlock.Settings.of(Material.METAL).requiresTool().strength(3.5F).sounds(BlockSoundGroup.LANTERN).luminance(luminance(15)).nonOpaque()));
	public static final HavenPair GOLD_CHAIN = new HavenPair(new ChainBlock(AbstractBlock.Settings.of(Material.METAL, MapColor.CLEAR).requiresTool().strength(5.0F, 6.0F).sounds(BlockSoundGroup.CHAIN).nonOpaque()));
	public static final HavenPair GOLD_BARS = new HavenPair(new HavenPaneBlock(AbstractBlock.Settings.of(Material.METAL, MapColor.CLEAR).requiresTool().strength(5.0F, 6.0F).sounds(BlockSoundGroup.METAL).nonOpaque()));
	public static final HavenPair CUT_GOLD = new HavenPair(new Block(AbstractBlock.Settings.copy(Blocks.GOLD_BLOCK)));
	public static final HavenPair CUT_GOLD_PILLAR = new HavenPair(new PillarBlock(AbstractBlock.Settings.copy(CUT_GOLD.BLOCK)));

	//More Iron
	public static final HavenPair CUT_IRON = new HavenPair(new Block(AbstractBlock.Settings.copy(Blocks.IRON_BLOCK)));
	public static final HavenPair CUT_IRON_PILLAR = new HavenPair(new PillarBlock(AbstractBlock.Settings.copy(CUT_IRON.BLOCK)));

	public static final Item TINKER_TOY = new Item(ITEM_SETTINGS);

	public static final HavenPair CHARCOAL_BLOCK = new HavenPair(new Block(AbstractBlock.Settings.of(Material.STONE, MapColor.BLACK).requiresTool().strength(5.0F, 6.0F)));

	public static final HavenPair AMETHYST_CRYSTAL_BLOCK = new HavenPair(new Block(FabricBlockSettings.of(Material.AMETHYST, MapColor.PURPLE).sounds(BlockSoundGroup.AMETHYST_CLUSTER).strength(1.5f, 1.5f).requiresTool().luminance(luminance(5))));
	public static final HavenPair AMETHYST_BRICKS = new HavenPair(new Block(FabricBlockSettings.of(Material.AMETHYST, MapColor.PURPLE).sounds(BlockSoundGroup.AMETHYST_BLOCK).strength(1.5f, 1.5f).requiresTool()));
	public static final HavenPair AMETHYST_BRICK_SLAB = new HavenPair(new HavenSlabBlock(AMETHYST_BRICKS.BLOCK));
	public static final HavenPair AMETHYST_BRICK_STAIRS = new HavenPair(new HavenStairsBlock(AMETHYST_BRICKS.BLOCK));
	public static final HavenPair AMETHYST_BRICK_WALL = new HavenPair(new HavenWallBlock(AMETHYST_BRICKS.BLOCK));
	public static final HavenPair AMETHYST_SLAB = new HavenPair(new HavenSlabBlock(Blocks.AMETHYST_BLOCK));
	public static final HavenPair AMETHYST_STAIRS = new HavenPair(new HavenStairsBlock(Blocks.AMETHYST_BLOCK));
	public static final HavenPair AMETHYST_WALL = new HavenPair(new HavenWallBlock(Blocks.AMETHYST_BLOCK));

	//Minecraft Earth Flowers
	public static final HavenFlower BUTTERCUP = new HavenFlower(StatusEffects.BLINDNESS, 11);
	public static final HavenFlower PINK_DAISY = new HavenFlower(StatusEffects.REGENERATION, 8);
	//Other Flowers
	public static final Map<DyeColor, HavenFlower> CARNATIONS = MapDyeColor((color) -> new HavenFlower(StatusEffects.WEAKNESS, 5));
	public static final HavenFlower ROSE = new HavenFlower(StatusEffects.WEAKNESS, 9);
	public static final HavenFlower BLUE_ROSE = new HavenFlower(StatusEffects.WEAKNESS, 9);
	public static final HavenFlower MAGENTA_TULIP = new HavenFlower(StatusEffects.FIRE_RESISTANCE, 4);
	public static final HavenFlower MARIGOLD = new HavenFlower(StatusEffects.WITHER, 5);
	public static final HavenFlower PINK_ALLIUM = new HavenFlower(StatusEffects.FIRE_RESISTANCE, 4);
	public static final HavenFlower LAVENDER = new HavenFlower(StatusEffects.INVISIBILITY, 8);
	public static final HavenFlower HYDRANGEA = new HavenFlower(StatusEffects.JUMP_BOOST, 7);
	public static final HavenTallPair AMARANTH = new HavenTallPair(new TallFlowerBlock(HavenFlower.TALL_SETTINGS));
	public static final HavenTallPair TALL_ALLIUM = new HavenTallPair(new TallFlowerBlock(HavenFlower.TALL_SETTINGS));
	public static final HavenTallPair TALL_PINK_ALLIUM = new HavenTallPair(new TallFlowerBlock(HavenFlower.TALL_SETTINGS));

	public static final ToolItem PTEROR = new SwordItem(ToolMaterials.NETHERITE, 3, -2.4F, new Item.Settings().group(ITEM_GROUP).fireproof());
	public static final ToolItem SBEHESOHE = new SwordItem(ToolMaterials.DIAMOND, 3, -2.4F, new Item.Settings().group(ITEM_GROUP).fireproof());
	public static final Item BROKEN_BOTTLE = new Item(ITEM_SETTINGS);
	public static final Item LOCKET = new Item(ITEM_SETTINGS);
	public static final Item EMERALD_LOCKET = new Item(ITEM_SETTINGS);

	public static final HavenPair SOFT_TNT = new HavenPair(new SoftTntBlock(AbstractBlock.Settings.of(Material.TNT).breakInstantly().sounds(BlockSoundGroup.GRASS)));
	public static final EntityType<SoftTntEntity> SOFT_TNT_ENTITY = new FabricEntityTypeBuilderImpl<SoftTntEntity>(SpawnGroup.MISC, SoftTntEntity::new)
			.dimensions(EntityDimensions.fixed(0.98F, 0.98F)).fireImmune().trackRangeBlocks(10).trackedUpdateRate(10).build();

	public static final Block COFFEE_PLANT = new CoffeePlantBlock(AbstractBlock.Settings.of(Material.PLANT).ticksRandomly().noCollision().sounds(BlockSoundGroup.CROP));
	public static final Item COFFEE_CHERRY = new AliasedBlockItem(COFFEE_PLANT, new Item.Settings().group(ITEM_GROUP).food(new FoodComponent.Builder()
		.statusEffect(new StatusEffectInstance(StatusEffects.SPEED, 60, 0), 1.0F)
		.statusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 60, 0), 1.0F)
		.hunger(2).saturationModifier(0.1F)
		.build()));
	public static final Item COFFEE_BEANS = new Item(ITEM_SETTINGS);
	public static final Item COFFEE = new CoffeeItem((new Item.Settings()).group(ITEM_GROUP).food((new FoodComponent.Builder())
		.statusEffect(new StatusEffectInstance(StatusEffects.SPEED, 200, 0), 1.0F)
		.statusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 200, 0), 1.0F)
		.build()));
	public static final Item BLACK_COFFEE = new CoffeeItem((new Item.Settings()).group(ITEM_GROUP).food((new FoodComponent.Builder())
		.statusEffect(new StatusEffectInstance(StatusEffects.SPEED, 200, 1), 1.0F)
		.statusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 200, 1), 1.0F)
		.build()));

	public static final Item CINNAMON = new Item(ITEM_SETTINGS);

	public static final WoodMaterial CHERRY = new WoodMaterial("cherry", MapColor.RAW_IRON_PINK, CherrySaplingGenerator::new);
	public static final HavenPair PALE_CHERRY_LEAVES = new HavenPair(new HavenLeavesBlock(CHERRY.LEAVES.BLOCK));
	public static final HavenPair PINK_CHERRY_LEAVES = new HavenPair(new HavenLeavesBlock(CHERRY.LEAVES.BLOCK));
	public static final HavenPair WHITE_CHERRY_LEAVES = new HavenPair(new HavenLeavesBlock(CHERRY.LEAVES.BLOCK));
	public static final Item CHERRY_ITEM = new Item(new Item.Settings().group(ITEM_GROUP).food(new FoodComponent.Builder().hunger(4).saturationModifier(0.3F).build()));

	private static ConfiguredFeature<TreeFeatureConfig, ?> CherryTreeFeature(Block leaves) {
		return Feature.TREE.configure(
				new TreeFeatureConfig.Builder(
						new SimpleBlockStateProvider(CHERRY.LOG.BLOCK.getDefaultState()),
						new ForkingTrunkPlacer(5, 2, 2),
						new SimpleBlockStateProvider(leaves.getDefaultState()),
						new SimpleBlockStateProvider(CHERRY.SAPLING.BLOCK.getDefaultState()),
						new AcaciaFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0)),
						new TwoLayersFeatureSize(1, 0, 2)
				).ignoreVines().build()
		);
	}
	public static final ConfiguredFeature<TreeFeatureConfig, ?> CHERRY_TREE = CherryTreeFeature(CHERRY.LEAVES.BLOCK);
	public static final ConfiguredFeature<TreeFeatureConfig, ?> PALE_CHERRY_TREE = CherryTreeFeature(PALE_CHERRY_LEAVES.BLOCK);
	public static final ConfiguredFeature<TreeFeatureConfig, ?> PINK_CHERRY_TREE = CherryTreeFeature(PINK_CHERRY_LEAVES.BLOCK);
	public static final ConfiguredFeature<TreeFeatureConfig, ?> WHITE_CHERRY_TREE = CherryTreeFeature(WHITE_CHERRY_LEAVES.BLOCK);

	public static final WoodMaterial CASSIA = new WoodMaterial("cassia", MapColor.BROWN, BlockSoundGroup.AZALEA_LEAVES, CassiaSaplingGenerator::new);
	public static final HavenPair FLOWERING_CASSIA_LEAVES = new HavenPair(new HavenLeavesBlock(CASSIA.LEAVES.BLOCK));
	public static final ConfiguredFeature<TreeFeatureConfig, ?> CASSIA_TREE = Feature.TREE.configure(
			new TreeFeatureConfig.Builder(
					new SimpleBlockStateProvider(CASSIA.LOG.BLOCK.getDefaultState()),
					new BendingTrunkPlacer(4, 2, 0, 3, UniformIntProvider.create(1, 2)),
					new WeightedBlockStateProvider(
							new DataPool.Builder()
									.add(CASSIA.LEAVES.BLOCK.getDefaultState(), 3)
									.add(FLOWERING_CASSIA_LEAVES.BLOCK.getDefaultState(), 1)
					),
					new SimpleBlockStateProvider(CASSIA.SAPLING.BLOCK.getDefaultState()),
					new RandomSpreadFoliagePlacer(ConstantIntProvider.create(3), ConstantIntProvider.create(0), ConstantIntProvider.create(2), 50),
					new TwoLayersFeatureSize(1, 0, 1)
			)
					.build()
	);

	public static final Item SNICKERDOODLE = new Item(new Item.Settings().group(ITEM_GROUP).food(FoodComponents.COOKIE));
	public static final Item CINNAMON_ROLL = new Item(new Item.Settings().group(ITEM_GROUP).food(new FoodComponent.Builder().hunger(3).saturationModifier(0.3F).build()));

	public static final Item APPLE_CIDER = new BottledDrinkItem(new Item.Settings().recipeRemainder(Items.GLASS_BOTTLE).food(new FoodComponent.Builder().hunger(5).saturationModifier(0.5F).build()).group(ITEM_GROUP));
	public static final Item RAMEN = new MushroomStewItem(new Item.Settings().maxCount(1).group(ITEM_GROUP).food(new FoodComponent.Builder().hunger(6).saturationModifier(0.6F).build()));
	public static final Item STIR_FRY = new MushroomStewItem(new Item.Settings().maxCount(1).group(ITEM_GROUP).food(new FoodComponent.Builder().hunger(6).saturationModifier(0.6F).build()));

	public static final FoodComponent CANDY_FOOD_COMPONENT = new FoodComponent.Builder().hunger(1).saturationModifier(0.1F).build();
	public static final Item.Settings CANDY_ITEM_SETTINGS = new Item.Settings().group(ITEM_GROUP).food(CANDY_FOOD_COMPONENT);
	public static final Item CINNAMON_BEAN = new Item(CANDY_ITEM_SETTINGS);
	public static final Item PINK_COTTON_CANDY = new Item(new Item.Settings().group(ITEM_GROUP).food(new FoodComponent.Builder().hunger(3).saturationModifier(0.1F).build()));
	public static final Item BLUE_COTTON_CANDY = new Item(new Item.Settings().group(ITEM_GROUP).food(new FoodComponent.Builder().hunger(3).saturationModifier(0.1F).build()));
	public static final Item CANDY_CANE = new Item(CANDY_ITEM_SETTINGS);
	public static final Item CARAMEL = new Item(CANDY_ITEM_SETTINGS);
	public static final Item CARAMEL_APPLE = new Item(new Item.Settings().group(ITEM_GROUP).food(new FoodComponent.Builder().hunger(5).saturationModifier(0.3F).build()));

	public static final Item AMETHYST_CANDY = new Item(ITEM_SETTINGS); //not edible usually (it's rocks)
	public static final Item.Settings ROCK_CANDY_SETTINGS = new Item.Settings().group(ITEM_GROUP).food(CANDY_FOOD_COMPONENT);
	public static final Map<DyeColor, Item> ROCK_CANDIES = MapDyeColor((color) -> new Item(ROCK_CANDY_SETTINGS));

	public static final Item THROWABLE_TOMATO_ITEM = new ThrowableTomatoItem(new Item.Settings().group(ITEM_GROUP).maxCount(16));
	public static final EntityType<ThrownTomatoEntity> THROWABLE_TOMATO_ENTITY = FabricEntityTypeBuilder.<ThrownTomatoEntity>create(SpawnGroup.MISC, ThrownTomatoEntity::new).dimensions(EntityDimensions.fixed(0.25F, 0.25F)).trackRangeBlocks(4).trackedUpdateRate(10).build();
	public static final DefaultParticleType TOMATO_PARTICLE = FabricParticleTypes.simple();
	public static final StatusEffect BOO_EFFECT = new BooEffect();
	public static final StatusEffect KILLJOY_EFFECT = new KilljoyEffect();

	//Server Blood
	public static final HavenPair BLOOD_BLOCK = new HavenPair(new Block(AbstractBlock.Settings.of(Material.SOLID_ORGANIC, MapColor.RED).strength(1.0F).sounds(BlockSoundGroup.WART_BLOCK)));
	public static final HavenPair BLOOD_FENCE = new HavenPair(new HavenFenceBlock(BLOOD_BLOCK.BLOCK));
	public static final HavenPair BLOOD_PANE = new HavenPair(new HavenPaneBlock(BLOOD_BLOCK.BLOCK));
	public static final HavenPair BLOOD_SLAB = new HavenPair(new HavenSlabBlock(BLOOD_BLOCK.BLOCK));
	public static final HavenPair BLOOD_STAIRS = new HavenPair(new HavenStairsBlock(BLOOD_BLOCK.BLOCK));
	public static final HavenPair BLOOD_WALL = new HavenPair(new HavenWallBlock(BLOOD_BLOCK.BLOCK));
	public static final HavenPair DRIED_BLOOD_BLOCK = new HavenPair(new Block(AbstractBlock.Settings.copy(BLOOD_BLOCK.BLOCK)));
	public static final HavenPair DRIED_BLOOD_FENCE = new HavenPair(new HavenFenceBlock(DRIED_BLOOD_BLOCK.BLOCK));
	public static final HavenPair DRIED_BLOOD_PANE = new HavenPair(new HavenPaneBlock(DRIED_BLOOD_BLOCK.BLOCK));
	public static final HavenPair DRIED_BLOOD_SLAB = new HavenPair(new HavenSlabBlock(DRIED_BLOOD_BLOCK.BLOCK));
	public static final HavenPair DRIED_BLOOD_STAIRS = new HavenPair(new HavenStairsBlock(DRIED_BLOOD_BLOCK.BLOCK));
	public static final HavenPair DRIED_BLOOD_WALL = new HavenPair(new HavenWallBlock(DRIED_BLOOD_BLOCK.BLOCK));

	public static final FlowableFluid STILL_BLOOD_FLUID = new BloodFluid.Still();
	public static final FlowableFluid FLOWING_BLOOD_FLUID = new BloodFluid.Flowing();
	public static final FluidBlock BLOOD_FLUID_BLOCK = new BloodFluidBlock(STILL_BLOOD_FLUID, FabricBlockSettings.copyOf(Blocks.WATER).mapColor(MapColor.RED));

	public static final Block BLOOD_CAULDRON = new BloodCauldronBlock(FabricBlockSettings.copyOf(Blocks.CAULDRON));
	public static final Item BLOOD_BOTTLE = new BloodBottleItem(new Item.Settings().recipeRemainder(Items.GLASS_BOTTLE).maxCount(1).group(ITEM_GROUP));
	public static final BucketItem BLOOD_BUCKET = new BloodBucketItem(STILL_BLOOD_FLUID, new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1).group(ITEM_GROUP));

	//Angel Bat
	public static final EntityType<AngelBatEntity> ANGEL_BAT_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, AngelBatEntity::new).dimensions(EntityDimensions.fixed(0.5F, 0.9F)).trackRangeBlocks(5).build();

	//Chicken Variants
	public static final EntityType<FancyChickenEntity> FANCY_CHICKEN_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, FancyChickenEntity::new).dimensions(EntityDimensions.fixed(0.4F, 0.7F)).trackRangeBlocks(10).build();
	public static final Item FANCY_CHICKEN_SPAWN_EGG = new SpawnEggItem(FANCY_CHICKEN_ENTITY, 16777215, 16777215, ITEM_SETTINGS);
	public static final Item FANCY_FEATHER = new Item(ITEM_SETTINGS);
	//Cow Variants
	public static final EntityType<MoobloomEntity> MOOBLOOM_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, MoobloomEntity::new).dimensions(EntityDimensions.fixed(0.9F, 1.4F)).trackRangeBlocks(10).build();
	public static final Item MOOBLOOM_SPAWN_EGG = new SpawnEggItem(MOOBLOOM_ENTITY, 16777215, 16777215, ITEM_SETTINGS);
	public static final EntityType<MooblossomEntity> MOOBLOSSOM_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, MooblossomEntity::new).dimensions(EntityDimensions.fixed(0.9F, 1.4F)).trackRangeBlocks(10).build();
	public static final Item MOOBLOSSOM_SPAWN_EGG = new SpawnEggItem(MOOBLOSSOM_ENTITY, 16777215, 16777215, ITEM_SETTINGS);
	public static final Block MAGENTA_MOOBLOSSOM_TULIP = new FlowerBlock(StatusEffects.FIRE_RESISTANCE, 4, HavenFlower.SETTINGS);
	public static final EntityType<MoolipEntity> MOOLIP_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, MoolipEntity::new).dimensions(EntityDimensions.fixed(0.9F, 1.4F)).trackRangeBlocks(10).build();
	public static final Item MOOLIP_SPAWN_EGG = new SpawnEggItem(MOOLIP_ENTITY, 16777215, 16777215, ITEM_SETTINGS);
	public static final EntityType<OrangeMooblossomEntity> ORANGE_MOOBLOSSOM_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, OrangeMooblossomEntity::new).dimensions(EntityDimensions.fixed(0.9F, 1.4F)).trackRangeBlocks(10).build();
	public static final Item ORANGE_MOOBLOSSOM_SPAWN_EGG = new SpawnEggItem(ORANGE_MOOBLOSSOM_ENTITY, 16777215, 16777215, ITEM_SETTINGS);
	public static final Block ORANGE_MOOBLOSSOM_TULIP = new FlowerBlock(StatusEffects.FIRE_RESISTANCE, 4, HavenFlower.SETTINGS);

	//Nether Mooshrooms
	public static final EntityType<CrimsonMooshroomEntity> CRIMSON_MOOSHROOM_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, CrimsonMooshroomEntity::new).dimensions(EntityDimensions.fixed(0.9F, 1.4F)).trackRangeBlocks(10).build();
	public static final Item CRIMSON_MOOSHROOM_SPAWN_EGG = new SpawnEggItem(CRIMSON_MOOSHROOM_ENTITY, 16777215, 16777215, ITEM_SETTINGS);
	public static final EntityType<WarpedMooshroomEntity> WARPED_MOOSHROOM_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, WarpedMooshroomEntity::new).dimensions(EntityDimensions.fixed(0.9F, 1.4F)).trackRangeBlocks(10).build();
	public static final Item WARPED_MOOSHROOM_SPAWN_EGG = new SpawnEggItem(WARPED_MOOSHROOM_ENTITY, 16777215, 16777215, ITEM_SETTINGS);

	//Flavored Milk
	public static final Item CHOCOLATE_MILK_BUCKET = new MilkBucketItem(new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1).group(ITEM_GROUP));
	public static final Item STRAWBERRY_MILK_BUCKET = new MilkBucketItem(new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1).group(ITEM_GROUP));
	public static final Item COFFEE_MILK_BUCKET = new CoffeeMilkBucketItem(new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1).group(ITEM_GROUP));

	//Cakes
	public static final HavenPair CHOCOLATE_CAKE = new HavenPair(new HavenCakeBlock(Flavor.CHOCOLATE));
	public static final HavenPair STRAWBERRY_CAKE = new HavenPair(new HavenCakeBlock(Flavor.STRAWBERRY));
	public static final HavenPair COFFEE_CAKE = new HavenPair(new HavenCakeBlock(Flavor.COFFEE));
	public static final HavenPair CARROT_CAKE = new HavenPair(new HavenCakeBlock(Flavor.CARROT));
	public static final HavenPair CONFETTI_CAKE = new HavenPair(new HavenCakeBlock(Flavor.CONFETTI));
	//Candle Cakes
	public static final HavenCandleCakeBlock CHOCOLATE_CANDLE_CAKE = new HavenCandleCakeBlock(Flavor.CHOCOLATE);
	public static final Map<DyeColor, HavenCandleCakeBlock> CHOCOLATE_CANDLE_CAKES = MapDyeColor((color) -> new HavenCandleCakeBlock(Flavor.CHOCOLATE));
	public static final HavenCandleCakeBlock STRAWBERRY_CANDLE_CAKE = new HavenCandleCakeBlock(Flavor.STRAWBERRY);
	public static final Map<DyeColor, HavenCandleCakeBlock> STRAWBERRY_CANDLE_CAKES = MapDyeColor((color) -> new HavenCandleCakeBlock(Flavor.STRAWBERRY));
	public static final HavenCandleCakeBlock COFFEE_CANDLE_CAKE = new HavenCandleCakeBlock(Flavor.COFFEE);
	public static final Map<DyeColor, HavenCandleCakeBlock> COFFEE_CANDLE_CAKES = MapDyeColor((color) -> new HavenCandleCakeBlock(Flavor.COFFEE));
	public static final HavenCandleCakeBlock CARROT_CANDLE_CAKE = new HavenCandleCakeBlock(Flavor.CARROT);
	public static final Map<DyeColor, HavenCandleCakeBlock> CARROT_CANDLE_CAKES = MapDyeColor((color) -> new HavenCandleCakeBlock(Flavor.CARROT));
	public static final HavenCandleCakeBlock CONFETTI_CANDLE_CAKE = new HavenCandleCakeBlock(Flavor.CONFETTI);
	public static final Map<DyeColor, HavenCandleCakeBlock> CONFETTI_CANDLE_CAKES = MapDyeColor((color) -> new HavenCandleCakeBlock(Flavor.CONFETTI));

	//Bottled Confetti
	public static final Item BOTTLED_CONFETTI_ITEM = new BottledConfettiItem(new Item.Settings().group(ITEM_GROUP).recipeRemainder(Items.GLASS_BOTTLE));
	public static final EntityType<BottledConfettiEntity> BOTTLED_CONFETTI_ENTITY = FabricEntityTypeBuilder.<BottledConfettiEntity>create(SpawnGroup.MISC, BottledConfettiEntity::new).dimensions(EntityDimensions.fixed(0.25F, 0.25F)).trackRangeBlocks(4).trackedUpdateRate(10).build();
	public static final EntityType<DroppedConfettiEntity> DROPPED_CONFETTI_ENTITY = FabricEntityTypeBuilder.<DroppedConfettiEntity>create(SpawnGroup.MISC, DroppedConfettiEntity::new).dimensions(EntityDimensions.fixed(0.25F, 0.25F)).trackRangeBlocks(4).trackedUpdateRate(10).build();
	public static final EntityType<ConfettiCloudEntity> CONFETTI_CLOUD_ENTITY = FabricEntityTypeBuilder.<ConfettiCloudEntity>create(SpawnGroup.MISC, ConfettiCloudEntity::new).build();

	//Boats
	public static final HavenBoat CRIMSON_BOAT = new HavenBoat("crimson", Blocks.CRIMSON_PLANKS, true);
	public static final HavenBoat WARPED_BOAT = new HavenBoat("warped", Blocks.WARPED_PLANKS, true);
	public static final EntityType<HavenBoatEntity> BOAT_ENTITY = FabricEntityTypeBuilder.<HavenBoatEntity>create(SpawnGroup.MISC, HavenBoatEntity::new).dimensions(EntityDimensions.fixed(1.375F, 0.5625F)).trackRangeBlocks(10).build();

	@Override
	public void onInitialize() {
		HavenRegistry.RegisterAll();
	}

	public static <T> Map<DyeColor, T> MapDyeColor(DyeMapFunc<T> op) {
		Map<DyeColor, T> output = new HashMap<>();
		for(DyeColor c : COLORS) output.put(c, op.op(c));
		return output;
	}
	public interface DyeMapFunc<T> {
		public T op(DyeColor c);
	}
	
	public static Map<Integer, String> ANCHOR_MAP = Map.ofEntries(
		entry(1, "jackdaw"),
		entry(2, "august"),
		entry(3, "bird"),
		entry(4, "moth"),
		entry(5, "rat"),
		entry(6, "stars"),
		entry(7, "whimsy"),
		entry(8, "aster"),
		entry(9, "gawain"),
		entry(10, "sleep"),
		entry(11, "lux"),
		entry(12, "sylph"),
		entry(13, "angel"),
		entry(14, "captain"),
		entry(15, "oz"),
		entry(16, "navn"),
		entry(17, "amber"),
		entry(18, "kota"),
		entry(19, "rhys"),
		entry(20, "soleil"),
		entry(21, "dj"),
		entry(22, "miasma"),
		entry(23, "k")
	);
	public static Map<Integer, AnchorCoreItem> ANCHOR_CORES = new HashMap<>();
	public static final Set<WoodMaterial> WOOD_MATERIALS = Set.<WoodMaterial>of(
		CASSIA, CHERRY
	);
	public static final Map<Block, Block> STRIPPED_BLOCKS = new HashMap<Block, Block>();
	public static final Map<Item, Float> COMPOSTABLE_ITEMS = new HashMap<Item, Float>();
	public static final List<SignType> SIGN_TYPES = new ArrayList<SignType>();
	public static final Map<Block, Block> WAXED_BLOCKS = new HashMap<Block, Block>(Map.<Block, Block>ofEntries(
		entry(Blocks.COPPER_BLOCK, Blocks.WAXED_COPPER_BLOCK), entry(Blocks.EXPOSED_COPPER, Blocks.WAXED_EXPOSED_COPPER),
		entry(Blocks.WEATHERED_COPPER, Blocks.WAXED_WEATHERED_COPPER), entry(Blocks.OXIDIZED_COPPER, Blocks.WAXED_OXIDIZED_COPPER),
		entry(Blocks.CUT_COPPER, Blocks.WAXED_CUT_COPPER), entry(Blocks.EXPOSED_CUT_COPPER, Blocks.WAXED_EXPOSED_CUT_COPPER),
		entry(Blocks.WEATHERED_CUT_COPPER, Blocks.WAXED_WEATHERED_CUT_COPPER), entry(Blocks.OXIDIZED_CUT_COPPER, Blocks.WAXED_OXIDIZED_CUT_COPPER),
		entry(Blocks.CUT_COPPER_SLAB, Blocks.WAXED_CUT_COPPER_SLAB), entry(Blocks.EXPOSED_CUT_COPPER_SLAB, Blocks.WAXED_EXPOSED_CUT_COPPER_SLAB),
		entry(Blocks.WEATHERED_CUT_COPPER_SLAB, Blocks.WAXED_WEATHERED_CUT_COPPER_SLAB), entry(Blocks.OXIDIZED_CUT_COPPER_SLAB, Blocks.WAXED_OXIDIZED_CUT_COPPER_SLAB),
		entry(Blocks.CUT_COPPER_STAIRS, Blocks.WAXED_CUT_COPPER_STAIRS), entry(Blocks.EXPOSED_CUT_COPPER_STAIRS, Blocks.WAXED_EXPOSED_CUT_COPPER_STAIRS),
		entry(Blocks.WEATHERED_CUT_COPPER_STAIRS, Blocks.WAXED_WEATHERED_CUT_COPPER_STAIRS), entry(Blocks.OXIDIZED_CUT_COPPER_STAIRS, Blocks.WAXED_OXIDIZED_CUT_COPPER_STAIRS)
	));
	public static final Set<OxidationScale> OXIDIZABLE_BLOCKS = new HashSet<OxidationScale>(Set.<OxidationScale>of(
		OxidationScale.COPPER_BLOCK, OxidationScale.CUT_COPPER,
		OxidationScale.CUT_COPPER_SLAB, OxidationScale.CUT_COPPER_STAIRS
	));

	public static final Set<HavenPair> LEAVES = new HashSet<HavenPair>(Set.<HavenPair>of(
		PALE_CHERRY_LEAVES, PINK_CHERRY_LEAVES, WHITE_CHERRY_LEAVES,
		FLOWERING_CASSIA_LEAVES
	));
	public static final Set<HavenFlower> FLOWERS = new HashSet<HavenFlower>(Set.<HavenFlower>of(
		BUTTERCUP, PINK_DAISY, ROSE, BLUE_ROSE, MAGENTA_TULIP, MARIGOLD,
		PINK_ALLIUM, LAVENDER, HYDRANGEA
	));
	public static final Set<HavenPair> TALL_FLOWERS = new HashSet<HavenPair>(Set.<HavenPair>of(
		AMARANTH, TALL_ALLIUM, TALL_PINK_ALLIUM
	));

	public static final Set<Item> FLAVORED_MILK_BUCKETS = new HashSet<Item>(Set.<Item>of(
		CHOCOLATE_MILK_BUCKET, STRAWBERRY_MILK_BUCKET, COFFEE_MILK_BUCKET
	));

	static {
		for(Integer owner : ANCHOR_MAP.keySet()) {
			ANCHOR_CORES.put(owner, new AnchorCoreItem(owner));
		}
		//Wood Materials
		for(WoodMaterial material : WOOD_MATERIALS) {
			//Stripped Blocks
			STRIPPED_BLOCKS.put(material.WOOD.BLOCK, material.STRIPPED_WOOD.BLOCK);
			STRIPPED_BLOCKS.put(material.LOG.BLOCK, material.STRIPPED_LOG.BLOCK);
			//Leaves
			LEAVES.add(material.LEAVES);
			//Compostable Items
			COMPOSTABLE_ITEMS.put(material.SAPLING.ITEM, 0.3f);
			//Sign Types
			SIGN_TYPES.add(material.SIGN.TYPE);
		}
		//Flowers
		for(DyeColor color : COLORS) FLOWERS.add(CARNATIONS.get(color));
		//Compostable Items
		for(HavenFlower flower : FLOWERS) COMPOSTABLE_ITEMS.put(flower.ITEM, 0.65F);
		for(HavenPair flower : TALL_FLOWERS) COMPOSTABLE_ITEMS.put(flower.ITEM, 0.65F);
		for(HavenPair leaf : LEAVES) COMPOSTABLE_ITEMS.put(leaf.ITEM, 0.3f);
		COMPOSTABLE_ITEMS.put(CINNAMON, 0.2f);
		COMPOSTABLE_ITEMS.put(SNICKERDOODLE, 0.85F);
		COMPOSTABLE_ITEMS.put(COFFEE_CHERRY, 0.65F);
		COMPOSTABLE_ITEMS.put(COFFEE_BEANS, 0.65F);
		COMPOSTABLE_ITEMS.put(CHERRY_ITEM, 0.65F);
		COMPOSTABLE_ITEMS.put(CHOCOLATE_CAKE.ITEM, 1F);
		COMPOSTABLE_ITEMS.put(STRAWBERRY_CAKE.ITEM, 1F);
		COMPOSTABLE_ITEMS.put(COFFEE_CAKE.ITEM, 1F);
		COMPOSTABLE_ITEMS.put(CARROT_CAKE.ITEM, 1F);
		COMPOSTABLE_ITEMS.put(CONFETTI_CAKE.ITEM, 1F);
		//Oxidizable Blocks
		OXIDIZABLE_BLOCKS.add(MEDIUM_WEIGHTED_PRESSURE_PLATE_OXIDATION);
		OXIDIZABLE_BLOCKS.add(COPPER_LANTERN_OXIDATION);
		OXIDIZABLE_BLOCKS.add(COPPER_CHAIN_OXIDATION);
		OXIDIZABLE_BLOCKS.add(COPPER_BARS_OXIDATION);
		OXIDIZABLE_BLOCKS.add(COPPER_PILLAR_OXIDATION);
		//Waxed Blocks
		WAXED_BLOCKS.put(MEDIUM_WEIGHTED_PRESSURE_PLATE.BLOCK, WAXED_MEDIUM_WEIGHTED_PRESSURE_PLATE.BLOCK);
		WAXED_BLOCKS.put(EXPOSED_MEDIUM_WEIGHTED_PRESSURE_PLATE.BLOCK, WAXED_EXPOSED_MEDIUM_WEIGHTED_PRESSURE_PLATE.BLOCK);
		WAXED_BLOCKS.put(WEATHERED_MEDIUM_WEIGHTED_PRESSURE_PLATE.BLOCK, WAXED_WEATHERED_MEDIUM_WEIGHTED_PRESSURE_PLATE.BLOCK);
		WAXED_BLOCKS.put(OXIDIZED_MEDIUM_WEIGHTED_PRESSURE_PLATE.BLOCK, WAXED_OXIDIZED_MEDIUM_WEIGHTED_PRESSURE_PLATE.BLOCK);
		WAXED_BLOCKS.put(COPPER_LANTERN.BLOCK, WAXED_COPPER_LANTERN.BLOCK);
		WAXED_BLOCKS.put(EXPOSED_COPPER_LANTERN.BLOCK, WAXED_EXPOSED_COPPER_LANTERN.BLOCK);
		WAXED_BLOCKS.put(WEATHERED_COPPER_LANTERN.BLOCK, WAXED_WEATHERED_COPPER_LANTERN.BLOCK);
		WAXED_BLOCKS.put(OXIDIZED_COPPER_LANTERN.BLOCK, WAXED_OXIDIZED_COPPER_LANTERN.BLOCK);
		WAXED_BLOCKS.put(COPPER_CHAIN.BLOCK, WAXED_COPPER_CHAIN.BLOCK);
		WAXED_BLOCKS.put(EXPOSED_COPPER_CHAIN.BLOCK, WAXED_EXPOSED_COPPER_CHAIN.BLOCK);
		WAXED_BLOCKS.put(WEATHERED_COPPER_CHAIN.BLOCK, WAXED_WEATHERED_COPPER_CHAIN.BLOCK);
		WAXED_BLOCKS.put(OXIDIZED_COPPER_CHAIN.BLOCK, WAXED_OXIDIZED_COPPER_CHAIN.BLOCK);
		WAXED_BLOCKS.put(COPPER_BARS.BLOCK, WAXED_COPPER_BARS.BLOCK);
		WAXED_BLOCKS.put(EXPOSED_COPPER_BARS.BLOCK, WAXED_EXPOSED_COPPER_BARS.BLOCK);
		WAXED_BLOCKS.put(WEATHERED_COPPER_BARS.BLOCK, WAXED_WEATHERED_COPPER_BARS.BLOCK);
		WAXED_BLOCKS.put(OXIDIZED_COPPER_BARS.BLOCK, WAXED_OXIDIZED_COPPER_BARS.BLOCK);
		WAXED_BLOCKS.put(CUT_COPPER_PILLAR.BLOCK, WAXED_CUT_COPPER_PILLAR.BLOCK);
		WAXED_BLOCKS.put(EXPOSED_CUT_COPPER_PILLAR.BLOCK, WAXED_EXPOSED_CUT_COPPER_PILLAR.BLOCK);
		WAXED_BLOCKS.put(WEATHERED_CUT_COPPER_PILLAR.BLOCK, WAXED_WEATHERED_CUT_COPPER_PILLAR.BLOCK);
		WAXED_BLOCKS.put(OXIDIZED_CUT_COPPER_PILLAR.BLOCK, WAXED_OXIDIZED_CUT_COPPER_PILLAR.BLOCK);
		//Sign Types
		SignTypeAccessor.getValues().addAll(SIGN_TYPES);
	}

	public static void log(Level level, String message){
		LOGGER.log(level, "[Haven] " + message);
	}
}
