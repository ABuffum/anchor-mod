package fun.wich.gen.world;

import fun.wich.ModBase;
import fun.wich.sound.ModSoundEvents;
import net.minecraft.client.sound.MusicType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.sound.MusicSound;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.carver.ConfiguredCarvers;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.surfacebuilder.ConfiguredSurfaceBuilders;

public class ModBiomeCreator {
	private static void addBasicFeatures(GenerationSettings.Builder generationSettings) {
		DefaultBiomeFeatures.addLandCarvers(generationSettings);
		DefaultBiomeFeatures.addAmethystGeodes(generationSettings);
		DefaultBiomeFeatures.addDungeons(generationSettings);
		DefaultBiomeFeatures.addMineables(generationSettings);
		DefaultBiomeFeatures.addSprings(generationSettings);
		DefaultBiomeFeatures.addFrozenTopLayer(generationSettings);
	}

	protected static int getSkyColor(float temperature) {
		float f = MathHelper.clamp(temperature / 3.0f, -1.0f, 1.0f);
		return MathHelper.hsvToRgb(0.62222224f - f * 0.05f, 0.5f + f * 0.1f, 1.0f);
	}
	private static Biome createBiome(Biome.Precipitation precipitation, float temperature, float downfall, SpawnSettings.Builder spawnSettings, GenerationSettings.Builder generationSettings, MusicSound music) {
		return createBiome(precipitation, temperature, downfall, 4159204, 329011, spawnSettings, generationSettings, music);
	}
	private static Biome createBiome(Biome.Precipitation precipitation, float temperature, float downfall, int waterColor, int waterFogColor, SpawnSettings.Builder spawnSettings, GenerationSettings.Builder generationSettings, MusicSound music) {
		return new Biome.Builder()
				.precipitation(precipitation)
				.temperature(temperature)
				.downfall(downfall)
				.effects(new BiomeEffects.Builder()
						.waterColor(waterColor)
						.waterFogColor(waterFogColor)
						.fogColor(12638463)
						.skyColor(getSkyColor(temperature))
						.moodSound(BiomeMoodSound.CAVE)
						.music(music)
						.build())
				.spawnSettings(spawnSettings.build())
				.generationSettings(generationSettings.build()).build();
	}
	private static Biome createBiome(SpawnSettings.Builder spawnSettings, GenerationSettings.Builder generationSettings, MusicSound music) {
		return new Biome.Builder()
				.precipitation(Biome.Precipitation.RAIN)
				.temperature(0.5f)
				.downfall(0.8f)
				.effects(new BiomeEffects.Builder()
						.grassColor(11983713)
						.foliageColor(11983713)
						.waterColor(6141935)
						.waterFogColor(6141935)
						.fogColor(12638463)
						.skyColor(getSkyColor(0.5f))
						.moodSound(BiomeMoodSound.CAVE).music(music)
						.build())
				.spawnSettings(spawnSettings.build())
				.generationSettings(generationSettings.build()).build();
	}

	public static Biome createMangroveSwamp() {
		SpawnSettings.Builder builder = new SpawnSettings.Builder();
		DefaultBiomeFeatures.addBatsAndMonsters(builder);
		builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.SLIME, 1, 1, 1));
		builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(ModBase.FROG_ENTITY, 10, 2, 5));
		builder.spawn(SpawnGroup.WATER_AMBIENT, new SpawnSettings.SpawnEntry(EntityType.TROPICAL_FISH, 25, 8, 8));
		builder.spawn(SpawnGroup.WATER_AMBIENT, new SpawnSettings.SpawnEntry(ModBase.PIRANHA_ENTITY, 5, 1, 5));
		GenerationSettings.Builder builder2 = new GenerationSettings.Builder().surfaceBuilder(ConfiguredSurfaceBuilders.SWAMP);
		DefaultBiomeFeatures.addFossils(builder2);
		addBasicFeatures(builder2);
		DefaultBiomeFeatures.addDefaultOres(builder2);
		ModBiomeFeatures.addMangroveSwampFeatures(builder2);
		builder2.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.SEAGRASS_SWAMP);
		MusicSound musicSound = MusicType.createIngameMusic(ModSoundEvents.MUSIC_OVERWORLD_SWAMP);
		return new Biome.Builder()
				.precipitation(Biome.Precipitation.RAIN).temperature(0.8f).downfall(0.9f).depth(-0.2f).scale(0.2f)
				.category(Biome.Category.SWAMP).effects(new BiomeEffects.Builder()
						.waterColor(3832426).waterFogColor(5077600).fogColor(12638463).skyColor(7972863).foliageColor(9285927)
						.grassColorModifier(BiomeEffects.GrassColorModifier.SWAMP)
						.moodSound(BiomeMoodSound.CAVE).music(musicSound).build())
				.spawnSettings(builder.build()).generationSettings(builder2.build()).build();
	}

	public static Biome createDeepDark() {
		SpawnSettings.Builder builder = new SpawnSettings.Builder();
		GenerationSettings.Builder builder2 = new GenerationSettings.Builder().surfaceBuilder(ConfiguredSurfaceBuilders.GRASS);
		builder2.carver(GenerationStep.Carver.AIR, ConfiguredCarvers.CAVE);
		builder2.carver(GenerationStep.Carver.AIR, ConfiguredCarvers.CANYON);
		DefaultBiomeFeatures.addAmethystGeodes(builder2);
		DefaultBiomeFeatures.addDungeons(builder2);
		DefaultBiomeFeatures.addMineables(builder2);
		DefaultBiomeFeatures.addFrozenTopLayer(builder2);
		DefaultBiomeFeatures.addPlainsTallGrass(builder2);
		DefaultBiomeFeatures.addDefaultOres(builder2);
		DefaultBiomeFeatures.addDefaultDisks(builder2);
		DefaultBiomeFeatures.addPlainsFeatures(builder2);
		DefaultBiomeFeatures.addDefaultMushrooms(builder2);
		DefaultBiomeFeatures.addDefaultVegetation(builder2);
		ModBiomeFeatures.addSculk(builder2);
		MusicSound musicSound = MusicType.createIngameMusic(ModSoundEvents.MUSIC_OVERWORLD_DEEP_DARK);
		return new Biome.Builder()
				.precipitation(Biome.Precipitation.RAIN).temperature(0.8f).downfall(0.4f).depth(0.1f).scale(0.25f)
				.category(Biome.Category.UNDERGROUND).effects(new BiomeEffects.Builder()
						.waterColor(4159204).waterFogColor(329011).fogColor(12638463).skyColor(7972863)
						.moodSound(BiomeMoodSound.CAVE).music(musicSound).build())
				.spawnSettings(builder.build()).generationSettings(builder2.build()).build();
	}

	public static Biome createCherryGrove() {
		GenerationSettings.Builder lookupBackedBuilder = new GenerationSettings.Builder().surfaceBuilder(ConfiguredSurfaceBuilders.GRASS);;
		SpawnSettings.Builder builder = new SpawnSettings.Builder();
		builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.DONKEY, 1, 1, 2)).spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.RABBIT, 2, 2, 6)).spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.SHEEP, 2, 2, 4));
		DefaultBiomeFeatures.addBatsAndMonsters(builder);
		addBasicFeatures(lookupBackedBuilder);
		DefaultBiomeFeatures.addPlainsTallGrass(lookupBackedBuilder);
		DefaultBiomeFeatures.addDefaultOres(lookupBackedBuilder);
		DefaultBiomeFeatures.addDefaultDisks(lookupBackedBuilder);
		ModBiomeFeatures.addCherryGroveFeatures(lookupBackedBuilder);
		DefaultBiomeFeatures.addEmeraldOre(lookupBackedBuilder);
		DefaultBiomeFeatures.addInfestedStone(lookupBackedBuilder);
		MusicSound musicSound = MusicType.createIngameMusic(ModSoundEvents.MUSIC_OVERWORLD_CHERRY_GROVE);
		return new Biome.Builder()
				.precipitation(Biome.Precipitation.RAIN).temperature(0.5f).downfall(0.8f).depth(1.0f).scale(0.5f)
				.category(Biome.Category.EXTREME_HILLS).effects(new BiomeEffects.Builder()
						.grassColor(11983713).foliageColor(11983713)
						.waterColor(6141935).waterFogColor(6141935).fogColor(12638463)
						.skyColor(MathHelper.hsvToRgb(0.6138889f, 0.51666665f, 1.0f))
						.moodSound(BiomeMoodSound.CAVE).music(musicSound).build())
				.spawnSettings(builder.build()).generationSettings(lookupBackedBuilder.build()).build();
	}
}
