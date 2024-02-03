package fun.wich.gen.feature;

import com.mojang.serialization.Codec;
import fun.wich.ModBase;
import fun.wich.block.sculk.SculkVeinBlock;
import fun.wich.gen.data.tag.ModBlockTags;
import fun.wich.util.CollectionUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SculkVeinFeature extends Feature<DefaultFeatureConfig> {
	public SculkVeinFeature(Codec<DefaultFeatureConfig> codec) { super(codec); }

	public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
		StructureWorldAccess world = context.getWorld();
		BlockPos blockPos = context.getOrigin();
		Random random = context.getRandom();
		if (SculkVeinFeature.notAirOrWater(world.getBlockState(blockPos))) return false;
		List<Direction> list = shuffleDirections(random);
		if (SculkVeinFeature.generate(world, blockPos, world.getBlockState(blockPos), random, list)) return true;
		BlockPos.Mutable mutable = blockPos.mutableCopy();
		for (Direction direction : list) {
			mutable.set(blockPos);
			List<Direction> list2 = shuffleDirections(random, direction.getOpposite());
			for (int i = 0; i < 20; ++i) {
				mutable.set(blockPos, direction);
				BlockState blockState = world.getBlockState(mutable);
				if (notAirOrWater(blockState) && !blockState.isOf(ModBase.SCULK_VEIN.asBlock())) break;
				if (!generate(world, mutable, blockState, random, list2)) continue;
				return true;
			}
		}
		return false;
	}

	public static boolean generate(StructureWorldAccess world, BlockPos pos, BlockState state, Random random, List<Direction> directions) {
		BlockPos.Mutable mutable = pos.mutableCopy();
		for (Direction direction : directions) {
			BlockState blockState = world.getBlockState(mutable.set(pos, direction));
			if (!blockState.isIn(ModBlockTags.SCULK_VEIN_CAN_PLACE_ON)) continue;
			BlockState blockState2 = ((SculkVeinBlock) ModBase.SCULK_VEIN.asBlock()).withDirection(state, world, pos, direction);
			if (blockState2 == null) return false;
			world.setBlockState(pos, blockState2, Block.NOTIFY_ALL);
			world.getChunk(pos).markBlockForPostProcessing(pos);
			((SculkVeinBlock)ModBase.SCULK_VEIN.asBlock()).getGrower().grow(blockState2, world, pos, direction, random, true);
			return true;
		}
		return false;
	}

	public List<Direction> shuffleDirections(Random random, Direction excluded) {
		return CollectionUtil.copyShuffled(Arrays.stream(Direction.values()).filter(direction -> direction != excluded), random);
	}

	public List<Direction> shuffleDirections(Random random) {
		return CollectionUtil.copyShuffled(Arrays.stream(Direction.values()), random);
	}

	private static boolean notAirOrWater(BlockState state) { return !state.isAir() && !state.isOf(Blocks.WATER); }
}
