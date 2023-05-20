package fun.mousewich.client.render.entity.model;

import fun.mousewich.entity.hostile.slime.TropicalSlimeEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.util.Pair;

import java.util.ArrayList;
import java.util.List;

@Environment(value= EnvType.CLIENT)
public class TropicalSlimeEntityModel extends SinglePartEntityModel<TropicalSlimeEntity> {
	private final ModelPart root;
	public final ModelPart[] bodies;

	public TropicalSlimeEntityModel(ModelPart root) {
		this.root = root;
		ArrayList<ModelPart> temp = new ArrayList<>(48);
		try {
			for (int i = 0; i < 48; i++) {
				temp.add(this.root.getChild(EntityModelPartNames.BODY + i));
			}
		}
		catch (Throwable ignored) { }
		this.bodies = temp.toArray(ModelPart[]::new);
	}

	public static TexturedModelData getOuterTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData root = modelData.getRoot();
		root.addChild(EntityModelPartNames.CUBE, ModelPartBuilder.create().uv(80, 154).cuboid(-4.0f, 16.0f, -4.0f, 8.0f, 8.0f, 8.0f), ModelTransform.NONE);
		return TexturedModelData.of(modelData, 140, 170);
	}

	public static TexturedModelData getInnerTexturedModelData() {
		List<Pair<Integer, Integer>> bodyUV = List.of(
				new Pair<>(0, 5), new Pair<>(28, 5), new Pair<>(56, 5), new Pair<>(84, 5), new Pair<>(112, 5),
				new Pair<>(0, 22), new Pair<>(28, 22), new Pair<>(56, 22), new Pair<>(84, 22), new Pair<>(112, 22),
				new Pair<>(0, 39), new Pair<>(28, 39), new Pair<>(56, 39), new Pair<>(84, 39), new Pair<>(112, 39),
				new Pair<>(0, 56), new Pair<>(28, 56), new Pair<>(56, 56), new Pair<>(84, 56), new Pair<>(112, 56),
				new Pair<>(0, 73), new Pair<>(28, 73), new Pair<>(56, 73), new Pair<>(84, 73), new Pair<>(112, 73),
				new Pair<>(0, 90), new Pair<>(28, 90), new Pair<>(56, 90), new Pair<>(84, 90), new Pair<>(112, 90),
				new Pair<>(0, 107), new Pair<>(28, 107), new Pair<>(56, 107), new Pair<>(84, 107), new Pair<>(112, 107),
				new Pair<>(0, 124), new Pair<>(28, 124), new Pair<>(56, 124), new Pair<>(84, 124), new Pair<>(112, 124),
				new Pair<>(0, 141), new Pair<>(28, 141), new Pair<>(56, 141), new Pair<>(84, 141), new Pair<>(112, 141),
				new Pair<>(0, 158), new Pair<>(28, 158), new Pair<>(56, 158)
		);
		ModelData modelData = new ModelData();
		ModelPartData root = modelData.getRoot();
		for (int i = 0; i < 48; i++) {
			Pair<Integer, Integer> pair = bodyUV.get(i);
			root.addChild(EntityModelPartNames.BODY + i, ModelPartBuilder.create().uv(pair.getLeft(), pair.getRight())
					.cuboid(-3.0f, 17.0f, -3.0f, 6.0f, 6.0f, 6.0f), ModelTransform.NONE);
		}
		root.addChild(EntityModelPartNames.RIGHT_EYE, ModelPartBuilder.create().uv(112, 153).cuboid(-3.25f, 18.0f, -3.5f, 2.0f, 2.0f, 2.0f), ModelTransform.NONE);
		root.addChild(EntityModelPartNames.LEFT_EYE, ModelPartBuilder.create().uv(112, 157).cuboid(1.25f, 18.0f, -3.5f, 2.0f, 2.0f, 2.0f), ModelTransform.NONE);
		root.addChild(EntityModelPartNames.MOUTH, ModelPartBuilder.create().uv(112, 161).cuboid(0.0f, 21.0f, -3.5f, 1.0f, 1.0f, 1.0f), ModelTransform.NONE);
		return TexturedModelData.of(modelData, 140, 170);
	}
	@Override
	public void setAngles(TropicalSlimeEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) { }
	@Override
	public ModelPart getPart() { return this.root; }
}