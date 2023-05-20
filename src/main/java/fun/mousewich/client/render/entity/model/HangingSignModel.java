package fun.mousewich.client.render.entity.model;

import fun.mousewich.ModBase;
import fun.mousewich.block.sign.HangingSignBlock;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.SignType;

@Environment(value= EnvType.CLIENT)
public class HangingSignModel extends Model {
	private static final String PLANK = "plank";
	private static final String V_CHAINS = "vChains";
	public static final String NORMAL_CHAINS = "normalChains";
	public static final String CHAIN_L1 = "chainL1";
	public static final String CHAIN_L2 = "chainL2";
	public static final String CHAIN_R1 = "chainR1";
	public static final String CHAIN_R2 = "chainR2";
	public static final String BOARD = "board";

	public static EntityModelLayer createSignLayer(SignType type) {
		if (ModBase.SIGN_TYPES.contains(type)) {
			String name = type.getName();
			Identifier id = ModBase.ID(name.startsWith("minecraft:") ? "minecraft:hanging_sign/" + name.substring("minecraft:".length()) : "hanging_sign/" + name);
			return new EntityModelLayer(id, "main");
		}
		return new EntityModelLayer(new Identifier("hanging_sign/" + type.getName()), "main");
	}

	public final ModelPart root;
	public final ModelPart plank;
	public final ModelPart vChains;
	public final ModelPart normalChains;
	public HangingSignModel(ModelPart root) {
		super(RenderLayer::getEntityCutoutNoCull);
		this.root = root;
		this.plank = root.getChild(PLANK);
		this.normalChains = root.getChild(NORMAL_CHAINS);
		this.vChains = root.getChild(V_CHAINS);
	}
	public void updateVisibleParts(BlockState state) {
		boolean bl;
		this.plank.visible = bl = !(state.getBlock() instanceof HangingSignBlock);
		this.vChains.visible = false;
		this.normalChains.visible = true;
		if (!bl) {
			boolean bl2 = state.get(Properties.ATTACHED);
			this.normalChains.visible = !bl2;
			this.vChains.visible = bl2;
		}
	}
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
		this.root.render(matrices, vertices, light, overlay, red, green, blue, alpha);
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		modelPartData.addChild(BOARD, ModelPartBuilder.create().uv(0, 12).cuboid(-7.0f, 0.0f, -1.0f, 14.0f, 10.0f, 2.0f), ModelTransform.NONE);
		modelPartData.addChild(PLANK, ModelPartBuilder.create().uv(0, 0).cuboid(-8.0f, -6.0f, -2.0f, 16.0f, 2.0f, 4.0f), ModelTransform.NONE);
		ModelPartData modelPartData2 = modelPartData.addChild(NORMAL_CHAINS, ModelPartBuilder.create(), ModelTransform.NONE);
		modelPartData2.addChild(CHAIN_L1, ModelPartBuilder.create().uv(0, 6).cuboid(-1.5f, 0.0f, 0.0f, 3.0f, 6.0f, 0.0f), ModelTransform.of(-5.0f, -6.0f, 0.0f, 0.0f, -0.7853982f, 0.0f));
		modelPartData2.addChild(CHAIN_L2, ModelPartBuilder.create().uv(6, 6).cuboid(-1.5f, 0.0f, 0.0f, 3.0f, 6.0f, 0.0f), ModelTransform.of(-5.0f, -6.0f, 0.0f, 0.0f, 0.7853982f, 0.0f));
		modelPartData2.addChild(CHAIN_R1, ModelPartBuilder.create().uv(0, 6).cuboid(-1.5f, 0.0f, 0.0f, 3.0f, 6.0f, 0.0f), ModelTransform.of(5.0f, -6.0f, 0.0f, 0.0f, -0.7853982f, 0.0f));
		modelPartData2.addChild(CHAIN_R2, ModelPartBuilder.create().uv(6, 6).cuboid(-1.5f, 0.0f, 0.0f, 3.0f, 6.0f, 0.0f), ModelTransform.of(5.0f, -6.0f, 0.0f, 0.0f, 0.7853982f, 0.0f));
		modelPartData.addChild(V_CHAINS, ModelPartBuilder.create().uv(14, 6).cuboid(-6.0f, -6.0f, 0.0f, 12.0f, 6.0f, 0.0f), ModelTransform.NONE);
		return TexturedModelData.of(modelData, 64, 32);
	}
}
