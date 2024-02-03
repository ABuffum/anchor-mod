package fun.wich.mixins.client.render.block;

import fun.wich.util.banners.ModBannerPatternContainer;
import fun.wich.util.banners.ModBannerPatternData;
import fun.wich.util.banners.ModBannerPatternRenderContext;
import net.minecraft.block.entity.BannerBlockEntity;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BannerBlockEntityRenderer;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Collections;
import java.util.List;

@Mixin(BannerBlockEntityRenderer.class)
public abstract class BannerBlockEntityRendererMixin {
	@Unique private static List<ModBannerPatternData> modBannerPatterns;
	@Unique private static int nextModBannerPatternIndex;

	@Inject(method = "render(Lnet/minecraft/block/entity/BannerBlockEntity;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;II)V", at = @At("HEAD"))
	private void Render(BannerBlockEntity banner, float f1, MatrixStack stack, VertexConsumerProvider provider, int i, int j, CallbackInfo ci) {
		ModBannerPatternRenderContext.setModBannerPatterns(((ModBannerPatternContainer) banner).getModBannerPatterns());
	}

	@Inject(method = "renderCanvas(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/model/ModelPart;Lnet/minecraft/client/util/SpriteIdentifier;ZLjava/util/List;Z)V",  at = @At("HEAD"))
	private static void ResetLocalContext(CallbackInfo info) {
		nextModBannerPatternIndex = 0;
		modBannerPatterns = ModBannerPatternRenderContext.getModBannerPatterns();
	}

	@Inject(method = "renderCanvas(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/model/ModelPart;Lnet/minecraft/client/util/SpriteIdentifier;ZLjava/util/List;Z)V",  at=@At(value="INVOKE", target="Ljava/util/List;get(I)Ljava/lang/Object;", ordinal=0, remap=false), locals = LocalCapture.CAPTURE_FAILHARD)
	private static void RenderPatternInline(MatrixStack stack, VertexConsumerProvider provider, int light, int overlay, ModelPart canvas, SpriteIdentifier baseSprite, boolean isBanner, List<Pair<BannerPattern, DyeColor>> patterns, boolean glint, CallbackInfo ci, int idx) {
		while (nextModBannerPatternIndex < modBannerPatterns.size()) {
			ModBannerPatternData data = modBannerPatterns.get(nextModBannerPatternIndex);
			if (data.index == idx - 1) {
				renderModBannerPattern(data, stack, provider, canvas, light, overlay, isBanner);
				nextModBannerPatternIndex++;
			}
			else break;
		}
	}

	@Inject(method = "renderCanvas(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/model/ModelPart;Lnet/minecraft/client/util/SpriteIdentifier;ZLjava/util/List;Z)V",  at = @At("RETURN"))
	private static void PatternRenderAfter(MatrixStack stack, VertexConsumerProvider provider, int light, int overlay, ModelPart canvas, SpriteIdentifier baseSprite, boolean isBanner, List<Pair<BannerPattern, DyeColor>> patterns, boolean glint, CallbackInfo info) {
		for (int i = nextModBannerPatternIndex; i < modBannerPatterns.size(); i++) {
			renderModBannerPattern(modBannerPatterns.get(i), stack, provider, canvas, light, overlay, isBanner);
		}
		modBannerPatterns = Collections.emptyList();
	}

	@Unique
	private static void renderModBannerPattern( ModBannerPatternData data, MatrixStack stack, VertexConsumerProvider provider, ModelPart canvas, int light, int overlay, boolean banner) {
		Identifier spriteId = data.pattern.getSpriteId(banner);
		SpriteIdentifier realSpriteId = new SpriteIdentifier(banner ? TexturedRenderLayers.BANNER_PATTERNS_ATLAS_TEXTURE : TexturedRenderLayers.SHIELD_PATTERNS_ATLAS_TEXTURE, spriteId);
		float[] color = data.color.getColorComponents();
		canvas.render(stack, realSpriteId.getVertexConsumer(provider, RenderLayer::getEntityNoOutline), light, overlay, color[0], color[1], color[2], 1.0f);
	}
}