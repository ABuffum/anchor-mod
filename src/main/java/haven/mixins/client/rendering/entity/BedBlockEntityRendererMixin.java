package haven.mixins.client.rendering.entity;

import haven.blocks.HavenBedBlock;
import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import net.minecraft.block.*;
import net.minecraft.block.entity.BedBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.enums.BedPart;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BedBlockEntityRenderer;
import net.minecraft.client.render.block.entity.LightmapCoordinatesRetriever;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BedBlockEntityRenderer.class)
public abstract class BedBlockEntityRendererMixin {
	@Inject(method="render(Lnet/minecraft/block/entity/BedBlockEntity;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;II)V", at=@At("HEAD"), cancellable = true)
	private void Render(BedBlockEntity bedBlockEntity, float f, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, int j, CallbackInfo ci) {
		_render(bedBlockEntity, f, matrixStack, vertexConsumerProvider, i, j);
		ci.cancel();
	}

	private void _render(BedBlockEntity bedBlockEntity, float f, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, int j) {
		World world = bedBlockEntity.getWorld();
		Block block = (world != null ? world.getBlockState(bedBlockEntity.getPos()) : bedBlockEntity.getCachedState()).getBlock();
		SpriteIdentifier spriteIdentifier;
		if (block instanceof HavenBedBlock bed) {
			spriteIdentifier = new SpriteIdentifier(TexturedRenderLayers.BEDS_ATLAS_TEXTURE, bed.GetTexture());
		}
		else {
			spriteIdentifier = TexturedRenderLayers.BED_TEXTURES[bedBlockEntity.getColor().getId()];
		}
		BedBlockEntityRenderer bber = (BedBlockEntityRenderer)(Object)this;
		BedBlockEntityRendererAccessor bbera = (BedBlockEntityRendererAccessor)bber;
		BedBlockEntityRendererInvoker bberi = (BedBlockEntityRendererInvoker)bber;
		if (world != null) {
			BlockState blockState = bedBlockEntity.getCachedState();
			DoubleBlockProperties.PropertySource<? extends BedBlockEntity> propertySource = DoubleBlockProperties.toPropertySource(BlockEntityType.BED, BedBlock::getBedPart, BedBlock::getOppositePartDirection, ChestBlock.FACING, blockState, world, bedBlockEntity.getPos(), (worldx, pos) -> {
				return false;
			});
			int k = ((Int2IntFunction)propertySource.apply(new LightmapCoordinatesRetriever())).get(i);
			bberi.InvokeRenderPart(matrixStack, vertexConsumerProvider, blockState.get(BedBlock.PART) == BedPart.HEAD ? bbera.getBedHead() : bbera.getBedFoot(), (Direction)blockState.get(BedBlock.FACING), spriteIdentifier, k, j, false);
		} else {
			bberi.InvokeRenderPart(matrixStack, vertexConsumerProvider, bbera.getBedHead(), Direction.SOUTH, spriteIdentifier, i, j, false);
			bberi.InvokeRenderPart(matrixStack, vertexConsumerProvider, bbera.getBedFoot(), Direction.SOUTH, spriteIdentifier, i, j, true);
		}
	}
}
