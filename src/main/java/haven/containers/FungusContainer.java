package haven.containers;

import haven.blocks.HavenSaplingBlock;
import haven.blocks.basic.HavenFungusBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.FungusBlock;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.HugeFungusFeatureConfig;

import java.util.function.Supplier;

public class FungusContainer extends PottedBlockContainer {
	public static AbstractBlock.Settings Settings(MapColor mapColor) {
		return AbstractBlock.Settings.of(Material.PLANT, mapColor).breakInstantly().noCollision().sounds(BlockSoundGroup.FUNGUS);
	}

	public FungusContainer(Supplier<ConfiguredFeature<HugeFungusFeatureConfig, ?>> fungusGenerator, MapColor mapColor) {
		this(fungusGenerator, Settings(mapColor));
	}
	public FungusContainer(Supplier<ConfiguredFeature<HugeFungusFeatureConfig, ?>> fungusGenerator, AbstractBlock.Settings settings) {
		super(new HavenFungusBlock(settings, fungusGenerator));
	}
}
