package fun.mousewich.gen;

import fun.mousewich.gen.carver.ModCarvers;
import fun.mousewich.gen.feature.SculkPatchFeature;
import fun.mousewich.gen.feature.config.SculkPatchFeatureConfig;
import fun.mousewich.ModBase;
import fun.mousewich.sound.ModSoundEvents;
import net.minecraft.block.Blocks;
import net.minecraft.client.sound.MusicType;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.sound.MusicSound;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.carver.ConfiguredCarvers;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.Feature;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DeepDarkBiome {

	public static final Feature<SculkPatchFeatureConfig> SCULK_PATCH_FEATURE = new SculkPatchFeature(SculkPatchFeatureConfig.CODEC);
	public static final ConfiguredFeature<SculkPatchFeatureConfig, ?> SCULK_PATCH_DEEP_DARK_FEATURE = SCULK_PATCH_FEATURE.configure(new SculkPatchFeatureConfig(10, 32, 64, 0, 1, ConstantIntProvider.create(0), 0.5f));
	public static final ConfiguredFeature<SculkPatchFeatureConfig, ?> SCULK_PATCH_ANCIENT_CITY_FEATURE = SCULK_PATCH_FEATURE.configure(new SculkPatchFeatureConfig(10, 32, 64, 0, 1, UniformIntProvider.create(1, 3), 0.5f));

	public static void addSculk(GenerationSettings.Builder builder) {
		//builder.feature(GenerationStep.Feature.UNDERGROUND_DECORATION, SCULK_VEIN_FEATURE);
		builder.feature(GenerationStep.Feature.UNDERGROUND_DECORATION, SCULK_PATCH_DEEP_DARK_FEATURE);
	}

	public static Biome createDeepDark() {
		SpawnSettings.Builder builder = new SpawnSettings.Builder();
		GenerationSettings.Builder builder2 = new GenerationSettings.Builder();
		builder2.carver(GenerationStep.Carver.AIR, ConfiguredCarvers.CAVE);
		builder2.carver(GenerationStep.Carver.AIR, ModCarvers.CAVE_EXTRA_UNDERGROUND);
		builder2.carver(GenerationStep.Carver.AIR, ConfiguredCarvers.CANYON);
		DefaultBiomeFeatures.addAmethystGeodes(builder2);
		DefaultBiomeFeatures.addDungeons(builder2);
		DefaultBiomeFeatures.addMineables(builder2);
		DefaultBiomeFeatures.addFrozenTopLayer(builder2);
		DefaultBiomeFeatures.addPlainsTallGrass(builder2);
		DefaultBiomeFeatures.addDefaultOres(builder2);//, true); //TODO: Large copper ores
		DefaultBiomeFeatures.addDefaultDisks(builder2);
		DefaultBiomeFeatures.addPlainsFeatures(builder2);
		DefaultBiomeFeatures.addDefaultMushrooms(builder2);
		DefaultBiomeFeatures.addDefaultVegetation(builder2);
		addSculk(builder2);
		MusicSound musicSound = MusicType.createIngameMusic(ModSoundEvents.MUSIC_OVERWORLD_DEEP_DARK);
		return createBiome(Biome.Precipitation.RAIN, 0.8f, 0.4f, builder, builder2, musicSound);
	}
	private static Biome createBiome(Biome.Precipitation precipitation, float temperature, float downfall, SpawnSettings.Builder spawnSettings, GenerationSettings.Builder generationSettings, @Nullable MusicSound music) {
		return new Biome.Builder().precipitation(precipitation).temperature(temperature).downfall(downfall).effects(new BiomeEffects.Builder().waterColor(4159204).waterFogColor(329011).fogColor(12638463).skyColor(getSkyColor(temperature)).moodSound(BiomeMoodSound.CAVE).music(music).build()).spawnSettings(spawnSettings.build()).generationSettings(generationSettings.build()).build();
	}
	protected static int getSkyColor(float temperature) {
		float f = MathHelper.clamp(temperature / 3.0f, -1.0f, 1.0f);
		return MathHelper.hsvToRgb(0.62222224f - f * 0.05f, 0.5f + f * 0.1f, 1.0f);
	}
}
