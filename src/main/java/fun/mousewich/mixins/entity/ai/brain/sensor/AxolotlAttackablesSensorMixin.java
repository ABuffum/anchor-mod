package fun.mousewich.mixins.entity.ai.brain.sensor;

import fun.mousewich.origins.power.HuntedByAxolotlsPower;
import fun.mousewich.origins.power.MobHostilityPower;
import fun.mousewich.origins.power.PowersUtil;
import net.minecraft.entity.EntityType;
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
		if (PowersUtil.Active(target, HuntedByAxolotlsPower.class)) {
			cir.setReturnValue(!axolotl.getBrain().hasMemoryModule(MemoryModuleType.HAS_HUNTING_COOLDOWN));
		}
	}

	@Inject(method="isAlwaysHostileTo", at = @At("HEAD"), cancellable = true)
	private void IsAlwaysHostileTo(LivingEntity target, CallbackInfoReturnable<Boolean> cir) {
		if (MobHostilityPower.shouldBeHostile(target, EntityType.AXOLOTL)) cir.setReturnValue(true);
	}
}
