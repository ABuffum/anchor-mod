package haven.particles;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;

@Environment(value=EnvType.CLIENT)
public class SculkChargeParticle extends SpriteBillboardParticle {
	private final SpriteProvider spriteProvider;

	SculkChargeParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteProvider spriteProvider) {
		super(world, x, y, z, velocityX, velocityY, velocityZ);
		this.spriteProvider = spriteProvider;
		this.scale(1.5f);
		this.collidesWithWorld = false;
		this.setSpriteForAge(spriteProvider);
	}

	@Override
	public int getBrightness(float tint) {
		return 240;
	}

	@Override
	public ParticleTextureSheet getType() {
		return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
	}

	@Override
	public void tick() {
		super.tick();
		this.setSpriteForAge(this.spriteProvider);
		this.velocityX *= 0.96F;
		this.velocityY *= 0.96F;
		this.velocityZ *= 0.96F;
	}

	@Environment(value= EnvType.CLIENT)
	public record Factory(SpriteProvider spriteProvider) implements ParticleFactory<SculkChargeParticleEffect>
	{
		@Override
		public Particle createParticle(SculkChargeParticleEffect sculkChargeParticleEffect, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
			SculkChargeParticle sculkChargeParticle = new SculkChargeParticle(clientWorld, d, e, f, g, h, i, this.spriteProvider);
			sculkChargeParticle.setVelocity(g, h, i);
			sculkChargeParticle.prevAngle = sculkChargeParticleEffect.roll();
			sculkChargeParticle.angle = sculkChargeParticleEffect.roll();
			sculkChargeParticle.setMaxAge(clientWorld.random.nextInt(12) + 8);
			return sculkChargeParticle;
		}
	}
}