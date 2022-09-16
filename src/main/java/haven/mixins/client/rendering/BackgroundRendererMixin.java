package haven.mixins.client.rendering;

import com.mojang.blaze3d.systems.RenderSystem;
import haven.blood.BloodFluid;
import haven.blocks.mud.MudFluid;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BackgroundRenderer.class)
public class BackgroundRendererMixin {
	@Shadow
	private static float red;
	@Shadow
	private static float green;
	@Shadow
	private static float blue;

	@Inject(at = @At("HEAD"), method = "render", cancellable = true)
	private static void mudFogColor(Camera camera, float tickDelta, ClientWorld world, int i, float f, CallbackInfo ci) {
		BlockPos blockPos = camera.getBlockPos();
		FluidState fluidState = world.getFluidState(blockPos);
		Fluid fluid = fluidState.getFluid();
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


	@Inject(at = @At("HEAD"), method = "applyFog", cancellable = true)
	private static void ApplyFog(Camera camera, BackgroundRenderer.FogType fogType, float viewDistance, boolean thickFog, CallbackInfo ci) {
		Entity entity = camera.getFocusedEntity();
		World world = entity.getEntityWorld();
		BlockPos blockPos = camera.getBlockPos();
		FluidState fluidState = world.getFluidState(blockPos);
		Fluid fluid = fluidState.getFluid();
		if (fluid instanceof MudFluid) {
			RenderSystem.setShaderFogStart(0.25F);
			RenderSystem.setShaderFogEnd(1.0F);
			ci.cancel();
		}
		else if (fluid instanceof BloodFluid) {
			RenderSystem.setShaderFogStart(0.25F);
			RenderSystem.setShaderFogEnd(1.0F);
			ci.cancel();
		}
	}
}
