package haven.blocks.basic;

import net.minecraft.block.FungusBlock;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.HugeFungusFeatureConfig;

import java.util.function.Supplier;

public class HavenFungusBlock extends FungusBlock {
	public HavenFungusBlock(Settings settings, Supplier<ConfiguredFeature<HugeFungusFeatureConfig, ?>> feature) {
		super(settings, feature);
	}
}
