package haven.blocks.sculk;

import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.chunk.light.ChunkLightProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class SculkTurfBlock extends Block implements Fertilizable {
	private final Block block;
	public Block getBlock() { return block; }
	public SculkTurfBlock(Block block, AbstractBlock.Settings settings) {
		super(settings);
		this.block = block;
		sculkTurf.put(this.block, this);
	}

	private static final Map<Block, SculkTurfBlock> sculkTurf = new HashMap<>();
	public static SculkTurfBlock getSculkTurf(Block block) {
		if (sculkTurf.containsKey(block)) return sculkTurf.get(block);
		return null;
	}


	private static boolean stayAlive(BlockState state, WorldView world, BlockPos pos) {
		BlockPos blockPos = pos.up();
		BlockState blockState = world.getBlockState(blockPos);
		int i = ChunkLightProvider.getRealisticOpacity(world, state, pos, blockState, blockPos, Direction.UP, blockState.getOpacity(world, blockPos));
		return i < world.getMaxLightLevel();
	}

	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		if (!stayAlive(state, world, pos)) {
			world.setBlockState(pos, block.getDefaultState());
		}
	}

	public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
		return world.getBlockState(pos.up()).isAir();
	}

	public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
		return false; //TODO: GROW
	}

	public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
		BlockState blockState = world.getBlockState(pos);
		BlockPos blockPos = pos.up();
		/*
		if (blockState.isOf(Blocks.CRIMSON_NYLIUM)) {
			NetherForestVegetationFeature.generate(world, random, blockPos, ConfiguredFeatures.Configs.CRIMSON_ROOTS_CONFIG, 3, 1);
		} else if (blockState.isOf(Blocks.WARPED_NYLIUM)) {
			NetherForestVegetationFeature.generate(world, random, blockPos, ConfiguredFeatures.Configs.WARPED_ROOTS_CONFIG, 3, 1);
			NetherForestVegetationFeature.generate(world, random, blockPos, ConfiguredFeatures.Configs.NETHER_SPROUTS_CONFIG, 3, 1);
			if (random.nextInt(8) == 0) {
				TwistingVinesFeature.tryGenerateVines(world, random, blockPos, 3, 1, 2);
			}
		}*/
	}
}
