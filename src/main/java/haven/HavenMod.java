package haven;

import haven.blocks.*;
import haven.blocks.anchors.AnchorBlock;
import haven.blocks.anchors.SubstituteAnchorBlock;
import haven.blocks.basic.*;
import haven.blood.*;
import haven.blocks.cake.CakeFlavor;
import haven.blocks.cake.HavenCakeBlock;
import haven.blocks.cake.HavenCandleCakeBlock;
import haven.blocks.mud.MudBlock;
import haven.blocks.mud.MudCauldronBlock;
import haven.blocks.mud.MudFluid;
import haven.blocks.mud.MudFluidBlock;
import haven.blocks.oxidizable.*;
import haven.boats.*;
import haven.damage.HavenDamageSource;
import haven.effects.*;
import haven.entities.*;
import haven.entities.anchors.AnchorBlockEntity;
import haven.entities.cloud.ConfettiCloudEntity;
import haven.entities.passive.*;
import haven.entities.projectiles.*;
import haven.entities.tnt.SoftTntEntity;
import haven.entities.anchors.SubstituteAnchorBlockEntity;
import haven.features.*;
import haven.items.*;
import haven.items.buckets.CopperPowderSnowBucketItem;
import haven.items.consumable.*;
import haven.items.buckets.CopperBucketItem;
import haven.items.buckets.WoodBucketItem;
import haven.items.consumable.WoodCottageCheeseBucketItem;
import haven.items.buckets.WoodPowderSnowBucketItem;
import haven.items.consumable.milk.*;
import haven.items.mud.MudBucketItem;
import haven.items.throwable.BottledConfettiItem;
import haven.items.throwable.ThrowableTomatoItem;
import haven.materials.base.BaseMaterial;
import haven.materials.wood.BaseTreeMaterial;
import haven.materials.gem.AmethystMaterial;
import haven.materials.gem.DiamondMaterial;
import haven.materials.gem.EmeraldMaterial;
import haven.materials.metal.*;
import haven.materials.providers.*;
import haven.boats.BoatMaterial;
import haven.materials.wood.BambooMaterial;
import haven.materials.wood.MangroveMaterial;
import haven.materials.wood.TreeMaterial;
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
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.fluid.*;
import net.minecraft.item.*;
import net.minecraft.particle.*;
import net.minecraft.sound.*;
import net.minecraft.util.*;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.*;
import net.minecraft.world.BlockView;
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
	public static final ItemGroup ITEM_GROUP = FabricItemGroupBuilder.build(HavenMod.ID("general"), () -> new ItemStack(ANCHOR_BLOCK));
	public static Item.Settings ItemSettings() {
		return new Item.Settings().group(ITEM_GROUP);
	}
	public static final HavenPair ANCHOR = new HavenPair(ANCHOR_BLOCK);
	public static final BlockEntityType<AnchorBlockEntity> ANCHOR_BLOCK_ENTITY = FabricBlockEntityTypeBuilder.create(AnchorBlockEntity::new, ANCHOR.BLOCK).build(null);
	public static final Block SUBSTITUTE_ANCHOR_BLOCK = new SubstituteAnchorBlock(FabricBlockSettings.of(Material.SOIL).hardness(1f));
	public static final BlockEntityType<SubstituteAnchorBlockEntity> SUBSTITUTE_ANCHOR_BLOCK_ENTITY = FabricBlockEntityTypeBuilder.create(SubstituteAnchorBlockEntity::new, SUBSTITUTE_ANCHOR_BLOCK).build(null);

	public static ToIntFunction<BlockState> luminance(int value) { return (state) -> value; }

	//More Torches
	public static final HavenTorch BONE_TORCH = new HavenTorch(FabricBlockSettings.of(Material.DECORATION).noCollision().breakInstantly().nonOpaque().luminance(luminance(14)).sounds(BlockSoundGroup.BONE), ParticleTypes.FLAME);
	//Metal Torches
	public static final DefaultParticleType COPPER_FLAME = FabricParticleTypes.simple(false);
	public static final HavenTorch COPPER_TORCH = HavenTorch.Oxidizable(Oxidizable.OxidizationLevel.UNAFFECTED, FabricBlockSettings.of(Material.DECORATION).noCollision().breakInstantly().nonOpaque().luminance(luminance(12)).sounds(BlockSoundGroup.COPPER), COPPER_FLAME);
	public static final HavenTorch EXPOSED_COPPER_TORCH = HavenTorch.Oxidizable(Oxidizable.OxidizationLevel.EXPOSED, FabricBlockSettings.copy(COPPER_TORCH.BLOCK), COPPER_FLAME);
	public static final HavenTorch WEATHERED_COPPER_TORCH = HavenTorch.Oxidizable(Oxidizable.OxidizationLevel.WEATHERED, FabricBlockSettings.copy(COPPER_TORCH.BLOCK), COPPER_FLAME);
	public static final HavenTorch OXIDIZED_COPPER_TORCH = HavenTorch.Oxidizable(Oxidizable.OxidizationLevel.OXIDIZED, FabricBlockSettings.copy(COPPER_TORCH.BLOCK), COPPER_FLAME);
	public static final WaxedTorch WAXED_COPPER_TORCH = new WaxedTorch(COPPER_TORCH, FabricBlockSettings.copy(COPPER_TORCH.BLOCK), COPPER_FLAME);
	public static final WaxedTorch WAXED_EXPOSED_COPPER_TORCH = new WaxedTorch(EXPOSED_COPPER_TORCH, FabricBlockSettings.copy(WAXED_COPPER_TORCH.BLOCK), COPPER_FLAME);
	public static final WaxedTorch WAXED_WEATHERED_COPPER_TORCH = new WaxedTorch(WEATHERED_COPPER_TORCH, FabricBlockSettings.copy(WAXED_COPPER_TORCH.BLOCK), COPPER_FLAME);
	public static final WaxedTorch WAXED_OXIDIZED_COPPER_TORCH = new WaxedTorch(OXIDIZED_COPPER_TORCH, FabricBlockSettings.copy(WAXED_COPPER_TORCH.BLOCK), COPPER_FLAME);
	public static final HavenTorch COPPER_SOUL_TORCH = HavenTorch.Oxidizable(Oxidizable.OxidizationLevel.UNAFFECTED, FabricBlockSettings.of(Material.DECORATION).noCollision().breakInstantly().nonOpaque().luminance(luminance(10)).sounds(BlockSoundGroup.COPPER), ParticleTypes.SOUL_FIRE_FLAME);
	public static final HavenTorch EXPOSED_COPPER_SOUL_TORCH = HavenTorch.Oxidizable(Oxidizable.OxidizationLevel.EXPOSED, FabricBlockSettings.copy(COPPER_SOUL_TORCH.BLOCK), ParticleTypes.SOUL_FIRE_FLAME);
	public static final HavenTorch WEATHERED_COPPER_SOUL_TORCH = HavenTorch.Oxidizable(Oxidizable.OxidizationLevel.WEATHERED, FabricBlockSettings.copy(COPPER_SOUL_TORCH.BLOCK), ParticleTypes.SOUL_FIRE_FLAME);
	public static final HavenTorch OXIDIZED_COPPER_SOUL_TORCH = HavenTorch.Oxidizable(Oxidizable.OxidizationLevel.OXIDIZED, FabricBlockSettings.copy(COPPER_SOUL_TORCH.BLOCK), ParticleTypes.SOUL_FIRE_FLAME);
	public static final WaxedTorch WAXED_COPPER_SOUL_TORCH = new WaxedTorch(COPPER_SOUL_TORCH, FabricBlockSettings.copy(COPPER_SOUL_TORCH.BLOCK), ParticleTypes.SOUL_FIRE_FLAME);
	public static final WaxedTorch WAXED_EXPOSED_COPPER_SOUL_TORCH = new WaxedTorch(EXPOSED_COPPER_SOUL_TORCH, FabricBlockSettings.copy(WAXED_COPPER_SOUL_TORCH.BLOCK), ParticleTypes.SOUL_FIRE_FLAME);
	public static final WaxedTorch WAXED_WEATHERED_COPPER_SOUL_TORCH = new WaxedTorch(WEATHERED_COPPER_SOUL_TORCH, FabricBlockSettings.copy(WAXED_COPPER_SOUL_TORCH.BLOCK), ParticleTypes.SOUL_FIRE_FLAME);
	public static final WaxedTorch WAXED_OXIDIZED_COPPER_SOUL_TORCH = new WaxedTorch(OXIDIZED_COPPER_SOUL_TORCH, FabricBlockSettings.copy(WAXED_COPPER_SOUL_TORCH.BLOCK), ParticleTypes.SOUL_FIRE_FLAME);

	//More Copper
	public static final CopperMaterial COPPER_MATERIAL = new CopperMaterial();
	public static final HavenPair MEDIUM_WEIGHTED_PRESSURE_PLATE = new HavenPair(new OxidizableWeightedPressurePlateBlock(Oxidizable.OxidizationLevel.UNAFFECTED, 75, AbstractBlock.Settings.of(Material.METAL).requiresTool().noCollision().strength(0.5F).sounds(BlockSoundGroup.WOOD)));
	public static final HavenPair EXPOSED_MEDIUM_WEIGHTED_PRESSURE_PLATE = new HavenPair(new OxidizableWeightedPressurePlateBlock(Oxidizable.OxidizationLevel.EXPOSED, 75, AbstractBlock.Settings.copy(MEDIUM_WEIGHTED_PRESSURE_PLATE.BLOCK)));
	public static final HavenPair WEATHERED_MEDIUM_WEIGHTED_PRESSURE_PLATE = new HavenPair(new OxidizableWeightedPressurePlateBlock(Oxidizable.OxidizationLevel.WEATHERED, 75, AbstractBlock.Settings.copy(MEDIUM_WEIGHTED_PRESSURE_PLATE.BLOCK)));
	public static final HavenPair OXIDIZED_MEDIUM_WEIGHTED_PRESSURE_PLATE = new HavenPair(new OxidizableWeightedPressurePlateBlock(Oxidizable.OxidizationLevel.OXIDIZED, 75, AbstractBlock.Settings.copy(MEDIUM_WEIGHTED_PRESSURE_PLATE.BLOCK)));
	public static final WaxedPair WAXED_MEDIUM_WEIGHTED_PRESSURE_PLATE = new WaxedPair(MEDIUM_WEIGHTED_PRESSURE_PLATE, new HavenWeightedPressurePlateBlock(75, AbstractBlock.Settings.copy(MEDIUM_WEIGHTED_PRESSURE_PLATE.BLOCK)));
	public static final WaxedPair WAXED_EXPOSED_MEDIUM_WEIGHTED_PRESSURE_PLATE = new WaxedPair(EXPOSED_MEDIUM_WEIGHTED_PRESSURE_PLATE, new HavenWeightedPressurePlateBlock(75, AbstractBlock.Settings.copy(WAXED_MEDIUM_WEIGHTED_PRESSURE_PLATE.BLOCK)));
	public static final WaxedPair WAXED_WEATHERED_MEDIUM_WEIGHTED_PRESSURE_PLATE = new WaxedPair(WEATHERED_MEDIUM_WEIGHTED_PRESSURE_PLATE, new HavenWeightedPressurePlateBlock(75, AbstractBlock.Settings.copy(WAXED_MEDIUM_WEIGHTED_PRESSURE_PLATE.BLOCK)));
	public static final WaxedPair WAXED_OXIDIZED_MEDIUM_WEIGHTED_PRESSURE_PLATE = new WaxedPair(OXIDIZED_MEDIUM_WEIGHTED_PRESSURE_PLATE, new HavenWeightedPressurePlateBlock(75, AbstractBlock.Settings.copy(WAXED_MEDIUM_WEIGHTED_PRESSURE_PLATE.BLOCK)));

	public static final HavenPair COPPER_LANTERN = new HavenPair(new OxidizableLanternBlock(Oxidizable.OxidizationLevel.UNAFFECTED, AbstractBlock.Settings.of(Material.METAL).requiresTool().strength(3.5F).sounds(BlockSoundGroup.LANTERN).luminance(luminance(13)).nonOpaque()));
	public static final HavenPair EXPOSED_COPPER_LANTERN = new HavenPair(new OxidizableLanternBlock(Oxidizable.OxidizationLevel.EXPOSED, AbstractBlock.Settings.copy(COPPER_LANTERN.BLOCK)));
	public static final HavenPair WEATHERED_COPPER_LANTERN = new HavenPair(new OxidizableLanternBlock(Oxidizable.OxidizationLevel.WEATHERED, AbstractBlock.Settings.copy(COPPER_LANTERN.BLOCK)));
	public static final HavenPair OXIDIZED_COPPER_LANTERN = new HavenPair(new OxidizableLanternBlock(Oxidizable.OxidizationLevel.OXIDIZED, AbstractBlock.Settings.copy(COPPER_LANTERN.BLOCK)));
	public static final WaxedPair WAXED_COPPER_LANTERN = new WaxedPair(COPPER_LANTERN, new LanternBlock(AbstractBlock.Settings.copy(COPPER_LANTERN.BLOCK)));
	public static final WaxedPair WAXED_EXPOSED_COPPER_LANTERN = new WaxedPair(EXPOSED_COPPER_LANTERN, new LanternBlock(AbstractBlock.Settings.copy(WAXED_COPPER_LANTERN.BLOCK)));
	public static final WaxedPair WAXED_WEATHERED_COPPER_LANTERN = new WaxedPair(WEATHERED_COPPER_LANTERN, new LanternBlock(AbstractBlock.Settings.copy(WAXED_COPPER_LANTERN.BLOCK)));
	public static final WaxedPair WAXED_OXIDIZED_COPPER_LANTERN = new WaxedPair(OXIDIZED_COPPER_LANTERN, new LanternBlock(AbstractBlock.Settings.copy(WAXED_COPPER_LANTERN.BLOCK)));
	public static final HavenPair COPPER_SOUL_LANTERN = new HavenPair(new OxidizableLanternBlock(Oxidizable.OxidizationLevel.UNAFFECTED, AbstractBlock.Settings.of(Material.METAL).requiresTool().strength(3.5F).sounds(BlockSoundGroup.LANTERN).luminance(luminance(10)).nonOpaque()));
	public static final HavenPair EXPOSED_COPPER_SOUL_LANTERN = new HavenPair(new OxidizableLanternBlock(Oxidizable.OxidizationLevel.EXPOSED, AbstractBlock.Settings.copy(COPPER_SOUL_LANTERN.BLOCK)));
	public static final HavenPair WEATHERED_COPPER_SOUL_LANTERN = new HavenPair(new OxidizableLanternBlock(Oxidizable.OxidizationLevel.WEATHERED, AbstractBlock.Settings.copy(COPPER_SOUL_LANTERN.BLOCK)));
	public static final HavenPair OXIDIZED_COPPER_SOUL_LANTERN = new HavenPair(new OxidizableLanternBlock(Oxidizable.OxidizationLevel.OXIDIZED, AbstractBlock.Settings.copy(COPPER_SOUL_LANTERN.BLOCK)));
	public static final WaxedPair WAXED_COPPER_SOUL_LANTERN = new WaxedPair(COPPER_SOUL_LANTERN, new LanternBlock(AbstractBlock.Settings.copy(COPPER_SOUL_LANTERN.BLOCK)));
	public static final WaxedPair WAXED_EXPOSED_COPPER_SOUL_LANTERN = new WaxedPair(EXPOSED_COPPER_SOUL_LANTERN, new LanternBlock(AbstractBlock.Settings.copy(WAXED_COPPER_SOUL_LANTERN.BLOCK)));
	public static final WaxedPair WAXED_WEATHERED_COPPER_SOUL_LANTERN = new WaxedPair(WEATHERED_COPPER_SOUL_LANTERN, new LanternBlock(AbstractBlock.Settings.copy(WAXED_COPPER_SOUL_LANTERN.BLOCK)));
	public static final WaxedPair WAXED_OXIDIZED_COPPER_SOUL_LANTERN = new WaxedPair(OXIDIZED_COPPER_SOUL_LANTERN, new LanternBlock(AbstractBlock.Settings.copy(WAXED_COPPER_SOUL_LANTERN.BLOCK)));

	public static final HavenPair COPPER_CHAIN = new HavenPair(new OxidizableChainBlock(Oxidizable.OxidizationLevel.UNAFFECTED, AbstractBlock.Settings.of(Material.METAL, MapColor.CLEAR).requiresTool().strength(5.0F, 6.0F).sounds(BlockSoundGroup.CHAIN).nonOpaque()));
	public static final HavenPair EXPOSED_COPPER_CHAIN = new HavenPair(new OxidizableChainBlock(Oxidizable.OxidizationLevel.EXPOSED, AbstractBlock.Settings.copy(COPPER_CHAIN.BLOCK)));
	public static final HavenPair WEATHERED_COPPER_CHAIN = new HavenPair(new OxidizableChainBlock(Oxidizable.OxidizationLevel.WEATHERED, AbstractBlock.Settings.copy(COPPER_CHAIN.BLOCK)));
	public static final HavenPair OXIDIZED_COPPER_CHAIN = new HavenPair(new OxidizableChainBlock(Oxidizable.OxidizationLevel.OXIDIZED, AbstractBlock.Settings.copy(COPPER_CHAIN.BLOCK)));
	public static final WaxedPair WAXED_COPPER_CHAIN = new WaxedPair(COPPER_CHAIN, new ChainBlock(AbstractBlock.Settings.copy(COPPER_CHAIN.BLOCK)));
	public static final WaxedPair WAXED_EXPOSED_COPPER_CHAIN = new WaxedPair(EXPOSED_COPPER_CHAIN, new ChainBlock(AbstractBlock.Settings.copy(WAXED_COPPER_CHAIN.BLOCK)));
	public static final WaxedPair WAXED_WEATHERED_COPPER_CHAIN = new WaxedPair(WEATHERED_COPPER_CHAIN, new ChainBlock(AbstractBlock.Settings.copy(WAXED_COPPER_CHAIN.BLOCK)));
	public static final WaxedPair WAXED_OXIDIZED_COPPER_CHAIN = new WaxedPair(OXIDIZED_COPPER_CHAIN, new ChainBlock(AbstractBlock.Settings.copy(WAXED_COPPER_CHAIN.BLOCK)));

	public static final HavenPair COPPER_BARS = new HavenPair(new OxidizablePaneBlock(Oxidizable.OxidizationLevel.UNAFFECTED, AbstractBlock.Settings.of(Material.METAL, MapColor.CLEAR).requiresTool().strength(5.0F, 6.0F).sounds(BlockSoundGroup.METAL).nonOpaque()));
	public static final HavenPair EXPOSED_COPPER_BARS = new HavenPair(new OxidizablePaneBlock(Oxidizable.OxidizationLevel.EXPOSED, AbstractBlock.Settings.copy(COPPER_BARS.BLOCK)));
	public static final HavenPair WEATHERED_COPPER_BARS = new HavenPair(new OxidizablePaneBlock(Oxidizable.OxidizationLevel.WEATHERED, AbstractBlock.Settings.copy(COPPER_BARS.BLOCK)));
	public static final HavenPair OXIDIZED_COPPER_BARS = new HavenPair(new OxidizablePaneBlock(Oxidizable.OxidizationLevel.OXIDIZED, AbstractBlock.Settings.copy(COPPER_BARS.BLOCK)));
	public static final WaxedPair WAXED_COPPER_BARS = new WaxedPair(COPPER_BARS, new HavenPaneBlock(AbstractBlock.Settings.copy(COPPER_BARS.BLOCK)));
	public static final WaxedPair WAXED_EXPOSED_COPPER_BARS = new WaxedPair(EXPOSED_COPPER_BARS, new HavenPaneBlock(AbstractBlock.Settings.copy(WAXED_COPPER_BARS.BLOCK)));
	public static final WaxedPair WAXED_WEATHERED_COPPER_BARS = new WaxedPair(WEATHERED_COPPER_BARS, new HavenPaneBlock(AbstractBlock.Settings.copy(WAXED_COPPER_BARS.BLOCK)));
	public static final WaxedPair WAXED_OXIDIZED_COPPER_BARS = new WaxedPair(OXIDIZED_COPPER_BARS, new HavenPaneBlock(AbstractBlock.Settings.copy(WAXED_COPPER_BARS.BLOCK)));

	public static final HavenPair COPPER_WALL = new HavenPair(new OxidizableWallBlock(Oxidizable.OxidizationLevel.UNAFFECTED, Blocks.COPPER_BLOCK));
	public static final HavenPair EXPOSED_COPPER_WALL = new HavenPair(new OxidizableWallBlock(Oxidizable.OxidizationLevel.EXPOSED, Blocks.EXPOSED_COPPER));
	public static final HavenPair WEATHERED_COPPER_WALL = new HavenPair(new OxidizableWallBlock(Oxidizable.OxidizationLevel.WEATHERED, Blocks.WEATHERED_COPPER));
	public static final HavenPair OXIDIZED_COPPER_WALL = new HavenPair(new OxidizableWallBlock(Oxidizable.OxidizationLevel.OXIDIZED, Blocks.OXIDIZED_COPPER));
	public static final WaxedPair WAXED_COPPER_WALL = new WaxedPair(COPPER_WALL, new HavenWallBlock(Blocks.WAXED_COPPER_BLOCK));
	public static final WaxedPair WAXED_EXPOSED_COPPER_WALL = new WaxedPair(EXPOSED_COPPER_WALL, new HavenWallBlock(Blocks.WAXED_EXPOSED_COPPER));
	public static final WaxedPair WAXED_WEATHERED_COPPER_WALL = new WaxedPair(WEATHERED_COPPER_WALL, new HavenWallBlock(Blocks.WAXED_WEATHERED_COPPER));
	public static final WaxedPair WAXED_OXIDIZED_COPPER_WALL = new WaxedPair(OXIDIZED_COPPER_WALL, new HavenWallBlock(Blocks.WAXED_OXIDIZED_COPPER));

	public static final HavenPair CUT_COPPER_PILLAR = new HavenPair(new OxidizablePillarBlock(Oxidizable.OxidizationLevel.UNAFFECTED, AbstractBlock.Settings.copy(Blocks.CUT_COPPER)));
	public static final HavenPair EXPOSED_CUT_COPPER_PILLAR = new HavenPair(new OxidizablePillarBlock(Oxidizable.OxidizationLevel.EXPOSED, AbstractBlock.Settings.copy(Blocks.EXPOSED_CUT_COPPER)));
	public static final HavenPair WEATHERED_CUT_COPPER_PILLAR = new HavenPair(new OxidizablePillarBlock(Oxidizable.OxidizationLevel.WEATHERED, AbstractBlock.Settings.copy(Blocks.WEATHERED_CUT_COPPER)));
	public static final HavenPair OXIDIZED_CUT_COPPER_PILLAR = new HavenPair(new OxidizablePillarBlock(Oxidizable.OxidizationLevel.OXIDIZED, AbstractBlock.Settings.copy(Blocks.OXIDIZED_CUT_COPPER)));
	public static final WaxedPair WAXED_CUT_COPPER_PILLAR = new WaxedPair(CUT_COPPER_PILLAR, new PillarBlock(AbstractBlock.Settings.copy(Blocks.WAXED_CUT_COPPER)));
	public static final WaxedPair WAXED_EXPOSED_CUT_COPPER_PILLAR = new WaxedPair(EXPOSED_CUT_COPPER_PILLAR, new PillarBlock(AbstractBlock.Settings.copy(Blocks.WAXED_EXPOSED_CUT_COPPER)));
	public static final WaxedPair WAXED_WEATHERED_CUT_COPPER_PILLAR = new WaxedPair(WEATHERED_CUT_COPPER_PILLAR, new PillarBlock(AbstractBlock.Settings.copy(Blocks.WAXED_WEATHERED_CUT_COPPER)));
	public static final WaxedPair WAXED_OXIDIZED_CUT_COPPER_PILLAR = new WaxedPair(OXIDIZED_CUT_COPPER_PILLAR, new PillarBlock(AbstractBlock.Settings.copy(Blocks.WAXED_OXIDIZED_CUT_COPPER)));

	public static final HavenPair CUT_COPPER_WALL = new HavenPair(new OxidizableWallBlock(Oxidizable.OxidizationLevel.UNAFFECTED, Blocks.CUT_COPPER));
	public static final HavenPair EXPOSED_CUT_COPPER_WALL = new HavenPair(new OxidizableWallBlock(Oxidizable.OxidizationLevel.EXPOSED, Blocks.EXPOSED_CUT_COPPER));
	public static final HavenPair WEATHERED_CUT_COPPER_WALL = new HavenPair(new OxidizableWallBlock(Oxidizable.OxidizationLevel.WEATHERED, Blocks.WEATHERED_CUT_COPPER));
	public static final HavenPair OXIDIZED_CUT_COPPER_WALL = new HavenPair(new OxidizableWallBlock(Oxidizable.OxidizationLevel.OXIDIZED, Blocks.OXIDIZED_CUT_COPPER));
	public static final WaxedPair WAXED_CUT_COPPER_WALL = new WaxedPair(CUT_COPPER_WALL, new HavenWallBlock(Blocks.WAXED_CUT_COPPER));
	public static final WaxedPair WAXED_EXPOSED_CUT_COPPER_WALL = new WaxedPair(EXPOSED_CUT_COPPER_WALL, new HavenWallBlock(Blocks.WAXED_EXPOSED_CUT_COPPER));
	public static final WaxedPair WAXED_WEATHERED_CUT_COPPER_WALL = new WaxedPair(WEATHERED_CUT_COPPER_WALL, new HavenWallBlock(Blocks.WAXED_WEATHERED_CUT_COPPER));
	public static final WaxedPair WAXED_OXIDIZED_CUT_COPPER_WALL = new WaxedPair(OXIDIZED_CUT_COPPER_WALL, new HavenWallBlock(Blocks.WAXED_OXIDIZED_CUT_COPPER));

	//More Gold
	public static final DefaultParticleType GOLD_FLAME = FabricParticleTypes.simple(false);
	public static final GoldMaterial GOLD_MATERIAL = new GoldMaterial();
	//More Iron
	public static final DefaultParticleType IRON_FLAME = FabricParticleTypes.simple(false);
	public static final IronMaterial IRON_MATERIAL = new IronMaterial();
	public static final Item DARK_IRON_INGOT = new Item(ItemSettings());
	public static final DarkIronMaterial DARK_IRON_MATERIAL = new DarkIronMaterial();
	public static final HavenPair DARK_HEAVY_WEIGHTED_PRESSURE_PLATE = new HavenPair(new HavenWeightedPressurePlateBlock(150, AbstractBlock.Settings.of(Material.METAL).requiresTool().noCollision().strength(0.5F).sounds(BlockSoundGroup.WOOD)));

	//More Netherite
	public static final DefaultParticleType NETHERITE_FLAME = FabricParticleTypes.simple(false);
	public static final NetheriteMaterial NETHERITE_MATERIAL = new NetheriteMaterial();
	public static final HavenPair CRUSHING_WEIGHTED_PRESSURE_PLATE = new HavenPair(new HavenWeightedPressurePlateBlock(300, AbstractBlock.Settings.of(Material.METAL).requiresTool().noCollision().strength(0.5F).sounds(BlockSoundGroup.WOOD)), ItemSettings().fireproof());

	public static final Item TINKER_TOY = new Item(ItemSettings());

	public static final HavenPair CHARCOAL_BLOCK = new HavenPair(new Block(AbstractBlock.Settings.of(Material.STONE, MapColor.BLACK).requiresTool().strength(5.0F, 6.0F)));
	//Amethyst
	public static final AmethystMaterial AMETHYST_MATERIAL = new AmethystMaterial();
	//Emerald
	public static final EmeraldMaterial EMERALD_MATERIAL = new EmeraldMaterial();
	//Diamond
	public static final DiamondMaterial DIAMOND_MATERIAL = new DiamondMaterial();
	//Melon Golem
	public static final EntityType<MelonSeedProjectileEntity> MELON_SEED_PROJECTILE_ENTITY = FabricEntityTypeBuilder.<MelonSeedProjectileEntity>create(SpawnGroup.MISC, MelonSeedProjectileEntity::new).dimensions(EntityDimensions.fixed(0.25F, 0.25F)).trackRangeBlocks(4).trackedUpdateRate(10).build();
	public static final EntityType<MelonGolemEntity> MELON_GOLEM_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.MISC, MelonGolemEntity::new).dimensions(EntityType.SNOW_GOLEM.getDimensions()).trackRangeBlocks(8).build();
	public static final HavenPair CARVED_MELON = new HavenPair(new CarvedMelonBlock(AbstractBlock.Settings.copy(Blocks.CARVED_PUMPKIN)));
	public static final HavenPair MELON_LANTERN = new HavenPair(new CarvedMelonBlock(AbstractBlock.Settings.copy(Blocks.JACK_O_LANTERN)));
	//Rainbow Sheep
	public static final HavenPair RAINBOW_WOOL = new HavenPair(new HavenFacingBlock(Blocks.WHITE_WOOL));
	public static final HavenPair RAINBOW_CARPET = new HavenPair(new HorziontalFacingCarpetBlock(AbstractBlock.Settings.copy(Blocks.WHITE_CARPET)));
	public static final HavenBed RAINBOW_BED = new HavenBed("rainbow");
	public static final EntityType<RainbowSheepEntity> RAINBOW_SHEEP_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, RainbowSheepEntity::new).dimensions(EntityType.SHEEP.getDimensions()).trackRangeBlocks(10).build();
	public static final Item RAINBOW_SHEEP_SPAWN_EGG = new SpawnEggItem(RAINBOW_SHEEP_ENTITY, 16777215, 16777215, ItemSettings());
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
	public static final HavenTallPair AMARANTH = new HavenTallPair(new TallFlowerBlock(HavenFlower.TallSettings()));
	public static final HavenTallPair TALL_ALLIUM = new HavenTallPair(new TallFlowerBlock(HavenFlower.TallSettings()));
	public static final HavenTallPair TALL_PINK_ALLIUM = new HavenTallPair(new TallFlowerBlock(HavenFlower.TallSettings()));

	public static final ToolItem PTEROR = new SwordItem(ToolMaterials.NETHERITE, 3, -2.4F, ItemSettings().fireproof());
	public static final ToolItem SBEHESOHE = new SwordItem(ToolMaterials.DIAMOND, 3, -2.4F, ItemSettings().fireproof());
	public static final Item BROKEN_BOTTLE = new Item(ItemSettings());
	public static final Item LOCKET = new Item(ItemSettings());
	public static final Item EMERALD_LOCKET = new Item(ItemSettings());

	public static final HavenPair SOFT_TNT = new HavenPair(new SoftTntBlock(AbstractBlock.Settings.of(Material.TNT).breakInstantly().sounds(BlockSoundGroup.GRASS)));
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

	public static final TreeMaterial CHERRY_MATERIAL = new TreeMaterial("cherry", MapColor.RAW_IRON_PINK, CherrySaplingGenerator::new);
	public static final HavenPair PALE_CHERRY_LEAVES = new HavenPair(new HavenLeavesBlock(CHERRY_MATERIAL.getLeaves().BLOCK));
	public static final HavenPair PINK_CHERRY_LEAVES = new HavenPair(new HavenLeavesBlock(CHERRY_MATERIAL.getLeaves().BLOCK));
	public static final HavenPair WHITE_CHERRY_LEAVES = new HavenPair(new HavenLeavesBlock(CHERRY_MATERIAL.getLeaves().BLOCK));
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

	public static final TreeMaterial CASSIA_MATERIAL = new TreeMaterial("cassia", MapColor.BROWN, BlockSoundGroup.AZALEA_LEAVES, CassiaSaplingGenerator::new);
	public static final HavenPair FLOWERING_CASSIA_LEAVES = new HavenPair(new HavenLeavesBlock(CASSIA_MATERIAL.getLeaves().BLOCK));
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

	public static final Item SNICKERDOODLE = new Item(ItemSettings().food(FoodComponents.COOKIE));
	public static final Item CINNAMON_ROLL = new Item(ItemSettings().food(new FoodComponent.Builder().hunger(3).saturationModifier(0.3F).build()));
	//Strawberry
	public static final Block STRAWBERRY_BUSH = new StrawberryBushBlock(AbstractBlock.Settings.copy(Blocks.SWEET_BERRY_BUSH));
	public static final Item STRAWBERRY = new AliasedBlockItem(STRAWBERRY_BUSH, ItemSettings().food(new FoodComponent.Builder().hunger(2).saturationModifier(0.1F).build()));
	//Drinks & Juice
	public static Item.Settings BottledDrinkSettings() {
		return ItemSettings().recipeRemainder(Items.GLASS_BOTTLE).food(new FoodComponent.Builder().hunger(4).saturationModifier(0.5F).build());
	}
	public static final Item APPLE_CIDER = new BottledDrinkItem(ItemSettings().recipeRemainder(Items.GLASS_BOTTLE).food(new FoodComponent.Builder().hunger(5).saturationModifier(0.6F).build()));
	public static final HavenPair JUICER = new HavenPair(new JuicerBlock(AbstractBlock.Settings.of(Material.STONE).requiresTool().strength(3.5F)), ItemSettings());
	public static final Item APPLE_JUICE = new BottledDrinkItem();
	public static final Item BEETROOT_JUICE = new BottledDrinkItem();
	public static final Item BLACK_APPLE_JUICE = new BottledDrinkItem(ItemSettings().recipeRemainder(Items.GLASS_BOTTLE).food(new FoodComponent.Builder().hunger(4).saturationModifier(0.5F).statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 2, 2), 1F).statusEffect(new StatusEffectInstance(StatusEffects.INSTANT_HEALTH, 1, 2), 1F).build()));
	public static final Item CABBAGE_JUICE = new BottledDrinkItem();
	public static final Item CACTUS_JUICE = new BottledDrinkItem();
	public static final Item CARROT_JUICE = new BottledDrinkItem();
	public static final Item CHERRY_JUICE = new BottledDrinkItem();
	public static final Item CHORUS_JUICE = new ChorusJuiceItem(BottledDrinkSettings());
	public static final Item GLOW_BERRY_JUICE = new BottledDrinkItem();
	public static final Item KELP_JUICE = new BottledDrinkItem();
	public static final Item MELON_JUICE = new BottledDrinkItem();
	public static final Item ONION_JUICE = new BottledDrinkItem();
	public static final Item POTATO_JUICE = new BottledDrinkItem();
	public static final Item PUMPKIN_JUICE = new BottledDrinkItem();
	public static final Item SEA_PICKLE_JUICE = new BottledDrinkItem();
	public static final Item STRAWBERRY_JUICE = new BottledDrinkItem();
	public static final Item SWEET_BERRY_JUICE = new BottledDrinkItem();
	public static final Item TOMATO_JUICE = new BottledDrinkItem();
	
	//Bamboo
	public static final BambooMaterial BAMBOO_MATERIAL = new BambooMaterial("bamboo", MapColor.DARK_GREEN);
	public static final BambooMaterial DRIED_BAMBOO_MATERIAL = new BambooMaterial("dried_bamboo", MapColor.PALE_YELLOW);
	public static final HavenPair DRIED_BAMBOO_BLOCK = new HavenPair(new DriedBambooBlock(AbstractBlock.Settings.copy(Blocks.BAMBOO)));
	public static final Block POTTED_DRIED_BAMBOO = new FlowerPotBlock(DRIED_BAMBOO_BLOCK.BLOCK, AbstractBlock.Settings.of(Material.DECORATION).breakInstantly().nonOpaque());

	//Misc Minecraft Earth
	public static final Item HORN = new Item(ItemSettings());
	//Liquid Mud
	public static final FlowableFluid STILL_MUD_FLUID = new MudFluid.Still();
	public static final FlowableFluid FLOWING_MUD_FLUID = new MudFluid.Flowing();
	public static final FluidBlock MUD_FLUID_BLOCK = new MudFluidBlock(STILL_MUD_FLUID, FabricBlockSettings.copyOf(Blocks.WATER).mapColor(MapColor.BROWN));
	public static final BucketItem MUD_BUCKET = new MudBucketItem(STILL_MUD_FLUID, ItemSettings().recipeRemainder(Items.BUCKET).maxCount(1));
	public static final Block MUD_CAULDRON = new MudCauldronBlock(FabricBlockSettings.copyOf(Blocks.CAULDRON));
	//Music Discs
	public static final Item MUSIC_DISC_OTHERSIDE = new MusicDiscItem(14, HavenSoundEvents.MUSIC_DISC_OTHERSIDE, ItemSettings().maxCount(1).rarity(Rarity.RARE));
	public static final Item MUSIC_DISC_5 = new MusicDiscItem(15, HavenSoundEvents.MUSIC_DISC_5, ItemSettings().maxCount(1).rarity(Rarity.RARE));
	public static final Item DISC_FRAGMENT_5 = new DiscFragmentItem(ItemSettings());
	//Goat Horn
	public static final Item GOAT_HORN = new Item(ItemSettings()); //TODO: Goat Horn
	//Mud
	public static final HavenPair MUD = new HavenPair(new MudBlock(AbstractBlock.Settings.copy(Blocks.DIRT).mapColor(MapColor.TERRACOTTA_CYAN).allowsSpawning(HavenMod::always).solidBlock(HavenMod::always).blockVision(HavenMod::always).suffocates(HavenMod::always).sounds(HavenBlockSoundGroups.MUD)));
	public static final HavenPair PACKED_MUD = new HavenPair(new Block(AbstractBlock.Settings.copy(Blocks.DIRT).strength(1.0f, 3.0f).sounds(HavenBlockSoundGroups.PACKED_MUD)));
	public static final HavenPair MUD_BRICKS = new HavenPair(new Block(AbstractBlock.Settings.of(Material.STONE, MapColor.TERRACOTTA_LIGHT_GRAY).requiresTool().strength(1.5f, 3.0f).sounds(HavenBlockSoundGroups.MUD_BRICKS)));
	public static final HavenPair MUD_BRICK_STAIRS = new HavenPair(new HavenStairsBlock(MUD_BRICKS.BLOCK));
	public static final HavenPair MUD_BRICK_SLAB = new HavenPair(new HavenSlabBlock(MUD_BRICKS.BLOCK));
	public static final HavenPair MUD_BRICK_WALL = new HavenPair(new HavenWallBlock(MUD_BRICKS.BLOCK));
	//Mangrove
	public static final MangroveMaterial MANGROVE_MATERIAL = new MangroveMaterial("mangrove", MapColor.RED);
	public static final HavenPair MANGROVE_ROOTS = new HavenPair(new MangroveRootsBlock(AbstractBlock.Settings.of(Material.WOOD, MapColor.SPRUCE_BROWN).strength(0.7f).ticksRandomly().sounds(HavenBlockSoundGroups.MANGROVE_ROOTS).nonOpaque().suffocates(BaseTreeMaterial::never).blockVision(BaseTreeMaterial::never).nonOpaque()));
	public static final HavenPair MUDDY_MANGROVE_ROOTS = new HavenPair(new PillarBlock(AbstractBlock.Settings.of(Material.SOIL, MapColor.SPRUCE_BROWN).strength(0.7f).sounds(HavenBlockSoundGroups.MUDDY_MANGROVE_ROOTS)));
	//Frogs
	public static final Material FROGLIGHT_MATERIAL = new Material.Builder(MapColor.CLEAR).build();
	public static final HavenPair OCHRE_FROGLIGHT = new HavenPair(new PillarBlock(AbstractBlock.Settings.of(FROGLIGHT_MATERIAL, MapColor.PALE_YELLOW).strength(0.3f).luminance(state -> 15).sounds(HavenBlockSoundGroups.FROGLIGHT)));
	public static final HavenPair VERDANT_FROGLIGHT = new HavenPair(new PillarBlock(AbstractBlock.Settings.of(FROGLIGHT_MATERIAL, MapColor.LICHEN_GREEN).strength(0.3f).luminance(state -> 15).sounds(HavenBlockSoundGroups.FROGLIGHT)));
	public static final HavenPair PEARLESCENT_FROGLIGHT = new HavenPair(new PillarBlock(AbstractBlock.Settings.of(FROGLIGHT_MATERIAL, MapColor.PINK).strength(0.3f).luminance(state -> 15).sounds(HavenBlockSoundGroups.FROGLIGHT)));
	//Deep Dark
	public static final HavenPair REINFORCED_DEEPSLATE = new HavenPair(new Block(AbstractBlock.Settings.of(Material.STONE, MapColor.DEEPSLATE_GRAY).sounds(BlockSoundGroup.DEEPSLATE).strength(55.0f, 1200.0f)));
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

	//Server Blood
	private static final Block BLOOD_BLOCK_BLOCK = new Block(AbstractBlock.Settings.of(Material.SOLID_ORGANIC, MapColor.RED).strength(1.0F).sounds(BlockSoundGroup.WART_BLOCK));
	public static final ItemGroup BLOOD_ITEM_GROUP = FabricItemGroupBuilder.build(HavenMod.ID("blood"), () -> new ItemStack(BLOOD_BLOCK_BLOCK));
	public static final Item.Settings BloodItemSettings() {
		return ItemSettings().group(BLOOD_ITEM_GROUP);
	}
	public static final HavenPair BLOOD_BLOCK = new HavenPair(BLOOD_BLOCK_BLOCK, BloodItemSettings());
	public static final BloodMaterial BLOOD_MATERIAL = new BloodMaterial("blood");
	public static final HavenPair DRIED_BLOOD_BLOCK = new HavenPair(new Block(AbstractBlock.Settings.copy(BLOOD_BLOCK.BLOCK)), BloodItemSettings());
	public static final BloodMaterial DRIED_BLOOD_MATERIAL = new BloodMaterial("dried_blood");

	public static final FlowableFluid STILL_BLOOD_FLUID = new BloodFluid.Still();
	public static final FlowableFluid FLOWING_BLOOD_FLUID = new BloodFluid.Flowing();
	public static final FluidBlock BLOOD_FLUID_BLOCK = new BloodFluidBlock(STILL_BLOOD_FLUID, FabricBlockSettings.copyOf(Blocks.WATER).mapColor(MapColor.RED));
	public static final BucketItem BLOOD_BUCKET = new BloodBucketItem(STILL_BLOOD_FLUID, BloodItemSettings().recipeRemainder(Items.BUCKET).maxCount(1));

	public static final Block BLOOD_CAULDRON = new BloodCauldronBlock(FabricBlockSettings.copyOf(Blocks.CAULDRON));
	public static final Item BLOOD_BOTTLE = new BloodBottleItem(BloodItemSettings().maxCount(16).recipeRemainder(Items.GLASS_BOTTLE));
	public static final Item LAVA_BOTTLE = new LavaBottleItem(ItemSettings().maxCount(16).recipeRemainder(Items.GLASS_BOTTLE));
	//TODO: WATER_BOTTLE
	public static final Item SUGAR_WATER_BOTTLE = new BottledDrinkItem(ItemSettings().maxCount(16).recipeRemainder(Items.GLASS_BOTTLE).food(new FoodComponent.Builder().hunger(0).saturationModifier(0.1F).build()));
	public static final Item ICHOR_BOTTLE = new IchorBottleItem(ItemSettings().maxCount(16).recipeRemainder(Items.GLASS_BOTTLE));
	public static final Item SLIME_BOTTLE = new Item(ItemSettings().maxCount(16).recipeRemainder(Items.GLASS_BOTTLE));
	public static final Item MAGMA_CREAM_BOTTLE = new Item(ItemSettings().maxCount(16).recipeRemainder(Items.GLASS_BOTTLE));
	//Syringes
	public static final Item BLOOD_SYRINGE = new BloodSyringeItem(BloodType.PLAYER) {
		@Override
		public void ApplyEffect(PlayerEntity user, LivingEntity entity) {
			BloodType bloodType = BloodType.Get(entity);
			if (bloodType == BloodType.PLAYER || bloodType == BloodType.ZOMBIE) entity.heal(1);
			else if (entity instanceof PlayerEntity && bloodType != BloodType.NONE) entity.heal(1);
			else super.ApplyEffect(user, entity);
		}
	};
	public static final Item ALLAY_BLOOD_SYRINGE = new BloodSyringeItem(BloodType.ALLAY);
	public static final Item ANEMIC_BLOOD_SYRINGE = new BloodSyringeItem(BloodType.ANEMIC);
	public static final Item AVIAN_BLOOD_SYRINGE = new BloodSyringeItem(BloodType.AVIAN);
	public static final Item AXOLOTL_BLOOD_SYRINGE = new BloodSyringeItem(BloodType.AXOLOTL);
	public static final Item BAT_BLOOD_SYRINGE = new BloodSyringeItem(BloodType.BAT);
	public static final Item BEAR_BLOOD_SYRINGE = new BloodSyringeItem(BloodType.BEAR);
	public static final Item BEE_BLOOD_SYRINGE = new BloodSyringeItem(BloodType.BEE) {
		@Override
		public void ApplyEffect(PlayerEntity user, LivingEntity entity) {
			BloodType bloodType = BloodType.Get(entity);
			if (bloodType == BloodType.BEE || bloodType == BloodType.BEE_ENDERMAN) entity.heal(1);
			else {
				super.ApplyEffect(user, entity);
				if (bloodType.IsPoisonVulnerable()) entity.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 100, 1));
			}
		}
	};
	public static final Item BEE_ENDERMAN_BLOOD_SYRINGE = new BloodSyringeItem(BloodType.BEE_ENDERMAN) {
		@Override
		public void ApplyEffect(PlayerEntity user, LivingEntity entity) {
			BloodType bloodType = BloodType.Get(entity);
			if (bloodType == BloodType.BEE || bloodType == BloodType.BEE_ENDERMAN) entity.heal(1);
			else {
				if (bloodType == BloodType.ENDERMAN || bloodType == BloodType.DRAGON) entity.heal(1);
				else super.ApplyEffect(user, entity);
				if (bloodType.IsPoisonVulnerable()) entity.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 100, 1));
			}
		}
	};
	public static final Item CANINE_BLOOD_SYRINGE = new BloodSyringeItem(BloodType.CANINE);
	public static final Item COW_BLOOD_SYRINGE = new BloodSyringeItem(BloodType.COW);
	public static final Item CREEPER_BLOOD_SYRINGE = new BloodSyringeItem(BloodType.CREEPER);
	public static final Item DISEASED_FELINE_BLOOD_SYRINGE = new BloodSyringeItem(BloodType.DISEASED_FELINE) {
		@Override
		public void ApplyEffect(PlayerEntity user, LivingEntity entity) {
			BloodType bloodType = BloodType.Get(entity);
			if (bloodType == BloodType.DISEASED_FELINE) entity.heal(1);
			else {
				if (bloodType == BloodType.FELINE) entity.heal(1);
				else super.ApplyEffect(user, entity);
				entity.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 200, 1));
			}
		}
	};
	public static final Item DOLPHIN_BLOOD_SYRINGE = new BloodSyringeItem(BloodType.DOLPHIN);
	public static final Item DRAGON_BLOOD_SYRINGE = new BloodSyringeItem(BloodType.DRAGON);
	public static final Item ENDERMAN_BLOOD_SYRINGE = new BloodSyringeItem(BloodType.ENDERMAN) {
		@Override
		public void ApplyEffect(PlayerEntity user, LivingEntity entity) {
			BloodType bloodType = BloodType.Get(entity);
			if (bloodType == BloodType.BEE_ENDERMAN || bloodType == BloodType.DRAGON) entity.heal(1);
			else super.ApplyEffect(user, entity);
		}
	};
	public static final Item EQUINE_BLOOD_SYRINGE = new BloodSyringeItem(BloodType.EQUINE);
	public static final Item FELINE_BLOOD_SYRINGE = new BloodSyringeItem(BloodType.FELINE);
	public static final Item FISH_BLOOD_SYRINGE = new BloodSyringeItem(BloodType.FISH);
	public static final Item GOAT_BLOOD_SYRINGE = new BloodSyringeItem(BloodType.GOAT);
	public static final Item HONEY_SYRINGE = new BloodSyringeItem(BloodType.HONEY) {
		@Override
		public void ApplyEffect(PlayerEntity user, LivingEntity entity) {
			BloodType bloodType = BloodType.Get(entity);
			if (bloodType == BloodType.HONEY || bloodType == BloodType.BEE || bloodType == BloodType.BEE_ENDERMAN || bloodType == BloodType.BEAR){
				entity.heal(1);
			}
			else {
				super.ApplyEffect(user, entity);
				if (!entity.getEntityWorld().isClient) user.removeStatusEffect(StatusEffects.POISON);
			}
		}
	};
	public static final Item ICHOR_SYRINGE = new BloodSyringeItem(BloodType.ICHOR) {
		@Override
		public void ApplyEffect(PlayerEntity user, LivingEntity entity) {
			BloodType bloodType = BloodType.Get(entity);
			if (bloodType == BloodType.ICHOR) entity.heal(1);
			else {
				super.ApplyEffect(user, entity);
				if (bloodType.IsPoisonVulnerable()) entity.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 100, 1));
				else if (bloodType.IsWitherVulnerable()) entity.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 100, 1));
				entity.damage(HavenDamageSource.ICHOR, 3);
			}
		}
	};
	public static final Item INSECT_BLOOD_SYRINGE = new BloodSyringeItem(BloodType.INSECT);
	public static final Item LAVA_SYRINGE = new BloodSyringeItem(BloodType.LAVA) {
		@Override
		public void ApplyEffect(PlayerEntity user, LivingEntity entity) {
			BloodType bloodType = BloodType.Get(entity);
			if (bloodType == BloodType.LAVA || bloodType == BloodType.MAGMA) entity.heal(1);
			else {
				super.ApplyEffect(user, entity);
				if (bloodType.IsFireVulnerable()) {
					entity.setOnFireFor(30);
					entity.damage(DamageSource.LAVA, 4);
				}
				else entity.damage(HavenDamageSource.INCOMPATIBLE_BLOOD, 4);
			}
		}
	};
	public static final Item LLAMA_BLOOD_SYRINGE = new BloodSyringeItem(BloodType.LLAMA);
	public static final Item MAGMA_CREAM_SYRINGE = new BloodSyringeItem(BloodType.MAGMA) {
		@Override
		public void ApplyEffect(PlayerEntity user, LivingEntity entity) {
			BloodType bloodType = BloodType.Get(entity);
			if (bloodType == BloodType.LAVA || bloodType == BloodType.MAGMA) entity.heal(1);
			else {
				super.ApplyEffect(user, entity);
				if (bloodType.IsFireVulnerable()) entity.setOnFireFor(5);
			}
		}
	};
	public static final Item NEPHAL_BLOOD_SYRINGE = new BloodSyringeItem(BloodType.NEPHAL) {
		@Override
		public void ApplyEffect(PlayerEntity user, LivingEntity entity) {
			BloodType bloodType = BloodType.Get(entity);
			if (bloodType == BloodType.NEPHAL) entity.heal(1);
			else if (bloodType == BloodType.VAMPIRE) {
				entity.heal(1);
				entity.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 200, 1));
			}
			else super.ApplyEffect(user, entity);
		}
	};
	public static final Item NETHER_BLOOD_SYRINGE = new BloodSyringeItem(BloodType.NETHER);
	public static final Item PHANTOM_BLOOD_SYRINGE = new BloodSyringeItem(BloodType.PHANTOM);
	public static final Item PIG_BLOOD_SYRINGE = new BloodSyringeItem(BloodType.PIG);
	public static final Item RABBIT_BLOOD_SYRINGE = new BloodSyringeItem(BloodType.RABBIT);
	public static final Item RAVAGER_BLOOD_SYRINGE = new BloodSyringeItem(BloodType.RAVAGER);
	public static final Item SHEEP_BLOOD_SYRINGE = new BloodSyringeItem(BloodType.SHEEP);
	public static final Item SLIME_SYRINGE = new BloodSyringeItem(BloodType.SLIME) {
		@Override
		public void ApplyEffect(PlayerEntity user, LivingEntity entity) {
			BloodType bloodType = BloodType.Get(entity);
			if (bloodType == BloodType.SLUDGE || bloodType == BloodType.MAGMA) entity.heal(1);
			else super.ApplyEffect(user, entity);
		}
	};
	public static final Item SLUDGE_SYRINGE = new BloodSyringeItem(BloodType.SLUDGE) {
		@Override
		public void ApplyEffect(PlayerEntity user, LivingEntity entity) {
			BloodType bloodType = BloodType.Get(entity);
			if (bloodType == BloodType.SLUDGE) entity.heal(1);
			else {
				super.ApplyEffect(user, entity);
				if (bloodType.IsPoisonVulnerable()) user.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 100, 1));
				else if (bloodType.IsWitherVulnerable()) user.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 100, 1));
				else user.damage(HavenDamageSource.INCOMPATIBLE_BLOOD, 3);
			}
		}
	};
	public static final Item SPIDER_BLOOD_SYRINGE = new BloodSyringeItem(BloodType.SPIDER);
	public static final Item SQUID_BLOOD_SYRINGE = new BloodSyringeItem(BloodType.SQUID);
	public static final Item STRIDER_BLOOD_SYRINGE = new BloodSyringeItem(BloodType.STRIDER);
	public static final Item SUGAR_WATER_SYRINGE = new BloodSyringeItem(BloodType.SUGAR_WATER) {
		@Override
		public void ApplyEffect(PlayerEntity user, LivingEntity entity) {
			BloodType bloodType = BloodType.Get(entity);
			if (bloodType == BloodType.SUGAR_WATER || bloodType == BloodType.WATER) entity.heal(1);
			else {
				super.ApplyEffect(user, entity);
				//Put out fires
				if (entity.isOnFire()) entity.setOnFire(false);
				//Hurt water-vulnerable
				if (bloodType.IsWaterVulnerable()) {
					entity.damage(HavenDamageSource.INJECTED_WATER, 4);
				}
			}
		}
	};
	public static final Item TURTLE_BLOOD_SYRINGE = new BloodSyringeItem(BloodType.TURTLE);
	public static final Item VAMPIRE_BLOOD_SYRINGE = new BloodSyringeItem(BloodType.VAMPIRE) {
		@Override
		public void ApplyEffect(PlayerEntity user, LivingEntity entity) {
			BloodType bloodType = BloodType.Get(entity);
			if (bloodType == BloodType.VAMPIRE) entity.heal(1);
			else {
				entity.damage(HavenDamageSource.INCOMPATIBLE_BLOOD, 1);
				if (bloodType.IsWitherVulnerable()) user.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 100, 1));
			}
		}
	};
	public static final Item VEX_BLOOD_SYRINGE = new BloodSyringeItem(BloodType.VEX);
	public static final Item VILLAGER_BLOOD_SYRINGE = new BloodSyringeItem(BloodType.VILLAGER);
	public static final Item WARDEN_BLOOD_SYRINGE = new BloodSyringeItem(BloodType.WARDEN);
	public static final Item WATER_SYRINGE = new BloodSyringeItem(BloodType.WATER) {
		@Override
		public void ApplyEffect(PlayerEntity user, LivingEntity entity) {
			BloodType bloodType = BloodType.Get(entity);
			if (bloodType == BloodType.WATER) entity.heal(1);
			else if (bloodType != BloodType.SUGAR_WATER) {
				super.ApplyEffect(user, entity);
				//Put out fires
				if (entity.isOnFire()) entity.setOnFire(false);
				//Hurt water-vulnerable
				if (bloodType.IsWaterVulnerable()) {
					entity.damage(HavenDamageSource.INJECTED_WATER, 4);
				}
			}
		}
	};
	public static final Item ZOMBIE_BLOOD_SYRINGE = new BloodSyringeItem(BloodType.ZOMBIE) {
		@Override
		public void ApplyEffect(PlayerEntity user, LivingEntity entity) {
			BloodType bloodType = BloodType.Get(entity);
			if (bloodType == BloodType.ZOMBIE) entity.heal(1);
			else {
				if(bloodType == BloodType.PLAYER) entity.heal(1);
				else super.ApplyEffect(user, entity);
				if (bloodType.IsPoisonVulnerable()) entity.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 100, 1));
			}
		}
	};

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
	public static final EntityType<MoobloomEntity> MOOBLOOM_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, MoobloomEntity::new).dimensions(EntityDimensions.fixed(0.9F, 1.4F)).trackRangeBlocks(10).build();
	public static final Item MOOBLOOM_SPAWN_EGG = new SpawnEggItem(MOOBLOOM_ENTITY, 16777215, 16777215, ItemSettings());
	public static final EntityType<MooblossomEntity> MOOBLOSSOM_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, MooblossomEntity::new).dimensions(EntityDimensions.fixed(0.9F, 1.4F)).trackRangeBlocks(10).build();
	public static final Item MOOBLOSSOM_SPAWN_EGG = new SpawnEggItem(MOOBLOSSOM_ENTITY, 16777215, 16777215, ItemSettings());
	public static final Block MAGENTA_MOOBLOSSOM_TULIP = new FlowerBlock(StatusEffects.FIRE_RESISTANCE, 4, HavenFlower.Settings());
	public static final EntityType<MoolipEntity> MOOLIP_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, MoolipEntity::new).dimensions(EntityDimensions.fixed(0.9F, 1.4F)).trackRangeBlocks(10).build();
	public static final Item MOOLIP_SPAWN_EGG = new SpawnEggItem(MOOLIP_ENTITY, 16777215, 16777215, ItemSettings());
	public static final EntityType<OrangeMooblossomEntity> ORANGE_MOOBLOSSOM_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, OrangeMooblossomEntity::new).dimensions(EntityDimensions.fixed(0.9F, 1.4F)).trackRangeBlocks(10).build();
	public static final Item ORANGE_MOOBLOSSOM_SPAWN_EGG = new SpawnEggItem(ORANGE_MOOBLOSSOM_ENTITY, 16777215, 16777215, ItemSettings());
	public static final Block ORANGE_MOOBLOSSOM_TULIP = new FlowerBlock(StatusEffects.FIRE_RESISTANCE, 4, HavenFlower.Settings());

	//Nether Mooshrooms
	public static final EntityType<CrimsonMooshroomEntity> CRIMSON_MOOSHROOM_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, CrimsonMooshroomEntity::new).dimensions(EntityDimensions.fixed(0.9F, 1.4F)).trackRangeBlocks(10).build();
	public static final Item CRIMSON_MOOSHROOM_SPAWN_EGG = new SpawnEggItem(CRIMSON_MOOSHROOM_ENTITY, 16777215, 16777215, ItemSettings());
	public static final EntityType<WarpedMooshroomEntity> WARPED_MOOSHROOM_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, WarpedMooshroomEntity::new).dimensions(EntityDimensions.fixed(0.9F, 1.4F)).trackRangeBlocks(10).build();
	public static final Item WARPED_MOOSHROOM_SPAWN_EGG = new SpawnEggItem(WARPED_MOOSHROOM_ENTITY, 16777215, 16777215, ItemSettings());

	//Flavored Milk
	public static final Item MILK_BOWL = new MilkBowlItem(ItemSettings().recipeRemainder(Items.BOWL).maxCount(16));
	public static final Item CHOCOLATE_MILK_BUCKET = new MilkBucketItem(ItemSettings().recipeRemainder(Items.BUCKET).maxCount(1));
	public static final Item CHOCOLATE_MILK_BOWL = new MilkBowlItem(ItemSettings().recipeRemainder(Items.BOWL).maxCount(16));
	public static final Item CHOCOLATE_MILK_BOTTLE = new MilkBottleItem(ItemSettings().recipeRemainder(Items.GLASS_BOTTLE).maxCount(16));
	public static final Item STRAWBERRY_MILK_BUCKET = new MilkBucketItem(ItemSettings().recipeRemainder(Items.BUCKET).maxCount(1));
	public static final Item STRAWBERRY_MILK_BOWL = new MilkBowlItem(ItemSettings().recipeRemainder(Items.BOWL).maxCount(16));
	public static final Item STRAWBERRY_MILK_BOTTLE = new MilkBottleItem(ItemSettings().recipeRemainder(Items.GLASS_BOTTLE).maxCount(16));
	public static final Item COFFEE_MILK_BUCKET = new CoffeeMilkBucketItem(ItemSettings().recipeRemainder(Items.BUCKET).maxCount(1));
	public static final Item COFFEE_MILK_BOWL = new CoffeeMilkBowlItem(ItemSettings().recipeRemainder(Items.BOWL).maxCount(16));
	public static final Item COFFEE_MILK_BOTTLE = new CoffeeMilkBottlelItem(ItemSettings().recipeRemainder(Items.GLASS_BOTTLE).maxCount(16));
	//Cheese
	public static final Block MILK_CAULDRON = new MilkCauldronBlock(0, AbstractBlock.Settings.copy(Blocks.WATER_CAULDRON));
	public static final Block COTTAGE_CHEESE_CAULDRON = new MilkCauldronBlock(1, AbstractBlock.Settings.copy(MILK_CAULDRON));
	public static final Block CHEESE_CAULDRON = new MilkCauldronBlock(2, AbstractBlock.Settings.copy(MILK_CAULDRON));
	public static final Block COTTAGE_CHEESE_BLOCK = new CottageCheeseBlock(AbstractBlock.Settings.of(Material.SOLID_ORGANIC, MapColor.OFF_WHITE).strength(1.0F).sounds(BlockSoundGroup.WART_BLOCK));
	public static final Item COTTAGE_CHEESE_BUCKET = new CottageCheeseBucketItem(COTTAGE_CHEESE_BLOCK, ItemSettings().maxCount(1).recipeRemainder(Items.BUCKET).food(new FoodComponent.Builder().hunger(2).saturationModifier(0.6F).build()));
	public static final Item COTTAGE_CHEESE_BOWL = new MushroomStewItem(ItemSettings().maxCount(1).recipeRemainder(Items.BOWL).food(new FoodComponent.Builder().hunger(2).saturationModifier(0.6F).build()));
	public static final HavenPair CHEESE_BLOCK = new HavenPair(new Block(AbstractBlock.Settings.of(Material.SOLID_ORGANIC, MapColor.YELLOW).strength(1.0F).sounds(BlockSoundGroup.WART_BLOCK)));
	public static final Item CHEESE = new Item(ItemSettings().food(new FoodComponent.Builder().hunger(2).saturationModifier(0.8F).build()));
	public static final Item GRILLED_CHEESE = new Item(ItemSettings().food(new FoodComponent.Builder().hunger(8).saturationModifier(0.8F).build()));
	//Wood Buckets
	public static final Item WOOD_BUCKET = new WoodBucketItem(Fluids.EMPTY, ItemSettings().maxCount(16));
	public static final Item WOOD_WATER_BUCKET = new WoodBucketItem(Fluids.WATER, ItemSettings().recipeRemainder(WOOD_BUCKET).maxCount(1));
	public static final Item WOOD_POWDER_SNOW_BUCKET = new WoodPowderSnowBucketItem(Blocks.POWDER_SNOW, SoundEvents.ITEM_BUCKET_EMPTY_POWDER_SNOW, ItemSettings().maxCount(1));
	public static final Item WOOD_MUD_BUCKET = new WoodBucketItem(STILL_MUD_FLUID, ItemSettings().recipeRemainder(WOOD_BUCKET).maxCount(1));
	public static final Item WOOD_BLOOD_BUCKET = new WoodBucketItem(STILL_BLOOD_FLUID, ItemSettings().recipeRemainder(WOOD_BUCKET).maxCount(1));
	public static final Item WOOD_MILK_BUCKET = new WoodMilkBucketItem(ItemSettings().recipeRemainder(WOOD_BUCKET).maxCount(1));
	public static final Item WOOD_CHOCOLATE_MILK_BUCKET = new WoodMilkBucketItem(ItemSettings().recipeRemainder(WOOD_BUCKET).maxCount(1));
	public static final Item WOOD_STRAWBERRY_MILK_BUCKET = new WoodMilkBucketItem(ItemSettings().recipeRemainder(WOOD_BUCKET).maxCount(1));
	public static final Item WOOD_COFFEE_MILK_BUCKET = new WoodCoffeeMilkBucketItem(ItemSettings().recipeRemainder(WOOD_BUCKET).maxCount(1));
	public static final Item WOOD_COTTAGE_CHEESE_BUCKET = new WoodCottageCheeseBucketItem(COTTAGE_CHEESE_BLOCK, ItemSettings().maxCount(1).recipeRemainder(Items.BUCKET).food(new FoodComponent.Builder().hunger(2).saturationModifier(0.6F).build()));
	//Copper Buckets
	public static final Item COPPER_BUCKET = new CopperBucketItem(Fluids.EMPTY, ItemSettings().maxCount(16));
	public static final Item COPPER_WATER_BUCKET = new CopperBucketItem(Fluids.WATER, ItemSettings().recipeRemainder(COPPER_BUCKET).maxCount(1));
	public static final Item COPPER_LAVA_BUCKET = new CopperBucketItem(Fluids.LAVA, ItemSettings().recipeRemainder(COPPER_BUCKET).maxCount(1));
	public static final Item COPPER_POWDER_SNOW_BUCKET = new CopperPowderSnowBucketItem(Blocks.POWDER_SNOW, SoundEvents.ITEM_BUCKET_EMPTY_POWDER_SNOW, ItemSettings().maxCount(1));
	public static final Item COPPER_MUD_BUCKET = new CopperBucketItem(STILL_MUD_FLUID, ItemSettings().recipeRemainder(COPPER_BUCKET).maxCount(1));
	public static final Item COPPER_BLOOD_BUCKET = new CopperBucketItem(STILL_BLOOD_FLUID, ItemSettings().recipeRemainder(COPPER_BUCKET).maxCount(1));
	public static final Item COPPER_MILK_BUCKET = new CopperMilkBucketItem(ItemSettings().recipeRemainder(COPPER_BUCKET).maxCount(1));
	public static final Item COPPER_CHOCOLATE_MILK_BUCKET = new CopperMilkBucketItem(ItemSettings().recipeRemainder(COPPER_BUCKET).maxCount(1));
	public static final Item COPPER_STRAWBERRY_MILK_BUCKET = new CopperMilkBucketItem(ItemSettings().recipeRemainder(COPPER_BUCKET).maxCount(1));
	public static final Item COPPER_COFFEE_MILK_BUCKET = new CopperCoffeeMilkBucketItem(ItemSettings().recipeRemainder(COPPER_BUCKET).maxCount(1));
	public static final Item COPPER_COTTAGE_CHEESE_BUCKET = new CopperCottageCheeseBucketItem(COTTAGE_CHEESE_BLOCK, ItemSettings().maxCount(1).recipeRemainder(Items.BUCKET).food(new FoodComponent.Builder().hunger(2).saturationModifier(0.6F).build()));
	//Cakes
	public static final HavenPair CHOCOLATE_CAKE = new HavenPair(new HavenCakeBlock(CakeFlavor.CHOCOLATE), ItemSettings().maxCount(1));
	public static final HavenPair STRAWBERRY_CAKE = new HavenPair(new HavenCakeBlock(CakeFlavor.STRAWBERRY), ItemSettings().maxCount(1));
	public static final HavenPair COFFEE_CAKE = new HavenPair(new HavenCakeBlock(CakeFlavor.COFFEE), ItemSettings().maxCount(1));
	public static final HavenPair CARROT_CAKE = new HavenPair(new HavenCakeBlock(CakeFlavor.CARROT), ItemSettings().maxCount(1));
	public static final HavenPair CONFETTI_CAKE = new HavenPair(new HavenCakeBlock(CakeFlavor.CONFETTI), ItemSettings().maxCount(1));
	//Candle Cakes
	public static final HavenCandleCakeBlock CHOCOLATE_CANDLE_CAKE = new HavenCandleCakeBlock(CakeFlavor.CHOCOLATE);
	public static final Map<DyeColor, HavenCandleCakeBlock> CHOCOLATE_CANDLE_CAKES = MapDyeColor((color) -> new HavenCandleCakeBlock(CakeFlavor.CHOCOLATE));
	public static final HavenCandleCakeBlock STRAWBERRY_CANDLE_CAKE = new HavenCandleCakeBlock(CakeFlavor.STRAWBERRY);
	public static final Map<DyeColor, HavenCandleCakeBlock> STRAWBERRY_CANDLE_CAKES = MapDyeColor((color) -> new HavenCandleCakeBlock(CakeFlavor.STRAWBERRY));
	public static final HavenCandleCakeBlock COFFEE_CANDLE_CAKE = new HavenCandleCakeBlock(CakeFlavor.COFFEE);
	public static final Map<DyeColor, HavenCandleCakeBlock> COFFEE_CANDLE_CAKES = MapDyeColor((color) -> new HavenCandleCakeBlock(CakeFlavor.COFFEE));
	public static final HavenCandleCakeBlock CARROT_CANDLE_CAKE = new HavenCandleCakeBlock(CakeFlavor.CARROT);
	public static final Map<DyeColor, HavenCandleCakeBlock> CARROT_CANDLE_CAKES = MapDyeColor((color) -> new HavenCandleCakeBlock(CakeFlavor.CARROT));
	public static final HavenCandleCakeBlock CONFETTI_CANDLE_CAKE = new HavenCandleCakeBlock(CakeFlavor.CONFETTI);
	public static final Map<DyeColor, HavenCandleCakeBlock> CONFETTI_CANDLE_CAKES = MapDyeColor((color) -> new HavenCandleCakeBlock(CakeFlavor.CONFETTI));

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
	public static final BoatMaterial CRIMSON_MATERIAL = new BoatMaterial("crimson", false, Blocks.CRIMSON_PLANKS);
	public static final BoatMaterial WARPED_MATERIAL = new BoatMaterial("warped", false, Blocks.WARPED_PLANKS);
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
		CASSIA_MATERIAL, CHERRY_MATERIAL, BAMBOO_MATERIAL, DRIED_BAMBOO_MATERIAL, MANGROVE_MATERIAL,
		//Metal
		COPPER_MATERIAL, IRON_MATERIAL, DARK_IRON_MATERIAL, GOLD_MATERIAL, NETHERITE_MATERIAL,
		//Gem
		AMETHYST_MATERIAL, EMERALD_MATERIAL, DIAMOND_MATERIAL,
		//Blood
		BLOOD_MATERIAL, DRIED_BLOOD_MATERIAL
	);
	public static final List<SignType> SIGN_TYPES = new ArrayList<SignType>();

	public static final Set<HavenPair> LEAVES = new HashSet<HavenPair>(Set.<HavenPair>of(
		PALE_CHERRY_LEAVES, PINK_CHERRY_LEAVES, WHITE_CHERRY_LEAVES, FLOWERING_CASSIA_LEAVES
	));
	public static final Set<HavenFlower> FLOWERS = new HashSet<HavenFlower>(Set.<HavenFlower>of(
		BUTTERCUP, PINK_DAISY, ROSE, BLUE_ROSE, MAGENTA_TULIP, MARIGOLD,
		PINK_ALLIUM, LAVENDER, HYDRANGEA
	));
	public static final Set<HavenPair> TALL_FLOWERS = new HashSet<HavenPair>(Set.<HavenPair>of(
		AMARANTH, TALL_ALLIUM, TALL_PINK_ALLIUM
	));

	public static final Set<Item> FLAVORED_MILK_BUCKETS = new HashSet<Item>(Set.<Item>of(
		CHOCOLATE_MILK_BUCKET, STRAWBERRY_MILK_BUCKET, COFFEE_MILK_BUCKET,
		WOOD_CHOCOLATE_MILK_BUCKET, WOOD_STRAWBERRY_MILK_BUCKET, WOOD_COFFEE_MILK_BUCKET,
		COPPER_CHOCOLATE_MILK_BUCKET, COPPER_STRAWBERRY_MILK_BUCKET, COPPER_COFFEE_MILK_BUCKET
	));

	public static final Set<Block> CONVERTIBLE_TO_MUD = new HashSet<Block>(Set.<Block>of(
		Blocks.DIRT, Blocks.COARSE_DIRT, Blocks.ROOTED_DIRT
	));

	public static final Set<Block> BLOOD_BLOCKS = new HashSet<Block>(Set.<Block>of(
		BLOOD_FLUID_BLOCK, BLOOD_BLOCK.BLOCK, DRIED_BLOOD_BLOCK.BLOCK
	));

	public static final Set<StatusEffect> MILK_IMMUNE_EFFECTS = new HashSet<StatusEffect>(Set.<StatusEffect>of(
		DETERIORATION_EFFECT
	));

	public static final Set<HavenBed> BEDS = new HashSet<HavenBed>(Set.<HavenBed>of(
		RAINBOW_BED
	));

	static {
		for(Integer owner : ANCHOR_MAP.keySet()) {
			ANCHOR_CORES.put(owner, new AnchorCoreItem(owner));
		}
		//Materials
		for(BaseMaterial material : MATERIALS) {
			if (material instanceof BundleProvider bundleProvider && material instanceof StrippedBundleProvider strippedBundle){
				StrippedBlockUtils.Register(bundleProvider.getBundle().BLOCK, strippedBundle.getStrippedBundle().BLOCK);
			}
			if (material instanceof LogProvider log && material instanceof StrippedLogProvider strippedLog) {
				StrippedBlockUtils.Register(log.getLog().BLOCK, strippedLog.getStrippedLog().BLOCK);
			}
			if (material instanceof WoodProvider wood && material instanceof StrippedWoodProvider strippedWood){
				StrippedBlockUtils.Register(wood.getWood().BLOCK, strippedWood.getStrippedWood().BLOCK);
			}
			if (material instanceof LeavesProvider leaves) LEAVES.add(leaves.getLeaves());
			if (material instanceof SignProvider sign) SIGN_TYPES.add(sign.getSign().TYPE);
		}
		//Flowers
		for(DyeColor color : COLORS) FLOWERS.add(CARNATIONS.get(color));
	}

	public static Boolean always(BlockState state, BlockView world, BlockPos pos) {
		return true;
	}
	public static Boolean always(BlockState state, BlockView world, BlockPos pos, EntityType<?> type) {
		return true;
	}
}
