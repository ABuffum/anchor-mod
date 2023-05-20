package fun.mousewich.gen.decorator;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.VineBlock;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

public class RandomLeavesVineTreeDecorator extends TreeDecorator {
	public static final Codec<RandomLeavesVineTreeDecorator> CODEC = Codec.floatRange(0.0f, 1.0f).fieldOf("probability").xmap(RandomLeavesVineTreeDecorator::new, treeDecorator -> treeDecorator.probability).codec();
	private final float probability;

	@Override
	protected TreeDecoratorType<?> getType() { return TreeDecoratorType.LEAVE_VINE; }

	public RandomLeavesVineTreeDecorator(float probability) { this.probability = probability; }

	@Override
	public void generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, List<BlockPos> logPositions, List<BlockPos> leavesPositions) {
		leavesPositions.forEach(pos -> {
			BlockPos blockPos;
			if (random.nextFloat() < this.probability && Feature.isAir(world, blockPos = pos.west())) {
				placeVines(world, blockPos, VineBlock.EAST, replacer);
			}
			if (random.nextFloat() < this.probability && Feature.isAir(world, blockPos = pos.east())) {
				placeVines(world, blockPos, VineBlock.WEST, replacer);
			}
			if (random.nextFloat() < this.probability && Feature.isAir(world, blockPos = pos.north())) {
				placeVines(world, blockPos, VineBlock.SOUTH, replacer);
			}
			if (random.nextFloat() < this.probability && Feature.isAir(world, blockPos = pos.south())) {
				placeVines(world, blockPos, VineBlock.NORTH , replacer);
			}
		});
	}

	/**
	 * Places a vine at a given position and then up to 4 more vines going downwards.
	 */
	private static void placeVines(TestableWorld world, BlockPos pos, BooleanProperty facing, BiConsumer<BlockPos, BlockState> replacer) {
		placeVine(replacer, pos, facing);
		int i = 4;
		for(pos = pos.down(); Feature.isAir(world, pos) && i > 0; --i) {
			placeVine(replacer, pos, facing);
			pos = pos.down();
		}
	}
}
