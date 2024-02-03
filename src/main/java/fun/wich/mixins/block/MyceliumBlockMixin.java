package fun.wich.mixins.block;

import fun.wich.ModBase;
import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Random;

@Mixin(MyceliumBlock.class)
public class MyceliumBlockMixin extends SpreadableBlock implements Fertilizable {
	protected MyceliumBlockMixin(Settings settings) { super(settings); }

	@Override
	public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
		return world.getBlockState(pos.up()).isAir();
	}
	@Override
	public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) { return true; }
	@Override
	public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
		//Generate Mycelium Roots
		BlockPos blockPos = pos.up();
		if (world.getBlockState(blockPos).isAir()) world.setBlockState(blockPos, ModBase.MYCELIUM_ROOTS.asBlock().getDefaultState());
	}
}
