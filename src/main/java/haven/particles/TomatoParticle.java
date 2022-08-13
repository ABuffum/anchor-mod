package haven.particles;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

public class TomatoParticle extends SpriteBillboardParticle {
	protected TomatoParticle(ClientWorld level, double xCoord, double yCoord, double zCoord,
							  SpriteProvider spriteSet, double xd, double yd, double zd) {
		super(level, xCoord, yCoord, zCoord, xd, yd, zd);

		//this.velocityMultiplier = 0.6F;
		this.x = xd;
		this.y = yd;
		this.z = zd;
		this.scale *= 0.75F;
		this.maxAge = 20;
		this.setSpriteForAge(spriteSet);

		this.colorRed = 1f;
		this.colorGreen = 1f;
		this.colorBlue = 1f;
	}

	@Override
	public void tick() {
		super.tick();
		fadeOut();
	}

	private void fadeOut() {
		this.colorAlpha = (-(1/(float)maxAge) * age + 1);
	}

	@Override
	public ParticleTextureSheet getType() {
		return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
	}

	@Environment(EnvType.CLIENT)
	public static class Factory implements ParticleFactory<DefaultParticleType> {
		private final SpriteProvider sprites;

		public Factory(SpriteProvider spriteSet) {
			this.sprites = spriteSet;
		}

		public Particle createParticle(DefaultParticleType particleType, ClientWorld level, double x, double y, double z,
									   double dx, double dy, double dz) {
			return new TomatoParticle(level, x, y, z, this.sprites, dx, dy, dz);
		}
	}
}
