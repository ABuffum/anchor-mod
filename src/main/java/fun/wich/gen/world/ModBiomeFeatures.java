package fun.wich.gen.world;

import fun.wich.ModBase;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.ConfiguredFeatures;

public class ModBiomeFeatures {
	public static void addMangroveSwampFeatures(GenerationSettings.Builder builder) {
		builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModBase.MANGROVE_CONFIGURED.getFeature());
		builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModBase.TALL_MANGROVE_CONFIGURED.getFeature());
		builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.PATCH_GRASS_NORMAL);
		builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.PATCH_DEAD_BUSH);
		builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.PATCH_WATERLILLY);
	}

	public static void addCherryGroveFeatures(GenerationSettings.Builder builder) {
		builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModBase.FLOWER_CHERRY_CONFIGURED.getFeature());
		builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModBase.CHERRY_CONFIGURED.getFeature());
		builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModBase.CHERRY_BEES_005_CONFIGURED.getFeature());
	}

	public static void addSculk(GenerationSettings.Builder builder) {
		builder.feature(GenerationStep.Feature.UNDERGROUND_DECORATION, ModBase.SCULK_VEIN_CONFIGURED.getFeature());
		builder.feature(GenerationStep.Feature.UNDERGROUND_DECORATION, ModBase.SCULK_PATCH_DEEP_DARK_CONFIGURED.getFeature());
	}
}
