package fun.mousewich.mixins.client.render.entity.renderer;

import fun.mousewich.ModBase;
import fun.mousewich.ModId;
import fun.mousewich.block.sign.HangingSignBlock;
import fun.mousewich.block.sign.WallHangingSignBlock;
import fun.mousewich.client.render.entity.model.HangingSignModel;
import fun.mousewich.util.dye.ModDyeColor;
import fun.mousewich.util.dye.ModDyedSign;
import net.minecraft.block.*;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.block.entity.SignBlockEntityRenderer;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.OrderedText;
import net.minecraft.util.Identifier;
import net.minecraft.util.SignType;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.*;

@Mixin(SignBlockEntityRenderer.class)
public class SignBlockEntityRendererMixin {
	private final Map<SignType, HangingSignModel> MODELS = new HashMap<>();

	@Inject(method="<init>", at = @At("TAIL"))
	private void Init(BlockEntityRendererFactory.Context context, CallbackInfo ci) {
		Set<SignType> signTypes = new HashSet<>(List.of(SignType.OAK, SignType.SPRUCE, SignType.BIRCH, SignType.ACACIA, SignType.JUNGLE, SignType.DARK_OAK, SignType.CRIMSON, SignType.WARPED));
		signTypes.addAll(ModBase.SIGN_TYPES);
		signTypes.addAll(ModBase.HANGING_SIGN_SUBTYPES.keySet());
		for (SignType type : signTypes) {
			MODELS.put(type, new HangingSignModel(context.getLayerModelPart(HangingSignModel.createSignLayer(type))));
		}
	}
	@Inject(method="render(Lnet/minecraft/block/entity/SignBlockEntity;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;II)V", at = @At("HEAD"), cancellable = true)
	private void RenderHangingOrModDyedSign(SignBlockEntity signBlockEntity, float f, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, int j, CallbackInfo ci) {
		if (signBlockEntity == null) return;
		Block block = signBlockEntity.getCachedState().getBlock();
		if (block instanceof HangingSignBlock || block instanceof WallHangingSignBlock) {
			renderHangingSign(signBlockEntity, matrixStack, vertexConsumerProvider, i, j);
			ci.cancel();
		}
		else if (signBlockEntity instanceof ModDyedSign) {
			RenderModColor(signBlockEntity, f, matrixStack, vertexConsumerProvider, i, j);
			ci.cancel();
		}
	}

	private void renderHangingSign(SignBlockEntity signBlockEntity, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, int j) {
		BlockState blockState = signBlockEntity.getCachedState();
		matrixStack.push();
		Block block = blockState.getBlock();
		SignType signType = block instanceof AbstractSignBlock sign ? sign.getSignType() : SignType.OAK;
		matrixStack.translate(0.5, 0.9375, 0.5);
		float g;
		if (block instanceof WallHangingSignBlock) g = -blockState.get(WallSignBlock.FACING).asRotation();
		else g = -blockState.get(HangingSignBlock.ROTATION) * 22.5f;
		matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(g));
		matrixStack.translate(0.0f, -0.3125f, 0.0f);
		HangingSignModel model = MODELS.get(signType);
		model.updateVisibleParts(blockState);
		renderSign(matrixStack, vertexConsumerProvider, i, j, signType, model);
		renderText(signBlockEntity, matrixStack, vertexConsumerProvider, i);
	}
	private static void renderSign(MatrixStack matrices, VertexConsumerProvider verticesProvider, int light, int overlay, SignType type, HangingSignModel model) {
		matrices.push();
		matrices.scale((float) 1.0, -(float) 1.0, -(float) 1.0);
		VertexConsumer vertexConsumer = verticesProvider.getBuffer(RenderLayer.getEntityCutoutNoCull(getHangingSignTexture(type)));
		model.root.render(matrices, vertexConsumer, light, overlay);
		matrices.pop();
	}

	@Shadow @Final private TextRenderer textRenderer;
	@Shadow private static int getColor(SignBlockEntity sign) { return 0; }
	@Shadow private static boolean shouldRender(SignBlockEntity sign, int signColor) { return false; }
	@Shadow @Final private Map<SignType, SignBlockEntityRenderer.SignModel> typeToModel;

	private void renderText(SignBlockEntity blockEntity, MatrixStack matrices, VertexConsumerProvider verticesProvider, int light) {
		int l;
		boolean bl;
		int k;
		float f = 0.015625f;
		Vec3d vec3d = new Vec3d(0.0, -0.32f, 0.063f);
		matrices.translate(vec3d.x, vec3d.y, vec3d.z);
		matrices.scale(f, -f, f);
		int i = getColor(blockEntity);
		OrderedText[] orderedTexts = blockEntity.updateSign(MinecraftClient.getInstance().shouldFilterText(), text -> {
			List<OrderedText> list = this.textRenderer.wrapLines(text, 50);
			return list.isEmpty() ? OrderedText.EMPTY : list.get(0);
		});
		if (blockEntity.isGlowingText()) {
			k = blockEntity instanceof ModDyedSign dyed ? dyed.GetModTextColor().getSignColor() : blockEntity.getTextColor().getSignColor();
			bl = shouldRender(blockEntity, k);
			l = 0xF000F0;
		}
		else {
			k = i;
			bl = false;
			l = light;
		}
		for (int m = 0; m < 4; ++m) {
			OrderedText orderedText = orderedTexts[m];
			float g = -this.textRenderer.getWidth(orderedText) / 2f;
			if (bl) {
				this.textRenderer.drawWithOutline(orderedText, g, m * 9 - 18, k, i, matrices.peek().getModel(), verticesProvider, l);
				continue;
			}
			this.textRenderer.draw(orderedText, g, (float)(m * 9 - 18), k, false, matrices.peek().getModel(), verticesProvider, false, 0, l);
		}
		matrices.pop();
	}
	private static final Map<SignType, Identifier> HANGING_SIGN_TYPE_TEXTURES = new HashMap<>();
	private static Identifier getHangingSignTexture(SignType signType) {
		if (!HANGING_SIGN_TYPE_TEXTURES.containsKey(signType)) {
			HANGING_SIGN_TYPE_TEXTURES.put(signType, createHangingSignTextureId(signType));
		}
		return HANGING_SIGN_TYPE_TEXTURES.get(signType);
	}
	private static Identifier createHangingSignTextureId(SignType type) {
		if (ModBase.SIGN_TYPES.contains(type) || ModBase.HANGING_SIGN_SUBTYPES.containsKey(type)) {
			String name = type.getName();
			return ModId.ID(name.startsWith("minecraft:") ?
					"minecraft:textures/entity/signs/hanging/" + name.substring("minecraft:".length()) + ".png"
					: "textures/entity/signs/hanging/" + name + ".png");
		}
		return new Identifier("textures/entity/signs/hanging/" + type.getName() + ".png");
	}

	@Inject(method="getColor", at=@At("HEAD"), cancellable=true)
	private static void GetModColor(SignBlockEntity sign, CallbackInfoReturnable<Integer> cir) {
		if (!(sign instanceof ModDyedSign dyed)) return;
		int i = dyed.GetModTextColor().getSignColor();
		int j = (int)((double) NativeImage.getRed(i) * 0.4);
		int k = (int)((double)NativeImage.getGreen(i) * 0.4);
		int l = (int)((double)NativeImage.getBlue(i) * 0.4);
		if (i == ModDyeColor.BLACK.getSignColor() && sign.isGlowingText()) cir.setReturnValue(-988212);
		else cir.setReturnValue(NativeImage.getAbgrColor(0, l, k, j));
	}
	public void RenderModColor(SignBlockEntity signBlockEntity, float f, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, int j) {
		int o;
		boolean bl;
		int n;
		BlockState blockState = signBlockEntity.getCachedState();
		matrixStack.push();
		SignType signType = SignBlockEntityRenderer.getSignType(blockState.getBlock());
		SignBlockEntityRenderer.SignModel signModel = this.typeToModel.get(signType);
		if (blockState.getBlock() instanceof SignBlock) {
			matrixStack.translate(0.5, 0.5, 0.5);
			float h = -((float)(blockState.get(SignBlock.ROTATION) * 360) / 16.0f);
			matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(h));
			signModel.stick.visible = true;
		} else {
			matrixStack.translate(0.5, 0.5, 0.5);
			float h = -blockState.get(WallSignBlock.FACING).asRotation();
			matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(h));
			matrixStack.translate(0.0, -0.3125, -0.4375);
			signModel.stick.visible = false;
		}
		matrixStack.push();
		matrixStack.scale(0.6666667f, -0.6666667f, -0.6666667f);
		SpriteIdentifier spriteIdentifier = TexturedRenderLayers.getSignTextureId(signType);
		VertexConsumer vertexConsumer = spriteIdentifier.getVertexConsumer(vertexConsumerProvider, signModel::getLayer);
		signModel.root.render(matrixStack, vertexConsumer, i, j);
		matrixStack.pop();
		matrixStack.translate(0.0, 0.3333333432674408, 0.046666666865348816);
		matrixStack.scale(0.010416667f, -0.010416667f, 0.010416667f);
		int l = getColor(signBlockEntity);
		OrderedText[] orderedTexts = signBlockEntity.updateSign(MinecraftClient.getInstance().shouldFilterText(), text -> {
			List<OrderedText> list = this.textRenderer.wrapLines(text, 90);
			return list.isEmpty() ? OrderedText.EMPTY : list.get(0);
		});
		if (signBlockEntity.isGlowingText()) {
			n = signBlockEntity instanceof ModDyedSign dyed ? dyed.GetModTextColor().getSignColor() : signBlockEntity.getTextColor().getSignColor();
			bl = shouldRender(signBlockEntity, n);
			o = 0xF000F0;
		}
		else {
			n = l;
			bl = false;
			o = i;
		}
		for (int p = 0; p < 4; ++p) {
			OrderedText orderedText = orderedTexts[p];
			float q = -this.textRenderer.getWidth(orderedText) / 2;
			if (bl) {
				this.textRenderer.drawWithOutline(orderedText, q, p * 10 - 20, n, l, matrixStack.peek().getModel(), vertexConsumerProvider, o);
				continue;
			}
			this.textRenderer.draw(orderedText, q, (float)(p * 10 - 20), n, false, matrixStack.peek().getModel(), vertexConsumerProvider, false, 0, o);
		}
		matrixStack.pop();
	}
}
