package fun.wich.mixins.block;

import fun.wich.ModBase;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.NetherrackBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(NetherrackBlock.class)
public class NetherrackBlockMixin {
	@Inject(method="grow", at = @At("HEAD"), cancellable = true)
	public void Grow(ServerWorld world, Random random, BlockPos pos, BlockState state, CallbackInfo ci) {
		boolean bl = false, bl2 = false, bl3 = false;
		for (BlockPos blockPos : BlockPos.iterate(pos.add(-1, -1, -1), pos.add(1, 1, 1))) {
			BlockState blockState = world.getBlockState(blockPos);
			bl = bl || blockState.isOf(Blocks.CRIMSON_NYLIUM);
			bl2 = bl2 || blockState.isOf(Blocks.WARPED_NYLIUM);
			bl3 = bl3 || blockState.isOf(ModBase.GILDED_NYLIUM.asBlock());
			if (bl && bl2 && bl3) break;
		}
		if (bl && bl2 && bl3) {
			int r = random.nextInt(3);
			world.setBlockState(pos, r == 0 ? Blocks.WARPED_NYLIUM.getDefaultState()
					: r == 1 ? Blocks.CRIMSON_NYLIUM.getDefaultState()
					: ModBase.GILDED_NYLIUM.asBlock().getDefaultState(), Block.NOTIFY_ALL);
		}
		else if (bl && bl3) {
			world.setBlockState(pos, random.nextBoolean() ? ModBase.GILDED_NYLIUM.asBlock().getDefaultState() : Blocks.CRIMSON_NYLIUM.getDefaultState(), Block.NOTIFY_ALL);
			ci.cancel();
		}
		else if (bl2 && bl3) {
			world.setBlockState(pos, random.nextBoolean() ? Blocks.WARPED_NYLIUM.getDefaultState() : ModBase.GILDED_NYLIUM.asBlock().getDefaultState(), Block.NOTIFY_ALL);
			ci.cancel();
		}
		else if (bl3) {
			world.setBlockState(pos, ModBase.GILDED_NYLIUM.asBlock().getDefaultState(), Block.NOTIFY_ALL);
			ci.cancel();
		}
	}
}
