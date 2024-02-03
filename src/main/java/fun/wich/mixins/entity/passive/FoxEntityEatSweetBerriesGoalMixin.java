package fun.wich.mixins.entity.passive;

import net.minecraft.block.BlockState;
import net.minecraft.block.CaveVines;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(FoxEntity.EatSweetBerriesGoal.class)
public class FoxEntityEatSweetBerriesGoalMixin {
	@Redirect(method="pickGlowBerries", at = @At(value="INVOKE", target="Lnet/minecraft/block/CaveVines;pickBerries(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/util/ActionResult;"))
	private ActionResult InjectBlockChangeEventIntoPickedGlowBerries(BlockState state, World world, BlockPos pos) {
		ActionResult result = CaveVines.pickBerries(state, world, pos);
		if (result == ActionResult.SUCCESS || result == ActionResult.CONSUME) {
			world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos);
		}
		return result;
	}
}
