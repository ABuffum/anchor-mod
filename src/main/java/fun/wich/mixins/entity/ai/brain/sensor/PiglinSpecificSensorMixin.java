package fun.wich.mixins.entity.ai.brain.sensor;

import com.google.common.collect.ImmutableList;
import fun.wich.ModBase;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.PiglinSpecificSensor;
import net.minecraft.entity.mob.*;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Optional;

@Mixin(PiglinSpecificSensor.class)
public class PiglinSpecificSensorMixin {

	@Inject(method="sense", at=@At("TAIL"))
	protected void OverrideNearestVisible(ServerWorld world, LivingEntity entity, CallbackInfo ci) {
		Brain<?> brain = entity.getBrain();
		Optional<LivingEntity> optional5 = Optional.empty();
		List<LivingEntity> list = brain.getOptionalMemory(MemoryModuleType.VISIBLE_MOBS).orElse(ImmutableList.of());
		for (LivingEntity livingEntity : list) {
			if (optional5.isPresent() || !(PiglinBrain.isZombified(livingEntity.getType()) || ModBase.IS_ZOMBIFIED_PIGLIN.isActive(livingEntity))) continue;
			optional5 = Optional.of(livingEntity);
		}
		brain.remember(MemoryModuleType.NEAREST_VISIBLE_ZOMBIFIED, optional5);
	}
}
