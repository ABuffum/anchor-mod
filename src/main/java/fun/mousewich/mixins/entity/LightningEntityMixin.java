package fun.mousewich.mixins.entity;

import fun.mousewich.util.OxidationScale;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LightningRodBlock;
import net.minecraft.block.Oxidizable;
import net.minecraft.entity.LightningEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Iterator;
import java.util.Optional;

@Mixin(LightningEntity.class)
public class LightningEntityMixin {
	@Shadow
	private static void cleanOxidizationAround(World world, BlockPos pos, BlockPos.Mutable mutablePos, int count) { }

	@Inject(method = "cleanOxidization", at = @At("HEAD"), cancellable = true)
	private static void CleanOxidation(World world, BlockPos pos, CallbackInfo ci) {
		BlockState blockState = world.getBlockState(pos);
		BlockPos blockPos2;
		BlockState blockState3;
		if (blockState.isOf(Blocks.LIGHTNING_ROD)) {
			blockPos2 = pos.offset(blockState.get(LightningRodBlock.FACING).getOpposite());
			blockState3 = world.getBlockState(blockPos2);
		} else {
			blockPos2 = pos;
			blockState3 = blockState;
		}
		if (blockState3.getBlock() instanceof Oxidizable) {
			world.setBlockState(blockPos2, OxidationScale.getUnaffectedState(world.getBlockState(blockPos2)));
			BlockPos.Mutable mutable = pos.mutableCopy();
			int i = world.random.nextInt(3) + 3;
			for(int j = 0; j < i; ++j) {
				int k = world.random.nextInt(8) + 1;
				cleanOxidizationAround(world, blockPos2, mutable, k);
			}
		}
		ci.cancel();
	}

	@Inject(method="cleanOxidizationAround(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)Ljava/util/Optional;", at = @At("HEAD"), cancellable = true)
	private static void CleanOxidationAround(World world, BlockPos pos, CallbackInfoReturnable<Optional<BlockPos>> cir) {
		Iterator<BlockPos> var2 = BlockPos.iterateRandomly(world.random, 10, pos, 1).iterator();
		BlockPos blockPos;
		BlockState blockState;
		do {
			if (!var2.hasNext()) cir.setReturnValue(Optional.empty());
			blockPos = var2.next();
			blockState = world.getBlockState(blockPos);
		} while(!(blockState.getBlock() instanceof Oxidizable));
		BlockPos finalBlockPos = blockPos;
		OxidationScale.getDecreasedState(blockState).ifPresent(state -> world.setBlockState(finalBlockPos, state));
		world.syncWorldEvent(WorldEvents.ELECTRICITY_SPARKS, blockPos, -1);
		cir.setReturnValue(Optional.of(blockPos));
	}
}
