package fun.mousewich.mixins.entity.ai.brain;

import fun.mousewich.ModBase;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.mob.HoglinBrain;
import net.minecraft.entity.mob.HoglinEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HoglinBrain.class)
public class HoglinBrainMixin {
	@Inject(method="targetEnemy", at=@At("HEAD"), cancellable=true)
	private static void targetEnemy(HoglinEntity hoglin, LivingEntity target, CallbackInfo ci) {
		if (hoglin.getBrain().hasActivity(Activity.AVOID) && ModBase.IS_PIGLIN_POWER.isActive(target)) ci.cancel();
	}
}
