package fun.wich.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

@Environment(value= EnvType.CLIENT)
public class ModExplosionLargeParticle extends SpriteBillboardParticle {
	private final SpriteProvider spriteProvider;

	protected ModExplosionLargeParticle(ClientWorld world, double x, double y, double z, double d, SpriteProvider spriteProvider) {
		super(world, x, y, z, 0.0, 0.0, 0.0);
		this.maxAge = 6 + this.random.nextInt(4);
		this.colorRed = this.colorGreen = this.colorBlue = this.random.nextFloat() * 0.6f + 0.4f;
		this.scale = 2.0f * (1.0f - (float)d * 0.5f);
		this.setSpriteForAge(this.spriteProvider = spriteProvider);
	}

	@Override
	public int getBrightness(float tint) { return 0xF000F0; }

	@Override
	public void tick() {
		this.prevPosX = this.x;
		this.prevPosY = this.y;
		this.prevPosZ = this.z;
		if (this.age++ >= this.maxAge) {
			this.markDead();
			return;
		}
		this.setSpriteForAge(this.spriteProvider);
	}

	@Override
	public ParticleTextureSheet getType() { return ParticleTextureSheet.PARTICLE_SHEET_LIT; }

	@Environment(value=EnvType.CLIENT)
	public static class Factory implements ParticleFactory<DefaultParticleType> {
		private final SpriteProvider spriteProvider;

		public Factory(SpriteProvider spriteProvider) { this.spriteProvider = spriteProvider; }

		@Override
		public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
			return new ModExplosionLargeParticle(clientWorld, d, e, f, g, this.spriteProvider);
		}
	}
}
