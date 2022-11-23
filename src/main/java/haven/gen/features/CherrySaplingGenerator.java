package haven.gen.features;

import haven.ModBase;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class CherrySaplingGenerator extends SaplingGenerator {
	@Nullable
	protected ConfiguredFeature<TreeFeatureConfig, ?> getTreeFeature(Random random, boolean bees) {
		int rand = random.nextInt(4);
		if (rand == 0) return ModBase.WHITE_CHERRY_TREE;
		else if (rand == 1) return ModBase.PALE_CHERRY_TREE;
		else if (rand == 2) return ModBase.PINK_CHERRY_TREE;
		else return ModBase.CHERRY_TREE;
	}
}
