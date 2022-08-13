package haven.anchors;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import static java.util.Map.entry;

// Made with Blockbench 4.2.5
// Exported for Minecraft version 1.17 - 1.18 with Mojang mappings
// Paste this class into your mod and generate all required imports


public class AnchorBlockModel<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
//	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "custom_model"), "main");
	private final ModelPart bb_main;

	public AnchorBlockModel(ModelPart root) {
		//this.bb_main = root.getChild("bb_main");
		
		ModelPart cube_r1 = new ModelPart(Arrays.asList(new ModelPart.Cuboid(0, 97, -1.5F, -10.0F, -1.5F, 3.0F, 20.0F, 3.0F, 0, 0, 0, false, 256, 256)), Collections.EMPTY_MAP);
		ModelPart cube_r2 = new ModelPart(Arrays.asList(new ModelPart.Cuboid(12, 97, -1.5F, -10.0F, -1.5F, 3.0F, 20.0F, 3.0F, 0, 0, 0, false, 256, 256)), Collections.EMPTY_MAP);
		ModelPart cube_r3 = new ModelPart(Arrays.asList(new ModelPart.Cuboid(102, 89, -1.5F, -10.0F, -1.5F, 3.0F, 20.0F, 3.0F, 0, 0, 0, false, 256, 256)), Collections.EMPTY_MAP);
		ModelPart cube_r4 = new ModelPart(Arrays.asList(new ModelPart.Cuboid(104, 26, -6.0F, 3.0F, -6.0F, 3.0F, 20.0F, 3.0F, 0, 0, 0, false, 256, 256)), Collections.EMPTY_MAP);
		ModelPart cube_r5 = new ModelPart(Arrays.asList(new ModelPart.Cuboid(0, 8, -1.0F, -4.5F, -1.0F, 2.0F, 8.0F, 2.0F, 0, 0, 0, false, 256, 256)), Collections.EMPTY_MAP);
		ModelPart cube_r6 = new ModelPart(Arrays.asList(new ModelPart.Cuboid(0, 21, -1.0F, -5.5F, -1.0F, 2.0F, 11.0F, 2.0F, 0, 0, 0, false, 256, 256)), Collections.EMPTY_MAP);
		ModelPart cube_r7 = new ModelPart(Arrays.asList(new ModelPart.Cuboid(8, 21, -1.3679F, -6.7998F, -1.0F, 2.0F, 11.0F, 2.0F, 0, 0, 0, false, 256, 256)), Collections.EMPTY_MAP);
		ModelPart cube_r8 = new ModelPart(Arrays.asList(new ModelPart.Cuboid(78, 89, -1.0795F, -15.8219F, -1.0F, 2.0F, 32.0F, 2.0F, 0, 0, 0, false, 256, 256)), Collections.EMPTY_MAP);
		ModelPart cube_r9 = new ModelPart(Arrays.asList(new ModelPart.Cuboid(86, 89, -1.0795F, -15.8219F, -1.0F, 2.0F, 32.0F, 2.0F, 0, 0, 0, false, 256, 256)), Collections.EMPTY_MAP);
		ModelPart cube_r10 = new ModelPart(Arrays.asList(new ModelPart.Cuboid(8, 8, -1.0F, -4.5F, -1.0F, 2.0F, 8.0F, 2.0F, 0, 0, 0, false, 256, 256)), Collections.EMPTY_MAP);
		ModelPart cube_r11 = new ModelPart(Arrays.asList(new ModelPart.Cuboid(0, 41, -1.0F, -5.5F, -1.0F, 2.0F, 11.0F, 2.0F, 0, 0, 0, false, 256, 256)), Collections.EMPTY_MAP);
		ModelPart cube_r12 = new ModelPart(Arrays.asList(new ModelPart.Cuboid(42, 41, -1.3679F, -6.7998F, -1.0F, 2.0F, 11.0F, 2.0F, 0, 0, 0, false, 256, 256)), Collections.EMPTY_MAP);
		ModelPart cube_r13 = new ModelPart(Arrays.asList(new ModelPart.Cuboid(32, 91, -1.0795F, -15.8219F, -1.0F, 2.0F, 32.0F, 2.0F, 0, 0, 0, false, 256, 256)), Collections.EMPTY_MAP);
		ModelPart cube_r14 = new ModelPart(Arrays.asList(new ModelPart.Cuboid(40, 93, -1.0795F, -15.8219F, -1.0F, 2.0F, 32.0F, 2.0F, 0, 0, 0, false, 256, 256)), Collections.EMPTY_MAP);
		ModelPart cube_r15 = new ModelPart(Arrays.asList(new ModelPart.Cuboid(54, 21, -1.0F, -4.5F, -1.0F, 2.0F, 8.0F, 2.0F, 0, 0, 0, false, 256, 256)), Collections.EMPTY_MAP);
		ModelPart cube_r16 = new ModelPart(Arrays.asList(new ModelPart.Cuboid(54, 0, -1.0F, -5.5F, -1.0F, 2.0F, 11.0F, 2.0F, 0, 0, 0, false, 256, 256)), Collections.EMPTY_MAP);
		ModelPart cube_r17 = new ModelPart(Arrays.asList(new ModelPart.Cuboid(24, 97, -1.3679F, -6.7998F, -1.0F, 2.0F, 11.0F, 2.0F, 0, 0, 0, false, 256, 256)), Collections.EMPTY_MAP);
		ModelPart cube_r18 = new ModelPart(Arrays.asList(new ModelPart.Cuboid(48, 93, -1.0795F, -15.8219F, -1.0F, 2.0F, 32.0F, 2.0F, 0, 0, 0, false, 256, 256)), Collections.EMPTY_MAP);
		ModelPart cube_r19 = new ModelPart(Arrays.asList(new ModelPart.Cuboid(56, 93, -1.0795F, -15.8219F, -1.0F, 2.0F, 32.0F, 2.0F, 0, 0, 0, false, 256, 256)), Collections.EMPTY_MAP);
		ModelPart cube_r20 = new ModelPart(Arrays.asList(new ModelPart.Cuboid(0, 61, -1.0F, -4.5F, -1.0F, 2.0F, 8.0F, 2.0F, 0, 0, 0, false, 256, 256)), Collections.EMPTY_MAP);
		ModelPart cube_r21 = new ModelPart(Arrays.asList(new ModelPart.Cuboid(24, 110, -1.0F, -5.5F, -1.0F, 2.0F, 11.0F, 2.0F, 0, 0, 0, false, 256, 256)), Collections.EMPTY_MAP);
		ModelPart cube_r22 = new ModelPart(Arrays.asList(new ModelPart.Cuboid(64, 93, -1.0795F, -15.8219F, -1.0F, 2.0F, 32.0F, 2.0F, 0, 0, 0, false, 256, 256)), Collections.EMPTY_MAP);
		ModelPart cube_r23 = new ModelPart(Arrays.asList(new ModelPart.Cuboid(102, 113, -1.3679F, -6.7998F, -1.0F, 2.0F, 11.0F, 2.0F, 0, 0, 0, false, 256, 256)), Collections.EMPTY_MAP);
		ModelPart cube_r24 = new ModelPart(Arrays.asList(new ModelPart.Cuboid(94, 89, -1.0795F, -15.8219F, -1.0F, 2.0F, 32.0F, 2.0F, 0, 0, 0, false, 256, 256)), Collections.EMPTY_MAP);
		ModelPart cube_r25 = new ModelPart(Arrays.asList(new ModelPart.Cuboid(36, 64, 6.0F, -38.0F, -8.0F, 2.0F, 5.0F, 2.0F, 0, 0, 0, false, 256, 256)), Collections.EMPTY_MAP);
		ModelPart cube_r26 = new ModelPart(Arrays.asList(new ModelPart.Cuboid(44, 66, 6.0F, -38.0F, -8.0F, 2.0F, 5.0F, 2.0F, 0, 0, 0, false, 256, 256)), Collections.EMPTY_MAP);
		ModelPart cube_r27 = new ModelPart(Arrays.asList(new ModelPart.Cuboid(78, 68, 6.0F, -38.0F, -8.0F, 2.0F, 5.0F, 2.0F, 0, 0, 0, false, 256, 256)), Collections.EMPTY_MAP);
		ModelPart cube_r28 = new ModelPart(Arrays.asList(new ModelPart.Cuboid(104, 54, 4.0F, -33.0F, -9.0F, 5.0F, 3.0F, 5.0F, 0, 0, 0, false, 256, 256)), Collections.EMPTY_MAP);
		ModelPart cube_r29 = new ModelPart(Arrays.asList(new ModelPart.Cuboid(105, 11, 4.0F, -33.0F, -9.0F, 5.0F, 3.0F, 5.0F, 0, 0, 0, false, 256, 256)), Collections.EMPTY_MAP);
		ModelPart cube_r30 = new ModelPart(Arrays.asList(new ModelPart.Cuboid(106, 70, 4.0F, -33.0F, -9.0F, 5.0F, 3.0F, 5.0F, 0, 0, 0, false, 256, 256)), Collections.EMPTY_MAP);
		
		ModelPart soul_r1 = new ModelPart(Arrays.asList(new ModelPart.Cuboid(79, 71, -5.0F, -29.0F, -46.0F, 9.0F, 9.0F, 9.0F, 0, 0, 0, false, 256, 256)), Collections.EMPTY_MAP);

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
		
		soul_r1.setTransform(ModelTransform.of(0.0F, 0.0F, 0.0F, -1.0472F, 1.0472F, 0.0F));
		
		this.bb_main = new ModelPart(
		    Arrays.asList(
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
		    	//
		    	entry("soul_r1", soul_r1)
		    )
		);
		
	}
/*
	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(0, 61).addBox(-6.0F, -12.0F, -6.0F, 12.0F, 12.0F, 12.0F, new CubeDeformation(0.0F))
		.texOffs(38, 80).addBox(-5.0F, -15.0F, -5.0F, 10.0F, 3.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(0, 85).addBox(-4.0F, -19.0F, -4.0F, 8.0F, 4.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(96, 0).addBox(-3.0F, -21.0F, -3.0F, 6.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(64, 38).addBox(-4.0F, -21.0F, -4.0F, 8.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(54, 21).addBox(-6.0F, -26.0F, -6.0F, 12.0F, 5.0F, 12.0F, new CubeDeformation(0.0F))
		.texOffs(42, 47).addBox(-7.0F, -27.0F, -7.0F, 14.0F, 5.0F, 14.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-9.0F, -30.0F, -9.0F, 18.0F, 3.0F, 18.0F, new CubeDeformation(0.0F))
		.texOffs(111, 44).addBox(4.0F, -33.0F, -9.0F, 5.0F, 3.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(68, 80).addBox(7.0F, -38.0F, -9.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(109, 107).addBox(4.0F, -33.0F, -9.0F, 5.0F, 3.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(0, 21).addBox(-9.0F, -74.0F, -9.0F, 18.0F, 2.0F, 18.0F, new CubeDeformation(0.0F))
		.texOffs(84, 54).addBox(-12.0F, -73.5F, -7.0F, 3.0F, 2.0F, 14.0F, new CubeDeformation(0.0F))
		.texOffs(90, 21).addBox(-7.0F, -73.5F, -12.0F, 14.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(84, 38).addBox(9.0F, -73.5F, -7.0F, 3.0F, 2.0F, 14.0F, new CubeDeformation(0.0F))
		.texOffs(72, 16).addBox(-7.0F, -73.5F, 9.0F, 14.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(54, 0).addBox(-7.0F, -76.0F, -7.0F, 14.0F, 2.0F, 14.0F, new CubeDeformation(0.0F))
		.texOffs(48, 66).addBox(-5.0F, -80.0F, -5.0F, 10.0F, 4.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-2.0F, -84.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 34).addBox(-1.0F, -87.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 41).addBox(-7.0F, -12.0F, -7.0F, 14.0F, 6.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition cube_r1 = bb_main.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 97).addBox(-1.5F, -10.0F, -1.5F, 3.0F, 20.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.4548F, -71.1605F, -7.2113F, -0.2618F, 0.0F, -0.2618F));

		PartDefinition cube_r2 = bb_main.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(12, 97).addBox(-1.5F, -10.0F, -1.5F, 3.0F, 20.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.4548F, -71.1605F, 7.2887F, 0.2618F, 0.0F, -0.2618F));

		PartDefinition cube_r3 = bb_main.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(102, 89).addBox(-1.5F, -10.0F, -1.5F, 3.0F, 20.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.2952F, -71.1605F, 7.2887F, 0.2618F, 0.0F, 0.2618F));

		PartDefinition cube_r4 = bb_main.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(104, 26).addBox(-6.0F, 3.0F, -6.0F, 3.0F, 20.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -81.0F, 0.5F, -0.2618F, 0.0F, 0.2618F));

		PartDefinition cube_r5 = bb_main.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(0, 8).addBox(-1.0F, -4.5F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.6442F, -71.9596F, 0.4652F, 1.5708F, 0.0F, 1.6581F));

		PartDefinition cube_r6 = bb_main.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(0, 21).addBox(-1.0F, -5.5F, -1.0F, 2.0F, 11.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.2956F, -67.9748F, 6.2152F, 1.5708F, 0.9163F, 1.6581F));

		PartDefinition cube_r7 = bb_main.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(8, 21).addBox(-1.3679F, -6.7998F, -1.0F, 2.0F, 11.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.2253F, -67.1706F, -7.3679F, -1.5708F, 0.9163F, -1.4835F));

		PartDefinition cube_r8 = bb_main.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(78, 89).addBox(-1.0795F, -15.8219F, -1.0F, 2.0F, 32.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.611F, -48.719F, 7.9205F, -1.5708F, 1.4835F, -1.4835F));

		PartDefinition cube_r9 = bb_main.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(86, 89).addBox(-1.0795F, -15.8219F, -1.0F, 2.0F, 32.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.611F, -48.719F, -8.0795F, 1.5708F, 1.4835F, 1.6581F));

		PartDefinition cube_r10 = bb_main.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(8, 8).addBox(-1.0F, -4.5F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-10.591F, -70.7394F, 0.4652F, 1.5708F, 0.0F, 1.4835F));

		PartDefinition cube_r11 = bb_main.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(0, 41).addBox(-1.0F, -5.5F, -1.0F, 2.0F, 11.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-10.2424F, -66.7546F, 6.2152F, 1.5708F, 0.9163F, 1.4835F));

		PartDefinition cube_r12 = bb_main.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(42, 41).addBox(-1.3679F, -6.7998F, -1.0F, 2.0F, 11.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-10.172F, -65.9505F, -7.3679F, -1.5708F, 0.9163F, -1.6581F));

		PartDefinition cube_r13 = bb_main.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(32, 91).addBox(-1.0795F, -15.8219F, -1.0F, 2.0F, 32.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.5577F, -47.4989F, 7.9205F, -1.5708F, 1.4835F, -1.6581F));

		PartDefinition cube_r14 = bb_main.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(40, 93).addBox(-1.0795F, -15.8219F, -1.0F, 2.0F, 32.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.5577F, -47.4989F, -8.0795F, 1.5708F, 1.4835F, 1.4835F));

		PartDefinition cube_r15 = bb_main.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(54, 21).addBox(-1.0F, -4.5F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.4652F, -70.7394F, -10.591F, 0.0F, -0.0873F, 1.5708F));

		PartDefinition cube_r16 = bb_main.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(54, 0).addBox(-1.0F, -5.5F, -1.0F, 2.0F, 11.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.2152F, -66.7546F, -10.2424F, 0.0693F, -0.0531F, 0.6527F));

		PartDefinition cube_r17 = bb_main.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(24, 97).addBox(-1.3679F, -6.7998F, -1.0F, 2.0F, 11.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.3679F, -65.9505F, -10.172F, 0.0693F, 0.0531F, -0.6527F));

		PartDefinition cube_r18 = bb_main.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(48, 93).addBox(-1.0795F, -15.8219F, -1.0F, 2.0F, 32.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.9205F, -47.4989F, -8.5577F, 0.0869F, 0.0076F, -0.0869F));

		PartDefinition cube_r19 = bb_main.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(56, 93).addBox(-1.0795F, -15.8219F, -1.0F, 2.0F, 32.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0795F, -47.4989F, -8.5577F, 0.0869F, -0.0076F, 0.0869F));

		PartDefinition cube_r20 = bb_main.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(0, 61).addBox(-1.0F, -4.5F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.4652F, -70.7394F, 10.591F, 0.0F, 0.0873F, 1.5708F));

		PartDefinition cube_r21 = bb_main.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(24, 110).addBox(-1.0F, -5.5F, -1.0F, 2.0F, 11.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.2152F, -66.7546F, 10.2424F, -0.0693F, 0.0531F, 0.6527F));

		PartDefinition cube_r22 = bb_main.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(64, 93).addBox(-1.0795F, -15.8219F, -1.0F, 2.0F, 32.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.9205F, -47.4989F, 8.5577F, -0.0869F, -0.0076F, -0.0869F));

		PartDefinition cube_r23 = bb_main.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(102, 113).addBox(-1.3679F, -6.7998F, -1.0F, 2.0F, 11.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.3679F, -65.9505F, 10.172F, -0.0693F, -0.0531F, -0.6527F));

		PartDefinition cube_r24 = bb_main.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(94, 89).addBox(-1.0795F, -15.8219F, -1.0F, 2.0F, 32.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0795F, -47.4989F, 8.5577F, -0.0869F, 0.0076F, 0.0869F));

		PartDefinition soul_r1 = bb_main.addOrReplaceChild("soul_r1", CubeListBuilder.create().texOffs(79, 71).addBox(-5.0F, -29.0F, -46.0F, 9.0F, 9.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.0472F, 1.0472F, 0.0F));

		PartDefinition cube_r25 = bb_main.addOrReplaceChild("cube_r25", CubeListBuilder.create().texOffs(36, 64).addBox(6.0F, -38.0F, -8.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 0.0F, 1.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r26 = bb_main.addOrReplaceChild("cube_r26", CubeListBuilder.create().texOffs(44, 66).addBox(6.0F, -38.0F, -8.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.0F, 1.0F, -3.1416F, 0.0F, 3.1416F));

		PartDefinition cube_r27 = bb_main.addOrReplaceChild("cube_r27", CubeListBuilder.create().texOffs(78, 68).addBox(6.0F, -38.0F, -8.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.0F, -1.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r28 = bb_main.addOrReplaceChild("cube_r28", CubeListBuilder.create().texOffs(104, 54).addBox(4.0F, -33.0F, -9.0F, 5.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r29 = bb_main.addOrReplaceChild("cube_r29", CubeListBuilder.create().texOffs(105, 11).addBox(4.0F, -33.0F, -9.0F, 5.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -3.1416F, 0.0F, 3.1416F));

		PartDefinition cube_r30 = bb_main.addOrReplaceChild("cube_r30", CubeListBuilder.create().texOffs(106, 70).addBox(4.0F, -33.0F, -9.0F, 5.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}
*/
	/*
	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}*/
/*
	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		bb_main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}*/

	@Override
	public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
		// TODO Auto-generated method stub
		bb_main.render(matrices, vertices, light, overlay, red, green, blue, alpha);
	}
}