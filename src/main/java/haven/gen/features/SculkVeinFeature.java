package haven.gen.features;

import com.mojang.serialization.Codec;
import haven.HavenMod;
import haven.blocks.sculk.SculkVeinBlock;
import haven.util.StructureUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.List;
import java.util.Random;

public class SculkVeinFeature extends Feature<SculkVeinFeatureConfig> {
	public SculkVeinFeature(Codec<SculkVeinFeatureConfig> codec) {
		super(codec);
	}

	public boolean generate(FeatureContext<SculkVeinFeatureConfig> context) {
		StructureWorldAccess structureWorldAccess = context.getWorld();
		BlockPos blockPos = context.getOrigin();
		Random random = context.getRandom();
		SculkVeinFeatureConfig config = context.getConfig();
		if (!SculkVeinFeature.isAirOrWater(structureWorldAccess.getBlockState(blockPos))) return false;
		List<Direction> list = config.shuffleDirections(random);
		if (SculkVeinFeature.generate(structureWorldAccess, blockPos, structureWorldAccess.getBlockState(blockPos), config, random, list)) {
			return true;
		}
		BlockPos.Mutable mutable = blockPos.mutableCopy();
		block0: for (Direction direction : list) {
			mutable.set(blockPos);
			List<Direction> list2 = config.shuffleDirections(random, direction.getOpposite());
			for (int i = 0; i < config.searchRange; ++i) {
				mutable.set(blockPos, direction);
				BlockState blockState = structureWorldAccess.getBlockState(mutable);
				if (!isAirOrWater(blockState) && !blockState.isOf(HavenMod.SCULK_VEIN.BLOCK)) break;
				if (!SculkVeinFeature.generate(structureWorldAccess, mutable, blockState, config, random, list2)) continue;
				return true;
			}
		}
		return false;
	}

	public static boolean generate(StructureWorldAccess world, BlockPos pos, BlockState state, SculkVeinFeatureConfig config, Random random, List<Direction> directions) {
		BlockPos.Mutable mutable = pos.mutableCopy();
		for (Direction direction : directions) {
			BlockState blockState = world.getBlockState(mutable.set(pos, direction));
			if (!StructureUtil.contains(config.canPlaceOn, blockState)) continue;
			BlockState blockState2 = ((SculkVeinBlock)HavenMod.SCULK_VEIN.BLOCK).withDirection(state, world, pos, direction);
			if (blockState2 == null) return false;
			world.setBlockState(pos, blockState2, Block.NOTIFY_ALL);
			world.getChunk(pos).markBlockForPostProcessing(pos);
			if (random.nextFloat() < config.spreadChance) ((SculkVeinBlock)HavenMod.SCULK_VEIN.BLOCK).getGrower().grow(blockState2, world, pos, direction, random, true);
			return true;
		}
		return false;
	}

	private static boolean isAirOrWater(BlockState state) {
		return state.isAir() || state.isOf(Blocks.WATER);
	}
}
