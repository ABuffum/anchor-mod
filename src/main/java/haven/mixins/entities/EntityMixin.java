package haven.mixins.entities;

import haven.HavenMod;
import haven.blocks.mud.MudFluid;
import haven.blood.BloodFluid;
import haven.events.HavenGameEvent;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.*;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(Entity.class)
public abstract class EntityMixin {
	@Inject(method="onSwimmingStart", at = @At("HEAD"), cancellable = true)
	private void OnSwimmingStart(CallbackInfo ci) {
		Entity e = (Entity)(Object)this;
		BlockPos pos = new BlockPos(e.getX(), e.getY(), e.getZ());
		FluidState fluidState = e.world.getFluidState(pos);
		Fluid fluid = fluidState.getFluid();
		boolean blood = fluid instanceof BloodFluid;
		boolean mud = fluid instanceof MudFluid;
		if (blood || mud) {
			Entity entity = e.hasPassengers() && e.getPrimaryPassenger() != null ? e.getPrimaryPassenger() : e;
			float f = entity == e ? 0.2F : 0.9F;
			Vec3d vec3d = entity.getVelocity();
			float g = Math.min(1.0F, (float)Math.sqrt(vec3d.x * vec3d.x * 0.20000000298023224D + vec3d.y * vec3d.y + vec3d.z * vec3d.z * 0.20000000298023224D) * f);
			Random random = e.world.getRandom();
			EntityInvoker ea = (EntityInvoker)e;
			if (g < 0.25F) e.playSound(ea.getSplashSoundInvoker(), g, 1.0F + (random.nextFloat() - random.nextFloat()) * 0.4F);
			else e.playSound(ea.getHighSpeedSplashSoundInvoker(), g, 1.0F + (random.nextFloat() - random.nextFloat()) * 0.4F);
			float h = (float) MathHelper.floor(e.getY());
			int j;
			double k;
			double l;
			for(j = 0; (float)j < 1.0F + e.getType().getDimensions().width * 20.0F; ++j) {
				k = (random.nextDouble() * 2.0D - 1.0D) * (double)e.getType().getDimensions().width;
				l = (random.nextDouble() * 2.0D - 1.0D) * (double)e.getType().getDimensions().width;
				if (blood) e.world.addParticle(HavenMod.BLOOD_BUBBLE, e.getX() + k, h + 1.0F, e.getZ() + l, vec3d.x, vec3d.y - random.nextDouble() * 0.20000000298023224D, vec3d.z);
				else if (mud) e.world.addParticle(HavenMod.MUD_BUBBLE, e.getX() + k, h + 1.0F, e.getZ() + l, vec3d.x, vec3d.y - random.nextDouble() * 0.20000000298023224D, vec3d.z);
			}
			for(j = 0; (float)j < 1.0F + e.getType().getDimensions().width * 20.0F; ++j) {
				k = (random.nextDouble() * 2.0D - 1.0D) * (double)e.getType().getDimensions().width;
				l = (random.nextDouble() * 2.0D - 1.0D) * (double)e.getType().getDimensions().width;
				if (blood) e.world.addParticle(HavenMod.BLOOD_SPLASH, e.getX() + k, h + 1.0F, e.getZ() + l, vec3d.x, vec3d.y, vec3d.z);
				else if (mud) e.world.addParticle(HavenMod.MUD_SPLASH, e.getX() + k, h + 1.0F, e.getZ() + l, vec3d.x, vec3d.y, vec3d.z);
			}
			e.emitGameEvent(GameEvent.SPLASH);
			ci.cancel();
		}
	}
	@Inject(method="kill", at = @At("TAIL"))
	public void Kill(CallbackInfo ci) {
		((Entity)(Object)this).emitGameEvent(HavenGameEvent.ENTITY_DIE);
	}
}
