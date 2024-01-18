package fun.mousewich.mixins.client.render.entity.renderer;

import fun.mousewich.client.render.entity.renderer.feature.ZombieHorseArmorFeatureRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.HorseBaseEntityRenderer;
import net.minecraft.client.render.entity.ZombieHorseEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.HorseEntityModel;
import net.minecraft.entity.passive.HorseBaseEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ZombieHorseEntityRenderer.class)
public abstract class ZombieHorseEntityRendererMixin extends HorseBaseEntityRenderer<HorseBaseEntity, HorseEntityModel<HorseBaseEntity>> {
	public ZombieHorseEntityRendererMixin(EntityRendererFactory.Context ctx, HorseEntityModel<HorseBaseEntity> model, float scale) {
		super(ctx, model, scale);
	}

	@Inject(method="<init>", at=@At("TAIL"))
	private void AddArmorFeature(EntityRendererFactory.Context ctx, EntityModelLayer layer, CallbackInfo ci) {
		this.addFeature(new ZombieHorseArmorFeatureRenderer(this, ctx.getModelLoader()));
	}
}
