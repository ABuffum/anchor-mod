package haven.features;

import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class MangroveSaplingGenerator extends SaplingGenerator {
	private final float tallChance;

	public MangroveSaplingGenerator(float tallChance) {
		this.tallChance = tallChance;
	}

	@Nullable
	@Override
	protected ConfiguredFeature<TreeFeatureConfig, ?> getTreeFeature(Random random, boolean bees) {
		return null;
	}
}