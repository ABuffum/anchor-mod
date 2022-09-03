package haven;

import haven.blocks.*;
import haven.effects.BooEffect;
import haven.effects.KilljoyEffect;
import haven.entities.*;
import haven.features.*;
import haven.items.*;

import haven.materials.WoodMaterial;
import haven.mixins.SignTypeAccessor;
import haven.util.HavenFlower;
import haven.util.HavenPair;
import haven.util.HavenTorch;
import haven.util.PottedBlock;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.*;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.fabricmc.fabric.api.transfer.v1.fluid.CauldronFluidContent;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.fluid.base.FullItemFluidStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.minecraft.block.*;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.block.entity.*;
import net.minecraft.entity.*;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.item.*;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.*;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.SignType;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.*;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;

import java.util.*;
import java.util.function.ToIntFunction;

import static java.util.Map.entry;

import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.AcaciaFoliagePlacer;
import net.minecraft.world.gen.foliage.RandomSpreadFoliagePlacer;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.trunk.BendingTrunkPlacer;
import net.minecraft.world.gen.trunk.ForkingTrunkPlacer;
import org.apache.logging.log4j.*;

@SuppressWarnings("deprecation")
public class HavenMod implements ModInitializer {
	public static Logger LOGGER = LogManager.getLogger();

	public static final String NAMESPACE = "haven";
	public static Identifier ID(String path) { return new Identifier(NAMESPACE, path); }

	public static final DyeColor[] COLORS = { DyeColor.BLACK, DyeColor.BLUE, DyeColor.BROWN, DyeColor.CYAN, DyeColor.GRAY, DyeColor.GREEN, DyeColor.LIGHT_BLUE, DyeColor.LIGHT_GRAY, DyeColor.LIME, DyeColor.MAGENTA, DyeColor.ORANGE, DyeColor.PINK, DyeColor.PURPLE, DyeColor.RED, DyeColor.WHITE, DyeColor.YELLOW };

	private static final Block ANCHOR_BLOCK = new AnchorBlock(FabricBlockSettings.of(Material.SOIL).hardness(1f));
	public static final ItemGroup ITEM_GROUP = FabricItemGroupBuilder.build(new Identifier(NAMESPACE, "general"), () -> new ItemStack(ANCHOR_BLOCK));
	public static final Item.Settings ITEM_SETTINGS = new Item.Settings().group(ITEM_GROUP);
	public static final HavenPair ANCHOR = new HavenPair(ANCHOR_BLOCK);
	public static BlockEntityType<AnchorBlockEntity> ANCHOR_BLOCK_ENTITY = FabricBlockEntityTypeBuilder.create(AnchorBlockEntity::new, ANCHOR.BLOCK).build(null);
	public static final Block SUBSTITUTE_ANCHOR_BLOCK = new SubstituteAnchorBlock(FabricBlockSettings.of(Material.SOIL).hardness(1f));
	public static BlockEntityType<SubstituteAnchorBlockEntity> SUBSTITUTE_ANCHOR_BLOCK_ENTITY = FabricBlockEntityTypeBuilder.create(SubstituteAnchorBlockEntity::new, SUBSTITUTE_ANCHOR_BLOCK).build(null);

	public static ToIntFunction<BlockState> luminance(int value) { return (state) -> value; }

	public static final HavenTorch BONE_TORCH = new HavenTorch("bone_torch", "bone_wall_torch", FabricBlockSettings.of(Material.DECORATION).noCollision().breakInstantly().nonOpaque().luminance(luminance(14)).sounds(BlockSoundGroup.BONE), ParticleTypes.FLAME);

	public static final Item TINKER_TOY = new TinkerToy(ITEM_SETTINGS);

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

	public static final ToolItem PTEROR = new SwordItem(ToolMaterials.NETHERITE, 3, -2.4F, new Item.Settings().group(ITEM_GROUP).fireproof());
	public static final ToolItem SBEHESOHE = new SwordItem(ToolMaterials.DIAMOND, 3, -2.4F, new Item.Settings().group(ITEM_GROUP).fireproof());

	public static final HavenPair SOFT_TNT = new HavenPair(new SoftTntBlock(AbstractBlock.Settings.of(Material.TNT).breakInstantly().sounds(BlockSoundGroup.GRASS)));
	public static final EntityType<SoftTntEntity> SOFT_TNT_ENTITY = new FabricEntityTypeBuilderImpl<SoftTntEntity>(SpawnGroup.MISC, SoftTntEntity::new)
			.dimensions(EntityDimensions.fixed(0.98F, 0.98F)).fireImmune().trackRangeBlocks(10).trackedUpdateRate(10).build();

	public static final Block COFFEE_PLANT = new CoffeePlantBlock(AbstractBlock.Settings.of(Material.PLANT).ticksRandomly().noCollision().sounds(BlockSoundGroup.CROP));
	public static final Item COFFEE_CHERRY = new AliasedBlockItem(COFFEE_PLANT, new Item.Settings().group(ITEM_GROUP).food(FoodComponents.SWEET_BERRIES));
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

	public static Block BLOOD_CAULDRON = new BloodCauldronBlock(FabricBlockSettings.copyOf(Blocks.CAULDRON));
	public static Item BLOOD_BOTTLE = new BloodBottleItem(new Item.Settings().recipeRemainder(Items.GLASS_BOTTLE).maxCount(1).group(ITEM_GROUP));
	public static BucketItem BLOOD_BUCKET = new BucketItem(STILL_BLOOD_FLUID, new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1).group(ITEM_GROUP));

	private static Block Register(String path, Block block) {
		return Registry.register(Registry.BLOCK, ID(path), block);
	}
	private static Item Register(String path, Item item) {
		return Registry.register(Registry.ITEM, ID(path), item);
	}
	private static void Register(String path, Block block, Item item) {
		Identifier id = ID(path);
		Registry.register(Registry.BLOCK, id, block);
		Registry.register(Registry.ITEM, id, item);
	}
	private static <T extends BlockEntity> BlockEntityType Register(String path, BlockEntityType<T> entity) {
		return Registry.register(Registry.BLOCK_ENTITY_TYPE, ID(path), entity);
	}
	private static StatusEffect Register(String path, StatusEffect effect) {
		return Registry.register(Registry.STATUS_EFFECT, ID(path), effect);
	}
	private static <T extends Entity> EntityType<T> Register(String path, EntityType entityType) {
		return Registry.register(Registry.ENTITY_TYPE, ID(path), entityType);
	}
	private static void Register(String path, HavenPair pair) {
		Identifier id = ID(path);
		Registry.register(Registry.BLOCK, id, pair.BLOCK);
		Registry.register(Registry.ITEM, id, pair.ITEM);
	}

	private static void Register(String path, PottedBlock potted) {
		Identifier id = ID(path);
		Registry.register(Registry.BLOCK, id, potted.BLOCK);
		Registry.register(Registry.ITEM, id, potted.ITEM);
		Registry.register(Registry.BLOCK, ID("potted_" + path), potted.POTTED);
	}

	private static void Register(String path, String wallPath, HavenTorch torch) {
		Identifier id = ID(path);
		Registry.register(Registry.BLOCK, id, torch.BLOCK);
		Registry.register(Registry.BLOCK, ID(wallPath), torch.WALL_BLOCK);
		Registry.register(Registry.ITEM, id, torch.ITEM);
	}

	private static void Register(String name, WoodMaterial material) {
		HavenMod.Register(name + "_log", material.LOG);
		HavenMod.Register("stripped_" + name + "_log", material.STRIPPED_LOG);
		HavenMod.Register(name + "_wood", material.WOOD);
		HavenMod.Register("stripped_" + name + "_wood", material.STRIPPED_WOOD);

		HavenMod.Register(name + "_leaves", material.LEAVES);
		HavenMod.Register(name + "_sapling", material.SAPLING);

		Register(name + "_planks", material.PLANKS);
		Register(name + "_stairs", material.STAIRS);
		Register(name + "_slab", material.SLAB);

		Register(name + "_fence", material.FENCE);
		Register(name + "_fence_gate", material.FENCE_GATE);
		Register(name + "_door", material.DOOR);
		Register(name + "_trapdoor", material.TRAPDOOR);
		Register(name + "_pressure_plate", material.PRESSURE_PLATE);
		Register(name + "_button", material.BUTTON);

		//TODO: Signs don't work right (fail out of edit screens and go invisible)
		//Register(name + "_sign", material.SIGN_BLOCK, material.SIGN_ITEM);
		//Register(name + "_wall_sign", material.WALL_SIGN_BLOCK);
	}

	@Override
	public void onInitialize() {
		Register("anchor", ANCHOR);
		Register("anchor_block_entity", ANCHOR_BLOCK_ENTITY);
		Register("substitute_anchor", SUBSTITUTE_ANCHOR_BLOCK);
		Register("substitute_anchor_block_entity", SUBSTITUTE_ANCHOR_BLOCK_ENTITY);

		//Anchor core items
		for(Integer owner : ANCHOR_MAP.keySet()) {
			Register(ANCHOR_MAP.get(owner) + "_core", ANCHOR_CORES.get(owner));
		}

		//Bone Torch
		Register("bone_torch", "bone_wall_torch", BONE_TORCH);

		//Tinker Toy
		Register("tinker_toy", TINKER_TOY);

		//Charcoal Block
		Register("charcoal_block", CHARCOAL_BLOCK);
		FuelRegistry.INSTANCE.add(CHARCOAL_BLOCK.ITEM, 16000);

		//Amethyst Crystal Block
		Register("amethyst_crystal_block", AMETHYST_CRYSTAL_BLOCK);
		//Amethyst Bricks
		Register("amethyst_bricks", AMETHYST_BRICKS);
		Register("amethyst_brick_slab", AMETHYST_BRICK_SLAB);
		Register("amethyst_brick_stairs", AMETHYST_BRICK_STAIRS);
		Register("amethyst_brick_wall", AMETHYST_BRICK_WALL);
		//Amethyst Variants
		Register("amethyst_slab", AMETHYST_SLAB);
		Register("amethyst_stairs", AMETHYST_STAIRS);
		Register("amethyst_wall", AMETHYST_WALL);

		//Carnations
		for (DyeColor color : COLORS) {
			Register(color.getName() + "_carnation", CARNATIONS.get(color));
		}
		//Minecraft Earth Flowers
		Register("buttercup", BUTTERCUP);
		Register("pink_daisy", PINK_DAISY);
		//Other Flowers
		Register("rose", ROSE);
		Register("blue_rose", BLUE_ROSE);
		Register("magenta_tulip", MAGENTA_TULIP);
		Register("marigold", MARIGOLD);
		Register("pink_allium", PINK_ALLIUM);

		//Reskins
		Register("pteror", PTEROR);
		Register("sbehesohe", SBEHESOHE);

		//Soft TNT
		Register("soft_tnt", SOFT_TNT);
		Register("soft_tnt", SOFT_TNT_ENTITY);
		DispenserBlock.registerBehavior(SOFT_TNT.BLOCK, new ItemDispenserBehavior() {
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
		Register("coffee_plant", COFFEE_PLANT);
		Register("coffee_cherry", COFFEE_CHERRY);
		Register("coffee_beans", COFFEE_BEANS);
		Register("coffee", COFFEE);
		Register("black_coffee", BLACK_COFFEE);

		//Cherry Trees
		Register("cherry", CHERRY);
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, ID("cherry_tree"), CHERRY_TREE);
		Register("white_cherry_leaves", WHITE_CHERRY_LEAVES);
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, ID("white_cherry_tree"), WHITE_CHERRY_TREE);
		Register("pale_cherry_leaves", PALE_CHERRY_LEAVES);
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, ID("pale_cherry_tree"), PALE_CHERRY_TREE);
		Register("pink_cherry_leaves", PINK_CHERRY_LEAVES);
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, ID("pink_cherry_tree"), PINK_CHERRY_TREE);

		//Cassia Trees & Cinnamon
		Register("cinnamon", CINNAMON);

		Register("cassia", CASSIA);
		Register("flowering_cassia_leaves", FLOWERING_CASSIA_LEAVES);
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(NAMESPACE, "cassia_tree"), CASSIA_TREE);

		Register("snickerdoodle", SNICKERDOODLE);
		Register("cinnamon_roll", CINNAMON_ROLL);

		//Misc Candies
		Register("cinnamon_bean", CINNAMON_BEAN);
		Register("pink_cotton_candy", PINK_COTTON_CANDY);
		Register("blue_cotton_candy", BLUE_COTTON_CANDY);
		Register("candy_cane", CANDY_CANE);
		Register("caramel", CARAMEL);
		Register("caramel_apple", CARAMEL_APPLE);

		//Amethyst & Rock Candy
		Register("amethyst_candy", AMETHYST_CANDY);
		for(DyeColor color : COLORS) {
			Register(color.getName() + "_rock_candy", ROCK_CANDIES.get(color));
		}

		//Throwable Tomatoes
		Register("throwable_tomato", THROWABLE_TOMATO_ITEM);
		Register("throwable_tomato", THROWABLE_TOMATO_ENTITY);
		Registry.register(Registry.PARTICLE_TYPE, new Identifier(NAMESPACE, "thrown_tomato"), TOMATO_PARTICLE);
		Register("boo", BOO_EFFECT);
		Register("killjoy", KILLJOY_EFFECT);

		//Server Blood
		Register("blood_block", BLOOD_BLOCK);
		Register("blood_fence", BLOOD_FENCE);
		Register("blood_pane", BLOOD_PANE);
		Register("blood_slab", BLOOD_SLAB);
		Register("blood_stairs", BLOOD_STAIRS);
		Register("blood_wall", BLOOD_WALL);
		Register("dried_blood_block", DRIED_BLOOD_BLOCK);
		Register("dried_blood_fence", DRIED_BLOOD_FENCE);
		Register("dried_blood_pane", DRIED_BLOOD_PANE);
		Register("dried_blood_slab", DRIED_BLOOD_SLAB);
		Register("dried_blood_stairs", DRIED_BLOOD_STAIRS);
		Register("dried_blood_wall", DRIED_BLOOD_WALL);

		Register("blood_bucket", BLOOD_BUCKET);
		Register("blood_cauldron", BLOOD_CAULDRON);
		CauldronBehavior.EMPTY_CAULDRON_BEHAVIOR.put(BLOOD_BUCKET, BloodCauldronBlock.FILL_FROM_BUCKET);
		CauldronFluidContent.registerCauldron(BLOOD_CAULDRON, STILL_BLOOD_FLUID, FluidConstants.BOTTLE, LeveledCauldronBlock.LEVEL);
		CauldronBehavior.EMPTY_CAULDRON_BEHAVIOR.put(BLOOD_BOTTLE, BloodCauldronBlock.FILL_FROM_BOTTLE);
		BloodCauldronBlock.BLOOD_CAULDRON_BEHAVIOR.put(HavenMod.BLOOD_BOTTLE, BloodCauldronBlock.FILL_FROM_BOTTLE);
		BloodCauldronBlock.BLOOD_CAULDRON_BEHAVIOR.put(Items.GLASS_BOTTLE, BloodCauldronBlock.EMPTY_TO_BOTTLE);
		Register("blood_bottle", BLOOD_BOTTLE);
		FluidStorage.combinedItemApiProvider(BLOOD_BOTTLE).register(context -> new FullItemFluidStorage(context, bottle -> ItemVariant.of(Items.GLASS_BOTTLE), FluidVariant.of(STILL_BLOOD_FLUID), FluidConstants.BOTTLE));
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
	public static final Set<WoodMaterial> WOOD_MATERIALS = Set.of(
			CASSIA, CHERRY
	);
	public static final Map<Block, Block> STRIPPED_BLOCKS = new HashMap<Block, Block>();
	public static final Map<Item, Float> COMPOSTABLE_ITEMS = new HashMap<Item, Float>();
	public static final List<SignType> SIGN_TYPES = new ArrayList<SignType>();

	public static final Set<HavenPair> LEAVES = new HashSet<HavenPair>(Set.of(
			PALE_CHERRY_LEAVES, PINK_CHERRY_LEAVES, WHITE_CHERRY_LEAVES,
			FLOWERING_CASSIA_LEAVES
	));
	public static final Set<HavenFlower> FLOWERS = new HashSet<HavenFlower>(Set.of(
			BUTTERCUP, PINK_DAISY,
			ROSE, BLUE_ROSE,
			MAGENTA_TULIP,
			MARIGOLD, PINK_ALLIUM
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
		for (HavenPair leaf : LEAVES) COMPOSTABLE_ITEMS.put(leaf.ITEM, 0.3f);
		//Extra Leaves
		COMPOSTABLE_ITEMS.put(CINNAMON, 0.2f);
		COMPOSTABLE_ITEMS.put(COFFEE_CHERRY, 0.65F);
		COMPOSTABLE_ITEMS.put(COFFEE_BEANS, 0.35F);
		//Sign Types
		SignTypeAccessor.getValues().addAll(SIGN_TYPES);
	}

	public static void log(Level level, String message){
		LOGGER.log(level, "[Haven] " + message);
	}
}
