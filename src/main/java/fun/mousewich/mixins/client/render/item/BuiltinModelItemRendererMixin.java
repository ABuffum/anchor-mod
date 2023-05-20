package fun.mousewich.mixins.client.render.item;

import fun.mousewich.block.basic.ModBedBlock;
import fun.mousewich.block.piglin.PiglinHeadParent;
import fun.mousewich.client.render.block.model.PiglinHeadEntityModel;
import fun.mousewich.client.render.block.renderer.PiglinHeadEntityRenderer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BuiltinModelItemRenderer.class)
public class BuiltinModelItemRendererMixin {
	@Shadow
	@Final
	private BlockEntityRenderDispatcher blockEntityRenderDispatcher;

	@Shadow @Final private EntityModelLoader entityModelLoader;

	private PiglinHeadEntityModel piglinHeadModel = null;

	@Inject(at = @At("HEAD"), method = "render", cancellable = true)
	private void renderItems(ItemStack stack, ModelTransformation.Mode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, CallbackInfo ci) {
		Item item = stack.getItem();
		if (item instanceof BlockItem blockItem) {
			Block block = blockItem.getBlock();
			if (block instanceof ModBedBlock) {
				BlockState blockState = block.getDefaultState();
				this.blockEntityRenderDispatcher.renderEntity((BlockEntity) BlockEntityType.BED.instantiate(BlockPos.ORIGIN, blockState), matrices, vertexConsumers, light, overlay);
				ci.cancel();
			}
			else if (block instanceof PiglinHeadParent head) {
				if (this.piglinHeadModel == null) this.piglinHeadModel = PiglinHeadEntityRenderer.getModel(this.entityModelLoader);
				PiglinHeadEntityRenderer.renderSkull(null, 180.0f, 0.0f, matrices, vertexConsumers, light, piglinHeadModel, head.isZombified());
				ci.cancel();
			}
		}
	}
}