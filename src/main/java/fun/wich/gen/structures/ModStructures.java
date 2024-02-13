package fun.wich.gen.structures;

import com.google.common.collect.ImmutableList;
import fun.wich.gen.data.tag.ModBlockTags;
import net.minecraft.block.Blocks;
import net.minecraft.structure.processor.*;
import net.minecraft.structure.rule.AlwaysTrueRuleTest;
import net.minecraft.structure.rule.RandomBlockMatchRuleTest;
import net.minecraft.util.Identifier;

public class ModStructures {
	public static final StructureProcessorList ANCIENT_CITY_START_DEGRADATION = new StructureProcessorList(ImmutableList.of(new RuleStructureProcessor(ImmutableList.of(new StructureProcessorRule(new RandomBlockMatchRuleTest(Blocks.DEEPSLATE_BRICKS, 0.3f), AlwaysTrueRuleTest.INSTANCE, Blocks.CRACKED_DEEPSLATE_BRICKS.getDefaultState()), new StructureProcessorRule(new RandomBlockMatchRuleTest(Blocks.DEEPSLATE_TILES, 0.3f), AlwaysTrueRuleTest.INSTANCE, Blocks.CRACKED_DEEPSLATE_TILES.getDefaultState()), new StructureProcessorRule(new RandomBlockMatchRuleTest(Blocks.SOUL_LANTERN, 0.05f), AlwaysTrueRuleTest.INSTANCE, Blocks.AIR.getDefaultState()))), new ProtectedBlocksStructureProcessor(new Identifier("features_cannot_replace"))));
	public static final StructureProcessorList ANCIENT_CITY_GENERIC_DEGRADATION = new StructureProcessorList(ImmutableList.of(new LimitedBlockRotStructureProcessor(ModBlockTags.ANCIENT_CITY_REPLACEABLE, 0.95f), new RuleStructureProcessor(ImmutableList.of(new StructureProcessorRule(new RandomBlockMatchRuleTest(Blocks.DEEPSLATE_BRICKS, 0.3f), AlwaysTrueRuleTest.INSTANCE, Blocks.CRACKED_DEEPSLATE_BRICKS.getDefaultState()), new StructureProcessorRule(new RandomBlockMatchRuleTest(Blocks.DEEPSLATE_TILES, 0.3f), AlwaysTrueRuleTest.INSTANCE, Blocks.CRACKED_DEEPSLATE_TILES.getDefaultState()), new StructureProcessorRule(new RandomBlockMatchRuleTest(Blocks.SOUL_LANTERN, 0.05f), AlwaysTrueRuleTest.INSTANCE, Blocks.AIR.getDefaultState()))), new ProtectedBlocksStructureProcessor(new Identifier("features_cannot_replace"))));
	public static final StructureProcessorList ANCIENT_CITY_WALLS_DEGRADATION = new StructureProcessorList(ImmutableList.of(new LimitedBlockRotStructureProcessor(ModBlockTags.ANCIENT_CITY_REPLACEABLE, 0.95f), new RuleStructureProcessor(ImmutableList.of(new StructureProcessorRule(new RandomBlockMatchRuleTest(Blocks.DEEPSLATE_BRICKS, 0.3f), AlwaysTrueRuleTest.INSTANCE, Blocks.CRACKED_DEEPSLATE_BRICKS.getDefaultState()), new StructureProcessorRule(new RandomBlockMatchRuleTest(Blocks.DEEPSLATE_TILES, 0.3f), AlwaysTrueRuleTest.INSTANCE, Blocks.CRACKED_DEEPSLATE_TILES.getDefaultState()), new StructureProcessorRule(new RandomBlockMatchRuleTest(Blocks.DEEPSLATE_TILE_SLAB, 0.3f), AlwaysTrueRuleTest.INSTANCE, Blocks.AIR.getDefaultState()), new StructureProcessorRule(new RandomBlockMatchRuleTest(Blocks.SOUL_LANTERN, 0.05f), AlwaysTrueRuleTest.INSTANCE, Blocks.AIR.getDefaultState()))), new ProtectedBlocksStructureProcessor(new Identifier("features_cannot_replace"))));

	//public static final Structure ANCIENT_CITY = StructureKeys.ANCIENT_CITY, new JigsawStructure(Structures.createConfig(ModBiomeTags.ANCIENT_CITY_HAS_STRUCTURE, Arrays.stream(SpawnGroup.values()).collect(Collectors.toMap(spawnGroup -> spawnGroup, spawnGroup -> new StructureSpawns(StructureSpawns.BoundingBox.STRUCTURE, Pool.empty()))), GenerationStep.Feature.UNDERGROUND_DECORATION, StructureTerrainAdaptation.BEARD_BOX), AncientCityGenerator.CITY_CENTER, Optional.of(new Identifier("city_anchor")), 7, ConstantHeightProvider.create(YOffset.fixed(-27)), false, Optional.empty(), 116);
}