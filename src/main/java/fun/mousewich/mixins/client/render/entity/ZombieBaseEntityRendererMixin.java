package fun.mousewich.mixins.client.render.entity;

import fun.mousewich.entity.FreezeConversionEntity;
import net.minecraft.client.render.entity.ZombieBaseEntityRenderer;
import net.minecraft.client.render.entity.model.ZombieEntityModel;
import net.minecraft.entity.mob.ZombieEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ZombieBaseEntityRenderer.class)
public abstract class ZombieBaseEntityRendererMixin<T extends ZombieEntity, M extends ZombieEntityModel<T>> {
	@Inject(method="isShaking(Lnet/minecraft/entity/mob/ZombieEntity;)Z", at=@At("HEAD"), cancellable=true)
	protected void IsShaking(T zombieEntity, CallbackInfoReturnable<Boolean> cir) {
		if (zombieEntity instanceof FreezeConversionEntity freezable && freezable.isShaking()) cir.setReturnValue(true);
	}
}
