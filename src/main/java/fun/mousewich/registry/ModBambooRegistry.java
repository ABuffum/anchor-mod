package fun.mousewich.registry;

import fun.mousewich.block.DriedBambooBlock;
import fun.mousewich.block.RowBlock;
import fun.mousewich.container.*;
import fun.mousewich.gen.data.tag.ModBlockTags;
import fun.mousewich.gen.data.tag.ModItemTags;
import fun.mousewich.sound.ModBlockSoundGroups;
import fun.mousewich.sound.ModSoundEvents;
import fun.mousewich.util.StrippedBlockUtil;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ToolMaterials;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.ItemTags;

import java.util.List;

import static fun.mousewich.ModBase.EN_US;
import static fun.mousewich.ModFactory.*;
import static fun.mousewich.registry.ModRegistry.Register;

public class ModBambooRegistry {
	//<editor-fold desc="Bamboo">
	public static final BlockContainer BAMBOO_BLOCK = MakeLog(MapColor.YELLOW, MapColor.DARK_GREEN, ModBlockSoundGroups.BAMBOO_WOOD, ItemSettings(ItemGroup.BUILDING_BLOCKS)).flammable(5, 5).fuel(300).blockTag(ModBlockTags.BAMBOO_BLOCKS).itemTag(ModItemTags.BAMBOO_BLOCKS, ModItemTags.BAMBOO_LOGS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer STRIPPED_BAMBOO_BLOCK = MakeLog(MapColor.YELLOW, ModBlockSoundGroups.BAMBOO_WOOD, ItemSettings(ItemGroup.BUILDING_BLOCKS)).flammable(5, 5).fuel(300).blockTag(ModBlockTags.BAMBOO_BLOCKS).itemTag(ModItemTags.BAMBOO_BLOCKS, ModItemTags.BAMBOO_LOGS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer BAMBOO_PLANKS = MakePlanks(MapColor.YELLOW, ModBlockSoundGroups.BAMBOO_WOOD, ItemSettings(ItemGroup.BUILDING_BLOCKS)).flammable(5, 20).fuel(300).cubeAllModel().blockTag(BlockTags.PLANKS).itemTag(ItemTags.PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer BAMBOO_STAIRS = MakeWoodStairs(BAMBOO_PLANKS, ItemSettings(ItemGroup.BUILDING_BLOCKS)).flammable(5, 20).fuel(300).stairsModel(BAMBOO_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer BAMBOO_SLAB = MakeWoodSlab(BAMBOO_PLANKS, ItemSettings(ItemGroup.BUILDING_BLOCKS)).flammable(5, 20).fuel(150).slabModel(BAMBOO_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer BAMBOO_FENCE = MakeWoodFence(BAMBOO_PLANKS, ItemSettings(ItemGroup.DECORATIONS)).flammable(5, 20).fuel(300).bambooFenceModel().blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer BAMBOO_FENCE_GATE = MakeWoodFenceGate(BAMBOO_PLANKS, ItemSettings(ItemGroup.REDSTONE), ModSoundEvents.BLOCK_BAMBOO_WOOD_FENCE_GATE_OPEN, ModSoundEvents.BLOCK_BAMBOO_WOOD_FENCE_GATE_CLOSE).flammable(5, 20).fuel(300).bambooFenceGateModel().blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer BAMBOO_DOOR = MakeWoodDoor(BAMBOO_PLANKS, ItemSettings(ItemGroup.REDSTONE), ModBlockSoundGroups.BAMBOO_WOOD, ModSoundEvents.BLOCK_BAMBOO_WOOD_DOOR_CLOSE, ModSoundEvents.BLOCK_BAMBOO_WOOD_DOOR_OPEN).fuel(200).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer BAMBOO_TRAPDOOR = MakeWoodTrapdoor(BAMBOO_PLANKS, ItemSettings(ItemGroup.REDSTONE), ModBlockSoundGroups.BAMBOO_WOOD, ModSoundEvents.BLOCK_BAMBOO_WOOD_TRAPDOOR_CLOSE, ModSoundEvents.BLOCK_BAMBOO_WOOD_TRAPDOOR_OPEN).fuel(300).trapdoorModel(true).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer BAMBOO_PRESSURE_PLATE = MakeWoodPressurePlate(BAMBOO_PLANKS, ItemSettings(ItemGroup.REDSTONE), ModBlockSoundGroups.BAMBOO_WOOD, ModSoundEvents.BLOCK_BAMBOO_WOOD_PRESSURE_PLATE_CLICK_ON, ModSoundEvents.BLOCK_BAMBOO_WOOD_PRESSURE_PLATE_CLICK_OFF).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer BAMBOO_BUTTON = MakeWoodButton(ItemSettings(ItemGroup.REDSTONE), ModBlockSoundGroups.BAMBOO_WOOD, ModSoundEvents.BLOCK_BAMBOO_WOOD_BUTTON_CLICK_ON, ModSoundEvents.BLOCK_BAMBOO_WOOD_BUTTON_CLICK_OFF).fuel(100).buttonModel(BAMBOO_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final SignContainer BAMBOO_SIGN = MakeSign("minecraft:bamboo", BAMBOO_PLANKS, SignItemSettings(ItemGroup.DECORATIONS), STRIPPED_BAMBOO_BLOCK, ModBlockSoundGroups.BAMBOO_WOOD).fuel(200).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer BAMBOO_IRON_HANGING_SIGN = MakeHangingSign(MakeSignType("bamboo_iron"), STRIPPED_BAMBOO_BLOCK, ModBlockSoundGroups.BAMBOO_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer BAMBOO_GOLD_HANGING_SIGN = MakeHangingSign(MakeSignType("bamboo_gold"), STRIPPED_BAMBOO_BLOCK, ModBlockSoundGroups.BAMBOO_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer BAMBOO_COPPER_HANGING_SIGN = MakeHangingSign(MakeSignType("bamboo_copper"), STRIPPED_BAMBOO_BLOCK, ModBlockSoundGroups.BAMBOO_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer BAMBOO_EXPOSED_COPPER_HANGING_SIGN = MakeHangingSign(MakeSignType("bamboo_exposed_copper"), STRIPPED_BAMBOO_BLOCK, ModBlockSoundGroups.BAMBOO_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer BAMBOO_WEATHERED_COPPER_HANGING_SIGN = MakeHangingSign(MakeSignType("bamboo_weathered_copper"), STRIPPED_BAMBOO_BLOCK, ModBlockSoundGroups.BAMBOO_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer BAMBOO_OXIDIZED_COPPER_HANGING_SIGN = MakeHangingSign(MakeSignType("bamboo_oxidized_copper"), STRIPPED_BAMBOO_BLOCK, ModBlockSoundGroups.BAMBOO_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer BAMBOO_NETHERITE_HANGING_SIGN = MakeHangingSign(MakeSignType("bamboo_netherite"), STRIPPED_BAMBOO_BLOCK, ModBlockSoundGroups.BAMBOO_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final BoatContainer BAMBOO_RAFT = MakeBoat("minecraft:bamboo", BAMBOO_PLANKS, BoatSettings(ItemGroup.TRANSPORTATION)).fuel(1200);
	//Mosaic
	public static final BlockContainer BAMBOO_MOSAIC = MakeBlock(BAMBOO_PLANKS, ItemSettings(ItemGroup.BUILDING_BLOCKS)).flammable(5, 20).fuel(300).cubeAllModel().blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer BAMBOO_MOSAIC_STAIRS = MakeWoodStairs(BAMBOO_MOSAIC, ItemSettings(ItemGroup.BUILDING_BLOCKS)).flammable(5, 20).fuel(300).stairsModel(BAMBOO_MOSAIC).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer BAMBOO_MOSAIC_SLAB = MakeSlab(BAMBOO_MOSAIC, ItemSettings(ItemGroup.BUILDING_BLOCKS)).flammable(5, 20).fuel(150).slabModel(BAMBOO_MOSAIC).blockTag(BlockTags.AXE_MINEABLE);
	//Extended
	public static final TorchContainer BAMBOO_TORCH = MakeTorch(BlockSoundGroup.BAMBOO).torchModel();
	public static final TorchContainer BAMBOO_SOUL_TORCH = MakeSoulTorch(BlockSoundGroup.BAMBOO).torchModel(BAMBOO_TORCH);
	public static final TorchContainer BAMBOO_ENDER_TORCH = MakeEnderTorch(BlockSoundGroup.BAMBOO).torchModel(BAMBOO_TORCH);
	public static final TorchContainer UNDERWATER_BAMBOO_TORCH = MakeUnderwaterTorch(BlockSoundGroup.BAMBOO).torchModel(BAMBOO_TORCH);
	public static final BlockContainer BAMBOO_BEEHIVE = MakeBeehive(MapColor.YELLOW, ModBlockSoundGroups.BAMBOO_WOOD).flammable(5, 20).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer BAMBOO_BOOKSHELF = MakeBookshelf(BAMBOO_PLANKS, ModBlockSoundGroups.BAMBOO_WOOD).flammable(30, 20).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer BAMBOO_CHISELED_BOOKSHELF = MakeChiseledBookshelf(MapColor.YELLOW).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer BAMBOO_CRAFTING_TABLE = MakeCraftingTable(BAMBOO_PLANKS, ModBlockSoundGroups.BAMBOO_WOOD).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer BAMBOO_LADDER = MakeLadder(BlockSoundGroup.BAMBOO).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer BAMBOO_WOODCUTTER = MakeWoodcutter(BAMBOO_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer BAMBOO_BARREL = MakeBarrel(MapColor.YELLOW, ModBlockSoundGroups.BAMBOO_WOOD).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer BAMBOO_LECTERN = MakeLectern(BAMBOO_PLANKS, ModBlockSoundGroups.BAMBOO_WOOD).flammable(30, 20).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer BAMBOO_POWDER_KEG = MakePowderKeg(BAMBOO_BARREL).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer BAMBOO_BLOCK_SLAB = MakeLogSlab(BAMBOO_BLOCK, ModBlockSoundGroups.BAMBOO_WOOD).flammable(5, 5).fuel(150).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer STRIPPED_BAMBOO_BLOCK_SLAB = MakeLogSlab(STRIPPED_BAMBOO_BLOCK, ModBlockSoundGroups.BAMBOO_WOOD).flammable(5, 5).fuel(150).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer BAMBOO_ROW = BuildBlock(new RowBlock(Block.Settings.of(Material.WOOD, MapColor.DARK_GREEN).strength(1.0F).sounds(BlockSoundGroup.BAMBOO))).blockTag(ModBlockTags.ROWS).itemTag(ModItemTags.ROWS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer BAMBOO_LOG = MakeLog(MapColor.YELLOW, MapColor.DARK_GREEN, ModBlockSoundGroups.BAMBOO_WOOD).flammable(5, 5).fuel(300).itemTag(ModItemTags.BAMBOO_LOGS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer BAMBOO_LOG_SLAB = MakeLogSlab(BAMBOO_LOG, ModBlockSoundGroups.BAMBOO_WOOD).flammable(5, 5).fuel(150).itemTag(ModItemTags.CHARRABLE_LOG_SLABS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer STRIPPED_BAMBOO_LOG = MakeLog(MapColor.YELLOW, ModBlockSoundGroups.BAMBOO_WOOD).flammable(5, 5).fuel(300).itemTag(ModItemTags.BAMBOO_LOGS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer STRIPPED_BAMBOO_LOG_SLAB = MakeLogSlab(STRIPPED_BAMBOO_LOG, ModBlockSoundGroups.BAMBOO_WOOD).flammable(5, 5).fuel(150).itemTag(ModItemTags.CHARRABLE_STRIPPED_LOG_SLABS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer BAMBOO_WOOD = MakeWood(MapColor.DARK_GREEN, ModBlockSoundGroups.BAMBOO_WOOD).flammable(5, 5).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer BAMBOO_WOOD_SLAB = MakeBarkSlab(BAMBOO_WOOD, BAMBOO_LOG).flammable(5, 5).fuel(150).itemTag(ModItemTags.CHARRABLE_WOOD_SLABS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer STRIPPED_BAMBOO_WOOD = MakeWood(MapColor.YELLOW, ModBlockSoundGroups.BAMBOO_WOOD).flammable(5, 5).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer STRIPPED_BAMBOO_WOOD_SLAB = MakeBarkSlab(STRIPPED_BAMBOO_WOOD, STRIPPED_BAMBOO_LOG).flammable(5, 5).fuel(150).itemTag(ModItemTags.CHARRABLE_STRIPPED_WOOD_SLABS).blockTag(BlockTags.AXE_MINEABLE);
	//Misc
	public static final Item BAMBOO_HAMMER = MakeHammer(ToolMaterials.WOOD, 7, -3.2F);
	//Campfires
	public static final BlockContainer BAMBOO_CAMPFIRE = MakeCampfire(MapColor.DARK_GREEN, ModBlockSoundGroups.BAMBOO_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer BAMBOO_SOUL_CAMPFIRE = MakeSoulCampfire(BAMBOO_CAMPFIRE, ModBlockSoundGroups.BAMBOO_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer BAMBOO_ENDER_CAMPFIRE = MakeEnderCampfire(BAMBOO_CAMPFIRE, ModBlockSoundGroups.BAMBOO_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	//</editor-fold>
	//<editor-fold desc="Bamboo (Dried)">
	public static final PottedBlockContainer DRIED_BAMBOO = new PottedBlockContainer(new DriedBambooBlock(Block.Settings.copy(Blocks.BAMBOO).mapColor(MapColor.YELLOW))).flammable(60, 60).fuel(50).dropSelf().blockTag(BlockTags.BAMBOO_PLANTABLE_ON).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer DRIED_BAMBOO_BLOCK = MakeLog(MapColor.PALE_YELLOW, MapColor.DIRT_BROWN, ModBlockSoundGroups.BAMBOO_WOOD).flammable(5, 5).fuel(300).blockTag(ModBlockTags.DRIED_BAMBOO_BLOCKS).itemTag(ModItemTags.DRIED_BAMBOO_BLOCKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer STRIPPED_DRIED_BAMBOO_BLOCK = MakeLog(MapColor.PALE_YELLOW, ModBlockSoundGroups.BAMBOO_WOOD).flammable(5, 5).fuel(300).blockTag(ModBlockTags.DRIED_BAMBOO_BLOCKS).itemTag(ModItemTags.DRIED_BAMBOO_BLOCKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer DRIED_BAMBOO_PLANKS = MakePlanks(MapColor.PALE_YELLOW, ModBlockSoundGroups.BAMBOO_WOOD).flammable(5, 20).fuel(300).cubeAllModel().blockTag(BlockTags.PLANKS).itemTag(ItemTags.PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer DRIED_BAMBOO_STAIRS = MakeWoodStairs(DRIED_BAMBOO_PLANKS).flammable(5, 20).fuel(300).stairsModel(DRIED_BAMBOO_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer DRIED_BAMBOO_SLAB = MakeSlab(DRIED_BAMBOO_PLANKS).flammable(5, 20).fuel(150).slabModel(DRIED_BAMBOO_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer DRIED_BAMBOO_FENCE = MakeWoodFence(DRIED_BAMBOO_PLANKS).flammable(5, 20).fuel(300).bambooFenceModel().blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer DRIED_BAMBOO_FENCE_GATE = MakeWoodFenceGate(DRIED_BAMBOO_PLANKS, ModSoundEvents.BLOCK_BAMBOO_WOOD_FENCE_GATE_OPEN, ModSoundEvents.BLOCK_BAMBOO_WOOD_FENCE_GATE_CLOSE).flammable(5, 20).fuel(300).bambooFenceGateModel().blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer DRIED_BAMBOO_DOOR = MakeWoodDoor(DRIED_BAMBOO_PLANKS, ModBlockSoundGroups.BAMBOO_WOOD, ModSoundEvents.BLOCK_BAMBOO_WOOD_DOOR_CLOSE, ModSoundEvents.BLOCK_BAMBOO_WOOD_DOOR_OPEN).fuel(200).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer DRIED_BAMBOO_TRAPDOOR = MakeWoodTrapdoor(DRIED_BAMBOO_PLANKS, ModBlockSoundGroups.BAMBOO_WOOD, ModSoundEvents.BLOCK_BAMBOO_WOOD_TRAPDOOR_CLOSE, ModSoundEvents.BLOCK_BAMBOO_WOOD_TRAPDOOR_OPEN).fuel(300).trapdoorModel(true).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer DRIED_BAMBOO_PRESSURE_PLATE = MakeWoodPressurePlate(DRIED_BAMBOO_PLANKS, ModBlockSoundGroups.BAMBOO_WOOD, ModSoundEvents.BLOCK_BAMBOO_WOOD_PRESSURE_PLATE_CLICK_ON, ModSoundEvents.BLOCK_BAMBOO_WOOD_PRESSURE_PLATE_CLICK_OFF).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer DRIED_BAMBOO_BUTTON = MakeWoodButton(ModBlockSoundGroups.BAMBOO_WOOD, ModSoundEvents.BLOCK_BAMBOO_WOOD_BUTTON_CLICK_ON, ModSoundEvents.BLOCK_BAMBOO_WOOD_BUTTON_CLICK_OFF).fuel(100).buttonModel(DRIED_BAMBOO_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final SignContainer DRIED_BAMBOO_SIGN = MakeSign("dried_bamboo", DRIED_BAMBOO_PLANKS, STRIPPED_DRIED_BAMBOO_BLOCK, ModBlockSoundGroups.BAMBOO_WOOD).fuel(200).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer DRIED_BAMBOO_IRON_HANGING_SIGN = MakeHangingSign(MakeSignType("dried_bamboo_iron"), STRIPPED_DRIED_BAMBOO_BLOCK, ModBlockSoundGroups.BAMBOO_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer DRIED_BAMBOO_GOLD_HANGING_SIGN = MakeHangingSign(MakeSignType("dried_bamboo_gold"), STRIPPED_DRIED_BAMBOO_BLOCK, ModBlockSoundGroups.BAMBOO_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer DRIED_BAMBOO_COPPER_HANGING_SIGN = MakeHangingSign(MakeSignType("dried_bamboo_copper"), STRIPPED_DRIED_BAMBOO_BLOCK, ModBlockSoundGroups.BAMBOO_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer DRIED_BAMBOO_EXPOSED_COPPER_HANGING_SIGN = MakeHangingSign(MakeSignType("dried_bamboo_exposed_copper"), STRIPPED_DRIED_BAMBOO_BLOCK, ModBlockSoundGroups.BAMBOO_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer DRIED_BAMBOO_WEATHERED_COPPER_HANGING_SIGN = MakeHangingSign(MakeSignType("dried_bamboo_weathered_copper"), STRIPPED_DRIED_BAMBOO_BLOCK, ModBlockSoundGroups.BAMBOO_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer DRIED_BAMBOO_OXIDIZED_COPPER_HANGING_SIGN = MakeHangingSign(MakeSignType("dried_bamboo_oxidized_copper"), STRIPPED_DRIED_BAMBOO_BLOCK, ModBlockSoundGroups.BAMBOO_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final WallBlockContainer DRIED_BAMBOO_NETHERITE_HANGING_SIGN = MakeHangingSign(MakeSignType("dried_bamboo_netherite"), STRIPPED_DRIED_BAMBOO_BLOCK, ModBlockSoundGroups.BAMBOO_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	//Mosaic
	public static final BlockContainer DRIED_BAMBOO_MOSAIC = MakeBlock(DRIED_BAMBOO_PLANKS).flammable(5, 20).fuel(300).cubeAllModel().blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer DRIED_BAMBOO_MOSAIC_STAIRS = MakeWoodStairs(DRIED_BAMBOO_MOSAIC).flammable(5, 20).fuel(300).stairsModel(DRIED_BAMBOO_MOSAIC).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer DRIED_BAMBOO_MOSAIC_SLAB = MakeSlab(DRIED_BAMBOO_MOSAIC).flammable(5, 20).fuel(150).slabModel(DRIED_BAMBOO_MOSAIC).blockTag(BlockTags.AXE_MINEABLE);
	//Extended
	public static final TorchContainer DRIED_BAMBOO_TORCH = MakeTorch(BlockSoundGroup.BAMBOO).torchModel();
	public static final TorchContainer DRIED_BAMBOO_SOUL_TORCH = MakeSoulTorch(BlockSoundGroup.BAMBOO).torchModel(DRIED_BAMBOO_TORCH);
	public static final TorchContainer DRIED_BAMBOO_ENDER_TORCH = MakeEnderTorch(BlockSoundGroup.BAMBOO).torchModel(DRIED_BAMBOO_TORCH);
	public static final TorchContainer UNDERWATER_DRIED_BAMBOO_TORCH = MakeUnderwaterTorch(BlockSoundGroup.BAMBOO).torchModel(DRIED_BAMBOO_TORCH);
	public static final BlockContainer DRIED_BAMBOO_BEEHIVE = MakeBeehive(MapColor.PALE_YELLOW, ModBlockSoundGroups.BAMBOO_WOOD).flammable(5, 20).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer DRIED_BAMBOO_BOOKSHELF = MakeBookshelf(DRIED_BAMBOO_PLANKS, ModBlockSoundGroups.BAMBOO_WOOD).flammable(30, 20).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer DRIED_BAMBOO_CHISELED_BOOKSHELF = MakeChiseledBookshelf(MapColor.PALE_YELLOW).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer DRIED_BAMBOO_CRAFTING_TABLE = MakeCraftingTable(DRIED_BAMBOO_PLANKS, ModBlockSoundGroups.BAMBOO_WOOD).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer DRIED_BAMBOO_LADDER = MakeLadder(BlockSoundGroup.BAMBOO).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer DRIED_BAMBOO_WOODCUTTER = MakeWoodcutter(DRIED_BAMBOO_PLANKS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer DRIED_BAMBOO_BARREL = MakeBarrel(MapColor.PALE_YELLOW, ModBlockSoundGroups.BAMBOO_WOOD).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer DRIED_BAMBOO_LECTERN = MakeLectern(DRIED_BAMBOO_PLANKS, ModBlockSoundGroups.BAMBOO_WOOD).flammable(30, 20).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer DRIED_BAMBOO_POWDER_KEG = MakePowderKeg(DRIED_BAMBOO_BARREL).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer DRIED_BAMBOO_BLOCK_SLAB = MakeLogSlab(DRIED_BAMBOO_BLOCK, ModBlockSoundGroups.BAMBOO_WOOD).flammable(5, 5).fuel(150).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer STRIPPED_DRIED_BAMBOO_BLOCK_SLAB = MakeLogSlab(STRIPPED_DRIED_BAMBOO_BLOCK, ModBlockSoundGroups.BAMBOO_WOOD).flammable(5, 5).fuel(150).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer DRIED_BAMBOO_ROW = BuildBlock(new RowBlock(Block.Settings.of(Material.WOOD, MapColor.DARK_GREEN).strength(1.0F).sounds(BlockSoundGroup.BAMBOO))).blockTag(ModBlockTags.ROWS).itemTag(ModItemTags.ROWS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer DRIED_BAMBOO_LOG = MakeLog(MapColor.PALE_YELLOW, MapColor.DIRT_BROWN, ModBlockSoundGroups.BAMBOO_WOOD).flammable(5, 5).fuel(300).itemTag(ModItemTags.DRIED_BAMBOO_LOGS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer DRIED_BAMBOO_LOG_SLAB = MakeLogSlab(DRIED_BAMBOO_LOG, LogSettings(MapColor.PALE_YELLOW, ModBlockSoundGroups.BAMBOO_WOOD)).flammable(5, 5).fuel(150).itemTag(ModItemTags.CHARRABLE_LOG_SLABS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer STRIPPED_DRIED_BAMBOO_LOG = MakeLog(MapColor.PALE_YELLOW, ModBlockSoundGroups.BAMBOO_WOOD).flammable(5, 5).fuel(300).itemTag(ModItemTags.DRIED_BAMBOO_LOGS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer STRIPPED_DRIED_BAMBOO_LOG_SLAB = MakeLogSlab(STRIPPED_DRIED_BAMBOO_LOG, LogSettings(MapColor.PALE_YELLOW, ModBlockSoundGroups.BAMBOO_WOOD)).flammable(5, 5).fuel(150).itemTag(ModItemTags.CHARRABLE_STRIPPED_LOG_SLABS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer DRIED_BAMBOO_WOOD = MakeWood(MapColor.DIRT_BROWN, ModBlockSoundGroups.BAMBOO_WOOD).flammable(5, 5).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer DRIED_BAMBOO_WOOD_SLAB = MakeBarkSlab(DRIED_BAMBOO_WOOD, DRIED_BAMBOO_LOG).flammable(5, 5).fuel(150).itemTag(ModItemTags.CHARRABLE_WOOD_SLABS).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer STRIPPED_DRIED_BAMBOO_WOOD = MakeWood(MapColor.PALE_YELLOW, ModBlockSoundGroups.BAMBOO_WOOD).flammable(5, 5).fuel(300).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer STRIPPED_DRIED_BAMBOO_WOOD_SLAB = MakeBarkSlab(STRIPPED_DRIED_BAMBOO_WOOD, STRIPPED_DRIED_BAMBOO_LOG).flammable(5, 5).fuel(150).itemTag(ModItemTags.CHARRABLE_STRIPPED_WOOD_SLABS).blockTag(BlockTags.AXE_MINEABLE);
	//Misc
	public static final Item DRIED_BAMBOO_HAMMER = MakeHammer(ToolMaterials.WOOD, 7, -3.2F);
	//Campfires
	public static final BlockContainer DRIED_BAMBOO_CAMPFIRE = MakeCampfire(MapColor.DIRT_BROWN, ModBlockSoundGroups.BAMBOO_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer DRIED_BAMBOO_SOUL_CAMPFIRE = MakeSoulCampfire(DRIED_BAMBOO_CAMPFIRE, ModBlockSoundGroups.BAMBOO_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	public static final BlockContainer DRIED_BAMBOO_ENDER_CAMPFIRE = MakeEnderCampfire(DRIED_BAMBOO_CAMPFIRE, ModBlockSoundGroups.BAMBOO_WOOD).blockTag(BlockTags.AXE_MINEABLE);
	//</editor-fold>

	public static void RegisterBamboo() {
		//<editor-fold desc="Bamboo">
		Register("minecraft:bamboo_block", BAMBOO_BLOCK, List.of(EN_US.Block(EN_US.Bamboo())));
		Register("minecraft:stripped_bamboo_block", STRIPPED_BAMBOO_BLOCK, List.of(EN_US.Block(EN_US.Bamboo(EN_US.Stripped()))));
		StrippedBlockUtil.Register(BAMBOO_BLOCK, STRIPPED_BAMBOO_BLOCK);
		Register("minecraft:bamboo_planks", BAMBOO_PLANKS, List.of(EN_US.Planks(EN_US.Bamboo())));
		Register("minecraft:bamboo_stairs", BAMBOO_STAIRS, List.of(EN_US.Stairs(EN_US.Bamboo())));
		Register("minecraft:bamboo_slab", BAMBOO_SLAB, List.of(EN_US.Slab(EN_US.Bamboo())));
		Register("minecraft:bamboo_fence", BAMBOO_FENCE, List.of(EN_US.Fence(EN_US.Fence(EN_US.Bamboo()))));
		Register("minecraft:bamboo_fence_gate", BAMBOO_FENCE_GATE, List.of(EN_US.Gate(EN_US.Fence(EN_US.Bamboo()))));
		Register("minecraft:bamboo_door", BAMBOO_DOOR, List.of(EN_US.Door(EN_US.Bamboo())));
		Register("minecraft:bamboo_trapdoor", BAMBOO_TRAPDOOR, List.of(EN_US.Trapdoor(EN_US.Bamboo())));
		Register("minecraft:bamboo_pressure_plate", BAMBOO_PRESSURE_PLATE, List.of(EN_US.Plate(EN_US.Pressure(EN_US.Bamboo()))));
		Register("minecraft:bamboo_button", BAMBOO_BUTTON, List.of(EN_US.Button(EN_US.Bamboo())));
		Register("minecraft:bamboo", BAMBOO_SIGN, List.of(EN_US._Sign(EN_US.Bamboo())));
		Register("bamboo_iron_hanging_sign", "bamboo_iron_wall_hanging_sign", BAMBOO_IRON_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Bamboo(), EN_US.Chain(EN_US.Iron(EN_US.with())))));
		Register("bamboo_gold_hanging_sign", "bamboo_gold_wall_hanging_sign", BAMBOO_GOLD_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Bamboo(), EN_US.Chain(EN_US.Gold(EN_US.with())))));
		Register("bamboo_copper_hanging_sign", "bamboo_copper_wall_hanging_sign", BAMBOO_COPPER_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Bamboo(), EN_US.Chain(EN_US.Copper(EN_US.with())))));
		Register("bamboo_exposed_copper_hanging_sign", "bamboo_exposed_copper_wall_hanging_sign", BAMBOO_EXPOSED_COPPER_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Bamboo(), EN_US.Chain(EN_US.Copper(EN_US.Exposed(EN_US.with()))))));
		Register("bamboo_weathered_copper_hanging_sign", "bamboo_weathered_copper_wall_hanging_sign", BAMBOO_WEATHERED_COPPER_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Bamboo(), EN_US.Chain(EN_US.Copper(EN_US.Weathered(EN_US.with()))))));
		Register("bamboo_oxidized_copper_hanging_sign", "bamboo_oxidized_copper_wall_hanging_sign", BAMBOO_OXIDIZED_COPPER_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Bamboo(), EN_US.Chain(EN_US.Copper(EN_US.Oxidized(EN_US.with()))))));
		Register("bamboo_netherite_hanging_sign", "bamboo_netherite_wall_hanging_sign", BAMBOO_NETHERITE_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Bamboo(), EN_US.Chain(EN_US.Netherite(EN_US.with())))));
		Register("minecraft:bamboo", "raft", BAMBOO_RAFT, List.of(EN_US._Raft(EN_US.Bamboo())));
		//Bamboo Mosaic
		Register("minecraft:bamboo_mosaic", BAMBOO_MOSAIC, List.of(EN_US.Mosaic(EN_US.Bamboo())));
		Register("minecraft:bamboo_mosaic_stairs", BAMBOO_MOSAIC_STAIRS, List.of(EN_US.Stairs(EN_US.Mosaic(EN_US.Bamboo()))));
		Register("minecraft:bamboo_mosaic_slab", BAMBOO_MOSAIC_SLAB, List.of(EN_US.Slab(EN_US.Mosaic(EN_US.Bamboo()))));
		//Extended
		Register("bamboo_torch", "bamboo_wall_torch", BAMBOO_TORCH, List.of(EN_US._Torch(EN_US.Bamboo())));
		Register("bamboo_soul_torch", "bamboo_soul_wall_torch", BAMBOO_SOUL_TORCH, List.of(EN_US._Torch(EN_US.Soul(EN_US.Bamboo()))));
		Register("bamboo_ender_torch", "bamboo_ender_wall_torch", BAMBOO_ENDER_TORCH, List.of(EN_US._Torch(EN_US.Ender(EN_US.Bamboo()))));
		Register("underwater_bamboo_torch", "underwater_bamboo_wall_torch", UNDERWATER_BAMBOO_TORCH, List.of(EN_US._Torch(EN_US.Bamboo(EN_US.Underwater()))));
		Register("bamboo_beehive", BAMBOO_BEEHIVE, List.of(EN_US.Beehive(EN_US.Bamboo())));
		Register("bamboo_bookshelf", BAMBOO_BOOKSHELF, List.of(EN_US.Bookshelf(EN_US.Bamboo())));
		Register("bamboo_chiseled_bookshelf", BAMBOO_CHISELED_BOOKSHELF, List.of(EN_US.Bookshelf(EN_US.Chiseled(EN_US.Bamboo()))));
		Register("bamboo_crafting_table", BAMBOO_CRAFTING_TABLE, List.of(EN_US.Table(EN_US.Crafting(EN_US.Bamboo()))));
		Register("bamboo_ladder", BAMBOO_LADDER, List.of(EN_US.Ladder(EN_US.Bamboo())));
		Register("bamboo_woodcutter", BAMBOO_WOODCUTTER, List.of(EN_US.Woodcutter(EN_US.Bamboo())));
		Register("bamboo_barrel", BAMBOO_BARREL, List.of(EN_US.Barrel(EN_US.Bamboo())));
		Register("bamboo_lectern", BAMBOO_LECTERN, List.of(EN_US.Lectern(EN_US.Bamboo())));
		Register("bamboo_powder_keg", BAMBOO_POWDER_KEG, List.of(EN_US.Keg(EN_US.Powder(EN_US.Bamboo()))));
		Register("bamboo_block_slab", BAMBOO_BLOCK_SLAB, List.of(EN_US.Slab(EN_US.Block(EN_US.Bamboo()))));
		Register("stripped_bamboo_block_slab", STRIPPED_BAMBOO_BLOCK_SLAB, List.of(EN_US.Slab(EN_US.Block(EN_US.Bamboo(EN_US.Stripped())))));
		StrippedBlockUtil.Register(BAMBOO_BLOCK_SLAB, STRIPPED_BAMBOO_BLOCK_SLAB);
		Register("bamboo_row", BAMBOO_ROW, List.of(EN_US.Row(EN_US.Bamboo())));
		Register("bamboo_log", BAMBOO_LOG, List.of(EN_US.Log(EN_US.Bamboo())));
		Register("bamboo_log_slab", BAMBOO_LOG_SLAB, List.of(EN_US.Slab(EN_US.Log(EN_US.Bamboo()))));
		Register("stripped_bamboo_log", STRIPPED_BAMBOO_LOG, List.of(EN_US.Log(EN_US.Bamboo(EN_US.Stripped()))));
		StrippedBlockUtil.Register(BAMBOO_LOG, STRIPPED_BAMBOO_LOG);
		Register("stripped_bamboo_log_slab", STRIPPED_BAMBOO_LOG_SLAB, List.of(EN_US.Slab(EN_US.Log(EN_US.Bamboo(EN_US.Stripped())))));
		StrippedBlockUtil.Register(BAMBOO_LOG_SLAB, STRIPPED_BAMBOO_LOG_SLAB);
		Register("bamboo_wood", BAMBOO_WOOD, List.of(EN_US.Wood(EN_US.Bamboo())));
		Register("bamboo_wood_slab", BAMBOO_WOOD_SLAB, List.of(EN_US.Slab(EN_US.Wood(EN_US.Bamboo()))));
		Register("stripped_bamboo_wood", STRIPPED_BAMBOO_WOOD, List.of(EN_US.Wood(EN_US.Bamboo(EN_US.Stripped()))));
		StrippedBlockUtil.Register(BAMBOO_WOOD, STRIPPED_BAMBOO_WOOD);
		Register("stripped_bamboo_wood_slab", STRIPPED_BAMBOO_WOOD_SLAB, List.of(EN_US.Slab(EN_US.Wood(EN_US.Bamboo(EN_US.Stripped())))));
		StrippedBlockUtil.Register(BAMBOO_WOOD_SLAB, STRIPPED_BAMBOO_WOOD_SLAB);
		Register("bamboo_hammer", BAMBOO_HAMMER, List.of(EN_US.Hammer(EN_US.Bamboo())));
		Register("bamboo_campfire", BAMBOO_CAMPFIRE, List.of(EN_US.Campfire(EN_US.Bamboo())));
		Register("bamboo_soul_campfire", BAMBOO_SOUL_CAMPFIRE, List.of(EN_US.Campfire(EN_US.Soul(EN_US.Bamboo()))));
		Register("bamboo_ender_campfire", BAMBOO_ENDER_CAMPFIRE, List.of(EN_US.Campfire(EN_US.Ender(EN_US.Bamboo()))));
		//</editor-fold>
		//<editor-fold desc="Bamboo (Dried)">
		Register("dried_bamboo", DRIED_BAMBOO, List.of(EN_US._Potted(EN_US.Bamboo(EN_US.Dried()))));
		Register("dried_bamboo_block", DRIED_BAMBOO_BLOCK, List.of(EN_US.Block(EN_US.Bamboo(EN_US.Dried()))));
		Register("stripped_dried_bamboo_block", STRIPPED_DRIED_BAMBOO_BLOCK, List.of(EN_US.Block(EN_US.Bamboo(EN_US.Dried(EN_US.Stripped())))));
		StrippedBlockUtil.Register(DRIED_BAMBOO_BLOCK, STRIPPED_DRIED_BAMBOO_BLOCK);
		Register("dried_bamboo_planks", DRIED_BAMBOO_PLANKS, List.of(EN_US.Planks(EN_US.Bamboo(EN_US.Dried()))));
		Register("dried_bamboo_stairs", DRIED_BAMBOO_STAIRS, List.of(EN_US.Stairs(EN_US.Bamboo(EN_US.Dried()))));
		Register("dried_bamboo_slab", DRIED_BAMBOO_SLAB, List.of(EN_US.Slab(EN_US.Bamboo(EN_US.Dried()))));
		Register("dried_bamboo_fence", DRIED_BAMBOO_FENCE, List.of(EN_US.Fence(EN_US.Fence(EN_US.Bamboo(EN_US.Dried())))));
		Register("dried_bamboo_fence_gate", DRIED_BAMBOO_FENCE_GATE, List.of(EN_US.Gate(EN_US.Fence(EN_US.Bamboo(EN_US.Dried())))));
		Register("dried_bamboo_door", DRIED_BAMBOO_DOOR, List.of(EN_US.Door(EN_US.Bamboo(EN_US.Dried()))));
		Register("dried_bamboo_trapdoor", DRIED_BAMBOO_TRAPDOOR, List.of(EN_US.Trapdoor(EN_US.Bamboo(EN_US.Dried()))));
		Register("dried_bamboo_pressure_plate", DRIED_BAMBOO_PRESSURE_PLATE, List.of(EN_US.Plate(EN_US.Pressure(EN_US.Bamboo(EN_US.Dried())))));
		Register("dried_bamboo_button", DRIED_BAMBOO_BUTTON, List.of(EN_US.Button(EN_US.Bamboo(EN_US.Dried()))));
		Register("dried_bamboo", DRIED_BAMBOO_SIGN, List.of(EN_US._Sign(EN_US.Bamboo(EN_US.Dried()))));
		Register("dried_bamboo_iron_hanging_sign", "dried_bamboo_iron_wall_hanging_sign", DRIED_BAMBOO_IRON_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Bamboo(EN_US.Dried()), EN_US.Chain(EN_US.Iron(EN_US.with())))));
		Register("dried_bamboo_gold_hanging_sign", "dried_bamboo_gold_wall_hanging_sign", DRIED_BAMBOO_GOLD_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Bamboo(EN_US.Dried()), EN_US.Chain(EN_US.Gold(EN_US.with())))));
		Register("dried_bamboo_copper_hanging_sign", "dried_bamboo_copper_wall_hanging_sign", DRIED_BAMBOO_COPPER_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Bamboo(EN_US.Dried()), EN_US.Chain(EN_US.Copper(EN_US.with())))));
		Register("dried_bamboo_exposed_copper_hanging_sign", "dried_bamboo_exposed_copper_wall_hanging_sign", DRIED_BAMBOO_EXPOSED_COPPER_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Bamboo(EN_US.Dried()), EN_US.Chain(EN_US.Copper(EN_US.Exposed(EN_US.with()))))));
		Register("dried_bamboo_weathered_copper_hanging_sign", "dried_bamboo_weathered_copper_wall_hanging_sign", DRIED_BAMBOO_WEATHERED_COPPER_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Bamboo(EN_US.Dried()), EN_US.Chain(EN_US.Copper(EN_US.Weathered(EN_US.with()))))));
		Register("dried_bamboo_oxidized_copper_hanging_sign", "dried_bamboo_oxidized_copper_wall_hanging_sign", DRIED_BAMBOO_OXIDIZED_COPPER_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Bamboo(EN_US.Dried()), EN_US.Chain(EN_US.Copper(EN_US.Oxidized(EN_US.with()))))));
		Register("dried_bamboo_netherite_hanging_sign", "dried_bamboo_netherite_wall_hanging_sign", DRIED_BAMBOO_NETHERITE_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Bamboo(EN_US.Dried()), EN_US.Chain(EN_US.Netherite(EN_US.with())))));
		//Bamboo Mosaic
		Register("dried_bamboo_mosaic", DRIED_BAMBOO_MOSAIC, List.of(EN_US.Mosaic(EN_US.Bamboo(EN_US.Dried()))));
		Register("dried_bamboo_mosaic_stairs", DRIED_BAMBOO_MOSAIC_STAIRS, List.of(EN_US.Stairs(EN_US.Mosaic(EN_US.Bamboo(EN_US.Dried())))));
		Register("dried_bamboo_mosaic_slab", DRIED_BAMBOO_MOSAIC_SLAB, List.of(EN_US.Slab(EN_US.Mosaic(EN_US.Bamboo(EN_US.Dried())))));
		//Extended
		Register("dried_bamboo_torch", "dried_bamboo_wall_torch", DRIED_BAMBOO_TORCH, List.of(EN_US._Torch(EN_US.Bamboo(EN_US.Dried()))));
		Register("dried_bamboo_soul_torch", "dried_bamboo_soul_wall_torch", DRIED_BAMBOO_SOUL_TORCH, List.of(EN_US._Torch(EN_US.Soul(EN_US.Bamboo(EN_US.Dried())))));
		Register("dried_bamboo_ender_torch", "dried_bamboo_ender_wall_torch", DRIED_BAMBOO_ENDER_TORCH, List.of(EN_US._Torch(EN_US.Ender(EN_US.Bamboo(EN_US.Dried())))));
		Register("underwater_dried_bamboo_torch", "underwater_dried_bamboo_wall_torch", UNDERWATER_DRIED_BAMBOO_TORCH, List.of(EN_US._Torch(EN_US.Bamboo(EN_US.Dried(EN_US.Underwater())))));
		Register("dried_bamboo_beehive", DRIED_BAMBOO_BEEHIVE, List.of(EN_US.Beehive(EN_US.Bamboo(EN_US.Dried()))));
		Register("dried_bamboo_bookshelf", DRIED_BAMBOO_BOOKSHELF, List.of(EN_US.Bookshelf(EN_US.Bamboo(EN_US.Dried()))));
		Register("dried_bamboo_chiseled_bookshelf", DRIED_BAMBOO_CHISELED_BOOKSHELF, List.of(EN_US.Bookshelf(EN_US.Chiseled(EN_US.Bamboo(EN_US.Dried())))));
		Register("dried_bamboo_crafting_table", DRIED_BAMBOO_CRAFTING_TABLE, List.of(EN_US.Table(EN_US.Crafting(EN_US.Bamboo(EN_US.Dried())))));
		Register("dried_bamboo_ladder", DRIED_BAMBOO_LADDER, List.of(EN_US.Ladder(EN_US.Bamboo(EN_US.Dried()))));
		Register("dried_bamboo_woodcutter", DRIED_BAMBOO_WOODCUTTER, List.of(EN_US.Woodcutter(EN_US.Bamboo(EN_US.Dried()))));
		Register("dried_bamboo_barrel", DRIED_BAMBOO_BARREL, List.of(EN_US.Barrel(EN_US.Bamboo(EN_US.Dried()))));
		Register("dried_bamboo_lectern", DRIED_BAMBOO_LECTERN, List.of(EN_US.Lectern(EN_US.Bamboo(EN_US.Dried()))));
		Register("dried_bamboo_powder_keg", DRIED_BAMBOO_POWDER_KEG, List.of(EN_US.Keg(EN_US.Powder(EN_US.Bamboo(EN_US.Dried())))));
		Register("dried_bamboo_block_slab", DRIED_BAMBOO_BLOCK_SLAB, List.of(EN_US.Slab(EN_US.Block(EN_US.Bamboo(EN_US.Dried())))));
		Register("stripped_dried_bamboo_block_slab", STRIPPED_DRIED_BAMBOO_BLOCK_SLAB, List.of(EN_US.Slab(EN_US.Block(EN_US.Bamboo(EN_US.Dried(EN_US.Stripped()))))));
		StrippedBlockUtil.Register(DRIED_BAMBOO_BLOCK_SLAB, STRIPPED_DRIED_BAMBOO_BLOCK_SLAB);
		Register("dried_bamboo_row", DRIED_BAMBOO_ROW, List.of(EN_US.Row(EN_US.Bamboo(EN_US.Dried()))));
		Register("dried_bamboo_log", DRIED_BAMBOO_LOG, List.of(EN_US.Log(EN_US.Bamboo(EN_US.Dried()))));
		Register("dried_bamboo_log_slab", DRIED_BAMBOO_LOG_SLAB, List.of(EN_US.Slab(EN_US.Log(EN_US.Bamboo(EN_US.Dried())))));
		Register("stripped_dried_bamboo_log", STRIPPED_DRIED_BAMBOO_LOG, List.of(EN_US.Log(EN_US.Bamboo(EN_US.Dried(EN_US.Stripped())))));
		StrippedBlockUtil.Register(DRIED_BAMBOO_LOG, STRIPPED_DRIED_BAMBOO_LOG);
		Register("stripped_dried_bamboo_log_slab", STRIPPED_DRIED_BAMBOO_LOG_SLAB, List.of(EN_US.Slab(EN_US.Log(EN_US.Bamboo(EN_US.Dried(EN_US.Stripped()))))));
		StrippedBlockUtil.Register(DRIED_BAMBOO_LOG_SLAB, STRIPPED_DRIED_BAMBOO_LOG_SLAB);
		Register("dried_bamboo_wood", DRIED_BAMBOO_WOOD, List.of(EN_US.Wood(EN_US.Bamboo(EN_US.Dried()))));
		Register("dried_bamboo_wood_slab", DRIED_BAMBOO_WOOD_SLAB, List.of(EN_US.Slab(EN_US.Wood(EN_US.Bamboo(EN_US.Dried())))));
		Register("stripped_dried_bamboo_wood", STRIPPED_DRIED_BAMBOO_WOOD, List.of(EN_US.Wood(EN_US.Bamboo(EN_US.Dried(EN_US.Stripped())))));
		StrippedBlockUtil.Register(DRIED_BAMBOO_WOOD, STRIPPED_DRIED_BAMBOO_WOOD);
		Register("stripped_dried_bamboo_wood_slab", STRIPPED_DRIED_BAMBOO_WOOD_SLAB, List.of(EN_US.Slab(EN_US.Wood(EN_US.Bamboo(EN_US.Dried(EN_US.Stripped()))))));
		StrippedBlockUtil.Register(DRIED_BAMBOO_WOOD_SLAB, STRIPPED_DRIED_BAMBOO_WOOD_SLAB);
		Register("dried_bamboo_hammer", DRIED_BAMBOO_HAMMER, List.of(EN_US.Hammer(EN_US.Bamboo(EN_US.Dried()))));
		Register("dried_bamboo_campfire", DRIED_BAMBOO_CAMPFIRE, List.of(EN_US.Campfire(EN_US.Bamboo(EN_US.Dried()))));
		Register("dried_bamboo_soul_campfire", DRIED_BAMBOO_SOUL_CAMPFIRE, List.of(EN_US.Campfire(EN_US.Soul(EN_US.Bamboo(EN_US.Dried())))));
		Register("dried_bamboo_ender_campfire", DRIED_BAMBOO_ENDER_CAMPFIRE, List.of(EN_US.Campfire(EN_US.Ender(EN_US.Bamboo(EN_US.Dried())))));
		//</editor-fold>
	}
}
