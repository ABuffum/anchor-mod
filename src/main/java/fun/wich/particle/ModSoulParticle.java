package fun.wich.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

@Environment(value=EnvType.CLIENT)
public class ModSoulParticle extends AbstractSlowingParticle {
	private final SpriteProvider spriteProvider;
	protected boolean sculk;

	ModSoulParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteProvider spriteProvider) {
		super(world, x, y, z, velocityX, velocityY, velocityZ);
		this.spriteProvider = spriteProvider;
		this.scale(1.5f);
		this.setSpriteForAge(spriteProvider);
	}

	@Override
	public int getBrightness(float tint) { return this.sculk ? 240 : super.getBrightness(tint); }

	@Override
	public ParticleTextureSheet getType() { return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT; }

	@Override
	public void tick() {
		super.tick();
		this.setSpriteForAge(this.spriteProvider);
	}

	@Environment(value= EnvType.CLIENT)
	public static class SculkSoulFactory implements ParticleFactory<DefaultParticleType> {
		private final SpriteProvider spriteProvider;

		public SculkSoulFactory(SpriteProvider spriteProvider) {
			this.spriteProvider = spriteProvider;
		}

		@Override
		public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
			ModSoulParticle soulParticle = new ModSoulParticle(clientWorld, d, e, f, g, h, i, this.spriteProvider);
			soulParticle.sculk = true;
			return soulParticle;
		}
	}

	@Environment(value=EnvType.CLIENT)
	public static class Factory implements ParticleFactory<DefaultParticleType> {
		private final SpriteProvider spriteProvider;

		public Factory(SpriteProvider spriteProvider) { this.spriteProvider = spriteProvider; }

		@Override
		public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
			return new ModSoulParticle(clientWorld, d, e, f, g, h, i, this.spriteProvider);
		}
	}
}
