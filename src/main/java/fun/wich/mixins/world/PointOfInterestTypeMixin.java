package fun.wich.mixins.world;

import com.google.common.collect.ImmutableSet;
import fun.wich.block.basic.ModBarrelBlock;
import fun.wich.block.basic.ModBeehiveBlock;
import fun.wich.block.basic.ModLecternBlock;
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
		Set<Block> blocks = null;
		if (block == Blocks.BEEHIVE) blocks = ModBeehiveBlock.BEEHIVES;
		else if (block == Blocks.LECTERN) blocks = ModLecternBlock.LECTERNS;
		else if (block == Blocks.BARREL) blocks = ModBarrelBlock.BARRELS;
		if (blocks != null) {
			Set<BlockState> set = new HashSet<>(block.getStateManager().getStates());
			for (Block b : blocks) set.addAll(b.getStateManager().getStates());
			cir.setReturnValue(ImmutableSet.copyOf(set));
		}
	}
}
