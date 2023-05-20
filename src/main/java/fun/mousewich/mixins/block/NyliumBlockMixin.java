package fun.mousewich.mixins.block;

import fun.mousewich.ModBase;
import net.minecraft.block.BlockState;
import net.minecraft.block.NyliumBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.NetherForestVegetationFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(NyliumBlock.class)
public class NyliumBlockMixin {
	@Inject(method="grow", at = @At("TAIL"))
	private void Grow(ServerWorld world, Random random, BlockPos pos, BlockState state, CallbackInfo ci) {
		if (world.getBlockState(pos).isOf(ModBase.GILDED_NYLIUM.asBlock())) {
			NetherForestVegetationFeature.generate(world, random, pos.up(), ModBase.GILDED_ROOTS_CONFIG, 3, 1);
		}
	}
}
