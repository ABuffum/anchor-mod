package fun.wich.mixins.client.render;

import fun.wich.ModBase;
import fun.wich.ModId;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.util.Identifier;
import net.minecraft.util.SignType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityModelLayers.class)
public class EntityModelLayersMixin {
	@Inject(method = "createSign", at = @At("HEAD"), cancellable = true)
	private static void RetextureSigns(SignType type, CallbackInfoReturnable<EntityModelLayer> cir) {
		if (ModBase.SIGN_TYPES.contains(type) || ModBase.HANGING_SIGN_SUBTYPES.containsKey(type)) {
			String name = type.getName();
			Identifier id = ModId.ID(name.startsWith("minecraft:") ? "minecraft:sign/" + name.substring("minecraft:".length()) : "sign/" + name);
			cir.setReturnValue(new EntityModelLayer(id, "main"));
		}
	}
}
