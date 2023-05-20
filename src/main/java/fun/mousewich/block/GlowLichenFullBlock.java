package fun.mousewich.block;

import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Random;
import java.util.stream.Stream;

public class GlowLichenFullBlock extends Block implements Fertilizable {
	public GlowLichenFullBlock(BlockConvertible block) { this(block.asBlock()); }
	public GlowLichenFullBlock(Block block) { this(Settings.copy(block)); }
	public GlowLichenFullBlock(Settings settings) { super(settings); }

	public static boolean canGrowIn(BlockState state) {
		return state.isAir() || state.isOf(Blocks.GLOW_LICHEN) || state.isOf(Blocks.WATER) && state.getFluidState().isStill();
	}
	public static boolean canFertilize(BlockView world, BlockPos pos) {
		return Stream.of(DIRECTIONS).anyMatch(direction -> canGrowIn(world.getBlockState(pos.offset(direction.getOpposite()))));
	}
	@Override
	public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) { return canFertilize(world, pos); }
	@Override
	public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) { return true; }
	@Override
	public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
		for (Direction direction : DIRECTIONS) {
			BlockPos blockPos = pos.offset(direction);
			BlockState blockState = world.getBlockState(blockPos);
			BooleanProperty property = ConnectingBlock.FACING_PROPERTIES.get(direction.getOpposite());
			if (canGrowIn(blockState)) {
				if (blockState.isOf(Blocks.GLOW_LICHEN)) {
					if (!blockState.get(property)) {
						world.setBlockState(blockPos, blockState.with(property, true), NOTIFY_ALL);
					}
				}
				else {
					BlockState outState = Blocks.GLOW_LICHEN.getDefaultState();
					if (!blockState.isAir()) outState = outState.with(Properties.WATERLOGGED, true);
					world.setBlockState(blockPos, outState.with(property, true), NOTIFY_ALL);
				}
			}
		}
	}
}
