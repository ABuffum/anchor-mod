package fun.mousewich.client.render.entity.renderer;

import fun.mousewich.ModBase;
import fun.mousewich.ModClient;
import fun.mousewich.client.render.entity.model.ChestBoatEntityModel;
import fun.mousewich.client.render.entity.model.ChestRaftEntityModel;
import fun.mousewich.entity.vehicle.ChestBoatEntity;
import fun.mousewich.entity.vehicle.ModBoatType;
import fun.mousewich.entity.vehicle.ModChestBoatEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3f;

@Environment(EnvType.CLIENT)
public class ChestBoatEntityRenderer extends EntityRenderer<ChestBoatEntity> {
	private final ChestBoatEntityModel boat;
	private final ChestRaftEntityModel raft;

	public ChestBoatEntityRenderer(EntityRendererFactory.Context context) {
		super(context);
		this.shadowRadius = 0.8F;
		boat = new ChestBoatEntityModel(context.getPart(ModClient.CHEST_BOAT_MODEL_LAYER));
		raft = new ChestRaftEntityModel(context.getPart(ModClient.CHEST_RAFT_MODEL_LAYER));
	}

	public void render(ChestBoatEntity boatEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
		matrixStack.push();
		matrixStack.translate(0.0D, 0.375D, 0.0D);
		matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180.0F - f));
		float h = (float) boatEntity.getDamageWobbleTicks() - g;
		float j = boatEntity.getDamageWobbleStrength() - g;
		if (j < 0.0F) j = 0.0F;
		if (h > 0.0F) matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(MathHelper.sin(h) * h * j / 10.0F * (float) boatEntity.getDamageWobbleSide()));
		float k = boatEntity.interpolateBubbleWobble(g);
		if (!MathHelper.approximatelyEquals(k, 0.0F)) matrixStack.multiply(new Quaternion(new Vec3f(1.0F, 0.0F, 1.0F), boatEntity.interpolateBubbleWobble(g), true));
		boolean isRaft = boatEntity instanceof ModChestBoatEntity modChestBoat && modChestBoat.getModBoatType() == ModBase.BAMBOO_RAFT.getType();
		matrixStack.scale(-1.0F, -1.0F, 1.0F);
		matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(90.0F));
		if (isRaft) {
			VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(raft.getLayer(getTexture(boatEntity)));
			raft.setAngles(boatEntity, g, 0.0F, -0.1F, 0.0F, 0.0F);
			raft.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
		}
		else {
			VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(boat.getLayer(getTexture(boatEntity)));
			boat.setAngles(boatEntity, g, 0.0F, -0.1F, 0.0F, 0.0F);
			boat.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
		}
		if (!boatEntity.isSubmergedInWater()) {
			VertexConsumer vertexConsumer2 = vertexConsumerProvider.getBuffer(RenderLayer.getWaterMask());
			if (!isRaft)boat.getWaterPatch().render(matrixStack, vertexConsumer2, i, OverlayTexture.DEFAULT_UV);
		}
		matrixStack.pop();
		super.render(boatEntity, f, g, matrixStack, vertexConsumerProvider, i);
	}

	public Identifier getTexture(ChestBoatEntity boatEntity) {
		if (boatEntity instanceof ModChestBoatEntity modChestBoat) return getIdentifier(modChestBoat.getModBoatType());
		return getIdentifier(boatEntity.getBoatType());
	}
	private Identifier getIdentifier(BoatEntity.Type type) {
		return new Identifier("textures/entity/chest_boat/" + type.getName() + ".png");
	}
	private Identifier getIdentifier(ModBoatType type) {
		if (type.getName().startsWith("minecraft:")) return new Identifier("textures/entity/chest_boat/" + type.getName().substring("minecraft:".length()) + ".png");
		return ModBase.ID("textures/entity/chest_boat/" + type.getName() + ".png");
	}
}
