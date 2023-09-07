package fun.mousewich.mixins.client.render.entity;

import fun.mousewich.client.render.entity.renderer.ModVexEntityRenderer;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.EntityRenderers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(EntityRenderers.class)
public class EntityRenderersMixin {
	@Shadow
	@Final
	private static Map<EntityType<?>, EntityRendererFactory<?>> RENDERER_FACTORIES;
	@Inject(method="register", at = @At("HEAD"), cancellable = true)
	private static <T extends Entity> void RegisterVex(EntityType<? extends T> type, EntityRendererFactory<T> factory, CallbackInfo ci) {
		if (type == EntityType.VEX) {
			@SuppressWarnings("unchecked") EntityRendererFactory<T> newFactory = ctx -> (EntityRenderer<T>)new ModVexEntityRenderer(ctx);
			RENDERER_FACTORIES.put(type, newFactory);
			ci.cancel();
		}
	}
}
