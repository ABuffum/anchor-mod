package fun.mousewich.mixins.client.render.entity;

import com.google.common.collect.ImmutableMap;
import fun.mousewich.client.render.entity.renderer.ModVexEntityRenderer;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.EntityRenderers;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.resource.ResourceManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;

@Mixin(EntityRenderDispatcher.class)
public class EntityRenderDispatcherMixin {
	@Shadow @Final
	private ItemRenderer itemRenderer;
	@Shadow @Final
	private TextRenderer textRenderer;
	@Shadow @Final
	private EntityModelLoader modelLoader;
	@Shadow
	private Map<EntityType<?>, EntityRenderer<?>> renderers;
	@Shadow
	private Map<String, EntityRenderer<? extends PlayerEntity>> modelRenderers;
	@Inject(method="reload", at = @At("HEAD"), cancellable=true)
	public void Reload(ResourceManager manager, CallbackInfo ci) {
		EntityRendererFactory.Context context = new EntityRendererFactory.Context((EntityRenderDispatcher)(Object)this, this.itemRenderer, manager, this.modelLoader, this.textRenderer);
		this.renderers = EntityRenderers.reloadEntityRenderers(context);
		this.modelRenderers = EntityRenderers.reloadPlayerRenderers(context);
		if (this.renderers.containsKey(EntityType.VEX)) {
			this.renderers = new HashMap<>(this.renderers);
			this.renderers.put(EntityType.VEX, new ModVexEntityRenderer(context));
			this.renderers = ImmutableMap.copyOf(this.renderers);
			ci.cancel();
		}
	}
}
