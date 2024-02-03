package fun.wich.mixins.entity.ai.brain;

import fun.wich.ModBase;
import fun.wich.origins.power.MobHostilityPower;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.task.LookTargetUtil;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.entity.mob.PiglinEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(PiglinBrain.class)
public class PiglinBrainMixin {
	@Shadow
	private static boolean getNearestZombifiedPiglin(PiglinEntity piglin) { return false; }

	@Inject(method="wearsGoldArmor", at = @At("HEAD"), cancellable = true)
	private static void ignoreGoldArmor(LivingEntity target, CallbackInfoReturnable<Boolean> cir) {
		if (MobHostilityPower.shouldBeHostile(target, EntityType.PIGLIN)) cir.setReturnValue(false);
	}

	@Inject(method="getPreferredTarget", at=@At("HEAD"), cancellable=true)
	private static void GetPreferredTargetExcludingPiglinPlayers(PiglinEntity piglin, CallbackInfoReturnable<Optional<? extends LivingEntity>> cir) {
		Brain<PiglinEntity> brain = piglin.getBrain();
		if (getNearestZombifiedPiglin(piglin)) return;
		Optional<? extends LivingEntity> optional = LookTargetUtil.getEntity(piglin, MemoryModuleType.ANGRY_AT);
		if (optional.isPresent() && Sensor.testAttackableTargetPredicateIgnoreVisibility(piglin, optional.get())) return;
		//Start modifying behavior here
		//TODO: there is probably a cleaner way to exclude "piglin players" from the nearest-visible targeting
		if (brain.hasMemoryModule(MemoryModuleType.UNIVERSAL_ANGER) && (optional = brain.getOptionalMemory(MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER)).isPresent()) {
			if (!ModBase.IS_PIGLIN_POWER.isActive(optional.get())) {
				cir.setReturnValue(optional);
				return;
			}
		}
		if ((optional = brain.getOptionalMemory(MemoryModuleType.NEAREST_VISIBLE_NEMESIS)).isPresent()) {
			cir.setReturnValue(optional);
			return;
		}
		optional = brain.getOptionalMemory(MemoryModuleType.NEAREST_TARGETABLE_PLAYER_NOT_WEARING_GOLD);
		if (optional.isPresent() && Sensor.testAttackableTargetPredicate(piglin, optional.get())) {
			cir.setReturnValue(optional);
			return;
		}
		cir.setReturnValue(Optional.empty());
	}
}
