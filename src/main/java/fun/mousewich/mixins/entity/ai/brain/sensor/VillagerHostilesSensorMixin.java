package fun.mousewich.mixins.entity.ai.brain.sensor;

import fun.mousewich.origins.power.VillagersFleePower;
import io.github.apace100.apoli.component.PowerHolderComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.sensor.VillagerHostilesSensor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(VillagerHostilesSensor.class)
public class VillagerHostilesSensorMixin {
	@Inject(method="matches", at = @At("HEAD"), cancellable = true)
	protected void Matches(LivingEntity entity, LivingEntity target, CallbackInfoReturnable<Boolean> cir) {
		for (VillagersFleePower power : PowerHolderComponent.getPowers(target, VillagersFleePower.class)) {
			if (power.isActive()) {
				if (target.squaredDistanceTo(entity) <= (double)(power.distance * power.distance)) {
					cir.setReturnValue(true);
				}
			}
		}
	}
}
