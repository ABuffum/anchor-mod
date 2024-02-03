package fun.wich.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.block.MushroomBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.feature.HugeMushroomFeature;
import net.minecraft.world.gen.feature.HugeMushroomFeatureConfig;

public class HugeBlueMushroomFeature extends HugeMushroomFeature {
	public HugeBlueMushroomFeature(Codec<HugeMushroomFeatureConfig> codec) { super(codec); }
	protected void generateCap(WorldAccess world, Random random, BlockPos start, int y, BlockPos.Mutable mutable, HugeMushroomFeatureConfig config) {
		for(int i = y - 1; i <= y; ++i) {
			int j = i < y ? config.foliageRadius : config.foliageRadius - 1;
			int k = config.foliageRadius - 2;
			for(int l = -j; l <= j; ++l) {
				for(int m = -j; m <= j; ++m) {
					if (i >= y || (l == j || l == -j) != (m == j || m == -j)) {
						mutable.set(start, l, i, m);
						if (!world.getBlockState(mutable).isOpaqueFullCube(world, mutable)) {
							BlockState blockState = config.capProvider.getBlockState(random, start);
							if (blockState.contains(MushroomBlock.WEST) && blockState.contains(MushroomBlock.EAST) && blockState.contains(MushroomBlock.NORTH) && blockState.contains(MushroomBlock.SOUTH) && blockState.contains(MushroomBlock.UP)) {
								blockState = blockState
										.with(MushroomBlock.UP, i >= y - 1)
										.with(MushroomBlock.WEST, l < -k)
										.with(MushroomBlock.EAST, l > k)
										.with(MushroomBlock.NORTH, m < -k)
										.with(MushroomBlock.SOUTH, m > k);
							}
							this.setBlockState(world, mutable, blockState);
						}
					}
				}
			}
		}

	}
	protected int getCapSize(int i, int j, int capSize, int y) { return (y < j && y >= j - 3 || y == j) ? capSize : 0; }
}
