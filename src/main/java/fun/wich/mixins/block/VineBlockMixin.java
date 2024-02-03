package fun.wich.mixins.block;

import fun.wich.ModGameRules;
import net.minecraft.block.BlockState;
import net.minecraft.block.VineBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(VineBlock.class)
public class VineBlockMixin {
	@Inject(method="randomTick", at = @At("HEAD"), cancellable = true)
	private void CancelIfGameruleStopsSpread(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {
		if (!world.getGameRules().getBoolean(ModGameRules.DO_VINES_SPREAD)) ci.cancel();
	}
}
