package fun.mousewich.mixins.client.render.entity;

import com.google.common.collect.ImmutableMap;
import fun.mousewich.client.render.entity.model.ModVexEntityModel;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.entity.model.EntityModels;
import net.minecraft.resource.ResourceManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;

@Mixin(EntityModelLoader.class)
public class EntityModelLoaderMixin {
	@Shadow
	private Map<EntityModelLayer, TexturedModelData> modelParts;
	@Inject(method="reload", at = @At("HEAD"), cancellable = true)
	public void Reload(ResourceManager manager, CallbackInfo ci) {
		Map<EntityModelLayer, TexturedModelData> tempMap = new HashMap<>(EntityModels.getModels());
		if (tempMap.containsKey(EntityModelLayers.VEX)) {
			tempMap.put(EntityModelLayers.VEX, ModVexEntityModel.getTexturedModelData());
			this.modelParts = ImmutableMap.copyOf(tempMap);
			ci.cancel();
		}
	}
}
