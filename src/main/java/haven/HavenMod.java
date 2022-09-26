package haven;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import haven.blocks.*;
import haven.blocks.anchors.*;
import haven.blocks.basic.*;
import haven.blocks.gourd.*;
import haven.blood.*;
import haven.blocks.cake.*;
import haven.blocks.mud.*;
import haven.blocks.oxidizable.*;
import haven.boats.*;
import haven.command.ChorusCommand;
import haven.containers.*;
import haven.damage.HavenDamageSource;
import haven.effects.*;
import haven.entities.*;
import haven.entities.anchors.*;
import haven.entities.cloud.*;
import haven.entities.passive.*;
import haven.entities.passive.cow.*;
import haven.entities.projectiles.*;
import haven.entities.tnt.*;
import haven.features.*;
import haven.items.*;
import haven.items.consumable.*;
import haven.items.buckets.*;
import haven.items.consumable.milk.*;
import haven.items.mud.MudBucketItem;
import haven.items.syringe.*;
import haven.items.throwable.*;
import haven.materials.*;
import haven.materials.base.BaseMaterial;
import haven.materials.gem.*;
import haven.materials.metal.*;
import haven.materials.providers.*;
import haven.materials.stone.DripstoneMaterial;
import haven.materials.stone.TuffMaterial;
import haven.materials.wood.*;
import haven.sounds.*;
import haven.util.*;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.*;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;

import net.minecraft.block.*;
import net.minecraft.block.entity.*;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.*;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.*;
import net.minecraft.item.*;
import net.minecraft.particle.*;
import net.minecraft.sound.*;
import net.minecraft.util.*;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.intprovider.*;
import net.minecraft.world.World;
import net.minecraft.world.gen.CountConfig;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.RangeDecoratorConfig;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.*;
import net.minecraft.world.gen.heightprovider.UniformHeightProvider;
import net.minecraft.world.gen.placer.SimpleBlockPlacer;
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
	public static final ItemGroup ITEM_GROUP = FabricItemGroupBuilder.build(HavenMod.ID("general"), () -> new ItemStack(ANCHOR_BLOCK));
	public static Item.Settings ItemSettings() {
		return new Item.Settings().group(ITEM_GROUP);
	}
	public static final BlockContainer ANCHOR = new BlockContainer(ANCHOR_BLOCK);
	public static final BlockEntityType<AnchorBlockEntity> ANCHOR_BLOCK_ENTITY = FabricBlockEntityTypeBuilder.create(AnchorBlockEntity::new, ANCHOR.BLOCK).build(null);
	public static final Block SUBSTITUTE_ANCHOR_BLOCK = new SubstituteAnchorBlock(FabricBlockSettings.of(Material.SOIL).hardness(1f));
	public static final BlockEntityType<SubstituteAnchorBlockEntity> SUBSTITUTE_ANCHOR_BLOCK_ENTITY = FabricBlockEntityTypeBuilder.create(SubstituteAnchorBlockEntity::new, SUBSTITUTE_ANCHOR_BLOCK).build(null);

	public static ToIntFunction<BlockState> luminance(int value) { return (state) -> value; }

	//More Torches / Lanterns
	public static AbstractBlock.Settings UnlitLanternSettings() {
		return AbstractBlock.Settings.of(Material.METAL).requiresTool().strength(3.5F).sounds(BlockSoundGroup.LANTERN).nonOpaque();
	}
	public static final TorchContainer.Unlit UNLIT_TORCH = new TorchContainer.Unlit(Blocks.TORCH, Blocks.WALL_TORCH);
	public static final TorchContainer.Unlit UNLIT_SOUL_TORCH = new TorchContainer.Unlit(Blocks.SOUL_TORCH, Blocks.SOUL_WALL_TORCH);
	public static final Block UNLIT_LANTERN = new LanternBlock(UnlitLanternSettings());
	public static final Block UNLIT_SOUL_LANTERN = new LanternBlock(UnlitLanternSettings());

	public static final DefaultParticleType UNDERWATER_TORCH_GLOW = FabricParticleTypes.simple(false);
	public static final TorchContainer UNDERWATER_TORCH = TorchContainer.Waterloggable(AbstractBlock.Settings.of(Material.DECORATION).noCollision().breakInstantly().luminance(luminance(14)).sounds(BlockSoundGroup.WOOD), UNDERWATER_TORCH_GLOW);

	//More Copper
	public static final DefaultParticleType COPPER_FLAME_PARTICLE = FabricParticleTypes.simple(false);
	public static final CopperMaterial COPPER_MATERIAL = new CopperMaterial();

	public static final OxidizableBlockContainer MEDIUM_WEIGHTED_PRESSURE_PLATE = new OxidizableBlockContainer(
			(level, settings) -> new OxidizableWeightedPressurePlateBlock(level, 75, settings),
			(settings) -> new HavenWeightedPressurePlateBlock(75, settings),
			(level) -> AbstractBlock.Settings.of(Material.METAL, OxidationScale.getMapColor(level)).requiresTool().noCollision().strength(0.5F).sounds(BlockSoundGroup.WOOD)
	);
	//More Gold
	public static final DefaultParticleType GOLD_FLAME_PARTICLE = FabricParticleTypes.simple(false);
	public static final GoldMaterial GOLD_MATERIAL = new GoldMaterial();
	//More Iron
	public static final DefaultParticleType IRON_FLAME_PARTICLE = FabricParticleTypes.simple(false);
	public static final IronMaterial IRON_MATERIAL = new IronMaterial();
	public static final Item DARK_IRON_INGOT = new Item(ItemSettings());
	public static final DarkIronMaterial DARK_IRON_MATERIAL = new DarkIronMaterial();
	public static final BlockContainer DARK_HEAVY_WEIGHTED_PRESSURE_PLATE = new BlockContainer(new HavenWeightedPressurePlateBlock(150, AbstractBlock.Settings.of(Material.METAL).requiresTool().noCollision().strength(0.5F).sounds(BlockSoundGroup.WOOD)));

	//More Netherite
	public static final DefaultParticleType NETHERITE_FLAME_PARTICLE = FabricParticleTypes.simple(false);
	public static final NetheriteMaterial NETHERITE_MATERIAL = new NetheriteMaterial();
	public static final BlockContainer CRUSHING_WEIGHTED_PRESSURE_PLATE = new BlockContainer(new HavenWeightedPressurePlateBlock(300, AbstractBlock.Settings.of(Material.METAL).requiresTool().noCollision().strength(0.5F).sounds(BlockSoundGroup.WOOD)), ItemSettings().fireproof());

	public static final Item TINKER_TOY = new Item(ItemSettings());

	public static final BlockContainer CHARCOAL_BLOCK = new BlockContainer(new Block(AbstractBlock.Settings.of(Material.STONE, MapColor.BLACK).requiresTool().strength(5.0F, 6.0F)));

	public static final StuddedLeatherMaterial STUDDED_LEATHER_MATERIAL = new StuddedLeatherMaterial();
	//Gem Materials
	public static final AmethystMaterial AMETHYST_MATERIAL = new AmethystMaterial();
	public static final EmeraldMaterial EMERALD_MATERIAL = new EmeraldMaterial();
	public static final DiamondMaterial DIAMOND_MATERIAL = new DiamondMaterial();
	public static final QuartzMaterial QUARTZ_MATERIAL = new QuartzMaterial();
	//Obsidian
	public static final ObsidianMaterial OBSIDIAN_MATERIAL = new ObsidianMaterial();
	public static final CryingObsidianMaterial CRYING_OBSIDIAN_MATERIAL = new CryingObsidianMaterial("crying_obsidian", ParticleTypes.DRIPPING_OBSIDIAN_TEAR);
	//Bleeding Obsidian
	public static final DefaultParticleType LANDING_OBSIDIAN_BLOOD = FabricParticleTypes.simple(false);
	public static final DefaultParticleType FALLING_OBSIDIAN_BLOOD = FabricParticleTypes.simple(false);
	public static final DefaultParticleType DRIPPING_OBSIDIAN_BLOOD = FabricParticleTypes.simple(false);
	public static final BlockContainer BLEEDING_OBSIDIAN = new BlockContainer(new BleedingObsidianBlock(AbstractBlock.Settings.copy(Blocks.CRYING_OBSIDIAN)));
	public static final CryingObsidianMaterial BLEEDING_OBSIDIAN_MATERIAL = new CryingObsidianMaterial("bleeding_obsidian", DRIPPING_OBSIDIAN_BLOOD);
	//Bone
	public static final BoneMaterial BONE_MATERIAL = new BoneMaterial();
	//Stone Materials
	public static final DripstoneMaterial DRIPSTONE_MATERIAL = new DripstoneMaterial();
	public static final TuffMaterial TUFF_MATERIAL = new TuffMaterial();
	//Soul Jack o' Lantern
	public static final BlockContainer SOUL_JACK_O_LANTERN = new BlockContainer(new CarvedGourdBlock(AbstractBlock.Settings.of(Material.GOURD, MapColor.ORANGE).strength(1.0F).sounds(BlockSoundGroup.WOOD).luminance(luminance(10)).allowsSpawning(BaseMaterial::always)));
	//Melon Golem
	public static final EntityType<MelonSeedProjectileEntity> MELON_SEED_PROJECTILE_ENTITY = FabricEntityTypeBuilder.<MelonSeedProjectileEntity>create(SpawnGroup.MISC, MelonSeedProjectileEntity::new).dimensions(EntityDimensions.fixed(0.25F, 0.25F)).trackRangeBlocks(4).trackedUpdateRate(10).build();
	public static final EntityType<MelonGolemEntity> MELON_GOLEM_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.MISC, MelonGolemEntity::new).dimensions(EntityType.SNOW_GOLEM.getDimensions()).trackRangeBlocks(8).build();
	public static final BlockContainer CARVED_MELON = new BlockContainer(new CarvedMelonBlock(AbstractBlock.Settings.of(Material.GOURD, MapColor.LIME).strength(1.0F).sounds(BlockSoundGroup.WOOD).allowsSpawning(BaseMaterial::always)));
	public static final BlockContainer MELON_LANTERN = new BlockContainer(new CarvedMelonBlock(AbstractBlock.Settings.of(Material.GOURD, MapColor.LIME).strength(1.0F).sounds(BlockSoundGroup.WOOD).luminance(luminance(15)).allowsSpawning(BaseMaterial::always)));
	public static final BlockContainer SOUL_MELON_LANTERN = new BlockContainer(new CarvedMelonBlock(AbstractBlock.Settings.of(Material.GOURD, MapColor.LIME).strength(1.0F).sounds(BlockSoundGroup.WOOD).luminance(luminance(10)).allowsSpawning(BaseMaterial::always)));
	//White Pumpkin, White Snow Golem
	public static final EntityType<WhiteSnowGolemEntity> WHITE_SNOW_GOLEM_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.MISC, WhiteSnowGolemEntity::new).dimensions(EntityType.SNOW_GOLEM.getDimensions()).trackRangeBlocks(8).build();
	public static final BlockContainer WHITE_JACK_O_LANTERN = new BlockContainer(new CarvedWhitePumpkinBlock(AbstractBlock.Settings.of(Material.GOURD, MapColor.WHITE).strength(1.0F).sounds(BlockSoundGroup.WOOD).luminance(luminance(15)).allowsSpawning(BaseMaterial::always)));
	public static final BlockContainer WHITE_SOUL_JACK_O_LANTERN = new BlockContainer(new CarvedWhitePumpkinBlock(AbstractBlock.Settings.of(Material.GOURD, MapColor.WHITE).strength(1.0F).sounds(BlockSoundGroup.WOOD).luminance(luminance(10)).allowsSpawning(BaseMaterial::always)));
	public static final CarvedGourdContainer WHITE_PUMPKIN = new CarvedGourdContainer(
			new CarvedWhitePumpkinBlock(AbstractBlock.Settings.of(Material.GOURD, MapColor.WHITE).strength(1.0F).sounds(BlockSoundGroup.WOOD).allowsSpawning(BaseMaterial::always)),
			new CarvedWhitePumpkinBlock(AbstractBlock.Settings.of(Material.GOURD, MapColor.WHITE).strength(1.0F).sounds(BlockSoundGroup.WOOD).luminance(luminance(10)).allowsSpawning(BaseMaterial::always)),
			SoundEvents.BLOCK_PUMPKIN_CARVE,
			AbstractBlock.Settings.of(Material.GOURD, MapColor.WHITE).strength(1.0F).sounds(BlockSoundGroup.WOOD),
			AbstractBlock.Settings.copy(Blocks.PUMPKIN_STEM),
			AbstractBlock.Settings.copy(Blocks.ATTACHED_PUMPKIN_STEM), ItemSettings());
	//Rotten Pumpkin
	public static final BlockContainer CARVED_ROTTEN_PUMPKIN = new BlockContainer(new CarvedGourdBlock(AbstractBlock.Settings.of(Material.GOURD, MapColor.PALE_YELLOW).strength(1.0F).sounds(BlockSoundGroup.WOOD).allowsSpawning(BaseMaterial::always)));
	public static final BlockContainer ROTTEN_JACK_O_LANTERN = new BlockContainer(new CarvedGourdBlock(AbstractBlock.Settings.of(Material.GOURD, MapColor.PALE_YELLOW).strength(1.0F).sounds(BlockSoundGroup.WOOD).luminance(luminance(15)).allowsSpawning(BaseMaterial::always)));
	public static final BlockContainer ROTTEN_SOUL_JACK_O_LANTERN = new BlockContainer(new CarvedGourdBlock(AbstractBlock.Settings.of(Material.GOURD, MapColor.PALE_YELLOW).strength(1.0F).sounds(BlockSoundGroup.WOOD).luminance(luminance(10)).allowsSpawning(BaseMaterial::always)));
	public static final Item ROTTEN_PUMPKIN_SEEDS = new Item(ItemSettings());
	public static final BlockContainer ROTTEN_PUMPKIN = new BlockContainer(new CarvableGourdBlock(
			AbstractBlock.Settings.of(Material.GOURD, MapColor.PALE_YELLOW).strength(1.0F).sounds(BlockSoundGroup.WOOD),
			SoundEvents.BLOCK_PUMPKIN_CARVE,
			() -> (StemBlock)Blocks.PUMPKIN_STEM,
			() -> (AttachedStemBlock)Blocks.ATTACHED_PUMPKIN_STEM,
			() -> (CarvedGourdBlock)CARVED_ROTTEN_PUMPKIN.BLOCK, () -> ROTTEN_PUMPKIN_SEEDS));
	//Misc
	public static final BlockContainer TINTED_GLASS_PANE = new BlockContainer(new TintedGlassPaneBlock(AbstractBlock.Settings.of(Material.GLASS).mapColor(MapColor.GRAY).strength(0.3F).sounds(BlockSoundGroup.GLASS).nonOpaque()));
	//Rainbow Sheep
	public static final BlockContainer RAINBOW_WOOL = new BlockContainer(new HavenFacingBlock(Blocks.WHITE_WOOL));
	public static final BlockContainer RAINBOW_CARPET = new BlockContainer(new HorziontalFacingCarpetBlock(AbstractBlock.Settings.copy(Blocks.WHITE_CARPET)));
	public static final BedContainer RAINBOW_BED = new BedContainer("rainbow");
	public static final EntityType<RainbowSheepEntity> RAINBOW_SHEEP_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, RainbowSheepEntity::new).dimensions(EntityType.SHEEP.getDimensions()).trackRangeBlocks(10).build();
	public static final Item RAINBOW_SHEEP_SPAWN_EGG = new SpawnEggItem(RAINBOW_SHEEP_ENTITY, 16777215, 16777215, ItemSettings());
	//Minecraft Earth Flowers
	public static final FlowerContainer BUTTERCUP = new FlowerContainer(StatusEffects.BLINDNESS, 11);
	public static final FlowerContainer PINK_DAISY = new FlowerContainer(StatusEffects.REGENERATION, 8);
	//Other Flowers
	public static final Map<DyeColor, FlowerContainer> CARNATIONS = MapDyeColor((color) -> new FlowerContainer(StatusEffects.WEAKNESS, 5));
	public static final FlowerContainer ROSE = new FlowerContainer(StatusEffects.WEAKNESS, 9);
	public static final FlowerContainer BLUE_ROSE = new FlowerContainer(StatusEffects.WEAKNESS, 9);
	public static final FlowerContainer MAGENTA_TULIP = new FlowerContainer(StatusEffects.FIRE_RESISTANCE, 4);
	public static final FlowerContainer MARIGOLD = new FlowerContainer(StatusEffects.WITHER, 5);
	public static final FlowerContainer PINK_ALLIUM = new FlowerContainer(StatusEffects.FIRE_RESISTANCE, 4);
	public static final FlowerContainer LAVENDER = new FlowerContainer(StatusEffects.INVISIBILITY, 8);
	public static final FlowerContainer HYDRANGEA = new FlowerContainer(StatusEffects.JUMP_BOOST, 7);
	public static final FlowerContainer PAEONIA = new FlowerContainer(StatusEffects.STRENGTH, 6);
	public static final TallBlockContainer AMARANTH = new TallBlockContainer(new TallFlowerBlock(FlowerContainer.TallSettings()));
	public static final TallBlockContainer TALL_ALLIUM = new TallBlockContainer(new TallFlowerBlock(FlowerContainer.TallSettings()));
	public static final TallBlockContainer TALL_PINK_ALLIUM = new TallBlockContainer(new TallFlowerBlock(FlowerContainer.TallSettings()));

	public static final ToolItem PTEROR = new SwordItem(ToolMaterials.NETHERITE, 3, -2.4F, ItemSettings().fireproof());
	public static final ToolItem SBEHESOHE = new SwordItem(ToolMaterials.DIAMOND, 3, -2.4F, ItemSettings().fireproof());
	public static final Item BROKEN_BOTTLE = new Item(ItemSettings());
	public static final Item LOCKET = new Item(ItemSettings());
	public static final Item EMERALD_LOCKET = new Item(ItemSettings());
	public static final Item AMBER_EYE = new AmberEyeItem(ItemSettings().maxCount(1));
	public static final Block AMBER_EYE_END_PORTAL_FRAME = new AmberEyeEndPortalFrameBlock(AbstractBlock.Settings.of(Material.STONE, MapColor.PURPLE).sounds(BlockSoundGroup.GLASS).luminance(luminance(1)).strength(-1.0F, 3600000.0F));

	public static final BlockContainer SOFT_TNT = new BlockContainer(new SoftTntBlock(AbstractBlock.Settings.of(Material.TNT).breakInstantly().sounds(BlockSoundGroup.GRASS)));
	public static final EntityType<SoftTntEntity> SOFT_TNT_ENTITY = new FabricEntityTypeBuilderImpl<SoftTntEntity>(SpawnGroup.MISC, SoftTntEntity::new)
			.dimensions(EntityDimensions.fixed(0.98F, 0.98F)).fireImmune().trackRangeBlocks(10).trackedUpdateRate(10).build();

	public static final Block COFFEE_PLANT = new CoffeePlantBlock(AbstractBlock.Settings.of(Material.PLANT).ticksRandomly().noCollision().sounds(BlockSoundGroup.CROP));
	public static final Item COFFEE_CHERRY = new AliasedBlockItem(COFFEE_PLANT, ItemSettings().food(new FoodComponent.Builder()
		.statusEffect(new StatusEffectInstance(StatusEffects.SPEED, 60, 0), 1.0F)
		.statusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 60, 0), 1.0F)
		.hunger(2).saturationModifier(0.1F)
		.build()));
	public static final Item COFFEE_BEANS = new Item(ItemSettings());
	public static final Item COFFEE = new CoffeeItem(ItemSettings().food(new FoodComponent.Builder()
		.statusEffect(new StatusEffectInstance(StatusEffects.SPEED, 200, 0), 1.0F)
		.statusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 200, 0), 1.0F)
		.build()));
	public static final Item BLACK_COFFEE = new CoffeeItem(ItemSettings().food(new FoodComponent.Builder()
		.statusEffect(new StatusEffectInstance(StatusEffects.SPEED, 200, 1), 1.0F)
		.statusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 200, 1), 1.0F)
		.build()));

	public static final Item CINNAMON = new Item(ItemSettings());

	public static final TreeMaterial CHERRY_MATERIAL = new TreeMaterial("cherry", MapColor.RAW_IRON_PINK, CherrySaplingGenerator::new, true);
	public static final BlockContainer PALE_CHERRY_LEAVES = new BlockContainer(new HavenLeavesBlock(CHERRY_MATERIAL.getLeaves().BLOCK));
	public static final BlockContainer PINK_CHERRY_LEAVES = new BlockContainer(new HavenLeavesBlock(CHERRY_MATERIAL.getLeaves().BLOCK));
	public static final BlockContainer WHITE_CHERRY_LEAVES = new BlockContainer(new HavenLeavesBlock(CHERRY_MATERIAL.getLeaves().BLOCK));
	public static final Item CHERRY_ITEM = new Item(ItemSettings().food(new FoodComponent.Builder().hunger(4).saturationModifier(0.3F).build()));

	private static ConfiguredFeature<TreeFeatureConfig, ?> CherryTreeFeature(Block leaves) {
		return Feature.TREE.configure(
				new TreeFeatureConfig.Builder(
						new SimpleBlockStateProvider(CHERRY_MATERIAL.getLog().BLOCK.getDefaultState()),
						new ForkingTrunkPlacer(5, 2, 2),
						new SimpleBlockStateProvider(leaves.getDefaultState()),
						new SimpleBlockStateProvider(CHERRY_MATERIAL.getSapling().BLOCK.getDefaultState()),
						new AcaciaFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0)),
						new TwoLayersFeatureSize(1, 0, 2)
				).ignoreVines().build()
		);
	}
	public static final ConfiguredFeature<TreeFeatureConfig, ?> CHERRY_TREE = CherryTreeFeature(CHERRY_MATERIAL.getLeaves().BLOCK);
	public static final ConfiguredFeature<TreeFeatureConfig, ?> PALE_CHERRY_TREE = CherryTreeFeature(PALE_CHERRY_LEAVES.BLOCK);
	public static final ConfiguredFeature<TreeFeatureConfig, ?> PINK_CHERRY_TREE = CherryTreeFeature(PINK_CHERRY_LEAVES.BLOCK);
	public static final ConfiguredFeature<TreeFeatureConfig, ?> WHITE_CHERRY_TREE = CherryTreeFeature(WHITE_CHERRY_LEAVES.BLOCK);

	public static final TreeMaterial CASSIA_MATERIAL = new TreeMaterial("cassia", MapColor.BROWN, BlockSoundGroup.AZALEA_LEAVES, CassiaSaplingGenerator::new, true);
	public static final BlockContainer FLOWERING_CASSIA_LEAVES = new BlockContainer(new HavenLeavesBlock(CASSIA_MATERIAL.getLeaves().BLOCK));
	public static final ConfiguredFeature<TreeFeatureConfig, ?> CASSIA_TREE = Feature.TREE.configure(
			new TreeFeatureConfig.Builder(
					new SimpleBlockStateProvider(CASSIA_MATERIAL.getLog().BLOCK.getDefaultState()),
					new BendingTrunkPlacer(4, 2, 0, 3, UniformIntProvider.create(1, 2)),
					new WeightedBlockStateProvider(
							new DataPool.Builder()
									.add(CASSIA_MATERIAL.getLeaves().BLOCK.getDefaultState(), 3)
									.add(FLOWERING_CASSIA_LEAVES.BLOCK.getDefaultState(), 1)
					),
					new SimpleBlockStateProvider(CASSIA_MATERIAL.getSapling().BLOCK.getDefaultState()),
					new RandomSpreadFoliagePlacer(ConstantIntProvider.create(3), ConstantIntProvider.create(0), ConstantIntProvider.create(2), 50),
					new TwoLayersFeatureSize(1, 0, 1)
			)
					.build()
	);
	//Cookies
	public static final Item SNICKERDOODLE = new Item(ItemSettings().food(FoodComponents.COOKIE));
	public static final Item CHORUS_COOKIE = new ChorusFruitItem(ItemSettings().food(FoodComponents.COOKIE));
	public static final Item CHOCOLATE_COOKIE = new Item(ItemSettings().food(FoodComponents.COOKIE));
	public static final Item CONFETTI_COOKIE = new Item(ItemSettings().food(FoodComponents.COOKIE));
	public static final Item CINNAMON_ROLL = new Item(ItemSettings().food(new FoodComponent.Builder().hunger(3).saturationModifier(0.3F).build()));
	//Strawberry
	public static final Block STRAWBERRY_BUSH = new StrawberryBushBlock(AbstractBlock.Settings.copy(Blocks.SWEET_BERRY_BUSH));
	public static final Item STRAWBERRY = new AliasedBlockItem(STRAWBERRY_BUSH, ItemSettings().food(new FoodComponent.Builder().hunger(2).saturationModifier(0.1F).build()));
	//Drinks & Juice
	public static Item.Settings BottledDrinkSettings() {
		return ItemSettings().recipeRemainder(Items.GLASS_BOTTLE).food(new FoodComponent.Builder().hunger(4).saturationModifier(0.5F).build());
	}
	public static final Item APPLE_CIDER = new BottledDrinkItem(ItemSettings().recipeRemainder(Items.GLASS_BOTTLE).food(new FoodComponent.Builder().hunger(5).saturationModifier(0.6F).build()));
	public static final BlockContainer JUICER = new BlockContainer(new JuicerBlock(AbstractBlock.Settings.of(Material.STONE).requiresTool().strength(3.5F)), ItemSettings());
	public static final Item APPLE_JUICE = new BottledDrinkItem(BottledDrinkSettings());
	public static final Item BEETROOT_JUICE = new BottledDrinkItem(BottledDrinkSettings());
	public static final Item BLACK_APPLE_JUICE = new BottledDrinkItem(ItemSettings().recipeRemainder(Items.GLASS_BOTTLE).food(new FoodComponent.Builder().hunger(4).saturationModifier(0.5F).statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 2, 2), 1F).statusEffect(new StatusEffectInstance(StatusEffects.INSTANT_HEALTH, 1, 2), 1F).build()));
	public static final Item CABBAGE_JUICE = new BottledDrinkItem(BottledDrinkSettings());
	public static final Item CACTUS_JUICE = new BottledDrinkItem(BottledDrinkSettings());
	public static final Item CARROT_JUICE = new BottledDrinkItem(BottledDrinkSettings());
	public static final Item CHERRY_JUICE = new BottledDrinkItem(BottledDrinkSettings());
	public static final Item CHORUS_JUICE = new ChorusJuiceItem(BottledDrinkSettings());
	public static final Item GLOW_BERRY_JUICE = new BottledDrinkItem(BottledDrinkSettings());
	public static final Item KELP_JUICE = new BottledDrinkItem(BottledDrinkSettings());
	public static final Item MELON_JUICE = new BottledDrinkItem(BottledDrinkSettings());
	public static final Item ONION_JUICE = new BottledDrinkItem(BottledDrinkSettings());
	public static final Item POTATO_JUICE = new BottledDrinkItem(BottledDrinkSettings());
	public static final Item PUMPKIN_JUICE = new BottledDrinkItem(BottledDrinkSettings());
	public static final Item SEA_PICKLE_JUICE = new BottledDrinkItem(BottledDrinkSettings());
	public static final Item STRAWBERRY_JUICE = new BottledDrinkItem(BottledDrinkSettings());
	public static final Item SWEET_BERRY_JUICE = new BottledDrinkItem(BottledDrinkSettings());
	public static final Item TOMATO_JUICE = new BottledDrinkItem(BottledDrinkSettings());
	public static Item.Settings SmoothieSettings() {
		return ItemSettings().recipeRemainder(Items.GLASS_BOTTLE).food(new FoodComponent.Builder().hunger(5).saturationModifier(0.5F).build());
	}
	public static final Item APPLE_SMOOTHIE = new BottledDrinkItem(SmoothieSettings());
	public static final Item BEETROOT_SMOOTHIE = new BottledDrinkItem(SmoothieSettings());
	public static final Item BLACK_APPLE_SMOOTHIE = new BottledDrinkItem(ItemSettings().recipeRemainder(Items.GLASS_BOTTLE).food(new FoodComponent.Builder().hunger(5).saturationModifier(0.5F).statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 2, 2), 1F).statusEffect(new StatusEffectInstance(StatusEffects.INSTANT_HEALTH, 1, 2), 1F).build()));
	public static final Item CABBAGE_SMOOTHIE = new BottledDrinkItem(SmoothieSettings());
	public static final Item CACTUS_SMOOTHIE = new BottledDrinkItem(SmoothieSettings());
	public static final Item CARROT_SMOOTHIE = new BottledDrinkItem(SmoothieSettings());
	public static final Item CHERRY_SMOOTHIE = new BottledDrinkItem(SmoothieSettings());
	public static final Item CHORUS_SMOOTHIE = new ChorusJuiceItem(SmoothieSettings());
	public static final Item GLOW_BERRY_SMOOTHIE = new BottledDrinkItem(SmoothieSettings());
	public static final Item KELP_SMOOTHIE = new BottledDrinkItem(SmoothieSettings());
	public static final Item MELON_SMOOTHIE = new BottledDrinkItem(SmoothieSettings());
	public static final Item ONION_SMOOTHIE = new BottledDrinkItem(SmoothieSettings());
	public static final Item POTATO_SMOOTHIE = new BottledDrinkItem(SmoothieSettings());
	public static final Item PUMPKIN_SMOOTHIE = new BottledDrinkItem(SmoothieSettings());
	public static final Item SEA_PICKLE_SMOOTHIE = new BottledDrinkItem(SmoothieSettings());
	public static final Item STRAWBERRY_SMOOTHIE = new BottledDrinkItem(SmoothieSettings());
	public static final Item SWEET_BERRY_SMOOTHIE = new BottledDrinkItem(SmoothieSettings());
	public static final Item TOMATO_SMOOTHIE = new BottledDrinkItem(SmoothieSettings());

	//Milkshakes & Ice Cream
	public static final Item MILKSHAKE = new MilkBottleItem(SmoothieSettings());
	public static final Item CHOCOLATE_MILKSHAKE = new MilkBottleItem(SmoothieSettings());
	public static final Item COFFEE_MILKSHAKE = new CoffeeMilkBottleItem(SmoothieSettings());
	public static final Item STRAWBERRY_MILKSHAKE = new MilkBottleItem(SmoothieSettings());
	public static final Item CHOCOLATE_CHIP_MILKSHAKE = new MilkBottleItem(SmoothieSettings());
	public static Item.Settings IceCreamSettings() {
		return ItemSettings().recipeRemainder(Items.GLASS_BOTTLE).food(new FoodComponent.Builder().hunger(6).saturationModifier(0.5F).build());
	}
	public static final Item ICE_CREAM = new MilkBottleItem(IceCreamSettings());
	public static final Item CHOCOLATE_ICE_CREAM = new MilkBottleItem(IceCreamSettings());
	public static final Item COFFEE_ICE_CREAM = new CoffeeMilkBottleItem(IceCreamSettings());
	public static final Item STRAWBERRY_ICE_CREAM = new MilkBottleItem(IceCreamSettings());
	public static final Item CHOCOLATE_CHIP_ICE_CREAM = new MilkBottleItem(IceCreamSettings());
	//Hotdog
	public static final Item HOTDOG = new Item(ItemSettings().food(new FoodComponent.Builder().hunger(4).saturationModifier(0.4F).build()));
	
	//Bamboo
	public static final BambooMaterial BAMBOO_MATERIAL = new BambooMaterial("bamboo", MapColor.DARK_GREEN);
	public static final BambooMaterial DRIED_BAMBOO_MATERIAL = new BambooMaterial("dried_bamboo", MapColor.PALE_YELLOW);
	public static final BlockContainer DRIED_BAMBOO_BLOCK = new BlockContainer(new DriedBambooBlock(AbstractBlock.Settings.copy(Blocks.BAMBOO)));
	public static final Block POTTED_DRIED_BAMBOO = new FlowerPotBlock(DRIED_BAMBOO_BLOCK.BLOCK, AbstractBlock.Settings.of(Material.DECORATION).breakInstantly().nonOpaque());

	//Charred Wood
	public static final BaseTreeMaterial CHARRED_MATERIAL = new BaseTreeMaterial("charred", MapColor.BLACK, false);

	//Vanilla Wood
	public static final VanillaWoodMaterial ACACIA_MATERIAL = new VanillaWoodMaterial("acacia", MapColor.ORANGE);
	public static final VanillaWoodMaterial BIRCH_MATERIAL = new VanillaWoodMaterial("birch", MapColor.PALE_YELLOW);
	public static final VanillaWoodMaterial DARK_OAK_MATERIAL = new VanillaWoodMaterial("dark_oak", MapColor.BROWN);
	public static final VanillaWoodMaterial JUNGLE_MATERIAL = new VanillaWoodMaterial("jungle", MapColor.DIRT_BROWN);
	//public static final VanillaWoodMaterial OAK_MATERIAL = new VanillaWoodMaterial("oak", MapColor.OAK_TAN);
	public static final VanillaWoodMaterial SPRUCE_MATERIAL = new VanillaWoodMaterial("spruce", MapColor.SPRUCE_BROWN);
	public static final VanillaNetherWoodMaterial CRIMSON_MATERIAL = new VanillaNetherWoodMaterial("crimson", MapColor.DULL_PINK, Blocks.CRIMSON_PLANKS, false);
	public static final VanillaNetherWoodMaterial WARPED_MATERIAL = new VanillaNetherWoodMaterial("warped", MapColor.DARK_AQUA, Blocks.WARPED_PLANKS, false);
	//Amber Fungus
	public static final PottedBlockContainer GILDED_ROOTS = new PottedBlockContainer(new HavenRootsBlock(AbstractBlock.Settings.of(Material.NETHER_SHOOTS, MapColor.GOLD).noCollision().breakInstantly().sounds(BlockSoundGroup.ROOTS)));
	public static final HugeFungusFeatureConfig GILDED_FUNGUS_CONFIG;
	public static final HugeFungusFeatureConfig GILDED_FUNGUS_NOT_PLANTED_CONFIG;
	public static final ConfiguredFeature<?, ?> GILDED_FOREST_VEGETATION;
	public static final ConfiguredFeature<?, ?> PATCH_GILDED_ROOTS;
	public static final ConfiguredFeature<?, ?> GILDED_FUNGI;
	public static final ConfiguredFeature<HugeFungusFeatureConfig, ?> GILDED_FUNGI_PLANTED;
	private static ConfiguredFeature<HugeFungusFeatureConfig, ?> getGildedFungiPlanted() { return GILDED_FUNGI_PLANTED; }
	public static final FungusMaterial GILDED_MATERIAL = new FungusMaterial("gilded", MapColor.GOLD, HavenMod::getGildedFungiPlanted);
	public static final BlockPileFeatureConfig GILDED_ROOTS_CONFIG = new BlockPileFeatureConfig(
		new WeightedBlockStateProvider(DataPool.<BlockState>builder()
			.add(GILDED_ROOTS.BLOCK.getDefaultState(), 87)
			.add(GILDED_MATERIAL.getFungus().BLOCK.getDefaultState(), 11)
			.add(Blocks.CRIMSON_FUNGUS.getDefaultState(), 1)
			.add(Blocks.WARPED_FUNGUS.getDefaultState(), 1)
		)
	);
	public static final BlockContainer GILDED_NYLIUM = new BlockContainer(new HavenNyliumBlock(AbstractBlock.Settings.of(Material.STONE, MapColor.GOLD).requiresTool().strength(0.4F).sounds(BlockSoundGroup.NYLIUM).ticksRandomly()));

	//Misc Removed
	public static final BlockContainer WAX_BLOCK = new BlockContainer(new Block(AbstractBlock.Settings.copy(Blocks.HONEYCOMB_BLOCK)));

	//Misc Minecraft Earth
	public static final Item HORN = new Item(ItemSettings());
	//Liquid Mud
	public static final DefaultParticleType MUD_BUBBLE = FabricParticleTypes.simple(false);
	public static final DefaultParticleType MUD_SPLASH = FabricParticleTypes.simple(false);
	public static final DefaultParticleType DRIPPING_MUD = FabricParticleTypes.simple(false);
	public static final DefaultParticleType FALLING_MUD = FabricParticleTypes.simple(false);
	public static final DefaultParticleType FALLING_DRIPSTONE_MUD = FabricParticleTypes.simple(false);
	public static final FlowableFluid STILL_MUD_FLUID = new MudFluid.Still();
	public static final FlowableFluid FLOWING_MUD_FLUID = new MudFluid.Flowing();
	public static final FluidBlock MUD_FLUID_BLOCK = new MudFluidBlock(STILL_MUD_FLUID, FabricBlockSettings.copyOf(Blocks.WATER).mapColor(MapColor.BROWN));
	public static final BucketItem MUD_BUCKET = new MudBucketItem(STILL_MUD_FLUID, ItemSettings().recipeRemainder(Items.BUCKET).maxCount(1));
	public static final Block MUD_CAULDRON;
	//Music Discs
	public static final Item MUSIC_DISC_OTHERSIDE = new MusicDiscItem(14, HavenSoundEvents.MUSIC_DISC_OTHERSIDE, ItemSettings().maxCount(1).rarity(Rarity.RARE));
	public static final Item MUSIC_DISC_5 = new MusicDiscItem(15, HavenSoundEvents.MUSIC_DISC_5, ItemSettings().maxCount(1).rarity(Rarity.RARE));
	public static final Item DISC_FRAGMENT_5 = new DiscFragmentItem(ItemSettings());
	//Goat Horn
	public static final Item GOAT_HORN = new GoatHornItem(ItemSettings().maxCount(1));
	//Mud
	public static final BlockContainer MUD = new BlockContainer(new MudBlock(AbstractBlock.Settings.copy(Blocks.DIRT).mapColor(MapColor.TERRACOTTA_CYAN).allowsSpawning(BaseMaterial::always).solidBlock(BaseMaterial::always).blockVision(BaseMaterial::always).suffocates(BaseMaterial::always).sounds(HavenBlockSoundGroups.MUD)));
	public static final MudMaterial MUD_MATERIAL = new MudMaterial();
	//Mangrove
	public static final MangroveMaterial MANGROVE_MATERIAL = new MangroveMaterial("mangrove", MapColor.RED, BlockSoundGroup.GRASS, true);
	public static final BlockContainer MANGROVE_ROOTS = new BlockContainer(new MangroveRootsBlock(AbstractBlock.Settings.of(Material.WOOD, MapColor.SPRUCE_BROWN).strength(0.7f).ticksRandomly().sounds(HavenBlockSoundGroups.MANGROVE_ROOTS).nonOpaque().suffocates(BaseMaterial::never).blockVision(BaseMaterial::never).nonOpaque()));
	public static final BlockContainer MUDDY_MANGROVE_ROOTS = new BlockContainer(new PillarBlock(AbstractBlock.Settings.of(Material.SOIL, MapColor.SPRUCE_BROWN).strength(0.7f).sounds(HavenBlockSoundGroups.MUDDY_MANGROVE_ROOTS)));
	//Frogs
	public static final Material FROGLIGHT_MATERIAL = new Material.Builder(MapColor.CLEAR).build();
	public static final BlockContainer OCHRE_FROGLIGHT = new BlockContainer(new PillarBlock(AbstractBlock.Settings.of(FROGLIGHT_MATERIAL, MapColor.PALE_YELLOW).strength(0.3f).luminance(state -> 15).sounds(HavenBlockSoundGroups.FROGLIGHT)));
	public static final BlockContainer VERDANT_FROGLIGHT = new BlockContainer(new PillarBlock(AbstractBlock.Settings.of(FROGLIGHT_MATERIAL, MapColor.LICHEN_GREEN).strength(0.3f).luminance(state -> 15).sounds(HavenBlockSoundGroups.FROGLIGHT)));
	public static final BlockContainer PEARLESCENT_FROGLIGHT = new BlockContainer(new PillarBlock(AbstractBlock.Settings.of(FROGLIGHT_MATERIAL, MapColor.PINK).strength(0.3f).luminance(state -> 15).sounds(HavenBlockSoundGroups.FROGLIGHT)));
	//Deep Dark
	public static final BlockContainer REINFORCED_DEEPSLATE = new BlockContainer(new Block(AbstractBlock.Settings.of(Material.STONE, MapColor.DEEPSLATE_GRAY).sounds(BlockSoundGroup.DEEPSLATE).strength(55.0f, 1200.0f)));
	public static final Item ECHO_SHARD = new Item(ItemSettings());

	public static final Item RAMEN = new MushroomStewItem(ItemSettings().maxCount(1).food(new FoodComponent.Builder().hunger(6).saturationModifier(0.6F).build()));
	public static final Item STIR_FRY = new MushroomStewItem(ItemSettings().maxCount(1).food(new FoodComponent.Builder().hunger(6).saturationModifier(0.6F).build()));

	public static final FoodComponent CANDY_FOOD_COMPONENT = new FoodComponent.Builder().hunger(1).saturationModifier(0.1F).build();
	public static Item.Settings CandyItemSettings() {
		return ItemSettings().food(CANDY_FOOD_COMPONENT);
	}
	public static final Item CINNAMON_BEAN = new Item(CandyItemSettings());
	public static final Item PINK_COTTON_CANDY = new Item(ItemSettings().food(new FoodComponent.Builder().hunger(3).saturationModifier(0.1F).build()));
	public static final Item BLUE_COTTON_CANDY = new Item(ItemSettings().food(new FoodComponent.Builder().hunger(3).saturationModifier(0.1F).build()));
	public static final Item CANDY_CANE = new Item(CandyItemSettings());
	public static final Item CARAMEL = new Item(CandyItemSettings());
	public static final Item LOLLIPOP = new Item(CandyItemSettings());
	public static final Item MILK_CHOCOLATE = new Item(CandyItemSettings());
	public static final Item DARK_CHOCOLATE = new Item(CandyItemSettings());
	public static final Item WHITE_CHOCOLATE = new Item(CandyItemSettings());
	public static final Item CARAMEL_APPLE = new Item(ItemSettings().food(new FoodComponent.Builder().hunger(5).saturationModifier(0.3F).build()));

	public static final Item MARSHMALLOW = new Item(CandyItemSettings());
	public static final Item ROAST_MARSHMALLOW = new Item(ItemSettings().recipeRemainder(Items.STICK).food(new FoodComponent.Builder().hunger(1).saturationModifier(0.2F).build()));
	public static final Item MARSHMALLOW_ON_STICK = new Item(ItemSettings().food(new FoodComponent.Builder().hunger(2).saturationModifier(0.2F).build()));
	public static final Item ROAST_MARSHMALLOW_ON_STICK = new Item(ItemSettings().recipeRemainder(Items.STICK).food(new FoodComponent.Builder().hunger(2).saturationModifier(0.2F).build()));

	public static final Item AMETHYST_CANDY = new Item(ItemSettings()); //not edible usually (it's rocks)
	public static final Map<DyeColor, Item> ROCK_CANDIES = MapDyeColor((color) -> new Item(ItemSettings().food(CANDY_FOOD_COMPONENT)));

	public static final Item THROWABLE_TOMATO_ITEM = new ThrowableTomatoItem(ItemSettings().maxCount(16));
	public static final EntityType<ThrownTomatoEntity> THROWABLE_TOMATO_ENTITY = FabricEntityTypeBuilder.<ThrownTomatoEntity>create(SpawnGroup.MISC, ThrownTomatoEntity::new).dimensions(EntityDimensions.fixed(0.25F, 0.25F)).trackRangeBlocks(4).trackedUpdateRate(10).build();
	public static final DefaultParticleType TOMATO_PARTICLE = FabricParticleTypes.simple();
	public static final StatusEffect BOO_EFFECT = new BooEffect();
	public static final StatusEffect KILLJOY_EFFECT = new KilljoyEffect();
	public static final Item TOMATO_SOUP = new MushroomStewItem(ItemSettings().food(FoodComponents.BEETROOT_SOUP));

	public static final StatusEffect BONE_ROT_EFFECT = new BoneRotEffect();
	public static final StatusEffect BLEEDING_EFFECT = new BleedingEffect();

	//Server Blood
	public static final DefaultParticleType BLOOD_BUBBLE = FabricParticleTypes.simple(false);
	public static final DefaultParticleType BLOOD_SPLASH = FabricParticleTypes.simple(false);
	public static final DefaultParticleType DRIPPING_BLOOD = FabricParticleTypes.simple(false);
	public static final DefaultParticleType FALLING_BLOOD = FabricParticleTypes.simple(false);
	public static final DefaultParticleType FALLING_DRIPSTONE_BLOOD = FabricParticleTypes.simple(false);
	private static final Block BLOOD_BLOCK_BLOCK = new Block(AbstractBlock.Settings.of(Material.SOLID_ORGANIC, MapColor.RED).strength(1.0F).sounds(BlockSoundGroup.WART_BLOCK));
	public static final ItemGroup BLOOD_ITEM_GROUP = FabricItemGroupBuilder.build(HavenMod.ID("blood"), () -> new ItemStack(BLOOD_BLOCK_BLOCK));
	public static final Item.Settings BloodItemSettings() {
		return ItemSettings().group(BLOOD_ITEM_GROUP);
	}
	public static final BlockContainer BLOOD_BLOCK = new BlockContainer(BLOOD_BLOCK_BLOCK, BloodItemSettings());
	public static final BloodMaterial BLOOD_MATERIAL = new BloodMaterial("blood");
	public static final BlockContainer DRIED_BLOOD_BLOCK = new BlockContainer(new Block(AbstractBlock.Settings.copy(BLOOD_BLOCK.BLOCK)), BloodItemSettings());
	public static final BloodMaterial DRIED_BLOOD_MATERIAL = new BloodMaterial("dried_blood");

	public static final FlowableFluid STILL_BLOOD_FLUID = new BloodFluid.Still();
	public static final FlowableFluid FLOWING_BLOOD_FLUID = new BloodFluid.Flowing();
	public static final FluidBlock BLOOD_FLUID_BLOCK = new BloodFluidBlock(STILL_BLOOD_FLUID, FabricBlockSettings.copyOf(Blocks.WATER).mapColor(MapColor.RED));
	public static final BucketItem BLOOD_BUCKET = new BloodBucketItem(STILL_BLOOD_FLUID, BloodItemSettings().recipeRemainder(Items.BUCKET).maxCount(1));

	public static final Block BLOOD_CAULDRON;
	public static final Item BLOOD_BOTTLE = new BloodBottleItem(BloodItemSettings().maxCount(16).recipeRemainder(Items.GLASS_BOTTLE));
	public static final Item LAVA_BOTTLE = new LavaBottleItem(ItemSettings().maxCount(16).recipeRemainder(Items.GLASS_BOTTLE));
	//TODO: WATER_BOTTLE
	public static final Item SUGAR_WATER_BOTTLE = new SugarWaterBottleItem(ItemSettings().maxCount(16).recipeRemainder(Items.GLASS_BOTTLE).food(new FoodComponent.Builder().hunger(0).saturationModifier(0.1F).build()));
	public static final StatusEffect ICHORED_EFFECT = new IchoredEffect();
	public static final Item ICHOR_BOTTLE = new IchorBottleItem(ItemSettings().maxCount(16).recipeRemainder(Items.GLASS_BOTTLE));
	public static final Item SLIME_BOTTLE = new SlimeBottleItem(ItemSettings().maxCount(16).recipeRemainder(Items.GLASS_BOTTLE));
	public static final Item SLUDGE_BOTTLE = new SludgeBottleItem(ItemSettings().maxCount(16).recipeRemainder(Items.GLASS_BOTTLE));
	public static final Item MAGMA_CREAM_BOTTLE = new MagmaCreamBottleItem(ItemSettings().maxCount(16).recipeRemainder(Items.GLASS_BOTTLE));
	public static final Item MUD_BOTTLE = new MudBottleItem(ItemSettings().maxCount(16).recipeRemainder(Items.GLASS_BOTTLE));
	//Syringes
	public static final Item BLOOD_SYRINGE = new BloodSyringeItem(BloodType.PLAYER, (PlayerEntity user, LivingEntity entity) -> {
		BloodType bloodType = BloodType.Get(entity);
			if (bloodType == BloodType.PLAYER || bloodType == BloodType.ZOMBIE) BloodSyringeItem.heal(entity,1);
			else if (entity instanceof PlayerEntity && bloodType != BloodType.NONE) BloodSyringeItem.heal(entity, 1);
			else entity.damage(HavenDamageSource.Injected(user, bloodType), 1);
	});
	public static final Item ALLAY_BLOOD_SYRINGE = new BloodSyringeItem(BloodType.ALLAY);
	public static final Item ANEMIC_BLOOD_SYRINGE = new BloodSyringeItem(BloodType.ANEMIC);
	public static final Item AVIAN_BLOOD_SYRINGE = new BloodSyringeItem(BloodType.AVIAN);
	public static final Item AXOLOTL_BLOOD_SYRINGE = new BloodSyringeItem(BloodType.AXOLOTL);
	public static final Item BAT_BLOOD_SYRINGE = new BloodSyringeItem(BloodType.BAT);
	public static final Item BEAR_BLOOD_SYRINGE = new BloodSyringeItem(BloodType.BEAR);
	public static final Item BEE_BLOOD_SYRINGE = new BloodSyringeItem(BloodType.BEE, (PlayerEntity user, LivingEntity entity) -> {
		BloodType bloodType = BloodType.Get(entity);
		if (bloodType == BloodType.BEE || bloodType == BloodType.BEE_ENDERMAN) BloodSyringeItem.heal(entity, 1);
		else {
			entity.damage(HavenDamageSource.Injected(user, bloodType), 1);
			if (bloodType.IsPoisonVulnerable()) entity.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 100, 1));
		}
	});
	public static final Item BEE_ENDERMAN_BLOOD_SYRINGE = new BloodSyringeItem(BloodType.BEE_ENDERMAN, (PlayerEntity user, LivingEntity entity) -> {
		BloodType bloodType = BloodType.Get(entity);
		if (bloodType == BloodType.BEE || bloodType == BloodType.BEE_ENDERMAN) BloodSyringeItem.heal(entity, 1);
		else {
			if (bloodType == BloodType.ENDERMAN || bloodType == BloodType.DRAGON) BloodSyringeItem.heal(entity, 1);
			else entity.damage(HavenDamageSource.Injected("bee_enderman_blood", user), 1);
			if (bloodType.IsPoisonVulnerable()) entity.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 100, 1));
		}
	});
	public static final Item CANINE_BLOOD_SYRINGE = new BloodSyringeItem(BloodType.CANINE);
	public static final Item CHORUS_SYRINGE = new BloodSyringeItem(BloodType.CHORUS, (PlayerEntity user, LivingEntity entity) -> {
		BloodType bloodType = BloodType.Get(entity);
		if (bloodType == BloodType.CHORUS) BloodSyringeItem.heal(entity, 1);
		else {
			entity.damage(HavenDamageSource.Injected("chorus", user), 1);
			if (bloodType.IsChorusVulnerable()) ChorusCommand.TeleportEntity(entity);
		}
	});
	public static final Item COW_BLOOD_SYRINGE = new BloodSyringeItem(BloodType.COW);
	public static final Item CREEPER_BLOOD_SYRINGE = new BloodSyringeItem(BloodType.CREEPER);
	public static final Item DISEASED_FELINE_BLOOD_SYRINGE = new BloodSyringeItem(BloodType.DISEASED_FELINE, (PlayerEntity user, LivingEntity entity) -> {
		BloodType bloodType = BloodType.Get(entity);
		if (bloodType == BloodType.DISEASED_FELINE) BloodSyringeItem.heal(entity, 1);
		else {
			if (bloodType == BloodType.FELINE) BloodSyringeItem.heal(entity, 1);
			entity.damage(HavenDamageSource.Injected("diseased_blood", user), 1);
			entity.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 200, 1));
		}
	});
	public static final Item DOLPHIN_BLOOD_SYRINGE = new BloodSyringeItem(BloodType.DOLPHIN);
	public static final Item DRAGON_BLOOD_SYRINGE = new BloodSyringeItem(BloodType.DRAGON, (PlayerEntity user, LivingEntity entity) -> {
		BloodType bloodType = BloodType.Get(entity);
		if (bloodType == BloodType.DRAGON) BloodSyringeItem.heal(entity, 1);
		else entity.damage(HavenDamageSource.Injected("dragon_blood", user), 1);
	});
	public static final Item ENDERMAN_BLOOD_SYRINGE = new BloodSyringeItem(BloodType.ENDERMAN, (PlayerEntity user, LivingEntity entity) -> {
		BloodType bloodType = BloodType.Get(entity);
		if (bloodType == BloodType.BEE_ENDERMAN || bloodType == BloodType.DRAGON) BloodSyringeItem.heal(entity, 1);
		else entity.damage(HavenDamageSource.Injected(user, bloodType), 1);
	});
	public static final Item EQUINE_BLOOD_SYRINGE = new BloodSyringeItem(BloodType.EQUINE);
	public static final Item FELINE_BLOOD_SYRINGE = new BloodSyringeItem(BloodType.FELINE);
	public static final Item FISH_BLOOD_SYRINGE = new BloodSyringeItem(BloodType.FISH);
	public static final Item GOAT_BLOOD_SYRINGE = new BloodSyringeItem(BloodType.GOAT, (PlayerEntity user, LivingEntity entity) -> {
		BloodType bloodType = BloodType.Get(entity);
		if (bloodType == BloodType.GOAT || bloodType == BloodType.SHEEP) BloodSyringeItem.heal(entity, 1);
		else entity.damage(HavenDamageSource.Injected("goat_blood", user), 1);
	});
	public static final Item HONEY_SYRINGE = new BloodSyringeItem(BloodType.HONEY,(PlayerEntity user, LivingEntity entity) -> {
		BloodType bloodType = BloodType.Get(entity);
		if (bloodType == BloodType.HONEY || bloodType == BloodType.BEE || bloodType == BloodType.BEE_ENDERMAN || bloodType == BloodType.BEAR){
			BloodSyringeItem.heal(entity, 1);
		}
		else {
			entity.damage(HavenDamageSource.Injected("honey", user), 1);
			if (!entity.getEntityWorld().isClient) user.removeStatusEffect(StatusEffects.POISON);
		}
	});
	public static final Item ICHOR_SYRINGE = new BloodSyringeItem(BloodType.ICHOR,(PlayerEntity user, LivingEntity entity) -> {
		BloodType bloodType = BloodType.Get(entity);
		if (bloodType == BloodType.ICHOR) BloodSyringeItem.heal(entity, 1);
		else {
			entity.damage(HavenDamageSource.Injected("ichor", user), 4);
			entity.addStatusEffect(new StatusEffectInstance(ICHORED_EFFECT, 200, 1));
		}
	});
	public static final Item INSECT_BLOOD_SYRINGE = new BloodSyringeItem(BloodType.INSECT);
	public static final Item LAVA_SYRINGE = new BloodSyringeItem(BloodType.LAVA, (PlayerEntity user, LivingEntity entity) -> {
		BloodType bloodType = BloodType.Get(entity);
		if (bloodType == BloodType.LAVA || bloodType == BloodType.MAGMA) BloodSyringeItem.heal(entity, 1);
		else {
			if (bloodType.IsFireVulnerable()) {
				entity.setOnFireFor(20000);
				entity.damage(HavenDamageSource.Injected("lava", user), 4);
			}
			else entity.damage(HavenDamageSource.Injected("lava", user), 1);
		}
	});
	public static final Item LLAMA_BLOOD_SYRINGE = new BloodSyringeItem(BloodType.LLAMA);
	public static final Item MAGMA_CREAM_SYRINGE = new BloodSyringeItem(BloodType.MAGMA, (PlayerEntity user, LivingEntity entity) -> {
		BloodType bloodType = BloodType.Get(entity);
		if (bloodType == BloodType.LAVA || bloodType == BloodType.MAGMA) BloodSyringeItem.heal(entity, 1);
		else {
			if (bloodType == BloodType.SLIME) BloodSyringeItem.heal(entity, 1);
			else entity.damage(HavenDamageSource.Injected("magma_cream", user), 1);
			if (bloodType.IsFireVulnerable()) entity.setOnFireFor(5);
		}
	});
	public static final Item MUD_SYRINGE = new BloodSyringeItem(BloodType.MUD, (PlayerEntity user, LivingEntity entity) -> {
		BloodType bloodType = BloodType.Get(entity);
		if (bloodType == BloodType.MUD) entity.heal(1);
		else entity.damage(HavenDamageSource.Injected("mud", user), 1);
	});
	private static void ApplyMilkSyringe(PlayerEntity user, LivingEntity entity) { ApplyMilkSyringe(user, entity, false); }
	private static void ApplyMilkSyringe(PlayerEntity user, LivingEntity entity, boolean coffee) {
		BloodType bloodType = BloodType.Get(entity);
		if (bloodType == BloodType.MILK) entity.heal(1);
		else if (bloodType != BloodType.COW && bloodType != BloodType.GOAT){
			entity.damage(HavenDamageSource.Injected("milk", user), 1);
		}
		MilkUtils.ApplyMilk(entity.getEntityWorld(), user, coffee);
	}
	public static final Item MILK_SYRINGE = new BloodSyringeItem(BloodType.MILK, HavenMod::ApplyMilkSyringe);
	public static final Item NEPHAL_BLOOD_SYRINGE = new BloodSyringeItem(BloodType.NEPHAL, (PlayerEntity user, LivingEntity entity) -> {
		BloodType bloodType = BloodType.Get(entity);
		if (bloodType == BloodType.NEPHAL) entity.heal(1);
		else if (bloodType == BloodType.VAMPIRE) {
			entity.heal(1);
			entity.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 200, 1));
		}
		else entity.damage(HavenDamageSource.Injected(user, bloodType), 1);
	});
	public static final Item NETHER_BLOOD_SYRINGE = new BloodSyringeItem(BloodType.NETHER);
	public static final Item NETHER_ROYALTY_BLOOD_SYRINGE = new BloodSyringeItem(BloodType.NETHER_ROYALTY, (PlayerEntity user, LivingEntity entity) -> {
		BloodType bloodType = BloodType.Get(entity);
		if (bloodType == BloodType.NETHER_ROYALTY || bloodType == BloodType.LAVA) BloodSyringeItem.heal(entity, 1);
		else if (bloodType == BloodType.NETHER) BloodSyringeItem.heal(entity, 2);
		else {
			if (bloodType.IsFireVulnerable()) entity.setOnFireFor(5);
			entity.damage(HavenDamageSource.Injected("nether_royalty_blood", user), 2);
		}
	});
	public static final Item PHANTOM_BLOOD_SYRINGE = new BloodSyringeItem(BloodType.PHANTOM);
	public static final Item PIG_BLOOD_SYRINGE = new BloodSyringeItem(BloodType.PIG);
	public static final Item RABBIT_BLOOD_SYRINGE = new BloodSyringeItem(BloodType.RABBIT);
	public static final Item RAVAGER_BLOOD_SYRINGE = new BloodSyringeItem(BloodType.RAVAGER);
	public static final Item SHEEP_BLOOD_SYRINGE = new BloodSyringeItem(BloodType.SHEEP, (PlayerEntity user, LivingEntity entity) -> {
		BloodType bloodType = BloodType.Get(entity);
		if (bloodType == BloodType.SHEEP || bloodType == BloodType.GOAT) BloodSyringeItem.heal(entity, 1);
		else entity.damage(HavenDamageSource.Injected("sheep_blood", user), 1);
	});
	public static final Item SLIME_SYRINGE = new BloodSyringeItem(BloodType.SLIME, (PlayerEntity user, LivingEntity entity) -> {
		BloodType bloodType = BloodType.Get(entity);
		if (bloodType == BloodType.SLUDGE || bloodType == BloodType.MAGMA) entity.heal(1);
		else {
			entity.damage(HavenDamageSource.Injected("slime", user), 1);
			//TODO: Being injected with slime makes you sticky
		}
	});
	public static final Item SLUDGE_SYRINGE = new BloodSyringeItem(BloodType.SLUDGE, (PlayerEntity user, LivingEntity entity) -> {
		BloodType bloodType = BloodType.Get(entity);
		if (bloodType == BloodType.SLUDGE) entity.heal(1);
		else {
			user.damage(HavenDamageSource.Injected("sludge", user), 4);
			if (bloodType.IsPoisonVulnerable()) user.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 200, 1));
			if (bloodType.IsWitherVulnerable()) user.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 200, 1));
			//TODO: Sludge makes you sticky if you miraculously survive the withering and poison
		}
	});
	public static final Item SPIDER_BLOOD_SYRINGE = new BloodSyringeItem(BloodType.SPIDER);
	public static final Item SQUID_BLOOD_SYRINGE = new BloodSyringeItem(BloodType.SQUID);
	public static final Item STRIDER_BLOOD_SYRINGE = new BloodSyringeItem(BloodType.STRIDER);
	public static final Item SUGAR_WATER_SYRINGE = new BloodSyringeItem(BloodType.SUGAR_WATER, (PlayerEntity user, LivingEntity entity) -> {
		BloodType bloodType = BloodType.Get(entity);
		//Put out fires
		if (entity.isOnFire()) entity.setOnFire(false);
		//Heal or hurt
		if (bloodType == BloodType.SUGAR_WATER || bloodType == BloodType.WATER) entity.heal(1);
		else if (bloodType != BloodType.MUD) {
			//Hurt water-vulnerable
			if (bloodType.IsWaterVulnerable()) entity.damage(HavenDamageSource.Injected("sugar_water", user), 4);
			else entity.damage(HavenDamageSource.Injected("sugar_water", user), 1);
		}
	});
	public static final Item TURTLE_BLOOD_SYRINGE = new BloodSyringeItem(BloodType.TURTLE);
	public static final Item VAMPIRE_BLOOD_SYRINGE = new BloodSyringeItem(BloodType.VAMPIRE, (PlayerEntity user, LivingEntity entity) -> {
		BloodType bloodType = BloodType.Get(entity);
		if (bloodType == BloodType.VAMPIRE) entity.heal(1);
		else {
			if (bloodType.IsWitherVulnerable()) user.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 100, 1));
			entity.damage(HavenDamageSource.Injected(user, bloodType), 1);
		}
	});
	public static final Item VEX_BLOOD_SYRINGE = new BloodSyringeItem(BloodType.VEX);
	public static final Item VILLAGER_BLOOD_SYRINGE = new BloodSyringeItem(BloodType.VILLAGER);
	public static final Item WARDEN_BLOOD_SYRINGE = new BloodSyringeItem(BloodType.WARDEN);
	public static final Item WATER_SYRINGE = new BloodSyringeItem(BloodType.WATER, (PlayerEntity user, LivingEntity entity) -> {
		BloodType bloodType = BloodType.Get(entity);
		//Put out fires
		if (entity.isOnFire()) entity.setOnFire(false);
		//Heal or Hurt
		if (bloodType == BloodType.WATER) entity.heal(1);
		else if (bloodType != BloodType.SUGAR_WATER && bloodType != BloodType.MUD) {
			//Hurt water-vulnerable
			if (bloodType.IsWaterVulnerable()) entity.damage(HavenDamageSource.Injected("water", user), 4);
			else entity.damage(HavenDamageSource.Injected("water", user), 1);
		}
	});
	public static final Item ZOMBIE_BLOOD_SYRINGE = new BloodSyringeItem(BloodType.ZOMBIE, (PlayerEntity user, LivingEntity entity) -> {
		BloodType bloodType = BloodType.Get(entity);
		if (bloodType == BloodType.ZOMBIE) entity.heal(1);
		else {
			if(bloodType == BloodType.PLAYER) entity.heal(1);
			else entity.damage(HavenDamageSource.Injected(user, bloodType), 1);
			if (bloodType.IsPoisonVulnerable()) entity.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 100, 1));
		}
	});

	public static final Item CONFETTI_SYRINGE = new BaseSyringeItem((PlayerEntity user, LivingEntity entity) -> {
		BloodType bloodType = BloodType.Get(entity);
		if (bloodType == BloodType.SUGAR_WATER) {
			if (user == entity) BloodSyringeItem.heal(entity, 4);
			else entity.damage(HavenDamageSource.Injected("confetti_as_clown", user), 4);
		}
		else entity.damage(HavenDamageSource.Injected("confetti", user), 1);
	});
	public static final Item DRAGON_BREATH_SYRINGE = new BaseSyringeItem((PlayerEntity user, LivingEntity entity) -> {
		BloodType bloodType = BloodType.Get(entity);
		if (bloodType == BloodType.DRAGON) BloodSyringeItem.heal(entity, 4);
		else entity.damage(HavenDamageSource.Injected("dragon_breath", user), 4);
	});
	public static final Item EXPERIENCE_SYRINGE = new ExperienceSyringeItem();
	public static final Item CHOCOLATE_MILK_SYRINGE = new BaseSyringeItem(HavenMod::ApplyMilkSyringe);
	public static final Item COFFEE_MILK_SYRINGE = new BaseSyringeItem((user, entity) -> ApplyMilkSyringe(user, entity, true));
	public static final Item STRAWBERRY_MILK_SYRINGE = new BaseSyringeItem(HavenMod::ApplyMilkSyringe);

	public static final StatusEffect DETERIORATION_EFFECT = new DeteriorationEffect();
	public static final Item SECRET_INGREDIENT = new Item(BloodItemSettings());
	public static final Item SYRINGE = new EmptySyringeItem(BloodItemSettings().maxCount(16));
	public static final Item DIRTY_SYRINGE = new Item(BloodItemSettings());
	public static final Item SYRINGE_BLINDNESS = new SyringeItem(new StatusEffectInstance(StatusEffects.BLINDNESS, 600, 4, true, false));
	public static final Item SYRINGE_MINING_FATIGUE = new SyringeItem(new StatusEffectInstance(StatusEffects.MINING_FATIGUE, (600), 4, true, false));
	public static final Item SYRINGE_POISON = new SyringeItem(new StatusEffectInstance(StatusEffects.POISON, 600, 4, true, false));
	public static final Item SYRINGE_REGENERATION = new SyringeItem(new StatusEffectInstance(StatusEffects.REGENERATION, 600, 4, true, false));
	public static final Item SYRINGE_SATURATION = new SyringeItem(new StatusEffectInstance(StatusEffects.SATURATION, 600, 4, true, false));
	public static final Item SYRINGE_SLOWNESS = new SyringeItem(new StatusEffectInstance(StatusEffects.SLOWNESS, 600, 4, true, false));
	public static final Item SYRINGE_WEAKNESS = new SyringeItem(new StatusEffectInstance(StatusEffects.WEAKNESS, 600, 4, true, false));
	public static final Item SYRINGE_WITHER = new SyringeItem(new StatusEffectInstance(StatusEffects.WITHER, 600, 4, true, false));
	public static final Item SYRINGE_EXP1 = new SyringeItem(new StatusEffectInstance(DETERIORATION_EFFECT, 600, 4, true, false));
	public static final Item SYRINGE_EXP2 = new SyringeItem(new StatusEffectInstance(DETERIORATION_EFFECT, 600, 4, true, false));
	public static final Item SYRINGE_EXP3 = new SyringeItem(new StatusEffectInstance(DETERIORATION_EFFECT, 600, 4, true, false));
	
	//Angel Bat
	public static final EntityType<AngelBatEntity> ANGEL_BAT_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, AngelBatEntity::new).dimensions(EntityDimensions.fixed(0.5F, 0.9F)).trackRangeBlocks(5).build();

	//Chicken Variants
	public static final EntityType<FancyChickenEntity> FANCY_CHICKEN_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, FancyChickenEntity::new).dimensions(EntityDimensions.fixed(0.4F, 0.7F)).trackRangeBlocks(10).build();
	public static final Item FANCY_CHICKEN_SPAWN_EGG = new SpawnEggItem(FANCY_CHICKEN_ENTITY, 16777215, 16777215, ItemSettings());
	public static final Item FANCY_FEATHER = new Item(ItemSettings());
	//Cow Variants
	public static final EntityType<CowcoaEntity> COWCOA_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, CowcoaEntity::new).dimensions(EntityDimensions.fixed(0.9F, 1.4F)).trackRangeBlocks(10).build();
	public static final Item COACOW_SPAWN_EGG = new SpawnEggItem(COWCOA_ENTITY, 16777215, 16777215, ItemSettings());
	public static final EntityType<CowfeeEntity> COWFEE_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, CowfeeEntity::new).dimensions(EntityDimensions.fixed(0.9F, 1.4F)).trackRangeBlocks(10).build();
	public static final Item COWFEE_SPAWN_EGG = new SpawnEggItem(COWFEE_ENTITY, 16777215, 16777215, ItemSettings());
	public static final EntityType<StrawbovineEntity> STRAWBOVINE_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, StrawbovineEntity::new).dimensions(EntityDimensions.fixed(0.9F, 1.4F)).trackRangeBlocks(10).build();
	public static final Item STRAWBOVINE_SPAWN_EGG = new SpawnEggItem(STRAWBOVINE_ENTITY, 16777215, 16777215, ItemSettings());
	public static final EntityType<MoobloomEntity> MOOBLOOM_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, MoobloomEntity::new).dimensions(EntityDimensions.fixed(0.9F, 1.4F)).trackRangeBlocks(10).build();
	public static final Item MOOBLOOM_SPAWN_EGG = new SpawnEggItem(MOOBLOOM_ENTITY, 16777215, 16777215, ItemSettings());
	public static final EntityType<MoolipEntity> MOOLIP_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, MoolipEntity::new).dimensions(EntityDimensions.fixed(0.9F, 1.4F)).trackRangeBlocks(10).build();
	public static final Item MOOLIP_SPAWN_EGG = new SpawnEggItem(MOOLIP_ENTITY, 16777215, 16777215, ItemSettings());
	public static final EntityType<MooblossomEntity> MOOBLOSSOM_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, MooblossomEntity::new).dimensions(EntityDimensions.fixed(0.9F, 1.4F)).trackRangeBlocks(10).build();
	public static final Item MOOBLOSSOM_SPAWN_EGG = new SpawnEggItem(MOOBLOSSOM_ENTITY, 16777215, 16777215, ItemSettings());
	public static final Block MAGENTA_MOOBLOSSOM_TULIP = new FlowerBlock(StatusEffects.FIRE_RESISTANCE, 4, FlowerContainer.Settings());
	public static final EntityType<OrangeMooblossomEntity> ORANGE_MOOBLOSSOM_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, OrangeMooblossomEntity::new).dimensions(EntityDimensions.fixed(0.9F, 1.4F)).trackRangeBlocks(10).build();
	public static final Item ORANGE_MOOBLOSSOM_SPAWN_EGG = new SpawnEggItem(ORANGE_MOOBLOSSOM_ENTITY, 16777215, 16777215, ItemSettings());
	public static final Block ORANGE_MOOBLOSSOM_TULIP = new FlowerBlock(StatusEffects.FIRE_RESISTANCE, 4, FlowerContainer.Settings());
	public static final EntityType<PinkMooblossomEntity> PINK_MOOBLOSSOM_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, PinkMooblossomEntity::new).dimensions(EntityDimensions.fixed(0.9F, 1.4F)).trackRangeBlocks(10).build();
	public static final Item PINK_MOOBLOSSOM_SPAWN_EGG = new SpawnEggItem(PINK_MOOBLOSSOM_ENTITY, 16777215, 16777215, ItemSettings());
	public static final Block PINK_MOOBLOSSOM_TULIP = new FlowerBlock(StatusEffects.FIRE_RESISTANCE, 4, FlowerContainer.Settings());
	public static final EntityType<RedMooblossomEntity> RED_MOOBLOSSOM_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, RedMooblossomEntity::new).dimensions(EntityDimensions.fixed(0.9F, 1.4F)).trackRangeBlocks(10).build();
	public static final Item RED_MOOBLOSSOM_SPAWN_EGG = new SpawnEggItem(RED_MOOBLOSSOM_ENTITY, 16777215, 16777215, ItemSettings());
	public static final Block RED_MOOBLOSSOM_TULIP = new FlowerBlock(StatusEffects.FIRE_RESISTANCE, 4, FlowerContainer.Settings());
	public static final EntityType<WhiteMooblossomEntity> WHITE_MOOBLOSSOM_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, WhiteMooblossomEntity::new).dimensions(EntityDimensions.fixed(0.9F, 1.4F)).trackRangeBlocks(10).build();
	public static final Item WHITE_MOOBLOSSOM_SPAWN_EGG = new SpawnEggItem(WHITE_MOOBLOSSOM_ENTITY, 16777215, 16777215, ItemSettings());
	public static final Block WHITE_MOOBLOSSOM_TULIP = new FlowerBlock(StatusEffects.FIRE_RESISTANCE, 4, FlowerContainer.Settings());

	//Nether Mooshrooms
	public static final EntityType<GildedMooshroomEntity> GILDED_MOOSHROOM_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, GildedMooshroomEntity::new).dimensions(EntityDimensions.fixed(0.9F, 1.4F)).trackRangeBlocks(10).build();
	public static final Item GILDED_MOOSHROOM_SPAWN_EGG = new SpawnEggItem(GILDED_MOOSHROOM_ENTITY, 16777215, 16777215, ItemSettings());
	public static final EntityType<CrimsonMooshroomEntity> CRIMSON_MOOSHROOM_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, CrimsonMooshroomEntity::new).dimensions(EntityDimensions.fixed(0.9F, 1.4F)).trackRangeBlocks(10).build();
	public static final Item CRIMSON_MOOSHROOM_SPAWN_EGG = new SpawnEggItem(CRIMSON_MOOSHROOM_ENTITY, 16777215, 16777215, ItemSettings());
	public static final EntityType<WarpedMooshroomEntity> WARPED_MOOSHROOM_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, WarpedMooshroomEntity::new).dimensions(EntityDimensions.fixed(0.9F, 1.4F)).trackRangeBlocks(10).build();
	public static final Item WARPED_MOOSHROOM_SPAWN_EGG = new SpawnEggItem(WARPED_MOOSHROOM_ENTITY, 16777215, 16777215, ItemSettings());

	//Flavored Milk
	public static final Item MILK_BOWL = new MilkBowlItem(ItemSettings().recipeRemainder(Items.BOWL).maxCount(16));
	public static final Item CHOCOLATE_MILK_BUCKET = new HavenMilkBucketItem(BucketProvider.DEFAULT_PROVIDER.FilledBucketSettings(), BucketProvider.DEFAULT_PROVIDER);
	public static final Item CHOCOLATE_MILK_BOWL = new MilkBowlItem(ItemSettings().recipeRemainder(Items.BOWL).maxCount(16));
	public static final Item CHOCOLATE_MILK_BOTTLE = new MilkBottleItem(ItemSettings().recipeRemainder(Items.GLASS_BOTTLE).maxCount(16));
	public static final Item STRAWBERRY_MILK_BUCKET = new HavenMilkBucketItem(BucketProvider.DEFAULT_PROVIDER.FilledBucketSettings(), BucketProvider.DEFAULT_PROVIDER);
	public static final Item STRAWBERRY_MILK_BOWL = new MilkBowlItem(ItemSettings().recipeRemainder(Items.BOWL).maxCount(16));
	public static final Item STRAWBERRY_MILK_BOTTLE = new MilkBottleItem(ItemSettings().recipeRemainder(Items.GLASS_BOTTLE).maxCount(16));
	public static final Item COFFEE_MILK_BUCKET = new CoffeeMilkBucketItem(BucketProvider.DEFAULT_PROVIDER.FilledBucketSettings(), BucketProvider.DEFAULT_PROVIDER);
	public static final Item COFFEE_MILK_BOWL = new CoffeeMilkBowlItem(ItemSettings().recipeRemainder(Items.BOWL).maxCount(16));
	public static final Item COFFEE_MILK_BOTTLE = new CoffeeMilkBottleItem(ItemSettings().recipeRemainder(Items.GLASS_BOTTLE).maxCount(16));
	//Cheese
	public static final FoodComponent COTTAGE_CHEESE_FOOD_COMPONENT = new FoodComponent.Builder().hunger(2).saturationModifier(0.6F).build();
	public static final Block MILK_CAULDRON = new MilkCauldronBlock(0, AbstractBlock.Settings.copy(Blocks.WATER_CAULDRON));
	public static final Block COTTAGE_CHEESE_CAULDRON = new MilkCauldronBlock(1, AbstractBlock.Settings.copy(MILK_CAULDRON));
	public static final Block CHEESE_CAULDRON = new MilkCauldronBlock(2, AbstractBlock.Settings.copy(MILK_CAULDRON));
	public static final Block COTTAGE_CHEESE_BLOCK = new CottageCheeseBlock(AbstractBlock.Settings.of(Material.SOLID_ORGANIC, MapColor.OFF_WHITE).strength(1.0F).sounds(BlockSoundGroup.WART_BLOCK));
	public static final Item COTTAGE_CHEESE_BUCKET = new CottageCheeseBucketItem(COTTAGE_CHEESE_BLOCK, BucketProvider.DEFAULT_PROVIDER.FilledBucketSettings().food(COTTAGE_CHEESE_FOOD_COMPONENT), BucketProvider.DEFAULT_PROVIDER);
	public static final Item COTTAGE_CHEESE_BOWL = new MushroomStewItem(ItemSettings().maxCount(1).recipeRemainder(Items.BOWL).food(COTTAGE_CHEESE_FOOD_COMPONENT));
	public static final BlockContainer CHEESE_BLOCK = new BlockContainer(new Block(AbstractBlock.Settings.of(Material.SOLID_ORGANIC, MapColor.YELLOW).strength(1.0F).sounds(BlockSoundGroup.WART_BLOCK)));
	public static final Item CHEESE = new Item(ItemSettings().food(new FoodComponent.Builder().hunger(2).saturationModifier(0.8F).build()));
	public static final Item GRILLED_CHEESE = new Item(ItemSettings().food(new FoodComponent.Builder().hunger(8).saturationModifier(0.8F).build()));
	//Wood Buckets
	public static final LiteralWoodMaterial WOOD_MATERIAL = new LiteralWoodMaterial();
	//Cakes
	public static final CakeContainer CHOCOLATE_CAKE = new CakeContainer(CakeContainer.Flavor.CHOCOLATE);
	public static final CakeContainer STRAWBERRY_CAKE = new CakeContainer(CakeContainer.Flavor.STRAWBERRY);
	public static final CakeContainer COFFEE_CAKE = new CakeContainer(CakeContainer.Flavor.COFFEE);
	public static final CakeContainer CARROT_CAKE = new CakeContainer(CakeContainer.Flavor.CARROT);
	public static final CakeContainer CONFETTI_CAKE = new CakeContainer(CakeContainer.Flavor.CONFETTI);
	public static final CakeContainer CHORUS_CAKE = new CakeContainer(CakeContainer.Flavor.CHORUS);
	//Grappling Rod
	public static final Item GRAPPLING_ROD = new GrapplingRodItem(ItemSettings().maxDamage(256));

	//Bottled Confetti & Dragon's Breath
	public static final Item BOTTLED_CONFETTI_ITEM = new BottledConfettiItem(ItemSettings().recipeRemainder(Items.GLASS_BOTTLE));
	public static final EntityType<BottledConfettiEntity> BOTTLED_CONFETTI_ENTITY = FabricEntityTypeBuilder.<BottledConfettiEntity>create(SpawnGroup.MISC, BottledConfettiEntity::new).dimensions(EntityDimensions.fixed(0.25F, 0.25F)).trackRangeBlocks(4).trackedUpdateRate(10).build();
	public static final EntityType<DroppedConfettiEntity> DROPPED_CONFETTI_ENTITY = FabricEntityTypeBuilder.<DroppedConfettiEntity>create(SpawnGroup.MISC, DroppedConfettiEntity::new).dimensions(EntityDimensions.fixed(0.25F, 0.25F)).trackRangeBlocks(4).trackedUpdateRate(10).build();
	public static final EntityType<ConfettiCloudEntity> CONFETTI_CLOUD_ENTITY = FabricEntityTypeBuilder.<ConfettiCloudEntity>create(SpawnGroup.MISC, ConfettiCloudEntity::new).build();
	public static final EntityType<DroppedDragonBreathEntity> DROPPED_DRAGON_BREATH_ENTITY = FabricEntityTypeBuilder.<DroppedDragonBreathEntity>create(SpawnGroup.MISC, DroppedDragonBreathEntity::new).dimensions(EntityDimensions.fixed(0.25F, 0.25F)).trackRangeBlocks(4).trackedUpdateRate(10).build();
	public static final EntityType<ConfettiCloudEntity> DRAGON_BREATH_CLOUD_ENTITY = FabricEntityTypeBuilder.<ConfettiCloudEntity>create(SpawnGroup.MISC, ConfettiCloudEntity::new).build();

	//Boats
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
	public static final Set<BaseMaterial> MATERIALS = Set.<BaseMaterial>of(
		//Wood
		CASSIA_MATERIAL, CHERRY_MATERIAL, BAMBOO_MATERIAL, DRIED_BAMBOO_MATERIAL, CHARRED_MATERIAL, MANGROVE_MATERIAL,
		//Vanilla Wood
		ACACIA_MATERIAL, BIRCH_MATERIAL, DARK_OAK_MATERIAL, JUNGLE_MATERIAL, /*OAK_MATERIAL,*/ SPRUCE_MATERIAL, CRIMSON_MATERIAL, WARPED_MATERIAL,
		//Nether Wood
		GILDED_MATERIAL,
		//Metal
		COPPER_MATERIAL, IRON_MATERIAL, DARK_IRON_MATERIAL, GOLD_MATERIAL, NETHERITE_MATERIAL,
		//Gem
		AMETHYST_MATERIAL, EMERALD_MATERIAL, DIAMOND_MATERIAL, QUARTZ_MATERIAL,
		//Obsidian
		OBSIDIAN_MATERIAL, CRYING_OBSIDIAN_MATERIAL, BLEEDING_OBSIDIAN_MATERIAL,
		//Stone
		DRIPSTONE_MATERIAL, TUFF_MATERIAL,
		//Blood
		BLOOD_MATERIAL, DRIED_BLOOD_MATERIAL,
		//Misc
		BONE_MATERIAL, STUDDED_LEATHER_MATERIAL, WOOD_MATERIAL, MUD_MATERIAL
	);
	public static BiMap<Block, Block> UNLIT_LANTERNS = HashBiMap.create();
	public static final List<SignType> SIGN_TYPES = new ArrayList<SignType>();

	public static final Set<FlowerContainer> FLOWERS = new HashSet<FlowerContainer>(Set.<FlowerContainer>of(
		BUTTERCUP, PINK_DAISY, ROSE, BLUE_ROSE, MAGENTA_TULIP, MARIGOLD,
		PINK_ALLIUM, LAVENDER, HYDRANGEA, PAEONIA
	));
	public static final Set<BlockContainer> TALL_FLOWERS = new HashSet<BlockContainer>(Set.<BlockContainer>of(
		AMARANTH, TALL_ALLIUM, TALL_PINK_ALLIUM
	));

	public static final Set<StatusEffect> MILK_IMMUNE_EFFECTS = new HashSet<StatusEffect>(Set.<StatusEffect>of(
		DETERIORATION_EFFECT, BLEEDING_EFFECT, BONE_ROT_EFFECT
	));

	public static final Set<BedContainer> BEDS = new HashSet<BedContainer>(Set.<BedContainer>of( RAINBOW_BED ));
	public static final Set<Block> CAMPFIRES = new HashSet<Block>();

	static {
		//Amber Fungus
		GILDED_FUNGUS_CONFIG = new HugeFungusFeatureConfig(GILDED_NYLIUM.BLOCK.getDefaultState(), GILDED_MATERIAL.getStem().BLOCK.getDefaultState(), GILDED_MATERIAL.getWartBlock().BLOCK.getDefaultState(), Blocks.SHROOMLIGHT.getDefaultState(), true);
		GILDED_FUNGUS_NOT_PLANTED_CONFIG = new HugeFungusFeatureConfig(GILDED_FUNGUS_CONFIG.validBaseBlock, GILDED_FUNGUS_CONFIG.stemState, GILDED_FUNGUS_CONFIG.hatState, GILDED_FUNGUS_CONFIG.decorationState, false);
		GILDED_FOREST_VEGETATION = Feature.NETHER_FOREST_VEGETATION.configure(GILDED_ROOTS_CONFIG).decorate(Decorator.COUNT_MULTILAYER.configure(new CountConfig(6)));
		PATCH_GILDED_ROOTS = Feature.RANDOM_PATCH.configure((new RandomPatchFeatureConfig.Builder(new SimpleBlockStateProvider(GILDED_ROOTS.BLOCK.getDefaultState()), new SimpleBlockPlacer())).tries(64).cannotProject().build()).range(new RangeDecoratorConfig(UniformHeightProvider.create(YOffset.getBottom(), YOffset.getTop())));
		GILDED_FUNGI = Feature.HUGE_FUNGUS.configure(GILDED_FUNGUS_NOT_PLANTED_CONFIG).decorate(Decorator.COUNT_MULTILAYER.configure(new CountConfig(8)));
		GILDED_FUNGI_PLANTED = Feature.HUGE_FUNGUS.configure(GILDED_FUNGUS_CONFIG);
		//Anchors
		for(Integer owner : ANCHOR_MAP.keySet()) {
			ANCHOR_CORES.put(owner, new AnchorCoreItem(owner));
		}
		UNLIT_LANTERNS.put(Blocks.LANTERN, UNLIT_LANTERN);
		UNLIT_LANTERNS.put(Blocks.SOUL_LANTERN, UNLIT_SOUL_LANTERN);
		//Materials
		for(BaseMaterial material : MATERIALS) {
			if (material instanceof SignProvider sign) SIGN_TYPES.add(sign.getSign().TYPE);
			if (material instanceof LanternProvider lantern) UNLIT_LANTERNS.put(lantern.getLantern().BLOCK, lantern.getUnlitLantern());
			if (material instanceof SoulLanternProvider soulLantern) UNLIT_LANTERNS.put(soulLantern.getSoulLantern().BLOCK, soulLantern.getUnlitSoulLantern());
			if (material instanceof CampfireProvider campfire) CAMPFIRES.add(campfire.getCampfire().BLOCK);
			if (material instanceof SoulCampfireProvider soulCampfire) CAMPFIRES.add(soulCampfire.getSoulCampfire().BLOCK);
		}
		//Flowers
		for(DyeColor color : COLORS) FLOWERS.add(CARNATIONS.get(color));
		//Cauldrons
		BLOOD_CAULDRON = new BloodCauldronBlock(FabricBlockSettings.copyOf(Blocks.CAULDRON));
		MUD_CAULDRON = new MudCauldronBlock(FabricBlockSettings.copyOf(Blocks.CAULDRON));
	}
}
