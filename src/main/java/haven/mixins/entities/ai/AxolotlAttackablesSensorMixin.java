package haven.mixins.entities.ai;

import haven.origins.powers.AttackedByAxolotlsPower;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.AxolotlAttackablesSensor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AxolotlAttackablesSensor.class)
public class AxolotlAttackablesSensorMixin {
	@Inject(method="canHunt", at = @At("HEAD"), cancellable = true)
	private void CanHunt(LivingEntity axolotl, LivingEntity target, CallbackInfoReturnable<Boolean> cir) {
		if (!AttackedByAxolotlsPower.getHostility(target).orElse(true)) {
			cir.setReturnValue(!axolotl.getBrain().hasMemoryModule(MemoryModuleType.HAS_HUNTING_COOLDOWN));
		}
	}

	@Inject(method="isAlwaysHostileTo", at = @At("HEAD"), cancellable = true)
	private void IsAlwaysHostileTo(LivingEntity target, CallbackInfoReturnable<Boolean> cir) {
		if (AttackedByAxolotlsPower.getHostility(target).orElse(false)) cir.setReturnValue(true);
	}
}
