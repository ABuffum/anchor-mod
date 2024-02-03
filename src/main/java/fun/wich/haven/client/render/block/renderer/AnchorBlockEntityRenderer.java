package fun.wich.haven.client.render.block.renderer;

import fun.wich.haven.block.anchor.AnchorBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static java.util.Map.entry;

@Environment(EnvType.CLIENT)
public class AnchorBlockEntityRenderer implements BlockEntityRenderer<AnchorBlockEntity> {
	private final ModelPart bb_main;
	private final ModelPart core;

	public AnchorBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
		ModelPart cube_r1 = new ModelPart(List.of(new ModelPart.Cuboid(0, 97, -1.5F, -10.0F, -1.5F, 3.0F, 20.0F, 3.0F, 0, 0, 0, false, 256, 256)), Collections.emptyMap());
		ModelPart cube_r2 = new ModelPart(List.of(new ModelPart.Cuboid(12, 97, -1.5F, -10.0F, -1.5F, 3.0F, 20.0F, 3.0F, 0, 0, 0, false, 256, 256)), Collections.emptyMap());
		ModelPart cube_r3 = new ModelPart(List.of(new ModelPart.Cuboid(102, 89, -1.5F, -10.0F, -1.5F, 3.0F, 20.0F, 3.0F, 0, 0, 0, false, 256, 256)), Collections.emptyMap());
		ModelPart cube_r4 = new ModelPart(List.of(new ModelPart.Cuboid(104, 26, -6.0F, 3.0F, -6.0F, 3.0F, 20.0F, 3.0F, 0, 0, 0, false, 256, 256)), Collections.emptyMap());
		ModelPart cube_r5 = new ModelPart(List.of(new ModelPart.Cuboid(0, 8, -1.0F, -4.5F, -1.0F, 2.0F, 8.0F, 2.0F, 0, 0, 0, false, 256, 256)), Collections.emptyMap());
		ModelPart cube_r6 = new ModelPart(List.of(new ModelPart.Cuboid(0, 21, -1.0F, -5.5F, -1.0F, 2.0F, 11.0F, 2.0F, 0, 0, 0, false, 256, 256)), Collections.emptyMap());
		ModelPart cube_r7 = new ModelPart(List.of(new ModelPart.Cuboid(8, 21, -1.3679F, -6.7998F, -1.0F, 2.0F, 11.0F, 2.0F, 0, 0, 0, false, 256, 256)), Collections.emptyMap());
		ModelPart cube_r8 = new ModelPart(List.of(new ModelPart.Cuboid(78, 89, -1.0795F, -15.8219F, -1.0F, 2.0F, 32.0F, 2.0F, 0, 0, 0, false, 256, 256)), Collections.emptyMap());
		ModelPart cube_r9 = new ModelPart(List.of(new ModelPart.Cuboid(86, 89, -1.0795F, -15.8219F, -1.0F, 2.0F, 32.0F, 2.0F, 0, 0, 0, false, 256, 256)), Collections.emptyMap());
		ModelPart cube_r10 = new ModelPart(List.of(new ModelPart.Cuboid(8, 8, -1.0F, -4.5F, -1.0F, 2.0F, 8.0F, 2.0F, 0, 0, 0, false, 256, 256)), Collections.emptyMap());
		ModelPart cube_r11 = new ModelPart(List.of(new ModelPart.Cuboid(0, 41, -1.0F, -5.5F, -1.0F, 2.0F, 11.0F, 2.0F, 0, 0, 0, false, 256, 256)), Collections.emptyMap());
		ModelPart cube_r12 = new ModelPart(List.of(new ModelPart.Cuboid(42, 41, -1.3679F, -6.7998F, -1.0F, 2.0F, 11.0F, 2.0F, 0, 0, 0, false, 256, 256)), Collections.emptyMap());
		ModelPart cube_r13 = new ModelPart(List.of(new ModelPart.Cuboid(32, 91, -1.0795F, -15.8219F, -1.0F, 2.0F, 32.0F, 2.0F, 0, 0, 0, false, 256, 256)), Collections.emptyMap());
		ModelPart cube_r14 = new ModelPart(List.of(new ModelPart.Cuboid(40, 93, -1.0795F, -15.8219F, -1.0F, 2.0F, 32.0F, 2.0F, 0, 0, 0, false, 256, 256)), Collections.emptyMap());
		ModelPart cube_r15 = new ModelPart(List.of(new ModelPart.Cuboid(54, 21, -1.0F, -4.5F, -1.0F, 2.0F, 8.0F, 2.0F, 0, 0, 0, false, 256, 256)), Collections.emptyMap());
		ModelPart cube_r16 = new ModelPart(List.of(new ModelPart.Cuboid(54, 0, -1.0F, -5.5F, -1.0F, 2.0F, 11.0F, 2.0F, 0, 0, 0, false, 256, 256)), Collections.emptyMap());
		ModelPart cube_r17 = new ModelPart(List.of(new ModelPart.Cuboid(24, 97, -1.3679F, -6.7998F, -1.0F, 2.0F, 11.0F, 2.0F, 0, 0, 0, false, 256, 256)), Collections.emptyMap());
		ModelPart cube_r18 = new ModelPart(List.of(new ModelPart.Cuboid(48, 93, -1.0795F, -15.8219F, -1.0F, 2.0F, 32.0F, 2.0F, 0, 0, 0, false, 256, 256)), Collections.emptyMap());
		ModelPart cube_r19 = new ModelPart(List.of(new ModelPart.Cuboid(56, 93, -1.0795F, -15.8219F, -1.0F, 2.0F, 32.0F, 2.0F, 0, 0, 0, false, 256, 256)), Collections.emptyMap());
		ModelPart cube_r20 = new ModelPart(List.of(new ModelPart.Cuboid(0, 61, -1.0F, -4.5F, -1.0F, 2.0F, 8.0F, 2.0F, 0, 0, 0, false, 256, 256)), Collections.emptyMap());
		ModelPart cube_r21 = new ModelPart(List.of(new ModelPart.Cuboid(24, 110, -1.0F, -5.5F, -1.0F, 2.0F, 11.0F, 2.0F, 0, 0, 0, false, 256, 256)), Collections.emptyMap());
		ModelPart cube_r22 = new ModelPart(List.of(new ModelPart.Cuboid(64, 93, -1.0795F, -15.8219F, -1.0F, 2.0F, 32.0F, 2.0F, 0, 0, 0, false, 256, 256)), Collections.emptyMap());
		ModelPart cube_r23 = new ModelPart(List.of(new ModelPart.Cuboid(102, 113, -1.3679F, -6.7998F, -1.0F, 2.0F, 11.0F, 2.0F, 0, 0, 0, false, 256, 256)), Collections.emptyMap());
		ModelPart cube_r24 = new ModelPart(List.of(new ModelPart.Cuboid(94, 89, -1.0795F, -15.8219F, -1.0F, 2.0F, 32.0F, 2.0F, 0, 0, 0, false, 256, 256)), Collections.emptyMap());
		ModelPart cube_r25 = new ModelPart(List.of(new ModelPart.Cuboid(36, 64, 6.0F, -38.0F, -8.0F, 2.0F, 5.0F, 2.0F, 0, 0, 0, false, 256, 256)), Collections.emptyMap());
		ModelPart cube_r26 = new ModelPart(List.of(new ModelPart.Cuboid(44, 66, 6.0F, -38.0F, -8.0F, 2.0F, 5.0F, 2.0F, 0, 0, 0, false, 256, 256)), Collections.emptyMap());
		ModelPart cube_r27 = new ModelPart(List.of(new ModelPart.Cuboid(78, 68, 6.0F, -38.0F, -8.0F, 2.0F, 5.0F, 2.0F, 0, 0, 0, false, 256, 256)), Collections.emptyMap());
		ModelPart cube_r28 = new ModelPart(List.of(new ModelPart.Cuboid(104, 54, 4.0F, -33.0F, -9.0F, 5.0F, 3.0F, 5.0F, 0, 0, 0, false, 256, 256)), Collections.emptyMap());
		ModelPart cube_r29 = new ModelPart(List.of(new ModelPart.Cuboid(105, 11, 4.0F, -33.0F, -9.0F, 5.0F, 3.0F, 5.0F, 0, 0, 0, false, 256, 256)), Collections.emptyMap());
		ModelPart cube_r30 = new ModelPart(List.of(new ModelPart.Cuboid(106, 70, 4.0F, -33.0F, -9.0F, 5.0F, 3.0F, 5.0F, 0, 0, 0, false, 256, 256)), Collections.emptyMap());

		cube_r1.setTransform(ModelTransform.of(7.4548F, -71.1605F, -7.2113F, -0.2618F, 0.0F, -0.2618F));
		cube_r2.setTransform(ModelTransform.of(7.4548F, -71.1605F, 7.2887F, 0.2618F, 0.0F, -0.2618F));
		cube_r3.setTransform(ModelTransform.of(-7.2952F, -71.1605F, 7.2887F, 0.2618F, 0.0F, 0.2618F));
		cube_r4.setTransform(ModelTransform.of(0.0F, -81.0F, 0.5F, -0.2618F, 0.0F, 0.2618F));
		cube_r5.setTransform(ModelTransform.of(10.6442F, -71.9596F, 0.4652F, 1.5708F, 0.0F, 1.6581F));
		cube_r6.setTransform(ModelTransform.of(10.2956F, -67.9748F, 6.2152F, 1.5708F, 0.9163F, 1.6581F));
		cube_r7.setTransform(ModelTransform.of(10.2253F, -67.1706F, -7.3679F, -1.5708F, 0.9163F, -1.4835F));
		cube_r8.setTransform(ModelTransform.of(8.611F, -48.719F, 7.9205F, -1.5708F, 1.4835F, -1.4835F));
		cube_r9.setTransform(ModelTransform.of(8.611F, -48.719F, -8.0795F, 1.5708F, 1.4835F, 1.6581F));
		cube_r10.setTransform(ModelTransform.of(-10.591F, -70.7394F, 0.4652F, 1.5708F, 0.0F, 1.4835F));
		cube_r11.setTransform(ModelTransform.of(-10.2424F, -66.7546F, 6.2152F, 1.5708F, 0.9163F, 1.4835F));
		cube_r12.setTransform(ModelTransform.of(-10.172F, -65.9505F, -7.3679F, -1.5708F, 0.9163F, -1.6581F));
		cube_r13.setTransform(ModelTransform.of(-8.5577F, -47.4989F, 7.9205F, -1.5708F, 1.4835F, -1.6581F));
		cube_r14.setTransform(ModelTransform.of(-8.5577F, -47.4989F, -8.0795F, 1.5708F, 1.4835F, 1.4835F));
		cube_r15.setTransform(ModelTransform.of(-0.4652F, -70.7394F, -10.591F, 0.0F, -0.0873F, 1.5708F));
		cube_r16.setTransform(ModelTransform.of(-6.2152F, -66.7546F, -10.2424F, 0.0693F, -0.0531F, 0.6527F));
		cube_r17.setTransform(ModelTransform.of(7.3679F, -65.9505F, -10.172F, 0.0693F, 0.0531F, -0.6527F));
		cube_r18.setTransform(ModelTransform.of(-7.9205F, -47.4989F, -8.5577F, 0.0869F, 0.0076F, -0.0869F));
		cube_r19.setTransform(ModelTransform.of(8.0795F, -47.4989F, -8.5577F, 0.0869F, -0.0076F, 0.0869F));
		cube_r20.setTransform(ModelTransform.of(-0.4652F, -70.7394F, 10.591F, 0.0F, 0.0873F, 1.5708F));
		cube_r21.setTransform(ModelTransform.of(-6.2152F, -66.7546F, 10.2424F, -0.0693F, 0.0531F, 0.6527F));
		cube_r22.setTransform(ModelTransform.of(-7.9205F, -47.4989F, 8.5577F, -0.0869F, -0.0076F, -0.0869F));
		cube_r23.setTransform(ModelTransform.of(7.3679F, -65.9505F, 10.172F, -0.0693F, -0.0531F, -0.6527F));
		cube_r24.setTransform(ModelTransform.of(8.0795F, -47.4989F, 8.5577F, -0.0869F, 0.0076F, 0.0869F));
		cube_r25.setTransform(ModelTransform.of(1.0F, 0.0F, 1.0F, 0.0F, -1.5708F, 0.0F));
		cube_r26.setTransform(ModelTransform.of(-1.0F, 0.0F, 1.0F, -3.1416F, 0.0F, 3.1416F));
		cube_r27.setTransform(ModelTransform.of(-1.0F, 0.0F, -1.0F, 0.0F, 1.5708F, 0.0F));
		cube_r28.setTransform(ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));
		cube_r29.setTransform(ModelTransform.of(0.0F, 0.0F, 0.0F, -3.1416F, 0.0F, 3.1416F));
		cube_r30.setTransform(ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		ModelPart wick1_r1 = new ModelPart(List.of(new ModelPart.Cuboid(0, -1, 0.0F, -1.0F, -0.5F, 0.0F, 1.0F, 1.0F, 0, 0, 0, false, 256, 256)), Collections.emptyMap());
		wick1_r1.setTransform(ModelTransform.of(0.0F, -87.0F, 0.0F, 0.0F, 0.7854F, 0.0F));
		ModelPart wick2_r1 = new ModelPart(List.of(new ModelPart.Cuboid(0, -1, 0.0F, -1.0F, -0.5F, 0.0F, 1.0F, 1.0F, 0, 0, 0, false, 256, 256)), Collections.emptyMap());
		wick2_r1.setTransform(ModelTransform.of(0.0F, -87.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

		this.core = new ModelPart(List.of(new ModelPart.Cuboid(79, 71, -5.0F, -29.0F, -46.0F, 9.0F, 9.0F, 9.0F, 0, 0, 0, false, 256, 256)), Collections.emptyMap());
		this.core.setTransform(ModelTransform.of(0.0F, 0.0F, 0.0F, -1.0472F, 1.0472F, 0.0F));

		this.bb_main = new ModelPart(
				List.of(
						new ModelPart.Cuboid(38, 80, -5.0F, -15.0F, -5.0F, 10.0F, 3.0F, 10.0F, 0, 0, 0, false, 256, 256),
						new ModelPart.Cuboid(0, 85, -4.0F, -19.0F, -4.0F, 8.0F, 4.0F, 8.0F, 0, 0, 0, false, 256, 256),
						new ModelPart.Cuboid(96, 0, -3.0F, -21.0F, -3.0F, 6.0F, 2.0F, 6.0F, 0, 0, 0, false, 256, 256),
						new ModelPart.Cuboid(64, 38, -4.0F, -21.0F, -4.0F, 8.0F, 1.0F, 8.0F, 0, 0, 0, false, 256, 256),
						new ModelPart.Cuboid(54, 21, -6.0F, -26.0F, -6.0F, 12.0F, 5.0F, 12.0F, 0, 0, 0, false, 256, 256),
						new ModelPart.Cuboid(42, 47, -7.0F, -27.0F, -7.0F, 14.0F, 5.0F, 14.0F, 0, 0, 0, false, 256, 256),
						new ModelPart.Cuboid(0, 0, -9.0F, -30.0F, -9.0F, 18.0F, 3.0F, 18.0F, 0, 0, 0, false, 256, 256),
						new ModelPart.Cuboid(111, 44, 4.0F, -33.0F, -9.0F, 5.0F, 3.0F, 5.0F, 0, 0, 0, false, 256, 256),
						new ModelPart.Cuboid(68, 80, 7.0F, -38.0F, -9.0F, 2.0F, 5.0F, 2.0F, 0, 0, 0, false, 256, 256),
						new ModelPart.Cuboid(109, 107, 4.0F, -33.0F, -9.0F, 5.0F, 3.0F, 5.0F, 0, 0, 0, false, 256, 256),
						new ModelPart.Cuboid(0, 21, -9.0F, -74.0F, -9.0F, 18.0F, 2.0F, 18.0F, 0, 0, 0, false, 256, 256),
						new ModelPart.Cuboid(84, 54, -12.0F, -73.5F, -7.0F, 3.0F, 2.0F, 14.0F, 0, 0, 0, false, 256, 256),
						new ModelPart.Cuboid(90, 21, -7.0F, -73.5F, -12.0F, 14.0F, 2.0F, 3.0F, 0, 0, 0, false, 256, 256),
						new ModelPart.Cuboid(84, 38, 9.0F, -73.5F, -7.0F, 3.0F, 2.0F, 14.0F, 0, 0, 0, false, 256, 256),
						new ModelPart.Cuboid(72, 16, -7.0F, -73.5F, 9.0F, 14.0F, 2.0F, 3.0F, 0, 0, 0, false, 256, 256),
						new ModelPart.Cuboid(54, 0, -7.0F, -76.0F, -7.0F, 14.0F, 2.0F, 14.0F, 0, 0, 0, false, 256, 256),
						new ModelPart.Cuboid(48, 66, -5.0F, -80.0F, -5.0F, 10.0F, 4.0F, 10.0F, 0, 0, 0, false, 256, 256),
						new ModelPart.Cuboid(0, 0, -2.0F, -84.0F, -2.0F, 4.0F, 4.0F, 4.0F, 0, 0, 0, false, 256, 256),
						new ModelPart.Cuboid(0, 34, -1.0F, -87.0F, -1.0F, 2.0F, 3.0F, 2.0F, 0, 0, 0, false, 256, 256),
						new ModelPart.Cuboid(0, 41, -7.0F, -12.0F, -7.0F, 14.0F, 6.0F, 14.0F, 0, 0, 0, false, 256, 256)
				),
				Map.ofEntries(
						entry("cube_r1", cube_r1),
						entry("cube_r2", cube_r2),
						entry("cube_r3", cube_r3),
						entry("cube_r4", cube_r4),
						entry("cube_r5", cube_r5),
						entry("cube_r6", cube_r6),
						entry("cube_r7", cube_r7),
						entry("cube_r8", cube_r8),
						entry("cube_r9", cube_r9),
						entry("cube_r10", cube_r10),
						entry("cube_r11", cube_r11),
						entry("cube_r12", cube_r12),
						entry("cube_r13", cube_r13),
						entry("cube_r14", cube_r14),
						entry("cube_r15", cube_r15),
						entry("cube_r16", cube_r16),
						entry("cube_r17", cube_r17),
						entry("cube_r18", cube_r18),
						entry("cube_r19", cube_r19),
						entry("cube_r20", cube_r20),
						entry("cube_r21", cube_r21),
						entry("cube_r22", cube_r22),
						entry("cube_r23", cube_r23),
						entry("cube_r24", cube_r24),
						entry("cube_r25", cube_r25),
						entry("cube_r26", cube_r26),
						entry("cube_r27", cube_r27),
						entry("cube_r28", cube_r28),
						entry("cube_r29", cube_r29),
						entry("cube_r30", cube_r30),
						entry("wick1_r1", wick1_r1),
						entry("wick2_r1", wick2_r1)
				)
		);
	}

	@Override
	public void render(AnchorBlockEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
		matrices.push();
		// Fetch the appropriate texture
		Identifier textureId = blockEntity.getTextureId();
		// adjust the render tick
		double tick = blockEntity.prevTick += tickDelta;
		// Calculate the current offset in the y value
		double offset = Math.sin(tick / 8.0) / 4.0;
		// Move the structure
		matrices.translate(0.5, -0.375, 0.5);
		// Flip it all right-side-up
		matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(180));
		// Render the structure
		this.bb_main.render(matrices, vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(textureId)), light, overlay, 1f, 1f, 1f, 1f);
		if (blockEntity.getOwner() != 0) {
			matrices.translate(0, offset, 0);
			matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion((float)tick * 4));
			// Render the core
			this.core.render(matrices, vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(textureId, false)), light, overlay, 1f, 1f, 1f, 1f);
		}

		// Mandatory call after GL calls
		matrices.pop();
	}
}