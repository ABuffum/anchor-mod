package fun.mousewich.sound;

import net.minecraft.sound.BlockSoundGroup;

public class ModBlockSoundGroups {
	//<editor-fold desc="Echo">
	public static final BlockSoundGroup ECHO_BLOCK = new BlockSoundGroup(1.0f, 1.0f, ModSoundEvents.BLOCK_ECHO_BLOCK_BREAK, ModSoundEvents.BLOCK_ECHO_BLOCK_STEP, ModSoundEvents.BLOCK_ECHO_BLOCK_PLACE, ModSoundEvents.BLOCK_ECHO_BLOCK_HIT, ModSoundEvents.BLOCK_ECHO_BLOCK_FALL);
	public static final BlockSoundGroup ECHO_CLUSTER = new BlockSoundGroup(1.0f, 1.0f, ModSoundEvents.BLOCK_ECHO_CLUSTER_BREAK, ModSoundEvents.BLOCK_ECHO_CLUSTER_STEP, ModSoundEvents.BLOCK_ECHO_CLUSTER_PLACE, ModSoundEvents.BLOCK_ECHO_CLUSTER_HIT, ModSoundEvents.BLOCK_ECHO_CLUSTER_FALL);
	public static final BlockSoundGroup SMALL_ECHO_BUD = new BlockSoundGroup(1.0f, 1.0f, ModSoundEvents.BLOCK_SMALL_ECHO_BUD_BREAK, ModSoundEvents.BLOCK_ECHO_CLUSTER_STEP, ModSoundEvents.BLOCK_SMALL_ECHO_BUD_PLACE, ModSoundEvents.BLOCK_ECHO_CLUSTER_HIT, ModSoundEvents.BLOCK_ECHO_CLUSTER_FALL);
	public static final BlockSoundGroup MEDIUM_ECHO_BUD = new BlockSoundGroup(1.0f, 1.0f, ModSoundEvents.BLOCK_MEDIUM_ECHO_BUD_BREAK, ModSoundEvents.BLOCK_ECHO_CLUSTER_STEP, ModSoundEvents.BLOCK_MEDIUM_ECHO_BUD_PLACE, ModSoundEvents.BLOCK_ECHO_CLUSTER_HIT, ModSoundEvents.BLOCK_ECHO_CLUSTER_FALL);
	public static final BlockSoundGroup LARGE_ECHO_BUD = new BlockSoundGroup(1.0f, 1.0f, ModSoundEvents.BLOCK_LARGE_ECHO_BUD_BREAK, ModSoundEvents.BLOCK_ECHO_CLUSTER_STEP, ModSoundEvents.BLOCK_LARGE_ECHO_BUD_PLACE, ModSoundEvents.BLOCK_ECHO_CLUSTER_HIT, ModSoundEvents.BLOCK_ECHO_CLUSTER_FALL);
	//</editor-fold>
	//<editor-fold desc="Mangrove & Mud">
	public static final BlockSoundGroup MANGROVE_ROOTS = new BlockSoundGroup(1.0f, 1.0f, ModSoundEvents.BLOCK_MANGROVE_ROOTS_BREAK, ModSoundEvents.BLOCK_MANGROVE_ROOTS_STEP, ModSoundEvents.BLOCK_MANGROVE_ROOTS_PLACE, ModSoundEvents.BLOCK_MANGROVE_ROOTS_HIT, ModSoundEvents.BLOCK_MANGROVE_ROOTS_FALL);
	public static final BlockSoundGroup MUDDY_MANGROVE_ROOTS = new BlockSoundGroup(1.0f, 1.0f, ModSoundEvents.BLOCK_MUDDY_MANGROVE_ROOTS_BREAK, ModSoundEvents.BLOCK_MUDDY_MANGROVE_ROOTS_STEP, ModSoundEvents.BLOCK_MUDDY_MANGROVE_ROOTS_PLACE, ModSoundEvents.BLOCK_MUDDY_MANGROVE_ROOTS_HIT, ModSoundEvents.BLOCK_MUDDY_MANGROVE_ROOTS_FALL);
	public static final BlockSoundGroup MUD = new BlockSoundGroup(1.0f, 1.0f, ModSoundEvents.BLOCK_MUD_BREAK, ModSoundEvents.BLOCK_MUD_STEP, ModSoundEvents.BLOCK_MUD_PLACE, ModSoundEvents.BLOCK_MUD_HIT, ModSoundEvents.BLOCK_MUD_FALL);
	public static final BlockSoundGroup MUD_BRICKS = new BlockSoundGroup(1.0f, 1.0f, ModSoundEvents.BLOCK_MUD_BRICKS_BREAK, ModSoundEvents.BLOCK_MUD_BRICKS_STEP, ModSoundEvents.BLOCK_MUD_BRICKS_PLACE, ModSoundEvents.BLOCK_MUD_BRICKS_HIT, ModSoundEvents.BLOCK_MUD_BRICKS_FALL);
	public static final BlockSoundGroup PACKED_MUD = new BlockSoundGroup(1.0f, 1.0f, ModSoundEvents.BLOCK_PACKED_MUD_BREAK, ModSoundEvents.BLOCK_PACKED_MUD_STEP, ModSoundEvents.BLOCK_PACKED_MUD_PLACE, ModSoundEvents.BLOCK_PACKED_MUD_HIT, ModSoundEvents.BLOCK_PACKED_MUD_FALL);
	//</editor-fold>
	//<editor-fold desc="Frogs">
	public static final BlockSoundGroup FROGLIGHT = new BlockSoundGroup(1.0f, 1.0f, ModSoundEvents.BLOCK_FROGLIGHT_BREAK, ModSoundEvents.BLOCK_FROGLIGHT_STEP, ModSoundEvents.BLOCK_FROGLIGHT_PLACE, ModSoundEvents.BLOCK_FROGLIGHT_HIT, ModSoundEvents.BLOCK_FROGLIGHT_FALL);
	public static final BlockSoundGroup FROGSPAWN = new BlockSoundGroup(1.0f, 1.0f, ModSoundEvents.BLOCK_FROGSPAWN_BREAK, ModSoundEvents.BLOCK_FROGSPAWN_STEP, ModSoundEvents.BLOCK_FROGSPAWN_PLACE, ModSoundEvents.BLOCK_FROGSPAWN_HIT, ModSoundEvents.BLOCK_FROGSPAWN_FALL);
	//</editor-fold>
	//<editor-fold desc="Sculk">
	public static final BlockSoundGroup SCULK_CATALYST = new BlockSoundGroup(1.0f, 1.0f, ModSoundEvents.BLOCK_SCULK_CATALYST_BREAK, ModSoundEvents.BLOCK_SCULK_CATALYST_STEP, ModSoundEvents.BLOCK_SCULK_CATALYST_PLACE, ModSoundEvents.BLOCK_SCULK_CATALYST_HIT, ModSoundEvents.BLOCK_SCULK_CATALYST_FALL);
	public static final BlockSoundGroup SCULK = new BlockSoundGroup(1.0f, 1.0f, ModSoundEvents.BLOCK_SCULK_BREAK, ModSoundEvents.BLOCK_SCULK_STEP, ModSoundEvents.BLOCK_SCULK_PLACE, ModSoundEvents.BLOCK_SCULK_HIT, ModSoundEvents.BLOCK_SCULK_FALL);
	public static final BlockSoundGroup SCULK_VEIN = new BlockSoundGroup(1.0f, 1.0f, ModSoundEvents.BLOCK_SCULK_VEIN_BREAK, ModSoundEvents.BLOCK_SCULK_VEIN_STEP, ModSoundEvents.BLOCK_SCULK_VEIN_PLACE, ModSoundEvents.BLOCK_SCULK_VEIN_HIT, ModSoundEvents.BLOCK_SCULK_VEIN_FALL);
	public static final BlockSoundGroup SCULK_SHRIEKER = new BlockSoundGroup(1.0f, 1.0f, ModSoundEvents.BLOCK_SCULK_SHRIEKER_BREAK, ModSoundEvents.BLOCK_SCULK_SHRIEKER_STEP, ModSoundEvents.BLOCK_SCULK_SHRIEKER_PLACE, ModSoundEvents.BLOCK_SCULK_SHRIEKER_HIT, ModSoundEvents.BLOCK_SCULK_SHRIEKER_FALL);
	//</editor-fold>
	//<editor-fold desc="Cherry">
	public static final BlockSoundGroup CHERRY_WOOD = new BlockSoundGroup(1.0f, 1.0f, ModSoundEvents.BLOCK_CHERRY_WOOD_BREAK, ModSoundEvents.BLOCK_CHERRY_WOOD_STEP, ModSoundEvents.BLOCK_CHERRY_WOOD_PLACE, ModSoundEvents.BLOCK_CHERRY_WOOD_HIT, ModSoundEvents.BLOCK_CHERRY_WOOD_FALL);
	public static final BlockSoundGroup CHERRY_SAPLING = new BlockSoundGroup(1.0f, 1.0f, ModSoundEvents.BLOCK_CHERRY_SAPLING_BREAK, ModSoundEvents.BLOCK_CHERRY_SAPLING_STEP, ModSoundEvents.BLOCK_CHERRY_SAPLING_PLACE, ModSoundEvents.BLOCK_CHERRY_SAPLING_HIT, ModSoundEvents.BLOCK_CHERRY_SAPLING_FALL);
	public static final BlockSoundGroup CHERRY_LEAVES = new BlockSoundGroup(1.0f, 1.0f, ModSoundEvents.BLOCK_CHERRY_LEAVES_BREAK, ModSoundEvents.BLOCK_CHERRY_LEAVES_STEP, ModSoundEvents.BLOCK_CHERRY_LEAVES_PLACE, ModSoundEvents.BLOCK_CHERRY_LEAVES_HIT, ModSoundEvents.BLOCK_CHERRY_LEAVES_FALL);
	public static final BlockSoundGroup CHERRY_WOOD_HANGING_SIGN = new BlockSoundGroup(1.0f, 1.0f, ModSoundEvents.BLOCK_CHERRY_WOOD_HANGING_SIGN_BREAK, ModSoundEvents.BLOCK_CHERRY_WOOD_HANGING_SIGN_STEP, ModSoundEvents.BLOCK_CHERRY_WOOD_HANGING_SIGN_PLACE, ModSoundEvents.BLOCK_CHERRY_WOOD_HANGING_SIGN_HIT, ModSoundEvents.BLOCK_CHERRY_WOOD_HANGING_SIGN_FALL);
	public static final BlockSoundGroup PINK_PETALS = new BlockSoundGroup(1.0f, 1.0f, ModSoundEvents.BLOCK_PINK_PETALS_BREAK, ModSoundEvents.BLOCK_PINK_PETALS_STEP, ModSoundEvents.BLOCK_PINK_PETALS_PLACE, ModSoundEvents.BLOCK_PINK_PETALS_HIT, ModSoundEvents.BLOCK_PINK_PETALS_FALL);
	//</editor-fold>+
	public static final BlockSoundGroup HANGING_SIGN = new BlockSoundGroup(1.0F, 1.0F, ModSoundEvents.BLOCK_HANGING_SIGN_BREAK, ModSoundEvents.BLOCK_HANGING_SIGN_STEP, ModSoundEvents.BLOCK_HANGING_SIGN_PLACE, ModSoundEvents.BLOCK_HANGING_SIGN_HIT, ModSoundEvents.BLOCK_HANGING_SIGN_FALL);
	public static final BlockSoundGroup BAMBOO_WOOD = new BlockSoundGroup(1.0F, 1.0F, ModSoundEvents.BLOCK_BAMBOO_WOOD_BREAK, ModSoundEvents.BLOCK_BAMBOO_WOOD_STEP, ModSoundEvents.BLOCK_BAMBOO_WOOD_PLACE, ModSoundEvents.BLOCK_BAMBOO_WOOD_HIT, ModSoundEvents.BLOCK_BAMBOO_WOOD_FALL);
	public static final BlockSoundGroup NETHER_WOOD = new BlockSoundGroup(1.0F, 1.0F, ModSoundEvents.BLOCK_NETHER_WOOD_BREAK, ModSoundEvents.BLOCK_NETHER_WOOD_STEP, ModSoundEvents.BLOCK_NETHER_WOOD_PLACE, ModSoundEvents.BLOCK_NETHER_WOOD_HIT, ModSoundEvents.BLOCK_NETHER_WOOD_FALL);
	public static final BlockSoundGroup SUSPICIOUS_SAND = new BlockSoundGroup(1.0f, 1.0f, ModSoundEvents.BLOCK_SUSPICIOUS_SAND_BREAK, ModSoundEvents.BLOCK_SUSPICIOUS_SAND_STEP, ModSoundEvents.BLOCK_SUSPICIOUS_SAND_PLACE, ModSoundEvents.BLOCK_SUSPICIOUS_SAND_HIT, ModSoundEvents.BLOCK_SUSPICIOUS_SAND_FALL);
	public static final BlockSoundGroup SUSPICIOUS_GRAVEL = new BlockSoundGroup(1.0f, 1.0f, ModSoundEvents.BLOCK_SUSPICIOUS_GRAVEL_BREAK, ModSoundEvents.BLOCK_SUSPICIOUS_GRAVEL_STEP, ModSoundEvents.BLOCK_SUSPICIOUS_GRAVEL_PLACE, ModSoundEvents.BLOCK_SUSPICIOUS_GRAVEL_HIT, ModSoundEvents.BLOCK_SUSPICIOUS_GRAVEL_FALL);
	public static final BlockSoundGroup DECORATED_POT = new BlockSoundGroup(1.0f, 1.0f, ModSoundEvents.INTENTIONALLY_EMPTY, ModSoundEvents.BLOCK_DECORATED_POT_STEP, ModSoundEvents.BLOCK_DECORATED_POT_PLACE, ModSoundEvents.BLOCK_DECORATED_POT_HIT, ModSoundEvents.BLOCK_DECORATED_POT_FALL);
	public static final BlockSoundGroup CRACKED_DECORATED_POT = new BlockSoundGroup(1.0f, 1.0f, ModSoundEvents.BLOCK_DECORATED_POT_SHATTER, ModSoundEvents.BLOCK_DECORATED_POT_STEP, ModSoundEvents.BLOCK_DECORATED_POT_PLACE, ModSoundEvents.BLOCK_DECORATED_POT_HIT, ModSoundEvents.BLOCK_DECORATED_POT_FALL);
	public static final BlockSoundGroup CHISELED_BOOKSHELF = new BlockSoundGroup(1.0f, 1.0f, ModSoundEvents.BLOCK_CHISELED_BOOKSHELF_BREAK, ModSoundEvents.BLOCK_CHISELED_BOOKSHELF_STEP, ModSoundEvents.BLOCK_CHISELED_BOOKSHELF_PLACE, ModSoundEvents.BLOCK_CHISELED_BOOKSHELF_HIT, ModSoundEvents.BLOCK_CHISELED_BOOKSHELF_FALL);

}
