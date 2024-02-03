package fun.wich.mixins.entity.ai;

import fun.wich.effect.ModStatusEffects;
import net.minecraft.entity.ai.brain.task.LongJumpTask;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LongJumpTask.class)
public class LongJumpTaskMixin {
	@Inject(method="shouldRun(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/mob/MobEntity;)Z", at=@At("HEAD"), cancellable=true)
	protected void ShouldRun(ServerWorld serverWorld, MobEntity mobEntity, CallbackInfoReturnable<Boolean> cir) {
		if (mobEntity.hasStatusEffect(ModStatusEffects.STICKY)) cir.setReturnValue(false);
	}
}
