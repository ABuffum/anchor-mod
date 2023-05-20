package fun.mousewich.block;

import fun.mousewich.ModBase;
import fun.mousewich.block.basic.ModSlabBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Fertilizable;
import net.minecraft.block.enums.SlabType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Random;

public class GlowLichenSlabBlock extends ModSlabBlock implements Fertilizable {
	public GlowLichenSlabBlock(BlockConvertible block) { super(block); }
	public GlowLichenSlabBlock(Block block) { super(block); }
	public GlowLichenSlabBlock(Settings settings) { super(settings); }

	@Override
	public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
		return state.get(TYPE) == SlabType.DOUBLE && GlowLichenFullBlock.canFertilize(world, pos);
	}
	@Override
	public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) { return state.get(TYPE) == SlabType.DOUBLE; }

	@Override
	public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
		((Fertilizable)ModBase.GLOW_LICHEN_BLOCK.asBlock()).grow(world, random, pos, state);
	}
}
