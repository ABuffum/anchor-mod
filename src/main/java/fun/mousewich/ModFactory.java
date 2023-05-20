package fun.mousewich;

import fun.mousewich.block.*;
import fun.mousewich.block.basic.*;
import fun.mousewich.block.container.ChiseledBookshelfBlock;
import fun.mousewich.block.dust.sand.SandyBlock;
import fun.mousewich.block.gourd.CarvedGourdBlock;
import fun.mousewich.block.oxidizable.*;
import fun.mousewich.block.sculk.SculkTurfBlock;
import fun.mousewich.block.sign.HangingSignBlock;
import fun.mousewich.block.sign.WallHangingSignBlock;
import fun.mousewich.block.tnt.ModTntBlock;
import fun.mousewich.block.tnt.PowderKegBlock;
import fun.mousewich.container.*;
import fun.mousewich.entity.projectile.ModArrowEntity;
import fun.mousewich.entity.tnt.PowderKegEntity;
import fun.mousewich.gen.data.ModDatagen;
import fun.mousewich.gen.data.loot.BlockLootGenerator;
import fun.mousewich.gen.data.loot.DropTable;
import fun.mousewich.gen.data.tag.ModBlockTags;
import fun.mousewich.gen.data.tag.ModItemTags;
import fun.mousewich.item.HangingSignItem;
import fun.mousewich.item.armor.ModArmorItem;
import fun.mousewich.item.armor.ModHorseArmorItem;
import fun.mousewich.item.armor.OxidizableArmoritem;
import fun.mousewich.item.basic.ChestBoatItem;
import fun.mousewich.item.consumable.BottledDrinkItemImpl;
import fun.mousewich.item.consumable.BottledMilkItem;
import fun.mousewich.item.tool.*;
import fun.mousewich.item.tool.oxidized.*;
import fun.mousewich.material.ModArmorMaterials;
import fun.mousewich.material.ModMaterials;
import fun.mousewich.material.ModToolMaterials;
import fun.mousewich.sound.ModBlockSoundGroups;
import fun.mousewich.util.OxidationScale;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.block.*;
import net.minecraft.block.dispenser.BoatDispenserBehavior;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.data.server.BlockLootTableGenerator;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.*;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Pair;
import net.minecraft.util.SignType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

import static fun.mousewich.ModBase.ID;

public class ModFactory {
	//Helpful BlockState Delegates
	public static boolean never(BlockState state, BlockView world, BlockPos pos) { return false; }
	public static boolean never(BlockState var1, BlockView world, BlockPos pos, EntityType<?> type) { return false; }
	public static boolean noSpawning(BlockState var1, BlockView var2, BlockPos var3, EntityType<?> var) { return false; }
	public static boolean canSpawnOnLeaves(BlockState state, BlockView world, BlockPos pos, EntityType<?> type) {
		return type == EntityType.OCELOT || type == EntityType.PARROT;
	}
	public static Boolean always(BlockState state, BlockView world, BlockPos pos) { return true; }
	public static Boolean always(BlockState state, BlockView world, BlockPos pos, EntityType<?> type) { return true; }
	//Helpful Block Functions
	public static ToIntFunction<BlockState> createLightLevelFromLitBlockState(int litLevel) {
		return (state) -> state.get(Properties.LIT) ? litLevel : 0;
	}
	public static final ToIntFunction<BlockState> LUMINANCE_15 = state -> 15, LUMINANCE_14 = state -> 14, LUMINANCE_13 = state -> 13, LUMINANCE_12 = state -> 12;
	public static final ToIntFunction<BlockState> LUMINANCE_11 = state -> 11, LUMINANCE_10 = state -> 10, LUMINANCE_9 = state -> 9, LUMINANCE_8 = state -> 8;
	public static final ToIntFunction<BlockState> LUMINANCE_7 = state -> 7, LUMINANCE_6 = state -> 6, LUMINANCE_5 = state -> 5, LUMINANCE_4 = state -> 4;
	public static final ToIntFunction<BlockState> LUMINANCE_3 = state -> 3, LUMINANCE_2 = state -> 2, LUMINANCE_1 = state -> 1, LUMINANCE_0 = state -> 0;

	public static Item.Settings ItemSettings() { return new Item.Settings().group(ModBase.ITEM_GROUP); }
	public static Item.Settings ItemSettings(ItemGroup itemGroup) { return ItemSettings().group(itemGroup); }
	public static Item.Settings GlassBottledItemSettings() { return ItemSettings().maxCount(16).recipeRemainder(Items.GLASS_BOTTLE); }
	public static Item.Settings NetheriteItemSettings() { return ItemSettings().fireproof(); }
	//Items
	public static <T extends Item> T GeneratedItem(T item) { ModDatagen.Cache.Models.GENERATED.add(item); return item; }
	public static <T extends Item> T HandheldItem(T item) { ModDatagen.Cache.Models.HANDHELD.add(item); return item; }
	public static <T extends Item> T HammerItem(T item) { ModDatagen.Cache.Models.HAMMER.add(item); return item; }
	public static <T extends Item> T ParentedItem(T item, Item parent) { ModDatagen.Cache.Models.PARENTED.add(new Pair<>(item, parent)); return item; }

	private static Item MakeItemCommon(Item.Settings settings) { return new Item(settings); }
	public static Item MakeGeneratedItem() { return MakeGeneratedItem(ItemSettings()); }
	public static Item MakeGeneratedItem(Item.Settings settings) { return GeneratedItem(MakeItemCommon(settings)); }
	public static Item MakeHandheldItem() { return MakeHandheldItem(ItemSettings()); }
	public static Item MakeHandheldItem(Item.Settings settings) { return HandheldItem(MakeItemCommon(settings)); }

	private static ModAxeItem MakeAxeCommon(ModToolMaterials material) { return MakeAxeCommon(new ModAxeItem(material)); }
	private static ModAxeItem MakeAxeCommon(ModAxeItem item) { ModDatagen.Cache.Tags.Register(ModItemTags.AXES, item); return item; }
	public static ModAxeItem MakeAxe(ModToolMaterials material) { return HandheldItem(MakeAxeCommon(material)); }
	public static ModAxeItem MakeOxidizableAxe(Oxidizable.OxidizationLevel level, ModToolMaterials material) { return HandheldItem(MakeAxeCommon(new OxidizableAxeItem(level, material))); }
	public static ModAxeItem MakeWaxedAxe(ModToolMaterials material, Item parent) { return ParentedItem(MakeAxeCommon(material), parent); }

	private static HammerItem MakeHammerCommon(ModToolMaterials material) { return MakeHammerCommon(new HammerItem(material)); }
	private static HammerItem MakeHammerCommon(HammerItem item) { ModDatagen.Cache.Tags.Register(ModItemTags.HAMMERS, item); return item; }
	public static HammerItem MakeHammer(ToolMaterial material, float attackDamage, float attackSpeed) { return HammerItem(MakeHammerCommon(new HammerItem(material, attackDamage, attackSpeed))); }
	public static HammerItem MakeHammer(ToolMaterial material, float attackDamage, float attackSpeed, Item.Settings settings) { return HammerItem(MakeHammerCommon(new HammerItem(material, attackDamage, attackSpeed, settings))); }
	public static HammerItem MakeHammer(ModToolMaterials material) { return HammerItem(MakeHammerCommon(material)); }
	public static HammerItem MakeOxidizableHammer(Oxidizable.OxidizationLevel level, ModToolMaterials material) { return HammerItem(MakeHammerCommon(new OxidizableHammerItem(level, material))); }
	public static HammerItem MakeWaxedHammer(ModToolMaterials material, Item parent) { return ParentedItem(MakeHammerCommon(material), parent); }

	private static ModHoeItem MakeHoeCommon(ModToolMaterials material) { return MakeHoeCommon(new ModHoeItem(material)); }
	private static ModHoeItem MakeHoeCommon(ModHoeItem item) { ModDatagen.Cache.Tags.Register(ModItemTags.HOES, item); return item; }
	public static ModHoeItem MakeHoe(ModToolMaterials material) { return HandheldItem(MakeHoeCommon(material)); }
	public static ModHoeItem MakeOxidizableHoe(Oxidizable.OxidizationLevel level, ModToolMaterials material) { return HandheldItem(MakeHoeCommon(new OxidizableHoeItem(level, material))); }
	public static ModHoeItem MakeWaxedHoe(ModToolMaterials material, Item parent) { return ParentedItem(MakeHoeCommon(material), parent); }

	private static ModPickaxeItem MakePickaxeCommon(ModToolMaterials material) { return MakePickaxeCommon(new ModPickaxeItem(material)); }
	private static ModPickaxeItem MakePickaxeCommon(ModPickaxeItem item) { ModDatagen.Cache.Tags.Register(ModItemTags.PICKAXES, item); return item; }
	public static ModPickaxeItem MakePickaxe(ModToolMaterials material) { return HandheldItem(MakePickaxeCommon(material)); }
	public static ModPickaxeItem MakeOxidizablePickaxe(Oxidizable.OxidizationLevel level, ModToolMaterials material) { return HandheldItem(MakePickaxeCommon(new OxidizablePickaxeItem(level, material))); }
	public static ModPickaxeItem MakeWaxedPickaxe(ModToolMaterials material, Item parent) { return ParentedItem(MakePickaxeCommon(material), parent); }

	private static ModShovelItem MakeShovelCommon(ModToolMaterials material) { return MakeShovelCommon(new ModShovelItem(material)); }
	private static ModShovelItem MakeShovelCommon(ModShovelItem item) { ModDatagen.Cache.Tags.Register(ModItemTags.SHOVELS, item); return item; }
	public static ModShovelItem MakeShovel(ModToolMaterials material) { return HandheldItem(MakeShovelCommon(material)); }
	public static ModShovelItem MakeOxidizableShovel(Oxidizable.OxidizationLevel level, ModToolMaterials material) { return HandheldItem(MakeShovelCommon(new OxidizableShovelItem(level, material))); }
	public static ModShovelItem MakeWaxedShovel(ModToolMaterials material, Item parent) { return ParentedItem(MakeShovelCommon(material), parent); }

	private static ModSwordItem MakeSwordCommon(ModToolMaterials material) { return MakeSwordCommon(new ModSwordItem(material)); }
	private static ModSwordItem MakeSwordCommon(ModSwordItem item) { ModDatagen.Cache.Tags.Register(ModItemTags.SWORDS, item); return item; }
	public static ModSwordItem MakeSword(ModToolMaterials material) { return HandheldItem(MakeSwordCommon(material)); }
	public static ModSwordItem MakeOxidizableSword(Oxidizable.OxidizationLevel level, ModToolMaterials material) { return HandheldItem(MakeSwordCommon(new OxidizableSwordItem(level, material))); }
	public static ModSwordItem MakeWaxedSword(ModToolMaterials material, Item parent) { return ParentedItem(MakeSwordCommon(material), parent); }

	private static ModKnifeItem MakeKnifeCommon(ModToolMaterials material) { return MakeKnifeCommon(new ModKnifeItem(material)); }
	private static ModKnifeItem MakeKnifeCommon(ModKnifeItem item) { ModDatagen.Cache.Tags.Register(ModItemTags.KNIVES, item); return item; }
	public static ModKnifeItem MakeKnife(ModToolMaterials material) { return HandheldItem(MakeKnifeCommon(material)); }
	public static ModKnifeItem MakeOxidizableKnife(Oxidizable.OxidizationLevel level, ModToolMaterials material) { return HandheldItem(MakeKnifeCommon(new OxidizableKnifeItem(level, material))); }
	public static ModKnifeItem MakeWaxedKnife(ModToolMaterials material, Item parent) { return ParentedItem(MakeKnifeCommon(material), parent); }

	private static ModShearsItem MakeShearsCommon(ToolMaterials material) { return MakeShearsCommon(new ModShearsItem(material)); }
	private static ModShearsItem MakeShearsCommon(ModToolMaterials material) { return MakeShearsCommon(new ModShearsItem(material)); }
	private static ModShearsItem MakeShearsCommon(ModShearsItem item) { ModDatagen.Cache.Tags.Register(ModItemTags.SHEARS, item); return item.dispensable(); }
	public static ModShearsItem MakeShears(ToolMaterials material) { return GeneratedItem(MakeShearsCommon(material)); }
	public static ModShearsItem MakeShears(ToolMaterials material, Item.Settings settings) { return GeneratedItem(MakeShearsCommon(new ModShearsItem(material, settings))); }
	public static ModShearsItem MakeShears(ModToolMaterials material) { return GeneratedItem(MakeShearsCommon(material)); }
	public static ModShearsItem MakeOxidizableShears(Oxidizable.OxidizationLevel level, ModToolMaterials material) { return GeneratedItem(MakeShearsCommon(new OxidizableShearsItem(level, material))); }
	public static ModShearsItem MakeWaxedShears(ModToolMaterials material, Item parent) { return ParentedItem(MakeShearsCommon(material), parent); }

	private static ModArmorItem MakeHelmetCommon(ArmorMaterial material) { return MakeHelmetCommon(new ModArmorItem(material, EquipmentSlot.HEAD)); }
	private static ModArmorItem MakeHelmetCommon(ModArmorItem item) { ModDatagen.Cache.Tags.Register(ModItemTags.HELMETS, item); return item.dispensible(); }
	
	public static ModArmorItem MakeHelmet(ArmorMaterial material) { return GeneratedItem(MakeHelmetCommon(material)); }
	public static ModArmorItem MakeOxidizableHelmet(Oxidizable.OxidizationLevel level, ArmorMaterial material) { return GeneratedItem(MakeHelmetCommon(new OxidizableArmoritem(level, material, EquipmentSlot.HEAD))); }
	public static ModArmorItem MakeWaxedHelmet(ArmorMaterial material, Item parent) { return ParentedItem(MakeHelmetCommon(material), parent); }

	private static ModArmorItem MakeChestplateCommon(ArmorMaterial material) { return MakeChestplateCommon(new ModArmorItem(material, EquipmentSlot.CHEST)); }
	private static ModArmorItem MakeChestplateCommon(ModArmorItem item) { ModDatagen.Cache.Tags.Register(ModItemTags.HELMETS, item); return item.dispensible(); }

	public static ModArmorItem MakeChestplate(ArmorMaterial material) { return GeneratedItem(MakeChestplateCommon(material)); }
	public static ModArmorItem MakeOxidizableChestplate(Oxidizable.OxidizationLevel level, ArmorMaterial material) { return GeneratedItem(MakeChestplateCommon(new OxidizableArmoritem(level, material, EquipmentSlot.CHEST))); }
	public static ModArmorItem MakeWaxedChestplate(ArmorMaterial material, Item parent) { return ParentedItem(MakeChestplateCommon(material), parent); }

	private static ModArmorItem MakeLeggingsCommon(ArmorMaterial material) { return MakeLeggingsCommon(new ModArmorItem(material, EquipmentSlot.LEGS)); }
	private static ModArmorItem MakeLeggingsCommon(ModArmorItem item) { ModDatagen.Cache.Tags.Register(ModItemTags.HELMETS, item); return item.dispensible(); }

	public static ModArmorItem MakeLeggings(ArmorMaterial material) { return GeneratedItem(MakeLeggingsCommon(material)); }
	public static ModArmorItem MakeOxidizableLeggings(Oxidizable.OxidizationLevel level, ArmorMaterial material) { return GeneratedItem(MakeLeggingsCommon(new OxidizableArmoritem(level, material, EquipmentSlot.LEGS))); }
	public static ModArmorItem MakeWaxedLeggings(ArmorMaterial material, Item parent) { return ParentedItem(MakeLeggingsCommon(material), parent); }

	private static ModArmorItem MakeBootsCommon(ArmorMaterial material) { return MakeBootsCommon(new ModArmorItem(material, EquipmentSlot.FEET)); }
	private static ModArmorItem MakeBootsCommon(ModArmorItem item) { ModDatagen.Cache.Tags.Register(ModItemTags.HELMETS, item); return item.dispensible(); }

	public static ModArmorItem MakeBoots(ArmorMaterial material) { return GeneratedItem(MakeBootsCommon(material)); }
	public static ModArmorItem MakeOxidizableBoots(Oxidizable.OxidizationLevel level, ArmorMaterial material) { return GeneratedItem(MakeBootsCommon(new OxidizableArmoritem(level, material, EquipmentSlot.FEET))); }
	public static ModArmorItem MakeWaxedBoots(ArmorMaterial material, Item parent) { return ParentedItem(MakeBootsCommon(material), parent); }

	public static ModHorseArmorItem MakeHorseArmor(ModArmorMaterials material) { return MakeHorseArmor(material.getHorseProtectionAmount(), material); }
	public static ModHorseArmorItem MakeHorseArmor(int bonus, ArmorMaterial material) { return MakeHorseArmor(bonus, material.getName()); }
	public static ModHorseArmorItem MakeHorseArmor(int bonus, String name) { return MakeHorseArmor(bonus, name, ItemSettings().maxCount(1)); }
	public static ModHorseArmorItem MakeHorseArmor(int bonus, String name, Item.Settings settings) {
		return GeneratedItem(new ModHorseArmorItem(bonus, name, settings).dispenseSilently());
	}

	public static SpawnEggItem MakeSpawnEgg(EntityType<? extends MobEntity> entityType, int primaryColor, int secondaryColor) { return MakeSpawnEgg(entityType, primaryColor, secondaryColor, ItemSettings()); }
	public static SpawnEggItem MakeSpawnEgg(EntityType<? extends MobEntity> entityType, int primaryColor, int secondaryColor, Item.Settings settings) {
		SpawnEggItem item = new SpawnEggItem(entityType, primaryColor, secondaryColor, settings);
		ModDatagen.Cache.Models.SPAWN_EGG.add(item);
		return item;
	}

	public static Item.Settings BucketSettings() { return ItemSettings().maxCount(16); }
	public static Item.Settings FilledBucketSettings(Supplier<Item> getBucket) { return ItemSettings().recipeRemainder(getBucket.get()).maxCount(1); }

	public static FoodComponent.Builder FoodSettings(int hunger, float saturation) { return new FoodComponent.Builder().hunger(hunger).saturationModifier(saturation); }
	public static Item MakeFood(int hunger, float saturation) { return MakeFood(FoodSettings(hunger, saturation).build()); }
	public static Item MakeFood(FoodComponent food) { return GeneratedItem(new Item(ItemSettings().food(food))); }
	public static FoodComponent PoisonousFoodSettings(int hunger, float saturation) {
		return FoodSettings(hunger, saturation).statusEffect(new StatusEffectInstance(StatusEffects.POISON, 100, 0), 0.6F).build();
	}
	public static Item MakePoisonousFood(int hunger, float saturation) { return MakeFood(PoisonousFoodSettings(hunger, saturation)); }
	public static FoodComponent RottenMeatSettings(int hunger, float saturation) {
		return FoodSettings(hunger, saturation).statusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 600, 0), 0.8F).meat().build();
	}
	public static Item MakeRottenMeat(int hunger, float saturation) { return MakeFood(RottenMeatSettings(hunger, saturation)); }

	public static Item.Settings DrinkSettings(int hunger, float saturation) { return DrinkSettings(FoodSettings(hunger, saturation).build()); }
	public static Item.Settings DrinkSettings(FoodComponent food) { return ItemSettings().recipeRemainder(Items.GLASS_BOTTLE).food(food); }
	public static Item MakeDrink(int hunger, float saturation) { return MakeDrink(DrinkSettings(hunger, saturation)); }
	public static Item MakeDrink(FoodComponent food) { return MakeDrink(DrinkSettings(food)); }
	public static Item MakeDrink(Item.Settings settings) { return GeneratedItem(new BottledDrinkItemImpl(settings)); }

	public static Item MakeDairyDrink(int hunger, float saturation) { return MakeDairyDrink(FoodSettings(hunger, saturation).build()); }
	public static Item MakeDairyDrink(FoodComponent food) { return MakeDairyDrink(DrinkSettings(food)); }
	public static Item MakeDairyDrink(Item.Settings settings) { return GeneratedItem(new BottledMilkItem(settings)); }

	public static Item MakeStew(int hunger, float saturation) { return MakeStew(FoodSettings(hunger, saturation).build()); }
	public static Item MakeStew(FoodComponent food) { return MakeStew(ItemSettings().food(food).recipeRemainder(Items.BOWL)); }
	public static Item MakeStew(Item.Settings settings) { return GeneratedItem(new MushroomStewItem(settings)); }

	public static EntityModelLayer MakeModelLayer(String path) { return MakeModelLayer(path, "main"); }
	public static EntityModelLayer MakeModelLayer(String path, String name) { return new EntityModelLayer(ID(path), name); }

	public static EntityType<ModArrowEntity> MakeArrowEntity(EntityType.EntityFactory<ModArrowEntity> factory) {
		return FabricEntityTypeBuilder.create(SpawnGroup.MISC, factory).dimensions(EntityDimensions.fixed(0.5f, 0.5f)).trackRangeChunks(4).trackedUpdateRate(20).build();
	}

	public static BlockContainer MakeBeehive(MapColor color) { return MakeBeehive(color, BlockSoundGroup.WOOD); }
	public static BlockContainer MakeBeehive(MapColor color, BlockSoundGroup sounds) {
		BlockContainer container = new BlockContainer(new ModBeehiveBlock(Block.Settings.of(Material.WOOD, color).strength(0.6f).sounds(sounds)))
				.drops(DropTable.BEEHIVE).blockTag(ModBlockTags.WOODEN_BEEHIVES).itemTag(ModItemTags.BEEHIVES);
		ModDatagen.Cache.Models.BEEHIVE.add(container);
		return container;
	}

	public static BlockContainer MakeBookshelf(BlockConvertible base) { return MakeBookshelf(base.asBlock()); }
	public static BlockContainer MakeBookshelf(Block base) { return MakeBookshelf(base, base.getSoundGroup(base.getDefaultState())); }
	public static BlockContainer MakeBookshelf(BlockConvertible base, BlockSoundGroup sounds) { return MakeBookshelf(base.asBlock(), sounds); }
	public static BlockContainer MakeBookshelf(Block base, BlockSoundGroup sounds) {
		BlockContainer container = new BlockContainer(new BookshelfBlock(
				Block.Settings.of(Material.WOOD, base.getDefaultMapColor()).strength(1.5F).sounds(sounds)))
				.drops(DropTable.BOOKSHELF).blockTag(ModBlockTags.BOOKSHELVES).itemTag(ModItemTags.BOOKSHELVES);
		ModDatagen.Cache.Models.BOOKSHELF.add(new Pair<>(container, base));
		return container;
	}

	public static BlockContainer MakeChiseledBookshelf(MapColor color) { return MakeChiseledBookshelf(color, ModBlockSoundGroups.CHISELED_BOOKSHELF); }
	public static BlockContainer MakeChiseledBookshelf(MapColor color, BlockSoundGroup sounds) {
		BlockContainer container = new BlockContainer(new ChiseledBookshelfBlock(Block.Settings.of(Material.WOOD, color).strength(1.5F).sounds(sounds)))
				.dropSelf().blockTag(ModBlockTags.CHISELED_BOOKSHELVES);
		ModDatagen.Cache.Models.CHISELED_BOOKSHELF.add(container);
		return container;
	}

	public static BlockContainer MakeCraftingTable(BlockConvertible base) { return MakeCraftingTable(base.asBlock()); }
	public static BlockContainer MakeCraftingTable(Block base) { return MakeCraftingTable(base, base.getSoundGroup(base.getDefaultState())); }
	public static BlockContainer MakeCraftingTable(BlockConvertible base, BlockSoundGroup sounds) { return MakeCraftingTable(base.asBlock(), sounds); }
	public static BlockContainer MakeCraftingTable(Block base, BlockSoundGroup sounds) {
		BlockContainer container = new BlockContainer(new ModCraftingTableBlock(Block.Settings.of(Material.WOOD, base.getDefaultMapColor()).strength(2.5f).sounds(sounds))).dropSelf();
		ModDatagen.Cache.Models.CRAFTING_TABLE.add(new Pair<>(container, base));
		return container.blockTag(ModBlockTags.CRAFTING_TABLES);
	}

	public static Block.Settings DyeBlockSettings(DyeColor color) {
		return Block.Settings.of(Material.SOLID_ORGANIC, color.getMapColor()).strength(1.0f).sounds(BlockSoundGroup.SHROOMLIGHT);
	}

	public static final Material FROGSPAWN_MATERIAL = new Material(MapColor.WATER_BLUE, false, false, false, false, false, false, PistonBehavior.DESTROY);
	public static BlockContainer MakeFroglight(MapColor color) { return MakeFroglight(color, ItemSettings()); }
	public static BlockContainer MakeFroglight(MapColor color, Item.Settings settings) {
		return new BlockContainer(new PillarBlock(Block.Settings.of(ModMaterials.FROGLIGHT, color).strength(0.3f).luminance(LUMINANCE_15).sounds(ModBlockSoundGroups.FROGLIGHT)), settings).dropSelf();
	}

	public static Block.Settings TntSettings() { return Block.Settings.of(Material.TNT).breakInstantly().sounds(BlockSoundGroup.GRASS); }
	public static Block.Settings TntSettings(MapColor color) { return TntSettings().mapColor(color); }

	public static BlockContainer MakePowderKeg(BlockConvertible base) { return MakePowderKeg(base.asBlock()); }
	public static BlockContainer MakePowderKeg(Block base) {
		BlockContainer container = BuildBlock(new PowderKegBlock(BarrelSettings(base.getDefaultMapColor(), base.getSoundGroup(base.getDefaultState()))))
				.dispenser(ModTntBlock.DispenserBehavior(PowderKegEntity::new))
				.blockTag(BlockTags.AXE_MINEABLE)
				.blockTag(ModBlockTags.POWDER_KEGS);
		ModDatagen.Cache.Models.POWDER_KEG.add(new Pair<>(container, base));
		return container;
	}

	public static Item.Settings PlushieItemSettings() { return ItemSettings(ModBase.PLUSHIE_GROUP); }
	public static Block.Settings PlushieSettings() {
		return Block.Settings.of(Material.WOOL).nonOpaque().sounds(BlockSoundGroup.WOOL).strength(0.7F);
	}
	public static Block.Settings PlushieSettings(MapColor color) { return PlushieSettings().mapColor(color); }
	public static BlockContainer MakePlushie(Function<Block.Settings, Block> blockProvider) { return MakePlushie(blockProvider, PlushieSettings()); }
	public static BlockContainer MakePlushie(Function<Block.Settings, Block> blockProvider, MapColor color) { return MakePlushie(blockProvider, PlushieSettings(color)); }
	public static BlockContainer MakePlushie(Function<Block.Settings, Block> blockProvider, Block.Settings settings) {
		BlockContainer container = BuildBlock(blockProvider.apply(settings), PlushieItemSettings());
		ModDatagen.Cache.Models.PLUSHIE.add(container);
		return container;
	}

	public static Block.Settings TorchSettings(int luminance, BlockSoundGroup sounds) {
		return FabricBlockSettings.of(Material.DECORATION).noCollision().breakInstantly().nonOpaque().luminance(createLightLevelFromLitBlockState(luminance)).sounds(sounds);
	}
	public static TorchContainer MakeTorch(int luminance, BlockSoundGroup sounds, DefaultParticleType particle) { return MakeTorch(luminance, sounds, particle, ItemSettings()); }
	public static TorchContainer MakeTorch(int luminance, BlockSoundGroup sounds, DefaultParticleType particle, Item.Settings settings) {
		return new TorchContainer(TorchSettings(luminance, sounds), particle, settings).dropSelf();
	}
	public static TorchContainer MakeTorch(BlockSoundGroup sounds, DefaultParticleType particle) { return MakeTorch(14, sounds, particle); }
	public static TorchContainer MakeTorch(BlockSoundGroup sounds, DefaultParticleType particle, Item.Settings settings) { return MakeTorch(14, sounds, particle, settings); }
	public static TorchContainer MakeTorch(BlockSoundGroup sounds) { return MakeTorch(sounds, ParticleTypes.FLAME); }
	public static TorchContainer MakeTorch(BlockSoundGroup sounds, Item.Settings settings) { return MakeTorch(sounds, ParticleTypes.FLAME, settings); }
	public static TorchContainer MakeEnderTorch(BlockSoundGroup sounds) { return MakeTorch(12, sounds, ModBase.ENDER_FIRE_FLAME_PARTICLE); }
	public static TorchContainer MakeEnderTorch(BlockSoundGroup sounds, Item.Settings settings) { return MakeTorch(12, sounds, ModBase.ENDER_FIRE_FLAME_PARTICLE, settings); }
	public static TorchContainer MakeSoulTorch(BlockSoundGroup sounds) { return MakeTorch(10, sounds, ParticleTypes.SOUL_FIRE_FLAME); }
	public static TorchContainer MakeSoulTorch(BlockSoundGroup sounds, Item.Settings settings) { return MakeTorch(10, sounds, ParticleTypes.SOUL_FIRE_FLAME, settings); }
	public static TorchContainer MakeUnderwaterTorch(BlockSoundGroup sounds) { return MakeUnderwaterTorch(sounds, ItemSettings()); }
	public static TorchContainer MakeUnderwaterTorch(BlockSoundGroup sounds, Item.Settings settings) {
		return TorchContainer.Waterloggable(TorchSettings(14, sounds), ModBase.UNDERWATER_TORCH_GLOW, settings).dropSelf();
	}

	public static TorchContainer MakeOxidizableTorch(Oxidizable.OxidizationLevel level, int luminance, BlockSoundGroup sounds, DefaultParticleType particle) {
		return TorchContainer.Oxidizable(level, TorchSettings(luminance, sounds).ticksRandomly(), particle).dropSelf();
	}
	public static TorchContainer MakeOxidizableTorch(Oxidizable.OxidizationLevel level, BlockSoundGroup sounds, DefaultParticleType particle) {
		return MakeOxidizableTorch(level, 14, sounds, particle);
	}
	public static TorchContainer MakeOxidizableEnderTorch(Oxidizable.OxidizationLevel level, BlockSoundGroup sounds) {
		return MakeOxidizableTorch(level, 12, sounds, ModBase.ENDER_FIRE_FLAME_PARTICLE);
	}
	public static TorchContainer MakeOxidizableSoulTorch(Oxidizable.OxidizationLevel level, BlockSoundGroup sounds) {
		return MakeOxidizableTorch(level, 10, sounds, ParticleTypes.SOUL_FIRE_FLAME);
	}
	public static TorchContainer MakeOxidizableUnderwaterTorch(Oxidizable.OxidizationLevel level, BlockSoundGroup sounds) {
		return TorchContainer.WaterloggableOxidizable(level, TorchSettings(14, sounds).ticksRandomly(), ModBase.UNDERWATER_TORCH_GLOW).dropSelf();
	}

	public static Block.Settings OxidizablePressurePlateSettings(Oxidizable.OxidizationLevel level) {
		return OxidizablePressurePlateSettings(level, BlockSoundGroup.WOOD);
	}
	public static Block.Settings OxidizablePressurePlateSettings(Oxidizable.OxidizationLevel level, BlockSoundGroup sounds) {
		return Block.Settings.of(Material.METAL, OxidationScale.getMapColor(level)).requiresTool().noCollision().strength(0.5F).sounds(sounds);
	}

	public static Block.Settings LanternSettings(int luminance) {
		return Block.Settings.of(Material.METAL).requiresTool().strength(3.5F).sounds(BlockSoundGroup.LANTERN).luminance(createLightLevelFromLitBlockState(luminance)).nonOpaque();
	}
	public static BlockContainer MakeLantern(int luminance) { return MakeLantern(luminance, ItemSettings()); }
	public static BlockContainer MakeLantern(int luminance, Item.Settings settings) {
		return new BlockContainer(new LightableLanternBlock(LanternSettings(luminance)), settings).dropSelf();
	}
	public static Block.Settings UnlitLanternSettings() {
		return Block.Settings.of(Material.METAL).requiresTool().strength(3.5F).sounds(BlockSoundGroup.LANTERN).nonOpaque();
	}
	public static UnlitLanternBlock MakeUnlitLantern(Block lit, ItemConvertible getPickStack) {
		return new UnlitLanternBlock(lit, getPickStack, UnlitLanternSettings());
	}
	public static BlockContainer MakeOxidizableLantern(int luminance, Oxidizable.OxidizationLevel level) {
		return new BlockContainer(new OxidizableLightableLanternBlock(level, LanternSettings(luminance))).dropSelf();
	}

	private static BlockContainer MakeCampfireCommon(int luminance, int fireDamage, MapColor mapColor, BlockSoundGroup sounds, boolean emitsParticles) {
		return new BlockContainer(new ModCampfireBlock(emitsParticles, fireDamage, Block.Settings.of(Material.WOOD, mapColor).strength(2.0F).sounds(sounds).luminance(createLightLevelFromLitBlockState(luminance)).nonOpaque()))
				.blockTag(BlockTags.CAMPFIRES).blockTag(BlockTags.AXE_MINEABLE);
	}
	public static BlockContainer MakeCampfire(MapColor color) { return MakeCampfire(color, BlockSoundGroup.WOOD); }
	public static BlockContainer MakeCampfire(MapColor color, BlockSoundGroup sounds) {
		BlockContainer container = MakeCampfireCommon(15, 1, color, sounds, true).drops(DropTable.CAMPFIRE);
		ModDatagen.Cache.Models.CAMPFIRE.add(container);
		return container;
	}
	public static BlockContainer MakeSoulCampfire(BlockConvertible base) { return MakeSoulCampfire(base.asBlock()); }
	public static BlockContainer MakeSoulCampfire(Block base) { return MakeSoulCampfire(base, BlockSoundGroup.WOOD); }
	public static BlockContainer MakeSoulCampfire(BlockConvertible base, BlockSoundGroup sounds) { return MakeSoulCampfire(base.asBlock(), sounds); }
	public static BlockContainer MakeSoulCampfire(Block base, BlockSoundGroup sounds) {
		BlockContainer container = MakeCampfireCommon(10, 2, base.getDefaultMapColor(), sounds, false).drops(DropTable.SOUL_CAMPFIRE);
		ModDatagen.Cache.Models.CAMPFIRE_SOUL.add(new Pair<>(container, base));
		return container.blockTag(BlockTags.PIGLIN_REPELLENTS).itemTag(ItemTags.PIGLIN_REPELLENTS).itemTag(ModItemTags.SOUL_CAMPFIRES);
	}
	public static BlockContainer MakeEnderCampfire(BlockConvertible base) { return MakeEnderCampfire(base.asBlock()); }
	public static BlockContainer MakeEnderCampfire(Block base) { return MakeEnderCampfire(base, BlockSoundGroup.WOOD); }
	public static BlockContainer MakeEnderCampfire(BlockConvertible base, BlockSoundGroup sounds) { return MakeEnderCampfire(base.asBlock(), sounds); }
	public static BlockContainer MakeEnderCampfire(Block base, BlockSoundGroup sounds) {
		BlockContainer container = MakeCampfireCommon(13, 3, base.getDefaultMapColor(), sounds, false).drops(DropTable.ENDER_CAMPFIRE);
		ModDatagen.Cache.Models.CAMPFIRE_ENDER.add(new Pair<>(container, base));
		return container;
	}

	public static Block.Settings BarsSettings(BlockSoundGroup sounds) {
		return Block.Settings.of(Material.METAL, MapColor.CLEAR).requiresTool().strength(5.0F, 6.0F).sounds(sounds).nonOpaque();
	}
	private static BlockContainer MakeBarsCommon(BlockSoundGroup sounds, Item.Settings settings) {
		return new BlockContainer(new ModPaneBlock(BarsSettings(sounds)), settings).dropSelf();
	}
	public static BlockContainer MakeBars() { return MakeBars(BlockSoundGroup.METAL); }
	public static BlockContainer MakeBars(BlockSoundGroup sounds) { return MakeBars(sounds, ItemSettings()); }
	public static BlockContainer MakeBars(BlockSoundGroup sounds, Item.Settings settings) {
		BlockContainer container = MakeBarsCommon(sounds, settings);
		ModDatagen.Cache.Models.BARS.add(container);
		return container;
	}
	public static BlockContainer MakeOxidizableBars(BlockSoundGroup sounds, Oxidizable.OxidizationLevel level) {
		BlockContainer container = new BlockContainer(new OxidizablePaneBlock(level, BarsSettings(sounds))).dropSelf();
		ModDatagen.Cache.Models.BARS.add(container);
		return container;
	}
	public static BlockContainer MakeWaxedBars() { return MakeWaxedBars(BlockSoundGroup.METAL); }
	public static BlockContainer MakeWaxedBars(BlockSoundGroup sounds) { return MakeWaxedBars(sounds, ItemSettings()); }
	public static BlockContainer MakeWaxedBars(BlockSoundGroup sounds, Item.Settings settings) {
		return MakeBarsCommon(sounds, settings);
	}

	public static Block.Settings ChainSettings() {
		return Block.Settings.of(Material.METAL, MapColor.CLEAR).requiresTool().strength(5.0F, 6.0F).sounds(BlockSoundGroup.CHAIN).nonOpaque();
	}
	public static BlockContainer MakeChain() { return MakeChain(ItemSettings()); }
	public static BlockContainer MakeChain(Item.Settings settings) { return BuildChain(MakeChainCommon(settings)); }
	public static BlockContainer MakeOxidizableChain(Oxidizable.OxidizationLevel level) { return BuildChain(new BlockContainer(new OxidizableChainBlock(level, ChainSettings()))); }
	public static BlockContainer MakeWaxedChain() { return MakeChainCommon(ItemSettings()).dropSelf().blockTag(BlockTags.PICKAXE_MINEABLE); }
	private static BlockContainer MakeChainCommon(Item.Settings settings) { return new BlockContainer(new ChainBlock(ChainSettings()), settings); }
	private static BlockContainer BuildChain(BlockContainer container) {
		ModDatagen.Cache.Models.CHAIN.add(container);
		return container.dropSelf().blockTag(BlockTags.PICKAXE_MINEABLE);
	}
	public static BlockContainer MakeHeavyChain() { return MakeHeavyChain(ItemSettings()); }
	public static BlockContainer MakeHeavyChain(Item.Settings settings) { return BuildHeavyChain(MakeHeavyChainCommon(settings)); }
	public static BlockContainer MakeOxidizableHeavyChain(Oxidizable.OxidizationLevel level) { return BuildHeavyChain(new BlockContainer(new OxidizableHeavyChainBlock(level, ChainSettings()))); }
	public static BlockContainer MakeWaxedHeavyChain() { return MakeHeavyChainCommon(ItemSettings()).dropSelf().blockTag(BlockTags.PICKAXE_MINEABLE); }
	private static BlockContainer MakeHeavyChainCommon(Item.Settings settings) { return new BlockContainer(new HeavyChainBlock(ChainSettings().strength(6.0F, 7.0F)), settings); }
	private static BlockContainer BuildHeavyChain(BlockContainer container) {
		ModDatagen.Cache.Models.HEAVY_CHAIN.add(container);
		return container.dropSelf().blockTag(BlockTags.PICKAXE_MINEABLE);
	}

	public static Block.Settings BedSettings(MapColor color, BlockSoundGroup sounds, ToIntFunction<BlockState> luminance) {
		Block.Settings output = AbstractBlock.Settings.copy(Blocks.WHITE_BED).mapColor(color).sounds(sounds);
		return luminance == null ? output : output.luminance(luminance);
	}

	public static BedContainer MakeBed(String name) { return MakeBed(name, MapColor.WHITE); }
	public static BedContainer MakeBed(String name, MapColor color) { return MakeBed(name, color, BlockSoundGroup.WOOD); }
	public static BedContainer MakeBed(String name, MapColor color, BlockSoundGroup sounds) { return MakeBed(name, color, sounds, null); }
	public static BedContainer MakeBed(String name, MapColor color, BlockSoundGroup sounds, ToIntFunction<BlockState> luminance) {
		BedContainer bed = new BedContainer(name, BedSettings(color, sounds, luminance), ItemSettings().maxCount(1));
		BlockLootGenerator.Drops.put(bed.asBlock(), DropTable.BED);
		return bed;
	}

	public static Item.Settings BoatSettings() { return ItemSettings().maxCount(1); }
	public static Item.Settings BoatSettings(ItemGroup group) { return ItemSettings(group).maxCount(1); }
	public static ChestBoatItem MakeChestBoat(BoatEntity.Type type, Item.Settings settings) {
		ChestBoatItem boat = new ChestBoatItem(type, settings.maxCount(1));
		DispenserBlock.registerBehavior(boat, new BoatDispenserBehavior(type));
		ModDatagen.Cache.Tags.Register(ModItemTags.CHEST_BOATS, boat);
		ModDatagen.Cache.Models.GENERATED.add(boat);
		return boat;
	}

	public static BoatContainer MakeBoat(String name, IBlockItemContainer base) { return MakeBoat(name, base, BoatSettings()); }
	public static BoatContainer MakeBoat(String name, IBlockItemContainer base, Item.Settings settings) {
		BoatContainer boat = new BoatContainer(name, base, false, settings).dispensable();
		ModDatagen.Cache.Tags.Register(ItemTags.BOATS, boat.asItem());
		ModDatagen.Cache.Models.GENERATED.add(boat.asItem());
		ModDatagen.Cache.Tags.Register(ModItemTags.CHEST_BOATS, boat.getChestBoat());
		ModDatagen.Cache.Models.GENERATED.add(boat.getChestBoat());
		return boat;
	}

	public static BlockContainer MakeCandle(MapColor mapColor, double luminance) {
		return new BlockContainer(new CandleBlock(Block.Settings.of(Material.DECORATION, mapColor).nonOpaque().strength(0.1F).sounds(BlockSoundGroup.CANDLE)
				.luminance((state) -> (int)(state.get(CandleBlock.LIT) ? luminance * state.get(CandleBlock.CANDLES) : 0))))
				.drops(DropTable.CANDLE).blockTag(BlockTags.CANDLES).itemTag(ItemTags.CANDLES);
	}

	public static Block.Settings LadderSettings(BlockSoundGroup sounds) {
		return Block.Settings.of(Material.DECORATION).strength(0.4F).sounds(sounds).nonOpaque();
	}
	private static BlockContainer MakeLadderCommon(BlockSoundGroup sounds) {
		return new BlockContainer(new ModLadderBlock(LadderSettings(sounds))).dropSelf().blockTag(BlockTags.CLIMBABLE);
	}
	public static BlockContainer MakeLadder() { return MakeLadder(BlockSoundGroup.LADDER); }
	public static BlockContainer MakeLadder(BlockSoundGroup sounds) {
		BlockContainer container = MakeLadderCommon(sounds);
		ModDatagen.Cache.Models.LADDER.add(container);
		return container;
	}
	public static BlockContainer MakeSpecialLadder(BlockSoundGroup sounds) {
		return MakeLadderCommon(sounds);
	}

	public static Block.Settings InfestedBlockSettings() { return Block.Settings.of(Material.ORGANIC_PRODUCT); }
	public static Block.Settings InfestedBlockSettings(BlockSoundGroup sounds) { return InfestedBlockSettings().sounds(sounds); }
	public static Block.Settings InfestedBlockSettings(MapColor color) { return Block.Settings.of(Material.ORGANIC_PRODUCT, color); }
	public static Block.Settings InfestedBlockSettings(MapColor color, BlockSoundGroup sounds) { return InfestedBlockSettings(color).sounds(sounds); }

	public static BlockContainer MakeInfested(IBlockItemContainer container) { return MakeInfested(container, InfestedBlockSettings()); }
	public static BlockContainer MakeInfested(Block block, ItemConvertible item) { return MakeInfested(block, item, InfestedBlockSettings()); }
	public static BlockContainer MakeInfested(IBlockItemContainer container, BlockSoundGroup sounds) { return MakeInfested(container, InfestedBlockSettings(sounds)); }
	public static BlockContainer MakeInfested(Block block, ItemConvertible item, BlockSoundGroup sounds) { return MakeInfested(block, item, InfestedBlockSettings(sounds)); }
	public static BlockContainer MakeInfested(IBlockItemContainer container, MapColor color) { return MakeInfested(container, InfestedBlockSettings(color)); }
	public static BlockContainer MakeInfested(Block block, ItemConvertible item, MapColor color) { return MakeInfested(block, item, InfestedBlockSettings(color)); }
	public static BlockContainer MakeInfested(IBlockItemContainer container, MapColor color, BlockSoundGroup sounds) { return MakeInfested(container, InfestedBlockSettings(color, sounds)); }
	public static BlockContainer MakeInfested(Block block, ItemConvertible item, MapColor color, BlockSoundGroup sounds) { return MakeInfested(block, item, InfestedBlockSettings(color, sounds)); }
	public static BlockContainer MakeInfested(IBlockItemContainer container, Block.Settings settings) { return MakeInfested(container.asBlock(), container.asItem(), settings); }
	public static BlockContainer MakeInfested(Block block, ItemConvertible item, Block.Settings settings) {
		return BuildBlock(new InfestedBlock(block, settings), DropTable.SilkTouchOrElse(item));
	}

	public static BlockContainer MakeWood(IBlockItemContainer log, Item.Settings settings) {
		return new BlockContainer(new PillarBlock(Block.Settings.copy(log.asBlock())), settings).dropSelf();
	}
	public static BlockContainer MakeWood(MapColor color) { return MakeWood(color, ItemSettings()); }
	public static BlockContainer MakeWood(MapColor color, Item.Settings settings) { return MakeWood(color, BlockSoundGroup.WOOD, settings); }
	public static BlockContainer MakeWood(MapColor color, BlockSoundGroup soundGroup) { return MakeWood(color, soundGroup, ItemSettings()); }
	public static BlockContainer MakeWood(MapColor color, BlockSoundGroup soundGroup, Item.Settings settings) {
		return new BlockContainer(new ModPillarBlock(LogSettings(color, soundGroup)), settings).dropSelf();
	}

	public static Block.Settings ButtonSettings(float strength, BlockSoundGroup sounds) { return Block.Settings.of(Material.DECORATION).noCollision().strength(strength).sounds(sounds); }
	public static BlockContainer MakeWoodButton() { return MakeWoodButton(ItemSettings()); }
	public static BlockContainer MakeWoodButton(Item.Settings settings) {
		return new BlockContainer(new ModWoodenButtonBlock(ButtonSettings(0.5F, BlockSoundGroup.WOOD)), settings).dropSelf();
	}
	public static BlockContainer MakeWoodButton(BlockSoundGroup soundGroup, SoundEvent pressed, SoundEvent released) { return MakeWoodButton(ItemSettings(), soundGroup, pressed, released); }
	public static BlockContainer MakeWoodButton(Item.Settings settings, BlockSoundGroup soundGroup, SoundEvent pressed, SoundEvent released) {
		return new BlockContainer(new ModWoodenButtonBlock(ButtonSettings(0.5F, soundGroup), pressed, released), settings).dropSelf();
	}

	public static BlockContainer MakeMetalButton() { return MakeMetalButton(BlockSoundGroup.METAL); }
	public static BlockContainer MakeMetalButton(BlockSoundGroup sounds) { return MakeMetalButton(sounds, ItemSettings()); }
	public static BlockContainer MakeMetalButton(BlockSoundGroup sounds, Item.Settings settings) {
		return new BlockContainer(new MetalButtonBlock(ButtonSettings(10, sounds)), settings).dropSelf();
	}
	public static BlockContainer MakeOxidizableButton(BlockSoundGroup sounds, Oxidizable.OxidizationLevel level) {
		return new BlockContainer(new OxidizableButtonBlock(level, ButtonSettings(10, sounds))).dropSelf();
	}

	public static Block.Settings WoodPressurePlateSettings(MapColor color, BlockSoundGroup soundGroup) { return Block.Settings.of(Material.WOOD, color).noCollision().strength(0.5F).sounds(soundGroup); }
	public static BlockContainer MakeWoodPressurePlate(IBlockItemContainer base) { return MakeWoodPressurePlate(base, ItemSettings()); }
	public static BlockContainer MakeWoodPressurePlate(IBlockItemContainer base, Item.Settings settings) {
		return MakeWoodPressurePlate(base, settings, BlockSoundGroup.WOOD, SoundEvents.BLOCK_WOODEN_PRESSURE_PLATE_CLICK_ON, SoundEvents.BLOCK_WOODEN_PRESSURE_PLATE_CLICK_OFF);
	}
	public static BlockContainer MakeWoodPressurePlate(IBlockItemContainer base, BlockSoundGroup soundGroup, SoundEvent pressed, SoundEvent released) { return MakeWoodPressurePlate(base, ItemSettings(), soundGroup, pressed, released); }
	public static BlockContainer MakeWoodPressurePlate(IBlockItemContainer base, Item.Settings settings, BlockSoundGroup soundGroup, SoundEvent pressed, SoundEvent released) {
		ModPressurePlateBlock block = new ModPressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING, WoodPressurePlateSettings(base.asBlock().getDefaultMapColor(), soundGroup), pressed, released);
		return BuildWoodPressurePlate(block, base.asBlock(), settings);
	}
	public static BlockContainer BuildWoodPressurePlate(ModPressurePlateBlock block, Block base, Item.Settings settings) {
		BlockContainer container = new BlockContainer(block, settings).dropSelf();
		container.blockTag(BlockTags.WOODEN_PRESSURE_PLATES).itemTag(ItemTags.WOODEN_PRESSURE_PLATES);
		ModDatagen.Cache.Models.PRESSURE_PLATE.add(new Pair<>(container, base));
		return container;
	}

	private static BlockContainer BuildDoorCommon(BlockContainer container) {
		ModDatagen.Cache.Models.DOOR.add(container);
		return container.drops(DropTable.DOOR);
	}

	public static BlockContainer BuildMetalDoor(ModDoorBlock door) { return BuildMetalDoor(door, ItemSettings()); }
	public static BlockContainer BuildMetalDoor(ModDoorBlock door, Item.Settings settings) {
		return BuildDoorCommon(new BlockContainer(door, settings)).blockTag(BlockTags.DOORS).itemTag(ItemTags.DOORS);
	}

	public static Block.Settings WoodDoorSettings(MapColor color, BlockSoundGroup soundGroup) { return Block.Settings.of(Material.WOOD, color).strength(3.0F).sounds(soundGroup).nonOpaque(); }
	public static BlockContainer MakeWoodDoor(IBlockItemContainer ingredient) { return MakeWoodDoor(ingredient, ItemSettings()); }
	public static BlockContainer MakeWoodDoor(IBlockItemContainer ingredient, Item.Settings settings) {
		return BuildWoodDoor(new ModDoorBlock(WoodDoorSettings(ingredient.asBlock().getDefaultMapColor(), BlockSoundGroup.WOOD)), settings);
	}
	public static BlockContainer MakeWoodDoor(IBlockItemContainer ingredient, BlockSoundGroup soundGroup, SoundEvent opened, SoundEvent closed) { return MakeWoodDoor(ingredient, ItemSettings(), soundGroup, opened, closed); }
	public static BlockContainer MakeWoodDoor(IBlockItemContainer ingredient, Item.Settings settings, BlockSoundGroup soundGroup, SoundEvent opened, SoundEvent closed) {
		return BuildWoodDoor(new ModDoorBlock(WoodDoorSettings(ingredient.asBlock().getDefaultMapColor(), soundGroup), opened, closed), settings);
	}
	public static BlockContainer BuildWoodDoor(ModDoorBlock door) { return BuildWoodDoor(door, ItemSettings()); }
	public static BlockContainer BuildWoodDoor(ModDoorBlock door, Item.Settings settings) {
		return BuildDoorCommon(new BlockContainer(door, settings)).blockTag(BlockTags.WOODEN_DOORS).itemTag(ItemTags.WOODEN_DOORS);
	}

	public static SignContainer MakeSign(String name, BlockConvertible planks, BlockConvertible hangingSignBase) { return MakeSign(name, planks.asBlock(), hangingSignBase.asBlock()); }
	public static SignContainer MakeSign(String name, Block planks, Block hangingSignBase) { return MakeSign(name, planks, SignItemSettings(), hangingSignBase); }
	public static SignContainer MakeSign(String name, BlockConvertible planks, Item.Settings settings, BlockConvertible hangingSignBase) { return MakeSign(name, planks.asBlock(), settings, hangingSignBase.asBlock()); }
	public static SignContainer MakeSign(String name, Block planks, Item.Settings settings, Block hangingSignBase) {
		return MakeSign(name, planks, settings, hangingSignBase, ModBlockSoundGroups.HANGING_SIGN);
	}
	public static SignContainer MakeSign(String name, BlockConvertible planks, BlockConvertible hangingSignBase, BlockSoundGroup sounds) { return MakeSign(name, planks.asBlock(), hangingSignBase.asBlock(), sounds); }
	public static SignContainer MakeSign(String name, Block planks, Block hangingSignBase, BlockSoundGroup sounds) { return MakeSign(name, planks, ItemSettings(), hangingSignBase, sounds); }
	public static SignContainer MakeSign(String name, BlockConvertible planks, Item.Settings settings, BlockConvertible hangingSignBase, BlockSoundGroup sounds) { return MakeSign(name, planks.asBlock(), settings, hangingSignBase.asBlock(), sounds); }
	public static SignContainer MakeSign(String name, Block planks, Item.Settings settings, Block hangingSignBase, BlockSoundGroup sounds) {
		return MakeSign(name, planks, settings, hangingSignBase, sounds, ModBlockSoundGroups.HANGING_SIGN);
	}
	public static SignContainer MakeSign(String name, BlockConvertible planks, Item.Settings settings, BlockConvertible hangingSignBase, BlockSoundGroup sounds, BlockSoundGroup hangingSounds) { return MakeSign(name, planks.asBlock(), settings, hangingSignBase.asBlock(), sounds, hangingSounds); }
	public static SignContainer MakeSign(String name, Block planks, Item.Settings settings, Block hangingSignBase, BlockSoundGroup sounds, BlockSoundGroup hangingSounds) {
		SignContainer container = new SignContainer(name, Material.WOOD, sounds, settings.maxCount(16), hangingSignBase, hangingSounds).dropSelf();
		ModDatagen.Cache.Models.SIGN.add(new Pair<>(container, planks));
		ModDatagen.Cache.Tags.Register(BlockTags.STANDING_SIGNS, container.asBlock());
		ModDatagen.Cache.Tags.Register(BlockTags.WALL_SIGNS, container.getWallBlock());
		ModDatagen.Cache.Tags.Register(ItemTags.SIGNS, container.asItem());
		return container;
	}

	public static Block.Settings HangingSignSettings(Block base) { return HangingSignSettings(base, ModBlockSoundGroups.HANGING_SIGN); }
	public static Block.Settings HangingSignSettings(Block base, BlockSoundGroup sounds) {
		return Block.Settings.of(Material.WOOD, base.getDefaultMapColor()).noCollision().strength(1.0f).sounds(sounds);
	}
	public static Item.Settings SignItemSettings() { return ItemSettings().maxCount(16); }
	public static Item.Settings SignItemSettings(ItemGroup group) { return ItemSettings(group).maxCount(16); }
	public static WallBlockContainer MakeHangingSign(SignType type, Block base) { return MakeHangingSign(type, base, ItemSettings()); }
	public static WallBlockContainer MakeHangingSign(SignType type, Block base, Item.Settings itemSettings) { return MakeHangingSign(type, base, HangingSignSettings(base), itemSettings); }
	public static WallBlockContainer MakeHangingSign(SignType type, Block base, Item.Settings itemSettings, BlockSoundGroup sounds) { return MakeHangingSign(type, base, HangingSignSettings(base, sounds), itemSettings); }
	public static WallBlockContainer MakeHangingSign(SignType type, Block base, Block.Settings settings, Item.Settings itemSettings) {
		HangingSignBlock hanging = new HangingSignBlock(settings, type);
		WallHangingSignBlock wall = new WallHangingSignBlock(settings, type);
		HangingSignItem item = new HangingSignItem(hanging, wall, itemSettings);
		WallBlockContainer container = new WallBlockContainer(hanging, wall, item).dropSelf();
		ModDatagen.Cache.Models.HANGING_SIGN.add(new Pair<>(container, base));
		ModDatagen.Cache.Tags.Register(ModBlockTags.CEILING_HANGING_SIGNS, container.asBlock());
		ModDatagen.Cache.Tags.Register(ModBlockTags.WALL_HANGING_SIGNS, container.getWallBlock());
		return container;
	}

	public static Block.Settings PlanksSettings(MapColor mapColor) { return PlanksSettings(mapColor, BlockSoundGroup.WOOD); }
	public static Block.Settings PlanksSettings(MapColor mapColor, BlockSoundGroup soundGroup) {
		return Block.Settings.of(Material.WOOD, mapColor).strength(2.0F, 3.0F).sounds(soundGroup);
	}
	public static BlockContainer MakePlanks(MapColor mapColor) { return MakePlanks(mapColor, ItemSettings()); }
	public static BlockContainer MakePlanks(MapColor mapColor, Item.Settings settings) { return MakePlanks(PlanksSettings(mapColor), settings); }
	public static BlockContainer MakePlanks(MapColor mapColor, BlockSoundGroup sounds) { return MakePlanks(mapColor, sounds, ItemSettings()); }
	public static BlockContainer MakePlanks(MapColor mapColor, BlockSoundGroup sounds, Item.Settings settings) { return MakePlanks(PlanksSettings(mapColor, sounds), settings); }
	public static BlockContainer MakePlanks(Block.Settings blockSettings, Item.Settings itemSettings) {
		return new BlockContainer(new Block(blockSettings), itemSettings).dropSelf()
				.blockTag(BlockTags.PLANKS).itemTag(ItemTags.PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	}

	public static Block.Settings LogSettings(MapColor color) { return LogSettings(color, BlockSoundGroup.WOOD); }
	public static Block.Settings LogSettings(MapColor color, BlockSoundGroup soundGroup) { return logSettings(Block.Settings.of(Material.WOOD, color), soundGroup); }
	public static Block.Settings LogSettings(MapColor top, MapColor side, BlockSoundGroup soundGroup) { return logSettings(Block.Settings.of(Material.WOOD, state -> state.get(PillarBlock.AXIS) == Direction.Axis.Y ? top : side), soundGroup); }
	private static Block.Settings logSettings(Block.Settings settings, BlockSoundGroup soundGroup) { return settings.strength(2.0f).sounds(soundGroup); }

	public static BlockContainer MakeLog(MapColor color) { return MakeLog(color,ItemSettings()); }
	public static BlockContainer MakeLog(MapColor color, Item.Settings settings) { return MakeLog(color, BlockSoundGroup.WOOD, settings); }
	public static BlockContainer MakeLog(MapColor color, BlockSoundGroup soundGroup) { return MakeLog(color, soundGroup, ItemSettings()); }
	public static BlockContainer MakeLog(MapColor color, BlockSoundGroup soundGroup, Item.Settings settings) { return makeLog(LogSettings(color, soundGroup), settings); }
	public static BlockContainer MakeLog(MapColor top, MapColor side) { return MakeLog(top, side, ItemSettings()); }
	public static BlockContainer MakeLog(MapColor top, MapColor side, Item.Settings settings) { return MakeLog(top, side, BlockSoundGroup.WOOD, settings); }
	public static BlockContainer MakeLog(MapColor top, MapColor side, BlockSoundGroup soundGroup) { return MakeLog(top, side, soundGroup, ItemSettings()); }
	public static BlockContainer MakeLog(MapColor top, MapColor side, BlockSoundGroup soundGroup, Item.Settings settings) { return makeLog(LogSettings(top, side, soundGroup), settings); }
	private static BlockContainer makeLog(Block.Settings blockSettings, Item.Settings settings) {
		return new BlockContainer(new ModPillarBlock(blockSettings), settings).dropSelf();
	}

	public static BlockContainer MakeLogSlab(BlockConvertible log) { return MakeLogSlab(log.asBlock()); }
	public static BlockContainer MakeLogSlab(Block log) { return MakeLogSlab(log, LogSettings(log.getDefaultMapColor())); }
	public static BlockContainer MakeLogSlab(BlockConvertible log, BlockSoundGroup sounds) { return MakeLogSlab(log.asBlock(), sounds); }
	public static BlockContainer MakeLogSlab(Block log, BlockSoundGroup sounds) { return MakeLogSlab(log, LogSettings(log.getDefaultMapColor(), sounds)); }
	public static BlockContainer MakeLogSlab(BlockConvertible log, Block.Settings settings) { return MakeLogSlab(log.asBlock(), settings); }
	public static BlockContainer MakeLogSlab(Block log, Block.Settings settings) {
		BlockContainer container = MakeSlab(settings).blockTag(ModBlockTags.LOG_SLABS).itemTag(ModItemTags.LOG_SLABS);
		ModDatagen.Cache.Models.LOG_SLAB.add(new Pair<>(container, log));
		return container.blockTag(BlockTags.AXE_MINEABLE);
	}

	public static BlockContainer MakeBarkSlab(BlockConvertible wood, BlockConvertible log) { return MakeBarkSlab(wood, log.asBlock()); }
	public static BlockContainer MakeBarkSlab(BlockConvertible wood, Block log) { return MakeBarkSlab(wood.asBlock(), log); }
	public static BlockContainer MakeBarkSlab(Block wood, Block log) {
		BlockContainer container = BuildSlab(new HorizontalFacingSlabBlock(wood)).blockTag(ModBlockTags.LOG_SLABS).itemTag(ModItemTags.LOG_SLABS);
		ModDatagen.Cache.Models.BARK_SLAB.add(new Pair<>(container, log));
		return container.blockTag(BlockTags.AXE_MINEABLE);
	}

	public static Block.Settings GourdSettings(MapColor color) {
		return Block.Settings.of(Material.GOURD, color).strength(1.0F).sounds(BlockSoundGroup.WOOD).allowsSpawning(ModFactory::always);
	}
	public static BlockContainer MakeCarvedGourd(MapColor color) { return BuildBlock(new CarvedGourdBlock(GourdSettings(color))); }
	public static BlockContainer MakeGourdLantern(MapColor color, ToIntFunction<BlockState> luminance) {
		return BuildBlock(new CarvedGourdBlock(GourdSettings(color).luminance(luminance)));
	}

	public static BlockContainer MakeBlock(Block.Settings blockSettings) { return MakeBlock(blockSettings, ItemSettings()); }
	public static BlockContainer MakeBlock(Block.Settings blockSettings, Item.Settings itemSettings) { return BuildBlock(new Block(blockSettings), itemSettings); }
	public static BlockContainer MakeBlock(BlockConvertible base) { return MakeBlock(base, ItemSettings()); }
	public static BlockContainer MakeBlock(BlockConvertible base, Item.Settings settings) { return BuildBlock(new Block(Block.Settings.copy(base.asBlock())), settings); }
	public static BlockContainer MakeBlock(Block base) { return MakeBlock(base, ItemSettings()); }
	public static BlockContainer MakeBlock(Block base, Item.Settings settings) { return BuildBlock(new Block(Block.Settings.copy(base)), settings); }
	public static BlockContainer BuildBlock(Block block) { return BuildBlock(block, ItemSettings()); }
	public static BlockContainer BuildBlock(Block block, Item.Settings settings) { return BuildBlock(block, null, settings); }
	public static BlockContainer BuildBlock(Block block, DropTable dropTable) { return BuildBlock(block, dropTable, ItemSettings()); }
	public static BlockContainer BuildBlock(Block block, DropTable dropTable, Item.Settings settings) {
		return new BlockContainer(block, settings).drops(dropTable);
	}

	public static BlockContainer MakeSlab(Block.Settings settings) { return BuildSlab(new ModSlabBlock(settings)); }
	public static BlockContainer MakeSlab(BlockConvertible base) { return MakeSlab(base.asBlock()); }
	public static BlockContainer MakeSlab(BlockConvertible base, Item.Settings settings) { return MakeSlab(base.asBlock(), settings); }
	public static BlockContainer MakeSlab(Block base) { return MakeSlab(base, ItemSettings()); }
	public static BlockContainer MakeSlab(Block base, Item.Settings settings) { return BuildSlab(new ModSlabBlock(base), settings); }
	public static BlockContainer BuildSlab(Function<BlockConvertible, Block> func, BlockConvertible base) { return BuildSlab(func.apply(base)); }
	public static BlockContainer BuildSlab(Function<Block, Block> func, Block base) { return BuildSlab(func.apply(base)); }
	public static BlockContainer BuildSlab(Block slab) { return BuildSlab(slab, ItemSettings()); }
	public static BlockContainer BuildSlab(Block slab, Item.Settings settings) { return BuildSlab(slab, null, settings); }
	public static BlockContainer BuildSlab(Block slab, DropTable dropTable) { return BuildSlab(slab, dropTable, ItemSettings()); }
	public static BlockContainer BuildSlab(Block slab, DropTable dropTable, Item.Settings settings) {
		BlockContainer container = new BlockContainer(slab, settings).drops(dropTable);
		return container.blockTag(BlockTags.SLABS).itemTag(ItemTags.SLABS);
	}
	private static BlockContainer WoodenSlabHelper(BlockContainer container) {
		return container.blockTag(BlockTags.WOODEN_SLABS).itemTag(ItemTags.WOODEN_SLABS);
	}
	public static BlockContainer MakeWoodSlab(BlockConvertible base) { return WoodenSlabHelper(MakeSlab(base)); }
	public static BlockContainer MakeWoodSlab(BlockConvertible base, Item.Settings settings) { return WoodenSlabHelper(MakeSlab(base, settings)); }
	public static BlockContainer MakeWoodSlab(Block base) { return WoodenSlabHelper(MakeSlab(base)); }
	public static BlockContainer MakeWoodSlab(Block base, Item.Settings settings) { return WoodenSlabHelper(MakeSlab(base, settings)); }

	public static BlockContainer MakeOxidizableSlab(BlockConvertible base, Oxidizable.OxidizationLevel level) { return MakeOxidizableSlab(base.asBlock(), level); }
	public static BlockContainer MakeOxidizableSlab(Block base, Oxidizable.OxidizationLevel level) {
		return new BlockContainer(new OxidizableSlabBlock(level, Block.Settings.copy(base))).dropSlabs();
	}

	public static BlockContainer MakeStairs(BlockConvertible base) { return MakeStairs(base.asBlock()); }
	public static BlockContainer MakeStairs(BlockConvertible base, Item.Settings settings) { return MakeStairs(base.asBlock(), settings); }
	public static BlockContainer MakeStairs(Block base) { return MakeStairs(base, ItemSettings()); }
	public static BlockContainer MakeStairs(Block base, Item.Settings settings) { return BuildStairs(new ModStairsBlock(base), settings); }
	public static BlockContainer BuildStairs(Function<BlockConvertible, Block> func, BlockConvertible base) { return BuildStairs(func.apply(base)); }
	public static BlockContainer BuildStairs(Function<Block, Block> func, Block base) { return BuildStairs(func.apply(base)); }
	public static BlockContainer BuildStairs(Block stairs) { return BuildStairs(stairs, ItemSettings()); }
	public static BlockContainer BuildStairs(Block stairs, Item.Settings settings) { return BuildStairs(stairs, null, settings); }
	public static BlockContainer BuildStairs(Block stairs, DropTable dropTable) { return BuildStairs(stairs, dropTable, ItemSettings()); }
	public static BlockContainer BuildStairs(Block stairs, DropTable dropTable, Item.Settings settings) {
		return new BlockContainer(stairs, settings).drops(dropTable)
				.blockTag(BlockTags.STAIRS).itemTag(ItemTags.STAIRS);
	}

	public static BlockContainer MakeWoodStairs(BlockConvertible base) { return MakeWoodStairs(base.asBlock()); }
	public static BlockContainer MakeWoodStairs(BlockConvertible base, Item.Settings settings) { return MakeWoodStairs(base.asBlock(), settings); }
	public static BlockContainer MakeWoodStairs(Block base) { return MakeWoodStairs(base, ItemSettings()); }
	public static BlockContainer MakeWoodStairs(Block base, Item.Settings settings) {
		return  new BlockContainer(new ModStairsBlock(base), settings).dropSelf()
				.blockTag(BlockTags.WOODEN_STAIRS).itemTag(ItemTags.WOODEN_STAIRS).blockTag(BlockTags.AXE_MINEABLE);
	}

	public static BlockContainer MakeOxidizableStairs(BlockConvertible base, Oxidizable.OxidizationLevel level) { return MakeOxidizableStairs(base.asBlock(), level); }
	public static BlockContainer MakeOxidizableStairs(Block base, Oxidizable.OxidizationLevel level) {
		BlockContainer container = new BlockContainer(new OxidizableStairsBlock(level, base.getDefaultState(), Block.Settings.copy(base))).dropSelf();
		return container.blockTag(BlockTags.STAIRS).itemTag(ItemTags.STAIRS);
	}
	public static BlockContainer MakeWaxedStairs(BlockConvertible base) { return MakeWaxedStairs(base.asBlock()); }
	public static BlockContainer MakeWaxedStairs(Block base) {
		return BuildBlock(new ModStairsBlock(base)).blockTag(BlockTags.STAIRS).itemTag(ItemTags.STAIRS);
	}

	public static BlockContainer MakeWall(BlockConvertible base) { return MakeWall(base.asBlock()); }
	public static BlockContainer MakeWall(BlockConvertible base, Item.Settings settings) { return MakeWall(base.asBlock(), settings); }
	public static BlockContainer MakeWall(Block base) { return MakeWall(base, ItemSettings()); }
	public static BlockContainer MakeWall(Block base, Item.Settings settings) { return BuildWall(new ModWallBlock(base), settings); }
	public static BlockContainer BuildWall(Function<BlockConvertible, Block> func, BlockConvertible base) { return BuildWall(func.apply(base)); }
	public static BlockContainer BuildWall(Function<Block, Block> func, Block base) { return BuildWall(func.apply(base)); }
	public static BlockContainer BuildWall(Block wall) { return BuildWall(wall, ItemSettings()); }
	public static BlockContainer BuildWall(Block wall, Item.Settings settings) { return BuildWall(wall, null, settings); }
	public static BlockContainer BuildWall(Block wall, DropTable dropTable) { return BuildWall(wall, dropTable, ItemSettings()); }
	public static BlockContainer BuildWall(Block wall, DropTable dropTable, Item.Settings settings) {
		BlockContainer container = new BlockContainer(wall, settings).drops(dropTable);
		return container.blockTag(BlockTags.WALLS).itemTag(ItemTags.WALLS);
	}

	public static BlockContainer MakeOxidizableWall(BlockConvertible base, Oxidizable.OxidizationLevel level) { return MakeOxidizableWall(base.asBlock(), level); }
	public static BlockContainer MakeOxidizableWall(Block base, Oxidizable.OxidizationLevel level) {
		BlockContainer container = new BlockContainer(new OxidizableWallBlock(level, base)).dropSelf();
		return container.blockTag(BlockTags.WALLS).itemTag(ItemTags.WALLS);
	}
	public static BlockContainer MakeWaxedWall(BlockConvertible base) { return MakeWaxedWall(base.asBlock()); }
	public static BlockContainer MakeWaxedWall(Block base) {
		return BuildBlock(new ModWallBlock(base)).blockTag(BlockTags.STAIRS).itemTag(ItemTags.STAIRS);
	}

	public static BlockContainer MakeSandy(BlockConvertible block) { return MakeSandy(block.asBlock()); }
	public static BlockContainer MakeSandy(Block block) { return BuildBlock(new SandyBlock(block)); }

	private static BlockContainer MakeFenceCommon(Block base, Item.Settings settings) {
		return new BlockContainer(new ModFenceBlock(base), settings).dropSelf();
	}
	public static BlockContainer MakeFence(IBlockItemContainer base) { return MakeFence(base.asBlock()); }
	public static BlockContainer MakeFence(Block base) { return MakeFence(base, ItemSettings()); }
	public static BlockContainer MakeFence(IBlockItemContainer base, Item.Settings settings) { return MakeFence(base.asBlock(), settings); }
	public static BlockContainer MakeFence(Block base, Item.Settings settings) {
		return MakeFenceCommon(base, settings).blockTag(BlockTags.FENCES).itemTag(ItemTags.FENCES);
	}
	public static BlockContainer MakeWoodFence(IBlockItemContainer base) { return MakeWoodFence(base, ItemSettings()); }
	public static BlockContainer MakeWoodFence(IBlockItemContainer base, Item.Settings settings) {
		return MakeFenceCommon(base.asBlock(), settings).blockTag(BlockTags.WOODEN_FENCES).itemTag(ItemTags.WOODEN_FENCES);
	}

	public static BlockContainer MakeWoodFenceGate(IBlockItemContainer ingredient) { return MakeWoodFenceGate(ingredient, ItemSettings()); }
	public static BlockContainer MakeWoodFenceGate(IBlockItemContainer ingredient, Item.Settings settings) {
		return new BlockContainer(new FenceGateBlock(Block.Settings.copy(ingredient.asBlock())), settings).dropSelf();
	}
	public static BlockContainer MakeWoodFenceGate(IBlockItemContainer ingredient, SoundEvent opened, SoundEvent closed) { return MakeWoodFenceGate(ingredient, ItemSettings(), opened, closed); }
	public static BlockContainer MakeWoodFenceGate(IBlockItemContainer ingredient, Item.Settings settings, SoundEvent opened, SoundEvent closed) {
		return new BlockContainer(new ModFenceGateBlock(Block.Settings.copy(ingredient.asBlock()), opened, closed), settings).dropSelf();
	}

	public static Block.Settings MetalTrapdoorSettings(MapColor color, BlockSoundGroup soundGroup) { return Block.Settings.of(Material.METAL).requiresTool().mapColor(color).sounds(soundGroup).nonOpaque().allowsSpawning(ModFactory::never); }
	public static Block.Settings MetalTrapdoorSettings(MapColor color, BlockSoundGroup soundGroup, float strength) { return MetalTrapdoorSettings(color, soundGroup).strength(strength); }
	public static Block.Settings MetalTrapdoorSettings(MapColor color, BlockSoundGroup soundGroup, float strength, float resistance) { return MetalTrapdoorSettings(color, soundGroup).strength(strength, resistance); }

	public static BlockContainer MakeThinMetalTrapdoor(MapColor color, BlockSoundGroup soundGroup, float strength) {
		return BuildThinMetalTrapdoor(new ThinTrapdoorBlock(MetalTrapdoorSettings(color, soundGroup, strength), SoundEvents.BLOCK_IRON_TRAPDOOR_OPEN, SoundEvents.BLOCK_IRON_TRAPDOOR_CLOSE));
	}
	public static BlockContainer MakeThinMetalTrapdoor(MapColor color, BlockSoundGroup soundGroup, float strength, float resistance) {
		return BuildThinMetalTrapdoor(new ThinTrapdoorBlock(MetalTrapdoorSettings(color, soundGroup, strength, resistance), SoundEvents.BLOCK_IRON_TRAPDOOR_OPEN, SoundEvents.BLOCK_IRON_TRAPDOOR_CLOSE));
	}
	public static BlockContainer BuildThinMetalTrapdoor(ThinTrapdoorBlock block) {
		BlockContainer container = BuildBlock(block);
		ModDatagen.Cache.Models.THIN_TRAPDOOR.add(container);
		return BuildMetalTrapdoor(container);
	}
	public static BlockContainer MakeMetalTrapdoor(MapColor color, BlockSoundGroup soundGroup, float strength) {
		return BuildMetalTrapdoor(new ModTrapdoorBlock(MetalTrapdoorSettings(color, soundGroup, strength), SoundEvents.BLOCK_IRON_TRAPDOOR_OPEN, SoundEvents.BLOCK_IRON_TRAPDOOR_CLOSE));
	}
	public static BlockContainer MakeMetalTrapdoor(MapColor color, BlockSoundGroup soundGroup, float strength, float resistance) {
		return BuildMetalTrapdoor(new ModTrapdoorBlock(MetalTrapdoorSettings(color, soundGroup, strength, resistance), SoundEvents.BLOCK_IRON_TRAPDOOR_OPEN, SoundEvents.BLOCK_IRON_TRAPDOOR_CLOSE));
	}
	public static BlockContainer BuildMetalTrapdoor(ModTrapdoorBlock block) {
		BlockContainer container = BuildBlock(block);
		return BuildMetalTrapdoor(container);
	}
	private static BlockContainer BuildMetalTrapdoor(BlockContainer container) {
		container.blockTag(BlockTags.TRAPDOORS).itemTag(ItemTags.TRAPDOORS).blockTag(BlockTags.PICKAXE_MINEABLE);
		return container;
	}

	public static BlockContainer MakeOxidizableTrapdoor(Oxidizable.OxidizationLevel level, float strength) {
		return BuildThinMetalTrapdoor(new OxidizableTrapdoorBlock(level, MetalTrapdoorSettings(OxidationScale.getMapColor(level), BlockSoundGroup.COPPER, strength)));
	}
	public static BlockContainer MakeWaxedTrapdoor(MapColor color, BlockSoundGroup soundGroup, float strength) {
		return BuildMetalTrapdoor(BuildBlock(new ThinTrapdoorBlock(MetalTrapdoorSettings(color, soundGroup, strength), SoundEvents.BLOCK_IRON_TRAPDOOR_OPEN, SoundEvents.BLOCK_IRON_TRAPDOOR_CLOSE)));
	}

	public static Block.Settings WoodTrapdoorSettings(MapColor color, BlockSoundGroup soundGroup) { return Block.Settings.of(Material.WOOD, color).strength(3.0F).sounds(soundGroup).nonOpaque().allowsSpawning(ModFactory::noSpawning); }
	public static BlockContainer MakeWoodTrapdoor(IBlockItemContainer base) { return MakeWoodTrapdoor(base, ItemSettings()); }
	public static BlockContainer MakeWoodTrapdoor(IBlockItemContainer base, Item.Settings settings) {
		return BuildWoodTrapdoor(new ModTrapdoorBlock(WoodTrapdoorSettings(base.asBlock().getDefaultMapColor(), BlockSoundGroup.WOOD)), settings);
	}
	public static BlockContainer MakeWoodTrapdoor(IBlockItemContainer base, BlockSoundGroup soundGroup, SoundEvent opened, SoundEvent closed) { return MakeWoodTrapdoor(base, ItemSettings(), soundGroup, opened, closed); }
	public static BlockContainer MakeWoodTrapdoor(IBlockItemContainer base, Item.Settings settings, BlockSoundGroup soundGroup, SoundEvent opened, SoundEvent closed) {
		return BuildWoodTrapdoor(new ModTrapdoorBlock(WoodTrapdoorSettings(base.asBlock().getDefaultMapColor(), soundGroup), opened, closed), settings);
	}
	public static BlockContainer BuildWoodTrapdoor(ModTrapdoorBlock block, Item.Settings settings) {
		BlockContainer container = new BlockContainer(block, settings);
		return container.dropSelf().blockTag(BlockTags.WOODEN_TRAPDOORS).itemTag(ItemTags.WOODEN_TRAPDOORS);
	}

	public static Block.Settings GlassTrapdoorSettings(Block block) {
		return Block.Settings.copy(block).strength(3.0F).nonOpaque().allowsSpawning(ModFactory::noSpawning);
	}

	public static BlockContainer MakeWoodcutter(BlockConvertible base) { return MakeWoodcutter(base.asBlock()); }
	public static BlockContainer MakeWoodcutter(Block base) {
		BlockContainer container = BuildBlock(new WoodcutterBlock(Block.Settings.of(Material.WOOD, base.getDefaultMapColor()).sounds(base.getSoundGroup(base.getDefaultState())).strength(3.5F)))
				.blockTag(BlockTags.AXE_MINEABLE);
		ModDatagen.Cache.Models.WOODCUTTER.add(new Pair<>(container, base));
		return container;
	}

	public static Block.Settings BarrelSettings(MapColor color, BlockSoundGroup soundGroup) {
		return Block.Settings.of(Material.WOOD).mapColor(color).strength(2.5f).sounds(soundGroup);
	}
	public static BlockContainer MakeBarrel(MapColor color) { return MakeBarrel(color, BlockSoundGroup.WOOD); }
	public static BlockContainer MakeBarrel(MapColor color, BlockSoundGroup soundGroup) {
		BlockContainer container = new BlockContainer(new ModBarrelBlock(BarrelSettings(color, soundGroup)))
				.drops(BlockLootTableGenerator::nameableContainerDrops)
				.blockTag(BlockTags.AXE_MINEABLE).blockTag(ModBlockTags.BARRELS);
		ModDatagen.Cache.Models.BARREL.add(container);
		return container;
	}

	public static Block.Settings LeafBlockSettings() { return LeafBlockSettings(BlockSoundGroup.GRASS); }
	public static Block.Settings LeafBlockSettings(BlockSoundGroup sounds) {
		return Block.Settings.of(Material.LEAVES).strength(0.2F).ticksRandomly().sounds(sounds).nonOpaque().allowsSpawning(ModFactory::canSpawnOnLeaves).suffocates(ModFactory::never).blockVision(ModFactory::never);
	}
	public static BlockContainer MakeLeaves(Block.Settings settings) {
		return BuildLeaves(new ModLeavesBlock(settings), ItemSettings());
	}
	public static BlockContainer BuildLeaves(Block block, Item.Settings settings) {
		BlockContainer container = new BlockContainer(block, settings)
				.flammable(30, 60).compostable(0.3f)
				.blockTag(BlockTags.LEAVES).itemTag(ItemTags.LEAVES).blockTag(BlockTags.HOE_MINEABLE);
		ModDatagen.Cache.Models.LEAVES.add(container);
		return container;
	}

	public static BlockContainer MakeLectern(BlockConvertible base) { return MakeLectern(base.asBlock()); }
	public static BlockContainer MakeLectern(Block base) { return MakeLectern(base, base.getSoundGroup(base.getDefaultState())); }
	public static BlockContainer MakeLectern(BlockConvertible base, BlockSoundGroup sounds) { return MakeLectern(base.asBlock(), sounds); }
	public static BlockContainer MakeLectern(Block base, BlockSoundGroup sounds) {
		BlockContainer container = new BlockContainer(new ModLecternBlock(AbstractBlock.Settings.of(Material.WOOD).mapColor(base.getDefaultMapColor()).strength(2.5f).sounds(sounds))).dropSelf();
		ModDatagen.Cache.Models.LECTERN.add(new Pair<>(container, base));
		return container.blockTag(ModBlockTags.LECTERNS);
	}
	
	public static Block MakeMooblossomFlower(BlockConvertible flower) { return MakeMooblossomFlower(flower.asBlock()); }
	public static Block MakeMooblossomFlower(Block flower) { return MakeMooblossomFlower((FlowerBlock)flower); }
	public static Block MakeMooblossomFlower(FlowerBlock flower) { return new FlowerBlock(flower.getEffectInStew(), flower.getEffectInStewDuration(), FlowerSettings()); }

	public static Item.Settings FlowerItemSettings() { return ItemSettings(ModBase.FLOWER_GROUP); }
	public static Block.Settings FlowerSettings() {
		return Block.Settings.of(Material.PLANT).noCollision().breakInstantly().nonOpaque().sounds(BlockSoundGroup.GRASS);
	}
	public static FlowerContainer MakeFlower(StatusEffect effect, int effectDuration) { return MakeFlower(effect, effectDuration, FlowerItemSettings()); }
	public static FlowerContainer MakeFlower(StatusEffect effect, int effectDuration, Item.Settings settings) {
		return new FlowerContainer(effect, effectDuration, FlowerSettings(), settings).flammable(60, 100).compostable(0.65f).dropSelf();
	}
	public static Block.Settings TallFlowerSettings() {
		return Block.Settings.of(Material.REPLACEABLE_PLANT).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS);
	}
	public static TallBlockContainer MakeTallFlower() {
		return new TallBlockContainer(new TallFlowerBlock(TallFlowerSettings()), FlowerItemSettings()).flammable(60, 100).compostable(0.65f).dropSelf();
	}
	public static TallBlockContainer MakeCuttableFlower(Supplier<FlowerBlock> shortBlock) {
		return MakeCuttableFlower(shortBlock, null);
	}
	public static TallBlockContainer MakeCuttableFlower(Supplier<FlowerBlock> shortBlock, Function<World, ItemStack> alsoDrop) {
		return MakeCuttableFlower(TallFlowerSettings(), shortBlock, alsoDrop);
	}
	public static TallBlockContainer MakeCuttableFlower(Block.Settings settings, Supplier<FlowerBlock> shortBlock, Function<World, ItemStack> alsoDrop) {
		return new TallBlockContainer(new CuttableFlowerBlock(settings, shortBlock, alsoDrop), FlowerItemSettings()).flammable(60, 100).compostable(0.65f).dropSelf();
	}

	public static Block.Settings FlowerPartSeedBlockSettings() { return FlowerSettings().ticksRandomly(); }
	public static FlowerPartContainer MakeFlowerParts(FlowerContainer flower) { return MakeFlowerParts(flower.asBlock()); }
	public static FlowerPartContainer MakeFlowerParts(TallBlockContainer flower) { return MakeFlowerParts(flower.asBlock()); }
	public static FlowerPartContainer MakeFlowerParts(Block flower) { return MakeFlowerParts(flower, FlowerItemSettings()); }
	public static FlowerPartContainer MakeFlowerParts(Block flower, Item.Settings settings) { return MakeFlowerParts(flower, FlowerPartSeedBlockSettings(), settings); }
	public static FlowerPartContainer MakeFlowerParts(Block flower, Block.Settings seedBlockSettings, Item.Settings itemSettings) {
		FlowerPartContainer container = new FlowerPartContainer(flower, seedBlockSettings, itemSettings).compostable(0.3f, 0.1f).dropSelf();
		ModDatagen.Cache.Tags.Register(ModItemTags.SEEDS, container.asItem());
		return container;
	}

	public static BlockContainer MakeSculkTurf(Block block, Item item) {
		BlockContainer turf = BuildBlock(new SculkTurfBlock(block), DropTable.SilkTouchOrElse(item));
		ModDatagen.Cache.Tags.Register(ModBlockTags.SCULK_TURFS, turf);
		return turf;
	}
}
