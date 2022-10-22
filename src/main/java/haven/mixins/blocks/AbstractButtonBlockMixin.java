package haven.mixins.blocks;

import haven.events.HavenGameEvent;
import net.minecraft.block.AbstractButtonBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(AbstractButtonBlock.class)
public class AbstractButtonBlockMixin {
	@Inject(method="onUse", at = @At("TAIL"))
	public void onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir) {
		if (!state.get(AbstractButtonBlock.POWERED)) world.emitGameEvent(player, HavenGameEvent.BLOCK_ACTIVATE, pos);
	}

	@Inject(method="tryPowerWithProjectiles", at = @At("TAIL"))
	private void tryPowerWithProjectiles(BlockState state, World world, BlockPos pos, CallbackInfo ci) {
		List<PersistentProjectileEntity> list = world.getNonSpectatingEntities(PersistentProjectileEntity.class, state.getOutlineShape(world, pos).getBoundingBox().offset(pos));
		boolean bl = !list.isEmpty();
		if (bl != state.get(AbstractButtonBlock.POWERED)) {
			world.emitGameEvent(list.stream().findFirst().orElse(null), bl ? HavenGameEvent.BLOCK_ACTIVATE : HavenGameEvent.BLOCK_DEACTIVATE, pos);
		}
	}
}
