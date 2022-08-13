package haven;

import haven.anchors.*;
import haven.blocks.*;
import haven.effects.BooEffect;
import haven.entities.*;
import haven.features.*;
import haven.items.*;

import haven.mixins.SignTypeAccessor;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.*;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.*;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.block.entity.*;
import net.minecraft.entity.*;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.SignType;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.*;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;

import java.util.*;

import static java.util.Map.entry;

import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.RandomSpreadFoliagePlacer;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.trunk.BendingTrunkPlacer;
import org.apache.logging.log4j.*;

@SuppressWarnings("deprecation")
public class HavenMod implements ModInitializer {
	public static Logger LOGGER = LogManager.getLogger();

	public static final String NAMESPACE = "haven";
	
	public static BlockEntityType<AnchorBlockEntity> ANCHOR_BLOCK_ENTITY;
	public static final Block ANCHOR_BLOCK = new AnchorBlock(FabricBlockSettings.of(Material.SOIL).hardness(1f));
	public static final Block SUBSTITUTE_ANCHOR_BLOCK = new SubstituteAnchorBlock(FabricBlockSettings.of(Material.SOIL).hardness(1f));

	public static final ItemGroup ITEM_GROUP = FabricItemGroupBuilder.build(new Identifier(NAMESPACE, "general"), () -> new ItemStack(ANCHOR_BLOCK));
	public static final Item.Settings ITEM_SETTINGS = new Item.Settings().group(ITEM_GROUP);

	public static final Item ANCHOR_ITEM = new BlockItem(ANCHOR_BLOCK, ITEM_SETTINGS);

	public static final Block BONE_TORCH_BLOCK = new BoneTorchBlock(FabricBlockSettings.of(Material.DECORATION).noCollision().breakInstantly().nonOpaque().luminance((state) -> {
		return 14;
	}).sounds(BlockSoundGroup.BONE), ParticleTypes.FLAME);
	public static final Block BONE_WALL_TORCH_BLOCK = new BoneWallTorchBlock(FabricBlockSettings.of(Material.DECORATION).noCollision().breakInstantly().nonOpaque().luminance((state) -> {
		return 14;
	}).sounds(BlockSoundGroup.WOOD), ParticleTypes.FLAME);
	public static final BlockItem BONE_TORCH_ITEM = new WallStandingBlockItem(BONE_TORCH_BLOCK, BONE_WALL_TORCH_BLOCK, ITEM_SETTINGS);

	public static final Item TINKER_TOY = new TinkerToy(ITEM_SETTINGS);

	public static final Block CHARCOAL_BLOCK = new Block(AbstractBlock.Settings.of(Material.STONE, MapColor.BLACK).requiresTool().strength(5.0F, 6.0F));
	public static final Item CHARCOAL_BLOCK_ITEM = new BlockItem(CHARCOAL_BLOCK, ITEM_SETTINGS);

	public static final Block AMETHYST_CRYSTAL_BLOCK = new AmethystCrystalBlock(FabricBlockSettings.of(Material.AMETHYST, MapColor.PURPLE).sounds(BlockSoundGroup.AMETHYST_CLUSTER).strength(1.5f, 1.5f).requiresTool().luminance((state) -> {
		return 5;
	}));
	public static final Item AMETHYST_CRYSTAL_BLOCK_ITEM = new BlockItem(AMETHYST_CRYSTAL_BLOCK, ITEM_SETTINGS);

	public static final Block AMETHYST_BRICKS_BLOCK = new AmethystBricksBlock(FabricBlockSettings.of(Material.AMETHYST, MapColor.PURPLE).sounds(BlockSoundGroup.AMETHYST_BLOCK).strength(1.5f, 1.5f).requiresTool());
	public static final Item AMETHYST_BRICKS_ITEM = new BlockItem(AMETHYST_BRICKS_BLOCK, ITEM_SETTINGS);
	public static final Block AMETHYST_BRICK_SLAB_BLOCK = new AmethystBrickSlabBlock(AbstractBlock.Settings.copy(AMETHYST_BRICKS_BLOCK));
	public static final Item AMETHYST_BRICK_SLAB_ITEM = new BlockItem(AMETHYST_BRICK_SLAB_BLOCK, ITEM_SETTINGS);
	public static final Block AMETHYST_BRICK_STAIRS_BLOCK = new AmethystBrickStairsBlock(AMETHYST_BRICKS_BLOCK.getDefaultState(), AbstractBlock.Settings.copy(AMETHYST_BRICKS_BLOCK));
	public static final Item AMETHYST_BRICK_STAIRS_ITEM = new BlockItem(AMETHYST_BRICK_STAIRS_BLOCK, ITEM_SETTINGS);
	public static final Block AMETHYST_BRICK_WALL_BLOCK = new AmethystBrickWallBlock(AbstractBlock.Settings.copy(AMETHYST_BRICKS_BLOCK));
	public static final Item AMETHYST_BRICK_WALL_ITEM = new BlockItem(AMETHYST_BRICK_WALL_BLOCK, ITEM_SETTINGS);

	public static final Block AMETHYST_SLAB_BLOCK = new AmethystSlabBlock(AbstractBlock.Settings.copy(Blocks.AMETHYST_BLOCK));
	public static final Item AMETHYST_SLAB_ITEM = new BlockItem(AMETHYST_SLAB_BLOCK, ITEM_SETTINGS);
	public static final Block AMETHYST_STAIRS_BLOCK = new AmethystStairsBlock(Blocks.AMETHYST_BLOCK.getDefaultState(), AbstractBlock.Settings.copy(Blocks.AMETHYST_BLOCK));
	public static final Item AMETHYST_STAIRS_ITEM = new BlockItem(AMETHYST_STAIRS_BLOCK, ITEM_SETTINGS);
	public static final Block AMETHYST_WALL_BLOCK = new AmethystWallBlock(AbstractBlock.Settings.copy(Blocks.AMETHYST_BLOCK));
	public static final Item AMETHYST_WALL_ITEM = new BlockItem(AMETHYST_WALL_BLOCK, ITEM_SETTINGS);

	//Carnations
	public static final Block BLACK_CARNATION_BLOCK = new FlowerBlock(StatusEffects.WEAKNESS, 5, FabricBlockSettings.of(Material.PLANT).noCollision().breakInstantly().nonOpaque().sounds(BlockSoundGroup.GRASS));
	public static final Item BLACK_CARNATION_ITEM = new BlockItem(BLACK_CARNATION_BLOCK, ITEM_SETTINGS);
	public static final Block POTTED_BLACK_CARNATION = new FlowerPotBlock(BLACK_CARNATION_BLOCK, AbstractBlock.Settings.of(Material.DECORATION).breakInstantly().nonOpaque());
	public static final Block BLUE_CARNATION_BLOCK = new FlowerBlock(StatusEffects.WEAKNESS, 5, FabricBlockSettings.of(Material.PLANT).noCollision().breakInstantly().nonOpaque().sounds(BlockSoundGroup.GRASS));
	public static final Item BLUE_CARNATION_ITEM = new BlockItem(BLUE_CARNATION_BLOCK, ITEM_SETTINGS);
	public static final Block POTTED_BLUE_CARNATION = new FlowerPotBlock(BLUE_CARNATION_BLOCK, AbstractBlock.Settings.of(Material.DECORATION).breakInstantly().nonOpaque());
	public static final Block BROWN_CARNATION_BLOCK = new FlowerBlock(StatusEffects.WEAKNESS, 5, FabricBlockSettings.of(Material.PLANT).noCollision().breakInstantly().nonOpaque().sounds(BlockSoundGroup.GRASS));
	public static final Item BROWN_CARNATION_ITEM = new BlockItem(BROWN_CARNATION_BLOCK, ITEM_SETTINGS);
	public static final Block POTTED_BROWN_CARNATION = new FlowerPotBlock(BROWN_CARNATION_BLOCK, AbstractBlock.Settings.of(Material.DECORATION).breakInstantly().nonOpaque());
	public static final Block CYAN_CARNATION_BLOCK = new FlowerBlock(StatusEffects.WEAKNESS, 5, FabricBlockSettings.of(Material.PLANT).noCollision().breakInstantly().nonOpaque().sounds(BlockSoundGroup.GRASS));
	public static final Item CYAN_CARNATION_ITEM = new BlockItem(CYAN_CARNATION_BLOCK, ITEM_SETTINGS);
	public static final Block POTTED_CYAN_CARNATION = new FlowerPotBlock(CYAN_CARNATION_BLOCK, AbstractBlock.Settings.of(Material.DECORATION).breakInstantly().nonOpaque());
	public static final Block GRAY_CARNATION_BLOCK = new FlowerBlock(StatusEffects.WEAKNESS, 5, FabricBlockSettings.of(Material.PLANT).noCollision().breakInstantly().nonOpaque().sounds(BlockSoundGroup.GRASS));
	public static final Item GRAY_CARNATION_ITEM = new BlockItem(GRAY_CARNATION_BLOCK, ITEM_SETTINGS);
	public static final Block POTTED_GRAY_CARNATION = new FlowerPotBlock(GRAY_CARNATION_BLOCK, AbstractBlock.Settings.of(Material.DECORATION).breakInstantly().nonOpaque());
	public static final Block GREEN_CARNATION_BLOCK = new FlowerBlock(StatusEffects.WEAKNESS, 5, FabricBlockSettings.of(Material.PLANT).noCollision().breakInstantly().nonOpaque().sounds(BlockSoundGroup.GRASS));
	public static final Item GREEN_CARNATION_ITEM = new BlockItem(GREEN_CARNATION_BLOCK, ITEM_SETTINGS);
	public static final Block POTTED_GREEN_CARNATION = new FlowerPotBlock(GREEN_CARNATION_BLOCK, AbstractBlock.Settings.of(Material.DECORATION).breakInstantly().nonOpaque());
	public static final Block LIGHT_BLUE_CARNATION_BLOCK = new FlowerBlock(StatusEffects.WEAKNESS, 5, FabricBlockSettings.of(Material.PLANT).noCollision().breakInstantly().nonOpaque().sounds(BlockSoundGroup.GRASS));
	public static final Item LIGHT_BLUE_CARNATION_ITEM = new BlockItem(LIGHT_BLUE_CARNATION_BLOCK, ITEM_SETTINGS);
	public static final Block POTTED_LIGHT_BLUE_CARNATION = new FlowerPotBlock(LIGHT_BLUE_CARNATION_BLOCK, AbstractBlock.Settings.of(Material.DECORATION).breakInstantly().nonOpaque());
	public static final Block LIGHT_GRAY_CARNATION_BLOCK = new FlowerBlock(StatusEffects.WEAKNESS, 5, FabricBlockSettings.of(Material.PLANT).noCollision().breakInstantly().nonOpaque().sounds(BlockSoundGroup.GRASS));
	public static final Item LIGHT_GRAY_CARNATION_ITEM = new BlockItem(LIGHT_GRAY_CARNATION_BLOCK, ITEM_SETTINGS);
	public static final Block POTTED_LIGHT_GRAY_CARNATION = new FlowerPotBlock(LIGHT_GRAY_CARNATION_BLOCK, AbstractBlock.Settings.of(Material.DECORATION).breakInstantly().nonOpaque());
	public static final Block LIME_CARNATION_BLOCK = new FlowerBlock(StatusEffects.WEAKNESS, 5, FabricBlockSettings.of(Material.PLANT).noCollision().breakInstantly().nonOpaque().sounds(BlockSoundGroup.GRASS));
	public static final Item LIME_CARNATION_ITEM = new BlockItem(LIME_CARNATION_BLOCK, ITEM_SETTINGS);
	public static final Block POTTED_LIME_CARNATION = new FlowerPotBlock(LIME_CARNATION_BLOCK, AbstractBlock.Settings.of(Material.DECORATION).breakInstantly().nonOpaque());
	public static final Block MAGENTA_CARNATION_BLOCK = new FlowerBlock(StatusEffects.WEAKNESS, 5, FabricBlockSettings.of(Material.PLANT).noCollision().breakInstantly().nonOpaque().sounds(BlockSoundGroup.GRASS));
	public static final Item MAGENTA_CARNATION_ITEM = new BlockItem(MAGENTA_CARNATION_BLOCK, ITEM_SETTINGS);
	public static final Block POTTED_MAGENTA_CARNATION = new FlowerPotBlock(MAGENTA_CARNATION_BLOCK, AbstractBlock.Settings.of(Material.DECORATION).breakInstantly().nonOpaque());
	public static final Block ORANGE_CARNATION_BLOCK = new FlowerBlock(StatusEffects.WEAKNESS, 5, FabricBlockSettings.of(Material.PLANT).noCollision().breakInstantly().nonOpaque().sounds(BlockSoundGroup.GRASS));
	public static final Item ORANGE_CARNATION_ITEM = new BlockItem(ORANGE_CARNATION_BLOCK, ITEM_SETTINGS);
	public static final Block POTTED_ORANGE_CARNATION = new FlowerPotBlock(ORANGE_CARNATION_BLOCK, AbstractBlock.Settings.of(Material.DECORATION).breakInstantly().nonOpaque());
	public static final Block PINK_CARNATION_BLOCK = new FlowerBlock(StatusEffects.WEAKNESS, 5, FabricBlockSettings.of(Material.PLANT).noCollision().breakInstantly().nonOpaque().sounds(BlockSoundGroup.GRASS));
	public static final Item PINK_CARNATION_ITEM = new BlockItem(PINK_CARNATION_BLOCK, ITEM_SETTINGS);
	public static final Block POTTED_PINK_CARNATION = new FlowerPotBlock(PINK_CARNATION_BLOCK, AbstractBlock.Settings.of(Material.DECORATION).breakInstantly().nonOpaque());
	public static final Block PURPLE_CARNATION_BLOCK = new FlowerBlock(StatusEffects.WEAKNESS, 5, FabricBlockSettings.of(Material.PLANT).noCollision().breakInstantly().nonOpaque().sounds(BlockSoundGroup.GRASS));
	public static final Item PURPLE_CARNATION_ITEM = new BlockItem(PURPLE_CARNATION_BLOCK, ITEM_SETTINGS);
	public static final Block POTTED_PURPLE_CARNATION = new FlowerPotBlock(PURPLE_CARNATION_BLOCK, AbstractBlock.Settings.of(Material.DECORATION).breakInstantly().nonOpaque());
	public static final Block RED_CARNATION_BLOCK = new FlowerBlock(StatusEffects.WEAKNESS, 5, FabricBlockSettings.of(Material.PLANT).noCollision().breakInstantly().nonOpaque().sounds(BlockSoundGroup.GRASS));
	public static final Item RED_CARNATION_ITEM = new BlockItem(RED_CARNATION_BLOCK, ITEM_SETTINGS);
	public static final Block POTTED_RED_CARNATION = new FlowerPotBlock(RED_CARNATION_BLOCK, AbstractBlock.Settings.of(Material.DECORATION).breakInstantly().nonOpaque());
	public static final Block WHITE_CARNATION_BLOCK = new FlowerBlock(StatusEffects.WEAKNESS, 5, FabricBlockSettings.of(Material.PLANT).noCollision().breakInstantly().nonOpaque().sounds(BlockSoundGroup.GRASS));
	public static final Item WHITE_CARNATION_ITEM = new BlockItem(WHITE_CARNATION_BLOCK, ITEM_SETTINGS);
	public static final Block POTTED_WHITE_CARNATION = new FlowerPotBlock(WHITE_CARNATION_BLOCK, AbstractBlock.Settings.of(Material.DECORATION).breakInstantly().nonOpaque());
	public static final Block YELLOW_CARNATION_BLOCK = new FlowerBlock(StatusEffects.WEAKNESS, 5, FabricBlockSettings.of(Material.PLANT).noCollision().breakInstantly().nonOpaque().sounds(BlockSoundGroup.GRASS));
	public static final Item YELLOW_CARNATION_ITEM = new BlockItem(YELLOW_CARNATION_BLOCK, ITEM_SETTINGS);
	public static final Block POTTED_YELLOW_CARNATION = new FlowerPotBlock(YELLOW_CARNATION_BLOCK, AbstractBlock.Settings.of(Material.DECORATION).breakInstantly().nonOpaque());

	public static final ToolItem PTEROR = new SwordItem(ToolMaterials.NETHERITE, 3, -2.4F, new Item.Settings().group(ITEM_GROUP).fireproof());
	public static final ToolItem SBEHESOHE = new SwordItem(ToolMaterials.DIAMOND, 3, -2.4F, new Item.Settings().group(ITEM_GROUP).fireproof());
	public static final TridentItem PRIDE_TRIDENT = new TridentItem(new Item.Settings().group(ITEM_GROUP).fireproof());

	public static final Block SOFT_TNT_BLOCK = new SoftTntBlock(AbstractBlock.Settings.of(Material.TNT).breakInstantly().sounds(BlockSoundGroup.GRASS));
	public static final Item SOFT_TNT_ITEM = new BlockItem(SOFT_TNT_BLOCK, ITEM_SETTINGS);
	public static final EntityType<SoftTntEntity> SOFT_TNT_ENTITY = new FabricEntityTypeBuilderImpl<SoftTntEntity>(SpawnGroup.MISC, SoftTntEntity::new)
			.dimensions(EntityDimensions.fixed(0.98F, 0.98F)).fireImmune().trackRangeBlocks(10).trackedUpdateRate(10).build();

	public static final Block COFFEE_PLANT = new CoffeePlantBlock(AbstractBlock.Settings.of(Material.PLANT).ticksRandomly().noCollision().sounds(BlockSoundGroup.CROP));
	public static final Item COFFEE_CHERRY = new AliasedBlockItem(COFFEE_PLANT, (new Item.Settings()).group(ITEM_GROUP).food(FoodComponents.SWEET_BERRIES));
	public static final Item COFFEE_BEANS = new Item(ITEM_SETTINGS);
	public static final Item COFFEE = new CoffeeItem((new Item.Settings()).group(ITEM_GROUP).food((new FoodComponent.Builder())
			.statusEffect(new StatusEffectInstance(StatusEffects.SPEED, 200, 0), 1.0F)
			.statusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 200, 0), 1.0F)
			.build()));
	public static final Item BLACK_COFFEE = new CoffeeItem((new Item.Settings()).group(ITEM_GROUP).food((new FoodComponent.Builder())
			.statusEffect(new StatusEffectInstance(StatusEffects.SPEED, 200, 1), 1.0F)
			.statusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 200, 1), 1.0F)
			.build()));

	public static final Item CINNAMON = new Cinnamon(ITEM_SETTINGS);

	public static final Block CASSIA_LOG_BLOCK = new PillarBlock(AbstractBlock.Settings.of(Material.WOOD, MapColor.BROWN).strength(2.0F).sounds(BlockSoundGroup.WOOD));
	public static final Item CASSIA_LOG_ITEM = new BlockItem(CASSIA_LOG_BLOCK, ITEM_SETTINGS);
	public static final Block STRIPPED_CASSIA_LOG_BLOCK = new PillarBlock(AbstractBlock.Settings.of(Material.WOOD, MapColor.BROWN).strength(2.0F).sounds(BlockSoundGroup.WOOD));
	public static final Item STRIPPED_CASSIA_LOG_ITEM = new BlockItem(STRIPPED_CASSIA_LOG_BLOCK, ITEM_SETTINGS);
	public static final Block CASSIA_WOOD_BLOCK = new PillarBlock(AbstractBlock.Settings.of(Material.WOOD, MapColor.BROWN).strength(2.0F).sounds(BlockSoundGroup.WOOD));
	public static final Item CASSIA_WOOD_ITEM = new BlockItem(CASSIA_WOOD_BLOCK, ITEM_SETTINGS);
	public static final Block STRIPPED_CASSIA_WOOD_BLOCK = new PillarBlock(AbstractBlock.Settings.of(Material.WOOD, MapColor.BROWN).strength(2.0F).sounds(BlockSoundGroup.WOOD));
	public static final Item STRIPPED_CASSIA_WOOD_ITEM = new BlockItem(STRIPPED_CASSIA_WOOD_BLOCK, ITEM_SETTINGS);

	private static Boolean canSpawnOnLeaves(BlockState state, BlockView world, BlockPos pos, EntityType<?> type) { return type == EntityType.OCELOT || type == EntityType.PARROT; }
	private static boolean never(BlockState state, BlockView world, BlockPos pos) { return false; }

	public static final Block CASSIA_LEAVES_BLOCK = new LeavesBlock(AbstractBlock.Settings.of(Material.LEAVES).strength(0.2F).ticksRandomly().sounds(BlockSoundGroup.AZALEA_LEAVES).nonOpaque().allowsSpawning(HavenMod::canSpawnOnLeaves).suffocates(HavenMod::never).blockVision(HavenMod::never));
	public static final Item CASSIA_LEAVES_ITEM = new BlockItem(CASSIA_LEAVES_BLOCK, ITEM_SETTINGS);
	public static final Block FLOWERING_CASSIA_LEAVES_BLOCK = new LeavesBlock(AbstractBlock.Settings.of(Material.LEAVES).strength(0.2F).ticksRandomly().sounds(BlockSoundGroup.AZALEA_LEAVES).nonOpaque().allowsSpawning(HavenMod::canSpawnOnLeaves).suffocates(HavenMod::never).blockVision(HavenMod::never));
	public static final Item FLOWERING_CASSIA_LEAVES_ITEM = new BlockItem(FLOWERING_CASSIA_LEAVES_BLOCK, ITEM_SETTINGS);
	public static final Block CASSIA_SAPLING_BLOCK = new CassiaSaplingBlock(new CassiaSaplingGenerator(), AbstractBlock.Settings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GRASS));
	public static final Item CASSIA_SAPLING_ITEM = new BlockItem(CASSIA_SAPLING_BLOCK, ITEM_SETTINGS);
	public static final Block POTTED_CASSIA_SAPLING_BLOCK = new FlowerPotBlock(CASSIA_SAPLING_BLOCK, AbstractBlock.Settings.of(Material.DECORATION).breakInstantly().nonOpaque());

	public static final ConfiguredFeature<TreeFeatureConfig, ?> CASSIA_TREE = Feature.TREE.configure(
		(new TreeFeatureConfig.Builder(
			new SimpleBlockStateProvider(CASSIA_LOG_BLOCK.getDefaultState()),
				new BendingTrunkPlacer(4, 2, 0, 3, UniformIntProvider.create(1, 2)),
				new WeightedBlockStateProvider(
						new DataPool.Builder()
						.add(CASSIA_LEAVES_BLOCK.getDefaultState(), 3)
						.add(FLOWERING_CASSIA_LEAVES_BLOCK.getDefaultState(), 1)
				),
				new SimpleBlockStateProvider(CASSIA_SAPLING_BLOCK.getDefaultState()),
				new RandomSpreadFoliagePlacer(ConstantIntProvider.create(3), ConstantIntProvider.create(0), ConstantIntProvider.create(2), 50),
				new TwoLayersFeatureSize(1, 0, 1)
			)
		)
		//.dirtProvider(new SimpleBlockStateProvider(Blocks.ROOTED_DIRT.getDefaultState()))
		//.forceDirt()
		.build()
	);

	public static final Block CASSIA_PLANKS_BLOCK = new Block(AbstractBlock.Settings.of(Material.WOOD, MapColor.BROWN).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD));
	public static final Item CASSIA_PLANKS_ITEM = new BlockItem(CASSIA_PLANKS_BLOCK, ITEM_SETTINGS);
	public static final Block CASSIA_STAIRS_BLOCK = new CassiaStairsBlock(CASSIA_PLANKS_BLOCK.getDefaultState(), AbstractBlock.Settings.copy(CASSIA_PLANKS_BLOCK));
	public static final Item CASSIA_STAIRS_ITEM = new BlockItem(CASSIA_STAIRS_BLOCK, ITEM_SETTINGS);
	public static final Block CASSIA_SLAB_BLOCK = new SlabBlock(AbstractBlock.Settings.of(Material.WOOD, MapColor.BROWN).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD));
	public static final Item CASSIA_SLAB_ITEM = new BlockItem(CASSIA_SLAB_BLOCK, ITEM_SETTINGS);

	public static final SignType CASSIA_SIGN_TYPE = new CassiaSignType("cassia");
	public static final Block CASSIA_SIGN_BLOCK = new SignBlock(AbstractBlock.Settings.of(Material.WOOD).noCollision().strength(1.0F).sounds(BlockSoundGroup.WOOD), CASSIA_SIGN_TYPE);
	public static final Block CASSIA_WALL_SIGN_BLOCK = new WallSignBlock(AbstractBlock.Settings.of(Material.WOOD).noCollision().strength(1.0F).sounds(BlockSoundGroup.WOOD).dropsLike(CASSIA_SIGN_BLOCK), CASSIA_SIGN_TYPE);
	public static final Item CASSIA_SIGN_ITEM = new SignItem((new Item.Settings()).maxCount(16).group(ITEM_GROUP), CASSIA_SIGN_BLOCK, CASSIA_WALL_SIGN_BLOCK);

	public static final Block CASSIA_FENCE_BLOCK = new FenceBlock(AbstractBlock.Settings.of(Material.WOOD, CASSIA_PLANKS_BLOCK.getDefaultMapColor()).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD));
	public static final Item CASSIA_FENCE_ITEM = new BlockItem(CASSIA_FENCE_BLOCK, ITEM_SETTINGS);
	public static final Block CASSIA_FENCE_GATE_BLOCK = new FenceGateBlock(AbstractBlock.Settings.of(Material.WOOD, CASSIA_PLANKS_BLOCK.getDefaultMapColor()).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD));
	public static final Item CASSIA_FENCE_GATE_ITEM = new BlockItem(CASSIA_FENCE_GATE_BLOCK, ITEM_SETTINGS);
	public static final Block CASSIA_DOOR_BLOCK = new CassiaDoorBlock(AbstractBlock.Settings.of(Material.WOOD, CASSIA_PLANKS_BLOCK.getDefaultMapColor()).strength(3.0F).sounds(BlockSoundGroup.WOOD).nonOpaque());
	public static final Item CASSIA_DOOR_ITEM = new TallBlockItem(CASSIA_DOOR_BLOCK, ITEM_SETTINGS);
	public static final Block CASSIA_TRAPDOOR_BLOCK = new CassiaTrapdoorBlock(AbstractBlock.Settings.of(Material.WOOD, MapColor.OAK_TAN).strength(3.0F).sounds(BlockSoundGroup.WOOD).nonOpaque().allowsSpawning((a, b, c, d) -> false));
	public static final Item CASSIA_TRAPDOOR_ITEM = new BlockItem(CASSIA_TRAPDOOR_BLOCK, ITEM_SETTINGS);
	public static final Block CASSIA_PRESSURE_PLATE_BLOCK = new CassiaPressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING, AbstractBlock.Settings.of(Material.WOOD, CASSIA_PLANKS_BLOCK.getDefaultMapColor()).noCollision().strength(0.5F).sounds(BlockSoundGroup.WOOD));
	public static final Item CASSIA_PRESSURE_PLATE_ITEM = new BlockItem(CASSIA_PRESSURE_PLATE_BLOCK, ITEM_SETTINGS);
	public static final Block CASSIA_BUTTON_BLOCK = new CassiaButtonBlock(AbstractBlock.Settings.of(Material.DECORATION).noCollision().strength(0.5F).sounds(BlockSoundGroup.WOOD));
	public static final Item CASSIA_BUTTON_ITEM = new BlockItem(CASSIA_BUTTON_BLOCK, ITEM_SETTINGS);

	public static final Item SNICKERDOODLE = new Item((new Item.Settings()).group(ITEM_GROUP).food((new FoodComponent.Builder()).hunger(2).saturationModifier(0.1F).build()));
	public static final Item CINNAMON_ROLL = new Item((new Item.Settings()).group(ITEM_GROUP).food((new FoodComponent.Builder()).hunger(2).saturationModifier(0.2F).build()));

	public static final Item THROWABLE_TOMATO_ITEM = new ThrowableTomatoItem(new Item.Settings().group(ITEM_GROUP).maxCount(16));
	public static final EntityType<ThrownTomatoEntity> THROWABLE_TOMATO_ENTITY = FabricEntityTypeBuilder.<ThrownTomatoEntity>create(SpawnGroup.MISC, ThrownTomatoEntity::new).dimensions(EntityDimensions.fixed(0.25F, 0.25F)).trackRangeBlocks(4).trackedUpdateRate(10).build();
	public static final DefaultParticleType TOMATO_PARTICLE = FabricParticleTypes.simple();
	public static final StatusEffect BOO_EFFECT = new BooEffect();

	@Override
	public void onInitialize() {
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "anchor"), ANCHOR_BLOCK);
		Registry.register(Registry.ITEM, new Identifier(NAMESPACE, "anchor"), ANCHOR_ITEM);
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "substitute_anchor"), SUBSTITUTE_ANCHOR_BLOCK);

		ANCHOR_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, NAMESPACE + ":" + "anchor_block_entity", FabricBlockEntityTypeBuilder.create(AnchorBlockEntity::new, ANCHOR_BLOCK).build(null));
		
		//Anchor core items
		for(Integer owner : ANCHOR_MAP.keySet()) {
			Registry.register(Registry.ITEM, new Identifier(NAMESPACE, ANCHOR_MAP.get(owner) + "_core"), ANCHOR_CORES.get(owner));
		}

		//Bone Torch
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "bone_torch"), BONE_TORCH_BLOCK);
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "bone_wall_torch"), BONE_WALL_TORCH_BLOCK);
		Registry.register(Registry.ITEM, new Identifier(NAMESPACE, "bone_torch"), BONE_TORCH_ITEM);

		//Tinker Toy
		Registry.register(Registry.ITEM, new Identifier(NAMESPACE, "tinker_toy"), TINKER_TOY);

		//Charcoal Block
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "charcoal_block"), CHARCOAL_BLOCK);
		Registry.register(Registry.ITEM, new Identifier(NAMESPACE, "charcoal_block"), CHARCOAL_BLOCK_ITEM);
		FuelRegistry.INSTANCE.add(CHARCOAL_BLOCK_ITEM, 16000);

		//Amethyst Crystal Block
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "amethyst_crystal_block"), AMETHYST_CRYSTAL_BLOCK);
		Registry.register(Registry.ITEM, new Identifier(NAMESPACE, "amethyst_crystal_block"), AMETHYST_CRYSTAL_BLOCK_ITEM);
		//Amethyst Bricks
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "amethyst_bricks"), AMETHYST_BRICKS_BLOCK);
		Registry.register(Registry.ITEM, new Identifier(NAMESPACE, "amethyst_bricks"), AMETHYST_BRICKS_ITEM);
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "amethyst_brick_slab"), AMETHYST_BRICK_SLAB_BLOCK);
		Registry.register(Registry.ITEM, new Identifier(NAMESPACE, "amethyst_brick_slab"), AMETHYST_BRICK_SLAB_ITEM);
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "amethyst_brick_stairs"), AMETHYST_BRICK_STAIRS_BLOCK);
		Registry.register(Registry.ITEM, new Identifier(NAMESPACE, "amethyst_brick_stairs"), AMETHYST_BRICK_STAIRS_ITEM);
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "amethyst_brick_wall"), AMETHYST_BRICK_WALL_BLOCK);
		Registry.register(Registry.ITEM, new Identifier(NAMESPACE, "amethyst_brick_wall"), AMETHYST_BRICK_WALL_ITEM);
		//Amethyst Variants
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "amethyst_slab"), AMETHYST_SLAB_BLOCK);
		Registry.register(Registry.ITEM, new Identifier(NAMESPACE, "amethyst_slab"), AMETHYST_SLAB_ITEM);
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "amethyst_stairs"), AMETHYST_STAIRS_BLOCK);
		Registry.register(Registry.ITEM, new Identifier(NAMESPACE, "amethyst_stairs"), AMETHYST_STAIRS_ITEM);
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "amethyst_wall"), AMETHYST_WALL_BLOCK);
		Registry.register(Registry.ITEM, new Identifier(NAMESPACE, "amethyst_wall"), AMETHYST_WALL_ITEM);

		//Carnations
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "black_carnation"), BLACK_CARNATION_BLOCK);
		Registry.register(Registry.ITEM, new Identifier(NAMESPACE, "black_carnation"), BLACK_CARNATION_ITEM);
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "potted_black_carnation"), POTTED_BLACK_CARNATION);
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "blue_carnation"), BLUE_CARNATION_BLOCK);
		Registry.register(Registry.ITEM, new Identifier(NAMESPACE, "blue_carnation"), BLUE_CARNATION_ITEM);
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "potted_blue_carnation"), POTTED_BLUE_CARNATION);
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "brown_carnation"), BROWN_CARNATION_BLOCK);
		Registry.register(Registry.ITEM, new Identifier(NAMESPACE, "brown_carnation"), BROWN_CARNATION_ITEM);
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "potted_brown_carnation"), POTTED_BROWN_CARNATION);
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "cyan_carnation"), CYAN_CARNATION_BLOCK);
		Registry.register(Registry.ITEM, new Identifier(NAMESPACE, "cyan_carnation"), CYAN_CARNATION_ITEM);
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "potted_cyan_carnation"), POTTED_CYAN_CARNATION);
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "gray_carnation"), GRAY_CARNATION_BLOCK);
		Registry.register(Registry.ITEM, new Identifier(NAMESPACE, "gray_carnation"), GRAY_CARNATION_ITEM);
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "potted_gray_carnation"), POTTED_GRAY_CARNATION);
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "green_carnation"), GREEN_CARNATION_BLOCK);
		Registry.register(Registry.ITEM, new Identifier(NAMESPACE, "green_carnation"), GREEN_CARNATION_ITEM);
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "potted_green_carnation"), POTTED_GREEN_CARNATION);
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "light_blue_carnation"), LIGHT_BLUE_CARNATION_BLOCK);
		Registry.register(Registry.ITEM, new Identifier(NAMESPACE, "light_blue_carnation"), LIGHT_BLUE_CARNATION_ITEM);
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "potted_light_blue_carnation"), POTTED_LIGHT_BLUE_CARNATION);
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "light_gray_carnation"), LIGHT_GRAY_CARNATION_BLOCK);
		Registry.register(Registry.ITEM, new Identifier(NAMESPACE, "light_gray_carnation"), LIGHT_GRAY_CARNATION_ITEM);
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "potted_light_gray_carnation"), POTTED_LIGHT_GRAY_CARNATION);
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "lime_carnation"), LIME_CARNATION_BLOCK);
		Registry.register(Registry.ITEM, new Identifier(NAMESPACE, "lime_carnation"), LIME_CARNATION_ITEM);
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "potted_lime_carnation"), POTTED_LIME_CARNATION);
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "magenta_carnation"), MAGENTA_CARNATION_BLOCK);
		Registry.register(Registry.ITEM, new Identifier(NAMESPACE, "magenta_carnation"), MAGENTA_CARNATION_ITEM);
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "potted_magenta_carnation"), POTTED_MAGENTA_CARNATION);
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "orange_carnation"), ORANGE_CARNATION_BLOCK);
		Registry.register(Registry.ITEM, new Identifier(NAMESPACE, "orange_carnation"), ORANGE_CARNATION_ITEM);
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "potted_orange_carnation"), POTTED_ORANGE_CARNATION);
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "pink_carnation"), PINK_CARNATION_BLOCK);
		Registry.register(Registry.ITEM, new Identifier(NAMESPACE, "pink_carnation"), PINK_CARNATION_ITEM);
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "potted_pink_carnation"), POTTED_PINK_CARNATION);
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "purple_carnation"), PURPLE_CARNATION_BLOCK);
		Registry.register(Registry.ITEM, new Identifier(NAMESPACE, "purple_carnation"), PURPLE_CARNATION_ITEM);
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "potted_purple_carnation"), POTTED_PURPLE_CARNATION);
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "red_carnation"), RED_CARNATION_BLOCK);
		Registry.register(Registry.ITEM, new Identifier(NAMESPACE, "red_carnation"), RED_CARNATION_ITEM);
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "potted_red_carnation"), POTTED_RED_CARNATION);
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "white_carnation"), WHITE_CARNATION_BLOCK);
		Registry.register(Registry.ITEM, new Identifier(NAMESPACE, "white_carnation"), WHITE_CARNATION_ITEM);
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "potted_white_carnation"), POTTED_WHITE_CARNATION);
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "yellow_carnation"), YELLOW_CARNATION_BLOCK);
		Registry.register(Registry.ITEM, new Identifier(NAMESPACE, "yellow_carnation"), YELLOW_CARNATION_ITEM);
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "potted_yellow_carnation"), POTTED_YELLOW_CARNATION);

		//Reskins
		Registry.register(Registry.ITEM, new Identifier(NAMESPACE, "pteror"), PTEROR);
		Registry.register(Registry.ITEM, new Identifier(NAMESPACE, "sbehesohe"), SBEHESOHE);
		Registry.register(Registry.ITEM, new Identifier(NAMESPACE, "pride_trident"), PRIDE_TRIDENT);

		//Soft TNT
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "soft_tnt"), SOFT_TNT_BLOCK);
		Registry.register(Registry.ITEM, new Identifier(NAMESPACE, "soft_tnt"), SOFT_TNT_ITEM);
		Registry.register(Registry.ENTITY_TYPE, new Identifier(NAMESPACE, "soft_tnt"), SOFT_TNT_ENTITY);
		DispenserBlock.registerBehavior(SOFT_TNT_BLOCK, new ItemDispenserBehavior() {
			protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
				World world = pointer.getWorld();
				BlockPos blockPos = pointer.getPos().offset((Direction)pointer.getBlockState().get(DispenserBlock.FACING));
				SoftTntEntity tntEntity = new SoftTntEntity(world, (double)blockPos.getX() + 0.5D, (double)blockPos.getY(), (double)blockPos.getZ() + 0.5D, (LivingEntity)null);
				world.spawnEntity(tntEntity);
				world.playSound((PlayerEntity)null, tntEntity.getX(), tntEntity.getY(), tntEntity.getZ(), SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.0F);
				world.emitGameEvent((Entity)null, GameEvent.ENTITY_PLACE, blockPos);
				stack.decrement(1);
				return stack;
			}
		});

		//Coffee
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "coffee_plant"), COFFEE_PLANT);
		Registry.register(Registry.ITEM, new Identifier(NAMESPACE, "coffee_cherry"), COFFEE_CHERRY);
		Registry.register(Registry.ITEM, new Identifier(NAMESPACE, "coffee_beans"), COFFEE_BEANS);
		Registry.register(Registry.ITEM, new Identifier(NAMESPACE, "coffee"), COFFEE);
		Registry.register(Registry.ITEM, new Identifier(NAMESPACE, "black_coffee"), BLACK_COFFEE);

		//Cassia Trees & Cinnamon
		Registry.register(Registry.ITEM, new Identifier(NAMESPACE, "cinnamon"), CINNAMON);

		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "cassia_log"), CASSIA_LOG_BLOCK);
		Registry.register(Registry.ITEM, new Identifier(NAMESPACE, "cassia_log"), CASSIA_LOG_ITEM);
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "stripped_cassia_log"), STRIPPED_CASSIA_LOG_BLOCK);
		Registry.register(Registry.ITEM, new Identifier(NAMESPACE, "stripped_cassia_log"), STRIPPED_CASSIA_LOG_ITEM);
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "cassia_wood"), CASSIA_WOOD_BLOCK);
		Registry.register(Registry.ITEM, new Identifier(NAMESPACE, "cassia_wood"), CASSIA_WOOD_ITEM);
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "stripped_cassia_wood"), STRIPPED_CASSIA_WOOD_BLOCK);
		Registry.register(Registry.ITEM, new Identifier(NAMESPACE, "stripped_cassia_wood"), STRIPPED_CASSIA_WOOD_ITEM);

		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "cassia_leaves"), CASSIA_LEAVES_BLOCK);
		Registry.register(Registry.ITEM, new Identifier(NAMESPACE, "cassia_leaves"), CASSIA_LEAVES_ITEM);
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "flowering_cassia_leaves"), FLOWERING_CASSIA_LEAVES_BLOCK);
		Registry.register(Registry.ITEM, new Identifier(NAMESPACE, "flowering_cassia_leaves"), FLOWERING_CASSIA_LEAVES_ITEM);
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "cassia_sapling"), CASSIA_SAPLING_BLOCK);
		Registry.register(Registry.ITEM, new Identifier(NAMESPACE, "cassia_sapling"), CASSIA_SAPLING_ITEM);
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "potted_cassia_sapling"), POTTED_CASSIA_SAPLING_BLOCK);

		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "cassia_planks"), CASSIA_PLANKS_BLOCK);
		Registry.register(Registry.ITEM, new Identifier(NAMESPACE, "cassia_planks"), CASSIA_PLANKS_ITEM);
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "cassia_stairs"), CASSIA_STAIRS_BLOCK);
		Registry.register(Registry.ITEM, new Identifier(NAMESPACE, "cassia_stairs"), CASSIA_STAIRS_ITEM);
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "cassia_slab"), CASSIA_SLAB_BLOCK);
		Registry.register(Registry.ITEM, new Identifier(NAMESPACE, "cassia_slab"), CASSIA_SLAB_ITEM);

		//TODO: Cassia Signs don't work right (fail out of edit screens and go invisible)
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "cassia_sign"), CASSIA_SIGN_BLOCK);
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "cassia_wall_sign"), CASSIA_WALL_SIGN_BLOCK);
		Registry.register(Registry.ITEM, new Identifier(NAMESPACE, "cassia_sign"), CASSIA_SIGN_ITEM);

		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "cassia_fence"), CASSIA_FENCE_BLOCK);
		Registry.register(Registry.ITEM, new Identifier(NAMESPACE, "cassia_fence"), CASSIA_FENCE_ITEM);
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "cassia_fence_gate"), CASSIA_FENCE_GATE_BLOCK);
		Registry.register(Registry.ITEM, new Identifier(NAMESPACE, "cassia_fence_gate"), CASSIA_FENCE_GATE_ITEM);
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "cassia_door"), CASSIA_DOOR_BLOCK);
		Registry.register(Registry.ITEM, new Identifier(NAMESPACE, "cassia_door"), CASSIA_DOOR_ITEM);
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "cassia_trapdoor"), CASSIA_TRAPDOOR_BLOCK);
		Registry.register(Registry.ITEM, new Identifier(NAMESPACE, "cassia_trapdoor"), CASSIA_TRAPDOOR_ITEM);
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "cassia_pressure_plate"), CASSIA_PRESSURE_PLATE_BLOCK);
		Registry.register(Registry.ITEM, new Identifier(NAMESPACE, "cassia_pressure_plate"), CASSIA_PRESSURE_PLATE_ITEM);
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "cassia_button"), CASSIA_BUTTON_BLOCK);
		Registry.register(Registry.ITEM, new Identifier(NAMESPACE, "cassia_button"), CASSIA_BUTTON_ITEM);
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(NAMESPACE, "cassia_tree"), CASSIA_TREE);

		Registry.register(Registry.ITEM, new Identifier(NAMESPACE, "snickerdoodle"), SNICKERDOODLE);
		Registry.register(Registry.ITEM, new Identifier(NAMESPACE, "cinnamon_roll"), CINNAMON_ROLL);

		//Throwable Tomatoes
		Registry.register(Registry.ITEM, new Identifier(NAMESPACE, "throwable_tomato"), THROWABLE_TOMATO_ITEM);
		Registry.register(Registry.ENTITY_TYPE, new Identifier(NAMESPACE, "throwable_tomato"), THROWABLE_TOMATO_ENTITY);
		Registry.register(Registry.PARTICLE_TYPE, new Identifier(NAMESPACE, "thrown_tomato"), TOMATO_PARTICLE);
		Registry.register(Registry.STATUS_EFFECT, new Identifier(NAMESPACE, "boo"), BOO_EFFECT);

		/*
		ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new SimpleSynchronousResourceReloadListener() {
			@Override
			public void reload(ResourceManager manager) {
				// Clear Caches Here
				for(Identifier id : manager.findResources("my_resource_folder", path -> path.endsWith(".json"))) {
					try(InputStream stream = manager.getResource(id).getInputStream()) {
						// Consume the stream however you want, medium, rare, or well done.
					} catch(Exception e) {
						log(Level.ERROR, "Error occurred while loading resource json " + id.toString() + "\n" + e.toString());
					}
				}
			}

			@Override
			public Identifier getFabricId() {
				return new Identifier("haven", "my_resources");
			}
		});*/
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
	public static Map<Integer, AnchorCoreItem> ANCHOR_CORES;

	public static final Map<Block, Block> STRIPPED_BLOCKS = new HashMap<Block, Block>();
	public static final Map<Item, Float> COMPOSTABLE_ITEMS = new HashMap<Item, Float>();
	public static final List<SignType> SIGN_TYPES = new ArrayList<SignType>();

	static {
		ANCHOR_CORES = new HashMap<Integer, AnchorCoreItem>();
		for(Integer owner : ANCHOR_MAP.keySet()) {
			ANCHOR_CORES.put(owner, new AnchorCoreItem(owner));
		}

		STRIPPED_BLOCKS.put(CASSIA_WOOD_BLOCK, STRIPPED_CASSIA_WOOD_BLOCK);
		STRIPPED_BLOCKS.put(CASSIA_LOG_BLOCK, STRIPPED_CASSIA_LOG_BLOCK);

		COMPOSTABLE_ITEMS.put(CASSIA_SAPLING_ITEM, 0.3f);
		COMPOSTABLE_ITEMS.put(CASSIA_LEAVES_ITEM, 0.3f);
		COMPOSTABLE_ITEMS.put(FLOWERING_CASSIA_LEAVES_ITEM, 0.3f);

		COMPOSTABLE_ITEMS.put(CINNAMON, 0.2f);

		SIGN_TYPES.add(CASSIA_SIGN_TYPE);
		SignTypeAccessor.getValues().addAll(SIGN_TYPES);
	}

	public static void log(Level level, String message){
		LOGGER.log(level, "[Haven] " + message);
	}
}
