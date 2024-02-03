package fun.wich.client.render.entity.renderer;

import fun.wich.ModId;
import fun.wich.client.render.entity.ModEntityModelLayers;
import fun.wich.client.render.entity.model.CapedIllagerEntityModel;
import fun.wich.client.render.entity.renderer.feature.IllagerCapeFeatureRenderer;
import fun.wich.entity.hostile.illager.MageEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.IllagerEntityRenderer;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(value= EnvType.CLIENT)
public class MageEntityRenderer extends IllagerEntityRenderer<MageEntity> {
	public static final Identifier TEXTURE = ModId.ID("textures/entity/illager/mage.png");

	public MageEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new CapedIllagerEntityModel<>(context.getPart(ModEntityModelLayers.CAPED_ILLAGER)), 0.5f);
		this.addFeature(new HeldItemFeatureRenderer<>(this){
			@Override
			public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, MageEntity entity, float f, float g, float h, float j, float k, float l) {
				if (entity.isSpellcasting()) super.render(matrixStack, vertexConsumerProvider, i, entity, f, g, h, j, k, l);
			}
		});
		this.addFeature(new IllagerCapeFeatureRenderer<>(this));
		this.model.getHat().visible = true;
	}

	@Override
	public Identifier getTexture(MageEntity pillagerEntity) { return TEXTURE; }
}