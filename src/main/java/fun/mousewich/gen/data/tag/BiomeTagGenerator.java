package fun.mousewich.gen.data.tag;

import fun.mousewich.ModBase;
import fun.mousewich.gen.data.ModDatagen;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.tag.Tag;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;

import java.util.Map;
import java.util.Set;

public class BiomeTagGenerator extends FabricTagProvider.DynamicRegistryTagProvider<Biome> {
	public BiomeTagGenerator(FabricDataGenerator dataGenerator) {
		super(dataGenerator, Registry.BIOME_KEY, "worldgen/biomes", ModBase.NAMESPACE + ":biome_tag_generator");
	}

	@Override
	protected void generateTags() {
		for (Map.Entry<Tag.Identified<Biome>, Set<Biome>> entry : ModDatagen.Cache.Tags.BIOME_TAGS.entrySet()) {
			getOrCreateTagBuilder(entry.getKey()).add(entry.getValue().toArray(Biome[]::new));
			entry.getValue().clear();
		}
		ModDatagen.Cache.Tags.BIOME_TAGS.clear();

		getOrCreateTagBuilder(ModBiomeTags.ALLOWS_SURFACE_SLIME_SPAWNS).add(BiomeKeys.SWAMP, ModBase.MANGROVE_SWAMP);
		getOrCreateTagBuilder(ModBiomeTags.ANCIENT_CITY_HAS_STRUCTURE).add(ModBase.DEEP_DARK);
		getOrCreateTagBuilder(ModBiomeTags.HAS_CLOSER_WATER_FOG).add(BiomeKeys.SWAMP, ModBase.MANGROVE_SWAMP);
		getOrCreateTagBuilder(ModBiomeTags.IS_DEEP_OCEAN).add(BiomeKeys.DEEP_FROZEN_OCEAN, BiomeKeys.DEEP_COLD_OCEAN, BiomeKeys.DEEP_OCEAN, BiomeKeys.DEEP_LUKEWARM_OCEAN, BiomeKeys.DEEP_WARM_OCEAN);
		getOrCreateTagBuilder(ModBiomeTags.IS_END).add(BiomeKeys.THE_END, BiomeKeys.END_HIGHLANDS, BiomeKeys.END_MIDLANDS, BiomeKeys.SMALL_END_ISLANDS, BiomeKeys.END_BARRENS);
		getOrCreateTagBuilder(ModBiomeTags.IS_OCEAN).addTag(ModBiomeTags.IS_DEEP_OCEAN).add(BiomeKeys.FROZEN_OCEAN, BiomeKeys.OCEAN, BiomeKeys.COLD_OCEAN, BiomeKeys.LUKEWARM_OCEAN, BiomeKeys.WARM_OCEAN);
		getOrCreateTagBuilder(ModBiomeTags.IS_RIVER).add(BiomeKeys.RIVER, BiomeKeys.FROZEN_RIVER);
		getOrCreateTagBuilder(ModBiomeTags.IS_SAVANNA).add(BiomeKeys.SAVANNA, BiomeKeys.SAVANNA_PLATEAU);
		getOrCreateTagBuilder(ModBiomeTags.MINESHAFT_BLOCKING).add(ModBase.DEEP_DARK);
		getOrCreateTagBuilder(ModBiomeTags.SPAWNS_COLD_VARIANT_FROGS)
				.add(BiomeKeys.ICE_SPIKES)
				.add(BiomeKeys.FROZEN_OCEAN, BiomeKeys.DEEP_FROZEN_OCEAN, BiomeKeys.MOUNTAINS)
				.add(BiomeKeys.MOUNTAIN_EDGE, BiomeKeys.SNOWY_MOUNTAINS, BiomeKeys.TAIGA_MOUNTAINS, BiomeKeys.SNOWY_TAIGA_MOUNTAINS)
				.add(BiomeKeys.SNOWY_TAIGA_HILLS)
				.add(ModBase.DEEP_DARK)
				.add(BiomeKeys.FROZEN_RIVER, BiomeKeys.SNOWY_TAIGA, BiomeKeys.SNOWY_BEACH)
				.addTag(ModBiomeTags.IS_END);
		getOrCreateTagBuilder(ModBiomeTags.SPAWNS_WARM_VARIANT_FROGS)
				.add(BiomeKeys.DESERT, BiomeKeys.WARM_OCEAN)
				.add(BiomeKeys.JUNGLE, BiomeKeys.JUNGLE_EDGE, BiomeKeys.JUNGLE_HILLS)
				.add(BiomeKeys.BAMBOO_JUNGLE, BiomeKeys.BAMBOO_JUNGLE_HILLS)
				.add(BiomeKeys.MODIFIED_JUNGLE, BiomeKeys.MODIFIED_JUNGLE_EDGE)
				.add(ModBase.MANGROVE_SWAMP)
				.addTag(ModBiomeTags.IS_SAVANNA)
				.add(BiomeKeys.NETHER_WASTES, BiomeKeys.BASALT_DELTAS, BiomeKeys.SOUL_SAND_VALLEY)
				.add(BiomeKeys.CRIMSON_FOREST, BiomeKeys.WARPED_FOREST)
				.add(BiomeKeys.BADLANDS, BiomeKeys.BADLANDS_PLATEAU, BiomeKeys.ERODED_BADLANDS)
				.add(BiomeKeys.MODIFIED_BADLANDS_PLATEAU, BiomeKeys.MODIFIED_WOODED_BADLANDS_PLATEAU)
				.add(BiomeKeys.WOODED_BADLANDS_PLATEAU);
		getOrCreateTagBuilder(ModBiomeTags.TRAIL_RUINS_HAS_STRUCTURE)
				.add(BiomeKeys.TAIGA, BiomeKeys.SNOWY_TAIGA, BiomeKeys.TAIGA_MOUNTAINS, BiomeKeys.TAIGA_HILLS)
				.add(BiomeKeys.SNOWY_TAIGA_MOUNTAINS, BiomeKeys.SNOWY_TAIGA_HILLS, BiomeKeys.GIANT_SPRUCE_TAIGA)
				.add(BiomeKeys.GIANT_TREE_TAIGA, BiomeKeys.GIANT_SPRUCE_TAIGA_HILLS, BiomeKeys.GIANT_SPRUCE_TAIGA)
				.add(BiomeKeys.TALL_BIRCH_FOREST, BiomeKeys.TALL_BIRCH_HILLS)
				.add(BiomeKeys.JUNGLE, BiomeKeys.JUNGLE_EDGE, BiomeKeys.JUNGLE_HILLS);
		getOrCreateTagBuilder(ModBiomeTags.WARM_OCEANS).add(BiomeKeys.LUKEWARM_OCEAN, BiomeKeys.WARM_OCEAN, BiomeKeys.DEEP_LUKEWARM_OCEAN);
		getOrCreateTagBuilder(ModBiomeTags.WATER_ON_MAP_OUTLINES).addTag(ModBiomeTags.IS_OCEAN).addTag(ModBiomeTags.IS_RIVER)
				.add(BiomeKeys.SWAMP, ModBase.MANGROVE_SWAMP);
	}
}