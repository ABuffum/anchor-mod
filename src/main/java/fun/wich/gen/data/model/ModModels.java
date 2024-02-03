package fun.wich.gen.data.model;

import fun.wich.ModId;
import net.minecraft.data.client.model.Model;
import net.minecraft.data.client.model.TextureKey;

import java.util.Optional;

public class ModModels {
	public static final Model CUBE_NORTH_WEST_MIRRORED_ALL = Make("minecraft:block/cube_north_west_mirrored_all", "_north_west_mirrored", TextureKey.ALL);
	public static final Model CUSTOM_FENCE_INVENTORY = Make("minecraft:block/custom_fence_inventory", "_inventory", TextureKey.PARTICLE, TextureKey.TEXTURE);
	public static final Model CUSTOM_FENCE_POST = Make("minecraft:block/custom_fence_post", "_post", TextureKey.PARTICLE, TextureKey.TEXTURE);
	public static final Model CUSTOM_FENCE_SIDE_EAST = Make("minecraft:block/custom_fence_side_east", "_side_east", TextureKey.PARTICLE, TextureKey.TEXTURE);
	public static final Model CUSTOM_FENCE_SIDE_NORTH = Make("minecraft:block/custom_fence_side_north", "_side_north", TextureKey.PARTICLE, TextureKey.TEXTURE);
	public static final Model CUSTOM_FENCE_SIDE_SOUTH = Make("minecraft:block/custom_fence_side_south", "_side_south", TextureKey.PARTICLE, TextureKey.TEXTURE);
	public static final Model CUSTOM_FENCE_SIDE_WEST = Make("minecraft:block/custom_fence_side_west", "_side_west", TextureKey.PARTICLE, TextureKey.TEXTURE);
	public static final Model TEMPLATE_BARS_CAP = Make("block/_template_bars_cap", "_cap", TextureKey.PARTICLE, ModTextureKeys.BARS, TextureKey.EDGE);
	public static final Model TEMPLATE_BARS_CAP_ALT = Make("block/_template_bars_cap_alt", "_cap_alt", TextureKey.PARTICLE, ModTextureKeys.BARS, TextureKey.EDGE);
	public static final Model TEMPLATE_BARS_POST = Make("block/_template_bars_post", "_post", TextureKey.PARTICLE, ModTextureKeys.BARS, TextureKey.EDGE);
	public static final Model TEMPLATE_BARS_POST_ENDS = Make("block/_template_bars_post_ends", "_post_ends", TextureKey.PARTICLE, ModTextureKeys.BARS, TextureKey.EDGE);
	public static final Model TEMPLATE_BARS_SIDE = Make("block/_template_bars_side", "_side", TextureKey.PARTICLE, ModTextureKeys.BARS, TextureKey.EDGE);
	public static final Model TEMPLATE_BARS_SIDE_ALT = Make("block/_template_bars_side_alt", "_side_alt", TextureKey.PARTICLE, ModTextureKeys.BARS, TextureKey.EDGE);
	public static final Model TEMPLATE_BONE_TORCH = Make("block/_template_bone_torch", TextureKey.TORCH);
	public static final Model TEMPLATE_BONE_TORCH_WALL = Make("block/_template_bone_torch_wall", TextureKey.TORCH);
	public static final Model TEMPLATE_CAKE = Make("block/_template_cake", TextureKey.BOTTOM, TextureKey.SIDE, TextureKey.TOP, TextureKey.PARTICLE);
	public static final Model TEMPLATE_CAKE_SLICE1 = Make("block/_template_cake_slice1", "_slice1", TextureKey.BOTTOM, TextureKey.SIDE, TextureKey.TOP, TextureKey.INSIDE, TextureKey.PARTICLE);
	public static final Model TEMPLATE_CAKE_SLICE2 = Make("block/_template_cake_slice2", "_slice2", TextureKey.BOTTOM, TextureKey.SIDE, TextureKey.TOP, TextureKey.INSIDE, TextureKey.PARTICLE);
	public static final Model TEMPLATE_CAKE_SLICE3 = Make("block/_template_cake_slice3", "_slice3", TextureKey.BOTTOM, TextureKey.SIDE, TextureKey.TOP, TextureKey.INSIDE, TextureKey.PARTICLE);
	public static final Model TEMPLATE_CAKE_SLICE4 = Make("block/_template_cake_slice4", "_slice4", TextureKey.BOTTOM, TextureKey.SIDE, TextureKey.TOP, TextureKey.INSIDE, TextureKey.PARTICLE);
	public static final Model TEMPLATE_CAKE_SLICE5 = Make("block/_template_cake_slice5", "_slice5", TextureKey.BOTTOM, TextureKey.SIDE, TextureKey.TOP, TextureKey.INSIDE, TextureKey.PARTICLE);
	public static final Model TEMPLATE_CAKE_SLICE6 = Make("block/_template_cake_slice6", "_slice6", TextureKey.BOTTOM, TextureKey.SIDE, TextureKey.TOP, TextureKey.INSIDE, TextureKey.PARTICLE);
	public static final Model TEMPLATE_CAMPFIRE = Make("block/_template_campfire", ModTextureKeys.LOG, TextureKey.LIT_LOG, TextureKey.FIRE);
	public static final Model TEMPLATE_CAMPFIRE_OFF = Make("block/_template_campfire_off", "_off", ModTextureKeys.LOG);
	public static final Model TEMPLATE_CHAIN = Make("block/_template_chain", TextureKey.ALL);
	public static final Model TEMPLATE_CHISELED_BOOKSHELF_INVENTORY = Make("minecraft:block/template_chiseled_bookshelf_inventory", TextureKey.TOP, TextureKey.FRONT, TextureKey.SIDE);
	public static final Model TEMPLATE_CHISELED_BOOKSHELF_SLOT_TOP_LEFT = Make("minecraft:block/template_chiseled_bookshelf_slot_top_left", "_slot_top_left", TextureKey.TEXTURE);
	public static final Model TEMPLATE_CHISELED_BOOKSHELF_SLOT_TOP_MID = Make("minecraft:block/template_chiseled_bookshelf_slot_top_mid", "_slot_top_mid", TextureKey.TEXTURE);
	public static final Model TEMPLATE_CHISELED_BOOKSHELF_SLOT_TOP_RIGHT = Make("minecraft:block/template_chiseled_bookshelf_slot_top_right", "_slot_top_right", TextureKey.TEXTURE);
	public static final Model TEMPLATE_CHISELED_BOOKSHELF_SLOT_BOTTOM_LEFT = Make("minecraft:block/template_chiseled_bookshelf_slot_bottom_left", "_slot_bottom_left", TextureKey.TEXTURE);
	public static final Model TEMPLATE_CHISELED_BOOKSHELF_SLOT_BOTTOM_MID = Make("minecraft:block/template_chiseled_bookshelf_slot_bottom_mid", "_slot_bottom_mid", TextureKey.TEXTURE);
	public static final Model TEMPLATE_CHISELED_BOOKSHELF_SLOT_BOTTOM_RIGHT = Make("minecraft:block/template_chiseled_bookshelf_slot_bottom_right", "_slot_bottom_right", TextureKey.TEXTURE);
	public static final Model TEMPLATE_CUSTOM_FENCE_GATE = Make("minecraft:block/template_custom_fence_gate", TextureKey.PARTICLE, TextureKey.TEXTURE);
	public static final Model TEMPLATE_CUSTOM_FENCE_GATE_OPEN = Make("minecraft:block/template_custom_fence_gate_open", "_open", TextureKey.PARTICLE, TextureKey.TEXTURE);
	public static final Model TEMPLATE_CUSTOM_FENCE_GATE_WALL = Make("minecraft:block/template_custom_fence_gate_wall", "_wall", TextureKey.PARTICLE, TextureKey.TEXTURE);
	public static final Model TEMPLATE_CUSTOM_FENCE_GATE_WALL_OPEN = Make("minecraft:block/template_custom_fence_gate_wall_open", "_wall_open", TextureKey.PARTICLE, TextureKey.TEXTURE);
	public static final Model TEMPLATE_GLASS_TRAPDOOR_BOTTOM = Make("block/_template_glass_trapdoor_bottom", "_bottom", TextureKey.TOP, TextureKey.SIDE);
	public static final Model TEMPLATE_GLASS_TRAPDOOR_OPEN = Make("block/_template_glass_trapdoor_open", "_open", TextureKey.TOP, TextureKey.SIDE);
	public static final Model TEMPLATE_GLASS_TRAPDOOR_TOP = Make("block/_template_glass_trapdoor_top", "_top", TextureKey.TOP, TextureKey.SIDE);
	public static final Model TEMPLATE_GLAZED_TERRACOTTA_SLAB = Make("block/_template_glazed_terracotta_slab", TextureKey.PATTERN);
	public static final Model TEMPLATE_GLAZED_TERRACOTTA_SLAB_TOP = Make("block/_template_glazed_terracotta_slab_top", "_top", TextureKey.PATTERN);
	public static final Model TEMPLATE_HEAVY_CHAIN = Make("block/_template_heavy_chain", TextureKey.ALL);
	public static final Model TEMPLATE_LADDER = Make("block/_template_ladder", TextureKey.TEXTURE);
	public static final Model TEMPLATE_LECTERN = Make("block/_template_lectern", TextureKey.BOTTOM, ModTextureKeys.BASE, TextureKey.FRONT, ModTextureKeys.SIDES, TextureKey.TOP);
	public static final Model TEMPLATE_MAGMA_CUBE_LANTERN = Make("block/_template_magma_cube_lantern", TextureKey.TEXTURE);
	public static final Model TEMPLATE_MULTI_TEXTURE_CARPET = Make("block/_template_multi_texture_carpet", TextureKey.PARTICLE, TextureKey.NORTH, TextureKey.SOUTH, TextureKey.EAST, TextureKey.WEST, TextureKey.UP, TextureKey.DOWN);
	public static final Model TEMPLATE_MULTI_TEXTURE_SLAB = Make("block/_template_multi_texture_slab", TextureKey.PARTICLE, TextureKey.NORTH, TextureKey.SOUTH, TextureKey.EAST, TextureKey.WEST, TextureKey.UP, TextureKey.DOWN);
	public static final Model TEMPLATE_MULTI_TEXTURE_SLAB_TOP = Make("block/_template_multi_texture_slab_top", "_top", TextureKey.PARTICLE, TextureKey.NORTH, TextureKey.SOUTH, TextureKey.EAST, TextureKey.WEST, TextureKey.UP, TextureKey.DOWN);
	public static final Model TEMPLATE_POST = Make("block/_template_post", TextureKey.ALL);
	public static final Model TEMPLATE_SIMPLE_HAMMER = Make("item/_template_simple_hammer", TextureKey.TEXTURE);
	public static final Model TEMPLATE_SLIME_LANTERN = Make("block/_template_slime_lantern", TextureKey.TEXTURE);
	public static final Model TEMPLATE_THICK_TORCH = Make("block/_template_thick_torch", TextureKey.ALL);
	public static final Model TEMPLATE_THIN_TRAPDOOR_BOTTOM = Make("block/_template_thin_trapdoor_bottom", "_bottom", TextureKey.TEXTURE);
	public static final Model TEMPLATE_THIN_TRAPDOOR_OPEN = Make("block/_template_thin_trapdoor_open", "_open", TextureKey.TEXTURE);
	public static final Model TEMPLATE_THIN_TRAPDOOR_TOP = Make("block/_template_thin_trapdoor_top", "_top", TextureKey.TEXTURE);
	public static final Model TEMPLATE_WOODCUTTER = Make("block/_template_woodcutter", ModTextureKeys.PLANKS);
	//<editor-fold desc="Plushies">
	public static final Model TEMPLATE_PLUSHIE_ = Make("block/plushie/", TextureKey.ALL, TextureKey.PARTICLE);
	public static final Model TEMPLATE_PLUSHIE_ALLAY = Make("block/plushie/allay", TextureKey.ALL, TextureKey.PARTICLE);
	public static final Model TEMPLATE_PLUSHIE_AXOLOTL = Make("block/plushie/axolotl", TextureKey.ALL, TextureKey.PARTICLE);
	public static final Model TEMPLATE_PLUSHIE_BAT = Make("block/plushie/bat", TextureKey.ALL, TextureKey.PARTICLE);
	public static final Model TEMPLATE_PLUSHIE_BEAR = Make("block/plushie/bear", TextureKey.ALL, TextureKey.PARTICLE);
	public static final Model TEMPLATE_PLUSHIE_BEE = Make("block/plushie/bee", TextureKey.ALL, TextureKey.PARTICLE);
	public static final Model TEMPLATE_PLUSHIE_CAMEL = Make("block/plushie/camel", TextureKey.ALL, TextureKey.PARTICLE);
	public static final Model TEMPLATE_PLUSHIE_CAT = Make("block/plushie/cat", TextureKey.ALL, TextureKey.PARTICLE);
	public static final Model TEMPLATE_PLUSHIE_CHICKEN = Make("block/plushie/chicken", TextureKey.ALL, TextureKey.PARTICLE);
	public static final Model TEMPLATE_PLUSHIE_COW = Make("block/plushie/cow", TextureKey.ALL, TextureKey.PARTICLE);
	public static final Model TEMPLATE_PLUSHIE_CREEPER = Make("block/plushie/creeper", TextureKey.ALL, TextureKey.PARTICLE);
	public static final Model TEMPLATE_PLUSHIE_DOLPHIN = Make("block/plushie/dolphin", TextureKey.ALL, TextureKey.PARTICLE);
	public static final Model TEMPLATE_PLUSHIE_DRAGON = Make("block/plushie/dragon", TextureKey.ALL, TextureKey.PARTICLE);
	public static final Model TEMPLATE_PLUSHIE_ENDERMAN = Make("block/plushie/enderman", TextureKey.ALL, TextureKey.PARTICLE);
	public static final Model TEMPLATE_PLUSHIE_FANCY_CHICKEN = Make("block/plushie/fancy_chicken", TextureKey.ALL, TextureKey.PARTICLE);
	public static final Model TEMPLATE_PLUSHIE_FOX = Make("block/plushie/fox", TextureKey.ALL, TextureKey.PARTICLE);
	public static final Model TEMPLATE_PLUSHIE_FROG = Make("block/plushie/frog", TextureKey.ALL, TextureKey.PARTICLE);
	public static final Model TEMPLATE_PLUSHIE_GOAT = Make("block/plushie/goat", TextureKey.ALL, TextureKey.PARTICLE);
	public static final Model TEMPLATE_PLUSHIE_LLAMA = Make("block/plushie/llama", TextureKey.ALL, TextureKey.PARTICLE);
	public static final Model TEMPLATE_PLUSHIE_PANDA = Make("block/plushie/panda", TextureKey.ALL, TextureKey.PARTICLE);
	public static final Model TEMPLATE_PLUSHIE_PARROT = Make("block/plushie/parrot", TextureKey.ALL, TextureKey.PARTICLE);
	public static final Model TEMPLATE_PLUSHIE_PIG = Make("block/plushie/pig", TextureKey.ALL, TextureKey.PARTICLE);
	public static final Model TEMPLATE_PLUSHIE_SHEARED_SHEEP = Make("block/plushie/sheared_sheep", TextureKey.ALL, TextureKey.PARTICLE);
	public static final Model TEMPLATE_PLUSHIE_SHEARED_SNOW_GOLEM = Make("block/plushie/sheared_snow_golem", TextureKey.ALL, TextureKey.PARTICLE);
	public static final Model TEMPLATE_PLUSHIE_SHEEP = Make("block/plushie/sheep", TextureKey.ALL, TextureKey.PARTICLE);
	public static final Model TEMPLATE_PLUSHIE_SLIME = Make("block/plushie/slime", TextureKey.ALL, TextureKey.PARTICLE);
	public static final Model TEMPLATE_PLUSHIE_SNOW_GOLEM = Make("block/plushie/snow_golem", TextureKey.ALL, TextureKey.PARTICLE);
	public static final Model TEMPLATE_PLUSHIE_TADPOLE = Make("block/plushie/tadpole", TextureKey.ALL, TextureKey.PARTICLE);
	public static final Model TEMPLATE_PLUSHIE_TURTLE = Make("block/plushie/turtle", TextureKey.ALL, TextureKey.PARTICLE);
	public static final Model TEMPLATE_PLUSHIE_WARDEN = Make("block/plushie/warden", TextureKey.ALL, TextureKey.PARTICLE);
	public static final Model TEMPLATE_PLUSHIE_WOLF = Make("block/plushie/wolf", TextureKey.ALL, TextureKey.PARTICLE);
	//</editor-fold>

	public static Model Make(String path, TextureKey... requiredTextureKeys) {
		return new Model(Optional.of(ModId.ID(path)), Optional.empty(), requiredTextureKeys);
	}
	public static Model Make(String path, String variant, TextureKey... requiredTextureKeys) {
		return new Model(Optional.of(ModId.ID(path)), Optional.of(variant), requiredTextureKeys);
	}
}
