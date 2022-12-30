package haven.rendering.entities.tnt;

import haven.ModBase;
import haven.entities.tnt.SoftTntEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;

import java.util.Arrays;
import java.util.Collections;

@Environment(EnvType.CLIENT)
public class SoftTntEntityRenderer extends ModTntEntityRenderer<SoftTntEntity> {
	public SoftTntEntityRenderer(EntityRendererFactory.Context context) {
		super(context, ModBase.ID("textures/entity/tnt/soft.png"));
	}
}
