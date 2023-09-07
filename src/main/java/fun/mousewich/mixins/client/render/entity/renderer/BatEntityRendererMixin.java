package fun.mousewich.mixins.client.render.entity.renderer;

import fun.mousewich.haven.entity.AngelBatEntity;
import net.minecraft.client.render.entity.BatEntityRenderer;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BatEntityRenderer.class)
public class BatEntityRendererMixin {
	@Inject(method="getTexture(Lnet/minecraft/entity/passive/BatEntity;)Lnet/minecraft/util/Identifier;", at=@At("HEAD"), cancellable = true)
	private void GetTexture(BatEntity batEntity, CallbackInfoReturnable<Identifier> cir) {
		if (batEntity instanceof AngelBatEntity) cir.setReturnValue(AngelBatEntity.getTexture());
	}
}

