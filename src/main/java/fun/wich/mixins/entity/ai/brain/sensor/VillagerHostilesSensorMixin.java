package fun.wich.mixins.entity.ai.brain.sensor;

import fun.wich.entity.ModEntityType;
import fun.wich.origins.power.VillagersFleePower;
import io.github.apace100.apoli.component.PowerHolderComponent;
import net.minecraft.entity.EntityType;
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
		double distance = entity.squaredDistanceTo(target);
		EntityType<?> type = target.getType();
		if (type == ModEntityType.ICEOLOGER_ENTITY) {
			if (distance <= 144) {
				cir.setReturnValue(true);
				return;
			}
		}
		else if (type == ModEntityType.MOUNTAINEER_ENTITY) {
			if (distance <= 100) {
				cir.setReturnValue(true);
				return;
			}
		}
		else if (type == ModEntityType.MAGE_ENTITY) {
			if (distance <= 144) {
				cir.setReturnValue(true);
				return;
			}
		}
		for (VillagersFleePower power : PowerHolderComponent.getPowers(target, VillagersFleePower.class)) {
			if (power.isActive()) {
				if (distance <= (double)(power.distance * power.distance)) {
					cir.setReturnValue(true);
					return;
				}
			}
		}
	}
}
