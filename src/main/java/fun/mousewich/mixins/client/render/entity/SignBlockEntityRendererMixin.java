package fun.mousewich.mixins.client.render.entity;

import fun.mousewich.ModBase;
import fun.mousewich.block.sign.HangingSignBlock;
import fun.mousewich.block.sign.WallHangingSignBlock;
import fun.mousewich.client.render.entity.model.HangingSignModel;
import net.minecraft.block.AbstractSignBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.WallSignBlock;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.block.entity.SignBlockEntityRenderer;
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

import java.util.*;

import static fun.mousewich.ModBase.SIGN_TYPES;

@Mixin(SignBlockEntityRenderer.class)
public class SignBlockEntityRendererMixin {
	private final Map<SignType, HangingSignModel> MODELS = new HashMap<>();

	@Inject(method="<init>", at = @At("TAIL"))
	private void Init(BlockEntityRendererFactory.Context context, CallbackInfo ci) {
		Set<SignType> signTypes = new HashSet<>(List.of(SignType.OAK, SignType.SPRUCE, SignType.BIRCH, SignType.ACACIA, SignType.JUNGLE, SignType.DARK_OAK, SignType.CRIMSON, SignType.WARPED));
		signTypes.addAll(SIGN_TYPES);
		for (SignType type : signTypes) {
			MODELS.put(type, new HangingSignModel(context.getLayerModelPart(HangingSignModel.createSignLayer(type))));
		}
	}
	@Inject(method="render(Lnet/minecraft/block/entity/SignBlockEntity;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;II)V", at = @At("HEAD"), cancellable = true)
	private void RenderHangingSign(SignBlockEntity signBlockEntity, float f, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, int j, CallbackInfo ci) {
		if (signBlockEntity == null) return;
		Block block = signBlockEntity.getCachedState().getBlock();
		if (block instanceof HangingSignBlock || block instanceof WallHangingSignBlock) {
			renderHangingSign(signBlockEntity, matrixStack, vertexConsumerProvider, i, j);
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

	@Shadow @Final
	private TextRenderer textRenderer;
	@Shadow
	private static int getColor(SignBlockEntity sign) { return 0; }
	@Shadow
	private static boolean shouldRender(SignBlockEntity sign, int signColor) { return false; }
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
			k = blockEntity.getTextColor().getSignColor();
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
		if (ModBase.SIGN_TYPES.contains(type)) {
			String name = type.getName();
			return ModBase.ID(name.startsWith("minecraft:") ?
					"minecraft:textures/entity/signs/hanging/" + name.substring("minecraft:".length()) + ".png"
					: "textures/entity/signs/hanging/" + name + ".png");
		}
		return new Identifier("textures/entity/signs/hanging/" + type.getName() + ".png");
	}
}
