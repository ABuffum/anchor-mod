package fun.wich.gen.feature;

import fun.wich.ModBase;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class GrapeSaplingGenerator extends SaplingGenerator {
	@Nullable
	protected ConfiguredFeature<TreeFeatureConfig, ?> getTreeFeature(Random random, boolean bees) {
		return (bees ? ModBase.GRAPE_TREE_BEES : ModBase.GRAPE_TREE).getFeature();
	}
}
