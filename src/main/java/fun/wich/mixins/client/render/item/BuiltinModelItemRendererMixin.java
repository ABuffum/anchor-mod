package fun.wich.mixins.client.render.item;

import com.mojang.authlib.GameProfile;
import fun.wich.block.basic.ModBedBlock;
import fun.wich.block.piglin.PiglinHeadParent;
import fun.wich.block.ragdoll.RagdollBlock;
import fun.wich.block.ragdoll.RagdollBlockEntity;
import fun.wich.client.render.block.model.PiglinHeadEntityModel;
import fun.wich.client.render.block.renderer.PiglinHeadEntityRenderer;
import fun.wich.client.render.block.renderer.RagdollBlockEntityRenderer;
import fun.wich.client.render.entity.ModEntityModelLayers;
import fun.wich.client.render.entity.model.JavelinEntityModel;
import fun.wich.entity.ModNbtKeys;
import fun.wich.item.JavelinItem;
import fun.wich.util.banners.ModBannerPatternConversions;
import fun.wich.util.banners.ModBannerPatternRenderContext;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.SkullBlockEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.math.BlockPos;
import org.apache.commons.lang3.StringUtils;
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
	private SkullBlockEntityModel ragdollModel = null;
	private JavelinEntityModel javelinModel = null;

	@Inject(method="render", at=@At(value="INVOKE", target="Lnet/minecraft/client/render/block/entity/BannerBlockEntityRenderer;renderCanvas(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/model/ModelPart;Lnet/minecraft/client/util/SpriteIdentifier;ZLjava/util/List;Z)V"))
	private void SetLoomPatterns(ItemStack itemStack, ModelTransformation.Mode mode, MatrixStack matrixStack, VertexConsumerProvider provider, int i, int j, CallbackInfo info) {
		NbtList nbt = ModBannerPatternConversions.getModBannerPatternNbt(itemStack);
		ModBannerPatternRenderContext.setModBannerPatterns(ModBannerPatternConversions.makeModBannerPatternData(nbt));
	}

	@Inject(method="render", at=@At("HEAD"), cancellable=true)
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
			else if (block instanceof RagdollBlock) {
				GameProfile gameProfile2 = null;
				if (stack.hasNbt()) {
					NbtCompound nbtCompound = stack.getNbt();
					if (nbtCompound.contains(ModNbtKeys.OWNER, 10)) {
						gameProfile2 = NbtHelper.toGameProfile(nbtCompound.getCompound(ModNbtKeys.OWNER));
					}
					else if (nbtCompound.contains(ModNbtKeys.OWNER, 8) && !StringUtils.isBlank(nbtCompound.getString(ModNbtKeys.OWNER))) {
						gameProfile2 = new GameProfile(null, nbtCompound.getString(ModNbtKeys.OWNER));
						nbtCompound.remove(ModNbtKeys.OWNER);
						RagdollBlockEntity.loadProperties(gameProfile2, gameProfile -> nbtCompound.put(ModNbtKeys.OWNER, NbtHelper.writeGameProfile(new NbtCompound(), gameProfile)));
					}
				}

				if (this.ragdollModel == null) this.ragdollModel = RagdollBlockEntityRenderer.getSkullModel(this.entityModelLoader);
				RenderLayer renderLayer = RagdollBlockEntityRenderer.getRenderLayer(gameProfile2);
				RagdollBlockEntityRenderer.renderSkull(180.0f, 0.0f, matrices, vertexConsumers, light, this.ragdollModel, renderLayer);
				ci.cancel();
			}
		}
		else if (item instanceof JavelinItem javelin) {
			matrices.push();
			matrices.scale(1.0f, -1.0f, -1.0f);
			if (this.javelinModel == null) this.javelinModel = new JavelinEntityModel(this.entityModelLoader.getModelPart(ModEntityModelLayers.JAVELIN));
			VertexConsumer vertexConsumer2 = ItemRenderer.getDirectItemGlintConsumer(vertexConsumers, RenderLayer.getEntityCutout(javelin.getTexture()), false, stack.hasGlint());
			this.javelinModel.render(matrices, vertexConsumer2, light, overlay, 1.0f, 1.0f, 1.0f, 1.0f);
			matrices.pop();
		}
	}
}