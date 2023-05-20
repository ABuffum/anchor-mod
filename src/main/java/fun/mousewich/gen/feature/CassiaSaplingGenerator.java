package fun.mousewich.gen.feature;

import fun.mousewich.ModBase;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class CassiaSaplingGenerator extends SaplingGenerator {
	@Nullable
	protected ConfiguredFeature<TreeFeatureConfig, ?> getTreeFeature(Random random, boolean bees) {
		return ModBase.CASSIA_TREE.getFeature();
	}
}
