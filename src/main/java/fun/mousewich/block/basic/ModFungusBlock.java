package fun.mousewich.block.basic;

import net.minecraft.block.FungusBlock;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.HugeFungusFeatureConfig;

import java.util.function.Supplier;

public class ModFungusBlock extends FungusBlock {
	public ModFungusBlock(Settings settings, Supplier<ConfiguredFeature<HugeFungusFeatureConfig, ?>> feature) {
		super(settings, feature);
	}
}
