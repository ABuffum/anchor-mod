package fun.wich;

import com.google.common.collect.ImmutableSet;
import com.nhoryzon.mc.farmersdelight.registry.EffectsRegistry;
import com.nhoryzon.mc.farmersdelight.registry.ItemsRegistry;
import fun.wich.advancement.ModCriteria;
import fun.wich.block.*;
import fun.wich.block.amethyst.AmethystSlabBlock;
import fun.wich.block.amethyst.AmethystStairsBlock;
import fun.wich.block.amethyst.AmethystWallBlock;
import fun.wich.block.archaeology.SuspiciousSandBlock;
import fun.wich.block.archaeology.SuspiciousSandBlockEntity;
import fun.wich.block.basic.*;
import fun.wich.block.container.ChiseledBookshelfBlockEntity;
import fun.wich.block.cryingobsidian.*;
import fun.wich.block.dust.sand.*;
import fun.wich.block.echo.*;
import fun.wich.block.flint.*;
import fun.wich.block.fluid.*;
import fun.wich.block.glass.*;
import fun.wich.block.gourd.*;
import fun.wich.block.MudBlock;
import fun.wich.block.mangrove.MangroveLeavesBlock;
import fun.wich.block.mangrove.MangroveRootsBlock;
import fun.wich.block.mangrove.PropaguleBlock;
import fun.wich.block.piglin.PiglinHeadBlock;
import fun.wich.block.piglin.PiglinHeadEntity;
import fun.wich.block.piglin.WallPiglinHeadBlock;
import fun.wich.block.plushie.*;
import fun.wich.block.ragdoll.RagdollBlock;
import fun.wich.block.ragdoll.RagdollBlockEntity;
import fun.wich.block.sculk.*;
import fun.wich.block.sign.HangingSignBlock;
import fun.wich.block.torch.ThickTorchBlock;
import fun.wich.block.torch.UnlitTorchBlock;
import fun.wich.block.torch.UnlitWallTorchBlock;
import fun.wich.client.render.gui.Generic1x1ContainerScreenHandler;
import fun.wich.client.render.gui.WoodcutterScreenHandler;
import fun.wich.command.*;
import fun.wich.container.*;
import fun.wich.damage.ModDamageSource;
import fun.wich.dispenser.BucketDispenserBehavior;
import fun.wich.dispenser.HorseArmorDispenserBehavior;
import fun.wich.dispenser.ModCarvedPumpkinBlockDispenserBehavior;
import fun.wich.dispenser.WearableDispenserBehavior;
import fun.wich.effect.*;
import fun.wich.enchantment.*;
import fun.wich.entity.IceChunkEntity;
import fun.wich.entity.ModActivities;
import fun.wich.entity.ModDataHandlers;
import fun.wich.entity.PurpleEyeOfEnderEntity;
import fun.wich.entity.ai.ModMemoryModules;
import fun.wich.entity.ai.sensor.FrogAttackablesSensor;
import fun.wich.entity.ai.sensor.IsInWaterSensor;
import fun.wich.entity.ai.sensor.ModSensorType;
import fun.wich.entity.ai.sensor.WardenAttackablesSensor;
import fun.wich.entity.blood.BloodType;
import fun.wich.entity.cloud.ConfettiCloudEntity;
import fun.wich.entity.hostile.illager.IceologerEntity;
import fun.wich.entity.hostile.illager.MageEntity;
import fun.wich.entity.hostile.RedPhantomEntity;
import fun.wich.entity.hostile.SlimeCreeperEntity;
import fun.wich.entity.hostile.piranha.PiranhaEntity;
import fun.wich.entity.hostile.skeleton.*;
import fun.wich.entity.hostile.slime.*;
import fun.wich.entity.hostile.spider.*;
import fun.wich.entity.hostile.warden.WardenEntity;
import fun.wich.entity.hostile.zombie.FrozenZombieEntity;
import fun.wich.entity.hostile.zombie.JungleZombieEntity;
import fun.wich.entity.hostile.zombie.SlimeZombieEntity;
import fun.wich.entity.neutral.golem.*;
import fun.wich.entity.passive.*;
import fun.wich.entity.passive.camel.CamelBrain;
import fun.wich.entity.passive.camel.CamelEntity;
import fun.wich.entity.passive.chicken.FancyChickenEntity;
import fun.wich.entity.passive.chicken.SlimeChickenEntity;
import fun.wich.entity.passive.cow.*;
import fun.wich.entity.passive.frog.*;
import fun.wich.entity.passive.sheep.*;
import fun.wich.entity.passive.sniffer.SnifferEntity;
import fun.wich.entity.projectile.*;
import fun.wich.entity.tnt.*;
import fun.wich.entity.variants.SnowGolemVariant;
import fun.wich.entity.vehicle.ChestBoatEntity;
import fun.wich.entity.vehicle.DispenserMinecartEntity;
import fun.wich.entity.vehicle.ModChestBoatEntity;
import fun.wich.event.ModGameEvent;
import fun.wich.event.ModPositionSourceTypes;
import fun.wich.gen.data.ModDatagen;
import fun.wich.gen.data.language.ModLanguageCache;
import fun.wich.gen.data.loot.DropTable;
import fun.wich.gen.data.tag.ModBiomeTags;
import fun.wich.gen.data.tag.ModBlockTags;
import fun.wich.gen.data.tag.ModItemTags;
import fun.wich.gen.feature.*;
import fun.wich.gen.feature.config.CherryTreeFeatureConfig;
import fun.wich.gen.feature.config.MangroveTreeFeatureConfig;
import fun.wich.gen.feature.config.SculkPatchFeatureConfig;
import fun.wich.gen.loot.ModLootTables;
import fun.wich.gen.loot.SetInstrumentLootFunction;
import fun.wich.gen.provider.ModSimpleBlockStateProvider;
import fun.wich.gen.world.ModBiomeCreator;
import fun.wich.haven.HavenMod;
import fun.wich.item.*;
import fun.wich.item.armor.ModDyeableArmorItem;
import fun.wich.item.armor.ModDyeableHorseArmorItem;
import fun.wich.item.basic.ModDyeItem;
import fun.wich.item.basic.ModMusicDiscItem;
import fun.wich.item.bucket.BucketProvider;
import fun.wich.item.bucket.CoffeeMilkBucketItem;
import fun.wich.item.bucket.ModBucketItem;
import fun.wich.item.bucket.ModMilkBucketItem;
import fun.wich.item.consumable.*;
import fun.wich.item.horn.WindHornItem;
import fun.wich.item.horn.goat.GoatHornItem;
import fun.wich.item.horn.goat.GoatHornSalveItem;
import fun.wich.item.pouch.ChickenPouchItem;
import fun.wich.item.pouch.EntityPouchItem;
import fun.wich.item.pouch.PouchItem;
import fun.wich.item.projectile.*;
import fun.wich.item.syringe.*;
import fun.wich.item.tool.GravityHammerItem;
import fun.wich.item.tool.ThunderingHammerItem;
import fun.wich.item.tool.echo.*;
import fun.wich.material.ModArmorMaterials;
import fun.wich.material.ModToolMaterials;
import fun.wich.mixins.world.BuiltinBiomesInvoker;
import fun.wich.origins.power.*;
import fun.wich.particle.ModParticleTypes;
import fun.wich.recipe.WoodcuttingRecipe;
import fun.wich.recipe.WoodcuttingRecipeSerializer;
import fun.wich.registry.*;
import fun.wich.ryft.RyftMod;
import fun.wich.sound.IdentifiedSounds;
import fun.wich.sound.ModBlockSoundGroups;
import fun.wich.sound.ModSoundEvents;
import fun.wich.trim.*;
import fun.wich.util.*;
import fun.wich.entity.vehicle.ModBoatEntity;

import fun.wich.util.dye.ModDyeColor;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.PowerTypeReference;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.*;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;

import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.fabricmc.fabric.api.transfer.v1.fluid.CauldronFluidContent;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.fluid.base.FullItemFluidStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.mixin.object.builder.PointOfInterestTypeAccessor;
import net.fabricmc.fabric.mixin.object.builder.SpawnRestrictionAccessor;
import net.fabricmc.fabric.mixin.object.builder.VillagerProfessionAccessor;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.*;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.block.dispenser.FallibleItemDispenserBehavior;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.block.dispenser.ProjectileDispenserBehavior;
import net.minecraft.block.entity.*;
import net.minecraft.block.enums.SculkSensorPhase;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.sensor.TemptationsSensor;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.FishEntity;
import net.minecraft.entity.passive.LlamaEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.fluid.*;
import net.minecraft.item.*;
import net.minecraft.loot.function.LootFunctionType;
import net.minecraft.particle.*;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.server.command.RaidCommand;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.*;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.*;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Position;
import net.minecraft.util.math.intprovider.*;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.event.GameEvent;
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
import net.minecraft.world.gen.treedecorator.BeehiveTreeDecorator;
import net.minecraft.world.gen.trunk.*;
import net.minecraft.world.poi.PointOfInterestType;

import java.util.*;

import static fun.wich.ModFactory.*;
import static fun.wich.ModFactory.MakeSignType;
import static fun.wich.registry.ModRegistry.*;

public class ModBase implements ModInitializer {
	public static final ModLanguageCache.en_us EN_US = new ModLanguageCache.en_us();
	public static final ModLanguageCache[] LANGUAGE_CACHES = new ModLanguageCache[] { EN_US };

	private static ItemStack GetItemGroupIcon() { return new ItemStack(HavenMod.ANCHOR); }
	public static final ItemGroup ITEM_GROUP = FabricItemGroupBuilder.build(ModId.ID("wichpack"), ModBase::GetItemGroupIcon);

	//<editor-fold desc="Acacia">
	public static final Item ACACIA_CHEST_BOAT = MakeChestBoat(BoatEntity.Type.ACACIA, BoatSettings(ItemGroup.TRANSPORTATION));
	public static final WallBlockContainer ACACIA_HANGING_SIGN = MakeHangingSign(SignType.ACACIA, Blocks.STRIPPED_ACACIA_LOG, SignItemSettings(ItemGroup.DECORATIONS)).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer ACACIA_IRON_HANGING_SIGN = MakeHangingSign(MakeSignType("acacia_iron"), Blocks.STRIPPED_ACACIA_LOG).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer ACACIA_GOLD_HANGING_SIGN = MakeHangingSign(MakeSignType("acacia_gold"), Blocks.STRIPPED_ACACIA_LOG).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer ACACIA_COPPER_HANGING_SIGN = MakeHangingSign(MakeSignType("acacia_copper"), Blocks.STRIPPED_ACACIA_LOG).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer ACACIA_EXPOSED_COPPER_HANGING_SIGN = MakeHangingSign(MakeSignType("acacia_exposed_copper"), Blocks.STRIPPED_ACACIA_LOG).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer ACACIA_WEATHERED_COPPER_HANGING_SIGN = MakeHangingSign(MakeSignType("acacia_weathered_copper"), Blocks.STRIPPED_ACACIA_LOG).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer ACACIA_OXIDIZED_COPPER_HANGING_SIGN = MakeHangingSign(MakeSignType("acacia_oxidized_copper"), Blocks.STRIPPED_ACACIA_LOG).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer ACACIA_NETHERITE_HANGING_SIGN = MakeHangingSign(MakeSignType("acacia_netherite"), Blocks.STRIPPED_ACACIA_LOG).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer ACACIA_BEEHIVE = MakeBeehive(MapColor.ORANGE).flammable(5, 20).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer ACACIA_BOOKSHELF = MakeBookshelf(Blocks.ACACIA_PLANKS).flammable(30, 20).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer ACACIA_CHISELED_BOOKSHELF = MakeChiseledBookshelf(MapColor.ORANGE).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer ACACIA_CRAFTING_TABLE = MakeCraftingTable(Blocks.ACACIA_PLANKS).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer ACACIA_BARREL = MakeBarrel(MapColor.ORANGE).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer ACACIA_LADDER = MakeLadder().blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer ACACIA_LECTERN = MakeLectern(Blocks.ACACIA_PLANKS).flammable(30, 20).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer ACACIA_WOODCUTTER = MakeWoodcutter(Blocks.ACACIA_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer ACACIA_POWDER_KEG = MakePowderKeg(ACACIA_BARREL).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer ACACIA_LOG_SLAB = MakeLogSlab(Blocks.ACACIA_LOG).flammable(5, 5).fuel(150).itemTag(ModItemTags.CHARRABLE_LOG_SLABS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer STRIPPED_ACACIA_LOG_SLAB = MakeLogSlab(Blocks.STRIPPED_ACACIA_LOG).flammable(5, 5).fuel(150).itemTag(ModItemTags.CHARRABLE_STRIPPED_LOG_SLABS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer ACACIA_WOOD_SLAB = MakeBarkSlab(Blocks.ACACIA_WOOD, Blocks.ACACIA_LOG).flammable(5, 5).fuel(150).itemTag(ModItemTags.CHARRABLE_WOOD_SLABS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer STRIPPED_ACACIA_WOOD_SLAB = MakeBarkSlab(Blocks.STRIPPED_ACACIA_WOOD, Blocks.STRIPPED_ACACIA_LOG).flammable(5, 5).fuel(150).itemTag(ModItemTags.CHARRABLE_STRIPPED_WOOD_SLABS).blockTag(BlockTags.AXE_MINEABLE);
	//Minecraft Dungeons
	public static final BlockContainer LIGHT_GREEN_ACACIA_LEAVES = MakeLeaves(Block.Settings.copy(Blocks.ACACIA_LEAVES).mapColor(MapColor.EMERALD_GREEN));
	public static final BlockContainer RED_ACACIA_LEAVES = MakeLeaves(Block.Settings.copy(Blocks.ACACIA_LEAVES).mapColor(MapColor.RED));
	public static final BlockContainer YELLOW_ACACIA_LEAVES = MakeLeaves(Block.Settings.copy(Blocks.ACACIA_LEAVES).mapColor(MapColor.ORANGE));
	//Misc
	public static final Item ACACIA_HAMMER = MakeHammer(ToolMaterials.WOOD, 7, -3.2F);
	//Torches
	public static final TorchContainer ACACIA_TORCH = MakeTorch(BlockSoundGroup.WOOD).torchModel();
	public static final TorchContainer ACACIA_SOUL_TORCH = MakeSoulTorch(BlockSoundGroup.WOOD).torchModel(ACACIA_TORCH);
	public static final TorchContainer ACACIA_ENDER_TORCH = MakeEnderTorch(BlockSoundGroup.WOOD).torchModel(ACACIA_TORCH);
	public static final TorchContainer UNDERWATER_ACACIA_TORCH = MakeUnderwaterTorch(BlockSoundGroup.WOOD).torchModel(ACACIA_TORCH);
	//Campfires
	public static final BlockContainer ACACIA_CAMPFIRE = MakeCampfire(MapColor.STONE_GRAY).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer ACACIA_SOUL_CAMPFIRE = MakeSoulCampfire(ACACIA_CAMPFIRE).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer ACACIA_ENDER_CAMPFIRE = MakeEnderCampfire(ACACIA_CAMPFIRE).blockTag(BlockTags.AXE_MINEABLE);
	//</editor-fold>
	//<editor-fold desc="Amethyst">
	public static final BlockContainer AMETHYST_STAIRS = BuildStairs(AmethystStairsBlock::new, Blocks.AMETHYST_BLOCK).stairsModel(Blocks.AMETHYST_BLOCK).blockTag(BlockTags.CRYSTAL_SOUND_BLOCKS).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer AMETHYST_SLAB = BuildSlab(AmethystSlabBlock::new, Blocks.AMETHYST_BLOCK).slabModel(Blocks.AMETHYST_BLOCK).blockTag(BlockTags.CRYSTAL_SOUND_BLOCKS).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer AMETHYST_WALL = BuildWall(AmethystWallBlock::new, Blocks.AMETHYST_BLOCK).wallModel(Blocks.AMETHYST_BLOCK).blockTag(BlockTags.CRYSTAL_SOUND_BLOCKS).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer AMETHYST_CRYSTAL_BLOCK = BuildBlock(new AmethystBlock(Block.Settings.of(Material.AMETHYST, MapColor.PURPLE).sounds(BlockSoundGroup.AMETHYST_CLUSTER).strength(1.5f, 1.5f).requiresTool().luminance(LUMINANCE_5))).cubeAllModel().blockTag(BlockTags.CRYSTAL_SOUND_BLOCKS).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer AMETHYST_CRYSTAL_STAIRS = BuildStairs(AmethystStairsBlock::new, AMETHYST_CRYSTAL_BLOCK).stairsModel(AMETHYST_CRYSTAL_BLOCK).blockTag(BlockTags.CRYSTAL_SOUND_BLOCKS).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer AMETHYST_CRYSTAL_SLAB = BuildSlab(AmethystSlabBlock::new, AMETHYST_CRYSTAL_BLOCK).slabModel(AMETHYST_CRYSTAL_BLOCK).blockTag(BlockTags.CRYSTAL_SOUND_BLOCKS).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer AMETHYST_CRYSTAL_WALL = BuildWall(AmethystWallBlock::new, AMETHYST_CRYSTAL_BLOCK).wallModel(AMETHYST_CRYSTAL_BLOCK).blockTag(BlockTags.CRYSTAL_SOUND_BLOCKS).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer AMETHYST_BRICKS = BuildBlock(new AmethystBlock(Block.Settings.of(Material.AMETHYST, MapColor.PURPLE).sounds(BlockSoundGroup.AMETHYST_BLOCK).strength(1.5f, 1.5f).requiresTool())).cubeAllModel().blockTag(BlockTags.CRYSTAL_SOUND_BLOCKS).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer AMETHYST_BRICK_STAIRS = BuildStairs(AmethystStairsBlock::new, AMETHYST_BRICKS).stairsModel(AMETHYST_BRICKS).blockTag(BlockTags.CRYSTAL_SOUND_BLOCKS).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer AMETHYST_BRICK_SLAB = BuildSlab(AmethystSlabBlock::new, AMETHYST_BRICKS).slabModel(AMETHYST_BRICKS).blockTag(BlockTags.CRYSTAL_SOUND_BLOCKS).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer AMETHYST_BRICK_WALL = BuildWall(AmethystWallBlock::new, AMETHYST_BRICKS).wallModel(AMETHYST_BRICKS).blockTag(BlockTags.CRYSTAL_SOUND_BLOCKS).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final Item AMETHYST_AXE = MakeAxe(ModToolMaterials.AMETHYST);
	public static final Item AMETHYST_HOE = MakeHoe(ModToolMaterials.AMETHYST);
	public static final Item AMETHYST_PICKAXE = MakePickaxe(ModToolMaterials.AMETHYST);
	public static final Item AMETHYST_SHOVEL = MakeShovel(ModToolMaterials.AMETHYST);
	public static final Item AMETHYST_SWORD = MakeSword(ModToolMaterials.AMETHYST);
	public static final Item AMETHYST_KNIFE = MakeKnife(ModToolMaterials.AMETHYST);
	public static final Item AMETHYST_HAMMER = MakeHammer(ModToolMaterials.AMETHYST);
	public static final Item AMETHYST_HELMET = MakeHelmet(ModArmorMaterials.AMETHYST);
	public static final Item AMETHYST_CHESTPLATE = MakeChestplate(ModArmorMaterials.AMETHYST);
	public static final Item AMETHYST_LEGGINGS = MakeLeggings(ModArmorMaterials.AMETHYST);
	public static final Item AMETHYST_BOOTS = MakeBoots(ModArmorMaterials.AMETHYST);
	public static final Item AMETHYST_HORSE_ARMOR = MakeHorseArmor(ModArmorMaterials.AMETHYST);
	//</editor-fold>
	//<editor-fold desc="Andesite">
	public static final BlockContainer ANDESITE_BRICKS = MakeBlock(Blocks.ANDESITE).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer ANDESITE_BRICK_STAIRS = MakeStairs(ANDESITE_BRICKS).stairsModel(ANDESITE_BRICKS).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer ANDESITE_BRICK_SLAB = MakeSlab(ANDESITE_BRICKS).slabModel(ANDESITE_BRICKS).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer ANDESITE_BRICK_WALL = MakeWall(ANDESITE_BRICKS).wallModel(ANDESITE_BRICKS).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer CHISELED_ANDESITE_BRICKS = MakeBlock(ANDESITE_BRICKS).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer ANDESITE_TILES = MakeBlock(ANDESITE_BRICKS).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer ANDESITE_TILE_STAIRS = MakeStairs(ANDESITE_TILES).stairsModel(ANDESITE_TILES).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer ANDESITE_TILE_SLAB = MakeSlab(ANDESITE_TILES).slabModel(ANDESITE_TILES).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer ANDESITE_TILE_WALL = MakeWall(ANDESITE_TILES).wallModel(ANDESITE_TILES).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer POLISHED_ANDESITE_WALL = MakeWall(Blocks.POLISHED_ANDESITE).wallModel(Blocks.POLISHED_ANDESITE).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer CUT_POLISHED_ANDESITE = MakeBlock(Blocks.POLISHED_ANDESITE).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer CUT_POLISHED_ANDESITE_STAIRS = MakeStairs(CUT_POLISHED_ANDESITE).stairsModel(CUT_POLISHED_ANDESITE).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer CUT_POLISHED_ANDESITE_SLAB = MakeSlab(CUT_POLISHED_ANDESITE).slabModel(CUT_POLISHED_ANDESITE).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer CUT_POLISHED_ANDESITE_WALL = MakeWall(CUT_POLISHED_ANDESITE).wallModel(CUT_POLISHED_ANDESITE).blockTag(BlockTags.PICKAXE_MINEABLE);
	//</editor-fold>
	//<editor-fold desc="Arrows">
	public static final ArrowContainer AMETHYST_ARROW = new ArrowContainer(AmethystArrowEntity::new, AmethystArrowEntity::new, AmethystArrowEntity::new).generatedItemModel();
	public static final ArrowContainer BLINDING_ARROW = new ArrowContainer(BlindingArrowEntity::new, BlindingArrowEntity::new, BlindingArrowEntity::new).generatedItemModel();
	public static final ArrowContainer BONE_ARROW = new ArrowContainer(BoneArrowEntity::new, BoneArrowEntity::new, BoneArrowEntity::new).generatedItemModel();
	public static final ArrowContainer CHORUS_ARROW = new ArrowContainer(ChorusArrowEntity::new, ChorusArrowEntity::new, ChorusArrowEntity::new).generatedItemModel();
	//</editor-fold>
	//<editor-fold desc="Basalt">
	public static final BlockContainer POLISHED_BASALT_SLAB = MakeSlab(Blocks.POLISHED_BASALT).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer SMOOTH_BASALT_STAIRS = MakeStairs(Blocks.SMOOTH_BASALT).stairsModel(Blocks.SMOOTH_BASALT).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer SMOOTH_BASALT_SLAB = MakeSlab(Blocks.SMOOTH_BASALT).slabModel(Blocks.SMOOTH_BASALT).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer SMOOTH_BASALT_WALL = MakeWall(Blocks.SMOOTH_BASALT).wallModel(Blocks.SMOOTH_BASALT).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer SMOOTH_BASALT_BRICKS = MakeBlock(Blocks.SMOOTH_BASALT).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer SMOOTH_BASALT_BRICK_STAIRS = MakeStairs(SMOOTH_BASALT_BRICKS).stairsModel(SMOOTH_BASALT_BRICKS).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer SMOOTH_BASALT_BRICK_SLAB = MakeSlab(SMOOTH_BASALT_BRICKS).slabModel(SMOOTH_BASALT_BRICKS).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer SMOOTH_BASALT_BRICK_WALL = MakeWall(SMOOTH_BASALT_BRICKS).wallModel(SMOOTH_BASALT_BRICKS).blockTag(BlockTags.PICKAXE_MINEABLE);
	//</editor-fold>
	//<editor-fold desc="Birch">
	public static final Item BIRCH_CHEST_BOAT = MakeChestBoat(BoatEntity.Type.BIRCH, BoatSettings(ItemGroup.TRANSPORTATION));
	public static final WallBlockContainer BIRCH_HANGING_SIGN = MakeHangingSign(SignType.BIRCH, Blocks.STRIPPED_BIRCH_LOG, SignItemSettings(ItemGroup.DECORATIONS)).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer BIRCH_IRON_HANGING_SIGN = MakeHangingSign(MakeSignType("birch_iron"), Blocks.STRIPPED_BIRCH_LOG).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer BIRCH_GOLD_HANGING_SIGN = MakeHangingSign(MakeSignType("birch_gold"), Blocks.STRIPPED_BIRCH_LOG).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer BIRCH_COPPER_HANGING_SIGN = MakeHangingSign(MakeSignType("birch_copper"), Blocks.STRIPPED_BIRCH_LOG).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer BIRCH_EXPOSED_COPPER_HANGING_SIGN = MakeHangingSign(MakeSignType("birch_exposed_copper"), Blocks.STRIPPED_BIRCH_LOG).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer BIRCH_WEATHERED_COPPER_HANGING_SIGN = MakeHangingSign(MakeSignType("birch_weathered_copper"), Blocks.STRIPPED_BIRCH_LOG).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer BIRCH_OXIDIZED_COPPER_HANGING_SIGN = MakeHangingSign(MakeSignType("birch_oxidized_copper"), Blocks.STRIPPED_BIRCH_LOG).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer BIRCH_NETHERITE_HANGING_SIGN = MakeHangingSign(MakeSignType("birch_netherite"), Blocks.STRIPPED_BIRCH_LOG).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer BIRCH_BEEHIVE = MakeBeehive(MapColor.PALE_YELLOW).flammable(5, 20).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer BIRCH_BOOKSHELF = MakeBookshelf(Blocks.BIRCH_PLANKS).flammable(30, 20).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer BIRCH_CHISELED_BOOKSHELF = MakeChiseledBookshelf(MapColor.PALE_YELLOW).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer BIRCH_CRAFTING_TABLE = MakeCraftingTable(Blocks.BIRCH_PLANKS).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer BIRCH_LADDER = MakeLadder().blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer BIRCH_WOODCUTTER = MakeWoodcutter(Blocks.BIRCH_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer BIRCH_BARREL = MakeBarrel(MapColor.PALE_YELLOW).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer BIRCH_LECTERN = MakeLectern(Blocks.BIRCH_PLANKS).flammable(30, 20).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer BIRCH_POWDER_KEG = MakePowderKeg(BIRCH_BARREL).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer BIRCH_LOG_SLAB = MakeLogSlab(Blocks.BIRCH_LOG).flammable(5, 5).fuel(150).itemTag(ModItemTags.CHARRABLE_LOG_SLABS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer STRIPPED_BIRCH_LOG_SLAB = MakeLogSlab(Blocks.STRIPPED_BIRCH_LOG).flammable(5, 5).fuel(150).itemTag(ModItemTags.CHARRABLE_STRIPPED_LOG_SLABS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer BIRCH_WOOD_SLAB = MakeBarkSlab(Blocks.BIRCH_WOOD, Blocks.BIRCH_LOG).flammable(5, 5).fuel(150).itemTag(ModItemTags.CHARRABLE_WOOD_SLABS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer STRIPPED_BIRCH_WOOD_SLAB = MakeBarkSlab(Blocks.STRIPPED_BIRCH_WOOD, Blocks.STRIPPED_BIRCH_LOG).flammable(5, 5).fuel(150).itemTag(ModItemTags.CHARRABLE_STRIPPED_WOOD_SLABS).blockTag(BlockTags.AXE_MINEABLE);
	//Minecraft Dungeons
	public static final BlockContainer LIGHT_GREEN_BIRCH_LEAVES = MakeLeaves(Block.Settings.copy(Blocks.BIRCH_LEAVES).mapColor(MapColor.EMERALD_GREEN));
	public static final BlockContainer RED_BIRCH_LEAVES = MakeLeaves(Block.Settings.copy(Blocks.BIRCH_LEAVES).mapColor(MapColor.RED));
	public static final BlockContainer YELLOW_BIRCH_LEAVES = MakeLeaves(Block.Settings.copy(Blocks.BIRCH_LEAVES).mapColor(MapColor.YELLOW));
	public static final BlockContainer WHITE_BIRCH_LEAVES = MakeLeaves(Block.Settings.copy(Blocks.BIRCH_LEAVES).mapColor(MapColor.LIGHT_BLUE_GRAY));
	//Misc
	public static final Item BIRCH_HAMMER = MakeHammer(ToolMaterials.WOOD, 7, -3.2F);
	//Torches
	public static final TorchContainer BIRCH_TORCH = MakeTorch(BlockSoundGroup.WOOD).torchModel();
	public static final TorchContainer BIRCH_SOUL_TORCH = MakeSoulTorch(BlockSoundGroup.WOOD).torchModel(BIRCH_TORCH);
	public static final TorchContainer BIRCH_ENDER_TORCH = MakeEnderTorch(BlockSoundGroup.WOOD).torchModel(BIRCH_TORCH);
	public static final TorchContainer UNDERWATER_BIRCH_TORCH = MakeUnderwaterTorch(BlockSoundGroup.WOOD).torchModel(BIRCH_TORCH);
	//Campfires
	public static final BlockContainer BIRCH_CAMPFIRE = MakeCampfire(MapColor.OFF_WHITE).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer BIRCH_SOUL_CAMPFIRE = MakeSoulCampfire(BIRCH_CAMPFIRE).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer BIRCH_ENDER_CAMPFIRE = MakeEnderCampfire(BIRCH_CAMPFIRE).blockTag(BlockTags.AXE_MINEABLE);
	//</editor-fold>
	//<editor-fold desc="Blackstone">
	public static final BlockContainer SMOOTH_CHISELED_POLISHED_BLACKSTONE = MakeBlock(Blocks.CHISELED_POLISHED_BLACKSTONE).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer POLISHED_BLACKSTONE_TILES = MakeBlock(Blocks.POLISHED_BLACKSTONE_BRICKS).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer POLISHED_BLACKSTONE_TILE_STAIRS = MakeStairs(POLISHED_BLACKSTONE_TILES).stairsModel(POLISHED_BLACKSTONE_TILES).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer POLISHED_BLACKSTONE_TILE_SLAB = MakeSlab(POLISHED_BLACKSTONE_TILES).slabModel(POLISHED_BLACKSTONE_TILES).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer POLISHED_BLACKSTONE_TILE_WALL = MakeWall(POLISHED_BLACKSTONE_TILES).wallModel(POLISHED_BLACKSTONE_TILES).blockTag(BlockTags.PICKAXE_MINEABLE);
	//</editor-fold>
	//<editor-fold desc="Blaze">
	public static final BlockContainer BLAZE_POWDER_BLOCK = BuildBlock(new BlazePowderBlock(Block.Settings.copy(Blocks.GOLD_BLOCK).luminance(LUMINANCE_15).requiresTool())).cubeAllModel().blockTag(ModBlockTags.INFLICTS_FIRE_DAMAGE).blockTag(ModBlockTags.SIZZLE_RAIN_BLOCKS).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final TorchContainer BLAZE_TORCH = MakeTorch(15, BlockSoundGroup.METAL, ParticleTypes.FLAME).torchModel();
	public static final TorchContainer BLAZE_SOUL_TORCH = MakeTorch(15, BlockSoundGroup.METAL, ParticleTypes.SOUL_FIRE_FLAME).torchModel(BLAZE_TORCH);
	public static final TorchContainer BLAZE_ENDER_TORCH = MakeTorch(15, BlockSoundGroup.METAL, ModParticleTypes.ENDER_FIRE_FLAME).torchModel(BLAZE_TORCH);
	public static final TorchContainer UNDERWATER_BLAZE_TORCH = MakeUnderwaterTorch(15, BlockSoundGroup.METAL).torchModel(BLAZE_TORCH);
	//</editor-fold>
	public static final BlockContainer BLUE_SHROOMLIGHT = MakeBlock(Block.Settings.copy(Blocks.SHROOMLIGHT).mapColor(MapColor.CYAN)).cubeAllModel().blockTag(BlockTags.HOE_MINEABLE);
	//<editor-fold desc="Blood">
	public static final BlockContainer BLOOD_BLOCK = MakeBlock(Block.Settings.of(Material.SOLID_ORGANIC, MapColor.RED).strength(1.0F).sounds(BlockSoundGroup.WART_BLOCK)).cubeAllModel();
	public static final BlockContainer BLOOD_FENCE = MakeFence(BLOOD_BLOCK).fenceModel(BLOOD_BLOCK);
	public static final BlockContainer BLOOD_PANE = BuildBlock(new ModPaneBlock(BLOOD_BLOCK)).paneModel(BLOOD_BLOCK);
	public static final BlockContainer BLOOD_STAIRS = MakeStairs(BLOOD_BLOCK).stairsModel(BLOOD_BLOCK);
	public static final BlockContainer BLOOD_SLAB = MakeSlab(BLOOD_BLOCK).slabModel(BLOOD_BLOCK);
	public static final BlockContainer BLOOD_WALL = MakeWall(BLOOD_BLOCK).wallModel(BLOOD_BLOCK);
	//Dried
	public static final BlockContainer DRIED_BLOOD_BLOCK = MakeBlock(BLOOD_BLOCK).cubeAllModel();
	public static final BlockContainer DRIED_BLOOD_FENCE = MakeFence(DRIED_BLOOD_BLOCK).fenceModel(DRIED_BLOOD_BLOCK);
	public static final BlockContainer DRIED_BLOOD_PANE = BuildBlock(new ModPaneBlock(DRIED_BLOOD_BLOCK)).paneModel(DRIED_BLOOD_BLOCK);
	public static final BlockContainer DRIED_BLOOD_STAIRS = MakeStairs(DRIED_BLOOD_BLOCK).stairsModel(DRIED_BLOOD_BLOCK);
	public static final BlockContainer DRIED_BLOOD_SLAB = MakeSlab(DRIED_BLOOD_BLOCK).slabModel(DRIED_BLOOD_BLOCK);
	public static final BlockContainer DRIED_BLOOD_WALL = MakeWall(DRIED_BLOOD_BLOCK).wallModel(DRIED_BLOOD_BLOCK);
	//Liquid
	public static final FlowableFluid STILL_BLOOD_FLUID = new BloodFluid.Still();
	public static final FlowableFluid FLOWING_BLOOD_FLUID = new BloodFluid.Flowing();
	public static final FluidBlock BLOOD_FLUID_BLOCK = new BloodFluidBlock(STILL_BLOOD_FLUID, FabricBlockSettings.copyOf(Blocks.WATER).mapColor(MapColor.RED));
	public static final Item BLOOD_BOTTLE = GeneratedItem(new BottledDrinkItem(ItemSettings().maxCount(16).recipeRemainder(Items.GLASS_BOTTLE)) {
		@Override
		public void OnDrink(ItemStack stack, LivingEntity user) {
			if (PowersUtil.Active(user, DrinkBloodPower.class)) user.heal(1);
			else user.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 200, 1));
		}
	});
	public static final BucketItem BLOOD_BUCKET = GeneratedItem(new ModBucketItem(STILL_BLOOD_FLUID, ItemSettings().recipeRemainder(Items.BUCKET).maxCount(1), BucketProvider.DEFAULT_PROVIDER));
	//</editor-fold>
	//<editor-fold desc="Boats">
	public static final EntityType<ModBoatEntity> MOD_BOAT_ENTITY = FabricEntityTypeBuilder.<ModBoatEntity>create(SpawnGroup.MISC, ModBoatEntity::new).dimensions(EntityDimensions.fixed(1.375F, 0.5625F)).trackRangeChunks(10).build();
	public static final EntityType<ChestBoatEntity> CHEST_BOAT_ENTITY = FabricEntityTypeBuilder.<ChestBoatEntity>create(SpawnGroup.MISC, ChestBoatEntity::new).dimensions(EntityDimensions.fixed(1.375f, 0.5625f)).trackRangeChunks(10).build();
	public static final EntityType<ModChestBoatEntity> MOD_CHEST_BOAT_ENTITY = FabricEntityTypeBuilder.<ModChestBoatEntity>create(SpawnGroup.MISC, ModChestBoatEntity::new).dimensions(EntityDimensions.fixed(1.375f, 0.5625f)).trackRangeChunks(10).build();
	//</editor-fold>
	//<editor-fold desc="Bone">
	public static final TorchContainer BONE_TORCH = MakeTorch(BlockSoundGroup.BONE);
	public static final TorchContainer BONE_SOUL_TORCH = MakeSoulTorch(BlockSoundGroup.BONE);
	public static final TorchContainer BONE_ENDER_TORCH = MakeEnderTorch(BlockSoundGroup.BONE);
	public static final TorchContainer UNDERWATER_BONE_TORCH = MakeUnderwaterTorch(BlockSoundGroup.BONE);
	public static final BlockContainer BONE_LADDER = MakeSpecialLadder(BlockSoundGroup.BONE).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer BONE_ROW = BuildBlock(new RowBlock(Block.Settings.of(Material.STONE, MapColor.PALE_YELLOW).requiresTool().strength(2.0F).sounds(BlockSoundGroup.BONE).nonOpaque())).blockTag(ModBlockTags.ROWS).itemTag(ModItemTags.ROWS).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer BONE_SLAB = MakeSlab(Blocks.BONE_BLOCK).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final Item BONE_KNIFE = MakeKnife(ModToolMaterials.BONE);
	public static final Item BONE_HAMMER = MakeHammer(ModToolMaterials.BONE);
	//</editor-fold>
	//<editor-fold desc="Bone Spider">
	public static final EntityType<BoneSpiderEntity> BONE_SPIDER_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, BoneSpiderEntity::new).dimensions(EntityDimensions.fixed(1.4f, 0.9f)).trackRangeChunks(8).build();
	public static final Item BONE_SPIDER_SPAWN_EGG = MakeSpawnEgg(BONE_SPIDER_ENTITY, 0x270F19, 0x632FB7);
	public static final Item BONE_SHARD = MakeGeneratedItem();
	public static final EntityType<BoneShardEntity> BONE_SHARD_PROJECTILE_ENTITY = FabricEntityTypeBuilder.<BoneShardEntity>create(SpawnGroup.MISC, BoneShardEntity::new).dimensions(EntityDimensions.fixed(0.5f, 0.5f)).trackRangeChunks(4).trackedUpdateRate(20).build();
	//</editor-fold>
	//<editor-fold desc="Books">
	public static final Item UNREADABLE_BOOK = ParentedItem(new Item(ItemSettings()), Items.WRITTEN_BOOK);
	public static final Item RED_BOOK = MakeGeneratedItem();
	public static final Item ORANGE_BOOK = MakeGeneratedItem();
	public static final Item YELLOW_BOOK = MakeGeneratedItem();
	public static final Item GREEN_BOOK = MakeGeneratedItem();
	public static final Item BLUE_BOOK = MakeGeneratedItem();
	public static final Item PURPLE_BOOK = MakeGeneratedItem();
	public static final Item GRAY_BOOK = MakeGeneratedItem();
	//</editor-fold>
	//<editor-fold desc="Bottled Confetti & Dragon's Breath">
	public static final Item BOTTLED_CONFETTI_ITEM = GeneratedItem(new BottledConfettiItem(ItemSettings().recipeRemainder(Items.GLASS_BOTTLE)));
	public static final EntityType<BottledConfettiEntity> BOTTLED_CONFETTI_ENTITY = FabricEntityTypeBuilder.<BottledConfettiEntity>create(SpawnGroup.MISC, BottledConfettiEntity::new).dimensions(EntityDimensions.fixed(0.25F, 0.25F)).trackRangeChunks(4).trackedUpdateRate(10).build();
	public static final EntityType<DroppedConfettiEntity> DROPPED_CONFETTI_ENTITY = FabricEntityTypeBuilder.<DroppedConfettiEntity>create(SpawnGroup.MISC, DroppedConfettiEntity::new).dimensions(EntityDimensions.fixed(0.25F, 0.25F)).trackRangeChunks(4).trackedUpdateRate(10).build();
	public static final EntityType<ConfettiCloudEntity> CONFETTI_CLOUD_ENTITY = FabricEntityTypeBuilder.<ConfettiCloudEntity>create(SpawnGroup.MISC, ConfettiCloudEntity::new).build();
	public static final EntityType<DroppedDragonBreathEntity> DROPPED_DRAGON_BREATH_ENTITY = FabricEntityTypeBuilder.<DroppedDragonBreathEntity>create(SpawnGroup.MISC, DroppedDragonBreathEntity::new).dimensions(EntityDimensions.fixed(0.25F, 0.25F)).trackRangeChunks(4).trackedUpdateRate(10).build();
	public static final EntityType<ConfettiCloudEntity> DRAGON_BREATH_CLOUD_ENTITY = FabricEntityTypeBuilder.<ConfettiCloudEntity>create(SpawnGroup.MISC, ConfettiCloudEntity::new).build();
	//</editor-fold>
	//<editor-fold desc="Bottled Lightning">
	public static final Item BOTTLED_LIGHTNING_ITEM = GeneratedItem(new BottledLightningItem(ItemSettings().recipeRemainder(Items.GLASS_BOTTLE)));
	public static final EntityType<BottledLightningEntity> BOTTLED_LIGHTNING_ENTITY = FabricEntityTypeBuilder.<BottledLightningEntity>create(SpawnGroup.MISC, BottledLightningEntity::new).dimensions(EntityDimensions.fixed(0.25F, 0.25F)).trackRangeChunks(4).trackedUpdateRate(10).build();
	//</editor-fold>
	//<editor-fold desc="Brews">
	/*//TODO: Apply burning effect https://minecraft.wiki/w/Minecraft_Dungeons:Burning
	public static final Item BURNING_BREW = GeneratedItem(new BottledDrinkItem(GlassBottledItemSettings()) {
		@Override
		public void OnDrink(ItemStack stack, LivingEntity user) {

		}
	});*/
	public static final Item DENSE_BREW = GeneratedItem(new BottledDrinkItem(GlassBottledItemSettings()) { @Override public void OnDrink(ItemStack stack, LivingEntity user) { user.addStatusEffect(new StatusEffectInstance(ModStatusEffects.DENSE_BREW, 3000)); } });
	/*//TODO: reduce damage by 1/2 for 15 seconds
	public static final Item OAKWOOD_BREW = GeneratedItem(new BottledDrinkItem(GlassBottledItemSettings()) {
		@Override
		public void OnDrink(ItemStack stack, LivingEntity user) {

		}
	});*/
	/*//TODO: Apply shadow form https://minecraft.wiki/w/Minecraft_Dungeons:Shadow_Brew
	public static final Item SHADOW_BREW = GeneratedItem(new BottledDrinkItem(GlassBottledItemSettings()) {
		@Override
		public void OnDrink(ItemStack stack, LivingEntity user) {

		}
	});*/
	//TODO: reduce damage by 1/6
	public static final Item SWEET_BREW = GeneratedItem(new BottledDrinkItem(GlassBottledItemSettings()) {
		@Override
		public void OnDrink(ItemStack stack, LivingEntity user) {
			user.addStatusEffect(new StatusEffectInstance(ModStatusEffects.FREEZING_RESISTANCE, 2400, 0));
		}
	});
	//</editor-fold>
	public static final Item BROOM = new BroomItem(0.25D, ItemSettings());
	//<editor-fold desc="Brush and Suspicious Sediment">
	public static final Item BRUSH = HandheldItem(new BrushItem(ItemSettings(ItemGroup.TOOLS).maxDamage(64)));
	public static final BlockContainer SUSPICIOUS_SAND = BuildBlock(new SuspiciousSandBlock(Blocks.SAND, Block.Settings.of(Material.AGGREGATE, MapColor.PALE_YELLOW).strength(0.25f).sounds(ModBlockSoundGroups.SUSPICIOUS_SAND), ModSoundEvents.ITEM_BRUSH_BRUSHING_SAND, ModSoundEvents.ITEM_BRUSH_BRUSHING_SAND_COMPLETE), DropTable.NOTHING, ItemSettings(ItemGroup.BUILDING_BLOCKS)).blockTag(BlockTags.SAND).itemTag(ItemTags.SAND).blockTag(BlockTags.SHOVEL_MINEABLE);
	public static final BlockContainer SUSPICIOUS_GRAVEL = BuildBlock(new SuspiciousSandBlock(Blocks.GRAVEL, Block.Settings.of(Material.AGGREGATE, MapColor.STONE_GRAY).strength(0.25f).sounds(ModBlockSoundGroups.SUSPICIOUS_GRAVEL), ModSoundEvents.ITEM_BRUSH_BRUSHING_GRAVEL, ModSoundEvents.ITEM_BRUSH_BRUSHING_GRAVEL_COMPLETE), DropTable.NOTHING, ItemSettings(ItemGroup.BUILDING_BLOCKS)).blockTag(BlockTags.SHOVEL_MINEABLE);
	public static final BlockEntityType<SuspiciousSandBlockEntity> SUSPICIOUS_BLOCK_ENTITY = FabricBlockEntityTypeBuilder.create(SuspiciousSandBlockEntity::new, SUSPICIOUS_SAND.asBlock(), SUSPICIOUS_GRAVEL.asBlock()).build();
	//</editor-fold>
	//<editor-fold desc="Calcite">
	public static final BlockContainer CALCITE_STAIRS = MakeStairs(Blocks.CALCITE).stairsModel(Blocks.CALCITE).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer CALCITE_SLAB = MakeSlab(Blocks.CALCITE).slabModel(Blocks.CALCITE).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer CALCITE_WALL = MakeWall(Blocks.CALCITE).wallModel(Blocks.CALCITE).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer SMOOTH_CALCITE = MakeBlock(Blocks.CALCITE).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer SMOOTH_CALCITE_STAIRS = MakeStairs(SMOOTH_CALCITE).stairsModel(SMOOTH_CALCITE).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer SMOOTH_CALCITE_SLAB = MakeSlab(SMOOTH_CALCITE).slabModel(SMOOTH_CALCITE).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer SMOOTH_CALCITE_WALL = MakeWall(SMOOTH_CALCITE).wallModel(SMOOTH_CALCITE).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer CALCITE_BRICKS = MakeBlock(Blocks.CALCITE).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer CALCITE_BRICK_STAIRS = MakeStairs(CALCITE_BRICKS).stairsModel(CALCITE_BRICKS).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer CALCITE_BRICK_SLAB = MakeSlab(CALCITE_BRICKS).slabModel(CALCITE_BRICKS).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer CALCITE_BRICK_WALL = MakeWall(CALCITE_BRICKS).wallModel(CALCITE_BRICKS).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer CALCITE_TILES = MakeBlock(CALCITE_BRICKS).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer CALCITE_TILE_STAIRS = MakeStairs(CALCITE_TILES).stairsModel(CALCITE_TILES).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer CALCITE_TILE_SLAB = MakeSlab(CALCITE_TILES).slabModel(CALCITE_TILES).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer CALCITE_TILE_WALL = MakeWall(CALCITE_TILES).wallModel(CALCITE_TILES).blockTag(BlockTags.PICKAXE_MINEABLE);
	//</editor-fold>
	//<editor-fold desc="Camel">
	public static final ModSensorType<TemptationsSensor> CAMEL_TEMPTATIONS_SENSOR = new ModSensorType<>("camel_temptations", () -> new TemptationsSensor(CamelBrain.getBreedingIngredient()));
	public static final EntityType<CamelEntity> CAMEL_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, CamelEntity::new).dimensions(EntityDimensions.fixed(1.7f, 2.375f)).trackRangeChunks(10).build();
	public static final Item CAMEL_SPAWN_EGG = MakeSpawnEgg(CAMEL_ENTITY, 16565097, 13341495, ItemSettings(ItemGroup.MISC));
	//</editor-fold>
	//<editor-fold desc="Cassia">
	public static final BlockContainer CASSIA_LOG = MakeLog(MapColor.BROWN).flammable(5, 5).fuel(300).blockTag(ModBlockTags.CASSIA_LOGS).itemTag(ModItemTags.CASSIA_LOGS).blockTag(ModBlockTags.OVERWORLD_NATURAL_LOGS).itemTag(ModItemTags.CHARRABLE_LOGS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer STRIPPED_CASSIA_LOG = MakeLog(MapColor.BROWN).flammable(5, 5).fuel(300).blockTag(ModBlockTags.CASSIA_LOGS).itemTag(ModItemTags.CASSIA_LOGS).itemTag(ModItemTags.CHARRABLE_STRIPPED_LOGS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer CASSIA_WOOD = MakeWood(MapColor.BROWN).flammable(5, 5).fuel(300).blockTag(ModBlockTags.CASSIA_LOGS).itemTag(ModItemTags.CASSIA_LOGS).itemTag(ModItemTags.CHARRABLE_WOODS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer STRIPPED_CASSIA_WOOD = MakeWood(MapColor.BROWN).flammable(5, 5).fuel(300).blockTag(ModBlockTags.CASSIA_LOGS).itemTag(ModItemTags.CASSIA_LOGS).itemTag(ModItemTags.CHARRABLE_STRIPPED_WOODS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer CASSIA_LEAVES = MakeLeaves(LeafBlockSettings(BlockSoundGroup.AZALEA_LEAVES));
	public static final BlockContainer FLOWERING_CASSIA_LEAVES = MakeLeaves(LeafBlockSettings(BlockSoundGroup.AZALEA_LEAVES)).blockTag(BlockTags.FLOWERS).itemTag(ItemTags.FLOWERS);
	public static final BlockContainer CASSIA_PLANKS = MakePlanks(MapColor.BROWN).flammable(5, 20).fuel(300).cubeAllModel().blockTag(BlockTags.PLANKS).itemTag(ItemTags.PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer CASSIA_STAIRS = MakeWoodStairs(CASSIA_PLANKS).flammable(5, 20).fuel(300).stairsModel(CASSIA_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer CASSIA_SLAB = MakeSlab(CASSIA_PLANKS).flammable(5, 20).fuel(150).slabModel(CASSIA_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer CASSIA_FENCE = MakeWoodFence(CASSIA_PLANKS).flammable(5, 20).fuel(300).fenceModel(CASSIA_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer CASSIA_FENCE_GATE = MakeWoodFenceGate(CASSIA_PLANKS).flammable(5, 20).fuel(300).fenceGateModel(CASSIA_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer CASSIA_DOOR = MakeWoodDoor(CASSIA_PLANKS).fuel(200).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer CASSIA_TRAPDOOR = MakeWoodTrapdoor(CASSIA_PLANKS).fuel(300).trapdoorModel(true).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer CASSIA_PRESSURE_PLATE = MakeWoodPressurePlate(CASSIA_PLANKS).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer CASSIA_BUTTON = MakeWoodButton().fuel(100).buttonModel(CASSIA_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final SignContainer CASSIA_SIGN = MakeSign("cassia", CASSIA_PLANKS, STRIPPED_CASSIA_LOG).fuel(200).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer CASSIA_IRON_HANGING_SIGN = MakeHangingSign(MakeSignType("cassia_iron"), STRIPPED_CASSIA_LOG).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer CASSIA_GOLD_HANGING_SIGN = MakeHangingSign(MakeSignType("cassia_gold"), STRIPPED_CASSIA_LOG).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer CASSIA_COPPER_HANGING_SIGN = MakeHangingSign(MakeSignType("cassia_copper"), STRIPPED_CASSIA_LOG).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer CASSIA_EXPOSED_COPPER_HANGING_SIGN = MakeHangingSign(MakeSignType("cassia_exposed_copper"), STRIPPED_CASSIA_LOG).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer CASSIA_WEATHERED_COPPER_HANGING_SIGN = MakeHangingSign(MakeSignType("cassia_weathered_copper"), STRIPPED_CASSIA_LOG).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer CASSIA_OXIDIZED_COPPER_HANGING_SIGN = MakeHangingSign(MakeSignType("cassia_oxidized_copper"), STRIPPED_CASSIA_LOG).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer CASSIA_NETHERITE_HANGING_SIGN = MakeHangingSign(MakeSignType("cassia_netherite"), STRIPPED_CASSIA_LOG).blockTag(BlockTags.AXE_MINEABLE);
	public static final BoatContainer CASSIA_BOAT = MakeBoat("cassia", CASSIA_PLANKS).fuel(1200);
	public static final PottedBlockContainer CASSIA_SAPLING = new PottedBlockContainer(new ModSaplingBlock(new CassiaSaplingGenerator(), Block.Settings.of(Material.PLANT, MapColor.PINK).noCollision().ticksRandomly().breakInstantly())).compostable(0.3f).dropSelf().pottedModel().blockTag(BlockTags.SAPLINGS).itemTag(ItemTags.SAPLINGS);
	//Extended
	public static final BlockContainer CASSIA_BEEHIVE = MakeBeehive(MapColor.BROWN).flammable(5, 20).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer CASSIA_BOOKSHELF = MakeBookshelf(CASSIA_PLANKS).flammable(30, 20).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer CASSIA_CHISELED_BOOKSHELF = MakeChiseledBookshelf(MapColor.BROWN).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer CASSIA_CRAFTING_TABLE = MakeCraftingTable(CASSIA_PLANKS).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer CASSIA_LADDER = MakeLadder().blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer CASSIA_WOODCUTTER = MakeWoodcutter(CASSIA_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer CASSIA_BARREL = MakeBarrel(MapColor.BROWN).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer CASSIA_LECTERN = MakeLectern(CASSIA_PLANKS).flammable(30, 20).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer CASSIA_POWDER_KEG = MakePowderKeg(CASSIA_BARREL).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer CASSIA_LOG_SLAB = MakeLogSlab(CASSIA_LOG).flammable(5, 5).fuel(150).itemTag(ModItemTags.CHARRABLE_LOG_SLABS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer STRIPPED_CASSIA_LOG_SLAB = MakeLogSlab(STRIPPED_CASSIA_LOG).flammable(5, 5).fuel(150).itemTag(ModItemTags.CHARRABLE_STRIPPED_LOG_SLABS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer CASSIA_WOOD_SLAB = MakeBarkSlab(CASSIA_WOOD, CASSIA_LOG).flammable(5, 5).fuel(150).itemTag(ModItemTags.CHARRABLE_WOOD_SLABS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer STRIPPED_CASSIA_WOOD_SLAB = MakeBarkSlab(STRIPPED_CASSIA_WOOD, STRIPPED_CASSIA_LOG).flammable(5, 5).fuel(150).itemTag(ModItemTags.CHARRABLE_STRIPPED_WOOD_SLABS).blockTag(BlockTags.AXE_MINEABLE);
	//Misc
	public static final Item CASSIA_HAMMER = MakeHammer(ToolMaterials.WOOD, 7, -3.2F);
	//Torches
	public static final TorchContainer CASSIA_TORCH = MakeTorch(BlockSoundGroup.WOOD).torchModel();
	public static final TorchContainer CASSIA_SOUL_TORCH = MakeSoulTorch(BlockSoundGroup.WOOD).torchModel(CASSIA_TORCH);
	public static final TorchContainer CASSIA_ENDER_TORCH = MakeEnderTorch(BlockSoundGroup.WOOD).torchModel(CASSIA_TORCH);
	public static final TorchContainer UNDERWATER_CASSIA_TORCH = MakeUnderwaterTorch(BlockSoundGroup.WOOD).torchModel(CASSIA_TORCH);
	//Campfires
	public static final BlockContainer CASSIA_CAMPFIRE = MakeCampfire(MapColor.BROWN).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer CASSIA_SOUL_CAMPFIRE = MakeSoulCampfire(CASSIA_CAMPFIRE).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer CASSIA_ENDER_CAMPFIRE = MakeEnderCampfire(CASSIA_CAMPFIRE).blockTag(BlockTags.AXE_MINEABLE);
	//</editor-fold>
	//<editor-fold desc="Charred">
	public static final BlockContainer CHARRED_LOG = MakeLog(MapColor.BLACK).blockTag(ModBlockTags.CHARRED_LOGS).itemTag(ModItemTags.CHARRED_LOGS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer STRIPPED_CHARRED_LOG = MakeLog(MapColor.BLACK).blockTag(ModBlockTags.CHARRED_LOGS).itemTag(ModItemTags.CHARRED_LOGS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer CHARRED_WOOD = MakeWood(MapColor.BLACK).blockTag(ModBlockTags.CHARRED_LOGS).itemTag(ModItemTags.CHARRED_LOGS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer STRIPPED_CHARRED_WOOD = MakeWood(MapColor.BLACK).blockTag(ModBlockTags.CHARRED_LOGS).itemTag(ModItemTags.CHARRED_LOGS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer CHARRED_PLANKS = MakePlanks(MapColor.BLACK).cubeAllModel().blockTag(BlockTags.PLANKS).itemTag(ItemTags.PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer CHARRED_STAIRS = MakeWoodStairs(CHARRED_PLANKS).stairsModel(CHARRED_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer CHARRED_SLAB = MakeSlab(CHARRED_PLANKS).slabModel(CHARRED_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer CHARRED_FENCE = MakeWoodFence(CHARRED_PLANKS).fenceModel(CHARRED_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer CHARRED_FENCE_GATE = MakeWoodFenceGate(CHARRED_PLANKS).fenceGateModel(CHARRED_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer CHARRED_DOOR = MakeWoodDoor(CHARRED_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer CHARRED_TRAPDOOR = MakeWoodTrapdoor(CHARRED_PLANKS).trapdoorModel(true).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer CHARRED_PRESSURE_PLATE = MakeWoodPressurePlate(CHARRED_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer CHARRED_BUTTON = MakeWoodButton().buttonModel(CHARRED_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final SignContainer CHARRED_SIGN = MakeSign("charred", CHARRED_PLANKS, STRIPPED_CHARRED_LOG).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer CHARRED_IRON_HANGING_SIGN = MakeHangingSign(MakeSignType("charred_iron"), STRIPPED_CHARRED_LOG).blockTag(BlockTags.NON_FLAMMABLE_WOOD).itemTag(ItemTags.NON_FLAMMABLE_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer CHARRED_GOLD_HANGING_SIGN = MakeHangingSign(MakeSignType("charred_gold"), STRIPPED_CHARRED_LOG).blockTag(BlockTags.NON_FLAMMABLE_WOOD).itemTag(ItemTags.NON_FLAMMABLE_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer CHARRED_COPPER_HANGING_SIGN = MakeHangingSign(MakeSignType("charred_copper"), STRIPPED_CHARRED_LOG).blockTag(BlockTags.NON_FLAMMABLE_WOOD).itemTag(ItemTags.NON_FLAMMABLE_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer CHARRED_EXPOSED_COPPER_HANGING_SIGN = MakeHangingSign(MakeSignType("charred_exposed_copper"), STRIPPED_CHARRED_LOG).blockTag(BlockTags.NON_FLAMMABLE_WOOD).itemTag(ItemTags.NON_FLAMMABLE_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer CHARRED_WEATHERED_COPPER_HANGING_SIGN = MakeHangingSign(MakeSignType("charred_weathered_copper"), STRIPPED_CHARRED_LOG).blockTag(BlockTags.NON_FLAMMABLE_WOOD).itemTag(ItemTags.NON_FLAMMABLE_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer CHARRED_OXIDIZED_COPPER_HANGING_SIGN = MakeHangingSign(MakeSignType("charred_oxidized_copper"), STRIPPED_CHARRED_LOG).blockTag(BlockTags.NON_FLAMMABLE_WOOD).itemTag(ItemTags.NON_FLAMMABLE_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer CHARRED_NETHERITE_HANGING_SIGN = MakeHangingSign(MakeSignType("charred_netherite"), STRIPPED_CHARRED_LOG).blockTag(BlockTags.NON_FLAMMABLE_WOOD).itemTag(ItemTags.NON_FLAMMABLE_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final BoatContainer CHARRED_BOAT = MakeBoat("charred", CHARRED_PLANKS); //TODO: Fireproof
	public static final BlockContainer CHARRED_BEEHIVE = MakeBeehive(MapColor.BLACK).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer CHARRED_BOOKSHELF = MakeBookshelf(CHARRED_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer CHARRED_CHISELED_BOOKSHELF = MakeChiseledBookshelf(MapColor.BLACK).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer CHARRED_CRAFTING_TABLE = MakeCraftingTable(CHARRED_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer CHARRED_LADDER = MakeLadder().blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer CHARRED_WOODCUTTER = MakeWoodcutter(CHARRED_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer CHARRED_BARREL = MakeBarrel(MapColor.BLACK).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer CHARRED_LECTERN = MakeLectern(CHARRED_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer CHARRED_POWDER_KEG = MakePowderKeg(CHARRED_BARREL).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer CHARRED_LOG_SLAB = MakeLogSlab(CHARRED_LOG).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer STRIPPED_CHARRED_LOG_SLAB = MakeLogSlab(STRIPPED_CHARRED_LOG).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer CHARRED_WOOD_SLAB = MakeBarkSlab(CHARRED_WOOD, CHARRED_LOG).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer STRIPPED_CHARRED_WOOD_SLAB = MakeBarkSlab(STRIPPED_CHARRED_WOOD, STRIPPED_CHARRED_LOG).blockTag(BlockTags.AXE_MINEABLE);
	//Misc
	public static final Item CHARRED_HAMMER = MakeHammer(ToolMaterials.WOOD, 7, -3.2F);
	//Torches
	public static final TorchContainer CHARRED_TORCH = MakeTorch(BlockSoundGroup.WOOD).torchModel();
	public static final TorchContainer CHARRED_SOUL_TORCH = MakeSoulTorch(BlockSoundGroup.WOOD).torchModel(CHARRED_TORCH);
	public static final TorchContainer CHARRED_ENDER_TORCH = MakeEnderTorch(BlockSoundGroup.WOOD).torchModel(CHARRED_TORCH);
	public static final TorchContainer UNDERWATER_CHARRED_TORCH = MakeUnderwaterTorch(BlockSoundGroup.WOOD).torchModel(CHARRED_TORCH);
	//Campfires
	public static final BlockContainer CHARRED_CAMPFIRE = MakeCampfire(MapColor.BLACK).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer CHARRED_SOUL_CAMPFIRE = MakeSoulCampfire(CHARRED_CAMPFIRE).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer CHARRED_ENDER_CAMPFIRE = MakeEnderCampfire(CHARRED_CAMPFIRE).blockTag(BlockTags.AXE_MINEABLE);
	//</editor-fold>
	//<editor-fold desc="Cherry">
	public static final BlockContainer CHERRY_LOG = MakeLog(MapColor.TERRACOTTA_WHITE, MapColor.TERRACOTTA_GRAY, ModBlockSoundGroups.CHERRY_WOOD, ItemSettings(ItemGroup.BUILDING_BLOCKS)).flammable(5, 5).fuel(300).blockTag(ModBlockTags.CHERRY_LOGS).itemTag(ModItemTags.CHERRY_LOGS).blockTag(ModBlockTags.OVERWORLD_NATURAL_LOGS).itemTag(ModItemTags.CHARRABLE_LOGS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer STRIPPED_CHERRY_LOG = MakeLog(MapColor.TERRACOTTA_WHITE, MapColor.TERRACOTTA_PINK, ModBlockSoundGroups.CHERRY_WOOD, ItemSettings(ItemGroup.BUILDING_BLOCKS)).flammable(5, 5).fuel(300).blockTag(ModBlockTags.CHERRY_LOGS).itemTag(ModItemTags.CHERRY_LOGS).itemTag(ModItemTags.CHARRABLE_STRIPPED_LOGS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer CHERRY_WOOD = MakeWood(MapColor.TERRACOTTA_GRAY, ModBlockSoundGroups.CHERRY_WOOD, ItemSettings(ItemGroup.BUILDING_BLOCKS)).flammable(5, 5).fuel(300).blockTag(ModBlockTags.CHERRY_LOGS).itemTag(ModItemTags.CHERRY_LOGS).itemTag(ModItemTags.CHARRABLE_WOODS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer STRIPPED_CHERRY_WOOD = MakeWood(MapColor.TERRACOTTA_PINK, ModBlockSoundGroups.CHERRY_WOOD, ItemSettings(ItemGroup.BUILDING_BLOCKS)).flammable(5, 5).fuel(300).blockTag(ModBlockTags.CHERRY_LOGS).itemTag(ModItemTags.CHERRY_LOGS).itemTag(ModItemTags.CHARRABLE_STRIPPED_WOODS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer CHERRY_LEAVES = BuildLeaves(new CherryLeavesBlock(Block.Settings.of(Material.LEAVES, MapColor.PINK).strength(0.2f).ticksRandomly().sounds(ModBlockSoundGroups.CHERRY_LEAVES).nonOpaque().allowsSpawning(ModFactory::canSpawnOnLeaves).suffocates(ModFactory::never).blockVision(ModFactory::never)), ItemSettings(ItemGroup.DECORATIONS)).blockTag(BlockTags.FLOWERS).itemTag(ItemTags.FLOWERS);
	public static final BlockContainer CHERRY_PLANKS = MakePlanks(MapColor.TERRACOTTA_WHITE, ModBlockSoundGroups.CHERRY_WOOD, ItemSettings(ItemGroup.BUILDING_BLOCKS)).flammable(5, 20).fuel(300).cubeAllModel().blockTag(BlockTags.PLANKS).itemTag(ItemTags.PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer CHERRY_STAIRS = MakeWoodStairs(CHERRY_PLANKS, ItemSettings(ItemGroup.BUILDING_BLOCKS)).flammable(5, 20).fuel(300).stairsModel(CHERRY_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer CHERRY_SLAB = MakeSlab(CHERRY_PLANKS, ItemSettings(ItemGroup.BUILDING_BLOCKS)).flammable(5, 20).fuel(150).slabModel(CHERRY_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer CHERRY_FENCE = MakeWoodFence(CHERRY_PLANKS, ItemSettings(ItemGroup.DECORATIONS)).flammable(5, 20).fuel(300).fenceModel(CHERRY_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer CHERRY_FENCE_GATE = MakeWoodFenceGate(CHERRY_PLANKS, ItemSettings(ItemGroup.REDSTONE), ModSoundEvents.BLOCK_CHERRY_WOOD_FENCE_GATE_OPEN, ModSoundEvents.BLOCK_CHERRY_WOOD_FENCE_GATE_CLOSE).flammable(5, 20).fuel(300).fenceGateModel(CHERRY_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer CHERRY_DOOR = MakeWoodDoor(CHERRY_PLANKS, ItemSettings(ItemGroup.REDSTONE), ModBlockSoundGroups.CHERRY_WOOD, ModSoundEvents.BLOCK_CHERRY_WOOD_DOOR_CLOSE, ModSoundEvents.BLOCK_CHERRY_WOOD_DOOR_OPEN).fuel(200).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer CHERRY_TRAPDOOR = MakeWoodTrapdoor(CHERRY_PLANKS, ItemSettings(ItemGroup.REDSTONE), ModBlockSoundGroups.CHERRY_WOOD, ModSoundEvents.BLOCK_CHERRY_WOOD_TRAPDOOR_CLOSE, ModSoundEvents.BLOCK_CHERRY_WOOD_TRAPDOOR_OPEN).fuel(300).trapdoorModel(true).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer CHERRY_PRESSURE_PLATE = MakeWoodPressurePlate(CHERRY_PLANKS, ItemSettings(ItemGroup.REDSTONE), ModBlockSoundGroups.CHERRY_WOOD, ModSoundEvents.BLOCK_CHERRY_WOOD_PRESSURE_PLATE_CLICK_ON, ModSoundEvents.BLOCK_CHERRY_WOOD_PRESSURE_PLATE_CLICK_OFF).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer CHERRY_BUTTON = MakeWoodButton(ItemSettings(ItemGroup.REDSTONE), ModBlockSoundGroups.CHERRY_WOOD, ModSoundEvents.BLOCK_CHERRY_WOOD_BUTTON_CLICK_ON, ModSoundEvents.BLOCK_CHERRY_WOOD_BUTTON_CLICK_OFF).fuel(100).buttonModel(CHERRY_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final SignContainer CHERRY_SIGN = MakeSign("minecraft:cherry", CHERRY_PLANKS, SignItemSettings(ItemGroup.DECORATIONS), STRIPPED_CHERRY_LOG, ModBlockSoundGroups.CHERRY_WOOD, ModBlockSoundGroups.CHERRY_WOOD_HANGING_SIGN).fuel(200).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer CHERRY_IRON_HANGING_SIGN = MakeHangingSign(MakeSignType("cherry_iron"), STRIPPED_CHERRY_LOG, ModBlockSoundGroups.CHERRY_WOOD_HANGING_SIGN).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer CHERRY_GOLD_HANGING_SIGN = MakeHangingSign(MakeSignType("cherry_gold"), STRIPPED_CHERRY_LOG, ModBlockSoundGroups.CHERRY_WOOD_HANGING_SIGN).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer CHERRY_COPPER_HANGING_SIGN = MakeHangingSign(MakeSignType("cherry_copper"), STRIPPED_CHERRY_LOG, ModBlockSoundGroups.CHERRY_WOOD_HANGING_SIGN).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer CHERRY_EXPOSED_COPPER_HANGING_SIGN = MakeHangingSign(MakeSignType("cherry_exposed_copper"), STRIPPED_CHERRY_LOG, ModBlockSoundGroups.CHERRY_WOOD_HANGING_SIGN).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer CHERRY_WEATHERED_COPPER_HANGING_SIGN = MakeHangingSign(MakeSignType("cherry_weathered_copper"), STRIPPED_CHERRY_LOG, ModBlockSoundGroups.CHERRY_WOOD_HANGING_SIGN).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer CHERRY_OXIDIZED_COPPER_HANGING_SIGN = MakeHangingSign(MakeSignType("cherry_oxidized_copper"), STRIPPED_CHERRY_LOG, ModBlockSoundGroups.CHERRY_WOOD_HANGING_SIGN).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer CHERRY_NETHERITE_HANGING_SIGN = MakeHangingSign(MakeSignType("cherry_netherite"), STRIPPED_CHERRY_LOG, ModBlockSoundGroups.CHERRY_WOOD_HANGING_SIGN).blockTag(BlockTags.AXE_MINEABLE);
	public static final BoatContainer CHERRY_BOAT = MakeBoat("minecraft:cherry", CHERRY_PLANKS, BoatSettings(ItemGroup.TRANSPORTATION)).fuel(1200);
	public static final PottedBlockContainer CHERRY_SAPLING = new PottedBlockContainer(new CherrySaplingBlock(new CherrySaplingGenerator(), Block.Settings.of(Material.PLANT, MapColor.PINK).noCollision().ticksRandomly().breakInstantly().sounds(ModBlockSoundGroups.CHERRY_SAPLING)), ItemSettings(ItemGroup.DECORATIONS)).compostable(0.3f).dropSelf().pottedModel().blockTag(BlockTags.SAPLINGS).itemTag(ItemTags.SAPLINGS);
	//Extended
	public static final BlockContainer CHERRY_BEEHIVE = MakeBeehive(MapColor.TERRACOTTA_WHITE, ModBlockSoundGroups.CHERRY_WOOD).flammable(5, 20).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer CHERRY_BOOKSHELF = MakeBookshelf(CHERRY_PLANKS, ModBlockSoundGroups.CHERRY_WOOD).flammable(30, 20).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer CHERRY_CHISELED_BOOKSHELF = MakeChiseledBookshelf(MapColor.TERRACOTTA_WHITE).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer CHERRY_CRAFTING_TABLE = MakeCraftingTable(CHERRY_PLANKS, ModBlockSoundGroups.CHERRY_WOOD).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer CHERRY_LADDER = MakeLadder().blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer CHERRY_WOODCUTTER = MakeWoodcutter(CHERRY_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer CHERRY_BARREL = MakeBarrel(MapColor.TERRACOTTA_WHITE, ModBlockSoundGroups.CHERRY_WOOD).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer CHERRY_LECTERN = MakeLectern(CHERRY_PLANKS, ModBlockSoundGroups.CHERRY_WOOD).flammable(30, 20).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer CHERRY_POWDER_KEG = MakePowderKeg(CHERRY_BARREL).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer CHERRY_LOG_SLAB = MakeLogSlab(CHERRY_LOG, ModBlockSoundGroups.CHERRY_WOOD).flammable(5, 5).fuel(150).itemTag(ModItemTags.CHARRABLE_LOG_SLABS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer STRIPPED_CHERRY_LOG_SLAB = MakeLogSlab(STRIPPED_CHERRY_LOG, ModBlockSoundGroups.CHERRY_WOOD).flammable(5, 5).fuel(150).itemTag(ModItemTags.CHARRABLE_STRIPPED_LOG_SLABS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer CHERRY_WOOD_SLAB = MakeBarkSlab(CHERRY_WOOD, CHERRY_LOG).flammable(5, 5).fuel(150).itemTag(ModItemTags.CHARRABLE_WOOD_SLABS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer STRIPPED_CHERRY_WOOD_SLAB = MakeBarkSlab(STRIPPED_CHERRY_WOOD, STRIPPED_CHERRY_LOG).flammable(5, 5).fuel(150).itemTag(ModItemTags.CHARRABLE_STRIPPED_WOOD_SLABS).blockTag(BlockTags.AXE_MINEABLE);
	//Misc
	public static final Item CHERRY_HAMMER = MakeHammer(ToolMaterials.WOOD, 7, -3.2F);
	//Torches
	public static final TorchContainer CHERRY_TORCH = MakeTorch(BlockSoundGroup.WOOD).torchModel();
	public static final TorchContainer CHERRY_SOUL_TORCH = MakeSoulTorch(BlockSoundGroup.WOOD).torchModel(CHERRY_TORCH);
	public static final TorchContainer CHERRY_ENDER_TORCH = MakeEnderTorch(BlockSoundGroup.WOOD).torchModel(CHERRY_TORCH);
	public static final TorchContainer UNDERWATER_CHERRY_TORCH = MakeUnderwaterTorch(BlockSoundGroup.WOOD).torchModel(CHERRY_TORCH);
	//Campfires
	public static final BlockContainer CHERRY_CAMPFIRE = MakeCampfire(MapColor.TERRACOTTA_GRAY, ModBlockSoundGroups.CHERRY_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer CHERRY_SOUL_CAMPFIRE = MakeSoulCampfire(CHERRY_CAMPFIRE, ModBlockSoundGroups.CHERRY_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer CHERRY_ENDER_CAMPFIRE = MakeEnderCampfire(CHERRY_CAMPFIRE, ModBlockSoundGroups.CHERRY_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	//</editor-fold>
	public static final BlockEntityType<ChiseledBookshelfBlockEntity> CHISELED_BOOKSHELF_ENTITY = FabricBlockEntityTypeBuilder.create(ChiseledBookshelfBlockEntity::new).build();
	public static final Item CHUM = GeneratedItem(new ChumItem(ItemSettings().food(FoodComponents.ROTTEN_FLESH)));
	//<editor-fold desc="Coal and Charcoal">
	public static final BlockContainer COAL_STAIRS = MakeStairs(Blocks.COAL_BLOCK).flammable(5, 10).fuel(12000).stairsModel(Blocks.COAL_BLOCK).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer COAL_SLAB = MakeSlab(Blocks.COAL_BLOCK).flammable(5, 20).fuel(8000).slabModel(Blocks.COAL_BLOCK).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer CHARCOAL_BLOCK = MakeBlock(Blocks.COAL_BLOCK).flammable(5, 5).fuel(16000).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer CHARCOAL_STAIRS = MakeStairs(CHARCOAL_BLOCK).flammable(5, 10).fuel(12000).stairsModel(CHARCOAL_BLOCK).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer CHARCOAL_SLAB = MakeSlab(CHARCOAL_BLOCK).flammable(5, 20).fuel(8000).slabModel(CHARCOAL_BLOCK).blockTag(BlockTags.PICKAXE_MINEABLE);
	//</editor-fold
	public static final BlockContainer COARSE_DIRT_SLAB = MakeSlab(Blocks.COARSE_DIRT).slabModel(Blocks.COARSE_DIRT).blockTag(BlockTags.SHOVEL_MINEABLE);
	public static final BlockContainer COCOA_BLOCK = MakeBlock(Block.Settings.of(Material.SOLID_ORGANIC, MapColor.BROWN).strength(0.8F).sounds(BlockSoundGroup.WOOD)).compostable(0.65f).cubeAllModel().blockTag(BlockTags.AXE_MINEABLE);
	//<editor-fold desc="Coffee">
	private static final Block COFFEE_PLANT_BLOCK = new CoffeePlantBlock(Block.Settings.of(Material.PLANT).ticksRandomly().noCollision().sounds(BlockSoundGroup.CROP));
	public static final BlockContainer COFFEE_PLANT = new BlockContainer(
			COFFEE_PLANT_BLOCK, GeneratedItem(new AliasedBlockItem(COFFEE_PLANT_BLOCK, ItemSettings().food(FoodSettings(2, 0.1F)
			.statusEffect(new StatusEffectInstance(StatusEffects.SPEED, 60, 0), 1.0F)
			.statusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 60, 0), 1.0F)
			.build())))
	).flammable(30, 60).compostable(0.65F).dropSelf();
	public static final Item COFFEE_BEANS = MakeGeneratedItem();
	public static final Item COFFEE = GeneratedItem(new BottledDrinkItemImpl(ItemSettings().food(new FoodComponent.Builder()
			.statusEffect(new StatusEffectInstance(StatusEffects.SPEED, 200, 0), 1.0F)
			.statusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 200, 0), 1.0F)
			.build())));
	public static final Item BLACK_COFFEE = GeneratedItem(new BottledDrinkItemImpl(ItemSettings().food(new FoodComponent.Builder()
			.statusEffect(new StatusEffectInstance(StatusEffects.SPEED, 200, 1), 1.0F)
			.statusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 200, 1), 1.0F)
			.build())));
	//</editor-fold>
	//<editor-fold desc="Concrete">
	public static final Map<DyeColor, BlockContainer> CONCRETE_STAIRS = ColorUtil.Map(color -> { Block block = ColorUtil.GetConcreteBlock(color); return MakeStairs(block).stairsModel(block).blockTag(BlockTags.PICKAXE_MINEABLE); });
	public static final Map<DyeColor, BlockContainer> CONCRETE_SLABS = ColorUtil.Map(color -> { Block block = ColorUtil.GetConcreteBlock(color); return MakeSlab(block).slabModel(block).blockTag(BlockTags.PICKAXE_MINEABLE); });
	public static final Map<DyeColor, BlockContainer> CONCRETE_WALLS = ColorUtil.Map(color -> { Block block = ColorUtil.GetConcreteBlock(color); return MakeWall(block).wallModel(block).blockTag(BlockTags.PICKAXE_MINEABLE); });
	//</editor-fold>
	//<editor-fold desc="Cow Variants">
	public static final EntityType<BlueMooshroomEntity> BLUE_MOOSHROOM_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, BlueMooshroomEntity::new).dimensions(EntityDimensions.fixed(0.9F, 1.4F)).trackRangeChunks(10).build();
	public static final Item BLUE_MOOSHROOM_SPAWN_EGG = MakeSpawnEgg(BLUE_MOOSHROOM_ENTITY, 0x0D6794, 0x929292);
	public static final EntityType<NetherMooshroomEntity> NETHER_MOOSHROOM_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, NetherMooshroomEntity::new).dimensions(EntityDimensions.fixed(0.9F, 1.4F)).trackRangeChunks(10).build();
	public static final Item NETHER_MOOSHROOM_SPAWN_EGG = MakeSpawnEgg(NETHER_MOOSHROOM_ENTITY, 0x871116, 0xFF6500);
	//Mooblooms
	public static final EntityType<MoobloomEntity> MOOBLOOM_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, MoobloomEntity::new).dimensions(EntityDimensions.fixed(0.9F, 1.4F)).trackRangeChunks(10).build();
	public static final Item MOOBLOOM_SPAWN_EGG = MakeSpawnEgg(MOOBLOOM_ENTITY, 0xFDD500, 0xFDF7BA);
	public static final EntityType<MoolipEntity> MOOLIP_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, MoolipEntity::new).dimensions(EntityDimensions.fixed(0.9F, 1.4F)).trackRangeChunks(10).build();
	public static final Item MOOLIP_SPAWN_EGG = MakeSpawnEgg(MOOLIP_ENTITY, 0xFFA9C2, 0xFFE4E4);
	public static final EntityType<MooblossomEntity> MOOBLOSSOM_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, MooblossomEntity::new).dimensions(EntityDimensions.fixed(0.9F, 1.4F)).trackRangeChunks(10).build();
	public static final Item MOOBLOSSOM_SPAWN_EGG = MakeSpawnEgg(MOOBLOSSOM_ENTITY, 0xDF317C, 0x994369);
	//</editor-fold>
	//<editor-fold desc="Crimson">
	public static final WallBlockContainer CRIMSON_HANGING_SIGN = MakeHangingSign(SignType.CRIMSON, Blocks.STRIPPED_CRIMSON_STEM, SignItemSettings(ItemGroup.DECORATIONS)).blockTag(BlockTags.NON_FLAMMABLE_WOOD).itemTag(ItemTags.NON_FLAMMABLE_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer CRIMSON_IRON_HANGING_SIGN = MakeHangingSign(MakeSignType("crimson_iron"), Blocks.STRIPPED_CRIMSON_STEM, ModBlockSoundGroups.NETHER_WOOD).blockTag(BlockTags.NON_FLAMMABLE_WOOD).itemTag(ItemTags.NON_FLAMMABLE_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer CRIMSON_GOLD_HANGING_SIGN = MakeHangingSign(MakeSignType("crimson_gold"), Blocks.STRIPPED_CRIMSON_STEM, ModBlockSoundGroups.NETHER_WOOD).blockTag(BlockTags.NON_FLAMMABLE_WOOD).itemTag(ItemTags.NON_FLAMMABLE_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer CRIMSON_COPPER_HANGING_SIGN = MakeHangingSign(MakeSignType("crimson_copper"), Blocks.STRIPPED_CRIMSON_STEM, ModBlockSoundGroups.NETHER_WOOD).blockTag(BlockTags.NON_FLAMMABLE_WOOD).itemTag(ItemTags.NON_FLAMMABLE_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer CRIMSON_EXPOSED_COPPER_HANGING_SIGN = MakeHangingSign(MakeSignType("crimson_exposed_copper"), Blocks.STRIPPED_CRIMSON_STEM, ModBlockSoundGroups.NETHER_WOOD).blockTag(BlockTags.NON_FLAMMABLE_WOOD).itemTag(ItemTags.NON_FLAMMABLE_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer CRIMSON_WEATHERED_COPPER_HANGING_SIGN = MakeHangingSign(MakeSignType("crimson_weathered_copper"), Blocks.STRIPPED_CRIMSON_STEM, ModBlockSoundGroups.NETHER_WOOD).blockTag(BlockTags.NON_FLAMMABLE_WOOD).itemTag(ItemTags.NON_FLAMMABLE_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer CRIMSON_OXIDIZED_COPPER_HANGING_SIGN = MakeHangingSign(MakeSignType("crimson_oxidized_copper"), Blocks.STRIPPED_CRIMSON_STEM, ModBlockSoundGroups.NETHER_WOOD).blockTag(BlockTags.NON_FLAMMABLE_WOOD).itemTag(ItemTags.NON_FLAMMABLE_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer CRIMSON_NETHERITE_HANGING_SIGN = MakeHangingSign(MakeSignType("crimson_netherite"), Blocks.STRIPPED_CRIMSON_STEM, ModBlockSoundGroups.NETHER_WOOD).blockTag(BlockTags.NON_FLAMMABLE_WOOD).itemTag(ItemTags.NON_FLAMMABLE_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer CRIMSON_BEEHIVE = MakeBeehive(MapColor.DULL_PINK, ModBlockSoundGroups.NETHER_WOOD).blockTag(BlockTags.NON_FLAMMABLE_WOOD).itemTag(ItemTags.NON_FLAMMABLE_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer CRIMSON_BOOKSHELF = MakeBookshelf(Blocks.CRIMSON_PLANKS, ModBlockSoundGroups.NETHER_WOOD).blockTag(BlockTags.NON_FLAMMABLE_WOOD).itemTag(ItemTags.NON_FLAMMABLE_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer CRIMSON_CHISELED_BOOKSHELF = MakeChiseledBookshelf(MapColor.DULL_PINK, ModBlockSoundGroups.NETHER_WOOD).blockTag(BlockTags.NON_FLAMMABLE_WOOD).itemTag(ItemTags.NON_FLAMMABLE_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer CRIMSON_CRAFTING_TABLE = MakeCraftingTable(Blocks.CRIMSON_PLANKS, ModBlockSoundGroups.NETHER_WOOD).blockTag(BlockTags.NON_FLAMMABLE_WOOD).itemTag(ItemTags.NON_FLAMMABLE_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer CRIMSON_LADDER = MakeLadder().blockTag(BlockTags.NON_FLAMMABLE_WOOD).itemTag(ItemTags.NON_FLAMMABLE_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer CRIMSON_WOODCUTTER = MakeWoodcutter(Blocks.CRIMSON_PLANKS).blockTag(BlockTags.NON_FLAMMABLE_WOOD).itemTag(ItemTags.NON_FLAMMABLE_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer CRIMSON_BARREL = MakeBarrel(MapColor.DULL_PINK, ModBlockSoundGroups.NETHER_WOOD).blockTag(BlockTags.NON_FLAMMABLE_WOOD).itemTag(ItemTags.NON_FLAMMABLE_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer CRIMSON_LECTERN = MakeLectern(Blocks.CRIMSON_PLANKS, ModBlockSoundGroups.NETHER_WOOD).blockTag(BlockTags.NON_FLAMMABLE_WOOD).itemTag(ItemTags.NON_FLAMMABLE_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer CRIMSON_POWDER_KEG = MakePowderKeg(CRIMSON_BARREL).blockTag(BlockTags.NON_FLAMMABLE_WOOD).itemTag(ItemTags.NON_FLAMMABLE_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer CRIMSON_STEM_SLAB = MakeLogSlab(Blocks.CRIMSON_STEM).blockTag(BlockTags.NON_FLAMMABLE_WOOD).itemTag(ItemTags.NON_FLAMMABLE_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer STRIPPED_CRIMSON_STEM_SLAB = MakeLogSlab(Blocks.STRIPPED_CRIMSON_STEM).blockTag(BlockTags.NON_FLAMMABLE_WOOD).itemTag(ItemTags.NON_FLAMMABLE_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer CRIMSON_HYPHAE_SLAB = MakeBarkSlab(Blocks.CRIMSON_HYPHAE, Blocks.CRIMSON_STEM).blockTag(BlockTags.NON_FLAMMABLE_WOOD).itemTag(ItemTags.NON_FLAMMABLE_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer STRIPPED_CRIMSON_HYPHAE_SLAB = MakeBarkSlab(Blocks.STRIPPED_CRIMSON_HYPHAE, Blocks.STRIPPED_CRIMSON_STEM).blockTag(BlockTags.NON_FLAMMABLE_WOOD).itemTag(ItemTags.NON_FLAMMABLE_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	//Misc
	public static final Item CRIMSON_HAMMER = MakeHammer(ToolMaterials.WOOD, 7, -3.2F);
	public static final BlockContainer NETHER_WART_SLAB = MakeSlab(Blocks.NETHER_WART_BLOCK).slabModel(Blocks.NETHER_WART_BLOCK).blockTag(BlockTags.HOE_MINEABLE);
	//Torches
	public static final TorchContainer CRIMSON_TORCH = MakeTorch(ModBlockSoundGroups.NETHER_WOOD).torchModel();
	public static final TorchContainer CRIMSON_SOUL_TORCH = MakeSoulTorch(ModBlockSoundGroups.NETHER_WOOD).torchModel(CRIMSON_TORCH);
	public static final TorchContainer CRIMSON_ENDER_TORCH = MakeEnderTorch(ModBlockSoundGroups.NETHER_WOOD).torchModel(CRIMSON_TORCH);
	public static final TorchContainer UNDERWATER_CRIMSON_TORCH = MakeUnderwaterTorch(ModBlockSoundGroups.NETHER_WOOD).torchModel(CRIMSON_TORCH);
	//Campfires
	public static final BlockContainer CRIMSON_CAMPFIRE = MakeCampfire(MapColor.DARK_CRIMSON, BlockSoundGroup.NETHER_STEM).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer CRIMSON_SOUL_CAMPFIRE = MakeSoulCampfire(CRIMSON_CAMPFIRE, BlockSoundGroup.NETHER_STEM).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer CRIMSON_ENDER_CAMPFIRE = MakeEnderCampfire(CRIMSON_CAMPFIRE, BlockSoundGroup.NETHER_STEM).blockTag(BlockTags.AXE_MINEABLE);
	//</editor-fold>
	//<editor-fold desc="Dark Iron">
	public static final Item DARK_IRON_NUGGET = MakeGeneratedItem();
	public static final Item DARK_IRON_INGOT = MakeGeneratedItem();
	public static final Item DARK_IRON_ROD = MakeHandheldItem();
	public static final TorchContainer DARK_IRON_TORCH = MakeTorch(14, BlockSoundGroup.METAL, ModParticleTypes.IRON_FLAME).torchModel();
	public static final TorchContainer DARK_IRON_SOUL_TORCH = MakeSoulTorch(BlockSoundGroup.METAL).torchModel(DARK_IRON_TORCH);
	public static final TorchContainer DARK_IRON_ENDER_TORCH = MakeEnderTorch(BlockSoundGroup.METAL).torchModel(DARK_IRON_TORCH);
	public static final TorchContainer UNDERWATER_DARK_IRON_TORCH = MakeUnderwaterTorch(BlockSoundGroup.METAL).torchModel(DARK_IRON_TORCH);
	public static final BlockContainer DARK_IRON_BLOCK = MakeBlock(Blocks.IRON_BLOCK).cubeAllModel().blockTag(BlockTags.NEEDS_STONE_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer DARK_IRON_BUTTON = MakeMetalButton().buttonModel(DARK_IRON_BLOCK).blockTag(BlockTags.NEEDS_STONE_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer HEAVY_CHAIN = MakeHeavyChain().blockTag(BlockTags.NEEDS_STONE_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer DARK_IRON_BARS = MakeBars().blockTag(BlockTags.NEEDS_STONE_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer DARK_IRON_STAIRS = MakeStairs(DARK_IRON_BLOCK).stairsModel(DARK_IRON_BLOCK).blockTag(BlockTags.NEEDS_STONE_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer DARK_IRON_SLAB = MakeSlab(DARK_IRON_BLOCK).slabModel(DARK_IRON_BLOCK).blockTag(BlockTags.NEEDS_STONE_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer DARK_IRON_WALL = MakeWall(DARK_IRON_BLOCK).wallModel(DARK_IRON_BLOCK).blockTag(BlockTags.NEEDS_STONE_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer DARK_IRON_DOOR = BuildMetalDoor(new ModDoorBlock(Block.Settings.copy(Blocks.IRON_DOOR))).blockTag(BlockTags.NEEDS_STONE_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer DARK_IRON_TRAPDOOR = MakeMetalTrapdoor(MapColor.IRON_GRAY, BlockSoundGroup.METAL, 3.0f).trapdoorModel(false).blockTag(BlockTags.NEEDS_STONE_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer DARK_IRON_BRICKS = MakeBlock(DARK_IRON_BLOCK).cubeAllModel().blockTag(BlockTags.NEEDS_STONE_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer DARK_IRON_BRICK_STAIRS = MakeStairs(DARK_IRON_BRICKS).stairsModel(DARK_IRON_BRICKS).blockTag(BlockTags.NEEDS_STONE_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer DARK_IRON_BRICK_SLAB = MakeSlab(DARK_IRON_BRICKS).slabModel(DARK_IRON_BRICKS).blockTag(BlockTags.NEEDS_STONE_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer DARK_IRON_BRICK_WALL = MakeWall(DARK_IRON_BRICKS).wallModel(DARK_IRON_BRICKS).blockTag(BlockTags.NEEDS_STONE_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer CUT_DARK_IRON = MakeBlock(DARK_IRON_BLOCK).cubeAllModel().blockTag(BlockTags.NEEDS_STONE_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer CUT_DARK_IRON_PILLAR = BuildBlock(new ModPillarBlock(CUT_DARK_IRON)).blockTag(BlockTags.NEEDS_STONE_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer CUT_DARK_IRON_STAIRS = MakeStairs(CUT_DARK_IRON).stairsModel(CUT_DARK_IRON).blockTag(BlockTags.NEEDS_STONE_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer CUT_DARK_IRON_SLAB = MakeSlab(CUT_DARK_IRON).slabModel(CUT_DARK_IRON).blockTag(BlockTags.NEEDS_STONE_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer CUT_DARK_IRON_WALL = MakeWall(CUT_DARK_IRON).wallModel(CUT_DARK_IRON).blockTag(BlockTags.NEEDS_STONE_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer DARK_HEAVY_WEIGHTED_PRESSURE_PLATE = BuildBlock(new ModWeightedPressurePlateBlock(150, Block.Settings.of(Material.METAL).requiresTool().noCollision().strength(0.5F).sounds(BlockSoundGroup.WOOD))).weightedPressurePlateModel(DARK_IRON_BLOCK).blockTag(BlockTags.PRESSURE_PLATES).blockTag(BlockTags.NEEDS_STONE_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	//Misc
	public static final Item DARK_IRON_HAMMER = MakeHammer(ModToolMaterials.DARK_IRON);
	public static final Item DARK_IRON_SHEARS = MakeShears(ModToolMaterials.DARK_IRON);
	private static final BucketContainer DARK_IRON_BUCKET_PROVIDER = new BucketContainer();
	public static final Item DARK_IRON_BUCKET = DARK_IRON_BUCKET_PROVIDER.getBucket();
	public static final Item DARK_IRON_WATER_BUCKET = DARK_IRON_BUCKET_PROVIDER.getWaterBucket();
	public static final Item DARK_IRON_LAVA_BUCKET = DARK_IRON_BUCKET_PROVIDER.getLavaBucket();
	public static final Item DARK_IRON_POWDER_SNOW_BUCKET = DARK_IRON_BUCKET_PROVIDER.getPowderSnowBucket();
	public static final Item DARK_IRON_BLOOD_BUCKET = DARK_IRON_BUCKET_PROVIDER.getBloodBucket();
	public static final Item DARK_IRON_MUD_BUCKET = DARK_IRON_BUCKET_PROVIDER.getMudBucket();
	public static final Item DARK_IRON_MILK_BUCKET = DARK_IRON_BUCKET_PROVIDER.getMilkBucket();
	public static final Item DARK_IRON_CHOCOLATE_MILK_BUCKET = DARK_IRON_BUCKET_PROVIDER.getChocolateMilkBucket();
	public static final Item DARK_IRON_COFFEE_MILK_BUCKET = DARK_IRON_BUCKET_PROVIDER.getCoffeeMilkBucket();
	public static final Item DARK_IRON_STRAWBERRY_MILK_BUCKET = DARK_IRON_BUCKET_PROVIDER.getStrawberryMilkBucket();
	public static final Item DARK_IRON_VANILLA_MILK_BUCKET = DARK_IRON_BUCKET_PROVIDER.getVanillaMilkBucket();
	//</editor-fold>
	//<editor-fold desc="Dark Oak">
	public static final Item DARK_OAK_CHEST_BOAT = MakeChestBoat(BoatEntity.Type.DARK_OAK, BoatSettings(ItemGroup.TRANSPORTATION));
	public static final WallBlockContainer DARK_OAK_HANGING_SIGN = MakeHangingSign(SignType.DARK_OAK, Blocks.STRIPPED_DARK_OAK_LOG, SignItemSettings(ItemGroup.DECORATIONS)).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer DARK_OAK_IRON_HANGING_SIGN = MakeHangingSign(MakeSignType("dark_oak_iron"), Blocks.STRIPPED_DARK_OAK_LOG).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer DARK_OAK_GOLD_HANGING_SIGN = MakeHangingSign(MakeSignType("dark_oak_gold"), Blocks.STRIPPED_DARK_OAK_LOG).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer DARK_OAK_COPPER_HANGING_SIGN = MakeHangingSign(MakeSignType("dark_oak_copper"), Blocks.STRIPPED_DARK_OAK_LOG).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer DARK_OAK_EXPOSED_COPPER_HANGING_SIGN = MakeHangingSign(MakeSignType("dark_oak_exposed_copper"), Blocks.STRIPPED_DARK_OAK_LOG).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer DARK_OAK_WEATHERED_COPPER_HANGING_SIGN = MakeHangingSign(MakeSignType("dark_oak_weathered_copper"), Blocks.STRIPPED_DARK_OAK_LOG).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer DARK_OAK_OXIDIZED_COPPER_HANGING_SIGN = MakeHangingSign(MakeSignType("dark_oak_oxidized_copper"), Blocks.STRIPPED_DARK_OAK_LOG).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer DARK_OAK_NETHERITE_HANGING_SIGN = MakeHangingSign(MakeSignType("dark_oak_netherite"), Blocks.STRIPPED_DARK_OAK_LOG).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer DARK_OAK_BEEHIVE = MakeBeehive(MapColor.BROWN).flammable(5, 20).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer DARK_OAK_BOOKSHELF = MakeBookshelf(Blocks.DARK_OAK_PLANKS).flammable(30, 20).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer DARK_OAK_CHISELED_BOOKSHELF = MakeChiseledBookshelf(MapColor.BROWN).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer DARK_OAK_CRAFTING_TABLE = MakeCraftingTable(Blocks.DARK_OAK_PLANKS).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer DARK_OAK_LADDER = MakeLadder().blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer DARK_OAK_WOODCUTTER = MakeWoodcutter(Blocks.DARK_OAK_PLANKS).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer DARK_OAK_BARREL = MakeBarrel(MapColor.BROWN).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer DARK_OAK_LECTERN = MakeLectern(Blocks.DARK_OAK_PLANKS).flammable(30, 20).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer DARK_OAK_POWDER_KEG = MakePowderKeg(DARK_OAK_BARREL).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer DARK_OAK_LOG_SLAB = MakeLogSlab(Blocks.DARK_OAK_LOG).flammable(5, 5).fuel(150).itemTag(ModItemTags.CHARRABLE_LOG_SLABS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer STRIPPED_DARK_OAK_LOG_SLAB = MakeLogSlab(Blocks.STRIPPED_DARK_OAK_LOG).flammable(5, 5).fuel(150).itemTag(ModItemTags.CHARRABLE_STRIPPED_LOG_SLABS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer DARK_OAK_WOOD_SLAB = MakeBarkSlab(Blocks.DARK_OAK_WOOD, Blocks.DARK_OAK_LOG).flammable(5, 5).fuel(150).itemTag(ModItemTags.CHARRABLE_WOOD_SLABS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer STRIPPED_DARK_OAK_WOOD_SLAB = MakeBarkSlab(Blocks.STRIPPED_DARK_OAK_WOOD, Blocks.STRIPPED_DARK_OAK_LOG).flammable(5, 5).fuel(150).itemTag(ModItemTags.CHARRABLE_STRIPPED_WOOD_SLABS).blockTag(BlockTags.AXE_MINEABLE);
	//Misc
	public static final Item DARK_OAK_HAMMER = MakeHammer(ToolMaterials.WOOD, 7, -3.2F);
	//Torches
	public static final TorchContainer DARK_OAK_TORCH = MakeTorch(BlockSoundGroup.WOOD).torchModel();
	public static final TorchContainer DARK_OAK_SOUL_TORCH = MakeSoulTorch(BlockSoundGroup.WOOD).torchModel(DARK_OAK_TORCH);
	public static final TorchContainer DARK_OAK_ENDER_TORCH = MakeEnderTorch(BlockSoundGroup.WOOD).torchModel(DARK_OAK_TORCH);
	public static final TorchContainer UNDERWATER_DARK_OAK_TORCH = MakeUnderwaterTorch(BlockSoundGroup.WOOD).torchModel(DARK_OAK_TORCH);
	//Campfires
	public static final BlockContainer DARK_OAK_CAMPFIRE = MakeCampfire(MapColor.BROWN).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer DARK_OAK_SOUL_CAMPFIRE = MakeSoulCampfire(DARK_OAK_CAMPFIRE).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer DARK_OAK_ENDER_CAMPFIRE = MakeEnderCampfire(DARK_OAK_CAMPFIRE).blockTag(BlockTags.AXE_MINEABLE);
	//</editor-fold>
	//<editor-fold desc="Deepslate">
	public static final BlockContainer REINFORCED_DEEPSLATE = BuildBlock(new Block(Block.Settings.of(Material.STONE, MapColor.DEEPSLATE_GRAY).sounds(BlockSoundGroup.DEEPSLATE).strength(55.0f, 1200.0f)), DropTable.NOTHING, ItemSettings(ItemGroup.BUILDING_BLOCKS)).blockTag(BlockTags.DRAGON_IMMUNE, BlockTags.WITHER_IMMUNE, BlockTags.FEATURES_CANNOT_REPLACE, ModBlockTags.PISTON_IMMOVABLE);
	public static final BlockContainer MOSSY_COBBLED_DEEPSLATE = MakeBlock(Blocks.COBBLED_DEEPSLATE).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer MOSSY_COBBLED_DEEPSLATE_STAIRS = MakeStairs(MOSSY_COBBLED_DEEPSLATE).stairsModel(MOSSY_COBBLED_DEEPSLATE).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer MOSSY_COBBLED_DEEPSLATE_SLAB = MakeSlab(MOSSY_COBBLED_DEEPSLATE).slabModel(MOSSY_COBBLED_DEEPSLATE).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer MOSSY_COBBLED_DEEPSLATE_WALL = MakeWall(MOSSY_COBBLED_DEEPSLATE).wallModel(MOSSY_COBBLED_DEEPSLATE).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer MOSSY_DEEPSLATE_BRICKS = MakeBlock(Blocks.DEEPSLATE_BRICKS).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer MOSSY_DEEPSLATE_BRICK_STAIRS = MakeStairs(MOSSY_DEEPSLATE_BRICKS).stairsModel(MOSSY_DEEPSLATE_BRICKS).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer MOSSY_DEEPSLATE_BRICK_SLAB = MakeSlab(MOSSY_DEEPSLATE_BRICKS).slabModel(MOSSY_DEEPSLATE_BRICKS).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer MOSSY_DEEPSLATE_BRICK_WALL = MakeWall(MOSSY_DEEPSLATE_BRICKS).wallModel(MOSSY_DEEPSLATE_BRICKS).blockTag(BlockTags.PICKAXE_MINEABLE);
	//</editor-fold>
	//<editor-fold desc="Diamond">
	public static final BlockContainer DIAMOND_STAIRS = MakeStairs(Blocks.DIAMOND_BLOCK).stairsModel(Blocks.DIAMOND_BLOCK).blockTag(BlockTags.NEEDS_IRON_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer DIAMOND_SLAB = MakeSlab(Blocks.DIAMOND_BLOCK).slabModel(Blocks.DIAMOND_BLOCK).blockTag(BlockTags.NEEDS_IRON_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer DIAMOND_WALL = MakeWall(Blocks.DIAMOND_BLOCK).wallModel(Blocks.DIAMOND_BLOCK).blockTag(BlockTags.NEEDS_IRON_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer DIAMOND_BRICKS = MakeBlock(Blocks.DIAMOND_BLOCK).cubeAllModel().blockTag(BlockTags.NEEDS_IRON_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer DIAMOND_BRICK_STAIRS = MakeStairs(DIAMOND_BRICKS).stairsModel(DIAMOND_BRICKS).blockTag(BlockTags.NEEDS_IRON_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer DIAMOND_BRICK_SLAB = MakeSlab(DIAMOND_BRICKS).slabModel(DIAMOND_BRICKS).blockTag(BlockTags.NEEDS_IRON_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer DIAMOND_BRICK_WALL = MakeWall(DIAMOND_BRICKS).wallModel(DIAMOND_BRICKS).blockTag(BlockTags.NEEDS_IRON_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final Item DIAMOND_HAMMER = MakeHammer(ToolMaterials.DIAMOND, 6, -3);
	//</editor-fold>
	//<editor-fold desc="Diorite">
	public static final BlockContainer DIORITE_BRICKS = MakeBlock(Blocks.DIORITE).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer DIORITE_BRICK_STAIRS = MakeStairs(DIORITE_BRICKS).stairsModel(DIORITE_BRICKS).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer DIORITE_BRICK_SLAB = MakeSlab(DIORITE_BRICKS).slabModel(DIORITE_BRICKS).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer DIORITE_BRICK_WALL = MakeWall(DIORITE_BRICKS).wallModel(DIORITE_BRICKS).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer CHISELED_DIORITE_BRICKS = MakeBlock(DIORITE_BRICKS).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer DIORITE_TILES = MakeBlock(DIORITE_BRICKS).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer DIORITE_TILE_STAIRS = MakeStairs(DIORITE_TILES).stairsModel(DIORITE_TILES).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer DIORITE_TILE_SLAB = MakeSlab(DIORITE_TILES).slabModel(DIORITE_TILES).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer DIORITE_TILE_WALL = MakeWall(DIORITE_TILES).wallModel(DIORITE_TILES).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer POLISHED_DIORITE_WALL = MakeWall(Blocks.POLISHED_DIORITE).wallModel(Blocks.POLISHED_DIORITE).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer CUT_POLISHED_DIORITE = MakeBlock(Blocks.POLISHED_DIORITE).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer CUT_POLISHED_DIORITE_STAIRS = MakeStairs(CUT_POLISHED_DIORITE).stairsModel(CUT_POLISHED_DIORITE).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer CUT_POLISHED_DIORITE_SLAB = MakeSlab(CUT_POLISHED_DIORITE).slabModel(CUT_POLISHED_DIORITE).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer CUT_POLISHED_DIORITE_WALL = MakeWall(CUT_POLISHED_DIORITE).wallModel(CUT_POLISHED_DIORITE).blockTag(BlockTags.PICKAXE_MINEABLE);
	//</editor-fold>
	//<editor-fold desc="Dogwood">
	public static final BlockContainer DOGWOOD_LOG = MakeLog(MapColor.RAW_IRON_PINK, MapColor.TERRACOTTA_LIGHT_GRAY).flammable(5, 5).fuel(300).blockTag(ModBlockTags.DOGWOOD_LOGS).itemTag(ModItemTags.DOGWOOD_LOGS).blockTag(ModBlockTags.OVERWORLD_NATURAL_LOGS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer STRIPPED_DOGWOOD_LOG = MakeLog(MapColor.RAW_IRON_PINK, MapColor.TERRACOTTA_PINK).flammable(5, 5).fuel(300).blockTag(ModBlockTags.DOGWOOD_LOGS).itemTag(ModItemTags.DOGWOOD_LOGS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer DOGWOOD_WOOD = MakeWood(MapColor.TERRACOTTA_LIGHT_GRAY).flammable(5, 5).fuel(300).blockTag(ModBlockTags.DOGWOOD_LOGS).itemTag(ModItemTags.DOGWOOD_LOGS).blockTag(BlockTags.AXE_MINEABLE).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer STRIPPED_DOGWOOD_WOOD = MakeWood(MapColor.TERRACOTTA_PINK).flammable(5, 5).fuel(300).blockTag(ModBlockTags.DOGWOOD_LOGS).itemTag(ModItemTags.DOGWOOD_LOGS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer PINK_DOGWOOD_LEAVES = MakeLeaves(LeafBlockSettings().mapColor(MapColor.PINK)).blockTag(BlockTags.FLOWERS).itemTag(ItemTags.FLOWERS);
	public static final BlockContainer PALE_DOGWOOD_LEAVES = MakeLeaves(LeafBlockSettings().mapColor(MapColor.DULL_PINK)).blockTag(BlockTags.FLOWERS).itemTag(ItemTags.FLOWERS);
	public static final BlockContainer WHITE_DOGWOOD_LEAVES = MakeLeaves(LeafBlockSettings().mapColor(MapColor.WHITE)).blockTag(BlockTags.FLOWERS).itemTag(ItemTags.FLOWERS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer DOGWOOD_PLANKS = MakePlanks(MapColor.RAW_IRON_PINK).flammable(5, 20).fuel(300).cubeAllModel().blockTag(BlockTags.PLANKS).itemTag(ItemTags.PLANKS);
	public static final BlockContainer DOGWOOD_STAIRS = MakeWoodStairs(DOGWOOD_PLANKS).flammable(5, 20).fuel(300).stairsModel(DOGWOOD_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer DOGWOOD_SLAB = MakeSlab(DOGWOOD_PLANKS).flammable(5, 20).fuel(150).slabModel(DOGWOOD_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer DOGWOOD_FENCE = MakeWoodFence(DOGWOOD_PLANKS).flammable(5, 20).fuel(300).fenceModel(DOGWOOD_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer DOGWOOD_FENCE_GATE = MakeWoodFenceGate(DOGWOOD_PLANKS).flammable(5, 20).fuel(300).fenceGateModel(DOGWOOD_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer DOGWOOD_DOOR = MakeWoodDoor(DOGWOOD_PLANKS).fuel(200).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer DOGWOOD_TRAPDOOR = MakeWoodTrapdoor(DOGWOOD_PLANKS).fuel(300).trapdoorModel(true).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer DOGWOOD_PRESSURE_PLATE = MakeWoodPressurePlate(DOGWOOD_PLANKS).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer DOGWOOD_BUTTON = MakeWoodButton().fuel(100).buttonModel(DOGWOOD_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final SignContainer DOGWOOD_SIGN = MakeSign("dogwood", DOGWOOD_PLANKS, STRIPPED_DOGWOOD_LOG).fuel(200).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer DOGWOOD_IRON_HANGING_SIGN = MakeHangingSign(MakeSignType("dogwood_iron"), STRIPPED_DOGWOOD_LOG).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer DOGWOOD_GOLD_HANGING_SIGN = MakeHangingSign(MakeSignType("dogwood_gold"), STRIPPED_DOGWOOD_LOG).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer DOGWOOD_COPPER_HANGING_SIGN = MakeHangingSign(MakeSignType("dogwood_copper"), STRIPPED_DOGWOOD_LOG).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer DOGWOOD_EXPOSED_COPPER_HANGING_SIGN = MakeHangingSign(MakeSignType("dogwood_exposed_copper"), STRIPPED_DOGWOOD_LOG).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer DOGWOOD_WEATHERED_COPPER_HANGING_SIGN = MakeHangingSign(MakeSignType("dogwood_weathered_copper"), STRIPPED_DOGWOOD_LOG).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer DOGWOOD_OXIDIZED_COPPER_HANGING_SIGN = MakeHangingSign(MakeSignType("dogwood_oxidized_copper"), STRIPPED_DOGWOOD_LOG).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer DOGWOOD_NETHERITE_HANGING_SIGN = MakeHangingSign(MakeSignType("dogwood_netherite"), STRIPPED_DOGWOOD_LOG).blockTag(BlockTags.AXE_MINEABLE);
	public static final BoatContainer DOGWOOD_BOAT = MakeBoat("dogwood", DOGWOOD_PLANKS).fuel(1200);
	public static final PottedBlockContainer DOGWOOD_SAPLING = new PottedBlockContainer(new ModSaplingBlock(new DogwoodSaplingGenerator(), Block.Settings.of(Material.PLANT, MapColor.WHITE).noCollision().ticksRandomly().breakInstantly())).compostable(0.3f).dropSelf().pottedModel().blockTag(BlockTags.SAPLINGS).itemTag(ItemTags.SAPLINGS);
	//Extended
	public static final BlockContainer DOGWOOD_BEEHIVE = MakeBeehive(MapColor.RAW_IRON_PINK).flammable(5, 20).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer DOGWOOD_BOOKSHELF = MakeBookshelf(DOGWOOD_PLANKS).flammable(30, 20).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer DOGWOOD_CHISELED_BOOKSHELF = MakeChiseledBookshelf(MapColor.RAW_IRON_PINK).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer DOGWOOD_CRAFTING_TABLE = MakeCraftingTable(DOGWOOD_PLANKS).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer DOGWOOD_LADDER = MakeLadder().blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer DOGWOOD_WOODCUTTER = MakeWoodcutter(DOGWOOD_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer DOGWOOD_BARREL = MakeBarrel(MapColor.RAW_IRON_PINK).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer DOGWOOD_LECTERN = MakeLectern(DOGWOOD_PLANKS).flammable(30, 20).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer DOGWOOD_POWDER_KEG = MakePowderKeg(DOGWOOD_BARREL).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer DOGWOOD_LOG_SLAB = MakeLogSlab(DOGWOOD_LOG).flammable(5, 5).fuel(150).itemTag(ModItemTags.CHARRABLE_LOG_SLABS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer STRIPPED_DOGWOOD_LOG_SLAB = MakeLogSlab(STRIPPED_DOGWOOD_LOG).flammable(5, 5).fuel(150).itemTag(ModItemTags.CHARRABLE_STRIPPED_LOG_SLABS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer DOGWOOD_WOOD_SLAB = MakeBarkSlab(DOGWOOD_WOOD, DOGWOOD_LOG).flammable(5, 5).fuel(150).itemTag(ModItemTags.CHARRABLE_WOOD_SLABS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer STRIPPED_DOGWOOD_WOOD_SLAB = MakeBarkSlab(STRIPPED_DOGWOOD_WOOD, STRIPPED_DOGWOOD_LOG).flammable(5, 5).fuel(150).itemTag(ModItemTags.CHARRABLE_STRIPPED_WOOD_SLABS).blockTag(BlockTags.AXE_MINEABLE);
	//Misc
	public static final Item DOGWOOD_HAMMER = MakeHammer(ToolMaterials.WOOD, 7, -3.2F);
	//Torches
	public static final TorchContainer DOGWOOD_TORCH = MakeTorch(BlockSoundGroup.WOOD).torchModel();
	public static final TorchContainer DOGWOOD_SOUL_TORCH = MakeSoulTorch(BlockSoundGroup.WOOD).torchModel(DOGWOOD_TORCH);
	public static final TorchContainer DOGWOOD_ENDER_TORCH = MakeEnderTorch(BlockSoundGroup.WOOD).torchModel(DOGWOOD_TORCH);
	public static final TorchContainer UNDERWATER_DOGWOOD_TORCH = MakeUnderwaterTorch(BlockSoundGroup.WOOD).torchModel(DOGWOOD_TORCH);
	//Campfires
	public static final BlockContainer DOGWOOD_CAMPFIRE = MakeCampfire(MapColor.TERRACOTTA_LIGHT_GRAY).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer DOGWOOD_SOUL_CAMPFIRE = MakeSoulCampfire(DOGWOOD_CAMPFIRE).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer DOGWOOD_ENDER_CAMPFIRE = MakeEnderCampfire(DOGWOOD_CAMPFIRE).blockTag(BlockTags.AXE_MINEABLE);
	//</editor-fold>
	//<editor-fold desc="Dripstone">
	public static final BlockContainer DRIPSTONE_STAIRS = MakeStairs(Blocks.DRIPSTONE_BLOCK).stairsModel(Blocks.DRIPSTONE_BLOCK).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer DRIPSTONE_SLAB = MakeSlab(Blocks.DRIPSTONE_BLOCK).slabModel(Blocks.DRIPSTONE_BLOCK).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer DRIPSTONE_WALL = MakeWall(Blocks.DRIPSTONE_BLOCK).wallModel(Blocks.DRIPSTONE_BLOCK).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer SMOOTH_DRIPSTONE = MakeBlock(Blocks.DRIPSTONE_BLOCK).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer SMOOTH_DRIPSTONE_STAIRS = MakeStairs(SMOOTH_DRIPSTONE).stairsModel(SMOOTH_DRIPSTONE).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer SMOOTH_DRIPSTONE_SLAB = MakeSlab(SMOOTH_DRIPSTONE).slabModel(SMOOTH_DRIPSTONE).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer SMOOTH_DRIPSTONE_WALL = MakeWall(SMOOTH_DRIPSTONE).wallModel(SMOOTH_DRIPSTONE).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer DRIPSTONE_BRICKS = MakeBlock(Blocks.DRIPSTONE_BLOCK).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer DRIPSTONE_BRICK_STAIRS = MakeStairs(DRIPSTONE_BRICKS).stairsModel(DRIPSTONE_BRICKS).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer DRIPSTONE_BRICK_SLAB = MakeSlab(DRIPSTONE_BRICKS).slabModel(DRIPSTONE_BRICKS).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer DRIPSTONE_BRICK_WALL = MakeWall(DRIPSTONE_BRICKS).wallModel(DRIPSTONE_BRICKS).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer CHISELED_DRIPSTONE_BRICKS = MakeBlock(DRIPSTONE_BRICKS).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer DRIPSTONE_TILES = MakeBlock(DRIPSTONE_BRICKS).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer DRIPSTONE_TILE_STAIRS = MakeStairs(DRIPSTONE_TILES).stairsModel(DRIPSTONE_TILES).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer DRIPSTONE_TILE_SLAB = MakeSlab(DRIPSTONE_TILES).slabModel(DRIPSTONE_TILES).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer DRIPSTONE_TILE_WALL = MakeWall(DRIPSTONE_TILES).wallModel(DRIPSTONE_TILES).blockTag(BlockTags.PICKAXE_MINEABLE);
	//</editor-fold>
	//<editor-fold desc="Dyes">
	public static final Map<DyeColor, BlockContainer> DYE_BLOCKS = ColorUtil.Map(color -> MakeBlock(DyeBlockSettings(color)).cubeAllModel());
	//<editor-fold desc="Beige">
	public static final Item BEIGE_DYE = GeneratedItem(new ModDyeItem(ModDyeColor.BEIGE, ItemSettings()));
	public static final BlockContainer BEIGE_DYE_BLOCK = MakeBlock(DyeBlockSettings(MapColor.PALE_YELLOW)).cubeAllModel();
	//Candles
	public static final BlockContainer BEIGE_CANDLE = MakeCandle(MapColor.PALE_YELLOW, 3);
	public static final Block BEIGE_CANDLE_CAKE = new ModCandleCakeBlock(4, () -> Blocks.CAKE, () -> Items.CAKE).drops(BEIGE_CANDLE.asBlock());
	//Concrete
	public static final BlockContainer BEIGE_CONCRETE = MakeBlock(Block.Settings.copy(Blocks.WHITE_CONCRETE).mapColor(MapColor.PALE_YELLOW)).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer BEIGE_CONCRETE_STAIRS = MakeStairs(BEIGE_CONCRETE).stairsModel(BEIGE_CONCRETE).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer BEIGE_CONCRETE_SLAB = MakeSlab(BEIGE_CONCRETE).slabModel(BEIGE_CONCRETE).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer BEIGE_CONCRETE_WALL = MakeWall(BEIGE_CONCRETE).wallModel(BEIGE_CONCRETE).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer BEIGE_CONCRETE_POWDER = BuildBlock(new ConcretePowderBlock(BEIGE_CONCRETE.asBlock(), Block.Settings.of(Material.AGGREGATE, DyeColor.RED).strength(0.5f).sounds(BlockSoundGroup.SAND).mapColor(MapColor.PALE_YELLOW))).cubeAllModel();
	//Terracotta
	public static final BlockContainer BEIGE_TERRACOTTA = MakeBlock(Block.Settings.copy(Blocks.WHITE_TERRACOTTA).mapColor(MapColor.PALE_YELLOW)).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer BEIGE_TERRACOTTA_STAIRS = MakeStairs(BEIGE_TERRACOTTA).stairsModel(BEIGE_TERRACOTTA).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer BEIGE_TERRACOTTA_SLAB = MakeSlab(BEIGE_TERRACOTTA).slabModel(BEIGE_TERRACOTTA).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer BEIGE_TERRACOTTA_WALL = MakeWall(BEIGE_TERRACOTTA).wallModel(BEIGE_TERRACOTTA).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer BEIGE_GLAZED_TERRACOTTA = BuildBlock(new GlazedTerracottaBlock(Block.Settings.of(Material.STONE, MapColor.PALE_YELLOW).requiresTool().strength(1.4f))).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer BEIGE_GLAZED_TERRACOTTA_SLAB = BuildSlab(new HorizontalFacingSlabBlock(BEIGE_GLAZED_TERRACOTTA)).blockTag(BlockTags.PICKAXE_MINEABLE);
	//Wool & Fleece
	public static final BlockContainer BEIGE_WOOL = MakeBlock(Block.Settings.copy(Blocks.WHITE_WOOL).mapColor(MapColor.PALE_YELLOW)).flammable(30, 60).fuel(100).blockTag(BlockTags.WOOL);
	public static final BlockContainer BEIGE_WOOL_SLAB = MakeSlab(BEIGE_WOOL).flammable(40, 40).fuel(50).slabModel(BEIGE_WOOL).blockTag(ModBlockTags.WOOL_SLABS).itemTag(ModItemTags.WOOL_SLABS);
	public static final BlockContainer BEIGE_CARPET = BuildBlock(new CarpetBlock(Block.Settings.copy(Blocks.WHITE_CARPET).mapColor(MapColor.PALE_YELLOW)))
			.flammable(60, 20).fuel(67).dispenser(new HorseArmorDispenserBehavior()::dispenseSilently)
			.blockTag(BlockTags.CARPETS).itemTag(ItemTags.CARPETS);
	public static final BlockContainer BEIGE_FLEECE = MakeBlock(BEIGE_WOOL).flammable(30, 60).fuel(100).blockTag(BlockTags.WOOL);
	public static final BlockContainer BEIGE_FLEECE_SLAB = MakeSlab(BEIGE_FLEECE).flammable(40, 40).fuel(50).slabModel(BEIGE_FLEECE).blockTag(ModBlockTags.WOOL_SLABS).itemTag(ModItemTags.WOOL_SLABS);
	public static final BlockContainer BEIGE_FLEECE_CARPET = BuildBlock(new CarpetBlock(Block.Settings.copy(Blocks.WHITE_CARPET).mapColor(MapColor.PALE_YELLOW)))
			.flammable(60, 20).fuel(67).dispenser(new HorseArmorDispenserBehavior()::dispenseSilently)
			.blockTag(BlockTags.CARPETS).itemTag(ItemTags.CARPETS).blockTag(ModBlockTags.FLEECE_CARPETS).itemTag(ModItemTags.FLEECE_CARPETS);
	//Glass
	public static final BlockContainer BEIGE_STAINED_GLASS = new BlockContainer(new ModStainedGlassBlock(Block.Settings.copy(Blocks.WHITE_STAINED_GLASS).mapColor(MapColor.PALE_YELLOW), ModDyeColor.BEIGE)).requireSilkTouch().cubeAllModel().blockTag(BlockTags.IMPERMEABLE);
	public static final BlockContainer BEIGE_STAINED_GLASS_PANE = new BlockContainer(new ModStainedGlassPaneBlock(Block.Settings.copy(Blocks.WHITE_STAINED_GLASS_PANE).mapColor(MapColor.PALE_YELLOW), ModDyeColor.BEIGE)).requireSilkTouch().paneModel(BEIGE_STAINED_GLASS).blockTag(BlockTags.IMPERMEABLE);
	public static final BlockContainer BEIGE_STAINED_GLASS_SLAB = BuildSlab(new ModStainedGlassSlabBlock(BEIGE_STAINED_GLASS, ModDyeColor.BEIGE)).blockTag(BlockTags.IMPERMEABLE);
	public static final BlockContainer BEIGE_STAINED_GLASS_TRAPDOOR = BuildBlock(new ModStainedGlassTrapdoorBlock(BEIGE_STAINED_GLASS, BEIGE_STAINED_GLASS_SLAB, ModDyeColor.BEIGE)).blockTag(BlockTags.TRAPDOORS).itemTag(ItemTags.TRAPDOORS);
	//</editor-fold>
	//<editor-fold desc="Burgundy">
	public static final Item BURGUNDY_DYE = GeneratedItem(new ModDyeItem(ModDyeColor.BURGUNDY, ItemSettings()));
	public static final BlockContainer BURGUNDY_DYE_BLOCK = MakeBlock(DyeBlockSettings(MapColor.DARK_RED)).cubeAllModel();
	//Candles
	public static final BlockContainer BURGUNDY_CANDLE = MakeCandle(MapColor.DARK_RED, 3);
	public static final Block BURGUNDY_CANDLE_CAKE = new ModCandleCakeBlock(4, () -> Blocks.CAKE, () -> Items.CAKE).drops(BURGUNDY_CANDLE.asBlock());
	//Concrete
	public static final BlockContainer BURGUNDY_CONCRETE = MakeBlock(Block.Settings.copy(Blocks.WHITE_CONCRETE).mapColor(MapColor.DARK_RED)).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer BURGUNDY_CONCRETE_STAIRS = MakeStairs(BURGUNDY_CONCRETE).stairsModel(BURGUNDY_CONCRETE).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer BURGUNDY_CONCRETE_SLAB = MakeSlab(BURGUNDY_CONCRETE).slabModel(BURGUNDY_CONCRETE).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer BURGUNDY_CONCRETE_WALL = MakeWall(BURGUNDY_CONCRETE).wallModel(BURGUNDY_CONCRETE).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer BURGUNDY_CONCRETE_POWDER = BuildBlock(new ConcretePowderBlock(BURGUNDY_CONCRETE.asBlock(), Block.Settings.of(Material.AGGREGATE, DyeColor.RED).strength(0.5f).sounds(BlockSoundGroup.SAND).mapColor(MapColor.DARK_RED))).cubeAllModel();
	//Terracotta
	public static final BlockContainer BURGUNDY_TERRACOTTA = MakeBlock(Block.Settings.copy(Blocks.WHITE_TERRACOTTA).mapColor(MapColor.DARK_RED)).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer BURGUNDY_TERRACOTTA_STAIRS = MakeStairs(BURGUNDY_TERRACOTTA).stairsModel(BURGUNDY_TERRACOTTA).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer BURGUNDY_TERRACOTTA_SLAB = MakeSlab(BURGUNDY_TERRACOTTA).slabModel(BURGUNDY_TERRACOTTA).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer BURGUNDY_TERRACOTTA_WALL = MakeWall(BURGUNDY_TERRACOTTA).wallModel(BURGUNDY_TERRACOTTA).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer BURGUNDY_GLAZED_TERRACOTTA = BuildBlock(new GlazedTerracottaBlock(Block.Settings.of(Material.STONE, MapColor.DARK_RED).requiresTool().strength(1.4f))).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer BURGUNDY_GLAZED_TERRACOTTA_SLAB = BuildSlab(new HorizontalFacingSlabBlock(BURGUNDY_GLAZED_TERRACOTTA)).blockTag(BlockTags.PICKAXE_MINEABLE);
	//Wool & Fleece
	public static final BlockContainer BURGUNDY_WOOL = MakeBlock(Block.Settings.copy(Blocks.WHITE_WOOL).mapColor(MapColor.DARK_RED)).flammable(30, 60).fuel(100).blockTag(BlockTags.WOOL);
	public static final BlockContainer BURGUNDY_WOOL_SLAB = MakeSlab(BURGUNDY_WOOL).flammable(40, 40).fuel(50).slabModel(BURGUNDY_WOOL).blockTag(ModBlockTags.WOOL_SLABS).itemTag(ModItemTags.WOOL_SLABS);
	public static final BlockContainer BURGUNDY_CARPET = BuildBlock(new CarpetBlock(Block.Settings.copy(Blocks.WHITE_CARPET).mapColor(MapColor.DARK_RED)))
			.flammable(60, 20).fuel(67).dispenser(new HorseArmorDispenserBehavior()::dispenseSilently)
			.blockTag(BlockTags.CARPETS).itemTag(ItemTags.CARPETS);
	public static final BlockContainer BURGUNDY_FLEECE = MakeBlock(BURGUNDY_WOOL).flammable(30, 60).fuel(100).blockTag(BlockTags.WOOL);
	public static final BlockContainer BURGUNDY_FLEECE_SLAB = MakeSlab(BURGUNDY_FLEECE).flammable(40, 40).fuel(50).slabModel(BURGUNDY_FLEECE).blockTag(ModBlockTags.WOOL_SLABS).itemTag(ModItemTags.WOOL_SLABS);
	public static final BlockContainer BURGUNDY_FLEECE_CARPET = BuildBlock(new CarpetBlock(Block.Settings.copy(Blocks.WHITE_CARPET).mapColor(MapColor.DARK_RED)))
			.flammable(60, 20).fuel(67).dispenser(new HorseArmorDispenserBehavior()::dispenseSilently)
			.blockTag(BlockTags.CARPETS).itemTag(ItemTags.CARPETS).blockTag(ModBlockTags.FLEECE_CARPETS).itemTag(ModItemTags.FLEECE_CARPETS);
	//Glass
	public static final BlockContainer BURGUNDY_STAINED_GLASS = new BlockContainer(new ModStainedGlassBlock(Block.Settings.copy(Blocks.WHITE_STAINED_GLASS).mapColor(MapColor.DARK_RED), ModDyeColor.BURGUNDY)).requireSilkTouch().cubeAllModel().blockTag(BlockTags.IMPERMEABLE);
	public static final BlockContainer BURGUNDY_STAINED_GLASS_PANE = new BlockContainer(new ModStainedGlassPaneBlock(Block.Settings.copy(Blocks.WHITE_STAINED_GLASS_PANE).mapColor(MapColor.DARK_RED), ModDyeColor.BURGUNDY)).requireSilkTouch().paneModel(BURGUNDY_STAINED_GLASS).blockTag(BlockTags.IMPERMEABLE);
	public static final BlockContainer BURGUNDY_STAINED_GLASS_SLAB = BuildSlab(new ModStainedGlassSlabBlock(BURGUNDY_STAINED_GLASS, ModDyeColor.BURGUNDY)).blockTag(BlockTags.IMPERMEABLE);
	public static final BlockContainer BURGUNDY_STAINED_GLASS_TRAPDOOR = BuildBlock(new ModStainedGlassTrapdoorBlock(BURGUNDY_STAINED_GLASS, BURGUNDY_STAINED_GLASS_SLAB, ModDyeColor.BURGUNDY)).blockTag(BlockTags.TRAPDOORS).itemTag(ItemTags.TRAPDOORS);
	//</editor-fold>
	//<editor-fold desc="Lavender">
	public static final Item LAVENDER_DYE = GeneratedItem(new ModDyeItem(ModDyeColor.LAVENDER, ItemSettings()));
	public static final BlockContainer LAVENDER_DYE_BLOCK = MakeBlock(DyeBlockSettings(MapColor.PALE_PURPLE)).cubeAllModel();
	//Candles
	public static final BlockContainer LAVENDER_CANDLE = MakeCandle(MapColor.PALE_PURPLE, 3);
	public static final Block LAVENDER_CANDLE_CAKE = new ModCandleCakeBlock(4, () -> Blocks.CAKE, () -> Items.CAKE).drops(LAVENDER_CANDLE.asBlock());
	//Concrete
	public static final BlockContainer LAVENDER_CONCRETE = MakeBlock(Block.Settings.copy(Blocks.WHITE_CONCRETE).mapColor(MapColor.PALE_PURPLE)).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer LAVENDER_CONCRETE_STAIRS = MakeStairs(LAVENDER_CONCRETE).stairsModel(LAVENDER_CONCRETE).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer LAVENDER_CONCRETE_SLAB = MakeSlab(LAVENDER_CONCRETE).slabModel(LAVENDER_CONCRETE).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer LAVENDER_CONCRETE_WALL = MakeWall(LAVENDER_CONCRETE).wallModel(LAVENDER_CONCRETE).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer LAVENDER_CONCRETE_POWDER = BuildBlock(new ConcretePowderBlock(LAVENDER_CONCRETE.asBlock(), Block.Settings.of(Material.AGGREGATE, DyeColor.RED).strength(0.5f).sounds(BlockSoundGroup.SAND).mapColor(MapColor.PALE_PURPLE))).cubeAllModel();
	//Terracotta
	public static final BlockContainer LAVENDER_TERRACOTTA = MakeBlock(Block.Settings.copy(Blocks.WHITE_TERRACOTTA).mapColor(MapColor.PALE_PURPLE)).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer LAVENDER_TERRACOTTA_STAIRS = MakeStairs(LAVENDER_TERRACOTTA).stairsModel(LAVENDER_TERRACOTTA).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer LAVENDER_TERRACOTTA_SLAB = MakeSlab(LAVENDER_TERRACOTTA).slabModel(LAVENDER_TERRACOTTA).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer LAVENDER_TERRACOTTA_WALL = MakeWall(LAVENDER_TERRACOTTA).wallModel(LAVENDER_TERRACOTTA).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer LAVENDER_GLAZED_TERRACOTTA = BuildBlock(new GlazedTerracottaBlock(Block.Settings.of(Material.STONE, MapColor.PALE_PURPLE).requiresTool().strength(1.4f))).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer LAVENDER_GLAZED_TERRACOTTA_SLAB = BuildSlab(new HorizontalFacingSlabBlock(LAVENDER_GLAZED_TERRACOTTA)).blockTag(BlockTags.PICKAXE_MINEABLE);
	//Wool & Fleece
	public static final BlockContainer LAVENDER_WOOL = MakeBlock(Block.Settings.copy(Blocks.WHITE_WOOL).mapColor(MapColor.PALE_PURPLE)).flammable(30, 60).fuel(100).blockTag(BlockTags.WOOL);
	public static final BlockContainer LAVENDER_WOOL_SLAB = MakeSlab(LAVENDER_WOOL).flammable(40, 40).fuel(50).slabModel(LAVENDER_WOOL).blockTag(ModBlockTags.WOOL_SLABS).itemTag(ModItemTags.WOOL_SLABS);
	public static final BlockContainer LAVENDER_CARPET = BuildBlock(new CarpetBlock(Block.Settings.copy(Blocks.WHITE_CARPET).mapColor(MapColor.PALE_PURPLE)))
			.flammable(60, 20).fuel(67).dispenser(new HorseArmorDispenserBehavior()::dispenseSilently)
			.blockTag(BlockTags.CARPETS).itemTag(ItemTags.CARPETS);
	public static final BlockContainer LAVENDER_FLEECE = MakeBlock(LAVENDER_WOOL).flammable(30, 60).fuel(100).blockTag(BlockTags.WOOL);
	public static final BlockContainer LAVENDER_FLEECE_SLAB = MakeSlab(LAVENDER_FLEECE).flammable(40, 40).fuel(50).slabModel(LAVENDER_FLEECE).blockTag(ModBlockTags.WOOL_SLABS).itemTag(ModItemTags.WOOL_SLABS);
	public static final BlockContainer LAVENDER_FLEECE_CARPET = BuildBlock(new CarpetBlock(Block.Settings.copy(Blocks.WHITE_CARPET).mapColor(MapColor.PALE_PURPLE)))
			.flammable(60, 20).fuel(67).dispenser(new HorseArmorDispenserBehavior()::dispenseSilently)
			.blockTag(BlockTags.CARPETS).itemTag(ItemTags.CARPETS).blockTag(ModBlockTags.FLEECE_CARPETS).itemTag(ModItemTags.FLEECE_CARPETS);
	//Glass
	public static final BlockContainer LAVENDER_STAINED_GLASS = new BlockContainer(new ModStainedGlassBlock(Block.Settings.copy(Blocks.WHITE_STAINED_GLASS).mapColor(MapColor.PALE_PURPLE), ModDyeColor.LAVENDER)).requireSilkTouch().cubeAllModel().blockTag(BlockTags.IMPERMEABLE);
	public static final BlockContainer LAVENDER_STAINED_GLASS_PANE = new BlockContainer(new ModStainedGlassPaneBlock(Block.Settings.copy(Blocks.WHITE_STAINED_GLASS_PANE).mapColor(MapColor.PALE_PURPLE), ModDyeColor.LAVENDER)).requireSilkTouch().paneModel(LAVENDER_STAINED_GLASS).blockTag(BlockTags.IMPERMEABLE);
	public static final BlockContainer LAVENDER_STAINED_GLASS_SLAB = BuildSlab(new ModStainedGlassSlabBlock(LAVENDER_STAINED_GLASS, ModDyeColor.LAVENDER)).blockTag(BlockTags.IMPERMEABLE);
	public static final BlockContainer LAVENDER_STAINED_GLASS_TRAPDOOR = BuildBlock(new ModStainedGlassTrapdoorBlock(LAVENDER_STAINED_GLASS, LAVENDER_STAINED_GLASS_SLAB, ModDyeColor.LAVENDER)).blockTag(BlockTags.TRAPDOORS).itemTag(ItemTags.TRAPDOORS);
	//</editor-fold>
	//<editor-fold desc="Mint">
	public static final Item MINT_DYE = GeneratedItem(new ModDyeItem(ModDyeColor.MINT, ItemSettings()));
	public static final BlockContainer MINT_DYE_BLOCK = MakeBlock(DyeBlockSettings(MapColor.LICHEN_GREEN)).cubeAllModel();
	//Candles
	public static final BlockContainer MINT_CANDLE = MakeCandle(MapColor.LICHEN_GREEN, 3);
	public static final Block MINT_CANDLE_CAKE = new ModCandleCakeBlock(4, () -> Blocks.CAKE, () -> Items.CAKE).drops(MINT_CANDLE.asBlock());
	//Concrete
	public static final BlockContainer MINT_CONCRETE = MakeBlock(Block.Settings.copy(Blocks.WHITE_CONCRETE).mapColor(MapColor.LICHEN_GREEN)).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer MINT_CONCRETE_STAIRS = MakeStairs(MINT_CONCRETE).stairsModel(MINT_CONCRETE).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer MINT_CONCRETE_SLAB = MakeSlab(MINT_CONCRETE).slabModel(MINT_CONCRETE).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer MINT_CONCRETE_WALL = MakeWall(MINT_CONCRETE).wallModel(MINT_CONCRETE).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer MINT_CONCRETE_POWDER = BuildBlock(new ConcretePowderBlock(MINT_CONCRETE.asBlock(), Block.Settings.of(Material.AGGREGATE, DyeColor.RED).strength(0.5f).sounds(BlockSoundGroup.SAND).mapColor(MapColor.LICHEN_GREEN))).cubeAllModel();
	//Terracotta
	public static final BlockContainer MINT_TERRACOTTA = MakeBlock(Block.Settings.copy(Blocks.WHITE_TERRACOTTA).mapColor(MapColor.LICHEN_GREEN)).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer MINT_TERRACOTTA_STAIRS = MakeStairs(MINT_TERRACOTTA).stairsModel(MINT_TERRACOTTA).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer MINT_TERRACOTTA_SLAB = MakeSlab(MINT_TERRACOTTA).slabModel(MINT_TERRACOTTA).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer MINT_TERRACOTTA_WALL = MakeWall(MINT_TERRACOTTA).wallModel(MINT_TERRACOTTA).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer MINT_GLAZED_TERRACOTTA = BuildBlock(new GlazedTerracottaBlock(Block.Settings.of(Material.STONE, MapColor.LICHEN_GREEN).requiresTool().strength(1.4f))).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer MINT_GLAZED_TERRACOTTA_SLAB = BuildSlab(new HorizontalFacingSlabBlock(MINT_GLAZED_TERRACOTTA)).blockTag(BlockTags.PICKAXE_MINEABLE);
	//Wool & Fleece
	public static final BlockContainer MINT_WOOL = MakeBlock(Block.Settings.copy(Blocks.WHITE_WOOL).mapColor(MapColor.LICHEN_GREEN)).flammable(30, 60).fuel(100).blockTag(BlockTags.WOOL);
	public static final BlockContainer MINT_WOOL_SLAB = MakeSlab(MINT_WOOL).flammable(40, 40).fuel(50).slabModel(MINT_WOOL).blockTag(ModBlockTags.WOOL_SLABS).itemTag(ModItemTags.WOOL_SLABS);
	public static final BlockContainer MINT_CARPET = BuildBlock(new CarpetBlock(Block.Settings.copy(Blocks.WHITE_CARPET).mapColor(MapColor.LICHEN_GREEN)))
			.flammable(60, 20).fuel(67).dispenser(new HorseArmorDispenserBehavior()::dispenseSilently)
			.blockTag(BlockTags.CARPETS).itemTag(ItemTags.CARPETS);
	public static final BlockContainer MINT_FLEECE = MakeBlock(MINT_WOOL).flammable(30, 60).fuel(100).blockTag(BlockTags.WOOL);
	public static final BlockContainer MINT_FLEECE_SLAB = MakeSlab(MINT_FLEECE).flammable(40, 40).fuel(50).slabModel(MINT_FLEECE).blockTag(ModBlockTags.WOOL_SLABS).itemTag(ModItemTags.WOOL_SLABS);
	public static final BlockContainer MINT_FLEECE_CARPET = BuildBlock(new CarpetBlock(Block.Settings.copy(Blocks.WHITE_CARPET).mapColor(MapColor.LICHEN_GREEN)))
			.flammable(60, 20).fuel(67).dispenser(new HorseArmorDispenserBehavior()::dispenseSilently)
			.blockTag(BlockTags.CARPETS).itemTag(ItemTags.CARPETS).blockTag(ModBlockTags.FLEECE_CARPETS).itemTag(ModItemTags.FLEECE_CARPETS);
	//Glass
	public static final BlockContainer MINT_STAINED_GLASS = new BlockContainer(new ModStainedGlassBlock(Block.Settings.copy(Blocks.WHITE_STAINED_GLASS).mapColor(MapColor.LICHEN_GREEN), ModDyeColor.MINT)).requireSilkTouch().cubeAllModel().blockTag(BlockTags.IMPERMEABLE);
	public static final BlockContainer MINT_STAINED_GLASS_PANE = new BlockContainer(new ModStainedGlassPaneBlock(Block.Settings.copy(Blocks.WHITE_STAINED_GLASS_PANE).mapColor(MapColor.LICHEN_GREEN), ModDyeColor.MINT)).requireSilkTouch().paneModel(MINT_STAINED_GLASS).blockTag(BlockTags.IMPERMEABLE);
	public static final BlockContainer MINT_STAINED_GLASS_SLAB = BuildSlab(new ModStainedGlassSlabBlock(MINT_STAINED_GLASS, ModDyeColor.MINT)).blockTag(BlockTags.IMPERMEABLE);
	public static final BlockContainer MINT_STAINED_GLASS_TRAPDOOR = BuildBlock(new ModStainedGlassTrapdoorBlock(MINT_STAINED_GLASS, MINT_STAINED_GLASS_SLAB, ModDyeColor.MINT)).blockTag(BlockTags.TRAPDOORS).itemTag(ItemTags.TRAPDOORS);
	//</editor-fold>
	//</editor-fold>
	//<editor-fold desc="Echo">
	public static final Item ECHO_SHARD = MakeGeneratedItem(ItemSettings(ItemGroup.MISC));
	//Extended
	public static final BlockContainer ECHO_BLOCK = BuildBlock(new EchoBlock(Block.Settings.of(Material.SCULK).strength(1.5F).sounds(ModBlockSoundGroups.ECHO_BLOCK).requiresTool())).cubeAllModel().blockTag(ModBlockTags.VIBRATION_RESONATORS_ECHO).blockTag(ModBlockTags.ECHO_SOUND_BLOCKS).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer ECHO_STAIRS = BuildStairs(EchoStairsBlock::new, ECHO_BLOCK).stairsModel(ECHO_BLOCK).blockTag(ModBlockTags.ECHO_SOUND_BLOCKS).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer ECHO_SLAB = BuildSlab(EchoSlabBlock::new, ECHO_BLOCK).slabModel(ECHO_BLOCK).blockTag(ModBlockTags.ECHO_SOUND_BLOCKS).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer ECHO_WALL = BuildWall(EchoWallBlock::new, ECHO_BLOCK).wallModel(ECHO_BLOCK).blockTag(ModBlockTags.ECHO_SOUND_BLOCKS).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer ECHO_BRICKS = BuildBlock(new EchoBlock(Block.Settings.of(Material.SCULK).sounds(ModBlockSoundGroups.ECHO_BLOCK).strength(1.5f, 1.5f).requiresTool())).cubeAllModel().blockTag(ModBlockTags.ECHO_SOUND_BLOCKS).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer ECHO_BRICK_STAIRS = BuildStairs(EchoStairsBlock::new, ECHO_BRICKS).stairsModel(ECHO_BRICKS).blockTag(ModBlockTags.ECHO_SOUND_BLOCKS).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer ECHO_BRICK_SLAB = BuildSlab(EchoSlabBlock::new, ECHO_BRICKS).slabModel(ECHO_BRICKS).blockTag(ModBlockTags.ECHO_SOUND_BLOCKS).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer ECHO_BRICK_WALL = BuildWall(EchoWallBlock::new, ECHO_BRICKS).wallModel(ECHO_BRICKS).blockTag(ModBlockTags.ECHO_SOUND_BLOCKS).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer ECHO_CRYSTAL_BLOCK = BuildBlock(new EchoBlock(Block.Settings.of(Material.SCULK).sounds(ModBlockSoundGroups.ECHO_CLUSTER).strength(1.5f, 1.5f).requiresTool().luminance(LUMINANCE_5))).cubeAllModel().blockTag(ModBlockTags.ECHO_SOUND_BLOCKS).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer ECHO_CRYSTAL_STAIRS = BuildStairs(EchoStairsBlock::new, ECHO_CRYSTAL_BLOCK).stairsModel(ECHO_CRYSTAL_BLOCK).blockTag(ModBlockTags.ECHO_SOUND_BLOCKS).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer ECHO_CRYSTAL_SLAB = BuildSlab(EchoSlabBlock::new, ECHO_CRYSTAL_BLOCK).slabModel(ECHO_CRYSTAL_BLOCK).blockTag(ModBlockTags.ECHO_SOUND_BLOCKS).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer ECHO_CRYSTAL_WALL = BuildWall(EchoWallBlock::new, ECHO_CRYSTAL_BLOCK).wallModel(ECHO_CRYSTAL_BLOCK).blockTag(ModBlockTags.ECHO_SOUND_BLOCKS).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer BUDDING_ECHO = BuildBlock(new BuddingEchoBlock(Block.Settings.copy(ECHO_BLOCK.asBlock()).ticksRandomly()), DropTable.NOTHING).cubeAllModel().blockTag(ModBlockTags.ECHO_SOUND_BLOCKS).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer ECHO_CLUSTER = new BlockContainer(new EchoClusterBlock(7, 3, Block.Settings.of(Material.SCULK).nonOpaque().ticksRandomly().sounds(ModBlockSoundGroups.ECHO_CLUSTER).strength(1.5F).luminance(LUMINANCE_3))).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer LARGE_ECHO_BUD = new BlockContainer(new EchoClusterBlock(5, 3, Block.Settings.copy(ECHO_CLUSTER.asBlock()).sounds(ModBlockSoundGroups.MEDIUM_ECHO_BUD).luminance(LUMINANCE_2))).requireSilkTouch().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer MEDIUM_ECHO_BUD = new BlockContainer(new EchoClusterBlock(4, 3, Block.Settings.copy(ECHO_CLUSTER.asBlock()).sounds(ModBlockSoundGroups.LARGE_ECHO_BUD).luminance(LUMINANCE_1))).requireSilkTouch().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer SMALL_ECHO_BUD = new BlockContainer(new EchoClusterBlock(3, 4, Block.Settings.copy(ECHO_CLUSTER.asBlock()).sounds(ModBlockSoundGroups.SMALL_ECHO_BUD))).requireSilkTouch().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final Item ECHO_AXE = HandheldItem(new EchoAxeItem(ModToolMaterials.ECHO, 5, -3, 1));
	public static final Item ECHO_HOE = HandheldItem(new EchoHoeItem(ModToolMaterials.ECHO, -4, 0, 1));
	public static final Item ECHO_PICKAXE = HandheldItem(new EchoPickaxeItem(ModToolMaterials.ECHO, 1, -2.8F, 1));
	public static final Item ECHO_SHOVEL = HandheldItem(new EchoShovelItem(ModToolMaterials.ECHO, 1.5F, -3, 1));
	public static final Item ECHO_SWORD = HandheldItem(new EchoSwordItem(ModToolMaterials.ECHO, 3, -2.4F, 1));
	public static final Item ECHO_KNIFE = HandheldItem(new EchoKnifeItem(ModToolMaterials.ECHO, 1));
	public static final Item ECHO_HAMMER = HammerItem(new EchoHammerItem(ModToolMaterials.ECHO, 7.5F, -3, 1));
	//</editor-fold>
	//<editor-fold desc="Emerald">
	public static final BlockContainer EMERALD_STAIRS = MakeStairs(Blocks.EMERALD_BLOCK).stairsModel(Blocks.EMERALD_BLOCK).blockTag(BlockTags.NEEDS_IRON_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer EMERALD_SLAB = MakeSlab(Blocks.EMERALD_BLOCK).slabModel(Blocks.EMERALD_BLOCK).blockTag(BlockTags.NEEDS_IRON_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer EMERALD_WALL = MakeWall(Blocks.EMERALD_BLOCK).wallModel(Blocks.EMERALD_BLOCK).blockTag(BlockTags.NEEDS_IRON_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer EMERALD_BRICKS = MakeBlock(Blocks.EMERALD_BLOCK).cubeAllModel().blockTag(BlockTags.NEEDS_IRON_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer EMERALD_BRICK_STAIRS = MakeStairs(EMERALD_BRICKS).stairsModel(EMERALD_BRICKS).blockTag(BlockTags.NEEDS_IRON_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer EMERALD_BRICK_SLAB = MakeSlab(EMERALD_BRICKS).slabModel(EMERALD_BRICKS).blockTag(BlockTags.NEEDS_IRON_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer EMERALD_BRICK_WALL = MakeWall(EMERALD_BRICKS).wallModel(EMERALD_BRICKS).blockTag(BlockTags.NEEDS_IRON_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer CUT_EMERALD = MakeBlock(Blocks.EMERALD_BLOCK).cubeAllModel().blockTag(BlockTags.NEEDS_IRON_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer CUT_EMERALD_STAIRS = MakeStairs(CUT_EMERALD).stairsModel(CUT_EMERALD).blockTag(BlockTags.NEEDS_IRON_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer CUT_EMERALD_SLAB = MakeSlab(CUT_EMERALD).slabModel(CUT_EMERALD).blockTag(BlockTags.NEEDS_IRON_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer CUT_EMERALD_WALL = MakeWall(CUT_EMERALD).wallModel(CUT_EMERALD).blockTag(BlockTags.NEEDS_IRON_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final Item EMERALD_AXE = MakeAxe(ModToolMaterials.EMERALD);
	public static final Item EMERALD_HOE = MakeHoe(ModToolMaterials.EMERALD);
	public static final Item EMERALD_PICKAXE = MakePickaxe(ModToolMaterials.EMERALD);
	public static final Item EMERALD_SHOVEL = MakeShovel(ModToolMaterials.EMERALD);
	public static final Item EMERALD_SWORD = MakeSword(ModToolMaterials.EMERALD);
	public static final Item EMERALD_KNIFE = MakeKnife(ModToolMaterials.EMERALD);
	public static final Item EMERALD_HAMMER = MakeHammer(ModToolMaterials.EMERALD);
	public static final Item EMERALD_HELMET = MakeHelmet(ModArmorMaterials.EMERALD);
	public static final Item EMERALD_CHESTPLATE = MakeChestplate(ModArmorMaterials.EMERALD);
	public static final Item EMERALD_LEGGINGS = MakeLeggings(ModArmorMaterials.EMERALD);
	public static final Item EMERALD_BOOTS = MakeBoots(ModArmorMaterials.EMERALD);
	public static final Item EMERALD_HORSE_ARMOR = MakeHorseArmor(ModArmorMaterials.EMERALD);
	//</editor-fold>
	//<editor-fold desc="End Stone">
	public static final BlockContainer END_STONE_SLAB = MakeSlab(Blocks.END_STONE).slabModel(Blocks.END_STONE).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer END_STONE_PILLAR = BuildBlock(new ModPillarBlock(Blocks.END_STONE_BRICKS)).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer END_STONE_TILES = MakeBlock(Blocks.END_STONE_BRICKS).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer END_STONE_TILE_STAIRS = MakeStairs(END_STONE_TILES).stairsModel(END_STONE_TILES).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer END_STONE_TILE_SLAB = MakeSlab(END_STONE_TILES).slabModel(END_STONE_TILES).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer END_STONE_TILE_WALL = MakeWall(END_STONE_TILES).wallModel(END_STONE_TILES).blockTag(BlockTags.PICKAXE_MINEABLE);
	//</editor-fold>
	//<editor-fold desc="End Shale">
	public static final BlockContainer COBBLED_END_SHALE = MakeBlock(Block.Settings.copy(Blocks.END_STONE).strength(4.0f, 9.0f).mapColor(MapColor.TEAL)).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer COBBLED_END_SHALE_STAIRS = MakeStairs(COBBLED_END_SHALE).stairsModel(COBBLED_END_SHALE).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer COBBLED_END_SHALE_SLAB = MakeSlab(COBBLED_END_SHALE).slabModel(COBBLED_END_SHALE).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer COBBLED_END_SHALE_WALL = MakeWall(COBBLED_END_SHALE).wallModel(COBBLED_END_SHALE).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer END_SHALE = BuildBlock(new ModPillarBlock(Block.Settings.copy(Blocks.END_STONE).strength(3.5f, 9.0f).mapColor(MapColor.TEAL)), DropTable.SilkTouchOrElse(COBBLED_END_SHALE.asItem())).blockTag(ModBlockTags.SCULK_REPLACEABLE).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer END_SHALE_BRICKS = MakeBlock(END_SHALE).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer END_SHALE_BRICK_STAIRS = MakeStairs(END_SHALE_BRICKS).stairsModel(END_SHALE_BRICKS).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer END_SHALE_BRICK_SLAB = MakeSlab(END_SHALE_BRICKS).slabModel(END_SHALE_BRICKS).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer END_SHALE_BRICK_WALL = MakeWall(END_SHALE_BRICKS).wallModel(END_SHALE_BRICKS).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer END_SHALE_TILES = MakeBlock(END_SHALE_BRICKS).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer END_SHALE_TILE_STAIRS = MakeStairs(END_SHALE_TILES).stairsModel(END_SHALE_TILES).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer END_SHALE_TILE_SLAB = MakeSlab(END_SHALE_TILES).slabModel(END_SHALE_TILES).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer END_SHALE_TILE_WALL = MakeWall(END_SHALE_TILES).wallModel(END_SHALE_TILES).blockTag(BlockTags.PICKAXE_MINEABLE);
	//</editor-fold>
	//<editor-fold desc="End Rock">
	public static final BlockContainer END_ROCK = BuildBlock(new ModPillarBlock(Blocks.END_STONE), DropTable.SilkTouchOrElse(Items.END_STONE)).blockTag(ModBlockTags.SCULK_REPLACEABLE).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer END_ROCK_SHALE = BuildBlock(new ModFacingBlock(Blocks.END_STONE), DropTable.SilkTouchOrElse(Items.END_STONE)).blockTag(ModBlockTags.SCULK_REPLACEABLE).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer END_SHALE_ROCK = BuildBlock(new ModFacingBlock(END_SHALE), DropTable.SilkTouchOrElse(COBBLED_END_SHALE.asItem())).blockTag(ModBlockTags.SCULK_REPLACEABLE).blockTag(BlockTags.PICKAXE_MINEABLE);
	//</editor-fold>
	public static final Item PURPLE_ENDER_EYE = GeneratedItem(new PurpleEnderEyeItem(ItemSettings()));
	public static final EntityType<PurpleEyeOfEnderEntity> PURPLE_EYE_OF_ENDER_ENTITY = FabricEntityTypeBuilder.<PurpleEyeOfEnderEntity>create(SpawnGroup.MISC, PurpleEyeOfEnderEntity::new).dimensions(EntityDimensions.fixed(0.25f, 0.25f)).trackRangeChunks(4).trackedUpdateRate(4).build();
	public static final Block PURPLE_EYE_END_PORTAL_FRAME = new PurpleEyeEndPortalFrameBlock(Block.Settings.of(Material.STONE, MapColor.PURPLE).sounds(BlockSoundGroup.GLASS).luminance(LUMINANCE_1).strength(-1.0F, 3600000.0F));
	//<editor-fold desc="Ender Fire">
	public static final BlockContainer ENDER_CANDLE = MakeCandle(MapColor.PALE_YELLOW, 2.75);
	public static final Block ENDER_CANDLE_CAKE = new ModCandleCakeBlock(3, () -> Blocks.CAKE, () -> Items.CAKE) {
		@Override public boolean isEnderCandle() { return true; }
	}.drops(ENDER_CANDLE.asBlock());
	public static final TorchContainer ENDER_TORCH = MakeTorch(12, BlockSoundGroup.WOOD, ModParticleTypes.ENDER_FIRE_FLAME);
	public static final BlockContainer ENDER_LANTERN = MakeLantern(13);
	public static final BlockContainer ENDER_CAMPFIRE = MakeEnderCampfire(Blocks.CAMPFIRE);
	//</editor-fold>
	//<editor-fold desc="Fancy Chicken">
	public static final EntityType<FancyChickenEntity> FANCY_CHICKEN_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, FancyChickenEntity::new).dimensions(EntityDimensions.fixed(0.4F, 0.7F)).trackRangeChunks(10).build();
	public static final Item FANCY_CHICKEN_SPAWN_EGG = MakeSpawnEgg(FANCY_CHICKEN_ENTITY, 0xB788CB, 0xF7B035);
	//</editor-fold>
	//<editor-fold desc="Feathers">
	public static final Item FANCY_FEATHER = MakeGeneratedItem(); //Minecraft Earth
	public static final Item BLACK_FEATHER = MakeGeneratedItem();
	public static final Item RED_FEATHER = MakeGeneratedItem();
	public static final Item LIGHT_FEATHER = MakeGeneratedItem(); //TODO:https://minecraft.fandom.com/wiki/Minecraft_Dungeons:Light_Feather
	//</editor-fold>
	public static final Item FIRING_CHARGE = ParentedItem(new FiringChargeItem(ItemSettings()), Items.FIRE_CHARGE);
	//TODO: Port Minecraft Dungeons: Shadow Step to Kaden
	//<editor-fold desc="Fleece">
	public static final Map<DyeColor, BlockContainer> FLEECE = ColorUtil.Map(color -> MakeBlock(ColorUtil.GetWoolBlock(color)).flammable(30, 60).fuel(100).blockTag(ModBlockTags.FLEECE).itemTag(ModItemTags.FLEECE));
	public static final Map<DyeColor, BlockContainer> FLEECE_SLABS = ColorUtil.Map(color -> { Block block = FLEECE.get(color).asBlock(); return MakeSlab(block).flammable(40, 40).fuel(50).slabModel(block).blockTag(ModBlockTags.FLEECE_SLABS).itemTag(ModItemTags.FLEECE_SLABS); });
	public static final Map<DyeColor, BlockContainer> FLEECE_CARPETS = ColorUtil.Map(color -> BuildBlock(new ModDyedCarpetBlock(color, Block.Settings.copy(ColorUtil.GetWoolCarpetBlock(color)))).flammable(60, 20).fuel(67).dispenser(new HorseArmorDispenserBehavior()::dispenseSilently).blockTag(BlockTags.CARPETS).itemTag(ItemTags.CARPETS).blockTag(ModBlockTags.FLEECE_CARPETS).itemTag(ModItemTags.FLEECE_CARPETS));
	public static final BlockContainer RAINBOW_FLEECE = BuildBlock(new ModFacingBlock(Blocks.WHITE_WOOL)).flammable(30, 60).fuel(100).blockTag(ModBlockTags.FLEECE).itemTag(ModItemTags.FLEECE);
	public static final BlockContainer RAINBOW_FLEECE_SLAB = BuildSlab(new HorizontalFacingSlabBlock(RAINBOW_FLEECE.asBlock())).flammable(40, 40).fuel(50).blockTag(ModBlockTags.FLEECE_SLABS).itemTag(ModItemTags.FLEECE_SLABS);
	public static final BlockContainer RAINBOW_FLEECE_CARPET = BuildBlock(new HorziontalFacingCarpetBlock(RAINBOW_FLEECE.asBlock())).flammable(60, 20).fuel(67).blockTag(BlockTags.CARPETS).itemTag(ItemTags.CARPETS).blockTag(ModBlockTags.FLEECE_CARPETS).itemTag(ModItemTags.FLEECE_CARPETS);
	public static final Item FLEECE_HELMET = GeneratedItem(new ModDyeableArmorItem(0xFFFFFF, ModArmorMaterials.FLEECE, EquipmentSlot.HEAD));
	public static final Item FLEECE_CHESTPLATE = GeneratedItem(new ModDyeableArmorItem(0xFFFFFF, ModArmorMaterials.FLEECE, EquipmentSlot.CHEST));
	public static final Item FLEECE_LEGGINGS = GeneratedItem(new ModDyeableArmorItem(0xFFFFFF, ModArmorMaterials.FLEECE, EquipmentSlot.LEGS));
	public static final Item FLEECE_BOOTS = GeneratedItem(new ModDyeableArmorItem(0xFFFFFF, ModArmorMaterials.FLEECE, EquipmentSlot.FEET));
	public static final Item FLEECE_HORSE_ARMOR = GeneratedItem(HorseArmorDispenserBehavior.Dispensible(new ModDyeableHorseArmorItem(0xFFFFFF, ModArmorMaterials.FLEECE)));
	//</editor-fold>
	//<editor-fold desc="Flint">
	public static final BlockContainer FLINT_BLOCK = BuildBlock(new FlintBlock(Block.Settings.of(Material.STONE, MapColor.DEEPSLATE_GRAY).strength(1.5f, 3.0f).sounds(BlockSoundGroup.METAL)), DropTable.FLINT).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer FLINT_SLAB = BuildSlab(new FlintSlabBlock(FLINT_BLOCK), DropTable.FLINT_SLAB).slabModel(FLINT_BLOCK).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer FLINT_BRICKS = BuildBlock(new FlintBlock(FLINT_BLOCK), DropTable.FLINT).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer FLINT_BRICK_STAIRS = BuildStairs(new FlintStairsBlock(FLINT_BRICKS), DropTable.FLINT).stairsModel(FLINT_BRICKS).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer FLINT_BRICK_SLAB = BuildSlab(new FlintSlabBlock(FLINT_BRICKS), DropTable.FLINT_SLAB).slabModel(FLINT_BRICKS).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer FLINT_BRICK_WALL = BuildWall(new FlintWallBlock(FLINT_BRICKS), DropTable.FLINT).wallModel(FLINT_BRICKS).blockTag(BlockTags.PICKAXE_MINEABLE);
	//</editor-fold>
	private static Item getHydrangeaItem() { return HYDRANGEA.asItem(); }
	public static final ItemGroup FLOWER_GROUP = FabricItemGroupBuilder.build(ModId.ID("flowers"), () -> new ItemStack(ModBase::getHydrangeaItem));
	//<editor-fold desc="Flowers">
	//<editor-fold desc="Minecraft Earth">
	public static final FlowerContainer BUTTERCUP = MakeFlower(StatusEffects.BLINDNESS, 11).pottedModel();
	public static final FlowerContainer PINK_DAISY = MakeFlower(StatusEffects.REGENERATION, 8).pottedModel();
	//</editor-fold>
	public static final FlowerContainer ROSE = new FlowerContainer(new GrowableFlowerBlock(StatusEffects.WEAKNESS, 9, FlowerSettings(), () -> (TallFlowerBlock)Blocks.ROSE_BUSH), FlowerItemSettings()).flammable(60, 100).compostable(0.65f).dropSelf().pottedModel().blockTag(BlockTags.SMALL_FLOWERS).itemTag(ItemTags.SMALL_FLOWERS);
	private static TallFlowerBlock getBlueRoseBush() { return (TallFlowerBlock) BLUE_ROSE_BUSH.asBlock(); }
	public static final FlowerContainer BLUE_ROSE = new FlowerContainer(new GrowableFlowerBlock(StatusEffects.WEAKNESS, 9, FlowerSettings(), ModBase::getBlueRoseBush), FlowerItemSettings()).flammable(60, 100).compostable(0.65f).dropSelf().pottedModel().blockTag(BlockTags.SMALL_FLOWERS).itemTag(ItemTags.SMALL_FLOWERS);
	public static final FlowerContainer MAGENTA_TULIP = MakeFlower(StatusEffects.FIRE_RESISTANCE, 4).pottedModel();
	public static final FlowerContainer MARIGOLD = MakeFlower(StatusEffects.WITHER, 5).pottedModel();
	//<editor-fold desc="Orchids">
	public static final FlowerContainer INDIGO_ORCHID = MakeFlower(StatusEffects.SATURATION, 7).pottedModel();
	public static final FlowerContainer MAGENTA_ORCHID = MakeFlower(StatusEffects.SATURATION, 7).pottedModel();
	public static final FlowerContainer ORANGE_ORCHID = MakeFlower(StatusEffects.SATURATION, 7).pottedModel();
	public static final FlowerContainer PURPLE_ORCHID = MakeFlower(StatusEffects.SATURATION, 7).pottedModel();
	public static final FlowerContainer RED_ORCHID = MakeFlower(StatusEffects.SATURATION, 7).pottedModel();
	public static final FlowerContainer WHITE_ORCHID = MakeFlower(StatusEffects.SATURATION, 7).pottedModel();
	public static final FlowerContainer YELLOW_ORCHID = MakeFlower(StatusEffects.SATURATION, 7).pottedModel();
	//</editor-fold>
	private static TallFlowerBlock getTallPinkAlliumBlock() { return (TallFlowerBlock) TALL_PINK_ALLIUM.asBlock(); }
	public static final FlowerContainer PINK_ALLIUM = new FlowerContainer(new GrowableFlowerBlock(StatusEffects.FIRE_RESISTANCE, 4, FlowerSettings(), ModBase::getTallPinkAlliumBlock), FlowerItemSettings()).flammable(60, 100).compostable(0.65f).dropSelf().pottedModel().blockTag(BlockTags.SMALL_FLOWERS).itemTag(ItemTags.SMALL_FLOWERS);
	public static final FlowerContainer LAVENDER = MakeFlower(StatusEffects.INVISIBILITY, 8).pottedModel();
	public static final FlowerContainer HYDRANGEA = MakeFlower(StatusEffects.JUMP_BOOST, 7).pottedModel();
	public static final FlowerContainer PAEONIA = MakeFlower(StatusEffects.STRENGTH, 6).pottedModel();
	public static final FlowerContainer ASTER = MakeFlower(StatusEffects.INSTANT_DAMAGE, 1).pottedModel();
	//<editor-fold desc="Tall">
	public static final TallBlockContainer AMARANTH = MakeTallFlower();
	public static final TallBlockContainer BLUE_ROSE_BUSH = MakeCuttableFlower(() -> (FlowerBlock)BLUE_ROSE.asBlock());
	public static final TallBlockContainer TALL_ALLIUM = MakeCuttableFlower(() -> (FlowerBlock)Blocks.ALLIUM);
	public static final TallBlockContainer TALL_PINK_ALLIUM = MakeCuttableFlower(() -> (FlowerBlock) PINK_ALLIUM.asBlock());
	//</editor-fold>
	public static final Item VANILLA = MakeGeneratedItem();
	private static TallFlowerBlock getTallVanillaBlock() { return (TallFlowerBlock) TALL_VANILLA.asBlock(); }
	public static final FlowerContainer VANILLA_FLOWER = new FlowerContainer(new GrowableFlowerBlock(StatusEffects.INSTANT_HEALTH, 11, FlowerSettings(), ModBase::getTallVanillaBlock), FlowerItemSettings()).flammable(60, 100).compostable(0.65f).dropSelf().pottedModel().blockTag(BlockTags.SMALL_FLOWERS).itemTag(ItemTags.SMALL_FLOWERS);
	public static final TallBlockContainer TALL_VANILLA = MakeCuttableFlower(() -> (FlowerBlock)VANILLA_FLOWER.asBlock(), (world) -> new ItemStack(VANILLA, world.random.nextInt(2) + 1));
	//</editor-fold>
	//<editor-fold desc="Flower Parts">
	//<editor-fold desc="Minecraft Earth">
	public static final FlowerPartContainer BUTTERCUP_PARTS = MakeFlowerParts(BUTTERCUP).flowerPartModel();
	public static final FlowerPartContainer PINK_DAISY_PARTS = MakeFlowerParts(PINK_DAISY);
	//</editor-fold>
	public static final FlowerPartContainer ROSE_PARTS = MakeFlowerParts(ROSE);
	public static final FlowerPartContainer BLUE_ROSE_PARTS = MakeFlowerParts(BLUE_ROSE);
	public static final FlowerPartContainer MAGENTA_TULIP_PARTS = MakeFlowerParts(MAGENTA_TULIP);
	public static final FlowerPartContainer MARIGOLD_PARTS = MakeFlowerParts(MARIGOLD).flowerPartModel();
	//<editor-fold desc="Orchids">
	public static final FlowerPartContainer INDIGO_ORCHID_PARTS = MakeFlowerParts(INDIGO_ORCHID);
	public static final FlowerPartContainer MAGENTA_ORCHID_PARTS = MakeFlowerParts(MAGENTA_ORCHID);
	public static final FlowerPartContainer ORANGE_ORCHID_PARTS = MakeFlowerParts(ORANGE_ORCHID);
	public static final FlowerPartContainer PURPLE_ORCHID_PARTS = MakeFlowerParts(PURPLE_ORCHID);
	public static final FlowerPartContainer RED_ORCHID_PARTS = MakeFlowerParts(RED_ORCHID);
	public static final FlowerPartContainer WHITE_ORCHID_PARTS = MakeFlowerParts(WHITE_ORCHID);
	public static final FlowerPartContainer YELLOW_ORCHID_PARTS = MakeFlowerParts(YELLOW_ORCHID);
	//</editor-fold>
	public static final FlowerPartContainer PINK_ALLIUM_PARTS = MakeFlowerParts(PINK_ALLIUM);
	public static final FlowerPartContainer LAVENDER_PARTS = MakeFlowerParts(LAVENDER).flowerPartModel();
	public static final FlowerPartContainer HYDRANGEA_PARTS = MakeFlowerParts(HYDRANGEA).flowerPartModel();
	public static final FlowerPartContainer PAEONIA_PARTS = MakeFlowerParts(PAEONIA);
	public static final FlowerPartContainer ASTER_PARTS = MakeFlowerParts(ASTER).flowerPartModel();
	//<editor-fold desc="Tall">
	public static final FlowerPartContainer AMARANTH_PARTS = MakeFlowerParts(AMARANTH).flowerPartModel();
	//</editor-fold>
	public static final FlowerPartContainer VANILLA_PARTS = MakeFlowerParts(VANILLA_FLOWER).flowerPartModel();
	//<editor-fold desc="Vanilla Minecraft">
	public static final FlowerPartContainer ALLIUM_PARTS = MakeFlowerParts(Blocks.ALLIUM);
	public static final FlowerPartContainer AZURE_BLUET_PARTS = MakeFlowerParts(Blocks.AZURE_BLUET).flowerPartModel();
	public static final FlowerPartContainer BLUE_ORCHID_PARTS = MakeFlowerParts(Blocks.BLUE_ORCHID);
	public static final FlowerPartContainer CORNFLOWER_PARTS = MakeFlowerParts(Blocks.CORNFLOWER).flowerPartModel();
	public static final FlowerPartContainer DANDELION_PARTS = MakeFlowerParts(Blocks.DANDELION).flowerPartModel();
	public static final FlowerPartContainer LILAC_PARTS = MakeFlowerParts(Blocks.LILAC).flowerPartModel();
	public static final FlowerPartContainer LILY_OF_THE_VALLEY_PARTS = MakeFlowerParts(Blocks.LILY_OF_THE_VALLEY);
	public static final FlowerPartContainer ORANGE_TULIP_PARTS = MakeFlowerParts(Blocks.ORANGE_TULIP);
	public static final FlowerPartContainer OXEYE_DAISY_PARTS = MakeFlowerParts(Blocks.OXEYE_DAISY);
	public static final FlowerPartContainer PEONY_PARTS = MakeFlowerParts(Blocks.PEONY);
	public static final FlowerPartContainer PINK_TULIP_PARTS = MakeFlowerParts(Blocks.PINK_TULIP);
	public static final FlowerPartContainer POPPY_PARTS = MakeFlowerParts(Blocks.POPPY).flowerPartModel();
	public static final FlowerPartContainer RED_TULIP_PARTS = MakeFlowerParts(Blocks.RED_TULIP);
	public static final FlowerPartContainer SUNFLOWER_PARTS = MakeFlowerParts(Blocks.SUNFLOWER).flowerPartModel();
	public static final FlowerPartContainer WHITE_TULIP_PARTS = MakeFlowerParts(Blocks.WHITE_TULIP);
	public static final FlowerPartContainer WITHER_ROSE_PARTS = MakeFlowerParts(Blocks.WITHER_ROSE);
	//</editor-fold>
	//Special Flower Petals
	public static final Item AZALEA_PETALS = MakeGeneratedItem(FlowerItemSettings());
	public static final Item SPORE_BLOSSOM_PETAL = MakeGeneratedItem(FlowerItemSettings());
	public static final Item CASSIA_PETALS = MakeGeneratedItem(FlowerItemSettings());
	public static final Item PINK_DOGWOOD_PETALS = MakeGeneratedItem(FlowerItemSettings());
	public static final Item PALE_DOGWOOD_PETALS = MakeGeneratedItem(FlowerItemSettings());
	public static final Item WHITE_DOGWOOD_PETALS = MakeGeneratedItem(FlowerItemSettings());
	//</editor-fold>
	//<editor-fold desc="Flowers (Mooblossom Variants)">
	public static final Block MOOBLOSSOM_MAGENTA_TULIP = MakeMooblossomFlower(MAGENTA_TULIP);
	public static final Block MOOBLOSSOM_RED_TULIP = MakeMooblossomFlower(Blocks.RED_TULIP);
	public static final Block MOOBLOSSOM_ORANGE_TULIP = MakeMooblossomFlower(Blocks.ORANGE_TULIP);
	public static final Block MOOBLOSSOM_WHITE_TULIP = MakeMooblossomFlower(Blocks.WHITE_TULIP);
	public static final Block MOOBLOSSOM_PINK_TULIP = MakeMooblossomFlower(Blocks.PINK_TULIP);
	//</editor-fold>
	//<editor-fold desc="Food">
	public static final Item CINNAMON = MakeGeneratedItem();
	public static final Item CHERRY = MakeFood(4, 0.3F);
	public static final Item GREEN_APPLE = MakeFood(FoodComponents.APPLE);
	public static final Block STRAWBERRY_BUSH = new StrawberryBushBlock(Block.Settings.copy(Blocks.SWEET_BERRY_BUSH));
	public static final Item STRAWBERRY = GeneratedItem(new AliasedBlockItem(STRAWBERRY_BUSH, ItemSettings().food(FoodSettings(2, 0.1F).build())));
	public static final Item SNICKERDOODLE = MakeFood(FoodComponents.COOKIE);
	public static final Item CHOCOLATE_COOKIE = MakeFood(FoodComponents.COOKIE);
	public static final Item CHORUS_COOKIE = GeneratedItem(new ChorusFruitItem(ItemSettings().food(FoodComponents.COOKIE)));
	public static final Item CONFETTI_COOKIE = MakeFood(FoodComponents.COOKIE);
	public static final Item CINNAMON_ROLL = MakeFood(3, 0.3F);
	public static final Item TOMATO_SOUP = MakeStew(FoodComponents.BEETROOT_SOUP);
	public static final Item HOTDOG = MakeFood(4, 0.4F);
	public static final Item RAMEN = MakeStew(6, 0.6F);
	public static final Item STIR_FRY = MakeStew(6, 0.6F);
	//Dairy
	public static final Item CHEESE = MakeFood(2, 0.8F);
	public static final Item GRILLED_CHEESE = MakeFood(8, 0.8F);
	public static final Item COTTAGE_CHEESE_BOWL = MakeStew(2, 0.6F);
	//Dairy Sweets
	public static final Item EGG_NOG = MakeDrink(FoodSettings(6, 0.5F).statusEffect(new StatusEffectInstance(EffectsRegistry.COMFORT.get()), 1).build());
	public static final Item MILKSHAKE = MakeDairyDrink(6, 0.5F);
	public static final Item CHOCOLATE_MILKSHAKE = MakeDairyDrink(6, 0.5F);
	public static final Item COFFEE_MILKSHAKE = MakeDairyDrink(FoodSettings(6, 0.5F)
			.statusEffect(new StatusEffectInstance(StatusEffects.SPEED, 200, 0), 1.0F)
			.statusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 200, 0), 1.0F)
			.build());
	public static final Item STRAWBERRY_MILKSHAKE = MakeDairyDrink(6, 0.5F);
	public static final Item VANILLA_MILKSHAKE = MakeDairyDrink(6, 0.5F);
	public static final Item CHOCOLATE_CHIP_MILKSHAKE = MakeDairyDrink(6, 0.5F);
	public static final Item ICE_CREAM = MakeDairyDrink(6, 0.5F);
	public static final Item CHOCOLATE_ICE_CREAM = MakeDairyDrink(6, 0.5F);
	public static final Item COFFEE_ICE_CREAM = MakeDairyDrink(FoodSettings(6, 0.5F)
			.statusEffect(new StatusEffectInstance(StatusEffects.SPEED, 200, 0), 1.0F)
			.statusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 200, 0), 1.0F)
			.build());
	public static final Item STRAWBERRY_ICE_CREAM = MakeDairyDrink(6, 0.5F);
	public static final Item VANILLA_ICE_CREAM = MakeDairyDrink(6, 0.5F);
	public static final Item CHOCOLATE_CHIP_ICE_CREAM = MakeDairyDrink(6, 0.5F);
	//Candy
	public static final Item CINNAMON_BEAN = MakeFood(1, 0.1F);
	public static final Item PINK_COTTON_CANDY = MakeFood(3, 0.1F);
	public static final Item BLUE_COTTON_CANDY = MakeFood(3, 0.1F);
	public static final Item CANDY_CANE = MakeFood(1, 0.1F);
	public static final Item CANDY_CORN = MakeFood(1, 0.1F);
	public static final Item CARAMEL = MakeFood(1, 0.1F);
	public static final Item CARAMEL_APPLE = MakeHandheldItem(ItemSettings().recipeRemainder(Items.STICK).food(FoodSettings(5, 0.3F).build()));
	public static final Item LOLLIPOP = MakeFood(1, 0.1F);
	public static final Item MILK_CHOCOLATE = MakeFood(1, 0.1F);
	public static final Item DARK_CHOCOLATE = MakeFood(1, 0.1F);
	public static final Item WHITE_CHOCOLATE = MakeFood(1, 0.1F);
	public static final Item MARSHMALLOW = MakeFood(1, 0.1F);
	public static final Item ROAST_MARSHMALLOW = MakeFood(1, 0.2F);
	public static final Item MARSHMALLOW_ON_STICK = MakeHandheldItem(ItemSettings().recipeRemainder(Items.STICK).food(FoodSettings(2, 0.2F).build()));
	public static final Item ROAST_MARSHMALLOW_ON_STICK = MakeHandheldItem(ItemSettings().recipeRemainder(Items.STICK).food(FoodSettings(2, 0.2F).build()));
	public static final Map<DyeColor, Item> ROCK_CANDIES = ColorUtil.Map((color) -> MakeHandheldItem(ItemSettings().recipeRemainder(Items.STICK).food(FoodSettings(1, 0.1F).build())));
	//</editor-fold>
	//TODO: Throw Golden Egg
	//<editor-fold desc="Food (Golden)">
	public static final Item GOLDEN_POTATO = MakeFood(2, 0.6F);
	public static final Item GOLDEN_BAKED_POTATO = MakeFood(10, 1.2F);
	public static final Item GOLDEN_BEETROOT = MakeFood(2, 1.2F);
	public static final Item GOLDEN_CHORUS_FRUIT = GeneratedItem(new ChorusFruitItem(ItemSettings().food(FoodSettings(8, 0.6F).alwaysEdible().build())));
	public static final Item GOLDEN_TOMATO = MakeFood(2, 0.8F);
	public static final Item GOLDEN_ONION = MakeFood(4, 0.8F);
	public static final Item GOLDEN_EGG = MakeGeneratedItem();
	//</editor-fold>
	//TODO: Throw spoiled eggs
	//<editor-fold desc="Food (Rotten)">
	public static final Item POISONOUS_CARROT = MakePoisonousFood(3, 0.6F);
	public static final Item POISONOUS_BEETROOT = MakePoisonousFood(2, 0.6F);
	public static final Item POISONOUS_GLOW_BERRIES = MakePoisonousFood(2, 0.1F);
	public static final Item POISONOUS_SWEET_BERRIES = MakePoisonousFood(2, 0.1F);
	public static final Item POISONOUS_TOMATO = MakePoisonousFood(2, 0.3F);
	public static final Item WILTED_CABBAGE = MakePoisonousFood(2, 0.4F);
	public static final Item WILTED_ONION = MakePoisonousFood(2, 0.4F);
	public static final Item WILTED_KELP = MakeGeneratedItem();
	public static final Item MOLDY_BREAD = MakePoisonousFood(5, 0.6F);
	public static final Item MOLDY_COOKIE = MakePoisonousFood(2, 0.1F);
	public static final Item ROTTEN_PUMPKIN_PIE = MakePoisonousFood(8, 0.3F);
	public static final Item SPOILED_EGG = MakeGeneratedItem();
	public static final Item ROTTEN_BEEF = MakeRottenMeat(8, 0.8F);
	public static final Item ROTTEN_CHEVON = MakeRottenMeat(6, 0.6F);
	public static final Item ROTTEN_CHICKEN = MakeRottenMeat(6, 0.6F);
	public static final Item ROTTEN_COD = MakeRottenMeat(5, 0.6F);
	public static final Item ROTTEN_MUTTON = MakeRottenMeat(6, 0.8F);
	public static final Item ROTTEN_PORKCHOP = MakeRottenMeat(8, 0.8F);
	public static final Item ROTTEN_RABBIT = MakeRottenMeat(5, 0.6F);
	public static final Item ROTTEN_SALMON = MakeRottenMeat(6, 0.8F);
	//</editor-fold>
	//TODO: Frogs throw up jazz hands after jumping. They don't croak. What that tongue [not] do.
	//<editor-fold desc="Frogs">
	public static final BlockContainer OCHRE_FROGLIGHT = MakeFroglight(MapColor.PALE_YELLOW, ItemSettings(ItemGroup.DECORATIONS));
	public static final BlockContainer VERDANT_FROGLIGHT = MakeFroglight(MapColor.LICHEN_GREEN, ItemSettings(ItemGroup.DECORATIONS));
	public static final BlockContainer PEARLESCENT_FROGLIGHT = MakeFroglight(MapColor.PINK, ItemSettings(ItemGroup.DECORATIONS));

	public static final ModSensorType<TemptationsSensor> FROG_TEMPTATIONS_SENSOR = new ModSensorType<>("frog_temptations", () -> new TemptationsSensor(FrogBrain.getTemptItems()));
	public static final ModSensorType<FrogAttackablesSensor> FROG_ATTACKABLES_SENSOR = new ModSensorType<>("frog_attackables", FrogAttackablesSensor::new);
	public static final ModSensorType<IsInWaterSensor> IS_IN_WATER_SENSOR = new ModSensorType<>("is_in_water", IsInWaterSensor::new);
	public static final EntityType<FrogEntity> FROG_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, FrogEntity::new).dimensions(EntityDimensions.fixed(0.5f, 0.5f)).trackRangeChunks(10).build();
	public static final Item FROG_SPAWN_EGG = MakeSpawnEgg(FROG_ENTITY, 13661252, 0xFFC77C, ItemSettings(ItemGroup.MISC));
	private static final Block FROGSPAWN_BLOCK = new FrogspawnBlock(Block.Settings.of(FROGSPAWN_MATERIAL).breakInstantly().nonOpaque().noCollision().sounds(ModBlockSoundGroups.FROGSPAWN));
	public static final BlockContainer FROGSPAWN = new BlockContainer(FROGSPAWN_BLOCK, new LilyPadItem(FROGSPAWN_BLOCK, ItemSettings(ItemGroup.MISC))).drops(DropTable.NOTHING);
	public static final EntityType<TadpoleEntity> TADPOLE_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, TadpoleEntity::new).dimensions(EntityDimensions.fixed(TadpoleEntity.WIDTH, TadpoleEntity.HEIGHT)).trackRangeChunks(10).build();
	public static final Item TADPOLE_BUCKET = GeneratedItem(new EntityBucketItem(TADPOLE_ENTITY, Fluids.WATER, ModSoundEvents.ITEM_BUCKET_EMPTY_TADPOLE, ItemSettings(ItemGroup.MISC).maxCount(1)));
	public static final Item TADPOLE_SPAWN_EGG = MakeSpawnEgg(TADPOLE_ENTITY, 7164733, 1444352, ItemSettings(ItemGroup.MISC));
	//</editor-fold>
	public static final ScreenHandlerType<Generic1x1ContainerScreenHandler> GENERIC_1X1_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(ModId.ID("generic_1x1"), Generic1x1ContainerScreenHandler::new);
	//<editor-fold desc="Gilded Blackstone">
	public static final BlockContainer GILDED_BLACKSTONE_STAIRS = MakeStairs(Blocks.GILDED_BLACKSTONE).stairsModel(Blocks.GILDED_BLACKSTONE).blockTag(BlockTags.GUARDED_BY_PIGLINS).itemTag(ItemTags.PIGLIN_LOVED).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer GILDED_BLACKSTONE_SLAB = MakeSlab(Blocks.GILDED_BLACKSTONE).slabModel(Blocks.GILDED_BLACKSTONE).blockTag(BlockTags.GUARDED_BY_PIGLINS).itemTag(ItemTags.PIGLIN_LOVED).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer GILDED_BLACKSTONE_WALL = MakeWall(Blocks.GILDED_BLACKSTONE).wallModel(Blocks.GILDED_BLACKSTONE).blockTag(BlockTags.GUARDED_BY_PIGLINS).itemTag(ItemTags.PIGLIN_LOVED).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer POLISHED_GILDED_BLACKSTONE = MakeBlock(Blocks.GILDED_BLACKSTONE).cubeAllModel().blockTag(BlockTags.GUARDED_BY_PIGLINS).itemTag(ItemTags.PIGLIN_LOVED).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer POLISHED_GILDED_BLACKSTONE_STAIRS = MakeStairs(POLISHED_GILDED_BLACKSTONE).stairsModel(POLISHED_GILDED_BLACKSTONE).blockTag(BlockTags.GUARDED_BY_PIGLINS).itemTag(ItemTags.PIGLIN_LOVED).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer POLISHED_GILDED_BLACKSTONE_SLAB = MakeSlab(POLISHED_GILDED_BLACKSTONE).slabModel(POLISHED_GILDED_BLACKSTONE).blockTag(BlockTags.GUARDED_BY_PIGLINS).itemTag(ItemTags.PIGLIN_LOVED).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer POLISHED_GILDED_BLACKSTONE_WALL = MakeWall(POLISHED_GILDED_BLACKSTONE).wallModel(POLISHED_GILDED_BLACKSTONE).blockTag(BlockTags.GUARDED_BY_PIGLINS).itemTag(ItemTags.PIGLIN_LOVED).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer POLISHED_GILDED_BLACKSTONE_BRICKS = MakeBlock(Blocks.GILDED_BLACKSTONE).cubeAllModel().blockTag(BlockTags.GUARDED_BY_PIGLINS).itemTag(ItemTags.PIGLIN_LOVED).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer POLISHED_GILDED_BLACKSTONE_BRICK_STAIRS = MakeStairs(POLISHED_GILDED_BLACKSTONE_BRICKS).stairsModel(POLISHED_GILDED_BLACKSTONE_BRICKS).blockTag(BlockTags.GUARDED_BY_PIGLINS).itemTag(ItemTags.PIGLIN_LOVED).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer POLISHED_GILDED_BLACKSTONE_BRICK_SLAB = MakeSlab(POLISHED_GILDED_BLACKSTONE_BRICKS).slabModel(POLISHED_GILDED_BLACKSTONE_BRICKS).blockTag(BlockTags.GUARDED_BY_PIGLINS).itemTag(ItemTags.PIGLIN_LOVED).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer POLISHED_GILDED_BLACKSTONE_BRICK_WALL = MakeWall(POLISHED_GILDED_BLACKSTONE_BRICKS).wallModel(POLISHED_GILDED_BLACKSTONE_BRICKS).blockTag(BlockTags.GUARDED_BY_PIGLINS).itemTag(ItemTags.PIGLIN_LOVED).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer CRACKED_POLISHED_GILDED_BLACKSTONE_BRICKS = MakeBlock(Blocks.GILDED_BLACKSTONE).cubeAllModel().blockTag(BlockTags.GUARDED_BY_PIGLINS).itemTag(ItemTags.PIGLIN_LOVED).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer CHISELED_POLISHED_GILDED_BLACKSTONE = MakeBlock(Blocks.GILDED_BLACKSTONE).cubeAllModel().blockTag(BlockTags.GUARDED_BY_PIGLINS).itemTag(ItemTags.PIGLIN_LOVED).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer SMOOTH_CHISELED_POLISHED_GILDED_BLACKSTONE = MakeBlock(CHISELED_POLISHED_GILDED_BLACKSTONE).cubeAllModel().blockTag(BlockTags.GUARDED_BY_PIGLINS).itemTag(ItemTags.PIGLIN_LOVED).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer POLISHED_GILDED_BLACKSTONE_TILES = MakeBlock(POLISHED_GILDED_BLACKSTONE_BRICKS).cubeAllModel().blockTag(BlockTags.GUARDED_BY_PIGLINS).itemTag(ItemTags.PIGLIN_LOVED).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer POLISHED_GILDED_BLACKSTONE_TILE_STAIRS = MakeStairs(POLISHED_GILDED_BLACKSTONE_TILES).stairsModel(POLISHED_GILDED_BLACKSTONE_TILES).blockTag(BlockTags.GUARDED_BY_PIGLINS).itemTag(ItemTags.PIGLIN_LOVED).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer POLISHED_GILDED_BLACKSTONE_TILE_SLAB = MakeSlab(POLISHED_GILDED_BLACKSTONE_TILES).slabModel(POLISHED_GILDED_BLACKSTONE_TILES).blockTag(BlockTags.GUARDED_BY_PIGLINS).itemTag(ItemTags.PIGLIN_LOVED).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer POLISHED_GILDED_BLACKSTONE_TILE_WALL = MakeWall(POLISHED_GILDED_BLACKSTONE_TILES).wallModel(POLISHED_GILDED_BLACKSTONE_TILES).blockTag(BlockTags.GUARDED_BY_PIGLINS).itemTag(ItemTags.PIGLIN_LOVED).blockTag(BlockTags.PICKAXE_MINEABLE);
	//</editor-fold>
	//<editor-fold desc= "Gilded Fungus">
	public static final BlockContainer GILDED_STEM = MakeLog(MapColor.GOLD).blockTag(ModBlockTags.GILDED_STEMS).itemTag(ModItemTags.GILDED_STEMS).blockTag(BlockTags.NON_FLAMMABLE_WOOD).itemTag(ItemTags.NON_FLAMMABLE_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer STRIPPED_GILDED_STEM = MakeLog(MapColor.GOLD).blockTag(ModBlockTags.GILDED_STEMS).itemTag(ModItemTags.GILDED_STEMS).blockTag(BlockTags.NON_FLAMMABLE_WOOD).itemTag(ItemTags.NON_FLAMMABLE_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer GILDED_HYPHAE = MakeWood(MapColor.GOLD).blockTag(ModBlockTags.GILDED_STEMS).itemTag(ModItemTags.GILDED_STEMS).blockTag(BlockTags.NON_FLAMMABLE_WOOD).itemTag(ItemTags.NON_FLAMMABLE_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer STRIPPED_GILDED_HYPHAE = MakeWood(MapColor.GOLD).blockTag(ModBlockTags.GILDED_STEMS).itemTag(ModItemTags.GILDED_STEMS).blockTag(BlockTags.NON_FLAMMABLE_WOOD).itemTag(ItemTags.NON_FLAMMABLE_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer GILDED_PLANKS = MakePlanks(MapColor.GOLD, ModBlockSoundGroups.NETHER_WOOD).cubeAllModel().blockTag(BlockTags.PLANKS).itemTag(ItemTags.PLANKS).blockTag(BlockTags.NON_FLAMMABLE_WOOD).itemTag(ItemTags.NON_FLAMMABLE_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer GILDED_STAIRS = MakeWoodStairs(GILDED_PLANKS).stairsModel(GILDED_PLANKS).blockTag(BlockTags.NON_FLAMMABLE_WOOD).itemTag(ItemTags.NON_FLAMMABLE_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer GILDED_SLAB = MakeSlab(GILDED_PLANKS).slabModel(GILDED_PLANKS).blockTag(BlockTags.NON_FLAMMABLE_WOOD).itemTag(ItemTags.NON_FLAMMABLE_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer GILDED_FENCE = MakeWoodFence(GILDED_PLANKS).fenceModel(GILDED_PLANKS).blockTag(BlockTags.NON_FLAMMABLE_WOOD).itemTag(ItemTags.NON_FLAMMABLE_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer GILDED_FENCE_GATE = MakeWoodFenceGate(GILDED_PLANKS, ModSoundEvents.BLOCK_NETHER_WOOD_FENCE_GATE_OPEN, ModSoundEvents.BLOCK_NETHER_WOOD_FENCE_GATE_CLOSE).fenceGateModel(GILDED_PLANKS).blockTag(BlockTags.NON_FLAMMABLE_WOOD).itemTag(ItemTags.NON_FLAMMABLE_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer GILDED_DOOR = MakeWoodDoor(GILDED_PLANKS, ModBlockSoundGroups.NETHER_WOOD, ModSoundEvents.BLOCK_NETHER_WOOD_DOOR_OPEN, ModSoundEvents.BLOCK_NETHER_WOOD_DOOR_CLOSE).blockTag(BlockTags.NON_FLAMMABLE_WOOD).itemTag(ItemTags.NON_FLAMMABLE_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer GILDED_TRAPDOOR = MakeWoodTrapdoor(GILDED_PLANKS, ModBlockSoundGroups.NETHER_WOOD, ModSoundEvents.BLOCK_NETHER_WOOD_TRAPDOOR_OPEN, ModSoundEvents.BLOCK_NETHER_WOOD_TRAPDOOR_CLOSE).trapdoorModel(true).blockTag(BlockTags.NON_FLAMMABLE_WOOD).itemTag(ItemTags.NON_FLAMMABLE_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer GILDED_PRESSURE_PLATE = MakeWoodPressurePlate(GILDED_PLANKS, ModBlockSoundGroups.NETHER_WOOD, ModSoundEvents.BLOCK_NETHER_WOOD_PRESSURE_PLATE_CLICK_ON, ModSoundEvents.BLOCK_NETHER_WOOD_PRESSURE_PLATE_CLICK_OFF).blockTag(BlockTags.NON_FLAMMABLE_WOOD).itemTag(ItemTags.NON_FLAMMABLE_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer GILDED_BUTTON = MakeWoodButton(ModBlockSoundGroups.NETHER_WOOD, ModSoundEvents.BLOCK_NETHER_WOOD_BUTTON_CLICK_ON, ModSoundEvents.BLOCK_NETHER_WOOD_BUTTON_CLICK_OFF).buttonModel(GILDED_PLANKS).blockTag(BlockTags.NON_FLAMMABLE_WOOD).itemTag(ItemTags.NON_FLAMMABLE_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final SignContainer GILDED_SIGN = MakeSign("gilded", GILDED_PLANKS, STRIPPED_GILDED_STEM, ModBlockSoundGroups.NETHER_WOOD).blockTag(BlockTags.NON_FLAMMABLE_WOOD).itemTag(ItemTags.NON_FLAMMABLE_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer GILDED_IRON_HANGING_SIGN = MakeHangingSign(MakeSignType("gilded_iron"), STRIPPED_GILDED_STEM, ModBlockSoundGroups.NETHER_WOOD).blockTag(BlockTags.NON_FLAMMABLE_WOOD).itemTag(ItemTags.NON_FLAMMABLE_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer GILDED_GOLD_HANGING_SIGN = MakeHangingSign(MakeSignType("gilded_gold"), STRIPPED_GILDED_STEM, ModBlockSoundGroups.NETHER_WOOD).blockTag(BlockTags.NON_FLAMMABLE_WOOD).itemTag(ItemTags.NON_FLAMMABLE_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer GILDED_COPPER_HANGING_SIGN = MakeHangingSign(MakeSignType("gilded_copper"), STRIPPED_GILDED_STEM, ModBlockSoundGroups.NETHER_WOOD).blockTag(BlockTags.NON_FLAMMABLE_WOOD).itemTag(ItemTags.NON_FLAMMABLE_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer GILDED_EXPOSED_COPPER_HANGING_SIGN = MakeHangingSign(MakeSignType("gilded_exposed_copper"), STRIPPED_GILDED_STEM, ModBlockSoundGroups.NETHER_WOOD).blockTag(BlockTags.NON_FLAMMABLE_WOOD).itemTag(ItemTags.NON_FLAMMABLE_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer GILDED_WEATHERED_COPPER_HANGING_SIGN = MakeHangingSign(MakeSignType("gilded_weathered_copper"), STRIPPED_GILDED_STEM, ModBlockSoundGroups.NETHER_WOOD).blockTag(BlockTags.NON_FLAMMABLE_WOOD).itemTag(ItemTags.NON_FLAMMABLE_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer GILDED_OXIDIZED_COPPER_HANGING_SIGN = MakeHangingSign(MakeSignType("gilded_oxidized_copper"), STRIPPED_GILDED_STEM, ModBlockSoundGroups.NETHER_WOOD).blockTag(BlockTags.NON_FLAMMABLE_WOOD).itemTag(ItemTags.NON_FLAMMABLE_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer GILDED_NETHERITE_HANGING_SIGN = MakeHangingSign(MakeSignType("gilded_netherite"), STRIPPED_GILDED_STEM, ModBlockSoundGroups.NETHER_WOOD).blockTag(BlockTags.NON_FLAMMABLE_WOOD).itemTag(ItemTags.NON_FLAMMABLE_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer GILDED_BEEHIVE = MakeBeehive(MapColor.GOLD, ModBlockSoundGroups.NETHER_WOOD).blockTag(BlockTags.NON_FLAMMABLE_WOOD).itemTag(ItemTags.NON_FLAMMABLE_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer GILDED_BOOKSHELF = MakeBookshelf(GILDED_PLANKS, ModBlockSoundGroups.NETHER_WOOD).blockTag(BlockTags.NON_FLAMMABLE_WOOD).itemTag(ItemTags.NON_FLAMMABLE_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer GILDED_CHISELED_BOOKSHELF = MakeChiseledBookshelf(MapColor.GOLD, ModBlockSoundGroups.NETHER_WOOD).blockTag(BlockTags.NON_FLAMMABLE_WOOD).itemTag(ItemTags.NON_FLAMMABLE_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer GILDED_CRAFTING_TABLE = MakeCraftingTable(GILDED_PLANKS, ModBlockSoundGroups.NETHER_WOOD).blockTag(BlockTags.NON_FLAMMABLE_WOOD).itemTag(ItemTags.NON_FLAMMABLE_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer GILDED_LADDER = MakeLadder().blockTag(BlockTags.NON_FLAMMABLE_WOOD).itemTag(ItemTags.NON_FLAMMABLE_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer GILDED_WOODCUTTER = MakeWoodcutter(GILDED_PLANKS).blockTag(BlockTags.NON_FLAMMABLE_WOOD).itemTag(ItemTags.NON_FLAMMABLE_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer GILDED_BARREL = MakeBarrel(MapColor.GOLD, ModBlockSoundGroups.NETHER_WOOD).blockTag(BlockTags.NON_FLAMMABLE_WOOD).itemTag(ItemTags.NON_FLAMMABLE_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer GILDED_LECTERN = MakeLectern(GILDED_PLANKS, ModBlockSoundGroups.NETHER_WOOD).blockTag(BlockTags.NON_FLAMMABLE_WOOD).itemTag(ItemTags.NON_FLAMMABLE_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer GILDED_POWDER_KEG = MakePowderKeg(GILDED_BARREL).blockTag(BlockTags.NON_FLAMMABLE_WOOD).itemTag(ItemTags.NON_FLAMMABLE_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer GILDED_STEM_SLAB = MakeLogSlab(GILDED_STEM).blockTag(BlockTags.NON_FLAMMABLE_WOOD).itemTag(ItemTags.NON_FLAMMABLE_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer STRIPPED_GILDED_STEM_SLAB = MakeLogSlab(STRIPPED_GILDED_STEM).blockTag(BlockTags.NON_FLAMMABLE_WOOD).itemTag(ItemTags.NON_FLAMMABLE_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer GILDED_HYPHAE_SLAB = MakeBarkSlab(GILDED_HYPHAE, GILDED_STEM).blockTag(BlockTags.NON_FLAMMABLE_WOOD).itemTag(ItemTags.NON_FLAMMABLE_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer STRIPPED_GILDED_HYPHAE_SLAB = MakeBarkSlab(STRIPPED_GILDED_HYPHAE, STRIPPED_GILDED_STEM).blockTag(BlockTags.NON_FLAMMABLE_WOOD).itemTag(ItemTags.NON_FLAMMABLE_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	//Misc
	public static final Item GILDED_HAMMER = MakeHammer(ToolMaterials.WOOD, 7, -3.2F);
	//Torches
	public static final TorchContainer GILDED_TORCH = MakeTorch(ModBlockSoundGroups.NETHER_WOOD).torchModel();
	public static final TorchContainer GILDED_SOUL_TORCH = MakeSoulTorch(ModBlockSoundGroups.NETHER_WOOD).torchModel(GILDED_TORCH);
	public static final TorchContainer GILDED_ENDER_TORCH = MakeEnderTorch(ModBlockSoundGroups.NETHER_WOOD).torchModel(GILDED_TORCH);
	public static final TorchContainer UNDERWATER_GILDED_TORCH = MakeUnderwaterTorch(ModBlockSoundGroups.NETHER_WOOD).torchModel(GILDED_TORCH);
	//Campfires
	public static final BlockContainer GILDED_CAMPFIRE = MakeCampfire(MapColor.GOLD, BlockSoundGroup.NETHER_STEM).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer GILDED_SOUL_CAMPFIRE = MakeSoulCampfire(GILDED_CAMPFIRE, BlockSoundGroup.NETHER_STEM).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer GILDED_ENDER_CAMPFIRE = MakeEnderCampfire(GILDED_CAMPFIRE, BlockSoundGroup.NETHER_STEM).blockTag(BlockTags.AXE_MINEABLE);
	//Fungus
	private static ConfiguredFeature<HugeFungusFeatureConfig, ?> getGildedFungiPlanted() { return GILDED_FUNGI_PLANTED.getFeature(); }
	public static final FungusContainer GILDED_FUNGUS = new FungusContainer(ModBase::getGildedFungiPlanted, MapColor.GOLD).blockTag(BlockTags.ENDERMAN_HOLDABLE).blockTag(BlockTags.AXE_MINEABLE);
	public static final PottedBlockContainer GILDED_ROOTS = new PottedBlockContainer(new ModRootsBlock(Block.Settings.of(Material.NETHER_SHOOTS, MapColor.GOLD).noCollision().breakInstantly().sounds(BlockSoundGroup.ROOTS))).blockTag(ModBlockTags.COMBINATION_STEP_SOUND_BLOCKS).blockTag(BlockTags.ENDERMAN_HOLDABLE).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer GILDED_WART = new BlockContainer(new ModWartBlock(Block.Settings.copy(Blocks.NETHER_WART).mapColor(MapColor.GOLD))).compostable(0.65f).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer GILDED_WART_BLOCK = MakeBlock(Block.Settings.copy(Blocks.WARPED_WART_BLOCK).mapColor(MapColor.GOLD)).cubeAllModel().blockTag(BlockTags.WART_BLOCKS).itemTag(ModItemTags.WART_BLOCKS).blockTag(BlockTags.HOE_MINEABLE);
	public static final BlockContainer GILDED_WART_SLAB = MakeSlab(GILDED_WART_BLOCK).slabModel(GILDED_WART_BLOCK).blockTag(BlockTags.HOE_MINEABLE);
	public static final BlockContainer GILDED_NYLIUM = BuildBlock(new ModNyliumBlock(Block.Settings.of(Material.STONE, MapColor.GOLD).requiresTool().strength(0.4F).sounds(BlockSoundGroup.NYLIUM).ticksRandomly()), DropTable.SilkTouchOrElse(Items.NETHERRACK)).blockTag(BlockTags.NYLIUM, BlockTags.MUSHROOM_GROW_BLOCK).blockTag(BlockTags.PICKAXE_MINEABLE);
	//</editor-fold>
	//<editor-fold desc="Glass">
	public static final BlockContainer TINTED_GLASS_PANE = BuildBlock(new TintedGlassPaneBlock(Block.Settings.of(Material.GLASS).mapColor(MapColor.GRAY).strength(0.3F).sounds(BlockSoundGroup.GLASS).nonOpaque())).paneModel(Blocks.TINTED_GLASS).itemTag(ModItemTags.GLASS_PANES);
	public static final BlockContainer TINTED_GLASS_SLAB = BuildSlab(new TintedGlassSlabBlock(Blocks.TINTED_GLASS)).blockTag(BlockTags.IMPERMEABLE);
	public static final BlockContainer TINTED_GLASS_TRAPDOOR = BuildBlock(new TintedGlassTrapdoorBlock(Blocks.TINTED_GLASS, TINTED_GLASS_SLAB.asBlock())).blockTag(BlockTags.TRAPDOORS).itemTag(ItemTags.TRAPDOORS);
	public static final BlockContainer RUBY_GLASS = BuildBlock(new TintedGlassBlock(Block.Settings.copy(Blocks.TINTED_GLASS).mapColor(MapColor.RED))).cubeAllModel().blockTag(BlockTags.IMPERMEABLE);
	public static final BlockContainer RUBY_GLASS_PANE = BuildBlock(new TintedGlassPaneBlock(Block.Settings.of(Material.GLASS).mapColor(MapColor.RED).strength(0.3F).sounds(BlockSoundGroup.GLASS).nonOpaque())).paneModel(RUBY_GLASS).itemTag(ModItemTags.GLASS_PANES);
	public static final BlockContainer RUBY_GLASS_SLAB = BuildSlab(new TintedGlassSlabBlock(RUBY_GLASS.asBlock())).blockTag(BlockTags.IMPERMEABLE);
	public static final BlockContainer RUBY_GLASS_TRAPDOOR = BuildBlock(new TintedGlassTrapdoorBlock(RUBY_GLASS.asBlock(), RUBY_GLASS_SLAB.asBlock())).blockTag(BlockTags.TRAPDOORS).itemTag(ItemTags.TRAPDOORS);
	public static final BlockContainer SAPPHIRE_GLASS = BuildBlock(new TintedGlassBlock(Block.Settings.copy(Blocks.TINTED_GLASS).mapColor(MapColor.BLUE))).cubeAllModel().blockTag(BlockTags.IMPERMEABLE);
	public static final BlockContainer SAPPHIRE_GLASS_PANE = BuildBlock(new TintedGlassPaneBlock(Block.Settings.of(Material.GLASS).mapColor(MapColor.BLUE).strength(0.3F).sounds(BlockSoundGroup.GLASS).nonOpaque())).paneModel(SAPPHIRE_GLASS).itemTag(ModItemTags.GLASS_PANES);
	public static final BlockContainer SAPPHIRE_GLASS_SLAB = BuildSlab(new TintedGlassSlabBlock(SAPPHIRE_GLASS.asBlock())).blockTag(BlockTags.IMPERMEABLE);
	public static final BlockContainer SAPPHIRE_GLASS_TRAPDOOR = BuildBlock(new TintedGlassTrapdoorBlock(SAPPHIRE_GLASS.asBlock(), SAPPHIRE_GLASS_SLAB.asBlock())).blockTag(BlockTags.TRAPDOORS).itemTag(ItemTags.TRAPDOORS);
	public static final BlockContainer GLASS_SLAB = BuildSlab(new GlassSlabBlock(Blocks.GLASS), DropTable.SILK_TOUCH_SLAB).blockTag(BlockTags.IMPERMEABLE);
	public static final BlockContainer GLASS_TRAPDOOR = new BlockContainer(new GlassTrapdoorBlock(Blocks.GLASS, GLASS_SLAB.asBlock())).requireSilkTouch().blockTag(BlockTags.TRAPDOORS).itemTag(ItemTags.TRAPDOORS);
	public static final Map<DyeColor, BlockContainer> STAINED_GLASS_SLABS = ColorUtil.Map(color -> BuildSlab(new StainedGlassSlabBlock(color, ColorUtil.GetStainedGlassBlock(color)), DropTable.SILK_TOUCH_SLAB).blockTag(BlockTags.IMPERMEABLE));
	public static final Map<DyeColor, BlockContainer> STAINED_GLASS_TRAPDOORS = ColorUtil.Map(color -> new BlockContainer(new StainedGlassTrapdoorBlock(color, ColorUtil.GetStainedGlassBlock(color), STAINED_GLASS_SLABS.get(color).asBlock())).requireSilkTouch().blockTag(BlockTags.TRAPDOORS).itemTag(ItemTags.TRAPDOORS));
	//</editor-fold>
	//<editor-fold desc="Glow Lichen">
	public static final BlockContainer GLOW_LICHEN_BLOCK = BuildBlock(new GlowLichenFullBlock(Block.Settings.of(Material.MOSS_BLOCK, MapColor.LICHEN_GREEN).strength(0.1f).sounds(BlockSoundGroup.GLOW_LICHEN).luminance(LUMINANCE_7))).compostable(0.65f).blockTag(BlockTags.HOE_MINEABLE);
	public static final BlockContainer GLOW_LICHEN_SLAB = BuildSlab(new GlowLichenSlabBlock(GLOW_LICHEN_BLOCK)).compostable(0.325f).slabModel(GLOW_LICHEN_BLOCK).blockTag(BlockTags.HOE_MINEABLE);
	public static final BlockContainer GLOW_LICHEN_CARPET = BuildBlock(new CarpetBlock(Block.Settings.copy(Blocks.MOSS_CARPET).mapColor(MapColor.LICHEN_GREEN).luminance(LUMINANCE_7))).compostable(0.3f).blockTag(BlockTags.CARPETS).itemTag(ItemTags.CARPETS).blockTag(ModBlockTags.COMBINATION_STEP_SOUND_BLOCKS).blockTag(BlockTags.HOE_MINEABLE);
	public static final BedContainer GLOW_LICHEN_BED = MakeBed("glow_lichen", MapColor.LICHEN_GREEN, BlockSoundGroup.GLOW_LICHEN, LUMINANCE_7);
	//</editor-fold>
	//<editor-fold desc="Goat">
	public static final LootFunctionType SET_INSTRUMENT_LOOT_FUNCTION = new LootFunctionType(new SetInstrumentLootFunction.Serializer());
	public static final Item GOAT_HORN = new GoatHornItem(ItemSettings(ItemGroup.MISC).maxCount(1));
	//Extended
	public static final Item CHEVON = MakeFood(FoodComponents.MUTTON);
	public static final Item COOKED_CHEVON = MakeFood(FoodComponents.COOKED_MUTTON);
	public static final Item GOAT_HORN_SALVE = GeneratedItem(new GoatHornSalveItem(ItemSettings()));
	//</editor-fold>
	//<editor-fold desc="Goggles">
	public static final Item TINTED_GOGGLES = MakeHelmet(ModArmorMaterials.TINTED_GOGGLES);
	public static final Item RUBY_GOGGLES = MakeHelmet(ModArmorMaterials.RUBY_GOGGLES);
	public static final Item SAPPHIRE_GOGGLES = MakeHelmet(ModArmorMaterials.SAPPHIRE_GOGGLES);
	//</editor-fold>
	//<editor-fold desc="Gold">
	public static final Item GOLD_ROD = MakeHandheldItem();
	public static final BlockContainer END_GOLD_ORE = BuildBlock(new OreBlock(Block.Settings.of(Material.STONE, MapColor.PALE_YELLOW).requiresTool().strength(3f, 3f).sounds(ModBlockSoundGroups.END_GOLD_ORE), UniformIntProvider.create(0, 1)), DropTable.EndOre(Items.GOLD_NUGGET)).cubeAllModel().blockTag(BlockTags.GOLD_ORES).blockTag(ModBlockTags.SNAPS_GOAT_HORN).blockTag(BlockTags.NEEDS_IRON_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final TorchContainer GOLD_TORCH = MakeTorch(14, BlockSoundGroup.METAL, ModParticleTypes.GOLD_FLAME).torchModel();
	public static final TorchContainer GOLD_SOUL_TORCH = MakeSoulTorch(BlockSoundGroup.METAL).torchModel(GOLD_TORCH);
	public static final TorchContainer GOLD_ENDER_TORCH = MakeEnderTorch(BlockSoundGroup.METAL).torchModel(GOLD_TORCH);
	public static final TorchContainer UNDERWATER_GOLD_TORCH = MakeUnderwaterTorch(BlockSoundGroup.METAL).torchModel(GOLD_TORCH);
	public static final BlockContainer GOLD_LANTERN = MakeLantern(15).blockTag(BlockTags.GUARDED_BY_PIGLINS).itemTag(ItemTags.PIGLIN_LOVED).blockTag(BlockTags.NEEDS_IRON_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer GOLD_SOUL_LANTERN = MakeSoulLantern().blockTag(BlockTags.GUARDED_BY_PIGLINS).itemTag(ItemTags.PIGLIN_LOVED).blockTag(BlockTags.NEEDS_IRON_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer GOLD_ENDER_LANTERN = MakeLantern(13).blockTag(BlockTags.GUARDED_BY_PIGLINS).itemTag(ItemTags.PIGLIN_LOVED).blockTag(BlockTags.NEEDS_IRON_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer GOLD_BUTTON = MakeMetalButton().buttonModel(Blocks.GOLD_BLOCK).blockTag(BlockTags.GUARDED_BY_PIGLINS).itemTag(ItemTags.PIGLIN_LOVED).blockTag(BlockTags.NEEDS_IRON_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer GOLD_CHAIN = MakeChain().blockTag(BlockTags.GUARDED_BY_PIGLINS).itemTag(ItemTags.PIGLIN_LOVED).blockTag(BlockTags.NEEDS_IRON_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer HEAVY_GOLD_CHAIN = MakeHeavyChain().blockTag(BlockTags.GUARDED_BY_PIGLINS).itemTag(ItemTags.PIGLIN_LOVED).blockTag(BlockTags.NEEDS_IRON_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer GOLD_BARS = MakeBars().blockTag(BlockTags.GUARDED_BY_PIGLINS).itemTag(ItemTags.PIGLIN_LOVED).blockTag(BlockTags.NEEDS_IRON_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer GOLD_STAIRS = MakeStairs(Blocks.GOLD_BLOCK).stairsModel(Blocks.GOLD_BLOCK).blockTag(BlockTags.GUARDED_BY_PIGLINS).itemTag(ItemTags.PIGLIN_LOVED).blockTag(BlockTags.NEEDS_IRON_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer GOLD_SLAB = MakeSlab(Blocks.GOLD_BLOCK).slabModel(Blocks.GOLD_BLOCK).blockTag(BlockTags.GUARDED_BY_PIGLINS).itemTag(ItemTags.PIGLIN_LOVED).blockTag(BlockTags.NEEDS_IRON_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer GOLD_WALL = MakeWall(Blocks.GOLD_BLOCK).wallModel(Blocks.GOLD_BLOCK).blockTag(BlockTags.GUARDED_BY_PIGLINS).itemTag(ItemTags.PIGLIN_LOVED).blockTag(BlockTags.NEEDS_IRON_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer GOLD_BRICKS = MakeBlock(Blocks.GOLD_BLOCK).cubeAllModel().blockTag(BlockTags.GUARDED_BY_PIGLINS).itemTag(ItemTags.PIGLIN_LOVED).blockTag(BlockTags.NEEDS_IRON_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer GOLD_BRICK_STAIRS = MakeStairs(GOLD_BRICKS).stairsModel(GOLD_BRICKS).blockTag(BlockTags.GUARDED_BY_PIGLINS).itemTag(ItemTags.PIGLIN_LOVED).blockTag(BlockTags.NEEDS_IRON_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer GOLD_BRICK_SLAB = MakeSlab(GOLD_BRICKS).slabModel(GOLD_BRICKS).blockTag(BlockTags.GUARDED_BY_PIGLINS).itemTag(ItemTags.PIGLIN_LOVED).blockTag(BlockTags.NEEDS_IRON_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer GOLD_BRICK_WALL = MakeWall(GOLD_BRICKS).wallModel(GOLD_BRICKS).blockTag(BlockTags.GUARDED_BY_PIGLINS).itemTag(ItemTags.PIGLIN_LOVED).blockTag(BlockTags.NEEDS_IRON_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer CUT_GOLD = MakeBlock(Blocks.GOLD_BLOCK).cubeAllModel().blockTag(BlockTags.GUARDED_BY_PIGLINS).itemTag(ItemTags.PIGLIN_LOVED).blockTag(BlockTags.NEEDS_IRON_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer CUT_GOLD_PILLAR = BuildBlock(new ModPillarBlock(CUT_GOLD)).blockTag(BlockTags.GUARDED_BY_PIGLINS).itemTag(ItemTags.PIGLIN_LOVED).blockTag(BlockTags.NEEDS_IRON_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer CUT_GOLD_STAIRS = MakeStairs(CUT_GOLD).stairsModel(CUT_GOLD).blockTag(BlockTags.GUARDED_BY_PIGLINS).itemTag(ItemTags.PIGLIN_LOVED).blockTag(BlockTags.NEEDS_IRON_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer CUT_GOLD_SLAB = MakeSlab(CUT_GOLD).slabModel(CUT_GOLD).blockTag(BlockTags.GUARDED_BY_PIGLINS).itemTag(ItemTags.PIGLIN_LOVED).blockTag(BlockTags.NEEDS_IRON_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer CUT_GOLD_WALL = MakeWall(CUT_GOLD).wallModel(CUT_GOLD).blockTag(BlockTags.GUARDED_BY_PIGLINS).itemTag(ItemTags.PIGLIN_LOVED).blockTag(BlockTags.NEEDS_IRON_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer GOLD_DOOR = BuildMetalDoor(new ModDoorBlock(Block.Settings.copy(Blocks.IRON_DOOR).mapColor(MapColor.GOLD))).blockTag(BlockTags.NEEDS_IRON_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer GOLD_TRAPDOOR = MakeThinMetalTrapdoor(MapColor.YELLOW, BlockSoundGroup.METAL, 3.0f).blockTag(BlockTags.GUARDED_BY_PIGLINS).itemTag(ItemTags.PIGLIN_LOVED).blockTag(BlockTags.NEEDS_IRON_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final Item GOLDEN_HAMMER = MakeHammer(ToolMaterials.GOLD, 7, -3);
	public static final ShearsItem GOLDEN_SHEARS = MakeShears(ToolMaterials.GOLD);
	private static final BucketContainer GOLDEN_BUCKET_PROVIDER = new BucketContainer();
	public static final Item GOLDEN_BUCKET = GOLDEN_BUCKET_PROVIDER.getBucket();
	public static final Item GOLDEN_WATER_BUCKET = GOLDEN_BUCKET_PROVIDER.getWaterBucket();
	public static final Item GOLDEN_LAVA_BUCKET = GOLDEN_BUCKET_PROVIDER.getLavaBucket();
	public static final Item GOLDEN_POWDER_SNOW_BUCKET = GOLDEN_BUCKET_PROVIDER.getPowderSnowBucket();
	public static final Item GOLDEN_BLOOD_BUCKET = GOLDEN_BUCKET_PROVIDER.getBloodBucket();
	public static final Item GOLDEN_MUD_BUCKET = GOLDEN_BUCKET_PROVIDER.getMudBucket();
	public static final Item GOLDEN_MILK_BUCKET = GOLDEN_BUCKET_PROVIDER.getMilkBucket();
	public static final Item GOLDEN_CHOCOLATE_MILK_BUCKET = GOLDEN_BUCKET_PROVIDER.getChocolateMilkBucket();
	public static final Item GOLDEN_COFFEE_MILK_BUCKET = GOLDEN_BUCKET_PROVIDER.getCoffeeMilkBucket();
	public static final Item GOLDEN_STRAWBERRY_MILK_BUCKET = GOLDEN_BUCKET_PROVIDER.getStrawberryMilkBucket();
	public static final Item GOLDEN_VANILLA_MILK_BUCKET = GOLDEN_BUCKET_PROVIDER.getVanillaMilkBucket();
	//</editor-fold>
	//<editor-fold desc="Gourds">
	public static final BlockContainer SOUL_JACK_O_LANTERN = MakeGourdLantern(MapColor.ORANGE, LUMINANCE_10).blockTag(ModBlockTags.GOURD_LANTERNS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer ENDER_JACK_O_LANTERN = MakeGourdLantern(MapColor.ORANGE, LUMINANCE_13).blockTag(ModBlockTags.GOURD_LANTERNS).blockTag(BlockTags.AXE_MINEABLE);
	//Burnt Pumpkin
	public static final BlockContainer BURNT_PUMPKIN = MakeBlock(Block.Settings.copy(Blocks.PUMPKIN).mapColor(MapColor.BLACK)).blockTag(ModBlockTags.PUMPKINS).blockTag(BlockTags.AXE_MINEABLE);
	//White Pumpkin
	private static StemBlock getWhitePumpkinStem() { return WHITE_PUMPKIN_STEM; }
	private static AttachedStemBlock getAttachedWhitePumpkinStem() { return ATTACHED_WHITE_PUMPKIN_STEM; }
	private static Item getWhitePumpkinSeeds() { return WHITE_PUMPKIN_SEEDS; }
	public static final BlockContainer CARVED_WHITE_PUMPKIN = BuildBlock(new ModCarvedPumpkinBlock(GourdSettings(MapColor.WHITE), () -> SnowGolemVariant.WHITE)).compostable(0.65f).blockTag(ModBlockTags.CARVED_PUMPKINS).itemTag(ModItemTags.CARVED_PUMPKINS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer WHITE_PUMPKIN = BuildBlock(new CarvableGourdBlock(
			Block.Settings.of(Material.GOURD, MapColor.WHITE).strength(1.0F).sounds(BlockSoundGroup.WOOD),
			SoundEvents.BLOCK_PUMPKIN_CARVE, ModBase::getWhitePumpkinStem, ModBase::getAttachedWhitePumpkinStem,
			() -> (CarvedGourdBlock)CARVED_WHITE_PUMPKIN.asBlock(), () -> new ItemStack(getWhitePumpkinSeeds(), 4))).compostable(0.65f).blockTag(ModBlockTags.PUMPKINS).blockTag(BlockTags.AXE_MINEABLE);
	public static final StemBlock WHITE_PUMPKIN_STEM = new GourdStemBlock((GourdBlock)WHITE_PUMPKIN.asBlock(), ModBase::getWhitePumpkinSeeds, Block.Settings.copy(Blocks.PUMPKIN_STEM));
	public static final AttachedStemBlock ATTACHED_WHITE_PUMPKIN_STEM = new AttachedGourdStemBlock((GourdBlock)WHITE_PUMPKIN.asBlock(), ModBase::getWhitePumpkinSeeds, Block.Settings.copy(Blocks.ATTACHED_PUMPKIN_STEM));
	public static final Item WHITE_PUMPKIN_SEEDS = GeneratedItem(new AliasedBlockItem(WHITE_PUMPKIN_STEM, ItemSettings()));
	public static final BlockContainer WHITE_JACK_O_LANTERN = BuildBlock(new ModCarvedPumpkinBlock(GourdSettings(MapColor.WHITE).luminance(LUMINANCE_15), () -> SnowGolemVariant.WHITE)).blockTag(ModBlockTags.GOURD_LANTERNS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer WHITE_SOUL_JACK_O_LANTERN = MakeGourdLantern(MapColor.WHITE, LUMINANCE_10).blockTag(ModBlockTags.GOURD_LANTERNS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer WHITE_ENDER_JACK_O_LANTERN = MakeGourdLantern(MapColor.WHITE, LUMINANCE_13).blockTag(ModBlockTags.GOURD_LANTERNS).blockTag(BlockTags.AXE_MINEABLE);
	//Rotten Pumpkin
	public static final BlockContainer CARVED_ROTTEN_PUMPKIN = BuildBlock(new ModCarvedPumpkinBlock(GourdSettings(MapColor.PALE_YELLOW), () -> SnowGolemVariant.ROTTEN)).compostable(0.65F).blockTag(ModBlockTags.CARVED_PUMPKINS).itemTag(ModItemTags.CARVED_PUMPKINS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer ROTTEN_JACK_O_LANTERN = BuildBlock(new ModCarvedPumpkinBlock(GourdSettings(MapColor.PALE_YELLOW).luminance(LUMINANCE_15), () -> SnowGolemVariant.ROTTEN)).blockTag(ModBlockTags.GOURD_LANTERNS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer ROTTEN_SOUL_JACK_O_LANTERN = MakeGourdLantern(MapColor.PALE_YELLOW, LUMINANCE_10).blockTag(ModBlockTags.GOURD_LANTERNS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer ROTTEN_ENDER_JACK_O_LANTERN = MakeGourdLantern(MapColor.PALE_YELLOW, LUMINANCE_13).blockTag(ModBlockTags.GOURD_LANTERNS).blockTag(BlockTags.AXE_MINEABLE);
	public static final Item ROTTEN_PUMPKIN_SEEDS = MakeGeneratedItem();
	public static final BlockContainer ROTTEN_PUMPKIN = BuildBlock(new CarvableGourdBlock(
			Block.Settings.of(Material.GOURD, MapColor.PALE_YELLOW).strength(1.0F).sounds(BlockSoundGroup.WOOD),
			SoundEvents.BLOCK_PUMPKIN_CARVE, () -> (StemBlock)Blocks.PUMPKIN_STEM, () -> (AttachedStemBlock)Blocks.ATTACHED_PUMPKIN_STEM,
			() -> (CarvedGourdBlock) CARVED_ROTTEN_PUMPKIN.asBlock(), () -> new ItemStack(ROTTEN_PUMPKIN_SEEDS, 4))).compostable(0.65f).blockTag(ModBlockTags.PUMPKINS).blockTag(BlockTags.AXE_MINEABLE);
	//</editor-fold>
	//<editor-fold desc="Granite">
	public static final BlockContainer CHISELED_GRANITE = MakeBlock(Blocks.GRANITE).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer CHISELED_GRANITE_SLAB = MakeSlab(CHISELED_GRANITE).slabModel(CHISELED_GRANITE).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer GRANITE_BRICKS = MakeBlock(Blocks.GRANITE).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer GRANITE_BRICK_STAIRS = MakeStairs(GRANITE_BRICKS).stairsModel(GRANITE_BRICKS).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer GRANITE_BRICK_SLAB = MakeSlab(GRANITE_BRICKS).slabModel(GRANITE_BRICKS).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer GRANITE_BRICK_WALL = MakeWall(GRANITE_BRICKS).wallModel(GRANITE_BRICKS).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer CHISELED_GRANITE_BRICKS = MakeBlock(GRANITE_BRICKS).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer GRANITE_TILES = MakeBlock(GRANITE_BRICKS).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer GRANITE_TILE_STAIRS = MakeStairs(GRANITE_TILES).stairsModel(GRANITE_TILES).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer GRANITE_TILE_SLAB = MakeSlab(GRANITE_TILES).slabModel(GRANITE_TILES).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer GRANITE_TILE_WALL = MakeWall(GRANITE_TILES).wallModel(GRANITE_TILES).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer POLISHED_GRANITE_WALL = MakeWall(Blocks.POLISHED_GRANITE).wallModel(Blocks.POLISHED_GRANITE).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer CUT_POLISHED_GRANITE = MakeBlock(Blocks.POLISHED_GRANITE).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer CUT_POLISHED_GRANITE_STAIRS = MakeStairs(CUT_POLISHED_GRANITE).stairsModel(CUT_POLISHED_GRANITE).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer CUT_POLISHED_GRANITE_SLAB = MakeSlab(CUT_POLISHED_GRANITE).slabModel(CUT_POLISHED_GRANITE).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer CUT_POLISHED_GRANITE_WALL = MakeWall(CUT_POLISHED_GRANITE).wallModel(CUT_POLISHED_GRANITE).blockTag(BlockTags.PICKAXE_MINEABLE);
	//</editor-fold>
	//<editor-fold desc="Grapes">
	public static final Block GRAPE_VINES = new GrapeVinesHeadBlock(Block.Settings.of(Material.PLANT).ticksRandomly().noCollision().breakInstantly().sounds(ModBlockSoundGroups.GRAPE_VINES));
	public static final Block GRAPE_VINES_PLANT = new GrapeVinesBodyBlock(Block.Settings.of(Material.PLANT).noCollision().breakInstantly().sounds(ModBlockSoundGroups.GRAPE_VINES));
	public static final Item GRAPES = MakeFood(2, 0.1F);
	public static final BlockContainer GRAPE_LEAVES = new BlockContainer(new GrapeLeavesBlock(Blocks.SPRUCE_LEAVES)).flammable(30, 60).compostable(0.3f).blockTag(BlockTags.LEAVES).itemTag(ItemTags.LEAVES).blockTag(BlockTags.HOE_MINEABLE, ModBlockTags.GRAPE_GROWABLE);
	public static final PottedBlockContainer GRAPE_SAPLING = new PottedBlockContainer(new ModSaplingBlock(new GrapeSaplingGenerator(), Block.Settings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly())).compostable(0.3f).dropSelf().pottedModel().blockTag(BlockTags.SAPLINGS).itemTag(ItemTags.SAPLINGS);
	//</editor-fold>
	public static final Item GRAPPLING_ROD = new GrapplingRodItem(ItemSettings().maxDamage(256));
	//<editor-fold desc="Gunpowder">
	public static final BlockContainer GUNPOWDER_BLOCK = MakeBlock(Block.Settings.of(Material.WOOL, MapColor.GRAY).strength(0.8f).sounds(BlockSoundGroup.SAND)).flammable(100, 100).cubeAllModel().blockTag(BlockTags.SHOVEL_MINEABLE);
	public static final BlockContainer GUNPOWDER_FUSE = BuildBlock(new GunpowderFuseBlock(Block.Settings.of(Material.DECORATION).noCollision().breakInstantly().luminance(s -> s.get(Properties.LIT) ? 15 : 0))).flammable(100, 100).generatedItemModel();
	//</editor-fold>
	//<editor-fold desc="Hammers">
	public static final Item GRAVITY_HAMMER = new GravityHammerItem(2, ModToolMaterials.OBSIDIAN, ModToolMaterials.OBSIDIAN.getHammerDamage() + 1, ModToolMaterials.OBSIDIAN.getHammerSpeed());
	public static final Item REPULSION_HAMMER = new GravityHammerItem(-2, ModToolMaterials.END_STONE, ModToolMaterials.END_STONE.getHammerDamage() + 1, ModToolMaterials.END_STONE.getHammerSpeed());
	public static final Item THUNDERING_HAMMER = new ThunderingHammerItem(3, ToolMaterials.IRON, ModToolMaterials.DARK_IRON.getHammerDamage() + 1, ModToolMaterials.DARK_IRON.getHammerSpeed());
	//</editor-fold>
	//<editor-fold desc="Hay">
	public static final BlockContainer HAY_PLANKS = MakePlanks(MapColor.YELLOW).flammable(5, 20).fuel(300).cubeAllModel().blockTag(BlockTags.PLANKS).itemTag(ItemTags.PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer HAY_STAIRS = MakeWoodStairs(HAY_PLANKS).flammable(5, 20).fuel(300).stairsModel(HAY_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer HAY_SLAB = MakeSlab(HAY_PLANKS).flammable(5, 20).fuel(150).slabModel(HAY_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer HAY_FENCE = MakeWoodFence(HAY_PLANKS).flammable(5, 20).fuel(300).fenceModel(HAY_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer HAY_FENCE_GATE = MakeWoodFenceGate(HAY_PLANKS).flammable(5, 20).fuel(300).fenceGateModel(HAY_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer HAY_DOOR = MakeWoodDoor(HAY_PLANKS).fuel(200).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer HAY_TRAPDOOR = MakeWoodTrapdoor(HAY_PLANKS).fuel(300).trapdoorModel(true).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer HAY_PRESSURE_PLATE = MakeWoodPressurePlate(HAY_PLANKS).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer HAY_BUTTON = MakeWoodButton().fuel(100).buttonModel(HAY_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final SignContainer HAY_SIGN = MakeSign("hay", HAY_PLANKS, HAY_PLANKS).fuel(200).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer HAY_IRON_HANGING_SIGN = MakeHangingSign(MakeSignType("hay_iron"), HAY_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer HAY_GOLD_HANGING_SIGN = MakeHangingSign(MakeSignType("hay_gold"), HAY_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer HAY_COPPER_HANGING_SIGN = MakeHangingSign(MakeSignType("hay_copper"), HAY_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer HAY_EXPOSED_COPPER_HANGING_SIGN = MakeHangingSign(MakeSignType("hay_exposed_copper"), HAY_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer HAY_WEATHERED_COPPER_HANGING_SIGN = MakeHangingSign(MakeSignType("hay_weathered_copper"), HAY_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer HAY_OXIDIZED_COPPER_HANGING_SIGN = MakeHangingSign(MakeSignType("hay_oxidized_copper"), HAY_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer HAY_NETHERITE_HANGING_SIGN = MakeHangingSign(MakeSignType("hay_netherite"), HAY_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BoatContainer HAY_BOAT = MakeBoat("hay", HAY_PLANKS).fuel(1200);
	//Extended
	public static final BlockContainer HAY_BEEHIVE = MakeBeehive(MapColor.YELLOW).flammable(5, 20).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer HAY_BOOKSHELF = MakeBookshelf(HAY_PLANKS).flammable(30, 20).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer HAY_CHISELED_BOOKSHELF = MakeChiseledBookshelf(MapColor.YELLOW).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer HAY_CRAFTING_TABLE = MakeCraftingTable(HAY_PLANKS).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer HAY_LADDER = MakeLadder().blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer HAY_WOODCUTTER = MakeWoodcutter(HAY_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer HAY_BARREL = MakeBarrel(MapColor.YELLOW).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer HAY_LECTERN = MakeLectern(HAY_PLANKS).flammable(30, 20).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer HAY_POWDER_KEG = MakePowderKeg(HAY_BARREL).blockTag(BlockTags.AXE_MINEABLE);
	//Torches
	public static final TorchContainer HAY_TORCH = MakeTorch(BlockSoundGroup.WOOD).torchModel();
	public static final TorchContainer HAY_SOUL_TORCH = MakeSoulTorch(BlockSoundGroup.WOOD).torchModel(HAY_TORCH);
	public static final TorchContainer HAY_ENDER_TORCH = MakeEnderTorch(BlockSoundGroup.WOOD).torchModel(HAY_TORCH);
	public static final TorchContainer UNDERWATER_HAY_TORCH = MakeUnderwaterTorch(BlockSoundGroup.WOOD).torchModel(HAY_TORCH);
	//</editor-fold>
	public static final BlockContainer HEDGE_BLOCK = MakeBlock(Block.Settings.of(Material.LEAVES).strength(0.2F).sounds(BlockSoundGroup.GRASS).nonOpaque()).flammable(5, 20).compostable(0.75f).dropSelf().blockTag(ModBlockTags.BROOM_SWEEPS).blockTag(BlockTags.HOE_MINEABLE);
	//<editor-fold desc="Hedgehog">
	public static final EntityType<HedgehogEntity> HEDGEHOG_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, HedgehogEntity::new).dimensions(EntityDimensions.fixed(0.5F, 0.45F)).trackRangeChunks(8).build();
	public static final Item HEDGEHOG_SPAWN_EGG = MakeSpawnEgg(HEDGEHOG_ENTITY, 11440263, 7558239);
	//</editor-fold>
	public static final Item HORN = MakeGeneratedItem(); //Minecraft Earth
	//<editor-fold desc="Icy Spider">
	public static final EntityType<IcySpiderEntity> ICY_SPIDER_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, IcySpiderEntity::new).dimensions(EntityDimensions.fixed(1.4f, 0.9f)).trackRangeChunks(8).build();
	public static final Item ICY_SPIDER_SPAWN_EGG = MakeSpawnEgg(ICY_SPIDER_ENTITY, 0x045480, 0x3CFEFB);
	//</editor-fold>
	//<editor-fold desc="Igneous Rocks">
	public static final BlockContainer COOLED_MAGMA_BLOCK = BuildBlock(new CooledMagmaBlock(Blocks.MAGMA_BLOCK)).cubeAllModel().blockTag(ModBlockTags.INFLICTS_FIRE_DAMAGE).blockTag(ModBlockTags.SIZZLE_RAIN_BLOCKS).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer PUMICE = BuildBlock(new PumiceBlock(Block.Settings.of(Material.STONE, MapColor.BROWN).requiresTool().strength(1.5f, 6.0f))).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	//</editor-fold>
	//<editor-fold desc="Iron">
	public static final Item RAW_IRON_NUGGET = MakeGeneratedItem();
	public static final Item IRON_ROD = MakeHandheldItem();
	public static final BlockContainer NETHER_IRON_ORE = BuildBlock(new OreBlock(Block.Settings.of(Material.STONE, MapColor.DARK_RED).requiresTool().strength(3f, 3f).sounds(ModBlockSoundGroups.NETHER_IRON_ORE), UniformIntProvider.create(0, 1)), DropTable.NetherOre(RAW_IRON_NUGGET)).cubeAllModel().blockTag(BlockTags.IRON_ORES).blockTag(ModBlockTags.SNAPS_GOAT_HORN).blockTag(BlockTags.NEEDS_STONE_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer END_IRON_ORE = BuildBlock(new OreBlock(Block.Settings.of(Material.STONE, MapColor.PALE_YELLOW).requiresTool().strength(3f, 3f).sounds(ModBlockSoundGroups.END_IRON_ORE), UniformIntProvider.create(0, 1)), DropTable.EndOre(RAW_IRON_NUGGET)).cubeAllModel().blockTag(BlockTags.IRON_ORES).blockTag(ModBlockTags.SNAPS_GOAT_HORN).blockTag(BlockTags.NEEDS_STONE_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final TorchContainer IRON_TORCH = MakeTorch(14, BlockSoundGroup.METAL, ModParticleTypes.IRON_FLAME).torchModel();
	public static final TorchContainer IRON_SOUL_TORCH = MakeSoulTorch(BlockSoundGroup.METAL).torchModel(IRON_TORCH);
	public static final TorchContainer IRON_ENDER_TORCH = MakeEnderTorch(BlockSoundGroup.METAL).torchModel(IRON_TORCH);
	public static final TorchContainer UNDERWATER_IRON_TORCH = MakeUnderwaterTorch(BlockSoundGroup.METAL).torchModel(IRON_TORCH);
	public static final BlockContainer IRON_LANTERN = MakeLantern(15).blockTag(BlockTags.NEEDS_STONE_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer IRON_SOUL_LANTERN = MakeSoulLantern().blockTag(BlockTags.NEEDS_STONE_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer IRON_ENDER_LANTERN = MakeLantern(13).blockTag(BlockTags.NEEDS_STONE_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer IRON_BUTTON = MakeMetalButton().buttonModel(Blocks.IRON_BLOCK).blockTag(BlockTags.NEEDS_STONE_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer IRON_CHAIN = MakeChain().blockTag(BlockTags.NEEDS_STONE_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer HEAVY_IRON_CHAIN = MakeHeavyChain().blockTag(BlockTags.NEEDS_STONE_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer IRON_STAIRS = MakeStairs(Blocks.IRON_BLOCK).stairsModel(Blocks.IRON_BLOCK).blockTag(BlockTags.NEEDS_STONE_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer IRON_SLAB = MakeSlab(Blocks.IRON_BLOCK).slabModel(Blocks.IRON_BLOCK).blockTag(BlockTags.NEEDS_STONE_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer IRON_WALL = MakeWall(Blocks.IRON_BLOCK).wallModel(Blocks.IRON_BLOCK).blockTag(BlockTags.NEEDS_STONE_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer IRON_BRICKS = MakeBlock(Blocks.IRON_BLOCK).cubeAllModel().blockTag(BlockTags.NEEDS_STONE_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer IRON_BRICK_STAIRS = MakeStairs(IRON_BRICKS).stairsModel(IRON_BRICKS).blockTag(BlockTags.NEEDS_STONE_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer IRON_BRICK_SLAB = MakeSlab(IRON_BRICKS).slabModel(IRON_BRICKS).blockTag(BlockTags.NEEDS_STONE_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer IRON_BRICK_WALL = MakeWall(IRON_BRICKS).wallModel(IRON_BRICKS).blockTag(BlockTags.NEEDS_STONE_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer CUT_IRON = MakeBlock(Blocks.IRON_BLOCK).cubeAllModel().blockTag(BlockTags.NEEDS_STONE_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer CUT_IRON_PILLAR = BuildBlock(new ModPillarBlock(CUT_IRON)).blockTag(BlockTags.NEEDS_STONE_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer CUT_IRON_STAIRS = MakeStairs(CUT_IRON).stairsModel(CUT_IRON).blockTag(BlockTags.NEEDS_STONE_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer CUT_IRON_SLAB = MakeSlab(CUT_IRON).slabModel(CUT_IRON).blockTag(BlockTags.NEEDS_STONE_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer CUT_IRON_WALL = MakeWall(CUT_IRON).wallModel(CUT_IRON).blockTag(BlockTags.NEEDS_STONE_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final Item IRON_HAMMER = MakeHammer(ToolMaterials.IRON, 7, -3.1F);
	//</editor-fold>
	//<editor-fold desc="Juice">
	public static final Item APPLE_CIDER = MakeDrink(5, 0.6F);
	public static final Item GOLDEN_APPLE_CIDER = MakeDrink(FoodSettings(5, 1.4F)
			.statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 100, 1), 1)
			.statusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 2400, 0), 1).alwaysEdible().build());
	public static final BlockContainer JUICER = BuildBlock(new JuicerBlock(Block.Settings.of(Material.STONE).requiresTool().strength(3.5F))).blockTag(BlockTags.PICKAXE_MINEABLE);
	//Juice
	public static final Item APPLE_JUICE = MakeDrink(4, 0.5F);
	public static final Item BEETROOT_JUICE = MakeDrink(4, 0.5F);
	public static final Item BLACK_APPLE_JUICE = MakeDrink(FoodSettings(4, 0.5F)
			.statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 2, 2), 1F)
			.statusEffect(new StatusEffectInstance(StatusEffects.INSTANT_HEALTH, 1, 2), 1F).build());
	public static final Item CABBAGE_JUICE = MakeDrink(4, 0.5F);
	public static final Item CACTUS_JUICE = MakeDrink(4, 0.5F);
	public static final Item CARROT_JUICE = MakeDrink(4, 0.5F);
	public static final Item CHERRY_JUICE = MakeDrink(4, 0.5F);
	public static final Item CHORUS_JUICE = GeneratedItem(new BottledChorusItem(DrinkSettings(4, 0.5F)));
	public static final Item GLOW_BERRY_JUICE = MakeDrink(4, 0.5F);
	public static final Item GOLDEN_APPLE_JUICE = MakeDrink(FoodSettings(4, 1.3F)
			.statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 100, 1), 1)
			.statusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 2400, 0), 1).alwaysEdible().build());
	public static final Item ENCHANTED_GOLDEN_APPLE_JUICE = ParentedItem(new BottledDrinkItem(DrinkSettings(FoodSettings(4, 1.3F)
			.statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 400, 1), 1)
			.statusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 6000, 0), 1)
			.statusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 6000, 0), 1)
			.statusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 2400, 3), 1).alwaysEdible().build())) {
		@Override public void OnDrink(ItemStack stack, LivingEntity user) { }
		@Override public boolean hasGlint(ItemStack stack) { return true; }
	}, GOLDEN_APPLE_JUICE);
	public static final Item GOLDEN_BEETROOT_JUICE = MakeDrink(4, 1.3F);
	public static final Item GOLDEN_CARROT_JUICE = MakeDrink(6, 1.3F);
	public static final Item GOLDEN_CHORUS_JUICE = GeneratedItem(new BottledChorusItem(DrinkSettings(8, 1.3F)));
	public static final Item GOLDEN_ONION_JUICE = MakeDrink(6, 1.3F);
	public static final Item GOLDEN_POTATO_JUICE = MakeDrink(4, 1.3F);
	public static final Item GOLDEN_TOMATO_JUICE = MakeDrink(4, 1.3F);
	public static final Item GRAPE_JUICE = MakeDrink(4, 0.5F);
	public static final Item KELP_JUICE = MakeDrink(4, 0.5F);
	public static final Item MELON_JUICE = MakeDrink(4, 0.5F);
	public static final Item ONION_JUICE = MakeDrink(4, 0.5F);
	public static final Item POTATO_JUICE = MakeDrink(4, 0.5F);
	public static final Item PUMPKIN_JUICE = MakeDrink(4, 0.5F);
	public static final Item SEA_PICKLE_JUICE = MakeDrink(4, 0.5F);
	public static final Item STRAWBERRY_JUICE = MakeDrink(4, 0.5F);
	public static final Item SWEET_BERRY_JUICE = MakeDrink(4, 0.5F);
	public static final Item TOMATO_JUICE = MakeDrink(4, 0.5F);
	//Smoothies
	public static final Item APPLE_SMOOTHIE = MakeDrink(5, 0.5F);
	public static final Item BEETROOT_SMOOTHIE = MakeDrink(5, 0.5F);
	public static final Item BLACK_APPLE_SMOOTHIE = MakeDrink(FoodSettings(5, 0.5F).statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 2, 2), 1F).statusEffect(new StatusEffectInstance(StatusEffects.INSTANT_HEALTH, 1, 2), 1F).build());
	public static final Item CABBAGE_SMOOTHIE = MakeDrink(5, 0.5F);
	public static final Item CACTUS_SMOOTHIE = MakeDrink(5, 0.5F);
	public static final Item CARROT_SMOOTHIE = MakeDrink(5, 0.5F);
	public static final Item CHERRY_SMOOTHIE = MakeDrink(5, 0.5F);
	public static final Item CHORUS_SMOOTHIE = GeneratedItem(new BottledChorusItem(DrinkSettings(5, 0.5F)));
	public static final Item GLOW_BERRY_SMOOTHIE = MakeDrink(5, 0.5F);
	public static final Item GOLDEN_APPLE_SMOOTHIE = MakeDrink(FoodSettings(5, 1.3F)
			.statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 100, 1), 1)
			.statusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 2400, 0), 1).alwaysEdible().build());
	public static final Item ENCHANTED_GOLDEN_APPLE_SMOOTHIE = ParentedItem(new BottledDrinkItem(DrinkSettings(FoodSettings(5, 1.3F)
			.statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 400, 1), 1)
			.statusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 6000, 0), 1)
			.statusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 6000, 0), 1)
			.statusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 2400, 3), 1).alwaysEdible().build())) {
		@Override public void OnDrink(ItemStack stack, LivingEntity user) { }
		@Override public boolean hasGlint(ItemStack stack) { return true; }
	}, GOLDEN_APPLE_SMOOTHIE);
	public static final Item GOLDEN_BEETROOT_SMOOTHIE = MakeDrink(5, 1.3F);
	public static final Item GOLDEN_CARROT_SMOOTHIE = MakeDrink(7, 1.3F);
	public static final Item GOLDEN_CHORUS_SMOOTHIE = GeneratedItem(new BottledChorusItem(DrinkSettings(9, 1.3F)));
	public static final Item GOLDEN_ONION_SMOOTHIE = MakeDrink(7, 1.3F);
	public static final Item GOLDEN_POTATO_SMOOTHIE = MakeDrink(5, 1.3F);
	public static final Item GOLDEN_TOMATO_SMOOTHIE = MakeDrink(5, 1.3F);
	public static final Item GRAPE_SMOOTHIE = MakeDrink(5, 0.5F);
	public static final Item KELP_SMOOTHIE = MakeDrink(5, 0.5F);
	public static final Item MELON_SMOOTHIE = MakeDrink(5, 0.5F);
	public static final Item ONION_SMOOTHIE = MakeDrink(5, 0.5F);
	public static final Item POTATO_SMOOTHIE = MakeDrink(5, 0.5F);
	public static final Item PUMPKIN_SMOOTHIE = MakeDrink(5, 0.5F);
	public static final Item SEA_PICKLE_SMOOTHIE = MakeDrink(5, 0.5F);
	public static final Item STRAWBERRY_SMOOTHIE = MakeDrink(5, 0.5F);
	public static final Item SWEET_BERRY_SMOOTHIE = MakeDrink(5, 0.5F);
	public static final Item TOMATO_SMOOTHIE = MakeDrink(5, 0.5F);
	//</editor-fold>
	//<editor-fold desc="Jumping Spider">
	public static final EntityType<JumpingSpiderEntity> JUMPING_SPIDER_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, JumpingSpiderEntity::new).dimensions(EntityDimensions.fixed(0.7f, 0.5f)).trackRangeChunks(8).build();
	public static final Item JUMPING_SPIDER_SPAWN_EGG = MakeSpawnEgg(JUMPING_SPIDER_ENTITY, 0x281206, 0x3C0202);
	//</editor-fold>
	//<editor-fold desc="Jungle">
	public static final Item JUNGLE_CHEST_BOAT = MakeChestBoat(BoatEntity.Type.JUNGLE, BoatSettings(ItemGroup.TRANSPORTATION));
	public static final WallBlockContainer JUNGLE_HANGING_SIGN = MakeHangingSign(SignType.JUNGLE, Blocks.STRIPPED_JUNGLE_LOG, SignItemSettings(ItemGroup.DECORATIONS)).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer JUNGLE_IRON_HANGING_SIGN = MakeHangingSign(MakeSignType("jungle_iron"), Blocks.STRIPPED_JUNGLE_LOG).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer JUNGLE_GOLD_HANGING_SIGN = MakeHangingSign(MakeSignType("jungle_gold"), Blocks.STRIPPED_JUNGLE_LOG).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer JUNGLE_COPPER_HANGING_SIGN = MakeHangingSign(MakeSignType("jungle_copper"), Blocks.STRIPPED_JUNGLE_LOG).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer JUNGLE_EXPOSED_COPPER_HANGING_SIGN = MakeHangingSign(MakeSignType("jungle_exposed_copper"), Blocks.STRIPPED_JUNGLE_LOG).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer JUNGLE_WEATHERED_COPPER_HANGING_SIGN = MakeHangingSign(MakeSignType("jungle_weathered_copper"), Blocks.STRIPPED_JUNGLE_LOG).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer JUNGLE_OXIDIZED_COPPER_HANGING_SIGN = MakeHangingSign(MakeSignType("jungle_oxidized_copper"), Blocks.STRIPPED_JUNGLE_LOG).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer JUNGLE_NETHERITE_HANGING_SIGN = MakeHangingSign(MakeSignType("jungle_netherite"), Blocks.STRIPPED_JUNGLE_LOG).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer JUNGLE_BEEHIVE = MakeBeehive(MapColor.DIRT_BROWN).flammable(5, 20).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer JUNGLE_BOOKSHELF = MakeBookshelf(Blocks.JUNGLE_PLANKS).flammable(30, 20).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer JUNGLE_CHISELED_BOOKSHELF = MakeChiseledBookshelf(MapColor.DIRT_BROWN).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer JUNGLE_CRAFTING_TABLE = MakeCraftingTable(Blocks.JUNGLE_PLANKS).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer JUNGLE_LADDER = MakeLadder().blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer JUNGLE_WOODCUTTER = MakeWoodcutter(Blocks.JUNGLE_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer JUNGLE_BARREL = MakeBarrel(MapColor.DIRT_BROWN).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer JUNGLE_LECTERN = MakeLectern(Blocks.JUNGLE_PLANKS).flammable(30, 20).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer JUNGLE_POWDER_KEG = MakePowderKeg(JUNGLE_BARREL).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer JUNGLE_LOG_SLAB = MakeLogSlab(Blocks.JUNGLE_LOG).flammable(5, 5).fuel(150).itemTag(ModItemTags.CHARRABLE_LOG_SLABS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer STRIPPED_JUNGLE_LOG_SLAB = MakeLogSlab(Blocks.STRIPPED_JUNGLE_LOG).flammable(5, 5).fuel(150).itemTag(ModItemTags.CHARRABLE_STRIPPED_LOG_SLABS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer JUNGLE_WOOD_SLAB = MakeBarkSlab(Blocks.JUNGLE_WOOD, Blocks.JUNGLE_LOG).flammable(5, 5).fuel(150).itemTag(ModItemTags.CHARRABLE_WOOD_SLABS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer STRIPPED_JUNGLE_WOOD_SLAB = MakeBarkSlab(Blocks.STRIPPED_JUNGLE_WOOD, Blocks.STRIPPED_JUNGLE_LOG).flammable(5, 5).fuel(150).itemTag(ModItemTags.CHARRABLE_STRIPPED_WOOD_SLABS).blockTag(BlockTags.AXE_MINEABLE);
	//Misc
	public static final Item JUNGLE_HAMMER = MakeHammer(ToolMaterials.WOOD, 7, -3.2F);
	//Torches
	public static final TorchContainer JUNGLE_TORCH = MakeTorch(BlockSoundGroup.WOOD).torchModel();
	public static final TorchContainer JUNGLE_SOUL_TORCH = MakeSoulTorch(BlockSoundGroup.WOOD).torchModel(JUNGLE_TORCH);
	public static final TorchContainer JUNGLE_ENDER_TORCH = MakeEnderTorch(BlockSoundGroup.WOOD).torchModel(JUNGLE_TORCH);
	public static final TorchContainer UNDERWATER_JUNGLE_TORCH = MakeUnderwaterTorch(BlockSoundGroup.WOOD).torchModel(JUNGLE_TORCH);
	//Campfires
	public static final BlockContainer JUNGLE_CAMPFIRE = MakeCampfire(MapColor.DIRT_BROWN).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer JUNGLE_SOUL_CAMPFIRE = MakeSoulCampfire(JUNGLE_CAMPFIRE).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer JUNGLE_ENDER_CAMPFIRE = MakeEnderCampfire(JUNGLE_CAMPFIRE).blockTag(BlockTags.AXE_MINEABLE);
	//</editor-fold>
	public static final Item KILL_POTION = new BottledDrinkItem(GlassBottledItemSettings()) {
		@Override
		public void OnDrink(ItemStack stack, LivingEntity user) {
			user.damage(ModDamageSource.DIE_INSTANTLY, 9999);
			if (user.getHealth() > 0) user.kill(); //They're supposed to be dead after the damage, even if they're in creative, kill that fucker
		}
	};
	public static final BlockContainer LAPIS_SLAB = MakeSlab(Blocks.LAPIS_BLOCK).slabModel(Blocks.LAPIS_BLOCK).blockTag(BlockTags.NEEDS_STONE_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final Item LAVA_BOTTLE = GeneratedItem(new BottledDrinkItem(GlassBottledItemSettings()) {
		@Override
		public void OnDrink(ItemStack stack, LivingEntity user) {
			BloodType bloodType = BloodType.Get(user);
			if (bloodType == BloodType.LAVA) user.heal(2);
			else if (bloodType == MAGMA_CREAM_BLOOD_TYPE) user.heal(1);
			else if (bloodType == HavenMod.NETHER_ROYALTY_BLOOD_TYPE) { }
			else {
				user.damage(ModDamageSource.DRANK_LAVA, 8);
				user.setOnFireFor(20000);
			}
		}
		@Override
		public ActionResult useOnBlock(ItemUsageContext context) {
			//Extracted almost verbatim from FlintAndSteelItem::useOnBlock
			BlockPos blockPos;
			PlayerEntity playerEntity = context.getPlayer();
			World world = context.getWorld();
			BlockState blockState = world.getBlockState(blockPos = context.getBlockPos());
			ItemStack itemStack = context.getStack();
			BlockPos newPos = blockPos;
			BlockState newState = null;
			if (CampfireBlock.canBeLit(blockState) || CandleBlock.canBeLit(blockState) || CandleCakeBlock.canBeLit(blockState)) {
				newState = blockState.with(Properties.LIT, true);
			}
			if (newState == null && AbstractFireBlock.canPlaceAt(world, newPos = blockPos.offset(context.getSide()), context.getPlayerFacing())) {
				newState = AbstractFireBlock.getState(world, newPos);
				if (playerEntity instanceof ServerPlayerEntity serverPlayer) Criteria.PLACED_BLOCK.trigger(serverPlayer, newPos, itemStack);
			}
			if (newState != null) {
				world.playSound(playerEntity, newPos, SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.BLOCKS, 1.0f, world.getRandom().nextFloat() * 0.4f + 0.8f);
				world.setBlockState(newPos, newState, Block.NOTIFY_ALL | Block.REDRAW_ON_MAIN_THREAD);
				world.emitGameEvent(playerEntity, GameEvent.BLOCK_PLACE, blockPos);
				if (playerEntity != null) {
					itemStack.decrement(1);
					playerEntity.giveItemStack(new ItemStack(this.getRecipeRemainder()));
				}
				return ActionResult.success(world.isClient());
			}
			return super.useOnBlock(context);
		}
	});
	public static final BlockContainer LIGHT_BLUE_TARGET = BuildBlock(new TargetBlock(Block.Settings.of(Material.SOLID_ORGANIC, MapColor.OFF_WHITE).strength(0.5f).sounds(BlockSoundGroup.GRASS))).flammable(15, 20).blockTag(BlockTags.HOE_MINEABLE);
	public static final Item MAGMA_CREAM_BOTTLE = GeneratedItem(new BottledSlimeItem(GlassBottledItemSettings()) {
		@Override
		public void OnDrink(ItemStack stack, LivingEntity user) {
			user.addStatusEffect(new StatusEffectInstance(ModStatusEffects.STICKY, 600));
			user.damage(ModDamageSource.DRANK_MAGMA_CREAM, 4);
			user.setOnFireFor(200);
		}
	});
	public static final Item DISTILLED_WATER_BOTTLE = new BottledDrinkItem(DrinkSettings(0, 0)) {
		@Override
		public void OnDrink(ItemStack stack, LivingEntity user) {
			BloodType bloodType = BloodType.Get(user);
			if (bloodType == BloodType.WATER) user.heal(2);
			else if (bloodType == BloodType.LAVA) user.damage(ModDamageSource.DRANK_WATER, 4);
			else if (bloodType == MAGMA_CREAM_BLOOD_TYPE) user.damage(ModDamageSource.DRANK_WATER, 4);
			else if (bloodType == ENDERMAN_BLOOD_TYPE) user.damage(ModDamageSource.DRANK_WATER, 2);
			else if (bloodType == ENDER_DRAGON_BLOOD_TYPE) user.damage(ModDamageSource.DRANK_WATER, 2);
			else if (bloodType == HavenMod.BEE_ENDERMAN_BLOOD_TYPE) user.damage(ModDamageSource.DRANK_WATER, 2);
			else if (bloodType == HavenMod.NETHER_ROYALTY_BLOOD_TYPE) user.damage(ModDamageSource.DRANK_WATER, 4);
		}
	};
	public static final Item SUGAR_WATER_BOTTLE = GeneratedItem( new BottledDrinkItem(DrinkSettings(0, 0.1F)) {
		@Override
		public void OnDrink(ItemStack stack, LivingEntity user) {
			BloodType bloodType = BloodType.Get(user);
			if (ModConfig.REGISTER_HAVEN_MOD && bloodType == HavenMod.CONFETTI_BLOOD_TYPE) user.heal(2);
			else if (bloodType == BloodType.WATER) user.heal(1);
		}
	});
	public static final Item SYRUP_BOTTLE = GeneratedItem(new BottledDrinkItem(DrinkSettings(0, 0.1F)) {
		@Override
		public void OnDrink(ItemStack stack, LivingEntity user) {
			BloodType bloodType = BloodType.Get(user);
			if (bloodType == BloodType.SAP) user.heal(2);
			else if (ModConfig.REGISTER_HAVEN_MOD && bloodType == HavenMod.CONFETTI_BLOOD_TYPE) user.heal(1);
		}
	});
	public static final Item SAP_BOTTLE = ParentedItem(new BottledDrinkItem(DrinkSettings(0, 0.1F)) {
		@Override
		public void OnDrink(ItemStack stack, LivingEntity user) {
			BloodType bloodType = BloodType.Get(user);
			if (bloodType == BloodType.SAP) user.heal(2);
			user.addStatusEffect(new StatusEffectInstance(ModStatusEffects.STICKY, 600));
		}
	}, SYRUP_BOTTLE);
	//<editor-fold desc="Mangrove">
	public static final BlockContainer MANGROVE_LOG = MakeLog(MapColor.RED, MapColor.SPRUCE_BROWN, ItemSettings(ItemGroup.BUILDING_BLOCKS)).flammable(5, 5).fuel(300).blockTag(ModBlockTags.MANGROVE_LOGS).itemTag(ModItemTags.MANGROVE_LOGS).blockTag(ModBlockTags.OVERWORLD_NATURAL_LOGS).itemTag(ModItemTags.CHARRABLE_LOGS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer STRIPPED_MANGROVE_LOG = MakeLog(MapColor.RED, ItemSettings(ItemGroup.BUILDING_BLOCKS)).flammable(5, 5).fuel(300).blockTag(ModBlockTags.MANGROVE_LOGS).itemTag(ModItemTags.MANGROVE_LOGS).itemTag(ModItemTags.CHARRABLE_STRIPPED_LOGS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer MANGROVE_WOOD = MakeWood(MapColor.SPRUCE_BROWN, ItemSettings(ItemGroup.BUILDING_BLOCKS)).flammable(5, 5).fuel(300).blockTag(ModBlockTags.MANGROVE_LOGS).itemTag(ModItemTags.MANGROVE_LOGS).itemTag(ModItemTags.CHARRABLE_WOODS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer STRIPPED_MANGROVE_WOOD = MakeWood(MapColor.RED, ItemSettings(ItemGroup.BUILDING_BLOCKS)).flammable(5, 5).fuel(300).blockTag(ModBlockTags.MANGROVE_LOGS).itemTag(ModItemTags.MANGROVE_LOGS).itemTag(ModItemTags.CHARRABLE_STRIPPED_WOODS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer MANGROVE_LEAVES = BuildLeaves(new MangroveLeavesBlock(LeafBlockSettings()), ItemSettings(ItemGroup.DECORATIONS)).blockTag(ModBlockTags.MANGROVE_LOGS_CAN_GROW_THROUGH);
	public static final BlockContainer MANGROVE_PLANKS = MakePlanks(MapColor.RED, ItemSettings(ItemGroup.BUILDING_BLOCKS)).flammable(5, 20).fuel(300).cubeAllModel().blockTag(BlockTags.PLANKS).itemTag(ItemTags.PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer MANGROVE_STAIRS = MakeWoodStairs(MANGROVE_PLANKS, ItemSettings(ItemGroup.BUILDING_BLOCKS)).flammable(5, 20).fuel(300).stairsModel(MANGROVE_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer MANGROVE_SLAB = MakeSlab(MANGROVE_PLANKS, ItemSettings(ItemGroup.BUILDING_BLOCKS)).flammable(5, 20).fuel(150).slabModel(MANGROVE_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer MANGROVE_FENCE = MakeWoodFence(MANGROVE_PLANKS, ItemSettings(ItemGroup.DECORATIONS)).flammable(5, 20).fuel(300).fenceModel(MANGROVE_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer MANGROVE_FENCE_GATE = MakeWoodFenceGate(MANGROVE_PLANKS, ItemSettings(ItemGroup.REDSTONE)).flammable(5, 20).fuel(300).fenceGateModel(MANGROVE_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer MANGROVE_DOOR = MakeWoodDoor(MANGROVE_PLANKS, ItemSettings(ItemGroup.REDSTONE)).fuel(200).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer MANGROVE_TRAPDOOR = MakeWoodTrapdoor(MANGROVE_PLANKS, ItemSettings(ItemGroup.REDSTONE)).fuel(300).trapdoorModel(true).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer MANGROVE_PRESSURE_PLATE = MakeWoodPressurePlate(MANGROVE_PLANKS, ItemSettings(ItemGroup.REDSTONE)).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer MANGROVE_BUTTON = MakeWoodButton(ItemSettings(ItemGroup.REDSTONE)).fuel(100).buttonModel(MANGROVE_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer MANGROVE_ROOTS = BuildBlock(new MangroveRootsBlock(Block.Settings.of(Material.WOOD, MapColor.SPRUCE_BROWN).strength(0.7f).ticksRandomly().sounds(ModBlockSoundGroups.MANGROVE_ROOTS).nonOpaque().suffocates(ModFactory::never).blockVision(ModFactory::never).nonOpaque()), ItemSettings(ItemGroup.BUILDING_BLOCKS)).flammable(5, 20).fuel(300).blockTag(ModBlockTags.FROGS_SPAWNABLE_ON).blockTag(ModBlockTags.MANGROVE_LOGS_CAN_GROW_THROUGH).blockTag(ModBlockTags.MANGROVE_ROOTS_CAN_GROW_THROUGH).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer MUDDY_MANGROVE_ROOTS = BuildBlock(new ModPillarBlock(Block.Settings.of(Material.SOIL, MapColor.SPRUCE_BROWN).strength(0.7f).sounds(ModBlockSoundGroups.MUDDY_MANGROVE_ROOTS)), ItemSettings(ItemGroup.BUILDING_BLOCKS)).blockTag(BlockTags.DIRT).blockTag(ModBlockTags.FROGS_SPAWNABLE_ON).blockTag(ModBlockTags.MANGROVE_LOGS_CAN_GROW_THROUGH).blockTag(ModBlockTags.MANGROVE_ROOTS_CAN_GROW_THROUGH).blockTag(ModBlockTags.SNIFFER_DIGGABLE_BLOCK).blockTag(BlockTags.AXE_MINEABLE);
	public static final SignContainer MANGROVE_SIGN = MakeSign("minecraft:mangrove", MANGROVE_PLANKS, SignItemSettings(ItemGroup.DECORATIONS), STRIPPED_MANGROVE_LOG).fuel(200).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer MANGROVE_IRON_HANGING_SIGN = MakeHangingSign(MakeSignType("mangrove_iron"), STRIPPED_MANGROVE_LOG).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer MANGROVE_GOLD_HANGING_SIGN = MakeHangingSign(MakeSignType("mangrove_gold"), STRIPPED_MANGROVE_LOG).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer MANGROVE_COPPER_HANGING_SIGN = MakeHangingSign(MakeSignType("mangrove_copper"), STRIPPED_MANGROVE_LOG).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer MANGROVE_EXPOSED_COPPER_HANGING_SIGN = MakeHangingSign(MakeSignType("mangrove_exposed_copper"), STRIPPED_MANGROVE_LOG).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer MANGROVE_WEATHERED_COPPER_HANGING_SIGN = MakeHangingSign(MakeSignType("mangrove_weathered_copper"), STRIPPED_MANGROVE_LOG).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer MANGROVE_OXIDIZED_COPPER_HANGING_SIGN = MakeHangingSign(MakeSignType("mangrove_oxidized_copper"), STRIPPED_MANGROVE_LOG).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer MANGROVE_NETHERITE_HANGING_SIGN = MakeHangingSign(MakeSignType("mangrove_netherite"), STRIPPED_MANGROVE_LOG).blockTag(BlockTags.AXE_MINEABLE);
	public static final BoatContainer MANGROVE_BOAT = MakeBoat("minecraft:mangrove", MANGROVE_PLANKS, BoatSettings(ItemGroup.TRANSPORTATION)).fuel(1200);
	public static final PottedBlockContainer MANGROVE_PROPAGULE = new PottedBlockContainer(new PropaguleBlock(Block.Settings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GRASS)), ItemSettings(ItemGroup.DECORATIONS)).compostable(0.3f).blockTag(BlockTags.SAPLINGS).itemTag(ItemTags.SAPLINGS).blockTag(BlockTags.FLOWERS).itemTag(ItemTags.FLOWERS).blockTag(ModBlockTags.MANGROVE_LOGS_CAN_GROW_THROUGH).blockTag(ModBlockTags.MANGROVE_ROOTS_CAN_GROW_THROUGH); //Drop table omitted intentionally
	//Extended
	public static final BlockContainer MANGROVE_BEEHIVE = MakeBeehive(MapColor.RED).flammable(5, 20).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer MANGROVE_BOOKSHELF = MakeBookshelf(MANGROVE_PLANKS).flammable(30, 20).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer MANGROVE_CHISELED_BOOKSHELF = MakeChiseledBookshelf(MapColor.RED).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer MANGROVE_CRAFTING_TABLE = MakeCraftingTable(MANGROVE_PLANKS).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer MANGROVE_LADDER = MakeLadder().blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer MANGROVE_WOODCUTTER = MakeWoodcutter(MANGROVE_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer MANGROVE_BARREL = MakeBarrel(MapColor.RED).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer MANGROVE_LECTERN = MakeLectern(MANGROVE_PLANKS).flammable(30, 20).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer MANGROVE_POWDER_KEG = MakePowderKeg(MANGROVE_BARREL).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer MANGROVE_LOG_SLAB = MakeLogSlab(MANGROVE_LOG).flammable(5, 5).fuel(150).itemTag(ModItemTags.CHARRABLE_LOG_SLABS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer STRIPPED_MANGROVE_LOG_SLAB = MakeLogSlab(STRIPPED_MANGROVE_LOG).flammable(5, 5).fuel(150).itemTag(ModItemTags.CHARRABLE_STRIPPED_LOG_SLABS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer MANGROVE_WOOD_SLAB = MakeBarkSlab(MANGROVE_WOOD, MANGROVE_LOG).flammable(5, 5).fuel(150).itemTag(ModItemTags.CHARRABLE_WOOD_SLABS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer STRIPPED_MANGROVE_WOOD_SLAB = MakeBarkSlab(STRIPPED_MANGROVE_WOOD, STRIPPED_MANGROVE_LOG).flammable(5, 5).fuel(150).itemTag(ModItemTags.CHARRABLE_STRIPPED_WOOD_SLABS).blockTag(BlockTags.AXE_MINEABLE);
	//Misc
	public static final Item MANGROVE_HAMMER = MakeHammer(ToolMaterials.WOOD, 7, -3.2F);
	//Torches
	public static final TorchContainer MANGROVE_TORCH = MakeTorch(BlockSoundGroup.WOOD).torchModel();
	public static final TorchContainer MANGROVE_SOUL_TORCH = MakeSoulTorch(BlockSoundGroup.WOOD).torchModel(MANGROVE_TORCH);
	public static final TorchContainer MANGROVE_ENDER_TORCH = MakeEnderTorch(BlockSoundGroup.WOOD).torchModel(MANGROVE_TORCH);
	public static final TorchContainer UNDERWATER_MANGROVE_TORCH = MakeUnderwaterTorch(BlockSoundGroup.WOOD).torchModel(MANGROVE_TORCH);
	//Campfires
	public static final BlockContainer MANGROVE_CAMPFIRE = MakeCampfire(MapColor.SPRUCE_BROWN).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer MANGROVE_SOUL_CAMPFIRE = MakeSoulCampfire(MANGROVE_CAMPFIRE).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer MANGROVE_ENDER_CAMPFIRE = MakeEnderCampfire(MANGROVE_CAMPFIRE).blockTag(BlockTags.AXE_MINEABLE);
	//</editor-fold>
	//<editor-fold desc="Melon">
	public static final EntityType<MelonSeedProjectileEntity> MELON_SEED_PROJECTILE_ENTITY = FabricEntityTypeBuilder.<MelonSeedProjectileEntity>create(SpawnGroup.MISC, MelonSeedProjectileEntity::new).dimensions(EntityDimensions.fixed(0.25F, 0.25F)).trackRangeChunks(4).trackedUpdateRate(10).build();
	public static final EntityType<MelonGolemEntity> MELON_GOLEM_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.MISC, MelonGolemEntity::new).dimensions(EntityType.SNOW_GOLEM.getDimensions()).trackRangeChunks(8).build();
	public static final BlockContainer CARVED_MELON = BuildBlock(new CarvedMelonBlock(GourdSettings(MapColor.LIME))).compostable(0.65f).blockTag(ModBlockTags.CARVED_MELONS).itemTag(ModItemTags.CARVED_MELONS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer MELON_LANTERN = BuildBlock(new CarvedMelonBlock(GourdSettings(MapColor.LIME).luminance(LUMINANCE_15))).blockTag(ModBlockTags.GOURD_LANTERNS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer SOUL_MELON_LANTERN = MakeGourdLantern(MapColor.LIME, LUMINANCE_10).blockTag(ModBlockTags.GOURD_LANTERNS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer ENDER_MELON_LANTERN = MakeGourdLantern(MapColor.LIME, LUMINANCE_13).blockTag(ModBlockTags.GOURD_LANTERNS).blockTag(BlockTags.AXE_MINEABLE);
	//</editor-fold>
	//<editor-fold desc="Milk and Cheese">
	//Flavored Milk
	public static final Item MILK_BOWL = GeneratedItem(new MilkBowlItem(ItemSettings().recipeRemainder(Items.BOWL).maxCount(16)));
	public static final Item CHOCOLATE_MILK_BUCKET = GeneratedItem(new ModMilkBucketItem(BucketProvider.DEFAULT_PROVIDER.FilledBucketSettings(), BucketProvider.DEFAULT_PROVIDER));
	public static final Item CHOCOLATE_MILK_BOWL = GeneratedItem(new MilkBowlItem(ItemSettings().recipeRemainder(Items.BOWL).maxCount(16)));
	public static final Item CHOCOLATE_MILK_BOTTLE = MakeDairyDrink(GlassBottledItemSettings());
	public static final Item COFFEE_MILK_BUCKET = GeneratedItem(new CoffeeMilkBucketItem(BucketProvider.DEFAULT_PROVIDER.FilledBucketSettings(), BucketProvider.DEFAULT_PROVIDER));
	public static final Item COFFEE_MILK_BOWL = GeneratedItem(new CoffeeMilkBowlItem(ItemSettings().recipeRemainder(Items.BOWL).maxCount(16)));
	public static final Item COFFEE_MILK_BOTTLE = GeneratedItem(new BottledMilkItem(GlassBottledItemSettings().food(new FoodComponent.Builder()
			.statusEffect(new StatusEffectInstance(StatusEffects.SPEED, 200, 0), 1.0F)
			.statusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 200, 0), 1.0F)
			.build())) {
		@Override
		public void OnDrink(ItemStack stack, LivingEntity user) { MilkUtil.ApplyMilk(user.world, user, true); }
	});
	public static final Item STRAWBERRY_MILK_BUCKET = GeneratedItem(new ModMilkBucketItem(BucketProvider.DEFAULT_PROVIDER.FilledBucketSettings(), BucketProvider.DEFAULT_PROVIDER));
	public static final Item STRAWBERRY_MILK_BOWL = GeneratedItem(new MilkBowlItem(ItemSettings().recipeRemainder(Items.BOWL).maxCount(16)));
	public static final Item STRAWBERRY_MILK_BOTTLE = MakeDairyDrink(GlassBottledItemSettings());
	public static final Item VANILLA_MILK_BUCKET = GeneratedItem(new ModMilkBucketItem(BucketProvider.DEFAULT_PROVIDER.FilledBucketSettings(), BucketProvider.DEFAULT_PROVIDER));
	public static final Item VANILLA_MILK_BOWL = GeneratedItem(new MilkBowlItem(ItemSettings().recipeRemainder(Items.BOWL).maxCount(16)));
	public static final Item VANILLA_MILK_BOTTLE = MakeDairyDrink(GlassBottledItemSettings());
	//Cheese
	public static final BlockContainer CHEESE_BLOCK = MakeBlock(Block.Settings.of(Material.SOLID_ORGANIC, MapColor.YELLOW).strength(1.0F).sounds(BlockSoundGroup.WART_BLOCK)).cubeAllModel().blockTag(BlockTags.SHOVEL_MINEABLE);
	//</editor-fold>
	//<editor-fold desc="Minecarts">
	public static final EntityType<DispenserMinecartEntity> DISPENSER_MINECART_ENTITY = FabricEntityTypeBuilder.<DispenserMinecartEntity>create(SpawnGroup.MISC, DispenserMinecartEntity::new).dimensions(EntityDimensions.fixed(0.98f, 0.7f)).trackRangeChunks(8).build();
	public static final Item DISPENSER_MINECART_ITEM = GeneratedItem(new DispenserMinecartItem(ItemSettings().maxCount(1)));
	//</editor-fold>
	//<editor-fold desc="Moss">
	public static final BlockContainer MOSS_SLAB = BuildSlab(new MossSlabBlock(Blocks.MOSS_BLOCK)).compostable(0.325f).slabModel(Blocks.MOSS_BLOCK).blockTag(BlockTags.HOE_MINEABLE);
	public static final BedContainer MOSS_BED = MakeBed("moss", MapColor.GREEN, BlockSoundGroup.MOSS_BLOCK);
	public static final EntityType<MossySheepEntity> MOSSY_SHEEP_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, MossySheepEntity::new).dimensions(EntityType.SHEEP.getDimensions()).trackRangeChunks(10).build();
	public static final Item MOSSY_SHEEP_SPAWN_EGG = GeneratedItem(new SpawnEggItem(MOSSY_SHEEP_ENTITY, 0xFFFFFF, 0xFFFFFF, ItemSettings()));
	//</editor-fold>
	//<editor-fold desc="Mud">
	public static final BlockContainer MUD = BuildBlock(new MudBlock(Block.Settings.copy(Blocks.DIRT).mapColor(MapColor.TERRACOTTA_CYAN).allowsSpawning(ModFactory::always).solidBlock(ModFactory::always).blockVision(ModFactory::always).suffocates(ModFactory::always).sounds(ModBlockSoundGroups.MUD)), ItemSettings(ItemGroup.BUILDING_BLOCKS)).cubeAllModel().blockTag(BlockTags.DIRT).blockTag(ModBlockTags.FROGS_SPAWNABLE_ON).blockTag(ModBlockTags.MANGROVE_LOGS_CAN_GROW_THROUGH).blockTag(ModBlockTags.MANGROVE_ROOTS_CAN_GROW_THROUGH).blockTag(ModBlockTags.SNIFFER_DIGGABLE_BLOCK).blockTag(BlockTags.SHOVEL_MINEABLE);
	public static final BlockContainer PACKED_MUD = MakeBlock(Block.Settings.copy(Blocks.DIRT).strength(1.0f, 3.0f).sounds(ModBlockSoundGroups.PACKED_MUD), ItemSettings(ItemGroup.BUILDING_BLOCKS)).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer MUD_BRICKS = MakeBlock(Block.Settings.of(Material.STONE, MapColor.TERRACOTTA_LIGHT_GRAY).requiresTool().strength(1.5f, 3.0f).sounds(ModBlockSoundGroups.MUD_BRICKS), ItemSettings(ItemGroup.BUILDING_BLOCKS)).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer MUD_BRICK_STAIRS = MakeStairs(MUD_BRICKS, ItemSettings(ItemGroup.BUILDING_BLOCKS)).stairsModel(MUD_BRICKS).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer MUD_BRICK_SLAB = MakeSlab(MUD_BRICKS, ItemSettings(ItemGroup.BUILDING_BLOCKS)).slabModel(MUD_BRICKS).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer MUD_BRICK_WALL = MakeWall(MUD_BRICKS, ItemSettings(ItemGroup.BUILDING_BLOCKS)).wallModel(MUD_BRICKS).blockTag(BlockTags.PICKAXE_MINEABLE);
	//Extended
	public static final BlockContainer PACKED_MUD_STAIRS = MakeStairs(PACKED_MUD).stairsModel(PACKED_MUD).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer PACKED_MUD_SLAB = MakeSlab(PACKED_MUD).slabModel(PACKED_MUD).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer PACKED_MUD_WALL = MakeWall(PACKED_MUD).wallModel(PACKED_MUD).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer CHISELED_MUD_BRICKS = MakeBlock(MUD_BRICKS).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer SMOOTH_CHISELED_MUD_BRICKS = MakeBlock(CHISELED_MUD_BRICKS).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	//</editor-fold>
	//<editor-fold desc="Mud (Liquid)">
	public static final FlowableFluid STILL_MUD_FLUID = new MudFluid.Still();
	public static final FlowableFluid FLOWING_MUD_FLUID = new MudFluid.Flowing();
	public static final FluidBlock MUD_FLUID_BLOCK = new MudFluidBlock(STILL_MUD_FLUID, FabricBlockSettings.copyOf(Blocks.WATER).mapColor(MapColor.BROWN));
	public static final Item MUD_BOTTLE = GeneratedItem(new BottledDrinkItem(GlassBottledItemSettings()) { @Override public void OnDrink(ItemStack stack, LivingEntity user) { user.damage(ModDamageSource.DRANK_MUD, 1); } });
	public static final BucketItem MUD_BUCKET = GeneratedItem(new ModBucketItem(STILL_MUD_FLUID, ItemSettings().recipeRemainder(Items.BUCKET).maxCount(1), BucketProvider.DEFAULT_PROVIDER));
	//</editor-fold>
	//<editor-fold desc="Mushrooms">
	public static final PottedBlockContainer DEATH_CAP_MUSHROOM = new PottedBlockContainer(new DeathCapMushroomBlock(
			Block.Settings.of(Material.PLANT, MapColor.PURPLE).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GRASS).luminance(state -> 1)),
			ItemSettings().food(FoodSettings(2, 0.2f).statusEffect(new StatusEffectInstance(ModStatusEffects.FRENZIED, 200), 1).build())
	).compostable(0.65f).drops(DropTable.FOUR_COUNT).dropPotted();
	//Nethershrooms
	public static final PottedBlockContainer BLUE_NETHERSHROOM = new PottedBlockContainer(new NethershroomBlock(
			Block.Settings.of(Material.PLANT, MapColor.CYAN).dynamicBounds().breakInstantly().sounds(BlockSoundGroup.FUNGUS)),
			ItemSettings()
	).compostable(0.65f).drops(DropTable.FOUR_COUNT).dropPotted();
	//Blue Mushroom
	private static ModConfiguredFeature<?, ?> getHugeBlueMushroomConfigured() { return HUGE_BLUE_MUSHROOM_CONFIGURED; }
	public static final PottedBlockContainer BLUE_MUSHROOM = new PottedBlockContainer(new MushroomPlantBlock(Block.Settings.of(Material.PLANT, MapColor.LIGHT_BLUE).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GRASS).postProcess(ModFactory::always), () -> getHugeBlueMushroomConfigured().getFeature())).compostable(0.65f).dropSelf().blockTag(BlockTags.ENDERMAN_HOLDABLE);
	public static final BlockContainer BLUE_MUSHROOM_BLOCK = BuildBlock(new MushroomBlock(Block.Settings.of(Material.WOOD, MapColor.LIGHT_BLUE).strength(0.2F).sounds(BlockSoundGroup.WOOD)), DropTable.Mushroom(BLUE_MUSHROOM)).compostable(0.85f).blockTag(BlockTags.AXE_MINEABLE);
	public static final Feature<HugeMushroomFeatureConfig> HUGE_BLUE_MUSHROOM_FEATURE = new HugeBlueMushroomFeature(HugeMushroomFeatureConfig.CODEC);
	public static final ModConfiguredFeature<?, ?> HUGE_BLUE_MUSHROOM_CONFIGURED = new ModConfiguredFeature<>("huge_blue_mushroom", new ConfiguredFeature<>(HUGE_BLUE_MUSHROOM_FEATURE, new HugeMushroomFeatureConfig(new ModSimpleBlockStateProvider(BLUE_MUSHROOM_BLOCK.asBlock().getDefaultState().with(MushroomBlock.DOWN, false)), new ModSimpleBlockStateProvider(Blocks.MUSHROOM_STEM.getDefaultState().with(MushroomBlock.UP, false).with(MushroomBlock.DOWN, false)), 3)));
	//</editor-fold>
	//<editor-fold desc= "Mushroom Wood (Blue)">
	public static final BlockContainer BLUE_MUSHROOM_PLANKS = MakePlanks(MapColor.LIGHT_BLUE).flammable(5, 20).fuel(300).cubeAllModel().blockTag(BlockTags.PLANKS).itemTag(ItemTags.PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer BLUE_MUSHROOM_STAIRS = MakeWoodStairs(BLUE_MUSHROOM_PLANKS).flammable(5, 20).fuel(300).stairsModel(BLUE_MUSHROOM_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer BLUE_MUSHROOM_SLAB = MakeSlab(BLUE_MUSHROOM_PLANKS).flammable(5, 20).fuel(150).slabModel(BLUE_MUSHROOM_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer BLUE_MUSHROOM_FENCE = MakeWoodFence(BLUE_MUSHROOM_PLANKS).flammable(5, 20).fuel(300).fenceModel(BLUE_MUSHROOM_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer BLUE_MUSHROOM_FENCE_GATE = MakeWoodFenceGate(BLUE_MUSHROOM_PLANKS).flammable(5, 20).fuel(300).fenceGateModel(BLUE_MUSHROOM_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer BLUE_MUSHROOM_DOOR = MakeWoodDoor(BLUE_MUSHROOM_PLANKS).fuel(200).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer BLUE_MUSHROOM_TRAPDOOR = MakeWoodTrapdoor(BLUE_MUSHROOM_PLANKS).fuel(300).trapdoorModel(true).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer BLUE_MUSHROOM_PRESSURE_PLATE = MakeWoodPressurePlate(BLUE_MUSHROOM_PLANKS).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer BLUE_MUSHROOM_BUTTON = MakeWoodButton().fuel(100).buttonModel(BLUE_MUSHROOM_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final SignContainer BLUE_MUSHROOM_SIGN = MakeSign("blue_mushroom", BLUE_MUSHROOM_PLANKS, BLUE_MUSHROOM_BLOCK).fuel(200).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer BLUE_MUSHROOM_IRON_HANGING_SIGN = MakeHangingSign(MakeSignType("blue_mushroom_iron"), BLUE_MUSHROOM_BLOCK).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer BLUE_MUSHROOM_GOLD_HANGING_SIGN = MakeHangingSign(MakeSignType("blue_mushroom_gold"), BLUE_MUSHROOM_BLOCK).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer BLUE_MUSHROOM_COPPER_HANGING_SIGN = MakeHangingSign(MakeSignType("blue_mushroom_copper"), BLUE_MUSHROOM_BLOCK).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer BLUE_MUSHROOM_EXPOSED_COPPER_HANGING_SIGN = MakeHangingSign(MakeSignType("blue_mushroom_exposed_copper"), BLUE_MUSHROOM_BLOCK).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer BLUE_MUSHROOM_WEATHERED_COPPER_HANGING_SIGN = MakeHangingSign(MakeSignType("blue_mushroom_weathered_copper"), BLUE_MUSHROOM_BLOCK).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer BLUE_MUSHROOM_OXIDIZED_COPPER_HANGING_SIGN = MakeHangingSign(MakeSignType("blue_mushroom_oxidized_copper"), BLUE_MUSHROOM_BLOCK).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer BLUE_MUSHROOM_NETHERITE_HANGING_SIGN = MakeHangingSign(MakeSignType("blue_mushroom_netherite"), BLUE_MUSHROOM_BLOCK).blockTag(BlockTags.AXE_MINEABLE);
	public static final BoatContainer BLUE_MUSHROOM_BOAT = MakeBoat("blue_mushroom", BLUE_MUSHROOM_PLANKS).fuel(1200);
	//Extended
	public static final BlockContainer BLUE_MUSHROOM_BEEHIVE = MakeBeehive(MapColor.LIGHT_BLUE).flammable(5, 20).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer BLUE_MUSHROOM_BOOKSHELF = MakeBookshelf(BLUE_MUSHROOM_PLANKS).flammable(30, 20).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer BLUE_MUSHROOM_CHISELED_BOOKSHELF = MakeChiseledBookshelf(MapColor.LIGHT_BLUE).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer BLUE_MUSHROOM_CRAFTING_TABLE = MakeCraftingTable(BLUE_MUSHROOM_PLANKS).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer BLUE_MUSHROOM_LADDER = MakeLadder().blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer BLUE_MUSHROOM_WOODCUTTER = MakeWoodcutter(BLUE_MUSHROOM_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer BLUE_MUSHROOM_BARREL = MakeBarrel(MapColor.LIGHT_BLUE).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer BLUE_MUSHROOM_LECTERN = MakeLectern(BLUE_MUSHROOM_PLANKS).flammable(30, 20).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer BLUE_MUSHROOM_POWDER_KEG = MakePowderKeg(BLUE_MUSHROOM_BARREL).blockTag(BlockTags.AXE_MINEABLE);
	//Torches
	public static final TorchContainer BLUE_MUSHROOM_TORCH = MakeTorch(BlockSoundGroup.WOOD).torchModel();
	public static final TorchContainer BLUE_MUSHROOM_SOUL_TORCH = MakeSoulTorch(BlockSoundGroup.WOOD).torchModel(BLUE_MUSHROOM_TORCH);
	public static final TorchContainer BLUE_MUSHROOM_ENDER_TORCH = MakeEnderTorch(BlockSoundGroup.WOOD).torchModel(BLUE_MUSHROOM_TORCH);
	public static final TorchContainer UNDERWATER_BLUE_MUSHROOM_TORCH = MakeUnderwaterTorch(BlockSoundGroup.WOOD).torchModel(BLUE_MUSHROOM_TORCH);
	//Campfires
	public static final BlockContainer BLUE_MUSHROOM_CAMPFIRE = MakeCampfire(MapColor.LIGHT_BLUE).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer BLUE_MUSHROOM_SOUL_CAMPFIRE = MakeSoulCampfire(BLUE_MUSHROOM_CAMPFIRE).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer BLUE_MUSHROOM_ENDER_CAMPFIRE = MakeEnderCampfire(BLUE_MUSHROOM_CAMPFIRE).blockTag(BlockTags.AXE_MINEABLE);
	//</editor-fold>
	//<editor-fold desc= "Mushroom Wood (Brown)">
	public static final BlockContainer BROWN_MUSHROOM_PLANKS = MakePlanks(MapColor.DIRT_BROWN).flammable(5, 20).fuel(300).cubeAllModel().blockTag(BlockTags.PLANKS).itemTag(ItemTags.PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer BROWN_MUSHROOM_STAIRS = MakeWoodStairs(BROWN_MUSHROOM_PLANKS).flammable(5, 20).fuel(300).stairsModel(BROWN_MUSHROOM_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer BROWN_MUSHROOM_SLAB = MakeSlab(BROWN_MUSHROOM_PLANKS).flammable(5, 20).fuel(150).slabModel(BROWN_MUSHROOM_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer BROWN_MUSHROOM_FENCE = MakeWoodFence(BROWN_MUSHROOM_PLANKS).flammable(5, 20).fuel(300).fenceModel(BROWN_MUSHROOM_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer BROWN_MUSHROOM_FENCE_GATE = MakeWoodFenceGate(BROWN_MUSHROOM_PLANKS).flammable(5, 20).fuel(300).fenceGateModel(BROWN_MUSHROOM_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer BROWN_MUSHROOM_DOOR = MakeWoodDoor(BROWN_MUSHROOM_PLANKS).fuel(200).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer BROWN_MUSHROOM_TRAPDOOR = MakeWoodTrapdoor(BROWN_MUSHROOM_PLANKS).fuel(300).trapdoorModel(true).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer BROWN_MUSHROOM_PRESSURE_PLATE = MakeWoodPressurePlate(BROWN_MUSHROOM_PLANKS).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer BROWN_MUSHROOM_BUTTON = MakeWoodButton().fuel(100).buttonModel(BROWN_MUSHROOM_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final SignContainer BROWN_MUSHROOM_SIGN = MakeSign("brown_mushroom", BROWN_MUSHROOM_PLANKS.asBlock(), Blocks.BROWN_MUSHROOM_BLOCK).fuel(200).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer BROWN_MUSHROOM_IRON_HANGING_SIGN = MakeHangingSign(MakeSignType("brown_mushroom_iron"), Blocks.BROWN_MUSHROOM_BLOCK).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer BROWN_MUSHROOM_GOLD_HANGING_SIGN = MakeHangingSign(MakeSignType("brown_mushroom_gold"), Blocks.BROWN_MUSHROOM_BLOCK).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer BROWN_MUSHROOM_COPPER_HANGING_SIGN = MakeHangingSign(MakeSignType("brown_mushroom_copper"), Blocks.BROWN_MUSHROOM_BLOCK).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer BROWN_MUSHROOM_EXPOSED_COPPER_HANGING_SIGN = MakeHangingSign(MakeSignType("brown_mushroom_exposed_copper"), Blocks.BROWN_MUSHROOM_BLOCK).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer BROWN_MUSHROOM_WEATHERED_COPPER_HANGING_SIGN = MakeHangingSign(MakeSignType("brown_mushroom_weathered_copper"), Blocks.BROWN_MUSHROOM_BLOCK).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer BROWN_MUSHROOM_OXIDIZED_COPPER_HANGING_SIGN = MakeHangingSign(MakeSignType("brown_mushroom_oxidized_copper"), Blocks.BROWN_MUSHROOM_BLOCK).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer BROWN_MUSHROOM_NETHERITE_HANGING_SIGN = MakeHangingSign(MakeSignType("brown_mushroom_netherite"), Blocks.BROWN_MUSHROOM_BLOCK).blockTag(BlockTags.AXE_MINEABLE);
	public static final BoatContainer BROWN_MUSHROOM_BOAT = MakeBoat("brown_mushroom", BROWN_MUSHROOM_PLANKS).fuel(1200);
	//Extended
	public static final BlockContainer BROWN_MUSHROOM_BEEHIVE = MakeBeehive(MapColor.DIRT_BROWN).flammable(5, 20).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer BROWN_MUSHROOM_BOOKSHELF = MakeBookshelf(BROWN_MUSHROOM_PLANKS).flammable(30, 20).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer BROWN_MUSHROOM_CHISELED_BOOKSHELF = MakeChiseledBookshelf(MapColor.DIRT_BROWN).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer BROWN_MUSHROOM_CRAFTING_TABLE = MakeCraftingTable(BROWN_MUSHROOM_PLANKS).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer BROWN_MUSHROOM_LADDER = MakeLadder().blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer BROWN_MUSHROOM_WOODCUTTER = MakeWoodcutter(BROWN_MUSHROOM_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer BROWN_MUSHROOM_BARREL = MakeBarrel(MapColor.DIRT_BROWN).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer BROWN_MUSHROOM_LECTERN = MakeLectern(BROWN_MUSHROOM_PLANKS).flammable(30, 20).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer BROWN_MUSHROOM_POWDER_KEG = MakePowderKeg(BROWN_MUSHROOM_BARREL).blockTag(BlockTags.AXE_MINEABLE);
	//Torches
	public static final TorchContainer BROWN_MUSHROOM_TORCH = MakeTorch(BlockSoundGroup.WOOD).torchModel();
	public static final TorchContainer BROWN_MUSHROOM_SOUL_TORCH = MakeSoulTorch(BlockSoundGroup.WOOD).torchModel(BROWN_MUSHROOM_TORCH);
	public static final TorchContainer BROWN_MUSHROOM_ENDER_TORCH = MakeEnderTorch(BlockSoundGroup.WOOD).torchModel(BROWN_MUSHROOM_TORCH);
	public static final TorchContainer UNDERWATER_BROWN_MUSHROOM_TORCH = MakeUnderwaterTorch(BlockSoundGroup.WOOD).torchModel(BROWN_MUSHROOM_TORCH);
	//Campfires
	public static final BlockContainer BROWN_MUSHROOM_CAMPFIRE = MakeCampfire(MapColor.DIRT_BROWN).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer BROWN_MUSHROOM_SOUL_CAMPFIRE = MakeSoulCampfire(BROWN_MUSHROOM_CAMPFIRE).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer BROWN_MUSHROOM_ENDER_CAMPFIRE = MakeEnderCampfire(BROWN_MUSHROOM_CAMPFIRE).blockTag(BlockTags.AXE_MINEABLE);
	//</editor-fold>
	//<editor-fold desc= "Mushroom Wood (Red)">
	public static final BlockContainer RED_MUSHROOM_PLANKS = MakePlanks(MapColor.RED).flammable(5, 20).fuel(300).cubeAllModel().blockTag(BlockTags.PLANKS).itemTag(ItemTags.PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer RED_MUSHROOM_STAIRS = MakeWoodStairs(RED_MUSHROOM_PLANKS).flammable(5, 20).fuel(300).stairsModel(RED_MUSHROOM_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer RED_MUSHROOM_SLAB = MakeSlab(RED_MUSHROOM_PLANKS).flammable(5, 20).fuel(150).slabModel(RED_MUSHROOM_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer RED_MUSHROOM_FENCE = MakeWoodFence(RED_MUSHROOM_PLANKS).flammable(5, 20).fuel(300).fenceModel(RED_MUSHROOM_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer RED_MUSHROOM_FENCE_GATE = MakeWoodFenceGate(RED_MUSHROOM_PLANKS).flammable(5, 20).fuel(300).fenceGateModel(RED_MUSHROOM_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer RED_MUSHROOM_DOOR = MakeWoodDoor(RED_MUSHROOM_PLANKS).fuel(200).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer RED_MUSHROOM_TRAPDOOR = MakeWoodTrapdoor(RED_MUSHROOM_PLANKS).fuel(300).trapdoorModel(true).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer RED_MUSHROOM_PRESSURE_PLATE = MakeWoodPressurePlate(RED_MUSHROOM_PLANKS).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer RED_MUSHROOM_BUTTON = MakeWoodButton().fuel(100).buttonModel(RED_MUSHROOM_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final SignContainer RED_MUSHROOM_SIGN = MakeSign("red_mushroom", RED_MUSHROOM_PLANKS.asBlock(), Blocks.RED_MUSHROOM_BLOCK).fuel(200).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer RED_MUSHROOM_IRON_HANGING_SIGN = MakeHangingSign(MakeSignType("red_mushroom_iron"), Blocks.RED_MUSHROOM_BLOCK).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer RED_MUSHROOM_GOLD_HANGING_SIGN = MakeHangingSign(MakeSignType("red_mushroom_gold"), Blocks.RED_MUSHROOM_BLOCK).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer RED_MUSHROOM_COPPER_HANGING_SIGN = MakeHangingSign(MakeSignType("red_mushroom_copper"), Blocks.RED_MUSHROOM_BLOCK).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer RED_MUSHROOM_EXPOSED_COPPER_HANGING_SIGN = MakeHangingSign(MakeSignType("red_mushroom_exposed_copper"), Blocks.RED_MUSHROOM_BLOCK).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer RED_MUSHROOM_WEATHERED_COPPER_HANGING_SIGN = MakeHangingSign(MakeSignType("red_mushroom_weathered_copper"), Blocks.RED_MUSHROOM_BLOCK).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer RED_MUSHROOM_OXIDIZED_COPPER_HANGING_SIGN = MakeHangingSign(MakeSignType("red_mushroom_oxidized_copper"), Blocks.RED_MUSHROOM_BLOCK).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer RED_MUSHROOM_NETHERITE_HANGING_SIGN = MakeHangingSign(MakeSignType("red_mushroom_netherite"), Blocks.RED_MUSHROOM_BLOCK).blockTag(BlockTags.AXE_MINEABLE);
	public static final BoatContainer RED_MUSHROOM_BOAT = MakeBoat("red_mushroom", RED_MUSHROOM_PLANKS).fuel(1200);
	//Extended
	public static final BlockContainer RED_MUSHROOM_BEEHIVE = MakeBeehive(MapColor.RED).flammable(5, 20).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer RED_MUSHROOM_BOOKSHELF = MakeBookshelf(RED_MUSHROOM_PLANKS).flammable(30, 20).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer RED_MUSHROOM_CHISELED_BOOKSHELF = MakeChiseledBookshelf(MapColor.RED).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer RED_MUSHROOM_CRAFTING_TABLE = MakeCraftingTable(RED_MUSHROOM_PLANKS).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer RED_MUSHROOM_LADDER = MakeLadder().blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer RED_MUSHROOM_WOODCUTTER = MakeWoodcutter(RED_MUSHROOM_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer RED_MUSHROOM_BARREL = MakeBarrel(MapColor.RED).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer RED_MUSHROOM_LECTERN = MakeLectern(RED_MUSHROOM_PLANKS).flammable(30, 20).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer RED_MUSHROOM_POWDER_KEG = MakePowderKeg(RED_MUSHROOM_BARREL).blockTag(BlockTags.AXE_MINEABLE);
	//Torches
	public static final TorchContainer RED_MUSHROOM_TORCH = MakeTorch(BlockSoundGroup.WOOD).torchModel();
	public static final TorchContainer RED_MUSHROOM_SOUL_TORCH = MakeSoulTorch(BlockSoundGroup.WOOD).torchModel(RED_MUSHROOM_TORCH);
	public static final TorchContainer RED_MUSHROOM_ENDER_TORCH = MakeEnderTorch(BlockSoundGroup.WOOD).torchModel(RED_MUSHROOM_TORCH);
	public static final TorchContainer UNDERWATER_RED_MUSHROOM_TORCH = MakeUnderwaterTorch(BlockSoundGroup.WOOD).torchModel(RED_MUSHROOM_TORCH);
	//Campfires
	public static final BlockContainer RED_MUSHROOM_CAMPFIRE = MakeCampfire(MapColor.RED).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer RED_MUSHROOM_SOUL_CAMPFIRE = MakeSoulCampfire(RED_MUSHROOM_CAMPFIRE).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer RED_MUSHROOM_ENDER_CAMPFIRE = MakeEnderCampfire(RED_MUSHROOM_CAMPFIRE).blockTag(BlockTags.AXE_MINEABLE);
	//</editor-fold>
	//<editor-fold desc= "Mushroom Wood (Stem)">
	public static final BlockContainer MUSHROOM_STEM_PLANKS = MakePlanks(MapColor.GRAY).flammable(5, 20).fuel(300).cubeAllModel().blockTag(BlockTags.PLANKS).itemTag(ItemTags.PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer MUSHROOM_STEM_STAIRS = MakeWoodStairs(MUSHROOM_STEM_PLANKS).flammable(5, 20).fuel(300).stairsModel(MUSHROOM_STEM_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer MUSHROOM_STEM_SLAB = MakeSlab(MUSHROOM_STEM_PLANKS).flammable(5, 20).fuel(150).slabModel(MUSHROOM_STEM_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer MUSHROOM_STEM_FENCE = MakeWoodFence(MUSHROOM_STEM_PLANKS).flammable(5, 20).fuel(300).fenceModel(MUSHROOM_STEM_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer MUSHROOM_STEM_FENCE_GATE = MakeWoodFenceGate(MUSHROOM_STEM_PLANKS).flammable(5, 20).fuel(300).fenceGateModel(MUSHROOM_STEM_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer MUSHROOM_STEM_DOOR = MakeWoodDoor(MUSHROOM_STEM_PLANKS).fuel(200).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer MUSHROOM_STEM_TRAPDOOR = MakeWoodTrapdoor(MUSHROOM_STEM_PLANKS).fuel(300).trapdoorModel(true).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer MUSHROOM_STEM_PRESSURE_PLATE = MakeWoodPressurePlate(MUSHROOM_STEM_PLANKS).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer MUSHROOM_STEM_BUTTON = MakeWoodButton().fuel(100).buttonModel(MUSHROOM_STEM_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final SignContainer MUSHROOM_STEM_SIGN = MakeSign("mushroom_stem", MUSHROOM_STEM_PLANKS.asBlock(), Blocks.MUSHROOM_STEM).fuel(200).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer MUSHROOM_STEM_IRON_HANGING_SIGN = MakeHangingSign(MakeSignType("mushroom_stem_iron"), Blocks.MUSHROOM_STEM).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer MUSHROOM_STEM_GOLD_HANGING_SIGN = MakeHangingSign(MakeSignType("mushroom_stem_gold"), Blocks.MUSHROOM_STEM).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer MUSHROOM_STEM_COPPER_HANGING_SIGN = MakeHangingSign(MakeSignType("mushroom_stem_copper"), Blocks.MUSHROOM_STEM).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer MUSHROOM_STEM_EXPOSED_COPPER_HANGING_SIGN = MakeHangingSign(MakeSignType("mushroom_stem_exposed_copper"), Blocks.MUSHROOM_STEM).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer MUSHROOM_STEM_WEATHERED_COPPER_HANGING_SIGN = MakeHangingSign(MakeSignType("mushroom_stem_weathered_copper"), Blocks.MUSHROOM_STEM).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer MUSHROOM_STEM_OXIDIZED_COPPER_HANGING_SIGN = MakeHangingSign(MakeSignType("mushroom_stem_oxidized_copper"), Blocks.MUSHROOM_STEM).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer MUSHROOM_STEM_NETHERITE_HANGING_SIGN = MakeHangingSign(MakeSignType("mushroom_stem_netherite"), Blocks.MUSHROOM_STEM).blockTag(BlockTags.AXE_MINEABLE);
	public static final BoatContainer MUSHROOM_STEM_BOAT = MakeBoat("mushroom_stem", MUSHROOM_STEM_PLANKS).fuel(1200);
	//Extended
	public static final BlockContainer MUSHROOM_STEM_BEEHIVE = MakeBeehive(MapColor.GRAY).flammable(5, 20).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer MUSHROOM_STEM_BOOKSHELF = MakeBookshelf(MUSHROOM_STEM_PLANKS).flammable(30, 20).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer MUSHROOM_STEM_CHISELED_BOOKSHELF = MakeChiseledBookshelf(MapColor.GRAY).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer MUSHROOM_STEM_CRAFTING_TABLE = MakeCraftingTable(MUSHROOM_STEM_PLANKS).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer MUSHROOM_STEM_LADDER = MakeLadder().blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer MUSHROOM_STEM_WOODCUTTER = MakeWoodcutter(MUSHROOM_STEM_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer MUSHROOM_STEM_BARREL = MakeBarrel(MapColor.GRAY).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer MUSHROOM_STEM_LECTERN = MakeLectern(MUSHROOM_STEM_PLANKS).flammable(30, 20).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer MUSHROOM_STEM_POWDER_KEG = MakePowderKeg(MUSHROOM_STEM_BARREL).blockTag(BlockTags.AXE_MINEABLE);
	//Torches
	public static final TorchContainer MUSHROOM_STEM_TORCH = MakeTorch(BlockSoundGroup.WOOD).torchModel();
	public static final TorchContainer MUSHROOM_STEM_SOUL_TORCH = MakeSoulTorch(BlockSoundGroup.WOOD).torchModel(MUSHROOM_STEM_TORCH);
	public static final TorchContainer MUSHROOM_STEM_ENDER_TORCH = MakeEnderTorch(BlockSoundGroup.WOOD).torchModel(MUSHROOM_STEM_TORCH);
	public static final TorchContainer UNDERWATER_MUSHROOM_STEM_TORCH = MakeUnderwaterTorch(BlockSoundGroup.WOOD).torchModel(MUSHROOM_STEM_TORCH);
	//Campfires
	public static final BlockContainer MUSHROOM_STEM_CAMPFIRE = MakeCampfire(MapColor.GRAY).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer MUSHROOM_STEM_SOUL_CAMPFIRE = MakeSoulCampfire(MUSHROOM_STEM_CAMPFIRE).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer MUSHROOM_STEM_ENDER_CAMPFIRE = MakeEnderCampfire(MUSHROOM_STEM_CAMPFIRE).blockTag(BlockTags.AXE_MINEABLE);
	//</editor-fold>
	//<editor-fold desc="Music Discs">
	public static final Item MUSIC_DISC_OTHERSIDE = GeneratedItem(new ModMusicDiscItem(14, ModSoundEvents.MUSIC_DISC_OTHERSIDE, ItemSettings(ItemGroup.MISC).maxCount(1).rarity(Rarity.RARE)));
	public static final Item MUSIC_DISC_5 = GeneratedItem(new ModMusicDiscItem(15, ModSoundEvents.MUSIC_DISC_5, ItemSettings(ItemGroup.MISC).maxCount(1).rarity(Rarity.RARE)));
	public static final Item DISC_FRAGMENT_5 = GeneratedItem(new DiscFragmentItem(ItemSettings(ItemGroup.MISC)));
	public static final Item MUSIC_DISC_RELIC = GeneratedItem(new ModMusicDiscItem(14, ModSoundEvents.MUSIC_DISC_RELIC, ItemSettings(ItemGroup.MISC).maxCount(1).rarity(Rarity.RARE)));
	//</editor-fold>
	//<editor-fold desc="Mycelium">
	public static final PottedBlockContainer MYCELIUM_ROOTS = new PottedBlockContainer(new MyceliumRootsBlock(Block.Settings.of(Material.REPLACEABLE_PLANT, MapColor.PURPLE).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS))).compostable(0.65f).requireSilkTouch().blockTag(BlockTags.ENDERMAN_HOLDABLE);
	public static final ModConfiguredFeature<SimpleBlockFeatureConfig, ?> SINGLE_MYCELIUM_ROOTS = new ModConfiguredFeature<>("single_mycelium_roots", new ConfiguredFeature<>(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(new SimpleBlockStateProvider(MYCELIUM_ROOTS.asBlock().getDefaultState()))));
	public static final ModConfiguredFeature<SimpleBlockFeatureConfig, ?> SINGLE_BROWN_MUSHROOM = new ModConfiguredFeature<>("single_brown_mushroom", new ConfiguredFeature<>(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(new SimpleBlockStateProvider(Blocks.BROWN_MUSHROOM.getDefaultState()))));
	public static final ModConfiguredFeature<SimpleBlockFeatureConfig, ?> SINGLE_BLUE_MUSHROOM = new ModConfiguredFeature<>("single_blue_mushroom", new ConfiguredFeature<>(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(new SimpleBlockStateProvider(BLUE_MUSHROOM.asBlock().getDefaultState()))));
	public static final ModConfiguredFeature<SimpleBlockFeatureConfig, ?> SINGLE_RED_MUSHROOM = new ModConfiguredFeature<>("single_red_mushroom", new ConfiguredFeature<>(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(new SimpleBlockStateProvider(Blocks.RED_MUSHROOM.getDefaultState()))));
	//</editor-fold>
	//<editor-fold desc="Nether Bricks">
	public static final BlockContainer SMOOTH_CHISELED_NETHER_BRICKS = MakeBlock(Blocks.CHISELED_NETHER_BRICKS).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer RED_NETHER_BRICK_FENCE = MakeFence(Blocks.RED_NETHER_BRICKS).fenceModel(Blocks.RED_NETHER_BRICKS).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer CHISELED_RED_NETHER_BRICKS = MakeBlock(Blocks.RED_NETHER_BRICKS).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer SMOOTH_CHISELED_RED_NETHER_BRICKS = MakeBlock(CHISELED_RED_NETHER_BRICKS).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer CRACKED_RED_NETHER_BRICKS = MakeBlock(Blocks.RED_NETHER_BRICKS).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer BLUE_NETHER_BRICKS = MakeBlock(Block.Settings.copy(Blocks.RED_NETHER_BRICKS).mapColor(MapColor.BRIGHT_TEAL)).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer BLUE_NETHER_BRICK_STAIRS = MakeStairs(BLUE_NETHER_BRICKS).stairsModel(BLUE_NETHER_BRICKS).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer BLUE_NETHER_BRICK_SLAB = MakeSlab(BLUE_NETHER_BRICKS).slabModel(BLUE_NETHER_BRICKS).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer BLUE_NETHER_BRICK_WALL = MakeWall(BLUE_NETHER_BRICKS).wallModel(BLUE_NETHER_BRICKS).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer BLUE_NETHER_BRICK_FENCE = MakeFence(BLUE_NETHER_BRICKS).fenceModel(BLUE_NETHER_BRICKS).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer CHISELED_BLUE_NETHER_BRICKS = MakeBlock(BLUE_NETHER_BRICKS).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer SMOOTH_CHISELED_BLUE_NETHER_BRICKS = MakeBlock(CHISELED_BLUE_NETHER_BRICKS).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer CRACKED_BLUE_NETHER_BRICKS = MakeBlock(BLUE_NETHER_BRICKS).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer YELLOW_NETHER_BRICKS = MakeBlock(Block.Settings.copy(Blocks.RED_NETHER_BRICKS).mapColor(MapColor.GOLD)).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer YELLOW_NETHER_BRICK_STAIRS = MakeStairs(YELLOW_NETHER_BRICKS).stairsModel(YELLOW_NETHER_BRICKS).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer YELLOW_NETHER_BRICK_SLAB = MakeSlab(YELLOW_NETHER_BRICKS).slabModel(YELLOW_NETHER_BRICKS).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer YELLOW_NETHER_BRICK_WALL = MakeWall(YELLOW_NETHER_BRICKS).wallModel(YELLOW_NETHER_BRICKS).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer YELLOW_NETHER_BRICK_FENCE = MakeFence(YELLOW_NETHER_BRICKS).fenceModel(YELLOW_NETHER_BRICKS).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer CHISELED_YELLOW_NETHER_BRICKS = MakeBlock(YELLOW_NETHER_BRICKS).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer SMOOTH_CHISELED_YELLOW_NETHER_BRICKS = MakeBlock(CHISELED_YELLOW_NETHER_BRICKS).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer CRACKED_YELLOW_NETHER_BRICKS = MakeBlock(YELLOW_NETHER_BRICKS).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer BLACK_NETHER_BRICKS = MakeBlock(Block.Settings.copy(Blocks.RED_NETHER_BRICKS).mapColor(MapColor.BLACK)).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer BLACK_NETHER_BRICK_STAIRS = MakeStairs(BLACK_NETHER_BRICKS).stairsModel(BLACK_NETHER_BRICKS).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer BLACK_NETHER_BRICK_SLAB = MakeSlab(BLACK_NETHER_BRICKS).slabModel(BLACK_NETHER_BRICKS).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer BLACK_NETHER_BRICK_WALL = MakeWall(BLACK_NETHER_BRICKS).wallModel(BLACK_NETHER_BRICKS).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer BLACK_NETHER_BRICK_FENCE = MakeFence(BLACK_NETHER_BRICKS).fenceModel(BLACK_NETHER_BRICKS).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer CHISELED_BLACK_NETHER_BRICKS = MakeBlock(BLACK_NETHER_BRICKS).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer SMOOTH_CHISELED_BLACK_NETHER_BRICKS = MakeBlock(CHISELED_BLACK_NETHER_BRICKS).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer CRACKED_BLACK_NETHER_BRICKS = MakeBlock(BLACK_NETHER_BRICKS).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	//</editor-fold>
	//<editor-fold desc="Netherite">
	public static final Item NETHERITE_NUGGET = MakeGeneratedItem(NetheriteItemSettings());
	public static final Item NETHERITE_ROD = MakeHandheldItem(NetheriteItemSettings());
	public static final TorchContainer NETHERITE_TORCH = MakeTorch(14, BlockSoundGroup.NETHERITE, ModParticleTypes.NETHERITE_FLAME, NetheriteItemSettings()).torchModel();
	public static final TorchContainer NETHERITE_SOUL_TORCH = MakeSoulTorch(BlockSoundGroup.NETHERITE, NetheriteItemSettings()).torchModel(NETHERITE_TORCH);
	public static final TorchContainer NETHERITE_ENDER_TORCH = MakeEnderTorch(BlockSoundGroup.NETHERITE, NetheriteItemSettings()).torchModel(NETHERITE_TORCH);
	public static final TorchContainer UNDERWATER_NETHERITE_TORCH = MakeUnderwaterTorch(BlockSoundGroup.NETHERITE, NetheriteItemSettings()).torchModel(NETHERITE_TORCH);
	public static final BlockContainer NETHERITE_LANTERN = MakeLantern(15, NetheriteItemSettings()).blockTag(BlockTags.NEEDS_DIAMOND_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer NETHERITE_SOUL_LANTERN = MakeSoulLantern(NetheriteItemSettings()).blockTag(BlockTags.NEEDS_DIAMOND_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer NETHERITE_ENDER_LANTERN = MakeLantern(13, NetheriteItemSettings()).blockTag(BlockTags.NEEDS_DIAMOND_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer NETHERITE_BUTTON = MakeMetalButton(BlockSoundGroup.NETHERITE, NetheriteItemSettings()).buttonModel(Blocks.NETHERITE_BLOCK).blockTag(BlockTags.NEEDS_DIAMOND_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer NETHERITE_CHAIN = MakeChain(NetheriteItemSettings()).blockTag(BlockTags.NEEDS_DIAMOND_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer HEAVY_NETHERITE_CHAIN = MakeHeavyChain(NetheriteItemSettings()).blockTag(BlockTags.NEEDS_DIAMOND_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer NETHERITE_BARS = MakeBars(BlockSoundGroup.NETHERITE, NetheriteItemSettings()).blockTag(BlockTags.NEEDS_DIAMOND_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer NETHERITE_STAIRS = MakeStairs(Blocks.NETHERITE_BLOCK, NetheriteItemSettings()).stairsModel(Blocks.NETHERITE_BLOCK).blockTag(BlockTags.NEEDS_DIAMOND_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer NETHERITE_SLAB = MakeSlab(Blocks.NETHERITE_BLOCK, NetheriteItemSettings()).slabModel(Blocks.NETHERITE_BLOCK).blockTag(BlockTags.NEEDS_DIAMOND_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer NETHERITE_WALL = MakeWall(Blocks.NETHERITE_BLOCK, NetheriteItemSettings()).wallModel(Blocks.NETHERITE_BLOCK).blockTag(BlockTags.NEEDS_DIAMOND_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer NETHERITE_BRICKS = MakeBlock(Blocks.NETHERITE_BLOCK, NetheriteItemSettings()).cubeAllModel().blockTag(BlockTags.NEEDS_DIAMOND_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer NETHERITE_BRICK_STAIRS = MakeStairs(NETHERITE_BRICKS, NetheriteItemSettings()).stairsModel(NETHERITE_BRICKS).blockTag(BlockTags.NEEDS_DIAMOND_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer NETHERITE_BRICK_SLAB = MakeSlab(NETHERITE_BRICKS, NetheriteItemSettings()).slabModel(NETHERITE_BRICKS).blockTag(BlockTags.NEEDS_DIAMOND_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer NETHERITE_BRICK_WALL = MakeWall(NETHERITE_BRICKS, NetheriteItemSettings()).wallModel(NETHERITE_BRICKS).blockTag(BlockTags.NEEDS_DIAMOND_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer CUT_NETHERITE = MakeBlock(Blocks.NETHERITE_BLOCK, NetheriteItemSettings()).cubeAllModel().blockTag(BlockTags.NEEDS_DIAMOND_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer CUT_NETHERITE_PILLAR = BuildBlock(new ModPillarBlock(Blocks.NETHERITE_BLOCK), NetheriteItemSettings()).blockTag(BlockTags.NEEDS_DIAMOND_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer CUT_NETHERITE_STAIRS = MakeStairs(CUT_NETHERITE, NetheriteItemSettings()).stairsModel(CUT_NETHERITE).blockTag(BlockTags.NEEDS_DIAMOND_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer CUT_NETHERITE_SLAB = MakeSlab(CUT_NETHERITE, NetheriteItemSettings()).slabModel(CUT_NETHERITE).blockTag(BlockTags.NEEDS_DIAMOND_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer CUT_NETHERITE_WALL = MakeWall(CUT_NETHERITE, NetheriteItemSettings()).wallModel(CUT_NETHERITE).blockTag(BlockTags.NEEDS_DIAMOND_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer NETHERITE_DOOR = BuildMetalDoor(new ModDoorBlock(Block.Settings.copy(Blocks.IRON_DOOR).mapColor(MapColor.BLACK)), NetheriteItemSettings()).blockTag(BlockTags.NEEDS_DIAMOND_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer NETHERITE_TRAPDOOR = MakeMetalTrapdoor(MapColor.BLACK, BlockSoundGroup.NETHERITE, 3.0f, 12.0f, NetheriteItemSettings()).trapdoorModel(false).blockTag(BlockTags.NEEDS_DIAMOND_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer CRUSHING_WEIGHTED_PRESSURE_PLATE = BuildBlock(new ModWeightedPressurePlateBlock(300, Block.Settings.of(Material.METAL).requiresTool().noCollision().strength(0.5F).sounds(BlockSoundGroup.WOOD)), NetheriteItemSettings()).weightedPressurePlateModel(Blocks.NETHERITE_BLOCK).blockTag(BlockTags.PRESSURE_PLATES).blockTag(BlockTags.NEEDS_DIAMOND_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final Item NETHERITE_HAMMER = MakeHammer(ToolMaterials.NETHERITE, 6, -3, NetheriteItemSettings());
	public static final ShearsItem NETHERITE_SHEARS = MakeShears(ToolMaterials.NETHERITE, NetheriteItemSettings());
	public static final Item NETHERITE_HORSE_ARMOR = MakeHorseArmor(15, ArmorMaterials.NETHERITE.getName(), NetheriteItemSettings().maxCount(1));
	private static final BucketContainer NETHERITE_BUCKET_PROVIDER = new BucketContainer(true);
	public static final Item NETHERITE_BUCKET = NETHERITE_BUCKET_PROVIDER.getBucket();
	public static final Item NETHERITE_WATER_BUCKET = NETHERITE_BUCKET_PROVIDER.getWaterBucket();
	public static final Item NETHERITE_LAVA_BUCKET = NETHERITE_BUCKET_PROVIDER.getLavaBucket();
	public static final Item NETHERITE_POWDER_SNOW_BUCKET = NETHERITE_BUCKET_PROVIDER.getPowderSnowBucket();
	public static final Item NETHERITE_BLOOD_BUCKET = NETHERITE_BUCKET_PROVIDER.getBloodBucket();
	public static final Item NETHERITE_MUD_BUCKET = NETHERITE_BUCKET_PROVIDER.getMudBucket();
	public static final Item NETHERITE_MILK_BUCKET = NETHERITE_BUCKET_PROVIDER.getMilkBucket();
	public static final Item NETHERITE_CHOCOLATE_MILK_BUCKET = NETHERITE_BUCKET_PROVIDER.getChocolateMilkBucket();
	public static final Item NETHERITE_COFFEE_MILK_BUCKET = NETHERITE_BUCKET_PROVIDER.getCoffeeMilkBucket();
	public static final Item NETHERITE_STRAWBERRY_MILK_BUCKET = NETHERITE_BUCKET_PROVIDER.getStrawberryMilkBucket();
	public static final Item NETHERITE_VANILLA_MILK_BUCKET = NETHERITE_BUCKET_PROVIDER.getVanillaMilkBucket();
	//</editor-fold>
	//<editor-fold desc="Oak">
	public static final Item OAK_CHEST_BOAT = MakeChestBoat(BoatEntity.Type.OAK, BoatSettings(ItemGroup.TRANSPORTATION));
	public static final WallBlockContainer OAK_HANGING_SIGN = MakeHangingSign(SignType.OAK, Blocks.STRIPPED_OAK_LOG, SignItemSettings(ItemGroup.DECORATIONS)).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer OAK_IRON_HANGING_SIGN = MakeHangingSign(MakeSignType("oak_iron"), Blocks.STRIPPED_OAK_LOG).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer OAK_GOLD_HANGING_SIGN = MakeHangingSign(MakeSignType("oak_gold"), Blocks.STRIPPED_OAK_LOG).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer OAK_COPPER_HANGING_SIGN = MakeHangingSign(MakeSignType("oak_copper"), Blocks.STRIPPED_OAK_LOG).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer OAK_EXPOSED_COPPER_HANGING_SIGN = MakeHangingSign(MakeSignType("oak_exposed_copper"), Blocks.STRIPPED_OAK_LOG).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer OAK_WEATHERED_COPPER_HANGING_SIGN = MakeHangingSign(MakeSignType("oak_weathered_copper"), Blocks.STRIPPED_OAK_LOG).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer OAK_OXIDIZED_COPPER_HANGING_SIGN = MakeHangingSign(MakeSignType("oak_oxidized_copper"), Blocks.STRIPPED_OAK_LOG).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer OAK_NETHERITE_HANGING_SIGN = MakeHangingSign(MakeSignType("oak_netherite"), Blocks.STRIPPED_OAK_LOG).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer CHISELED_BOOKSHELF = MakeChiseledBookshelf(MapColor.OAK_TAN).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer WOODCUTTER = MakeWoodcutter(Blocks.OAK_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer OAK_BARREL = MakeBarrel(MapColor.OAK_TAN).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer OAK_POWDER_KEG = MakePowderKeg(OAK_BARREL).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer OAK_LOG_SLAB = MakeLogSlab(Blocks.OAK_LOG).flammable(5, 5).fuel(150).itemTag(ModItemTags.CHARRABLE_LOG_SLABS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer STRIPPED_OAK_LOG_SLAB = MakeLogSlab(Blocks.STRIPPED_OAK_LOG).flammable(5, 5).fuel(150).itemTag(ModItemTags.CHARRABLE_STRIPPED_LOG_SLABS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer OAK_WOOD_SLAB = MakeBarkSlab(Blocks.OAK_WOOD, Blocks.OAK_LOG).flammable(5, 5).fuel(150).itemTag(ModItemTags.CHARRABLE_WOOD_SLABS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer STRIPPED_OAK_WOOD_SLAB = MakeBarkSlab(Blocks.STRIPPED_OAK_WOOD, Blocks.STRIPPED_OAK_LOG).flammable(5, 5).fuel(150).itemTag(ModItemTags.CHARRABLE_STRIPPED_WOOD_SLABS).blockTag(BlockTags.AXE_MINEABLE);
	//Minecraft Dungeons
	public static final BlockContainer BLUE_GREEN_OAK_LEAVES = MakeLeaves(Block.Settings.copy(Blocks.OAK_LEAVES).mapColor(MapColor.TERRACOTTA_CYAN));
	public static final BlockContainer LIGHT_GREEN_OAK_LEAVES = MakeLeaves(Block.Settings.copy(Blocks.OAK_LEAVES).mapColor(MapColor.EMERALD_GREEN));
	public static final BlockContainer RED_OAK_LEAVES = MakeLeaves(Block.Settings.copy(Blocks.OAK_LEAVES).mapColor(MapColor.RED));
	public static final BlockContainer WHITE_OAK_LEAVES = MakeLeaves(Block.Settings.copy(Blocks.OAK_LEAVES).mapColor(MapColor.LIGHT_BLUE_GRAY));
	public static final BlockContainer YELLOW_OAK_LEAVES = MakeLeaves(Block.Settings.copy(Blocks.OAK_LEAVES).mapColor(MapColor.YELLOW));
	//Misc
	public static final Item OAK_HAMMER = MakeHammer(ToolMaterials.WOOD, 7, -3.2F);
	//</editor-fold>
	//<editor-fold desc="Obsidian">
	public static final BlockContainer OBSIDIAN_STAIRS = MakeStairs(Blocks.OBSIDIAN).stairsModel(Blocks.OBSIDIAN).blockTag(ModBlockTags.PISTON_IMMOVABLE).blockTag(BlockTags.NEEDS_DIAMOND_TOOL);
	public static final BlockContainer OBSIDIAN_SLAB = MakeSlab(Blocks.OBSIDIAN).slabModel(Blocks.OBSIDIAN).blockTag(ModBlockTags.PISTON_IMMOVABLE).blockTag(BlockTags.NEEDS_DIAMOND_TOOL);
	public static final BlockContainer OBSIDIAN_WALL = MakeWall(Blocks.OBSIDIAN).wallModel(Blocks.OBSIDIAN).blockTag(ModBlockTags.PISTON_IMMOVABLE).blockTag(BlockTags.NEEDS_DIAMOND_TOOL);
	public static final BlockContainer POLISHED_OBSIDIAN = MakeBlock(Blocks.OBSIDIAN).cubeAllModel().blockTag(ModBlockTags.PISTON_IMMOVABLE).blockTag(BlockTags.NEEDS_DIAMOND_TOOL);
	public static final BlockContainer POLISHED_OBSIDIAN_STAIRS = MakeStairs(POLISHED_OBSIDIAN).stairsModel(POLISHED_OBSIDIAN).blockTag(ModBlockTags.PISTON_IMMOVABLE).blockTag(BlockTags.NEEDS_DIAMOND_TOOL);
	public static final BlockContainer POLISHED_OBSIDIAN_SLAB = MakeSlab(POLISHED_OBSIDIAN).slabModel(POLISHED_OBSIDIAN).blockTag(ModBlockTags.PISTON_IMMOVABLE).blockTag(BlockTags.NEEDS_DIAMOND_TOOL);
	public static final BlockContainer POLISHED_OBSIDIAN_WALL = MakeWall(POLISHED_OBSIDIAN).wallModel(POLISHED_OBSIDIAN).blockTag(ModBlockTags.PISTON_IMMOVABLE).blockTag(BlockTags.NEEDS_DIAMOND_TOOL);
	public static final BlockContainer POLISHED_OBSIDIAN_BRICKS = MakeBlock(POLISHED_OBSIDIAN).cubeAllModel().blockTag(ModBlockTags.PISTON_IMMOVABLE).blockTag(BlockTags.NEEDS_DIAMOND_TOOL);
	public static final BlockContainer POLISHED_OBSIDIAN_BRICK_STAIRS = MakeStairs(POLISHED_OBSIDIAN_BRICKS).stairsModel(POLISHED_OBSIDIAN_BRICKS).blockTag(ModBlockTags.PISTON_IMMOVABLE).blockTag(BlockTags.NEEDS_DIAMOND_TOOL);
	public static final BlockContainer POLISHED_OBSIDIAN_BRICK_SLAB = MakeSlab(POLISHED_OBSIDIAN_BRICKS).slabModel(POLISHED_OBSIDIAN_BRICKS).blockTag(ModBlockTags.PISTON_IMMOVABLE).blockTag(BlockTags.NEEDS_DIAMOND_TOOL);
	public static final BlockContainer POLISHED_OBSIDIAN_BRICK_WALL = MakeWall(POLISHED_OBSIDIAN_BRICKS).wallModel(POLISHED_OBSIDIAN_BRICKS).blockTag(ModBlockTags.PISTON_IMMOVABLE).blockTag(BlockTags.NEEDS_DIAMOND_TOOL);
	public static final BlockContainer CRACKED_POLISHED_OBSIDIAN_BRICKS = MakeBlock(POLISHED_OBSIDIAN_BRICKS).cubeAllModel().blockTag(ModBlockTags.PISTON_IMMOVABLE).blockTag(BlockTags.NEEDS_DIAMOND_TOOL);
	//Crying
	public static final BlockContainer CRYING_OBSIDIAN_STAIRS = BuildStairs(new CryingObsidianStairsBlock(Blocks.CRYING_OBSIDIAN, ParticleTypes.DRIPPING_OBSIDIAN_TEAR)).stairsModel(Blocks.CRYING_OBSIDIAN).blockTag(ModBlockTags.PISTON_IMMOVABLE).blockTag(BlockTags.NEEDS_DIAMOND_TOOL);
	public static final BlockContainer CRYING_OBSIDIAN_SLAB = BuildSlab(new CryingObsidianSlabBlock(Blocks.CRYING_OBSIDIAN, ParticleTypes.DRIPPING_OBSIDIAN_TEAR)).slabModel(Blocks.CRYING_OBSIDIAN).blockTag(ModBlockTags.PISTON_IMMOVABLE).blockTag(BlockTags.NEEDS_DIAMOND_TOOL);
	public static final BlockContainer CRYING_OBSIDIAN_WALL = BuildWall(new CryingObsidianWallBlock(Blocks.CRYING_OBSIDIAN, ParticleTypes.DRIPPING_OBSIDIAN_TEAR)).wallModel(Blocks.CRYING_OBSIDIAN).blockTag(ModBlockTags.PISTON_IMMOVABLE).blockTag(BlockTags.NEEDS_DIAMOND_TOOL);
	public static final BlockContainer POLISHED_CRYING_OBSIDIAN = BuildBlock(new CryingObsidianBlock(Block.Settings.copy(Blocks.CRYING_OBSIDIAN))).cubeAllModel().blockTag(ModBlockTags.PISTON_IMMOVABLE).blockTag(BlockTags.NEEDS_DIAMOND_TOOL);
	public static final BlockContainer POLISHED_CRYING_OBSIDIAN_STAIRS = BuildStairs(new CryingObsidianStairsBlock(POLISHED_CRYING_OBSIDIAN, ParticleTypes.DRIPPING_OBSIDIAN_TEAR)).stairsModel(POLISHED_CRYING_OBSIDIAN).blockTag(ModBlockTags.PISTON_IMMOVABLE).blockTag(BlockTags.NEEDS_DIAMOND_TOOL);
	public static final BlockContainer POLISHED_CRYING_OBSIDIAN_SLAB = BuildSlab(new CryingObsidianSlabBlock(POLISHED_CRYING_OBSIDIAN, ParticleTypes.DRIPPING_OBSIDIAN_TEAR)).slabModel(POLISHED_CRYING_OBSIDIAN).blockTag(ModBlockTags.PISTON_IMMOVABLE).blockTag(BlockTags.NEEDS_DIAMOND_TOOL);
	public static final BlockContainer POLISHED_CRYING_OBSIDIAN_WALL = BuildWall(new CryingObsidianWallBlock(POLISHED_CRYING_OBSIDIAN, ParticleTypes.DRIPPING_OBSIDIAN_TEAR)).wallModel(POLISHED_CRYING_OBSIDIAN).blockTag(ModBlockTags.PISTON_IMMOVABLE).blockTag(BlockTags.NEEDS_DIAMOND_TOOL);
	public static final BlockContainer POLISHED_CRYING_OBSIDIAN_BRICKS = BuildBlock(new CryingObsidianBlock(Block.Settings.copy(POLISHED_CRYING_OBSIDIAN.asBlock()))).cubeAllModel().blockTag(ModBlockTags.PISTON_IMMOVABLE).blockTag(BlockTags.NEEDS_DIAMOND_TOOL);
	public static final BlockContainer POLISHED_CRYING_OBSIDIAN_BRICK_STAIRS = BuildStairs(new CryingObsidianStairsBlock(POLISHED_CRYING_OBSIDIAN_BRICKS, ParticleTypes.DRIPPING_OBSIDIAN_TEAR)).stairsModel(POLISHED_CRYING_OBSIDIAN_BRICKS).blockTag(ModBlockTags.PISTON_IMMOVABLE).blockTag(BlockTags.NEEDS_DIAMOND_TOOL);
	public static final BlockContainer POLISHED_CRYING_OBSIDIAN_BRICK_SLAB = BuildSlab(new CryingObsidianSlabBlock(POLISHED_CRYING_OBSIDIAN_BRICKS, ParticleTypes.DRIPPING_OBSIDIAN_TEAR)).slabModel(POLISHED_CRYING_OBSIDIAN_BRICKS).blockTag(ModBlockTags.PISTON_IMMOVABLE).blockTag(BlockTags.NEEDS_DIAMOND_TOOL);
	public static final BlockContainer POLISHED_CRYING_OBSIDIAN_BRICK_WALL = BuildWall(new CryingObsidianWallBlock(POLISHED_CRYING_OBSIDIAN_BRICKS, ParticleTypes.DRIPPING_OBSIDIAN_TEAR)).wallModel(POLISHED_CRYING_OBSIDIAN_BRICKS).blockTag(ModBlockTags.PISTON_IMMOVABLE).blockTag(BlockTags.NEEDS_DIAMOND_TOOL);
	public static final BlockContainer CRACKED_POLISHED_CRYING_OBSIDIAN_BRICKS = BuildBlock(new CryingObsidianBlock(Block.Settings.copy(POLISHED_CRYING_OBSIDIAN_BRICKS.asBlock()))).cubeAllModel().blockTag(ModBlockTags.PISTON_IMMOVABLE).blockTag(BlockTags.NEEDS_DIAMOND_TOOL);
	//Bleeding
	public static final BlockContainer BLEEDING_OBSIDIAN = BuildBlock(new BleedingObsidianBlock(Blocks.CRYING_OBSIDIAN)).cubeAllModel().blockTag(ModBlockTags.PISTON_IMMOVABLE).blockTag(BlockTags.NEEDS_DIAMOND_TOOL);
	public static final BlockContainer BLEEDING_OBSIDIAN_STAIRS = BuildStairs(new CryingObsidianStairsBlock(BLEEDING_OBSIDIAN, ModParticleTypes.DRIPPING_OBSIDIAN_BLOOD)).stairsModel(BLEEDING_OBSIDIAN).blockTag(ModBlockTags.PISTON_IMMOVABLE).blockTag(BlockTags.NEEDS_DIAMOND_TOOL);
	public static final BlockContainer BLEEDING_OBSIDIAN_SLAB = BuildSlab(new CryingObsidianSlabBlock(BLEEDING_OBSIDIAN, ModParticleTypes.DRIPPING_OBSIDIAN_BLOOD)).slabModel(BLEEDING_OBSIDIAN).blockTag(ModBlockTags.PISTON_IMMOVABLE).blockTag(BlockTags.NEEDS_DIAMOND_TOOL);
	public static final BlockContainer BLEEDING_OBSIDIAN_WALL = BuildWall(new CryingObsidianWallBlock(BLEEDING_OBSIDIAN, ModParticleTypes.DRIPPING_OBSIDIAN_BLOOD)).wallModel(BLEEDING_OBSIDIAN).blockTag(ModBlockTags.PISTON_IMMOVABLE).blockTag(BlockTags.NEEDS_DIAMOND_TOOL);
	public static final BlockContainer POLISHED_BLEEDING_OBSIDIAN = BuildBlock(new BleedingObsidianBlock(BLEEDING_OBSIDIAN)).cubeAllModel().blockTag(ModBlockTags.PISTON_IMMOVABLE).blockTag(BlockTags.NEEDS_DIAMOND_TOOL);
	public static final BlockContainer POLISHED_BLEEDING_OBSIDIAN_STAIRS = BuildStairs(new CryingObsidianStairsBlock(POLISHED_BLEEDING_OBSIDIAN, ModParticleTypes.DRIPPING_OBSIDIAN_BLOOD)).stairsModel(POLISHED_BLEEDING_OBSIDIAN).blockTag(ModBlockTags.PISTON_IMMOVABLE).blockTag(BlockTags.NEEDS_DIAMOND_TOOL);
	public static final BlockContainer POLISHED_BLEEDING_OBSIDIAN_SLAB = BuildSlab(new CryingObsidianSlabBlock(POLISHED_BLEEDING_OBSIDIAN, ModParticleTypes.DRIPPING_OBSIDIAN_BLOOD)).slabModel(POLISHED_BLEEDING_OBSIDIAN).blockTag(ModBlockTags.PISTON_IMMOVABLE).blockTag(BlockTags.NEEDS_DIAMOND_TOOL);
	public static final BlockContainer POLISHED_BLEEDING_OBSIDIAN_WALL = BuildWall(new CryingObsidianWallBlock(POLISHED_BLEEDING_OBSIDIAN, ModParticleTypes.DRIPPING_OBSIDIAN_BLOOD)).wallModel(POLISHED_BLEEDING_OBSIDIAN).blockTag(ModBlockTags.PISTON_IMMOVABLE).blockTag(BlockTags.NEEDS_DIAMOND_TOOL);
	public static final BlockContainer POLISHED_BLEEDING_OBSIDIAN_BRICKS = BuildBlock(new BleedingObsidianBlock(POLISHED_BLEEDING_OBSIDIAN)).cubeAllModel().blockTag(ModBlockTags.PISTON_IMMOVABLE).blockTag(BlockTags.NEEDS_DIAMOND_TOOL);
	public static final BlockContainer POLISHED_BLEEDING_OBSIDIAN_BRICK_STAIRS = BuildStairs(new CryingObsidianStairsBlock(POLISHED_BLEEDING_OBSIDIAN_BRICKS, ModParticleTypes.DRIPPING_OBSIDIAN_BLOOD)).stairsModel(POLISHED_BLEEDING_OBSIDIAN_BRICKS).blockTag(ModBlockTags.PISTON_IMMOVABLE).blockTag(BlockTags.NEEDS_DIAMOND_TOOL);
	public static final BlockContainer POLISHED_BLEEDING_OBSIDIAN_BRICK_SLAB = BuildSlab(new CryingObsidianSlabBlock(POLISHED_BLEEDING_OBSIDIAN_BRICKS, ModParticleTypes.DRIPPING_OBSIDIAN_BLOOD)).slabModel(POLISHED_BLEEDING_OBSIDIAN_BRICKS).blockTag(ModBlockTags.PISTON_IMMOVABLE).blockTag(BlockTags.NEEDS_DIAMOND_TOOL);
	public static final BlockContainer POLISHED_BLEEDING_OBSIDIAN_BRICK_WALL = BuildWall(new CryingObsidianWallBlock(POLISHED_BLEEDING_OBSIDIAN_BRICKS, ModParticleTypes.DRIPPING_OBSIDIAN_BLOOD)).wallModel(POLISHED_BLEEDING_OBSIDIAN_BRICKS).blockTag(ModBlockTags.PISTON_IMMOVABLE).blockTag(BlockTags.NEEDS_DIAMOND_TOOL);
	public static final BlockContainer CRACKED_POLISHED_BLEEDING_OBSIDIAN_BRICKS = BuildBlock(new BleedingObsidianBlock(POLISHED_BLEEDING_OBSIDIAN_BRICKS)).cubeAllModel().blockTag(ModBlockTags.PISTON_IMMOVABLE).blockTag(BlockTags.NEEDS_DIAMOND_TOOL);
	//Tools & Armor
	public static final Item OBSIDIAN_AXE = MakeAxe(ModToolMaterials.OBSIDIAN);
	public static final Item OBSIDIAN_HOE = MakeHoe(ModToolMaterials.OBSIDIAN);
	public static final Item OBSIDIAN_PICKAXE = MakePickaxe(ModToolMaterials.OBSIDIAN);
	public static final Item OBSIDIAN_SHOVEL = MakeShovel(ModToolMaterials.OBSIDIAN);
	public static final Item OBSIDIAN_SWORD = MakeSword(ModToolMaterials.OBSIDIAN);
	public static final Item OBSIDIAN_KNIFE = MakeKnife(ModToolMaterials.OBSIDIAN);
	public static final Item OBSIDIAN_HAMMER = MakeHammer(ModToolMaterials.OBSIDIAN);
	public static final Item OBSIDIAN_HELMET = MakeHelmet(ModArmorMaterials.OBSIDIAN);
	public static final Item OBSIDIAN_CHESTPLATE = MakeChestplate(ModArmorMaterials.OBSIDIAN);
	public static final Item OBSIDIAN_LEGGINGS = MakeLeggings(ModArmorMaterials.OBSIDIAN);
	public static final Item OBSIDIAN_BOOTS = MakeBoots(ModArmorMaterials.OBSIDIAN);
	public static final Item OBSIDIAN_HORSE_ARMOR = MakeHorseArmor(ModArmorMaterials.OBSIDIAN);
	//</editor-fold>
	//<editor-fold desc="Phantoms">
	public static final EntityType<RedPhantomEntity> RED_PHANTOM_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, RedPhantomEntity::new).dimensions(EntityDimensions.fixed(0.9f, 0.5f)).trackRangeChunks(8).build();
	public static final Item RED_PHANTOM_SPAWN_EGG = MakeSpawnEgg(RED_PHANTOM_ENTITY, 0x881214, 0x00E5F9);
	//</editor-fold>
	//<editor-fold desc="Piglin Head">
	private static final Block PIGLIN_HEAD_BLOCK = new PiglinHeadBlock(Block.Settings.of(Material.DECORATION).strength(1.0f), false);
	private static final Block PIGLIN_WALL_HEAD = new WallPiglinHeadBlock(Block.Settings.of(Material.DECORATION).strength(1.0f), false);
	public static final WallBlockContainer PIGLIN_HEAD = new WallBlockContainer(PIGLIN_HEAD_BLOCK, PIGLIN_WALL_HEAD, new VerticallyAttachableBlockItem(PIGLIN_HEAD_BLOCK, PIGLIN_WALL_HEAD, ItemSettings(ItemGroup.DECORATIONS).rarity(Rarity.UNCOMMON), Direction.DOWN)).dropSelf().itemTag(ModItemTags.NOTEBLOCK_TOP_INSTRUMENTS).itemTag(ModItemTags.HEAD_WEARABLE_BLOCKS);
	//Zombified
	private static final Block ZOMBIFIED_PIGLIN_HEAD_BLOCK = new PiglinHeadBlock(Block.Settings.of(Material.DECORATION).strength(1.0f), true);
	private static final Block ZOMBIFIED_PIGLIN_WALL_HEAD = new WallPiglinHeadBlock(Block.Settings.of(Material.DECORATION).strength(1.0f), true);
	public static final WallBlockContainer ZOMBIFIED_PIGLIN_HEAD = new WallBlockContainer(ZOMBIFIED_PIGLIN_HEAD_BLOCK, ZOMBIFIED_PIGLIN_WALL_HEAD, new VerticallyAttachableBlockItem(ZOMBIFIED_PIGLIN_HEAD_BLOCK, ZOMBIFIED_PIGLIN_WALL_HEAD, ItemSettings().rarity(Rarity.UNCOMMON), Direction.DOWN)).dropSelf().itemTag(ModItemTags.NOTEBLOCK_TOP_INSTRUMENTS).itemTag(ModItemTags.HEAD_WEARABLE_BLOCKS);
	//Entity
	public static final BlockEntityType<PiglinHeadEntity> PIGLIN_HEAD_BLOCK_ENTITY = FabricBlockEntityTypeBuilder.create(PiglinHeadEntity::new, PIGLIN_HEAD_BLOCK, PIGLIN_WALL_HEAD, ZOMBIFIED_PIGLIN_HEAD_BLOCK, ZOMBIFIED_PIGLIN_WALL_HEAD).build();
	//</editor-fold>
	public static final BlockContainer PINK_PETALS = new BlockContainer(new FlowerbedBlock(Block.Settings.of(Material.REPLACEABLE_PLANT, MapColor.PINK).strength(0.1f).noCollision().sounds(ModBlockSoundGroups.PINK_PETALS)), ItemSettings(ItemGroup.DECORATIONS)).compostable(0.3f).blockTag(BlockTags.FLOWERS).itemTag(ItemTags.FLOWERS).blockTag(BlockTags.INSIDE_STEP_SOUND_BLOCKS).blockTag(BlockTags.HOE_MINEABLE);
	//<editor-fold desc="Piranha">
	public static final EntityType<PiranhaEntity> PIRANHA_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.WATER_AMBIENT, PiranhaEntity::new).dimensions(EntityDimensions.fixed(0.7F, 0.7F)).trackRangeChunks(4).build();
	public static final Item PIRANHA_SPAWN_EGG = MakeSpawnEgg(PIRANHA_ENTITY, 4877153, 11762012);
	public static final Item PIRANHA = MakeFood(2, 0.1f);
	public static final Item COOKED_PIRANHA = MakeFood(6, 0.7f);
	//</editor-fold>
	//<editor-fold desc="Pitcher Plant">
	private static final Block PITCHER_CROP_BLOCK = new PitcherCropBlock(Block.Settings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.CROP).dynamicBounds());
	public static final BlockContainer PITCHER_CROP = new BlockContainer(PITCHER_CROP_BLOCK, new AliasedBlockItem(PITCHER_CROP_BLOCK, ItemSettings(ItemGroup.DECORATIONS))).compostable(0.3f).generatedItemModel();
	public static final TallBlockContainer PITCHER_PLANT = new TallBlockContainer(new TallPlantBlock(Block.Settings.of(Material.REPLACEABLE_PLANT).noCollision().breakInstantly().sounds(BlockSoundGroup.CROP)) {
		@Override public OffsetType getOffsetType() { return OffsetType.XZ; }
	}, ItemSettings(ItemGroup.DECORATIONS)).compostable(0.85f).generatedItemModel();
	//</editor-fold>
	//<editor-fold desc="Plushies">
	/*
	 * Plushie Blocks inspired by the Plushie Mod
	 * https://github.com/Link4real/Plushie-Mod/
	 *
	 * Models for Bats, Bears, Chickens, Pigs, Wolves, Sheared Sheep, and Sheared Snow Golems are original to this mod
	 *
	 * Remaining models come from the Plushie Mod
	 */
	private static Item getFancyChickenPlushieItem() { return FANCY_CHICKEN_PLUSHIE.asItem(); }
	public static final ItemGroup PLUSHIE_GROUP = FabricItemGroupBuilder.build(ModId.ID("plushies"), () -> new ItemStack(ModBase::getFancyChickenPlushieItem));
	//<editor-fold desc="Allays & Vexes">
	public static final BlockContainer ALLAY_PLUSHIE = MakePlushie(AllayPlushieBlock::new);
	public static final BlockContainer VEX_PLUSHIE = MakePlushie(AllayPlushieBlock::new);
	//</editor-fold>
	//<editor-fold desc="Axolotls">
	public static final BlockContainer BLUE_AXOLOTL_PLUSHIE = MakePlushie(AxolotlPlushieBlock::new);
	public static final BlockContainer CYAN_AXOLOTL_PLUSHIE = MakePlushie(AxolotlPlushieBlock::new);
	public static final BlockContainer GOLD_AXOLOTL_PLUSHIE = MakePlushie(AxolotlPlushieBlock::new);
	public static final BlockContainer LUCY_AXOLOTL_PLUSHIE = MakePlushie(AxolotlPlushieBlock::new);
	public static final BlockContainer WILD_AXOLOTL_PLUSHIE = MakePlushie(AxolotlPlushieBlock::new);
	//Haven
	public static final BlockContainer BLACK_AXOLOTL_PLUSHIE = MakePlushie(AxolotlPlushieBlock::new);
	public static final BlockContainer CYANIDE_AXOLOTL_PLUSHIE = MakePlushie(AxolotlPlushieBlock::new);
	public static final BlockContainer GLOW_AXOLOTL_PLUSHIE = MakePlushie(AxolotlPlushieBlock::new);
	public static final BlockContainer GOLDEN_AXOLOTL_PLUSHIE = MakePlushie(AxolotlPlushieBlock::new);
	public static final BlockContainer GREEN_AXOLOTL_PLUSHIE = MakePlushie(AxolotlPlushieBlock::new);
	public static final BlockContainer LUCIA_AXOLOTL_PLUSHIE = MakePlushie(AxolotlPlushieBlock::new);
	public static final BlockContainer RED_AXOLOTL_PLUSHIE = MakePlushie(AxolotlPlushieBlock::new);
	public static final BlockContainer SCULK_AXOLOTL_PLUSHIE = MakePlushie(AxolotlPlushieBlock::new);
	public static final BlockContainer WHITE_AXOLOTL_PLUSHIE = MakePlushie(AxolotlPlushieBlock::new);
	public static final BlockContainer WILDER_AXOLOTL_PLUSHIE = MakePlushie(AxolotlPlushieBlock::new);
	//</editor-fold>
	public static final BlockContainer BAT_PLUSHIE = MakePlushie(BatPlushieBlock::new);
	public static final BlockContainer BEE_PLUSHIE = MakePlushie(BeePlushieBlock::new);
	//<editor-fold desc="Camel">
	public static final BlockContainer CAMEL_PLUSHIE = MakePlushie(CamelPlushieBlock::new);
	public static final BlockContainer SADDLED_CAMEL_PLUSHIE = MakePlushie(CamelPlushieBlock::new);
	//</editor-fold>
	//<editor-fold desc="Cat">
	public static final BlockContainer OCELOT_PLUSHIE = MakePlushie(CatPlushieBlock::new);
	public static final BlockContainer ALL_BLACK_CAT_PLUSHIE = MakePlushie(CatPlushieBlock::new);
	public static final BlockContainer BLACK_CAT_PLUSHIE = MakePlushie(CatPlushieBlock::new);
	public static final BlockContainer BRITISH_SHORTHAIR_CAT_PLUSHIE = MakePlushie(CatPlushieBlock::new);
	public static final BlockContainer CALICO_CAT_PLUSHIE = MakePlushie(CatPlushieBlock::new);
	public static final BlockContainer JELLIE_CAT_PLUSHIE = MakePlushie(CatPlushieBlock::new);
	public static final BlockContainer PERSIAN_CAT_PLUSHIE = MakePlushie(CatPlushieBlock::new);
	public static final BlockContainer RAGDOLL_CAT_PLUSHIE = MakePlushie(CatPlushieBlock::new);
	public static final BlockContainer RED_CAT_PLUSHIE = MakePlushie(CatPlushieBlock::new);
	public static final BlockContainer SIAMESE_CAT_PLUSHIE = MakePlushie(CatPlushieBlock::new);
	public static final BlockContainer TABBY_CAT_PLUSHIE = MakePlushie(CatPlushieBlock::new);
	public static final BlockContainer WHITE_CAT_PLUSHIE = MakePlushie(CatPlushieBlock::new);
	//</editor-fold>
	//<editor-fold desc="Chickens">
	public static final BlockContainer CHICKEN_PLUSHIE = MakePlushie(ChickenPlushieBlock::new);
	public static final BlockContainer FANCY_CHICKEN_PLUSHIE = MakePlushie(FancyChickenPlushieBlock::new);
	public static final BlockContainer AMBER_CHICKEN_PLUSHIE = MakePlushie(ChickenPlushieBlock::new);
	public static final BlockContainer BRONZED_CHICKEN_PLUSHIE = MakePlushie(ChickenPlushieBlock::new);
	public static final BlockContainer GOLD_CRESTED_CHICKEN_PLUSHIE = MakePlushie(ChickenPlushieBlock::new);
	public static final BlockContainer MIDNIGHT_CHICKEN_PLUSHIE = MakePlushie(ChickenPlushieBlock::new);
	public static final BlockContainer SKEWBALD_CHICKEN_PLUSHIE = MakePlushie(ChickenPlushieBlock::new);
	public static final BlockContainer STORMY_CHICKEN_PLUSHIE = MakePlushie(ChickenPlushieBlock::new);
	//</editor-fold>
	//<editor-fold desc="Cows">
	public static final BlockContainer COW_PLUSHIE = MakePlushie(CowPlushieBlock::new);
	public static final BlockContainer ALBINO_COW_PLUSHIE = MakePlushie(CowPlushieBlock::new);
	public static final BlockContainer ASHEN_COW_PLUSHIE = MakePlushie(CowPlushieBlock::new);
	public static final BlockContainer COOKIE_COW_PLUSHIE = MakePlushie(CowPlushieBlock::new);
	public static final BlockContainer CREAM_COW_PLUSHIE = MakePlushie(CowPlushieBlock::new);
	public static final BlockContainer DAIRY_COW_PLUSHIE = MakePlushie(CowPlushieBlock::new);
	public static final BlockContainer PINTO_COW_PLUSHIE = MakePlushie(CowPlushieBlock::new);
	public static final BlockContainer SUNSET_COW_PLUSHIE = MakePlushie(CowPlushieBlock::new);
	public static final BlockContainer UMBRA_COW_PLUSHIE = MakePlushie(CowPlushieBlock::new);
	public static final BlockContainer SHEARED_UMBRA_COW_PLUSHIE = MakePlushie(CowPlushieBlock::new);
	public static final BlockContainer WOOLY_COW_PLUSHIE = MakePlushie(CowPlushieBlock::new);
	public static final BlockContainer SHEARED_WOOLY_COW_PLUSHIE = MakePlushie(CowPlushieBlock::new);
	public static final BlockContainer MOOSHROOM_PLUSHIE = MakePlushie(CowPlushieBlock::new);
	public static final BlockContainer BROWN_MOOSHROOM_PLUSHIE = MakePlushie(CowPlushieBlock::new);
	public static final BlockContainer BLUE_MOOSHROOM_PLUSHIE = MakePlushie(CowPlushieBlock::new);
	public static final BlockContainer CRIMSON_MOOSHROOM_PLUSHIE = MakePlushie(CowPlushieBlock::new);
	public static final BlockContainer WARPED_MOOSHROOM_PLUSHIE = MakePlushie(CowPlushieBlock::new);
	public static final BlockContainer GILDED_MOOSHROOM_PLUSHIE = MakePlushie(CowPlushieBlock::new);
	public static final BlockContainer MOOBLOOM_PLUSHIE = MakePlushie(CowPlushieBlock::new);
	public static final BlockContainer MOOLIP_PLUSHIE = MakePlushie(CowPlushieBlock::new);
	public static final BlockContainer MAGENTA_TULIP_MOOBLOSSOM_PLUSHIE = MakePlushie(CowPlushieBlock::new);
	public static final BlockContainer RED_TULIP_MOOBLOSSOM_PLUSHIE = MakePlushie(CowPlushieBlock::new);
	public static final BlockContainer ORANGE_TULIP_MOOBLOSSOM_PLUSHIE = MakePlushie(CowPlushieBlock::new);
	public static final BlockContainer WHITE_TULIP_MOOBLOSSOM_PLUSHIE = MakePlushie(CowPlushieBlock::new);
	public static final BlockContainer PINK_TULIP_MOOBLOSSOM_PLUSHIE = MakePlushie(CowPlushieBlock::new);
	//</editor-fold>
	public static final BlockContainer CREEPER_PLUSHIE = MakePlushie(CreeperPlushieBlock::new);
	public static final BlockContainer DOLPHIN_PLUSHIE = MakePlushie(DolphinPlushieBlock::new);
	public static final BlockContainer ENDER_DRAGON_PLUSHIE = MakePlushie(DragonPlushieBlock::new);
	//<editor-fold desc="Enderman">
	public static final BlockContainer ENDERMAN_PLUSHIE = MakePlushie(EndermanPlushieBlock::new);
	public static final BlockContainer WHITE_ENDERMAN_PLUSHIE = MakePlushie(EndermanPlushieBlock::new);
	//</editor-fold>
	//<editor-fold desc="Fox">
	public static final BlockContainer FOX_PLUSHIE = MakePlushie(FoxPlushieBlock::new);
	public static final BlockContainer SNOW_FOX_PLUSHIE = MakePlushie(FoxPlushieBlock::new);
	//</editor-fold>
	//<editor-fold desc="Frog">
	public static final BlockContainer COLD_FROG_PLUSHIE = MakePlushie(FrogPlushieBlock::new);
	public static final BlockContainer TEMPERATE_FROG_PLUSHIE = MakePlushie(FrogPlushieBlock::new);
	public static final BlockContainer WARM_FROG_PLUSHIE = MakePlushie(FrogPlushieBlock::new);
	//</editor-fold>
	//<editor-fold desc="Goat">
	public static final BlockContainer GOAT_PLUSHIE = MakePlushie(GoatPlushieBlock::new);
	public static final BlockContainer DARK_GOAT_PLUSHIE = MakePlushie(GoatPlushieBlock::new);
	//</editor-fold>
	//<editor-fold desc="Llama">
	public static final BlockContainer BROWN_LLAMA_PLUSHIE = MakePlushie(LlamaPlushieBlock::new);
	public static final BlockContainer BROWN_TRADER_LLAMA_PLUSHIE = MakePlushie(LlamaPlushieBlock::new);
	public static final BlockContainer BROWN_JOLLY_LLAMA_PLUSHIE = MakePlushie(LlamaPlushieBlock::new);
	public static final BlockContainer RAINBOW_CARPETED_BROWN_LLAMA_PLUSHIE = MakePlushie(LlamaPlushieBlock::new);
	public static final BlockContainer MOSS_CARPETED_BROWN_LLAMA_PLUSHIE = MakePlushie(LlamaPlushieBlock::new);
	public static final BlockContainer GLOW_LICHEN_CARPETED_BROWN_LLAMA_PLUSHIE = MakePlushie(LlamaPlushieBlock::new);
	public static final BlockContainer BEIGE_CARPETED_BROWN_LLAMA_PLUSHIE = MakePlushie(LlamaPlushieBlock::new);
	public static final BlockContainer BURGUNDY_CARPETED_BROWN_LLAMA_PLUSHIE = MakePlushie(LlamaPlushieBlock::new);
	public static final BlockContainer LAVENDER_CARPETED_BROWN_LLAMA_PLUSHIE = MakePlushie(LlamaPlushieBlock::new);
	public static final BlockContainer MINT_CARPETED_BROWN_LLAMA_PLUSHIE = MakePlushie(LlamaPlushieBlock::new);
	public static final Map<DyeColor, BlockContainer> CARPETED_BROWN_LLAMA_PLUSHIES = ColorUtil.Map(color -> MakePlushie(LlamaPlushieBlock::new));
	public static final BlockContainer CREAMY_LLAMA_PLUSHIE = MakePlushie(LlamaPlushieBlock::new);
	public static final BlockContainer CREAMY_TRADER_LLAMA_PLUSHIE = MakePlushie(LlamaPlushieBlock::new);
	public static final BlockContainer CREAMY_JOLLY_LLAMA_PLUSHIE = MakePlushie(LlamaPlushieBlock::new);
	public static final BlockContainer RAINBOW_CARPETED_CREAMY_LLAMA_PLUSHIE = MakePlushie(LlamaPlushieBlock::new);
	public static final BlockContainer MOSS_CARPETED_CREAMY_LLAMA_PLUSHIE = MakePlushie(LlamaPlushieBlock::new);
	public static final BlockContainer GLOW_LICHEN_CARPETED_CREAMY_LLAMA_PLUSHIE = MakePlushie(LlamaPlushieBlock::new);
	public static final BlockContainer BEIGE_CARPETED_CREAMY_LLAMA_PLUSHIE = MakePlushie(LlamaPlushieBlock::new);
	public static final BlockContainer BURGUNDY_CARPETED_CREAMY_LLAMA_PLUSHIE = MakePlushie(LlamaPlushieBlock::new);
	public static final BlockContainer LAVENDER_CARPETED_CREAMY_LLAMA_PLUSHIE = MakePlushie(LlamaPlushieBlock::new);
	public static final BlockContainer MINT_CARPETED_CREAMY_LLAMA_PLUSHIE = MakePlushie(LlamaPlushieBlock::new);
	public static final Map<DyeColor, BlockContainer> CARPETED_CREAMY_LLAMA_PLUSHIES = ColorUtil.Map(color -> MakePlushie(LlamaPlushieBlock::new));
	public static final BlockContainer GRAY_LLAMA_PLUSHIE = MakePlushie(LlamaPlushieBlock::new);
	public static final BlockContainer GRAY_TRADER_LLAMA_PLUSHIE = MakePlushie(LlamaPlushieBlock::new);
	public static final BlockContainer GRAY_JOLLY_LLAMA_PLUSHIE = MakePlushie(LlamaPlushieBlock::new);
	public static final BlockContainer RAINBOW_CARPETED_GRAY_LLAMA_PLUSHIE = MakePlushie(LlamaPlushieBlock::new);
	public static final BlockContainer MOSS_CARPETED_GRAY_LLAMA_PLUSHIE = MakePlushie(LlamaPlushieBlock::new);
	public static final BlockContainer GLOW_LICHEN_CARPETED_GRAY_LLAMA_PLUSHIE = MakePlushie(LlamaPlushieBlock::new);
	public static final BlockContainer BEIGE_CARPETED_GRAY_LLAMA_PLUSHIE = MakePlushie(LlamaPlushieBlock::new);
	public static final BlockContainer BURGUNDY_CARPETED_GRAY_LLAMA_PLUSHIE = MakePlushie(LlamaPlushieBlock::new);
	public static final BlockContainer LAVENDER_CARPETED_GRAY_LLAMA_PLUSHIE = MakePlushie(LlamaPlushieBlock::new);
	public static final BlockContainer MINT_CARPETED_GRAY_LLAMA_PLUSHIE = MakePlushie(LlamaPlushieBlock::new);
	public static final Map<DyeColor, BlockContainer> CARPETED_GRAY_LLAMA_PLUSHIES = ColorUtil.Map(color -> MakePlushie(LlamaPlushieBlock::new));
	public static final BlockContainer WHITE_LLAMA_PLUSHIE = MakePlushie(LlamaPlushieBlock::new);
	public static final BlockContainer WHITE_TRADER_LLAMA_PLUSHIE = MakePlushie(LlamaPlushieBlock::new);
	public static final BlockContainer WHITE_JOLLY_LLAMA_PLUSHIE = MakePlushie(LlamaPlushieBlock::new);
	public static final BlockContainer RAINBOW_CARPETED_WHITE_LLAMA_PLUSHIE = MakePlushie(LlamaPlushieBlock::new);
	public static final BlockContainer MOSS_CARPETED_WHITE_LLAMA_PLUSHIE = MakePlushie(LlamaPlushieBlock::new);
	public static final BlockContainer GLOW_LICHEN_CARPETED_WHITE_LLAMA_PLUSHIE = MakePlushie(LlamaPlushieBlock::new);
	public static final BlockContainer BEIGE_CARPETED_WHITE_LLAMA_PLUSHIE = MakePlushie(LlamaPlushieBlock::new);
	public static final BlockContainer BURGUNDY_CARPETED_WHITE_LLAMA_PLUSHIE = MakePlushie(LlamaPlushieBlock::new);
	public static final BlockContainer LAVENDER_CARPETED_WHITE_LLAMA_PLUSHIE = MakePlushie(LlamaPlushieBlock::new);
	public static final BlockContainer MINT_CARPETED_WHITE_LLAMA_PLUSHIE = MakePlushie(LlamaPlushieBlock::new);
	public static final Map<DyeColor, BlockContainer> CARPETED_WHITE_LLAMA_PLUSHIES = ColorUtil.Map(color -> MakePlushie(LlamaPlushieBlock::new));
	public static final BlockContainer MOCHA_LLAMA_PLUSHIE = MakePlushie(LlamaPlushieBlock::new);
	public static final BlockContainer MOCHA_TRADER_LLAMA_PLUSHIE = MakePlushie(LlamaPlushieBlock::new);
	public static final BlockContainer MOCHA_JOLLY_LLAMA_PLUSHIE = MakePlushie(LlamaPlushieBlock::new);
	public static final BlockContainer RAINBOW_CARPETED_MOCHA_LLAMA_PLUSHIE = MakePlushie(LlamaPlushieBlock::new);
	public static final BlockContainer MOSS_CARPETED_MOCHA_LLAMA_PLUSHIE = MakePlushie(LlamaPlushieBlock::new);
	public static final BlockContainer GLOW_LICHEN_CARPETED_MOCHA_LLAMA_PLUSHIE = MakePlushie(LlamaPlushieBlock::new);
	public static final BlockContainer BEIGE_CARPETED_MOCHA_LLAMA_PLUSHIE = MakePlushie(LlamaPlushieBlock::new);
	public static final BlockContainer BURGUNDY_CARPETED_MOCHA_LLAMA_PLUSHIE = MakePlushie(LlamaPlushieBlock::new);
	public static final BlockContainer LAVENDER_CARPETED_MOCHA_LLAMA_PLUSHIE = MakePlushie(LlamaPlushieBlock::new);
	public static final BlockContainer MINT_CARPETED_MOCHA_LLAMA_PLUSHIE = MakePlushie(LlamaPlushieBlock::new);
	public static final Map<DyeColor, BlockContainer> CARPETED_MOCHA_LLAMA_PLUSHIES = ColorUtil.Map(color -> MakePlushie(LlamaPlushieBlock::new));
	public static final BlockContainer COCOA_LLAMA_PLUSHIE = MakePlushie(LlamaPlushieBlock::new);
	public static final BlockContainer COCOA_TRADER_LLAMA_PLUSHIE = MakePlushie(LlamaPlushieBlock::new);
	public static final BlockContainer COCOA_JOLLY_LLAMA_PLUSHIE = MakePlushie(LlamaPlushieBlock::new);
	public static final BlockContainer RAINBOW_CARPETED_COCOA_LLAMA_PLUSHIE = MakePlushie(LlamaPlushieBlock::new);
	public static final BlockContainer MOSS_CARPETED_COCOA_LLAMA_PLUSHIE = MakePlushie(LlamaPlushieBlock::new);
	public static final BlockContainer GLOW_LICHEN_CARPETED_COCOA_LLAMA_PLUSHIE = MakePlushie(LlamaPlushieBlock::new);
	public static final BlockContainer BEIGE_CARPETED_COCOA_LLAMA_PLUSHIE = MakePlushie(LlamaPlushieBlock::new);
	public static final BlockContainer BURGUNDY_CARPETED_COCOA_LLAMA_PLUSHIE = MakePlushie(LlamaPlushieBlock::new);
	public static final BlockContainer LAVENDER_CARPETED_COCOA_LLAMA_PLUSHIE = MakePlushie(LlamaPlushieBlock::new);
	public static final BlockContainer MINT_CARPETED_COCOA_LLAMA_PLUSHIE = MakePlushie(LlamaPlushieBlock::new);
	public static final Map<DyeColor, BlockContainer> CARPETED_COCOA_LLAMA_PLUSHIES = ColorUtil.Map(color -> MakePlushie(LlamaPlushieBlock::new));
	//</editor-fold>
	//<editor-fold desc="Panda">
	public static final BlockContainer PANDA_PLUSHIE = MakePlushie(PandaPlushieBlock::new);
	public static final BlockContainer BROWN_PANDA_PLUSHIE = MakePlushie(PandaPlushieBlock::new);
	//</editor-fold>
	//<editor-fold desc="Parrot">
	public static final BlockContainer BLUE_PARROT_PLUSHIE = MakePlushie(ParrotPlushieBlock::new);
	public static final BlockContainer GREEN_PARROT_PLUSHIE = MakePlushie(ParrotPlushieBlock::new);
	public static final BlockContainer GREY_PARROT_PLUSHIE = MakePlushie(ParrotPlushieBlock::new);
	public static final BlockContainer RED_PARROT_PLUSHIE = MakePlushie(ParrotPlushieBlock::new);
	public static final BlockContainer YELLOW_BLUE_PARROT_PLUSHIE = MakePlushie(ParrotPlushieBlock::new);
	public static final BlockContainer GOLDEN_PARROT_PLUSHIE = MakePlushie(ParrotPlushieBlock::new);
	//</editor-fold>
	//<editor-fold desc="Pigs">
	public static final BlockContainer PIG_PLUSHIE = MakePlushie(PigPlushieBlock::new);
	public static final BlockContainer SADDLED_PIG_PLUSHIE = MakePlushie(PigPlushieBlock::new);
	public static final BlockContainer MOTTLED_PIG_PLUSHIE = MakePlushie(PigPlushieBlock::new);
	public static final BlockContainer MUDDY_PIG_PLUSHIE = MakePlushie(PigPlushieBlock::new);
	public static final BlockContainer DRIED_MUDDY_PIG_PLUSHIE = MakePlushie(PigPlushieBlock::new);
	public static final BlockContainer PALE_PIG_PLUSHIE = MakePlushie(PigPlushieBlock::new);
	public static final BlockContainer PIEBALD_PIG_PLUSHIE = MakePlushie(PigPlushieBlock::new);
	public static final BlockContainer PINK_FOOTED_PIG_PLUSHIE = MakePlushie(PigPlushieBlock::new);
	public static final BlockContainer SOOTY_PIG_PLUSHIE = MakePlushie(PigPlushieBlock::new);
	public static final BlockContainer SPOTTED_PIG_PLUSHIE = MakePlushie(PigPlushieBlock::new);
	//</editor-fold>
	public static final BlockContainer POLAR_BEAR_PLUSHIE = MakePlushie(BearPlushieBlock::new);
	//<editor-fold desc="Sheep">
	public static final Map<DyeColor, BlockContainer> SHEEP_PLUSHIES = ColorUtil.Map(color -> MakePlushie(SheepPlushieBlock::new));
	public static final BlockContainer GOLDEN_SHEEP_PLUSHIE = MakePlushie(SheepPlushieBlock::new);
	public static final BlockContainer FLECKED_SHEEP_PLUSHIE = MakePlushie(SheepPlushieBlock::new);
	public static final BlockContainer FUZZY_SHEEP_PLUSHIE = MakePlushie(SheepPlushieBlock::new);
	public static final BlockContainer INKY_SHEEP_PLUSHIE = MakePlushie(SheepPlushieBlock::new);
	public static final BlockContainer LONG_NOSE_SHEEP_PLUSHIE = MakePlushie(SheepPlushieBlock::new);
	public static final BlockContainer PATCHED_SHEEP_PLUSHIE = MakePlushie(SheepPlushieBlock::new);
	public static final BlockContainer ROCKY_SHEEP_PLUSHIE = MakePlushie(SheepPlushieBlock::new);
	public static final BlockContainer RAINBOW_SHEEP_PLUSHIE = MakePlushie(SheepPlushieBlock::new);
	public static final BlockContainer MOSSY_SHEEP_PLUSHIE = MakePlushie(SheepPlushieBlock::new);
	//</editor-fold>
	//<editor-fold desc="Sheep (Sheared)">
	public static final BlockContainer SHEARED_SHEEP_PLUSHIE = MakePlushie(ShearedSheepPlushieBlock::new);
	public static final BlockContainer SHEARED_FLECKED_SHEEP_PLUSHIE = MakePlushie(ShearedSheepPlushieBlock::new);
	public static final BlockContainer SHEARED_FUZZY_SHEEP_PLUSHIE = MakePlushie(ShearedSheepPlushieBlock::new);
	public static final BlockContainer SHEARED_INKY_SHEEP_PLUSHIE = MakePlushie(ShearedSheepPlushieBlock::new);
	public static final BlockContainer SHEARED_LONG_NOSE_SHEEP_PLUSHIE = MakePlushie(ShearedSheepPlushieBlock::new);
	public static final BlockContainer SHEARED_PATCHED_SHEEP_PLUSHIE = MakePlushie(ShearedSheepPlushieBlock::new);
	public static final BlockContainer SHEARED_ROCKY_SHEEP_PLUSHIE = MakePlushie(ShearedSheepPlushieBlock::new);
	public static final BlockContainer SHEARED_RAINBOW_SHEEP_PLUSHIE = MakePlushie(ShearedSheepPlushieBlock::new);
	public static final BlockContainer SHEARED_MOSSY_SHEEP_PLUSHIE = MakePlushie(ShearedSheepPlushieBlock::new);
	//</editor-fold>
	//<editor-fold desc="Slime">
	public static final BlockContainer MAGMA_CUBE_PLUSHIE = MakePlushie(SlimePlushieBlock::new);
	public static final BlockContainer SLIME_PLUSHIE = MakePlushie(SlimePlushieBlock::new);
	public static final BlockContainer TROPICAL_SLIME_PLUSHIE = MakePlushie(SlimePlushieBlock::new);
	public static final BlockContainer PINK_SLIME_PLUSHIE = MakePlushie(SlimePlushieBlock::new);
	//</editor-fold>
	//<editor-fold desc="Snow Golems">
	public static final BlockContainer SNOW_GOLEM_PLUSHIE = MakePlushie(SnowGolemPlushieBlock::new);

	public static final BlockContainer WHITE_SNOW_GOLEM_PLUSHIE = MakePlushie(SnowGolemPlushieBlock::new);
	public static final BlockContainer ROTTEN_SNOW_GOLEM_PLUSHIE = MakePlushie(SnowGolemPlushieBlock::new);
	public static final BlockContainer MELON_GOLEM_PLUSHIE = MakePlushie(SnowGolemPlushieBlock::new);
	//</editor-fold>
	//<editor-fold desc="Snow Golems (Sheared)">
	public static final BlockContainer SHEARED_SNOW_GOLEM_PLUSHIE = MakePlushie(ShearedSnowGolemPlushieBlock::new);
	public static final BlockContainer SHEARED_MELON_GOLEM_PLUSHIE = MakePlushie(ShearedSnowGolemPlushieBlock::new);
	//</editor-fold>
	public static final BlockContainer TADPOLE_PLUSHIE = MakePlushie(TadpolePlushieBlock::new);
	//<editor-fold desc="Turtle">
	public static final BlockContainer TURTLE_PLUSHIE = MakePlushie(TurtlePlushieBlock::new);
	public static final BlockContainer RUBY_TURTLE_PLUSHIE = MakePlushie(TurtlePlushieBlock::new);
	//</editor-fold>
	public static final BlockContainer WARDEN_PLUSHIE = MakePlushie(WardenPlushieBlock::new);
	//<editor-fold desc="Wolf">
	public static final BlockContainer WOLF_PLUSHIE = MakePlushie(WolfPlushieBlock::new);
	public static final BlockContainer GRAY_WOLF_PLUSHIE = MakePlushie(WolfPlushieBlock::new);
	public static final BlockContainer BLACK_WOLF_PLUSHIE = MakePlushie(WolfPlushieBlock::new);
	public static final BlockContainer BROWN_WOLF_PLUSHIE = MakePlushie(WolfPlushieBlock::new);
	//</editor-fold>
	//</editor-fold>
	//<editor-fold desc="Poison">
	public static final Item POISON_VIAL = GeneratedItem(new BottledPoisonItem(400, 0, GlassBottledItemSettings()));
	public static final Item SPIDER_POISON_VIAL = GeneratedItem(new BottledPoisonItem(500, 1, GlassBottledItemSettings()));
	public static final Item PUFFERFISH_POISON_VIAL = GeneratedItem(new BottledPoisonItem(600, 2, GlassBottledItemSettings()));
	//</editor-fold>
	//<editor-fold desc="Pottery Sherds (1.20)">
	public static final Item ANGLER_POTTERY_SHERD = MakeGeneratedItem(ItemSettings(ItemGroup.MATERIALS));
	public static final Item ARCHER_POTTERY_SHERD = MakeGeneratedItem(ItemSettings(ItemGroup.MATERIALS));
	public static final Item ARMS_UP_POTTERY_SHERD = MakeGeneratedItem(ItemSettings(ItemGroup.MATERIALS));
	public static final Item BLADE_POTTERY_SHERD = MakeGeneratedItem(ItemSettings(ItemGroup.MATERIALS));
	public static final Item BREWER_POTTERY_SHERD = MakeGeneratedItem(ItemSettings(ItemGroup.MATERIALS));
	public static final Item BURN_POTTERY_SHERD = MakeGeneratedItem(ItemSettings(ItemGroup.MATERIALS));
	public static final Item DANGER_POTTERY_SHERD = MakeGeneratedItem(ItemSettings(ItemGroup.MATERIALS));
	public static final Item EXPLORER_POTTERY_SHERD = MakeGeneratedItem(ItemSettings(ItemGroup.MATERIALS));
	public static final Item FRIEND_POTTERY_SHERD = MakeGeneratedItem(ItemSettings(ItemGroup.MATERIALS));
	public static final Item HEART_POTTERY_SHERD = MakeGeneratedItem(ItemSettings(ItemGroup.MATERIALS));
	public static final Item HEARTBREAK_POTTERY_SHERD = MakeGeneratedItem(ItemSettings(ItemGroup.MATERIALS));
	public static final Item HOWL_POTTERY_SHERD = MakeGeneratedItem(ItemSettings(ItemGroup.MATERIALS));
	public static final Item MINER_POTTERY_SHERD = MakeGeneratedItem(ItemSettings(ItemGroup.MATERIALS));
	public static final Item MOURNER_POTTERY_SHERD = MakeGeneratedItem(ItemSettings(ItemGroup.MATERIALS));
	public static final Item PLENTY_POTTERY_SHERD = MakeGeneratedItem(ItemSettings(ItemGroup.MATERIALS));
	public static final Item PRIZE_POTTERY_SHERD = MakeGeneratedItem(ItemSettings(ItemGroup.MATERIALS));
	public static final Item SHEAF_POTTERY_SHERD = MakeGeneratedItem(ItemSettings(ItemGroup.MATERIALS));
	public static final Item SHELTER_POTTERY_SHERD = MakeGeneratedItem(ItemSettings(ItemGroup.MATERIALS));
	public static final Item SKULL_POTTERY_SHERD = MakeGeneratedItem(ItemSettings(ItemGroup.MATERIALS));
	public static final Item SNORT_POTTERY_SHERD = MakeGeneratedItem(ItemSettings(ItemGroup.MATERIALS));
	//</editor-fold>
	public static final Item POUCH = GeneratedItem(new PouchItem(ItemSettings().maxCount(16)));
	public static final EntityType<PowderKegEntity> POWDER_KEG_ENTITY = FabricEntityTypeBuilder.<PowderKegEntity>create(SpawnGroup.MISC, PowderKegEntity::new).dimensions(EntityDimensions.fixed(0.98F, 0.98F)).fireImmune().trackRangeChunks(10).trackedUpdateRate(10).build();
	//<editor-fold desc="Prismarine">
	public static final Item PRISMARINE_ROD = MakeHandheldItem();
	public static final TorchContainer PRISMARINE_TORCH = MakeTorch(15, BlockSoundGroup.STONE, ModParticleTypes.PRISMARINE_FLAME).torchModel();
	public static final TorchContainer PRISMARINE_SOUL_TORCH = MakeSoulTorch(BlockSoundGroup.STONE).torchModel(PRISMARINE_TORCH);
	public static final TorchContainer PRISMARINE_ENDER_TORCH = MakeEnderTorch(BlockSoundGroup.STONE).torchModel(PRISMARINE_TORCH);
	public static final TorchContainer UNDERWATER_PRISMARINE_TORCH = MakeUnderwaterTorch(BlockSoundGroup.STONE).torchModel(PRISMARINE_TORCH);
	public static final BlockContainer CHISELED_PRISMARINE_BRICKS = MakeBlock(Blocks.PRISMARINE).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer SMOOTH_CHISELED_PRISMARINE_BRICKS = MakeBlock(CHISELED_PRISMARINE_BRICKS).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer CUT_PRISMARINE_BRICKS = MakeBlock(Blocks.PRISMARINE_BRICKS).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer CUT_PRISMARINE_BRICK_STAIRS = MakeStairs(CUT_PRISMARINE_BRICKS).stairsModel(CUT_PRISMARINE_BRICKS).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer CUT_PRISMARINE_BRICK_SLAB = MakeSlab(CUT_PRISMARINE_BRICKS).slabModel(CUT_PRISMARINE_BRICKS).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer CHISELED_PRISMARINE = MakeBlock(Blocks.PRISMARINE_BRICKS).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer CHISELED_PRISMARINE_STAIRS = MakeStairs(CHISELED_PRISMARINE).stairsModel(CHISELED_PRISMARINE).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer CHISELED_PRISMARINE_SLAB = MakeSlab(CHISELED_PRISMARINE).slabModel(CHISELED_PRISMARINE).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer PRISMARINE_TILES = MakeBlock(Blocks.PRISMARINE_BRICKS).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer PRISMARINE_TILE_STAIRS = MakeStairs(PRISMARINE_TILES).stairsModel(PRISMARINE_TILES).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer PRISMARINE_TILE_SLAB = MakeSlab(PRISMARINE_TILES).slabModel(PRISMARINE_TILES).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer PRISMARINE_TILE_WALL = MakeWall(PRISMARINE_TILES).wallModel(PRISMARINE_TILES).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer DARK_PRISMARINE_WALL = MakeWall(Blocks.DARK_PRISMARINE).wallModel(Blocks.DARK_PRISMARINE).blockTag(BlockTags.PICKAXE_MINEABLE);
	//</editor-fold>
	//<editor-fold desc="Purpur">
	public static final BlockContainer PURPUR_WALL = MakeWall(Blocks.PURPUR_BLOCK).wallModel(Blocks.PURPUR_BLOCK).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer CRACKED_PURPUR_BLOCK = MakeBlock(Blocks.PURPUR_BLOCK).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer CHISELED_PURPUR = MakeBlock(Blocks.PURPUR_BLOCK).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer CHISELED_PURPUR_STAIRS = MakeStairs(CHISELED_PURPUR).stairsModel(CHISELED_PURPUR).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer CHISELED_PURPUR_SLAB = MakeSlab(CHISELED_PURPUR).slabModel(CHISELED_PURPUR).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer CHISELED_PURPUR_WALL = MakeWall(CHISELED_PURPUR).wallModel(CHISELED_PURPUR).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer PURPUR_MOSAIC = MakeBlock(Blocks.PURPUR_BLOCK).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer PURPUR_MOSAIC_STAIRS = MakeStairs(PURPUR_MOSAIC).stairsModel(PURPUR_MOSAIC).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer PURPUR_MOSAIC_SLAB = MakeSlab(PURPUR_MOSAIC).slabModel(PURPUR_MOSAIC).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer PURPUR_MOSAIC_WALL = MakeWall(PURPUR_MOSAIC).wallModel(PURPUR_MOSAIC).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer SMOOTH_PURPUR = MakeBlock(Blocks.PURPUR_BLOCK).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer SMOOTH_PURPUR_STAIRS = MakeStairs(SMOOTH_PURPUR).stairsModel(SMOOTH_PURPUR).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer SMOOTH_PURPUR_SLAB = MakeSlab(SMOOTH_PURPUR).slabModel(SMOOTH_PURPUR).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer SMOOTH_PURPUR_WALL = MakeWall(SMOOTH_PURPUR).wallModel(SMOOTH_PURPUR).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer PURPUR_BRICKS = MakeBlock(Blocks.PURPUR_BLOCK).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer PURPUR_BRICK_STAIRS = MakeStairs(PURPUR_BRICKS).stairsModel(PURPUR_BRICKS).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer PURPUR_BRICK_SLAB = MakeSlab(PURPUR_BRICKS).slabModel(PURPUR_BRICKS).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer PURPUR_BRICK_WALL = MakeWall(PURPUR_BRICKS).wallModel(PURPUR_BRICKS).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer CHISELED_PURPUR_BRICKS = MakeBlock(PURPUR_BRICKS).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer SMOOTH_CHISELED_PURPUR_BRICKS = MakeBlock(CHISELED_PURPUR_BRICKS).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer PURPUR_TILES = MakeBlock(PURPUR_BRICKS).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer PURPUR_TILE_STAIRS = MakeStairs(PURPUR_TILES).stairsModel(PURPUR_TILES).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer PURPUR_TILE_SLAB = MakeSlab(PURPUR_TILES).slabModel(PURPUR_TILES).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer PURPUR_TILE_WALL = MakeWall(PURPUR_TILES).wallModel(PURPUR_TILES).blockTag(BlockTags.PICKAXE_MINEABLE);
	//</editor-fold>
	//<editor-fold desc="Quartz">
	public static final BlockContainer QUARTZ_ORE = BuildBlock(new OreBlock(Block.Settings.copy(Blocks.EMERALD_ORE), UniformIntProvider.create(2, 5)), DropTable.Ore(Items.QUARTZ)).cubeAllModel().blockTag(ModBlockTags.QUARTZ_ORES).blockTag(ModBlockTags.SNAPS_GOAT_HORN).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer DEEPSLATE_QUARTZ_ORE = BuildBlock(new OreBlock(Block.Settings.copy(Blocks.DEEPSLATE_EMERALD_ORE), UniformIntProvider.create(2, 5)), DropTable.Ore(Items.QUARTZ)).cubeAllModel().blockTag(ModBlockTags.QUARTZ_ORES).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer QUARTZ_CRYSTAL_BLOCK = MakeBlock(Blocks.QUARTZ_BLOCK).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer QUARTZ_CRYSTAL_STAIRS = MakeStairs(QUARTZ_CRYSTAL_BLOCK).stairsModel(QUARTZ_CRYSTAL_BLOCK).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer QUARTZ_CRYSTAL_SLAB = MakeSlab(QUARTZ_CRYSTAL_BLOCK).slabModel(QUARTZ_CRYSTAL_BLOCK).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer QUARTZ_CRYSTAL_WALL = MakeWall(QUARTZ_CRYSTAL_BLOCK).wallModel(QUARTZ_CRYSTAL_BLOCK).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer SMOOTH_QUARTZ_WALL = BuildBlock(new ModWallBlock(Blocks.SMOOTH_QUARTZ)).blockTag(BlockTags.WALLS).itemTag(ItemTags.WALLS).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer QUARTZ_WALL = BuildBlock(new ModWallBlock(Blocks.QUARTZ_BLOCK)).blockTag(BlockTags.WALLS).itemTag(ItemTags.WALLS).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer QUARTZ_BRICK_STAIRS = MakeStairs(Blocks.QUARTZ_BRICKS).stairsModel(Blocks.QUARTZ_BRICKS).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer QUARTZ_BRICK_SLAB = MakeSlab(Blocks.QUARTZ_BRICKS).slabModel(Blocks.QUARTZ_BRICKS).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer QUARTZ_BRICK_WALL = MakeWall(Blocks.QUARTZ_BRICKS).wallModel(Blocks.QUARTZ_BRICKS).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer CHISELED_QUARTZ_BRICKS = MakeBlock(Blocks.QUARTZ_BRICKS).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer SMOOTH_CHISELED_QUARTZ_BRICKS = MakeBlock(CHISELED_QUARTZ_BRICKS).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final Item QUARTZ_AXE = MakeAxe(ModToolMaterials.QUARTZ);
	public static final Item QUARTZ_HOE = MakeHoe(ModToolMaterials.QUARTZ);
	public static final Item QUARTZ_PICKAXE = MakePickaxe(ModToolMaterials.QUARTZ);
	public static final Item QUARTZ_SHOVEL = MakeShovel(ModToolMaterials.QUARTZ);
	public static final Item QUARTZ_SWORD = MakeSword(ModToolMaterials.QUARTZ);
	public static final Item QUARTZ_KNIFE = MakeKnife(ModToolMaterials.QUARTZ);
	public static final Item QUARTZ_HAMMER = MakeHammer(ModToolMaterials.QUARTZ);
	public static final Item QUARTZ_HELMET = MakeHelmet(ModArmorMaterials.QUARTZ);
	public static final Item QUARTZ_CHESTPLATE = MakeChestplate(ModArmorMaterials.QUARTZ);
	public static final Item QUARTZ_LEGGINGS = MakeLeggings(ModArmorMaterials.QUARTZ);
	public static final Item QUARTZ_BOOTS = MakeBoots(ModArmorMaterials.QUARTZ);
	public static final Item QUARTZ_HORSE_ARMOR = MakeHorseArmor(ModArmorMaterials.QUARTZ);
	//</editor-fold>
	//TODO: Steal from containers
	//<editor-fold desc="Raccoon">
	public static final EntityType<RaccoonEntity> RACCOON_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, RaccoonEntity::new).dimensions(EntityDimensions.fixed(1F, 1F)).trackRangeChunks(8).build();
	public static final Item RACCOON_SPAWN_EGG = MakeSpawnEgg(RACCOON_ENTITY, 0x646464, 0x0B0B0B);
	//</editor-fold>
	//<editor-fold desc="Ragdoll">
	private static final Block RAGDOLL_BLOCK = new RagdollBlock(Block.Settings.copy(Blocks.PLAYER_HEAD).noCollision());
	private static final Item RAGDOLL_ITEM = new RagdollItem(RAGDOLL_BLOCK, ItemSettings());
	public static final BlockContainer RAGDOLL = new BlockContainer(RAGDOLL_BLOCK, RAGDOLL_ITEM).dropSelf();
	public static final BlockEntityType<RagdollBlockEntity> RAGDOLL_BLOCK_ENTITY = FabricBlockEntityTypeBuilder.create(RagdollBlockEntity::new, RAGDOLL_BLOCK).build();
	//</editor-fold>
	//<editor-fold desc="Raw Metal">
	public static final BlockContainer RAW_COPPER_SLAB = MakeSlab(Blocks.RAW_COPPER_BLOCK).slabModel(Blocks.RAW_COPPER_BLOCK).blockTag(BlockTags.NEEDS_STONE_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer RAW_GOLD_SLAB = MakeSlab(Blocks.RAW_GOLD_BLOCK).slabModel(Blocks.RAW_GOLD_BLOCK).blockTag(BlockTags.GUARDED_BY_PIGLINS).itemTag(ItemTags.PIGLIN_LOVED).blockTag(BlockTags.NEEDS_IRON_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer RAW_IRON_SLAB = MakeSlab(Blocks.RAW_IRON_BLOCK).slabModel(Blocks.RAW_IRON_BLOCK).blockTag(BlockTags.NEEDS_STONE_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	//</editor-fold>
	public static final Item RECOVERY_COMPASS = new Item(ItemSettings(ItemGroup.TOOLS)); //Minecraft 1.19
	//TODO: Stand on hind legs for defensive animation
	//<editor-fold desc="Red Panda">
	public static final EntityType<RedPandaEntity> RED_PANDA_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, RedPandaEntity::new).dimensions(EntityDimensions.fixed(1F, 1F)).trackRangeChunks(8).build();
	public static final Item RED_PANDA_SPAWN_EGG = MakeSpawnEgg(RED_PANDA_ENTITY, 0xC35330, 0x0B0B0B);
	//</editor-fold>
	//<editor-fold desc="Ruby">
	public static final Item RUBY = MakeGeneratedItem();
	public static final BlockContainer RUBY_ORE = BuildBlock(new OreBlock(Block.Settings.copy(Blocks.EMERALD_ORE), UniformIntProvider.create(3, 7)), DropTable.Ore(RUBY)).cubeAllModel().blockTag(ModBlockTags.RUBY_ORES).blockTag(ModBlockTags.SNAPS_GOAT_HORN).blockTag(BlockTags.NEEDS_IRON_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer DEEPSLATE_RUBY_ORE = BuildBlock(new OreBlock(Block.Settings.copy(Blocks.DEEPSLATE_EMERALD_ORE), UniformIntProvider.create(3, 7)), DropTable.Ore(RUBY)).cubeAllModel().blockTag(ModBlockTags.RUBY_ORES).blockTag(BlockTags.NEEDS_IRON_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer RUBY_BLOCK = MakeBlock(Block.Settings.copy(Blocks.EMERALD_BLOCK).mapColor(MapColor.RED)).cubeAllModel().blockTag(BlockTags.NEEDS_IRON_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer RUBY_STAIRS = MakeStairs(RUBY_BLOCK).stairsModel(RUBY_BLOCK).blockTag(BlockTags.NEEDS_IRON_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer RUBY_SLAB = MakeSlab(RUBY_BLOCK).slabModel(RUBY_BLOCK).blockTag(BlockTags.NEEDS_IRON_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer RUBY_WALL = MakeWall(RUBY_BLOCK).wallModel(RUBY_BLOCK).blockTag(BlockTags.NEEDS_IRON_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer RUBY_BRICKS = MakeBlock(RUBY_BLOCK).cubeAllModel().blockTag(BlockTags.NEEDS_IRON_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer RUBY_BRICK_STAIRS = MakeStairs(RUBY_BRICKS).stairsModel(RUBY_BRICKS).blockTag(BlockTags.NEEDS_IRON_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer RUBY_BRICK_SLAB = MakeSlab(RUBY_BRICKS).slabModel(RUBY_BRICKS).blockTag(BlockTags.NEEDS_IRON_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer RUBY_BRICK_WALL = MakeWall(RUBY_BRICKS).wallModel(RUBY_BRICKS).blockTag(BlockTags.NEEDS_IRON_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	//</editor-fold>
	//<editor-fold desc="Sapphire">
	public static final Item SAPPHIRE = MakeGeneratedItem();
	public static final BlockContainer SAPPHIRE_ORE = BuildBlock(new OreBlock(Block.Settings.copy(Blocks.EMERALD_ORE), UniformIntProvider.create(3, 7)), DropTable.Ore(SAPPHIRE)).cubeAllModel().blockTag(ModBlockTags.SAPPHIRE_ORES).blockTag(ModBlockTags.SNAPS_GOAT_HORN).blockTag(BlockTags.NEEDS_IRON_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer DEEPSLATE_SAPPHIRE_ORE = BuildBlock(new OreBlock(Block.Settings.copy(Blocks.DEEPSLATE_EMERALD_ORE), UniformIntProvider.create(3, 7)), DropTable.Ore(SAPPHIRE)).cubeAllModel().blockTag(ModBlockTags.SAPPHIRE_ORES).blockTag(BlockTags.NEEDS_IRON_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer SAPPHIRE_BLOCK = MakeBlock(Block.Settings.copy(Blocks.EMERALD_BLOCK).mapColor(MapColor.BLUE)).cubeAllModel().blockTag(BlockTags.NEEDS_IRON_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer SAPPHIRE_STAIRS = MakeStairs(SAPPHIRE_BLOCK).stairsModel(SAPPHIRE_BLOCK).blockTag(BlockTags.NEEDS_IRON_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer SAPPHIRE_SLAB = MakeSlab(SAPPHIRE_BLOCK).slabModel(SAPPHIRE_BLOCK).blockTag(BlockTags.NEEDS_IRON_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer SAPPHIRE_WALL = MakeWall(SAPPHIRE_BLOCK).wallModel(SAPPHIRE_BLOCK).blockTag(BlockTags.NEEDS_IRON_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer SAPPHIRE_BRICKS = MakeBlock(SAPPHIRE_BLOCK).cubeAllModel().blockTag(BlockTags.NEEDS_IRON_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer SAPPHIRE_BRICK_STAIRS = MakeStairs(SAPPHIRE_BRICKS).stairsModel(SAPPHIRE_BRICKS).blockTag(BlockTags.NEEDS_IRON_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer SAPPHIRE_BRICK_SLAB = MakeSlab(SAPPHIRE_BRICKS).slabModel(SAPPHIRE_BRICKS).blockTag(BlockTags.NEEDS_IRON_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer SAPPHIRE_BRICK_WALL = MakeWall(SAPPHIRE_BRICKS).wallModel(SAPPHIRE_BRICKS).blockTag(BlockTags.NEEDS_IRON_TOOL).blockTag(BlockTags.PICKAXE_MINEABLE);
	//</editor-fold>
	//<editor-fold desc="Sandstone">
	public static final BlockContainer SMOOTH_SANDSTONE_WALL = BuildBlock(new ModWallBlock(Blocks.SMOOTH_SANDSTONE)).blockTag(BlockTags.WALLS).itemTag(ItemTags.WALLS).blockTag(BlockTags.PICKAXE_MINEABLE); //TODO: Explicit wall model
	public static final BlockContainer SMOOTH_RED_SANDSTONE_WALL = BuildBlock(new ModWallBlock(Blocks.SMOOTH_RED_SANDSTONE)).blockTag(BlockTags.WALLS).itemTag(ItemTags.WALLS).blockTag(BlockTags.PICKAXE_MINEABLE); //TODO: Explicit wall model
	//</editor-fold>
	public static final BlockContainer SEED_BLOCK = MakeBlock(Block.Settings.of(Material.SOLID_ORGANIC, MapColor.DARK_GREEN).strength(0.8F).sounds(BlockSoundGroup.GRASS)).compostable(1f).cubeAllModel();
	//<editor-fold desc="Sculk">
	public static final BlockContainer SCULK = new BlockContainer(new SculkBlock(Block.Settings.of(Material.SCULK).strength(0.2f).sounds(ModBlockSoundGroups.SCULK)), ItemSettings(ItemGroup.DECORATIONS)).requireSilkTouch().cubeAllModel().blockTag(BlockTags.HOE_MINEABLE);
	public static final BlockContainer CALIBRATED_SCULK_SENSOR = new BlockContainer(new CalibratedSculkSensorBlock(Block.Settings.copy(Blocks.SCULK_SENSOR), 8), ItemSettings(ItemGroup.REDSTONE)).requireSilkTouch().blockTag(BlockTags.HOE_MINEABLE);
	public static final BlockEntityType<CalibratedSculkSensorBlockEntity> CALIBRATED_SCULK_SENSOR_ENTITY = FabricBlockEntityTypeBuilder.create(CalibratedSculkSensorBlockEntity::new, CALIBRATED_SCULK_SENSOR.asBlock()).build();
	public static final BlockContainer SCULK_VEIN = new BlockContainer(new SculkVeinBlock(Block.Settings.of(Material.SCULK).noCollision().strength(0.2f).sounds(ModBlockSoundGroups.SCULK_VEIN)), ItemSettings(ItemGroup.DECORATIONS)).requireSilkTouch().blockTag(BlockTags.INSIDE_STEP_SOUND_BLOCKS).blockTag(BlockTags.HOE_MINEABLE);
	public static final BlockContainer SCULK_CATALYST = new BlockContainer(new SculkCatalystBlock(Block.Settings.of(Material.SCULK).strength(3.0f, 3.0f).sounds(ModBlockSoundGroups.SCULK_CATALYST).luminance(LUMINANCE_6)), ItemSettings(ItemGroup.DECORATIONS)).requireSilkTouch().blockTag(BlockTags.HOE_MINEABLE);
	public static final BlockEntityType<SculkCatalystBlockEntity> SCULK_CATALYST_ENTITY = FabricBlockEntityTypeBuilder.create(SculkCatalystBlockEntity::new, SCULK_CATALYST.asBlock()).build();
	public static final BlockContainer SCULK_SHRIEKER = new BlockContainer(new SculkShriekerBlock(Block.Settings.of(Material.SCULK, MapColor.BLACK).strength(3.0f, 3.0f).sounds(ModBlockSoundGroups.SCULK_SHRIEKER)), ItemSettings(ItemGroup.DECORATIONS)).requireSilkTouch().blockTag(BlockTags.HOE_MINEABLE);
	public static final BlockEntityType<SculkShriekerBlockEntity> SCULK_SHRIEKER_ENTITY = FabricBlockEntityTypeBuilder.create(SculkShriekerBlockEntity::new, SCULK_SHRIEKER.asBlock()).build();
	//Extended
	public static final BlockContainer COBBLED_SCULK_STONE = MakeBlock(Block.Settings.of(Material.STONE, MapColor.BLACK).requiresTool().strength(3.0F, 6.0F).sounds(BlockSoundGroup.DEEPSLATE)).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer COBBLED_SCULK_STONE_STAIRS = MakeStairs(COBBLED_SCULK_STONE).stairsModel(COBBLED_SCULK_STONE).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer COBBLED_SCULK_STONE_SLAB = MakeSlab(COBBLED_SCULK_STONE).slabModel(COBBLED_SCULK_STONE).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer COBBLED_SCULK_STONE_WALL = MakeWall(COBBLED_SCULK_STONE).wallModel(COBBLED_SCULK_STONE).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer SCULK_STONE = BuildBlock(new Block(Block.Settings.of(Material.STONE, MapColor.BLACK).requiresTool().strength(2.5F, 6.0F).sounds(BlockSoundGroup.DEEPSLATE)), DropTable.SilkTouchOrElse(COBBLED_SCULK_STONE.asItem())).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer SCULK_STONE_STAIRS = MakeStairs(SCULK_STONE).stairsModel(SCULK_STONE).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer SCULK_STONE_SLAB = MakeSlab(SCULK_STONE).slabModel(SCULK_STONE).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer SCULK_STONE_WALL = MakeWall(SCULK_STONE).wallModel(SCULK_STONE).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer SCULK_STONE_BRICKS = MakeBlock(Block.Settings.copy(SCULK_STONE.asBlock()).sounds(BlockSoundGroup.DEEPSLATE_BRICKS)).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer SCULK_STONE_BRICK_STAIRS = MakeStairs(SCULK_STONE_BRICKS).stairsModel(SCULK_STONE_BRICKS).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer SCULK_STONE_BRICK_SLAB = MakeSlab(SCULK_STONE_BRICKS).slabModel(SCULK_STONE_BRICKS).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer SCULK_STONE_BRICK_WALL = MakeWall(SCULK_STONE_BRICKS).wallModel(SCULK_STONE_BRICKS).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer CHISELED_SCULK_STONE_BRICKS = MakeBlock(SCULK_STONE_BRICKS).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer SMOOTH_CHISELED_SCULK_STONE_BRICKS = MakeBlock(CHISELED_SCULK_STONE_BRICKS).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer SCULK_STONE_TILES = MakeBlock(Block.Settings.copy(SCULK_STONE.asBlock()).sounds(BlockSoundGroup.DEEPSLATE_TILES)).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer SCULK_STONE_TILE_STAIRS = MakeStairs(SCULK_STONE_TILES).stairsModel(SCULK_STONE_TILES).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer SCULK_STONE_TILE_SLAB = MakeSlab(SCULK_STONE_TILES).slabModel(SCULK_STONE_TILES).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer SCULK_STONE_TILE_WALL = MakeWall(SCULK_STONE_TILES).wallModel(SCULK_STONE_TILES).blockTag(BlockTags.PICKAXE_MINEABLE);
	//</editor-fold>
	//<editor-fold desc="Shale">
	public static final BlockContainer COBBLED_SHALE = MakeBlock(Block.Settings.of(Material.STONE).requiresTool().sounds(BlockSoundGroup.DEEPSLATE).mapColor(MapColor.BROWN).strength(2.5f, 6.0f)).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer SHALE = BuildBlock(new ModPillarBlock(Block.Settings.copy(COBBLED_SHALE.asBlock()).strength(2.0f, 6.0f)), DropTable.SilkTouchOrElse(COBBLED_SHALE.asItem())).blockTag(ModBlockTags.SCULK_REPLACEABLE).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer COBBLED_SHALE_STAIRS = MakeStairs(COBBLED_SHALE).stairsModel(COBBLED_SHALE).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer COBBLED_SHALE_SLAB = MakeSlab(COBBLED_SHALE).slabModel(COBBLED_SHALE).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer COBBLED_SHALE_WALL = MakeWall(COBBLED_SHALE).wallModel(COBBLED_SHALE).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer POLISHED_SHALE = MakeBlock(Block.Settings.copy(COBBLED_SHALE.asBlock()).sounds(BlockSoundGroup.POLISHED_DEEPSLATE)).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer POLISHED_SHALE_STAIRS = MakeStairs(POLISHED_SHALE).stairsModel(POLISHED_SHALE).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer POLISHED_SHALE_SLAB = MakeSlab(POLISHED_SHALE).slabModel(POLISHED_SHALE).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer POLISHED_SHALE_WALL = MakeWall(POLISHED_SHALE).wallModel(POLISHED_SHALE).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer SHALE_BRICKS = MakeBlock(Block.Settings.copy(SHALE.asBlock()).sounds(BlockSoundGroup.DEEPSLATE_BRICKS)).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer SHALE_BRICK_STAIRS = MakeStairs(SHALE_BRICKS).stairsModel(SHALE_BRICKS).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer SHALE_BRICK_SLAB = MakeSlab(SHALE_BRICKS).slabModel(SHALE_BRICKS).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer SHALE_BRICK_WALL = MakeWall(SHALE_BRICKS).wallModel(SHALE_BRICKS).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer SHALE_TILES = MakeBlock(Block.Settings.copy(SHALE.asBlock()).sounds(BlockSoundGroup.DEEPSLATE_TILES)).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer SHALE_TILE_STAIRS = MakeStairs(SHALE_TILES).stairsModel(SHALE_TILES).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer SHALE_TILE_SLAB = MakeSlab(SHALE_TILES).slabModel(SHALE_TILES).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer SHALE_TILE_WALL = MakeWall(SHALE_TILES).wallModel(SHALE_TILES).blockTag(BlockTags.PICKAXE_MINEABLE);
	//</editor-fold>
	//<editor-fold desc="Sculk Turf (After Shale because there is a shale sculk turf)">
	public static final BlockContainer ANDESITE_SCULK_TURF = MakeSculkTurf(Blocks.ANDESITE, Items.ANDESITE).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer BASALT_SCULK_TURF = MakeSculkTurf(Blocks.BASALT, Items.BASALT).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer BLACKSTONE_SCULK_TURF = MakeSculkTurf(Blocks.BLACKSTONE, Items.BLACKSTONE).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer CALCITE_SCULK_TURF = MakeSculkTurf(Blocks.CALCITE, Items.CALCITE).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer DEEPSLATE_SCULK_TURF = MakeSculkTurf(Blocks.DEEPSLATE, Items.COBBLED_DEEPSLATE).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer DIORITE_SCULK_TURF = MakeSculkTurf(Blocks.DIORITE, Items.DIORITE).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer DRIPSTONE_SCULK_TURF = MakeSculkTurf(Blocks.DRIPSTONE_BLOCK, Items.DRIPSTONE_BLOCK).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer END_SHALE_SCULK_TURF = MakeSculkTurf(END_SHALE.asBlock(), COBBLED_END_SHALE.asItem()).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer END_STONE_SCULK_TURF = MakeSculkTurf(Blocks.END_STONE, Items.END_STONE).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer END_ROCK_SCULK_TURF = MakeSculkTurf(END_ROCK.asBlock(), Items.END_STONE).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer END_ROCK_SHALE_SCULK_TURF = MakeSculkTurf(END_ROCK_SHALE.asBlock(), Items.END_STONE).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer END_SHALE_ROCK_SCULK_TURF = MakeSculkTurf(END_SHALE_ROCK.asBlock(), COBBLED_END_SHALE.asItem()).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer GRANITE_SCULK_TURF = MakeSculkTurf(Blocks.GRANITE, Items.GRANITE).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer NETHERRACK_SCULK_TURF = MakeSculkTurf(Blocks.NETHERRACK, Items.NETHERRACK).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer RED_SANDSTONE_SCULK_TURF = MakeSculkTurf(Blocks.RED_SANDSTONE, Items.RED_SANDSTONE).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer SANDSTONE_SCULK_TURF = MakeSculkTurf(Blocks.SANDSTONE, Items.SANDSTONE).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer SHALE_SCULK_TURF = MakeSculkTurf(SHALE.asBlock(), COBBLED_SHALE.asItem()).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer SMOOTH_BASALT_SCULK_TURF = MakeSculkTurf(Blocks.SMOOTH_BASALT, Items.SMOOTH_BASALT).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer STONE_SCULK_TURF = MakeSculkTurf(Blocks.STONE, Items.COBBLESTONE).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer TUFF_SCULK_TURF = MakeSculkTurf(Blocks.TUFF, Items.TUFF).blockTag(BlockTags.PICKAXE_MINEABLE);
	//</editor-fold>
	//<editor-fold desc="Shulker">
	public static final Item SHULKER_HELMET = MakeHelmet(ModArmorMaterials.SHULKER);
	public static final Item SHULKER_CHESTPLATE = MakeChestplate(ModArmorMaterials.SHULKER);
	public static final Item SHULKER_LEGGINGS = MakeLeggings(ModArmorMaterials.SHULKER);
	public static final Item SHULKER_BOOTS = MakeBoots(ModArmorMaterials.SHULKER);
	//</editor-fold>
	public static final BlockContainer EMPTY_LANTERN = BuildBlock(new LanternBlock(Block.Settings.of(Material.METAL).requiresTool().strength(3.5f).sounds(BlockSoundGroup.LANTERN).nonOpaque()));
	//<editor-fold desc="Slime">
	public static final Item BLUE_SLIME_BALL = MakeGeneratedItem();
	public static final Item PINK_SLIME_BALL = GeneratedItem(new PinkSlimeBallItem(ItemSettings()));
	public static final Item SLIME_BOTTLE = GeneratedItem(new BottledSlimeItem(GlassBottledItemSettings()));
	public static final Item BLUE_SLIME_BOTTLE = GeneratedItem(new BottledSlimeItem(GlassBottledItemSettings()));
	public static final Item PINK_SLIME_BOTTLE = GeneratedItem(new BottledSlimeItem(GlassBottledItemSettings()));
	public static final BlockContainer BLUE_SLIME_BLOCK = BuildBlock(new SlimeBlock(Block.Settings.copy(Blocks.SLIME_BLOCK).mapColor(MapColor.WATER_BLUE))).blockTag(ModBlockTags.SLIME_BLOCKS);
	public static final BlockContainer PINK_SLIME_BLOCK = BuildBlock(new SlimeBlock(Block.Settings.copy(Blocks.SLIME_BLOCK).mapColor(MapColor.MAGENTA))).blockTag(ModBlockTags.SLIME_BLOCKS);
	public static final EntityType<TropicalSlimeEntity> TROPICAL_SLIME_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, TropicalSlimeEntity::new).dimensions(EntityDimensions.changing(2.04f, 2.04f)).trackRangeChunks(10).build();
	public static final Item TROPICAL_SLIME_SPAWN_EGG = MakeSpawnEgg(TROPICAL_SLIME_ENTITY, 0x8FD3FF, 0x345C7A);
	public static final EntityType<PinkSlimeBallEntity> PINK_SLIME_BALL_ENTITY = FabricEntityTypeBuilder.<PinkSlimeBallEntity>create(SpawnGroup.MISC, PinkSlimeBallEntity::new).dimensions(EntityDimensions.fixed(0.25f, 0.25f)).trackRangeChunks(4).trackedUpdateRate(10).build();
	public static final EntityType<PinkSlimeEntity> PINK_SLIME_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, PinkSlimeEntity::new).dimensions(EntityDimensions.changing(2.04f, 2.04f)).trackRangeChunks(10).build();
	public static final Item PINK_SLIME_SPAWN_EGG = MakeSpawnEgg(PINK_SLIME_ENTITY, 0xE0A3EE, 0xB85ECE);
	//Other Mobs
	public static final Item SLIME_FEATHER = MakeGeneratedItem();
	public static final EntityType<SlimeChickenEntity> SLIME_CHICKEN_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, SlimeChickenEntity::new).dimensions(EntityDimensions.fixed(0.4F, 0.7F)).trackRangeChunks(10).build();
	public static final Item SLIME_CHICKEN_SPAWN_EGG = MakeSpawnEgg(SLIME_CHICKEN_ENTITY, 5349438, 0xFF0000);

	public static final EntityType<SlimeCowEntity> SLIME_COW_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, SlimeCowEntity::new).dimensions(EntityDimensions.fixed(0.9f, 1.4f)).trackRangeChunks(10).build();
	public static final Item SLIME_COW_SPAWN_EGG = MakeSpawnEgg(SLIME_COW_ENTITY, 5349438, 0x3F3024);

	public static final EntityType<SlimeHorseEntity> SLIME_HORSE_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, SlimeHorseEntity::new).dimensions(EntityDimensions.fixed(1.3964844f, 1.6f)).trackRangeChunks(10).build();
	public static final Item SLIME_HORSE_SPAWN_EGG = MakeSpawnEgg(SLIME_HORSE_ENTITY, 5349438, 0xCFC700);

	public static final EntityType<SlimeSpiderEntity> SLIME_SPIDER_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, SlimeSpiderEntity::new).dimensions(EntityDimensions.fixed(1.4f, 0.9f)).trackRangeChunks(8).build();
	public static final Item SLIME_SPIDER_SPAWN_EGG = MakeSpawnEgg(SLIME_SPIDER_ENTITY, 5349438, 11013646);
	//Lanterns
	public static final BlockContainer MAGMA_CUBE_LANTERN = BuildBlock(new SlimeLanternBlock(SlimeLanternSettings(LUMINANCE_15), EntityType.MAGMA_CUBE), DropTable.Drops(EMPTY_LANTERN)).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer SLIME_LANTERN = BuildBlock(new SlimeLanternBlock(SlimeLanternSettings(LUMINANCE_13), EntityType.SLIME), DropTable.Drops(EMPTY_LANTERN)).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer TROPICAL_SLIME_LANTERN = BuildBlock(new SlimeLanternBlock(SlimeLanternSettings(LUMINANCE_13), TROPICAL_SLIME_ENTITY), DropTable.Drops(EMPTY_LANTERN)).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer PINK_SLIME_LANTERN = BuildBlock(new SlimeLanternBlock(SlimeLanternSettings(LUMINANCE_13), PINK_SLIME_ENTITY), DropTable.Drops(EMPTY_LANTERN)).blockTag(BlockTags.PICKAXE_MINEABLE);
	//</editor-fold>
	//<editor-fold desc="Smithing Templates (1.20)">
	public static final Item NETHERITE_UPGRADE_SMITHING_TEMPLATE = GeneratedItem(SmithingTemplateItem.createNetheriteUpgrade());
	//</editor-fold>
	//<editor-fold desc="Sniffer">
	public static final EntityType<SnifferEntity> SNIFFER_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, SnifferEntity::new).dimensions(EntityDimensions.fixed(1.9f, 1.75f)).trackRangeChunks(10).build();
	public static final Item SNIFFER_SPAWN_EGG = MakeSpawnEgg(SNIFFER_ENTITY, 9840944, 5085536, ItemSettings(ItemGroup.MISC));
	public static final BlockContainer SNIFFER_EGG = BuildBlock(new SnifferEggBlock(Block.Settings.of(new Material.Builder(MapColor.DARK_GREEN).allowsMovement().build(), MapColor.RED).strength(0.5f).sounds(BlockSoundGroup.METAL).nonOpaque()), ItemSettings(ItemGroup.MISC)).generatedItemModel();
	//</editor-fold>
	//<editor-fold desc="Soul & Netherrack Fire">
	public static final BlockContainer SOUL_CANDLE = MakeCandle(MapColor.BROWN, 2.5);
	public static final Block SOUL_CANDLE_CAKE = new ModCandleCakeBlock(2, () -> Blocks.CAKE, () -> Items.CAKE) {
		@Override public boolean isSoulCandle() { return true; }
	}.drops(SOUL_CANDLE.asBlock());
	public static final BlockContainer NETHERRACK_CANDLE = MakeCandle(MapColor.DARK_RED, 2.75);
	public static final Block NETHERRACK_CANDLE_CAKE = new ModCandleCakeBlock(3, () -> Blocks.CAKE, () -> Items.CAKE) {
		@Override public boolean isNetherrackCandle() { return true; }
	}.drops(NETHERRACK_CANDLE.asBlock());
	//</editor-fold>
	//<editor-fold desc="Spruce">
	public static final Item SPRUCE_CHEST_BOAT = MakeChestBoat(BoatEntity.Type.SPRUCE, BoatSettings(ItemGroup.TRANSPORTATION));
	public static final WallBlockContainer SPRUCE_HANGING_SIGN = MakeHangingSign(SignType.SPRUCE, Blocks.STRIPPED_SPRUCE_LOG, SignItemSettings(ItemGroup.DECORATIONS)).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer SPRUCE_IRON_HANGING_SIGN = MakeHangingSign(MakeSignType("spruce_iron"), Blocks.STRIPPED_SPRUCE_LOG).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer SPRUCE_GOLD_HANGING_SIGN = MakeHangingSign(MakeSignType("spruce_gold"), Blocks.STRIPPED_SPRUCE_LOG).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer SPRUCE_COPPER_HANGING_SIGN = MakeHangingSign(MakeSignType("spruce_copper"), Blocks.STRIPPED_SPRUCE_LOG).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer SPRUCE_EXPOSED_COPPER_HANGING_SIGN = MakeHangingSign(MakeSignType("spruce_exposed_copper"), Blocks.STRIPPED_SPRUCE_LOG).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer SPRUCE_WEATHERED_COPPER_HANGING_SIGN = MakeHangingSign(MakeSignType("spruce_weathered_copper"), Blocks.STRIPPED_SPRUCE_LOG).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer SPRUCE_OXIDIZED_COPPER_HANGING_SIGN = MakeHangingSign(MakeSignType("spruce_oxidized_copper"), Blocks.STRIPPED_SPRUCE_LOG).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer SPRUCE_NETHERITE_HANGING_SIGN = MakeHangingSign(MakeSignType("spruce_netherite"), Blocks.STRIPPED_SPRUCE_LOG).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer SPRUCE_BEEHIVE = MakeBeehive(MapColor.SPRUCE_BROWN).flammable(5, 20).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer SPRUCE_BOOKSHELF = MakeBookshelf(Blocks.SPRUCE_PLANKS).flammable(30, 20).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer SPRUCE_CHISELED_BOOKSHELF = MakeChiseledBookshelf(MapColor.SPRUCE_BROWN).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer SPRUCE_CRAFTING_TABLE = MakeCraftingTable(Blocks.SPRUCE_PLANKS).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer SPRUCE_LADDER = MakeLadder().blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer SPRUCE_WOODCUTTER = MakeWoodcutter(Blocks.SPRUCE_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer SPRUCE_LECTERN = MakeLectern(Blocks.SPRUCE_PLANKS).flammable(30, 20).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer SPRUCE_POWDER_KEG = MakePowderKeg(Blocks.BARREL).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer SPRUCE_LOG_SLAB = MakeLogSlab(Blocks.SPRUCE_LOG).flammable(5, 5).fuel(150).itemTag(ModItemTags.CHARRABLE_LOG_SLABS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer STRIPPED_SPRUCE_LOG_SLAB = MakeLogSlab(Blocks.STRIPPED_SPRUCE_LOG).flammable(5, 5).fuel(150).itemTag(ModItemTags.CHARRABLE_STRIPPED_LOG_SLABS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer SPRUCE_WOOD_SLAB = MakeBarkSlab(Blocks.SPRUCE_WOOD, Blocks.SPRUCE_LOG).flammable(5, 5).fuel(150).itemTag(ModItemTags.CHARRABLE_WOOD_SLABS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer STRIPPED_SPRUCE_WOOD_SLAB = MakeBarkSlab(Blocks.STRIPPED_SPRUCE_WOOD, Blocks.STRIPPED_SPRUCE_LOG).flammable(5, 5).fuel(150).itemTag(ModItemTags.CHARRABLE_STRIPPED_WOOD_SLABS).blockTag(BlockTags.AXE_MINEABLE);
	//Minecraft Dungeons
	public static final BlockContainer WHITE_SPRUCE_LEAVES = MakeLeaves(Block.Settings.copy(Blocks.SPRUCE_LEAVES).mapColor(MapColor.LICHEN_GREEN));
	//Misc
	public static final Item SPRUCE_HAMMER = MakeHammer(ToolMaterials.WOOD, 7, -3.2F);
	//Torches
	public static final TorchContainer SPRUCE_TORCH = MakeTorch(BlockSoundGroup.WOOD).torchModel();
	public static final TorchContainer SPRUCE_SOUL_TORCH = MakeSoulTorch(BlockSoundGroup.WOOD).torchModel(SPRUCE_TORCH);
	public static final TorchContainer SPRUCE_ENDER_TORCH = MakeEnderTorch(BlockSoundGroup.WOOD).torchModel(SPRUCE_TORCH);
	public static final TorchContainer UNDERWATER_SPRUCE_TORCH = MakeUnderwaterTorch(BlockSoundGroup.WOOD).torchModel(SPRUCE_TORCH);
	//Campfires
	public static final BlockContainer SPRUCE_CAMPFIRE = MakeCampfire(MapColor.BROWN).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer SPRUCE_SOUL_CAMPFIRE = MakeSoulCampfire(SPRUCE_CAMPFIRE).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer SPRUCE_ENDER_CAMPFIRE = MakeEnderCampfire(SPRUCE_CAMPFIRE).blockTag(BlockTags.AXE_MINEABLE);
	//</editor-fold>
	public static final BlockContainer CREEPER_ANATOMY_STATUE = BuildBlock(new CreeperAnatomyStatueBlock(Block.Settings.copy(Blocks.SPRUCE_PLANKS)), ItemSettings().group(PLUSHIE_GROUP)).blockTag(BlockTags.AXE_MINEABLE);
	//<editor-fold desc="Stone">
	public static final BlockContainer MOSSY_CHISELED_STONE_BRICKS = MakeBlock(Blocks.MOSSY_STONE_BRICKS).cubeAllModel().blockTag(BlockTags.STONE_BRICKS).itemTag(ItemTags.STONE_BRICKS).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer STONE_TILES = MakeBlock(Blocks.STONE_BRICKS).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer STONE_TILE_STAIRS = MakeStairs(STONE_TILES).stairsModel(STONE_TILES).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer STONE_TILE_SLAB = MakeSlab(STONE_TILES).slabModel(STONE_TILES).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer STONE_TILE_WALL = MakeWall(STONE_TILES).wallModel(STONE_TILES).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer SMOOTH_STONE_STAIRS = MakeStairs(Blocks.SMOOTH_STONE).stairsModel(Blocks.SMOOTH_STONE).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final Item STONE_HAMMER = MakeHammer(ToolMaterials.STONE, 8, -3.2F);
	//</editor-fold>
	//<editor-fold desc="Studded Leather">
	public static final Item STUDDED_LEATHER_HELMET = MakeHelmet(ModArmorMaterials.STUDDED_LEATHER);
	public static final Item STUDDED_LEATHER_CHESTPLATE = MakeChestplate(ModArmorMaterials.STUDDED_LEATHER);
	public static final Item STUDDED_LEATHER_LEGGINGS = MakeLeggings(ModArmorMaterials.STUDDED_LEATHER);
	public static final Item STUDDED_LEATHER_BOOTS = MakeBoots(ModArmorMaterials.STUDDED_LEATHER);
	public static final Item STUDDED_LEATHER_HORSE_ARMOR = MakeHorseArmor(ModArmorMaterials.STUDDED_LEATHER);
	//</editor-fold>
	//<editor-fold desc="Sugar">
	public static final BlockContainer SUGAR_BLOCK = BuildBlock(new SugarBlock(Block.Settings.of(Material.AGGREGATE, MapColor.WHITE).strength(0.6F).sounds(BlockSoundGroup.SAND))).cubeAllModel().blockTag(BlockTags.SHOVEL_MINEABLE);
	public static final BlockContainer CARAMEL_BLOCK = MakeBlock(Block.Settings.of(Material.STONE, MapColor.ORANGE).strength(0.8F).sounds(ModBlockSoundGroups.MUD)).cubeAllModel().blockTag(BlockTags.SHOVEL_MINEABLE);
	//</editor-fold>
	//<editor-fold desc="Sugar Cane">
	public static final BlockContainer SUGAR_CANE_BLOCK = MakeLog(MapColor.LICHEN_GREEN, BlockSoundGroup.WOOD).flammable(5, 5).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer SUGAR_CANE_BLOCK_SLAB = MakeSlab(LogSettings(MapColor.LICHEN_GREEN)).flammable(5, 5).fuel(150).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer SUGAR_CANE_ROW = BuildBlock(new RowBlock(Block.Settings.of(Material.WOOD, MapColor.LICHEN_GREEN).strength(1.0F).sounds(BlockSoundGroup.WOOD))).blockTag(ModBlockTags.ROWS).itemTag(ModItemTags.ROWS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer SUGAR_CANE_BALE = BuildBlock(new HayBlock(Block.Settings.copy(Blocks.HAY_BLOCK).mapColor(MapColor.LICHEN_GREEN))).blockTag(BlockTags.HOE_MINEABLE);
	//Wood
	public static final BlockContainer SUGAR_CANE_PLANKS = MakePlanks(MapColor.LICHEN_GREEN).flammable(5, 20).fuel(300).cubeAllModel().blockTag(BlockTags.PLANKS).itemTag(ItemTags.PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer SUGAR_CANE_STAIRS = MakeWoodStairs(SUGAR_CANE_PLANKS).flammable(5, 20).fuel(300).stairsModel(SUGAR_CANE_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer SUGAR_CANE_SLAB = MakeSlab(SUGAR_CANE_PLANKS).flammable(5, 20).fuel(150).slabModel(SUGAR_CANE_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer SUGAR_CANE_FENCE = MakeWoodFence(SUGAR_CANE_PLANKS).flammable(5, 20).fuel(300).bambooFenceModel().blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer SUGAR_CANE_FENCE_GATE = MakeWoodFenceGate(SUGAR_CANE_PLANKS).flammable(5, 20).fuel(300).bambooFenceGateModel().blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer SUGAR_CANE_DOOR = MakeWoodDoor(SUGAR_CANE_PLANKS).fuel(200).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer SUGAR_CANE_TRAPDOOR = MakeWoodTrapdoor(SUGAR_CANE_PLANKS).fuel(300).trapdoorModel(true).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer SUGAR_CANE_PRESSURE_PLATE = MakeWoodPressurePlate(SUGAR_CANE_PLANKS).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer SUGAR_CANE_BUTTON = MakeWoodButton().fuel(100).buttonModel(SUGAR_CANE_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final SignContainer SUGAR_CANE_SIGN = MakeSign("sugar_cane", SUGAR_CANE_PLANKS, SUGAR_CANE_PLANKS).fuel(200).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer SUGAR_CANE_IRON_HANGING_SIGN = MakeHangingSign(MakeSignType("sugar_cane_iron"), SUGAR_CANE_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer SUGAR_CANE_GOLD_HANGING_SIGN = MakeHangingSign(MakeSignType("sugar_cane_gold"), SUGAR_CANE_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer SUGAR_CANE_COPPER_HANGING_SIGN = MakeHangingSign(MakeSignType("sugar_cane_copper"), SUGAR_CANE_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer SUGAR_CANE_EXPOSED_COPPER_HANGING_SIGN = MakeHangingSign(MakeSignType("sugar_cane_exposed_copper"), SUGAR_CANE_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer SUGAR_CANE_WEATHERED_COPPER_HANGING_SIGN = MakeHangingSign(MakeSignType("sugar_cane_weathered_copper"), SUGAR_CANE_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer SUGAR_CANE_OXIDIZED_COPPER_HANGING_SIGN = MakeHangingSign(MakeSignType("sugar_cane_oxidized_copper"), SUGAR_CANE_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer SUGAR_CANE_NETHERITE_HANGING_SIGN = MakeHangingSign(MakeSignType("sugar_cane_netherite"), SUGAR_CANE_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BoatContainer SUGAR_CANE_BOAT = MakeBoat("sugar_cane", SUGAR_CANE_PLANKS).fuel(1200);
	//Extended
	public static final BlockContainer SUGAR_CANE_BEEHIVE = MakeBeehive(MapColor.LICHEN_GREEN).flammable(5, 20).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer SUGAR_CANE_BOOKSHELF = MakeBookshelf(SUGAR_CANE_PLANKS).flammable(30, 20).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer SUGAR_CANE_CHISELED_BOOKSHELF = MakeChiseledBookshelf(MapColor.LICHEN_GREEN).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer SUGAR_CANE_CRAFTING_TABLE = MakeCraftingTable(SUGAR_CANE_PLANKS).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer SUGAR_CANE_LADDER = MakeLadder().blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer SUGAR_CANE_WOODCUTTER = MakeWoodcutter(SUGAR_CANE_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer SUGAR_CANE_BARREL = MakeBarrel(MapColor.LICHEN_GREEN).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer SUGAR_CANE_LECTERN = MakeLectern(SUGAR_CANE_PLANKS).flammable(30, 20).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer SUGAR_CANE_POWDER_KEG = MakePowderKeg(SUGAR_CANE_BARREL).blockTag(BlockTags.AXE_MINEABLE);
	//Mosaic
	public static final BlockContainer SUGAR_CANE_MOSAIC = MakeBlock(SUGAR_CANE_PLANKS).flammable(5, 20).dropSelf().cubeAllModel().blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer SUGAR_CANE_MOSAIC_STAIRS = MakeStairs(SUGAR_CANE_MOSAIC).flammable(5, 20).fuel(300).stairsModel(SUGAR_CANE_MOSAIC).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer SUGAR_CANE_MOSAIC_SLAB = MakeSlab(SUGAR_CANE_MOSAIC).flammable(5, 20).fuel(150).slabModel(SUGAR_CANE_MOSAIC).blockTag(BlockTags.AXE_MINEABLE);
	//Misc
	public static final Item SUGAR_CANE_HAMMER = MakeHammer(ToolMaterials.WOOD, 7, -3.2F);
	//Torches
	public static final TorchContainer SUGAR_CANE_TORCH = MakeTorch(BlockSoundGroup.WOOD).torchModel();
	public static final TorchContainer SUGAR_CANE_SOUL_TORCH = MakeSoulTorch(BlockSoundGroup.WOOD).torchModel(SUGAR_CANE_TORCH);
	public static final TorchContainer SUGAR_CANE_ENDER_TORCH = MakeEnderTorch(BlockSoundGroup.WOOD).torchModel(SUGAR_CANE_TORCH);
	public static final TorchContainer UNDERWATER_SUGAR_CANE_TORCH = MakeUnderwaterTorch(BlockSoundGroup.WOOD).torchModel(SUGAR_CANE_TORCH);
	//Campfires
	public static final BlockContainer SUGAR_CANE_CAMPFIRE = MakeCampfire(MapColor.LICHEN_GREEN).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer SUGAR_CANE_SOUL_CAMPFIRE = MakeSoulCampfire(SUGAR_CANE_CAMPFIRE).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer SUGAR_CANE_ENDER_CAMPFIRE = MakeEnderCampfire(SUGAR_CANE_CAMPFIRE).blockTag(BlockTags.AXE_MINEABLE);
	//</editor-fold>
	public static final BlockContainer SWEET_BERRY_LEAVES = new BlockContainer(new BerryLeavesBlock(Items.SWEET_BERRIES, Blocks.SPRUCE_LEAVES));
	//<editor-fold desc="Terracotta">
	public static final BlockContainer TERRACOTTA_STAIRS = MakeStairs(Blocks.TERRACOTTA).stairsModel(Blocks.TERRACOTTA).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer TERRACOTTA_SLAB = MakeSlab(Blocks.TERRACOTTA).slabModel(Blocks.TERRACOTTA).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer TERRACOTTA_WALL = MakeWall(Blocks.TERRACOTTA).wallModel(Blocks.TERRACOTTA).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final Map<DyeColor, BlockContainer> TERRACOTTA_STAIRS_MAP = ColorUtil.Map(color -> { Block block = ColorUtil.GetTerracottaBlock(color); return MakeStairs(block).stairsModel(block).blockTag(BlockTags.PICKAXE_MINEABLE); });
	public static final Map<DyeColor, BlockContainer> TERRACOTTA_SLABS = ColorUtil.Map(color -> { Block block = ColorUtil.GetTerracottaBlock(color); return MakeSlab(block).slabModel(block).blockTag(BlockTags.PICKAXE_MINEABLE); });
	public static final Map<DyeColor, BlockContainer> TERRACOTTA_WALLS = ColorUtil.Map(color -> { Block block = ColorUtil.GetTerracottaBlock(color); return MakeWall(block).wallModel(block).blockTag(BlockTags.PICKAXE_MINEABLE); });
	//Glazed
	public static final BlockContainer GLAZED_TERRACOTTA = BuildBlock(new GlazedTerracottaBlock(Block.Settings.of(Material.STONE, MapColor.ORANGE).requiresTool().strength(1.4f))).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer GLAZED_TERRACOTTA_SLAB = BuildSlab(new HorizontalFacingSlabBlock(GLAZED_TERRACOTTA)).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final Map<DyeColor, BlockContainer> GLAZED_TERRACOTTA_SLABS = ColorUtil.Map(color -> BuildSlab(new HorizontalFacingSlabBlock(ColorUtil.GetGlazedTerracottaBlock(color))).blockTag(BlockTags.PICKAXE_MINEABLE));
	//</editor-fold>
	//<editor-fold desc="Throwable Tomatoes">
	public static final Item THROWABLE_TOMATO_ITEM = GeneratedItem(new ThrowableTomatoItem(ItemSettings().maxCount(16)));
	public static final EntityType<ThrownTomatoEntity> THROWABLE_TOMATO_ENTITY = FabricEntityTypeBuilder.<ThrownTomatoEntity>create(SpawnGroup.MISC, ThrownTomatoEntity::new).dimensions(EntityDimensions.fixed(0.25F, 0.25F)).trackRangeChunks(4).trackedUpdateRate(10).build();
	//</editor-fold>
	public static final BlockContainer TIKI_TORCH = BuildBlock(new ThickTorchBlock(Block.Settings.of(Material.DECORATION).breakInstantly().nonOpaque().luminance(createLightLevelFromLitBlockState(15)).sounds(BlockSoundGroup.WOOD), ParticleTypes.FLAME)).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer TIKI_SOUL_TORCH = BuildBlock(new ThickTorchBlock(Block.Settings.of(Material.DECORATION).breakInstantly().nonOpaque().luminance(createLightLevelFromLitBlockState(10)).sounds(BlockSoundGroup.WOOD), ParticleTypes.SOUL_FIRE_FLAME)).blockTag(BlockTags.AXE_MINEABLE).blockTag(BlockTags.PIGLIN_REPELLENTS).itemTag(ItemTags.PIGLIN_REPELLENTS);
	public static final BlockContainer TIKI_ENDER_TORCH = BuildBlock(new ThickTorchBlock(Block.Settings.of(Material.DECORATION).breakInstantly().nonOpaque().luminance(createLightLevelFromLitBlockState(12)).sounds(BlockSoundGroup.WOOD), ModParticleTypes.ENDER_FIRE_FLAME)).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer TIKI_TORCH_POST = BuildBlock(new PostBlock(Block.Settings.copy(Blocks.JUNGLE_FENCE))).flammable(5, 20).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	//<editor-fold desc="Torchflower">
	private static final Block TORCHFLOWER_CROP_BLOCK = new TorchflowerBlock(Block.Settings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.CROP));
	public static final BlockContainer TORCHFLOWER_CROP = new BlockContainer(TORCHFLOWER_CROP_BLOCK, new AliasedBlockItem(TORCHFLOWER_CROP_BLOCK, ItemSettings(ItemGroup.DECORATIONS))).compostable(0.3f).generatedItemModel().blockTag(BlockTags.CROPS).itemTag(ModItemTags.VILLAGER_PLANTABLE_SEEDS).itemTag(ModItemTags.SEEDS).itemTag(ModItemTags.SNIFFER_FOOD);
	public static final FlowerContainer TORCHFLOWER = MakeFlower(StatusEffects.NIGHT_VISION, 5, ItemSettings(ItemGroup.DECORATIONS));
	//</editor-fold>
	//TODO: Trial Chambers
	public static final Item TRIAL_KEY = MakeGeneratedItem(ItemSettings(ItemGroup.MISC));
	//TODO: Armor Trims (1.20)
	//<editor-fold desc="Trimming Table (Mod solution to 1.20 smithing overhaul)">
	public static final RecipeType<TrimmingRecipe> TRIMMING_RECIPE_TYPE = RegisterRecipeType("trimming");
	public static final RecipeSerializer<TrimmingRecipe> TRIMMING_RECIPE_SERIALIZER = new TrimmingRecipe.Serializer();
	public static final BlockContainer TRIMMING_TABLE = BuildBlock(new TrimmingTableBlock(Blocks.SMITHING_TABLE), new Item.Settings()).blockTag(BlockTags.AXE_MINEABLE); //TODO: Hide from creative menu until it works
	public static final ScreenHandlerType<TrimmingScreenHandler> TRIMMING_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(ModId.ID("trimming"), TrimmingScreenHandler::new);
	//</editor-fold>
	//<editor-fold desc="Tuff">
	public static final BlockContainer TUFF_STAIRS = MakeStairs(Blocks.TUFF, ItemSettings(ItemGroup.BUILDING_BLOCKS)).stairsModel(Blocks.TUFF).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer TUFF_SLAB = MakeSlab(Blocks.TUFF, ItemSettings(ItemGroup.BUILDING_BLOCKS)).slabModel(Blocks.TUFF).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer TUFF_WALL = MakeWall(Blocks.TUFF, ItemSettings(ItemGroup.BUILDING_BLOCKS)).wallModel(Blocks.TUFF).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer POLISHED_TUFF = MakeBlock(Blocks.TUFF, ItemSettings(ItemGroup.BUILDING_BLOCKS)).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer POLISHED_TUFF_STAIRS = MakeStairs(POLISHED_TUFF, ItemSettings(ItemGroup.BUILDING_BLOCKS)).stairsModel(POLISHED_TUFF).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer POLISHED_TUFF_SLAB = MakeSlab(POLISHED_TUFF, ItemSettings(ItemGroup.BUILDING_BLOCKS)).slabModel(POLISHED_TUFF).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer POLISHED_TUFF_WALL = MakeWall(POLISHED_TUFF, ItemSettings(ItemGroup.BUILDING_BLOCKS)).wallModel(POLISHED_TUFF).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer TUFF_BRICKS = MakeBlock(Blocks.TUFF, ItemSettings(ItemGroup.BUILDING_BLOCKS)).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer TUFF_BRICK_STAIRS = MakeStairs(TUFF_BRICKS, ItemSettings(ItemGroup.BUILDING_BLOCKS)).stairsModel(TUFF_BRICKS).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer TUFF_BRICK_SLAB = MakeSlab(TUFF_BRICKS, ItemSettings(ItemGroup.BUILDING_BLOCKS)).slabModel(TUFF_BRICKS).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer TUFF_BRICK_WALL = MakeWall(TUFF_BRICKS, ItemSettings(ItemGroup.BUILDING_BLOCKS)).wallModel(TUFF_BRICKS).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer CHISELED_TUFF = MakeBlock(Blocks.TUFF, ItemSettings(ItemGroup.BUILDING_BLOCKS)).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer CHISELED_TUFF_BRICKS = MakeBlock(Blocks.TUFF, ItemSettings(ItemGroup.BUILDING_BLOCKS)).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer TUFF_TILES = MakeBlock(TUFF_BRICKS).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer TUFF_TILE_STAIRS = MakeStairs(TUFF_TILES).stairsModel(TUFF_TILES).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer TUFF_TILE_SLAB = MakeSlab(TUFF_TILES).slabModel(TUFF_TILES).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer TUFF_TILE_WALL = MakeWall(TUFF_TILES).wallModel(TUFF_TILES).blockTag(BlockTags.PICKAXE_MINEABLE);
	//</editor-fold>
	//<editor-fold desc="Turtle">
	public static final ArmorItem TURTLE_CHESTPLATE = MakeChestplate(ModArmorMaterials.TURTLE);
	public static final ArmorItem TURTLE_BOOTS = MakeBoots(ModArmorMaterials.TURTLE);
	//</editor-fold>
	public static final TorchContainer UNDERWATER_TORCH = MakeUnderwaterTorch(BlockSoundGroup.WOOD);
	//<editor-fold desc="Unlit Light Sources">
	public static final UnlitTorchContainer UNLIT_TORCH = new UnlitTorchContainer(new UnlitTorchBlock((TorchBlock)Blocks.TORCH), new UnlitWallTorchBlock((WallTorchBlock)Blocks.WALL_TORCH)).drops(Items.TORCH);
	public static final UnlitTorchContainer UNLIT_SOUL_TORCH = new UnlitTorchContainer(new UnlitTorchBlock((TorchBlock)Blocks.SOUL_TORCH), new UnlitWallTorchBlock((WallTorchBlock)Blocks.SOUL_WALL_TORCH)).drops(Items.SOUL_TORCH);
	public static final Block UNLIT_LANTERN = MakeUnlitLantern(Blocks.LANTERN, Items.LANTERN).dropsLantern();
	public static final Block UNLIT_SOUL_LANTERN = MakeUnlitLantern(Blocks.SOUL_LANTERN, Items.SOUL_LANTERN).dropsSoulLantern();
	//</editor-fold>
	//<editor-fold desc="Warden">
	public static final ModSensorType<WardenAttackablesSensor> WARDEN_ENTITY_SENSOR = new ModSensorType<>("minecraft:warden_entity_sensor", WardenAttackablesSensor::new);
	public static final EntityType<WardenEntity> WARDEN_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, WardenEntity::new).dimensions(EntityDimensions.fixed(0.9f, 2.9f)).trackRangeChunks(16).fireImmune().build();
	public static final Item WARDEN_SPAWN_EGG = MakeSpawnEgg(WARDEN_ENTITY, 1001033, 3790560, ItemSettings(ItemGroup.MISC));
	//</editor-fold>
	//<editor-fold desc="Warped">
	public static final WallBlockContainer WARPED_HANGING_SIGN = MakeHangingSign(SignType.WARPED, Blocks.STRIPPED_WARPED_STEM, SignItemSettings(ItemGroup.DECORATIONS)).blockTag(BlockTags.NON_FLAMMABLE_WOOD).itemTag(ItemTags.NON_FLAMMABLE_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer WARPED_IRON_HANGING_SIGN = MakeHangingSign(MakeSignType("warped_iron"), Blocks.STRIPPED_WARPED_STEM, ModBlockSoundGroups.NETHER_WOOD).blockTag(BlockTags.NON_FLAMMABLE_WOOD).itemTag(ItemTags.NON_FLAMMABLE_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer WARPED_GOLD_HANGING_SIGN = MakeHangingSign(MakeSignType("warped_gold"), Blocks.STRIPPED_WARPED_STEM, ModBlockSoundGroups.NETHER_WOOD).blockTag(BlockTags.NON_FLAMMABLE_WOOD).itemTag(ItemTags.NON_FLAMMABLE_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer WARPED_COPPER_HANGING_SIGN = MakeHangingSign(MakeSignType("warped_copper"), Blocks.STRIPPED_WARPED_STEM, ModBlockSoundGroups.NETHER_WOOD).blockTag(BlockTags.NON_FLAMMABLE_WOOD).itemTag(ItemTags.NON_FLAMMABLE_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer WARPED_EXPOSED_COPPER_HANGING_SIGN = MakeHangingSign(MakeSignType("warped_exposed_copper"), Blocks.STRIPPED_WARPED_STEM, ModBlockSoundGroups.NETHER_WOOD).blockTag(BlockTags.NON_FLAMMABLE_WOOD).itemTag(ItemTags.NON_FLAMMABLE_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer WARPED_WEATHERED_COPPER_HANGING_SIGN = MakeHangingSign(MakeSignType("warped_weathered_copper"), Blocks.STRIPPED_WARPED_STEM, ModBlockSoundGroups.NETHER_WOOD).blockTag(BlockTags.NON_FLAMMABLE_WOOD).itemTag(ItemTags.NON_FLAMMABLE_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer WARPED_OXIDIZED_COPPER_HANGING_SIGN = MakeHangingSign(MakeSignType("warped_oxidized_copper"), Blocks.STRIPPED_WARPED_STEM, ModBlockSoundGroups.NETHER_WOOD).blockTag(BlockTags.NON_FLAMMABLE_WOOD).itemTag(ItemTags.NON_FLAMMABLE_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer WARPED_NETHERITE_HANGING_SIGN = MakeHangingSign(MakeSignType("warped_netherite"), Blocks.STRIPPED_WARPED_STEM, ModBlockSoundGroups.NETHER_WOOD).blockTag(BlockTags.NON_FLAMMABLE_WOOD).itemTag(ItemTags.NON_FLAMMABLE_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer WARPED_BEEHIVE = MakeBeehive(MapColor.DARK_AQUA, ModBlockSoundGroups.NETHER_WOOD).blockTag(BlockTags.NON_FLAMMABLE_WOOD).itemTag(ItemTags.NON_FLAMMABLE_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer WARPED_BOOKSHELF = MakeBookshelf(Blocks.WARPED_PLANKS, ModBlockSoundGroups.NETHER_WOOD).blockTag(BlockTags.NON_FLAMMABLE_WOOD).itemTag(ItemTags.NON_FLAMMABLE_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer WARPED_CHISELED_BOOKSHELF = MakeChiseledBookshelf(MapColor.DARK_AQUA, ModBlockSoundGroups.NETHER_WOOD).blockTag(BlockTags.NON_FLAMMABLE_WOOD).itemTag(ItemTags.NON_FLAMMABLE_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer WARPED_CRAFTING_TABLE = MakeCraftingTable(Blocks.WARPED_PLANKS, ModBlockSoundGroups.NETHER_WOOD).blockTag(BlockTags.NON_FLAMMABLE_WOOD).itemTag(ItemTags.NON_FLAMMABLE_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer WARPED_LADDER = MakeLadder().blockTag(BlockTags.NON_FLAMMABLE_WOOD).itemTag(ItemTags.NON_FLAMMABLE_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer WARPED_WOODCUTTER = MakeWoodcutter(Blocks.WARPED_PLANKS).blockTag(BlockTags.NON_FLAMMABLE_WOOD).itemTag(ItemTags.NON_FLAMMABLE_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer WARPED_BARREL = MakeBarrel(MapColor.DARK_AQUA, ModBlockSoundGroups.NETHER_WOOD).blockTag(BlockTags.NON_FLAMMABLE_WOOD).itemTag(ItemTags.NON_FLAMMABLE_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer WARPED_LECTERN = MakeLectern(Blocks.WARPED_PLANKS, ModBlockSoundGroups.NETHER_WOOD).blockTag(BlockTags.NON_FLAMMABLE_WOOD).itemTag(ItemTags.NON_FLAMMABLE_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer WARPED_POWDER_KEG = MakePowderKeg(WARPED_BARREL).blockTag(BlockTags.NON_FLAMMABLE_WOOD).itemTag(ItemTags.NON_FLAMMABLE_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer WARPED_STEM_SLAB = MakeLogSlab(Blocks.WARPED_STEM).blockTag(BlockTags.NON_FLAMMABLE_WOOD).itemTag(ItemTags.NON_FLAMMABLE_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer STRIPPED_WARPED_STEM_SLAB = MakeLogSlab(Blocks.STRIPPED_WARPED_STEM).blockTag(BlockTags.NON_FLAMMABLE_WOOD).itemTag(ItemTags.NON_FLAMMABLE_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer WARPED_HYPHAE_SLAB = MakeBarkSlab(Blocks.WARPED_HYPHAE, Blocks.WARPED_STEM).blockTag(BlockTags.NON_FLAMMABLE_WOOD).itemTag(ItemTags.NON_FLAMMABLE_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer STRIPPED_WARPED_HYPHAE_SLAB = MakeBarkSlab(Blocks.STRIPPED_WARPED_HYPHAE, Blocks.STRIPPED_WARPED_STEM).blockTag(BlockTags.NON_FLAMMABLE_WOOD).itemTag(ItemTags.NON_FLAMMABLE_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	//Misc
	public static final Item WARPED_HAMMER = MakeHammer(ToolMaterials.WOOD, 7, -3.2F);
	//Wart
	public static final BlockContainer WARPED_WART = new BlockContainer(new ModWartBlock(Block.Settings.copy(Blocks.NETHER_WART).mapColor(MapColor.BRIGHT_TEAL))).compostable(0.65f).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer WARPED_WART_SLAB = MakeSlab(Blocks.WARPED_WART_BLOCK).slabModel(Blocks.WARPED_WART_BLOCK).blockTag(BlockTags.HOE_MINEABLE);
	//Torches
	public static final TorchContainer WARPED_TORCH = MakeTorch(ModBlockSoundGroups.NETHER_WOOD).torchModel();
	public static final TorchContainer WARPED_SOUL_TORCH = MakeSoulTorch(ModBlockSoundGroups.NETHER_WOOD).torchModel(WARPED_TORCH);
	public static final TorchContainer WARPED_ENDER_TORCH = MakeEnderTorch(ModBlockSoundGroups.NETHER_WOOD).torchModel(WARPED_TORCH);
	public static final TorchContainer UNDERWATER_WARPED_TORCH = MakeUnderwaterTorch(ModBlockSoundGroups.NETHER_WOOD).torchModel(WARPED_TORCH);
	//Campfires
	public static final BlockContainer WARPED_CAMPFIRE = MakeCampfire(MapColor.DARK_DULL_PINK, BlockSoundGroup.NETHER_STEM).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer WARPED_SOUL_CAMPFIRE = MakeSoulCampfire(WARPED_CAMPFIRE, BlockSoundGroup.NETHER_STEM).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer WARPED_ENDER_CAMPFIRE = MakeEnderCampfire(WARPED_CAMPFIRE, BlockSoundGroup.NETHER_STEM).blockTag(BlockTags.AXE_MINEABLE);
	//</editor-fold>
	public static final BlockContainer WAX_BLOCK = BuildBlock(new WaxBlock(Blocks.HONEYCOMB_BLOCK)).cubeAllModel();
	//<editor-fold desc="Wildfire">
	//TODO: Wildfire Mob https://minecraft.wiki/w/Minecraft_Dungeons:Wildfire
	//TODO: Wildfire Statue
	public static final Item WILDFIRE_CROWN = ModFactory.MakeHelmet(ModArmorMaterials.WILDFIRE);
	//</editor-fold>
	public static final Item WIND_HORN = new WindHornItem(ItemSettings().maxCount(1));
	//<editor-fold desc="Wood">
	private static final BucketContainer WOOD_BUCKET_PROVIDER = new BucketContainer(false, false);
	public static final Item WOOD_BUCKET = WOOD_BUCKET_PROVIDER.getBucket();
	public static final Item WOOD_WATER_BUCKET = WOOD_BUCKET_PROVIDER.getWaterBucket();
	public static final Item WOOD_POWDER_SNOW_BUCKET = WOOD_BUCKET_PROVIDER.getPowderSnowBucket();
	public static final Item WOOD_BLOOD_BUCKET = WOOD_BUCKET_PROVIDER.getBloodBucket();
	public static final Item WOOD_MUD_BUCKET = WOOD_BUCKET_PROVIDER.getMudBucket();
	public static final Item WOOD_MILK_BUCKET = WOOD_BUCKET_PROVIDER.getMilkBucket();
	public static final Item WOOD_CHOCOLATE_MILK_BUCKET = WOOD_BUCKET_PROVIDER.getChocolateMilkBucket();
	public static final Item WOOD_COFFEE_MILK_BUCKET = WOOD_BUCKET_PROVIDER.getCoffeeMilkBucket();
	public static final Item WOOD_STRAWBERRY_MILK_BUCKET = WOOD_BUCKET_PROVIDER.getStrawberryMilkBucket();
	public static final Item WOOD_VANILLA_MILK_BUCKET = WOOD_BUCKET_PROVIDER.getVanillaMilkBucket();
	//</editor-fold>
	//<editor-fold desc="Woodcutting">
	public static final RecipeType<WoodcuttingRecipe> WOODCUTTING_RECIPE_TYPE = RegisterRecipeType("woodcutting");
	public static final RecipeSerializer<WoodcuttingRecipe> WOODCUTTING_RECIPE_SERIALIZER = new WoodcuttingRecipeSerializer<>(WoodcuttingRecipe::new);
	public static final ScreenHandlerType<WoodcutterScreenHandler> WOODCUTTER_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(ModId.ID("woodcutter"), WoodcutterScreenHandler::new);
	//</editor-fold>
	//<editor-fold desc="Wool">
	public static final Map<DyeColor, BlockContainer> WOOL_SLABS = ColorUtil.Map(color -> { Block block = ColorUtil.GetWoolBlock(color); return MakeSlab(block).flammable(40, 40).fuel(50).slabModel(block).blockTag(ModBlockTags.WOOL_SLABS).itemTag(ModItemTags.WOOL_SLABS); });
	public static final BlockContainer RAINBOW_WOOL = BuildBlock(new ModFacingBlock(Blocks.WHITE_WOOL)).flammable(30, 60).fuel(100).blockTag(BlockTags.WOOL).itemTag(ItemTags.WOOL);
	public static final BlockContainer RAINBOW_WOOL_SLAB = BuildSlab(new HorizontalFacingSlabBlock(RAINBOW_WOOL)).flammable(40, 40).fuel(50).blockTag(ModBlockTags.WOOL_SLABS).itemTag(ModItemTags.WOOL_SLABS);
	public static final BlockContainer RAINBOW_CARPET = BuildBlock(new HorziontalFacingCarpetBlock(RAINBOW_WOOL)).flammable(60, 20).fuel(67).blockTag(BlockTags.CARPETS).itemTag(ItemTags.CARPETS).blockTag(ModBlockTags.WOOL_CARPETS).itemTag(ModItemTags.WOOL_CARPETS);
	public static final BedContainer RAINBOW_BED = MakeBed("rainbow");
	public static final EntityType<RainbowSheepEntity> RAINBOW_SHEEP_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, RainbowSheepEntity::new).dimensions(EntityType.SHEEP.getDimensions()).trackRangeChunks(10).build();
	public static final Item RAINBOW_SHEEP_SPAWN_EGG = GeneratedItem(new SpawnEggItem(RAINBOW_SHEEP_ENTITY, 0xFFFFFF, 0xFFFFFF, ItemSettings()));
	//</editor-fold>
	//<editor-fold desc="Creepers">
	public static final EntityType<SlimeCreeperEntity> SLIME_CREEPER_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, SlimeCreeperEntity::new).dimensions(EntityDimensions.fixed(0.6f, 1.7f)).trackRangeChunks(8).build();
	public static final Item SLIME_CREEPER_SPAWN_EGG = MakeSpawnEgg(SLIME_CREEPER_ENTITY, 5349438, 0);
	//</editor-fold>
	//<editor-fold desc="Skeletons">
	public static final EntityType<MossySkeletonEntity> MOSSY_SKELETON_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, MossySkeletonEntity::new).dimensions(EntityDimensions.fixed(0.6f, 1.6f)).trackRangeChunks(10).build();
	public static final Item MOSSY_SKELETON_SPAWN_EGG = MakeSpawnEgg(MOSSY_SKELETON_ENTITY, 0xD6D7C6, 0x526121);
	public static final EntityType<SlimySkeletonEntity> SLIMY_SKELETON_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, SlimySkeletonEntity::new).dimensions(EntityDimensions.fixed(0.6f, 1.6f)).trackRangeChunks(10).build();
	public static final Item SLIMY_SKELETON_SPAWN_EGG = MakeSpawnEgg(SLIMY_SKELETON_ENTITY, 5349438, 0x494949);
	public static final EntityType<SunkenSkeletonEntity> SUNKEN_SKELETON_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, SunkenSkeletonEntity::new).dimensions(EntityDimensions.fixed(0.6f, 1.6f)).trackRangeChunks(10).build();
	public static final Item SUNKEN_SKELETON_SPAWN_EGG = MakeSpawnEgg(SUNKEN_SKELETON_ENTITY, 0xD6D0C9, 0x98439E);
	//</editor-fold>
	//<editor-fold desc="Zombies">
	public static final EntityType<SlowingSnowballEntity> SLOWING_SNOWBALL_ENTITY = FabricEntityTypeBuilder.<SlowingSnowballEntity>create(SpawnGroup.MISC, SlowingSnowballEntity::new).dimensions(EntityDimensions.fixed(0.25f, 0.25f)).trackRangeChunks(4).trackedUpdateRate(10).build();
	public static final EntityType<FrozenZombieEntity> FROZEN_ZOMBIE_ENTITY = FabricEntityTypeBuilder.<FrozenZombieEntity>create(SpawnGroup.MONSTER, FrozenZombieEntity::new).dimensions(EntityDimensions.fixed(0.6f, 1.95f)).trackRangeChunks(8).build();
	public static final Item FROZEN_ZOMBIE_SPAWN_EGG = MakeSpawnEgg(FROZEN_ZOMBIE_ENTITY, 0x78BEDE, 0x5A8684);
	public static final EntityType<JungleZombieEntity> JUNGLE_ZOMBIE_ENTITY = FabricEntityTypeBuilder.<JungleZombieEntity>create(SpawnGroup.MONSTER, JungleZombieEntity::new).dimensions(EntityDimensions.fixed(0.6f, 1.95f)).trackRangeChunks(8).build();
	public static final Item JUNGLE_ZOMBIE_SPAWN_EGG = MakeSpawnEgg(JUNGLE_ZOMBIE_ENTITY, 0x67BC97, 0x5A8646);
	public static final EntityType<SlimeZombieEntity> SLIME_ZOMBIE_ENTITY = FabricEntityTypeBuilder.<SlimeZombieEntity>create(SpawnGroup.MONSTER, SlimeZombieEntity::new).dimensions(EntityDimensions.fixed(0.6f, 1.95f)).trackRangeChunks(8).build();
	public static final Item SLIME_ZOMBIE_SPAWN_EGG = MakeSpawnEgg(SLIME_ZOMBIE_ENTITY, 5349438, 0x5A8646);
	//</editor-fold>

	//<editor-fold desc="Jolly LLamas">
	public static final EntityType<JollyLlamaEntity> JOLLY_LLAMA_ENTITY = FabricEntityTypeBuilder.<JollyLlamaEntity>create(SpawnGroup.CREATURE, JollyLlamaEntity::new).dimensions(EntityDimensions.fixed(0.9f, 1.87f)).trackRangeChunks(10).build();
	public static final Item JOLLY_LLAMA_SPAWN_EGG = MakeSpawnEgg(JOLLY_LLAMA_ENTITY, 12623485, 0xFF0000);
	//</editor-fold>

	//<editor-fold desc="Villagers and Illagers">
	public static final BlockContainer FACETING_TABLE = new BlockContainer(new FacetingTableBlock(Block.Settings.copy(Blocks.STONECUTTER))).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final PointOfInterestType FACETING_TABLE_POI = PointOfInterestTypeAccessor.callCreate("faceting_table_poi", ImmutableSet.copyOf(FACETING_TABLE.asBlock().getStateManager().getStates()), 1, 1);
	public static final VillagerProfession LAPIDARY = VillagerProfessionAccessor.create("lapidary", FACETING_TABLE_POI, ImmutableSet.of(), ImmutableSet.of(), ModSoundEvents.ENTITY_VILLAGER_WORK_LAPIDARY);

	public static final EntityType<IceologerEntity> ICEOLOGER_ENTITY = FabricEntityTypeBuilder.<IceologerEntity>create(SpawnGroup.MONSTER, IceologerEntity::new).dimensions(EntityDimensions.fixed(0.6f, 1.95f)).trackRangeChunks(8).build();
	public static final Item ICEOLOGER_SPAWN_EGG = MakeSpawnEgg(ICEOLOGER_ENTITY, 0x959B9B, 0x01374B);
	public static final EntityType<IceChunkEntity> ICE_CHUNK_ENTITY = FabricEntityTypeBuilder.<IceChunkEntity>create(SpawnGroup.MISC, IceChunkEntity::new).dimensions(EntityDimensions.fixed(2f, 1.5f)).trackRangeChunks(8).build();

	public static final EntityType<MageEntity> MAGE_ENTITY = FabricEntityTypeBuilder.<MageEntity>create(SpawnGroup.MONSTER, MageEntity::new).dimensions(EntityDimensions.fixed(0.6f, 1.95f)).trackRangeChunks(8).build();
	public static final Item MAGE_SPAWN_EGG = MakeSpawnEgg(MAGE_ENTITY, 0x959B9B, 0x5A1949);

	public static void RegisterVillagers() {
		//Iceologer
		Register("iceologer", ICEOLOGER_ENTITY, List.of(EN_US.Iceologer()));
		FabricDefaultAttributeRegistry.register(ICEOLOGER_ENTITY, IceologerEntity.createSpellcasterAttributes());
		SpawnRestrictionAccessor.callRegister(ICEOLOGER_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HostileEntity::canMobSpawn);
		Register("ice_chunk", ICE_CHUNK_ENTITY, List.of(EN_US.Chunk(EN_US.Ice())));
		//Mage
		Register("mage", MAGE_ENTITY, List.of(EN_US.Iceologer()));
		FabricDefaultAttributeRegistry.register(MAGE_ENTITY, MageEntity.createSpellcasterAttributes());
		SpawnRestrictionAccessor.callRegister(MAGE_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HostileEntity::canMobSpawn);
		//Lapidary Villager
		Register("faceting_table", FACETING_TABLE, List.of(EN_US.Table(EN_US.Faceting())));
		PointOfInterestTypeAccessor.callSetup(Register(FACETING_TABLE_POI.getId(), FACETING_TABLE_POI));
		Register(LAPIDARY.getId(), LAPIDARY);
		TradeOfferHelper.registerVillagerOffers(LAPIDARY, 1, //Novice
				factories -> {
					factories.add((entity, random) -> new TradeOffer(new ItemStack(RUBY, 2), new ItemStack(Items.EMERALD), 12, 1, 0.1f));
					factories.add((entity, random) -> new TradeOffer(new ItemStack(Items.EMERALD, 2), new ItemStack(RUBY), 12, 1, 0.1f));
				});
		TradeOfferHelper.registerVillagerOffers(LAPIDARY, 2, //Apprentice
				factories -> {
					factories.add((entity, random) -> new TradeOffer(new ItemStack(SAPPHIRE), new ItemStack(Items.EMERALD), 12, 10, 0.05f));
					factories.add((entity, random) -> new TradeOffer(new ItemStack(Items.PRISMARINE_SHARD, 6), new ItemStack(Items.EMERALD), 12, 10, 0.05f));
					factories.add((entity, random) -> new TradeOffer(new ItemStack(Items.PRISMARINE_CRYSTALS, 6), new ItemStack(Items.EMERALD), 12, 10, 0.05f));
				});
		TradeOfferHelper.registerVillagerOffers(LAPIDARY, 3, //Journeyman
				factories -> {
					factories.add((entity, random) -> new TradeOffer(new ItemStack(Items.EMERALD, 2), new ItemStack(SAPPHIRE), 12, 10, 0.1f));
					factories.add((entity, random) -> new TradeOffer(new ItemStack(Items.EMERALD), new ItemStack(Items.PRISMARINE_SHARD, 3), 12, 10, 0.05f));
					factories.add((entity, random) -> new TradeOffer(new ItemStack(Items.EMERALD), new ItemStack(Items.PRISMARINE_CRYSTALS, 3), 12, 10, 0.05f));
				});
		TradeOfferHelper.registerVillagerOffers(LAPIDARY, 4, //Expert
				factories -> {
					factories.add((entity, random) -> new TradeOffer(new ItemStack(Items.EMERALD, 4), new ItemStack(ECHO_SHARD), 16, 20, 0.05f));
					factories.add((entity, random) -> new TradeOffer(new ItemStack(Items.EMERALD, 2), new ItemStack(ECHO_CLUSTER), 16, 20, 0.1f));
					factories.add((entity, random) -> new TradeOffer(new ItemStack(Items.EMERALD, 2), new ItemStack(Items.AMETHYST_CLUSTER), 16, 20, 0.1f));
					factories.add((entity, random) -> new TradeOffer(new ItemStack(Items.AMETHYST_SHARD, 12), new ItemStack(Items.EMERALD), 12, 30, 0.05f));
				});
		TradeOfferHelper.registerVillagerOffers(LAPIDARY, 5, //Master
				factories -> {
					factories.add((entity, random) -> new TradeOffer(new ItemStack(Items.EMERALD, 9), new ItemStack(Items.DIAMOND), 12, 30, 0.2f));
					factories.add((entity, random) -> new TradeOffer(new ItemStack(Items.EMERALD), new ItemStack(Items.AMETHYST_BLOCK), 12, 30, 0.05f));
					//TODO: Enable end crystal trading at some point
					//factories.add((entity, random) -> new TradeOffer(new ItemStack(Items.EMERALD, 32), new ItemStack(Items.END_CRYSTAL), 1, 60, 0.1f));
				});
	}
	//</editor-fold>

	//<editor-fold desc="Cakes">
	public static final CakeContainer CARROT_CAKE = new CakeContainer().compostable(1);
	public static final CakeContainer CHOCOLATE_CAKE = new CakeContainer().compostable(1);
	public static final CakeContainer CHORUS_CAKE = new CakeContainer() {
		@Override
		public void onEat(WorldAccess world, BlockPos pos, BlockState state, PlayerEntity player) {
			ChorusCommand.TeleportEntity(player);
		}
	}.compostable(1);
	public static final CakeContainer COFFEE_CAKE = new CakeContainer() {
		@Override
		public void onEat(WorldAccess world, BlockPos pos, BlockState state, PlayerEntity player) {
			player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 200, 0));
			player.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 200, 0));
		}
	}.compostable(1);
	public static final CakeContainer CONFETTI_CAKE = new CakeContainer().compostable(1);
	public static final CakeContainer STRAWBERRY_CAKE = new CakeContainer().compostable(1);
	public static final CakeContainer VANILLA_CAKE = new CakeContainer().compostable(1);
	//</editor-fold>

	//<editor-fold desc="Infested Blocks">
	public static final BlockContainer INFESTED_MOSSY_CHISELED_STONE_BRICKS = MakeInfested(MOSSY_CHISELED_STONE_BRICKS).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer INFESTED_COBBLED_DEEPSLATE = MakeInfested(Blocks.COBBLED_DEEPSLATE, Items.COBBLED_DEEPSLATE, MapColor.DEEPSLATE_GRAY, BlockSoundGroup.DEEPSLATE).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer INFESTED_MOSSY_COBBLED_DEEPSLATE = MakeInfested(MOSSY_COBBLED_DEEPSLATE, MapColor.DEEPSLATE_GRAY, BlockSoundGroup.DEEPSLATE).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer INFESTED_ANDESITE = MakeInfested(Blocks.ANDESITE, Items.ANDESITE, MapColor.STONE_GRAY).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer INFESTED_ANDESITE_BRICKS = MakeInfested(ANDESITE_BRICKS, MapColor.STONE_GRAY).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer INFESTED_CHISELED_ANDESITE_BRICKS = MakeInfested(CHISELED_ANDESITE_BRICKS, MapColor.STONE_GRAY).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer INFESTED_DIORITE = MakeInfested(Blocks.DIORITE, Items.DIORITE, MapColor.OFF_WHITE).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer INFESTED_DIORITE_BRICKS = MakeInfested(DIORITE_BRICKS, MapColor.OFF_WHITE).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer INFESTED_CHISELED_DIORITE_BRICKS = MakeInfested(CHISELED_DIORITE_BRICKS, MapColor.OFF_WHITE).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer INFESTED_GRANITE = MakeInfested(Blocks.GRANITE, Items.GRANITE, MapColor.DIRT_BROWN).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer INFESTED_GRANITE_BRICKS = MakeInfested(GRANITE_BRICKS, MapColor.DIRT_BROWN).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer INFESTED_CHISELED_GRANITE_BRICKS = MakeInfested(CHISELED_GRANITE_BRICKS, MapColor.DIRT_BROWN).blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer INFESTED_TUFF = MakeInfested(Blocks.TUFF, Items.TUFF, MapColor.TERRACOTTA_GRAY).blockTag(BlockTags.PICKAXE_MINEABLE);
	//</editor-fold>

	//<editor-fold desc="Sandy Blocks">
	public static final BlockContainer SANDY_COBBLESTONE = MakeSandy(Blocks.COBBLESTONE).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer SANDY_COBBLESTONE_SLAB = MakeSandySlab(Blocks.COBBLESTONE_SLAB).slabModel(SANDY_COBBLESTONE).blockTag(BlockTags.PICKAXE_MINEABLE);
	//<editor-fold desc="Andesite">
	public static final BlockContainer SANDY_POLISHED_ANDESITE = MakeSandy(Blocks.POLISHED_ANDESITE).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer SANDY_POLISHED_ANDESITE_SLAB = MakeSandySlab(Blocks.POLISHED_ANDESITE_SLAB).slabModel(SANDY_POLISHED_ANDESITE).blockTag(BlockTags.PICKAXE_MINEABLE);

	public static final BlockContainer SANDY_ANDESITE_BRICKS = MakeSandy(ANDESITE_BRICKS).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer SANDY_ANDESITE_BRICK_SLAB = MakeSandySlab(ANDESITE_BRICK_SLAB).slabModel(SANDY_ANDESITE_BRICKS).blockTag(BlockTags.PICKAXE_MINEABLE);

	public static final BlockContainer SANDY_CUT_POLISHED_ANDESITE = MakeSandy(CUT_POLISHED_ANDESITE).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer SANDY_CUT_POLISHED_ANDESITE_SLAB = MakeSandySlab(CUT_POLISHED_ANDESITE_SLAB).slabModel(SANDY_CUT_POLISHED_ANDESITE).blockTag(BlockTags.PICKAXE_MINEABLE);
	//</editor-fold>
	//<editor-fold desc="Diorite">
	public static final BlockContainer SANDY_POLISHED_DIORITE = MakeSandy(Blocks.POLISHED_DIORITE).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer SANDY_POLISHED_DIORITE_SLAB = MakeSandySlab(Blocks.POLISHED_DIORITE_SLAB).slabModel(SANDY_POLISHED_DIORITE).blockTag(BlockTags.PICKAXE_MINEABLE);

	public static final BlockContainer SANDY_DIORITE_BRICKS = MakeSandy(DIORITE_BRICKS).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer SANDY_DIORITE_BRICK_SLAB = MakeSandySlab(DIORITE_BRICK_SLAB).slabModel(SANDY_DIORITE_BRICKS).blockTag(BlockTags.PICKAXE_MINEABLE);

	public static final BlockContainer SANDY_CUT_POLISHED_DIORITE = MakeSandy(CUT_POLISHED_DIORITE).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer SANDY_CUT_POLISHED_DIORITE_SLAB = MakeSandySlab(CUT_POLISHED_DIORITE_SLAB).slabModel(SANDY_CUT_POLISHED_DIORITE).blockTag(BlockTags.PICKAXE_MINEABLE);
	//</editor-fold>
	//<editor-fold desc="Granite">
	public static final BlockContainer SANDY_POLISHED_GRANITE = MakeSandy(Blocks.POLISHED_GRANITE).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer SANDY_POLISHED_GRANITE_SLAB = MakeSandySlab(Blocks.POLISHED_GRANITE_SLAB).slabModel(SANDY_POLISHED_GRANITE).blockTag(BlockTags.PICKAXE_MINEABLE);

	public static final BlockContainer SANDY_GRANITE_BRICKS = MakeSandy(GRANITE_BRICKS).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer SANDY_GRANITE_BRICK_SLAB = MakeSandySlab(GRANITE_BRICK_SLAB).slabModel(SANDY_GRANITE_BRICKS).blockTag(BlockTags.PICKAXE_MINEABLE);

	public static final BlockContainer SANDY_CUT_POLISHED_GRANITE = MakeSandy(CUT_POLISHED_GRANITE).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer SANDY_CUT_POLISHED_GRANITE_SLAB = MakeSandySlab(CUT_POLISHED_GRANITE_SLAB).slabModel(SANDY_CUT_POLISHED_GRANITE).blockTag(BlockTags.PICKAXE_MINEABLE);
	//</editor-fold>
	//<editor-fold desc="Planks">
	public static final BlockContainer SANDY_ACACIA_PLANKS = MakeSandy(Blocks.ACACIA_PLANKS).flammable(5, 5).cubeAllModel().blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer SANDY_ACACIA_SLAB = MakeSandySlab(Blocks.ACACIA_SLAB).slabModel(SANDY_ACACIA_PLANKS).blockTag(BlockTags.AXE_MINEABLE);

	public static final BlockContainer SANDY_BIRCH_PLANKS = MakeSandy(Blocks.BIRCH_PLANKS).flammable(5, 5).cubeAllModel().blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer SANDY_BIRCH_SLAB = MakeSandySlab(Blocks.BIRCH_SLAB).slabModel(SANDY_BIRCH_PLANKS).blockTag(BlockTags.AXE_MINEABLE);

	public static final BlockContainer SANDY_BLUE_MUSHROOM_PLANKS = MakeSandy(BLUE_MUSHROOM_PLANKS).flammable(5, 5).cubeAllModel().blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer SANDY_BLUE_MUSHROOM_SLAB = MakeSandySlab(BLUE_MUSHROOM_SLAB).slabModel(SANDY_BLUE_MUSHROOM_PLANKS).blockTag(BlockTags.AXE_MINEABLE);

	public static final BlockContainer SANDY_BROWN_MUSHROOM_PLANKS = MakeSandy(BROWN_MUSHROOM_PLANKS).flammable(5, 5).cubeAllModel().blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer SANDY_BROWN_MUSHROOM_SLAB = MakeSandySlab(BROWN_MUSHROOM_SLAB).slabModel(SANDY_BROWN_MUSHROOM_PLANKS).blockTag(BlockTags.AXE_MINEABLE);

	public static final BlockContainer SANDY_CASSIA_PLANKS = MakeSandy(CASSIA_PLANKS).flammable(5, 5).cubeAllModel().blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer SANDY_CASSIA_SLAB = MakeSandySlab(CASSIA_SLAB).slabModel(SANDY_CASSIA_PLANKS).blockTag(BlockTags.AXE_MINEABLE);

	public static final BlockContainer SANDY_CHARRED_PLANKS = MakeSandy(CHARRED_PLANKS).cubeAllModel().blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer SANDY_CHARRED_SLAB = MakeSandySlab(CHARRED_SLAB).slabModel(SANDY_CHARRED_PLANKS).blockTag(BlockTags.AXE_MINEABLE);

	public static final BlockContainer SANDY_CHERRY_PLANKS = MakeSandy(CHERRY_PLANKS).flammable(5, 5).cubeAllModel().blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer SANDY_CHERRY_SLAB = MakeSandySlab(CHERRY_SLAB).slabModel(SANDY_CHERRY_PLANKS).blockTag(BlockTags.AXE_MINEABLE);

	public static final BlockContainer SANDY_CRIMSON_PLANKS = MakeSandy(Blocks.CRIMSON_PLANKS).cubeAllModel().blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer SANDY_CRIMSON_SLAB = MakeSandySlab(Blocks.CRIMSON_SLAB).slabModel(SANDY_CRIMSON_PLANKS).blockTag(BlockTags.AXE_MINEABLE);

	public static final BlockContainer SANDY_DARK_OAK_PLANKS = MakeSandy(Blocks.DARK_OAK_PLANKS).flammable(5, 5).cubeAllModel().blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer SANDY_DARK_OAK_SLAB = MakeSandySlab(Blocks.DARK_OAK_SLAB).slabModel(SANDY_DARK_OAK_PLANKS).blockTag(BlockTags.AXE_MINEABLE);

	public static final BlockContainer SANDY_DOGWOOD_PLANKS = MakeSandy(DOGWOOD_PLANKS).flammable(5, 5).cubeAllModel().blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer SANDY_DOGWOOD_SLAB = MakeSandySlab(DOGWOOD_SLAB).slabModel(SANDY_DOGWOOD_PLANKS).blockTag(BlockTags.AXE_MINEABLE);

	public static final BlockContainer SANDY_GILDED_PLANKS = MakeSandy(GILDED_PLANKS).cubeAllModel().blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer SANDY_GILDED_SLAB = MakeSandySlab(GILDED_SLAB).slabModel(SANDY_GILDED_PLANKS).blockTag(BlockTags.AXE_MINEABLE);

	public static final BlockContainer SANDY_HAY_PLANKS = MakeSandy(HAY_PLANKS).flammable(5, 5).cubeAllModel().blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer SANDY_HAY_SLAB = MakeSandySlab(HAY_SLAB).slabModel(SANDY_HAY_PLANKS).blockTag(BlockTags.AXE_MINEABLE);

	public static final BlockContainer SANDY_JUNGLE_PLANKS = MakeSandy(Blocks.JUNGLE_PLANKS).flammable(5, 5).cubeAllModel().blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer SANDY_JUNGLE_SLAB = MakeSandySlab(Blocks.JUNGLE_SLAB).slabModel(SANDY_JUNGLE_PLANKS).blockTag(BlockTags.AXE_MINEABLE);

	public static final BlockContainer SANDY_MANGROVE_PLANKS = MakeSandy(MANGROVE_PLANKS).flammable(5, 5).cubeAllModel().blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer SANDY_MANGROVE_SLAB = MakeSandySlab(MANGROVE_SLAB).slabModel(SANDY_MANGROVE_PLANKS).blockTag(BlockTags.AXE_MINEABLE);

	public static final BlockContainer SANDY_MUSHROOM_STEM_PLANKS = MakeSandy(MUSHROOM_STEM_PLANKS).flammable(5, 5).cubeAllModel().blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer SANDY_MUSHROOM_STEM_SLAB = MakeSandySlab(MUSHROOM_STEM_SLAB).slabModel(SANDY_MUSHROOM_STEM_PLANKS).blockTag(BlockTags.AXE_MINEABLE);

	public static final BlockContainer SANDY_OAK_PLANKS = MakeSandy(Blocks.OAK_PLANKS).flammable(5, 5).cubeAllModel().blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer SANDY_OAK_SLAB = MakeSandySlab(Blocks.OAK_SLAB).slabModel(SANDY_OAK_PLANKS).blockTag(BlockTags.AXE_MINEABLE);

	public static final BlockContainer SANDY_RED_MUSHROOM_PLANKS = MakeSandy(RED_MUSHROOM_PLANKS).flammable(5, 5).cubeAllModel().blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer SANDY_RED_MUSHROOM_SLAB = MakeSandySlab(RED_MUSHROOM_SLAB).slabModel(SANDY_RED_MUSHROOM_PLANKS).blockTag(BlockTags.AXE_MINEABLE);

	public static final BlockContainer SANDY_SPRUCE_PLANKS = MakeSandy(Blocks.SPRUCE_PLANKS).flammable(5, 5).cubeAllModel().blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer SANDY_SPRUCE_SLAB = MakeSandySlab(Blocks.SPRUCE_SLAB).slabModel(SANDY_SPRUCE_PLANKS).blockTag(BlockTags.AXE_MINEABLE);

	public static final BlockContainer SANDY_WARPED_PLANKS = MakeSandy(Blocks.WARPED_PLANKS).cubeAllModel().blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer SANDY_WARPED_SLAB = MakeSandySlab(Blocks.WARPED_SLAB).slabModel(SANDY_WARPED_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	//</editor-fold>
	//<editor-fold desc="Prismarine">
	public static final BlockContainer SANDY_PRISMARINE = MakeSandy(Blocks.PRISMARINE).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer SANDY_PRISMARINE_SLAB = MakeSandySlab(Blocks.PRISMARINE_SLAB).slabModel(SANDY_PRISMARINE).blockTag(BlockTags.PICKAXE_MINEABLE);

	public static final BlockContainer SANDY_SANDY_PRISMARINE = MakeSandy(SANDY_PRISMARINE).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer SANDY_SANDY_PRISMARINE_SLAB = MakeSandySlab(SANDY_PRISMARINE_SLAB).slabModel(SANDY_SANDY_PRISMARINE).blockTag(BlockTags.PICKAXE_MINEABLE);

	public static final BlockContainer SANDY_PRISMARINE_BRICKS = MakeSandy(Blocks.PRISMARINE_BRICKS).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer SANDY_PRISMARINE_BRICK_SLAB = MakeSandySlab(Blocks.PRISMARINE_BRICK_SLAB).slabModel(SANDY_PRISMARINE_BRICKS).blockTag(BlockTags.PICKAXE_MINEABLE);

	public static final BlockContainer SANDY_CHISELED_PRISMARINE_BRICKS = MakeSandy(CHISELED_PRISMARINE_BRICKS).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);

	public static final BlockContainer SANDY_DARK_PRISMARINE = MakeSandy(Blocks.DARK_PRISMARINE).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer SANDY_DARK_PRISMARINE_SLAB = MakeSandySlab(Blocks.DARK_PRISMARINE_SLAB).slabModel(SANDY_DARK_PRISMARINE).blockTag(BlockTags.PICKAXE_MINEABLE);

	public static final BlockContainer SANDY_SANDY_DARK_PRISMARINE = MakeSandy(SANDY_DARK_PRISMARINE).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer SANDY_SANDY_DARK_PRISMARINE_SLAB = MakeSandySlab(SANDY_DARK_PRISMARINE_SLAB).slabModel(SANDY_SANDY_DARK_PRISMARINE).blockTag(BlockTags.PICKAXE_MINEABLE);
	//</editor-fold>
	//<editor-fold desc="Purpur">
	public static final BlockContainer SANDY_PURPUR_BLOCK = MakeSandy(Blocks.PURPUR_BLOCK).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer SANDY_PURPUR_SLAB = MakeSandySlab(Blocks.PURPUR_SLAB).slabModel(SANDY_PURPUR_BLOCK).blockTag(BlockTags.PICKAXE_MINEABLE);

	public static final BlockContainer SANDY_CHISELED_PURPUR = MakeSandy(CHISELED_PURPUR).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer SANDY_CHISELED_PURPUR_SLAB = MakeSandySlab(CHISELED_PURPUR_SLAB).slabModel(SANDY_CHISELED_PURPUR).blockTag(BlockTags.PICKAXE_MINEABLE);

	public static final BlockContainer SANDY_SANDY_CHISELED_PURPUR = MakeSandy(SANDY_CHISELED_PURPUR).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer SANDY_SANDY_CHISELED_PURPUR_SLAB = MakeSandySlab(SANDY_CHISELED_PURPUR_SLAB).slabModel(SANDY_SANDY_CHISELED_PURPUR).blockTag(BlockTags.PICKAXE_MINEABLE);

	public static final BlockContainer SANDY_SMOOTH_PURPUR = MakeSandy(SMOOTH_PURPUR).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer SANDY_SMOOTH_PURPUR_SLAB = MakeSandySlab(SMOOTH_PURPUR_SLAB).slabModel(SANDY_SMOOTH_PURPUR).blockTag(BlockTags.PICKAXE_MINEABLE);

	public static final BlockContainer SANDY_PURPUR_BRICKS = MakeSandy(PURPUR_BRICKS).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer SANDY_PURPUR_BRICK_SLAB = MakeSandySlab(PURPUR_BRICK_SLAB).slabModel(SANDY_PURPUR_BRICKS).blockTag(BlockTags.PICKAXE_MINEABLE);
	//</editor-fold>
	//Entity
	public static final BlockEntityType<SandyBlockEntity> SANDY_BLOCK_ENTITY = FabricBlockEntityTypeBuilder.create(SandyBlockEntity::new).build();
	//</editor-fold>
	//<editor-fold desc="Sandy Blocks (Red)">
	public static final BlockContainer RED_SANDY_COBBLESTONE = MakeRedSandy(Blocks.COBBLESTONE).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer RED_SANDY_COBBLESTONE_SLAB = MakeRedSandySlab(Blocks.COBBLESTONE_SLAB).slabModel(RED_SANDY_COBBLESTONE).blockTag(BlockTags.PICKAXE_MINEABLE);
	//<editor-fold desc="Andesite">
	public static final BlockContainer RED_SANDY_POLISHED_ANDESITE = MakeRedSandy(Blocks.POLISHED_ANDESITE).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer RED_SANDY_POLISHED_ANDESITE_SLAB = MakeRedSandySlab(Blocks.POLISHED_ANDESITE_SLAB).slabModel(RED_SANDY_POLISHED_ANDESITE).blockTag(BlockTags.PICKAXE_MINEABLE);

	public static final BlockContainer RED_SANDY_ANDESITE_BRICKS = MakeRedSandy(ANDESITE_BRICKS).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer RED_SANDY_ANDESITE_BRICK_SLAB = MakeRedSandySlab(ANDESITE_BRICK_SLAB).slabModel(RED_SANDY_ANDESITE_BRICKS).blockTag(BlockTags.PICKAXE_MINEABLE);

	public static final BlockContainer RED_SANDY_CUT_POLISHED_ANDESITE = MakeRedSandy(CUT_POLISHED_ANDESITE).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer RED_SANDY_CUT_POLISHED_ANDESITE_SLAB = MakeRedSandySlab(CUT_POLISHED_ANDESITE_SLAB).slabModel(RED_SANDY_CUT_POLISHED_ANDESITE).blockTag(BlockTags.PICKAXE_MINEABLE);
	//</editor-fold>
	//<editor-fold desc="Diorite">
	public static final BlockContainer RED_SANDY_POLISHED_DIORITE = MakeRedSandy(Blocks.POLISHED_DIORITE).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer RED_SANDY_POLISHED_DIORITE_SLAB = MakeRedSandySlab(Blocks.POLISHED_DIORITE_SLAB).slabModel(RED_SANDY_POLISHED_DIORITE).blockTag(BlockTags.PICKAXE_MINEABLE);

	public static final BlockContainer RED_SANDY_DIORITE_BRICKS = MakeRedSandy(DIORITE_BRICKS).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer RED_SANDY_DIORITE_BRICK_SLAB = MakeRedSandySlab(DIORITE_BRICK_SLAB).slabModel(RED_SANDY_DIORITE_BRICKS).blockTag(BlockTags.PICKAXE_MINEABLE);

	public static final BlockContainer RED_SANDY_CUT_POLISHED_DIORITE = MakeRedSandy(CUT_POLISHED_DIORITE).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer RED_SANDY_CUT_POLISHED_DIORITE_SLAB = MakeRedSandySlab(CUT_POLISHED_DIORITE_SLAB).slabModel(RED_SANDY_CUT_POLISHED_DIORITE).blockTag(BlockTags.PICKAXE_MINEABLE);
	//</editor-fold>
	//<editor-fold desc="Granite">
	public static final BlockContainer RED_SANDY_POLISHED_GRANITE = MakeRedSandy(Blocks.POLISHED_GRANITE).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer RED_SANDY_POLISHED_GRANITE_SLAB = MakeRedSandySlab(Blocks.POLISHED_GRANITE_SLAB).slabModel(RED_SANDY_POLISHED_GRANITE).blockTag(BlockTags.PICKAXE_MINEABLE);

	public static final BlockContainer RED_SANDY_GRANITE_BRICKS = MakeRedSandy(GRANITE_BRICKS).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer RED_SANDY_GRANITE_BRICK_SLAB = MakeRedSandySlab(GRANITE_BRICK_SLAB).slabModel(RED_SANDY_GRANITE_BRICKS).blockTag(BlockTags.PICKAXE_MINEABLE);

	public static final BlockContainer RED_SANDY_CUT_POLISHED_GRANITE = MakeRedSandy(CUT_POLISHED_GRANITE).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer RED_SANDY_CUT_POLISHED_GRANITE_SLAB = MakeRedSandySlab(CUT_POLISHED_GRANITE_SLAB).slabModel(RED_SANDY_CUT_POLISHED_GRANITE).blockTag(BlockTags.PICKAXE_MINEABLE);
	//</editor-fold>
	//<editor-fold desc="Planks">
	public static final BlockContainer RED_SANDY_ACACIA_PLANKS = MakeRedSandy(Blocks.ACACIA_PLANKS).flammable(5, 5).cubeAllModel().blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer RED_SANDY_ACACIA_SLAB = MakeRedSandySlab(Blocks.ACACIA_SLAB).slabModel(RED_SANDY_ACACIA_PLANKS).blockTag(BlockTags.AXE_MINEABLE);

	public static final BlockContainer RED_SANDY_BIRCH_PLANKS = MakeRedSandy(Blocks.BIRCH_PLANKS).flammable(5, 5).cubeAllModel().blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer RED_SANDY_BIRCH_SLAB = MakeRedSandySlab(Blocks.BIRCH_SLAB).slabModel(RED_SANDY_BIRCH_PLANKS).blockTag(BlockTags.AXE_MINEABLE);

	public static final BlockContainer RED_SANDY_BLUE_MUSHROOM_PLANKS = MakeRedSandy(BLUE_MUSHROOM_PLANKS).flammable(5, 5).cubeAllModel().blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer RED_SANDY_BLUE_MUSHROOM_SLAB = MakeRedSandySlab(BLUE_MUSHROOM_SLAB).slabModel(RED_SANDY_BLUE_MUSHROOM_PLANKS).blockTag(BlockTags.AXE_MINEABLE);

	public static final BlockContainer RED_SANDY_BROWN_MUSHROOM_PLANKS = MakeRedSandy(BROWN_MUSHROOM_PLANKS).flammable(5, 5).cubeAllModel().blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer RED_SANDY_BROWN_MUSHROOM_SLAB = MakeRedSandySlab(BROWN_MUSHROOM_SLAB).slabModel(RED_SANDY_BROWN_MUSHROOM_PLANKS).blockTag(BlockTags.AXE_MINEABLE);

	public static final BlockContainer RED_SANDY_CASSIA_PLANKS = MakeRedSandy(CASSIA_PLANKS).flammable(5, 5).cubeAllModel().blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer RED_SANDY_CASSIA_SLAB = MakeRedSandySlab(CASSIA_SLAB).slabModel(RED_SANDY_CASSIA_PLANKS).blockTag(BlockTags.AXE_MINEABLE);

	public static final BlockContainer RED_SANDY_CHARRED_PLANKS = MakeRedSandy(CHARRED_PLANKS).cubeAllModel().blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer RED_SANDY_CHARRED_SLAB = MakeRedSandySlab(CHARRED_SLAB).slabModel(RED_SANDY_CHARRED_PLANKS).blockTag(BlockTags.AXE_MINEABLE);

	public static final BlockContainer RED_SANDY_CHERRY_PLANKS = MakeRedSandy(CHERRY_PLANKS).flammable(5, 5).cubeAllModel().blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer RED_SANDY_CHERRY_SLAB = MakeRedSandySlab(CHERRY_SLAB).slabModel(RED_SANDY_CHERRY_PLANKS).blockTag(BlockTags.AXE_MINEABLE);

	public static final BlockContainer RED_SANDY_CRIMSON_PLANKS = MakeRedSandy(Blocks.CRIMSON_PLANKS).cubeAllModel().blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer RED_SANDY_CRIMSON_SLAB = MakeRedSandySlab(Blocks.CRIMSON_SLAB).slabModel(RED_SANDY_CRIMSON_PLANKS).blockTag(BlockTags.AXE_MINEABLE);

	public static final BlockContainer RED_SANDY_DARK_OAK_PLANKS = MakeRedSandy(Blocks.DARK_OAK_PLANKS).flammable(5, 5).cubeAllModel().blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer RED_SANDY_DARK_OAK_SLAB = MakeRedSandySlab(Blocks.DARK_OAK_SLAB).slabModel(RED_SANDY_DARK_OAK_PLANKS).blockTag(BlockTags.AXE_MINEABLE);

	public static final BlockContainer RED_SANDY_DOGWOOD_PLANKS = MakeRedSandy(DOGWOOD_PLANKS).flammable(5, 5).cubeAllModel().blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer RED_SANDY_DOGWOOD_SLAB = MakeRedSandySlab(DOGWOOD_SLAB).slabModel(RED_SANDY_DOGWOOD_PLANKS).blockTag(BlockTags.AXE_MINEABLE);

	public static final BlockContainer RED_SANDY_GILDED_PLANKS = MakeRedSandy(GILDED_PLANKS).cubeAllModel().blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer RED_SANDY_GILDED_SLAB = MakeRedSandySlab(GILDED_SLAB).slabModel(RED_SANDY_GILDED_PLANKS).blockTag(BlockTags.AXE_MINEABLE);

	public static final BlockContainer RED_SANDY_HAY_PLANKS = MakeRedSandy(HAY_PLANKS).flammable(5, 5).cubeAllModel().blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer RED_SANDY_HAY_SLAB = MakeRedSandySlab(HAY_SLAB).slabModel(RED_SANDY_HAY_PLANKS).blockTag(BlockTags.AXE_MINEABLE);

	public static final BlockContainer RED_SANDY_JUNGLE_PLANKS = MakeRedSandy(Blocks.JUNGLE_PLANKS).flammable(5, 5).cubeAllModel().blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer RED_SANDY_JUNGLE_SLAB = MakeRedSandySlab(Blocks.JUNGLE_SLAB).slabModel(RED_SANDY_JUNGLE_PLANKS).blockTag(BlockTags.AXE_MINEABLE);

	public static final BlockContainer RED_SANDY_MANGROVE_PLANKS = MakeRedSandy(MANGROVE_PLANKS).flammable(5, 5).cubeAllModel().blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer RED_SANDY_MANGROVE_SLAB = MakeRedSandySlab(MANGROVE_SLAB).slabModel(RED_SANDY_MANGROVE_PLANKS).blockTag(BlockTags.AXE_MINEABLE);

	public static final BlockContainer RED_SANDY_MUSHROOM_STEM_PLANKS = MakeRedSandy(MUSHROOM_STEM_PLANKS).flammable(5, 5).cubeAllModel().blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer RED_SANDY_MUSHROOM_STEM_SLAB = MakeRedSandySlab(MUSHROOM_STEM_SLAB).slabModel(RED_SANDY_MUSHROOM_STEM_PLANKS).blockTag(BlockTags.AXE_MINEABLE);

	public static final BlockContainer RED_SANDY_OAK_PLANKS = MakeRedSandy(Blocks.OAK_PLANKS).flammable(5, 5).cubeAllModel().blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer RED_SANDY_OAK_SLAB = MakeRedSandySlab(Blocks.OAK_SLAB).slabModel(RED_SANDY_OAK_PLANKS).blockTag(BlockTags.AXE_MINEABLE);

	public static final BlockContainer RED_SANDY_RED_MUSHROOM_PLANKS = MakeRedSandy(RED_MUSHROOM_PLANKS).flammable(5, 5).cubeAllModel().blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer RED_SANDY_RED_MUSHROOM_SLAB = MakeRedSandySlab(RED_MUSHROOM_SLAB).slabModel(RED_SANDY_RED_MUSHROOM_PLANKS).blockTag(BlockTags.AXE_MINEABLE);

	public static final BlockContainer RED_SANDY_SPRUCE_PLANKS = MakeRedSandy(Blocks.SPRUCE_PLANKS).flammable(5, 5).cubeAllModel().blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer RED_SANDY_SPRUCE_SLAB = MakeRedSandySlab(Blocks.SPRUCE_SLAB).slabModel(RED_SANDY_SPRUCE_PLANKS).blockTag(BlockTags.AXE_MINEABLE);

	public static final BlockContainer RED_SANDY_WARPED_PLANKS = MakeRedSandy(Blocks.WARPED_PLANKS).cubeAllModel().blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer RED_SANDY_WARPED_SLAB = MakeRedSandySlab(Blocks.WARPED_SLAB).slabModel(RED_SANDY_WARPED_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	//</editor-fold>
	//<editor-fold desc="Prismarine">
	public static final BlockContainer RED_SANDY_PRISMARINE = MakeRedSandy(Blocks.PRISMARINE).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer RED_SANDY_PRISMARINE_SLAB = MakeRedSandySlab(Blocks.PRISMARINE_SLAB).slabModel(RED_SANDY_PRISMARINE).blockTag(BlockTags.PICKAXE_MINEABLE);

	public static final BlockContainer RED_SANDY_SANDY_PRISMARINE = MakeRedSandy(SANDY_PRISMARINE).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer RED_SANDY_SANDY_PRISMARINE_SLAB = MakeRedSandySlab(SANDY_PRISMARINE_SLAB).slabModel(RED_SANDY_SANDY_PRISMARINE).blockTag(BlockTags.PICKAXE_MINEABLE);

	public static final BlockContainer RED_SANDY_PRISMARINE_BRICKS = MakeRedSandy(Blocks.PRISMARINE_BRICKS).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer RED_SANDY_PRISMARINE_BRICK_SLAB = MakeRedSandySlab(Blocks.PRISMARINE_BRICK_SLAB).slabModel(RED_SANDY_PRISMARINE_BRICKS).blockTag(BlockTags.PICKAXE_MINEABLE);

	public static final BlockContainer RED_SANDY_CHISELED_PRISMARINE_BRICKS = MakeRedSandy(CHISELED_PRISMARINE_BRICKS).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);

	public static final BlockContainer RED_SANDY_DARK_PRISMARINE = MakeRedSandy(Blocks.DARK_PRISMARINE).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer RED_SANDY_DARK_PRISMARINE_SLAB = MakeRedSandySlab(Blocks.DARK_PRISMARINE_SLAB).slabModel(RED_SANDY_DARK_PRISMARINE).blockTag(BlockTags.PICKAXE_MINEABLE);

	public static final BlockContainer RED_SANDY_SANDY_DARK_PRISMARINE = MakeRedSandy(SANDY_DARK_PRISMARINE).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer RED_SANDY_SANDY_DARK_PRISMARINE_SLAB = MakeRedSandySlab(SANDY_DARK_PRISMARINE_SLAB).slabModel(RED_SANDY_SANDY_DARK_PRISMARINE).blockTag(BlockTags.PICKAXE_MINEABLE);
	//</editor-fold>
	//<editor-fold desc="Purpur">
	public static final BlockContainer RED_SANDY_PURPUR_BLOCK = MakeRedSandy(Blocks.PURPUR_BLOCK).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer RED_SANDY_PURPUR_SLAB = MakeRedSandySlab(Blocks.PURPUR_SLAB).slabModel(RED_SANDY_PURPUR_BLOCK).blockTag(BlockTags.PICKAXE_MINEABLE);

	public static final BlockContainer RED_SANDY_CHISELED_PURPUR = MakeRedSandy(CHISELED_PURPUR).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer RED_SANDY_CHISELED_PURPUR_SLAB = MakeRedSandySlab(CHISELED_PURPUR_SLAB).slabModel(RED_SANDY_CHISELED_PURPUR).blockTag(BlockTags.PICKAXE_MINEABLE);

	public static final BlockContainer RED_SANDY_SANDY_CHISELED_PURPUR = MakeRedSandy(SANDY_CHISELED_PURPUR).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer RED_SANDY_SANDY_CHISELED_PURPUR_SLAB = MakeRedSandySlab(SANDY_CHISELED_PURPUR_SLAB).slabModel(RED_SANDY_SANDY_CHISELED_PURPUR).blockTag(BlockTags.PICKAXE_MINEABLE);

	public static final BlockContainer RED_SANDY_SMOOTH_PURPUR = MakeRedSandy(SMOOTH_PURPUR).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer RED_SANDY_SMOOTH_PURPUR_SLAB = MakeRedSandySlab(SMOOTH_PURPUR_SLAB).slabModel(RED_SANDY_SMOOTH_PURPUR).blockTag(BlockTags.PICKAXE_MINEABLE);

	public static final BlockContainer RED_SANDY_PURPUR_BRICKS = MakeRedSandy(PURPUR_BRICKS).cubeAllModel().blockTag(BlockTags.PICKAXE_MINEABLE);
	public static final BlockContainer RED_SANDY_PURPUR_BRICK_SLAB = MakeRedSandySlab(PURPUR_BRICK_SLAB).slabModel(RED_SANDY_PURPUR_BRICKS).blockTag(BlockTags.PICKAXE_MINEABLE);
	//</editor-fold>
	//Entity
	public static final BlockEntityType<RedSandyBlockEntity> RED_SANDY_BLOCK_ENTITY = FabricBlockEntityTypeBuilder.create(RedSandyBlockEntity::new).build();
	//</editor-fold>

	//<editor-fold desc="Cauldrons">
	public static final Block BLOOD_CAULDRON = new BloodCauldronBlock(FabricBlockSettings.copyOf(Blocks.CAULDRON));
	public static final Block MUD_CAULDRON = new MudCauldronBlock(FabricBlockSettings.copyOf(Blocks.CAULDRON));
	public static final Block MILK_CAULDRON = new MilkCauldronBlock(0, Block.Settings.copy(Blocks.WATER_CAULDRON));
	public static final Block COTTAGE_CHEESE_CAULDRON = new MilkCauldronBlock(1, Block.Settings.copy(MILK_CAULDRON));
	public static final Block CHEESE_CAULDRON = new MilkCauldronBlock(2, Block.Settings.copy(MILK_CAULDRON));
	//</editor-fold>

	//<editor-fold desc="Blood Types">
	public static final BloodType AXOLOTL_BLOOD_TYPE = BloodType.Register("axolotl");
	public static final BloodType BAT_BLOOD_TYPE = BloodType.Register("bat");
	public static final BloodType BEE_BLOOD_TYPE = BloodType.Register("bee");
	public static final BloodType CAT_BLOOD_TYPE = BloodType.Register("cat");
	public static final BloodType CAMEL_BLOOD_TYPE = BloodType.Register("camel");
	public static final BloodType CAVE_SPIDER_BLOOD_TYPE = BloodType.Register("cave_spider");
	public static final BloodType CHICKEN_BLOOD_TYPE = BloodType.Register("chicken");
	public static final BloodType COW_BLOOD_TYPE = BloodType.Register("cow");
	public static final BloodType CREEPER_BLOOD_TYPE = BloodType.Register("creeper");
	public static final BloodType DOLPHIN_BLOOD_TYPE = BloodType.Register("dolphin");
	public static final BloodType DONKEY_BLOOD_TYPE = BloodType.Register("donkey");
	public static final BloodType ENDER_DRAGON_BLOOD_TYPE = BloodType.Register("ender_dragon");
	public static final BloodType ENDERMAN_BLOOD_TYPE = BloodType.Register("enderman");
	public static final BloodType ENDERMITE_BLOOD_TYPE = BloodType.Register("endermite");
	public static final BloodType FISH_BLOOD_TYPE = BloodType.Register("fish");
	public static final BloodType FOX_BLOOD_TYPE = BloodType.Register("fox");
	public static final BloodType FROG_BLOOD_TYPE = BloodType.Register("frog");
	public static final BloodType GLOW_SQUID_BLOOD_TYPE = BloodType.Register("glow_squid");
	public static final BloodType GOAT_BLOOD_TYPE = BloodType.Register("goat");
	public static final BloodType GUARDIAN_BLOOD_TYPE = BloodType.Register("guardian");
	public static final BloodType HOGLIN_BLOOD_TYPE = BloodType.Register("hoglin");
	public static final BloodType HORSE_BLOOD_TYPE = BloodType.Register("horse");
	public static final BloodType LLAMA_BLOOD_TYPE = BloodType.Register("llama");
	public static final BloodType MAGMA_CREAM_BLOOD_TYPE = BloodType.Register("magma_cream");
	public static final BloodType MOOSHROOM_BLOOD_TYPE = BloodType.Register("mooshroom");
	public static final BloodType MULE_BLOOD_TYPE = BloodType.Register("mule");
	public static final BloodType OCELOT_BLOOD_TYPE = BloodType.Register("ocelot");
	public static final BloodType PANDA_BLOOD_TYPE = BloodType.Register("panda");
	public static final BloodType PARROT_BLOOD_TYPE = BloodType.Register("parrot");
	public static final BloodType PHANTOM_BLOOD_TYPE = BloodType.Register("phantom");
	public static final BloodType PIG_BLOOD_TYPE = BloodType.Register("pig");
	public static final BloodType PIGLIN_BLOOD_TYPE = BloodType.Register("piglin");
	public static final BloodType POLAR_BEAR_BLOOD_TYPE = BloodType.Register("polar_bear");
	public static final BloodType PUFFERFISH_BLOOD_TYPE = BloodType.Register("pufferfish");
	public static final BloodType RABBIT_BLOOD_TYPE = BloodType.Register("rabbit");
	public static final BloodType RAVAGER_BLOOD_TYPE = BloodType.Register("ravager");
	public static final BloodType SHEEP_BLOOD_TYPE = BloodType.Register("sheep");
	public static final BloodType SHULKER_BLOOD_TYPE = BloodType.Register("shulker");
	public static final BloodType SILVERFISH_BLOOD_TYPE = BloodType.Register("silverfish");
	public static final BloodType SLIME_BLOOD_TYPE = BloodType.Register("slime");
	public static final BloodType SNIFFER_BLOOD_TYPE = BloodType.Register("sniffer");
	public static final BloodType SPIDER_BLOOD_TYPE = BloodType.Register("spider");
	public static final BloodType SQUID_BLOOD_TYPE = BloodType.Register("squid");
	public static final BloodType STRIDER_BLOOD_TYPE = BloodType.Register("strider");
	public static final BloodType TURTLE_BLOOD_TYPE = BloodType.Register("turtle");
	public static final BloodType VILLAGER_BLOOD_TYPE = BloodType.Register("villager");
	public static final BloodType WARDEN_BLOOD_TYPE = BloodType.Register("warden");
	public static final BloodType WOLF_BLOOD_TYPE = BloodType.Register("wolf");
	public static final BloodType ZOGLIN_BLOOD_TYPE = BloodType.Register("zoglin");
	public static final BloodType ZOMBIE_BLOOD_TYPE = BloodType.Register("zombie");
	public static final BloodType ZOMBIE_HORSE_BLOOD_TYPE = BloodType.Register("zombie_horse");
	public static final BloodType ZOMBIE_VILLAGER_BLOOD_TYPE = BloodType.Register("zombie_villager");
	public static final BloodType ZOMBIFIED_PIGLIN_BLOOD_TYPE = BloodType.Register("zombified_piglin");
	//</editor-fold>
	//<editor-fold desc="Mod Mob Blood Types">
	public static final BloodType HEDGEHOG_BLOOD_TYPE = BloodType.Register("hedgehog");
	public static final BloodType RACCOON_BLOOD_TYPE = BloodType.Register("raccoon");
	public static final BloodType RED_PANDA_BLOOD_TYPE = BloodType.Register("red_panda");
	public static final BloodType FLOWER_COW_BLOOD_TYPE = BloodType.Register("flower_cow");
	public static final BloodType PIRANHA_BLOOD_TYPE = BloodType.Register("piranha");
	public static final BloodType BLUE_SLIME_BLOOD_TYPE = BloodType.Register("blue_slime");
	public static final BloodType PINK_SLIME_BLOOD_TYPE = BloodType.Register("pink_slime");
	//</editor-fold>
	//<editor-fold desc="Special Blood Types">
	public static final BloodType AVIAN_BLOOD_TYPE = BloodType.Register(ModId.NAMESPACE, "avian");
	public static final BloodType NEPHAL_BLOOD_TYPE = BloodType.Register(ModId.NAMESPACE, "nephal");
	public static final BloodType VAMPIRE_BLOOD_TYPE = BloodType.Register(ModId.NAMESPACE, "vampire");
	//</editor-fold>
	//<editor-fold desc="Syringes">
	public static final Item SYRINGE = HandheldItem(new EmptySyringeItem(ItemSettings().maxCount(16)));
	public static final Item DIRTY_SYRINGE = MakeHandheldItem();
	public static final Item BLOOD_SYRINGE = HandheldItem(new BloodSyringeItem());
	//Special Syringes
	public static final Item CONFETTI_SYRINGE = HandheldItem(new ConfettiSyringeItem());
	public static final Item DRAGON_BREATH_SYRINGE = HandheldItem(new BaseSyringeItem((PlayerEntity user, ItemStack stack, LivingEntity entity) -> {
		BloodType bloodType = BloodType.Get(entity);
		if (bloodType == ENDER_DRAGON_BLOOD_TYPE) BloodSyringeItem.heal(entity, 4);
		else if (ModConfig.REGISTER_RYFT_MOD && bloodType == RyftMod.DRACONIC_BLOOD_TYPE) BloodSyringeItem.heal(entity, 4);
		else entity.damage(ModDamageSource.Injected("dragon_breath", user), 4);
	}));
	public static final Item EXPERIENCE_SYRINGE = HandheldItem(new ExperienceSyringeItem());
	public static final Item HONEY_SYRINGE = HandheldItem(new BloodSyringeItem((PlayerEntity user, ItemStack stack, LivingEntity entity) -> {
		BloodType bloodType = BloodType.Get(entity);
		if (bloodType == BloodType.HONEY || bloodType == BEE_BLOOD_TYPE) BloodSyringeItem.heal(entity, 1);
		else if (ModConfig.REGISTER_HAVEN_MOD && bloodType == HavenMod.BEE_ENDERMAN_BLOOD_TYPE) BloodSyringeItem.heal(entity, 1);
		else entity.damage(ModDamageSource.Injected(user, BloodType.HONEY), 1);
		if (!entity.world.isClient) entity.removeStatusEffect(StatusEffects.POISON);
	}));
	public static final Item LAVA_SYRINGE = HandheldItem(new BloodSyringeItem((PlayerEntity user, ItemStack stack, LivingEntity entity) -> {
		BloodType bloodType = BloodType.Get(entity);
		if (bloodType == BloodType.LAVA || bloodType == MAGMA_CREAM_BLOOD_TYPE) BloodSyringeItem.heal(entity, 1);
		else if (bloodType == BloodType.WATER) entity.damage(ModDamageSource.Injected("lava", user), 8);
		else if (ModConfig.REGISTER_HAVEN_MOD && bloodType == HavenMod.NETHER_ROYALTY_BLOOD_TYPE) BloodSyringeItem.heal(entity, 0);
		else {
			entity.setOnFireFor(20000);
			entity.damage(ModDamageSource.Injected(user, BloodType.LAVA), 4);
		}
	}));
	public static final Item MAGMA_CREAM_SYRINGE = HandheldItem(new BloodSyringeItem((PlayerEntity user, ItemStack stack, LivingEntity entity) -> {
		BloodType bloodType = BloodType.Get(entity);
		if (bloodType == BloodType.LAVA || bloodType == MAGMA_CREAM_BLOOD_TYPE) BloodSyringeItem.heal(entity, 1);
		else {
			if (bloodType == SLIME_BLOOD_TYPE || bloodType == BLUE_SLIME_BLOOD_TYPE || bloodType == PINK_SLIME_BLOOD_TYPE) BloodSyringeItem.heal(entity, 1);
			else if (ModConfig.REGISTER_HAVEN_MOD && bloodType == HavenMod.SLUDGE_BLOOD_TYPE) BloodSyringeItem.heal(entity, 1);
			else entity.damage(ModDamageSource.Injected(user, MAGMA_CREAM_BLOOD_TYPE), 1);
			entity.setOnFireFor(5);
		}
	}));
	public static final Item MUD_SYRINGE = HandheldItem(new BloodSyringeItem((PlayerEntity user, ItemStack stack, LivingEntity entity) -> {
		BloodType bloodType = BloodType.Get(entity);
		if (bloodType == BloodType.MUD || bloodType == BloodType.WATER) BloodSyringeItem.heal(entity, 1);
		else entity.damage(ModDamageSource.Injected(user, BloodType.MUD), 2);
	}));
	public static final Item SAP_SYRINGE = HandheldItem(new BloodSyringeItem((PlayerEntity user, ItemStack stack, LivingEntity entity) -> {
		BloodType bloodType = BloodType.Get(entity);
		if (bloodType == BloodType.SAP) BloodSyringeItem.heal(entity, 1);
		if (!entity.world.isClient) entity.removeStatusEffect(StatusEffects.NAUSEA);
	}));
	public static final Item SLIME_SYRINGE = HandheldItem(new SlimeSyringeItem());
	public static final Item BLUE_SLIME_SYRINGE = HandheldItem(new SlimeSyringeItem());
	public static final Item PINK_SLIME_SYRINGE = HandheldItem(new SlimeSyringeItem());
	public static final Item WATER_SYRINGE = HandheldItem(new BloodSyringeItem((PlayerEntity user, ItemStack stack, LivingEntity entity) -> {
		BloodType bloodType = BloodType.Get(entity);
		//Put out fires
		if (entity.isOnFire()) entity.setOnFire(false);
		//Heal or Hurt
		if (bloodType == BloodType.WATER || bloodType == BloodType.MUD) BloodSyringeItem.heal(entity, 1);
		else {
			int damage = 1;
			if (bloodType == BloodType.LAVA) damage = 8;
			else if (bloodType == ENDERMAN_BLOOD_TYPE) damage = 4;
			else if (bloodType == ENDER_DRAGON_BLOOD_TYPE) damage = 4;
			else if (ModConfig.REGISTER_HAVEN_MOD) {
				if (bloodType == HavenMod.NETHER_ROYALTY_BLOOD_TYPE) damage = 4;
			}
			entity.damage(ModDamageSource.Injected(user, BloodType.WATER), damage);
		}
	}));
	//Special Blood Types
	public static final Item NEPHAL_BLOOD_SYRINGE = ParentedItem(new BloodSyringeItem((PlayerEntity user, ItemStack stack, LivingEntity entity) -> {
		BloodType bloodType = BloodType.Get(entity);
		if (bloodType == NEPHAL_BLOOD_TYPE) entity.heal(1);
		else if (bloodType == VAMPIRE_BLOOD_TYPE) {
			entity.heal(1);
			entity.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 200, 1));
		}
		else entity.damage(ModDamageSource.Injected(user, bloodType), 1);
	}), BLOOD_SYRINGE);
	public static final Item VAMPIRE_BLOOD_SYRINGE = ParentedItem(new BloodSyringeItem((PlayerEntity user, ItemStack stack, LivingEntity entity) -> {
		BloodType bloodType = BloodType.Get(entity);
		if (bloodType == VAMPIRE_BLOOD_TYPE) entity.heal(1);
		else {
			entity.damage(ModDamageSource.Injected(user, bloodType), 1);
			user.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 100, 1));
		}
	}), BLOOD_SYRINGE);
	//</editor-fold>

	//<editor-fold desc="Javelins & Custom Tridents">
	public static final JavelinItem JAVELIN = GeneratedItem(new JavelinItem(4.0, -2.9f, ModId.ID("textures/entity/projectiles/javelin.png"), ItemSettings().maxDamage(250)));
	public static final EntityType<JavelinEntity> JAVELIN_ENTITY = FabricEntityTypeBuilder.<JavelinEntity>create(SpawnGroup.MISC, JavelinEntity::new).dimensions(EntityDimensions.fixed(0.5f, 0.5f)).trackRangeChunks(4).trackedUpdateRate(20).build();

	public static final JavelinItem AMETHYST_TRIDENT = GeneratedItem(new AmethystTridentItem(8.0, -2.9f, ModId.ID("textures/entity/projectiles/amethyst_trident.png"), ItemSettings().maxDamage(250)));
	private static class AmethystTridentItem extends JavelinItem {
		public AmethystTridentItem(double attackDamage, float attackSpeed, Identifier texture, Settings settings) {
			super(attackDamage, attackSpeed, texture, settings);
			this.FACTORY = AmethystTridentEntity::new;
		}
	}
	public static final EntityType<JavelinEntity> AMETHYST_TRIDENT_ENTITY = FabricEntityTypeBuilder.<JavelinEntity>create(SpawnGroup.MISC, AmethystTridentEntity::new).dimensions(EntityDimensions.fixed(0.5f, 0.5f)).trackRangeChunks(4).trackedUpdateRate(20).build();
	private static class AmethystTridentEntity extends JavelinEntity {
		public AmethystTridentEntity(EntityType<? extends JavelinEntity> entityType, World world) {
			super(entityType, world);
			this.item = AMETHYST_TRIDENT;
		}
		public AmethystTridentEntity(World world, LivingEntity owner, ItemStack stack) {
			super(AMETHYST_TRIDENT_ENTITY, world, owner, stack, AMETHYST_TRIDENT);
		}
	}
	//</editor-fold>

	//<editor-fold desc="Mod Buckets">
	public static final Item PIRANHA_BUCKET = GeneratedItem(new EntityBucketItem(PIRANHA_ENTITY, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, ItemSettings().maxCount(1)));
	//</editor-fold>
	//<editor-fold desc="Pouches">
	public static final Item CHICKEN_POUCH = GeneratedItem(new ChickenPouchItem(EntityType.CHICKEN, ItemSettings().maxCount(1)).dispensible());
	public static final Item RABBIT_POUCH = GeneratedItem(new EntityPouchItem(EntityType.RABBIT, ModSoundEvents.ITEM_POUCH_EMPTY_RABBIT, ItemSettings().maxCount(1)).dispensible());
	public static final Item PARROT_POUCH = GeneratedItem(new EntityPouchItem(EntityType.PARROT, ModSoundEvents.ITEM_POUCH_EMPTY_PARROT, ItemSettings().maxCount(1)).dispensible());
	public static final Item ENDERMITE_POUCH = GeneratedItem(new EntityPouchItem(EntityType.ENDERMITE, ModSoundEvents.ITEM_POUCH_EMPTY_ENDERMITE, ItemSettings().maxCount(1)).dispensible());
	public static final Item SILVERFISH_POUCH = GeneratedItem(new EntityPouchItem(EntityType.SILVERFISH, ModSoundEvents.ITEM_POUCH_EMPTY_SILVERFISH, ItemSettings().maxCount(1)).dispensible());
	//</editor-fold>
	//<editor-fold desc="Mod Pouches">
	public static final Item HEDGEHOG_POUCH = GeneratedItem(new EntityPouchItem(HEDGEHOG_ENTITY, ModSoundEvents.ITEM_POUCH_EMPTY_HEDGEHOG, ItemSettings().maxCount(1)).dispensible());
	//</editor-fold>
	//<editor-fold desc="Summoning Arrows">
	public static final ArrowContainer AXOLOTL_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.AXOLOTL, 16499171, 10890612);
	public static final ArrowContainer BAT_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.BAT, 4996656, 986895);
	public static final ArrowContainer BEE_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.BEE, 15582019, 4400155);
	public static final ArrowContainer BLAZE_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.BLAZE, 16167425, 16775294);
	public static final ArrowContainer CAT_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.CAT, 15714446, 9794134);
	public static final ArrowContainer CAMEL_SUMMONING_ARROW = ArrowContainer.Summoning(CAMEL_ENTITY, 16565097, 13341495);
	public static final ArrowContainer CAVE_SPIDER_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.CAVE_SPIDER, 803406, 11013646);
	public static final ArrowContainer CHICKEN_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.CHICKEN, 0xA1A1A1, 0xFF0000);
	public static final ArrowContainer COD_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.COD, 12691306, 15058059);
	public static final ArrowContainer COW_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.COW, 4470310, 0xA1A1A1);
	public static final ArrowContainer CREEPER_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.CREEPER, 894731, 0);
	public static final ArrowContainer DOLPHIN_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.DOLPHIN, 2243405, 0xF9F9F9);
	public static final ArrowContainer DONKEY_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.DONKEY, 5457209, 8811878);
	public static final ArrowContainer DROWNED_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.DROWNED, 9433559, 7969893);
	public static final ArrowContainer ELDER_GUARDIAN_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.ELDER_GUARDIAN, 13552826, 7632531);
	public static final ArrowContainer ENDERMAN_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.ENDERMAN, 0x161616, 0);
	public static final ArrowContainer ENDERMITE_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.ENDERMITE, 0x161616, 0x6E6E6E);
	public static final ArrowContainer EVOKER_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.EVOKER, 0x959B9B, 1973274);
	public static final ArrowContainer FOX_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.FOX, 14005919, 13396256);
	public static final ArrowContainer FROG_SUMMONING_ARROW = ArrowContainer.Summoning(FROG_ENTITY, 13661252, 0xFFC77C);
	public static final ArrowContainer GHAST_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.GHAST, 0xF9F9F9, 0xBCBCBC);
	public static final ArrowContainer GIANT_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.GIANT, 44975, 7969893);
	public static final ArrowContainer GLOW_SQUID_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.GLOW_SQUID, 611926, 8778172);
	public static final ArrowContainer GOAT_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.GOAT, 10851452, 5589310);
	public static final ArrowContainer GUARDIAN_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.GUARDIAN, 5931634, 15826224);
	public static final ArrowContainer HOGLIN_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.HOGLIN, 13004373, 6251620);
	public static final ArrowContainer HORSE_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.HORSE, 12623485, 0xEEE500);
	public static final ArrowContainer HUSK_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.HUSK, 7958625, 15125652);
	public static final ArrowContainer IRON_GOLEM_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.IRON_GOLEM, 0xB1B0B0, 0xE3901D);
	public static final ArrowContainer LLAMA_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.LLAMA, 12623485, 10051392);
	public static final ArrowContainer MAGMA_CUBE_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.MAGMA_CUBE, 0x340000, 0xFCFC00);
	public static final ArrowContainer MOOSHROOM_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.MOOSHROOM, 10489616, 0xB7B7B7);
	public static final ArrowContainer MULE_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.MULE, 1769984, 5321501);
	public static final ArrowContainer OCELOT_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.OCELOT, 15720061, 5653556);
	public static final ArrowContainer PANDA_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.PANDA, 0xE7E7E7, 0x1B1B22);
	public static final ArrowContainer PARROT_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.PARROT, 894731, 0xFF0000);
	public static final ArrowContainer PHANTOM_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.PHANTOM, 4411786, 0x88FF00);
	public static final ArrowContainer PIG_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.PIG, 15771042, 14377823);
	public static final ArrowContainer PIGLIN_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.PIGLIN, 10051392, 16380836);
	public static final ArrowContainer PIGLIN_BRUTE_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.PIGLIN_BRUTE, 5843472, 16380836);
	public static final ArrowContainer PILLAGER_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.PILLAGER, 5451574, 0x959B9B);
	public static final ArrowContainer POLAR_BEAR_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.POLAR_BEAR, 0xF2F2F2, 0x959590);
	public static final ArrowContainer PUFFERFISH_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.PUFFERFISH, 16167425, 3654642);
	public static final ArrowContainer RABBIT_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.RABBIT, 10051392, 7555121);
	public static final ArrowContainer RAVAGER_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.RAVAGER, 7697520, 5984329);
	public static final ArrowContainer SALMON_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.SALMON, 10489616, 951412);
	public static final ArrowContainer SHEEP_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.SHEEP, 0xE7E7E7, 0xFFB5B5);
	public static final ArrowContainer SHULKER_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.SHULKER, 9725844, 5060690);
	public static final ArrowContainer SILVERFISH_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.SILVERFISH, 0x6E6E6E, 0x303030);
	public static final ArrowContainer SKELETON_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.SKELETON, 0xC1C1C1, 0x494949);
	public static final ArrowContainer SKELETON_HORSE_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.SKELETON_HORSE, 6842447, 15066584);
	public static final ArrowContainer SLIME_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.SLIME, 5349438, 8306542);
	public static final ArrowContainer SNIFFER_SUMMONING_ARROW = ArrowContainer.Summoning(SNIFFER_ENTITY, 9840944, 5085536);
	public static final ArrowContainer SNOW_GOLEM_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.SNOW_GOLEM, 0xF0FDFD, 0xE3901D);
	public static final ArrowContainer SPIDER_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.SPIDER, 3419431, 11013646);
	public static final ArrowContainer SQUID_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.SQUID, 2243405, 7375001);
	public static final ArrowContainer STRAY_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.STRAY, 0x617677, 0xDDEAEA);
	public static final ArrowContainer STRIDER_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.STRIDER, 10236982, 0x4D494D);
	public static final ArrowContainer TADPOLE_SUMMONING_ARROW = ArrowContainer.Summoning(TADPOLE_ENTITY, 7164733, 1444352);
	public static final ArrowContainer TRADER_LLAMA_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.TRADER_LLAMA, 15377456, 4547222);
	public static final ArrowContainer TROPICAL_FISH_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.TROPICAL_FISH, 15690005, 0xFFF9EF);
	public static final ArrowContainer TURTLE_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.TURTLE, 0xE7E7E7, 44975);
	public static final ArrowContainer VEX_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.VEX, 8032420, 15265265);
	public static final ArrowContainer VILLAGER_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.VILLAGER, 5651507, 12422002);
	public static final ArrowContainer VINDICATOR_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.VINDICATOR, 0x959B9B, 2580065);
	public static final ArrowContainer WANDERING_TRADER_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.WANDERING_TRADER, 4547222, 15377456);
	public static final ArrowContainer WARDEN_SUMMONING_ARROW = ArrowContainer.Summoning(WARDEN_ENTITY, 1001033, 3790560);
	public static final ArrowContainer WITCH_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.WITCH, 0x340000, 5349438);
	public static final ArrowContainer WITHER_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.WITHER, 0x141414, 0x474D4D);
	public static final ArrowContainer WITHER_SKELETON_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.WITHER_SKELETON, 0x141414, 0x474D4D);
	public static final ArrowContainer WOLF_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.WOLF, 0xD7D3D3, 13545366);
	public static final ArrowContainer ZOGLIN_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.ZOGLIN, 13004373, 0xE6E6E6);
	public static final ArrowContainer ZOMBIE_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.ZOMBIE, 44975, 7969893);
	public static final ArrowContainer ZOMBIE_HORSE_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.ZOMBIE_HORSE, 3232308, 9945732);
	public static final ArrowContainer ZOMBIE_VILLAGER_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.ZOMBIE_VILLAGER, 5651507, 7969893);
	public static final ArrowContainer ZOMBIFIED_PIGLIN_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.ZOMBIFIED_PIGLIN, 15373203, 5009705);
	//</editor-fold>
	//<editor-fold desc="Mod Mob Summoning Arrows">
	public static final ArrowContainer MELON_GOLEM_SUMMONING_ARROW = ArrowContainer.Summoning(MELON_GOLEM_ENTITY, 0xF0FDFD, 0x7B7F16);
	public static final ArrowContainer BONE_SPIDER_SUMMONING_ARROW = ArrowContainer.Summoning(BONE_SPIDER_ENTITY, 0x270F19, 0x632FB7);
	public static final ArrowContainer ICY_SPIDER_SUMMONING_ARROW = ArrowContainer.Summoning(ICY_SPIDER_ENTITY, 0x045480, 0x3CFEFB);
	public static final ArrowContainer SLIME_SPIDER_SUMMONING_ARROW = ArrowContainer.Summoning(SLIME_SPIDER_ENTITY, 5349438, 11013646);
	public static final ArrowContainer HEDGEHOG_SUMMONING_ARROW = ArrowContainer.Summoning(HEDGEHOG_ENTITY, 11440263, 7558239);
	public static final ArrowContainer RACCOON_SUMMONING_ARROW = ArrowContainer.Summoning(RACCOON_ENTITY, 0x646464, 0x0B0B0B);
	public static final ArrowContainer RED_PANDA_SUMMONING_ARROW = ArrowContainer.Summoning(RED_PANDA_ENTITY, 0xC35330, 0x0B0B0B);
	public static final ArrowContainer JUMPING_SPIDER_SUMMONING_ARROW = ArrowContainer.Summoning(JUMPING_SPIDER_ENTITY, 0x281206, 0x3C0202);
	public static final ArrowContainer RED_PHANTOM_SUMMONING_ARROW = ArrowContainer.Summoning(RED_PHANTOM_ENTITY, 0x881214, 0x00E5F9);
	public static final ArrowContainer PIRANHA_SUMMONING_ARROW = ArrowContainer.Summoning(PIRANHA_ENTITY, 4877153, 11762012);
	public static final ArrowContainer TROPICAL_SLIME_SUMMONING_ARROW = ArrowContainer.Summoning(TROPICAL_SLIME_ENTITY, 0x8FD3FF, 0x345C7A);
	public static final ArrowContainer PINK_SLIME_SUMMONING_ARROW = ArrowContainer.Summoning(PINK_SLIME_ENTITY, 0xE0A3EE, 0xB85ECE);
	public static final ArrowContainer FANCY_CHICKEN_SUMMONING_ARROW = ArrowContainer.Summoning(FANCY_CHICKEN_ENTITY, 0xB788CB, 0xF7B035);
	public static final ArrowContainer SLIME_CHICKEN_SUMMONING_ARROW = ArrowContainer.Summoning(SLIME_CHICKEN_ENTITY, 5349438, 0xFF0000);
	public static final ArrowContainer SLIME_COW_SUMMONING_ARROW = ArrowContainer.Summoning(SLIME_COW_ENTITY, 5349438, 0x3F3024);
	public static final ArrowContainer SLIME_HORSE_SUMMONING_ARROW = ArrowContainer.Summoning(SLIME_HORSE_ENTITY, 5349438, 0xCFC700);
	public static final ArrowContainer BLUE_MOOSHROOM_SUMMONING_ARROW = ArrowContainer.Summoning(BLUE_MOOSHROOM_ENTITY, 0x0D6794, 0x929292);
	public static final ArrowContainer NETHER_MOOSHROOM_SUMMONING_ARROW = ArrowContainer.Summoning(NETHER_MOOSHROOM_ENTITY, 0x871116, 0xFF6500);
	public static final ArrowContainer MOOBLOOM_SUMMONING_ARROW = ArrowContainer.Summoning(MOOBLOOM_ENTITY, 0xFDD500, 0xFDF7BA);
	public static final ArrowContainer MOOLIP_SUMMONING_ARROW = ArrowContainer.Summoning(MOOLIP_ENTITY, 0xFFA9C2, 0xFFE4E4);
	public static final ArrowContainer MOOBLOSSOM_SUMMONING_ARROW = ArrowContainer.Summoning(MOOBLOSSOM_ENTITY, 0xDF317C, 0x994369);
	public static final ArrowContainer MOSSY_SHEEP_SUMMONING_ARROW = ArrowContainer.Summoning(MOSSY_SHEEP_ENTITY, 0xFFFFFF, 0x6C8031);
	public static final ArrowContainer RAINBOW_SHEEP_SUMMONING_ARROW = ArrowContainer.Summoning(RAINBOW_SHEEP_ENTITY, 0xFFFFFF, 0xFFFFFF).generatedItemModel();
	public static final ArrowContainer SLIME_CREEPER_SUMMONING_ARROW = ArrowContainer.Summoning(SLIME_CREEPER_ENTITY, 5349438, 0);
	public static final ArrowContainer MOSSY_SKELETON_SUMMONING_ARROW = ArrowContainer.Summoning(MOSSY_SKELETON_ENTITY, 0xD6D7C6, 0x526121);
	public static final ArrowContainer SLIMY_SKELETON_SUMMONING_ARROW = ArrowContainer.Summoning(SLIMY_SKELETON_ENTITY, 5349438, 0x494949);
	public static final ArrowContainer SUNKEN_SKELETON_SUMMONING_ARROW = ArrowContainer.Summoning(SUNKEN_SKELETON_ENTITY, 0xD6D0C9, 0x98439E);
	public static final ArrowContainer FROZEN_ZOMBIE_SUMMONING_ARROW = ArrowContainer.Summoning(FROZEN_ZOMBIE_ENTITY, 0x78BEDE, 0x5A8684);
	public static final ArrowContainer JUNGLE_ZOMBIE_SUMMONING_ARROW = ArrowContainer.Summoning(JUNGLE_ZOMBIE_ENTITY, 0x67BC97, 0x5A8646);
	public static final ArrowContainer SLIME_ZOMBIE_SUMMONING_ARROW = ArrowContainer.Summoning(SLIME_ZOMBIE_ENTITY, 5349438, 0x5A8646);
	public static final ArrowContainer ICEOLOGER_SUMMONING_ARROW = ArrowContainer.Summoning(ICEOLOGER_ENTITY, 5349438, 0x5A8646);
	public static final ArrowContainer MAGE_SUMMONING_ARROW = ArrowContainer.Summoning(MAGE_ENTITY, 5349438, 0x5A1949);
	public static final ArrowContainer JOLLY_LLAMA_SUMMONING_ARROW = ArrowContainer.Summoning(JOLLY_LLAMA_ENTITY, 12623485, 0xFF0000);
	//</editor-fold>

	//Worldgen
	//<editor-fold desc="Ruby Ore">
	public static ModConfiguredFeature<?, ?> OVERWORLD_RUBY_ORE_CONFIGURED = new ModConfiguredFeature<>("ore_ruby_configured", new ConfiguredFeature<>(
			Feature.ORE, new OreFeatureConfig(List.of(
			OreFeatureConfig.createTarget(OreFeatureConfig.Rules.STONE_ORE_REPLACEABLES, RUBY_ORE.asBlock().getDefaultState()),
			OreFeatureConfig.createTarget(OreFeatureConfig.Rules.DEEPSLATE_ORE_REPLACEABLES, DEEPSLATE_RUBY_ORE.asBlock().getDefaultState())),
			7)
	));
	//</editor-fold>
	//<editor-fold desc="Sapphire Ore">
	public static ModConfiguredFeature<?, ?> OVERWORLD_SAPPHIRE_ORE_CONFIGURED = new ModConfiguredFeature<>("ore_ruby_configured", new ConfiguredFeature<>(
			Feature.ORE, new OreFeatureConfig(List.of(
			OreFeatureConfig.createTarget(OreFeatureConfig.Rules.STONE_ORE_REPLACEABLES, SAPPHIRE_ORE.asBlock().getDefaultState()),
			OreFeatureConfig.createTarget(OreFeatureConfig.Rules.DEEPSLATE_ORE_REPLACEABLES, DEEPSLATE_SAPPHIRE_ORE.asBlock().getDefaultState())),
			5)
	));
	//</editor-fold>
	//<editor-fold desc="Mangrove Swamp">
	public static final Feature<MangroveTreeFeatureConfig> MANGROVE_TREE_FEATURE = new MangroveTreeFeature(MangroveTreeFeatureConfig.CODEC);

	public static final ModConfiguredFeature<MangroveTreeFeatureConfig, ?> MANGROVE_CONFIGURED = new ModConfiguredFeature<>("minecraft:mangrove", new ConfiguredFeature<>(MANGROVE_TREE_FEATURE, new MangroveTreeFeatureConfig(false)));
	public static final ModConfiguredFeature<MangroveTreeFeatureConfig, ?> TALL_MANGROVE_CONFIGURED = new ModConfiguredFeature<>("minecraft:tall_mangrove", new ConfiguredFeature<>(MANGROVE_TREE_FEATURE, new MangroveTreeFeatureConfig(true)));
	//</editor-fold>
	//<editor-fold desc="Cherry Grove">
	public static final Feature<CherryTreeFeatureConfig> CHERRY_TREE_FEATURE = new CherryTreeFeature(CherryTreeFeatureConfig.CODEC);
	public static final ModConfiguredFeature<CherryTreeFeatureConfig, ?> CHERRY_CONFIGURED = new ModConfiguredFeature<>("minecraft:cherry", new ConfiguredFeature<>(CHERRY_TREE_FEATURE, new CherryTreeFeatureConfig(false)));
	public static final ModConfiguredFeature<CherryTreeFeatureConfig, ?> CHERRY_BEES_005_CONFIGURED = new ModConfiguredFeature<>("minecraft:cherry_bees_005", new ConfiguredFeature<>(CHERRY_TREE_FEATURE, new CherryTreeFeatureConfig(true)));
	public static final ModConfiguredFeature<RandomPatchFeatureConfig, ?> FLOWER_CHERRY_CONFIGURED = new ModConfiguredFeature<>("minecraft:flower_cherry", () -> {
		DataPool.Builder<BlockState> builder = DataPool.builder();
		for (int i = 1; i <= 4; ++i) {
			for (Direction direction : Direction.Type.HORIZONTAL) {
				builder.add(PINK_PETALS.asBlock().getDefaultState().with(FlowerbedBlock.FLOWER_AMOUNT, i).with(FlowerbedBlock.FACING, direction), 1);
			}
		}
		return new ConfiguredFeature<>(Feature.FLOWER,
				new RandomPatchFeatureConfig.Builder(new WeightedBlockStateProvider(builder), new SimpleBlockPlacer())
						.tries(96)
						.spreadX(6)
						.spreadZ(6)
						.spreadY(2)
						.cannotProject()
						.build());
	});
	//</editor-fold>
	//<editor-fold desc="Cassia">
	public static final ModConfiguredFeature<TreeFeatureConfig, ?> CASSIA_TREE = new ModConfiguredFeature<>("cassia_tree", new ConfiguredFeature<>(Feature.TREE,
			new TreeFeatureConfig.Builder(
					new SimpleBlockStateProvider(CASSIA_LOG.asBlock().getDefaultState()),
					new BendingTrunkPlacer(4, 2, 0, 3, UniformIntProvider.create(1, 2)),
					new WeightedBlockStateProvider(
							new DataPool.Builder<BlockState>()
									.add(CASSIA_LEAVES.asBlock().getDefaultState(), 3)
									.add(FLOWERING_CASSIA_LEAVES.asBlock().getDefaultState(), 1)
					),
					new SimpleBlockStateProvider(CASSIA_SAPLING.asBlock().getDefaultState()),
					new RandomSpreadFoliagePlacer(ConstantIntProvider.create(3), ConstantIntProvider.create(0), ConstantIntProvider.create(2), 50),
					new TwoLayersFeatureSize(1, 0, 1)).build()));
	//</editor-fold>
	//<editor-fold desc="Dogwood">
	private static ModConfiguredFeature<TreeFeatureConfig, ?> DogwoodTreeFeature(Block leaves, String id) {
		return new ModConfiguredFeature(id, Feature.TREE.configure(
				new TreeFeatureConfig.Builder(
						new SimpleBlockStateProvider(DOGWOOD_LOG.asBlock().getDefaultState()),
						new ForkingTrunkPlacer(5, 2, 2),
						new SimpleBlockStateProvider(leaves.getDefaultState()),
						new SimpleBlockStateProvider(DOGWOOD_SAPLING.asBlock().getDefaultState()),
						new AcaciaFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0)),
						new TwoLayersFeatureSize(1, 0, 2)
				).ignoreVines().build()
		));
	}
	public static final ModConfiguredFeature<TreeFeatureConfig, ?> PINK_DOGWOOD_TREE_CONFIGURED = DogwoodTreeFeature(PINK_DOGWOOD_LEAVES.asBlock(), "pink_dogwood_tree");
	public static final ModConfiguredFeature<TreeFeatureConfig, ?> PALE_DOGWOOD_TREE_CONFIGURED = DogwoodTreeFeature(PALE_DOGWOOD_LEAVES.asBlock(), "pale_dogwood_tree");
	public static final ModConfiguredFeature<TreeFeatureConfig, ?> WHITE_DOGWOOD_TREE_CONFIGURED = DogwoodTreeFeature(WHITE_DOGWOOD_LEAVES.asBlock(), "white_dogwood_tree");
	//</editor-fold>
	//<editor-fold desc="Grape Tree">
	private static TreeFeatureConfig.Builder GrapeTreeFeature() {
		return new TreeFeatureConfig.Builder(
				new ModSimpleBlockStateProvider(Blocks.DARK_OAK_LOG.getDefaultState()),
				new StraightTrunkPlacer(4, 2, 0),
				new WeightedBlockStateProvider(new DataPool.Builder<BlockState>()
						.add(GRAPE_LEAVES.asBlock().getDefaultState(), 3)
						.add(GRAPE_LEAVES.asBlock().getDefaultState().with(Properties.BERRIES, true), 1)),
				new SimpleBlockStateProvider(GRAPE_SAPLING.asBlock().getDefaultState()),
				new BlobFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 3),
				new TwoLayersFeatureSize(1, 0, 1));
	}
	public static final ModConfiguredFeature<TreeFeatureConfig, ?> GRAPE_TREE = new ModConfiguredFeature<>("grape_tree", new ConfiguredFeature<>(Feature.TREE, GrapeTreeFeature().build()));
	public static final ModConfiguredFeature<TreeFeatureConfig, ?> GRAPE_TREE_BEES = new ModConfiguredFeature<>("grape_tree_bees", new ConfiguredFeature<>(Feature.TREE, GrapeTreeFeature().decorators(List.of(new BeehiveTreeDecorator(0.05f))).build()));
	//</editor-fold>
	//<editor-fold desc= "Gilded Fungus">
	public static final BlockPileFeatureConfig GILDED_ROOTS_CONFIG = new BlockPileFeatureConfig(
			new WeightedBlockStateProvider(DataPool.<BlockState>builder()
					.add(GILDED_ROOTS.asBlock().getDefaultState(), 87)
					.add(GILDED_FUNGUS.asBlock().getDefaultState(), 11)
					.add(Blocks.CRIMSON_FUNGUS.getDefaultState(), 1)
					.add(Blocks.WARPED_FUNGUS.getDefaultState(), 1)
			)
	);
	public static final HugeFungusFeatureConfig GILDED_FUNGUS_CONFIG = new HugeFungusFeatureConfig(GILDED_NYLIUM.asBlock().getDefaultState(), GILDED_STEM.asBlock().getDefaultState(), GILDED_WART_BLOCK.asBlock().getDefaultState(), BLUE_SHROOMLIGHT.asBlock().getDefaultState(), true);
	public static final HugeFungusFeatureConfig GILDED_FUNGUS_NOT_PLANTED_CONFIG = new HugeFungusFeatureConfig(GILDED_FUNGUS_CONFIG.validBaseBlock, GILDED_FUNGUS_CONFIG.stemState, GILDED_FUNGUS_CONFIG.hatState, GILDED_FUNGUS_CONFIG.decorationState, false);
	public static final ConfiguredFeature<?, ?> GILDED_FOREST_VEGETATION_CONFIGURED = Feature.NETHER_FOREST_VEGETATION.configure(GILDED_ROOTS_CONFIG).decorate(Decorator.COUNT_MULTILAYER.configure(new CountConfig(6)));
	public static final ConfiguredFeature<?, ?> PATCH_GILDED_ROOTS = Feature.RANDOM_PATCH.configure((new RandomPatchFeatureConfig.Builder(new SimpleBlockStateProvider(GILDED_ROOTS.asBlock().getDefaultState()), new SimpleBlockPlacer())).tries(64).cannotProject().build()).range(new RangeDecoratorConfig(UniformHeightProvider.create(YOffset.getBottom(), YOffset.getTop())));
	public static final ModConfiguredFeature<?, ?> GILDED_FUNGI = new ModConfiguredFeature<>("gilded_fungi", new ConfiguredFeature<>(Feature.HUGE_FUNGUS, GILDED_FUNGUS_NOT_PLANTED_CONFIG));
	public static final ModConfiguredFeature<HugeFungusFeatureConfig, ?> GILDED_FUNGI_PLANTED = new ModConfiguredFeature<>("gilded_fungi_planted", new ConfiguredFeature<>(Feature.HUGE_FUNGUS, GILDED_FUNGUS_CONFIG));
	//</editor-fold>
	//<editor-fold desc="Deep Dark">
	public static final Feature<SculkPatchFeatureConfig> SCULK_PATCH_FEATURE = new SculkPatchFeature(SculkPatchFeatureConfig.CODEC);
	public static final ModConfiguredFeature<SculkPatchFeatureConfig, ?> SCULK_PATCH_DEEP_DARK_CONFIGURED = new ModConfiguredFeature<>("minecraft:sculk_patch_deep_dark", new ConfiguredFeature<>(SCULK_PATCH_FEATURE, new SculkPatchFeatureConfig(10, 32, 64, 0, 1, ConstantIntProvider.create(0), 0.5f)));
	public static final ModConfiguredFeature<SculkPatchFeatureConfig, ?> SCULK_PATCH_ANCIENT_CITY_CONFIGURED = new ModConfiguredFeature<>("minecraft:sculk_patch_ancient_city", new ConfiguredFeature<>(SCULK_PATCH_FEATURE, new SculkPatchFeatureConfig(10, 32, 64, 0, 1, UniformIntProvider.create(1, 3), 0.5F)));
	public static final Feature<DefaultFeatureConfig> SCULK_VEIN_FEATURE = new SculkVeinFeature(DefaultFeatureConfig.CODEC);
	public static final ModConfiguredFeature<DefaultFeatureConfig, ?> SCULK_VEIN_CONFIGURED = new ModConfiguredFeature<>("minecraft:sculk_vein", new ConfiguredFeature<>(SCULK_VEIN_FEATURE, FeatureConfig.DEFAULT));
	//</editor-fold>
	//<editor-fold desc="Biomes">
	public static final RegistryKey<Biome> MANGROVE_SWAMP = RegistryKey.of(Registry.BIOME_KEY, ModId.ID("minecraft:mangrove_swamp"));
	public static final RegistryKey<Biome> DEEP_DARK = RegistryKey.of(Registry.BIOME_KEY, ModId.ID("minecraft:deep_dark"));
	public static final RegistryKey<Biome> CHERRY_GROVE = RegistryKey.of(Registry.BIOME_KEY, ModId.ID("minecraft:cherry_grove"));
	public static final Set<RegistryKey<Biome>> CAVE_BIOMES = Set.of(BiomeKeys.LUSH_CAVES, BiomeKeys.DRIPSTONE_CAVES, DEEP_DARK);
	//</editor-fold>

	//Worldedit
	public static final Item WORLDEDIT_WAND = MakeGeneratedItem();
	public static final Item WORLDEDIT_NAV = MakeGeneratedItem();
	public static final Item RAGDOLL_WAND = GeneratedItem(new RagdollWandItem(ItemSettings()));

	//<editor-fold desc="Origins Powers">
	public static final PowerType<?> EASY_FREEZE_POWER = new PowerTypeReference<>(ModId.ID("easy_freeze"));
	public static final PowerType<?> IDENTIFIED_SOUNDS_POWER = new PowerTypeReference<>(ModId.ID("identified_sounds"));
	public static final PowerType<?> IS_SKELETON_POWER = new PowerTypeReference<>(ModId.ID("is_skeleton"));
	public static final PowerType<?> IS_PIGLIN_POWER = new PowerTypeReference<>(ModId.ID("is_piglin"));
	public static final PowerType<?> IS_ZOMBIFIED_PIGLIN = new PowerTypeReference<>(ModId.ID("is_zombified_piglin"));
	public static final PowerType<?> SEE_IN_LAVA_POWER = new PowerTypeReference<>(ModId.ID("see_in_lava"));
	public static final PowerType<?> STARE_AT_ENDERMEN_POWER = new PowerTypeReference<>(ModId.ID("stare_at_endermen"));
	public static final PowerType<?> WARDEN_NEUTRAL_POWER = new PowerTypeReference<>(ModId.ID("warden_neutral"));
	private static void RegisterOriginsPowers() {
		Register(BiggerLungsPower::createFactory);
		Register(BurnForeverPower::createFactory);
		Register(CannotFreezePower::createFactory);
		Register(CapePower::createFactory);
		Register(ChorusImmunePower::createFactory);
		Register(ChorusTeleportPower::createFactory);
		Register(ClownPacifistPower::createFactory);
		Register(CobwebMovementPower::createFactory);
		Register(CrackBlocksPower::createFactory);
		Register(CustomLootTablePower::createFactory);
		Register(DragonNeutralityPower::createFactory);
		Register(DrinkBloodPower::createFactory);
		Register(DroppableInventoryPower::createFactory);
		Register(ElytraTexturePower::createFactory);
		Register(EnableTradePower::createFactory);
		Register(ExperienceHealingPower::createFactory);
		Register(ExperienceSiphonPower::createFactory);
		Register(FireImmunePower::createFactory);
		Register(FluidBreatherPower::createFactory);
		Register(HuntedByAxolotlsPower::createFactory);
		Register(IncreaseDurabilityPower::createFactory);
		Register(LactoseIntolerantPower::createFactory);
		Register(LightningRodPower::createFactory);
		Register(MobHostilityPower::createFactory);
		Register(PhantomNeutralityPower::createFactory);
		Register(PreventShiveringPower::createFactory);
		Register(PulsingSkinGlowPower::createFactory);
		Register(RecycleArrowsPower::createFactory);
		Register(RideRavagersPower::createFactory);
		Register(ScareMobPower::createFactory);
		Register(SingleSlotInventoryPower::createFactory);
		Register(SkinGlowPower::createFactory);
		Register(SneakSpeedPower::createFactory);
		Register(SoftStepsPower::createFactory);
		Register(SonicBoomPower::createFactory);
		Register(SoulSpeedPower::createFactory);
		Register(SummonLightningPower::createFactory);
		Register(TakeHoneyPower::createFactory);
		Register(UndeadPotionEffectsPower::createFactory);
		Register(VillagersFleePower::createFactory);
		Register(VillagerPriceModifierPower::createFactory);
		Register(WalkOnPowderSnowPower::createFactory);
		Register(WardenHeartbeatPower::createFactory);
		Register(ZombifiedPiglinAllyPower::createFactory);
		if (ModConfig.REGISTER_HAVEN_MOD) HavenMod.RegisterOriginPowers();
	}
	//</editor-fold>
	private static void RegisterCommands() {
		Event<CommandRegistrationCallback> COMMAND = CommandRegistrationCallback.EVENT;
		COMMAND.register(CleanseCommand::register);
		COMMAND.register(ChorusCommand::register);
		COMMAND.register(CottonFillCommand::register);
		COMMAND.register(ExtinguishCommand::register);
		COMMAND.register(IgniteCommand::register);
		COMMAND.register(GoToSpawnCommand::register);
		COMMAND.register(NearDeathCommand::register);
		COMMAND.register(SphereReplaceCommand::register);
		COMMAND.register(WardenSpawnTrackerCommand::register);
		//Vanilla Minecraft
		COMMAND.register((dispatcher, dedicated) -> RaidCommand.register(dispatcher));
	}

	private static boolean registered = false;
	private static void RegisterAll() {
		if (registered) return;
		registered = true;
		OxidationScale.Initialize();
		CrackedBlocks.Initialize();
		//<editor-fold desc="Boats">
		Register("mod_boat", MOD_BOAT_ENTITY, List.of(EN_US.Boat("Mod")));
		Register("minecraft:chest_boat", CHEST_BOAT_ENTITY, List.of(EN_US.ChestBoat()));
		Register("mod_chest_boat", MOD_CHEST_BOAT_ENTITY, List.of(EN_US.ChestBoat("Mod")));
		//</editor-fold>
		//<editor-fold desc="Minecarts">
		Register("dispenser_minecart", DISPENSER_MINECART_ENTITY, List.of(EN_US.Dispenser(EN_US.with(EN_US.Minecart()))));
		Register("dispenser_minecart", DISPENSER_MINECART_ITEM, List.of(EN_US.Dispenser(EN_US.with(EN_US.Minecart()))));
		DispenserMinecartEntity.OverrideDispenserBehaviors();
		//</editor-fold>
		Register("woodcutting", WOODCUTTING_RECIPE_SERIALIZER);
		//<editor-fold desc="Acacia">
		Register("minecraft:acacia_hanging_sign", "minecraft:acacia_wall_hanging_sign", ACACIA_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Acacia())));
		Register("acacia_iron_hanging_sign", "acacia_iron_wall_hanging_sign", ACACIA_IRON_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Acacia(), EN_US.Chain(EN_US.Iron(EN_US.with())))));
		Register("acacia_gold_hanging_sign", "acacia_gold_wall_hanging_sign", ACACIA_GOLD_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Acacia(), EN_US.Chain(EN_US.Gold(EN_US.with())))));
		Register("acacia_copper_hanging_sign", "acacia_copper_wall_hanging_sign", ACACIA_COPPER_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Acacia(), EN_US.Chain(EN_US.Copper(EN_US.with())))));
		Register("acacia_exposed_copper_hanging_sign", "acacia_exposed_copper_wall_hanging_sign", ACACIA_EXPOSED_COPPER_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Acacia(), EN_US.Chain(EN_US.Copper(EN_US.Exposed(EN_US.with()))))));
		Register("acacia_weathered_copper_hanging_sign", "acacia_weathered_copper_wall_hanging_sign", ACACIA_WEATHERED_COPPER_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Acacia(), EN_US.Chain(EN_US.Copper(EN_US.Weathered(EN_US.with()))))));
		Register("acacia_oxidized_copper_hanging_sign", "acacia_oxidized_copper_wall_hanging_sign", ACACIA_OXIDIZED_COPPER_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Acacia(), EN_US.Chain(EN_US.Copper(EN_US.Oxidized(EN_US.with()))))));
		Register("acacia_netherite_hanging_sign", "acacia_netherite_wall_hanging_sign", ACACIA_NETHERITE_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Acacia(), EN_US.Chain(EN_US.Netherite(EN_US.with())))));
		Register("acacia_beehive", ACACIA_BEEHIVE, List.of(EN_US.Beehive(EN_US.Acacia())));
		Register("acacia_bookshelf", ACACIA_BOOKSHELF, List.of(EN_US.Bookshelf(EN_US.Acacia())));
		Register("acacia_chiseled_bookshelf", ACACIA_CHISELED_BOOKSHELF, List.of(EN_US.ChiseledBookshelf(EN_US.Acacia())));
		Register("acacia_crafting_table", ACACIA_CRAFTING_TABLE, List.of(EN_US.Table(EN_US.Crafting(EN_US.Acacia()))));
		Register("acacia_ladder", ACACIA_LADDER, List.of(EN_US.Ladder(EN_US.Acacia())));
		Register("acacia_woodcutter", ACACIA_WOODCUTTER, List.of(EN_US.Woodcutter(EN_US.Acacia())));
		Register("acacia_barrel", ACACIA_BARREL, List.of(EN_US.Barrel(EN_US.Acacia())));
		Register("acacia_lectern", ACACIA_LECTERN, List.of(EN_US.Lectern(EN_US.Acacia())));
		Register("acacia_powder_keg", ACACIA_POWDER_KEG, List.of(EN_US.PowderKeg(EN_US.Acacia())));
		Register("acacia_log_slab", ACACIA_LOG_SLAB, List.of(EN_US.Slab(EN_US.Log(EN_US.Acacia()))));
		Register("stripped_acacia_log_slab", STRIPPED_ACACIA_LOG_SLAB, List.of(EN_US.Slab(EN_US.Log(EN_US.Acacia(EN_US.Stripped())))));
		StrippedBlockUtil.Register(ACACIA_LOG_SLAB, STRIPPED_ACACIA_LOG_SLAB);
		Register("acacia_wood_slab", ACACIA_WOOD_SLAB, List.of(EN_US.Slab(EN_US.Wood(EN_US.Acacia()))));
		Register("stripped_acacia_wood_slab", STRIPPED_ACACIA_WOOD_SLAB, List.of(EN_US.Slab(EN_US.Wood(EN_US.Acacia(EN_US.Stripped())))));
		StrippedBlockUtil.Register(ACACIA_WOOD_SLAB, STRIPPED_ACACIA_WOOD_SLAB);
		Register("light_green_acacia_leaves", LIGHT_GREEN_ACACIA_LEAVES, List.of(EN_US.Leaves(EN_US.Acacia(EN_US.Green(EN_US.Light())))));
		Register("red_acacia_leaves", RED_ACACIA_LEAVES, List.of(EN_US.Leaves(EN_US.Acacia(EN_US.Red()))));
		Register("yellow_acacia_leaves", YELLOW_ACACIA_LEAVES, List.of(EN_US.Leaves(EN_US.Acacia(EN_US.Yellow()))));
		Register("minecraft:acacia_chest_boat", ACACIA_CHEST_BOAT, List.of(EN_US.ChestBoat(EN_US.Acacia())));
		Register("acacia_hammer", ACACIA_HAMMER, List.of(EN_US.Hammer(EN_US.Acacia())));
		Register("acacia_torch", "acacia_wall_torch", ACACIA_TORCH, List.of(EN_US._Torch(EN_US.Acacia())));
		Register("acacia_soul_torch", "acacia_soul_wall_torch", ACACIA_SOUL_TORCH, List.of(EN_US.SoulTorch(EN_US.Acacia())));
		Register("acacia_ender_torch", "acacia_ender_wall_torch", ACACIA_ENDER_TORCH, List.of(EN_US.EnderTorch(EN_US.Acacia())));
		Register("underwater_acacia_torch", "underwater_acacia_wall_torch", UNDERWATER_ACACIA_TORCH, List.of(EN_US.Underwater_Torch(EN_US.Acacia())));
		Register("acacia_campfire", ACACIA_CAMPFIRE, List.of(EN_US.Campfire(EN_US.Acacia())));
		Register("acacia_soul_campfire", ACACIA_SOUL_CAMPFIRE, List.of(EN_US.Campfire(EN_US.Soul(EN_US.Acacia()))));
		Register("acacia_ender_campfire", ACACIA_ENDER_CAMPFIRE, List.of(EN_US.Campfire(EN_US.Ender(EN_US.Acacia()))));
		//</editor-fold>
		//<editor-fold desc="Birch">
		Register("minecraft:birch_hanging_sign", "minecraft:birch_wall_hanging_sign", BIRCH_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Birch())));
		Register("birch_iron_hanging_sign", "birch_iron_wall_hanging_sign", BIRCH_IRON_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Birch(), EN_US.Chain(EN_US.Iron(EN_US.with())))));
		Register("birch_gold_hanging_sign", "birch_gold_wall_hanging_sign", BIRCH_GOLD_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Birch(), EN_US.Chain(EN_US.Gold(EN_US.with())))));
		Register("birch_copper_hanging_sign", "birch_copper_wall_hanging_sign", BIRCH_COPPER_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Birch(), EN_US.Chain(EN_US.Copper(EN_US.with())))));
		Register("birch_exposed_copper_hanging_sign", "birch_exposed_copper_wall_hanging_sign", BIRCH_EXPOSED_COPPER_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Birch(), EN_US.Chain(EN_US.Copper(EN_US.Exposed(EN_US.with()))))));
		Register("birch_weathered_copper_hanging_sign", "birch_weathered_copper_wall_hanging_sign", BIRCH_WEATHERED_COPPER_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Birch(), EN_US.Chain(EN_US.Copper(EN_US.Weathered(EN_US.with()))))));
		Register("birch_oxidized_copper_hanging_sign", "birch_oxidized_copper_wall_hanging_sign", BIRCH_OXIDIZED_COPPER_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Birch(), EN_US.Chain(EN_US.Copper(EN_US.Oxidized(EN_US.with()))))));
		Register("birch_netherite_hanging_sign", "birch_netherite_wall_hanging_sign", BIRCH_NETHERITE_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Birch(), EN_US.Chain(EN_US.Netherite(EN_US.with())))));
		Register("birch_beehive", BIRCH_BEEHIVE, List.of(EN_US.Beehive(EN_US.Birch())));
		Register("birch_bookshelf", BIRCH_BOOKSHELF, List.of(EN_US.Bookshelf(EN_US.Birch())));
		Register("birch_chiseled_bookshelf", BIRCH_CHISELED_BOOKSHELF, List.of(EN_US.ChiseledBookshelf(EN_US.Birch())));
		Register("birch_crafting_table", BIRCH_CRAFTING_TABLE, List.of(EN_US.Table(EN_US.Crafting(EN_US.Birch()))));
		Register("birch_ladder", BIRCH_LADDER, List.of(EN_US.Ladder(EN_US.Birch())));
		Register("birch_woodcutter", BIRCH_WOODCUTTER, List.of(EN_US.Woodcutter(EN_US.Birch())));
		Register("birch_barrel", BIRCH_BARREL, List.of(EN_US.Barrel(EN_US.Birch())));
		Register("birch_lectern", BIRCH_LECTERN, List.of(EN_US.Lectern(EN_US.Birch())));
		Register("birch_powder_keg", BIRCH_POWDER_KEG, List.of(EN_US.PowderKeg(EN_US.Birch())));
		Register("birch_log_slab", BIRCH_LOG_SLAB, List.of(EN_US.Slab(EN_US.Log(EN_US.Birch()))));
		Register("stripped_birch_log_slab", STRIPPED_BIRCH_LOG_SLAB, List.of(EN_US.Slab(EN_US.Log(EN_US.Birch(EN_US.Stripped())))));
		StrippedBlockUtil.Register(BIRCH_LOG_SLAB, STRIPPED_BIRCH_LOG_SLAB);
		Register("birch_wood_slab", BIRCH_WOOD_SLAB, List.of(EN_US.Slab(EN_US.Wood(EN_US.Birch()))));
		Register("stripped_birch_wood_slab", STRIPPED_BIRCH_WOOD_SLAB, List.of(EN_US.Slab(EN_US.Wood(EN_US.Birch(EN_US.Stripped())))));
		StrippedBlockUtil.Register(BIRCH_WOOD_SLAB, STRIPPED_BIRCH_WOOD_SLAB);
		Register("light_green_birch_leaves", LIGHT_GREEN_BIRCH_LEAVES, List.of(EN_US.Leaves(EN_US.Birch(EN_US.Green(EN_US.Light())))));
		Register("red_birch_leaves", RED_BIRCH_LEAVES, List.of(EN_US.Leaves(EN_US.Birch(EN_US.Red()))));
		Register("yellow_birch_leaves", YELLOW_BIRCH_LEAVES, List.of(EN_US.Leaves(EN_US.Birch(EN_US.Yellow()))));
		Register("white_birch_leaves", WHITE_BIRCH_LEAVES, List.of(EN_US.Leaves(EN_US.Birch(EN_US.White()))));
		Register("minecraft:birch_chest_boat", BIRCH_CHEST_BOAT, List.of(EN_US.ChestBoat(EN_US.Birch())));
		Register("birch_hammer", BIRCH_HAMMER, List.of(EN_US.Hammer(EN_US.Birch())));
		Register("birch_torch", "birch_wall_torch", BIRCH_TORCH, List.of(EN_US._Torch(EN_US.Birch())));
		Register("birch_soul_torch", "birch_soul_wall_torch", BIRCH_SOUL_TORCH, List.of(EN_US.SoulTorch(EN_US.Birch())));
		Register("birch_ender_torch", "birch_ender_wall_torch", BIRCH_ENDER_TORCH, List.of(EN_US.EnderTorch(EN_US.Birch())));
		Register("underwater_birch_torch", "underwater_birch_wall_torch", UNDERWATER_BIRCH_TORCH, List.of(EN_US.Underwater_Torch(EN_US.Birch())));
		Register("birch_campfire", BIRCH_CAMPFIRE, List.of(EN_US.Campfire(EN_US.Birch())));
		Register("birch_soul_campfire", BIRCH_SOUL_CAMPFIRE, List.of(EN_US.Campfire(EN_US.Soul(EN_US.Birch()))));
		Register("birch_ender_campfire", BIRCH_ENDER_CAMPFIRE, List.of(EN_US.Campfire(EN_US.Ender(EN_US.Birch()))));
		//</editor-fold>
		//<editor-fold desc="Dark Oak">
		Register("minecraft:dark_oak_hanging_sign", "minecraft:dark_oak_wall_hanging_sign", DARK_OAK_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.DarkOak())));
		Register("dark_oak_iron_hanging_sign", "dark_oak_iron_wall_hanging_sign", DARK_OAK_IRON_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Oak(EN_US.Dark()), EN_US.Chain(EN_US.Iron(EN_US.with())))));
		Register("dark_oak_gold_hanging_sign", "dark_oak_gold_wall_hanging_sign", DARK_OAK_GOLD_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Oak(EN_US.Dark()), EN_US.Chain(EN_US.Gold(EN_US.with())))));
		Register("dark_oak_copper_hanging_sign", "dark_oak_copper_wall_hanging_sign", DARK_OAK_COPPER_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Oak(EN_US.Dark()), EN_US.Chain(EN_US.Copper(EN_US.with())))));
		Register("dark_oak_exposed_copper_hanging_sign", "dark_oak_exposed_copper_wall_hanging_sign", DARK_OAK_EXPOSED_COPPER_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Oak(EN_US.Dark()), EN_US.Chain(EN_US.Copper(EN_US.Exposed(EN_US.with()))))));
		Register("dark_oak_weathered_copper_hanging_sign", "dark_oak_weathered_copper_wall_hanging_sign", DARK_OAK_WEATHERED_COPPER_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Oak(EN_US.Dark()), EN_US.Chain(EN_US.Copper(EN_US.Weathered(EN_US.with()))))));
		Register("dark_oak_oxidized_copper_hanging_sign", "dark_oak_oxidized_copper_wall_hanging_sign", DARK_OAK_OXIDIZED_COPPER_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Oak(EN_US.Dark()), EN_US.Chain(EN_US.Copper(EN_US.Oxidized(EN_US.with()))))));
		Register("dark_oak_netherite_hanging_sign", "dark_oak_netherite_wall_hanging_sign", DARK_OAK_NETHERITE_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Oak(EN_US.Dark()), EN_US.Chain(EN_US.Netherite(EN_US.with())))));
		Register("dark_oak_beehive", DARK_OAK_BEEHIVE, List.of(EN_US.Beehive(EN_US.DarkOak())));
		Register("dark_oak_bookshelf", DARK_OAK_BOOKSHELF, List.of(EN_US.Bookshelf(EN_US.DarkOak())));
		Register("dark_oak_chiseled_bookshelf", DARK_OAK_CHISELED_BOOKSHELF, List.of(EN_US.ChiseledBookshelf(EN_US.DarkOak())));
		Register("dark_oak_crafting_table", DARK_OAK_CRAFTING_TABLE, List.of(EN_US.Table(EN_US.Crafting(EN_US.DarkOak()))));
		Register("dark_oak_ladder", DARK_OAK_LADDER, List.of(EN_US.Ladder(EN_US.DarkOak())));
		Register("dark_oak_woodcutter", DARK_OAK_WOODCUTTER, List.of(EN_US.Woodcutter(EN_US.DarkOak())));
		Register("dark_oak_barrel", DARK_OAK_BARREL, List.of(EN_US.Barrel(EN_US.DarkOak())));
		Register("dark_oak_lectern", DARK_OAK_LECTERN, List.of(EN_US.Lectern(EN_US.DarkOak())));
		Register("dark_oak_powder_keg", DARK_OAK_POWDER_KEG, List.of(EN_US.PowderKeg(EN_US.DarkOak())));
		Register("dark_oak_log_slab", DARK_OAK_LOG_SLAB, List.of(EN_US.Slab(EN_US.Log(EN_US.DarkOak()))));
		Register("stripped_dark_oak_log_slab", STRIPPED_DARK_OAK_LOG_SLAB, List.of(EN_US.Slab(EN_US.Log(EN_US.DarkOak(EN_US.Stripped())))));
		StrippedBlockUtil.Register(DARK_OAK_LOG_SLAB, STRIPPED_DARK_OAK_LOG_SLAB);
		Register("dark_oak_wood_slab", DARK_OAK_WOOD_SLAB, List.of(EN_US.Slab(EN_US.Wood(EN_US.DarkOak()))));
		Register("stripped_dark_oak_wood_slab", STRIPPED_DARK_OAK_WOOD_SLAB, List.of(EN_US.Slab(EN_US.Wood(EN_US.DarkOak(EN_US.Stripped())))));
		StrippedBlockUtil.Register(DARK_OAK_WOOD_SLAB, STRIPPED_DARK_OAK_WOOD_SLAB);
		Register("minecraft:dark_oak_chest_boat", DARK_OAK_CHEST_BOAT, List.of(EN_US.ChestBoat(EN_US.DarkOak())));
		Register("dark_oak_hammer", DARK_OAK_HAMMER, List.of(EN_US.Hammer(EN_US.DarkOak())));
		Register("dark_oak_torch", "dark_oak_wall_torch", DARK_OAK_TORCH, List.of(EN_US._Torch(EN_US.Oak(EN_US.Dark()))));
		Register("dark_oak_soul_torch", "dark_oak_soul_wall_torch", DARK_OAK_SOUL_TORCH, List.of(EN_US.SoulTorch(EN_US.Oak(EN_US.Dark()))));
		Register("dark_oak_ender_torch", "dark_oak_ender_wall_torch", DARK_OAK_ENDER_TORCH, List.of(EN_US.EnderTorch(EN_US.Oak(EN_US.Dark()))));
		Register("underwater_dark_oak_torch", "underwater_dark_oak_wall_torch", UNDERWATER_DARK_OAK_TORCH, List.of(EN_US.Underwater_Torch(EN_US.Oak(EN_US.Dark()))));
		Register("dark_oak_campfire", DARK_OAK_CAMPFIRE, List.of(EN_US.Campfire(EN_US.Oak(EN_US.Dark()))));
		Register("dark_oak_soul_campfire", DARK_OAK_SOUL_CAMPFIRE, List.of(EN_US.Campfire(EN_US.Soul(EN_US.Oak(EN_US.Dark())))));
		Register("dark_oak_ender_campfire", DARK_OAK_ENDER_CAMPFIRE, List.of(EN_US.Campfire(EN_US.Ender(EN_US.Oak(EN_US.Dark())))));
		//</editor-fold>
		//<editor-fold desc="Jungle">
		Register("minecraft:jungle_hanging_sign", "minecraft:jungle_wall_hanging_sign", JUNGLE_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Jungle())));
		Register("jungle_iron_hanging_sign", "jungle_iron_wall_hanging_sign", JUNGLE_IRON_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Jungle(), EN_US.Chain(EN_US.Iron(EN_US.with())))));
		Register("jungle_gold_hanging_sign", "jungle_gold_wall_hanging_sign", JUNGLE_GOLD_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Jungle(), EN_US.Chain(EN_US.Gold(EN_US.with())))));
		Register("jungle_copper_hanging_sign", "jungle_copper_wall_hanging_sign", JUNGLE_COPPER_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Jungle(), EN_US.Chain(EN_US.Copper(EN_US.with())))));
		Register("jungle_exposed_copper_hanging_sign", "jungle_exposed_copper_wall_hanging_sign", JUNGLE_EXPOSED_COPPER_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Jungle(), EN_US.Chain(EN_US.Copper(EN_US.Exposed(EN_US.with()))))));
		Register("jungle_weathered_copper_hanging_sign", "jungle_weathered_copper_wall_hanging_sign", JUNGLE_WEATHERED_COPPER_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Jungle(), EN_US.Chain(EN_US.Copper(EN_US.Weathered(EN_US.with()))))));
		Register("jungle_oxidized_copper_hanging_sign", "jungle_oxidized_copper_wall_hanging_sign", JUNGLE_OXIDIZED_COPPER_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Jungle(), EN_US.Chain(EN_US.Copper(EN_US.Oxidized(EN_US.with()))))));
		Register("jungle_netherite_hanging_sign", "jungle_netherite_wall_hanging_sign", JUNGLE_NETHERITE_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Jungle(), EN_US.Chain(EN_US.Netherite(EN_US.with())))));
		Register("jungle_beehive", JUNGLE_BEEHIVE, List.of(EN_US.Beehive(EN_US.Jungle())));
		Register("jungle_bookshelf", JUNGLE_BOOKSHELF, List.of(EN_US.Bookshelf(EN_US.Jungle())));
		Register("jungle_chiseled_bookshelf", JUNGLE_CHISELED_BOOKSHELF, List.of(EN_US.ChiseledBookshelf(EN_US.Jungle())));
		Register("jungle_crafting_table", JUNGLE_CRAFTING_TABLE, List.of(EN_US.Table(EN_US.Crafting(EN_US.Jungle()))));
		Register("jungle_ladder", JUNGLE_LADDER, List.of(EN_US.Ladder(EN_US.Jungle())));
		Register("jungle_woodcutter", JUNGLE_WOODCUTTER, List.of(EN_US.Woodcutter(EN_US.Jungle())));
		Register("jungle_barrel", JUNGLE_BARREL, List.of(EN_US.Barrel(EN_US.Jungle())));
		Register("jungle_lectern", JUNGLE_LECTERN, List.of(EN_US.Lectern(EN_US.Jungle())));
		Register("jungle_powder_keg", JUNGLE_POWDER_KEG, List.of(EN_US.PowderKeg(EN_US.Jungle())));
		Register("jungle_log_slab", JUNGLE_LOG_SLAB, List.of(EN_US.Slab(EN_US.Log(EN_US.Jungle()))));
		Register("stripped_jungle_log_slab", STRIPPED_JUNGLE_LOG_SLAB, List.of(EN_US.Slab(EN_US.Log(EN_US.Jungle(EN_US.Stripped())))));
		StrippedBlockUtil.Register(JUNGLE_LOG_SLAB, STRIPPED_JUNGLE_LOG_SLAB);
		Register("jungle_wood_slab", JUNGLE_WOOD_SLAB, List.of(EN_US.Slab(EN_US.Wood(EN_US.Jungle()))));
		Register("stripped_jungle_wood_slab", STRIPPED_JUNGLE_WOOD_SLAB, List.of(EN_US.Slab(EN_US.Wood(EN_US.Jungle(EN_US.Stripped())))));
		StrippedBlockUtil.Register(JUNGLE_WOOD_SLAB, STRIPPED_JUNGLE_WOOD_SLAB);
		Register("minecraft:jungle_chest_boat", JUNGLE_CHEST_BOAT, List.of(EN_US.ChestBoat(EN_US.Jungle())));
		Register("jungle_hammer", JUNGLE_HAMMER, List.of(EN_US.Hammer(EN_US.Jungle())));
		Register("jungle_torch", "jungle_wall_torch", JUNGLE_TORCH, List.of(EN_US._Torch(EN_US.Jungle())));
		Register("jungle_soul_torch", "jungle_soul_wall_torch", JUNGLE_SOUL_TORCH, List.of(EN_US.SoulTorch(EN_US.Jungle())));
		Register("jungle_ender_torch", "jungle_ender_wall_torch", JUNGLE_ENDER_TORCH, List.of(EN_US.EnderTorch(EN_US.Jungle())));
		Register("underwater_jungle_torch", "underwater_jungle_wall_torch", UNDERWATER_JUNGLE_TORCH, List.of(EN_US.Underwater_Torch(EN_US.Jungle())));
		Register("jungle_campfire", JUNGLE_CAMPFIRE, List.of(EN_US.Campfire(EN_US.Jungle())));
		Register("jungle_soul_campfire", JUNGLE_SOUL_CAMPFIRE, List.of(EN_US.Campfire(EN_US.Soul(EN_US.Jungle()))));
		Register("jungle_ender_campfire", JUNGLE_ENDER_CAMPFIRE, List.of(EN_US.Campfire(EN_US.Ender(EN_US.Jungle()))));
		//</editor-fold>
		//<editor-fold desc="Oak">
		Register("minecraft:oak_hanging_sign", "minecraft:oak_wall_hanging_sign", OAK_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Oak())));
		Register("oak_iron_hanging_sign", "oak_iron_wall_hanging_sign", OAK_IRON_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Oak(), EN_US.Chain(EN_US.Iron(EN_US.with())))));
		Register("oak_gold_hanging_sign", "oak_gold_wall_hanging_sign", OAK_GOLD_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Oak(), EN_US.Chain(EN_US.Gold(EN_US.with())))));
		Register("oak_copper_hanging_sign", "oak_copper_wall_hanging_sign", OAK_COPPER_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Oak(), EN_US.Chain(EN_US.Copper(EN_US.with())))));
		Register("oak_exposed_copper_hanging_sign", "oak_exposed_copper_wall_hanging_sign", OAK_EXPOSED_COPPER_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Oak(), EN_US.Chain(EN_US.Copper(EN_US.Exposed(EN_US.with()))))));
		Register("oak_weathered_copper_hanging_sign", "oak_weathered_copper_wall_hanging_sign", OAK_WEATHERED_COPPER_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Oak(), EN_US.Chain(EN_US.Copper(EN_US.Weathered(EN_US.with()))))));
		Register("oak_oxidized_copper_hanging_sign", "oak_oxidized_copper_wall_hanging_sign", OAK_OXIDIZED_COPPER_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Oak(), EN_US.Chain(EN_US.Copper(EN_US.Oxidized(EN_US.with()))))));
		Register("oak_netherite_hanging_sign", "oak_netherite_wall_hanging_sign", OAK_NETHERITE_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Oak(), EN_US.Chain(EN_US.Netherite(EN_US.with())))));
		Register("minecraft:chiseled_bookshelf", CHISELED_BOOKSHELF, List.of(EN_US.ChiseledBookshelf()));
		Register("woodcutter", WOODCUTTER, List.of(EN_US.Woodcutter()));
		Register("oak_barrel", OAK_BARREL, List.of(EN_US.Barrel(EN_US.Oak())));
		Register("oak_powder_keg", OAK_POWDER_KEG, List.of(EN_US.PowderKeg(EN_US.Oak())));
		Register("oak_log_slab", OAK_LOG_SLAB, List.of(EN_US.Slab(EN_US.Log(EN_US.Oak()))));
		Register("stripped_oak_log_slab", STRIPPED_OAK_LOG_SLAB, List.of(EN_US.Slab(EN_US.Log(EN_US.Oak(EN_US.Stripped())))));
		StrippedBlockUtil.Register(OAK_LOG_SLAB, STRIPPED_OAK_LOG_SLAB);
		Register("oak_wood_slab", OAK_WOOD_SLAB, List.of(EN_US.Slab(EN_US.Wood(EN_US.Oak()))));
		Register("stripped_oak_wood_slab", STRIPPED_OAK_WOOD_SLAB, List.of(EN_US.Slab(EN_US.Wood(EN_US.Oak(EN_US.Stripped())))));
		StrippedBlockUtil.Register(OAK_WOOD_SLAB, STRIPPED_OAK_WOOD_SLAB);
		Register("blue_green_oak_leaves", BLUE_GREEN_OAK_LEAVES, List.of(EN_US.Leaves(EN_US.Oak(EN_US.Green(EN_US.Blue())))));
		Register("light_green_oak_leaves", LIGHT_GREEN_OAK_LEAVES, List.of(EN_US.Leaves(EN_US.Oak(EN_US.Green(EN_US.Light())))));
		Register("red_oak_leaves", RED_OAK_LEAVES, List.of(EN_US.Leaves(EN_US.Oak(EN_US.Red()))));
		Register("white_oak_leaves", WHITE_OAK_LEAVES, List.of(EN_US.Leaves(EN_US.Oak(EN_US.White()))));
		Register("yellow_oak_leaves", YELLOW_OAK_LEAVES, List.of(EN_US.Leaves(EN_US.Oak(EN_US.Yellow()))));
		Register("minecraft:oak_chest_boat", OAK_CHEST_BOAT, List.of(EN_US.ChestBoat(EN_US.Oak())));
		Register("oak_hammer", OAK_HAMMER, List.of(EN_US.Hammer(EN_US.Oak())));
		//</editor-fold>
		//<editor-fold desc="Spruce">
		Register("minecraft:spruce_hanging_sign", "minecraft:spruce_wall_hanging_sign", SPRUCE_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Spruce())));
		Register("spruce_iron_hanging_sign", "spruce_iron_wall_hanging_sign", SPRUCE_IRON_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Spruce(), EN_US.Chain(EN_US.Iron(EN_US.with())))));
		Register("spruce_gold_hanging_sign", "spruce_gold_wall_hanging_sign", SPRUCE_GOLD_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Spruce(), EN_US.Chain(EN_US.Gold(EN_US.with())))));
		Register("spruce_copper_hanging_sign", "spruce_copper_wall_hanging_sign", SPRUCE_COPPER_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Spruce(), EN_US.Chain(EN_US.Copper(EN_US.with())))));
		Register("spruce_exposed_copper_hanging_sign", "spruce_exposed_copper_wall_hanging_sign", SPRUCE_EXPOSED_COPPER_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Spruce(), EN_US.Chain(EN_US.Copper(EN_US.Exposed(EN_US.with()))))));
		Register("spruce_weathered_copper_hanging_sign", "spruce_weathered_copper_wall_hanging_sign", SPRUCE_WEATHERED_COPPER_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Spruce(), EN_US.Chain(EN_US.Copper(EN_US.Weathered(EN_US.with()))))));
		Register("spruce_oxidized_copper_hanging_sign", "spruce_oxidized_copper_wall_hanging_sign", SPRUCE_OXIDIZED_COPPER_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Spruce(), EN_US.Chain(EN_US.Copper(EN_US.Oxidized(EN_US.with()))))));
		Register("spruce_netherite_hanging_sign", "spruce_netherite_wall_hanging_sign", SPRUCE_NETHERITE_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Spruce(), EN_US.Chain(EN_US.Netherite(EN_US.with())))));
		Register("spruce_beehive", SPRUCE_BEEHIVE, List.of(EN_US.Beehive(EN_US.Spruce())));
		Register("spruce_bookshelf", SPRUCE_BOOKSHELF, List.of(EN_US.Bookshelf(EN_US.Spruce())));
		Register("spruce_chiseled_bookshelf", SPRUCE_CHISELED_BOOKSHELF, List.of(EN_US.ChiseledBookshelf(EN_US.Spruce())));
		Register("spruce_crafting_table", SPRUCE_CRAFTING_TABLE, List.of(EN_US.Table(EN_US.Crafting(EN_US.Spruce()))));
		Register("spruce_ladder", SPRUCE_LADDER, List.of(EN_US.Ladder(EN_US.Spruce())));
		Register("spruce_woodcutter", SPRUCE_WOODCUTTER, List.of(EN_US.Woodcutter(EN_US.Spruce())));
		Register("spruce_lectern", SPRUCE_LECTERN, List.of(EN_US.Lectern(EN_US.Spruce())));
		Register("spruce_powder_keg", SPRUCE_POWDER_KEG, List.of(EN_US.PowderKeg(EN_US.Spruce())));
		Register("spruce_log_slab", SPRUCE_LOG_SLAB, List.of(EN_US.Slab(EN_US.Log(EN_US.Spruce()))));
		Register("stripped_spruce_log_slab", STRIPPED_SPRUCE_LOG_SLAB, List.of(EN_US.Slab(EN_US.Log(EN_US.Spruce(EN_US.Stripped())))));
		StrippedBlockUtil.Register(SPRUCE_LOG_SLAB, STRIPPED_SPRUCE_LOG_SLAB);
		Register("spruce_wood_slab", SPRUCE_WOOD_SLAB, List.of(EN_US.Slab(EN_US.Wood(EN_US.Spruce()))));
		Register("stripped_spruce_wood_slab", STRIPPED_SPRUCE_WOOD_SLAB, List.of(EN_US.Slab(EN_US.Wood(EN_US.Spruce(EN_US.Stripped())))));
		StrippedBlockUtil.Register(SPRUCE_WOOD_SLAB, STRIPPED_SPRUCE_WOOD_SLAB);
		Register("white_spruce_leaves", WHITE_SPRUCE_LEAVES, List.of(EN_US.Leaves(EN_US.Spruce(EN_US.White()))));
		Register("minecraft:spruce_chest_boat", SPRUCE_CHEST_BOAT, List.of(EN_US.ChestBoat(EN_US.Spruce())));
		Register("spruce_hammer", SPRUCE_HAMMER, List.of(EN_US.Hammer(EN_US.Spruce())));
		Register("spruce_torch", "spruce_wall_torch", SPRUCE_TORCH, List.of(EN_US._Torch(EN_US.Spruce())));
		Register("spruce_soul_torch", "spruce_soul_wall_torch", SPRUCE_SOUL_TORCH, List.of(EN_US.SoulTorch(EN_US.Spruce())));
		Register("spruce_ender_torch", "spruce_ender_wall_torch", SPRUCE_ENDER_TORCH, List.of(EN_US.EnderTorch(EN_US.Spruce())));
		Register("underwater_spruce_torch", "underwater_spruce_wall_torch", UNDERWATER_SPRUCE_TORCH, List.of(EN_US.Underwater_Torch(EN_US.Spruce())));
		Register("spruce_campfire", SPRUCE_CAMPFIRE, List.of(EN_US.Campfire(EN_US.Spruce())));
		Register("spruce_soul_campfire", SPRUCE_SOUL_CAMPFIRE, List.of(EN_US.Campfire(EN_US.Soul(EN_US.Spruce()))));
		Register("spruce_ender_campfire", SPRUCE_ENDER_CAMPFIRE, List.of(EN_US.Campfire(EN_US.Ender(EN_US.Spruce()))));
		//</editor-fold>
		//<editor-fold desc="Crimson">
		Register("minecraft:crimson_hanging_sign", "minecraft:crimson_wall_hanging_sign", CRIMSON_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Crimson())));
		Register("crimson_iron_hanging_sign", "crimson_iron_wall_hanging_sign", CRIMSON_IRON_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Crimson(), EN_US.Chain(EN_US.Iron(EN_US.with())))));
		Register("crimson_gold_hanging_sign", "crimson_gold_wall_hanging_sign", CRIMSON_GOLD_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Crimson(), EN_US.Chain(EN_US.Gold(EN_US.with())))));
		Register("crimson_copper_hanging_sign", "crimson_copper_wall_hanging_sign", CRIMSON_COPPER_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Crimson(), EN_US.Chain(EN_US.Copper(EN_US.with())))));
		Register("crimson_exposed_copper_hanging_sign", "crimson_exposed_copper_wall_hanging_sign", CRIMSON_EXPOSED_COPPER_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Crimson(), EN_US.Chain(EN_US.Copper(EN_US.Exposed(EN_US.with()))))));
		Register("crimson_weathered_copper_hanging_sign", "crimson_weathered_copper_wall_hanging_sign", CRIMSON_WEATHERED_COPPER_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Crimson(), EN_US.Chain(EN_US.Copper(EN_US.Weathered(EN_US.with()))))));
		Register("crimson_oxidized_copper_hanging_sign", "crimson_oxidized_copper_wall_hanging_sign", CRIMSON_OXIDIZED_COPPER_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Crimson(), EN_US.Chain(EN_US.Copper(EN_US.Oxidized(EN_US.with()))))));
		Register("crimson_netherite_hanging_sign", "crimson_netherite_wall_hanging_sign", CRIMSON_NETHERITE_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Crimson(), EN_US.Chain(EN_US.Netherite(EN_US.with())))));
		Register("crimson_beehive", CRIMSON_BEEHIVE, List.of(EN_US.Beehive(EN_US.Crimson())));
		Register("crimson_bookshelf", CRIMSON_BOOKSHELF, List.of(EN_US.Bookshelf(EN_US.Crimson())));
		Register("crimson_chiseled_bookshelf", CRIMSON_CHISELED_BOOKSHELF, List.of(EN_US.ChiseledBookshelf(EN_US.Crimson())));
		Register("crimson_crafting_table", CRIMSON_CRAFTING_TABLE, List.of(EN_US.Table(EN_US.Crafting(EN_US.Crimson()))));
		Register("crimson_ladder", CRIMSON_LADDER, List.of(EN_US.Ladder(EN_US.Crimson())));
		Register("crimson_woodcutter", CRIMSON_WOODCUTTER, List.of(EN_US.Woodcutter(EN_US.Crimson())));
		Register("crimson_barrel", CRIMSON_BARREL, List.of(EN_US.Barrel(EN_US.Crimson())));
		Register("crimson_lectern", CRIMSON_LECTERN, List.of(EN_US.Lectern(EN_US.Crimson())));
		Register("crimson_powder_keg", CRIMSON_POWDER_KEG, List.of(EN_US.PowderKeg(EN_US.Crimson())));
		Register("crimson_stem_slab", CRIMSON_STEM_SLAB, List.of(EN_US.Slab(EN_US.Stem(EN_US.Crimson()))));
		Register("stripped_crimson_stem_slab", STRIPPED_CRIMSON_STEM_SLAB, List.of(EN_US.Slab(EN_US.Stem(EN_US.Crimson(EN_US.Stripped())))));
		StrippedBlockUtil.Register(CRIMSON_STEM_SLAB, STRIPPED_CRIMSON_STEM_SLAB);
		Register("crimson_hyphae_slab", CRIMSON_HYPHAE_SLAB, List.of(EN_US.Slab(EN_US.Hyphae(EN_US.Crimson()))));
		Register("stripped_crimson_hyphae_slab", STRIPPED_CRIMSON_HYPHAE_SLAB, List.of(EN_US.Slab(EN_US.Hyphae(EN_US.Crimson(EN_US.Stripped())))));
		StrippedBlockUtil.Register(CRIMSON_HYPHAE_SLAB, STRIPPED_CRIMSON_HYPHAE_SLAB);
		Register("crimson_hammer", CRIMSON_HAMMER, List.of(EN_US.Hammer(EN_US.Crimson())));
		Register("nether_wart_slab", NETHER_WART_SLAB, List.of(EN_US.Slab(EN_US.Wart(EN_US.Nether()))));
		Register("crimson_torch", "crimson_wall_torch", CRIMSON_TORCH, List.of(EN_US._Torch(EN_US.Crimson())));
		Register("crimson_soul_torch", "crimson_soul_wall_torch", CRIMSON_SOUL_TORCH, List.of(EN_US.SoulTorch(EN_US.Crimson())));
		Register("crimson_ender_torch", "crimson_ender_wall_torch", CRIMSON_ENDER_TORCH, List.of(EN_US.EnderTorch(EN_US.Crimson())));
		Register("underwater_crimson_torch", "underwater_crimson_wall_torch", UNDERWATER_CRIMSON_TORCH, List.of(EN_US.Underwater_Torch(EN_US.Crimson())));
		Register("crimson_campfire", CRIMSON_CAMPFIRE, List.of(EN_US.Campfire(EN_US.Crimson())));
		Register("crimson_soul_campfire", CRIMSON_SOUL_CAMPFIRE, List.of(EN_US.Campfire(EN_US.Soul(EN_US.Crimson()))));
		Register("crimson_ender_campfire", CRIMSON_ENDER_CAMPFIRE, List.of(EN_US.Campfire(EN_US.Ender(EN_US.Crimson()))));
		//</editor-fold>
		//<editor-fold desc="Warped">
		Register("minecraft:warped_hanging_sign", "minecraft:warped_wall_hanging_sign", WARPED_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Warped())));
		Register("warped_iron_hanging_sign", "warped_iron_wall_hanging_sign", WARPED_IRON_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Warped(), EN_US.Chain(EN_US.Iron(EN_US.with())))));
		Register("warped_gold_hanging_sign", "warped_gold_wall_hanging_sign", WARPED_GOLD_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Warped(), EN_US.Chain(EN_US.Gold(EN_US.with())))));
		Register("warped_copper_hanging_sign", "warped_copper_wall_hanging_sign", WARPED_COPPER_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Warped(), EN_US.Chain(EN_US.Copper(EN_US.with())))));
		Register("warped_exposed_copper_hanging_sign", "warped_exposed_copper_wall_hanging_sign", WARPED_EXPOSED_COPPER_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Warped(), EN_US.Chain(EN_US.Copper(EN_US.Exposed(EN_US.with()))))));
		Register("warped_weathered_copper_hanging_sign", "warped_weathered_copper_wall_hanging_sign", WARPED_WEATHERED_COPPER_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Warped(), EN_US.Chain(EN_US.Copper(EN_US.Weathered(EN_US.with()))))));
		Register("warped_oxidized_copper_hanging_sign", "warped_oxidized_copper_wall_hanging_sign", WARPED_OXIDIZED_COPPER_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Warped(), EN_US.Chain(EN_US.Copper(EN_US.Oxidized(EN_US.with()))))));
		Register("warped_netherite_hanging_sign", "warped_netherite_wall_hanging_sign", WARPED_NETHERITE_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Warped(), EN_US.Chain(EN_US.Netherite(EN_US.with())))));
		Register("warped_beehive", WARPED_BEEHIVE, List.of(EN_US.Beehive(EN_US.Warped())));
		Register("warped_bookshelf", WARPED_BOOKSHELF, List.of(EN_US.Bookshelf(EN_US.Warped())));
		Register("warped_chiseled_bookshelf", WARPED_CHISELED_BOOKSHELF, List.of(EN_US.ChiseledBookshelf(EN_US.Warped())));
		Register("warped_crafting_table", WARPED_CRAFTING_TABLE, List.of(EN_US.Table(EN_US.Crafting(EN_US.Warped()))));
		Register("warped_ladder", WARPED_LADDER, List.of(EN_US.Ladder(EN_US.Warped())));
		Register("warped_woodcutter", WARPED_WOODCUTTER, List.of(EN_US.Woodcutter(EN_US.Warped())));
		Register("warped_barrel", WARPED_BARREL, List.of(EN_US.Barrel(EN_US.Warped())));
		Register("warped_lectern", WARPED_LECTERN, List.of(EN_US.Lectern(EN_US.Warped())));
		Register("warped_powder_keg", WARPED_POWDER_KEG, List.of(EN_US.PowderKeg(EN_US.Warped())));
		Register("warped_stem_slab", WARPED_STEM_SLAB, List.of(EN_US.Slab(EN_US.Stem(EN_US.Warped()))));
		Register("stripped_warped_stem_slab", STRIPPED_WARPED_STEM_SLAB, List.of(EN_US.Slab(EN_US.Stem(EN_US.Warped(EN_US.Stripped())))));
		StrippedBlockUtil.Register(WARPED_STEM_SLAB, STRIPPED_WARPED_STEM_SLAB);
		Register("warped_hyphae_slab", WARPED_HYPHAE_SLAB, List.of(EN_US.Slab(EN_US.Hyphae(EN_US.Warped()))));
		Register("stripped_warped_hyphae_slab", STRIPPED_WARPED_HYPHAE_SLAB, List.of(EN_US.Slab(EN_US.Hyphae(EN_US.Warped(EN_US.Stripped())))));
		StrippedBlockUtil.Register(WARPED_HYPHAE_SLAB, STRIPPED_WARPED_HYPHAE_SLAB);
		Register("warped_hammer", WARPED_HAMMER, List.of(EN_US.Hammer(EN_US.Warped())));
		Register("warped_wart", WARPED_WART, List.of(EN_US.Wart(EN_US.Warped())));
		Register("warped_wart_slab", WARPED_WART_SLAB, List.of(EN_US.Slab(EN_US.Wart(EN_US.Warped()))));
		Register("warped_torch", "warped_wall_torch", WARPED_TORCH, List.of(EN_US._Torch(EN_US.Warped())));
		Register("warped_soul_torch", "warped_soul_wall_torch", WARPED_SOUL_TORCH, List.of(EN_US.SoulTorch(EN_US.Warped())));
		Register("warped_ender_torch", "warped_ender_wall_torch", WARPED_ENDER_TORCH, List.of(EN_US.EnderTorch(EN_US.Warped())));
		Register("underwater_warped_torch", "underwater_warped_wall_torch", UNDERWATER_WARPED_TORCH, List.of(EN_US.Underwater_Torch(EN_US.Warped())));
		Register("warped_campfire", WARPED_CAMPFIRE, List.of(EN_US.Campfire(EN_US.Warped())));
		Register("warped_soul_campfire", WARPED_SOUL_CAMPFIRE, List.of(EN_US.Campfire(EN_US.Soul(EN_US.Warped()))));
		Register("warped_ender_campfire", WARPED_ENDER_CAMPFIRE, List.of(EN_US.Campfire(EN_US.Ender(EN_US.Warped()))));
		//</editor-fold>
		//<editor-fold desc="Light Sources">
		Register("underwater_torch", "underwater_wall_torch", UNDERWATER_TORCH, List.of(EN_US.Underwater_Torch()));
		Register("unlit_torch", "unlit_wall_torch", UNLIT_TORCH, List.of(EN_US._Torch(EN_US.Unlit())));
		Register("unlit_soul_torch", "unlit_soul_wall_torch", UNLIT_SOUL_TORCH, List.of(EN_US.SoulTorch(EN_US.Unlit())));
		Register("unlit_lantern", UNLIT_LANTERN, List.of(EN_US.Lantern(EN_US.Unlit())));
		Register("unlit_soul_lantern", UNLIT_SOUL_LANTERN, List.of(EN_US.SoulLantern(EN_US.Unlit())));
		Register("empty_lantern", EMPTY_LANTERN, List.of(EN_US.Lantern(EN_US.Empty())));
		//</editor-fold>
		//<editor-fold desc="Soul & Ender Fire">
		Register("soul_candle", SOUL_CANDLE, List.of(EN_US.Candle(EN_US.Soul())));
		Register("ender_candle", ENDER_CANDLE, List.of(EN_US.Candle(EN_US.Ender())));
		Register("netherrack_candle", NETHERRACK_CANDLE, List.of(EN_US.Candle(EN_US.Netherrack())));
		Register("soul_candle_cake", SOUL_CANDLE_CAKE, List.of(EN_US.Candle(EN_US.Soul(EN_US.with(EN_US.Cake())))));
		Register("ender_candle_cake", ENDER_CANDLE_CAKE, List.of(EN_US.Candle(EN_US.Ender(EN_US.with(EN_US.Cake())))));
		Register("netherrack_candle_cake", NETHERRACK_CANDLE_CAKE, List.of(EN_US.Candle(EN_US.Netherrack(EN_US.with(EN_US.Cake())))));
		Register("ender_torch", "ender_wall_torch", ENDER_TORCH, List.of(EN_US.EnderTorch()));
		Register("ender_lantern", ENDER_LANTERN, List.of(EN_US.EnderLantern()));
		Register("ender_campfire", ENDER_CAMPFIRE, List.of(EN_US.Campfire(EN_US.Ender())));
		//</editor-fold>
		//<editor-fold desc="Stone">
		Register("mossy_chiseled_stone_bricks", MOSSY_CHISELED_STONE_BRICKS, List.of(EN_US.Bricks(EN_US.Stone(EN_US.Chiseled(EN_US.Mossy())))));
		Register("stone_tiles", STONE_TILES, List.of(EN_US.Tiles(EN_US.Stone())));
		Register("stone_tile_stairs", STONE_TILE_STAIRS, List.of(EN_US.Stairs(EN_US.Tile(EN_US.Stone()))));
		Register("stone_tile_slab", STONE_TILE_SLAB, List.of(EN_US.Slab(EN_US.Tile(EN_US.Stone()))));
		Register("stone_tile_wall", STONE_TILE_WALL, List.of(EN_US.Wall(EN_US.Tile(EN_US.Stone()))));
		Register("smooth_stone_stairs", SMOOTH_STONE_STAIRS, List.of(EN_US.Stairs(EN_US.Stone(EN_US.Smooth()))));
		Register("stone_hammer", STONE_HAMMER, List.of(EN_US.Hammer(EN_US.Stone())));
		//</editor-fold>
		//<editor-fold desc="Andesite">
		Register("polished_andesite_wall", POLISHED_ANDESITE_WALL, List.of(EN_US.Wall(EN_US.Andesite(EN_US.Polished()))));
		Register("cut_polished_andesite", CUT_POLISHED_ANDESITE, List.of(EN_US.Andesite(EN_US.Polished(EN_US.Cut()))));
		Register("cut_polished_andesite_stairs", CUT_POLISHED_ANDESITE_STAIRS, List.of(EN_US.Stairs(EN_US.Andesite(EN_US.Polished(EN_US.Cut())))));
		Register("cut_polished_andesite_slab", CUT_POLISHED_ANDESITE_SLAB, List.of(EN_US.Slab(EN_US.Andesite(EN_US.Polished(EN_US.Cut())))));
		Register("cut_polished_andesite_wall", CUT_POLISHED_ANDESITE_WALL, List.of(EN_US.Wall(EN_US.Andesite(EN_US.Polished(EN_US.Cut())))));
		Register("andesite_bricks", ANDESITE_BRICKS, List.of(EN_US.Bricks(EN_US.Andesite())));
		Register("andesite_brick_stairs", ANDESITE_BRICK_STAIRS, List.of(EN_US.Stairs(EN_US.Brick(EN_US.Andesite()))));
		Register("andesite_brick_slab", ANDESITE_BRICK_SLAB, List.of(EN_US.Slab(EN_US.Brick(EN_US.Andesite()))));
		Register("andesite_brick_wall", ANDESITE_BRICK_WALL, List.of(EN_US.Wall(EN_US.Brick(EN_US.Andesite()))));
		Register("chiseled_andesite_bricks", CHISELED_ANDESITE_BRICKS, List.of(EN_US.Bricks(EN_US.Andesite(EN_US.Chiseled()))));
		Register("andesite_tiles", ANDESITE_TILES, List.of(EN_US.Tiles(EN_US.Andesite())));
		Register("andesite_tile_stairs", ANDESITE_TILE_STAIRS, List.of(EN_US.Stairs(EN_US.Tile(EN_US.Andesite()))));
		Register("andesite_tile_slab", ANDESITE_TILE_SLAB, List.of(EN_US.Slab(EN_US.Tile(EN_US.Andesite()))));
		Register("andesite_tile_wall", ANDESITE_TILE_WALL, List.of(EN_US.Wall(EN_US.Tile(EN_US.Andesite()))));
		//</editor-fold>
		//<editor-fold desc="Deepslate">
		Register("minecraft:reinforced_deepslate", REINFORCED_DEEPSLATE, List.of(EN_US.Deepslate(EN_US.Reinforced())));
		Register("mossy_cobbled_deepslate", MOSSY_COBBLED_DEEPSLATE, List.of(EN_US.Deepslate(EN_US.Cobbled(EN_US.Mossy()))));
		Register("mossy_cobbled_deepslate_stairs", MOSSY_COBBLED_DEEPSLATE_STAIRS, List.of(EN_US.Stairs(EN_US.Deepslate(EN_US.Cobbled(EN_US.Mossy())))));
		Register("mossy_cobbled_deepslate_slab", MOSSY_COBBLED_DEEPSLATE_SLAB, List.of(EN_US.Slab(EN_US.Deepslate(EN_US.Cobbled(EN_US.Mossy())))));
		Register("mossy_cobbled_deepslate_wall", MOSSY_COBBLED_DEEPSLATE_WALL, List.of(EN_US.Wall(EN_US.Deepslate(EN_US.Cobbled(EN_US.Mossy())))));
		Register("mossy_deepslate_bricks", MOSSY_DEEPSLATE_BRICKS, List.of(EN_US.Bricks(EN_US.Deepslate(EN_US.Mossy()))));
		Register("mossy_deepslate_brick_stairs", MOSSY_DEEPSLATE_BRICK_STAIRS, List.of(EN_US.Stairs(EN_US.Brick(EN_US.Deepslate(EN_US.Mossy())))));
		Register("mossy_deepslate_brick_slab", MOSSY_DEEPSLATE_BRICK_SLAB, List.of(EN_US.Slab(EN_US.Brick(EN_US.Deepslate(EN_US.Mossy())))));
		Register("mossy_deepslate_brick_wall", MOSSY_DEEPSLATE_BRICK_WALL, List.of(EN_US.Wall(EN_US.Brick(EN_US.Deepslate(EN_US.Mossy())))));
		//</editor-fold>
		//<editor-fold desc="Diorite">
		Register("polished_diorite_wall", POLISHED_DIORITE_WALL, List.of(EN_US.Wall(EN_US.Diorite(EN_US.Polished()))));
		Register("cut_polished_diorite", CUT_POLISHED_DIORITE, List.of(EN_US.Diorite(EN_US.Polished(EN_US.Cut()))));
		Register("cut_polished_diorite_stairs", CUT_POLISHED_DIORITE_STAIRS, List.of(EN_US.Stairs(EN_US.Diorite(EN_US.Polished(EN_US.Cut())))));
		Register("cut_polished_diorite_slab", CUT_POLISHED_DIORITE_SLAB, List.of(EN_US.Slab(EN_US.Diorite(EN_US.Polished(EN_US.Cut())))));
		Register("cut_polished_diorite_wall", CUT_POLISHED_DIORITE_WALL, List.of(EN_US.Wall(EN_US.Diorite(EN_US.Polished(EN_US.Cut())))));
		Register("diorite_bricks", DIORITE_BRICKS, List.of(EN_US.Bricks(EN_US.Diorite())));
		Register("diorite_brick_stairs", DIORITE_BRICK_STAIRS, List.of(EN_US.Stairs(EN_US.Brick(EN_US.Diorite()))));
		Register("diorite_brick_slab", DIORITE_BRICK_SLAB, List.of(EN_US.Slab(EN_US.Brick(EN_US.Diorite()))));
		Register("diorite_brick_wall", DIORITE_BRICK_WALL, List.of(EN_US.Wall(EN_US.Brick(EN_US.Diorite()))));
		Register("chiseled_diorite_bricks", CHISELED_DIORITE_BRICKS, List.of(EN_US.Bricks(EN_US.Diorite(EN_US.Chiseled()))));
		Register("diorite_tiles", DIORITE_TILES, List.of(EN_US.Tiles(EN_US.Diorite())));
		Register("diorite_tile_stairs", DIORITE_TILE_STAIRS, List.of(EN_US.Stairs(EN_US.Tile(EN_US.Diorite()))));
		Register("diorite_tile_slab", DIORITE_TILE_SLAB, List.of(EN_US.Slab(EN_US.Tile(EN_US.Diorite()))));
		Register("diorite_tile_wall", DIORITE_TILE_WALL, List.of(EN_US.Wall(EN_US.Tile(EN_US.Diorite()))));
		//</editor-fold>
		//<editor-fold desc="Granite">
		Register("polished_granite_wall", POLISHED_GRANITE_WALL, List.of(EN_US.Wall(EN_US.Granite(EN_US.Polished()))));
		Register("chiseled_granite", CHISELED_GRANITE, List.of(EN_US.Granite(EN_US.Chiseled())));
		Register("chiseled_granite_slab", CHISELED_GRANITE_SLAB, List.of(EN_US.Slab(EN_US.Granite(EN_US.Chiseled()))));
		Register("cut_polished_granite", CUT_POLISHED_GRANITE, List.of(EN_US.Granite(EN_US.Polished(EN_US.Cut()))));
		Register("cut_polished_granite_stairs", CUT_POLISHED_GRANITE_STAIRS, List.of(EN_US.Stairs(EN_US.Granite(EN_US.Polished(EN_US.Cut())))));
		Register("cut_polished_granite_slab", CUT_POLISHED_GRANITE_SLAB, List.of(EN_US.Slab(EN_US.Granite(EN_US.Polished(EN_US.Cut())))));
		Register("cut_polished_granite_wall", CUT_POLISHED_GRANITE_WALL, List.of(EN_US.Wall(EN_US.Granite(EN_US.Polished(EN_US.Cut())))));
		Register("granite_bricks", GRANITE_BRICKS, List.of(EN_US.Bricks(EN_US.Granite())));
		Register("granite_brick_stairs", GRANITE_BRICK_STAIRS, List.of(EN_US.Stairs(EN_US.Brick(EN_US.Granite()))));
		Register("granite_brick_slab", GRANITE_BRICK_SLAB, List.of(EN_US.Slab(EN_US.Brick(EN_US.Granite()))));
		Register("granite_brick_wall", GRANITE_BRICK_WALL, List.of(EN_US.Wall(EN_US.Brick(EN_US.Granite()))));
		Register("chiseled_granite_bricks", CHISELED_GRANITE_BRICKS, List.of(EN_US.Bricks(EN_US.Granite(EN_US.Chiseled()))));
		Register("granite_tiles", GRANITE_TILES, List.of(EN_US.Tiles(EN_US.Granite())));
		Register("granite_tile_stairs", GRANITE_TILE_STAIRS, List.of(EN_US.Stairs(EN_US.Tile(EN_US.Granite()))));
		Register("granite_tile_slab", GRANITE_TILE_SLAB, List.of(EN_US.Slab(EN_US.Tile(EN_US.Granite()))));
		Register("granite_tile_wall", GRANITE_TILE_WALL, List.of(EN_US.Wall(EN_US.Tile(EN_US.Granite()))));
		//</editor-fold>
		//<editor-fold desc="Sandstone">
		Register("smooth_sandstone_wall", SMOOTH_SANDSTONE_WALL, List.of(EN_US.Wall(EN_US.Sandstone(EN_US.Smooth()))));
		Register("smooth_red_sandstone_wall", SMOOTH_RED_SANDSTONE_WALL, List.of(EN_US.Wall(EN_US.Sandstone(EN_US.Red(EN_US.Smooth())))));
		//</editor-fold>
		//<editor-fold desc="Prismarine">
		Register("prismarine_rod", PRISMARINE_ROD, List.of(EN_US.Rod(EN_US.Prismarine())));
		Register("prismarine_torch", "prismarine_wall_torch", PRISMARINE_TORCH, List.of(EN_US._Torch(EN_US.Prismarine())));
		Register("prismarine_soul_torch", "prismarine_soul_wall_torch", PRISMARINE_SOUL_TORCH, List.of(EN_US.SoulTorch(EN_US.Prismarine())));
		Register("prismarine_ender_torch", "prismarine_ender_wall_torch", PRISMARINE_ENDER_TORCH, List.of(EN_US.EnderTorch(EN_US.Prismarine())));
		Register("underwater_prismarine_torch", "underwater_prismarine_wall_torch", UNDERWATER_PRISMARINE_TORCH, List.of(EN_US.Underwater_Torch(EN_US.Prismarine())));
		Register("chiseled_prismarine_bricks", CHISELED_PRISMARINE_BRICKS, List.of(EN_US.Bricks(EN_US.Prismarine(EN_US.Chiseled()))));
		Register("smooth_chiseled_prismarine_bricks", SMOOTH_CHISELED_PRISMARINE_BRICKS, List.of(EN_US.Bricks(EN_US.Prismarine(EN_US.Chiseled(EN_US.Smooth())))));
		Register("cut_prismarine_bricks", CUT_PRISMARINE_BRICKS, List.of(EN_US.Bricks(EN_US.Prismarine(EN_US.Cut()))));
		Register("cut_prismarine_brick_stairs", CUT_PRISMARINE_BRICK_STAIRS, List.of(EN_US.Stairs(EN_US.Brick(EN_US.Prismarine(EN_US.Cut())))));
		Register("cut_prismarine_brick_slab", CUT_PRISMARINE_BRICK_SLAB, List.of(EN_US.Slab(EN_US.Brick(EN_US.Prismarine(EN_US.Cut())))));
		Register("chiseled_prismarine", CHISELED_PRISMARINE, List.of(EN_US.Prismarine(EN_US.Chiseled())));
		Register("chiseled_prismarine_brick_stairs", CHISELED_PRISMARINE_STAIRS, List.of(EN_US.Stairs(EN_US.Prismarine(EN_US.Chiseled()))));
		Register("chiseled_prismarine_brick_slab", CHISELED_PRISMARINE_SLAB, List.of(EN_US.Slab(EN_US.Prismarine(EN_US.Chiseled()))));
		Register("prismarine_tiles", PRISMARINE_TILES, List.of(EN_US.Tiles(EN_US.Prismarine())));
		Register("prismarine_tile_stairs", PRISMARINE_TILE_STAIRS, List.of(EN_US.Stairs(EN_US.Tile(EN_US.Prismarine()))));
		Register("prismarine_tile_slab", PRISMARINE_TILE_SLAB, List.of(EN_US.Slab(EN_US.Tile(EN_US.Prismarine()))));
		Register("prismarine_tile_wall", PRISMARINE_TILE_WALL, List.of(EN_US.Wall(EN_US.Tile(EN_US.Prismarine()))));
		Register("dark_prismarine_wall", DARK_PRISMARINE_WALL, List.of(EN_US.Wall(EN_US.Prismarine(EN_US.Dark()))));
		//</editor-fold>
		//<editor-fold desc="Basalt">
		Register("polished_basalt_slab", POLISHED_BASALT_SLAB, List.of(EN_US.Slab(EN_US.Basalt(EN_US.Polished()))));
		Register("smooth_basalt_stairs", SMOOTH_BASALT_STAIRS, List.of(EN_US.Stairs(EN_US.Basalt(EN_US.Smooth()))));
		Register("smooth_basalt_slab", SMOOTH_BASALT_SLAB, List.of(EN_US.Slab(EN_US.Basalt(EN_US.Smooth()))));
		Register("smooth_basalt_wall", SMOOTH_BASALT_WALL, List.of(EN_US.Wall(EN_US.Basalt(EN_US.Smooth()))));
		Register("smooth_basalt_bricks", SMOOTH_BASALT_BRICKS, List.of(EN_US.Bricks(EN_US.Basalt(EN_US.Smooth()))));
		Register("smooth_basalt_brick_stairs", SMOOTH_BASALT_BRICK_STAIRS, List.of(EN_US.Stairs(EN_US.Brick(EN_US.Basalt(EN_US.Smooth())))));
		Register("smooth_basalt_brick_slab", SMOOTH_BASALT_BRICK_SLAB, List.of(EN_US.Slab(EN_US.Brick(EN_US.Basalt(EN_US.Smooth())))));
		Register("smooth_basalt_brick_wall", SMOOTH_BASALT_BRICK_WALL, List.of(EN_US.Wall(EN_US.Brick(EN_US.Basalt(EN_US.Smooth())))));
		//</editor-fold>
		//<editor-fold desc="Calcite">
		Register("calcite_stairs", CALCITE_STAIRS, List.of(EN_US.Stairs(EN_US.Calcite())));
		Register("calcite_slab", CALCITE_SLAB, List.of(EN_US.Slab(EN_US.Calcite())));
		Register("calcite_wall", CALCITE_WALL, List.of(EN_US.Wall(EN_US.Calcite())));
		Register("smooth_calcite", SMOOTH_CALCITE, List.of(EN_US.Calcite(EN_US.Smooth())));
		Register("smooth_calcite_stairs", SMOOTH_CALCITE_STAIRS, List.of(EN_US.Stairs(EN_US.Calcite(EN_US.Smooth()))));
		Register("smooth_calcite_slab", SMOOTH_CALCITE_SLAB, List.of(EN_US.Slab(EN_US.Calcite(EN_US.Smooth()))));
		Register("smooth_calcite_wall", SMOOTH_CALCITE_WALL, List.of(EN_US.Wall(EN_US.Calcite(EN_US.Smooth()))));
		Register("calcite_bricks", CALCITE_BRICKS, List.of(EN_US.Bricks(EN_US.Calcite())));
		Register("calcite_brick_stairs", CALCITE_BRICK_STAIRS, List.of(EN_US.Stairs(EN_US.Brick(EN_US.Calcite()))));
		Register("calcite_brick_slab", CALCITE_BRICK_SLAB, List.of(EN_US.Slab(EN_US.Brick(EN_US.Calcite()))));
		Register("calcite_brick_wall", CALCITE_BRICK_WALL, List.of(EN_US.Wall(EN_US.Brick(EN_US.Calcite()))));
		Register("calcite_tiles", CALCITE_TILES, List.of(EN_US.Tiles(EN_US.Calcite())));
		Register("calcite_tile_stairs", CALCITE_TILE_STAIRS, List.of(EN_US.Stairs(EN_US.Tile(EN_US.Calcite()))));
		Register("calcite_tile_slab", CALCITE_TILE_SLAB, List.of(EN_US.Slab(EN_US.Tile(EN_US.Calcite()))));
		Register("calcite_tile_wall", CALCITE_TILE_WALL, List.of(EN_US.Wall(EN_US.Tile(EN_US.Calcite()))));
		//</editor-fold>
		//<editor-fold desc="Dripstone">
		Register("dripstone_stairs", DRIPSTONE_STAIRS, List.of(EN_US.Stairs(EN_US.Dripstone())));
		Register("dripstone_slab", DRIPSTONE_SLAB, List.of(EN_US.Slab(EN_US.Dripstone())));
		Register("dripstone_wall", DRIPSTONE_WALL, List.of(EN_US.Wall(EN_US.Dripstone())));
		Register("smooth_dripstone", SMOOTH_DRIPSTONE, List.of(EN_US.Dripstone(EN_US.Smooth())));
		Register("smooth_dripstone_stairs", SMOOTH_DRIPSTONE_STAIRS, List.of(EN_US.Stairs(EN_US.Dripstone(EN_US.Smooth()))));
		Register("smooth_dripstone_slab", SMOOTH_DRIPSTONE_SLAB, List.of(EN_US.Slab(EN_US.Dripstone(EN_US.Smooth()))));
		Register("smooth_dripstone_wall", SMOOTH_DRIPSTONE_WALL, List.of(EN_US.Wall(EN_US.Dripstone(EN_US.Smooth()))));
		Register("dripstone_bricks", DRIPSTONE_BRICKS, List.of(EN_US.Bricks(EN_US.Dripstone())));
		Register("dripstone_brick_stairs", DRIPSTONE_BRICK_STAIRS, List.of(EN_US.Stairs(EN_US.Brick(EN_US.Dripstone()))));
		Register("dripstone_brick_slab", DRIPSTONE_BRICK_SLAB, List.of(EN_US.Slab(EN_US.Brick(EN_US.Dripstone()))));
		Register("dripstone_brick_wall", DRIPSTONE_BRICK_WALL, List.of(EN_US.Wall(EN_US.Brick(EN_US.Dripstone()))));
		Register("chiseled_dripstone_bricks", CHISELED_DRIPSTONE_BRICKS, List.of(EN_US.Bricks(EN_US.Dripstone(EN_US.Chiseled()))));
		Register("dripstone_tiles", DRIPSTONE_TILES, List.of(EN_US.Tiles(EN_US.Dripstone())));
		Register("dripstone_tile_stairs", DRIPSTONE_TILE_STAIRS, List.of(EN_US.Stairs(EN_US.Tile(EN_US.Dripstone()))));
		Register("dripstone_tile_slab", DRIPSTONE_TILE_SLAB, List.of(EN_US.Slab(EN_US.Tile(EN_US.Dripstone()))));
		Register("dripstone_tile_wall", DRIPSTONE_TILE_WALL, List.of(EN_US.Wall(EN_US.Tile(EN_US.Dripstone()))));
		//</editor-fold>
		//<editor-fold desc="Tuff">
		Register("minecraft:tuff_stairs", TUFF_STAIRS, List.of(EN_US.Stairs(EN_US.Tuff())));
		Register("minecraft:tuff_slab", TUFF_SLAB, List.of(EN_US.Slab(EN_US.Tuff())));
		Register("minecraft:tuff_wall", TUFF_WALL, List.of(EN_US.Wall(EN_US.Tuff())));
		Register("minecraft:polished_tuff", POLISHED_TUFF, List.of(EN_US.Tuff(EN_US.Polished())));
		Register("minecraft:polished_tuff_stairs", POLISHED_TUFF_STAIRS, List.of(EN_US.Stairs(EN_US.Tuff(EN_US.Polished()))));
		Register("minecraft:polished_tuff_slab", POLISHED_TUFF_SLAB, List.of(EN_US.Slab(EN_US.Tuff(EN_US.Polished()))));
		Register("minecraft:polished_tuff_wall", POLISHED_TUFF_WALL, List.of(EN_US.Wall(EN_US.Tuff(EN_US.Polished()))));
		Register("minecraft:tuff_bricks", TUFF_BRICKS, List.of(EN_US.Bricks(EN_US.Tuff())));
		Register("minecraft:tuff_brick_stairs", TUFF_BRICK_STAIRS, List.of(EN_US.Stairs(EN_US.Brick(EN_US.Tuff()))));
		Register("minecraft:tuff_brick_slab", TUFF_BRICK_SLAB, List.of(EN_US.Slab(EN_US.Brick(EN_US.Tuff()))));
		Register("minecraft:tuff_brick_wall", TUFF_BRICK_WALL, List.of(EN_US.Wall(EN_US.Brick(EN_US.Tuff()))));
		Register("minecraft:chiseled_tuff", CHISELED_TUFF, List.of(EN_US.Tuff(EN_US.Chiseled())));
		Register("minecraft:chiseled_tuff_bricks", CHISELED_TUFF_BRICKS, List.of(EN_US.Bricks(EN_US.Tuff(EN_US.Chiseled()))));
		Register("tuff_tiles", TUFF_TILES, List.of(EN_US.Tiles(EN_US.Tuff())));
		Register("tuff_tile_stairs", TUFF_TILE_STAIRS, List.of(EN_US.Stairs(EN_US.Tile(EN_US.Tuff()))));
		Register("tuff_tile_slab", TUFF_TILE_SLAB, List.of(EN_US.Slab(EN_US.Tile(EN_US.Tuff()))));
		Register("tuff_tile_wall", TUFF_TILE_WALL, List.of(EN_US.Wall(EN_US.Tile(EN_US.Tuff()))));
		//</editor-fold>
		//<editor-fold desc="Shale">
		Register("shale", SHALE, List.of(EN_US.Shale()));
		Register("cobbled_shale", COBBLED_SHALE, List.of(EN_US.Shale(EN_US.Cobbled())));
		Register("cobbled_shale_stairs", COBBLED_SHALE_STAIRS, List.of(EN_US.Stairs(EN_US.Shale(EN_US.Cobbled()))));
		Register("cobbled_shale_slab", COBBLED_SHALE_SLAB, List.of(EN_US.Slab(EN_US.Shale(EN_US.Cobbled()))));
		Register("cobbled_shale_wall", COBBLED_SHALE_WALL, List.of(EN_US.Wall(EN_US.Shale(EN_US.Cobbled()))));
		Register("polished_shale", POLISHED_SHALE, List.of(EN_US.Shale(EN_US.Polished())));
		Register("polished_shale_stairs", POLISHED_SHALE_STAIRS, List.of(EN_US.Stairs(EN_US.Shale(EN_US.Polished()))));
		Register("polished_shale_slab", POLISHED_SHALE_SLAB, List.of(EN_US.Slab(EN_US.Shale(EN_US.Polished()))));
		Register("polished_shale_wall", POLISHED_SHALE_WALL, List.of(EN_US.Wall(EN_US.Shale(EN_US.Polished()))));
		Register("shale_bricks", SHALE_BRICKS, List.of(EN_US.Bricks(EN_US.Shale())));
		Register("shale_brick_stairs", SHALE_BRICK_STAIRS, List.of(EN_US.Stairs(EN_US.Brick(EN_US.Shale()))));
		Register("shale_brick_slab", SHALE_BRICK_SLAB, List.of(EN_US.Slab(EN_US.Brick(EN_US.Shale()))));
		Register("shale_brick_wall", SHALE_BRICK_WALL, List.of(EN_US.Wall(EN_US.Brick(EN_US.Shale()))));
		Register("shale_tiles", SHALE_TILES, List.of(EN_US.Tiles(EN_US.Shale())));
		Register("shale_tile_stairs", SHALE_TILE_STAIRS, List.of(EN_US.Stairs(EN_US.Tile(EN_US.Shale()))));
		Register("shale_tile_slab", SHALE_TILE_SLAB, List.of(EN_US.Slab(EN_US.Tile(EN_US.Shale()))));
		Register("shale_tile_wall", SHALE_TILE_WALL, List.of(EN_US.Wall(EN_US.Tile(EN_US.Shale()))));
		//</editor-fold>
		//<editor-fold desc="End Stone">
		Register("end_stone_slab", END_STONE_SLAB, List.of(EN_US.Slab(EN_US.Stone(EN_US.End()))));
		Register("end_stone_pillar", END_STONE_PILLAR, List.of(EN_US.Pillar(EN_US.Stone(EN_US.End()))));
		Register("end_stone_tiles", END_STONE_TILES, List.of(EN_US.Tiles(EN_US.Stone(EN_US.End()))));
		Register("end_stone_tile_stairs", END_STONE_TILE_STAIRS, List.of(EN_US.Stairs(EN_US.Tile(EN_US.Stone(EN_US.End())))));
		Register("end_stone_tile_slab", END_STONE_TILE_SLAB, List.of(EN_US.Slab(EN_US.Tile(EN_US.Stone(EN_US.End())))));
		Register("end_stone_tile_wall", END_STONE_TILE_WALL, List.of(EN_US.Wall(EN_US.Tile(EN_US.Stone(EN_US.End())))));
		//</editor-fold>
		//<editor-fold desc="End Shale">
		Register("end_shale", END_SHALE, List.of(EN_US.Shale(EN_US.End())));
		Register("cobbled_end_shale", COBBLED_END_SHALE, List.of(EN_US.Shale(EN_US.End(EN_US.Cobbled()))));
		Register("cobbled_end_shale_stairs", COBBLED_END_SHALE_STAIRS, List.of(EN_US.Stairs(EN_US.Shale(EN_US.End(EN_US.Cobbled())))));
		Register("cobbled_end_shale_slab", COBBLED_END_SHALE_SLAB, List.of(EN_US.Slab(EN_US.Shale(EN_US.End(EN_US.Cobbled())))));
		Register("cobbled_end_shale_wall", COBBLED_END_SHALE_WALL, List.of(EN_US.Wall(EN_US.Shale(EN_US.End(EN_US.Cobbled())))));
		Register("end_shale_bricks", END_SHALE_BRICKS, List.of(EN_US.Bricks(EN_US.Shale(EN_US.End()))));
		Register("end_shale_brick_stairs", END_SHALE_BRICK_STAIRS, List.of(EN_US.Stairs(EN_US.Brick(EN_US.Shale(EN_US.End())))));
		Register("end_shale_brick_slab", END_SHALE_BRICK_SLAB, List.of(EN_US.Slab(EN_US.Brick(EN_US.Shale(EN_US.End())))));
		Register("end_shale_brick_wall", END_SHALE_BRICK_WALL, List.of(EN_US.Wall(EN_US.Brick(EN_US.Shale(EN_US.End())))));
		Register("end_shale_tiles", END_SHALE_TILES, List.of(EN_US.Tiles(EN_US.Shale(EN_US.End()))));
		Register("end_shale_tile_stairs", END_SHALE_TILE_STAIRS, List.of(EN_US.Stairs(EN_US.Tile(EN_US.Shale(EN_US.End())))));
		Register("end_shale_tile_slab", END_SHALE_TILE_SLAB, List.of(EN_US.Slab(EN_US.Tile(EN_US.Shale(EN_US.End())))));
		Register("end_shale_tile_wall", END_SHALE_TILE_WALL, List.of(EN_US.Wall(EN_US.Tile(EN_US.Shale(EN_US.End())))));
		//</editor-fold>
		//<editor-fold desc="End Rock">
		Register("end_rock", END_ROCK, List.of(EN_US.Rock(EN_US.End())));
		Register("end_rock_shale", END_ROCK_SHALE, List.of(EN_US.Shale(EN_US.Rock(EN_US.End()))));
		Register("end_shale_rock", END_SHALE_ROCK, List.of(EN_US.Rock(EN_US.Shale(EN_US.End()))));
		//</editor-fold>
		//<editor-fold desc="Purpur">
		Register("purpur_wall", PURPUR_WALL, List.of(EN_US.Wall(EN_US.Purpur())));
		Register("cracked_purpur_block", CRACKED_PURPUR_BLOCK, List.of(EN_US.Block(EN_US.Purpur(EN_US.Cracked()))));
		CrackedBlocks.Register(Blocks.PURPUR_BLOCK, CRACKED_PURPUR_BLOCK);
		Register("chiseled_purpur", CHISELED_PURPUR, List.of(EN_US.Purpur(EN_US.Chiseled())));
		Register("chiseled_purpur_stairs", CHISELED_PURPUR_STAIRS, List.of(EN_US.Stairs(EN_US.Purpur(EN_US.Chiseled()))));
		Register("chiseled_purpur_slab", CHISELED_PURPUR_SLAB, List.of(EN_US.Slab(EN_US.Purpur(EN_US.Chiseled()))));
		Register("chiseled_purpur_wall", CHISELED_PURPUR_WALL, List.of(EN_US.Wall(EN_US.Purpur(EN_US.Chiseled()))));
		Register("purpur_mosaic", PURPUR_MOSAIC, List.of(EN_US.Mosaic(EN_US.Purpur())));
		Register("purpur_mosaic_stairs", PURPUR_MOSAIC_STAIRS, List.of(EN_US.Stairs(EN_US.Mosaic(EN_US.Purpur()))));
		Register("purpur_mosaic_slab", PURPUR_MOSAIC_SLAB, List.of(EN_US.Slab(EN_US.Mosaic(EN_US.Purpur()))));
		Register("purpur_mosaic_wall", PURPUR_MOSAIC_WALL, List.of(EN_US.Wall(EN_US.Mosaic(EN_US.Purpur()))));
		Register("smooth_purpur", SMOOTH_PURPUR, List.of(EN_US.Purpur(EN_US.Smooth())));
		Register("smooth_purpur_stairs", SMOOTH_PURPUR_STAIRS, List.of(EN_US.Stairs(EN_US.Purpur(EN_US.Smooth()))));
		Register("smooth_purpur_slab", SMOOTH_PURPUR_SLAB, List.of(EN_US.Slab(EN_US.Purpur(EN_US.Smooth()))));
		Register("smooth_purpur_wall", SMOOTH_PURPUR_WALL, List.of(EN_US.Wall(EN_US.Purpur(EN_US.Smooth()))));
		Register("purpur_bricks", PURPUR_BRICKS, List.of(EN_US.Bricks(EN_US.Purpur())));
		Register("purpur_brick_stairs", PURPUR_BRICK_STAIRS, List.of(EN_US.Stairs(EN_US.Brick(EN_US.Purpur()))));
		Register("purpur_brick_slab", PURPUR_BRICK_SLAB, List.of(EN_US.Slab(EN_US.Brick(EN_US.Purpur()))));
		Register("purpur_brick_wall", PURPUR_BRICK_WALL, List.of(EN_US.Wall(EN_US.Brick(EN_US.Purpur()))));
		Register("chiseled_purpur_bricks", CHISELED_PURPUR_BRICKS, List.of(EN_US.Bricks(EN_US.Purpur(EN_US.Chiseled()))));
		Register("smooth_chiseled_purpur_bricks", SMOOTH_CHISELED_PURPUR_BRICKS, List.of(EN_US.Bricks(EN_US.Purpur(EN_US.Chiseled(EN_US.Smooth())))));
		Register("purpur_tiles", PURPUR_TILES, List.of(EN_US.Tiles(EN_US.Purpur())));
		Register("purpur_tile_stairs", PURPUR_TILE_STAIRS, List.of(EN_US.Stairs(EN_US.Tile(EN_US.Purpur()))));
		Register("purpur_tile_slab", PURPUR_TILE_SLAB, List.of(EN_US.Slab(EN_US.Tile(EN_US.Purpur()))));
		Register("purpur_tile_wall", PURPUR_TILE_WALL, List.of(EN_US.Wall(EN_US.Tile(EN_US.Purpur()))));
		//</editor-fold>
		//<editor-fold desc="Shulker">
		Register("shulker_helmet", SHULKER_HELMET, List.of(EN_US.Helmet(EN_US.Shulker())));
		Register("shulker_chestplate", SHULKER_CHESTPLATE, List.of(EN_US.Chestplate(EN_US.Shulker())));
		Register("shulker_leggings", SHULKER_LEGGINGS, List.of(EN_US.Leggings(EN_US.Shulker())));
		Register("shulker_boots", SHULKER_BOOTS, List.of(EN_US.Boots(EN_US.Shulker())));
		//</editor-fold>
		//<editor-fold desc="Purple Eye of Ender">
		Register("purple_ender_eye", PURPLE_ENDER_EYE, List.of(EN_US.Eye(EN_US.Ender(EN_US.Purple()))));
		Register("purple_eye_of_ender", PURPLE_EYE_OF_ENDER_ENTITY, List.of(EN_US.Ender(EN_US.of(EN_US.Eye(EN_US.Purple())))));
		Register("purple_eye_end_portal_frame", PURPLE_EYE_END_PORTAL_FRAME, List.of(EN_US.Frame(EN_US.Portal(EN_US.End(EN_US.Eye(EN_US.Purple()))))));
		//</editor-fold>
		//<editor-fold desc="Obsidian">
		Register("obsidian_stairs", OBSIDIAN_STAIRS, List.of(EN_US.Stairs(EN_US.Obsidian())));
		Register("obsidian_slab", OBSIDIAN_SLAB, List.of(EN_US.Slab(EN_US.Obsidian())));
		Register("obsidian_wall", OBSIDIAN_WALL, List.of(EN_US.Wall(EN_US.Obsidian())));
		Register("polished_obsidian", POLISHED_OBSIDIAN, List.of(EN_US.Obsidian(EN_US.Polished())));
		Register("polished_obsidian_stairs", POLISHED_OBSIDIAN_STAIRS, List.of(EN_US.Stairs(EN_US.Obsidian(EN_US.Polished()))));
		Register("polished_obsidian_slab", POLISHED_OBSIDIAN_SLAB, List.of(EN_US.Slab(EN_US.Obsidian(EN_US.Polished()))));
		Register("polished_obsidian_wall", POLISHED_OBSIDIAN_WALL, List.of(EN_US.Wall(EN_US.Obsidian(EN_US.Polished()))));
		Register("polished_obsidian_bricks", POLISHED_OBSIDIAN_BRICKS, List.of(EN_US.Bricks(EN_US.Obsidian(EN_US.Polished()))));
		Register("polished_obsidian_brick_stairs", POLISHED_OBSIDIAN_BRICK_STAIRS, List.of(EN_US.Stairs(EN_US.Brick(EN_US.Obsidian(EN_US.Polished())))));
		Register("polished_obsidian_brick_slab", POLISHED_OBSIDIAN_BRICK_SLAB, List.of(EN_US.Slab(EN_US.Brick(EN_US.Obsidian(EN_US.Polished())))));
		Register("polished_obsidian_brick_wall", POLISHED_OBSIDIAN_BRICK_WALL, List.of(EN_US.Wall(EN_US.Brick(EN_US.Obsidian(EN_US.Polished())))));
		Register("cracked_polished_obsidian_bricks", CRACKED_POLISHED_OBSIDIAN_BRICKS, List.of(EN_US.Bricks(EN_US.Obsidian(EN_US.Polished(EN_US.Cracked())))));
		CrackedBlocks.Register(POLISHED_OBSIDIAN_BRICKS, CRACKED_POLISHED_OBSIDIAN_BRICKS);
		//Crying
		Register("crying_obsidian_stairs", CRYING_OBSIDIAN_STAIRS, List.of(EN_US.Stairs(EN_US.Obsidian(EN_US.Crying()))));
		Register("crying_obsidian_slab", CRYING_OBSIDIAN_SLAB, List.of(EN_US.Slab(EN_US.Obsidian(EN_US.Crying()))));
		Register("crying_obsidian_wall", CRYING_OBSIDIAN_WALL, List.of(EN_US.Wall(EN_US.Obsidian(EN_US.Crying()))));
		Register("polished_crying_obsidian", POLISHED_CRYING_OBSIDIAN, List.of(EN_US.Obsidian(EN_US.Crying(EN_US.Polished()))));
		Register("polished_crying_obsidian_stairs", POLISHED_CRYING_OBSIDIAN_STAIRS, List.of(EN_US.Stairs(EN_US.Obsidian(EN_US.Crying(EN_US.Polished())))));
		Register("polished_crying_obsidian_slab", POLISHED_CRYING_OBSIDIAN_SLAB, List.of(EN_US.Slab(EN_US.Obsidian(EN_US.Crying(EN_US.Polished())))));
		Register("polished_crying_obsidian_wall", POLISHED_CRYING_OBSIDIAN_WALL, List.of(EN_US.Wall(EN_US.Obsidian(EN_US.Crying(EN_US.Polished())))));
		Register("polished_crying_obsidian_bricks", POLISHED_CRYING_OBSIDIAN_BRICKS, List.of(EN_US.Bricks(EN_US.Obsidian(EN_US.Crying(EN_US.Polished())))));
		Register("polished_crying_obsidian_brick_stairs", POLISHED_CRYING_OBSIDIAN_BRICK_STAIRS, List.of(EN_US.Stairs(EN_US.Brick(EN_US.Obsidian(EN_US.Crying(EN_US.Polished()))))));
		Register("polished_crying_obsidian_brick_slab", POLISHED_CRYING_OBSIDIAN_BRICK_SLAB, List.of(EN_US.Slab(EN_US.Brick(EN_US.Obsidian(EN_US.Crying(EN_US.Polished()))))));
		Register("polished_crying_obsidian_brick_wall", POLISHED_CRYING_OBSIDIAN_BRICK_WALL, List.of(EN_US.Wall(EN_US.Brick(EN_US.Obsidian(EN_US.Crying(EN_US.Polished()))))));
		Register("cracked_polished_crying_obsidian_bricks", CRACKED_POLISHED_CRYING_OBSIDIAN_BRICKS, List.of(EN_US.Bricks(EN_US.Obsidian(EN_US.Crying(EN_US.Polished(EN_US.Cracked()))))));
		CrackedBlocks.Register(POLISHED_CRYING_OBSIDIAN_BRICKS, CRACKED_POLISHED_CRYING_OBSIDIAN_BRICKS);
		//Bleeding
		Register("bleeding_obsidian", BLEEDING_OBSIDIAN, List.of(EN_US.Obsidian(EN_US.Bleeding())));
		Register("bleeding_obsidian_stairs", BLEEDING_OBSIDIAN_STAIRS, List.of(EN_US.Stairs(EN_US.Obsidian(EN_US.Bleeding()))));
		Register("bleeding_obsidian_slab", BLEEDING_OBSIDIAN_SLAB, List.of(EN_US.Slab(EN_US.Obsidian(EN_US.Bleeding()))));
		Register("bleeding_obsidian_wall", BLEEDING_OBSIDIAN_WALL, List.of(EN_US.Wall(EN_US.Obsidian(EN_US.Bleeding()))));
		Register("polished_bleeding_obsidian", POLISHED_BLEEDING_OBSIDIAN, List.of(EN_US.Obsidian(EN_US.Bleeding(EN_US.Polished()))));
		Register("polished_bleeding_obsidian_stairs", POLISHED_BLEEDING_OBSIDIAN_STAIRS, List.of(EN_US.Stairs(EN_US.Obsidian(EN_US.Bleeding(EN_US.Polished())))));
		Register("polished_bleeding_obsidian_slab", POLISHED_BLEEDING_OBSIDIAN_SLAB, List.of(EN_US.Slab(EN_US.Obsidian(EN_US.Bleeding(EN_US.Polished())))));
		Register("polished_bleeding_obsidian_wall", POLISHED_BLEEDING_OBSIDIAN_WALL, List.of(EN_US.Wall(EN_US.Obsidian(EN_US.Bleeding(EN_US.Polished())))));
		Register("polished_bleeding_obsidian_bricks", POLISHED_BLEEDING_OBSIDIAN_BRICKS, List.of(EN_US.Bricks(EN_US.Obsidian(EN_US.Bleeding(EN_US.Polished())))));
		Register("polished_bleeding_obsidian_brick_stairs", POLISHED_BLEEDING_OBSIDIAN_BRICK_STAIRS, List.of(EN_US.Stairs(EN_US.Brick(EN_US.Obsidian(EN_US.Bleeding(EN_US.Polished()))))));
		Register("polished_bleeding_obsidian_brick_slab", POLISHED_BLEEDING_OBSIDIAN_BRICK_SLAB, List.of(EN_US.Slab(EN_US.Brick(EN_US.Obsidian(EN_US.Bleeding(EN_US.Polished()))))));
		Register("polished_bleeding_obsidian_brick_wall", POLISHED_BLEEDING_OBSIDIAN_BRICK_WALL, List.of(EN_US.Wall(EN_US.Brick(EN_US.Obsidian(EN_US.Bleeding(EN_US.Polished()))))));
		Register("cracked_polished_bleeding_obsidian_bricks", CRACKED_POLISHED_BLEEDING_OBSIDIAN_BRICKS, List.of(EN_US.Bricks(EN_US.Obsidian(EN_US.Bleeding(EN_US.Polished(EN_US.Cracked()))))));
		CrackedBlocks.Register(POLISHED_BLEEDING_OBSIDIAN_BRICKS, CRACKED_POLISHED_BLEEDING_OBSIDIAN_BRICKS);
		//Tools & Armor
		Register("obsidian_shovel", OBSIDIAN_SHOVEL, List.of(EN_US.Shovel(EN_US.Obsidian())));
		Register("obsidian_pickaxe", OBSIDIAN_PICKAXE, List.of(EN_US.Pickaxe(EN_US.Obsidian())));
		Register("obsidian_axe", OBSIDIAN_AXE, List.of(EN_US.Axe(EN_US.Obsidian())));
		Register("obsidian_hoe", OBSIDIAN_HOE, List.of(EN_US.Hoe(EN_US.Obsidian())));
		Register("obsidian_sword", OBSIDIAN_SWORD, List.of(EN_US.Sword(EN_US.Obsidian())));
		Register("obsidian_knife", OBSIDIAN_KNIFE, List.of(EN_US.Knife(EN_US.Obsidian())));
		Register("obsidian_hammer", OBSIDIAN_HAMMER, List.of(EN_US.Hammer(EN_US.Obsidian())));
		Register("obsidian_helmet", OBSIDIAN_HELMET, List.of(EN_US.Helmet(EN_US.Obsidian())));
		Register("obsidian_chestplate", OBSIDIAN_CHESTPLATE, List.of(EN_US.Chestplate(EN_US.Obsidian())));
		Register("obsidian_leggings", OBSIDIAN_LEGGINGS, List.of(EN_US.Leggings(EN_US.Obsidian())));
		Register("obsidian_boots", OBSIDIAN_BOOTS, List.of(EN_US.Boots(EN_US.Obsidian())));
		Register("obsidian_horse_armor", OBSIDIAN_HORSE_ARMOR, List.of(EN_US.Armor(EN_US.Horse(EN_US.Obsidian()))));
		//</editor-fold>
		//<editor-fold desc="Amethyst">
		Register("amethyst_stairs", AMETHYST_STAIRS, List.of(EN_US.Stairs(EN_US.Amethyst())));
		Register("amethyst_slab", AMETHYST_SLAB, List.of(EN_US.Slab(EN_US.Amethyst())));
		Register("amethyst_wall", AMETHYST_WALL, List.of(EN_US.Wall(EN_US.Amethyst())));
		Register("amethyst_bricks", AMETHYST_BRICKS, List.of(EN_US.Bricks(EN_US.Amethyst())));
		Register("amethyst_brick_stairs", AMETHYST_BRICK_STAIRS, List.of(EN_US.Stairs(EN_US.Brick(EN_US.Amethyst()))));
		Register("amethyst_brick_slab", AMETHYST_BRICK_SLAB, List.of(EN_US.Slab(EN_US.Brick(EN_US.Amethyst()))));
		Register("amethyst_brick_wall", AMETHYST_BRICK_WALL, List.of(EN_US.Wall(EN_US.Brick(EN_US.Amethyst()))));
		Register("amethyst_crystal_block", AMETHYST_CRYSTAL_BLOCK, List.of(EN_US.Block(EN_US.Crystal(EN_US.Amethyst()))));
		Register("amethyst_crystal_stairs", AMETHYST_CRYSTAL_STAIRS, List.of(EN_US.Stairs(EN_US.Crystal(EN_US.Amethyst()))));
		Register("amethyst_crystal_slab", AMETHYST_CRYSTAL_SLAB, List.of(EN_US.Slab(EN_US.Crystal(EN_US.Amethyst()))));
		Register("amethyst_crystal_wall", AMETHYST_CRYSTAL_WALL, List.of(EN_US.Wall(EN_US.Crystal(EN_US.Amethyst()))));
		Register("amethyst_shovel", AMETHYST_SHOVEL, List.of(EN_US.Shovel(EN_US.Amethyst())));
		Register("amethyst_pickaxe", AMETHYST_PICKAXE, List.of(EN_US.Pickaxe(EN_US.Amethyst())));
		Register("amethyst_axe", AMETHYST_AXE, List.of(EN_US.Axe(EN_US.Amethyst())));
		Register("amethyst_hoe", AMETHYST_HOE, List.of(EN_US.Hoe(EN_US.Amethyst())));
		Register("amethyst_sword", AMETHYST_SWORD, List.of(EN_US.Sword(EN_US.Amethyst())));
		Register("amethyst_knife", AMETHYST_KNIFE, List.of(EN_US.Knife(EN_US.Amethyst())));
		Register("amethyst_hammer", AMETHYST_HAMMER, List.of(EN_US.Hammer(EN_US.Amethyst())));
		Register("amethyst_helmet", AMETHYST_HELMET, List.of(EN_US.Helmet(EN_US.Amethyst())));
		Register("amethyst_chestplate", AMETHYST_CHESTPLATE, List.of(EN_US.Chestplate(EN_US.Amethyst())));
		Register("amethyst_leggings", AMETHYST_LEGGINGS, List.of(EN_US.Leggings(EN_US.Amethyst())));
		Register("amethyst_boots", AMETHYST_BOOTS, List.of(EN_US.Boots(EN_US.Amethyst())));
		Register("amethyst_horse_armor", AMETHYST_HORSE_ARMOR, List.of(EN_US.Armor(EN_US.Horse(EN_US.Amethyst()))));
		//</editor-fold>
		//<editor-fold desc="Tinted Glass">
		Register("tinted_goggles", TINTED_GOGGLES, List.of(EN_US.Goggles(EN_US.Tinted())));
		Register("tinted_glass_pane", TINTED_GLASS_PANE, List.of(EN_US.Pane(EN_US.Glass(EN_US.Tinted()))));
		Register("tinted_glass_slab", TINTED_GLASS_SLAB, List.of(EN_US.Slab(EN_US.Glass(EN_US.Tinted()))));
		Register("tinted_glass_trapdoor", TINTED_GLASS_TRAPDOOR, List.of(EN_US.Trapdoor(EN_US.Glass(EN_US.Tinted()))));
		//</editor-fold>
		//<editor-fold desc="Ruby Glass">
		Register("ruby_goggles", RUBY_GOGGLES, List.of(EN_US.Goggles(EN_US.Ruby())));
		Register("ruby_glass", RUBY_GLASS, List.of(EN_US.Glass(EN_US.Ruby())));
		Register("ruby_glass_pane", RUBY_GLASS_PANE, List.of(EN_US.GlassPane(EN_US.Ruby())));
		Register("ruby_glass_slab", RUBY_GLASS_SLAB, List.of(EN_US.GlassSlab(EN_US.Ruby())));
		Register("ruby_glass_trapdoor", RUBY_GLASS_TRAPDOOR, List.of(EN_US.GlassTrapdoor(EN_US.Ruby())));
		//</editor-fold>
		//<editor-fold desc="Sapphire Glass">
		Register("sapphire_goggles", SAPPHIRE_GOGGLES, List.of(EN_US.Goggles(EN_US.Sapphire())));
		Register("sapphire_glass", SAPPHIRE_GLASS, List.of(EN_US.Glass(EN_US.Sapphire())));
		Register("sapphire_glass_pane", SAPPHIRE_GLASS_PANE, List.of(EN_US.Pane(EN_US.Glass(EN_US.Sapphire()))));
		Register("sapphire_glass_slab", SAPPHIRE_GLASS_SLAB, List.of(EN_US.Slab(EN_US.Glass(EN_US.Sapphire()))));
		Register("sapphire_glass_trapdoor", SAPPHIRE_GLASS_TRAPDOOR, List.of(EN_US.Trapdoor(EN_US.Glass(EN_US.Sapphire()))));
		//</editor-fold>
		//<editor-fold desc="Glass">
		Register("glass_slab", GLASS_SLAB, List.of(EN_US.Slab(EN_US.Glass())));
		Register("glass_trapdoor", GLASS_TRAPDOOR, List.of(EN_US.Trapdoor(EN_US.Glass())));
		for (DyeColor color : DyeColor.values()) {
			Register(color.getName() + "_stained_glass_slab", STAINED_GLASS_SLABS.get(color), List.of(EN_US.GlassSlab(EN_US.Color(color))));
		}
		for (DyeColor color : DyeColor.values()) {
			Register(color.getName() + "_stained_glass_trapdoor", STAINED_GLASS_TRAPDOORS.get(color), List.of(EN_US.GlassTrapdoor(EN_US.Color(color))));
		}
		//</editor-fold>
		//<editor-fold desc="Blaze">
		Register("blaze_powder_block", BLAZE_POWDER_BLOCK, List.of(EN_US.Block(EN_US.Powder(EN_US.Blaze()))));
		Register("blaze_torch", "blaze_wall_torch", BLAZE_TORCH, List.of(EN_US._Torch(EN_US.Blaze())));
		Register("blaze_soul_torch", "blaze_soul_wall_torch", BLAZE_SOUL_TORCH, List.of(EN_US.SoulTorch(EN_US.Blaze())));
		Register("blaze_ender_torch", "blaze_ender_wall_torch", BLAZE_ENDER_TORCH, List.of(EN_US.EnderTorch(EN_US.Blaze())));
		Register("underwater_blaze_torch", "underwater_blaze_wall_torch", UNDERWATER_BLAZE_TORCH, List.of(EN_US.Underwater_Torch(EN_US.Blaze())));
		//</editor-fold>
		//<editor-fold desc="Emerald">
		Register("emerald_stairs", EMERALD_STAIRS, List.of(EN_US.Stairs(EN_US.Emerald())));
		Register("emerald_slab", EMERALD_SLAB, List.of(EN_US.Slab(EN_US.Emerald())));
		Register("emerald_wall", EMERALD_WALL, List.of(EN_US.Wall(EN_US.Emerald())));
		Register("emerald_bricks", EMERALD_BRICKS, List.of(EN_US.Bricks(EN_US.Emerald())));
		Register("emerald_brick_stairs", EMERALD_BRICK_STAIRS, List.of(EN_US.Stairs(EN_US.Brick(EN_US.Emerald()))));
		Register("emerald_brick_slab", EMERALD_BRICK_SLAB, List.of(EN_US.Slab(EN_US.Brick(EN_US.Emerald()))));
		Register("emerald_brick_wall", EMERALD_BRICK_WALL, List.of(EN_US.Wall(EN_US.Brick(EN_US.Emerald()))));
		Register("cut_emerald", CUT_EMERALD, List.of(EN_US.Emerald(EN_US.Cut())));
		Register("cut_emerald_stairs", CUT_EMERALD_STAIRS, List.of(EN_US.Stairs(EN_US.Emerald(EN_US.Cut()))));
		Register("cut_emerald_slab", CUT_EMERALD_SLAB, List.of(EN_US.Slab(EN_US.Emerald(EN_US.Cut()))));
		Register("cut_emerald_wall", CUT_EMERALD_WALL, List.of(EN_US.Wall(EN_US.Emerald(EN_US.Cut()))));
		Register("emerald_shovel", EMERALD_SHOVEL, List.of(EN_US.Shovel(EN_US.Emerald())));
		Register("emerald_pickaxe", EMERALD_PICKAXE, List.of(EN_US.Pickaxe(EN_US.Emerald())));
		Register("emerald_axe", EMERALD_AXE, List.of(EN_US.Axe(EN_US.Emerald())));
		Register("emerald_hoe", EMERALD_HOE, List.of(EN_US.Hoe(EN_US.Emerald())));
		Register("emerald_sword", EMERALD_SWORD, List.of(EN_US.Sword(EN_US.Emerald())));
		Register("emerald_knife", EMERALD_KNIFE, List.of(EN_US.Knife(EN_US.Emerald())));
		Register("emerald_hammer", EMERALD_HAMMER, List.of(EN_US.Hammer(EN_US.Emerald())));
		Register("emerald_helmet", EMERALD_HELMET, List.of(EN_US.Helmet(EN_US.Emerald())));
		Register("emerald_chestplate", EMERALD_CHESTPLATE, List.of(EN_US.Chestplate(EN_US.Emerald())));
		Register("emerald_leggings", EMERALD_LEGGINGS, List.of(EN_US.Leggings(EN_US.Emerald())));
		Register("emerald_boots", EMERALD_BOOTS, List.of(EN_US.Boots(EN_US.Emerald())));
		Register("emerald_horse_armor", EMERALD_HORSE_ARMOR, List.of(EN_US.Armor(EN_US.Horse(EN_US.Emerald()))));
		//</editor-fold>
		//<editor-fold desc="Ruby">
		Register("ruby_ore", RUBY_ORE, List.of(EN_US.RubyOre()));
		Register("deepslate_ruby_ore", DEEPSLATE_RUBY_ORE, List.of(EN_US.Ore(EN_US.Ruby(EN_US.Deepslate()))));
		Register("ruby_block", RUBY_BLOCK, List.of(EN_US.Block(EN_US.Ruby())));
		Register("ruby_stairs", RUBY_STAIRS, List.of(EN_US.Stairs(EN_US.Ruby())));
		Register("ruby_slab", RUBY_SLAB, List.of(EN_US.Slab(EN_US.Ruby())));
		Register("ruby_wall", RUBY_WALL, List.of(EN_US.Wall(EN_US.Ruby())));
		Register("ruby_bricks", RUBY_BRICKS, List.of(EN_US.Bricks(EN_US.Ruby())));
		Register("ruby_brick_stairs", RUBY_BRICK_STAIRS, List.of(EN_US.Stairs(EN_US.Brick(EN_US.Ruby()))));
		Register("ruby_brick_slab", RUBY_BRICK_SLAB, List.of(EN_US.Slab(EN_US.Brick(EN_US.Ruby()))));
		Register("ruby_brick_wall", RUBY_BRICK_WALL, List.of(EN_US.Wall(EN_US.Brick(EN_US.Ruby()))));
		Register("ruby", RUBY, List.of(EN_US.Ruby()));
		//</editor-fold>
		//<editor-fold desc="Sapphire">
		Register("sapphire_ore", SAPPHIRE_ORE, List.of(EN_US.Ore(EN_US.Sapphire())));
		Register("deepslate_sapphire_ore", DEEPSLATE_SAPPHIRE_ORE, List.of(EN_US.Ore(EN_US.Sapphire(EN_US.Deepslate()))));
		Register("sapphire_block", SAPPHIRE_BLOCK, List.of(EN_US.Block(EN_US.Sapphire())));
		Register("sapphire_stairs", SAPPHIRE_STAIRS, List.of(EN_US.Stairs(EN_US.Sapphire())));
		Register("sapphire_slab", SAPPHIRE_SLAB, List.of(EN_US.Slab(EN_US.Sapphire())));
		Register("sapphire_wall", SAPPHIRE_WALL, List.of(EN_US.Wall(EN_US.Sapphire())));
		Register("sapphire_bricks", SAPPHIRE_BRICKS, List.of(EN_US.Bricks(EN_US.Sapphire())));
		Register("sapphire_brick_stairs", SAPPHIRE_BRICK_STAIRS, List.of(EN_US.Stairs(EN_US.Brick(EN_US.Sapphire()))));
		Register("sapphire_brick_slab", SAPPHIRE_BRICK_SLAB, List.of(EN_US.Slab(EN_US.Brick(EN_US.Sapphire()))));
		Register("sapphire_brick_wall", SAPPHIRE_BRICK_WALL, List.of(EN_US.Wall(EN_US.Brick(EN_US.Sapphire()))));
		Register("sapphire", SAPPHIRE, List.of(EN_US.Sapphire()));
		//</editor-fold>
		//<editor-fold desc="Diamond">
		Register("diamond_stairs", DIAMOND_STAIRS, List.of(EN_US.Stairs(EN_US.Diamond())));
		Register("diamond_slab", DIAMOND_SLAB, List.of(EN_US.Slab(EN_US.Diamond())));
		Register("diamond_wall", DIAMOND_WALL, List.of(EN_US.Wall(EN_US.Diamond())));
		Register("diamond_bricks", DIAMOND_BRICKS, List.of(EN_US.Bricks(EN_US.Diamond())));
		Register("diamond_brick_stairs", DIAMOND_BRICK_STAIRS, List.of(EN_US.Stairs(EN_US.Brick(EN_US.Diamond()))));
		Register("diamond_brick_slab", DIAMOND_BRICK_SLAB, List.of(EN_US.Slab(EN_US.Brick(EN_US.Diamond()))));
		Register("diamond_brick_wall", DIAMOND_BRICK_WALL, List.of(EN_US.Wall(EN_US.Brick(EN_US.Diamond()))));
		Register("diamond_hammer", DIAMOND_HAMMER, List.of(EN_US.Hammer(EN_US.Diamond())));
		//</editor-fold>
		//<editor-fold desc="Quartz">
		Register("quartz_ore", QUARTZ_ORE, List.of(EN_US.Ore(EN_US.Quartz())));
		Register("deepslate_quartz_ore", DEEPSLATE_QUARTZ_ORE, List.of(EN_US.Ore(EN_US.Quartz(EN_US.Deepslate()))));
		Register("quartz_crystal_block", QUARTZ_CRYSTAL_BLOCK, List.of(EN_US.Block(EN_US.Crystal(EN_US.Quartz()))));
		Register("quartz_crystal_stairs", QUARTZ_CRYSTAL_STAIRS, List.of(EN_US.Stairs(EN_US.Crystal(EN_US.Quartz()))));
		Register("quartz_crystal_slab", QUARTZ_CRYSTAL_SLAB, List.of(EN_US.Slab(EN_US.Crystal(EN_US.Quartz()))));
		Register("quartz_crystal_wall", QUARTZ_CRYSTAL_WALL, List.of(EN_US.Wall(EN_US.Crystal(EN_US.Quartz()))));
		Register("smooth_quartz_wall", SMOOTH_QUARTZ_WALL, List.of(EN_US.Wall(EN_US.Quartz(EN_US.Smooth()))));
		Register("quartz_wall", QUARTZ_WALL, List.of(EN_US.Wall(EN_US.Quartz())));
		Register("quartz_brick_stairs", QUARTZ_BRICK_STAIRS, List.of(EN_US.Stairs(EN_US.Brick(EN_US.Quartz()))));
		Register("quartz_brick_slab", QUARTZ_BRICK_SLAB, List.of(EN_US.Slab(EN_US.Brick(EN_US.Quartz()))));
		Register("quartz_brick_wall", QUARTZ_BRICK_WALL, List.of(EN_US.Wall(EN_US.Brick(EN_US.Quartz()))));
		Register("chiseled_quartz_bricks", CHISELED_QUARTZ_BRICKS, List.of(EN_US.Bricks(EN_US.Quartz(EN_US.Chiseled()))));
		Register("smooth_chiseled_quartz_bricks", SMOOTH_CHISELED_QUARTZ_BRICKS, List.of(EN_US.Bricks(EN_US.Quartz(EN_US.Chiseled(EN_US.Smooth())))));
		Register("quartz_shovel", QUARTZ_SHOVEL, List.of(EN_US.Shovel(EN_US.Quartz())));
		Register("quartz_pickaxe", QUARTZ_PICKAXE, List.of(EN_US.Pickaxe(EN_US.Quartz())));
		Register("quartz_axe", QUARTZ_AXE, List.of(EN_US.Axe(EN_US.Quartz())));
		Register("quartz_hoe", QUARTZ_HOE, List.of(EN_US.Hoe(EN_US.Quartz())));
		Register("quartz_sword", QUARTZ_SWORD, List.of(EN_US.Sword(EN_US.Quartz())));
		Register("quartz_knife", QUARTZ_KNIFE, List.of(EN_US.Knife(EN_US.Quartz())));
		Register("quartz_hammer", QUARTZ_HAMMER, List.of(EN_US.Hammer(EN_US.Quartz())));
		Register("quartz_helmet", QUARTZ_HELMET, List.of(EN_US.Helmet(EN_US.Quartz())));
		Register("quartz_chestplate", QUARTZ_CHESTPLATE, List.of(EN_US.Chestplate(EN_US.Quartz())));
		Register("quartz_leggings", QUARTZ_LEGGINGS, List.of(EN_US.Leggings(EN_US.Quartz())));
		Register("quartz_boots", QUARTZ_BOOTS, List.of(EN_US.Boots(EN_US.Quartz())));
		Register("quartz_horse_armor", QUARTZ_HORSE_ARMOR, List.of(EN_US.Armor(EN_US.Horse(EN_US.Quartz()))));
		//</editor-fold>
		RegisterIron();
		RegisterGold();
		ModCopperRegistry.RegisterCopper();
		//<editor-fold desc="Raw Metal">
		Register("raw_copper_slab", RAW_COPPER_SLAB, List.of(EN_US.Slab(EN_US.Copper(EN_US.Raw()))));
		Register("raw_gold_slab", RAW_GOLD_SLAB, List.of(EN_US.Slab(EN_US.Gold(EN_US.Raw()))));
		Register("raw_iron_slab", RAW_IRON_SLAB, List.of(EN_US.Slab(EN_US.Iron(EN_US.Raw()))));
		//</editor-fold>
		//<editor-fold desc="Netherite">
		Register("netherite_nugget", NETHERITE_NUGGET, List.of(EN_US.Nugget(EN_US.Netherite())));
		Register("netherite_rod", NETHERITE_ROD, List.of(EN_US.Rod(EN_US.Netherite())));
		Register("netherite_torch", "netherite_wall_torch", NETHERITE_TORCH, List.of(EN_US._Torch(EN_US.Netherite())));
		Register("netherite_soul_torch", "netherite_soul_wall_torch", NETHERITE_SOUL_TORCH, List.of(EN_US.SoulTorch(EN_US.Netherite())));
		Register("netherite_ender_torch", "netherite_ender_wall_torch", NETHERITE_ENDER_TORCH, List.of(EN_US.EnderTorch(EN_US.Netherite())));
		Register("underwater_netherite_torch", "underwater_netherite_wall_torch", UNDERWATER_NETHERITE_TORCH, List.of(EN_US.Underwater_Torch(EN_US.Netherite())));
		Register("netherite_lantern", NETHERITE_LANTERN, List.of(EN_US.Lantern(EN_US.Netherite())));
		Register("netherite_soul_lantern", NETHERITE_SOUL_LANTERN, List.of(EN_US.SoulLantern(EN_US.Netherite())));
		Register("netherite_ender_lantern", NETHERITE_ENDER_LANTERN, List.of(EN_US.EnderLantern(EN_US.Netherite())));
		Register("netherite_button", NETHERITE_BUTTON, List.of(EN_US.Button(EN_US.Netherite())));
		Register("netherite_chain", NETHERITE_CHAIN, List.of(EN_US.Chain(EN_US.Netherite())));
		Register("heavy_netherite_chain", HEAVY_NETHERITE_CHAIN, List.of(EN_US.Chain(EN_US.Netherite(EN_US.Heavy()))));
		Register("netherite_bars", NETHERITE_BARS, List.of(EN_US.Bars(EN_US.Netherite())));
		Register("netherite_stairs", NETHERITE_STAIRS, List.of(EN_US.Stairs(EN_US.Netherite())));
		Register("netherite_slab", NETHERITE_SLAB, List.of(EN_US.Slab(EN_US.Netherite())));
		Register("netherite_wall", NETHERITE_WALL, List.of(EN_US.Wall(EN_US.Netherite())));
		Register("netherite_bricks", NETHERITE_BRICKS, List.of(EN_US.Bricks(EN_US.Netherite())));
		Register("netherite_brick_stairs", NETHERITE_BRICK_STAIRS, List.of(EN_US.Stairs(EN_US.Brick(EN_US.Netherite()))));
		Register("netherite_brick_slab", NETHERITE_BRICK_SLAB, List.of(EN_US.Slab(EN_US.Brick(EN_US.Netherite()))));
		Register("netherite_brick_wall", NETHERITE_BRICK_WALL, List.of(EN_US.Wall(EN_US.Brick(EN_US.Netherite()))));
		Register("cut_netherite", CUT_NETHERITE, List.of(EN_US.Netherite(EN_US.Cut())));
		Register("cut_netherite_pillar", CUT_NETHERITE_PILLAR, List.of(EN_US.Pillar(EN_US.Netherite(EN_US.Cut()))));
		Register("cut_netherite_stairs", CUT_NETHERITE_STAIRS, List.of(EN_US.Stairs(EN_US.Netherite())));
		Register("cut_netherite_slab", CUT_NETHERITE_SLAB, List.of(EN_US.Slab(EN_US.Netherite(EN_US.Cut()))));
		Register("cut_netherite_wall", CUT_NETHERITE_WALL, List.of(EN_US.Wall(EN_US.Netherite(EN_US.Cut()))));
		Register("netherite_door", NETHERITE_DOOR, List.of(EN_US.Door(EN_US.Netherite())));
		Register("netherite_trapdoor", NETHERITE_TRAPDOOR, List.of(EN_US.Trapdoor(EN_US.Netherite())));
		Register("crushing_weighted_pressure_plate", CRUSHING_WEIGHTED_PRESSURE_PLATE, List.of(EN_US.Plate(EN_US.Pressure(EN_US.Weighted(EN_US.Crushing())))));
		Register("netherite_hammer", NETHERITE_HAMMER, List.of(EN_US.Hammer(EN_US.Netherite())));
		Register("netherite_shears", NETHERITE_SHEARS, List.of(EN_US.Shears(EN_US.Netherite())));
		Register("netherite_horse_armor", NETHERITE_HORSE_ARMOR, List.of(EN_US.Armor(EN_US.Horse(EN_US.Netherite()))));
		//Bucket
		Register("netherite_bucket", NETHERITE_BUCKET, List.of(EN_US.Bucket(EN_US.Netherite())));
		DispenserBlock.registerBehavior(NETHERITE_BUCKET, new BucketDispenserBehavior(NETHERITE_BUCKET_PROVIDER));
		Register("netherite_water_bucket", NETHERITE_WATER_BUCKET, List.of(EN_US.Bucket(EN_US.Water(EN_US.Netherite()))));
		CauldronBehavior.EMPTY_CAULDRON_BEHAVIOR.put(NETHERITE_WATER_BUCKET, (state, world, pos, player, hand, stack) -> BucketUtil.fillCauldron(world, pos, player, hand, stack,
				Blocks.WATER_CAULDRON.getDefaultState().with(LeveledCauldronBlock.LEVEL, 3),
				SoundEvents.ITEM_BUCKET_EMPTY, NETHERITE_BUCKET));
		CauldronBehavior.WATER_CAULDRON_BEHAVIOR.put(NETHERITE_BUCKET, (state, world, pos, player, hand, stack) -> CauldronBehavior.emptyCauldron(state, world, pos, player, hand, stack, new ItemStack(NETHERITE_WATER_BUCKET),
				(statex) -> statex.get(LeveledCauldronBlock.LEVEL) == 3, SoundEvents.ITEM_BUCKET_FILL));
		Register("netherite_lava_bucket", NETHERITE_LAVA_BUCKET, List.of(EN_US.Bucket(EN_US.Lava(EN_US.Netherite()))));
		Register("netherite_powder_snow_bucket", NETHERITE_POWDER_SNOW_BUCKET, List.of(EN_US.Bucket(EN_US.Snow(EN_US.Powder(EN_US.Netherite())))));
		CauldronBehavior.EMPTY_CAULDRON_BEHAVIOR.put(NETHERITE_POWDER_SNOW_BUCKET, (state, world, pos, player, hand, stack) -> BucketUtil.fillCauldron(world, pos, player, hand, stack,
				Blocks.POWDER_SNOW_CAULDRON.getDefaultState().with(LeveledCauldronBlock.LEVEL, 3),
				SoundEvents.ITEM_BUCKET_EMPTY_POWDER_SNOW, NETHERITE_BUCKET));
		CauldronBehavior.POWDER_SNOW_CAULDRON_BEHAVIOR.put(NETHERITE_BUCKET, (state, world, pos, player, hand, stack) -> CauldronBehavior.emptyCauldron(state, world, pos, player, hand, stack, new ItemStack(NETHERITE_POWDER_SNOW_BUCKET),
				(statex) -> statex.get(LeveledCauldronBlock.LEVEL) == 3, SoundEvents.ITEM_BUCKET_FILL_POWDER_SNOW));
		Register("netherite_blood_bucket", NETHERITE_BLOOD_BUCKET, List.of(EN_US.Bucket(EN_US.Blood(EN_US.Netherite()))));
		CauldronBehavior.EMPTY_CAULDRON_BEHAVIOR.put(NETHERITE_BLOOD_BUCKET, BloodCauldronBlock.FillFromBucket(NETHERITE_BUCKET));
		Register("netherite_mud_bucket", NETHERITE_MUD_BUCKET, List.of(EN_US.Bucket(EN_US.Mud(EN_US.Netherite()))));
		CauldronBehavior.EMPTY_CAULDRON_BEHAVIOR.put(NETHERITE_MUD_BUCKET, MudCauldronBlock.FillFromBucket(NETHERITE_BUCKET));
		Register("netherite_milk_bucket", NETHERITE_MILK_BUCKET, List.of(EN_US.Bucket(EN_US.Milk(EN_US.Netherite()))));
		CauldronBehavior.EMPTY_CAULDRON_BEHAVIOR.put(NETHERITE_MILK_BUCKET, MilkCauldronBlock.FillFromBucket(NETHERITE_BUCKET));
		Register("netherite_chocolate_milk_bucket", NETHERITE_CHOCOLATE_MILK_BUCKET, List.of(EN_US.Bucket(EN_US.Milk(EN_US.Chocolate(EN_US.Netherite())))));
		Register("netherite_coffee_milk_bucket", NETHERITE_COFFEE_MILK_BUCKET, List.of(EN_US.Bucket(EN_US.Milk(EN_US.Coffee(EN_US.Netherite())))));
		Register("netherite_strawberry_milk_bucket", NETHERITE_STRAWBERRY_MILK_BUCKET, List.of(EN_US.Bucket(EN_US.Milk(EN_US.Strawberry(EN_US.Netherite())))));
		Register("netherite_vanilla_milk_bucket", NETHERITE_VANILLA_MILK_BUCKET, List.of(EN_US.Bucket(EN_US.Milk(EN_US.Vanilla(EN_US.Netherite())))));
		//</editor-fold>
		//<editor-fold desc="Bone">
		Register("bone_torch", "bone_wall_torch", BONE_TORCH, List.of(EN_US._Torch(EN_US.Bone())));
		Register("bone_soul_torch", "bone_soul_wall_torch", BONE_SOUL_TORCH, List.of(EN_US.SoulTorch(EN_US.Bone())));
		Register("bone_ender_torch", "bone_ender_wall_torch", BONE_ENDER_TORCH, List.of(EN_US.EnderTorch(EN_US.Bone())));
		Register("underwater_bone_torch", "underwater_bone_wall_torch", UNDERWATER_BONE_TORCH, List.of(EN_US.Underwater_Torch(EN_US.Bone())));
		Register("bone_ladder", BONE_LADDER, List.of(EN_US.Ladder(EN_US.Bone())));
		Register("bone_slab", BONE_SLAB, List.of(EN_US.Slab(EN_US.Bone())));
		Register("bone_row", BONE_ROW, List.of(EN_US.Row(EN_US.Bone())));
		Register("bone_knife", BONE_KNIFE, List.of(EN_US.Knife(EN_US.Bone())));
		Register("bone_hammer", BONE_HAMMER, List.of(EN_US.Hammer(EN_US.Bone())));
		//</editor-fold>
		//<editor-fold desc="Bone Spider">
		Register("bone_spider", BONE_SPIDER_ENTITY, List.of(EN_US.Spider(EN_US.Bone())));
		BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.DARK_FOREST),
				SpawnGroup.MONSTER, BONE_SPIDER_ENTITY, 60, 2, 5);
		FabricDefaultAttributeRegistry.register(BONE_SPIDER_ENTITY, BoneSpiderEntity.createBoneSpiderAttributes());
		SpawnRestrictionAccessor.callRegister(BONE_SPIDER_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HostileEntity::canSpawnInDark);
		Register("bone_shard", BONE_SHARD, List.of(EN_US.Shard(EN_US.Bone())));
		Register("bone_shard_projectile", BONE_SHARD_PROJECTILE_ENTITY, List.of(EN_US.Shard(EN_US.Bone())));
		//</editor-fold>
		//<editor-fold desc="Icy Spider">
		Register("icy_spider", ICY_SPIDER_ENTITY, List.of(EN_US.Spider(EN_US.Icy())));
		BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.ICE_SPIKES, BiomeKeys.SNOWY_TUNDRA, BiomeKeys.SNOWY_MOUNTAINS),
				SpawnGroup.MONSTER, ICY_SPIDER_ENTITY, 20, 2, 5);
		FabricDefaultAttributeRegistry.register(ICY_SPIDER_ENTITY, IcySpiderEntity.createIcySpiderAttributes());
		SpawnRestrictionAccessor.callRegister(ICY_SPIDER_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HostileEntity::canSpawnInDark);
		//</editor-fold>
		//<editor-fold desc="Slime Spider">
		Register("slime_spider", SLIME_SPIDER_ENTITY, List.of(EN_US.Slider()));
		FabricDefaultAttributeRegistry.register(SLIME_SPIDER_ENTITY, SlimeSpiderEntity.createSlimeSpiderAttributes());
		SpawnRestrictionAccessor.callRegister(SLIME_SPIDER_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HostileEntity::canSpawnInDark);
		//</editor-fold>
		//<editor-fold desc="Creepers">
		Register("slime_creeper", SLIME_CREEPER_ENTITY, List.of(EN_US.Creeper(EN_US.Slime())));
		FabricDefaultAttributeRegistry.register(SLIME_CREEPER_ENTITY, SlimeCreeperEntity.createCreeperAttributes());
		SpawnRestrictionAccessor.callRegister(SLIME_CREEPER_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HostileEntity::canSpawnInDark);
		//</editor-fold>
		//<editor-fold desc="Phantoms">
		Register("red_phantom", RED_PHANTOM_ENTITY, List.of(EN_US.Phantom(EN_US.Red())));
		FabricDefaultAttributeRegistry.register(RED_PHANTOM_ENTITY, HostileEntity.createHostileAttributes());
		SpawnRestrictionAccessor.callRegister(RED_PHANTOM_ENTITY, SpawnRestriction.Location.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MobEntity::canMobSpawn);
		//</editor-fold>
		//<editor-fold desc="Skeletons">
		Register("mossy_skeleton", MOSSY_SKELETON_ENTITY, List.of(EN_US.Skeleton(EN_US.Mossy())));
		BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.JUNGLE, BiomeKeys.BAMBOO_JUNGLE, BiomeKeys.JUNGLE_EDGE, BiomeKeys.JUNGLE_HILLS, BiomeKeys.BAMBOO_JUNGLE_HILLS, BiomeKeys.MODIFIED_JUNGLE, BiomeKeys.MODIFIED_JUNGLE_EDGE),
				SpawnGroup.MONSTER, MOSSY_SKELETON_ENTITY, 40, 4, 4);
		FabricDefaultAttributeRegistry.register(MOSSY_SKELETON_ENTITY, MossySkeletonEntity.createAbstractSkeletonAttributes());
		SpawnRestrictionAccessor.callRegister(MOSSY_SKELETON_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HostileEntity::canSpawnInDark);
		Register("slimy_skeleton", SLIMY_SKELETON_ENTITY, List.of(EN_US.Skeleton(EN_US.Slimy())));
		FabricDefaultAttributeRegistry.register(SLIMY_SKELETON_ENTITY, MossySkeletonEntity.createAbstractSkeletonAttributes());
		SpawnRestrictionAccessor.callRegister(SLIMY_SKELETON_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HostileEntity::canSpawnInDark);
		Register("sunken_skeleton", SUNKEN_SKELETON_ENTITY, List.of(EN_US.Skeleton(EN_US.Sunken())));
		BiomeModifications.addSpawn(BiomeSelectors.tag(ModBiomeTags.WARM_OCEANS),
				SpawnGroup.MONSTER, SUNKEN_SKELETON_ENTITY, 10, 2, 4);
		FabricDefaultAttributeRegistry.register(SUNKEN_SKELETON_ENTITY, SunkenSkeletonEntity.createAbstractSkeletonAttributes());
		SpawnRestrictionAccessor.callRegister(SUNKEN_SKELETON_ENTITY, SpawnRestriction.Location.IN_WATER, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SunkenSkeletonEntity::canSpawn);
		//</editor-fold>
		//<editor-fold desc="Zombies">
		Register("slowing_snowball", SLOWING_SNOWBALL_ENTITY, List.of(EN_US.Snowball(EN_US.Slowing())));
		Register("frozen_zombie", FROZEN_ZOMBIE_ENTITY, List.of(EN_US.Zombie(EN_US.Frozen())));
		BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.ICE_SPIKES, BiomeKeys.SNOWY_TUNDRA,
						BiomeKeys.SNOWY_TAIGA, BiomeKeys.SNOWY_TAIGA_MOUNTAINS, BiomeKeys.SNOWY_TAIGA_HILLS, BiomeKeys.SNOWY_MOUNTAINS, BiomeKeys.SNOWY_BEACH),
				SpawnGroup.MONSTER, FROZEN_ZOMBIE_ENTITY, 40, 4, 4);
		FabricDefaultAttributeRegistry.register(FROZEN_ZOMBIE_ENTITY, FrozenZombieEntity.createZombieAttributes());
		SpawnRestrictionAccessor.callRegister(FROZEN_ZOMBIE_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, FrozenZombieEntity::canSpawn);
		Register("jungle_zombie", JUNGLE_ZOMBIE_ENTITY, List.of(EN_US.Zombie(EN_US.Jungle())));
		BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.JUNGLE, BiomeKeys.BAMBOO_JUNGLE, BiomeKeys.JUNGLE_EDGE, BiomeKeys.JUNGLE_HILLS, BiomeKeys.BAMBOO_JUNGLE_HILLS, BiomeKeys.MODIFIED_JUNGLE, BiomeKeys.MODIFIED_JUNGLE_EDGE),
				SpawnGroup.MONSTER, JUNGLE_ZOMBIE_ENTITY, 30, 4, 4);
		FabricDefaultAttributeRegistry.register(JUNGLE_ZOMBIE_ENTITY, JungleZombieEntity.createZombieAttributes());
		SpawnRestrictionAccessor.callRegister(JUNGLE_ZOMBIE_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ZombieEntity::canMobSpawn);
		Register("slime_zombie", SLIME_ZOMBIE_ENTITY, List.of(EN_US.Zombie(EN_US.Slime())));
		FabricDefaultAttributeRegistry.register(SLIME_ZOMBIE_ENTITY, SlimeZombieEntity.createZombieAttributes());
		SpawnRestrictionAccessor.callRegister(SLIME_ZOMBIE_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ZombieEntity::canMobSpawn);
		//</editor-fold>
		//<editor-fold desc="Blackstone">
		Register("smooth_chiseled_polished_blackstone", SMOOTH_CHISELED_POLISHED_BLACKSTONE, List.of(EN_US.Blackstone(EN_US.Polished(EN_US.Chiseled(EN_US.Smooth())))));
		Register("polished_blackstone_tiles", POLISHED_BLACKSTONE_TILES, List.of(EN_US.Tiles(EN_US.Blackstone(EN_US.Polished()))));
		Register("polished_blackstone_tile_stairs", POLISHED_BLACKSTONE_TILE_STAIRS, List.of(EN_US.Stairs(EN_US.Tile(EN_US.Blackstone(EN_US.Polished())))));
		Register("polished_blackstone_tile_slab", POLISHED_BLACKSTONE_TILE_SLAB, List.of(EN_US.Slab(EN_US.Tile(EN_US.Blackstone(EN_US.Polished())))));
		Register("polished_blackstone_tile_wall", POLISHED_BLACKSTONE_TILE_WALL, List.of(EN_US.Wall(EN_US.Tile(EN_US.Blackstone(EN_US.Polished())))));
		//</editor-fold>
		//<editor-fold desc="Gilded Blackstone">
		Register("gilded_blackstone_stairs", GILDED_BLACKSTONE_STAIRS, List.of(EN_US.Stairs(EN_US.Blackstone(EN_US.Gilded()))));
		Register("gilded_blackstone_slab", GILDED_BLACKSTONE_SLAB, List.of(EN_US.Slab(EN_US.Blackstone(EN_US.Gilded()))));
		Register("gilded_blackstone_wall", GILDED_BLACKSTONE_WALL, List.of(EN_US.Wall(EN_US.Blackstone(EN_US.Gilded()))));
		Register("polished_gilded_blackstone", POLISHED_GILDED_BLACKSTONE, List.of(EN_US.Blackstone(EN_US.Gilded(EN_US.Polished()))));
		Register("polished_gilded_blackstone_stairs", POLISHED_GILDED_BLACKSTONE_STAIRS, List.of(EN_US.Stairs(EN_US.Blackstone(EN_US.Gilded(EN_US.Polished())))));
		Register("polished_gilded_blackstone_slab", POLISHED_GILDED_BLACKSTONE_SLAB, List.of(EN_US.Slab(EN_US.Blackstone(EN_US.Gilded(EN_US.Polished())))));
		Register("polished_gilded_blackstone_wall", POLISHED_GILDED_BLACKSTONE_WALL, List.of(EN_US.Wall(EN_US.Blackstone(EN_US.Gilded(EN_US.Polished())))));
		Register("polished_gilded_blackstone_bricks", POLISHED_GILDED_BLACKSTONE_BRICKS, List.of(EN_US.Bricks(EN_US.Blackstone(EN_US.Gilded(EN_US.Polished())))));
		Register("polished_gilded_blackstone_brick_stairs", POLISHED_GILDED_BLACKSTONE_BRICK_STAIRS, List.of(EN_US.Stairs(EN_US.Brick(EN_US.Blackstone(EN_US.Gilded(EN_US.Polished()))))));
		Register("polished_gilded_blackstone_brick_slab", POLISHED_GILDED_BLACKSTONE_BRICK_SLAB, List.of(EN_US.Slab(EN_US.Brick(EN_US.Blackstone(EN_US.Gilded(EN_US.Polished()))))));
		Register("polished_gilded_blackstone_brick_wall", POLISHED_GILDED_BLACKSTONE_BRICK_WALL, List.of(EN_US.Wall(EN_US.Brick(EN_US.Blackstone(EN_US.Gilded(EN_US.Polished()))))));
		Register("cracked_polished_gilded_blackstone_bricks", CRACKED_POLISHED_GILDED_BLACKSTONE_BRICKS, List.of(EN_US.Bricks(EN_US.Blackstone(EN_US.Gilded(EN_US.Polished(EN_US.Cracked()))))));
		CrackedBlocks.Register(POLISHED_GILDED_BLACKSTONE_BRICKS, CRACKED_POLISHED_GILDED_BLACKSTONE_BRICKS);
		Register("chiseled_polished_gilded_blackstone", CHISELED_POLISHED_GILDED_BLACKSTONE, List.of(EN_US.Blackstone(EN_US.Gilded(EN_US.Polished(EN_US.Chiseled())))));
		Register("smooth_chiseled_polished_gilded_blackstone", SMOOTH_CHISELED_POLISHED_GILDED_BLACKSTONE, List.of(EN_US.Blackstone(EN_US.Gilded(EN_US.Polished(EN_US.Chiseled(EN_US.Smooth()))))));
		Register("polished_gilded_blackstone_tiles", POLISHED_GILDED_BLACKSTONE_TILES, List.of(EN_US.Tiles(EN_US.Blackstone(EN_US.Gilded(EN_US.Polished())))));
		Register("polished_gilded_blackstone_tile_stairs", POLISHED_GILDED_BLACKSTONE_TILE_STAIRS, List.of(EN_US.Stairs(EN_US.Tile(EN_US.Blackstone(EN_US.Gilded(EN_US.Polished()))))));
		Register("polished_gilded_blackstone_tile_slab", POLISHED_GILDED_BLACKSTONE_TILE_SLAB, List.of(EN_US.Slab(EN_US.Tile(EN_US.Blackstone(EN_US.Gilded(EN_US.Polished()))))));
		Register("polished_gilded_blackstone_tile_wall", POLISHED_GILDED_BLACKSTONE_TILE_WALL, List.of(EN_US.Wall(EN_US.Tile(EN_US.Blackstone(EN_US.Gilded(EN_US.Polished()))))));
		//</editor-fold>
		//<editor-fold desc="Arrows">
		Register("amethyst_arrow", AMETHYST_ARROW, List.of(EN_US.Arrow(EN_US.Amethyst())));
		Register("blinding_arrow", BLINDING_ARROW, List.of(EN_US.Arrow(EN_US.Blinding())));
		Register("bone_arrow", BONE_ARROW, List.of(EN_US.Arrow(EN_US.Bone())));
		Register("chorus_arrow", CHORUS_ARROW, List.of(EN_US.Arrow(EN_US.Chorus())));
		//</editor-fold>
		//<editor-fold desc="Golems and Gourds">
		Register("soul_jack_o_lantern", SOUL_JACK_O_LANTERN, List.of(EN_US.JackOLantern(EN_US.Soul())));
		Register("ender_jack_o_lantern", ENDER_JACK_O_LANTERN, List.of(EN_US.JackOLantern(EN_US.Ender())));
		Register("melon_seeds_projectile", MELON_SEED_PROJECTILE_ENTITY, List.of(EN_US.Seeds(EN_US.Melon())));
		Register("melon_golem", MELON_GOLEM_ENTITY, List.of(EN_US.Golem(EN_US.Melon())));
		FabricDefaultAttributeRegistry.register(MELON_GOLEM_ENTITY, MelonGolemEntity.createMelonGolemAttributes());
		Register("carved_melon", CARVED_MELON, List.of(EN_US.Melon(EN_US.Carved())));
		DispenserBlock.registerBehavior(CARVED_MELON.asBlock(), new FallibleItemDispenserBehavior() {
			protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
				World world = pointer.getWorld();
				BlockPos blockPos = pointer.getPos().offset(pointer.getBlockState().get(DispenserBlock.FACING));
				CarvedMelonBlock carvedMelonBlock = (CarvedMelonBlock) CARVED_MELON.asBlock();
				if (world.isAir(blockPos) && carvedMelonBlock.canDispense(world, blockPos)) {
					if (!world.isClient) {
						world.setBlockState(blockPos, carvedMelonBlock.getDefaultState(), Block.NOTIFY_ALL);
						world.emitGameEvent(null, GameEvent.BLOCK_PLACE, blockPos);
					}
					stack.decrement(1);
					this.setSuccess(true);
				}
				else this.setSuccess(ArmorItem.dispenseArmor(pointer, stack));
				return stack;
			}
		});
		Register("melon_lantern", MELON_LANTERN, List.of(EN_US.Lantern(EN_US.Melon())));
		Register("soul_melon_lantern", SOUL_MELON_LANTERN, List.of(EN_US.SoulLantern(EN_US.Melon())));
		Register("ender_melon_lantern", ENDER_MELON_LANTERN, List.of(EN_US.EnderLantern(EN_US.Melon())));
		//Burnt Pumpkin
		Register("burnt_pumpkin", BURNT_PUMPKIN, List.of(EN_US.Pumpkin(EN_US.Burnt())));
		//White Pumpkin
		Register("white_pumpkin", WHITE_PUMPKIN, List.of(EN_US.Pumpkin(EN_US.White())));
		Register("carved_white_pumpkin", CARVED_WHITE_PUMPKIN, List.of(EN_US.Carved(EN_US.Pumpkin(EN_US.White()))));
		DispenserBlock.registerBehavior(CARVED_WHITE_PUMPKIN.asBlock(), new ModCarvedPumpkinBlockDispenserBehavior(CARVED_WHITE_PUMPKIN));
		Register("white_pumpkin_stem", WHITE_PUMPKIN_STEM, List.of(EN_US.Stem(EN_US.Pumpkin(EN_US.White()))));
		ModDatagen.Cache.Drops.put(WHITE_PUMPKIN_STEM, DropTable.Drops(WHITE_PUMPKIN_SEEDS));
		Register("attached_white_pumpkin_stem", ATTACHED_WHITE_PUMPKIN_STEM, List.of(EN_US.Stem(EN_US.Pumpkin(EN_US.White(EN_US.Attached())))));
		ModDatagen.Cache.Drops.put(ATTACHED_WHITE_PUMPKIN_STEM, DropTable.Drops(WHITE_PUMPKIN_SEEDS));
		Register("white_pumpkin_seeds", WHITE_PUMPKIN_SEEDS, List.of(EN_US.Seeds(EN_US.Pumpkin(EN_US.White()))));
		CompostingChanceRegistry.INSTANCE.add(WHITE_PUMPKIN_SEEDS, 0.65F);
		Register("white_jack_o_lantern", WHITE_JACK_O_LANTERN, List.of(EN_US.JackOLantern(EN_US.White())));
		Register("white_soul_jack_o_lantern", WHITE_SOUL_JACK_O_LANTERN, List.of(EN_US.JackOLantern(EN_US.Soul(EN_US.White()))));
		Register("white_ender_jack_o_lantern", WHITE_ENDER_JACK_O_LANTERN, List.of(EN_US.JackOLantern(EN_US.Ender(EN_US.White()))));
		//Rotten Pumpkin
		Register("rotten_pumpkin", ROTTEN_PUMPKIN, List.of(EN_US.Pumpkin(EN_US.Rotten())));
		Register("carved_rotten_pumpkin", CARVED_ROTTEN_PUMPKIN, List.of(EN_US.Pumpkin(EN_US.Rotten(EN_US.Carved()))));
		DispenserBlock.registerBehavior(CARVED_ROTTEN_PUMPKIN.asBlock(), new ModCarvedPumpkinBlockDispenserBehavior(CARVED_ROTTEN_PUMPKIN));
		Register("rotten_jack_o_lantern", ROTTEN_JACK_O_LANTERN, List.of(EN_US.JackOLantern(EN_US.Rotten())));
		Register("rotten_soul_jack_o_lantern", ROTTEN_SOUL_JACK_O_LANTERN, List.of(EN_US.JackOLantern(EN_US.Soul(EN_US.Rotten()))));
		Register("rotten_ender_jack_o_lantern", ROTTEN_ENDER_JACK_O_LANTERN, List.of(EN_US.JackOLantern(EN_US.Ender(EN_US.Rotten()))));
		Register("rotten_pumpkin_seeds", ROTTEN_PUMPKIN_SEEDS, List.of(EN_US.Seeds(EN_US.Pumpkin(EN_US.Rotten()))));
		CompostingChanceRegistry.INSTANCE.add(ROTTEN_PUMPKIN_SEEDS, 0.65F);
		//</editor-fold>
		//<editor-fold desc="Mushrooms">
		Register("death_cap_mushroom", DEATH_CAP_MUSHROOM, List.of(EN_US._Potted(EN_US.Mushroom(EN_US.Cap(EN_US.Death())))));
		Register("blue_nethershroom", BLUE_NETHERSHROOM, List.of(EN_US._Potted(EN_US.Nethershroom(EN_US.Blue()))));
		Register("blue_mushroom", BLUE_MUSHROOM, List.of(EN_US._Potted(EN_US.Mushroom(EN_US.Blue()))));
		Register("blue_mushroom_block", BLUE_MUSHROOM_BLOCK, List.of(EN_US.Block(EN_US.Mushroom(EN_US.Blue()))));
		//</editor-fold>
		RegisterMushroomWood();
		Register("creeper_anatomy_statue", CREEPER_ANATOMY_STATUE, List.of(EN_US.Statue(EN_US.Anatomy(EN_US.Creeper()))));
		RegisterPlushies();
		RegisterDarkIron();
		//<editor-fold desc="Gilded Fungus">
		Register("gilded_stem", GILDED_STEM, List.of(EN_US.Stem(EN_US.Gilded())));
		Register("stripped_gilded_stem", STRIPPED_GILDED_STEM, List.of(EN_US.Stem(EN_US.Gilded(EN_US.Stripped()))));
		Register("gilded_hyphae", GILDED_HYPHAE, List.of(EN_US.Hyphae(EN_US.Gilded())));
		Register("stripped_gilded_hyphae", STRIPPED_GILDED_HYPHAE, List.of(EN_US.Hyphae(EN_US.Gilded(EN_US.Stripped()))));
		Register("gilded_planks", GILDED_PLANKS, List.of(EN_US.Planks(EN_US.Gilded())));
		Register("gilded_stairs", GILDED_STAIRS, List.of(EN_US.Stairs(EN_US.Gilded())));
		Register("gilded_slab", GILDED_SLAB, List.of(EN_US.Slab(EN_US.Gilded())));
		Register("gilded_fence", GILDED_FENCE, List.of(EN_US.Fence(EN_US.Gilded())));
		Register("gilded_fence_gate", GILDED_FENCE_GATE, List.of(EN_US.Gate(EN_US.Fence(EN_US.Gilded()))));
		Register("gilded_door", GILDED_DOOR, List.of(EN_US.Door(EN_US.Gilded())));
		Register("gilded_trapdoor", GILDED_TRAPDOOR, List.of(EN_US.Trapdoor(EN_US.Gilded())));
		Register("gilded_pressure_plate", GILDED_PRESSURE_PLATE, List.of(EN_US.Plate(EN_US.Pressure(EN_US.Gilded()))));
		Register("gilded_button", GILDED_BUTTON, List.of(EN_US.Button(EN_US.Gilded())));
		Register("gilded", GILDED_SIGN, List.of(EN_US._Sign(EN_US.Gilded())));
		Register("gilded_iron_hanging_sign", "gilded_iron_wall_hanging_sign", GILDED_IRON_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Gilded(), EN_US.Chain(EN_US.Iron(EN_US.with())))));
		Register("gilded_gold_hanging_sign", "gilded_gold_wall_hanging_sign", GILDED_GOLD_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Gilded(), EN_US.Chain(EN_US.Gold(EN_US.with())))));
		Register("gilded_copper_hanging_sign", "gilded_copper_wall_hanging_sign", GILDED_COPPER_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Gilded(), EN_US.Chain(EN_US.Copper(EN_US.with())))));
		Register("gilded_exposed_copper_hanging_sign", "gilded_exposed_copper_wall_hanging_sign", GILDED_EXPOSED_COPPER_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Gilded(), EN_US.Chain(EN_US.Copper(EN_US.Exposed(EN_US.with()))))));
		Register("gilded_weathered_copper_hanging_sign", "gilded_weathered_copper_wall_hanging_sign", GILDED_WEATHERED_COPPER_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Gilded(), EN_US.Chain(EN_US.Copper(EN_US.Weathered(EN_US.with()))))));
		Register("gilded_oxidized_copper_hanging_sign", "gilded_oxidized_copper_wall_hanging_sign", GILDED_OXIDIZED_COPPER_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Gilded(), EN_US.Chain(EN_US.Copper(EN_US.Oxidized(EN_US.with()))))));
		Register("gilded_netherite_hanging_sign", "gilded_netherite_wall_hanging_sign", GILDED_NETHERITE_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Gilded(), EN_US.Chain(EN_US.Netherite(EN_US.with())))));
		//Extended
		Register("gilded_beehive", GILDED_BEEHIVE, List.of(EN_US.Beehive(EN_US.Gilded())));
		Register("gilded_bookshelf", GILDED_BOOKSHELF, List.of(EN_US.Bookshelf(EN_US.Gilded())));
		Register("gilded_chiseled_bookshelf", GILDED_CHISELED_BOOKSHELF, List.of(EN_US.Bookshelf(EN_US.Chiseled(EN_US.Gilded()))));
		Register("gilded_crafting_table", GILDED_CRAFTING_TABLE, List.of(EN_US.Table(EN_US.Crafting(EN_US.Gilded()))));
		Register("gilded_ladder", GILDED_LADDER, List.of(EN_US.Ladder(EN_US.Gilded())));
		Register("gilded_woodcutter", GILDED_WOODCUTTER, List.of(EN_US.Woodcutter(EN_US.Gilded())));
		Register("gilded_barrel", GILDED_BARREL, List.of(EN_US.Barrel(EN_US.Gilded())));
		Register("gilded_lectern", GILDED_LECTERN, List.of(EN_US.Lectern(EN_US.Gilded())));
		Register("gilded_powder_keg", GILDED_POWDER_KEG, List.of(EN_US.Keg(EN_US.Powder(EN_US.Gilded()))));
		Register("gilded_stem_slab", GILDED_STEM_SLAB, List.of(EN_US.Stem(EN_US.Gilded())));
		Register("stripped_gilded_stem_slab", STRIPPED_GILDED_STEM_SLAB, List.of(EN_US.Slab(EN_US.Stem(EN_US.Gilded(EN_US.Stripped())))));
		Register("gilded_hyphae_slab", GILDED_HYPHAE_SLAB, List.of(EN_US.Slab(EN_US.Hyphae(EN_US.Gilded()))));
		Register("stripped_gilded_hyphae_slab", STRIPPED_GILDED_HYPHAE_SLAB, List.of(EN_US.Slab(EN_US.Hyphae(EN_US.Gilded(EN_US.Stripped())))));
		Register("gilded_hammer", GILDED_HAMMER, List.of(EN_US.Hammer(EN_US.Gilded())));
		//Torches
		Register("gilded_torch", "gilded_wall_torch", GILDED_TORCH, List.of(EN_US._Torch(EN_US.Gilded())));
		Register("gilded_soul_torch", "gilded_soul_wall_torch", GILDED_SOUL_TORCH, List.of(EN_US.SoulTorch(EN_US.Gilded())));
		Register("gilded_ender_torch", "gilded_ender_wall_torch", GILDED_ENDER_TORCH, List.of(EN_US.EnderTorch(EN_US.Gilded())));
		Register("underwater_gilded_torch", "underwater_gilded_wall_torch", UNDERWATER_GILDED_TORCH, List.of(EN_US.Underwater_Torch(EN_US.Gilded())));
		//Campfires
		Register("gilded_campfire", GILDED_CAMPFIRE, List.of(EN_US.Campfire(EN_US.Gilded())));
		Register("gilded_soul_campfire", GILDED_SOUL_CAMPFIRE, List.of(EN_US.Campfire(EN_US.Soul(EN_US.Gilded()))));
		Register("gilded_ender_campfire", GILDED_ENDER_CAMPFIRE, List.of(EN_US.Campfire(EN_US.Ender(EN_US.Gilded()))));
		//Fungus
		Register("gilded_fungus", GILDED_FUNGUS, List.of(EN_US._Potted(EN_US.Fungus(EN_US.Gilded()))));
		Register("gilded_roots", GILDED_ROOTS, List.of(EN_US._Potted(EN_US.Roots(EN_US.Gilded()))));
		Register("gilded_wart", GILDED_WART, List.of(EN_US.Wart(EN_US.Gilded())));
		Register("gilded_wart_block", GILDED_WART_BLOCK, List.of(EN_US.Block(EN_US.Wart(EN_US.Gilded()))));
		Register("gilded_wart_slab", GILDED_WART_SLAB, List.of(EN_US.Slab(EN_US.Wart(EN_US.Gilded()))));
		Register("gilded_nylium", GILDED_NYLIUM, List.of(EN_US.Nylium(EN_US.Gilded())));
		//</editor-fold>
		//<editor-fold desc="Nether Bricks">
		Register("smooth_chiseled_nether_bricks", SMOOTH_CHISELED_NETHER_BRICKS, List.of(EN_US.Bricks(EN_US.Nether(EN_US.Chiseled(EN_US.Smooth())))));
		Register("red_nether_brick_fence", RED_NETHER_BRICK_FENCE, List.of(EN_US.Fence(EN_US.Brick(EN_US.Nether(EN_US.Red())))));
		Register("chiseled_red_nether_bricks", CHISELED_RED_NETHER_BRICKS, List.of(EN_US.Bricks(EN_US.Nether(EN_US.Red(EN_US.Chiseled())))));
		Register("smooth_chiseled_red_nether_bricks", SMOOTH_CHISELED_RED_NETHER_BRICKS, List.of(EN_US.Bricks(EN_US.Nether(EN_US.Red(EN_US.Chiseled(EN_US.Smooth()))))));
		Register("cracked_red_nether_bricks", CRACKED_RED_NETHER_BRICKS, List.of(EN_US.Bricks(EN_US.Nether(EN_US.Red(EN_US.Cracked())))));
		CrackedBlocks.Register(Blocks.RED_NETHER_BRICKS, CRACKED_RED_NETHER_BRICKS);
		Register("blue_nether_bricks", BLUE_NETHER_BRICKS, List.of(EN_US.Bricks(EN_US.Nether(EN_US.Blue()))));
		Register("blue_nether_brick_stairs", BLUE_NETHER_BRICK_STAIRS, List.of(EN_US.Stairs(EN_US.Brick(EN_US.Nether(EN_US.Blue())))));
		Register("blue_nether_brick_slab", BLUE_NETHER_BRICK_SLAB, List.of(EN_US.Slab(EN_US.Brick(EN_US.Nether(EN_US.Blue())))));
		Register("blue_nether_brick_wall", BLUE_NETHER_BRICK_WALL, List.of(EN_US.Wall(EN_US.Brick(EN_US.Nether(EN_US.Blue())))));
		Register("blue_nether_brick_fence", BLUE_NETHER_BRICK_FENCE, List.of(EN_US.Fence(EN_US.Brick(EN_US.Nether(EN_US.Blue())))));
		Register("chiseled_blue_nether_bricks", CHISELED_BLUE_NETHER_BRICKS, List.of(EN_US.Bricks(EN_US.Nether(EN_US.Blue(EN_US.Chiseled())))));
		Register("smooth_chiseled_blue_nether_bricks", SMOOTH_CHISELED_BLUE_NETHER_BRICKS, List.of(EN_US.Bricks(EN_US.Nether(EN_US.Blue(EN_US.Chiseled(EN_US.Smooth()))))));
		Register("cracked_blue_nether_bricks", CRACKED_BLUE_NETHER_BRICKS, List.of(EN_US.Bricks(EN_US.Nether(EN_US.Blue(EN_US.Cracked())))));
		CrackedBlocks.Register(BLUE_NETHER_BRICKS, CRACKED_BLUE_NETHER_BRICKS);
		Register("yellow_nether_bricks", YELLOW_NETHER_BRICKS, List.of(EN_US.Bricks(EN_US.Nether(EN_US.Yellow()))));
		Register("yellow_nether_brick_stairs", YELLOW_NETHER_BRICK_STAIRS, List.of(EN_US.Stairs(EN_US.Brick(EN_US.Nether(EN_US.Yellow())))));
		Register("yellow_nether_brick_slab", YELLOW_NETHER_BRICK_SLAB, List.of(EN_US.Slab(EN_US.Brick(EN_US.Nether(EN_US.Yellow())))));
		Register("yellow_nether_brick_wall", YELLOW_NETHER_BRICK_WALL, List.of(EN_US.Wall(EN_US.Brick(EN_US.Nether(EN_US.Yellow())))));
		Register("yellow_nether_brick_fence", YELLOW_NETHER_BRICK_FENCE, List.of(EN_US.Fence(EN_US.Brick(EN_US.Nether(EN_US.Yellow())))));
		Register("chiseled_yellow_nether_bricks", CHISELED_YELLOW_NETHER_BRICKS, List.of(EN_US.Bricks(EN_US.Nether(EN_US.Yellow(EN_US.Chiseled())))));
		Register("smooth_chiseled_yellow_nether_bricks", SMOOTH_CHISELED_YELLOW_NETHER_BRICKS, List.of(EN_US.Bricks(EN_US.Nether(EN_US.Yellow(EN_US.Chiseled(EN_US.Smooth()))))));
		Register("cracked_yellow_nether_bricks", CRACKED_YELLOW_NETHER_BRICKS, List.of(EN_US.Bricks(EN_US.Nether(EN_US.Yellow(EN_US.Cracked())))));
		CrackedBlocks.Register(YELLOW_NETHER_BRICKS, CRACKED_YELLOW_NETHER_BRICKS);
		Register("black_nether_bricks", BLACK_NETHER_BRICKS, List.of(EN_US.Bricks(EN_US.Nether(EN_US.Black()))));
		Register("black_nether_brick_stairs", BLACK_NETHER_BRICK_STAIRS, List.of(EN_US.Stairs(EN_US.Brick(EN_US.Nether(EN_US.Black())))));
		Register("black_nether_brick_slab", BLACK_NETHER_BRICK_SLAB, List.of(EN_US.Slab(EN_US.Brick(EN_US.Nether(EN_US.Black())))));
		Register("black_nether_brick_wall", BLACK_NETHER_BRICK_WALL, List.of(EN_US.Wall(EN_US.Brick(EN_US.Nether(EN_US.Black())))));
		Register("black_nether_brick_fence", BLACK_NETHER_BRICK_FENCE, List.of(EN_US.Fence(EN_US.Brick(EN_US.Nether(EN_US.Black())))));
		Register("chiseled_black_nether_bricks", CHISELED_BLACK_NETHER_BRICKS, List.of(EN_US.Bricks(EN_US.Nether(EN_US.Black(EN_US.Chiseled())))));
		Register("smooth_chiseled_black_nether_bricks", SMOOTH_CHISELED_BLACK_NETHER_BRICKS, List.of(EN_US.Bricks(EN_US.Nether(EN_US.Black(EN_US.Chiseled(EN_US.Smooth()))))));
		Register("cracked_black_nether_bricks", CRACKED_BLACK_NETHER_BRICKS, List.of(EN_US.Bricks(EN_US.Nether(EN_US.Black(EN_US.Cracked())))));
		CrackedBlocks.Register(BLACK_NETHER_BRICKS, CRACKED_BLACK_NETHER_BRICKS);
		//</editor-fold>
		//<editor-fold desc="Misc Stuff">
		Register("coal_stairs", COAL_STAIRS, List.of(EN_US.Stairs(EN_US.Coal())));
		Register("coal_slab", COAL_SLAB, List.of(EN_US.Slab(EN_US.Coal())));
		Register("charcoal_block", CHARCOAL_BLOCK, List.of(EN_US.Block(EN_US.Charcoal())));
		Register("charcoal_stairs", CHARCOAL_STAIRS, List.of(EN_US.Stairs(EN_US.Charcoal())));
		Register("charcoal_slab", CHARCOAL_SLAB, List.of(EN_US.Slab(EN_US.Charcoal())));
		Register("coarse_dirt_slab", COARSE_DIRT_SLAB, List.of(EN_US.Slab(EN_US.Dirt(EN_US.Coarse()))));
		Register("cocoa_block", COCOA_BLOCK, List.of(EN_US.Block(EN_US.Cocoa())));
		Register("blue_shroomlight", BLUE_SHROOMLIGHT, List.of(EN_US.Shroomlight(EN_US.Blue())));
		Register("flint_block", FLINT_BLOCK, List.of(EN_US.Block(EN_US.Flint())));
		Register("flint_slab", FLINT_SLAB, List.of(EN_US.Slab(EN_US.Flint())));
		Register("flint_bricks", FLINT_BRICKS, List.of(EN_US.Bricks(EN_US.Flint())));
		Register("flint_brick_stairs", FLINT_BRICK_STAIRS, List.of(EN_US.Stairs(EN_US.Brick(EN_US.Flint()))));
		Register("flint_brick_slab", FLINT_BRICK_SLAB, List.of(EN_US.Slab(EN_US.Brick(EN_US.Flint()))));
		Register("flint_brick_wall", FLINT_BRICK_WALL, List.of(EN_US.Wall(EN_US.Brick(EN_US.Flint()))));
		Register("gunpowder_block", GUNPOWDER_BLOCK, List.of(EN_US.Block(EN_US.Gunpowder())));
		Register("gunpowder_fuse", GUNPOWDER_FUSE, List.of(EN_US.Fuse(EN_US.Gunpowder())));
		Register("powder_keg", POWDER_KEG_ENTITY, List.of(EN_US.Keg(EN_US.Powder())));
		Register("hedge_block", HEDGE_BLOCK, List.of(EN_US.Block(EN_US.Hedge())));
		Register("light_blue_target", LIGHT_BLUE_TARGET, List.of(EN_US.Target(EN_US.Blue(EN_US.Light()))));
		Register("mycelium_roots", MYCELIUM_ROOTS, List.of(EN_US._Potted(EN_US.Roots(EN_US.Mycelium()))));
		Register("seed_block", SEED_BLOCK, List.of(EN_US.Block(EN_US.Seed())));
		Register("sugar_block", SUGAR_BLOCK, List.of(EN_US.Block(EN_US.Sugar())));
		Register("caramel_block", CARAMEL_BLOCK, List.of(EN_US.Block(EN_US.Caramel())));
		Register("sweet_berry_leaves", SWEET_BERRY_LEAVES, List.of(EN_US.Leaves(EN_US.Berry(EN_US.Sweet()))));
		Register("wax_block", WAX_BLOCK, List.of(EN_US.Block(EN_US.Wax())));
		Register("wind_horn", WIND_HORN, List.of(EN_US.Horn(EN_US.Wind())));
		Register("grappling_rod", GRAPPLING_ROD, List.of(EN_US.Rod(EN_US.Grappling())));
		//</editor-fold>
		//<editor-fold desc="Wildfire">
		Register("wildfire_crown", WILDFIRE_CROWN, List.of(EN_US.Crown(EN_US.Wildfire())));
		//</editor-fold>
		//<editor-fold desc="Sugar Cane">
		Register("sugar_cane_block", SUGAR_CANE_BLOCK, List.of(EN_US.Block(EN_US.Cane(EN_US.Sugar()))));
		Register("sugar_cane_block_slab", SUGAR_CANE_BLOCK_SLAB, List.of(EN_US.Slab(EN_US.Block(EN_US.Cane(EN_US.Sugar())))));
		Register("sugar_cane_row", SUGAR_CANE_ROW, List.of(EN_US.Row(EN_US.Cane(EN_US.Sugar()))));
		Register("sugar_cane_bale", SUGAR_CANE_BALE, List.of(EN_US.Bale(EN_US.Cane(EN_US.Sugar()))));
		//Wood
		Register("sugar_cane_planks", SUGAR_CANE_PLANKS, List.of(EN_US.Planks(EN_US.Cane(EN_US.Sugar()))));
		Register("sugar_cane_stairs", SUGAR_CANE_STAIRS, List.of(EN_US.Stairs(EN_US.Cane(EN_US.Sugar()))));
		Register("sugar_cane_slab", SUGAR_CANE_SLAB, List.of(EN_US.Slab(EN_US.Cane(EN_US.Sugar()))));
		Register("sugar_cane_fence", SUGAR_CANE_FENCE, List.of(EN_US.Fence(EN_US.Cane(EN_US.Sugar()))));
		Register("sugar_cane_fence_gate", SUGAR_CANE_FENCE_GATE, List.of(EN_US.Gate(EN_US.Fence(EN_US.Cane(EN_US.Sugar())))));
		Register("sugar_cane_door", SUGAR_CANE_DOOR, List.of(EN_US.Door(EN_US.Cane(EN_US.Sugar()))));
		Register("sugar_cane_trapdoor", SUGAR_CANE_TRAPDOOR, List.of(EN_US.Trapdoor(EN_US.Cane(EN_US.Sugar()))));
		Register("sugar_cane_pressure_plate", SUGAR_CANE_PRESSURE_PLATE, List.of(EN_US.Plate(EN_US.Pressure(EN_US.Cane(EN_US.Sugar())))));
		Register("sugar_cane_button", SUGAR_CANE_BUTTON, List.of(EN_US.Button(EN_US.Cane(EN_US.Sugar()))));
		Register("sugar_cane", SUGAR_CANE_SIGN, List.of(EN_US._Sign(EN_US.Cane(EN_US.Sugar()))));
		Register("sugar_cane_iron_hanging_sign", "sugar_cane_iron_wall_hanging_sign", SUGAR_CANE_IRON_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Cane(EN_US.Sugar()), EN_US.Chain(EN_US.Iron(EN_US.with())))));
		Register("sugar_cane_gold_hanging_sign", "sugar_cane_gold_wall_hanging_sign", SUGAR_CANE_GOLD_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Cane(EN_US.Sugar()), EN_US.Chain(EN_US.Gold(EN_US.with())))));
		Register("sugar_cane_copper_hanging_sign", "sugar_cane_copper_wall_hanging_sign", SUGAR_CANE_COPPER_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Cane(EN_US.Sugar()), EN_US.Chain(EN_US.Copper(EN_US.with())))));
		Register("sugar_cane_exposed_copper_hanging_sign", "sugar_cane_exposed_copper_wall_hanging_sign", SUGAR_CANE_EXPOSED_COPPER_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Cane(EN_US.Sugar()), EN_US.Chain(EN_US.Copper(EN_US.Exposed(EN_US.with()))))));
		Register("sugar_cane_weathered_copper_hanging_sign", "sugar_cane_weathered_copper_wall_hanging_sign", SUGAR_CANE_WEATHERED_COPPER_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Cane(EN_US.Sugar()), EN_US.Chain(EN_US.Copper(EN_US.Weathered(EN_US.with()))))));
		Register("sugar_cane_oxidized_copper_hanging_sign", "sugar_cane_oxidized_copper_wall_hanging_sign", SUGAR_CANE_OXIDIZED_COPPER_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Cane(EN_US.Sugar()), EN_US.Chain(EN_US.Copper(EN_US.Oxidized(EN_US.with()))))));
		Register("sugar_cane_netherite_hanging_sign", "sugar_cane_netherite_wall_hanging_sign", SUGAR_CANE_NETHERITE_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Cane(EN_US.Sugar()), EN_US.Chain(EN_US.Netherite(EN_US.with())))));
		Register("sugar_cane", SUGAR_CANE_BOAT, List.of(EN_US._Boat(EN_US.Cane(EN_US.Sugar()))));
		//Extended
		Register("sugar_cane_beehive", SUGAR_CANE_BEEHIVE, List.of(EN_US.Beehive(EN_US.Cane(EN_US.Sugar()))));
		Register("sugar_cane_bookshelf", SUGAR_CANE_BOOKSHELF, List.of(EN_US.Bookshelf(EN_US.Cane(EN_US.Sugar()))));
		Register("sugar_cane_chiseled_bookshelf", SUGAR_CANE_CHISELED_BOOKSHELF, List.of(EN_US.Bookshelf(EN_US.Chiseled(EN_US.Cane(EN_US.Sugar())))));
		Register("sugar_cane_crafting_table", SUGAR_CANE_CRAFTING_TABLE, List.of(EN_US.Table(EN_US.Crafting(EN_US.Cane(EN_US.Sugar())))));
		Register("sugar_cane_ladder", SUGAR_CANE_LADDER, List.of(EN_US.Ladder(EN_US.Cane(EN_US.Sugar()))));
		Register("sugar_cane_woodcutter", SUGAR_CANE_WOODCUTTER, List.of(EN_US.Woodcutter(EN_US.Cane(EN_US.Sugar()))));
		Register("sugar_cane_barrel", SUGAR_CANE_BARREL, List.of(EN_US.Barrel(EN_US.Cane(EN_US.Sugar()))));
		Register("sugar_cane_lectern", SUGAR_CANE_LECTERN, List.of(EN_US.Lectern(EN_US.Cane(EN_US.Sugar()))));
		Register("sugar_cane_powder_keg", SUGAR_CANE_POWDER_KEG, List.of(EN_US.Keg(EN_US.Powder(EN_US.Cane(EN_US.Sugar())))));
		//Mosaic
		Register("sugar_cane_mosaic", SUGAR_CANE_MOSAIC, List.of(EN_US.Mosaic(EN_US.Cane(EN_US.Sugar()))));
		Register("sugar_cane_mosaic_stairs", SUGAR_CANE_MOSAIC_STAIRS, List.of(EN_US.Stairs(EN_US.Mosaic(EN_US.Cane(EN_US.Sugar())))));
		Register("sugar_cane_mosaic_slab", SUGAR_CANE_MOSAIC_SLAB, List.of(EN_US.Slab(EN_US.Mosaic(EN_US.Cane(EN_US.Sugar())))));
		//Misc
		Register("sugar_cane_hammer", SUGAR_CANE_HAMMER, List.of(EN_US.Hammer(EN_US.Cane(EN_US.Sugar()))));
		//Torches
		Register("sugar_cane_torch", "sugar_cane_wall_torch", SUGAR_CANE_TORCH, List.of(EN_US._Torch(EN_US.Cane(EN_US.Sugar()))));
		Register("sugar_cane_soul_torch", "sugar_cane_soul_wall_torch", SUGAR_CANE_SOUL_TORCH, List.of(EN_US.SoulTorch(EN_US.Cane(EN_US.Sugar()))));
		Register("sugar_cane_ender_torch", "sugar_cane_ender_wall_torch", SUGAR_CANE_ENDER_TORCH, List.of(EN_US.EnderTorch(EN_US.Cane(EN_US.Sugar()))));
		Register("underwater_sugar_cane_torch", "underwater_sugar_cane_wall_torch", UNDERWATER_SUGAR_CANE_TORCH, List.of(EN_US.Underwater_Torch(EN_US.Cane(EN_US.Sugar()))));
		//Campfires
		Register("sugar_cane_campfire", SUGAR_CANE_CAMPFIRE, List.of(EN_US.Campfire(EN_US.Cane(EN_US.Sugar()))));
		Register("sugar_cane_soul_campfire", SUGAR_CANE_SOUL_CAMPFIRE, List.of(EN_US.Campfire(EN_US.Soul(EN_US.Cane(EN_US.Sugar())))));
		Register("sugar_cane_ender_campfire", SUGAR_CANE_ENDER_CAMPFIRE, List.of(EN_US.Campfire(EN_US.Ender(EN_US.Cane(EN_US.Sugar())))));
		//</editor-fold>
		//<editor-fold desc="Hay">
		Register("hay_planks", HAY_PLANKS, List.of(EN_US.Planks(EN_US.Hay())));
		Register("hay_stairs", HAY_STAIRS, List.of(EN_US.Stairs(EN_US.Hay())));
		Register("hay_slab", HAY_SLAB, List.of(EN_US.Slab(EN_US.Hay())));
		Register("hay_fence", HAY_FENCE, List.of(EN_US.Fence(EN_US.Hay())));
		Register("hay_fence_gate", HAY_FENCE_GATE, List.of(EN_US.Gate(EN_US.Fence(EN_US.Hay()))));
		Register("hay_door", HAY_DOOR, List.of(EN_US.Door(EN_US.Hay())));
		Register("hay_trapdoor", HAY_TRAPDOOR, List.of(EN_US.Trapdoor(EN_US.Hay())));
		Register("hay_pressure_plate", HAY_PRESSURE_PLATE, List.of(EN_US.Plate(EN_US.Pressure(EN_US.Hay()))));
		Register("hay_button", HAY_BUTTON, List.of(EN_US.Button(EN_US.Hay())));
		Register("hay", HAY_SIGN, List.of(EN_US._Sign(EN_US.Hay())));
		Register("hay_iron_hanging_sign", "hay_iron_wall_hanging_sign", HAY_IRON_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Hay(), EN_US.Chain(EN_US.Iron(EN_US.with())))));
		Register("hay_gold_hanging_sign", "hay_gold_wall_hanging_sign", HAY_GOLD_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Hay(), EN_US.Chain(EN_US.Gold(EN_US.with())))));
		Register("hay_copper_hanging_sign", "hay_copper_wall_hanging_sign", HAY_COPPER_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Hay(), EN_US.Chain(EN_US.Copper(EN_US.with())))));
		Register("hay_exposed_copper_hanging_sign", "hay_exposed_copper_wall_hanging_sign", HAY_EXPOSED_COPPER_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Hay(), EN_US.Chain(EN_US.Copper(EN_US.Exposed(EN_US.with()))))));
		Register("hay_weathered_copper_hanging_sign", "hay_weathered_copper_wall_hanging_sign", HAY_WEATHERED_COPPER_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Hay(), EN_US.Chain(EN_US.Copper(EN_US.Weathered(EN_US.with()))))));
		Register("hay_oxidized_copper_hanging_sign", "hay_oxidized_copper_wall_hanging_sign", HAY_OXIDIZED_COPPER_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Hay(), EN_US.Chain(EN_US.Copper(EN_US.Oxidized(EN_US.with()))))));
		Register("hay_netherite_hanging_sign", "hay_netherite_wall_hanging_sign", HAY_NETHERITE_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Hay(), EN_US.Chain(EN_US.Netherite(EN_US.with())))));
		Register("hay", HAY_BOAT, List.of(EN_US._Boat(EN_US.Hay())));
		//Extended
		Register("hay_beehive", HAY_BEEHIVE, List.of(EN_US.Beehive(EN_US.Hay())));
		Register("hay_bookshelf", HAY_BOOKSHELF, List.of(EN_US.Bookshelf(EN_US.Hay())));
		Register("hay_chiseled_bookshelf", HAY_CHISELED_BOOKSHELF, List.of(EN_US.Bookshelf(EN_US.Chiseled(EN_US.Hay()))));
		Register("hay_crafting_table", HAY_CRAFTING_TABLE, List.of(EN_US.Table(EN_US.Crafting(EN_US.Hay()))));
		Register("hay_ladder", HAY_LADDER, List.of(EN_US.Ladder(EN_US.Hay())));
		Register("hay_woodcutter", HAY_WOODCUTTER, List.of(EN_US.Woodcutter(EN_US.Hay())));
		Register("hay_barrel", HAY_BARREL, List.of(EN_US.Barrel(EN_US.Hay())));
		Register("hay_lectern", HAY_LECTERN, List.of(EN_US.Lectern(EN_US.Hay())));
		Register("hay_powder_keg", HAY_POWDER_KEG, List.of(EN_US.Keg(EN_US.Powder(EN_US.Hay()))));
		//Torches
		Register("hay_torch", "hay_wall_torch", HAY_TORCH, List.of(EN_US._Torch(EN_US.Hay())));
		Register("hay_soul_torch", "hay_soul_wall_torch", HAY_SOUL_TORCH, List.of(EN_US.SoulTorch(EN_US.Hay())));
		Register("hay_ender_torch", "hay_ender_wall_torch", HAY_ENDER_TORCH, List.of(EN_US.EnderTorch(EN_US.Hay())));
		Register("underwater_hay_torch", "underwater_hay_wall_torch", UNDERWATER_HAY_TORCH, List.of(EN_US.Underwater_Torch(EN_US.Hay())));
		//</editor-fold>
		RegisterFood();
		//<editor-fold desc="Bottled Confetti & Dragon's Breath">
		Register("bottled_confetti", BOTTLED_CONFETTI_ITEM, List.of(EN_US.Confetti(EN_US.Bottled())));
		Register("bottled_confetti", BOTTLED_CONFETTI_ENTITY, List.of(EN_US.Confetti(EN_US.Bottled())));
		Register("dropped_confetti", DROPPED_CONFETTI_ENTITY, List.of(EN_US.Confetti(EN_US.Dropped())));
		Register("confetti_cloud", CONFETTI_CLOUD_ENTITY, List.of(EN_US.Cloud(EN_US.Confetti())));
		Register("dropped_dragon_breath", DROPPED_DRAGON_BREATH_ENTITY, List.of(EN_US.Breath(EN_US.Dragon(EN_US.Dropped()))));
		Register("dragon_breath_cloud", DRAGON_BREATH_CLOUD_ENTITY, List.of(EN_US.Cloud(EN_US.Breath(EN_US.Dragon()))));
		//</editor-fold>
		//<editor-fold desc="Bottled Lightning">
		Register("bottled_lightning", BOTTLED_LIGHTNING_ITEM, List.of(EN_US.Lightning(EN_US.Bottled())));
		Register("bottled_lightning", BOTTLED_LIGHTNING_ENTITY, List.of(EN_US.Lightning(EN_US.Bottled())));
		//</editor-fold>
		//<editor-fold desc="Throwable Tomato">
		Register("throwable_tomato", THROWABLE_TOMATO_ITEM, List.of(EN_US.Tomato(EN_US.Throwable())));
		Register("throwable_tomato", THROWABLE_TOMATO_ENTITY, List.of(EN_US.Tomato(EN_US.Throwable())));
		//</editor-fold>
		//<editor-fold desc="Poison">
		Register("poison_vial", POISON_VIAL, List.of(EN_US.Vial(EN_US.Poison())));
		JuicerBlock.JUICE_MAP.put(() -> Items.POISONOUS_POTATO, POISON_VIAL);
		JuicerBlock.JUICE_MAP.put(() -> POISONOUS_CARROT, POISON_VIAL);
		JuicerBlock.JUICE_MAP.put(() -> POISONOUS_BEETROOT, POISON_VIAL);
		JuicerBlock.JUICE_MAP.put(() -> POISONOUS_GLOW_BERRIES, POISON_VIAL);
		JuicerBlock.JUICE_MAP.put(() -> POISONOUS_SWEET_BERRIES, POISON_VIAL);
		JuicerBlock.JUICE_MAP.put(() -> POISONOUS_TOMATO, POISON_VIAL);
		Register("spider_poison_vial", SPIDER_POISON_VIAL, List.of(EN_US.Vial(EN_US.Venom(EN_US.Spider()))));
		JuicerBlock.JUICE_MAP.put(() -> Items.SPIDER_EYE, SPIDER_POISON_VIAL);
		Register("pufferfish_poison_vial", PUFFERFISH_POISON_VIAL, List.of(EN_US.Vial(EN_US.Poison(EN_US.Pufferfish()))));
		JuicerBlock.JUICE_MAP.put(() -> Items.PUFFERFISH, PUFFERFISH_POISON_VIAL);
		//</editor-fold>
		//<editor-fold desc="Hammers">
		Register("gravity_hammer", GRAVITY_HAMMER, List.of(EN_US.Gravity(EN_US.of(EN_US.Hammer()))));
		Register("repulsion_hammer", REPULSION_HAMMER, List.of(EN_US.Repulsion(EN_US.of(EN_US.Hammer()))));
		Register("thundering_hammer", THUNDERING_HAMMER, List.of(EN_US.Hammer(EN_US.Thundering())));
		//</editor-fold>
		RegisterDyes();
		//<editor-fold desc="More Misc Stuff">
		for(DyeColor color : DyeColor.values()) {
			String name = color.getName();
			Register(name + "_concrete_stairs", CONCRETE_STAIRS.get(color), List.of(EN_US.Stairs(EN_US.Concrete(EN_US.Color(color)))));
			Register(name + "_concrete_slab", CONCRETE_SLABS.get(color), List.of(EN_US.Slab(EN_US.Concrete(EN_US.Color(color)))));
			Register(name + "_concrete_wall", CONCRETE_WALLS.get(color), List.of(EN_US.Wall(EN_US.Concrete(EN_US.Color(color)))));
		}
		Register("terracotta_stairs", TERRACOTTA_STAIRS, List.of(EN_US.Stairs(EN_US.Terracotta())));
		Register("terracotta_slab", TERRACOTTA_SLAB, List.of(EN_US.Slab(EN_US.Terracotta())));
		Register("terracotta_wall", TERRACOTTA_WALL, List.of(EN_US.Wall(EN_US.Terracotta())));
		for(DyeColor color : DyeColor.values()) {
			String name = color.getName();
			Register(name + "_terracotta_stairs", TERRACOTTA_STAIRS_MAP.get(color), List.of(EN_US.Stairs(EN_US.Terracotta(EN_US.Color(color)))));
			Register(name + "_terracotta_slab", TERRACOTTA_SLABS.get(color), List.of(EN_US.Slab(EN_US.Terracotta(EN_US.Color(color)))));
			Register(name + "_terracotta_wall", TERRACOTTA_WALLS.get(color), List.of(EN_US.Wall(EN_US.Terracotta(EN_US.Color(color)))));
		}
		Register("glazed_terracotta", GLAZED_TERRACOTTA, List.of(EN_US.Terracotta(EN_US.Glazed())));
		Register("glazed_terracotta_slab", GLAZED_TERRACOTTA_SLAB, List.of(EN_US.Slab(EN_US.Terracotta(EN_US.Glazed()))));
		for(DyeColor color : DyeColor.values()) Register(color.getName() + "_glazed_terracotta_slab", GLAZED_TERRACOTTA_SLABS.get(color), List.of(EN_US.Slab(EN_US.Terracotta(EN_US.Glazed(EN_US.Color(color))))));
		Register("lapis_slab", LAPIS_SLAB, List.of(EN_US.Slab(EN_US.Lapis())));
		Register("kill_potion", KILL_POTION, List.of(EN_US.Instantly(EN_US.Die(EN_US.of(EN_US.Potion())))));
		Register("dense_brew", DENSE_BREW, List.of(EN_US.Brew(EN_US.Dense())));
		Register("sweet_brew", SWEET_BREW, List.of(EN_US.Brew(EN_US.Sweet())));
		Register("distilled_water_bottle", DISTILLED_WATER_BOTTLE, List.of(EN_US.Bottle(EN_US.Water(EN_US.Distilled()))));
		JuicerBlock.JUICE_MAP.put(() -> Items.ICE, DISTILLED_WATER_BOTTLE);
		JuicerBlock.JUICE_MAP.put(() -> Items.BLUE_ICE, DISTILLED_WATER_BOTTLE);
		JuicerBlock.JUICE_MAP.put(() -> Items.PACKED_ICE, DISTILLED_WATER_BOTTLE);
		Register("sugar_water_bottle", SUGAR_WATER_BOTTLE, List.of(EN_US.Bottle(EN_US.Water(EN_US.Sugar()))));
		Register("sap_bottle", SAP_BOTTLE, List.of(EN_US.Bottle(EN_US.Sap())));
		Register("syrup_bottle", SYRUP_BOTTLE, List.of(EN_US.Bottle(EN_US.Syrup())));
		Register("lava_bottle", LAVA_BOTTLE, List.of(EN_US.Bottle(EN_US.Lava())));
		FluidStorage.combinedItemApiProvider(LAVA_BOTTLE).register(context -> new FullItemFluidStorage(context, bottle -> ItemVariant.of(Items.GLASS_BOTTLE), FluidVariant.of(Fluids.LAVA), FluidConstants.BOTTLE));
		Register("magma_cream_bottle", MAGMA_CREAM_BOTTLE, List.of(EN_US.Bottle(EN_US.Cream(EN_US.Magma()))));
		JuicerBlock.JUICE_MAP.put(() -> Items.MAGMA_BLOCK, MAGMA_CREAM_BOTTLE);
		Register("cooled_magma_block", COOLED_MAGMA_BLOCK, List.of(EN_US.Block(EN_US.Magma(EN_US.Cooled()))));
		Register("pumice", PUMICE, List.of(EN_US.Pumice()));
		//</editor-fold>
		//<editor-fold desc="Slime">
		Register("blue_slime_ball", BLUE_SLIME_BALL, List.of(EN_US.Ball(EN_US.Slime(EN_US.Blue()))));
		Register("pink_slime_ball", PINK_SLIME_BALL, List.of(EN_US.Ball(EN_US.Slime(EN_US.Pink()))));
		DispenserBlock.registerBehavior(PINK_SLIME_BALL, new ProjectileDispenserBehavior(){
			@Override
			protected ProjectileEntity createProjectile(World world, Position position, ItemStack stack) {
				return Util.make(new PinkSlimeBallEntity(world, position.getX(), position.getY(), position.getZ()), entity -> entity.setItem(stack));
			}
		});
		Register("slime_bottle", SLIME_BOTTLE, List.of(EN_US.Bottle(EN_US.Slime())));
		JuicerBlock.JUICE_MAP.put(() -> Items.SLIME_BLOCK, SLIME_BOTTLE);
		Register("blue_slime_bottle", BLUE_SLIME_BOTTLE, List.of(EN_US.Bottle(EN_US.Slime(EN_US.Blue()))));
		JuicerBlock.JUICE_MAP.put(BLUE_SLIME_BLOCK::asItem, BLUE_SLIME_BOTTLE);
		Register("pink_slime_bottle", PINK_SLIME_BOTTLE, List.of(EN_US.Bottle(EN_US.Slime(EN_US.Pink()))));
		JuicerBlock.JUICE_MAP.put(PINK_SLIME_BLOCK::asItem, PINK_SLIME_BOTTLE);
		Register("blue_slime_block", BLUE_SLIME_BLOCK, List.of(EN_US.Block(EN_US.Slime(EN_US.Blue()))));
		Register("pink_slime_block", PINK_SLIME_BLOCK, List.of(EN_US.Block(EN_US.Slime(EN_US.Pink()))));
		Register("tropical_slime", TROPICAL_SLIME_ENTITY, List.of(EN_US.Slime(EN_US.Tropical())));
		FabricDefaultAttributeRegistry.register(TROPICAL_SLIME_ENTITY, HostileEntity.createHostileAttributes());
		BiomeModifications.addSpawn(BiomeSelectors.tag(ModBiomeTags.WARM_OCEANS).and(BiomeSelectors.includeByKey(BiomeKeys.JUNGLE)),
				SpawnGroup.MONSTER, TROPICAL_SLIME_ENTITY, 1, 1, 1);
		SpawnRestrictionAccessor.callRegister(TROPICAL_SLIME_ENTITY, SpawnRestriction.Location.IN_WATER, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, TropicalSlimeEntity::canMobSpawn);
		Register("pink_slime_ball", PINK_SLIME_BALL_ENTITY, List.of(EN_US.Ball(EN_US.Slime(EN_US.Pink()))));
		Register("pink_slime", PINK_SLIME_ENTITY, List.of(EN_US.Slime(EN_US.Pink())));
		FabricDefaultAttributeRegistry.register(PINK_SLIME_ENTITY, HostileEntity.createHostileAttributes());
		SpawnRestrictionAccessor.callRegister(PINK_SLIME_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SlimeEntity::canMobSpawn);
		//Other Mobs
		Register("slime_feather", SLIME_FEATHER, List.of(EN_US.Feather(EN_US.Slime())));
		Register("slime_chicken", SLIME_CHICKEN_ENTITY, List.of(EN_US.Slicken()));
		FabricDefaultAttributeRegistry.register(SLIME_CHICKEN_ENTITY, SlimeChickenEntity.createChickenAttributes());
		SpawnRestrictionAccessor.callRegister(SLIME_CHICKEN_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::isValidNaturalSpawn);

		Register("slime_cow", SLIME_COW_ENTITY, List.of(EN_US.Cow(EN_US.Slime())));
		FabricDefaultAttributeRegistry.register(SLIME_COW_ENTITY, SlimeCowEntity.createCowAttributes());
		SpawnRestrictionAccessor.callRegister(SLIME_COW_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::isValidNaturalSpawn);

		Register("slime_horse", SLIME_HORSE_ENTITY, List.of(EN_US.Slorse()));
		FabricDefaultAttributeRegistry.register(SLIME_HORSE_ENTITY, SlimeHorseEntity.createSlimeHorseAttributes());
		SpawnRestrictionAccessor.callRegister(SLIME_HORSE_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::isValidNaturalSpawn);
		//Lanterns
		Register("magma_cube_lantern", MAGMA_CUBE_LANTERN, List.of(EN_US.Lantern(EN_US.Cube(EN_US.Magma()))));
		Register("slime_lantern", SLIME_LANTERN, List.of(EN_US.Lantern(EN_US.Slime())));
		Register("tropical_slime_lantern", TROPICAL_SLIME_LANTERN, List.of(EN_US.Lantern(EN_US.Slime(EN_US.Tropical()))));
		Register("pink_slime_lantern", PINK_SLIME_LANTERN, List.of(EN_US.Lantern(EN_US.Slime(EN_US.Pink()))));
		//</editor-fold>
		Register("turtle_chestplate", TURTLE_CHESTPLATE, List.of(EN_US.Chestplate(EN_US.Turtle())));
		Register("turtle_boots", TURTLE_BOOTS, List.of(EN_US.Boots(EN_US.Turtle())));
		Register("horn", HORN, List.of(EN_US.Horn()));
		//<editor-fold desc="Feathers">
		Register("fancy_feather", FANCY_FEATHER, List.of(EN_US.Feather(EN_US.Fancy())));
		Register("black_feather", BLACK_FEATHER, List.of(EN_US.Feather(EN_US.Black())));
		Register("red_feather", RED_FEATHER, List.of(EN_US.Feather(EN_US.Red())));
		Register("light_feather", LIGHT_FEATHER, List.of(EN_US.Feather(EN_US.Light())));
		//</editor-fold>
		//<editor-fold desc="Books">
		Register("unreadable_book", UNREADABLE_BOOK, List.of(EN_US.Book(EN_US.Unreadable())));
		Register("red_book", RED_BOOK, List.of(EN_US.Book(EN_US.Red())));
		Register("orange_book", ORANGE_BOOK, List.of(EN_US.Book(EN_US.Orange())));
		Register("yellow_book", YELLOW_BOOK, List.of(EN_US.Book(EN_US.Yellow())));
		Register("green_book", GREEN_BOOK, List.of(EN_US.Book(EN_US.Green())));
		Register("blue_book", BLUE_BOOK, List.of(EN_US.Book(EN_US.Blue())));
		Register("purple_book", PURPLE_BOOK, List.of(EN_US.Book(EN_US.Purple())));
		Register("gray_book", GRAY_BOOK, List.of(EN_US.Book(EN_US.Gray())));
		//</editor-fold>
		//<editor-fold desc="Wood">
		Register("wood_bucket", WOOD_BUCKET, List.of(EN_US.Bucket(EN_US.Wood())));
		DispenserBlock.registerBehavior(WOOD_BUCKET, new BucketDispenserBehavior(WOOD_BUCKET_PROVIDER));
		Register("wood_water_bucket", WOOD_WATER_BUCKET, List.of(EN_US.Bucket(EN_US.Water(EN_US.Wood()))));
		CauldronBehavior.EMPTY_CAULDRON_BEHAVIOR.put(WOOD_WATER_BUCKET, (state, world, pos, player, hand, stack) -> BucketUtil.fillCauldron(world, pos, player, hand, stack,
				Blocks.WATER_CAULDRON.getDefaultState().with(LeveledCauldronBlock.LEVEL, 3),
				SoundEvents.ITEM_BUCKET_EMPTY, WOOD_BUCKET));
		CauldronBehavior.WATER_CAULDRON_BEHAVIOR.put(WOOD_BUCKET, (state, world, pos, player, hand, stack) -> CauldronBehavior.emptyCauldron(state, world, pos, player, hand, stack, new ItemStack(WOOD_WATER_BUCKET),
				(statex) -> statex.get(LeveledCauldronBlock.LEVEL) == 3, SoundEvents.ITEM_BUCKET_FILL));
		Register("wood_powder_snow_bucket", WOOD_POWDER_SNOW_BUCKET, List.of(EN_US.Bucket(EN_US.Snow(EN_US.Powder(EN_US.Wood())))));
		CauldronBehavior.EMPTY_CAULDRON_BEHAVIOR.put(WOOD_POWDER_SNOW_BUCKET, (state, world, pos, player, hand, stack) -> BucketUtil.fillCauldron(world, pos, player, hand, stack,
				Blocks.POWDER_SNOW_CAULDRON.getDefaultState().with(LeveledCauldronBlock.LEVEL, 3),
				SoundEvents.ITEM_BUCKET_EMPTY_POWDER_SNOW, WOOD_BUCKET));
		CauldronBehavior.POWDER_SNOW_CAULDRON_BEHAVIOR.put(WOOD_BUCKET, (state, world, pos, player, hand, stack) -> CauldronBehavior.emptyCauldron(state, world, pos, player, hand, stack, new ItemStack(WOOD_POWDER_SNOW_BUCKET),
				(statex) -> statex.get(LeveledCauldronBlock.LEVEL) == 3, SoundEvents.ITEM_BUCKET_FILL_POWDER_SNOW));
		Register("wood_blood_bucket", WOOD_BLOOD_BUCKET, List.of(EN_US.Bucket(EN_US.Blood(EN_US.Wood()))));
		CauldronBehavior.EMPTY_CAULDRON_BEHAVIOR.put(WOOD_BLOOD_BUCKET, BloodCauldronBlock.FillFromBucket(WOOD_BUCKET));
		Register("wood_mud_bucket", WOOD_MUD_BUCKET, List.of(EN_US.Bucket(EN_US.Mud(EN_US.Wood()))));
		CauldronBehavior.EMPTY_CAULDRON_BEHAVIOR.put(WOOD_MUD_BUCKET, MudCauldronBlock.FillFromBucket(WOOD_BUCKET));
		Register("wood_milk_bucket", WOOD_MILK_BUCKET, List.of(EN_US.Bucket(EN_US.Milk(EN_US.Wood()))));
		CauldronBehavior.EMPTY_CAULDRON_BEHAVIOR.put(WOOD_MILK_BUCKET, MilkCauldronBlock.FillFromBucket(WOOD_BUCKET));
		Register("wood_chocolate_milk_bucket", WOOD_CHOCOLATE_MILK_BUCKET, List.of(EN_US.Bucket(EN_US.Milk(EN_US.Chocolate(EN_US.Wood())))));
		Register("wood_coffee_milk_bucket", WOOD_COFFEE_MILK_BUCKET, List.of(EN_US.Bucket(EN_US.Milk(EN_US.Coffee(EN_US.Wood())))));
		Register("wood_strawberry_milk_bucket", WOOD_STRAWBERRY_MILK_BUCKET, List.of(EN_US.Bucket(EN_US.Milk(EN_US.Strawberry(EN_US.Wood())))));
		Register("wood_vanilla_milk_bucket", WOOD_VANILLA_MILK_BUCKET, List.of(EN_US.Bucket(EN_US.Milk(EN_US.Vanilla(EN_US.Wood())))));
		//</editor-fold>
		//<editor-fold desc="Wool">
		for (DyeColor color : DyeColor.values()) Register(color.getName() + "_wool_slab", WOOL_SLABS.get(color), List.of(EN_US.Slab(EN_US.Wool(EN_US.Color(color)))));
		Register("rainbow_wool", RAINBOW_WOOL, List.of(EN_US.Wool(EN_US.Rainbow())));
		Register("rainbow_wool_slab", RAINBOW_WOOL_SLAB, List.of(EN_US.Slab(EN_US.Wool(EN_US.Rainbow()))));
		Register("rainbow_carpet", RAINBOW_CARPET, List.of(EN_US.Carpet(EN_US.Rainbow())));
		Register("rainbow_bed", RAINBOW_BED, List.of(EN_US.Bed(EN_US.Rainbow())));
		Register("rainbow_sheep", RAINBOW_SHEEP_ENTITY, List.of(EN_US.Sheep(EN_US.Rainbow())));
		FabricDefaultAttributeRegistry.register(RAINBOW_SHEEP_ENTITY, RainbowSheepEntity.createSheepAttributes());
		SpawnRestrictionAccessor.callRegister(RAINBOW_SHEEP_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::isValidNaturalSpawn);
		//</editor-fold>
		//<editor-fold desc="Jolly Llama">
		Register("jolly_llama", JOLLY_LLAMA_ENTITY, List.of(EN_US.Llama(EN_US.Jolly())));
		FabricDefaultAttributeRegistry.register(JOLLY_LLAMA_ENTITY, LlamaEntity.createLlamaAttributes());
		SpawnRestrictionAccessor.callRegister(JOLLY_LLAMA_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::isValidNaturalSpawn);
		//</editor-fold>
		//<editor-fold desc="Chicken Variants">
		Register("fancy_chicken", FANCY_CHICKEN_ENTITY, List.of(EN_US.Chicken(EN_US.Fancy())));
		FabricDefaultAttributeRegistry.register(FANCY_CHICKEN_ENTITY, FancyChickenEntity.createChickenAttributes());
		SpawnRestrictionAccessor.callRegister(FANCY_CHICKEN_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::isValidNaturalSpawn);
		//</editor-fold>
		//<editor-fold desc="Cow Variants">
		Register("blue_mooshroom", BLUE_MOOSHROOM_ENTITY, List.of(EN_US.Mooshroom(EN_US.Blue())));
		FabricDefaultAttributeRegistry.register(BLUE_MOOSHROOM_ENTITY, BlueMooshroomEntity.createCowAttributes());
		SpawnRestrictionAccessor.callRegister(BLUE_MOOSHROOM_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, BlueMooshroomEntity::canSpawn);
		//Nether Mooshroom
		Register("nether_mooshroom", NETHER_MOOSHROOM_ENTITY, List.of(EN_US.Mooshroom(EN_US.Nether())));
		FabricDefaultAttributeRegistry.register(NETHER_MOOSHROOM_ENTITY, NetherMooshroomEntity.createCowAttributes());
		SpawnRestrictionAccessor.callRegister(NETHER_MOOSHROOM_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, NetherMooshroomEntity::canSpawn);
		//Flower Cows
		Register("moobloom", MOOBLOOM_ENTITY, List.of(EN_US.Moobloom()));
		FabricDefaultAttributeRegistry.register(MOOBLOOM_ENTITY, MoobloomEntity.createCowAttributes());
		SpawnRestrictionAccessor.callRegister(MOOBLOOM_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, FlowerCowEntity::canSpawn);
		Register("moolip", MOOLIP_ENTITY, List.of(EN_US.Moolip()));
		FabricDefaultAttributeRegistry.register(MOOLIP_ENTITY, MoolipEntity.createCowAttributes());
		SpawnRestrictionAccessor.callRegister(MOOLIP_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, FlowerCowEntity::canSpawn);
		Register("mooblossom", MOOBLOSSOM_ENTITY, List.of(EN_US.Mooblossom()));
		FabricDefaultAttributeRegistry.register(MOOBLOSSOM_ENTITY, MooblossomEntity.createCowAttributes());
		SpawnRestrictionAccessor.callRegister(MOOBLOSSOM_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, FlowerCowEntity::canSpawn);
		//Mooblossom Flowers
		Register("mooblossom_magenta_tulip", MOOBLOSSOM_MAGENTA_TULIP, List.of(EN_US.Tulip(EN_US.Magenta(EN_US.Mooblossom()))));
		Register("mooblossom_red_tulip", MOOBLOSSOM_RED_TULIP, List.of(EN_US.Tulip(EN_US.Red(EN_US.Mooblossom()))));
		Register("mooblossom_orange_tulip", MOOBLOSSOM_ORANGE_TULIP, List.of(EN_US.Tulip(EN_US.Orange(EN_US.Mooblossom()))));
		Register("mooblossom_white_tulip", MOOBLOSSOM_WHITE_TULIP, List.of(EN_US.Tulip(EN_US.White(EN_US.Mooblossom()))));
		Register("mooblossom_pink_tulip", MOOBLOSSOM_PINK_TULIP, List.of(EN_US.Tulip(EN_US.Pink(EN_US.Mooblossom()))));
		//</editor-fold>
		//<editor-fold desc="Moss">
		Register("moss_slab", MOSS_SLAB, List.of(EN_US.Slab(EN_US.Moss())));
		Register("moss_bed", MOSS_BED, List.of(EN_US.Bed(EN_US.Moss())));
		Register("mossy_sheep", MOSSY_SHEEP_ENTITY, List.of(EN_US.Sheep(EN_US.Mossy())));
		FabricDefaultAttributeRegistry.register(MOSSY_SHEEP_ENTITY, MossySheepEntity.createSheepAttributes());
		SpawnRestrictionAccessor.callRegister(MOSSY_SHEEP_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::isValidNaturalSpawn);
		BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.JUNGLE, BiomeKeys.BAMBOO_JUNGLE, BiomeKeys.JUNGLE_EDGE, BiomeKeys.JUNGLE_HILLS, BiomeKeys.BAMBOO_JUNGLE_HILLS, BiomeKeys.MODIFIED_JUNGLE, BiomeKeys.MODIFIED_JUNGLE_EDGE),
				SpawnGroup.CREATURE, MOSSY_SHEEP_ENTITY, 2, 2, 4);
		//</editor-fold>
		//<editor-fold desc="Glow Lichen">
		Register("glow_lichen_block", GLOW_LICHEN_BLOCK, List.of(EN_US.Block(EN_US.Lichen(EN_US.Glow()))));
		Register("glow_lichen_slab", GLOW_LICHEN_SLAB, List.of(EN_US.Slab(EN_US.Lichen(EN_US.Glow()))));
		Register("glow_lichen_carpet", GLOW_LICHEN_CARPET, List.of(EN_US.Carpet(EN_US.Lichen(EN_US.Glow()))));
		Register("glow_lichen_bed", GLOW_LICHEN_BED, List.of(EN_US.Bed(EN_US.Lichen(EN_US.Glow()))));
		//</editor-fold>
		//<editor-fold desc="Goat">
		Register("minecraft:set_instrument", SET_INSTRUMENT_LOOT_FUNCTION);
		Register("minecraft:goat_horn", GOAT_HORN, List.of(EN_US.Horn(EN_US.Goat())));
		//Extended
		Register("chevon", CHEVON, List.of(EN_US.Chevon()));
		Register("cooked_chevon", COOKED_CHEVON, List.of(EN_US.Chevon(EN_US.Cooked())));
		for (DyeColor color : DyeColor.values()) {
			Register(color.getName() + "_fleece", FLEECE.get(color), List.of(EN_US.Fleece(EN_US.Color(color))));
			Register(color.getName() + "_fleece_slab", FLEECE_SLABS.get(color), List.of(EN_US.Slab(EN_US.Fleece(EN_US.Color(color)))));
			Register(color.getName() + "_fleece_carpet", FLEECE_CARPETS.get(color), List.of(EN_US.Carpet(EN_US.Fleece(EN_US.Color(color)))));
		}
		Register("rainbow_fleece", RAINBOW_FLEECE, List.of(EN_US.Fleece(EN_US.Rainbow())));
		Register("rainbow_fleece_slab", RAINBOW_FLEECE_SLAB, List.of(EN_US.Slab(EN_US.Fleece(EN_US.Rainbow()))));
		Register("rainbow_fleece_carpet", RAINBOW_FLEECE_CARPET, List.of(EN_US.Carpet(EN_US.Fleece(EN_US.Rainbow()))));
		Register("fleece_helmet", FLEECE_HELMET, List.of(EN_US.Helmet(EN_US.Fleece())));
		Register("fleece_chestplate", FLEECE_CHESTPLATE, List.of(EN_US.Chestplate(EN_US.Fleece())));
		Register("fleece_leggings", FLEECE_LEGGINGS, List.of(EN_US.Leggings(EN_US.Fleece())));
		Register("fleece_boots", FLEECE_BOOTS, List.of(EN_US.Boots(EN_US.Fleece())));
		Register("fleece_horse_armor", FLEECE_HORSE_ARMOR, List.of(EN_US.Armor(EN_US.Horse(EN_US.Fleece()))));
		Register("goat_horn_salve", GOAT_HORN_SALVE, List.of(EN_US.Salve(EN_US.Horn(EN_US.Goat()))));
		//</editor-fold>
		//<editor-fold desc="Studded Leather">
		Register("studded_leather_helmet", STUDDED_LEATHER_HELMET, List.of(EN_US.Helmet(EN_US.Leather(EN_US.Studded()))));
		Register("studded_leather_chestplate", STUDDED_LEATHER_CHESTPLATE, List.of(EN_US.Chestplate(EN_US.Leather(EN_US.Studded()))));
		Register("studded_leather_leggings", STUDDED_LEATHER_LEGGINGS, List.of(EN_US.Leggings(EN_US.Leather(EN_US.Studded()))));
		Register("studded_leather_boots", STUDDED_LEATHER_BOOTS, List.of(EN_US.Boots(EN_US.Leather(EN_US.Studded()))));
		Register("studded_leather_horse_armor", STUDDED_LEATHER_HORSE_ARMOR, List.of(EN_US.Armor(EN_US.Horse(EN_US.Leather(EN_US.Studded())))));
		//</editor-fold>
		//<editor-fold desc="Charred Wood">
		Register("charred_log", CHARRED_LOG, List.of(EN_US.Log(EN_US.Charred())));
		Register("stripped_charred_log", STRIPPED_CHARRED_LOG, List.of(EN_US.Log(EN_US.Charred(EN_US.Stripped()))));
		StrippedBlockUtil.Register(CHARRED_LOG, STRIPPED_CHARRED_LOG);
		Register("charred_wood", CHARRED_WOOD, List.of(EN_US.Wood(EN_US.Charred())));
		Register("stripped_charred_wood", STRIPPED_CHARRED_WOOD, List.of(EN_US.Wood(EN_US.Charred(EN_US.Stripped()))));
		StrippedBlockUtil.Register(CHARRED_WOOD, STRIPPED_CHARRED_WOOD);
		Register("charred_planks", CHARRED_PLANKS, List.of(EN_US.Planks(EN_US.Charred())));
		Register("charred_stairs", CHARRED_STAIRS, List.of(EN_US.Stairs(EN_US.Charred())));
		Register("charred_slab", CHARRED_SLAB, List.of(EN_US.Slab(EN_US.Charred())));
		Register("charred_fence", CHARRED_FENCE, List.of(EN_US.Fence(EN_US.Charred())));
		Register("charred_fence_gate", CHARRED_FENCE_GATE, List.of(EN_US.Gate(EN_US.Fence(EN_US.Charred()))));
		Register("charred_door", CHARRED_DOOR, List.of(EN_US.Door(EN_US.Charred())));
		Register("charred_trapdoor", CHARRED_TRAPDOOR, List.of(EN_US.Trapdoor(EN_US.Charred())));
		Register("charred_pressure_plate", CHARRED_PRESSURE_PLATE, List.of(EN_US.Plate(EN_US.Pressure(EN_US.Charred()))));
		Register("charred_button", CHARRED_BUTTON, List.of(EN_US.Button(EN_US.Charred())));
		Register("charred", CHARRED_SIGN, List.of(EN_US._Sign(EN_US.Charred())));
		Register("charred_iron_hanging_sign", "charred_iron_wall_hanging_sign", CHARRED_IRON_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Charred(), EN_US.Chain(EN_US.Iron(EN_US.with())))));
		Register("charred_gold_hanging_sign", "charred_gold_wall_hanging_sign", CHARRED_GOLD_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Charred(), EN_US.Chain(EN_US.Gold(EN_US.with())))));
		Register("charred_copper_hanging_sign", "charred_copper_wall_hanging_sign", CHARRED_COPPER_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Charred(), EN_US.Chain(EN_US.Copper(EN_US.with())))));
		Register("charred_exposed_copper_hanging_sign", "charred_exposed_copper_wall_hanging_sign", CHARRED_EXPOSED_COPPER_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Charred(), EN_US.Chain(EN_US.Copper(EN_US.Exposed(EN_US.with()))))));
		Register("charred_weathered_copper_hanging_sign", "charred_weathered_copper_wall_hanging_sign", CHARRED_WEATHERED_COPPER_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Charred(), EN_US.Chain(EN_US.Copper(EN_US.Weathered(EN_US.with()))))));
		Register("charred_oxidized_copper_hanging_sign", "charred_oxidized_copper_wall_hanging_sign", CHARRED_OXIDIZED_COPPER_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Charred(), EN_US.Chain(EN_US.Copper(EN_US.Oxidized(EN_US.with()))))));
		Register("charred_netherite_hanging_sign", "charred_netherite_wall_hanging_sign", CHARRED_NETHERITE_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Charred(), EN_US.Chain(EN_US.Netherite(EN_US.with())))));
		Register("charred", CHARRED_BOAT, List.of(EN_US._Boat(EN_US.Charred())));
		Register("charred_beehive", CHARRED_BEEHIVE, List.of(EN_US.Beehive(EN_US.Charred())));
		Register("charred_bookshelf", CHARRED_BOOKSHELF, List.of(EN_US.Bookshelf(EN_US.Charred())));
		Register("charred_chiseled_bookshelf", CHARRED_CHISELED_BOOKSHELF, List.of(EN_US.ChiseledBookshelf(EN_US.Charred())));
		Register("charred_crafting_table", CHARRED_CRAFTING_TABLE, List.of(EN_US.Table(EN_US.Crafting(EN_US.Charred()))));
		Register("charred_ladder", CHARRED_LADDER, List.of(EN_US.Ladder(EN_US.Charred())));
		Register("charred_woodcutter", CHARRED_WOODCUTTER, List.of(EN_US.Woodcutter(EN_US.Charred())));
		Register("charred_barrel", CHARRED_BARREL, List.of(EN_US.Barrel(EN_US.Charred())));
		Register("charred_lectern", CHARRED_LECTERN, List.of(EN_US.Lectern(EN_US.Charred())));
		Register("charred_powder_keg", CHARRED_POWDER_KEG, List.of(EN_US.PowderKeg(EN_US.Charred())));
		Register("charred_log_slab", CHARRED_LOG_SLAB, List.of(EN_US.Slab(EN_US.Log(EN_US.Charred()))));
		Register("stripped_charred_log_slab", STRIPPED_CHARRED_LOG_SLAB, List.of(EN_US.Slab(EN_US.Log(EN_US.Stripped(EN_US.Charred())))));
		StrippedBlockUtil.Register(CHARRED_LOG_SLAB, STRIPPED_CHARRED_LOG_SLAB);
		Register("charred_wood_slab", CHARRED_WOOD_SLAB, List.of(EN_US.Slab(EN_US.Wood(EN_US.Charred()))));
		Register("stripped_charred_wood_slab", STRIPPED_CHARRED_WOOD_SLAB, List.of(EN_US.Slab(EN_US.Wood(EN_US.Stripped(EN_US.Charred())))));
		StrippedBlockUtil.Register(CHARRED_WOOD_SLAB, STRIPPED_CHARRED_WOOD_SLAB);
		Register("charred_hammer", CHARRED_HAMMER, List.of(EN_US.Hammer(EN_US.Charred())));
		Register("charred_torch", "charred_wall_torch", CHARRED_TORCH, List.of(EN_US._Torch(EN_US.Charred())));
		Register("charred_soul_torch", "charred_soul_wall_torch", CHARRED_SOUL_TORCH, List.of(EN_US.SoulTorch(EN_US.Charred())));
		Register("charred_ender_torch", "charred_ender_wall_torch", CHARRED_ENDER_TORCH, List.of(EN_US.EnderTorch(EN_US.Charred())));
		Register("underwater_charred_torch", "underwater_charred_wall_torch", UNDERWATER_CHARRED_TORCH, List.of(EN_US.Underwater_Torch(EN_US.Charred())));
		Register("charred_campfire", CHARRED_CAMPFIRE, List.of(EN_US.Campfire(EN_US.Charred())));
		Register("charred_soul_campfire", CHARRED_SOUL_CAMPFIRE, List.of(EN_US.Campfire(EN_US.Soul(EN_US.Charred()))));
		Register("charred_ender_campfire", CHARRED_ENDER_CAMPFIRE, List.of(EN_US.Campfire(EN_US.Ender(EN_US.Charred()))));
		//</editor-fold>
		Register("minecraft:music_disc_otherside", MUSIC_DISC_OTHERSIDE, List.of(EN_US.Disc(EN_US.Music())));
		Register("minecraft:music_disc_5", MUSIC_DISC_5, List.of(EN_US.Disc(EN_US.Music())));
		Register("minecraft:disc_fragment_5", DISC_FRAGMENT_5, List.of(EN_US.Fragment(EN_US.Disc())));
		Register("minecraft:music_disc_relic", MUSIC_DISC_RELIC, List.of(EN_US.Disc(EN_US.Music())));
		//<editor-fold desc="Mud">
		Register("minecraft:mud", MUD, List.of(EN_US.Mud()));
		Register("minecraft:packed_mud", PACKED_MUD, List.of(EN_US.Mud(EN_US.Packed())));
		Register("packed_mud_stairs", PACKED_MUD_STAIRS, List.of(EN_US.Stairs(EN_US.Mud(EN_US.Packed()))));
		Register("packed_mud_slab", PACKED_MUD_SLAB, List.of(EN_US.Slab(EN_US.Mud(EN_US.Packed()))));
		Register("packed_mud_wall", PACKED_MUD_WALL, List.of(EN_US.Wall(EN_US.Mud(EN_US.Packed()))));
		Register("minecraft:mud_bricks", MUD_BRICKS, List.of(EN_US.Bricks(EN_US.Mud())));
		Register("minecraft:mud_brick_stairs", MUD_BRICK_STAIRS, List.of(EN_US.Stairs(EN_US.Brick(EN_US.Mud()))));
		Register("minecraft:mud_brick_slab", MUD_BRICK_SLAB, List.of(EN_US.Slab(EN_US.Brick(EN_US.Mud()))));
		Register("minecraft:mud_brick_wall", MUD_BRICK_WALL, List.of(EN_US.Wall(EN_US.Brick(EN_US.Mud()))));
		Register("chiseled_mud_bricks", CHISELED_MUD_BRICKS, List.of(EN_US.Bricks(EN_US.Mud(EN_US.Chiseled()))));
		Register("smooth_chiseled_mud_bricks", SMOOTH_CHISELED_MUD_BRICKS, List.of(EN_US.Bricks(EN_US.Mud(EN_US.Chiseled(EN_US.Smooth())))));
		DispenserBlock.registerBehavior(Items.POTION, new ItemDispenserBehavior() {
			private final ItemDispenserBehavior fallback = new ItemDispenserBehavior();
			@Override
			public ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
				if (PotionUtil.getPotion(stack) != Potions.WATER) return this.fallback.dispense(pointer, stack);
				ServerWorld serverWorld = pointer.getWorld();
				BlockPos blockPos = pointer.getPos();
				BlockPos blockPos2 = pointer.getPos().offset(pointer.getBlockState().get(DispenserBlock.FACING));
				if (serverWorld.getBlockState(blockPos2).isIn(ModBlockTags.CONVERTIBLE_TO_MUD)) {
					if (!serverWorld.isClient) {
						for (int i = 0; i < 5; ++i) {
							serverWorld.spawnParticles(ParticleTypes.SPLASH, blockPos.getX() + serverWorld.random.nextDouble(), blockPos.getY() + 1, blockPos.getZ() + serverWorld.random.nextDouble(), 1, 0.0, 0.0, 0.0, 1.0);
						}
					}
					serverWorld.playSound(null, blockPos, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0f, 1.0f);
					serverWorld.emitGameEvent(null, GameEvent.FLUID_PLACE, blockPos);
					serverWorld.setBlockState(blockPos2, MUD.asBlock().getDefaultState());
					return new ItemStack(Items.GLASS_BOTTLE);
				}
				return this.fallback.dispense(pointer, stack);
			}
		});
		//</editor-fold>
		//<editor-fold desc="Mud (Liquid)">
		Register("mud_bottle", MUD_BOTTLE, List.of(EN_US.Bottle(EN_US.Mud())));
		JuicerBlock.JUICE_MAP.put(MUD::asItem, MUD_BOTTLE);
		Register("mud_bucket", MUD_BUCKET, List.of(EN_US.Bucket(EN_US.Mud())));
		CauldronBehavior.EMPTY_CAULDRON_BEHAVIOR.put(MUD_BUCKET, MudCauldronBlock.FillFromBucket(Items.BUCKET));
		FluidStorage.combinedItemApiProvider(MUD_BOTTLE).register(context -> new FullItemFluidStorage(context, bottle -> ItemVariant.of(Items.GLASS_BOTTLE), FluidVariant.of(STILL_MUD_FLUID), FluidConstants.BOTTLE));
		Register("mud_cauldron", MUD_CAULDRON, List.of(EN_US.Cauldron(EN_US.Mud())));
		Registry.register(Registry.FLUID, ModId.ID("still_mud"), STILL_MUD_FLUID);
		Registry.register(Registry.FLUID, ModId.ID("flowing_mud"), FLOWING_MUD_FLUID);
		Register("mud_fluid_block", MUD_FLUID_BLOCK, List.of(EN_US.Block(EN_US.Fluid(EN_US.Mud()))));
		//</editor-fold>
		RegisterBlood();
		//<editor-fold desc="Mangrove">
		Register("minecraft:mangrove_log", MANGROVE_LOG, List.of(EN_US.Log(EN_US.Mangrove())));
		Register("minecraft:stripped_mangrove_log", STRIPPED_MANGROVE_LOG, List.of(EN_US.Log(EN_US.Mangrove(EN_US.Stripped()))));
		StrippedBlockUtil.Register(MANGROVE_LOG, STRIPPED_MANGROVE_LOG);
		Register("minecraft:mangrove_wood", MANGROVE_WOOD, List.of(EN_US.Wood(EN_US.Mangrove())));
		Register("minecraft:stripped_mangrove_wood", STRIPPED_MANGROVE_WOOD, List.of(EN_US.Wood(EN_US.Mangrove(EN_US.Stripped()))));
		StrippedBlockUtil.Register(MANGROVE_WOOD, STRIPPED_MANGROVE_WOOD);
		Register("minecraft:mangrove_leaves", MANGROVE_LEAVES, List.of(EN_US.Leaves(EN_US.Mangrove())));
		Register("minecraft:mangrove_planks", MANGROVE_PLANKS, List.of(EN_US.Planks(EN_US.Mangrove())));
		Register("minecraft:mangrove_stairs", MANGROVE_STAIRS, List.of(EN_US.Stairs(EN_US.Mangrove())));
		Register("minecraft:mangrove_slab", MANGROVE_SLAB, List.of(EN_US.Slab(EN_US.Mangrove())));
		Register("minecraft:mangrove_fence", MANGROVE_FENCE, List.of(EN_US.Fence(EN_US.Mangrove())));
		Register("minecraft:mangrove_fence_gate", MANGROVE_FENCE_GATE, List.of(EN_US.Gate(EN_US.Fence(EN_US.Mangrove()))));
		Register("minecraft:mangrove_door", MANGROVE_DOOR, List.of(EN_US.Door(EN_US.Mangrove())));
		Register("minecraft:mangrove_trapdoor", MANGROVE_TRAPDOOR, List.of(EN_US.Trapdoor(EN_US.Mangrove())));
		Register("minecraft:mangrove_pressure_plate", MANGROVE_PRESSURE_PLATE, List.of(EN_US.Plate(EN_US.Pressure(EN_US.Mangrove()))));
		Register("minecraft:mangrove_button", MANGROVE_BUTTON, List.of(EN_US.Button(EN_US.Mangrove())));
		Register("minecraft:mangrove_roots", MANGROVE_ROOTS, List.of(EN_US.Roots(EN_US.Mangrove())));
		Register("minecraft:muddy_mangrove_roots", MUDDY_MANGROVE_ROOTS, List.of(EN_US.Muddy(EN_US.Roots(EN_US.Mangrove()))));
		Register("minecraft:mangrove", MANGROVE_SIGN, List.of(EN_US._Sign(EN_US.Mangrove())));
		Register("mangrove_iron_hanging_sign", "mangrove_iron_wall_hanging_sign", MANGROVE_IRON_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Mangrove(), EN_US.Chain(EN_US.Iron(EN_US.with())))));
		Register("mangrove_gold_hanging_sign", "mangrove_gold_wall_hanging_sign", MANGROVE_GOLD_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Mangrove(), EN_US.Chain(EN_US.Gold(EN_US.with())))));
		Register("mangrove_copper_hanging_sign", "mangrove_copper_wall_hanging_sign", MANGROVE_COPPER_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Mangrove(), EN_US.Chain(EN_US.Copper(EN_US.with())))));
		Register("mangrove_exposed_copper_hanging_sign", "mangrove_exposed_copper_wall_hanging_sign", MANGROVE_EXPOSED_COPPER_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Mangrove(), EN_US.Chain(EN_US.Copper(EN_US.Exposed(EN_US.with()))))));
		Register("mangrove_weathered_copper_hanging_sign", "mangrove_weathered_copper_wall_hanging_sign", MANGROVE_WEATHERED_COPPER_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Mangrove(), EN_US.Chain(EN_US.Copper(EN_US.Weathered(EN_US.with()))))));
		Register("mangrove_oxidized_copper_hanging_sign", "mangrove_oxidized_copper_wall_hanging_sign", MANGROVE_OXIDIZED_COPPER_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Mangrove(), EN_US.Chain(EN_US.Copper(EN_US.Oxidized(EN_US.with()))))));
		Register("mangrove_netherite_hanging_sign", "mangrove_netherite_wall_hanging_sign", MANGROVE_NETHERITE_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Mangrove(), EN_US.Chain(EN_US.Netherite(EN_US.with())))));
		Register("minecraft:mangrove", MANGROVE_BOAT, List.of(EN_US._Boat(EN_US.Mangrove())));
		Register("minecraft:mangrove_propagule", MANGROVE_PROPAGULE, List.of(EN_US._Potted(EN_US.Propagule(EN_US.Mangrove()))));
		//Extended
		Register("mangrove_beehive", MANGROVE_BEEHIVE, List.of(EN_US.Beehive(EN_US.Mangrove())));
		Register("mangrove_bookshelf", MANGROVE_BOOKSHELF, List.of(EN_US.Bookshelf(EN_US.Mangrove())));
		Register("mangrove_chiseled_bookshelf", MANGROVE_CHISELED_BOOKSHELF, List.of(EN_US.ChiseledBookshelf(EN_US.Mangrove())));
		Register("mangrove_crafting_table", MANGROVE_CRAFTING_TABLE, List.of(EN_US.Table(EN_US.Crafting(EN_US.Mangrove()))));
		Register("mangrove_ladder", MANGROVE_LADDER, List.of(EN_US.Ladder(EN_US.Mangrove())));
		Register("mangrove_woodcutter", MANGROVE_WOODCUTTER, List.of(EN_US.Woodcutter(EN_US.Mangrove())));
		Register("mangrove_barrel", MANGROVE_BARREL, List.of(EN_US.Barrel(EN_US.Mangrove())));
		Register("mangrove_lectern", MANGROVE_LECTERN, List.of(EN_US.Lectern(EN_US.Mangrove())));
		Register("mangrove_powder_keg", MANGROVE_POWDER_KEG, List.of(EN_US.PowderKeg(EN_US.Mangrove())));
		Register("mangrove_log_slab", MANGROVE_LOG_SLAB, List.of(EN_US.Slab(EN_US.Log(EN_US.Mangrove()))));
		Register("stripped_mangrove_log_slab", STRIPPED_MANGROVE_LOG_SLAB, List.of(EN_US.Slab(EN_US.Log(EN_US.Stripped(EN_US.Mangrove())))));
		StrippedBlockUtil.Register(MANGROVE_LOG_SLAB, STRIPPED_MANGROVE_LOG_SLAB);
		Register("mangrove_wood_slab", MANGROVE_WOOD_SLAB, List.of(EN_US.Slab(EN_US.Wood(EN_US.Mangrove()))));
		Register("stripped_mangrove_wood_slab", STRIPPED_MANGROVE_WOOD_SLAB, List.of(EN_US.Slab(EN_US.Wood(EN_US.Stripped(EN_US.Mangrove())))));
		StrippedBlockUtil.Register(MANGROVE_WOOD_SLAB, STRIPPED_MANGROVE_WOOD_SLAB);
		Register("mangrove_hammer", MANGROVE_HAMMER, List.of(EN_US.Hammer(EN_US.Mangrove())));
		Register("mangrove_torch", "mangrove_wall_torch", MANGROVE_TORCH, List.of(EN_US._Torch(EN_US.Mangrove())));
		Register("mangrove_soul_torch", "mangrove_soul_wall_torch", MANGROVE_SOUL_TORCH, List.of(EN_US.SoulTorch(EN_US.Mangrove())));
		Register("mangrove_ender_torch", "mangrove_ender_wall_torch", MANGROVE_ENDER_TORCH, List.of(EN_US.EnderTorch(EN_US.Mangrove())));
		Register("underwater_mangrove_torch", "underwater_mangrove_wall_torch", UNDERWATER_MANGROVE_TORCH, List.of(EN_US.Underwater_Torch(EN_US.Mangrove())));
		Register("mangrove_campfire", MANGROVE_CAMPFIRE, List.of(EN_US.Campfire(EN_US.Mangrove())));
		Register("mangrove_soul_campfire", MANGROVE_SOUL_CAMPFIRE, List.of(EN_US.Campfire(EN_US.Soul(EN_US.Mangrove()))));
		Register("mangrove_ender_campfire", MANGROVE_ENDER_CAMPFIRE, List.of(EN_US.Campfire(EN_US.Ender(EN_US.Mangrove()))));
		//</editor-fold>
		//<editor-fold desc="Cherry">
		Register("minecraft:cherry_log", CHERRY_LOG, List.of(EN_US.Log(EN_US.Cherry())));
		Register("minecraft:stripped_cherry_log", STRIPPED_CHERRY_LOG, List.of(EN_US.Log(EN_US.Cherry(EN_US.Stripped()))));
		StrippedBlockUtil.Register(CHERRY_LOG, STRIPPED_CHERRY_LOG);
		Register("minecraft:cherry_wood", CHERRY_WOOD, List.of(EN_US.Wood(EN_US.Cherry())));
		Register("minecraft:stripped_cherry_wood", STRIPPED_CHERRY_WOOD, List.of(EN_US.Wood(EN_US.Cherry(EN_US.Stripped()))));
		StrippedBlockUtil.Register(CHERRY_WOOD, STRIPPED_CHERRY_WOOD);
		Register("minecraft:cherry_leaves", CHERRY_LEAVES, List.of(EN_US.Leaves(EN_US.Cherry())));
		Register("minecraft:cherry_planks", CHERRY_PLANKS, List.of(EN_US.Planks(EN_US.Cherry())));
		Register("minecraft:cherry_stairs", CHERRY_STAIRS, List.of(EN_US.Stairs(EN_US.Cherry())));
		Register("minecraft:cherry_slab", CHERRY_SLAB, List.of(EN_US.Slab(EN_US.Cherry())));
		Register("minecraft:cherry_fence", CHERRY_FENCE, List.of(EN_US.Fence(EN_US.Cherry())));
		Register("minecraft:cherry_fence_gate", CHERRY_FENCE_GATE, List.of(EN_US.Gate(EN_US.Fence(EN_US.Cherry()))));
		Register("minecraft:cherry_door", CHERRY_DOOR, List.of(EN_US.Door(EN_US.Cherry())));
		Register("minecraft:cherry_trapdoor", CHERRY_TRAPDOOR, List.of(EN_US.Trapdoor(EN_US.Cherry())));
		Register("minecraft:cherry_pressure_plate", CHERRY_PRESSURE_PLATE, List.of(EN_US.Plate(EN_US.Pressure(EN_US.Cherry()))));
		Register("minecraft:cherry_button", CHERRY_BUTTON, List.of(EN_US.Button(EN_US.Cherry())));
		Register("minecraft:cherry", CHERRY_SIGN, List.of(EN_US._Sign(EN_US.Cherry())));
		Register("cherry_iron_hanging_sign", "cherry_iron_wall_hanging_sign", CHERRY_IRON_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Cherry(), EN_US.Chain(EN_US.Iron(EN_US.with())))));
		Register("cherry_gold_hanging_sign", "cherry_gold_wall_hanging_sign", CHERRY_GOLD_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Cherry(), EN_US.Chain(EN_US.Gold(EN_US.with())))));
		Register("cherry_copper_hanging_sign", "cherry_copper_wall_hanging_sign", CHERRY_COPPER_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Cherry(), EN_US.Chain(EN_US.Copper(EN_US.with())))));
		Register("cherry_exposed_copper_hanging_sign", "cherry_exposed_copper_wall_hanging_sign", CHERRY_EXPOSED_COPPER_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Cherry(), EN_US.Chain(EN_US.Copper(EN_US.Exposed(EN_US.with()))))));
		Register("cherry_weathered_copper_hanging_sign", "cherry_weathered_copper_wall_hanging_sign", CHERRY_WEATHERED_COPPER_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Cherry(), EN_US.Chain(EN_US.Copper(EN_US.Weathered(EN_US.with()))))));
		Register("cherry_oxidized_copper_hanging_sign", "cherry_oxidized_copper_wall_hanging_sign", CHERRY_OXIDIZED_COPPER_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Cherry(), EN_US.Chain(EN_US.Copper(EN_US.Oxidized(EN_US.with()))))));
		Register("cherry_netherite_hanging_sign", "cherry_netherite_wall_hanging_sign", CHERRY_NETHERITE_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Cherry(), EN_US.Chain(EN_US.Netherite(EN_US.with())))));
		Register("minecraft:cherry", CHERRY_BOAT, List.of(EN_US._Boat(EN_US.Cherry())));
		Register("minecraft:cherry_sapling", CHERRY_SAPLING, List.of(EN_US._Potted(EN_US.Sapling(EN_US.Cherry()))));
		//Extended
		Register("cherry_beehive", CHERRY_BEEHIVE, List.of(EN_US.Beehive(EN_US.Cherry())));
		Register("cherry_bookshelf", CHERRY_BOOKSHELF, List.of(EN_US.Bookshelf(EN_US.Cherry())));
		Register("cherry_chiseled_bookshelf", CHERRY_CHISELED_BOOKSHELF, List.of(EN_US.ChiseledBookshelf(EN_US.Cherry())));
		Register("cherry_crafting_table", CHERRY_CRAFTING_TABLE, List.of(EN_US.Table(EN_US.Crafting(EN_US.Cherry()))));
		Register("cherry_ladder", CHERRY_LADDER, List.of(EN_US.Ladder(EN_US.Cherry())));
		Register("cherry_woodcutter", CHERRY_WOODCUTTER, List.of(EN_US.Woodcutter(EN_US.Cherry())));
		Register("cherry_barrel", CHERRY_BARREL, List.of(EN_US.Barrel(EN_US.Cherry())));
		Register("cherry_lectern", CHERRY_LECTERN, List.of(EN_US.Lectern(EN_US.Cherry())));
		Register("cherry_powder_keg", CHERRY_POWDER_KEG, List.of(EN_US.PowderKeg(EN_US.Cherry())));
		Register("cherry_log_slab", CHERRY_LOG_SLAB, List.of(EN_US.Slab(EN_US.Log(EN_US.Cherry()))));
		Register("stripped_cherry_log_slab", STRIPPED_CHERRY_LOG_SLAB, List.of(EN_US.Slab(EN_US.Log(EN_US.Stripped(EN_US.Cherry())))));
		StrippedBlockUtil.Register(CHERRY_LOG_SLAB, STRIPPED_CHERRY_LOG_SLAB);
		Register("cherry_wood_slab", CHERRY_WOOD_SLAB, List.of(EN_US.Slab(EN_US.Wood(EN_US.Cherry()))));
		Register("stripped_cherry_wood_slab", STRIPPED_CHERRY_WOOD_SLAB, List.of(EN_US.Slab(EN_US.Wood(EN_US.Stripped(EN_US.Cherry())))));
		StrippedBlockUtil.Register(CHERRY_WOOD_SLAB, STRIPPED_CHERRY_WOOD_SLAB);
		Register("cherry_hammer", CHERRY_HAMMER, List.of(EN_US.Hammer(EN_US.Cherry())));
		Register("cherry_torch", "cherry_wall_torch", CHERRY_TORCH, List.of(EN_US._Torch(EN_US.Cherry())));
		Register("cherry_soul_torch", "cherry_soul_wall_torch", CHERRY_SOUL_TORCH, List.of(EN_US.SoulTorch(EN_US.Cherry())));
		Register("cherry_ender_torch", "cherry_ender_wall_torch", CHERRY_ENDER_TORCH, List.of(EN_US.EnderTorch(EN_US.Cherry())));
		Register("underwater_cherry_torch", "underwater_cherry_wall_torch", UNDERWATER_CHERRY_TORCH, List.of(EN_US.Underwater_Torch(EN_US.Cherry())));
		Register("cherry_campfire", CHERRY_CAMPFIRE, List.of(EN_US.Campfire(EN_US.Cherry())));
		Register("cherry_soul_campfire", CHERRY_SOUL_CAMPFIRE, List.of(EN_US.Campfire(EN_US.Soul(EN_US.Cherry()))));
		Register("cherry_ender_campfire", CHERRY_ENDER_CAMPFIRE, List.of(EN_US.Campfire(EN_US.Ender(EN_US.Cherry()))));
		//</editor-fold>
		//<editor-fold desc="Cassia">
		Register("cassia_log", CASSIA_LOG, List.of(EN_US.Log(EN_US.Cassia())));
		Register("stripped_cassia_log", STRIPPED_CASSIA_LOG, List.of(EN_US.Log(EN_US.Cassia(EN_US.Stripped()))));
		StrippedBlockUtil.Register(CASSIA_LOG, STRIPPED_CASSIA_LOG, StrippedBlockUtil.DropStack(new ItemStack(CINNAMON, 4)));
		Register("cassia_wood", CASSIA_WOOD, List.of(EN_US.Wood(EN_US.Cassia())));
		Register("stripped_cassia_wood", STRIPPED_CASSIA_WOOD, List.of(EN_US.Wood(EN_US.Cassia(EN_US.Stripped()))));
		StrippedBlockUtil.Register(CASSIA_WOOD, STRIPPED_CASSIA_WOOD, StrippedBlockUtil.DropStack(new ItemStack(CINNAMON, 6)));
		Register("cassia_leaves", CASSIA_LEAVES, List.of(EN_US.Leaves(EN_US.Cassia())));
		Register("flowering_cassia_leaves", FLOWERING_CASSIA_LEAVES, List.of(EN_US.Leaves(EN_US.Cassia(EN_US.Flowering()))));
		Register("cassia_planks", CASSIA_PLANKS, List.of(EN_US.Planks(EN_US.Cassia())));
		Register("cassia_stairs", CASSIA_STAIRS, List.of(EN_US.Stairs(EN_US.Cassia())));
		Register("cassia_slab", CASSIA_SLAB, List.of(EN_US.Slab(EN_US.Cassia())));
		Register("cassia_fence", CASSIA_FENCE, List.of(EN_US.Fence(EN_US.Cassia())));
		Register("cassia_fence_gate", CASSIA_FENCE_GATE, List.of(EN_US.Gate(EN_US.Fence(EN_US.Cassia()))));
		Register("cassia_door", CASSIA_DOOR, List.of(EN_US.Door(EN_US.Cassia())));
		Register("cassia_trapdoor", CASSIA_TRAPDOOR, List.of(EN_US.Trapdoor(EN_US.Cassia())));
		Register("cassia_pressure_plate", CASSIA_PRESSURE_PLATE, List.of(EN_US.Plate(EN_US.Pressure(EN_US.Cassia()))));
		Register("cassia_button", CASSIA_BUTTON, List.of(EN_US.Button(EN_US.Cassia())));
		Register("cassia", CASSIA_SIGN, List.of(EN_US._Sign(EN_US.Cassia())));
		Register("cassia_iron_hanging_sign", "cassia_iron_wall_hanging_sign", CASSIA_IRON_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Cassia(), EN_US.Chain(EN_US.Iron(EN_US.with())))));
		Register("cassia_gold_hanging_sign", "cassia_gold_wall_hanging_sign", CASSIA_GOLD_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Cassia(), EN_US.Chain(EN_US.Gold(EN_US.with())))));
		Register("cassia_copper_hanging_sign", "cassia_copper_wall_hanging_sign", CASSIA_COPPER_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Cassia(), EN_US.Chain(EN_US.Copper(EN_US.with())))));
		Register("cassia_exposed_copper_hanging_sign", "cassia_exposed_copper_wall_hanging_sign", CASSIA_EXPOSED_COPPER_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Cassia(), EN_US.Chain(EN_US.Copper(EN_US.Exposed(EN_US.with()))))));
		Register("cassia_weathered_copper_hanging_sign", "cassia_weathered_copper_wall_hanging_sign", CASSIA_WEATHERED_COPPER_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Cassia(), EN_US.Chain(EN_US.Copper(EN_US.Weathered(EN_US.with()))))));
		Register("cassia_oxidized_copper_hanging_sign", "cassia_oxidized_copper_wall_hanging_sign", CASSIA_OXIDIZED_COPPER_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Cassia(), EN_US.Chain(EN_US.Copper(EN_US.Oxidized(EN_US.with()))))));
		Register("cassia_netherite_hanging_sign", "cassia_netherite_wall_hanging_sign", CASSIA_NETHERITE_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Cassia(), EN_US.Chain(EN_US.Netherite(EN_US.with())))));
		Register("cassia", CASSIA_BOAT, List.of(EN_US._Boat(EN_US.Cassia())));
		Register("cassia_sapling", CASSIA_SAPLING, List.of(EN_US._Potted(EN_US.Sapling(EN_US.Cassia()))));
		Register("cassia_beehive", CASSIA_BEEHIVE, List.of(EN_US.Beehive(EN_US.Cassia())));
		Register("cassia_bookshelf", CASSIA_BOOKSHELF, List.of(EN_US.Bookshelf(EN_US.Cassia())));
		Register("cassia_chiseled_bookshelf", CASSIA_CHISELED_BOOKSHELF, List.of(EN_US.ChiseledBookshelf(EN_US.Cassia())));
		Register("cassia_crafting_table", CASSIA_CRAFTING_TABLE, List.of(EN_US.Table(EN_US.Crafting(EN_US.Cassia()))));
		Register("cassia_ladder", CASSIA_LADDER, List.of(EN_US.Ladder(EN_US.Cassia())));
		Register("cassia_woodcutter", CASSIA_WOODCUTTER, List.of(EN_US.Woodcutter(EN_US.Cassia())));
		Register("cassia_barrel", CASSIA_BARREL, List.of(EN_US.Barrel(EN_US.Cassia())));
		Register("cassia_lectern", CASSIA_LECTERN, List.of(EN_US.Lectern(EN_US.Cassia())));
		Register("cassia_powder_keg", CASSIA_POWDER_KEG, List.of(EN_US.PowderKeg(EN_US.Cassia())));
		Register("cassia_log_slab", CASSIA_LOG_SLAB, List.of(EN_US.Slab(EN_US.Log(EN_US.Cassia()))));
		Register("stripped_cassia_log_slab", STRIPPED_CASSIA_LOG_SLAB, List.of(EN_US.Slab(EN_US.Log(EN_US.Stripped(EN_US.Cassia())))));
		StrippedBlockUtil.Register(CASSIA_LOG_SLAB, STRIPPED_CASSIA_LOG_SLAB, StrippedBlockUtil.DropStack(new ItemStack(CINNAMON, 2)));
		Register("cassia_wood_slab", CASSIA_WOOD_SLAB, List.of(EN_US.Slab(EN_US.Wood(EN_US.Cassia()))));
		Register("stripped_cassia_wood_slab", STRIPPED_CASSIA_WOOD_SLAB, List.of(EN_US.Slab(EN_US.Wood(EN_US.Stripped(EN_US.Cassia())))));
		StrippedBlockUtil.Register(CASSIA_WOOD_SLAB, STRIPPED_CASSIA_WOOD_SLAB, StrippedBlockUtil.DropStack(new ItemStack(CINNAMON, 3)));
		Register("cassia_hammer", CASSIA_HAMMER, List.of(EN_US.Hammer(EN_US.Cassia())));
		Register("cassia_torch", "cassia_wall_torch", CASSIA_TORCH, List.of(EN_US._Torch(EN_US.Cassia())));
		Register("cassia_soul_torch", "cassia_soul_wall_torch", CASSIA_SOUL_TORCH, List.of(EN_US.SoulTorch(EN_US.Cassia())));
		Register("cassia_ender_torch", "cassia_ender_wall_torch", CASSIA_ENDER_TORCH, List.of(EN_US.EnderTorch(EN_US.Cassia())));
		Register("underwater_cassia_torch", "underwater_cassia_wall_torch", UNDERWATER_CASSIA_TORCH, List.of(EN_US.Underwater_Torch(EN_US.Cassia())));
		Register("cassia_campfire", CASSIA_CAMPFIRE, List.of(EN_US.Campfire(EN_US.Cassia())));
		Register("cassia_soul_campfire", CASSIA_SOUL_CAMPFIRE, List.of(EN_US.Campfire(EN_US.Soul(EN_US.Cassia()))));
		Register("cassia_ender_campfire", CASSIA_ENDER_CAMPFIRE, List.of(EN_US.Campfire(EN_US.Ender(EN_US.Cassia()))));
		//</editor-fold>
		//<editor-fold desc="Dogwood">
		Register("dogwood_log", DOGWOOD_LOG, List.of(EN_US.Log(EN_US.Dogwood())));
		Register("stripped_dogwood_log", STRIPPED_DOGWOOD_LOG, List.of(EN_US.Log(EN_US.Dogwood(EN_US.Stripped()))));
		StrippedBlockUtil.Register(DOGWOOD_LOG, STRIPPED_DOGWOOD_LOG);
		Register("dogwood_wood", DOGWOOD_WOOD, List.of(EN_US.Wood(EN_US.Dogwood())));
		Register("stripped_dogwood_wood", STRIPPED_DOGWOOD_WOOD, List.of(EN_US.Wood(EN_US.Dogwood(EN_US.Stripped()))));
		StrippedBlockUtil.Register(DOGWOOD_WOOD, STRIPPED_DOGWOOD_WOOD);
		Register("pink_dogwood_leaves", PINK_DOGWOOD_LEAVES, List.of(EN_US.Leaves(EN_US.Dogwood(EN_US.Pink()))));
		Register("pale_dogwood_leaves", PALE_DOGWOOD_LEAVES, List.of(EN_US.Leaves(EN_US.Dogwood(EN_US.Pale()))));
		Register("white_dogwood_leaves", WHITE_DOGWOOD_LEAVES, List.of(EN_US.Leaves(EN_US.Dogwood(EN_US.White()))));
		Register("dogwood_planks", DOGWOOD_PLANKS, List.of(EN_US.Planks(EN_US.Dogwood())));
		Register("dogwood_stairs", DOGWOOD_STAIRS, List.of(EN_US.Stairs(EN_US.Dogwood())));
		Register("dogwood_slab", DOGWOOD_SLAB, List.of(EN_US.Slab(EN_US.Dogwood())));
		Register("dogwood_fence", DOGWOOD_FENCE, List.of(EN_US.Fence(EN_US.Dogwood())));
		Register("dogwood_fence_gate", DOGWOOD_FENCE_GATE, List.of(EN_US.Gate(EN_US.Fence(EN_US.Dogwood()))));
		Register("dogwood_door", DOGWOOD_DOOR, List.of(EN_US.Door(EN_US.Dogwood())));
		Register("dogwood_trapdoor", DOGWOOD_TRAPDOOR, List.of(EN_US.Trapdoor(EN_US.Dogwood())));
		Register("dogwood_pressure_plate", DOGWOOD_PRESSURE_PLATE, List.of(EN_US.Plate(EN_US.Pressure(EN_US.Dogwood()))));
		Register("dogwood_button", DOGWOOD_BUTTON, List.of(EN_US.Button(EN_US.Dogwood())));
		Register("dogwood", DOGWOOD_SIGN, List.of(EN_US._Sign(EN_US.Dogwood())));
		Register("dogwood_iron_hanging_sign", "dogwood_iron_wall_hanging_sign", DOGWOOD_IRON_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Dogwood(), EN_US.Chain(EN_US.Iron(EN_US.with())))));
		Register("dogwood_gold_hanging_sign", "dogwood_gold_wall_hanging_sign", DOGWOOD_GOLD_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Dogwood(), EN_US.Chain(EN_US.Gold(EN_US.with())))));
		Register("dogwood_copper_hanging_sign", "dogwood_copper_wall_hanging_sign", DOGWOOD_COPPER_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Dogwood(), EN_US.Chain(EN_US.Copper(EN_US.with())))));
		Register("dogwood_exposed_copper_hanging_sign", "dogwood_exposed_copper_wall_hanging_sign", DOGWOOD_EXPOSED_COPPER_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Dogwood(), EN_US.Chain(EN_US.Copper(EN_US.Exposed(EN_US.with()))))));
		Register("dogwood_weathered_copper_hanging_sign", "dogwood_weathered_copper_wall_hanging_sign", DOGWOOD_WEATHERED_COPPER_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Dogwood(), EN_US.Chain(EN_US.Copper(EN_US.Weathered(EN_US.with()))))));
		Register("dogwood_oxidized_copper_hanging_sign", "dogwood_oxidized_copper_wall_hanging_sign", DOGWOOD_OXIDIZED_COPPER_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Dogwood(), EN_US.Chain(EN_US.Copper(EN_US.Oxidized(EN_US.with()))))));
		Register("dogwood_netherite_hanging_sign", "dogwood_netherite_wall_hanging_sign", DOGWOOD_NETHERITE_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Dogwood(), EN_US.Chain(EN_US.Netherite(EN_US.with())))));
		Register("dogwood", DOGWOOD_BOAT, List.of(EN_US._Boat(EN_US.Dogwood())));
		Register("dogwood_sapling", DOGWOOD_SAPLING, List.of(EN_US._Potted(EN_US.Sapling(EN_US.Dogwood()))));
		Register("dogwood_beehive", DOGWOOD_BEEHIVE, List.of(EN_US.Beehive(EN_US.Dogwood())));
		Register("dogwood_bookshelf", DOGWOOD_BOOKSHELF, List.of(EN_US.Bookshelf(EN_US.Dogwood())));
		Register("dogwood_chiseled_bookshelf", DOGWOOD_CHISELED_BOOKSHELF, List.of(EN_US.ChiseledBookshelf(EN_US.Dogwood())));
		Register("dogwood_crafting_table", DOGWOOD_CRAFTING_TABLE, List.of(EN_US.Table(EN_US.Crafting(EN_US.Dogwood()))));
		Register("dogwood_ladder", DOGWOOD_LADDER, List.of(EN_US.Ladder(EN_US.Dogwood())));
		Register("dogwood_woodcutter", DOGWOOD_WOODCUTTER, List.of(EN_US.Woodcutter(EN_US.Dogwood())));
		Register("dogwood_barrel", DOGWOOD_BARREL, List.of(EN_US.Barrel(EN_US.Dogwood())));
		Register("dogwood_lectern", DOGWOOD_LECTERN, List.of(EN_US.Lectern(EN_US.Dogwood())));
		Register("dogwood_powder_keg", DOGWOOD_POWDER_KEG, List.of(EN_US.PowderKeg(EN_US.Dogwood())));
		Register("dogwood_log_slab", DOGWOOD_LOG_SLAB, List.of(EN_US.Slab(EN_US.Log(EN_US.Dogwood()))));
		Register("stripped_dogwood_log_slab", STRIPPED_DOGWOOD_LOG_SLAB, List.of(EN_US.Slab(EN_US.Log(EN_US.Stripped(EN_US.Dogwood())))));
		StrippedBlockUtil.Register(DOGWOOD_LOG_SLAB, STRIPPED_DOGWOOD_LOG_SLAB);
		Register("dogwood_wood_slab", DOGWOOD_WOOD_SLAB, List.of(EN_US.Slab(EN_US.Wood(EN_US.Dogwood()))));
		Register("stripped_dogwood_wood_slab", STRIPPED_DOGWOOD_WOOD_SLAB, List.of(EN_US.Slab(EN_US.Wood(EN_US.Stripped(EN_US.Dogwood())))));
		StrippedBlockUtil.Register(DOGWOOD_WOOD_SLAB, STRIPPED_DOGWOOD_WOOD_SLAB);
		Register("dogwood_hammer", DOGWOOD_HAMMER, List.of(EN_US.Hammer(EN_US.Dogwood())));
		Register("dogwood_torch", "dogwood_wall_torch", DOGWOOD_TORCH, List.of(EN_US._Torch(EN_US.Dogwood())));
		Register("dogwood_soul_torch", "dogwood_soul_wall_torch", DOGWOOD_SOUL_TORCH, List.of(EN_US.SoulTorch(EN_US.Dogwood())));
		Register("dogwood_ender_torch", "dogwood_ender_wall_torch", DOGWOOD_ENDER_TORCH, List.of(EN_US.EnderTorch(EN_US.Dogwood())));
		Register("underwater_dogwood_torch", "underwater_dogwood_wall_torch", UNDERWATER_DOGWOOD_TORCH, List.of(EN_US.Underwater_Torch(EN_US.Dogwood())));
		Register("dogwood_campfire", DOGWOOD_CAMPFIRE, List.of(EN_US.Campfire(EN_US.Dogwood())));
		Register("dogwood_soul_campfire", DOGWOOD_SOUL_CAMPFIRE, List.of(EN_US.Campfire(EN_US.Soul(EN_US.Dogwood()))));
		Register("dogwood_ender_campfire", DOGWOOD_ENDER_CAMPFIRE, List.of(EN_US.Campfire(EN_US.Ender(EN_US.Dogwood()))));
		//</editor-fold>
		Register("tiki_torch", TIKI_TORCH, List.of(EN_US.Torch(EN_US.Tiki())));
		Register("tiki_soul_torch", TIKI_SOUL_TORCH, List.of(EN_US.Torch(EN_US.Soul(EN_US.Tiki()))));
		Register("tiki_ender_torch", TIKI_ENDER_TORCH, List.of(EN_US.Torch(EN_US.Ender(EN_US.Tiki()))));
		Register("tiki_torch_post", TIKI_TORCH_POST, List.of(EN_US.Post(EN_US.Torch(EN_US.Tiki()))));
		ModBambooRegistry.RegisterBamboo();
		Register("javelin", JAVELIN, List.of(EN_US.Javelin()));
		Register("javelin", JAVELIN_ENTITY, List.of(EN_US.Javelin()));
		Register("amethyst_trident", AMETHYST_TRIDENT, List.of(EN_US.Trident(EN_US.Amethyst())));
		Register("amethyst_trident", AMETHYST_TRIDENT_ENTITY, List.of(EN_US.Trident(EN_US.Amethyst())));
		//Mobs
		ModActivities.Initialize();
		ModDataHandlers.Initialize();
		//<editor-fold desc="Frogs">
		Register("minecraft:ochre_froglight", OCHRE_FROGLIGHT, List.of(EN_US.Froglight(EN_US.Ochre())));
		Register("minecraft:verdant_froglight", VERDANT_FROGLIGHT, List.of(EN_US.Froglight(EN_US.Verdant())));
		Register("minecraft:pearlescent_froglight", PEARLESCENT_FROGLIGHT, List.of(EN_US.Froglight(EN_US.Pearlescent())));
		FROG_TEMPTATIONS_SENSOR.Initialize();
		FROG_ATTACKABLES_SENSOR.Initialize();
		IS_IN_WATER_SENSOR.Initialize();
		Register("minecraft:frog", FROG_ENTITY, List.of(EN_US.Frog()));
		FabricDefaultAttributeRegistry.register(FROG_ENTITY, FrogEntity.createFrogAttributes());
		SpawnRestrictionAccessor.callRegister(FROG_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, FrogEntity::canSpawn);
		Register("minecraft:frogspawn", FROGSPAWN, List.of(EN_US.Frogspawn()));
		Register("minecraft:tadpole", TADPOLE_ENTITY, List.of(EN_US.Tadpole()));
		FabricDefaultAttributeRegistry.register(TADPOLE_ENTITY, TadpoleEntity.createTadpoleAttributes());
		SpawnRestrictionAccessor.callRegister(TADPOLE_ENTITY, SpawnRestriction.Location.IN_WATER, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, TadpoleEntity::canSpawn);
		Register("minecraft:tadpole_bucket", TADPOLE_BUCKET, List.of(EN_US.Tadpole(EN_US.BucketOf())));
		//</editor-fold>
		//<editor-fold desc="Sculk">
		Register("minecraft:sculk", SCULK, List.of(EN_US.Sculk()));
		Register("minecraft:sculk_vein", SCULK_VEIN, List.of(EN_US.SculkVein()));
		Register("minecraft:calibrated_sculk_sensor", CALIBRATED_SCULK_SENSOR, CALIBRATED_SCULK_SENSOR_ENTITY, List.of(EN_US.SculkSensor(EN_US.Calibrated())));
		Register("minecraft:sculk_catalyst", SCULK_CATALYST, SCULK_CATALYST_ENTITY, List.of(EN_US.SculkCatalyst()));
		Register("minecraft:sculk_shrieker", SCULK_SHRIEKER, SCULK_SHRIEKER_ENTITY, List.of(EN_US.SculkShrieker()));
		//</editor-fold>
		//<editor-fold desc="Wardens">
		WARDEN_ENTITY_SENSOR.Initialize();
		Register("minecraft:warden", WARDEN_ENTITY, List.of(EN_US.Warden()));
		FabricDefaultAttributeRegistry.register(WARDEN_ENTITY, WardenEntity.addAttributes());
		SpawnRestrictionAccessor.callRegister(WARDEN_ENTITY, SpawnRestriction.Location.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WardenEntity::canMobSpawn);
		//</editor-fold>
		Register("minecraft:recovery_compass", RECOVERY_COMPASS, List.of(EN_US.Compass(EN_US.Recovery())));
		//<editor-fold desc="Echo">
		Register("minecraft:echo_shard", ECHO_SHARD, List.of(EN_US.Shard(EN_US.Echo())));
		//Extended
		Register("echo_block", ECHO_BLOCK, List.of(EN_US.Block(EN_US.Echo())));
		Register("echo_stairs", ECHO_STAIRS, List.of(EN_US.Stairs(EN_US.Echo())));
		Register("echo_slab", ECHO_SLAB, List.of(EN_US.Slab(EN_US.Echo())));
		Register("echo_wall", ECHO_WALL, List.of(EN_US.Wall(EN_US.Echo())));
		Register("echo_bricks", ECHO_BRICKS, List.of(EN_US.Bricks(EN_US.Echo())));
		Register("echo_brick_stairs", ECHO_BRICK_STAIRS, List.of(EN_US.Stairs(EN_US.Brick(EN_US.Echo()))));
		Register("echo_brick_slab", ECHO_BRICK_SLAB, List.of(EN_US.Slab(EN_US.Brick(EN_US.Echo()))));
		Register("echo_brick_wall", ECHO_BRICK_WALL, List.of(EN_US.Wall(EN_US.Brick(EN_US.Echo()))));
		Register("echo_crystal_block", ECHO_CRYSTAL_BLOCK, List.of(EN_US.Block(EN_US.Crystal(EN_US.Echo()))));
		Register("echo_crystal_stairs", ECHO_CRYSTAL_STAIRS, List.of(EN_US.Stairs(EN_US.Crystal(EN_US.Echo()))));
		Register("echo_crystal_slab", ECHO_CRYSTAL_SLAB, List.of(EN_US.Slab(EN_US.Crystal(EN_US.Echo()))));
		Register("echo_crystal_wall", ECHO_CRYSTAL_WALL, List.of(EN_US.Wall(EN_US.Crystal(EN_US.Echo()))));
		Register("budding_echo", BUDDING_ECHO, List.of(EN_US.Echo(EN_US.Budding())));
		Register("echo_cluster", ECHO_CLUSTER, List.of(EN_US.Cluster(EN_US.Echo())));
		Register("large_echo_bud", LARGE_ECHO_BUD, List.of(EN_US.Bud(EN_US.Echo(EN_US.Large()))));
		Register("medium_echo_bud", MEDIUM_ECHO_BUD, List.of(EN_US.Bud(EN_US.Echo(EN_US.Medium()))));
		Register("small_echo_bud", SMALL_ECHO_BUD, List.of(EN_US.Bud(EN_US.Echo(EN_US.Small()))));
		Register("echo_axe", ECHO_AXE, List.of(EN_US.Axe(EN_US.Echo())));
		Register("echo_hoe", ECHO_HOE, List.of(EN_US.Hoe(EN_US.Echo())));
		Register("echo_pickaxe", ECHO_PICKAXE, List.of(EN_US.Pickaxe(EN_US.Echo())));
		Register("echo_shovel", ECHO_SHOVEL, List.of(EN_US.Shovel(EN_US.Echo())));
		Register("echo_sword", ECHO_SWORD, List.of(EN_US.Sword(EN_US.Echo())));
		Register("echo_knife", ECHO_KNIFE, List.of(EN_US.Knife(EN_US.Echo())));
		Register("echo_hammer", ECHO_HAMMER, List.of(EN_US.Hammer(EN_US.Echo())));
		//</editor-fold>
		//<editor-fold desc="Sculk (Extended)">
		Register("sculk_stone", SCULK_STONE, List.of(EN_US.Stone(EN_US.Sculk())));
		Register("sculk_stone_stairs", SCULK_STONE_STAIRS, List.of(EN_US.Stairs(EN_US.Stone(EN_US.Sculk()))));
		Register("sculk_stone_slab", SCULK_STONE_SLAB, List.of(EN_US.Slab(EN_US.Stone(EN_US.Sculk()))));
		Register("sculk_stone_wall", SCULK_STONE_WALL, List.of(EN_US.Wall(EN_US.Stone(EN_US.Sculk()))));
		Register("cobbled_sculk_stone", COBBLED_SCULK_STONE, List.of(EN_US.Stone(EN_US.Sculk(EN_US.Cobbled()))));
		Register("cobbled_sculk_stone_stairs", COBBLED_SCULK_STONE_STAIRS, List.of(EN_US.Stairs(EN_US.Stone(EN_US.Sculk(EN_US.Cobbled())))));
		Register("cobbled_sculk_stone_slab", COBBLED_SCULK_STONE_SLAB, List.of(EN_US.Slab(EN_US.Stone(EN_US.Sculk(EN_US.Cobbled())))));
		Register("cobbled_sculk_stone_wall", COBBLED_SCULK_STONE_WALL, List.of(EN_US.Wall(EN_US.Stone(EN_US.Sculk(EN_US.Cobbled())))));
		Register("sculk_stone_bricks", SCULK_STONE_BRICKS, List.of(EN_US.Bricks(EN_US.Stone(EN_US.Sculk()))));
		Register("sculk_stone_brick_stairs", SCULK_STONE_BRICK_STAIRS, List.of(EN_US.Stairs(EN_US.Brick(EN_US.Stone(EN_US.Sculk())))));
		Register("sculk_stone_brick_slab", SCULK_STONE_BRICK_SLAB, List.of(EN_US.Slab(EN_US.Brick(EN_US.Stone(EN_US.Sculk())))));
		Register("sculk_stone_brick_wall", SCULK_STONE_BRICK_WALL, List.of(EN_US.Wall(EN_US.Brick(EN_US.Stone(EN_US.Sculk())))));
		Register("chiseled_sculk_stone_bricks", CHISELED_SCULK_STONE_BRICKS, List.of(EN_US.Bricks(EN_US.Stone(EN_US.Sculk(EN_US.Chiseled())))));
		Register("smooth_chiseled_sculk_stone_bricks", SMOOTH_CHISELED_SCULK_STONE_BRICKS, List.of(EN_US.Bricks(EN_US.Stone(EN_US.Sculk(EN_US.Chiseled(EN_US.Smooth()))))));
		Register("sculk_stone_tiles", SCULK_STONE_TILES, List.of(EN_US.Tiles(EN_US.Stone(EN_US.Sculk()))));
		Register("sculk_stone_tile_stairs", SCULK_STONE_TILE_STAIRS, List.of(EN_US.Stairs(EN_US.Tile(EN_US.Stone(EN_US.Sculk())))));
		Register("sculk_stone_tile_slab", SCULK_STONE_TILE_SLAB, List.of(EN_US.Slab(EN_US.Tile(EN_US.Stone(EN_US.Sculk())))));
		Register("sculk_stone_tile_wall", SCULK_STONE_TILE_WALL, List.of(EN_US.Wall(EN_US.Tile(EN_US.Stone(EN_US.Sculk())))));
		//Turf
		Register("chum", CHUM, List.of(EN_US.Chum()));
		CompostingChanceRegistry.INSTANCE.add(CHUM, 0.75f);
		Register("andesite_sculk_turf", ANDESITE_SCULK_TURF, List.of(EN_US.Turf(EN_US.Sculk(EN_US.Andesite()))));
		Register("basalt_sculk_turf", BASALT_SCULK_TURF, List.of(EN_US.Turf(EN_US.Sculk(EN_US.Basalt()))));
		Register("blackstone_sculk_turf", BLACKSTONE_SCULK_TURF, List.of(EN_US.Turf(EN_US.Sculk(EN_US.Blackstone()))));
		Register("calcite_sculk_turf", CALCITE_SCULK_TURF, List.of(EN_US.Turf(EN_US.Sculk(EN_US.Calcite()))));
		Register("deepslate_sculk_turf", DEEPSLATE_SCULK_TURF, List.of(EN_US.Turf(EN_US.Sculk(EN_US.Deepslate()))));
		Register("diorite_sculk_turf", DIORITE_SCULK_TURF, List.of(EN_US.Turf(EN_US.Sculk(EN_US.Diorite()))));
		Register("dripstone_sculk_turf", DRIPSTONE_SCULK_TURF, List.of(EN_US.Turf(EN_US.Sculk(EN_US.Dripstone()))));
		Register("end_shale_sculk_turf", END_SHALE_SCULK_TURF, List.of(EN_US.Turf(EN_US.Sculk(EN_US.Shale(EN_US.End())))));
		Register("end_stone_sculk_turf", END_STONE_SCULK_TURF, List.of(EN_US.Turf(EN_US.Sculk(EN_US.Stone(EN_US.End())))));
		Register("end_rock_sculk_turf", END_ROCK_SCULK_TURF, List.of(EN_US.Turf(EN_US.Sculk(EN_US.Rock(EN_US.End())))));
		Register("end_rock_shale_sculk_turf", END_ROCK_SHALE_SCULK_TURF, List.of(EN_US.Turf(EN_US.Sculk(EN_US.Shale(EN_US.Rock(EN_US.End()))))));
		Register("end_shale_rock_sculk_turf", END_SHALE_ROCK_SCULK_TURF, List.of(EN_US.Turf(EN_US.Sculk(EN_US.Rock(EN_US.Shale(EN_US.End()))))));
		Register("granite_sculk_turf", GRANITE_SCULK_TURF, List.of(EN_US.Turf(EN_US.Sculk(EN_US.Granite()))));
		Register("netherrack_sculk_turf", NETHERRACK_SCULK_TURF, List.of(EN_US.Turf(EN_US.Sculk(EN_US.Netherrack()))));
		Register("red_sandstone_sculk_turf", RED_SANDSTONE_SCULK_TURF, List.of(EN_US.Turf(EN_US.Sculk(EN_US.Sandstone(EN_US.Red())))));
		Register("sandstone_sculk_turf", SANDSTONE_SCULK_TURF, List.of(EN_US.Turf(EN_US.Sculk(EN_US.Sandstone()))));
		Register("shale_sculk_turf", SHALE_SCULK_TURF, List.of(EN_US.Turf(EN_US.Sculk(EN_US.Shale()))));
		Register("smooth_basalt_sculk_turf", SMOOTH_BASALT_SCULK_TURF, List.of(EN_US.Turf(EN_US.Sculk(EN_US.Basalt(EN_US.Smooth())))));
		Register("stone_sculk_turf", STONE_SCULK_TURF, List.of(EN_US.Turf(EN_US.Sculk(EN_US.Stone()))));
		Register("tuff_sculk_turf", TUFF_SCULK_TURF, List.of(EN_US.Turf(EN_US.Sculk(EN_US.Tuff()))));
		//</editor-fold>
		//<editor-fold desc="Camel">
		CAMEL_TEMPTATIONS_SENSOR.Initialize();
		Register("minecraft:camel", CAMEL_ENTITY, List.of(EN_US.Camel()));
		FabricDefaultAttributeRegistry.register(CAMEL_ENTITY, CamelEntity.createCamelAttributes());
		//</editor-fold>
		Register("broom", BROOM, List.of(EN_US.Broom()));
		//<editor-fold desc="Sniffer">
		Register("minecraft:sniffer", SNIFFER_ENTITY, List.of(EN_US.Sniffer()));
		FabricDefaultAttributeRegistry.register(SNIFFER_ENTITY, SnifferEntity.createSnifferAttributes());
		Register("minecraft:sniffer_egg", SNIFFER_EGG, List.of(EN_US.Egg(EN_US.Sniffer())));
		//</editor-fold>
		RegisterBrushableBlocks();
		Register("minecraft:chiseled_bookshelf", CHISELED_BOOKSHELF_ENTITY);
		//<editor-fold desc="Pottery Shards">
		Register("minecraft:angler_pottery_sherd", ANGLER_POTTERY_SHERD, List.of(EN_US.Sherd(EN_US.Pottery(EN_US.Angler()))));
		Register("minecraft:archer_pottery_sherd", ARCHER_POTTERY_SHERD, List.of(EN_US.Sherd(EN_US.Pottery(EN_US.Archer()))));
		Register("minecraft:arms_up_pottery_sherd", ARMS_UP_POTTERY_SHERD, List.of(EN_US.Sherd(EN_US.Pottery(EN_US.ArmsUp()))));
		Register("minecraft:blade_pottery_sherd", BLADE_POTTERY_SHERD, List.of(EN_US.Sherd(EN_US.Pottery(EN_US.Blade()))));
		Register("minecraft:brewer_pottery_sherd", BREWER_POTTERY_SHERD, List.of(EN_US.Sherd(EN_US.Pottery(EN_US.Brewer()))));
		Register("minecraft:burn_pottery_sherd", BURN_POTTERY_SHERD, List.of(EN_US.Sherd(EN_US.Pottery(EN_US.Burn()))));
		Register("minecraft:danger_pottery_sherd", DANGER_POTTERY_SHERD, List.of(EN_US.Sherd(EN_US.Pottery(EN_US.Danger()))));
		Register("minecraft:explorer_pottery_sherd", EXPLORER_POTTERY_SHERD, List.of(EN_US.Sherd(EN_US.Pottery(EN_US.Explorer()))));
		Register("minecraft:friend_pottery_sherd", FRIEND_POTTERY_SHERD, List.of(EN_US.Sherd(EN_US.Pottery(EN_US.Friend()))));
		Register("minecraft:heart_pottery_sherd", HEART_POTTERY_SHERD, List.of(EN_US.Sherd(EN_US.Pottery(EN_US.Heart()))));
		Register("minecraft:heartbreak_pottery_sherd", HEARTBREAK_POTTERY_SHERD, List.of(EN_US.Sherd(EN_US.Pottery(EN_US.Heartbreak()))));
		Register("minecraft:howl_pottery_sherd", HOWL_POTTERY_SHERD, List.of(EN_US.Sherd(EN_US.Pottery(EN_US.Howl()))));
		Register("minecraft:miner_pottery_sherd", MINER_POTTERY_SHERD, List.of(EN_US.Sherd(EN_US.Pottery(EN_US.Miner()))));
		Register("minecraft:mourner_pottery_sherd", MOURNER_POTTERY_SHERD, List.of(EN_US.Sherd(EN_US.Pottery(EN_US.Mourner()))));
		Register("minecraft:plenty_pottery_sherd", PLENTY_POTTERY_SHERD, List.of(EN_US.Sherd(EN_US.Pottery(EN_US.Plenty()))));
		Register("minecraft:prize_pottery_sherd", PRIZE_POTTERY_SHERD, List.of(EN_US.Sherd(EN_US.Pottery(EN_US.Prize()))));
		Register("minecraft:sheaf_pottery_sherd", SHEAF_POTTERY_SHERD, List.of(EN_US.Sherd(EN_US.Pottery(EN_US.Sheaf()))));
		Register("minecraft:shelter_pottery_sherd", SHELTER_POTTERY_SHERD, List.of(EN_US.Sherd(EN_US.Pottery(EN_US.Shelter()))));
		Register("minecraft:skull_pottery_sherd", SKULL_POTTERY_SHERD, List.of(EN_US.Sherd(EN_US.Pottery(EN_US.Skull()))));
		Register("minecraft:snort_pottery_sherd", SNORT_POTTERY_SHERD, List.of(EN_US.Sherd(EN_US.Pottery(EN_US.Snort()))));
		//</editor-fold>
		//<editor-fold desc="Jumping Spider">
		Register("jumping_spider", JUMPING_SPIDER_ENTITY, List.of(EN_US.Spider(EN_US.Jumping())));
		BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.DESERT,
						BiomeKeys.JUNGLE, BiomeKeys.BAMBOO_JUNGLE, BiomeKeys.JUNGLE_EDGE, BiomeKeys.JUNGLE_HILLS, BiomeKeys.BAMBOO_JUNGLE_HILLS, BiomeKeys.MODIFIED_JUNGLE, BiomeKeys.MODIFIED_JUNGLE_EDGE,
						BiomeKeys.BADLANDS, BiomeKeys.ERODED_BADLANDS, BiomeKeys.BADLANDS_PLATEAU, BiomeKeys.WOODED_BADLANDS_PLATEAU, BiomeKeys.MODIFIED_BADLANDS_PLATEAU, BiomeKeys.MODIFIED_WOODED_BADLANDS_PLATEAU),
				SpawnGroup.MONSTER, JUMPING_SPIDER_ENTITY, 40, 1, 2);
		FabricDefaultAttributeRegistry.register(JUMPING_SPIDER_ENTITY, JumpingSpiderEntity.createJumpingSpiderAttributes());
		SpawnRestrictionAccessor.callRegister(JUMPING_SPIDER_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HostileEntity::canSpawnInDark);
		//</editor-fold>
		//<editor-fold desc="Hedgehog">
		Register("hedgehog", HEDGEHOG_ENTITY, List.of(EN_US.Hedgehog()));
		BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.GIANT_TREE_TAIGA, BiomeKeys.GIANT_TREE_TAIGA_HILLS, BiomeKeys.GIANT_SPRUCE_TAIGA, BiomeKeys.GIANT_SPRUCE_TAIGA_HILLS, BiomeKeys.BAMBOO_JUNGLE, BiomeKeys.JUNGLE_EDGE, BiomeKeys.JUNGLE_HILLS, BiomeKeys.BAMBOO_JUNGLE_HILLS, BiomeKeys.MODIFIED_JUNGLE, BiomeKeys.MODIFIED_JUNGLE_EDGE),
				SpawnGroup.MONSTER, HEDGEHOG_ENTITY, 18, 1, 4);
		FabricDefaultAttributeRegistry.register(HEDGEHOG_ENTITY, HedgehogEntity.createHedgehogAttributes());
		SpawnRestrictionAccessor.callRegister(HEDGEHOG_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::isValidNaturalSpawn);
		//</editor-fold>
		//<editor-fold desc="Racoon">
		Register("raccoon", RACCOON_ENTITY, List.of(EN_US.Raccoon()));
		FabricDefaultAttributeRegistry.register(RACCOON_ENTITY, RaccoonEntity.createRaccoonAttributes());
		SpawnRestrictionAccessor.callRegister(RACCOON_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::isValidNaturalSpawn);
		//</editor-fold>
		//<editor-fold desc="Red Panda">
		Register("red_panda", RED_PANDA_ENTITY, List.of(EN_US.Panda(EN_US.Red())));
		FabricDefaultAttributeRegistry.register(RED_PANDA_ENTITY, RedPandaEntity.createRedPandaAttributes());
		SpawnRestrictionAccessor.callRegister(RED_PANDA_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::isValidNaturalSpawn);
		//</editor-fold>
		RegisterFlowers();
		Register("firing_charge", FIRING_CHARGE, List.of(EN_US.Charge(EN_US.Firing())));
		Register("minecraft:trial_key", TRIAL_KEY, List.of(EN_US.Key(EN_US.Trial())));
		Register("minecraft:netherite_upgrade_smithing_template", NETHERITE_UPGRADE_SMITHING_TEMPLATE, List.of(EN_US.Template(EN_US.Smithing(EN_US.Upgrade(EN_US.Netherite())))));
		Register("trimming", TRIMMING_RECIPE_SERIALIZER);
		Register("trimming_table", TRIMMING_TABLE, List.of(EN_US.Table(EN_US.Trimming())));
		for (ArmorTrimPattern pattern : ArmorTrimPattern.values()) Register(pattern.getItemPath(), pattern.asItem(), pattern.getTranslations());
		//<editor-fold desc="Piranhas">
		Register("piranha", PIRANHA_ENTITY, List.of(EN_US.Piranha()));
		FabricDefaultAttributeRegistry.register(PIRANHA_ENTITY, PiranhaEntity.createPiranhaAttributes());
		BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.RIVER, BiomeKeys.JUNGLE, BiomeKeys.BAMBOO_JUNGLE, BiomeKeys.JUNGLE_EDGE, BiomeKeys.JUNGLE_HILLS, BiomeKeys.BAMBOO_JUNGLE_HILLS, BiomeKeys.MODIFIED_JUNGLE, BiomeKeys.MODIFIED_JUNGLE_EDGE, MANGROVE_SWAMP),
				SpawnGroup.WATER_AMBIENT, PIRANHA_ENTITY, 8, 1, 5);
		SpawnRestrictionAccessor.callRegister(PIRANHA_ENTITY, SpawnRestriction.Location.IN_WATER, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, FishEntity::canSpawn);
		Register("piranha", PIRANHA, List.of(EN_US.Piranha()));
		Register("cooked_piranha", COOKED_PIRANHA, List.of(EN_US.Piranha(EN_US.Cooked())));
		//</editor-fold>
		RegisterMobStorage();
		RegisterInfestedBlocks();
		RegisterMobSkullsAndRagdolls();
		RegisterSummoningArrows();
		RegisterSpawnEggs();
		ModBannerPatterns.RegisterBannerPatterns();
		ModPaintingMotives.RegisterPaintings();
		ModStatusEffects.RegisterEffects();
		ModEnchantments.RegisterEnchantments();
		RegisterOriginsPowers();
		RegisterCommands();
		ModGameEvent.RegisterAll();
		ModMemoryModules.Initialize();
		ModPositionSourceTypes.Initialize();
		IdentifiedSounds.RegisterAll();
		ModCriteria.Register();
		ModGameRules.Initialize();
		ModLootTables.Initialize();
		ModParticleTypes.RegisterParticles();
		RegisterWorldGen();
		RegisterVillagers();
		//Wands / Utility Items
		Register("worldedit_wand", WORLDEDIT_WAND, List.of("World Edit Wand"));
		Register("worldedit_nav", WORLDEDIT_NAV, List.of("World Edit Nav Tool"));
		Register("ragdoll_wand", RAGDOLL_WAND, List.of("Ragdoll Wand"));
		//Haven Modpack
		if (ModConfig.REGISTER_HAVEN_MOD) {
			HavenMod.RegisterAll();
		}
		//Ryft Modpack
		if (ModConfig.REGISTER_RYFT_MOD) {
			RyftMod.RegisterAll();
		}
	}

	private static void RegisterDyes() {
		for (DyeColor color : DyeColor.values()) Register(color.getName() + "_dye_block", DYE_BLOCKS.get(color), List.of(EN_US.Block(EN_US.Dye(EN_US.Color(color)))));
		//<editor-fold desc="Beige">
		Register("beige_dye", BEIGE_DYE, List.of(EN_US.Dye(EN_US.Beige())));
		Register("beige_dye_block", BEIGE_DYE_BLOCK, List.of(EN_US.Block(EN_US.Dye(EN_US.Beige()))));
		Register("beige_candle", BEIGE_CANDLE, List.of(EN_US.Candle(EN_US.Beige())));
		Register("beige_candle_cake", BEIGE_CANDLE_CAKE, List.of(EN_US.Candle(EN_US.Beige(EN_US.with(EN_US.Cake())))));
		Register("beige_carrot_candle_cake", CARROT_CAKE.BEIGE_CANDLE_CAKE, List.of(EN_US.Candle(EN_US.Beige(EN_US.with(EN_US.Cake(EN_US.Carrot()))))));
		Register("beige_chocolate_candle_cake", CHOCOLATE_CAKE.BEIGE_CANDLE_CAKE, List.of(EN_US.Candle(EN_US.Beige(EN_US.with(EN_US.Cake(EN_US.Chocolate()))))));
		Register("beige_chorus_candle_cake", CHORUS_CAKE.BEIGE_CANDLE_CAKE, List.of(EN_US.Candle(EN_US.Beige(EN_US.with(EN_US.Cake(EN_US.Chorus()))))));
		Register("beige_coffee_candle_cake", COFFEE_CAKE.BEIGE_CANDLE_CAKE, List.of(EN_US.Candle(EN_US.Beige(EN_US.with(EN_US.Cake(EN_US.Coffee()))))));
		Register("beige_confetti_candle_cake", CONFETTI_CAKE.BEIGE_CANDLE_CAKE, List.of(EN_US.Candle(EN_US.Beige(EN_US.with(EN_US.Cake(EN_US.Confetti()))))));
		Register("beige_strawberry_candle_cake", STRAWBERRY_CAKE.BEIGE_CANDLE_CAKE, List.of(EN_US.Candle(EN_US.Beige(EN_US.with(EN_US.Cake(EN_US.Strawberry()))))));
		Register("beige_vanilla_candle_cake", VANILLA_CAKE.BEIGE_CANDLE_CAKE, List.of(EN_US.Candle(EN_US.Beige(EN_US.with(EN_US.Cake(EN_US.Vanilla()))))));
		Register("beige_concrete", BEIGE_CONCRETE, List.of(EN_US.Concrete(EN_US.Beige())));
		Register("beige_concrete_stairs", BEIGE_CONCRETE_STAIRS, List.of(EN_US.Stairs(EN_US.Concrete(EN_US.Beige()))));
		Register("beige_concrete_slab", BEIGE_CONCRETE_SLAB, List.of(EN_US.Slab(EN_US.Concrete(EN_US.Beige()))));
		Register("beige_concrete_wall", BEIGE_CONCRETE_WALL, List.of(EN_US.Wall(EN_US.Concrete(EN_US.Beige()))));
		Register("beige_concrete_powder", BEIGE_CONCRETE_POWDER, List.of(EN_US.Powder(EN_US.Concrete(EN_US.Beige()))));
		Register("beige_terracotta", BEIGE_TERRACOTTA, List.of(EN_US.Terracotta(EN_US.Beige())));
		Register("beige_terracotta_stairs", BEIGE_TERRACOTTA_STAIRS, List.of(EN_US.Stairs(EN_US.Terracotta(EN_US.Beige()))));
		Register("beige_terracotta_slab", BEIGE_TERRACOTTA_SLAB, List.of(EN_US.Slab(EN_US.Terracotta(EN_US.Beige()))));
		Register("beige_terracotta_wall", BEIGE_TERRACOTTA_WALL, List.of(EN_US.Wall(EN_US.Terracotta(EN_US.Beige()))));
		Register("beige_glazed_terracotta", BEIGE_GLAZED_TERRACOTTA, List.of(EN_US.Terracotta(EN_US.Glazed(EN_US.Beige()))));
		Register("beige_glazed_terracotta_slab", BEIGE_GLAZED_TERRACOTTA_SLAB, List.of(EN_US.Slab(EN_US.Terracotta(EN_US.Glazed(EN_US.Beige())))));
		Register("beige_wool", BEIGE_WOOL, List.of(EN_US.Wool(EN_US.Beige())));
		Register("beige_wool_slab", BEIGE_WOOL_SLAB, List.of(EN_US.Slab(EN_US.Wool(EN_US.Beige()))));
		Register("beige_carpet", BEIGE_CARPET, List.of(EN_US.Carpet(EN_US.Beige())));
		Register("beige_fleece", BEIGE_FLEECE, List.of(EN_US.Fleece(EN_US.Beige())));
		Register("beige_fleece_slab", BEIGE_FLEECE_SLAB, List.of(EN_US.Slab(EN_US.Fleece(EN_US.Beige()))));
		Register("beige_fleece_carpet", BEIGE_FLEECE_CARPET, List.of(EN_US.Carpet(EN_US.Fleece(EN_US.Beige()))));
		Register("beige_stained_glass", BEIGE_STAINED_GLASS, List.of(EN_US.Glass(EN_US.Stained(EN_US.Beige()))));
		Register("beige_stained_glass_pane", BEIGE_STAINED_GLASS_PANE, List.of(EN_US.Pane(EN_US.Glass(EN_US.Stained(EN_US.Beige())))));
		Register("beige_stained_glass_slab", BEIGE_STAINED_GLASS_SLAB, List.of(EN_US.Slab(EN_US.Glass(EN_US.Stained(EN_US.Beige())))));
		Register("beige_stained_glass_trapdoor", BEIGE_STAINED_GLASS_TRAPDOOR, List.of(EN_US.Trapdoor(EN_US.Glass(EN_US.Stained(EN_US.Beige())))));
		//</editor-fold>
		//<editor-fold desc="Burgundy">
		Register("burgundy_dye", BURGUNDY_DYE, List.of(EN_US.Dye(EN_US.Burgundy())));
		Register("burgundy_dye_block", BURGUNDY_DYE_BLOCK, List.of(EN_US.Block(EN_US.Dye(EN_US.Burgundy()))));
		Register("burgundy_candle", BURGUNDY_CANDLE, List.of(EN_US.Candle(EN_US.Burgundy())));
		Register("burgundy_candle_cake", BURGUNDY_CANDLE_CAKE, List.of(EN_US.Candle(EN_US.Burgundy(EN_US.with(EN_US.Cake())))));
		Register("burgundy_carrot_candle_cake", CARROT_CAKE.BURGUNDY_CANDLE_CAKE, List.of(EN_US.Candle(EN_US.Burgundy(EN_US.with(EN_US.Cake(EN_US.Carrot()))))));
		Register("burgundy_chocolate_candle_cake", CHOCOLATE_CAKE.BURGUNDY_CANDLE_CAKE, List.of(EN_US.Candle(EN_US.Burgundy(EN_US.with(EN_US.Cake(EN_US.Chocolate()))))));
		Register("burgundy_chorus_candle_cake", CHORUS_CAKE.BURGUNDY_CANDLE_CAKE, List.of(EN_US.Candle(EN_US.Burgundy(EN_US.with(EN_US.Cake(EN_US.Chorus()))))));
		Register("burgundy_coffee_candle_cake", COFFEE_CAKE.BURGUNDY_CANDLE_CAKE, List.of(EN_US.Candle(EN_US.Burgundy(EN_US.with(EN_US.Cake(EN_US.Coffee()))))));
		Register("burgundy_confetti_candle_cake", CONFETTI_CAKE.BURGUNDY_CANDLE_CAKE, List.of(EN_US.Candle(EN_US.Burgundy(EN_US.with(EN_US.Cake(EN_US.Confetti()))))));
		Register("burgundy_strawberry_candle_cake", STRAWBERRY_CAKE.BURGUNDY_CANDLE_CAKE, List.of(EN_US.Candle(EN_US.Burgundy(EN_US.with(EN_US.Cake(EN_US.Strawberry()))))));
		Register("burgundy_vanilla_candle_cake", VANILLA_CAKE.BURGUNDY_CANDLE_CAKE, List.of(EN_US.Candle(EN_US.Burgundy(EN_US.with(EN_US.Cake(EN_US.Vanilla()))))));
		Register("burgundy_concrete", BURGUNDY_CONCRETE, List.of(EN_US.Concrete(EN_US.Burgundy())));
		Register("burgundy_concrete_stairs", BURGUNDY_CONCRETE_STAIRS, List.of(EN_US.Stairs(EN_US.Concrete(EN_US.Burgundy()))));
		Register("burgundy_concrete_slab", BURGUNDY_CONCRETE_SLAB, List.of(EN_US.Slab(EN_US.Concrete(EN_US.Burgundy()))));
		Register("burgundy_concrete_wall", BURGUNDY_CONCRETE_WALL, List.of(EN_US.Wall(EN_US.Concrete(EN_US.Burgundy()))));
		Register("burgundy_concrete_powder", BURGUNDY_CONCRETE_POWDER, List.of(EN_US.Powder(EN_US.Concrete(EN_US.Burgundy()))));
		Register("burgundy_terracotta", BURGUNDY_TERRACOTTA, List.of(EN_US.Terracotta(EN_US.Burgundy())));
		Register("burgundy_terracotta_stairs", BURGUNDY_TERRACOTTA_STAIRS, List.of(EN_US.Stairs(EN_US.Terracotta(EN_US.Burgundy()))));
		Register("burgundy_terracotta_slab", BURGUNDY_TERRACOTTA_SLAB, List.of(EN_US.Slab(EN_US.Terracotta(EN_US.Burgundy()))));
		Register("burgundy_terracotta_wall", BURGUNDY_TERRACOTTA_WALL, List.of(EN_US.Wall(EN_US.Terracotta(EN_US.Burgundy()))));
		Register("burgundy_glazed_terracotta", BURGUNDY_GLAZED_TERRACOTTA, List.of(EN_US.Terracotta(EN_US.Glazed(EN_US.Burgundy()))));
		Register("burgundy_glazed_terracotta_slab", BURGUNDY_GLAZED_TERRACOTTA_SLAB, List.of(EN_US.Slab(EN_US.Terracotta(EN_US.Glazed(EN_US.Burgundy())))));
		Register("burgundy_wool", BURGUNDY_WOOL, List.of(EN_US.Wool(EN_US.Burgundy())));
		Register("burgundy_wool_slab", BURGUNDY_WOOL_SLAB, List.of(EN_US.Slab(EN_US.Wool(EN_US.Burgundy()))));
		Register("burgundy_carpet", BURGUNDY_CARPET, List.of(EN_US.Carpet(EN_US.Burgundy())));
		Register("burgundy_fleece", BURGUNDY_FLEECE, List.of(EN_US.Fleece(EN_US.Burgundy())));
		Register("burgundy_fleece_slab", BURGUNDY_FLEECE_SLAB, List.of(EN_US.Slab(EN_US.Fleece(EN_US.Burgundy()))));
		Register("burgundy_fleece_carpet", BURGUNDY_FLEECE_CARPET, List.of(EN_US.Carpet(EN_US.Fleece(EN_US.Burgundy()))));
		Register("burgundy_stained_glass", BURGUNDY_STAINED_GLASS, List.of(EN_US.Glass(EN_US.Stained(EN_US.Burgundy()))));
		Register("burgundy_stained_glass_pane", BURGUNDY_STAINED_GLASS_PANE, List.of(EN_US.Pane(EN_US.Glass(EN_US.Stained(EN_US.Burgundy())))));
		Register("burgundy_stained_glass_slab", BURGUNDY_STAINED_GLASS_SLAB, List.of(EN_US.Slab(EN_US.Glass(EN_US.Stained(EN_US.Burgundy())))));
		Register("burgundy_stained_glass_trapdoor", BURGUNDY_STAINED_GLASS_TRAPDOOR, List.of(EN_US.Trapdoor(EN_US.Glass(EN_US.Stained(EN_US.Burgundy())))));
		//</editor-fold>
		//<editor-fold desc="Lavender">
		Register("lavender_dye", LAVENDER_DYE, List.of(EN_US.Dye(EN_US.Lavender())));
		Register("lavender_dye_block", LAVENDER_DYE_BLOCK, List.of(EN_US.Block(EN_US.Dye(EN_US.Lavender()))));
		Register("lavender_candle", LAVENDER_CANDLE, List.of(EN_US.Candle(EN_US.Lavender())));
		Register("lavender_candle_cake", LAVENDER_CANDLE_CAKE, List.of(EN_US.Candle(EN_US.Lavender(EN_US.with(EN_US.Cake())))));
		Register("lavender_carrot_candle_cake", CARROT_CAKE.LAVENDER_CANDLE_CAKE, List.of(EN_US.Candle(EN_US.Lavender(EN_US.with(EN_US.Cake(EN_US.Carrot()))))));
		Register("lavender_chocolate_candle_cake", CHOCOLATE_CAKE.LAVENDER_CANDLE_CAKE, List.of(EN_US.Candle(EN_US.Lavender(EN_US.with(EN_US.Cake(EN_US.Chocolate()))))));
		Register("lavender_chorus_candle_cake", CHORUS_CAKE.LAVENDER_CANDLE_CAKE, List.of(EN_US.Candle(EN_US.Lavender(EN_US.with(EN_US.Cake(EN_US.Chorus()))))));
		Register("lavender_coffee_candle_cake", COFFEE_CAKE.LAVENDER_CANDLE_CAKE, List.of(EN_US.Candle(EN_US.Lavender(EN_US.with(EN_US.Cake(EN_US.Coffee()))))));
		Register("lavender_confetti_candle_cake", CONFETTI_CAKE.LAVENDER_CANDLE_CAKE, List.of(EN_US.Candle(EN_US.Lavender(EN_US.with(EN_US.Cake(EN_US.Confetti()))))));
		Register("lavender_strawberry_candle_cake", STRAWBERRY_CAKE.LAVENDER_CANDLE_CAKE, List.of(EN_US.Candle(EN_US.Lavender(EN_US.with(EN_US.Cake(EN_US.Strawberry()))))));
		Register("lavender_vanilla_candle_cake", VANILLA_CAKE.LAVENDER_CANDLE_CAKE, List.of(EN_US.Candle(EN_US.Lavender(EN_US.with(EN_US.Cake(EN_US.Vanilla()))))));
		Register("lavender_concrete", LAVENDER_CONCRETE, List.of(EN_US.Concrete(EN_US.Lavender())));
		Register("lavender_concrete_stairs", LAVENDER_CONCRETE_STAIRS, List.of(EN_US.Stairs(EN_US.Concrete(EN_US.Lavender()))));
		Register("lavender_concrete_slab", LAVENDER_CONCRETE_SLAB, List.of(EN_US.Slab(EN_US.Concrete(EN_US.Lavender()))));
		Register("lavender_concrete_wall", LAVENDER_CONCRETE_WALL, List.of(EN_US.Wall(EN_US.Concrete(EN_US.Lavender()))));
		Register("lavender_concrete_powder", LAVENDER_CONCRETE_POWDER, List.of(EN_US.Powder(EN_US.Concrete(EN_US.Lavender()))));
		Register("lavender_terracotta", LAVENDER_TERRACOTTA, List.of(EN_US.Terracotta(EN_US.Lavender())));
		Register("lavender_terracotta_stairs", LAVENDER_TERRACOTTA_STAIRS, List.of(EN_US.Stairs(EN_US.Terracotta(EN_US.Lavender()))));
		Register("lavender_terracotta_slab", LAVENDER_TERRACOTTA_SLAB, List.of(EN_US.Slab(EN_US.Terracotta(EN_US.Lavender()))));
		Register("lavender_terracotta_wall", LAVENDER_TERRACOTTA_WALL, List.of(EN_US.Wall(EN_US.Terracotta(EN_US.Lavender()))));
		Register("lavender_glazed_terracotta", LAVENDER_GLAZED_TERRACOTTA, List.of(EN_US.Terracotta(EN_US.Glazed(EN_US.Lavender()))));
		Register("lavender_glazed_terracotta_slab", LAVENDER_GLAZED_TERRACOTTA_SLAB, List.of(EN_US.Slab(EN_US.Terracotta(EN_US.Glazed(EN_US.Lavender())))));
		Register("lavender_wool", LAVENDER_WOOL, List.of(EN_US.Wool(EN_US.Lavender())));
		Register("lavender_wool_slab", LAVENDER_WOOL_SLAB, List.of(EN_US.Slab(EN_US.Wool(EN_US.Lavender()))));
		Register("lavender_carpet", LAVENDER_CARPET, List.of(EN_US.Carpet(EN_US.Lavender())));
		Register("lavender_fleece", LAVENDER_FLEECE, List.of(EN_US.Fleece(EN_US.Lavender())));
		Register("lavender_fleece_slab", LAVENDER_FLEECE_SLAB, List.of(EN_US.Slab(EN_US.Fleece(EN_US.Lavender()))));
		Register("lavender_fleece_carpet", LAVENDER_FLEECE_CARPET, List.of(EN_US.Carpet(EN_US.Fleece(EN_US.Lavender()))));
		Register("lavender_stained_glass", LAVENDER_STAINED_GLASS, List.of(EN_US.Glass(EN_US.Stained(EN_US.Lavender()))));
		Register("lavender_stained_glass_pane", LAVENDER_STAINED_GLASS_PANE, List.of(EN_US.Pane(EN_US.Glass(EN_US.Stained(EN_US.Lavender())))));
		Register("lavender_stained_glass_slab", LAVENDER_STAINED_GLASS_SLAB, List.of(EN_US.Slab(EN_US.Glass(EN_US.Stained(EN_US.Lavender())))));
		Register("lavender_stained_glass_trapdoor", LAVENDER_STAINED_GLASS_TRAPDOOR, List.of(EN_US.Trapdoor(EN_US.Glass(EN_US.Stained(EN_US.Lavender())))));
		//</editor-fold>
		//<editor-fold desc="Mint">
		Register("mint_dye", MINT_DYE, List.of(EN_US.Dye(EN_US.Mint())));
		Register("mint_dye_block", MINT_DYE_BLOCK, List.of(EN_US.Block(EN_US.Dye(EN_US.Mint()))));
		Register("mint_candle", MINT_CANDLE, List.of(EN_US.Candle(EN_US.Mint())));
		Register("mint_candle_cake", MINT_CANDLE_CAKE, List.of(EN_US.Candle(EN_US.Mint(EN_US.with(EN_US.Cake())))));
		Register("mint_carrot_candle_cake", CARROT_CAKE.MINT_CANDLE_CAKE, List.of(EN_US.Candle(EN_US.Mint(EN_US.with(EN_US.Cake(EN_US.Carrot()))))));
		Register("mint_chocolate_candle_cake", CHOCOLATE_CAKE.MINT_CANDLE_CAKE, List.of(EN_US.Candle(EN_US.Mint(EN_US.with(EN_US.Cake(EN_US.Chocolate()))))));
		Register("mint_chorus_candle_cake", CHORUS_CAKE.MINT_CANDLE_CAKE, List.of(EN_US.Candle(EN_US.Mint(EN_US.with(EN_US.Cake(EN_US.Chorus()))))));
		Register("mint_coffee_candle_cake", COFFEE_CAKE.MINT_CANDLE_CAKE, List.of(EN_US.Candle(EN_US.Mint(EN_US.with(EN_US.Cake(EN_US.Coffee()))))));
		Register("mint_confetti_candle_cake", CONFETTI_CAKE.MINT_CANDLE_CAKE, List.of(EN_US.Candle(EN_US.Mint(EN_US.with(EN_US.Cake(EN_US.Confetti()))))));
		Register("mint_strawberry_candle_cake", STRAWBERRY_CAKE.MINT_CANDLE_CAKE, List.of(EN_US.Candle(EN_US.Mint(EN_US.with(EN_US.Cake(EN_US.Strawberry()))))));
		Register("mint_vanilla_candle_cake", VANILLA_CAKE.MINT_CANDLE_CAKE, List.of(EN_US.Candle(EN_US.Mint(EN_US.with(EN_US.Cake(EN_US.Vanilla()))))));
		Register("mint_concrete", MINT_CONCRETE, List.of(EN_US.Concrete(EN_US.Mint())));
		Register("mint_concrete_stairs", MINT_CONCRETE_STAIRS, List.of(EN_US.Stairs(EN_US.Concrete(EN_US.Mint()))));
		Register("mint_concrete_slab", MINT_CONCRETE_SLAB, List.of(EN_US.Slab(EN_US.Concrete(EN_US.Mint()))));
		Register("mint_concrete_wall", MINT_CONCRETE_WALL, List.of(EN_US.Wall(EN_US.Concrete(EN_US.Mint()))));
		Register("mint_concrete_powder", MINT_CONCRETE_POWDER, List.of(EN_US.Powder(EN_US.Concrete(EN_US.Mint()))));
		Register("mint_terracotta", MINT_TERRACOTTA, List.of(EN_US.Terracotta(EN_US.Mint())));
		Register("mint_terracotta_stairs", MINT_TERRACOTTA_STAIRS, List.of(EN_US.Stairs(EN_US.Terracotta(EN_US.Mint()))));
		Register("mint_terracotta_slab", MINT_TERRACOTTA_SLAB, List.of(EN_US.Slab(EN_US.Terracotta(EN_US.Mint()))));
		Register("mint_terracotta_wall", MINT_TERRACOTTA_WALL, List.of(EN_US.Wall(EN_US.Terracotta(EN_US.Mint()))));
		Register("mint_glazed_terracotta", MINT_GLAZED_TERRACOTTA, List.of(EN_US.Terracotta(EN_US.Glazed(EN_US.Mint()))));
		Register("mint_glazed_terracotta_slab", MINT_GLAZED_TERRACOTTA_SLAB, List.of(EN_US.Slab(EN_US.Terracotta(EN_US.Glazed(EN_US.Mint())))));
		Register("mint_wool", MINT_WOOL, List.of(EN_US.Wool(EN_US.Mint())));
		Register("mint_wool_slab", MINT_WOOL_SLAB, List.of(EN_US.Slab(EN_US.Wool(EN_US.Mint()))));
		Register("mint_carpet", MINT_CARPET, List.of(EN_US.Carpet(EN_US.Mint())));
		Register("mint_fleece", MINT_FLEECE, List.of(EN_US.Fleece(EN_US.Mint())));
		Register("mint_fleece_slab", MINT_FLEECE_SLAB, List.of(EN_US.Slab(EN_US.Fleece(EN_US.Mint()))));
		Register("mint_fleece_carpet", MINT_FLEECE_CARPET, List.of(EN_US.Carpet(EN_US.Fleece(EN_US.Mint()))));
		Register("mint_stained_glass", MINT_STAINED_GLASS, List.of(EN_US.Glass(EN_US.Stained(EN_US.Mint()))));
		Register("mint_stained_glass_pane", MINT_STAINED_GLASS_PANE, List.of(EN_US.Pane(EN_US.Glass(EN_US.Stained(EN_US.Mint())))));
		Register("mint_stained_glass_slab", MINT_STAINED_GLASS_SLAB, List.of(EN_US.Slab(EN_US.Glass(EN_US.Stained(EN_US.Mint())))));
		Register("mint_stained_glass_trapdoor", MINT_STAINED_GLASS_TRAPDOOR, List.of(EN_US.Trapdoor(EN_US.Glass(EN_US.Stained(EN_US.Mint())))));
		//</editor-fold>
	}
	private static void RegisterIron() {
		Register("raw_iron_nugget", RAW_IRON_NUGGET, List.of(EN_US.Nugget(EN_US.Iron(EN_US.Raw()))));
		Register("iron_rod", IRON_ROD, List.of(EN_US.Rod(EN_US.Iron())));
		Register("nether_iron_ore", NETHER_IRON_ORE, List.of(EN_US.Ore(EN_US.Iron(EN_US.Nether()))));
		Register("end_iron_ore", END_IRON_ORE, List.of(EN_US.Ore(EN_US.Iron(EN_US.End()))));
		Register("iron_torch", "iron_wall_torch", IRON_TORCH, List.of(EN_US._Torch(EN_US.Iron())));
		Register("iron_soul_torch", "iron_soul_wall_torch", IRON_SOUL_TORCH, List.of(EN_US.SoulTorch(EN_US.Iron())));
		Register("iron_ender_torch", "iron_ender_wall_torch", IRON_ENDER_TORCH, List.of(EN_US.EnderTorch(EN_US.Iron())));
		Register("underwater_iron_torch", "underwater_iron_wall_torch", UNDERWATER_IRON_TORCH, List.of(EN_US.Underwater_Torch(EN_US.Iron())));
		Register("iron_lantern", IRON_LANTERN, List.of(EN_US.Lantern(EN_US.Iron())));
		Register("iron_soul_lantern", IRON_SOUL_LANTERN, List.of(EN_US.SoulLantern(EN_US.Iron())));
		Register("iron_ender_lantern", IRON_ENDER_LANTERN, List.of(EN_US.EnderLantern(EN_US.Iron())));
		Register("iron_button", IRON_BUTTON, List.of(EN_US.Button(EN_US.Iron())));
		Register("iron_chain", IRON_CHAIN, List.of(EN_US.Chain(EN_US.Iron())));
		Register("heavy_iron_chain", HEAVY_IRON_CHAIN, List.of(EN_US.Chain(EN_US.Iron(EN_US.Heavy()))));
		Register("iron_stairs", IRON_STAIRS, List.of(EN_US.Stairs(EN_US.Iron())));
		Register("iron_slab", IRON_SLAB, List.of(EN_US.Slab(EN_US.Iron())));
		Register("iron_wall", IRON_WALL, List.of(EN_US.Wall(EN_US.Iron())));
		Register("iron_bricks", IRON_BRICKS, List.of(EN_US.Bricks(EN_US.Iron())));
		Register("iron_brick_stairs", IRON_BRICK_STAIRS, List.of(EN_US.Stairs(EN_US.Brick(EN_US.Iron()))));
		Register("iron_brick_slab", IRON_BRICK_SLAB, List.of(EN_US.Slab(EN_US.Brick(EN_US.Iron()))));
		Register("iron_brick_wall", IRON_BRICK_WALL, List.of(EN_US.Wall(EN_US.Brick(EN_US.Iron()))));
		Register("cut_iron", CUT_IRON, List.of(EN_US.Iron(EN_US.Cut())));
		Register("cut_iron_pillar", CUT_IRON_PILLAR, List.of(EN_US.Pillar(EN_US.Iron(EN_US.Cut()))));
		Register("cut_iron_stairs", CUT_IRON_STAIRS, List.of(EN_US.Stairs(EN_US.Iron(EN_US.Cut()))));
		Register("cut_iron_slab", CUT_IRON_SLAB, List.of(EN_US.Slab(EN_US.Iron(EN_US.Cut()))));
		Register("cut_iron_wall", CUT_IRON_WALL, List.of(EN_US.Wall(EN_US.Iron(EN_US.Cut()))));
		Register("iron_hammer", IRON_HAMMER, List.of(EN_US.Hammer(EN_US.Iron())));
	}
	private static void RegisterGold() {
		Register("gold_rod", GOLD_ROD, List.of(EN_US.Rod(EN_US.Gold())));
		Register("end_gold_ore", END_GOLD_ORE, List.of(EN_US.Ore(EN_US.Gold(EN_US.End()))));
		Register("gold_torch", "gold_wall_torch", GOLD_TORCH, List.of(EN_US._Torch(EN_US.Gold())));
		Register("gold_soul_torch", "gold_soul_wall_torch", GOLD_SOUL_TORCH, List.of(EN_US.SoulTorch(EN_US.Gold())));
		Register("gold_ender_torch", "gold_ender_wall_torch", GOLD_ENDER_TORCH, List.of(EN_US.EnderTorch(EN_US.Gold())));
		Register("underwater_gold_torch", "underwater_gold_wall_torch", UNDERWATER_GOLD_TORCH, List.of(EN_US.Underwater_Torch(EN_US.Gold())));
		Register("gold_lantern", GOLD_LANTERN, List.of(EN_US.Lantern(EN_US.Gold())));
		Register("gold_soul_lantern", GOLD_SOUL_LANTERN, List.of(EN_US.SoulLantern(EN_US.Gold())));
		Register("gold_ender_lantern", GOLD_ENDER_LANTERN, List.of(EN_US.EnderLantern(EN_US.Gold())));
		Register("gold_button", GOLD_BUTTON, List.of(EN_US.Button(EN_US.Gold())));
		Register("gold_chain", GOLD_CHAIN, List.of(EN_US.Chain(EN_US.Gold())));
		Register("heavy_gold_chain", HEAVY_GOLD_CHAIN, List.of(EN_US.Chain(EN_US.Gold(EN_US.Heavy()))));
		Register("gold_bars", GOLD_BARS, List.of(EN_US.Bars(EN_US.Gold())));
		Register("gold_stairs", GOLD_STAIRS, List.of(EN_US.Stairs(EN_US.Gold())));
		Register("gold_slab", GOLD_SLAB, List.of(EN_US.Slab(EN_US.Gold())));
		Register("gold_wall", GOLD_WALL, List.of(EN_US.Wall(EN_US.Gold())));
		Register("gold_bricks", GOLD_BRICKS, List.of(EN_US.Bricks(EN_US.Gold())));
		Register("gold_brick_stairs", GOLD_BRICK_STAIRS, List.of(EN_US.Stairs(EN_US.Brick(EN_US.Gold()))));
		Register("gold_brick_slab", GOLD_BRICK_SLAB, List.of(EN_US.Slab(EN_US.Brick(EN_US.Gold()))));
		Register("gold_brick_wall", GOLD_BRICK_WALL, List.of(EN_US.Wall(EN_US.Brick(EN_US.Gold()))));
		Register("cut_gold", CUT_GOLD, List.of(EN_US.Gold(EN_US.Cut())));
		Register("cut_gold_pillar", CUT_GOLD_PILLAR, List.of(EN_US.Pillar(EN_US.Gold(EN_US.Cut()))));
		Register("cut_gold_stairs", CUT_GOLD_STAIRS, List.of(EN_US.Stairs(EN_US.Gold(EN_US.Cut()))));
		Register("cut_gold_slab", CUT_GOLD_SLAB, List.of(EN_US.Slab(EN_US.Gold(EN_US.Cut()))));
		Register("cut_gold_wall", CUT_GOLD_WALL, List.of(EN_US.Wall(EN_US.Gold(EN_US.Cut()))));
		Register("gold_door", GOLD_DOOR, List.of(EN_US.Door(EN_US.Gold())));
		Register("gold_trapdoor", GOLD_TRAPDOOR, List.of(EN_US.Trapdoor(EN_US.Gold())));
		Register("golden_hammer", GOLDEN_HAMMER, List.of(EN_US.Hammer(EN_US.Golden())));
		Register("golden_shears", GOLDEN_SHEARS, List.of(EN_US.Shears(EN_US.Golden())));
		//Bucket
		Register("golden_bucket", GOLDEN_BUCKET, List.of(EN_US.Bucket(EN_US.Golden())));
		DispenserBlock.registerBehavior(GOLDEN_BUCKET, new BucketDispenserBehavior(GOLDEN_BUCKET_PROVIDER));
		Register("golden_water_bucket", GOLDEN_WATER_BUCKET, List.of(EN_US.Bucket(EN_US.Water(EN_US.Golden()))));
		CauldronBehavior.EMPTY_CAULDRON_BEHAVIOR.put(GOLDEN_WATER_BUCKET, (state, world, pos, player, hand, stack) -> BucketUtil.fillCauldron(world, pos, player, hand, stack,
				Blocks.WATER_CAULDRON.getDefaultState().with(LeveledCauldronBlock.LEVEL, 3),
				SoundEvents.ITEM_BUCKET_EMPTY, GOLDEN_BUCKET));
		CauldronBehavior.WATER_CAULDRON_BEHAVIOR.put(GOLDEN_BUCKET, (state, world, pos, player, hand, stack) -> CauldronBehavior.emptyCauldron(state, world, pos, player, hand, stack, new ItemStack(GOLDEN_WATER_BUCKET),
				(statex) -> statex.get(LeveledCauldronBlock.LEVEL) == 3, SoundEvents.ITEM_BUCKET_FILL));
		Register("golden_lava_bucket", GOLDEN_LAVA_BUCKET, List.of(EN_US.Bucket(EN_US.Lava(EN_US.Golden()))));
		Register("golden_powder_snow_bucket", GOLDEN_POWDER_SNOW_BUCKET, List.of(EN_US.Bucket(EN_US.Snow(EN_US.Powder(EN_US.Golden())))));
		CauldronBehavior.EMPTY_CAULDRON_BEHAVIOR.put(GOLDEN_POWDER_SNOW_BUCKET, (state, world, pos, player, hand, stack) -> BucketUtil.fillCauldron(world, pos, player, hand, stack,
				Blocks.POWDER_SNOW_CAULDRON.getDefaultState().with(LeveledCauldronBlock.LEVEL, 3),
				SoundEvents.ITEM_BUCKET_EMPTY_POWDER_SNOW, GOLDEN_BUCKET));
		CauldronBehavior.POWDER_SNOW_CAULDRON_BEHAVIOR.put(GOLDEN_BUCKET, (state, world, pos, player, hand, stack) -> CauldronBehavior.emptyCauldron(state, world, pos, player, hand, stack, new ItemStack(GOLDEN_POWDER_SNOW_BUCKET),
				(statex) -> statex.get(LeveledCauldronBlock.LEVEL) == 3, SoundEvents.ITEM_BUCKET_FILL_POWDER_SNOW));
		Register("golden_blood_bucket", GOLDEN_BLOOD_BUCKET, List.of(EN_US.Bucket(EN_US.Blood(EN_US.Golden()))));
		CauldronBehavior.EMPTY_CAULDRON_BEHAVIOR.put(GOLDEN_BLOOD_BUCKET, BloodCauldronBlock.FillFromBucket(GOLDEN_BUCKET));
		Register("golden_mud_bucket", GOLDEN_MUD_BUCKET, List.of(EN_US.Bucket(EN_US.Mud(EN_US.Golden()))));
		CauldronBehavior.EMPTY_CAULDRON_BEHAVIOR.put(GOLDEN_MUD_BUCKET, MudCauldronBlock.FillFromBucket(GOLDEN_BUCKET));
		Register("golden_milk_bucket", GOLDEN_MILK_BUCKET, List.of(EN_US.Bucket(EN_US.Milk(EN_US.Golden()))));
		CauldronBehavior.EMPTY_CAULDRON_BEHAVIOR.put(GOLDEN_MILK_BUCKET, MilkCauldronBlock.FillFromBucket(GOLDEN_BUCKET));
		Register("golden_chocolate_milk_bucket", GOLDEN_CHOCOLATE_MILK_BUCKET, List.of(EN_US.Bucket(EN_US.Milk(EN_US.Chocolate(EN_US.Golden())))));
		Register("golden_coffee_milk_bucket", GOLDEN_COFFEE_MILK_BUCKET, List.of(EN_US.Bucket(EN_US.Milk(EN_US.Coffee(EN_US.Golden())))));
		Register("golden_strawberry_milk_bucket", GOLDEN_STRAWBERRY_MILK_BUCKET, List.of(EN_US.Bucket(EN_US.Milk(EN_US.Strawberry(EN_US.Golden())))));
		Register("golden_vanilla_milk_bucket", GOLDEN_VANILLA_MILK_BUCKET, List.of(EN_US.Bucket(EN_US.Milk(EN_US.Vanilla(EN_US.Golden())))));
	}
	private static void RegisterBlood() {
		//<editor-fold desc="Blood">
		Register("blood_bottle", BLOOD_BOTTLE, List.of(EN_US.Bottle(EN_US.Blood())));
		Register("blood_bucket", BLOOD_BUCKET, List.of(EN_US.Bucket(EN_US.Blood())));
		Register("blood_cauldron", BLOOD_CAULDRON, List.of(EN_US.Cauldron(EN_US.Blood())));
		CauldronBehavior.EMPTY_CAULDRON_BEHAVIOR.put(BLOOD_BUCKET, BloodCauldronBlock.FillFromBucket(Items.BUCKET));
		CauldronFluidContent.registerCauldron(BLOOD_CAULDRON, STILL_BLOOD_FLUID, FluidConstants.BOTTLE, LeveledCauldronBlock.LEVEL);
		CauldronBehavior.EMPTY_CAULDRON_BEHAVIOR.put(BLOOD_BOTTLE, BloodCauldronBlock.FILL_FROM_BOTTLE);
		FluidStorage.combinedItemApiProvider(BLOOD_BOTTLE).register(context -> new FullItemFluidStorage(context, bottle -> ItemVariant.of(Items.GLASS_BOTTLE), FluidVariant.of(STILL_BLOOD_FLUID), FluidConstants.BOTTLE));
		Registry.register(Registry.FLUID, ModId.ID("still_blood"), STILL_BLOOD_FLUID);
		Registry.register(Registry.FLUID, ModId.ID("flowing_blood"), FLOWING_BLOOD_FLUID);
		Register("blood_fluid_block", BLOOD_FLUID_BLOCK, List.of(EN_US.Block(EN_US.Fluid(EN_US.Blood()))));
		//Solid
		Register("blood_block", BLOOD_BLOCK, List.of(EN_US.Block(EN_US.Blood())));
		JuicerBlock.JUICE_MAP.put(BLOOD_BLOCK::asItem, BLOOD_BOTTLE);
		Register("blood_fence", BLOOD_FENCE, List.of(EN_US.Fence(EN_US.Blood())));
		Register("blood_pane", BLOOD_PANE, List.of(EN_US.Pane(EN_US.Blood())));
		Register("blood_stairs", BLOOD_STAIRS, List.of(EN_US.Stairs(EN_US.Blood())));
		Register("blood_slab", BLOOD_SLAB, List.of(EN_US.Slab(EN_US.Blood())));
		Register("blood_wall", BLOOD_WALL, List.of(EN_US.Wall(EN_US.Blood())));
		Register("dried_blood_block", DRIED_BLOOD_BLOCK, List.of(EN_US.Block(EN_US.Blood(EN_US.Dried()))));
		Register("dried_blood_fence", DRIED_BLOOD_FENCE, List.of(EN_US.Fence(EN_US.Blood(EN_US.Dried()))));
		Register("dried_blood_pane", DRIED_BLOOD_PANE, List.of(EN_US.Pane(EN_US.Blood(EN_US.Dried()))));
		Register("dried_blood_stairs", DRIED_BLOOD_STAIRS, List.of(EN_US.Stairs(EN_US.Blood(EN_US.Dried()))));
		Register("dried_blood_slab", DRIED_BLOOD_SLAB, List.of(EN_US.Slab(EN_US.Blood(EN_US.Dried()))));
		Register("dried_blood_wall", DRIED_BLOOD_WALL, List.of(EN_US.Wall(EN_US.Blood(EN_US.Dried()))));
		//</editor-fold>
		//<editor-fold desc="Syringe">
		Register("syringe", SYRINGE, List.of(EN_US.Syringe()));
		Register("dirty_syringe", DIRTY_SYRINGE, List.of(EN_US.Syringe(EN_US.Dirty())));
		Register("blood_syringe", BLOOD_SYRINGE, List.of(EN_US.Syringe(EN_US.Blood())));
		Register("confetti_syringe", CONFETTI_SYRINGE, List.of(EN_US.Syringe(EN_US.Confetti())));
		Register("dragon_breath_syringe", DRAGON_BREATH_SYRINGE, List.of(EN_US.Syringe(EN_US.Breath(EN_US.Dragon()))));
		Register("experience_syringe", EXPERIENCE_SYRINGE, List.of(EN_US.Syringe(EN_US.Experience())));
		Register("honey_syringe", HONEY_SYRINGE, List.of(EN_US.Syringe(EN_US.Honey())));
		BloodType.RegisterBloodType(BloodType.HONEY, HONEY_SYRINGE);
		Register("lava_syringe", LAVA_SYRINGE, List.of(EN_US.Syringe(EN_US.Lava())));
		BloodType.RegisterBloodType(BloodType.LAVA, LAVA_SYRINGE);
		Register("magma_cream_syringe", MAGMA_CREAM_SYRINGE, List.of(EN_US.Syringe(EN_US.Cream(EN_US.Magma()))));
		BloodType.RegisterBloodType(MAGMA_CREAM_BLOOD_TYPE, MAGMA_CREAM_SYRINGE);
		Register("mud_syringe", MUD_SYRINGE, List.of(EN_US.Syringe(EN_US.Mud())));
		BloodType.RegisterBloodType(BloodType.MUD, MUD_SYRINGE);
		Register("sap_syringe", SAP_SYRINGE, List.of(EN_US.Syringe(EN_US.Sap())));
		BloodType.RegisterBloodType(BloodType.SAP, SAP_SYRINGE);
		Register("slime_syringe", SLIME_SYRINGE, List.of(EN_US.Syringe(EN_US.Slime())));
		BloodType.RegisterBloodType(SLIME_BLOOD_TYPE, SLIME_SYRINGE);
		Register("blue_slime_syringe", BLUE_SLIME_SYRINGE, List.of(EN_US.Syringe(EN_US.Slime(EN_US.Blue()))));
		BloodType.RegisterBloodType(BLUE_SLIME_BLOOD_TYPE, BLUE_SLIME_SYRINGE);
		Register("pink_slime_syringe", PINK_SLIME_SYRINGE, List.of(EN_US.Syringe(EN_US.Slime(EN_US.Pink()))));
		BloodType.RegisterBloodType(PINK_SLIME_BLOOD_TYPE, PINK_SLIME_SYRINGE);
		Register("water_syringe", WATER_SYRINGE, List.of(EN_US.Syringe(EN_US.Water())));
		//Special Blood Types
		Register("nephal_blood_syringe", NEPHAL_BLOOD_SYRINGE, List.of(EN_US.Syringe(EN_US.Blood(EN_US.Nephal()))));
		BloodType.RegisterBloodType(NEPHAL_BLOOD_TYPE, NEPHAL_BLOOD_SYRINGE);
		Register("vampire_blood_syringe", VAMPIRE_BLOOD_SYRINGE, List.of(EN_US.Syringe(EN_US.Blood(EN_US.Vampire()))));
		BloodType.RegisterBloodType(VAMPIRE_BLOOD_TYPE, VAMPIRE_BLOOD_SYRINGE);
		//</editor-fold>
	}
	private static void RegisterFlowers() {
		Register("minecraft:pink_petals", PINK_PETALS, List.of(EN_US.Petals(EN_US.Pink())));
		//<editor-fold desc="Torchflower">
		Register("minecraft:torchflower_crop", TORCHFLOWER_CROP.asBlock(), List.of(EN_US.Crop(EN_US.Torchflower())));
		Register("minecraft:torchflower_seeds", TORCHFLOWER_CROP.asItem(), List.of(EN_US.Seeds(EN_US.Torchflower())));
		Register("minecraft:torchflower", TORCHFLOWER, List.of(EN_US._Potted(EN_US.Torchflower())));
		//</editor-fold>
		//<editor-fold desc="Pitcher Plant">
		Register("minecraft:pitcher_crop", PITCHER_CROP.asBlock(), List.of(EN_US.Crop(EN_US.Pitcher())));
		Register("minecraft:pitcher_pod", PITCHER_CROP.asItem(), List.of(EN_US.Pod(EN_US.Pitcher())));
		Register("minecraft:pitcher_plant", PITCHER_PLANT, List.of(EN_US.Plant(EN_US.Pitcher())));
		//</editor-fold>
		//<editor-fold desc="Flowers">
		//<editor-fold desc="Minecraft Earth">
		Register("buttercup", BUTTERCUP, List.of(EN_US._Potted(EN_US.Buttercup())));
		Register("pink_daisy", PINK_DAISY, List.of(EN_US._Potted(EN_US.Daisy(EN_US.Pink()))));
		//</editor-fold>
		Register("rose", ROSE, List.of(EN_US._Potted(EN_US.Rose())));
		Register("blue_rose", BLUE_ROSE, List.of(EN_US._Potted(EN_US.Rose(EN_US.Blue()))));
		Register("magenta_tulip", MAGENTA_TULIP, List.of(EN_US._Potted(EN_US.Tulip(EN_US.Magenta()))));
		Register("marigold", MARIGOLD, List.of(EN_US._Potted(EN_US.Marigold())));
		Register("pink_allium", PINK_ALLIUM, List.of(EN_US._Potted(EN_US.Allium(EN_US.Pink()))));
		Register("lavender", LAVENDER, List.of(EN_US._Potted(EN_US.Lavender())));
		Register("hydrangea", HYDRANGEA, List.of(EN_US._Potted(EN_US.Hydrangea())));
		//<editor-fold desc="Orchids">
		Register("indigo_orchid", INDIGO_ORCHID, List.of(EN_US._Potted(EN_US.Orchid(EN_US.Indigo()))));
		Register("magenta_orchid", MAGENTA_ORCHID, List.of(EN_US._Potted(EN_US.Orchid(EN_US.Magenta()))));
		Register("orange_orchid", ORANGE_ORCHID, List.of(EN_US._Potted(EN_US.Orchid(EN_US.Orange()))));
		Register("purple_orchid", PURPLE_ORCHID, List.of(EN_US._Potted(EN_US.Orchid(EN_US.Purple()))));
		Register("red_orchid", RED_ORCHID, List.of(EN_US._Potted(EN_US.Orchid(EN_US.Red()))));
		Register("white_orchid", WHITE_ORCHID, List.of(EN_US._Potted(EN_US.Orchid(EN_US.White()))));
		Register("yellow_orchid", YELLOW_ORCHID, List.of(EN_US._Potted(EN_US.Orchid(EN_US.Yellow()))));
		//</editor-fold>
		Register("paeonia", PAEONIA, List.of(EN_US._Potted(EN_US.Paeonia())));
		Register("aster", ASTER, List.of(EN_US._Potted(EN_US.Aster())));
		//<editor-fold desc="Tall Flowers">
		Register("amaranth", AMARANTH, List.of(EN_US.Amaranth()));
		Register("blue_rose_bush", BLUE_ROSE_BUSH, List.of(EN_US.Bush(EN_US.Rose(EN_US.Blue()))));
		Register("tall_allium", TALL_ALLIUM, List.of(EN_US.Allium(EN_US.Tall())));
		Register("tall_pink_allium", TALL_PINK_ALLIUM, List.of(EN_US.Allium(EN_US.Pink(EN_US.Tall()))));
		//</editor-fold>
		Register("vanilla_flower", VANILLA_FLOWER, List.of(EN_US._Potted(EN_US.Flower(EN_US.Vanilla()))));
		Register("tall_vanilla", TALL_VANILLA, List.of(EN_US.Flower(EN_US.Vanilla(EN_US.Tall()))));
		Register("vanilla", VANILLA, List.of(EN_US.Vanilla()));
		CompostingChanceRegistry.INSTANCE.add(VANILLA, 0.2f);
		//</editor-fold>
		//<editor-fold desc="Flower Parts">
		//<editor-fold desc="Minecraft Earth">
		Register("buttercup", BUTTERCUP_PARTS, List.of(EN_US._FlowerParts(EN_US.Buttercup())));
		Register("pink_daisy", PINK_DAISY_PARTS, List.of(EN_US._FlowerParts(EN_US.Daisy(EN_US.Pink()))));
		//</editor-fold>
		Register("rose", ROSE_PARTS, List.of(EN_US._FlowerParts(EN_US.Rose())));
		Register("blue_rose", BLUE_ROSE_PARTS, List.of(EN_US._FlowerParts(EN_US.Rose(EN_US.Blue()))));
		Register("magenta_tulip", MAGENTA_TULIP_PARTS, List.of(EN_US._FlowerParts(EN_US.Tulip(EN_US.Magenta()))));
		Register("marigold", MARIGOLD_PARTS, List.of(EN_US._FlowerParts(EN_US.Marigold())));
		Register("pink_allium", PINK_ALLIUM_PARTS, List.of(EN_US._FlowerParts(EN_US.Allium(EN_US.Pink()))));
		Register("lavender", LAVENDER_PARTS, List.of(EN_US._FlowerParts(EN_US.Lavender())));
		Register("hydrangea", HYDRANGEA_PARTS, List.of(EN_US._FlowerParts(EN_US.Hydrangea())));
		//<editor-fold desc="Orchids">
		Register("indigo_orchid", INDIGO_ORCHID_PARTS, List.of(EN_US._FlowerParts(EN_US.Orchid(EN_US.Indigo()))));
		Register("magenta_orchid", MAGENTA_ORCHID_PARTS, List.of(EN_US._FlowerParts(EN_US.Orchid(EN_US.Magenta()))));
		Register("orange_orchid", ORANGE_ORCHID_PARTS, List.of(EN_US._FlowerParts(EN_US.Orchid(EN_US.Orange()))));
		Register("purple_orchid", PURPLE_ORCHID_PARTS, List.of(EN_US._FlowerParts(EN_US.Orchid(EN_US.Purple()))));
		Register("red_orchid", RED_ORCHID_PARTS, List.of(EN_US._FlowerParts(EN_US.Orchid(EN_US.Red()))));
		Register("white_orchid", WHITE_ORCHID_PARTS, List.of(EN_US._FlowerParts(EN_US.Orchid(EN_US.White()))));
		Register("yellow_orchid", YELLOW_ORCHID_PARTS, List.of(EN_US._FlowerParts(EN_US.Orchid(EN_US.Yellow()))));
		//</editor-fold>
		Register("paeonia", PAEONIA_PARTS, List.of(EN_US._FlowerParts(EN_US.Paeonia())));
		Register("aster", ASTER_PARTS, List.of(EN_US._FlowerParts(EN_US.Aster())));
		//<editor-fold desc="Tall Flowers">
		Register("amaranth", AMARANTH_PARTS, List.of(EN_US._FlowerParts(EN_US.Amaranth())));
		//</editor-fold>
		Register("vanilla", VANILLA_PARTS, List.of(EN_US._FlowerParts(EN_US.Vanilla())));
		//<editor-fold desc="Vanilla Minecraft">
		Register("allium", ALLIUM_PARTS, List.of(EN_US._FlowerParts(EN_US.Allium())));
		Register("azure_bluet", AZURE_BLUET_PARTS, List.of(EN_US._FlowerParts(EN_US.Bluet(EN_US.Azure()))));
		Register("blue_orchid", BLUE_ORCHID_PARTS, List.of(EN_US._FlowerParts(EN_US.Orchid(EN_US.Blue()))));
		Register("cornflower", CORNFLOWER_PARTS, List.of(EN_US._FlowerParts(EN_US.Cornflower())));
		Register("dandelion", DANDELION_PARTS, List.of(EN_US._FlowerParts(EN_US.Dandelion())));
		Register("lilac", LILAC_PARTS, List.of(EN_US._FlowerParts(EN_US.Lilac())));
		Register("lily_of_the_valley", LILY_OF_THE_VALLEY_PARTS, List.of(EN_US._FlowerParts(EN_US.Valley(EN_US.the(EN_US.of(EN_US.Lily()))))));
		Register("orange_tulip", ORANGE_TULIP_PARTS, List.of(EN_US._FlowerParts(EN_US.Tulip(EN_US.Orange()))));
		Register("oxeye_daisy", OXEYE_DAISY_PARTS, List.of(EN_US._FlowerParts(EN_US.Daisy(EN_US.Oxeye()))));
		Register("peony", PEONY_PARTS, List.of(EN_US._FlowerParts(EN_US.Peony())));
		Register("pink_tulip", PINK_TULIP_PARTS, List.of(EN_US._FlowerParts(EN_US.Tulip(EN_US.Pink()))));
		Register("poppy", POPPY_PARTS, List.of(EN_US._FlowerParts(EN_US.Poppy())));
		Register("red_tulip", RED_TULIP_PARTS, List.of(EN_US._FlowerParts(EN_US.Tulip(EN_US.Red()))));
		Register("sunflower", SUNFLOWER_PARTS, List.of(EN_US._FlowerParts(EN_US.Sunflower())));
		Register("white_tulip", WHITE_TULIP_PARTS, List.of(EN_US._FlowerParts(EN_US.Tulip(EN_US.White()))));
		Register("wither_rose", WITHER_ROSE_PARTS, List.of(EN_US._FlowerParts(EN_US.Rose(EN_US.Wither()))));
		//</editor-fold>
		//Special Flower Petals
		Register("azalea_petals", AZALEA_PETALS, List.of(EN_US.Petals(EN_US.Azalea())));
		CompostingChanceRegistry.INSTANCE.add(AZALEA_PETALS, 0.325F);
		Register("spore_blossom_petal", SPORE_BLOSSOM_PETAL, List.of(EN_US.Petal(EN_US.Blossom(EN_US.Spore()))));
		CompostingChanceRegistry.INSTANCE.add(SPORE_BLOSSOM_PETAL, 0.1625F);
		Register("cassia_petals", CASSIA_PETALS, List.of(EN_US.Petals(EN_US.Cassia())));
		CompostingChanceRegistry.INSTANCE.add(CASSIA_PETALS, 0.325F);
		Register("pink_dogwood_petals", PINK_DOGWOOD_PETALS, List.of(EN_US.Petals(EN_US.Dogwood(EN_US.Pink()))));
		CompostingChanceRegistry.INSTANCE.add(PINK_DOGWOOD_PETALS, 0.325F);
		Register("pale_dogwood_petals", PALE_DOGWOOD_PETALS, List.of(EN_US.Petals(EN_US.Dogwood(EN_US.Pale()))));
		CompostingChanceRegistry.INSTANCE.add(PALE_DOGWOOD_PETALS, 0.325F);
		Register("white_dogwood_petals", WHITE_DOGWOOD_PETALS, List.of(EN_US.Petals(EN_US.Dogwood(EN_US.White()))));
		CompostingChanceRegistry.INSTANCE.add(WHITE_DOGWOOD_PETALS, 0.325F);
		//</editor-fold>
	}
	private static void RegisterFood() {
		//<editor-fold desc="Food">
		Register("coffee_plant", COFFEE_PLANT.asBlock(), List.of(EN_US.Plant(EN_US.Coffee())));
		Register("coffee_cherry", COFFEE_PLANT.asItem(), List.of(EN_US.Cherry(EN_US.Coffee())));
		Register("coffee_beans", COFFEE_BEANS, List.of(EN_US.Beans(EN_US.Coffee())));
		CompostingChanceRegistry.INSTANCE.add(COFFEE_BEANS, 0.65F);
		Register("coffee", COFFEE, List.of(EN_US.Coffee()));
		Register("black_coffee", BLACK_COFFEE, List.of(EN_US.Coffee(EN_US.Black())));
		Register("cinnamon", CINNAMON, List.of(EN_US.Cinnamon()));
		CompostingChanceRegistry.INSTANCE.add(CINNAMON, 0.2f);
		Register("cherry", CHERRY, List.of(EN_US.Cherry()));
		CompostingChanceRegistry.INSTANCE.add(CHERRY, 0.45F);
		Register("green_apple", GREEN_APPLE, List.of(EN_US.Apple(EN_US.Green())));
		CompostingChanceRegistry.INSTANCE.add(GREEN_APPLE, 0.65F);
		Register("strawberry_bush", STRAWBERRY_BUSH, List.of(EN_US.Bush(EN_US.Strawberry())));
		FlammableBlockRegistry.getDefaultInstance().add(STRAWBERRY_BUSH, 60, 100);
		Register("strawberry", STRAWBERRY, List.of(EN_US.Strawberry()));
		CompostingChanceRegistry.INSTANCE.add(STRAWBERRY, 0.3F);
		Register("snickerdoodle", SNICKERDOODLE, List.of(EN_US.Snickerdoodle()));
		CompostingChanceRegistry.INSTANCE.add(SNICKERDOODLE, 0.85F);
		Register("chocolate_cookie", CHOCOLATE_COOKIE, List.of(EN_US.Cookie(EN_US.Chocolate())));
		CompostingChanceRegistry.INSTANCE.add(CHOCOLATE_COOKIE, 0.85F);
		Register("chorus_cookie", CHORUS_COOKIE, List.of(EN_US.Cookie(EN_US.Chorus())));
		CompostingChanceRegistry.INSTANCE.add(CHORUS_COOKIE, 0.85F);
		Register("confetti_cookie", CONFETTI_COOKIE, List.of(EN_US.Cookie(EN_US.Confetti())));
		CompostingChanceRegistry.INSTANCE.add(CONFETTI_COOKIE, 0.85F);
		Register("cinnamon_roll", CINNAMON_ROLL, List.of(EN_US.Roll(EN_US.Cinnamon())));
		CompostingChanceRegistry.INSTANCE.add(CINNAMON_ROLL, 0.85F);
		Register("tomato_soup", TOMATO_SOUP, List.of(EN_US.Soup(EN_US.Tomato())));
		Register("hotdog", HOTDOG, List.of(EN_US.Hotdog()));
		Register("ramen", RAMEN, List.of(EN_US.Ramen()));
		Register("stir_fry", STIR_FRY, List.of(EN_US.Fry(EN_US.Stir())));
		//Dairy
		Register("cheese", CHEESE, List.of(EN_US.Cheese()));
		Register("grilled_cheese", GRILLED_CHEESE, List.of(EN_US.Cheese(EN_US.Grilled())));
		Register("cottage_cheese_bowl", COTTAGE_CHEESE_BOWL, List.of(EN_US.Bowl(EN_US.Cheese(EN_US.Cottage()))));
		//Dairy Sweets
		Register("egg_nog", EGG_NOG, List.of(EN_US.Nog(EN_US.Egg())));
		Register("milkshake", MILKSHAKE, List.of(EN_US.Milkshake()));
		Register("chocolate_milkshake", CHOCOLATE_MILKSHAKE, List.of(EN_US.Milkshake(EN_US.Chocolate())));
		Register("coffee_milkshake", COFFEE_MILKSHAKE, List.of(EN_US.Milkshake(EN_US.Coffee())));
		Register("strawberry_milkshake", STRAWBERRY_MILKSHAKE, List.of(EN_US.Milkshake(EN_US.Strawberry())));
		Register("vanilla_milkshake", VANILLA_MILKSHAKE, List.of(EN_US.Milkshake(EN_US.Vanilla())));
		Register("chocolate_chip_milkshake", CHOCOLATE_CHIP_MILKSHAKE, List.of(EN_US.Milkshake(EN_US.Chip(EN_US.Chocolate()))));
		Register("ice_cream", ICE_CREAM, List.of(EN_US.Cream(EN_US.Ice())));
		Register("chocolate_ice_cream", CHOCOLATE_ICE_CREAM, List.of(EN_US.Cream(EN_US.Ice(EN_US.Chocolate()))));
		Register("coffee_ice_cream", COFFEE_ICE_CREAM, List.of(EN_US.Cream(EN_US.Ice(EN_US.Coffee()))));
		Register("strawberry_ice_cream", STRAWBERRY_ICE_CREAM, List.of(EN_US.Cream(EN_US.Ice(EN_US.Strawberry()))));
		Register("vanilla_ice_cream", VANILLA_ICE_CREAM, List.of(EN_US.Cream(EN_US.Ice(EN_US.Vanilla()))));
		Register("chocolate_chip_ice_cream", CHOCOLATE_CHIP_ICE_CREAM, List.of(EN_US.Cream(EN_US.Ice(EN_US.Chip(EN_US.Chocolate())))));
		//Candy
		Register("cinnamon_bean", CINNAMON_BEAN, List.of(EN_US.Bean(EN_US.Cinnamon())));
		Register("pink_cotton_candy", PINK_COTTON_CANDY, List.of(EN_US.Candy(EN_US.Cotton(EN_US.Pink()))));
		Register("blue_cotton_candy", BLUE_COTTON_CANDY, List.of(EN_US.Candy(EN_US.Cotton(EN_US.Blue()))));
		Register("candy_cane", CANDY_CANE, List.of(EN_US.Cane(EN_US.Candy())));
		Register("candy_corn", CANDY_CORN, List.of(EN_US.Corn(EN_US.Candy())));
		Register("caramel", CARAMEL, List.of(EN_US.Caramel()));
		Register("caramel_apple", CARAMEL_APPLE, List.of(EN_US.Apple(EN_US.Caramel())));
		Register("lollipop", LOLLIPOP, List.of(EN_US.Lollipop()));
		Register("milk_chocolate", MILK_CHOCOLATE, List.of(EN_US.Chocolate(EN_US.Milk())));
		Register("dark_chocolate", DARK_CHOCOLATE, List.of(EN_US.Chocolate(EN_US.Dark())));
		Register("white_chocolate", WHITE_CHOCOLATE, List.of(EN_US.Chocolate(EN_US.White())));
		Register("marshmallow", MARSHMALLOW, List.of(EN_US.Marshmallow()));
		Register("roast_marshmallow", ROAST_MARSHMALLOW, List.of(EN_US.Marshmallow(EN_US.Roast())));
		Register("marshmallow_on_stick", MARSHMALLOW_ON_STICK, List.of(EN_US.Stick(EN_US.a(EN_US.on(EN_US.Marshmallow())))));
		Register("roast_marshmallow_on_stick", ROAST_MARSHMALLOW_ON_STICK, List.of(EN_US.Stick(EN_US.a(EN_US.on(EN_US.Marshmallow(EN_US.Roast()))))));
		for (DyeColor color : DyeColor.values()) {
			Register(color.getName() + "_rock_candy", ROCK_CANDIES.get(color), List.of(EN_US.Candy(EN_US.Rock(EN_US.Color(color)))));
		}
		//</editor-fold>
		//<editor-fold desc="Grapes">
		Register("grape_leaves", GRAPE_LEAVES, List.of(EN_US.Leaves(EN_US.Grape())));
		Register("grape_sapling", GRAPE_SAPLING, List.of(EN_US._Potted(EN_US.Sapling(EN_US.Grape()))));
		Register("grape_vines", GRAPE_VINES, List.of(EN_US.Vines(EN_US.Grape())));
		ModDatagen.Cache.Drops.put(GRAPE_VINES, DropTable.BerryDrops(GRAPES));
		FlammableBlockRegistry.getDefaultInstance().add(GRAPE_VINES, 15, 60);
		Register("grape_vines_plant", GRAPE_VINES_PLANT, List.of(EN_US.Plant(EN_US.Vines(EN_US.Grape()))));
		ModDatagen.Cache.Drops.put(GRAPE_VINES_PLANT, DropTable.BerryDrops(GRAPES));
		FlammableBlockRegistry.getDefaultInstance().add(GRAPE_VINES_PLANT, 15, 60);
		Register("grapes", GRAPES, List.of(EN_US.Grapes()));
		CompostingChanceRegistry.INSTANCE.add(GRAPES, 0.3F);
		//</editor-fold>
		//<editor-fold desc="Golden">
		Register("golden_potato", GOLDEN_POTATO, List.of(EN_US.Potato(EN_US.Golden())));
		Register("golden_baked_potato", GOLDEN_BAKED_POTATO, List.of(EN_US.BakedPotato(EN_US.Golden())));
		Register("golden_beetroot", GOLDEN_BEETROOT, List.of(EN_US.Beetroot(EN_US.Golden())));
		Register("golden_chorus_fruit", GOLDEN_CHORUS_FRUIT, List.of(EN_US.Fruit(EN_US.Chorus(EN_US.Golden()))));
		Register("golden_tomato", GOLDEN_TOMATO, List.of(EN_US.Tomato(EN_US.Golden())));
		Register("golden_onion", GOLDEN_ONION, List.of(EN_US.Onion(EN_US.Golden())));
		Register("golden_egg", GOLDEN_EGG, List.of(EN_US.Egg(EN_US.Golden())));
		//</editor-fold>
		//<editor-fold desc="Rotten">
		Register("poisonous_carrot", POISONOUS_CARROT, List.of(EN_US.Carrot(EN_US.Poisonous())));
		Register("poisonous_beetroot", POISONOUS_BEETROOT, List.of(EN_US.Beetroot(EN_US.Poisonous())));
		Register("poisonous_glow_berries", POISONOUS_GLOW_BERRIES, List.of(EN_US.Berries(EN_US.Glow(EN_US.Poisonous()))));
		Register("poisonous_sweet_berries", POISONOUS_SWEET_BERRIES, List.of(EN_US.Berries(EN_US.Sweet(EN_US.Poisonous()))));
		Register("poisonous_tomato", POISONOUS_TOMATO, List.of(EN_US.Tomato(EN_US.Poisonous())));
		Register("wilted_cabbage", WILTED_CABBAGE, List.of(EN_US.Cabbage(EN_US.Wilted())));
		Register("wilted_onion", WILTED_ONION, List.of(EN_US.Onion(EN_US.Wilted())));
		Register("wilted_kelp", WILTED_KELP, List.of(EN_US.Kelp(EN_US.Wilted())));
		Register("moldy_bread", MOLDY_BREAD, List.of(EN_US.Bread(EN_US.Moldy())));
		Register("moldy_cookie", MOLDY_COOKIE, List.of(EN_US.Cookie(EN_US.Moldy())));
		Register("rotten_pumpkin_pie", ROTTEN_PUMPKIN_PIE, List.of(EN_US.Pie(EN_US.Pumpkin(EN_US.Rotten()))));
		Register("spoiled_egg", SPOILED_EGG, List.of(EN_US.Egg(EN_US.Spoiled())));
		Register("rotten_beef", ROTTEN_BEEF, List.of(EN_US.Beef(EN_US.Rotten())));
		Register("rotten_chevon", ROTTEN_CHEVON, List.of(EN_US.Chevon(EN_US.Rotten())));
		Register("rotten_chicken", ROTTEN_CHICKEN, List.of(EN_US.Chicken(EN_US.Rotten())));
		Register("rotten_cod", ROTTEN_COD, List.of(EN_US.Cod(EN_US.Rotten())));
		Register("rotten_mutton", ROTTEN_MUTTON, List.of(EN_US.Mutton(EN_US.Rotten())));
		Register("rotten_porkchop", ROTTEN_PORKCHOP, List.of(EN_US.Porkchop(EN_US.Rotten())));
		Register("rotten_rabbit", ROTTEN_RABBIT, List.of(EN_US.Rabbit(EN_US.Rotten())));
		Register("rotten_salmon", ROTTEN_SALMON, List.of(EN_US.Salmon(EN_US.Rotten())));
		//</editor-fold>
		//<editor-fold desc="Juices">
		Register("apple_cider", APPLE_CIDER, List.of(EN_US.Cider(EN_US.Apple())));
		Register("golden_apple_cider", GOLDEN_APPLE_CIDER, List.of(EN_US.Cider(EN_US.Apple(EN_US.Golden()))));
		Register("juicer", JUICER, List.of(EN_US.Juicer()));
		RegisterJuice("apple_juice", APPLE_JUICE, Items.APPLE, List.of(EN_US.Juice(EN_US.Apple())));
		JuicerBlock.JUICE_MAP.put(() -> GREEN_APPLE, APPLE_JUICE);
		RegisterJuice("beetroot_juice", BEETROOT_JUICE, Items.BEETROOT, List.of(EN_US.Juice(EN_US.Beetroot())));
		//TODO: Hook into better nether explicitly
		RegisterJuice("black_apple_juice", BLACK_APPLE_JUICE, () -> Registry.ITEM.get(new Identifier("betternether:black_apple")), List.of(EN_US.Juice(EN_US.Apple(EN_US.Black()))));
		RegisterJuice("cabbage_juice", CABBAGE_JUICE, ItemsRegistry.CABBAGE.get(), List.of(EN_US.Juice(EN_US.Cabbage())));
		RegisterJuice("cactus_juice", CACTUS_JUICE, Items.CACTUS, List.of(EN_US.Juice(EN_US.Cactus())));
		RegisterJuice("carrot_juice", CARROT_JUICE, Items.CARROT, List.of(EN_US.Juice(EN_US.Carrot())));
		RegisterJuice("cherry_juice", CHERRY_JUICE, CHERRY, List.of(EN_US.Juice(EN_US.Cherry())));
		RegisterJuice("chorus_juice", CHORUS_JUICE, Items.CHORUS_FRUIT, List.of(EN_US.Juice(EN_US.Chorus())));
		RegisterJuice("glow_berry_juice", GLOW_BERRY_JUICE, Items.GLOW_BERRIES, List.of(EN_US.Juice(EN_US.Berry(EN_US.Glow()))));
		RegisterJuice("golden_apple_juice", GOLDEN_APPLE_JUICE, Items.GOLDEN_APPLE, List.of(EN_US.Juice(EN_US.Apple(EN_US.Golden()))));
		RegisterJuice("enchanted_golden_apple_juice", ENCHANTED_GOLDEN_APPLE_JUICE, Items.ENCHANTED_GOLDEN_APPLE, List.of(EN_US.Juice(EN_US.Apple(EN_US.Golden(EN_US.Enchanted())))));
		RegisterJuice("golden_beetroot_juice", GOLDEN_BEETROOT_JUICE, GOLDEN_BEETROOT, List.of(EN_US.Juice(EN_US.Beetroot(EN_US.Golden()))));
		RegisterJuice("golden_carrot_juice", GOLDEN_CARROT_JUICE, Items.GOLDEN_CARROT, List.of(EN_US.Juice(EN_US.Carrot(EN_US.Golden()))));
		RegisterJuice("golden_chorus_juice", GOLDEN_CHORUS_JUICE, GOLDEN_CHORUS_FRUIT, List.of(EN_US.Juice(EN_US.Chorus(EN_US.Golden()))));
		RegisterJuice("golden_onion_juice", GOLDEN_ONION_JUICE, GOLDEN_ONION, List.of(EN_US.Juice(EN_US.Onion(EN_US.Golden()))));
		RegisterJuice("golden_potato_juice", GOLDEN_POTATO_JUICE, GOLDEN_POTATO, List.of(EN_US.Juice(EN_US.Potato(EN_US.Golden()))));
		RegisterJuice("golden_tomato_juice", GOLDEN_TOMATO_JUICE, GOLDEN_TOMATO, List.of(EN_US.Juice(EN_US.Tomato(EN_US.Golden()))));
		RegisterJuice("grape_juice", GRAPE_JUICE, GRAPES, List.of(EN_US.Juice(EN_US.Grape())));
		RegisterJuice("kelp_juice", KELP_JUICE, Items.KELP, List.of(EN_US.Juice(EN_US.Kelp())));
		RegisterJuice("melon_juice", MELON_JUICE, Items.MELON_SLICE, List.of(EN_US.Juice(EN_US.Melon())));
		RegisterJuice("onion_juice", ONION_JUICE, ItemsRegistry.ONION.get(), List.of(EN_US.Juice(EN_US.Onion())));
		RegisterJuice("potato_juice", POTATO_JUICE, Items.POTATO, List.of(EN_US.Juice(EN_US.Potato())));
		RegisterJuice("pumpkin_juice", PUMPKIN_JUICE, Items.PUMPKIN, List.of(EN_US.Juice(EN_US.Pumpkin())));
		JuicerBlock.JUICE_MAP.put(ModBase.WHITE_PUMPKIN::asItem, PUMPKIN_JUICE);
		RegisterJuice("sea_pickle_juice", SEA_PICKLE_JUICE, Items.SEA_PICKLE, List.of(EN_US.Juice(EN_US.Pickle(EN_US.Sea()))));
		RegisterJuice("strawberry_juice", STRAWBERRY_JUICE, STRAWBERRY, List.of(EN_US.Juice(EN_US.Strawberry())));
		RegisterJuice("sweet_berry_juice", SWEET_BERRY_JUICE, Items.SWEET_BERRIES, List.of(EN_US.Juice(EN_US.Berry(EN_US.Sweet()))));
		RegisterJuice("tomato_juice", TOMATO_JUICE, ItemsRegistry.TOMATO.get(), List.of(EN_US.Juice(EN_US.Tomato())));
		//Smoothies
		Register("apple_smoothie", APPLE_SMOOTHIE, List.of(EN_US.Smoothie(EN_US.Apple())));
		Register("beetroot_smoothie", BEETROOT_SMOOTHIE, List.of(EN_US.Smoothie(EN_US.Beetroot())));
		Register("black_apple_smoothie", BLACK_APPLE_SMOOTHIE, List.of(EN_US.Smoothie(EN_US.Apple(EN_US.Black()))));
		Register("cabbage_smoothie", CABBAGE_SMOOTHIE, List.of(EN_US.Smoothie(EN_US.Cabbage())));
		Register("cactus_smoothie", CACTUS_SMOOTHIE, List.of(EN_US.Smoothie(EN_US.Cactus())));
		Register("carrot_smoothie", CARROT_SMOOTHIE, List.of(EN_US.Smoothie(EN_US.Carrot())));
		Register("cherry_smoothie", CHERRY_SMOOTHIE, List.of(EN_US.Smoothie(EN_US.Cherry())));
		Register("chorus_smoothie", CHORUS_SMOOTHIE, List.of(EN_US.Smoothie(EN_US.Chorus())));
		Register("glow_berry_smoothie", GLOW_BERRY_SMOOTHIE, List.of(EN_US.Smoothie(EN_US.Berry(EN_US.Glow()))));
		Register("golden_apple_smoothie", GOLDEN_APPLE_SMOOTHIE, List.of(EN_US.Smoothie(EN_US.Apple(EN_US.Golden()))));
		Register("enchanted_golden_apple_smoothie", ENCHANTED_GOLDEN_APPLE_SMOOTHIE, List.of(EN_US.Smoothie(EN_US.Apple(EN_US.Golden(EN_US.Enchanted())))));
		Register("golden_beetroot_smoothie", GOLDEN_BEETROOT_SMOOTHIE, List.of(EN_US.Smoothie(EN_US.Beetroot(EN_US.Golden()))));
		Register("golden_carrot_smoothie", GOLDEN_CARROT_SMOOTHIE, List.of(EN_US.Smoothie(EN_US.Carrot(EN_US.Golden()))));
		Register("golden_chorus_smoothie", GOLDEN_CHORUS_SMOOTHIE, List.of(EN_US.Smoothie(EN_US.Chorus(EN_US.Golden()))));
		Register("golden_onion_smoothie", GOLDEN_ONION_SMOOTHIE, List.of(EN_US.Smoothie(EN_US.Onion(EN_US.Golden()))));
		Register("golden_potato_smoothie", GOLDEN_POTATO_SMOOTHIE, List.of(EN_US.Smoothie(EN_US.Potato(EN_US.Golden()))));
		Register("golden_tomato_smoothie", GOLDEN_TOMATO_SMOOTHIE, List.of(EN_US.Smoothie(EN_US.Tomato(EN_US.Golden()))));
		Register("grape_smoothie", GRAPE_SMOOTHIE, List.of(EN_US.Smoothie(EN_US.Grape())));
		Register("kelp_smoothie", KELP_SMOOTHIE, List.of(EN_US.Smoothie(EN_US.Kelp())));
		Register("melon_smoothie", MELON_SMOOTHIE, List.of(EN_US.Smoothie(EN_US.Melon())));
		Register("onion_smoothie", ONION_SMOOTHIE, List.of(EN_US.Smoothie(EN_US.Onion())));
		Register("potato_smoothie", POTATO_SMOOTHIE, List.of(EN_US.Smoothie(EN_US.Potato())));
		Register("pumpkin_smoothie", PUMPKIN_SMOOTHIE, List.of(EN_US.Smoothie(EN_US.Pumpkin())));
		Register("sea_pickle_smoothie", SEA_PICKLE_SMOOTHIE, List.of(EN_US.Smoothie(EN_US.Pickle(EN_US.Sea()))));
		Register("strawberry_smoothie", STRAWBERRY_SMOOTHIE, List.of(EN_US.Smoothie(EN_US.Strawberry())));
		Register("sweet_berry_smoothie", SWEET_BERRY_SMOOTHIE, List.of(EN_US.Smoothie(EN_US.Berry(EN_US.Sweet()))));
		Register("tomato_smoothie", TOMATO_SMOOTHIE, List.of(EN_US.Smoothie(EN_US.Tomato())));
		//Juicer (Misc)
		JuicerBlock.JUICE_MAP.put(() -> Items.HONEY_BLOCK, Items.HONEY_BOTTLE);
		//</editor-fold>
		//<editor-fold desc="Cakes">
		Register("carrot_cake", CARROT_CAKE.CAKE, List.of(EN_US.Cake(EN_US.Carrot())));
		Register("carrot_candle_cake", CARROT_CAKE.CANDLE_CAKE, List.of(EN_US.Candle(EN_US.with(EN_US.Cake(EN_US.Carrot())))));
		Register("carrot_soul_candle_cake", CARROT_CAKE.SOUL_CANDLE_CAKE, List.of(EN_US.Candle(EN_US.Soul(EN_US.with(EN_US.Cake(EN_US.Carrot()))))));
		Register("carrot_ender_candle_cake", CARROT_CAKE.ENDER_CANDLE_CAKE, List.of(EN_US.Candle(EN_US.Ender(EN_US.with(EN_US.Cake(EN_US.Carrot()))))));
		Register("carrot_netherrack_candle_cake", CARROT_CAKE.NETHERRACK_CANDLE_CAKE, List.of(EN_US.Candle(EN_US.Netherrack(EN_US.with(EN_US.Cake(EN_US.Carrot()))))));
		for (DyeColor color : DyeColor.values()) {
			Register(color + "_carrot_candle_cake", CARROT_CAKE.CANDLE_CAKES.get(color), List.of(EN_US.Candle(EN_US.Color(color, EN_US.with(EN_US.Cake(EN_US.Carrot()))))));
		}
		Register("chocolate_cake", CHOCOLATE_CAKE.CAKE, List.of(EN_US.Cake(EN_US.Chocolate())));
		Register("chocolate_candle_cake", CHOCOLATE_CAKE.CANDLE_CAKE, List.of(EN_US.Candle(EN_US.with(EN_US.Cake(EN_US.Chocolate())))));
		Register("chocolate_soul_candle_cake", CHOCOLATE_CAKE.SOUL_CANDLE_CAKE, List.of(EN_US.Candle(EN_US.Soul(EN_US.with(EN_US.Cake(EN_US.Chocolate()))))));
		Register("chocolate_ender_candle_cake", CHOCOLATE_CAKE.ENDER_CANDLE_CAKE, List.of(EN_US.Candle(EN_US.Ender(EN_US.with(EN_US.Cake(EN_US.Chocolate()))))));
		Register("chocolate_netherrack_candle_cake", CHOCOLATE_CAKE.NETHERRACK_CANDLE_CAKE, List.of(EN_US.Candle(EN_US.Netherrack(EN_US.with(EN_US.Cake(EN_US.Chocolate()))))));
		for (DyeColor color : DyeColor.values()) {
			Register(color + "_chocolate_candle_cake", CHOCOLATE_CAKE.CANDLE_CAKES.get(color), List.of(EN_US.Candle(EN_US.Color(color, EN_US.with(EN_US.Cake(EN_US.Chocolate()))))));
		}
		Register("chorus_cake", CHORUS_CAKE.CAKE, List.of(EN_US.Cake(EN_US.Chorus())));
		Register("chorus_candle_cake", CHORUS_CAKE.CANDLE_CAKE, List.of(EN_US.Candle(EN_US.with(EN_US.Cake(EN_US.Chorus())))));
		Register("chorus_soul_candle_cake", CHORUS_CAKE.SOUL_CANDLE_CAKE, List.of(EN_US.Candle(EN_US.Soul(EN_US.with(EN_US.Cake(EN_US.Chorus()))))));
		Register("chorus_ender_candle_cake", CHORUS_CAKE.ENDER_CANDLE_CAKE, List.of(EN_US.Candle(EN_US.Ender(EN_US.with(EN_US.Cake(EN_US.Chorus()))))));
		Register("chorus_netherrack_candle_cake", CHORUS_CAKE.NETHERRACK_CANDLE_CAKE, List.of(EN_US.Candle(EN_US.Netherrack(EN_US.with(EN_US.Cake(EN_US.Chorus()))))));
		for (DyeColor color : DyeColor.values()) {
			Register(color + "_chorus_candle_cake", CHORUS_CAKE.CANDLE_CAKES.get(color), List.of(EN_US.Candle(EN_US.Color(color, EN_US.with(EN_US.Cake(EN_US.Chorus()))))));
		}
		Register("coffee_cake", COFFEE_CAKE.CAKE, List.of(EN_US.Cake(EN_US.Coffee())));
		Register("coffee_candle_cake", COFFEE_CAKE.CANDLE_CAKE, List.of(EN_US.Candle(EN_US.with(EN_US.Cake(EN_US.Coffee())))));
		Register("coffee_soul_candle_cake", COFFEE_CAKE.SOUL_CANDLE_CAKE, List.of(EN_US.Candle(EN_US.Soul(EN_US.with(EN_US.Cake(EN_US.Coffee()))))));
		Register("coffee_ender_candle_cake", COFFEE_CAKE.ENDER_CANDLE_CAKE, List.of(EN_US.Candle(EN_US.Ender(EN_US.with(EN_US.Cake(EN_US.Coffee()))))));
		Register("coffee_netherrack_candle_cake", COFFEE_CAKE.NETHERRACK_CANDLE_CAKE, List.of(EN_US.Candle(EN_US.Netherrack(EN_US.with(EN_US.Cake(EN_US.Coffee()))))));
		for (DyeColor color : DyeColor.values()) {
			Register(color + "_coffee_candle_cake", COFFEE_CAKE.CANDLE_CAKES.get(color), List.of(EN_US.Candle(EN_US.Color(color, EN_US.with(EN_US.Cake(EN_US.Coffee()))))));
		}
		Register("confetti_cake", CONFETTI_CAKE.CAKE, List.of(EN_US.Cake(EN_US.Confetti())));
		Register("confetti_candle_cake", CONFETTI_CAKE.CANDLE_CAKE, List.of(EN_US.Candle(EN_US.with(EN_US.Cake(EN_US.Confetti())))));
		Register("confetti_soul_candle_cake", CONFETTI_CAKE.SOUL_CANDLE_CAKE, List.of(EN_US.Candle(EN_US.Soul(EN_US.with(EN_US.Cake(EN_US.Confetti()))))));
		Register("confetti_ender_candle_cake", CONFETTI_CAKE.ENDER_CANDLE_CAKE, List.of(EN_US.Candle(EN_US.Ender(EN_US.with(EN_US.Cake(EN_US.Confetti()))))));
		Register("confetti_netherrack_candle_cake", CONFETTI_CAKE.NETHERRACK_CANDLE_CAKE, List.of(EN_US.Candle(EN_US.Netherrack(EN_US.with(EN_US.Cake(EN_US.Confetti()))))));
		for (DyeColor color : DyeColor.values()) {
			Register(color + "_confetti_candle_cake", CONFETTI_CAKE.CANDLE_CAKES.get(color), List.of(EN_US.Candle(EN_US.Color(color, EN_US.with(EN_US.Cake(EN_US.Confetti()))))));
		}
		Register("strawberry_cake", STRAWBERRY_CAKE.CAKE, List.of(EN_US.Cake(EN_US.Strawberry())));
		Register("strawberry_candle_cake", STRAWBERRY_CAKE.CANDLE_CAKE, List.of(EN_US.Candle(EN_US.with(EN_US.Cake(EN_US.Strawberry())))));
		Register("strawberry_soul_candle_cake", STRAWBERRY_CAKE.SOUL_CANDLE_CAKE, List.of(EN_US.Candle(EN_US.Soul(EN_US.with(EN_US.Cake(EN_US.Strawberry()))))));
		Register("strawberry_ender_candle_cake", STRAWBERRY_CAKE.ENDER_CANDLE_CAKE, List.of(EN_US.Candle(EN_US.Ender(EN_US.with(EN_US.Cake(EN_US.Strawberry()))))));
		Register("strawberry_netherrack_candle_cake", STRAWBERRY_CAKE.NETHERRACK_CANDLE_CAKE, List.of(EN_US.Candle(EN_US.Netherrack(EN_US.with(EN_US.Cake(EN_US.Strawberry()))))));
		for (DyeColor color : DyeColor.values()) {
			Register(color + "_strawberry_candle_cake", STRAWBERRY_CAKE.CANDLE_CAKES.get(color), List.of(EN_US.Candle(EN_US.Color(color, EN_US.with(EN_US.Cake(EN_US.Strawberry()))))));
		}
		Register("vanilla_cake", VANILLA_CAKE.CAKE, List.of(EN_US.Cake(EN_US.Vanilla())));
		Register("vanilla_candle_cake", VANILLA_CAKE.CANDLE_CAKE, List.of(EN_US.Candle(EN_US.with(EN_US.Cake(EN_US.Vanilla())))));
		Register("vanilla_soul_candle_cake", VANILLA_CAKE.SOUL_CANDLE_CAKE, List.of(EN_US.Candle(EN_US.Soul(EN_US.with(EN_US.Cake(EN_US.Vanilla()))))));
		Register("vanilla_ender_candle_cake", VANILLA_CAKE.ENDER_CANDLE_CAKE, List.of(EN_US.Candle(EN_US.Ender(EN_US.with(EN_US.Cake(EN_US.Vanilla()))))));
		Register("vanilla_netherrack_candle_cake", VANILLA_CAKE.NETHERRACK_CANDLE_CAKE, List.of(EN_US.Candle(EN_US.Netherrack(EN_US.with(EN_US.Cake(EN_US.Vanilla()))))));
		for (DyeColor color : DyeColor.values()) {
			Register(color + "_vanilla_candle_cake", VANILLA_CAKE.CANDLE_CAKES.get(color), List.of(EN_US.Candle(EN_US.Color(color, EN_US.with(EN_US.Cake(EN_US.Vanilla()))))));
		}
		//</editor-fold>
		//<editor-fold desc="Milk & Cheese">
		Register("milk_bowl", MILK_BOWL, List.of(EN_US.Bowl(EN_US.Milk())));
		Register("chocolate_milk_bowl", CHOCOLATE_MILK_BOWL, List.of(EN_US.Bowl(EN_US.Milk(EN_US.Chocolate()))));
		Register("coffee_milk_bowl", COFFEE_MILK_BOWL, List.of(EN_US.Bowl(EN_US.Milk(EN_US.Coffee()))));
		Register("strawberry_milk_bowl", STRAWBERRY_MILK_BOWL, List.of(EN_US.Bowl(EN_US.Milk(EN_US.Strawberry()))));
		Register("vanilla_milk_bowl", VANILLA_MILK_BOWL, List.of(EN_US.Bowl(EN_US.Milk(EN_US.Vanilla()))));
		Register("chocolate_milk_bottle", CHOCOLATE_MILK_BOTTLE, List.of(EN_US.Bottle(EN_US.Milk(EN_US.Chocolate()))));
		Register("coffee_milk_bottle", COFFEE_MILK_BOTTLE, List.of(EN_US.Bottle(EN_US.Milk(EN_US.Coffee()))));
		Register("strawberry_milk_bottle", STRAWBERRY_MILK_BOTTLE, List.of(EN_US.Bottle(EN_US.Milk(EN_US.Strawberry()))));
		Register("vanilla_milk_bottle", VANILLA_MILK_BOTTLE, List.of(EN_US.Bottle(EN_US.Milk(EN_US.Vanilla()))));
		Register("milk_cauldron", MILK_CAULDRON, List.of(EN_US.Cauldron(EN_US.Milk())));
		Register("cottage_cheese_cauldron", COTTAGE_CHEESE_CAULDRON, List.of(EN_US.Cauldron(EN_US.Cheese(EN_US.Cottage()))));
		Register("cheese_cauldron", CHEESE_CAULDRON, List.of(EN_US.Cauldron(EN_US.Cheese())));
		CauldronBehavior.EMPTY_CAULDRON_BEHAVIOR.put(Items.MILK_BUCKET, MilkCauldronBlock.FillFromBucket(Items.BUCKET));
		Register("chocolate_milk_bucket", CHOCOLATE_MILK_BUCKET, List.of(EN_US.Bucket(EN_US.Milk(EN_US.Chocolate()))));
		Register("coffee_milk_bucket", COFFEE_MILK_BUCKET, List.of(EN_US.Bucket(EN_US.Milk(EN_US.Coffee()))));
		Register("strawberry_milk_bucket", STRAWBERRY_MILK_BUCKET, List.of(EN_US.Bucket(EN_US.Milk(EN_US.Strawberry()))));
		Register("vanilla_milk_bucket", VANILLA_MILK_BUCKET, List.of(EN_US.Bucket(EN_US.Milk(EN_US.Vanilla()))));
		Register("cheese_block", CHEESE_BLOCK, List.of(EN_US.Block(EN_US.Cheese())));
		//</editor-fold>
	}
	private static void RegisterWorldGen() {
		//Mycelium and Mushrooms
		SINGLE_MYCELIUM_ROOTS.Initialize();
		SINGLE_BROWN_MUSHROOM.Initialize();
		SINGLE_BLUE_MUSHROOM.Initialize();
		SINGLE_RED_MUSHROOM.Initialize();
		Register("huge_blue_mushroom", HUGE_BLUE_MUSHROOM_FEATURE);
		HUGE_BLUE_MUSHROOM_CONFIGURED.Initialize();
		//Ruby Ore
		OVERWORLD_RUBY_ORE_CONFIGURED.Initialize();
		//BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES, RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY, OVERWORLD_RUBY_ORE_CONFIGURED.getIdentifier()));
		//Sapphire Ore
		OVERWORLD_SAPPHIRE_ORE_CONFIGURED.Initialize();
		//BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES, RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY, OVERWORLD_SAPPHIRE_ORE_CONFIGURED.getIdentifier()));
		//Mangrove
		Register("mangrove_tree", MANGROVE_TREE_FEATURE);
		MANGROVE_CONFIGURED.Initialize();
		TALL_MANGROVE_CONFIGURED.Initialize();
		//Cherry
		Register("cherry_tree", CHERRY_TREE_FEATURE);
		CHERRY_CONFIGURED.Initialize();
		CHERRY_BEES_005_CONFIGURED.Initialize();
		FLOWER_CHERRY_CONFIGURED.Initialize();
		//Cassia
		CASSIA_TREE.Initialize();
		//BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.BEACH, BiomeKeys.SUNFLOWER_PLAINS), GenerationStep.Feature.VEGETAL_DECORATION, RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY, CASSIA_TREE.getIdentifier()));
		//Dogwood
		PINK_DOGWOOD_TREE_CONFIGURED.Initialize();
		PALE_DOGWOOD_TREE_CONFIGURED.Initialize();
		WHITE_DOGWOOD_TREE_CONFIGURED.Initialize();
		//Grape Tree
		GRAPE_TREE.Initialize();
		GRAPE_TREE_BEES.Initialize();
		//Gilded Fungus
		BuiltinRegistries.add(BuiltinRegistries.CONFIGURED_FEATURE, ModId.ID("gilded_forest_vegetation"), GILDED_FOREST_VEGETATION_CONFIGURED);
		GILDED_FUNGI.Initialize();
		GILDED_FUNGI_PLANTED.Initialize();
		//Sculk
		Register("sculk_patch", SCULK_PATCH_FEATURE);
		SCULK_PATCH_DEEP_DARK_CONFIGURED.Initialize();
		SCULK_PATCH_ANCIENT_CITY_CONFIGURED.Initialize();
		Register("sculk_vein", SCULK_VEIN_FEATURE);
		SCULK_VEIN_CONFIGURED.Initialize();
		//Biomes
		BuiltinBiomesInvoker.Register(ModRegistry.BIOME_RAW_ID++, MANGROVE_SWAMP, ModBiomeCreator.createMangroveSwamp());
		BuiltinBiomesInvoker.Register(ModRegistry.BIOME_RAW_ID++, DEEP_DARK, ModBiomeCreator.createDeepDark());
		BuiltinBiomesInvoker.Register(ModRegistry.BIOME_RAW_ID++, CHERRY_GROVE, ModBiomeCreator.createCherryGrove());
	}
	private static void RegisterDarkIron() {
		Register("dark_iron_nugget", DARK_IRON_NUGGET, List.of(EN_US.Nugget(EN_US.Iron(EN_US.Dark()))));
		Register("dark_iron_ingot", DARK_IRON_INGOT, List.of(EN_US.Ingot(EN_US.Iron(EN_US.Dark()))));
		Register("dark_iron_rod", DARK_IRON_ROD, List.of(EN_US.Rod(EN_US.Iron(EN_US.Dark()))));
		Register("dark_iron_torch", "dark_iron_wall_torch", DARK_IRON_TORCH, List.of(EN_US._Torch(EN_US.Iron(EN_US.Dark()))));
		Register("dark_iron_soul_torch", "dark_iron_soul_wall_torch", DARK_IRON_SOUL_TORCH, List.of(EN_US._Torch(EN_US.Soul(EN_US.Iron(EN_US.Dark())))));
		Register("dark_iron_ender_torch", "dark_iron_ender_wall_torch", DARK_IRON_ENDER_TORCH, List.of(EN_US._Torch(EN_US.Ender(EN_US.Iron(EN_US.Dark())))));
		Register("underwater_dark_iron_torch", "underwater_dark_iron_wall_torch", UNDERWATER_DARK_IRON_TORCH, List.of(EN_US._Torch(EN_US.Iron(EN_US.Dark(EN_US.Underwater())))));
		Register("dark_iron_button", DARK_IRON_BUTTON, List.of(EN_US.Button(EN_US.Iron(EN_US.Dark()))));
		Register("heavy_chain", HEAVY_CHAIN, List.of(EN_US.Chain(EN_US.Heavy())));
		Register("dark_iron_bars", DARK_IRON_BARS, List.of(EN_US.Bars(EN_US.Iron(EN_US.Dark()))));
		Register("dark_iron_block", DARK_IRON_BLOCK, List.of(EN_US.Block(EN_US.Iron(EN_US.Dark()))));
		Register("dark_iron_stairs", DARK_IRON_STAIRS, List.of(EN_US.Stairs(EN_US.Iron(EN_US.Dark()))));
		Register("dark_iron_slab", DARK_IRON_SLAB, List.of(EN_US.Slab(EN_US.Iron(EN_US.Dark()))));
		Register("dark_iron_wall", DARK_IRON_WALL, List.of(EN_US.Wall(EN_US.Iron(EN_US.Dark()))));
		Register("dark_iron_door", DARK_IRON_DOOR, List.of(EN_US.Door(EN_US.Iron(EN_US.Dark()))));
		Register("dark_iron_trapdoor", DARK_IRON_TRAPDOOR, List.of(EN_US.Trapdoor(EN_US.Iron(EN_US.Dark()))));
		Register("dark_iron_bricks", DARK_IRON_BRICKS, List.of(EN_US.Bricks(EN_US.Iron(EN_US.Dark()))));
		Register("dark_iron_brick_stairs", DARK_IRON_BRICK_STAIRS, List.of(EN_US.Stairs(EN_US.Brick(EN_US.Iron(EN_US.Dark())))));
		Register("dark_iron_brick_slab", DARK_IRON_BRICK_SLAB, List.of(EN_US.Slab(EN_US.Brick(EN_US.Iron(EN_US.Dark())))));
		Register("dark_iron_brick_wall", DARK_IRON_BRICK_WALL, List.of(EN_US.Wall(EN_US.Brick(EN_US.Iron(EN_US.Dark())))));
		Register("cut_dark_iron", CUT_DARK_IRON, List.of(EN_US.Iron(EN_US.Dark(EN_US.Cut()))));
		Register("cut_dark_iron_pillar", CUT_DARK_IRON_PILLAR, List.of(EN_US.Pillar(EN_US.Iron(EN_US.Dark(EN_US.Cut())))));
		Register("cut_dark_iron_stairs", CUT_DARK_IRON_STAIRS, List.of(EN_US.Stairs(EN_US.Iron(EN_US.Dark(EN_US.Cut())))));
		Register("cut_dark_iron_slab", CUT_DARK_IRON_SLAB, List.of(EN_US.Slab(EN_US.Iron(EN_US.Dark(EN_US.Cut())))));
		Register("cut_dark_iron_wall", CUT_DARK_IRON_WALL, List.of(EN_US.Wall(EN_US.Iron(EN_US.Dark(EN_US.Cut())))));
		Register("dark_heavy_weighted_pressure_plate", DARK_HEAVY_WEIGHTED_PRESSURE_PLATE, List.of(EN_US.Plate(EN_US.Pressure(EN_US.Weighted(EN_US.Heavy(EN_US.Dark()))))));
		//Misc
		Register("dark_iron_hammer", DARK_IRON_HAMMER, List.of(EN_US.Hammer(EN_US.Iron(EN_US.Dark()))));
		Register("dark_iron_shears", DARK_IRON_SHEARS, List.of(EN_US.Shears(EN_US.Iron(EN_US.Dark()))));
		//Bucket
		Register("dark_iron_bucket", DARK_IRON_BUCKET, List.of(EN_US.Bucket(EN_US.Iron(EN_US.Dark()))));
		DispenserBlock.registerBehavior(DARK_IRON_BUCKET, new BucketDispenserBehavior(DARK_IRON_BUCKET_PROVIDER));
		Register("dark_iron_water_bucket", DARK_IRON_WATER_BUCKET, List.of(EN_US.Bucket(EN_US.Water(EN_US.Iron(EN_US.Dark())))));
		CauldronBehavior.EMPTY_CAULDRON_BEHAVIOR.put(DARK_IRON_WATER_BUCKET, (state, world, pos, player, hand, stack) -> BucketUtil.fillCauldron(world, pos, player, hand, stack,
				Blocks.WATER_CAULDRON.getDefaultState().with(LeveledCauldronBlock.LEVEL, 3),
				SoundEvents.ITEM_BUCKET_EMPTY, DARK_IRON_BUCKET));
		CauldronBehavior.WATER_CAULDRON_BEHAVIOR.put(DARK_IRON_BUCKET, (state, world, pos, player, hand, stack) -> CauldronBehavior.emptyCauldron(state, world, pos, player, hand, stack, new ItemStack(DARK_IRON_WATER_BUCKET),
				(statex) -> statex.get(LeveledCauldronBlock.LEVEL) == 3, SoundEvents.ITEM_BUCKET_FILL));
		Register("dark_iron_lava_bucket", DARK_IRON_LAVA_BUCKET, List.of(EN_US.Bucket(EN_US.Lava(EN_US.Iron(EN_US.Dark())))));
		Register("dark_iron_powder_snow_bucket", DARK_IRON_POWDER_SNOW_BUCKET, List.of(EN_US.Bucket(EN_US.Snow(EN_US.Powder(EN_US.Iron(EN_US.Dark()))))));
		CauldronBehavior.EMPTY_CAULDRON_BEHAVIOR.put(DARK_IRON_POWDER_SNOW_BUCKET, (state, world, pos, player, hand, stack) -> BucketUtil.fillCauldron(world, pos, player, hand, stack,
				Blocks.POWDER_SNOW_CAULDRON.getDefaultState().with(LeveledCauldronBlock.LEVEL, 3),
				SoundEvents.ITEM_BUCKET_EMPTY_POWDER_SNOW, DARK_IRON_BUCKET));
		CauldronBehavior.POWDER_SNOW_CAULDRON_BEHAVIOR.put(DARK_IRON_BUCKET, (state, world, pos, player, hand, stack) -> CauldronBehavior.emptyCauldron(state, world, pos, player, hand, stack, new ItemStack(DARK_IRON_POWDER_SNOW_BUCKET),
				(statex) -> statex.get(LeveledCauldronBlock.LEVEL) == 3, SoundEvents.ITEM_BUCKET_FILL_POWDER_SNOW));
		Register("dark_iron_blood_bucket", DARK_IRON_BLOOD_BUCKET, List.of(EN_US.Bucket(EN_US.Blood(EN_US.Iron(EN_US.Dark())))));
		CauldronBehavior.EMPTY_CAULDRON_BEHAVIOR.put(DARK_IRON_BLOOD_BUCKET, BloodCauldronBlock.FillFromBucket(DARK_IRON_BUCKET));
		Register("dark_iron_mud_bucket", DARK_IRON_MUD_BUCKET, List.of(EN_US.Bucket(EN_US.Mud(EN_US.Iron(EN_US.Dark())))));
		CauldronBehavior.EMPTY_CAULDRON_BEHAVIOR.put(DARK_IRON_MUD_BUCKET, MudCauldronBlock.FillFromBucket(DARK_IRON_BUCKET));
		Register("dark_iron_milk_bucket", DARK_IRON_MILK_BUCKET, List.of(EN_US.Bucket(EN_US.Milk(EN_US.Iron(EN_US.Dark())))));
		CauldronBehavior.EMPTY_CAULDRON_BEHAVIOR.put(DARK_IRON_MILK_BUCKET, MilkCauldronBlock.FillFromBucket(DARK_IRON_BUCKET));
		Register("dark_iron_chocolate_milk_bucket", DARK_IRON_CHOCOLATE_MILK_BUCKET, List.of(EN_US.Bucket(EN_US.Milk(EN_US.Chocolate(EN_US.Iron(EN_US.Dark()))))));
		Register("dark_iron_coffee_milk_bucket", DARK_IRON_COFFEE_MILK_BUCKET, List.of(EN_US.Bucket(EN_US.Milk(EN_US.Coffee(EN_US.Iron(EN_US.Dark()))))));
		Register("dark_iron_strawberry_milk_bucket", DARK_IRON_STRAWBERRY_MILK_BUCKET, List.of(EN_US.Bucket(EN_US.Milk(EN_US.Strawberry(EN_US.Iron(EN_US.Dark()))))));
		Register("dark_iron_vanilla_milk_bucket", DARK_IRON_VANILLA_MILK_BUCKET, List.of(EN_US.Bucket(EN_US.Milk(EN_US.Vanilla(EN_US.Iron(EN_US.Dark()))))));
	}
	private static void RegisterMushroomWood() {
		//<editor-fold desc="Blue Mushroom">
		Register("blue_mushroom_planks", BLUE_MUSHROOM_PLANKS, List.of(EN_US.Planks(EN_US.Mushroom(EN_US.Blue()))));
		Register("blue_mushroom_stairs", BLUE_MUSHROOM_STAIRS, List.of(EN_US.Stairs(EN_US.Mushroom(EN_US.Blue()))));
		Register("blue_mushroom_slab", BLUE_MUSHROOM_SLAB, List.of(EN_US.Slab(EN_US.Mushroom(EN_US.Blue()))));
		Register("blue_mushroom_fence", BLUE_MUSHROOM_FENCE, List.of(EN_US.Fence(EN_US.Mushroom(EN_US.Blue()))));
		Register("blue_mushroom_fence_gate", BLUE_MUSHROOM_FENCE_GATE, List.of(EN_US.Gate(EN_US.Fence(EN_US.Mushroom(EN_US.Blue())))));
		Register("blue_mushroom_door", BLUE_MUSHROOM_DOOR, List.of(EN_US.Door(EN_US.Mushroom(EN_US.Blue()))));
		Register("blue_mushroom_trapdoor", BLUE_MUSHROOM_TRAPDOOR, List.of(EN_US.Trapdoor(EN_US.Mushroom(EN_US.Blue()))));
		Register("blue_mushroom_pressure_plate", BLUE_MUSHROOM_PRESSURE_PLATE, List.of(EN_US.Plate(EN_US.Pressure(EN_US.Mushroom(EN_US.Blue())))));
		Register("blue_mushroom_button", BLUE_MUSHROOM_BUTTON, List.of(EN_US.Button(EN_US.Mushroom(EN_US.Blue()))));
		Register("blue_mushroom", BLUE_MUSHROOM_SIGN, List.of(EN_US._Sign(EN_US.Mushroom(EN_US.Blue()))));
		Register("blue_mushroom_iron_hanging_sign", "blue_mushroom_iron_wall_hanging_sign", BLUE_MUSHROOM_IRON_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Mushroom(EN_US.Blue()), EN_US.Chain(EN_US.Iron(EN_US.with())))));
		Register("blue_mushroom_gold_hanging_sign", "blue_mushroom_gold_wall_hanging_sign", BLUE_MUSHROOM_GOLD_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Mushroom(EN_US.Blue()), EN_US.Chain(EN_US.Gold(EN_US.with())))));
		Register("blue_mushroom_copper_hanging_sign", "blue_mushroom_copper_wall_hanging_sign", BLUE_MUSHROOM_COPPER_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Mushroom(EN_US.Blue()), EN_US.Chain(EN_US.Copper(EN_US.with())))));
		Register("blue_mushroom_exposed_copper_hanging_sign", "blue_mushroom_exposed_copper_wall_hanging_sign", BLUE_MUSHROOM_EXPOSED_COPPER_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Mushroom(EN_US.Blue()), EN_US.Chain(EN_US.Copper(EN_US.Exposed(EN_US.with()))))));
		Register("blue_mushroom_weathered_copper_hanging_sign", "blue_mushroom_weathered_copper_wall_hanging_sign", BLUE_MUSHROOM_WEATHERED_COPPER_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Mushroom(EN_US.Blue()), EN_US.Chain(EN_US.Copper(EN_US.Weathered(EN_US.with()))))));
		Register("blue_mushroom_oxidized_copper_hanging_sign", "blue_mushroom_oxidized_copper_wall_hanging_sign", BLUE_MUSHROOM_OXIDIZED_COPPER_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Mushroom(EN_US.Blue()), EN_US.Chain(EN_US.Copper(EN_US.Oxidized(EN_US.with()))))));
		Register("blue_mushroom_netherite_hanging_sign", "blue_mushroom_netherite_wall_hanging_sign", BLUE_MUSHROOM_NETHERITE_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Mushroom(EN_US.Blue()), EN_US.Chain(EN_US.Netherite(EN_US.with())))));
		Register("blue_mushroom", BLUE_MUSHROOM_BOAT, List.of(EN_US._Boat(EN_US.Mushroom(EN_US.Blue()))));
		//Extended
		Register("blue_mushroom_beehive", BLUE_MUSHROOM_BEEHIVE, List.of(EN_US.Beehive(EN_US.Mushroom(EN_US.Blue()))));
		Register("blue_mushroom_bookshelf", BLUE_MUSHROOM_BOOKSHELF, List.of(EN_US.Bookshelf(EN_US.Mushroom(EN_US.Blue()))));
		Register("blue_mushroom_chiseled_bookshelf", BLUE_MUSHROOM_CHISELED_BOOKSHELF, List.of(EN_US.Bookshelf(EN_US.Chiseled(EN_US.Mushroom(EN_US.Blue())))));
		Register("blue_mushroom_crafting_table", BLUE_MUSHROOM_CRAFTING_TABLE, List.of(EN_US.Table(EN_US.Crafting(EN_US.Mushroom(EN_US.Blue())))));
		Register("blue_mushroom_ladder", BLUE_MUSHROOM_LADDER, List.of(EN_US.Ladder(EN_US.Mushroom(EN_US.Blue()))));
		Register("blue_mushroom_woodcutter", BLUE_MUSHROOM_WOODCUTTER, List.of(EN_US.Woodcutter(EN_US.Mushroom(EN_US.Blue()))));
		Register("blue_mushroom_barrel", BLUE_MUSHROOM_BARREL, List.of(EN_US.Barrel(EN_US.Mushroom(EN_US.Blue()))));
		Register("blue_mushroom_lectern", BLUE_MUSHROOM_LECTERN, List.of(EN_US.Lectern(EN_US.Mushroom(EN_US.Blue()))));
		Register("blue_mushroom_powder_keg", BLUE_MUSHROOM_POWDER_KEG, List.of(EN_US.Keg(EN_US.Powder(EN_US.Mushroom(EN_US.Blue())))));
		//Torches
		Register("blue_mushroom_torch", "blue_mushroom_wall_torch", BLUE_MUSHROOM_TORCH, List.of(EN_US._Torch(EN_US.Mushroom(EN_US.Blue()))));
		Register("blue_mushroom_soul_torch", "blue_mushroom_soul_wall_torch", BLUE_MUSHROOM_SOUL_TORCH, List.of(EN_US._Torch(EN_US.Soul(EN_US.Mushroom(EN_US.Blue())))));
		Register("blue_mushroom_ender_torch", "blue_mushroom_ender_wall_torch", BLUE_MUSHROOM_ENDER_TORCH, List.of(EN_US._Torch(EN_US.Ender(EN_US.Mushroom(EN_US.Blue())))));
		Register("underwater_blue_mushroom_torch", "underwater_blue_mushroom_wall_torch", UNDERWATER_BLUE_MUSHROOM_TORCH, List.of(EN_US._Torch(EN_US.Mushroom(EN_US.Blue(EN_US.Underwater())))));
		//Campfires
		Register("blue_mushroom_campfire", BLUE_MUSHROOM_CAMPFIRE, List.of(EN_US.Campfire(EN_US.Mushroom(EN_US.Blue()))));
		Register("blue_mushroom_soul_campfire", BLUE_MUSHROOM_SOUL_CAMPFIRE, List.of(EN_US.Campfire(EN_US.Soul(EN_US.Mushroom(EN_US.Blue())))));
		Register("blue_mushroom_ender_campfire", BLUE_MUSHROOM_ENDER_CAMPFIRE, List.of(EN_US.Campfire(EN_US.Ender(EN_US.Mushroom(EN_US.Blue())))));
		//</editor-fold>
		//<editor-fold desc="Brown Mushroom">
		Register("brown_mushroom_planks", BROWN_MUSHROOM_PLANKS, List.of(EN_US.Planks(EN_US.Mushroom(EN_US.Brown()))));
		Register("brown_mushroom_stairs", BROWN_MUSHROOM_STAIRS, List.of(EN_US.Stairs(EN_US.Mushroom(EN_US.Brown()))));
		Register("brown_mushroom_slab", BROWN_MUSHROOM_SLAB, List.of(EN_US.Slab(EN_US.Mushroom(EN_US.Brown()))));
		Register("brown_mushroom_fence", BROWN_MUSHROOM_FENCE, List.of(EN_US.Fence(EN_US.Mushroom(EN_US.Brown()))));
		Register("brown_mushroom_fence_gate", BROWN_MUSHROOM_FENCE_GATE, List.of(EN_US.Gate(EN_US.Fence(EN_US.Mushroom(EN_US.Brown())))));
		Register("brown_mushroom_door", BROWN_MUSHROOM_DOOR, List.of(EN_US.Door(EN_US.Mushroom(EN_US.Brown()))));
		Register("brown_mushroom_trapdoor", BROWN_MUSHROOM_TRAPDOOR, List.of(EN_US.Trapdoor(EN_US.Mushroom(EN_US.Brown()))));
		Register("brown_mushroom_pressure_plate", BROWN_MUSHROOM_PRESSURE_PLATE, List.of(EN_US.Plate(EN_US.Pressure(EN_US.Mushroom(EN_US.Brown())))));
		Register("brown_mushroom_button", BROWN_MUSHROOM_BUTTON, List.of(EN_US.Button(EN_US.Mushroom(EN_US.Brown()))));
		Register("brown_mushroom", BROWN_MUSHROOM_SIGN, List.of(EN_US._Sign(EN_US.Mushroom(EN_US.Brown()))));
		Register("brown_mushroom_iron_hanging_sign", "brown_mushroom_iron_wall_hanging_sign", BROWN_MUSHROOM_IRON_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Mushroom(EN_US.Brown()), EN_US.Chain(EN_US.Iron(EN_US.with())))));
		Register("brown_mushroom_gold_hanging_sign", "brown_mushroom_gold_wall_hanging_sign", BROWN_MUSHROOM_GOLD_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Mushroom(EN_US.Brown()), EN_US.Chain(EN_US.Gold(EN_US.with())))));
		Register("brown_mushroom_copper_hanging_sign", "brown_mushroom_copper_wall_hanging_sign", BROWN_MUSHROOM_COPPER_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Mushroom(EN_US.Brown()), EN_US.Chain(EN_US.Copper(EN_US.with())))));
		Register("brown_mushroom_exposed_copper_hanging_sign", "brown_mushroom_exposed_copper_wall_hanging_sign", BROWN_MUSHROOM_EXPOSED_COPPER_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Mushroom(EN_US.Brown()), EN_US.Chain(EN_US.Copper(EN_US.Exposed(EN_US.with()))))));
		Register("brown_mushroom_weathered_copper_hanging_sign", "brown_mushroom_weathered_copper_wall_hanging_sign", BROWN_MUSHROOM_WEATHERED_COPPER_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Mushroom(EN_US.Brown()), EN_US.Chain(EN_US.Copper(EN_US.Weathered(EN_US.with()))))));
		Register("brown_mushroom_oxidized_copper_hanging_sign", "brown_mushroom_oxidized_copper_wall_hanging_sign", BROWN_MUSHROOM_OXIDIZED_COPPER_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Mushroom(EN_US.Brown()), EN_US.Chain(EN_US.Copper(EN_US.Oxidized(EN_US.with()))))));
		Register("brown_mushroom_netherite_hanging_sign", "brown_mushroom_netherite_wall_hanging_sign", BROWN_MUSHROOM_NETHERITE_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Mushroom(EN_US.Brown()), EN_US.Chain(EN_US.Netherite(EN_US.with())))));
		Register("brown_mushroom", BROWN_MUSHROOM_BOAT, List.of(EN_US._Boat(EN_US.Mushroom(EN_US.Brown()))));
		//Extended
		Register("brown_mushroom_beehive", BROWN_MUSHROOM_BEEHIVE, List.of(EN_US.Beehive(EN_US.Mushroom(EN_US.Brown()))));
		Register("brown_mushroom_bookshelf", BROWN_MUSHROOM_BOOKSHELF, List.of(EN_US.Bookshelf(EN_US.Mushroom(EN_US.Brown()))));
		Register("brown_mushroom_chiseled_bookshelf", BROWN_MUSHROOM_CHISELED_BOOKSHELF, List.of(EN_US.Bookshelf(EN_US.Chiseled(EN_US.Mushroom(EN_US.Brown())))));
		Register("brown_mushroom_crafting_table", BROWN_MUSHROOM_CRAFTING_TABLE, List.of(EN_US.Table(EN_US.Crafting(EN_US.Mushroom(EN_US.Brown())))));
		Register("brown_mushroom_ladder", BROWN_MUSHROOM_LADDER, List.of(EN_US.Ladder(EN_US.Mushroom(EN_US.Brown()))));
		Register("brown_mushroom_woodcutter", BROWN_MUSHROOM_WOODCUTTER, List.of(EN_US.Woodcutter(EN_US.Mushroom(EN_US.Brown()))));
		Register("brown_mushroom_barrel", BROWN_MUSHROOM_BARREL, List.of(EN_US.Barrel(EN_US.Mushroom(EN_US.Brown()))));
		Register("brown_mushroom_lectern", BROWN_MUSHROOM_LECTERN, List.of(EN_US.Lectern(EN_US.Mushroom(EN_US.Brown()))));
		Register("brown_mushroom_powder_keg", BROWN_MUSHROOM_POWDER_KEG, List.of(EN_US.Keg(EN_US.Powder(EN_US.Mushroom(EN_US.Brown())))));
		//Torches
		Register("brown_mushroom_torch", "brown_mushroom_wall_torch", BROWN_MUSHROOM_TORCH, List.of(EN_US._Torch(EN_US.Mushroom(EN_US.Brown()))));
		Register("brown_mushroom_soul_torch", "brown_mushroom_soul_wall_torch", BROWN_MUSHROOM_SOUL_TORCH, List.of(EN_US._Torch(EN_US.Soul(EN_US.Mushroom(EN_US.Brown())))));
		Register("brown_mushroom_ender_torch", "brown_mushroom_ender_wall_torch", BROWN_MUSHROOM_ENDER_TORCH, List.of(EN_US._Torch(EN_US.Ender(EN_US.Mushroom(EN_US.Brown())))));
		Register("underwater_brown_mushroom_torch", "underwater_brown_mushroom_wall_torch", UNDERWATER_BROWN_MUSHROOM_TORCH, List.of(EN_US._Torch(EN_US.Mushroom(EN_US.Brown(EN_US.Underwater())))));
		//Campfires
		Register("brown_mushroom_campfire", BROWN_MUSHROOM_CAMPFIRE, List.of(EN_US.Campfire(EN_US.Mushroom(EN_US.Brown()))));
		Register("brown_mushroom_soul_campfire", BROWN_MUSHROOM_SOUL_CAMPFIRE, List.of(EN_US.Campfire(EN_US.Soul(EN_US.Mushroom(EN_US.Brown())))));
		Register("brown_mushroom_ender_campfire", BROWN_MUSHROOM_ENDER_CAMPFIRE, List.of(EN_US.Campfire(EN_US.Ender(EN_US.Mushroom(EN_US.Brown())))));
		//</editor-fold>
		//<editor-fold desc="Red Mushroom">
		Register("red_mushroom_planks", RED_MUSHROOM_PLANKS, List.of(EN_US.Planks(EN_US.Mushroom(EN_US.Red()))));
		Register("red_mushroom_stairs", RED_MUSHROOM_STAIRS, List.of(EN_US.Stairs(EN_US.Mushroom(EN_US.Red()))));
		Register("red_mushroom_slab", RED_MUSHROOM_SLAB, List.of(EN_US.Slab(EN_US.Mushroom(EN_US.Red()))));
		Register("red_mushroom_fence", RED_MUSHROOM_FENCE, List.of(EN_US.Fence(EN_US.Mushroom(EN_US.Red()))));
		Register("red_mushroom_fence_gate", RED_MUSHROOM_FENCE_GATE, List.of(EN_US.Gate(EN_US.Fence(EN_US.Mushroom(EN_US.Red())))));
		Register("red_mushroom_door", RED_MUSHROOM_DOOR, List.of(EN_US.Door(EN_US.Mushroom(EN_US.Red()))));
		Register("red_mushroom_trapdoor", RED_MUSHROOM_TRAPDOOR, List.of(EN_US.Trapdoor(EN_US.Mushroom(EN_US.Red()))));
		Register("red_mushroom_pressure_plate", RED_MUSHROOM_PRESSURE_PLATE, List.of(EN_US.Plate(EN_US.Pressure(EN_US.Mushroom(EN_US.Red())))));
		Register("red_mushroom_button", RED_MUSHROOM_BUTTON, List.of(EN_US.Button(EN_US.Mushroom(EN_US.Red()))));
		Register("red_mushroom", RED_MUSHROOM_SIGN, List.of(EN_US._Sign(EN_US.Mushroom(EN_US.Red()))));
		Register("red_mushroom_iron_hanging_sign", "red_mushroom_iron_wall_hanging_sign", RED_MUSHROOM_IRON_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Mushroom(EN_US.Red()), EN_US.Chain(EN_US.Iron(EN_US.with())))));
		Register("red_mushroom_gold_hanging_sign", "red_mushroom_gold_wall_hanging_sign", RED_MUSHROOM_GOLD_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Mushroom(EN_US.Red()), EN_US.Chain(EN_US.Gold(EN_US.with())))));
		Register("red_mushroom_copper_hanging_sign", "red_mushroom_copper_wall_hanging_sign", RED_MUSHROOM_COPPER_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Mushroom(EN_US.Red()), EN_US.Chain(EN_US.Copper(EN_US.with())))));
		Register("red_mushroom_exposed_copper_hanging_sign", "red_mushroom_exposed_copper_wall_hanging_sign", RED_MUSHROOM_EXPOSED_COPPER_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Mushroom(EN_US.Red()), EN_US.Chain(EN_US.Copper(EN_US.Exposed(EN_US.with()))))));
		Register("red_mushroom_weathered_copper_hanging_sign", "red_mushroom_weathered_copper_wall_hanging_sign", RED_MUSHROOM_WEATHERED_COPPER_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Mushroom(EN_US.Red()), EN_US.Chain(EN_US.Copper(EN_US.Weathered(EN_US.with()))))));
		Register("red_mushroom_oxidized_copper_hanging_sign", "red_mushroom_oxidized_copper_wall_hanging_sign", RED_MUSHROOM_OXIDIZED_COPPER_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Mushroom(EN_US.Red()), EN_US.Chain(EN_US.Copper(EN_US.Oxidized(EN_US.with()))))));
		Register("red_mushroom_netherite_hanging_sign", "red_mushroom_netherite_wall_hanging_sign", RED_MUSHROOM_NETHERITE_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Mushroom(EN_US.Red()), EN_US.Chain(EN_US.Netherite(EN_US.with())))));
		Register("red_mushroom", RED_MUSHROOM_BOAT, List.of(EN_US._Boat(EN_US.Mushroom(EN_US.Red()))));
		//Extended
		Register("red_mushroom_beehive", RED_MUSHROOM_BEEHIVE, List.of(EN_US.Beehive(EN_US.Mushroom(EN_US.Red()))));
		Register("red_mushroom_bookshelf", RED_MUSHROOM_BOOKSHELF, List.of(EN_US.Bookshelf(EN_US.Mushroom(EN_US.Red()))));
		Register("red_mushroom_chiseled_bookshelf", RED_MUSHROOM_CHISELED_BOOKSHELF, List.of(EN_US.Bookshelf(EN_US.Chiseled(EN_US.Mushroom(EN_US.Red())))));
		Register("red_mushroom_crafting_table", RED_MUSHROOM_CRAFTING_TABLE, List.of(EN_US.Table(EN_US.Crafting(EN_US.Mushroom(EN_US.Red())))));
		Register("red_mushroom_ladder", RED_MUSHROOM_LADDER, List.of(EN_US.Ladder(EN_US.Mushroom(EN_US.Red()))));
		Register("red_mushroom_woodcutter", RED_MUSHROOM_WOODCUTTER, List.of(EN_US.Woodcutter(EN_US.Mushroom(EN_US.Red()))));
		Register("red_mushroom_barrel", RED_MUSHROOM_BARREL, List.of(EN_US.Barrel(EN_US.Mushroom(EN_US.Red()))));
		Register("red_mushroom_lectern", RED_MUSHROOM_LECTERN, List.of(EN_US.Lectern(EN_US.Mushroom(EN_US.Red()))));
		Register("red_mushroom_powder_keg", RED_MUSHROOM_POWDER_KEG, List.of(EN_US.Keg(EN_US.Powder(EN_US.Mushroom(EN_US.Red())))));
		//Torches
		Register("red_mushroom_torch", "red_mushroom_wall_torch", RED_MUSHROOM_TORCH, List.of(EN_US._Torch(EN_US.Mushroom(EN_US.Red()))));
		Register("red_mushroom_soul_torch", "red_mushroom_soul_wall_torch", RED_MUSHROOM_SOUL_TORCH, List.of(EN_US._Torch(EN_US.Soul(EN_US.Mushroom(EN_US.Red())))));
		Register("red_mushroom_ender_torch", "red_mushroom_ender_wall_torch", RED_MUSHROOM_ENDER_TORCH, List.of(EN_US._Torch(EN_US.Ender(EN_US.Mushroom(EN_US.Red())))));
		Register("underwater_red_mushroom_torch", "underwater_red_mushroom_wall_torch", UNDERWATER_RED_MUSHROOM_TORCH, List.of(EN_US._Torch(EN_US.Mushroom(EN_US.Red(EN_US.Underwater())))));
		//Campfires
		Register("red_mushroom_campfire", RED_MUSHROOM_CAMPFIRE, List.of(EN_US.Campfire(EN_US.Mushroom(EN_US.Red()))));
		Register("red_mushroom_soul_campfire", RED_MUSHROOM_SOUL_CAMPFIRE, List.of(EN_US.Campfire(EN_US.Soul(EN_US.Mushroom(EN_US.Red())))));
		Register("red_mushroom_ender_campfire", RED_MUSHROOM_ENDER_CAMPFIRE, List.of(EN_US.Campfire(EN_US.Ender(EN_US.Mushroom(EN_US.Red())))));
		//</editor-fold>
		//<editor-fold desc="Mushroom Stem">
		Register("mushroom_stem_planks", MUSHROOM_STEM_PLANKS, List.of(EN_US.Planks(EN_US.Stem(EN_US.Mushroom()))));
		Register("mushroom_stem_stairs", MUSHROOM_STEM_STAIRS, List.of(EN_US.Stairs(EN_US.Stem(EN_US.Mushroom()))));
		Register("mushroom_stem_slab", MUSHROOM_STEM_SLAB, List.of(EN_US.Slab(EN_US.Stem(EN_US.Mushroom()))));
		Register("mushroom_stem_fence", MUSHROOM_STEM_FENCE, List.of(EN_US.Fence(EN_US.Stem(EN_US.Mushroom()))));
		Register("mushroom_stem_fence_gate", MUSHROOM_STEM_FENCE_GATE, List.of(EN_US.Gate(EN_US.Fence(EN_US.Stem(EN_US.Mushroom())))));
		Register("mushroom_stem_door", MUSHROOM_STEM_DOOR, List.of(EN_US.Door(EN_US.Stem(EN_US.Mushroom()))));
		Register("mushroom_stem_trapdoor", MUSHROOM_STEM_TRAPDOOR, List.of(EN_US.Trapdoor(EN_US.Stem(EN_US.Mushroom()))));
		Register("mushroom_stem_pressure_plate", MUSHROOM_STEM_PRESSURE_PLATE, List.of(EN_US.Plate(EN_US.Pressure(EN_US.Stem(EN_US.Mushroom())))));
		Register("mushroom_stem_button", MUSHROOM_STEM_BUTTON, List.of(EN_US.Button(EN_US.Stem(EN_US.Mushroom()))));
		Register("mushroom_stem", MUSHROOM_STEM_SIGN, List.of(EN_US._Sign(EN_US.Stem(EN_US.Mushroom()))));
		Register("mushroom_stem_iron_hanging_sign", "mushroom_stem_iron_wall_hanging_sign", MUSHROOM_STEM_IRON_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Stem(EN_US.Mushroom()), EN_US.Chain(EN_US.Iron(EN_US.with())))));
		Register("mushroom_stem_gold_hanging_sign", "mushroom_stem_gold_wall_hanging_sign", MUSHROOM_STEM_GOLD_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Stem(EN_US.Mushroom()), EN_US.Chain(EN_US.Gold(EN_US.with())))));
		Register("mushroom_stem_copper_hanging_sign", "mushroom_stem_copper_wall_hanging_sign", MUSHROOM_STEM_COPPER_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Stem(EN_US.Mushroom()), EN_US.Chain(EN_US.Copper(EN_US.with())))));
		Register("mushroom_stem_exposed_copper_hanging_sign", "mushroom_stem_exposed_copper_wall_hanging_sign", MUSHROOM_STEM_EXPOSED_COPPER_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Stem(EN_US.Mushroom()), EN_US.Chain(EN_US.Copper(EN_US.Exposed(EN_US.with()))))));
		Register("mushroom_stem_weathered_copper_hanging_sign", "mushroom_stem_weathered_copper_wall_hanging_sign", MUSHROOM_STEM_WEATHERED_COPPER_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Stem(EN_US.Mushroom()), EN_US.Chain(EN_US.Copper(EN_US.Weathered(EN_US.with()))))));
		Register("mushroom_stem_oxidized_copper_hanging_sign", "mushroom_stem_oxidized_copper_wall_hanging_sign", MUSHROOM_STEM_OXIDIZED_COPPER_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Stem(EN_US.Mushroom()), EN_US.Chain(EN_US.Copper(EN_US.Oxidized(EN_US.with()))))));
		Register("mushroom_stem_netherite_hanging_sign", "mushroom_stem_netherite_wall_hanging_sign", MUSHROOM_STEM_NETHERITE_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Stem(EN_US.Mushroom()), EN_US.Chain(EN_US.Netherite(EN_US.with())))));
		Register("mushroom_stem", MUSHROOM_STEM_BOAT, List.of(EN_US._Boat(EN_US.Stem(EN_US.Mushroom()))));
		//Extended
		Register("mushroom_stem_beehive", MUSHROOM_STEM_BEEHIVE, List.of(EN_US.Beehive(EN_US.Stem(EN_US.Mushroom()))));
		Register("mushroom_stem_bookshelf", MUSHROOM_STEM_BOOKSHELF, List.of(EN_US.Bookshelf(EN_US.Stem(EN_US.Mushroom()))));
		Register("mushroom_stem_chiseled_bookshelf", MUSHROOM_STEM_CHISELED_BOOKSHELF, List.of(EN_US.Bookshelf(EN_US.Chiseled(EN_US.Stem(EN_US.Mushroom())))));
		Register("mushroom_stem_crafting_table", MUSHROOM_STEM_CRAFTING_TABLE, List.of(EN_US.Table(EN_US.Crafting(EN_US.Stem(EN_US.Mushroom())))));
		Register("mushroom_stem_ladder", MUSHROOM_STEM_LADDER, List.of(EN_US.Ladder(EN_US.Stem(EN_US.Mushroom()))));
		Register("mushroom_stem_woodcutter", MUSHROOM_STEM_WOODCUTTER, List.of(EN_US.Woodcutter(EN_US.Stem(EN_US.Mushroom()))));
		Register("mushroom_stem_barrel", MUSHROOM_STEM_BARREL, List.of(EN_US.Barrel(EN_US.Stem(EN_US.Mushroom()))));
		Register("mushroom_stem_lectern", MUSHROOM_STEM_LECTERN, List.of(EN_US.Lectern(EN_US.Stem(EN_US.Mushroom()))));
		Register("mushroom_stem_powder_keg", MUSHROOM_STEM_POWDER_KEG, List.of(EN_US.Keg(EN_US.Powder(EN_US.Stem(EN_US.Mushroom())))));
		//Torches
		Register("mushroom_stem_torch", "mushroom_stem_wall_torch", MUSHROOM_STEM_TORCH, List.of(EN_US._Torch(EN_US.Stem(EN_US.Mushroom()))));
		Register("mushroom_stem_soul_torch", "mushroom_stem_soul_wall_torch", MUSHROOM_STEM_SOUL_TORCH, List.of(EN_US._Torch(EN_US.Soul(EN_US.Stem(EN_US.Mushroom())))));
		Register("mushroom_stem_ender_torch", "mushroom_stem_ender_wall_torch", MUSHROOM_STEM_ENDER_TORCH, List.of(EN_US._Torch(EN_US.Ender(EN_US.Stem(EN_US.Mushroom())))));
		Register("underwater_mushroom_stem_torch", "underwater_mushroom_stem_wall_torch", UNDERWATER_MUSHROOM_STEM_TORCH, List.of(EN_US._Torch(EN_US.Stem(EN_US.Mushroom(EN_US.Underwater())))));
		//Campfires
		Register("mushroom_stem_campfire", MUSHROOM_STEM_CAMPFIRE, List.of(EN_US.Campfire(EN_US.Stem(EN_US.Mushroom()))));
		Register("mushroom_stem_soul_campfire", MUSHROOM_STEM_SOUL_CAMPFIRE, List.of(EN_US.Campfire(EN_US.Soul(EN_US.Stem(EN_US.Mushroom())))));
		Register("mushroom_stem_ender_campfire", MUSHROOM_STEM_ENDER_CAMPFIRE, List.of(EN_US.Campfire(EN_US.Ender(EN_US.Stem(EN_US.Mushroom())))));
		//</editor-fold>
	}
	private static void RegisterPlushies() {
		//<editor-fold desc="Allays & Vexes">
		Register("allay_plushie", ALLAY_PLUSHIE, List.of(EN_US.Plushie(EN_US.Allay())));
		Register("vex_plushie", VEX_PLUSHIE, List.of(EN_US.Plushie(EN_US.Vex())));
		//</editor-fold>
		//<editor-fold desc="Axolotls">
		Register("blue_axolotl_plushie", BLUE_AXOLOTL_PLUSHIE, List.of(EN_US.Plushie(EN_US.Axolotl(EN_US.Blue()))));
		Register("cyan_axolotl_plushie", CYAN_AXOLOTL_PLUSHIE, List.of(EN_US.Plushie(EN_US.Axolotl(EN_US.Cyan()))));
		Register("gold_axolotl_plushie", GOLD_AXOLOTL_PLUSHIE, List.of(EN_US.Plushie(EN_US.Axolotl(EN_US.Gold()))));
		Register("lucy_axolotl_plushie", LUCY_AXOLOTL_PLUSHIE, List.of(EN_US.Plushie(EN_US.Axolotl(EN_US.Lucy()))));
		Register("wild_axolotl_plushie", WILD_AXOLOTL_PLUSHIE, List.of(EN_US.Plushie(EN_US.Axolotl(EN_US.Wild()))));
		//Haven
		Register("black_axolotl_plushie", BLACK_AXOLOTL_PLUSHIE, List.of(EN_US.Plushie(EN_US.Axolotl(EN_US.Black()))));
		Register("cyanide_axolotl_plushie", CYANIDE_AXOLOTL_PLUSHIE, List.of(EN_US.Plushie(EN_US.Axolotl(EN_US.Cyanide()))));
		Register("glow_axolotl_plushie", GLOW_AXOLOTL_PLUSHIE, List.of(EN_US.Plushie(EN_US.Axolotl(EN_US.Glow()))));
		Register("golden_axolotl_plushie", GOLDEN_AXOLOTL_PLUSHIE, List.of(EN_US.Plushie(EN_US.Axolotl(EN_US.Golden()))));
		Register("green_axolotl_plushie", GREEN_AXOLOTL_PLUSHIE, List.of(EN_US.Plushie(EN_US.Axolotl(EN_US.Green()))));
		Register("lucia_axolotl_plushie", LUCIA_AXOLOTL_PLUSHIE, List.of(EN_US.Plushie(EN_US.Axolotl(EN_US.Lucia()))));
		Register("red_axolotl_plushie", RED_AXOLOTL_PLUSHIE, List.of(EN_US.Plushie(EN_US.Axolotl(EN_US.Red()))));
		Register("sculk_axolotl_plushie", SCULK_AXOLOTL_PLUSHIE, List.of(EN_US.Plushie(EN_US.Axolotl(EN_US.Sculk()))));
		Register("white_axolotl_plushie", WHITE_AXOLOTL_PLUSHIE, List.of(EN_US.Plushie(EN_US.Axolotl(EN_US.White()))));
		Register("wilder_axolotl_plushie", WILDER_AXOLOTL_PLUSHIE, List.of(EN_US.Plushie(EN_US.Axolotl(EN_US.Wilder()))));
		//</editor-fold>
		Register("bat_plushie", BAT_PLUSHIE, List.of(EN_US.Plushie(EN_US.Bat())));
		Register("bee_plushie", BEE_PLUSHIE, List.of(EN_US.Plushie(EN_US.Bee())));
		//<editor-fold desc="Camel">
		Register("camel_plushie", CAMEL_PLUSHIE, List.of(EN_US.Plushie(EN_US.Camel())));
		Register("saddled_camel_plushie", SADDLED_CAMEL_PLUSHIE, List.of(EN_US.Plushie(EN_US.Camel(EN_US.Saddled()))));
		//</editor-fold>
		//<editor-fold desc="Cat">
		Register("ocelot_plushie", OCELOT_PLUSHIE, List.of(EN_US.Plushie(EN_US.Ocelot())));
		Register("all_black_cat_plushie", ALL_BLACK_CAT_PLUSHIE, List.of(EN_US.Plushie(EN_US.Cat(EN_US.Black(EN_US.All())))));
		Register("black_cat_plushie", BLACK_CAT_PLUSHIE, List.of(EN_US.Plushie(EN_US.Cat(EN_US.Black()))));
		Register("british_shorthair_cat_plushie", BRITISH_SHORTHAIR_CAT_PLUSHIE, List.of(EN_US.Plushie(EN_US.Cat(EN_US.Shorthair(EN_US.British())))));
		Register("calico_cat_plushie", CALICO_CAT_PLUSHIE, List.of(EN_US.Plushie(EN_US.Cat(EN_US.Calico()))));
		Register("jellie_cat_plushie", JELLIE_CAT_PLUSHIE, List.of(EN_US.Plushie(EN_US.Cat(EN_US.Jellie()))));
		Register("persian_cat_plushie", PERSIAN_CAT_PLUSHIE, List.of(EN_US.Plushie(EN_US.Cat(EN_US.Persian()))));
		Register("ragdoll_cat_plushie", RAGDOLL_CAT_PLUSHIE, List.of(EN_US.Plushie(EN_US.Cat(EN_US.Ragdoll()))));
		Register("red_cat_plushie", RED_CAT_PLUSHIE, List.of(EN_US.Plushie(EN_US.Cat(EN_US.Red()))));
		Register("siamese_cat_plushie", SIAMESE_CAT_PLUSHIE, List.of(EN_US.Plushie(EN_US.Cat(EN_US.Siamese()))));
		Register("tabby_cat_plushie", TABBY_CAT_PLUSHIE, List.of(EN_US.Plushie(EN_US.Cat(EN_US.Tabby()))));
		Register("white_cat_plushie", WHITE_CAT_PLUSHIE, List.of(EN_US.Plushie(EN_US.Cat(EN_US.White()))));
		//</editor-fold>
		//<editor-fold desc="Chickens">
		Register("chicken_plushie", CHICKEN_PLUSHIE, List.of(EN_US.Plushie(EN_US.Chicken())));
		Register("fancy_chicken_plushie", FANCY_CHICKEN_PLUSHIE, List.of(EN_US.Plushie(EN_US.Chicken(EN_US.Fancy()))));
		Register("amber_chicken_plushie", AMBER_CHICKEN_PLUSHIE, List.of(EN_US.Plushie(EN_US.Chicken(EN_US.Amber()))));
		Register("bronzed_chicken_plushie", BRONZED_CHICKEN_PLUSHIE, List.of(EN_US.Plushie(EN_US.Chicken(EN_US.Bronzed()))));
		Register("gold_crested_chicken_plushie", GOLD_CRESTED_CHICKEN_PLUSHIE, List.of(EN_US.Plushie(EN_US.Chicken(EN_US.Crested(EN_US.Gold())))));
		Register("midnight_chicken_plushie", MIDNIGHT_CHICKEN_PLUSHIE, List.of(EN_US.Plushie(EN_US.Chicken(EN_US.Midnight()))));
		Register("skewbald_chicken_plushie", SKEWBALD_CHICKEN_PLUSHIE, List.of(EN_US.Plushie(EN_US.Chicken(EN_US.Skewbald()))));
		Register("stormy_chicken_plushie", STORMY_CHICKEN_PLUSHIE, List.of(EN_US.Plushie(EN_US.Chicken(EN_US.Stormy()))));
		//</editor-fold>
		//<editor-fold desc="Cows">
		Register("cow_plushie", COW_PLUSHIE, List.of(EN_US.Plushie(EN_US.Cow())));
		Register("albino_cow_plushie", ALBINO_COW_PLUSHIE, List.of(EN_US.Plushie(EN_US.Cow(EN_US.Albino()))));
		Register("ashen_cow_plushie", ASHEN_COW_PLUSHIE, List.of(EN_US.Plushie(EN_US.Cow(EN_US.Ashen()))));
		Register("cookie_cow_plushie", COOKIE_COW_PLUSHIE, List.of(EN_US.Plushie(EN_US.Cow(EN_US.Cookie()))));
		Register("cream_cow_plushie", CREAM_COW_PLUSHIE, List.of(EN_US.Plushie(EN_US.Cow(EN_US.Cream()))));
		Register("dairy_cow_plushie", DAIRY_COW_PLUSHIE, List.of(EN_US.Plushie(EN_US.Cow(EN_US.Dairy()))));
		Register("pinto_cow_plushie", PINTO_COW_PLUSHIE, List.of(EN_US.Plushie(EN_US.Cow(EN_US.Pinto()))));
		Register("sunset_cow_plushie", SUNSET_COW_PLUSHIE, List.of(EN_US.Plushie(EN_US.Cow(EN_US.Sunset()))));
		Register("umbra_cow_plushie", UMBRA_COW_PLUSHIE, List.of(EN_US.Plushie(EN_US.Cow(EN_US.Umbra()))));
		Register("sheared_umbra_cow_plushie", SHEARED_UMBRA_COW_PLUSHIE, List.of(EN_US.Plushie(EN_US.Cow(EN_US.Umbra(EN_US.Sheared())))));
		Register("wooly_cow_plushie", WOOLY_COW_PLUSHIE, List.of(EN_US.Plushie(EN_US.Cow(EN_US.Wooly()))));
		Register("sheared_wooly_cow_plushie", SHEARED_WOOLY_COW_PLUSHIE, List.of(EN_US.Plushie(EN_US.Cow(EN_US.Wooly(EN_US.Sheared())))));
		Register("mooshroom_plushie", MOOSHROOM_PLUSHIE, List.of(EN_US.Plushie(EN_US.Mooshroom())));
		Register("brown_mooshroom_plushie", BROWN_MOOSHROOM_PLUSHIE, List.of(EN_US.Plushie(EN_US.Mooshroom(EN_US.Brown()))));
		Register("blue_mooshroom_plushie", BLUE_MOOSHROOM_PLUSHIE, List.of(EN_US.Plushie(EN_US.Mooshroom(EN_US.Blue()))));
		Register("crimson_mooshroom_plushie", CRIMSON_MOOSHROOM_PLUSHIE, List.of(EN_US.Plushie(EN_US.Mooshroom(EN_US.Crimson()))));
		Register("gilded_mooshroom_plushie", WARPED_MOOSHROOM_PLUSHIE, List.of(EN_US.Plushie(EN_US.Mooshroom(EN_US.Warped()))));
		Register("warped_mooshroom_plushie", GILDED_MOOSHROOM_PLUSHIE, List.of(EN_US.Plushie(EN_US.Mooshroom(EN_US.Gilded()))));
		Register("moobloom_plushie", MOOBLOOM_PLUSHIE, List.of(EN_US.Plushie(EN_US.Moobloom())));
		Register("moolip_plushie", MOOLIP_PLUSHIE, List.of(EN_US.Plushie(EN_US.Moolip())));
		Register("magenta_tulip_mooblossom_plushie", MAGENTA_TULIP_MOOBLOSSOM_PLUSHIE, List.of(EN_US.Plushie(EN_US.Mooblossom(EN_US.Tulip(EN_US.Magenta())))));
		Register("red_tulip_mooblossom_plushie", RED_TULIP_MOOBLOSSOM_PLUSHIE, List.of(EN_US.Plushie(EN_US.Mooblossom(EN_US.Tulip(EN_US.Red())))));
		Register("orange_tulip_mooblossom_plushie", ORANGE_TULIP_MOOBLOSSOM_PLUSHIE, List.of(EN_US.Plushie(EN_US.Mooblossom(EN_US.Tulip(EN_US.Orange())))));
		Register("white_tulip_mooblossom_plushie", WHITE_TULIP_MOOBLOSSOM_PLUSHIE, List.of(EN_US.Plushie(EN_US.Mooblossom(EN_US.Tulip(EN_US.White())))));
		Register("pink_tulip_mooblossom_plushie", PINK_TULIP_MOOBLOSSOM_PLUSHIE, List.of(EN_US.Plushie(EN_US.Mooblossom(EN_US.Tulip(EN_US.Pink())))));
		//</editor-fold>
		Register("creeper_plushie", CREEPER_PLUSHIE, List.of(EN_US.Plushie(EN_US.Creeper())));
		Register("dolphin_plushie", DOLPHIN_PLUSHIE, List.of(EN_US.Plushie(EN_US.Dolphin())));
		Register("ender_dragon_plushie", ENDER_DRAGON_PLUSHIE, List.of(EN_US.Plushie(EN_US.Dragon(EN_US.Ender()))));
		//<editor-fold desc="Enderman">
		Register("enderman_plushie", ENDERMAN_PLUSHIE, List.of(EN_US.Plushie(EN_US.Enderman())));
		Register("white_enderman_plushie", WHITE_ENDERMAN_PLUSHIE, List.of(EN_US.Plushie(EN_US.Enderman(EN_US.White()))));
		//</editor-fold>
		//<editor-fold desc="Fox">
		Register("fox_plushie", FOX_PLUSHIE, List.of(EN_US.Plushie(EN_US.Fox())));
		Register("snow_fox_plushie", SNOW_FOX_PLUSHIE, List.of(EN_US.Plushie(EN_US.Fox(EN_US.Snow()))));
		//</editor-fold>
		//<editor-fold desc="Frog">
		Register("cold_frog_plushie", COLD_FROG_PLUSHIE, List.of(EN_US.Plushie(EN_US.Frog(EN_US.Cold()))));
		Register("temperate_frog_plushie", TEMPERATE_FROG_PLUSHIE, List.of(EN_US.Plushie(EN_US.Frog(EN_US.Temperate()))));
		Register("warm_frog_plushie", WARM_FROG_PLUSHIE, List.of(EN_US.Plushie(EN_US.Frog(EN_US.Warm()))));
		//</editor-fold>
		//<editor-fold desc="Goat">
		Register("goat_plushie", GOAT_PLUSHIE, List.of(EN_US.Plushie(EN_US.Goat())));
		Register("dark_goat_plushie", DARK_GOAT_PLUSHIE, List.of(EN_US.Plushie(EN_US.Goat(EN_US.Dark()))));
		//</editor-fold>
		//<editor-fold desc="Llama">
		Register("brown_llama_plushie", BROWN_LLAMA_PLUSHIE, List.of(EN_US.Plushie(EN_US.Llama(EN_US.Brown()))));
		Register("brown_trader_llama_plushie", BROWN_TRADER_LLAMA_PLUSHIE, List.of(EN_US.Plushie(EN_US.Llama(EN_US.Trader(EN_US.Brown())))));
		Register("brown_jolly_llama_plushie", BROWN_JOLLY_LLAMA_PLUSHIE, List.of(EN_US.Plushie(EN_US.Llama(EN_US.Jolly(EN_US.Brown())))));
		Register("rainbow_carpeted_brown_llama_plushie", RAINBOW_CARPETED_BROWN_LLAMA_PLUSHIE, List.of(EN_US.Plushie(EN_US.Llama(EN_US.Brown(EN_US.Carpeted(EN_US.Rainbow()))))));
		Register("moss_carpeted_brown_llama_plushie", MOSS_CARPETED_BROWN_LLAMA_PLUSHIE, List.of(EN_US.Plushie(EN_US.Llama(EN_US.Brown(EN_US.Carpeted(EN_US.Moss()))))));
		Register("glow_lichen_carpeted_brown_llama_plushie", GLOW_LICHEN_CARPETED_BROWN_LLAMA_PLUSHIE, List.of(EN_US.Plushie(EN_US.Llama(EN_US.Brown(EN_US.Carpeted(EN_US.Lichen(EN_US.Glow())))))));
		Register("beige_carpeted_brown_llama_plushie", BEIGE_CARPETED_BROWN_LLAMA_PLUSHIE, List.of(EN_US.Plushie(EN_US.Llama(EN_US.Brown(EN_US.Carpeted(EN_US.Beige()))))));
		Register("burgundy_carpeted_brown_llama_plushie", BURGUNDY_CARPETED_BROWN_LLAMA_PLUSHIE, List.of(EN_US.Plushie(EN_US.Llama(EN_US.Brown(EN_US.Carpeted(EN_US.Burgundy()))))));
		Register("lavender_carpeted_brown_llama_plushie", LAVENDER_CARPETED_BROWN_LLAMA_PLUSHIE, List.of(EN_US.Plushie(EN_US.Llama(EN_US.Brown(EN_US.Carpeted(EN_US.Lavender()))))));
		Register("mint_carpeted_brown_llama_plushie", MINT_CARPETED_BROWN_LLAMA_PLUSHIE, List.of(EN_US.Plushie(EN_US.Llama(EN_US.Brown(EN_US.Carpeted(EN_US.Mint()))))));
		for (DyeColor color : DyeColor.values()) {
			Register(color.getName() + "_carpeted_brown_llama_plushie", CARPETED_BROWN_LLAMA_PLUSHIES.get(color), List.of(EN_US.Plushie(EN_US.Llama(EN_US.Brown(EN_US.Carpeted(EN_US.Color(color)))))));
		}
		Register("creamy_llama_plushie", CREAMY_LLAMA_PLUSHIE, List.of(EN_US.Plushie(EN_US.Llama(EN_US.Creamy()))));
		Register("creamy_trader_llama_plushie", CREAMY_TRADER_LLAMA_PLUSHIE, List.of(EN_US.Plushie(EN_US.Llama(EN_US.Trader(EN_US.Creamy())))));
		Register("creamy_jolly_llama_plushie", CREAMY_JOLLY_LLAMA_PLUSHIE, List.of(EN_US.Plushie(EN_US.Llama(EN_US.Jolly(EN_US.Creamy())))));
		Register("rainbow_carpeted_creamy_llama_plushie", RAINBOW_CARPETED_CREAMY_LLAMA_PLUSHIE, List.of(EN_US.Plushie(EN_US.Llama(EN_US.Creamy(EN_US.Carpeted(EN_US.Rainbow()))))));
		Register("moss_carpeted_creamy_llama_plushie", MOSS_CARPETED_CREAMY_LLAMA_PLUSHIE, List.of(EN_US.Plushie(EN_US.Llama(EN_US.Creamy(EN_US.Carpeted(EN_US.Moss()))))));
		Register("glow_lichen_carpeted_creamy_llama_plushie", GLOW_LICHEN_CARPETED_CREAMY_LLAMA_PLUSHIE, List.of(EN_US.Plushie(EN_US.Llama(EN_US.Creamy(EN_US.Carpeted(EN_US.Lichen(EN_US.Glow())))))));
		Register("beige_carpeted_creamy_llama_plushie", BEIGE_CARPETED_CREAMY_LLAMA_PLUSHIE, List.of(EN_US.Plushie(EN_US.Llama(EN_US.Creamy(EN_US.Carpeted(EN_US.Beige()))))));
		Register("burgundy_carpeted_creamy_llama_plushie", BURGUNDY_CARPETED_CREAMY_LLAMA_PLUSHIE, List.of(EN_US.Plushie(EN_US.Llama(EN_US.Creamy(EN_US.Carpeted(EN_US.Burgundy()))))));
		Register("lavender_carpeted_creamy_llama_plushie", LAVENDER_CARPETED_CREAMY_LLAMA_PLUSHIE, List.of(EN_US.Plushie(EN_US.Llama(EN_US.Creamy(EN_US.Carpeted(EN_US.Lavender()))))));
		Register("mint_carpeted_creamy_llama_plushie", MINT_CARPETED_CREAMY_LLAMA_PLUSHIE, List.of(EN_US.Plushie(EN_US.Llama(EN_US.Creamy(EN_US.Carpeted(EN_US.Mint()))))));
		for (DyeColor color : DyeColor.values()) {
			Register(color.getName() + "_carpeted_creamy_llama_plushie", CARPETED_CREAMY_LLAMA_PLUSHIES.get(color), List.of(EN_US.Plushie(EN_US.Llama(EN_US.Creamy(EN_US.Carpeted(EN_US.Color(color)))))));
		}
		Register("gray_llama_plushie", GRAY_LLAMA_PLUSHIE, List.of(EN_US.Plushie(EN_US.Llama(EN_US.Gray()))));
		Register("gray_trader_llama_plushie", GRAY_TRADER_LLAMA_PLUSHIE, List.of(EN_US.Plushie(EN_US.Llama(EN_US.Trader(EN_US.Gray())))));
		Register("gray_jolly_llama_plushie", GRAY_JOLLY_LLAMA_PLUSHIE, List.of(EN_US.Plushie(EN_US.Llama(EN_US.Jolly(EN_US.Gray())))));
		Register("rainbow_carpeted_gray_llama_plushie", RAINBOW_CARPETED_GRAY_LLAMA_PLUSHIE, List.of(EN_US.Plushie(EN_US.Llama(EN_US.Gray(EN_US.Carpeted(EN_US.Rainbow()))))));
		Register("moss_carpeted_gray_llama_plushie", MOSS_CARPETED_GRAY_LLAMA_PLUSHIE, List.of(EN_US.Plushie(EN_US.Llama(EN_US.Gray(EN_US.Carpeted(EN_US.Moss()))))));
		Register("glow_lichen_carpeted_gray_llama_plushie", GLOW_LICHEN_CARPETED_GRAY_LLAMA_PLUSHIE, List.of(EN_US.Plushie(EN_US.Llama(EN_US.Gray(EN_US.Carpeted(EN_US.Lichen(EN_US.Glow())))))));
		Register("beige_carpeted_gray_llama_plushie", BEIGE_CARPETED_GRAY_LLAMA_PLUSHIE, List.of(EN_US.Plushie(EN_US.Llama(EN_US.Gray(EN_US.Carpeted(EN_US.Beige()))))));
		Register("burgundy_carpeted_gray_llama_plushie", BURGUNDY_CARPETED_GRAY_LLAMA_PLUSHIE, List.of(EN_US.Plushie(EN_US.Llama(EN_US.Gray(EN_US.Carpeted(EN_US.Burgundy()))))));
		Register("lavender_carpeted_gray_llama_plushie", LAVENDER_CARPETED_GRAY_LLAMA_PLUSHIE, List.of(EN_US.Plushie(EN_US.Llama(EN_US.Gray(EN_US.Carpeted(EN_US.Lavender()))))));
		Register("mint_carpeted_gray_llama_plushie", MINT_CARPETED_GRAY_LLAMA_PLUSHIE, List.of(EN_US.Plushie(EN_US.Llama(EN_US.Gray(EN_US.Carpeted(EN_US.Mint()))))));
		for (DyeColor color : DyeColor.values()) {
			Register(color.getName() + "_carpeted_gray_llama_plushie", CARPETED_GRAY_LLAMA_PLUSHIES.get(color), List.of(EN_US.Plushie(EN_US.Llama(EN_US.Gray(EN_US.Carpeted(EN_US.Color(color)))))));
		}
		Register("white_llama_plushie", WHITE_LLAMA_PLUSHIE, List.of(EN_US.Plushie(EN_US.Llama(EN_US.White()))));
		Register("white_trader_llama_plushie", WHITE_TRADER_LLAMA_PLUSHIE, List.of(EN_US.Plushie(EN_US.Llama(EN_US.Trader(EN_US.White())))));
		Register("white_jolly_llama_plushie", WHITE_JOLLY_LLAMA_PLUSHIE, List.of(EN_US.Plushie(EN_US.Llama(EN_US.Jolly(EN_US.White())))));
		Register("rainbow_carpeted_white_llama_plushie", RAINBOW_CARPETED_WHITE_LLAMA_PLUSHIE, List.of(EN_US.Plushie(EN_US.Llama(EN_US.White(EN_US.Carpeted(EN_US.Rainbow()))))));
		Register("moss_carpeted_white_llama_plushie", MOSS_CARPETED_WHITE_LLAMA_PLUSHIE, List.of(EN_US.Plushie(EN_US.Llama(EN_US.White(EN_US.Carpeted(EN_US.Moss()))))));
		Register("glow_lichen_carpeted_white_llama_plushie", GLOW_LICHEN_CARPETED_WHITE_LLAMA_PLUSHIE, List.of(EN_US.Plushie(EN_US.Llama(EN_US.White(EN_US.Carpeted(EN_US.Lichen(EN_US.Glow())))))));
		Register("beige_carpeted_white_llama_plushie", BEIGE_CARPETED_WHITE_LLAMA_PLUSHIE, List.of(EN_US.Plushie(EN_US.Llama(EN_US.White(EN_US.Carpeted(EN_US.Beige()))))));
		Register("burgundy_carpeted_white_llama_plushie", BURGUNDY_CARPETED_WHITE_LLAMA_PLUSHIE, List.of(EN_US.Plushie(EN_US.Llama(EN_US.White(EN_US.Carpeted(EN_US.Burgundy()))))));
		Register("lavender_carpeted_white_llama_plushie", LAVENDER_CARPETED_WHITE_LLAMA_PLUSHIE, List.of(EN_US.Plushie(EN_US.Llama(EN_US.White(EN_US.Carpeted(EN_US.Lavender()))))));
		Register("mint_carpeted_white_llama_plushie", MINT_CARPETED_WHITE_LLAMA_PLUSHIE, List.of(EN_US.Plushie(EN_US.Llama(EN_US.White(EN_US.Carpeted(EN_US.Mint()))))));
		for (DyeColor color : DyeColor.values()) {
			Register(color.getName() + "_carpeted_white_llama_plushie", CARPETED_WHITE_LLAMA_PLUSHIES.get(color), List.of(EN_US.Plushie(EN_US.Llama(EN_US.White(EN_US.Carpeted(EN_US.Color(color)))))));
		}
		Register("mocha_llama_plushie", MOCHA_LLAMA_PLUSHIE, List.of(EN_US.Plushie(EN_US.Llama(EN_US.Mocha()))));
		Register("mocha_trader_llama_plushie", MOCHA_TRADER_LLAMA_PLUSHIE, List.of(EN_US.Plushie(EN_US.Llama(EN_US.Trader(EN_US.Mocha())))));
		Register("mocha_jolly_llama_plushie", MOCHA_JOLLY_LLAMA_PLUSHIE, List.of(EN_US.Plushie(EN_US.Llama(EN_US.Jolly(EN_US.Mocha())))));
		Register("rainbow_carpeted_mocha_llama_plushie", RAINBOW_CARPETED_MOCHA_LLAMA_PLUSHIE, List.of(EN_US.Plushie(EN_US.Llama(EN_US.Mocha(EN_US.Carpeted(EN_US.Rainbow()))))));
		Register("moss_carpeted_mocha_llama_plushie", MOSS_CARPETED_MOCHA_LLAMA_PLUSHIE, List.of(EN_US.Plushie(EN_US.Llama(EN_US.Mocha(EN_US.Carpeted(EN_US.Moss()))))));
		Register("glow_lichen_carpeted_mocha_llama_plushie", GLOW_LICHEN_CARPETED_MOCHA_LLAMA_PLUSHIE, List.of(EN_US.Plushie(EN_US.Llama(EN_US.Mocha(EN_US.Carpeted(EN_US.Lichen(EN_US.Glow())))))));
		Register("beige_carpeted_mocha_llama_plushie", BEIGE_CARPETED_MOCHA_LLAMA_PLUSHIE, List.of(EN_US.Plushie(EN_US.Llama(EN_US.Mocha(EN_US.Carpeted(EN_US.Beige()))))));
		Register("burgundy_carpeted_mocha_llama_plushie", BURGUNDY_CARPETED_MOCHA_LLAMA_PLUSHIE, List.of(EN_US.Plushie(EN_US.Llama(EN_US.Mocha(EN_US.Carpeted(EN_US.Burgundy()))))));
		Register("lavender_carpeted_mocha_llama_plushie", LAVENDER_CARPETED_MOCHA_LLAMA_PLUSHIE, List.of(EN_US.Plushie(EN_US.Llama(EN_US.Mocha(EN_US.Carpeted(EN_US.Lavender()))))));
		Register("mint_carpeted_mocha_llama_plushie", MINT_CARPETED_MOCHA_LLAMA_PLUSHIE, List.of(EN_US.Plushie(EN_US.Llama(EN_US.Mocha(EN_US.Carpeted(EN_US.Mint()))))));
		for (DyeColor color : DyeColor.values()) {
			Register(color.getName() + "_carpeted_mocha_llama_plushie", CARPETED_MOCHA_LLAMA_PLUSHIES.get(color), List.of(EN_US.Plushie(EN_US.Llama(EN_US.Mocha(EN_US.Carpeted(EN_US.Color(color)))))));
		}
		Register("cocoa_llama_plushie", COCOA_LLAMA_PLUSHIE, List.of(EN_US.Plushie(EN_US.Llama(EN_US.Cocoa()))));
		Register("cocoa_trader_llama_plushie", COCOA_TRADER_LLAMA_PLUSHIE, List.of(EN_US.Plushie(EN_US.Llama(EN_US.Trader(EN_US.Cocoa())))));
		Register("cocoa_jolly_llama_plushie", COCOA_JOLLY_LLAMA_PLUSHIE, List.of(EN_US.Plushie(EN_US.Llama(EN_US.Jolly(EN_US.Cocoa())))));
		Register("rainbow_carpeted_cocoa_llama_plushie", RAINBOW_CARPETED_COCOA_LLAMA_PLUSHIE, List.of(EN_US.Plushie(EN_US.Llama(EN_US.Cocoa(EN_US.Carpeted(EN_US.Rainbow()))))));
		Register("moss_carpeted_cocoa_llama_plushie", MOSS_CARPETED_COCOA_LLAMA_PLUSHIE, List.of(EN_US.Plushie(EN_US.Llama(EN_US.Cocoa(EN_US.Carpeted(EN_US.Moss()))))));
		Register("glow_lichen_carpeted_cocoa_llama_plushie", GLOW_LICHEN_CARPETED_COCOA_LLAMA_PLUSHIE, List.of(EN_US.Plushie(EN_US.Llama(EN_US.Cocoa(EN_US.Carpeted(EN_US.Lichen(EN_US.Glow())))))));
		Register("beige_carpeted_cocoa_llama_plushie", BEIGE_CARPETED_COCOA_LLAMA_PLUSHIE, List.of(EN_US.Plushie(EN_US.Llama(EN_US.Cocoa(EN_US.Carpeted(EN_US.Beige()))))));
		Register("burgundy_carpeted_cocoa_llama_plushie", BURGUNDY_CARPETED_COCOA_LLAMA_PLUSHIE, List.of(EN_US.Plushie(EN_US.Llama(EN_US.Cocoa(EN_US.Carpeted(EN_US.Burgundy()))))));
		Register("lavender_carpeted_cocoa_llama_plushie", LAVENDER_CARPETED_COCOA_LLAMA_PLUSHIE, List.of(EN_US.Plushie(EN_US.Llama(EN_US.Cocoa(EN_US.Carpeted(EN_US.Lavender()))))));
		Register("mint_carpeted_cocoa_llama_plushie", MINT_CARPETED_COCOA_LLAMA_PLUSHIE, List.of(EN_US.Plushie(EN_US.Llama(EN_US.Cocoa(EN_US.Carpeted(EN_US.Mint()))))));
		for (DyeColor color : DyeColor.values()) {
			Register(color.getName() + "_carpeted_cocoa_llama_plushie", CARPETED_COCOA_LLAMA_PLUSHIES.get(color), List.of(EN_US.Plushie(EN_US.Llama(EN_US.Cocoa(EN_US.Carpeted(EN_US.Color(color)))))));
		}
		//</editor-fold>
		//<editor-fold desc="Panda">
		Register("panda_plushie", PANDA_PLUSHIE, List.of(EN_US.Plushie(EN_US.Panda())));
		Register("brown_panda_plushie", BROWN_PANDA_PLUSHIE, List.of(EN_US.Plushie(EN_US.Panda(EN_US.Brown()))));
		//</editor-fold>
		//<editor-fold desc="Parrot">
		Register("blue_parrot_plushie", BLUE_PARROT_PLUSHIE, List.of(EN_US.Plushie(EN_US.Parrot(EN_US.Blue()))));
		Register("green_parrot_plushie", GREEN_PARROT_PLUSHIE, List.of(EN_US.Plushie(EN_US.Parrot(EN_US.Green()))));
		Register("grey_parrot_plushie", GREY_PARROT_PLUSHIE, List.of(EN_US.Plushie(EN_US.Parrot(EN_US.Grey()))));
		Register("red_parrot_plushie", RED_PARROT_PLUSHIE, List.of(EN_US.Plushie(EN_US.Parrot(EN_US.Red()))));
		Register("yellow_blue_parrot_plushie", YELLOW_BLUE_PARROT_PLUSHIE, List.of(EN_US.Plushie(EN_US.Parrot(EN_US.Blue(EN_US.Yellow())))));
		Register("golden_parrot_plushie", GOLDEN_PARROT_PLUSHIE, List.of(EN_US.Plushie(EN_US.Parrot(EN_US.Golden()))));
		//</editor-fold>
		//<editor-fold desc="Pigs">
		Register("pig_plushie", PIG_PLUSHIE, List.of(EN_US.Plushie(EN_US.Pig())));
		Register("saddled_pig_plushie", SADDLED_PIG_PLUSHIE, List.of(EN_US.Plushie(EN_US.Pig(EN_US.Saddled()))));
		Register("mottled_pig_plushie", MOTTLED_PIG_PLUSHIE, List.of(EN_US.Plushie(EN_US.Pig(EN_US.Mottled()))));
		Register("muddy_pig_plushie", MUDDY_PIG_PLUSHIE, List.of(EN_US.Plushie(EN_US.Pig(EN_US.Muddy()))));
		Register("dried_muddy_pig_plushie", DRIED_MUDDY_PIG_PLUSHIE, List.of(EN_US.Plushie(EN_US.Pig(EN_US.Muddy(EN_US.Dried())))));
		Register("pale_pig_plushie", PALE_PIG_PLUSHIE, List.of(EN_US.Plushie(EN_US.Pig(EN_US.Pale()))));
		Register("piebald_pig_plushie", PIEBALD_PIG_PLUSHIE, List.of(EN_US.Plushie(EN_US.Pig(EN_US.Piebald()))));
		Register("pink_footed_pig_plushie", PINK_FOOTED_PIG_PLUSHIE, List.of(EN_US.Plushie(EN_US.Pig(EN_US.Footed(EN_US.Pink())))));
		Register("sooty_pig_plushie", SOOTY_PIG_PLUSHIE, List.of(EN_US.Plushie(EN_US.Pig(EN_US.Sooty()))));
		Register("spotted_pig_plushie", SPOTTED_PIG_PLUSHIE, List.of(EN_US.Plushie(EN_US.Pig(EN_US.Spotted()))));
		//</editor-fold>
		Register("polar_bear_plushie", POLAR_BEAR_PLUSHIE, List.of(EN_US.Plushie(EN_US.Bear(EN_US.Polar()))));
		//<editor-fold desc="Sheep">
		for (DyeColor color : DyeColor.values()) {
			Register(color.getName() + "_sheep_plushie", SHEEP_PLUSHIES.get(color), List.of(EN_US.Plushie(EN_US.Plushie(EN_US.Sheep(EN_US.Color(color))))));
		}
		Register("golden_sheep_plushie", GOLDEN_SHEEP_PLUSHIE, List.of(EN_US.Plushie(EN_US.Sheep(EN_US.Golden()))));
		Register("flecked_sheep_plushie", FLECKED_SHEEP_PLUSHIE, List.of(EN_US.Plushie(EN_US.Sheep(EN_US.Flecked()))));
		Register("fuzzy_sheep_plushie", FUZZY_SHEEP_PLUSHIE, List.of(EN_US.Plushie(EN_US.Sheep(EN_US.Fuzzy()))));
		Register("inky_sheep_plushie", INKY_SHEEP_PLUSHIE, List.of(EN_US.Plushie(EN_US.Sheep(EN_US.Inky()))));
		Register("long_nose_sheep_plushie", LONG_NOSE_SHEEP_PLUSHIE, List.of(EN_US.Plushie(EN_US.Sheep(EN_US.Nose(EN_US.Long())))));
		Register("patched_sheep_plushie", PATCHED_SHEEP_PLUSHIE, List.of(EN_US.Plushie(EN_US.Sheep(EN_US.Patched()))));
		Register("rocky_sheep_plushie", ROCKY_SHEEP_PLUSHIE, List.of(EN_US.Plushie(EN_US.Sheep(EN_US.Rocky()))));
		Register("rainbow_sheep_plushie", RAINBOW_SHEEP_PLUSHIE, List.of(EN_US.Plushie(EN_US.Sheep(EN_US.Rainbow()))));
		Register("mossy_sheep_plushie", MOSSY_SHEEP_PLUSHIE, List.of(EN_US.Plushie(EN_US.Sheep(EN_US.Mossy()))));
		//</editor-fold>
		//<editor-fold desc="Sheep (Sheared)">'
		Register("sheared_sheep_plushie", SHEARED_SHEEP_PLUSHIE, List.of(EN_US.Plushie(EN_US.Sheep(EN_US.Sheared()))));
		Register("sheared_flecked_sheep_plushie", SHEARED_FLECKED_SHEEP_PLUSHIE, List.of(EN_US.Plushie(EN_US.Sheep(EN_US.Flecked(EN_US.Sheared())))));
		Register("sheared_fuzzy_sheep_plushie", SHEARED_FUZZY_SHEEP_PLUSHIE, List.of(EN_US.Plushie(EN_US.Sheep(EN_US.Fuzzy(EN_US.Sheared())))));
		Register("sheared_inky_sheep_plushie", SHEARED_INKY_SHEEP_PLUSHIE, List.of(EN_US.Plushie(EN_US.Sheep(EN_US.Inky(EN_US.Sheared())))));
		Register("sheared_long_nose_sheep_plushie", SHEARED_LONG_NOSE_SHEEP_PLUSHIE, List.of(EN_US.Plushie(EN_US.Sheep(EN_US.Nose(EN_US.Long(EN_US.Sheared()))))));
		Register("sheared_patched_sheep_plushie", SHEARED_PATCHED_SHEEP_PLUSHIE, List.of(EN_US.Plushie(EN_US.Sheep(EN_US.Patched(EN_US.Sheared())))));
		Register("sheared_rocky_sheep_plushie", SHEARED_ROCKY_SHEEP_PLUSHIE, List.of(EN_US.Plushie(EN_US.Sheep(EN_US.Rocky(EN_US.Sheared())))));
		Register("sheared_rainbow_sheep_plushie", SHEARED_RAINBOW_SHEEP_PLUSHIE, List.of(EN_US.Plushie(EN_US.Sheep(EN_US.Rainbow(EN_US.Sheared())))));
		Register("sheared_mossy_sheep_plushie", SHEARED_MOSSY_SHEEP_PLUSHIE, List.of(EN_US.Plushie(EN_US.Sheep(EN_US.Mossy(EN_US.Sheared())))));
		//</editor-fold>
		//<editor-fold desc="Slime">
		Register("magma_cube_plushie", MAGMA_CUBE_PLUSHIE, List.of(EN_US.Plushie(EN_US.Cube(EN_US.Magma()))));
		Register("slime_plushie", SLIME_PLUSHIE, List.of(EN_US.Plushie(EN_US.Slime())));
		Register("tropical_slime_plushie", TROPICAL_SLIME_PLUSHIE, List.of(EN_US.Plushie(EN_US.Slime(EN_US.Tropical()))));
		Register("pink_slime_plushie", PINK_SLIME_PLUSHIE, List.of(EN_US.Plushie(EN_US.Slime(EN_US.Pink()))));
		//</editor-fold>
		//<editor-fold desc="Snow Golems">
		Register("snow_golem_plushie", SNOW_GOLEM_PLUSHIE, List.of(EN_US.Plushie(EN_US.Golem(EN_US.Snow()))));
		Register("white_snow_golem_plushie", WHITE_SNOW_GOLEM_PLUSHIE, List.of(EN_US.Plushie(EN_US.Golem(EN_US.Snow(EN_US.White())))));
		Register("rotten_snow_golem_plushie", ROTTEN_SNOW_GOLEM_PLUSHIE, List.of(EN_US.Plushie(EN_US.Golem(EN_US.Snow(EN_US.Rotten())))));
		Register("melon_golem_plushie", MELON_GOLEM_PLUSHIE, List.of(EN_US.Plushie(EN_US.Golem(EN_US.Melon()))));
		//</editor-fold>
		//<editor-fold desc="Snow Golems (Sheared)">
		Register("sheared_snow_golem_plushie", SHEARED_SNOW_GOLEM_PLUSHIE, List.of(EN_US.Plushie(EN_US.Golem(EN_US.Snow(EN_US.Sheared())))));
		Register("sheared_melon_golem_plushie", SHEARED_MELON_GOLEM_PLUSHIE, List.of(EN_US.Plushie(EN_US.Golem(EN_US.Melon(EN_US.Sheared())))));
		//</editor-fold>
		Register("tadpole_plushie", TADPOLE_PLUSHIE, List.of(EN_US.Plushie(EN_US.Tadpole())));
		//<editor-fold desc="Turtle">
		Register("turtle_plushie", TURTLE_PLUSHIE, List.of(EN_US.Plushie(EN_US.Turtle())));
		Register("ruby_turtle_plushie", RUBY_TURTLE_PLUSHIE, List.of(EN_US.Plushie(EN_US.Turtle(EN_US.Ruby()))));
		//</editor-fold>
		Register("warden_plushie", WARDEN_PLUSHIE, List.of(EN_US.Plushie(EN_US.Warden())));
		//<editor-fold desc="Wolf">
		Register("wolf_plushie", WOLF_PLUSHIE, List.of(EN_US.Plushie(EN_US.Wolf())));
		Register("gray_wolf_plushie", GRAY_WOLF_PLUSHIE, List.of(EN_US.Plushie(EN_US.Wolf(EN_US.Gray()))));
		Register("black_wolf_plushie", BLACK_WOLF_PLUSHIE, List.of(EN_US.Plushie(EN_US.Wolf(EN_US.Black()))));
		Register("brown_wolf_plushie", BROWN_WOLF_PLUSHIE, List.of(EN_US.Plushie(EN_US.Wolf(EN_US.Brown()))));
		//</editor-fold>
	}
	private static void RegisterBrushableBlocks() {
		Register("minecraft:brush", BRUSH, List.of(EN_US.Brush()));
		Register("minecraft:suspicious_sand", SUSPICIOUS_SAND, List.of(EN_US.Sand(EN_US.Suspicious())));
		Register("minecraft:suspicious_gravel", SUSPICIOUS_GRAVEL, List.of(EN_US.Gravel(EN_US.Suspicious())));
		Register("minecraft:brushable_block", SUSPICIOUS_BLOCK_ENTITY);
		//<editor-fold desc="Sandy Blocks">
		Register("sandy_cobblestone", SANDY_COBBLESTONE, List.of(EN_US.Cobblestone(EN_US.Sandy())));
		Register("sandy_cobblestone_slab", SANDY_COBBLESTONE_SLAB, List.of(EN_US.Slab(EN_US.Cobblestone(EN_US.Sandy()))));
		//<editor-fold desc="Andesite">
		Register("sandy_polished_andesite", SANDY_POLISHED_ANDESITE, List.of(EN_US.Andesite(EN_US.Polished(EN_US.Sandy()))));
		Register("sandy_polished_andesite_slab", SANDY_POLISHED_ANDESITE_SLAB, List.of(EN_US.Andesite(EN_US.Slab(EN_US.Polished(EN_US.Sandy())))));
		Register("sandy_andesite_bricks", SANDY_ANDESITE_BRICKS, List.of(EN_US.Bricks(EN_US.Andesite(EN_US.Sandy()))));
		Register("sandy_andesite_brick_slab", SANDY_ANDESITE_BRICK_SLAB, List.of(EN_US.Bricks(EN_US.Slab(EN_US.Andesite(EN_US.Sandy())))));
		Register("sandy_cut_polished_andesite", SANDY_CUT_POLISHED_ANDESITE, List.of(EN_US.Andesite(EN_US.Polished(EN_US.Cut(EN_US.Sandy())))));
		Register("sandy_cut_polished_andesite_slab", SANDY_CUT_POLISHED_ANDESITE_SLAB, List.of(EN_US.Slab(EN_US.Andesite(EN_US.Polished(EN_US.Cut(EN_US.Sandy()))))));
		//</editor-fold>
		//<editor-fold desc="Diorite">
		Register("sandy_polished_diorite", SANDY_POLISHED_DIORITE, List.of(EN_US.Diorite(EN_US.Polished(EN_US.Sandy()))));
		Register("sandy_polished_diorite_slab", SANDY_POLISHED_DIORITE_SLAB, List.of(EN_US.Diorite(EN_US.Slab(EN_US.Polished(EN_US.Sandy())))));
		Register("sandy_diorite_bricks", SANDY_DIORITE_BRICKS, List.of(EN_US.Bricks(EN_US.Diorite(EN_US.Sandy()))));
		Register("sandy_diorite_brick_slab", SANDY_DIORITE_BRICK_SLAB, List.of(EN_US.Bricks(EN_US.Slab(EN_US.Diorite(EN_US.Sandy())))));
		Register("sandy_cut_polished_diorite", SANDY_CUT_POLISHED_DIORITE, List.of(EN_US.Diorite(EN_US.Polished(EN_US.Cut(EN_US.Sandy())))));
		Register("sandy_cut_polished_diorite_slab", SANDY_CUT_POLISHED_DIORITE_SLAB, List.of(EN_US.Slab(EN_US.Diorite(EN_US.Polished(EN_US.Cut(EN_US.Sandy()))))));
		//</editor-fold>
		//<editor-fold desc="Granite">
		Register("sandy_polished_granite", SANDY_POLISHED_GRANITE, List.of(EN_US.Granite(EN_US.Polished(EN_US.Sandy()))));
		Register("sandy_polished_granite_slab", SANDY_POLISHED_GRANITE_SLAB, List.of(EN_US.Granite(EN_US.Slab(EN_US.Polished(EN_US.Sandy())))));
		Register("sandy_granite_bricks", SANDY_GRANITE_BRICKS, List.of(EN_US.Bricks(EN_US.Granite(EN_US.Sandy()))));
		Register("sandy_granite_brick_slab", SANDY_GRANITE_BRICK_SLAB, List.of(EN_US.Bricks(EN_US.Slab(EN_US.Granite(EN_US.Sandy())))));
		Register("sandy_cut_polished_granite", SANDY_CUT_POLISHED_GRANITE, List.of(EN_US.Granite(EN_US.Polished(EN_US.Cut(EN_US.Sandy())))));
		Register("sandy_cut_polished_granite_slab", SANDY_CUT_POLISHED_GRANITE_SLAB, List.of(EN_US.Slab(EN_US.Granite(EN_US.Polished(EN_US.Cut(EN_US.Sandy()))))));
		//</editor-fold>
		//<editor-fold desc="Planks">
		Register("sandy_acacia_planks", SANDY_ACACIA_PLANKS, List.of(EN_US.Planks(EN_US.Acacia(EN_US.Sandy()))));
		Register("sandy_acacia_slab", SANDY_ACACIA_SLAB, List.of(EN_US.Slab(EN_US.Acacia(EN_US.Sandy()))));
		Register("sandy_birch_planks", SANDY_BIRCH_PLANKS, List.of(EN_US.Planks(EN_US.Birch(EN_US.Sandy()))));
		Register("sandy_birch_slab", SANDY_BIRCH_SLAB, List.of(EN_US.Slab(EN_US.Birch(EN_US.Sandy()))));
		Register("sandy_dark_oak_planks", SANDY_DARK_OAK_PLANKS, List.of(EN_US.Planks(EN_US.Oak(EN_US.Dark(EN_US.Sandy())))));
		Register("sandy_dark_oak_slab", SANDY_DARK_OAK_SLAB, List.of(EN_US.Slab(EN_US.Oak(EN_US.Dark(EN_US.Sandy())))));
		Register("sandy_jungle_planks", SANDY_JUNGLE_PLANKS, List.of(EN_US.Planks(EN_US.Jungle(EN_US.Sandy()))));
		Register("sandy_jungle_slab", SANDY_JUNGLE_SLAB, List.of(EN_US.Slab(EN_US.Jungle(EN_US.Sandy()))));
		Register("sandy_oak_planks", SANDY_OAK_PLANKS, List.of(EN_US.Planks(EN_US.Oak(EN_US.Sandy()))));
		Register("sandy_oak_slab", SANDY_OAK_SLAB, List.of(EN_US.Slab(EN_US.Oak(EN_US.Sandy()))));
		Register("sandy_spruce_planks", SANDY_SPRUCE_PLANKS, List.of(EN_US.Planks(EN_US.Spruce(EN_US.Sandy()))));
		Register("sandy_spruce_slab", SANDY_SPRUCE_SLAB, List.of(EN_US.Slab(EN_US.Spruce(EN_US.Sandy()))));
		Register("sandy_crimson_planks", SANDY_CRIMSON_PLANKS, List.of(EN_US.Planks(EN_US.Crimson(EN_US.Sandy()))));
		Register("sandy_crimson_slab", SANDY_CRIMSON_SLAB, List.of(EN_US.Slab(EN_US.Crimson(EN_US.Sandy()))));
		Register("sandy_warped_planks", SANDY_WARPED_PLANKS, List.of(EN_US.Planks(EN_US.Warped(EN_US.Sandy()))));
		Register("sandy_warped_slab", SANDY_WARPED_SLAB, List.of(EN_US.Slab(EN_US.Warped(EN_US.Sandy()))));
		Register("sandy_mangrove_planks", SANDY_MANGROVE_PLANKS, List.of(EN_US.Planks(EN_US.Mangrove(EN_US.Sandy()))));
		Register("sandy_mangrove_slab", SANDY_MANGROVE_SLAB, List.of(EN_US.Slab(EN_US.Mangrove(EN_US.Sandy()))));
		Register("sandy_cherry_planks", SANDY_CHERRY_PLANKS, List.of(EN_US.Planks(EN_US.Cherry(EN_US.Sandy()))));
		Register("sandy_cherry_slab", SANDY_CHERRY_SLAB, List.of(EN_US.Slab(EN_US.Cherry(EN_US.Sandy()))));
		Register("sandy_cassia_planks", SANDY_CASSIA_PLANKS, List.of(EN_US.Planks(EN_US.Cassia(EN_US.Sandy()))));
		Register("sandy_cassia_slab", SANDY_CASSIA_SLAB, List.of(EN_US.Slab(EN_US.Cassia(EN_US.Sandy()))));
		Register("sandy_charred_planks", SANDY_CHARRED_PLANKS, List.of(EN_US.Planks(EN_US.Charred(EN_US.Sandy()))));
		Register("sandy_charred_slab", SANDY_CHARRED_SLAB, List.of(EN_US.Slab(EN_US.Charred(EN_US.Sandy()))));
		Register("sandy_dogwood_planks", SANDY_DOGWOOD_PLANKS, List.of(EN_US.Planks(EN_US.Dogwood(EN_US.Sandy()))));
		Register("sandy_dogwood_slab", SANDY_DOGWOOD_SLAB, List.of(EN_US.Slab(EN_US.Dogwood(EN_US.Sandy()))));
		Register("sandy_gilded_planks", SANDY_GILDED_PLANKS, List.of(EN_US.Planks(EN_US.Gilded(EN_US.Sandy()))));
		Register("sandy_gilded_slab", SANDY_GILDED_SLAB, List.of(EN_US.Slab(EN_US.Gilded(EN_US.Sandy()))));
		Register("sandy_hay_planks", SANDY_HAY_PLANKS, List.of(EN_US.Planks(EN_US.Hay(EN_US.Sandy()))));
		Register("sandy_hay_slab", SANDY_HAY_SLAB, List.of(EN_US.Slab(EN_US.Hay(EN_US.Sandy()))));
		Register("sandy_blue_mushroom_planks", SANDY_BLUE_MUSHROOM_PLANKS, List.of(EN_US.Planks(EN_US.Mushroom(EN_US.Blue(EN_US.Sandy())))));
		Register("sandy_blue_mushroom_slab", SANDY_BLUE_MUSHROOM_SLAB, List.of(EN_US.Slab(EN_US.Mushroom(EN_US.Blue(EN_US.Sandy())))));
		Register("sandy_brown_mushroom_planks", SANDY_BROWN_MUSHROOM_PLANKS, List.of(EN_US.Planks(EN_US.Mushroom(EN_US.Brown(EN_US.Sandy())))));
		Register("sandy_brown_mushroom_slab", SANDY_BROWN_MUSHROOM_SLAB, List.of(EN_US.Slab(EN_US.Mushroom(EN_US.Brown(EN_US.Sandy())))));
		Register("sandy_red_mushroom_planks", SANDY_RED_MUSHROOM_PLANKS, List.of(EN_US.Planks(EN_US.Mushroom(EN_US.Red(EN_US.Sandy())))));
		Register("sandy_red_mushroom_slab", SANDY_RED_MUSHROOM_SLAB, List.of(EN_US.Slab(EN_US.Mushroom(EN_US.Red(EN_US.Sandy())))));
		Register("sandy_mushroom_stem_planks", SANDY_MUSHROOM_STEM_PLANKS, List.of(EN_US.Planks(EN_US.Stem(EN_US.Mushroom(EN_US.Sandy())))));
		Register("sandy_mushroom_stem_slab", SANDY_MUSHROOM_STEM_SLAB, List.of(EN_US.Slab(EN_US.Stem(EN_US.Mushroom(EN_US.Sandy())))));
		//</editor-fold>
		//<editor-fold desc="Prismarine">
		Register("sandy_prismarine", SANDY_PRISMARINE, List.of(EN_US.Prismarine(EN_US.Sandy())));
		Register("sandy_prismarine_slab", SANDY_PRISMARINE_SLAB, List.of(EN_US.Slab(EN_US.Prismarine(EN_US.Sandy()))));
		Register("sandy_sandy_prismarine", SANDY_SANDY_PRISMARINE, List.of(EN_US.Prismarine(EN_US.Sandy(EN_US.Sandy()))));
		Register("sandy_sandy_prismarine_slab", SANDY_SANDY_PRISMARINE_SLAB, List.of(EN_US.Slab(EN_US.Prismarine(EN_US.Sandy(EN_US.Sandy())))));
		Register("sandy_prismarine_bricks", SANDY_PRISMARINE_BRICKS, List.of(EN_US.Bricks(EN_US.Prismarine(EN_US.Sandy()))));
		Register("sandy_prismarine_brick_slab", SANDY_PRISMARINE_BRICK_SLAB, List.of(EN_US.Slab(EN_US.Brick(EN_US.Prismarine(EN_US.Sandy())))));
		Register("sandy_chiseled_prismarine_bricks", SANDY_CHISELED_PRISMARINE_BRICKS, List.of(EN_US.Bricks(EN_US.Prismarine(EN_US.Chiseled(EN_US.Sandy())))));
		Register("sandy_dark_prismarine", SANDY_DARK_PRISMARINE, List.of(EN_US.Prismarine(EN_US.Dark(EN_US.Sandy()))));
		Register("sandy_dark_prismarine_slab", SANDY_DARK_PRISMARINE_SLAB, List.of(EN_US.Slab(EN_US.Prismarine(EN_US.Dark(EN_US.Sandy())))));
		Register("sandy_sandy_dark_prismarine", SANDY_SANDY_DARK_PRISMARINE, List.of(EN_US.Prismarine(EN_US.Dark(EN_US.Sandy(EN_US.Sandy())))));
		Register("sandy_sandy_dark_prismarine_slab", SANDY_SANDY_DARK_PRISMARINE_SLAB, List.of(EN_US.Slab(EN_US.Prismarine(EN_US.Dark(EN_US.Sandy(EN_US.Sandy()))))));
		//</editor-fold>
		//<editor-fold desc="Purpur">
		Register("sandy_purpur_block", SANDY_PURPUR_BLOCK, List.of(EN_US.Block(EN_US.Purpur(EN_US.Sandy()))));
		Register("sandy_purpur_block_slab", SANDY_PURPUR_SLAB, List.of(EN_US.Slab(EN_US.Block(EN_US.Purpur(EN_US.Sandy())))));
		Register("sandy_purpur_bricks", SANDY_PURPUR_BRICKS, List.of(EN_US.Bricks(EN_US.Purpur(EN_US.Sandy()))));
		Register("sandy_purpur_brick_slab", SANDY_PURPUR_BRICK_SLAB, List.of(EN_US.Slab(EN_US.Brick(EN_US.Purpur(EN_US.Sandy())))));
		Register("sandy_smooth_purpur", SANDY_SMOOTH_PURPUR, List.of(EN_US.Purpur(EN_US.Smooth(EN_US.Sandy()))));
		Register("sandy_smooth_purpur_slab", SANDY_SMOOTH_PURPUR_SLAB, List.of(EN_US.Slab(EN_US.Purpur(EN_US.Smooth(EN_US.Sandy())))));
		Register("sandy_chiseled_purpur", SANDY_CHISELED_PURPUR, List.of(EN_US.Purpur(EN_US.Chiseled(EN_US.Sandy()))));
		Register("sandy_chiseled_purpur_slab", SANDY_CHISELED_PURPUR_SLAB, List.of(EN_US.Slab(EN_US.Purpur(EN_US.Chiseled(EN_US.Sandy())))));
		Register("sandy_sandy_chiseled_purpur", SANDY_SANDY_CHISELED_PURPUR, List.of(EN_US.Purpur(EN_US.Chiseled(EN_US.Sandy(EN_US.Sandy())))));
		Register("sandy_sandy_chiseled_purpur_slab", SANDY_SANDY_CHISELED_PURPUR_SLAB, List.of(EN_US.Slab(EN_US.Purpur(EN_US.Chiseled(EN_US.Sandy(EN_US.Sandy()))))));
		//</editor-fold>
		Register("sandy_block", SANDY_BLOCK_ENTITY);
		//</editor-fold>
		//<editor-fold desc="Sandy Blocks (Red)">
		Register("red_sandy_cobblestone", RED_SANDY_COBBLESTONE, List.of(EN_US.Cobblestone(EN_US.Sandy(EN_US.Red()))));
		Register("red_sandy_cobblestone_slab", RED_SANDY_COBBLESTONE_SLAB, List.of(EN_US.Slab(EN_US.Cobblestone(EN_US.Sandy(EN_US.Red())))));
		//<editor-fold desc="Andesite">
		Register("red_sandy_polished_andesite", RED_SANDY_POLISHED_ANDESITE, List.of(EN_US.Andesite(EN_US.Polished(EN_US.Sandy(EN_US.Red())))));
		Register("red_sandy_polished_andesite_slab", RED_SANDY_POLISHED_ANDESITE_SLAB, List.of(EN_US.Andesite(EN_US.Slab(EN_US.Polished(EN_US.Sandy(EN_US.Red()))))));
		Register("red_sandy_andesite_bricks", RED_SANDY_ANDESITE_BRICKS, List.of(EN_US.Bricks(EN_US.Andesite(EN_US.Sandy(EN_US.Red())))));
		Register("red_sandy_andesite_brick_slab", RED_SANDY_ANDESITE_BRICK_SLAB, List.of(EN_US.Bricks(EN_US.Slab(EN_US.Andesite(EN_US.Sandy(EN_US.Red()))))));
		Register("red_sandy_cut_polished_andesite", RED_SANDY_CUT_POLISHED_ANDESITE, List.of(EN_US.Andesite(EN_US.Polished(EN_US.Cut(EN_US.Sandy(EN_US.Red()))))));
		Register("red_sandy_cut_polished_andesite_slab", RED_SANDY_CUT_POLISHED_ANDESITE_SLAB, List.of(EN_US.Slab(EN_US.Andesite(EN_US.Polished(EN_US.Cut(EN_US.Sandy(EN_US.Red())))))));
		//</editor-fold>
		//<editor-fold desc="Diorite">
		Register("red_sandy_polished_diorite", RED_SANDY_POLISHED_DIORITE, List.of(EN_US.Diorite(EN_US.Polished(EN_US.Sandy(EN_US.Red())))));
		Register("red_sandy_polished_diorite_slab", RED_SANDY_POLISHED_DIORITE_SLAB, List.of(EN_US.Diorite(EN_US.Slab(EN_US.Polished(EN_US.Sandy(EN_US.Red()))))));
		Register("red_sandy_diorite_bricks", RED_SANDY_DIORITE_BRICKS, List.of(EN_US.Bricks(EN_US.Diorite(EN_US.Sandy(EN_US.Red())))));
		Register("red_sandy_diorite_brick_slab", RED_SANDY_DIORITE_BRICK_SLAB, List.of(EN_US.Bricks(EN_US.Slab(EN_US.Diorite(EN_US.Sandy(EN_US.Red()))))));
		Register("red_sandy_cut_polished_diorite", RED_SANDY_CUT_POLISHED_DIORITE, List.of(EN_US.Diorite(EN_US.Polished(EN_US.Cut(EN_US.Sandy(EN_US.Red()))))));
		Register("red_sandy_cut_polished_diorite_slab", RED_SANDY_CUT_POLISHED_DIORITE_SLAB, List.of(EN_US.Slab(EN_US.Diorite(EN_US.Polished(EN_US.Cut(EN_US.Sandy(EN_US.Red())))))));
		//</editor-fold>
		//<editor-fold desc="Granite">
		Register("red_sandy_polished_granite", RED_SANDY_POLISHED_GRANITE, List.of(EN_US.Granite(EN_US.Polished(EN_US.Sandy(EN_US.Red())))));
		Register("red_sandy_polished_granite_slab", RED_SANDY_POLISHED_GRANITE_SLAB, List.of(EN_US.Granite(EN_US.Slab(EN_US.Polished(EN_US.Sandy(EN_US.Red()))))));
		Register("red_sandy_granite_bricks", RED_SANDY_GRANITE_BRICKS, List.of(EN_US.Bricks(EN_US.Granite(EN_US.Sandy(EN_US.Red())))));
		Register("red_sandy_granite_brick_slab", RED_SANDY_GRANITE_BRICK_SLAB, List.of(EN_US.Bricks(EN_US.Slab(EN_US.Granite(EN_US.Sandy(EN_US.Red()))))));
		Register("red_sandy_cut_polished_granite", RED_SANDY_CUT_POLISHED_GRANITE, List.of(EN_US.Granite(EN_US.Polished(EN_US.Cut(EN_US.Sandy(EN_US.Red()))))));
		Register("red_sandy_cut_polished_granite_slab", RED_SANDY_CUT_POLISHED_GRANITE_SLAB, List.of(EN_US.Slab(EN_US.Granite(EN_US.Polished(EN_US.Cut(EN_US.Sandy(EN_US.Red())))))));
		//</editor-fold>
		//<editor-fold desc="Planks">
		Register("red_sandy_acacia_planks", RED_SANDY_ACACIA_PLANKS, List.of(EN_US.Planks(EN_US.Acacia(EN_US.Sandy(EN_US.Red())))));
		Register("red_sandy_acacia_slab", RED_SANDY_ACACIA_SLAB, List.of(EN_US.Slab(EN_US.Acacia(EN_US.Sandy(EN_US.Red())))));
		Register("red_sandy_birch_planks", RED_SANDY_BIRCH_PLANKS, List.of(EN_US.Planks(EN_US.Birch(EN_US.Sandy(EN_US.Red())))));
		Register("red_sandy_birch_slab", RED_SANDY_BIRCH_SLAB, List.of(EN_US.Slab(EN_US.Birch(EN_US.Sandy(EN_US.Red())))));
		Register("red_sandy_dark_oak_planks", RED_SANDY_DARK_OAK_PLANKS, List.of(EN_US.Planks(EN_US.Oak(EN_US.Dark(EN_US.Sandy(EN_US.Red()))))));
		Register("red_sandy_dark_oak_slab", RED_SANDY_DARK_OAK_SLAB, List.of(EN_US.Slab(EN_US.Oak(EN_US.Dark(EN_US.Sandy(EN_US.Red()))))));
		Register("red_sandy_jungle_planks", RED_SANDY_JUNGLE_PLANKS, List.of(EN_US.Planks(EN_US.Jungle(EN_US.Sandy(EN_US.Red())))));
		Register("red_sandy_jungle_slab", RED_SANDY_JUNGLE_SLAB, List.of(EN_US.Slab(EN_US.Jungle(EN_US.Sandy(EN_US.Red())))));
		Register("red_sandy_oak_planks", RED_SANDY_OAK_PLANKS, List.of(EN_US.Planks(EN_US.Oak(EN_US.Sandy(EN_US.Red())))));
		Register("red_sandy_oak_slab", RED_SANDY_OAK_SLAB, List.of(EN_US.Slab(EN_US.Oak(EN_US.Sandy(EN_US.Red())))));
		Register("red_sandy_spruce_planks", RED_SANDY_SPRUCE_PLANKS, List.of(EN_US.Planks(EN_US.Spruce(EN_US.Sandy(EN_US.Red())))));
		Register("red_sandy_spruce_slab", RED_SANDY_SPRUCE_SLAB, List.of(EN_US.Slab(EN_US.Spruce(EN_US.Sandy(EN_US.Red())))));
		Register("red_sandy_crimson_planks", RED_SANDY_CRIMSON_PLANKS, List.of(EN_US.Planks(EN_US.Crimson(EN_US.Sandy(EN_US.Red())))));
		Register("red_sandy_crimson_slab", RED_SANDY_CRIMSON_SLAB, List.of(EN_US.Slab(EN_US.Crimson(EN_US.Sandy(EN_US.Red())))));
		Register("red_sandy_warped_planks", RED_SANDY_WARPED_PLANKS, List.of(EN_US.Planks(EN_US.Warped(EN_US.Sandy(EN_US.Red())))));
		Register("red_sandy_warped_slab", RED_SANDY_WARPED_SLAB, List.of(EN_US.Slab(EN_US.Warped(EN_US.Sandy(EN_US.Red())))));
		Register("red_sandy_mangrove_planks", RED_SANDY_MANGROVE_PLANKS, List.of(EN_US.Planks(EN_US.Mangrove(EN_US.Sandy(EN_US.Red())))));
		Register("red_sandy_mangrove_slab", RED_SANDY_MANGROVE_SLAB, List.of(EN_US.Slab(EN_US.Mangrove(EN_US.Sandy(EN_US.Red())))));
		Register("red_sandy_cherry_planks", RED_SANDY_CHERRY_PLANKS, List.of(EN_US.Planks(EN_US.Cherry(EN_US.Sandy(EN_US.Red())))));
		Register("red_sandy_cherry_slab", RED_SANDY_CHERRY_SLAB, List.of(EN_US.Slab(EN_US.Cherry(EN_US.Sandy(EN_US.Red())))));
		Register("red_sandy_cassia_planks", RED_SANDY_CASSIA_PLANKS, List.of(EN_US.Planks(EN_US.Cassia(EN_US.Sandy(EN_US.Red())))));
		Register("red_sandy_cassia_slab", RED_SANDY_CASSIA_SLAB, List.of(EN_US.Slab(EN_US.Cassia(EN_US.Sandy(EN_US.Red())))));
		Register("red_sandy_charred_planks", RED_SANDY_CHARRED_PLANKS, List.of(EN_US.Planks(EN_US.Charred(EN_US.Sandy(EN_US.Red())))));
		Register("red_sandy_charred_slab", RED_SANDY_CHARRED_SLAB, List.of(EN_US.Slab(EN_US.Charred(EN_US.Sandy(EN_US.Red())))));
		Register("red_sandy_dogwood_planks", RED_SANDY_DOGWOOD_PLANKS, List.of(EN_US.Planks(EN_US.Dogwood(EN_US.Sandy(EN_US.Red())))));
		Register("red_sandy_dogwood_slab", RED_SANDY_DOGWOOD_SLAB, List.of(EN_US.Slab(EN_US.Dogwood(EN_US.Sandy(EN_US.Red())))));
		Register("red_sandy_gilded_planks", RED_SANDY_GILDED_PLANKS, List.of(EN_US.Planks(EN_US.Gilded(EN_US.Sandy(EN_US.Red())))));
		Register("red_sandy_gilded_slab", RED_SANDY_GILDED_SLAB, List.of(EN_US.Slab(EN_US.Gilded(EN_US.Sandy(EN_US.Red())))));
		Register("red_sandy_hay_planks", RED_SANDY_HAY_PLANKS, List.of(EN_US.Planks(EN_US.Hay(EN_US.Sandy(EN_US.Red())))));
		Register("red_sandy_hay_slab", RED_SANDY_HAY_SLAB, List.of(EN_US.Slab(EN_US.Hay(EN_US.Sandy(EN_US.Red())))));
		Register("red_sandy_blue_mushroom_planks", RED_SANDY_BLUE_MUSHROOM_PLANKS, List.of(EN_US.Planks(EN_US.Mushroom(EN_US.Blue(EN_US.Sandy(EN_US.Red()))))));
		Register("red_sandy_blue_mushroom_slab", RED_SANDY_BLUE_MUSHROOM_SLAB, List.of(EN_US.Slab(EN_US.Mushroom(EN_US.Blue(EN_US.Sandy(EN_US.Red()))))));
		Register("red_sandy_brown_mushroom_planks", RED_SANDY_BROWN_MUSHROOM_PLANKS, List.of(EN_US.Planks(EN_US.Mushroom(EN_US.Brown(EN_US.Sandy(EN_US.Red()))))));
		Register("red_sandy_brown_mushroom_slab", RED_SANDY_BROWN_MUSHROOM_SLAB, List.of(EN_US.Slab(EN_US.Mushroom(EN_US.Brown(EN_US.Sandy(EN_US.Red()))))));
		Register("red_sandy_red_mushroom_planks", RED_SANDY_RED_MUSHROOM_PLANKS, List.of(EN_US.Planks(EN_US.Mushroom(EN_US.Red(EN_US.Sandy(EN_US.Red()))))));
		Register("red_sandy_red_mushroom_slab", RED_SANDY_RED_MUSHROOM_SLAB, List.of(EN_US.Slab(EN_US.Mushroom(EN_US.Red(EN_US.Sandy(EN_US.Red()))))));
		Register("red_sandy_mushroom_stem_planks", RED_SANDY_MUSHROOM_STEM_PLANKS, List.of(EN_US.Planks(EN_US.Stem(EN_US.Mushroom(EN_US.Sandy(EN_US.Red()))))));
		Register("red_sandy_mushroom_stem_slab", RED_SANDY_MUSHROOM_STEM_SLAB, List.of(EN_US.Slab(EN_US.Stem(EN_US.Mushroom(EN_US.Sandy(EN_US.Red()))))));
		//</editor-fold>
		//<editor-fold desc="Prismarine">
		Register("red_sandy_prismarine", RED_SANDY_PRISMARINE, List.of(EN_US.Prismarine(EN_US.Sandy(EN_US.Red()))));
		Register("red_sandy_prismarine_slab", RED_SANDY_PRISMARINE_SLAB, List.of(EN_US.Slab(EN_US.Prismarine(EN_US.Sandy(EN_US.Red())))));
		Register("red_sandy_sandy_prismarine", RED_SANDY_SANDY_PRISMARINE, List.of(EN_US.Prismarine(EN_US.Sandy(EN_US.Sandy(EN_US.Red())))));
		Register("red_sandy_sandy_prismarine_slab", RED_SANDY_SANDY_PRISMARINE_SLAB, List.of(EN_US.Slab(EN_US.Prismarine(EN_US.Sandy(EN_US.Sandy(EN_US.Red()))))));
		Register("red_sandy_prismarine_bricks", RED_SANDY_PRISMARINE_BRICKS, List.of(EN_US.Bricks(EN_US.Prismarine(EN_US.Sandy(EN_US.Red())))));
		Register("red_sandy_prismarine_brick_slab", RED_SANDY_PRISMARINE_BRICK_SLAB, List.of(EN_US.Slab(EN_US.Brick(EN_US.Prismarine(EN_US.Sandy(EN_US.Red()))))));
		Register("red_sandy_chiseled_prismarine_bricks", RED_SANDY_CHISELED_PRISMARINE_BRICKS, List.of(EN_US.Bricks(EN_US.Prismarine(EN_US.Chiseled(EN_US.Sandy(EN_US.Red()))))));
		Register("red_sandy_dark_prismarine", RED_SANDY_DARK_PRISMARINE, List.of(EN_US.Prismarine(EN_US.Dark(EN_US.Sandy(EN_US.Red())))));
		Register("red_sandy_dark_prismarine_slab", RED_SANDY_DARK_PRISMARINE_SLAB, List.of(EN_US.Slab(EN_US.Prismarine(EN_US.Dark(EN_US.Sandy(EN_US.Red()))))));
		Register("red_sandy_sandy_dark_prismarine", RED_SANDY_SANDY_DARK_PRISMARINE, List.of(EN_US.Prismarine(EN_US.Dark(EN_US.Sandy(EN_US.Sandy(EN_US.Red()))))));
		Register("red_sandy_sandy_dark_prismarine_slab", RED_SANDY_SANDY_DARK_PRISMARINE_SLAB, List.of(EN_US.Slab(EN_US.Prismarine(EN_US.Dark(EN_US.Sandy(EN_US.Sandy(EN_US.Red())))))));
		//</editor-fold>
		//<editor-fold desc="Purpur">
		Register("red_sandy_purpur_block", RED_SANDY_PURPUR_BLOCK, List.of(EN_US.Block(EN_US.Purpur(EN_US.Sandy(EN_US.Red())))));
		Register("red_sandy_purpur_block_slab", RED_SANDY_PURPUR_SLAB, List.of(EN_US.Slab(EN_US.Block(EN_US.Purpur(EN_US.Sandy(EN_US.Red()))))));
		Register("red_sandy_purpur_bricks", RED_SANDY_PURPUR_BRICKS, List.of(EN_US.Bricks(EN_US.Purpur(EN_US.Sandy(EN_US.Red())))));
		Register("red_sandy_purpur_brick_slab", RED_SANDY_PURPUR_BRICK_SLAB, List.of(EN_US.Slab(EN_US.Brick(EN_US.Purpur(EN_US.Sandy(EN_US.Red()))))));
		Register("red_sandy_smooth_purpur", RED_SANDY_SMOOTH_PURPUR, List.of(EN_US.Purpur(EN_US.Smooth(EN_US.Sandy(EN_US.Red())))));
		Register("red_sandy_smooth_purpur_slab", RED_SANDY_SMOOTH_PURPUR_SLAB, List.of(EN_US.Slab(EN_US.Purpur(EN_US.Smooth(EN_US.Sandy(EN_US.Red()))))));
		Register("red_sandy_chiseled_purpur", RED_SANDY_CHISELED_PURPUR, List.of(EN_US.Purpur(EN_US.Chiseled(EN_US.Sandy(EN_US.Red())))));
		Register("red_sandy_chiseled_purpur_slab", RED_SANDY_CHISELED_PURPUR_SLAB, List.of(EN_US.Slab(EN_US.Purpur(EN_US.Chiseled(EN_US.Sandy(EN_US.Red()))))));
		Register("red_sandy_sandy_chiseled_purpur", RED_SANDY_SANDY_CHISELED_PURPUR, List.of(EN_US.Purpur(EN_US.Chiseled(EN_US.Sandy(EN_US.Sandy(EN_US.Red()))))));
		Register("red_sandy_sandy_chiseled_purpur_slab", RED_SANDY_SANDY_CHISELED_PURPUR_SLAB, List.of(EN_US.Slab(EN_US.Purpur(EN_US.Chiseled(EN_US.Sandy(EN_US.Sandy(EN_US.Red())))))));
		//</editor-fold>
		Register("red_sandy_block", RED_SANDY_BLOCK_ENTITY);
		//</editor-fold>
	}
	private static void RegisterInfestedBlocks() {
		Register("infested_mossy_chiseled_stone_bricks", INFESTED_MOSSY_CHISELED_STONE_BRICKS, List.of(EN_US.Bricks(EN_US.Stone(EN_US.Chiseled(EN_US.Mossy(EN_US.Infested()))))));
		Register("infested_cobbled_deepslate", INFESTED_COBBLED_DEEPSLATE, List.of(EN_US.Deepslate(EN_US.Cobbled(EN_US.Infested()))));
		Register("infested_mossy_cobbled_deepslate", INFESTED_MOSSY_COBBLED_DEEPSLATE, List.of(EN_US.Deepslate(EN_US.Cobbled(EN_US.Mossy(EN_US.Infested())))));
		Register("infested_andesite", INFESTED_ANDESITE, List.of(EN_US.Andesite(EN_US.Infested())));
		Register("infested_andesite_bricks", INFESTED_ANDESITE_BRICKS, List.of(EN_US.Bricks(EN_US.Andesite(EN_US.Infested()))));
		Register("infested_chiseled_andesite_bricks", INFESTED_CHISELED_ANDESITE_BRICKS, List.of(EN_US.Bricks(EN_US.Andesite(EN_US.Chiseled(EN_US.Infested())))));
		Register("infested_diorite", INFESTED_DIORITE, List.of(EN_US.Diorite(EN_US.Infested())));
		Register("infested_diorite_bricks", INFESTED_DIORITE_BRICKS, List.of(EN_US.Bricks(EN_US.Diorite(EN_US.Infested()))));
		Register("infested_chiseled_diorite_bricks", INFESTED_CHISELED_DIORITE_BRICKS, List.of(EN_US.Bricks(EN_US.Diorite(EN_US.Chiseled(EN_US.Infested())))));
		Register("infested_granite", INFESTED_GRANITE, List.of(EN_US.Granite(EN_US.Infested())));
		Register("infested_granite_bricks", INFESTED_GRANITE_BRICKS, List.of(EN_US.Bricks(EN_US.Granite(EN_US.Infested()))));
		Register("infested_chiseled_granite_bricks", INFESTED_CHISELED_GRANITE_BRICKS, List.of(EN_US.Bricks(EN_US.Granite(EN_US.Chiseled(EN_US.Infested())))));
		Register("infested_tuff", INFESTED_TUFF, List.of(EN_US.Tuff(EN_US.Infested())));
	}
	private static void RegisterMobStorage() {
		//<editor-fold desc="Buckets">
		Register("piranha_bucket", PIRANHA_BUCKET, List.of(EN_US.Piranha(EN_US.BucketOf())));
		//</editor-fold>
		//<editor-fold desc="Pouches">
		Register("pouch", POUCH, List.of(EN_US.Pouch()));
		Register("chicken_pouch", CHICKEN_POUCH, List.of(EN_US.Chicken(EN_US.of(EN_US.Pouch()))));
		Register("rabbit_pouch", RABBIT_POUCH, List.of(EN_US.Rabbit(EN_US.of(EN_US.Pouch()))));
		Register("parrot_pouch", PARROT_POUCH, List.of(EN_US.Parrot(EN_US.of(EN_US.Pouch()))));
		Register("endermite_pouch", ENDERMITE_POUCH, List.of(EN_US.Endermite(EN_US.of(EN_US.Pouch()))));
		Register("silverfish_pouch", SILVERFISH_POUCH, List.of(EN_US.Silverfish(EN_US.of(EN_US.Pouch()))));
		Register("hedgehog_pouch", HEDGEHOG_POUCH, List.of(EN_US.Hedgehog(EN_US.of(EN_US.Pouch()))));
		//</editor-fold>
	}
	private static void RegisterMobSkullsAndRagdolls() {
		//<editor-fold desc="Piglin & Zombie Piglin">
		Register("minecraft:piglin_head", PIGLIN_HEAD_BLOCK_ENTITY);
		Register("minecraft:piglin_head", "minecraft:piglin_wall_head", PIGLIN_HEAD, List.of(EN_US._Head(EN_US.Piglin())));
		DispenserBlock.registerBehavior(PIGLIN_HEAD, new WearableDispenserBehavior());
		Register("zombified_piglin_head", "zombified_piglin_wall_head", ZOMBIFIED_PIGLIN_HEAD, List.of(EN_US._Head(EN_US.Piglin(EN_US.Zombified()))));
		DispenserBlock.registerBehavior(ZOMBIFIED_PIGLIN_HEAD, new WearableDispenserBehavior());
		//</editor-fold>
		Register("ragdoll", RAGDOLL_BLOCK_ENTITY);
		Register("ragdoll", RAGDOLL, List.of(EN_US.Ragdoll()));
	}
	private static void RegisterSummoningArrows() {
		//<editor-fold desc="Summoning Arrows">
		Register("axolotl_summoning_arrow", AXOLOTL_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Axolotl()))));
		Register("bat_summoning_arrow", BAT_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Bat()))));
		Register("bee_summoning_arrow", BEE_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Bee()))));
		Register("blaze_summoning_arrow", BLAZE_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Blaze()))));
		Register("camel_summoning_arrow", CAMEL_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Camel()))));
		Register("cat_summoning_arrow", CAT_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Cat()))));
		Register("cave_spider_summoning_arrow", CAVE_SPIDER_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Spider(EN_US.Cave())))));
		Register("chicken_summoning_arrow", CHICKEN_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Chicken()))));
		Register("cod_summoning_arrow", COD_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Cod()))));
		Register("cow_summoning_arrow", COW_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Cow()))));
		Register("creeper_summoning_arrow", CREEPER_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Creeper()))));
		Register("dolphin_summoning_arrow", DOLPHIN_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Dolphin()))));
		Register("donkey_summoning_arrow", DONKEY_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Donkey()))));
		Register("drowned_summoning_arrow", DROWNED_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Drowned()))));
		Register("elder_guardian_summoning_arrow", ELDER_GUARDIAN_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Guardian(EN_US.Elder())))));
		Register("enderman_summoning_arrow", ENDERMAN_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Enderman()))));
		Register("endermite_summoning_arrow", ENDERMITE_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Endermite()))));
		Register("evoker_summoning_arrow", EVOKER_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Evoker()))));
		Register("fox_summoning_arrow", FOX_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Fox()))));
		Register("frog_summoning_arrow", FROG_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Frog()))));
		Register("ghast_summoning_arrow", GHAST_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Ghast()))));
		Register("giant_summoning_arrow", GIANT_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Giant()))));
		Register("glow_squid_summoning_arrow", GLOW_SQUID_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Squid(EN_US.Glow())))));
		Register("goat_summoning_arrow", GOAT_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Goat()))));
		Register("guardian_summoning_arrow", GUARDIAN_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Guardian()))));
		Register("hoglin_summoning_arrow", HOGLIN_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Hoglin()))));
		Register("horse_summoning_arrow", HORSE_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Horse()))));
		Register("husk_summoning_arrow", HUSK_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Husk()))));
		Register("iron_golem_summoning_arrow", IRON_GOLEM_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Golem(EN_US.Iron())))));
		Register("llama_summoning_arrow", LLAMA_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Llama()))));
		Register("magma_cube_summoning_arrow", MAGMA_CUBE_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Cube(EN_US.Magma())))));
		Register("mooshroom_summoning_arrow", MOOSHROOM_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Mooshroom()))));
		Register("mule_summoning_arrow", MULE_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Mule()))));
		Register("ocelot_summoning_arrow", OCELOT_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Ocelot()))));
		Register("panda_summoning_arrow", PANDA_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Panda()))));
		Register("parrot_summoning_arrow", PARROT_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Parrot()))));
		Register("phantom_summoning_arrow", PHANTOM_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Phantom()))));
		Register("pig_summoning_arrow", PIG_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Pig()))));
		Register("piglin_summoning_arrow", PIGLIN_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Piglin()))));
		Register("piglin_brute_summoning_arrow", PIGLIN_BRUTE_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Brute(EN_US.Piglin())))));
		Register("pillager_summoning_arrow", PILLAGER_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Pillager()))));
		Register("polar_bear_summoning_arrow", POLAR_BEAR_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Bear(EN_US.Polar())))));
		Register("pufferfish_summoning_arrow", PUFFERFISH_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Pufferfish()))));
		Register("rabbit_summoning_arrow", RABBIT_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Rabbit()))));
		Register("ravager_summoning_arrow", RAVAGER_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Ravager()))));
		Register("salmon_summoning_arrow", SALMON_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Salmon()))));
		Register("sheep_summoning_arrow", SHEEP_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Sheep()))));
		Register("shulker_summoning_arrow", SHULKER_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Shulker()))));
		Register("silverfish_summoning_arrow", SILVERFISH_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Silverfish()))));
		Register("skeleton_summoning_arrow", SKELETON_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Skeleton()))));
		Register("skeleton_horse_summoning_arrow", SKELETON_HORSE_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Horse(EN_US.Skeleton())))));
		Register("slime_summoning_arrow", SLIME_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Slime()))));
		Register("sniffer_summoning_arrow", SNIFFER_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Sniffer()))));
		Register("snow_golem_summoning_arrow", SNOW_GOLEM_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Golem(EN_US.Snow())))));
		Register("spider_summoning_arrow", SPIDER_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Spider()))));
		Register("squid_summoning_arrow", SQUID_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Squid()))));
		Register("stray_summoning_arrow", STRAY_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Stray()))));
		Register("strider_summoning_arrow", STRIDER_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Strider()))));
		Register("tadpole_summoning_arrow", TADPOLE_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Tadpole()))));
		Register("trader_llama_summoning_arrow", TRADER_LLAMA_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Llama(EN_US.Trader())))));
		Register("tropical_fish_summoning_arrow", TROPICAL_FISH_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Fish(EN_US.Tropical())))));
		Register("turtle_summoning_arrow", TURTLE_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Turtle()))));
		Register("vex_summoning_arrow", VEX_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Vex()))));
		Register("villager_summoning_arrow", VILLAGER_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Villager()))));
		Register("vindicator_summoning_arrow", VINDICATOR_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Vindicator()))));
		Register("wandering_trader_summoning_arrow", WANDERING_TRADER_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Trader(EN_US.Wandering())))));
		Register("warden_summoning_arrow", WARDEN_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Warden()))));
		Register("witch_summoning_arrow", WITCH_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Witch()))));
		Register("wither_summoning_arrow", WITHER_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Wither()))));
		Register("wither_skeleton_summoning_arrow", WITHER_SKELETON_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Skeleton(EN_US.Wither())))));
		Register("wolf_summoning_arrow", WOLF_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Wolf()))));
		Register("zoglin_summoning_arrow", ZOGLIN_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Zoglin()))));
		Register("zombie_summoning_arrow", ZOMBIE_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Zombie()))));
		Register("zombie_horse_summoning_arrow", ZOMBIE_HORSE_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Horse(EN_US.Zombie())))));
		Register("zombie_villager_summoning_arrow", ZOMBIE_VILLAGER_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Villager(EN_US.Zombie())))));
		Register("zombie_piglin_summoning_arrow", ZOMBIFIED_PIGLIN_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Piglin(EN_US.Zombified())))));
		//</editor-fold>
		//<editor-fold desc="Mod Mob Summoning Arrows">
		Register("melon_golem_summoning_arrow", MELON_GOLEM_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Golem(EN_US.Melon())))));
		Register("bone_spider_summoning_arrow", BONE_SPIDER_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Spider(EN_US.Bone())))));
		Register("icy_spider_summoning_arrow", ICY_SPIDER_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Spider(EN_US.Icy())))));
		Register("slime_spider_summoning_arrow", SLIME_SPIDER_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Slider()))));
		Register("hedgehog_summoning_arrow", HEDGEHOG_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Hedgehog()))));
		Register("raccoon_summoning_arrow", RACCOON_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Raccoon()))));
		Register("red_panda_summoning_arrow", RED_PANDA_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Panda(EN_US.Red())))));
		Register("jumping_spider_summoning_arrow", JUMPING_SPIDER_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Spider(EN_US.Jumping())))));
		Register("red_phantom_summoning_arrow", RED_PHANTOM_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Phantom(EN_US.Red())))));
		Register("piranha_summoning_arrow", PIRANHA_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Piranha()))));
		Register("fancy_chicken_summoning_arrow", FANCY_CHICKEN_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Chicken(EN_US.Fancy())))));
		Register("blue_mooshroom_summoning_arrow", BLUE_MOOSHROOM_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Mooshroom(EN_US.Blue())))));
		Register("nether_mooshroom_summoning_arrow", NETHER_MOOSHROOM_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Mooshroom(EN_US.Nether())))));
		Register("moobloom_summoning_arrow", MOOBLOOM_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Moobloom()))));
		Register("moolip_summoning_arrow", MOOLIP_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Moolip()))));
		Register("mooblossom_summoning_arrow", MOOBLOSSOM_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Mooblossom()))));
		Register("mossy_sheep_summoning_arrow", MOSSY_SHEEP_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Sheep(EN_US.Mossy())))));
		Register("rainbow_sheep_summoning_arrow", RAINBOW_SHEEP_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Sheep(EN_US.Rainbow())))));
		Register("slime_creeper_summoning_arrow", SLIME_CREEPER_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Creeper(EN_US.Slime())))));
		Register("mossy_skeleton_summoning_arrow", MOSSY_SKELETON_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Skeleton(EN_US.Mossy())))));
		Register("slimy_skeleton_summoning_arrow", SLIMY_SKELETON_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Skeleton(EN_US.Slimy())))));
		Register("sunken_skeleton_summoning_arrow", SUNKEN_SKELETON_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Skeleton(EN_US.Sunken())))));
		Register("tropical_slime_summoning_arrow", TROPICAL_SLIME_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Slime(EN_US.Tropical())))));
		Register("pink_slime_summoning_arrow", PINK_SLIME_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Slime(EN_US.Pink())))));
		Register("slime_chicken_summoning_arrow", SLIME_CHICKEN_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Slicken()))));
		Register("slime_cow_summoning_arrow", SLIME_COW_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Cow(EN_US.Slime())))));
		Register("slime_horse_summoning_arrow", SLIME_HORSE_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Slorse()))));
		Register("frozen_zombie_summoning_arrow", FROZEN_ZOMBIE_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Zombie(EN_US.Frozen())))));
		Register("jungle_zombie_summoning_arrow", JUNGLE_ZOMBIE_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Zombie(EN_US.Jungle())))));
		Register("slime_zombie_summoning_arrow", SLIME_ZOMBIE_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Zombie(EN_US.Slime())))));
		Register("iceologer_summoning_arrow", ICEOLOGER_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Iceologer()))));
		Register("mage_summoning_arrow", MAGE_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Mage()))));
		Register("jolly_llama_summoning_arrow", JOLLY_LLAMA_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Llama(EN_US.Jolly())))));
		//</editor-fold>
	}
	private static void RegisterSpawnEggs() {
		//<editor-fold desc="Spawn Eggs">
		Register("minecraft:frog_spawn_egg", FROG_SPAWN_EGG, List.of(EN_US.Egg(EN_US.Spawn(EN_US.Frog()))));
		Register("minecraft:tadpole_spawn_egg", TADPOLE_SPAWN_EGG, List.of(EN_US.Egg(EN_US.Spawn(EN_US.Tadpole()))));
		Register("minecraft:warden_spawn_egg", WARDEN_SPAWN_EGG, List.of(EN_US.Egg(EN_US.Spawn(EN_US.Warden()))));
		Register("minecraft:camel_spawn_egg", CAMEL_SPAWN_EGG, List.of(EN_US.Egg(EN_US.Spawn(EN_US.Camel()))));
		Register("minecraft:sniffer_spawn_egg", SNIFFER_SPAWN_EGG, List.of(EN_US.Egg(EN_US.Spawn(EN_US.Sniffer()))));
		//</editor-fold>
		//<editor-fold desc="Mod Mob Spawn Eggs">
		Register("bone_spider_spawn_egg", BONE_SPIDER_SPAWN_EGG, List.of(EN_US.Egg(EN_US.Spawn(EN_US.Spider(EN_US.Bone())))));
		Register("icy_spider_spawn_egg", ICY_SPIDER_SPAWN_EGG, List.of(EN_US.Egg(EN_US.Spawn(EN_US.Spider(EN_US.Icy())))));
		Register("slime_spider_spawn_egg", SLIME_SPIDER_SPAWN_EGG, List.of(EN_US.Egg(EN_US.Spawn(EN_US.Slider()))));
		Register("hedgehog_spawn_egg", HEDGEHOG_SPAWN_EGG, List.of(EN_US.Egg(EN_US.Spawn(EN_US.Hedgehog()))));
		Register("raccoon_spawn_egg", RACCOON_SPAWN_EGG, List.of(EN_US.Egg(EN_US.Spawn(EN_US.Raccoon()))));
		Register("red_panda_spawn_egg", RED_PANDA_SPAWN_EGG, List.of(EN_US.Egg(EN_US.Spawn(EN_US.Panda(EN_US.Red())))));
		Register("jumping_spider_spawn_egg", JUMPING_SPIDER_SPAWN_EGG, List.of(EN_US.Egg(EN_US.Spawn(EN_US.Spider(EN_US.Jumping())))));
		Register("red_phantom_spawn_egg", RED_PHANTOM_SPAWN_EGG, List.of(EN_US.Egg(EN_US.Spawn(EN_US.Phantom(EN_US.Red())))));
		Register("piranha_spawn_egg", PIRANHA_SPAWN_EGG, List.of(EN_US.Egg(EN_US.Spawn(EN_US.Piranha()))));
		Register("fancy_chicken_spawn_egg", FANCY_CHICKEN_SPAWN_EGG, List.of(EN_US.Egg(EN_US.Spawn(EN_US.Chicken(EN_US.Fancy())))));
		Register("blue_mooshroom_spawn_egg", BLUE_MOOSHROOM_SPAWN_EGG, List.of(EN_US.Egg(EN_US.Spawn(EN_US.Mooshroom(EN_US.Blue())))));
		Register("nether_mooshroom_spawn_egg", NETHER_MOOSHROOM_SPAWN_EGG, List.of(EN_US.Egg(EN_US.Spawn(EN_US.Mooshroom(EN_US.Nether())))));
		Register("moobloom_spawn_egg", MOOBLOOM_SPAWN_EGG, List.of(EN_US.Egg(EN_US.Spawn(EN_US.Moobloom()))));
		Register("moolip_spawn_egg", MOOLIP_SPAWN_EGG, List.of(EN_US.Egg(EN_US.Spawn(EN_US.Moolip()))));
		Register("mooblossom_spawn_egg", MOOBLOSSOM_SPAWN_EGG, List.of(EN_US.Egg(EN_US.Spawn(EN_US.Mooblossom()))));
		Register("mossy_sheep_spawn_egg", MOSSY_SHEEP_SPAWN_EGG, List.of(EN_US.Egg(EN_US.Spawn(EN_US.Sheep(EN_US.Mossy())))));
		Register("rainbow_sheep_spawn_egg", RAINBOW_SHEEP_SPAWN_EGG, List.of(EN_US.Egg(EN_US.Spawn(EN_US.Sheep(EN_US.Rainbow())))));
		Register("slime_creeper_spawn_egg", SLIME_CREEPER_SPAWN_EGG, List.of(EN_US.Egg(EN_US.Spawn(EN_US.Creeper(EN_US.Slime())))));
		Register("mossy_skeleton_spawn_egg", MOSSY_SKELETON_SPAWN_EGG, List.of(EN_US.Egg(EN_US.Spawn(EN_US.Skeleton(EN_US.Mossy())))));
		Register("slimy_skeleton_spawn_egg", SLIMY_SKELETON_SPAWN_EGG, List.of(EN_US.Egg(EN_US.Spawn(EN_US.Skeleton(EN_US.Slimy())))));
		Register("sunken_skeleton_spawn_egg", SUNKEN_SKELETON_SPAWN_EGG, List.of(EN_US.Egg(EN_US.Spawn(EN_US.Skeleton(EN_US.Sunken())))));
		Register("tropical_slime_spawn_egg", TROPICAL_SLIME_SPAWN_EGG, List.of(EN_US.Egg(EN_US.Spawn(EN_US.Slime(EN_US.Tropical())))));
		Register("pink_slime_spawn_egg", PINK_SLIME_SPAWN_EGG, List.of(EN_US.Egg(EN_US.Spawn(EN_US.Slime(EN_US.Pink())))));
		Register("slime_chicken_spawn_egg", SLIME_CHICKEN_SPAWN_EGG, List.of(EN_US.Egg(EN_US.Spawn(EN_US.Slicken()))));
		Register("slime_cow_spawn_egg", SLIME_COW_SPAWN_EGG, List.of(EN_US.Egg(EN_US.Spawn(EN_US.Cow(EN_US.Slime())))));
		Register("slime_horse_spawn_egg", SLIME_HORSE_SPAWN_EGG, List.of(EN_US.Egg(EN_US.Spawn(EN_US.Slorse()))));
		Register("frozen_zombie_spawn_egg", FROZEN_ZOMBIE_SPAWN_EGG, List.of(EN_US.Egg(EN_US.Spawn(EN_US.Zombie(EN_US.Frozen())))));
		Register("jungle_zombie_spawn_egg", JUNGLE_ZOMBIE_SPAWN_EGG, List.of(EN_US.Egg(EN_US.Spawn(EN_US.Zombie(EN_US.Jungle())))));
		Register("slime_zombie_spawn_egg", SLIME_ZOMBIE_SPAWN_EGG, List.of(EN_US.Egg(EN_US.Spawn(EN_US.Zombie(EN_US.Slime())))));
		Register("iceologer_spawn_egg", ICEOLOGER_SPAWN_EGG, List.of(EN_US.Egg(EN_US.Spawn(EN_US.Iceologer()))));
		Register("mage_spawn_egg", MAGE_SPAWN_EGG, List.of(EN_US.Egg(EN_US.Spawn(EN_US.Mage()))));
		Register("jolly_llama_spawn_egg", JOLLY_LLAMA_SPAWN_EGG, List.of(EN_US.Egg(EN_US.Spawn(EN_US.Llama(EN_US.Jolly())))));
		//</editor-fold>
		//Add dispenser behavior (will override existing spawn egg behaviors but those really shouldn't be different anyway????
		ItemDispenserBehavior itemDispenserBehavior = new ItemDispenserBehavior() {
			@Override
			public ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
				Direction direction = pointer.getBlockState().get(DispenserBlock.FACING);
				EntityType<?> entityType = ((SpawnEggItem)stack.getItem()).getEntityType(stack.getNbt());
				try {
					entityType.spawnFromItemStack(pointer.getWorld(), stack, null, pointer.getPos().offset(direction), SpawnReason.DISPENSER, direction != Direction.UP, false);
				}
				catch (Exception exception) {
					//LOGGER.error("Error while dispensing spawn egg from dispenser at {}", pointer.getPos(), exception);
					return ItemStack.EMPTY;
				}
				stack.decrement(1);
				pointer.getWorld().emitGameEvent(GameEvent.ENTITY_PLACE, pointer.getPos());
				return stack;
			}
		};
		for (SpawnEggItem spawnEggItem : SpawnEggItem.getAll()) {
			DispenserBlock.registerBehavior(spawnEggItem, itemDispenserBehavior);
		}
	}

	//Sculk
	public static final BlockContainer SCULK_SENSOR = new BlockContainer(new ModSculkSensorBlock(Block.Settings.of(Material.SCULK, MapColor.CYAN).strength(1.5f).sounds(BlockSoundGroup.SCULK_SENSOR).luminance(state -> 1).emissiveLighting((state, world, pos) -> SculkUtil.getPhase(state) == SculkSensorPhase.ACTIVE), 8));
	public static final BlockEntityType<ModSculkSensorBlockEntity> SCULK_SENSOR_ENTITY = FabricBlockEntityTypeBuilder.create(ModSculkSensorBlockEntity::new, SCULK_SENSOR.asBlock()).build();

	public static Map<SignType, SignType> HANGING_SIGN_SUBTYPES = Map.<SignType, SignType>ofEntries(
			//Acacia
			Map.entry(((HangingSignBlock)ACACIA_IRON_HANGING_SIGN.asBlock()).getSignType(), SignType.ACACIA),
			Map.entry(((HangingSignBlock)ACACIA_GOLD_HANGING_SIGN.asBlock()).getSignType(), SignType.ACACIA),
			Map.entry(((HangingSignBlock)ACACIA_COPPER_HANGING_SIGN.asBlock()).getSignType(), SignType.ACACIA),
			Map.entry(((HangingSignBlock)ACACIA_EXPOSED_COPPER_HANGING_SIGN.asBlock()).getSignType(), SignType.ACACIA),
			Map.entry(((HangingSignBlock)ACACIA_WEATHERED_COPPER_HANGING_SIGN.asBlock()).getSignType(), SignType.ACACIA),
			Map.entry(((HangingSignBlock)ACACIA_OXIDIZED_COPPER_HANGING_SIGN.asBlock()).getSignType(), SignType.ACACIA),
			Map.entry(((HangingSignBlock)ACACIA_NETHERITE_HANGING_SIGN.asBlock()).getSignType(), SignType.ACACIA),
			//Birch
			Map.entry(((HangingSignBlock)BIRCH_IRON_HANGING_SIGN.asBlock()).getSignType(), SignType.BIRCH),
			Map.entry(((HangingSignBlock)BIRCH_GOLD_HANGING_SIGN.asBlock()).getSignType(), SignType.BIRCH),
			Map.entry(((HangingSignBlock)BIRCH_COPPER_HANGING_SIGN.asBlock()).getSignType(), SignType.BIRCH),
			Map.entry(((HangingSignBlock)BIRCH_EXPOSED_COPPER_HANGING_SIGN.asBlock()).getSignType(), SignType.BIRCH),
			Map.entry(((HangingSignBlock)BIRCH_WEATHERED_COPPER_HANGING_SIGN.asBlock()).getSignType(), SignType.BIRCH),
			Map.entry(((HangingSignBlock)BIRCH_OXIDIZED_COPPER_HANGING_SIGN.asBlock()).getSignType(), SignType.BIRCH),
			Map.entry(((HangingSignBlock)BIRCH_NETHERITE_HANGING_SIGN.asBlock()).getSignType(), SignType.BIRCH),
			//Dark Oak
			Map.entry(((HangingSignBlock)DARK_OAK_IRON_HANGING_SIGN.asBlock()).getSignType(), SignType.DARK_OAK),
			Map.entry(((HangingSignBlock)DARK_OAK_GOLD_HANGING_SIGN.asBlock()).getSignType(), SignType.DARK_OAK),
			Map.entry(((HangingSignBlock)DARK_OAK_COPPER_HANGING_SIGN.asBlock()).getSignType(), SignType.DARK_OAK),
			Map.entry(((HangingSignBlock)DARK_OAK_EXPOSED_COPPER_HANGING_SIGN.asBlock()).getSignType(), SignType.DARK_OAK),
			Map.entry(((HangingSignBlock)DARK_OAK_WEATHERED_COPPER_HANGING_SIGN.asBlock()).getSignType(), SignType.DARK_OAK),
			Map.entry(((HangingSignBlock)DARK_OAK_OXIDIZED_COPPER_HANGING_SIGN.asBlock()).getSignType(), SignType.DARK_OAK),
			Map.entry(((HangingSignBlock)DARK_OAK_NETHERITE_HANGING_SIGN.asBlock()).getSignType(), SignType.DARK_OAK),
			//Jungle
			Map.entry(((HangingSignBlock)JUNGLE_IRON_HANGING_SIGN.asBlock()).getSignType(), SignType.JUNGLE),
			Map.entry(((HangingSignBlock)JUNGLE_GOLD_HANGING_SIGN.asBlock()).getSignType(), SignType.JUNGLE),
			Map.entry(((HangingSignBlock)JUNGLE_COPPER_HANGING_SIGN.asBlock()).getSignType(), SignType.JUNGLE),
			Map.entry(((HangingSignBlock)JUNGLE_EXPOSED_COPPER_HANGING_SIGN.asBlock()).getSignType(), SignType.JUNGLE),
			Map.entry(((HangingSignBlock)JUNGLE_WEATHERED_COPPER_HANGING_SIGN.asBlock()).getSignType(), SignType.JUNGLE),
			Map.entry(((HangingSignBlock)JUNGLE_OXIDIZED_COPPER_HANGING_SIGN.asBlock()).getSignType(), SignType.JUNGLE),
			Map.entry(((HangingSignBlock)JUNGLE_NETHERITE_HANGING_SIGN.asBlock()).getSignType(), SignType.JUNGLE),
			//Oak
			Map.entry(((HangingSignBlock)OAK_IRON_HANGING_SIGN.asBlock()).getSignType(), SignType.OAK),
			Map.entry(((HangingSignBlock)OAK_GOLD_HANGING_SIGN.asBlock()).getSignType(), SignType.OAK),
			Map.entry(((HangingSignBlock)OAK_COPPER_HANGING_SIGN.asBlock()).getSignType(), SignType.OAK),
			Map.entry(((HangingSignBlock)OAK_EXPOSED_COPPER_HANGING_SIGN.asBlock()).getSignType(), SignType.OAK),
			Map.entry(((HangingSignBlock)OAK_WEATHERED_COPPER_HANGING_SIGN.asBlock()).getSignType(), SignType.OAK),
			Map.entry(((HangingSignBlock)OAK_OXIDIZED_COPPER_HANGING_SIGN.asBlock()).getSignType(), SignType.OAK),
			Map.entry(((HangingSignBlock)OAK_NETHERITE_HANGING_SIGN.asBlock()).getSignType(), SignType.OAK),
			//Spruce
			Map.entry(((HangingSignBlock)SPRUCE_IRON_HANGING_SIGN.asBlock()).getSignType(), SignType.SPRUCE),
			Map.entry(((HangingSignBlock)SPRUCE_GOLD_HANGING_SIGN.asBlock()).getSignType(), SignType.SPRUCE),
			Map.entry(((HangingSignBlock)SPRUCE_COPPER_HANGING_SIGN.asBlock()).getSignType(), SignType.SPRUCE),
			Map.entry(((HangingSignBlock)SPRUCE_EXPOSED_COPPER_HANGING_SIGN.asBlock()).getSignType(), SignType.SPRUCE),
			Map.entry(((HangingSignBlock)SPRUCE_WEATHERED_COPPER_HANGING_SIGN.asBlock()).getSignType(), SignType.SPRUCE),
			Map.entry(((HangingSignBlock)SPRUCE_OXIDIZED_COPPER_HANGING_SIGN.asBlock()).getSignType(), SignType.SPRUCE),
			Map.entry(((HangingSignBlock)SPRUCE_NETHERITE_HANGING_SIGN.asBlock()).getSignType(), SignType.SPRUCE),
			//Crimson
			Map.entry(((HangingSignBlock)CRIMSON_IRON_HANGING_SIGN.asBlock()).getSignType(), SignType.CRIMSON),
			Map.entry(((HangingSignBlock)CRIMSON_GOLD_HANGING_SIGN.asBlock()).getSignType(), SignType.CRIMSON),
			Map.entry(((HangingSignBlock)CRIMSON_COPPER_HANGING_SIGN.asBlock()).getSignType(), SignType.CRIMSON),
			Map.entry(((HangingSignBlock)CRIMSON_EXPOSED_COPPER_HANGING_SIGN.asBlock()).getSignType(), SignType.CRIMSON),
			Map.entry(((HangingSignBlock)CRIMSON_WEATHERED_COPPER_HANGING_SIGN.asBlock()).getSignType(), SignType.CRIMSON),
			Map.entry(((HangingSignBlock)CRIMSON_OXIDIZED_COPPER_HANGING_SIGN.asBlock()).getSignType(), SignType.CRIMSON),
			Map.entry(((HangingSignBlock)CRIMSON_NETHERITE_HANGING_SIGN.asBlock()).getSignType(), SignType.CRIMSON),
			//Warped
			Map.entry(((HangingSignBlock)WARPED_IRON_HANGING_SIGN.asBlock()).getSignType(), SignType.WARPED),
			Map.entry(((HangingSignBlock)WARPED_GOLD_HANGING_SIGN.asBlock()).getSignType(), SignType.WARPED),
			Map.entry(((HangingSignBlock)WARPED_COPPER_HANGING_SIGN.asBlock()).getSignType(), SignType.WARPED),
			Map.entry(((HangingSignBlock)WARPED_EXPOSED_COPPER_HANGING_SIGN.asBlock()).getSignType(), SignType.WARPED),
			Map.entry(((HangingSignBlock)WARPED_WEATHERED_COPPER_HANGING_SIGN.asBlock()).getSignType(), SignType.WARPED),
			Map.entry(((HangingSignBlock)WARPED_OXIDIZED_COPPER_HANGING_SIGN.asBlock()).getSignType(), SignType.WARPED),
			Map.entry(((HangingSignBlock)WARPED_NETHERITE_HANGING_SIGN.asBlock()).getSignType(), SignType.WARPED),
			//Bamboo
			Map.entry(((HangingSignBlock)ModBambooRegistry.BAMBOO_IRON_HANGING_SIGN.asBlock()).getSignType(), ModBambooRegistry.BAMBOO_SIGN.getType()),
			Map.entry(((HangingSignBlock)ModBambooRegistry.BAMBOO_GOLD_HANGING_SIGN.asBlock()).getSignType(), ModBambooRegistry.BAMBOO_SIGN.getType()),
			Map.entry(((HangingSignBlock)ModBambooRegistry.BAMBOO_COPPER_HANGING_SIGN.asBlock()).getSignType(), ModBambooRegistry.BAMBOO_SIGN.getType()),
			Map.entry(((HangingSignBlock)ModBambooRegistry.BAMBOO_EXPOSED_COPPER_HANGING_SIGN.asBlock()).getSignType(), ModBambooRegistry.BAMBOO_SIGN.getType()),
			Map.entry(((HangingSignBlock)ModBambooRegistry.BAMBOO_WEATHERED_COPPER_HANGING_SIGN.asBlock()).getSignType(), ModBambooRegistry.BAMBOO_SIGN.getType()),
			Map.entry(((HangingSignBlock)ModBambooRegistry.BAMBOO_OXIDIZED_COPPER_HANGING_SIGN.asBlock()).getSignType(), ModBambooRegistry.BAMBOO_SIGN.getType()),
			Map.entry(((HangingSignBlock)ModBambooRegistry.BAMBOO_NETHERITE_HANGING_SIGN.asBlock()).getSignType(), ModBambooRegistry.BAMBOO_SIGN.getType()),
			//Dried Bamboo
			Map.entry(((HangingSignBlock)ModBambooRegistry.DRIED_BAMBOO_IRON_HANGING_SIGN.asBlock()).getSignType(), ModBambooRegistry.DRIED_BAMBOO_SIGN.getType()),
			Map.entry(((HangingSignBlock)ModBambooRegistry.DRIED_BAMBOO_GOLD_HANGING_SIGN.asBlock()).getSignType(), ModBambooRegistry.DRIED_BAMBOO_SIGN.getType()),
			Map.entry(((HangingSignBlock)ModBambooRegistry.DRIED_BAMBOO_COPPER_HANGING_SIGN.asBlock()).getSignType(), ModBambooRegistry.DRIED_BAMBOO_SIGN.getType()),
			Map.entry(((HangingSignBlock)ModBambooRegistry.DRIED_BAMBOO_EXPOSED_COPPER_HANGING_SIGN.asBlock()).getSignType(), ModBambooRegistry.DRIED_BAMBOO_SIGN.getType()),
			Map.entry(((HangingSignBlock)ModBambooRegistry.DRIED_BAMBOO_WEATHERED_COPPER_HANGING_SIGN.asBlock()).getSignType(), ModBambooRegistry.DRIED_BAMBOO_SIGN.getType()),
			Map.entry(((HangingSignBlock)ModBambooRegistry.DRIED_BAMBOO_OXIDIZED_COPPER_HANGING_SIGN.asBlock()).getSignType(), ModBambooRegistry.DRIED_BAMBOO_SIGN.getType()),
			Map.entry(((HangingSignBlock)ModBambooRegistry.DRIED_BAMBOO_NETHERITE_HANGING_SIGN.asBlock()).getSignType(), ModBambooRegistry.DRIED_BAMBOO_SIGN.getType()),
			//Mangrove
			Map.entry(((HangingSignBlock)MANGROVE_IRON_HANGING_SIGN.asBlock()).getSignType(), MANGROVE_SIGN.getType()),
			Map.entry(((HangingSignBlock)MANGROVE_GOLD_HANGING_SIGN.asBlock()).getSignType(), MANGROVE_SIGN.getType()),
			Map.entry(((HangingSignBlock)MANGROVE_COPPER_HANGING_SIGN.asBlock()).getSignType(), MANGROVE_SIGN.getType()),
			Map.entry(((HangingSignBlock)MANGROVE_EXPOSED_COPPER_HANGING_SIGN.asBlock()).getSignType(), MANGROVE_SIGN.getType()),
			Map.entry(((HangingSignBlock)MANGROVE_WEATHERED_COPPER_HANGING_SIGN.asBlock()).getSignType(), MANGROVE_SIGN.getType()),
			Map.entry(((HangingSignBlock)MANGROVE_OXIDIZED_COPPER_HANGING_SIGN.asBlock()).getSignType(), MANGROVE_SIGN.getType()),
			Map.entry(((HangingSignBlock)MANGROVE_NETHERITE_HANGING_SIGN.asBlock()).getSignType(), MANGROVE_SIGN.getType()),
			//Cherry
			Map.entry(((HangingSignBlock)CHERRY_IRON_HANGING_SIGN.asBlock()).getSignType(), CHERRY_SIGN.getType()),
			Map.entry(((HangingSignBlock)CHERRY_GOLD_HANGING_SIGN.asBlock()).getSignType(), CHERRY_SIGN.getType()),
			Map.entry(((HangingSignBlock)CHERRY_COPPER_HANGING_SIGN.asBlock()).getSignType(), CHERRY_SIGN.getType()),
			Map.entry(((HangingSignBlock)CHERRY_EXPOSED_COPPER_HANGING_SIGN.asBlock()).getSignType(), CHERRY_SIGN.getType()),
			Map.entry(((HangingSignBlock)CHERRY_WEATHERED_COPPER_HANGING_SIGN.asBlock()).getSignType(), CHERRY_SIGN.getType()),
			Map.entry(((HangingSignBlock)CHERRY_OXIDIZED_COPPER_HANGING_SIGN.asBlock()).getSignType(), CHERRY_SIGN.getType()),
			Map.entry(((HangingSignBlock)CHERRY_NETHERITE_HANGING_SIGN.asBlock()).getSignType(), CHERRY_SIGN.getType()),
			//Cassia
			Map.entry(((HangingSignBlock)CASSIA_IRON_HANGING_SIGN.asBlock()).getSignType(), CASSIA_SIGN.getType()),
			Map.entry(((HangingSignBlock)CASSIA_GOLD_HANGING_SIGN.asBlock()).getSignType(), CASSIA_SIGN.getType()),
			Map.entry(((HangingSignBlock)CASSIA_COPPER_HANGING_SIGN.asBlock()).getSignType(), CASSIA_SIGN.getType()),
			Map.entry(((HangingSignBlock)CASSIA_EXPOSED_COPPER_HANGING_SIGN.asBlock()).getSignType(), CASSIA_SIGN.getType()),
			Map.entry(((HangingSignBlock)CASSIA_WEATHERED_COPPER_HANGING_SIGN.asBlock()).getSignType(), CASSIA_SIGN.getType()),
			Map.entry(((HangingSignBlock)CASSIA_OXIDIZED_COPPER_HANGING_SIGN.asBlock()).getSignType(), CASSIA_SIGN.getType()),
			Map.entry(((HangingSignBlock)CASSIA_NETHERITE_HANGING_SIGN.asBlock()).getSignType(), CASSIA_SIGN.getType()),
			//Charred
			Map.entry(((HangingSignBlock)CHARRED_IRON_HANGING_SIGN.asBlock()).getSignType(), CHARRED_SIGN.getType()),
			Map.entry(((HangingSignBlock)CHARRED_GOLD_HANGING_SIGN.asBlock()).getSignType(), CHARRED_SIGN.getType()),
			Map.entry(((HangingSignBlock)CHARRED_COPPER_HANGING_SIGN.asBlock()).getSignType(), CHARRED_SIGN.getType()),
			Map.entry(((HangingSignBlock)CHARRED_EXPOSED_COPPER_HANGING_SIGN.asBlock()).getSignType(), CHARRED_SIGN.getType()),
			Map.entry(((HangingSignBlock)CHARRED_WEATHERED_COPPER_HANGING_SIGN.asBlock()).getSignType(), CHARRED_SIGN.getType()),
			Map.entry(((HangingSignBlock)CHARRED_OXIDIZED_COPPER_HANGING_SIGN.asBlock()).getSignType(), CHARRED_SIGN.getType()),
			Map.entry(((HangingSignBlock)CHARRED_NETHERITE_HANGING_SIGN.asBlock()).getSignType(), CHARRED_SIGN.getType()),
			//Dogwood
			Map.entry(((HangingSignBlock)DOGWOOD_IRON_HANGING_SIGN.asBlock()).getSignType(), DOGWOOD_SIGN.getType()),
			Map.entry(((HangingSignBlock)DOGWOOD_GOLD_HANGING_SIGN.asBlock()).getSignType(), DOGWOOD_SIGN.getType()),
			Map.entry(((HangingSignBlock)DOGWOOD_COPPER_HANGING_SIGN.asBlock()).getSignType(), DOGWOOD_SIGN.getType()),
			Map.entry(((HangingSignBlock)DOGWOOD_EXPOSED_COPPER_HANGING_SIGN.asBlock()).getSignType(), DOGWOOD_SIGN.getType()),
			Map.entry(((HangingSignBlock)DOGWOOD_WEATHERED_COPPER_HANGING_SIGN.asBlock()).getSignType(), DOGWOOD_SIGN.getType()),
			Map.entry(((HangingSignBlock)DOGWOOD_OXIDIZED_COPPER_HANGING_SIGN.asBlock()).getSignType(), DOGWOOD_SIGN.getType()),
			Map.entry(((HangingSignBlock)DOGWOOD_NETHERITE_HANGING_SIGN.asBlock()).getSignType(), DOGWOOD_SIGN.getType()),
			//Hay
			Map.entry(((HangingSignBlock)HAY_IRON_HANGING_SIGN.asBlock()).getSignType(), HAY_SIGN.getType()),
			Map.entry(((HangingSignBlock)HAY_GOLD_HANGING_SIGN.asBlock()).getSignType(), HAY_SIGN.getType()),
			Map.entry(((HangingSignBlock)HAY_COPPER_HANGING_SIGN.asBlock()).getSignType(), HAY_SIGN.getType()),
			Map.entry(((HangingSignBlock)HAY_EXPOSED_COPPER_HANGING_SIGN.asBlock()).getSignType(), HAY_SIGN.getType()),
			Map.entry(((HangingSignBlock)HAY_WEATHERED_COPPER_HANGING_SIGN.asBlock()).getSignType(), HAY_SIGN.getType()),
			Map.entry(((HangingSignBlock)HAY_OXIDIZED_COPPER_HANGING_SIGN.asBlock()).getSignType(), HAY_SIGN.getType()),
			Map.entry(((HangingSignBlock)HAY_NETHERITE_HANGING_SIGN.asBlock()).getSignType(), HAY_SIGN.getType()),
			//Sugar Cane
			Map.entry(((HangingSignBlock)SUGAR_CANE_IRON_HANGING_SIGN.asBlock()).getSignType(), SUGAR_CANE_SIGN.getType()),
			Map.entry(((HangingSignBlock)SUGAR_CANE_GOLD_HANGING_SIGN.asBlock()).getSignType(), SUGAR_CANE_SIGN.getType()),
			Map.entry(((HangingSignBlock)SUGAR_CANE_COPPER_HANGING_SIGN.asBlock()).getSignType(), SUGAR_CANE_SIGN.getType()),
			Map.entry(((HangingSignBlock)SUGAR_CANE_EXPOSED_COPPER_HANGING_SIGN.asBlock()).getSignType(), SUGAR_CANE_SIGN.getType()),
			Map.entry(((HangingSignBlock)SUGAR_CANE_WEATHERED_COPPER_HANGING_SIGN.asBlock()).getSignType(), SUGAR_CANE_SIGN.getType()),
			Map.entry(((HangingSignBlock)SUGAR_CANE_OXIDIZED_COPPER_HANGING_SIGN.asBlock()).getSignType(), SUGAR_CANE_SIGN.getType()),
			Map.entry(((HangingSignBlock)SUGAR_CANE_NETHERITE_HANGING_SIGN.asBlock()).getSignType(), SUGAR_CANE_SIGN.getType()),
			//Gilded
			Map.entry(((HangingSignBlock)GILDED_IRON_HANGING_SIGN.asBlock()).getSignType(), GILDED_SIGN.getType()),
			Map.entry(((HangingSignBlock)GILDED_GOLD_HANGING_SIGN.asBlock()).getSignType(), GILDED_SIGN.getType()),
			Map.entry(((HangingSignBlock)GILDED_COPPER_HANGING_SIGN.asBlock()).getSignType(), GILDED_SIGN.getType()),
			Map.entry(((HangingSignBlock)GILDED_EXPOSED_COPPER_HANGING_SIGN.asBlock()).getSignType(), GILDED_SIGN.getType()),
			Map.entry(((HangingSignBlock)GILDED_WEATHERED_COPPER_HANGING_SIGN.asBlock()).getSignType(), GILDED_SIGN.getType()),
			Map.entry(((HangingSignBlock)GILDED_OXIDIZED_COPPER_HANGING_SIGN.asBlock()).getSignType(), GILDED_SIGN.getType()),
			Map.entry(((HangingSignBlock)GILDED_NETHERITE_HANGING_SIGN.asBlock()).getSignType(), GILDED_SIGN.getType()),
			//Blue Mushroom
			Map.entry(((HangingSignBlock)BLUE_MUSHROOM_IRON_HANGING_SIGN.asBlock()).getSignType(), BLUE_MUSHROOM_SIGN.getType()),
			Map.entry(((HangingSignBlock)BLUE_MUSHROOM_GOLD_HANGING_SIGN.asBlock()).getSignType(), BLUE_MUSHROOM_SIGN.getType()),
			Map.entry(((HangingSignBlock)BLUE_MUSHROOM_COPPER_HANGING_SIGN.asBlock()).getSignType(), BLUE_MUSHROOM_SIGN.getType()),
			Map.entry(((HangingSignBlock)BLUE_MUSHROOM_EXPOSED_COPPER_HANGING_SIGN.asBlock()).getSignType(), BLUE_MUSHROOM_SIGN.getType()),
			Map.entry(((HangingSignBlock)BLUE_MUSHROOM_WEATHERED_COPPER_HANGING_SIGN.asBlock()).getSignType(), BLUE_MUSHROOM_SIGN.getType()),
			Map.entry(((HangingSignBlock)BLUE_MUSHROOM_OXIDIZED_COPPER_HANGING_SIGN.asBlock()).getSignType(), BLUE_MUSHROOM_SIGN.getType()),
			Map.entry(((HangingSignBlock)BLUE_MUSHROOM_NETHERITE_HANGING_SIGN.asBlock()).getSignType(), BLUE_MUSHROOM_SIGN.getType()),
			//Brown Mushroom
			Map.entry(((HangingSignBlock)BROWN_MUSHROOM_IRON_HANGING_SIGN.asBlock()).getSignType(), BROWN_MUSHROOM_SIGN.getType()),
			Map.entry(((HangingSignBlock)BROWN_MUSHROOM_GOLD_HANGING_SIGN.asBlock()).getSignType(), BROWN_MUSHROOM_SIGN.getType()),
			Map.entry(((HangingSignBlock)BROWN_MUSHROOM_COPPER_HANGING_SIGN.asBlock()).getSignType(), BROWN_MUSHROOM_SIGN.getType()),
			Map.entry(((HangingSignBlock)BROWN_MUSHROOM_EXPOSED_COPPER_HANGING_SIGN.asBlock()).getSignType(), BROWN_MUSHROOM_SIGN.getType()),
			Map.entry(((HangingSignBlock)BROWN_MUSHROOM_WEATHERED_COPPER_HANGING_SIGN.asBlock()).getSignType(), BROWN_MUSHROOM_SIGN.getType()),
			Map.entry(((HangingSignBlock)BROWN_MUSHROOM_OXIDIZED_COPPER_HANGING_SIGN.asBlock()).getSignType(), BROWN_MUSHROOM_SIGN.getType()),
			Map.entry(((HangingSignBlock)BROWN_MUSHROOM_NETHERITE_HANGING_SIGN.asBlock()).getSignType(), BROWN_MUSHROOM_SIGN.getType()),
			//Red Mushroom
			Map.entry(((HangingSignBlock)RED_MUSHROOM_IRON_HANGING_SIGN.asBlock()).getSignType(), RED_MUSHROOM_SIGN.getType()),
			Map.entry(((HangingSignBlock)RED_MUSHROOM_GOLD_HANGING_SIGN.asBlock()).getSignType(), RED_MUSHROOM_SIGN.getType()),
			Map.entry(((HangingSignBlock)RED_MUSHROOM_COPPER_HANGING_SIGN.asBlock()).getSignType(), RED_MUSHROOM_SIGN.getType()),
			Map.entry(((HangingSignBlock)RED_MUSHROOM_EXPOSED_COPPER_HANGING_SIGN.asBlock()).getSignType(), RED_MUSHROOM_SIGN.getType()),
			Map.entry(((HangingSignBlock)RED_MUSHROOM_WEATHERED_COPPER_HANGING_SIGN.asBlock()).getSignType(), RED_MUSHROOM_SIGN.getType()),
			Map.entry(((HangingSignBlock)RED_MUSHROOM_OXIDIZED_COPPER_HANGING_SIGN.asBlock()).getSignType(), RED_MUSHROOM_SIGN.getType()),
			Map.entry(((HangingSignBlock)RED_MUSHROOM_NETHERITE_HANGING_SIGN.asBlock()).getSignType(), RED_MUSHROOM_SIGN.getType()),
			//Mushroom Stem
			Map.entry(((HangingSignBlock)MUSHROOM_STEM_IRON_HANGING_SIGN.asBlock()).getSignType(), MUSHROOM_STEM_SIGN.getType()),
			Map.entry(((HangingSignBlock)MUSHROOM_STEM_GOLD_HANGING_SIGN.asBlock()).getSignType(), MUSHROOM_STEM_SIGN.getType()),
			Map.entry(((HangingSignBlock)MUSHROOM_STEM_COPPER_HANGING_SIGN.asBlock()).getSignType(), MUSHROOM_STEM_SIGN.getType()),
			Map.entry(((HangingSignBlock)MUSHROOM_STEM_EXPOSED_COPPER_HANGING_SIGN.asBlock()).getSignType(), MUSHROOM_STEM_SIGN.getType()),
			Map.entry(((HangingSignBlock)MUSHROOM_STEM_WEATHERED_COPPER_HANGING_SIGN.asBlock()).getSignType(), MUSHROOM_STEM_SIGN.getType()),
			Map.entry(((HangingSignBlock)MUSHROOM_STEM_OXIDIZED_COPPER_HANGING_SIGN.asBlock()).getSignType(), MUSHROOM_STEM_SIGN.getType()),
			Map.entry(((HangingSignBlock)MUSHROOM_STEM_NETHERITE_HANGING_SIGN.asBlock()).getSignType(), MUSHROOM_STEM_SIGN.getType())
	);
	public static final List<SignType> SIGN_TYPES = new ArrayList<>(List.of(
			ModBambooRegistry.BAMBOO_SIGN.getType(), ModBambooRegistry.DRIED_BAMBOO_SIGN.getType(), MANGROVE_SIGN.getType(), CHERRY_SIGN.getType(),
			CASSIA_SIGN.getType(), CHARRED_SIGN.getType(), DOGWOOD_SIGN.getType(),
			HAY_SIGN.getType(), SUGAR_CANE_SIGN.getType(), GILDED_SIGN.getType(),
			BLUE_MUSHROOM_SIGN.getType(), BROWN_MUSHROOM_SIGN.getType(), RED_MUSHROOM_SIGN.getType(), MUSHROOM_STEM_SIGN.getType()
	));

	@Override
	public void onInitialize() {
		ModId.LOGGER.info("Wich Pack Initializing");
		RegisterAll();
	}

}
