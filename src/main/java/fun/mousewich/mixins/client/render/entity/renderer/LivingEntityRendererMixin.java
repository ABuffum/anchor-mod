package fun.mousewich.mixins.client.render.entity.renderer;

import fun.mousewich.origins.power.PowersUtil;
import fun.mousewich.origins.power.PreventShiveringPower;
import io.github.apace100.apoli.power.ShakingPower;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin extends EntityRenderer<LivingEntity> {
	protected LivingEntityRendererMixin(EntityRendererFactory.Context ctx) { super(ctx); }

	@Inject(method = "isShaking", at = @At("HEAD"), cancellable = true)
	private void disallowShivering(LivingEntity entity, CallbackInfoReturnable<Boolean> cir) {
		if (PowersUtil.Active(entity, PreventShiveringPower.class)) {
			cir.setReturnValue(PowersUtil.Active(entity, ShakingPower.class));
		}
	}
}
