package fun.mousewich.mixins.world;

import com.google.common.collect.ImmutableSet;
import fun.mousewich.block.basic.ModBeehiveBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.poi.PointOfInterestType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashSet;
import java.util.Set;

@Mixin(PointOfInterestType.class)
public class PointOfInterestTypeMixin {
	@Inject(method="getAllStatesOf", at=@At("HEAD"), cancellable=true)
	private static void AddBeehiveBlockStates(Block block, CallbackInfoReturnable<Set<BlockState>> cir) {
		if (block == Blocks.BEEHIVE) {
			Set<BlockState> set = new HashSet<>(block.getStateManager().getStates());
			for (Block beehive : ModBeehiveBlock.BEEHIVES) set.addAll(beehive.getStateManager().getStates());
			cir.setReturnValue(ImmutableSet.copyOf(set));
		}
	}
}
