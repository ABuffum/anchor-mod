package haven.deprecated;

import haven.containers.BlockContainer;
import haven.containers.SignContainer;
import haven.containers.WallBlockContainer;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

import java.util.function.Function;

import static haven.ModBase.*;
import static haven.ModRegistry.*;

public class Deprecated {
	public static Item.Settings ItemSettings() {
		return new Item.Settings();
	}

	public static BlockContainer ReplacedBy(BlockContainer container, Function<Block, Block> makeBlock) {
		return new BlockContainer(makeBlock.apply(container.getBlock()), ReplacedBy(container.getItem()));
	}
	public static WallBlockContainer ReplacedBy(SignContainer container) {
		return new WallBlockContainer(new DeprecatedSignBlock(container.getBlock()), new DeprecatedWallSignBlock(container.getWallBlock()), ReplacedBy(container.getItem()));
	}
	public static Item ReplacedBy(Item item) { return new DeprecatedItem(item); }

	public static void RegisterOldGoatForCompatibility() {
		Register("reinforced_deepslate", ReplacedBy(REINFORCED_DEEPSLATE, DeprecatedBlock::new));
		Register("goat_horn", ReplacedBy(GOAT_HORN));
		Register("sculk_sensor", ReplacedBy(SCULK_SENSOR, DeprecatedSculkSensorBlock::new));
		Register("sculk", ReplacedBy(SCULK, DeprecatedBlock::new));
		Register("sculk_vein", ReplacedBy(SCULK_VEIN, DeprecatedSculkVeinBlock::new));
		Register("sculk_catalyst", ReplacedBy(SCULK_CATALYST, DeprecatedSculkCatalystBlock::new));
		Register("sculk_shrieker", ReplacedBy(SCULK_SHRIEKER, DeprecatedSculkShriekerBlock::new));
		Register("warden_spawn_egg", ReplacedBy(WARDEN_SPAWN_EGG));
	}

	public static void RegisterOldMusicDiscsForCompatibility() {
		Register("music_disc_otherside", ReplacedBy(MUSIC_DISC_OTHERSIDE));
		Register("music_disc_5", ReplacedBy(MUSIC_DISC_5));
		Register("disc_fragment_5", ReplacedBy(DISC_FRAGMENT_5));
	}

	public static void RegisterOldMudForCompatibility() {
		Register("mud", ReplacedBy(MUD, DeprecatedBlock::new));
		Register("packed_mud", ReplacedBy(PACKED_MUD, DeprecatedBlock::new));
		Register("mud_bricks", ReplacedBy(MUD_BRICKS, DeprecatedBlock::new));
		Register("mud_brick_slab", ReplacedBy(MUD_BRICK_SLAB, DeprecatedSlabBlock::new));
		Register("mud_brick_stairs", ReplacedBy(MUD_BRICK_STAIRS, DeprecatedStairsBlock::new));
		Register("mud_brick_wall", ReplacedBy(MUD_BRICK_WALL, DeprecatedWallBlock::new));
	}

	public static void RegisterOldFrogsForCompatibility() {
		Register("ochre_froglight", ReplacedBy(OCHRE_FROGLIGHT, DeprecatedPillarBlock::new));
		Register("verdant_froglight", ReplacedBy(VERDANT_FROGLIGHT, DeprecatedPillarBlock::new));
		Register("pearlescent_froglight", ReplacedBy(PEARLESCENT_FROGLIGHT, DeprecatedPillarBlock::new));
	}

	public static void RegisterOldMangroveForCompatibility() {
		Register("mangrove_log", ReplacedBy(MANGROVE_MATERIAL.getLog(), DeprecatedPillarBlock::new));
		Register("stripped_mangrove_log", ReplacedBy(MANGROVE_MATERIAL.getStrippedLog(), DeprecatedPillarBlock::new));
		Register("mangrove_wood", ReplacedBy(MANGROVE_MATERIAL.getWood(), DeprecatedPillarBlock::new));
		Register("stripped_mangrove_wood", ReplacedBy(MANGROVE_MATERIAL.getStrippedWood(), DeprecatedPillarBlock::new));
		Register("mangrove_leaves", ReplacedBy(MANGROVE_MATERIAL.getLeaves(), DeprecatedLeavesBlock::new));
		Register("mangrove_planks", ReplacedBy(MANGROVE_MATERIAL.getPlanks(), DeprecatedBlock::new));
		Register("mangrove_slab", ReplacedBy(MANGROVE_MATERIAL.getSlab(), DeprecatedSlabBlock::new));
		Register("mangrove_stairs", ReplacedBy(MANGROVE_MATERIAL.getStairs(), DeprecatedStairsBlock::new));
		Register("mangrove_fence", ReplacedBy(MANGROVE_MATERIAL.getFence(), DeprecatedFenceBlock::new));
		Register("mangrove_fence_gate", ReplacedBy(MANGROVE_MATERIAL.getFenceGate(), DeprecatedFenceGateBlock::new));
		Register("mangrove_door", ReplacedBy(MANGROVE_MATERIAL.getDoor(), DeprecatedDoorBlock::new));
		Register("mangrove_trapdoor", ReplacedBy(MANGROVE_MATERIAL.getTrapdoor(), DeprecatedTrapdoorBlock::new));
		Register("mangrove_pressure_plate", ReplacedBy(MANGROVE_MATERIAL.getPressurePlate(), DeprecatedPressurePlateBlock::new));
		Register("mangrove_button", ReplacedBy(MANGROVE_MATERIAL.getButton(), DeprecatedButtonBlock::new));
		Register("mangrove_sign", "mangrove_wall_sign", ReplacedBy(MANGROVE_MATERIAL.getSign()));
		Register("mangrove_boat", ReplacedBy(MANGROVE_MATERIAL.getBoat().getItem()));
		Register("mangrove_roots", ReplacedBy(MANGROVE_ROOTS, DeprecatedMangroveRootsBlock::new));
		Register("muddy_mangrove_roots", ReplacedBy(MUDDY_MANGROVE_ROOTS, DeprecatedPillarBlock::new));
	}

	public static void RegisterOldDeepDarkForCompatibility() {
		Register("echo_shard", ReplacedBy(ECHO_SHARD));
	}
}
