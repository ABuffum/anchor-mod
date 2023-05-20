package fun.mousewich.gen.feature;

import fun.mousewich.ModBase;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class DogwoodSaplingGenerator extends SaplingGenerator {
	@Nullable
	protected ConfiguredFeature<TreeFeatureConfig, ?> getTreeFeature(Random random, boolean bees) {
		return (switch (random.nextInt(6)) {
			case 0 -> ModBase.PINK_DOGWOOD_TREE_CONFIGURED;
			case 1, 2 -> ModBase.PALE_DOGWOOD_TREE_CONFIGURED;
			default -> ModBase.WHITE_DOGWOOD_TREE_CONFIGURED;
		}).getFeature();
	}
}
