package haven.mixins.client.rendering;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import haven.ModBase;
import haven.blood.BloodFluid;
import haven.blocks.mud.MudFluid;
import haven.effects.StatusEffectFogModifier;
import haven.util.MixinStore;
import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.apace100.apoli.power.ModifyCameraSubmersionTypePower;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.CameraSubmersionType;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.CubicSampler;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;
import net.minecraft.world.biome.source.BiomeAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(BackgroundRenderer.class)
public class BackgroundRendererMixin {
	private static final List<StatusEffectFogModifier> FOG_MODIFIERS = Lists.newArrayList(new StatusEffectFogModifier.BlindnessFogModifier(), new StatusEffectFogModifier.DarknessFogModifier(), new StatusEffectFogModifier.FlashbangedFogModifier());

	@Shadow
	private static float red;
	@Shadow
	private static float green;
	@Shadow
	private static float blue;
	@Shadow
	private static int waterFogColor = -1;
	@Shadow
	private static int nextWaterFogColor = -1;
	@Shadow
	private static long lastWaterFogColorUpdateTime = -1L;

	private static StatusEffectFogModifier getFogModifier(Entity entity, float f) {
		if (entity instanceof LivingEntity livingEntity) {
			return FOG_MODIFIERS.stream().filter((statusEffectFogModifier) -> {
				return statusEffectFogModifier.shouldApply(livingEntity, f);
			}).findFirst().orElse(null);
		}
		else return null;
	}

	@Inject(method = "render", at = @At("HEAD"), cancellable = true)
	private static void mudFogColor(Camera camera, float tickDelta, ClientWorld world, int i, float f, CallbackInfo ci) {
		Fluid fluid = world.getFluidState(camera.getBlockPos()).getFluid();
		if (fluid instanceof MudFluid) {
			red = 87.0F / 255.0F;
			green = 54.0F / 255.0F;
			blue = 35.0F / 255.0F;
			RenderSystem.clearColor(red, green, blue, 0.0F);
			ci.cancel();
		}
		else if (fluid instanceof BloodFluid) {
			red = 127.0F / 255.0F;
			green = 0.0F / 255.0F;
			blue = 0.0F / 255.0F;
			RenderSystem.clearColor(red, green, blue, 0.0F);
			ci.cancel();
		}
	}
	@Inject(method = "render", at = @At("HEAD"), cancellable = true)
	private static void render(Camera camera, float tickDelta, ClientWorld world, int viewDistance, float skyDarkness, CallbackInfo ci) {
		CameraSubmersionType cameraSubmersionType = camera.getSubmersionType();
		//Origins override
		if(camera.getFocusedEntity() instanceof LivingEntity) {
			for(ModifyCameraSubmersionTypePower p : PowerHolderComponent.getPowers(camera.getFocusedEntity(), ModifyCameraSubmersionTypePower.class)) {
				if(p.doesModify(cameraSubmersionType)) {
					cameraSubmersionType = p.getNewType();
				}
			}
		}
		Entity entity = camera.getFocusedEntity();
		if (cameraSubmersionType == CameraSubmersionType.WATER) {
			long l = Util.getMeasuringTimeMs();
			int i = world.getBiome(new BlockPos(camera.getPos())).getWaterFogColor();
			if (lastWaterFogColorUpdateTime < 0L) {
				waterFogColor = i;
				nextWaterFogColor = i;
				lastWaterFogColorUpdateTime = l;
			}
			int j = waterFogColor >> 16 & 0xFF;
			int k = waterFogColor >> 8 & 0xFF;
			int m = waterFogColor & 0xFF;
			int n = nextWaterFogColor >> 16 & 0xFF;
			int o = nextWaterFogColor >> 8 & 0xFF;
			int p = nextWaterFogColor & 0xFF;
			float f = MathHelper.clamp((float)(l - lastWaterFogColorUpdateTime) / 5000.0f, 0.0f, 1.0f);
			float g = MathHelper.lerp(f, n, j);
			float h = MathHelper.lerp(f, o, k);
			float q = MathHelper.lerp(f, p, m);
			red = g / 255.0f;
			green = h / 255.0f;
			blue = q / 255.0f;
			if (waterFogColor != i) {
				waterFogColor = i;
				nextWaterFogColor = MathHelper.floor(g) << 16 | MathHelper.floor(h) << 8 | MathHelper.floor(q);
				lastWaterFogColorUpdateTime = l;
			}
		}
		else if (cameraSubmersionType == CameraSubmersionType.LAVA) {
			red = 0.6f;
			green = 0.1f;
			blue = 0.0f;
			lastWaterFogColorUpdateTime = -1L;
		}
		else if (cameraSubmersionType == CameraSubmersionType.POWDER_SNOW) {
			red = 0.623f;
			green = 0.734f;
			blue = 0.785f;
			lastWaterFogColorUpdateTime = -1L;
			RenderSystem.clearColor(red, green, blue, 0.0f);
		}
		else {
			float g;
			float h;
			float f;
			float r = 0.25f + 0.75f * (float)viewDistance / 32.0f;
			r = 1.0f - (float)Math.pow(r, 0.25);
			Vec3d vec3d = world.method_23777(camera.getPos(), tickDelta);
			float s = (float)vec3d.x;
			float t = (float)vec3d.y;
			float u = (float)vec3d.z;
			float v = MathHelper.clamp(MathHelper.cos(world.getSkyAngle(tickDelta) * ((float)Math.PI * 2)) * 2.0f + 0.5f, 0.0f, 1.0f);
			BiomeAccess biomeAccess = world.getBiomeAccess();
			Vec3d vec3d2 = camera.getPos().subtract(2.0, 2.0, 2.0).multiply(0.25);
			Vec3d vec3d3 = CubicSampler.sampleColor(vec3d2, (x, y, z) -> world.getSkyProperties().adjustFogColor(Vec3d.unpackRgb(biomeAccess.getBiomeForNoiseGen(x, y, z).getFogColor()), v));
			red = (float)vec3d3.getX();
			green = (float)vec3d3.getY();
			blue = (float)vec3d3.getZ();
			if (viewDistance >= 4) {
				float[] fs;
				f = MathHelper.sin(world.getSkyAngleRadians(tickDelta)) > 0.0f ? -1.0f : 1.0f;
				Vec3f vec3f = new Vec3f(f, 0.0f, 0.0f);
				h = camera.getHorizontalPlane().dot(vec3f);
				if (h < 0.0f) {
					h = 0.0f;
				}
				if (h > 0.0f && (fs = world.getSkyProperties().getFogColorOverride(world.getSkyAngle(tickDelta), tickDelta)) != null) {
					red = red * (1.0f - (h *= fs[3])) + fs[0] * h;
					green = green * (1.0f - h) + fs[1] * h;
					blue = blue * (1.0f - h) + fs[2] * h;
				}
			}
			red += (s - red) * r;
			green += (t - green) * r;
			blue += (u - blue) * r;
			f = world.getRainGradient(tickDelta);
			if (f > 0.0f) {
				float g2 = 1.0f - f * 0.5f;
				h = 1.0f - f * 0.4f;
				red *= g2;
				green *= g2;
				blue *= h;
			}
			if ((g = world.getThunderGradient(tickDelta)) > 0.0f) {
				h = 1.0f - g * 0.5f;
				red *= h;
				green *= h;
				blue *= h;
			}
			lastWaterFogColorUpdateTime = -1L;
		}
		float r = (float)(((float)camera.getPos().y - (float)world.getBottomY()) * world.getLevelProperties().getHorizonShadingRatio());
		StatusEffectFogModifier statusEffectFogModifier = getFogModifier(entity, tickDelta);
		if (statusEffectFogModifier != null) {
			LivingEntity livingEntity = (LivingEntity)entity;
			StatusEffectInstance statusEffectInstance = livingEntity.getStatusEffect(statusEffectFogModifier.getStatusEffect());
			r = statusEffectFogModifier.applyColorModifier(livingEntity, statusEffectInstance, r, tickDelta);

			if (r < 1.0f && cameraSubmersionType != CameraSubmersionType.LAVA && cameraSubmersionType != CameraSubmersionType.POWDER_SNOW) {
				if (r < 0.0f) r = 0.0f;
				red *= r;
				green *= r;
				blue *= r;
			}
			if (skyDarkness > 0.0f) {
				red = red * (1.0f - skyDarkness) + red * 0.7f * skyDarkness;
				green = green * (1.0f - skyDarkness) + green * 0.6f * skyDarkness;
				blue = blue * (1.0f - skyDarkness) + blue * 0.6f * skyDarkness;
			}
			if (livingEntity.hasStatusEffect(ModBase.FLASHBANGED_EFFECT)) {
				red = Math.max(red, 1 - red);
				green = Math.max(green, 1 - green);
				blue = Math.max(blue, 1 - blue);
			}
			RenderSystem.clearColor(red, green, blue, 0.0f);
			ci.cancel();
		}
	}

	@Inject(method = "applyFog", at = @At("HEAD"), cancellable = true)
	private static void applyFog(Camera camera, BackgroundRenderer.FogType fogType, float viewDistance, boolean thickFog, CallbackInfo ci) {
		Entity entity = camera.getFocusedEntity();
		Fluid fluid = entity.getEntityWorld().getFluidState(camera.getBlockPos()).getFluid();
		if (fluid instanceof MudFluid || fluid instanceof BloodFluid) {
			RenderSystem.setShaderFogStart(0.25F);
			RenderSystem.setShaderFogEnd(1.0F);
			ci.cancel();
		}
		CameraSubmersionType cameraSubmersionType = camera.getSubmersionType();
		if (cameraSubmersionType != CameraSubmersionType.LAVA) {
			if (cameraSubmersionType != CameraSubmersionType.POWDER_SNOW) {
				StatusEffectFogModifier.FogData fogData = new StatusEffectFogModifier.FogData(fogType);
				StatusEffectFogModifier statusEffectFogModifier = getFogModifier(entity, MixinStore.worldrenderer_render_tickDelta);
				if (statusEffectFogModifier != null) {
					LivingEntity livingEntity = (LivingEntity)entity;
					StatusEffectInstance statusEffectInstance = livingEntity.getStatusEffect(statusEffectFogModifier.getStatusEffect());
					if (statusEffectInstance != null) {
						statusEffectFogModifier.applyStartEndModifier(fogData, livingEntity, statusEffectInstance, viewDistance, MixinStore.worldrenderer_render_tickDelta);
					}
					RenderSystem.setShaderFogStart(fogData.fogStart);
					RenderSystem.setShaderFogEnd(fogData.fogEnd);
					ci.cancel();
				}
			}
		}
	}
}
