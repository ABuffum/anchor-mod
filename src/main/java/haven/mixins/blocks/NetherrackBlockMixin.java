package haven.mixins.blocks;

import haven.HavenMod;
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

import java.util.Iterator;
import java.util.Random;

@Mixin(NetherrackBlock.class)
public class NetherrackBlockMixin {
	@Inject(method="grow", at = @At("HEAD"), cancellable = true)
	public void Grow(ServerWorld world, Random random, BlockPos pos, BlockState state, CallbackInfo ci) {
		boolean bl = false, bl2 = false, bl3 = false;
		Iterator var7 = BlockPos.iterate(pos.add(-1, -1, -1), pos.add(1, 1, 1)).iterator();
		while(var7.hasNext()) {
			BlockPos blockPos = (BlockPos)var7.next();
			BlockState blockState = world.getBlockState(blockPos);
			bl = bl || blockState.isOf(Blocks.CRIMSON_NYLIUM);
			bl2 = bl2 || blockState.isOf(Blocks.WARPED_NYLIUM);
			bl3 = bl3 || blockState.isOf(HavenMod.GILDED_NYLIUM.BLOCK);
			if (bl && bl2 && bl3) break;
		}
		if (bl && bl2 && bl3) {
			int r = random.nextInt(3);
			world.setBlockState(pos, r == 0 ? Blocks.WARPED_NYLIUM.getDefaultState()
					: r == 1 ? Blocks.CRIMSON_NYLIUM.getDefaultState()
					: HavenMod.GILDED_NYLIUM.BLOCK.getDefaultState(), Block.NOTIFY_ALL);
		}
		else if (bl && bl3) {
			world.setBlockState(pos, random.nextBoolean() ? HavenMod.GILDED_NYLIUM.BLOCK.getDefaultState() : Blocks.CRIMSON_NYLIUM.getDefaultState(), Block.NOTIFY_ALL);
			ci.cancel();
		}
		else if (bl2 && bl3) {
			world.setBlockState(pos, random.nextBoolean() ? Blocks.WARPED_NYLIUM.getDefaultState() : HavenMod.GILDED_NYLIUM.BLOCK.getDefaultState(), Block.NOTIFY_ALL);
			ci.cancel();
		}
		else if (bl3) {
			world.setBlockState(pos, HavenMod.GILDED_NYLIUM.BLOCK.getDefaultState(), Block.NOTIFY_ALL);
			ci.cancel();
		}
	}
}
