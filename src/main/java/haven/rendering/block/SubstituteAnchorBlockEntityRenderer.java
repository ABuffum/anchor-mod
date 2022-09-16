package haven.rendering.block;

import haven.HavenMod;
import haven.entities.anchors.SubstituteAnchorBlockEntity;

import net.fabricmc.api.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.entity.*;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.*;

@Environment(EnvType.CLIENT)
public class SubstituteAnchorBlockEntityRenderer implements BlockEntityRenderer<SubstituteAnchorBlockEntity> {
	public SubstituteAnchorBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) { }

	@Override
	public void render(SubstituteAnchorBlockEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
		matrices.push();
		// Fetch the appropriate texture
		Identifier textureId = blockEntity.getTextureId();
		// Calculate the current offset in the y value
		double offset = (Math.PI - Math.sin((blockEntity.getWorld().getTime() + tickDelta) / 8.0)) / 16.0;
		double offset2 = Math.cos((blockEntity.getWorld().getTime() + tickDelta) / 8.0) / 16.0;
		if (blockEntity.getOwner() != 0) {
			matrices.scale(2F, 2F, 2F);
			matrices.translate(0.0625 + offset, ((offset + offset2) / 2F), 0.25 + offset2);
			matrices.multiply(Vec3f.NEGATIVE_Y.getDegreesQuaternion((blockEntity.getWorld().getTime() + tickDelta) * 4));
			// Render the core
			int lightAbove = WorldRenderer.getLightmapCoordinates(blockEntity.getWorld(), blockEntity.getPos().up());
			MinecraftClient.getInstance().getItemRenderer().renderItem(new ItemStack(HavenMod.ANCHOR_CORES.get(blockEntity.getOwner())), ModelTransformation.Mode.GROUND, lightAbove, overlay, matrices, vertexConsumers, 0);
		}
		// Mandatory call after GL calls
		matrices.pop();
	}
}