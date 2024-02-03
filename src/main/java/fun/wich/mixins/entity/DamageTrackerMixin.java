package fun.wich.mixins.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.LadderBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageTracker;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(DamageTracker.class)
public abstract class DamageTrackerMixin {
	@Shadow @Final private LivingEntity entity;
	@Shadow private String fallDeathSuffix;

	@Inject(method="setFallDeathSuffix", at = @At("HEAD"), cancellable = true)
	public void SetFallDeathSuffixForLadders(CallbackInfo ci) {
		Optional<BlockPos> optional = this.entity.getClimbingPos();
		if (optional.isPresent()) {
			BlockState blockState = this.entity.world.getBlockState(optional.get());
			if (blockState.getBlock() instanceof LadderBlock) {
				this.fallDeathSuffix = "ladder";
				ci.cancel();
			}
		}
	}
}
