package fun.wich.haven.client.render.block.renderer;

import fun.wich.haven.block.anchor.BrokenAnchorBlockEntity;
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
public class BrokenAnchorBlockEntityRenderer implements BlockEntityRenderer<BrokenAnchorBlockEntity> {
	private final ModelPart bb_main;
	public BrokenAnchorBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
		ModelPart cube_r1 = new ModelPart(List.of(new ModelPart.Cuboid(0, 34, -1.0F, -5.0F, -1.0F, 2.0F, 3.0F, 2.0F, 0, 0, 0, false, 256, 256)), Collections.emptyMap());
		cube_r1.setTransform(ModelTransform.of(-15.2444F, -4.5837F, -27.4307F, 1.3918F, -0.6214F, 1.6913F));

		ModelPart cube_r2 = new ModelPart(List.of(new ModelPart.Cuboid(0, 0, -2.0F, -6.0F, -2.0F, 4.0F, 4.0F, 4.0F, 0, 0, 0, false, 256, 256)), Collections.emptyMap());
		cube_r2.setTransform(ModelTransform.of(-15.6759F, -6.944F, -24.2303F, 1.3918F, -0.6214F, 1.6913F));

		ModelPart cube_r3 = new ModelPart(List.of(new ModelPart.Cuboid(0, 97, -1.5F, -10.0F, -1.5F, 3.0F, 20.0F, 3.0F, 0, 0, 0, false, 256, 256)), Collections.emptyMap());
		cube_r3.setTransform(ModelTransform.of(-24.2767F, -5.0732F, -15.4621F, 1.1535F, -0.3632F, 1.642F));

		ModelPart cube_r4 = new ModelPart(List.of(new ModelPart.Cuboid(12, 97, -1.5F, -10.0F, -1.5F, 3.0F, 20.0F, 3.0F, 0, 0, 0, false, 256, 256)), Collections.emptyMap());
		cube_r4.setTransform(ModelTransform.of(-9.9311F, -4.8503F, -13.3633F, 1.6771F, -0.3632F, 1.642F));

		ModelPart cube_r5 = new ModelPart(List.of(new ModelPart.Cuboid(102, 89, -1.5F, -10.0F, -1.5F, 3.0F, 20.0F, 3.0F, 0, 0, 0, false, 256, 256)), Collections.emptyMap());
		cube_r5.setTransform(ModelTransform.of(-8.4899F, -16.7565F, -21.9499F, 1.604F, -0.8779F, 1.7634F));

		ModelPart cube_r6 = new ModelPart(List.of(new ModelPart.Cuboid(48, 66, -5.0F, -6.0F, -5.0F, 10.0F, 4.0F, 10.0F, 0, 0, 0, false, 256, 256)), Collections.emptyMap());
		cube_r6.setTransform(ModelTransform.of(-16.1075F, -9.3043F, -21.0299F, 1.3918F, -0.6214F, 1.6913F));
		ModelPart cube_r6_1 = new ModelPart(List.of(new ModelPart.Cuboid(54, 0, -7.0F, -2.0F, -7.0F, 14.0F, 2.0F, 14.0F, 0, 0, 0, false, 256, 256)), Collections.emptyMap());
		cube_r6_1.setTransform(ModelTransform.of(-16.1075F, -9.3043F, -21.0299F, 1.3918F, -0.6214F, 1.6913F));
		ModelPart cube_r6_2 = new ModelPart(List.of(new ModelPart.Cuboid(0, 21, -9.0F, 0.0F, -9.0F, 18.0F, 2.0F, 18.0F, 0, 0, 0, false, 256, 256)), Collections.emptyMap());
		cube_r6_2.setTransform(ModelTransform.of(-16.1075F, -9.3043F, -21.0299F, 1.3918F, -0.6214F, 1.6913F));

		ModelPart cube_r7 = new ModelPart(List.of(new ModelPart.Cuboid(72, 16, -7.0F, 0.0F, 9.0F, 14.0F, 2.0F, 3.0F, 0, 0, 0, false, 256, 256)), Collections.emptyMap());
		cube_r7.setTransform(ModelTransform.of(-16.1614F, -9.5994F, -20.6298F, 1.3918F, -0.6214F, 1.6913F));
		ModelPart cube_r7_1 = new ModelPart(List.of(new ModelPart.Cuboid(84, 38, 9.0F, 0.0F, -7.0F, 3.0F, 2.0F, 14.0F, 0, 0, 0, false, 256, 256)), Collections.emptyMap());
		cube_r7_1.setTransform(ModelTransform.of(-16.1614F, -9.5994F, -20.6298F, 1.3918F, -0.6214F, 1.6913F));
		ModelPart cube_r7_2 = new ModelPart(List.of(new ModelPart.Cuboid(90, 21, -7.0F, 0.0F, -12.0F, 14.0F, 2.0F, 3.0F, 0, 0, 0, false, 256, 256)), Collections.emptyMap());
		cube_r7_2.setTransform(ModelTransform.of(-16.1614F, -9.5994F, -20.6298F, 1.3918F, -0.6214F, 1.6913F));
		ModelPart cube_r7_3 = new ModelPart(List.of(new ModelPart.Cuboid(84, 54, -12.0F, 0.0F, -7.0F, 3.0F, 2.0F, 14.0F, 0, 0, 0, false, 256, 256)), Collections.emptyMap());
		cube_r7_3.setTransform(ModelTransform.of(-16.1614F, -9.5994F, -20.6298F, 1.3918F, -0.6214F, 1.6913F));

		ModelPart cube_r8 = new ModelPart(List.of(new ModelPart.Cuboid(104, 26, -6.0F, 3.0F, -6.0F, 3.0F, 20.0F, 3.0F, 0, 0, 0, false, 256, 256)), Collections.emptyMap());
		cube_r8.setTransform(ModelTransform.of(-14.8576F, -5.1661F, -26.5582F, 1.0804F, -0.8779F, 1.7634F));

		ModelPart cube_r9 = new ModelPart(List.of(new ModelPart.Cuboid(0, 8, -1.0F, -4.5F, -1.0F, 2.0F, 8.0F, 2.0F, 0, 0, 0, false, 256, 256)), Collections.emptyMap());
		cube_r9.setTransform(ModelTransform.of(-16.9073F, -1.9092F, -13.1336F, 0.2192F, -0.8425F, -1.72F));

		ModelPart cube_r10 = new ModelPart(List.of(new ModelPart.Cuboid(0, 21, -1.0F, -5.5F, -1.0F, 2.0F, 11.0F, 2.0F, 0, 0, 0, false, 256, 256)), Collections.emptyMap());
		cube_r10.setTransform(ModelTransform.of(-11.6144F, -4.4535F, -9.316F, 0.8084F, -0.3464F, -2.6872F));

		ModelPart cube_r11 = new ModelPart(List.of(new ModelPart.Cuboid(8, 21, -1.3679F, -6.7998F, -1.0F, 2.0F, 11.0F, 2.0F, 0, 0, 0, false, 256, 256)), Collections.emptyMap());
		cube_r11.setTransform(ModelTransform.of(-25.1328F, -5.1937F, -10.6796F, 0.6598F, 0.6055F, 2.6495F));

		ModelPart cube_r12 = new ModelPart(List.of(new ModelPart.Cuboid(78, 89, -1.0795F, -15.8219F, -1.0F, 2.0F, 32.0F, 2.0F, 0, 0, 0, false, 256, 256)), Collections.emptyMap());
		cube_r12.setTransform(ModelTransform.of(8.391F, -2.2464F, 9.7912F, -1.4839F, 0.0003F, -1.5784F));

		ModelPart cube_r13 = new ModelPart(List.of(new ModelPart.Cuboid(86, 89, -1.0795F, -15.8219F, -1.0F, 2.0F, 32.0F, 2.0F, 0, 0, 0, false, 256, 256)), Collections.emptyMap());
		cube_r13.setTransform(ModelTransform.of(17.611F, -1.719F, -8.0795F, 1.4922F, -0.0001F, 1.579F));

		ModelPart cube_r14 = new ModelPart(List.of(new ModelPart.Cuboid(8, 8, -1.0F, -4.5F, -1.0F, 2.0F, 8.0F, 2.0F, 0, 0, 0, false, 256, 256)), Collections.emptyMap());
		cube_r14.setTransform(ModelTransform.of(-14.9641F, -19.7702F, -24.5192F, 0.2764F, -1.0118F, -1.7913F));

		ModelPart cube_r15 = new ModelPart(List.of(new ModelPart.Cuboid(0, 41, -1.0F, -5.5F, -1.0F, 2.0F, 11.0F, 2.0F, 0, 0, 0, false, 256, 256)), Collections.emptyMap());
		cube_r15.setTransform(ModelTransform.of(-9.7393F, -21.7517F, -20.2957F, 0.98F, -0.4129F, -2.7763F));

		ModelPart cube_r16 = new ModelPart(List.of(new ModelPart.Cuboid(42, 41, -1.3679F, -6.7998F, -1.0F, 2.0F, 11.0F, 2.0F, 0, 0, 0, false, 256, 256)), Collections.emptyMap());
		cube_r16.setTransform(ModelTransform.of(-23.2714F, -22.3783F, -21.5774F, 0.8532F, 0.6828F, 2.7406F));

		ModelPart cube_r17 = new ModelPart(List.of(new ModelPart.Cuboid(32, 91, -1.0795F, -15.8219F, -1.0F, 2.0F, 32.0F, 2.0F, 0, 0, 0, false, 256, 256)), Collections.emptyMap());
		cube_r17.setTransform(ModelTransform.of(-11.5577F, -1.4989F, 10.9205F, -1.5708F, 1.4835F, 3.1416F));

		ModelPart cube_r18 = new ModelPart(List.of(new ModelPart.Cuboid(40, 93, -1.0795F, -15.8219F, -1.0F, 2.0F, 32.0F, 2.0F, 0, 0, 0, false, 256, 256)), Collections.emptyMap());
		cube_r18.setTransform(ModelTransform.of(-8.5577F, -1.4989F, -8.0795F, 1.0059F, 0.0136F, 1.6176F));

		ModelPart cube_r19 = new ModelPart(List.of(new ModelPart.Cuboid(54, 21, -1.0F, -4.5F, -1.0F, 2.0F, 8.0F, 2.0F, 0, 0, 0, false, 256, 256)), Collections.emptyMap());
		cube_r19.setTransform(ModelTransform.of(-26.8919F, -11.7667F, -20.2248F, -1.4436F, -0.9436F, -1.607F));

		ModelPart cube_r20 = new ModelPart(List.of(new ModelPart.Cuboid(54, 0, -1.0F, -5.5F, -1.0F, 2.0F, 11.0F, 2.0F, 0, 0, 0, false, 256, 256)), Collections.emptyMap());
		cube_r20.setTransform(ModelTransform.of(-26.4151F, -18.7541F, -20.3334F, 1.3178F, -1.2688F, 1.8798F));

		ModelPart cube_r21 = new ModelPart(List.of(new ModelPart.Cuboid(24, 97, -1.3679F, -6.7998F, -1.0F, 2.0F, 11.0F, 2.0F, 0, 0, 0, false, 256, 256)), Collections.emptyMap());
		cube_r21.setTransform(ModelTransform.of(-27.7594F, -8.2633F, -11.7726F, 1.4962F, 0.0311F, 1.6355F));

		ModelPart cube_r22 = new ModelPart(List.of(new ModelPart.Cuboid(48, 93, -1.0795F, -15.8219F, -1.0F, 2.0F, 32.0F, 2.0F, 0, 0, 0, false, 256, 256)), Collections.emptyMap());
		cube_r22.setTransform(ModelTransform.of(-8.0521F, -0.9887F, -7.7348F, 1.5496F, -0.5643F, 0.0644F));

		ModelPart cube_r23 = new ModelPart(List.of(new ModelPart.Cuboid(56, 93, -1.0795F, -15.8219F, -1.0F, 2.0F, 32.0F, 2.0F, 0, 0, 0, false, 256, 256)), Collections.emptyMap());
		cube_r23.setTransform(ModelTransform.of(17.089F, -1.1371F, -6.9011F, 1.5712F, -0.0789F, 0.0005F));

		ModelPart cube_r24 = new ModelPart(List.of(new ModelPart.Cuboid(0, 61, -1.0F, -4.5F, -1.0F, 2.0F, 8.0F, 2.0F, 0, 0, 0, false, 256, 256)), Collections.emptyMap());
		cube_r24.setTransform(ModelTransform.of(-5.9356F, -11.441F, -17.159F, -1.2186F, -0.9018F, -1.8884F));

		ModelPart cube_r25 = new ModelPart(List.of(new ModelPart.Cuboid(24, 110, -1.0F, -5.5F, -1.0F, 2.0F, 11.0F, 2.0F, 0, 0, 0, false, 256, 256)), Collections.emptyMap());
		cube_r25.setTransform(ModelTransform.of(-6.1485F, -18.4391F, -17.3685F, 0.8971F, -1.2208F, 2.1773F));

		ModelPart cube_r26 = new ModelPart(List.of(new ModelPart.Cuboid(64, 93, -1.0795F, -15.8219F, -1.0F, 2.0F, 32.0F, 2.0F, 0, 0, 0, false, 256, 256)), Collections.emptyMap());
		cube_r26.setTransform(ModelTransform.of(-11.5022F, -2.1337F, 11.5577F, -0.0869F, -0.0076F, -1.5705F));

		ModelPart cube_r27 = new ModelPart(List.of(new ModelPart.Cuboid(102, 113, -1.3679F, -6.7998F, -1.0F, 2.0F, 11.0F, 2.0F, 0, 0, 0, false, 256, 256)), Collections.emptyMap());
		cube_r27.setTransform(ModelTransform.of(-7.6321F, -7.9505F, -8.828F, 1.3552F, 0.0157F, 1.5304F));

		ModelPart cube_r28 = new ModelPart(List.of(new ModelPart.Cuboid(94, 89, -1.0795F, -15.8219F, -1.0F, 2.0F, 32.0F, 2.0F, 0, 0, 0, false, 256, 256)), Collections.emptyMap());
		cube_r28.setTransform(ModelTransform.of(8.0795F, -1.4989F, 8.5577F, -1.5695F, -0.0866F, -0.0152F));

		ModelPart cube_r29 = new ModelPart(List.of(new ModelPart.Cuboid(36, 64, 6.0F, -32.0F, -8.0F, 2.0F, 5.0F, 2.0F, 0, 0, 0, false, 256, 256)), Collections.emptyMap());
		cube_r29.setTransform(ModelTransform.of(1.0F, 0.0F, 1.0F, 0.0F, -1.5708F, 0.0F));

		ModelPart cube_r30 = new ModelPart(List.of(new ModelPart.Cuboid(44, 66, 6.0F, -32.0F, -8.0F, 2.0F, 5.0F, 2.0F, 0, 0, 0, false, 256, 256)), Collections.emptyMap());
		cube_r30.setTransform(ModelTransform.of(-1.0F, 0.0F, 1.0F, -3.1416F, 0.0F, 3.1416F));

		ModelPart cube_r31 = new ModelPart(List.of(new ModelPart.Cuboid(78, 68, 6.0F, -32.0F, -8.0F, 2.0F, 5.0F, 2.0F, 0, 0, 0, false, 256, 256)), Collections.emptyMap());
		cube_r31.setTransform(ModelTransform.of(-1.0F, 0.0F, -1.0F, 0.0F, 1.5708F, 0.0F));

		ModelPart cube_r32 = new ModelPart(List.of(new ModelPart.Cuboid(104, 54, 4.0F, -27.0F, -9.0F, 5.0F, 3.0F, 5.0F, 0, 0, 0, false, 256, 256)), Collections.emptyMap());
		cube_r32.setTransform(ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

		ModelPart cube_r33 = new ModelPart(List.of(new ModelPart.Cuboid(106, 70, 4.0F, -27.0F, -9.0F, 5.0F, 3.0F, 5.0F, 0, 0, 0, false, 256, 256)), Collections.emptyMap());
		cube_r33.setTransform(ModelTransform.of(0.0F, 0.0F, 0.0F, -3.1416F, 0.0F, 3.1416F));

		ModelPart cube_r34 = new ModelPart(List.of(new ModelPart.Cuboid(106, 70, 4.0F, -27.0F, -9.0F, 5.0F, 3.0F, 5.0F, 0, 0, 0, false, 256, 256)), Collections.emptyMap());
		cube_r34.setTransform(ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		this.bb_main = new ModelPart(
				List.of(
					//new ModelPart.Cuboid(0, 62, -6.0F, -11.0F, -6.0F, 12.0F, 11.0F, 12.0F, 0, 0, 0, false, 256, 256),
					new ModelPart.Cuboid(38, 80, -5.0F, -9.0F, -5.0F, 10.0F, 3.0F, 10.0F, 0, 0, 0, false, 256, 256),
					new ModelPart.Cuboid(0, 85, -4.0F, -13.0F, -4.0F, 8.0F, 4.0F, 8.0F, 0, 0, 0, false, 256, 256),
					new ModelPart.Cuboid(96, 0, -3.0F, -15.0F, -3.0F, 6.0F, 2.0F, 6.0F, 0, 0, 0, false, 256, 256),
					new ModelPart.Cuboid(64, 38, -4.0F, -15.0F, -4.0F, 8.0F, 1.0F, 8.0F, 0, 0, 0, false, 256, 256),
					new ModelPart.Cuboid(54, 21, -6.0F, -20.0F, -6.0F, 12.0F, 5.0F, 12.0F, 0, 0, 0, false, 256, 256),
					new ModelPart.Cuboid(42, 47, -7.0F, -21.0F, -7.0F, 14.0F, 5.0F, 14.0F, 0, 0, 0, false, 256, 256),
					new ModelPart.Cuboid(0, 0, -9.0F, -24.0F, -9.0F, 18.0F, 3.0F, 18.0F, 0, 0, 0, false, 256, 256),
					new ModelPart.Cuboid(68, 80, 7.0F, -32.0F, -9.0F, 2.0F, 5.0F, 2.0F, 0, 0, 0, false, 256, 256),
					new ModelPart.Cuboid(109, 107, 4.0F, -27.0F, -9.0F, 5.0F, 3.0F, 5.0F, 0, 0, 0, false, 256, 256),
					new ModelPart.Cuboid(0, 41, -7.0F, -6.0F, -7.0F, 14.0F, 6.0F, 14.0F, 0, 0, 0, false, 256, 256)
				),
				Map.ofEntries(
						entry("cube_r1", cube_r1),
						entry("cube_r2", cube_r2),
						entry("cube_r3", cube_r3),
						entry("cube_r4", cube_r4),
						entry("cube_r5", cube_r5),
						entry("cube_r6", cube_r6),
						entry("cube_r6_1", cube_r6_1),
						entry("cube_r6_2", cube_r6_2),

						entry("cube_r7", cube_r7),
						entry("cube_r7_1", cube_r7_1),
						entry("cube_r7_2", cube_r7_2),
						entry("cube_r7_3", cube_r7_3),

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
						entry("cube_r31", cube_r31),
						entry("cube_r32", cube_r32),
						entry("cube_r33", cube_r33),
						entry("cube_r34", cube_r34)
				)
		);
	}

	@Override
	public void render(BrokenAnchorBlockEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
		matrices.push();
		// Fetch the appropriate texture
		Identifier textureId = blockEntity.getTextureId();
		// Move the structure
		matrices.translate(0.5, 0, 0.5);
		// Flip it all right-side-up
		matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(180));
		// Render the structure
		this.bb_main.render(matrices, vertexConsumers.getBuffer(RenderLayer.getEntitySolid(textureId)), light, overlay, 1f, 1f, 1f, 1f);
		// Mandatory call after GL calls
		matrices.pop();
	}
}