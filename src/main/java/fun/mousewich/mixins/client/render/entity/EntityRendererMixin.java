package fun.mousewich.mixins.client.render.entity;

import fun.mousewich.origins.power.SkinGlowPower;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin<T extends Entity> {

	//@Inject(method="getLight", at = @At("RETURN"), cancellable = true)
	//public final void getLight(T entity, float tickDelta, CallbackInfoReturnable<Integer> cir) {
	//	int glow = EntitySkinGlowPowerUtil.getGlow(entity);
	//	cir.setReturnValue(Math.max(cir.getReturnValue(), LightmapTextureManager.pack(glow, glow)));
	//}

	@Inject(method="getBlockLight", at = @At("RETURN"), cancellable = true)
	protected void getBlockLight(T entity, BlockPos pos, CallbackInfoReturnable<Integer> cir) {
		cir.setReturnValue(Math.max(cir.getReturnValue(), SkinGlowPower.getGlow(entity)));
	}
}
