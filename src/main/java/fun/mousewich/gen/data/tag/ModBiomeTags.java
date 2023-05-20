package fun.mousewich.gen.data.tag;

import fun.mousewich.ModBase;
import net.fabricmc.fabric.api.tag.TagFactory;
import net.minecraft.tag.Tag;
import net.minecraft.tag.Tag.Identified;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;

public class ModBiomeTags {
	public static final Tag.Identified<Biome> ALLOWS_SURFACE_SLIME_SPAWNS = createMinecraftTag("allows_surface_slime_spawns");
	public static final Tag.Identified<Biome> ANCIENT_CITY_HAS_STRUCTURE = createMinecraftTag("has_structure/ancient_city");
	public static final Tag.Identified<Biome> HAS_CLOSER_WATER_FOG = createMinecraftTag("has_closer_water_fog");
	public static final Tag.Identified<Biome> INCREASED_FIRE_BURNOUT = createMinecraftTag("increased_fire_burnout");
	public static final Tag.Identified<Biome> IS_DEEP_OCEAN = createMinecraftTag("is_deep_ocean");
	public static final Tag.Identified<Biome> IS_END = createMinecraftTag("is_end");
	public static final Tag.Identified<Biome> IS_OCEAN = createMinecraftTag("is_ocean");
	public static final Tag.Identified<Biome> IS_OVERWORLD = createMinecraftTag("is_overworld");
	public static final Tag.Identified<Biome> IS_RIVER = createMinecraftTag("is_river");
	public static final Tag.Identified<Biome> IS_SAVANNA = createMinecraftTag("is_savanna");
	public static final Tag.Identified<Biome> MINESHAFT_BLOCKING = createMinecraftTag("mineshaft_blocking");
	public static final Tag.Identified<Biome> SPAWNS_COLD_VARIANT_FROGS = createMinecraftTag("spawns_cold_variant_frogs");
	public static final Tag.Identified<Biome> SPAWNS_WARM_VARIANT_FROGS = createMinecraftTag("spawns_warm_variant_frogs");
	public static final Tag.Identified<Biome> TRAIL_RUINS_HAS_STRUCTURE = createMinecraftTag("has_structure/trail_ruins");
	public static final Tag.Identified<Biome> WARM_OCEANS = createTag("warm_oceans");
	public static final Tag.Identified<Biome> WATER_ON_MAP_OUTLINES = createMinecraftTag("water_on_map_outlines");

	private static Tag.Identified<Biome> createTag(String name) { return TagFactory.BIOME.create(ModBase.ID(name)); }
	private static Tag.Identified<Biome> createCommonTag(String name) { return TagFactory.BIOME.create(new Identifier("c", name)); }
	private static Tag.Identified<Biome> createMinecraftTag(String name) { return TagFactory.BIOME.create(new Identifier(name)); }
}
