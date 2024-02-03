package fun.wich.mixins.client.render.entity.model;

import fun.wich.client.render.entity.model.ModEntityModelPartNames;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.LlamaEntityModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LlamaEntityModel.class)
public class LlamaEntityModelMixin {
	@Redirect(method="getTexturedModelData", at=@At(value="INVOKE", target="Lnet/minecraft/client/model/TexturedModelData;of(Lnet/minecraft/client/model/ModelData;II)Lnet/minecraft/client/model/TexturedModelData;"))
	private static TexturedModelData AddAntlerPlanes(ModelData partData, int textureWidth, int textureHeight) {
		ModelPartData modelPartData = partData.getRoot();
		ModelPartData head = modelPartData.getChild(EntityModelPartNames.HEAD);
		head.addChild(ModEntityModelPartNames.ANTLER, ModelPartBuilder.create()
				.uv(112, 0).cuboid(-12, -29, -3, 8, 16, 0)
				.uv(112, 0).mirrored().cuboid(4, -29.0F, -3, 8, 16.0F, 0), ModelTransform.NONE);
		return TexturedModelData.of(partData, textureWidth, textureHeight);
	}
}
