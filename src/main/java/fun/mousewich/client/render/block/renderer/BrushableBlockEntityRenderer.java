package fun.mousewich.client.render.block.renderer;

import fun.mousewich.block.ModProperties;
import fun.mousewich.block.dust.BrushableBlockEntity;
import fun.mousewich.util.math.RotationAxis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

@Environment(value=EnvType.CLIENT)
public class BrushableBlockEntityRenderer<E extends BrushableBlockEntity> implements BlockEntityRenderer<E> {
	public BrushableBlockEntityRenderer(BlockEntityRendererFactory.Context context) { }

	@Override
	public void render(E entity, float f, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, int j) {
		if (entity.getWorld() == null) return;
		int k = entity.getCachedState().get(ModProperties.DUSTED);
		if (k <= 0) return;
		Direction direction = entity.getHitDirection();
		if (direction == null) return;
		ItemStack itemStack = entity.getItem();
		if (itemStack.isEmpty()) return;
		matrixStack.push();
		matrixStack.translate(0.0f, 0.5f, 0.0f);
		float[] fs = this.getTranslation(direction, k);
		matrixStack.translate(fs[0], fs[1], fs[2]);
		matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(75));
		boolean bl = direction == Direction.EAST || direction == Direction.WEST;
		matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((bl ? 90 : 0) + 11));
		matrixStack.scale(0.5f, 0.5f, 0.5f);
		int l = WorldRenderer.getLightmapCoordinates(entity.getWorld(), entity.getCachedState(), entity.getPos().offset(direction));
		MinecraftClient client = MinecraftClient.getInstance();
		if (client != null) renderItemExplicit(client.getItemRenderer(), itemStack, l, matrixStack, vertexConsumerProvider, entity.getWorld());
		matrixStack.pop();
	}

	private void renderItemExplicit(ItemRenderer itemRenderer, ItemStack stack, int light, MatrixStack matrices, VertexConsumerProvider vertexConsumers, World world) {
		itemRenderer.renderItem(null, stack, ModelTransformation.Mode.FIXED, false, matrices, vertexConsumers, world, light, OverlayTexture.DEFAULT_UV, 0);
	}

	private float[] getTranslation(Direction direction, int dustedLevel) {
		float[] fs = new float[] { 0.5f, 0.0f, 0.5f };
		float f = (float)dustedLevel * 0.075f;
		switch (direction) {
			case EAST -> fs[0] = 0.73f + f;
			case WEST -> fs[0] = 0.25f - f;
			case UP -> fs[1] = 0.25f + f;
			case DOWN -> fs[1] = -0.23f - f;
			case NORTH -> fs[2] = 0.25f - f;
			case SOUTH -> fs[2] = 0.73f + f;
		}
		return fs;
	}
}
