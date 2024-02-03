package fun.wich.client.render.entity.renderer;

import fun.wich.ModId;
import fun.wich.client.render.entity.ModEntityModelLayers;
import fun.wich.client.render.entity.model.RaftEntityModel;
import fun.wich.entity.vehicle.ModBoatEntity;
import fun.wich.entity.vehicle.ModBoatType;
import fun.wich.registry.ModBambooRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.BoatEntityModel;
import net.minecraft.client.render.entity.model.CompositeEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3f;

@Environment(EnvType.CLIENT)
public class ModBoatEntityRenderer extends EntityRenderer<ModBoatEntity> {
	private final BoatEntityModel model;
	private final RaftEntityModel raft;

	public ModBoatEntityRenderer(EntityRendererFactory.Context context) {
		super(context);
		this.shadowRadius = 0.8F;
		this.model = new BoatEntityModel(context.getPart(ModEntityModelLayers.BOAT));
		this.raft = new RaftEntityModel(context.getPart(ModEntityModelLayers.RAFT));
	}

	public void render(ModBoatEntity boatEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
		matrixStack.push();
		matrixStack.translate(0.0D, 0.375D, 0.0D);
		matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180.0F - f));
		float h = (float)boatEntity.getDamageWobbleTicks() - g;
		float j = Math.max(0.0F, boatEntity.getDamageWobbleStrength() - g);
		if (h > 0.0F) matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(MathHelper.sin(h) * h * j / 10.0F * boatEntity.getDamageWobbleSide()));
		float k = boatEntity.interpolateBubbleWobble(g);
		if (!MathHelper.approximatelyEquals(k, 0.0F)) {
			matrixStack.multiply(new Quaternion(new Vec3f(1.0F, 0.0F, 1.0F), boatEntity.interpolateBubbleWobble(g), true));
		}
		ModBoatType type = boatEntity.getModBoatType();
		boolean isRaft = type == ModBambooRegistry.BAMBOO_RAFT.getType();
		CompositeEntityModel<BoatEntity> boatEntityModel = isRaft ? raft : model;
		matrixStack.scale(-1.0F, -1.0F, 1.0F);
		matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(90.0F));
		boatEntityModel.setAngles(boatEntity, g, 0.0F, -0.1F, 0.0F, 0.0F);
		VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(boatEntityModel.getLayer(getIdentifier(type)));
		boatEntityModel.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
		if (!boatEntity.isSubmergedInWater()) {
			VertexConsumer vertexConsumer2 = vertexConsumerProvider.getBuffer(RenderLayer.getWaterMask());
			if (!isRaft) {
				((BoatEntityModel)boatEntityModel).getWaterPatch().render(matrixStack, vertexConsumer2, i, OverlayTexture.DEFAULT_UV);
			}
		}
		matrixStack.pop();
		super.render(boatEntity, f, g, matrixStack, vertexConsumerProvider, i);
	}

	private Identifier getIdentifier(ModBoatType type) {
		if (type.getName().contains("minecraft:")) return new Identifier("textures/entity/boat/" + type.getName().substring("minecraft:".length()) + ".png");
		return ModId.ID("textures/entity/boat/" + type.getName() + ".png");
	}

	public Identifier getTexture(ModBoatEntity boatEntity) { return getIdentifier(boatEntity.getModBoatType()); }
}
