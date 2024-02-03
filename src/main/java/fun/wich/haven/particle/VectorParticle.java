package fun.wich.haven.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.MathHelper;

import java.util.Random;

@Environment(EnvType.CLIENT)
public class VectorParticle extends SpriteBillboardParticle {
	static final Random RANDOM = new Random();
	private final SpriteProvider spriteProvider;

	public VectorParticle(ClientWorld clientWorld, double d, double e, double f, double g, double h, double i, SpriteProvider spriteProvider) {
		super(clientWorld, d, e, f, g, h, i);
		this.field_28786 = 0.96F;
		this.field_28787 = true;
		this.spriteProvider = spriteProvider;
		this.scale *= 0.75F;
		this.collidesWithWorld = false;
		this.setSpriteForAge(spriteProvider);
	}

	public ParticleTextureSheet getType() {
		return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
	}

	public int getBrightness(float tint) {
		float f = ((float) this.age + tint) / (float) this.maxAge;
		f = MathHelper.clamp(f, 0.0F, 1.0F);
		int i = super.getBrightness(tint);
		int j = i & 255;
		int k = i >> 16 & 255;
		j += (int) (f * 15.0F * 16.0F);
		if (j > 240) j = 240;
		return j | k << 16;
	}

	public void tick() {
		super.tick();
		this.setSpriteForAge(this.spriteProvider);
	}

	@Environment(EnvType.CLIENT)
	public static class ScrapeFactory implements ParticleFactory<DefaultParticleType> {
		private final double field_29573 = 0.01D;
		private final SpriteProvider spriteProvider;

		public ScrapeFactory(SpriteProvider spriteProvider) {
			this.spriteProvider = spriteProvider;
		}

		public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
			VectorParticle vectorParticle = new VectorParticle(clientWorld, d, e, f, 0.0D, 0.0D, 0.0D, this.spriteProvider);
			if (clientWorld.random.nextBoolean()) vectorParticle.setColor(0.29F, 0.58F, 0.51F);
			else vectorParticle.setColor(0.43F, 0.77F, 0.62F);
			vectorParticle.setVelocity(g * 0.01D, h * 0.01D, i * 0.01D);
			vectorParticle.setMaxAge(clientWorld.random.nextInt(30) + 10);
			return vectorParticle;
		}
	}

	@Environment(EnvType.CLIENT)
	public static class ElectricSparkFactory implements ParticleFactory<DefaultParticleType> {
		private final double field_29570 = 0.25D;
		private final SpriteProvider spriteProvider;

		public ElectricSparkFactory(SpriteProvider spriteProvider) {
			this.spriteProvider = spriteProvider;
		}

		public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
			VectorParticle vectorParticle = new VectorParticle(clientWorld, d, e, f, 0.0D, 0.0D, 0.0D, this.spriteProvider);
			vectorParticle.setColor(1.0F, 0.9F, 1.0F);
			vectorParticle.setVelocity(g * 0.25D, h * 0.25D, i * 0.25D);
			vectorParticle.setMaxAge(clientWorld.random.nextInt(2) + 2);
			return vectorParticle;
		}
	}

	@Environment(EnvType.CLIENT)
	public static class WaxOffFactory implements ParticleFactory<DefaultParticleType> {
		private final double field_29575 = 0.01D;
		private final SpriteProvider spriteProvider;

		public WaxOffFactory(SpriteProvider spriteProvider) {
			this.spriteProvider = spriteProvider;
		}

		public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
			VectorParticle vectorParticle = new VectorParticle(clientWorld, d, e, f, 0.0D, 0.0D, 0.0D, this.spriteProvider);
			vectorParticle.setColor(1.0F, 0.9F, 1.0F);
			vectorParticle.setVelocity(g * 0.01D / 2.0D, h * 0.01D, i * 0.01D / 2.0D);
			vectorParticle.setMaxAge(clientWorld.random.nextInt(30) + 10);
			return vectorParticle;
		}
	}

	@Environment(EnvType.CLIENT)
	public static class WaxOnFactory implements ParticleFactory<DefaultParticleType> {
		private final double field_29577 = 0.01D;
		private final SpriteProvider spriteProvider;

		public WaxOnFactory(SpriteProvider spriteProvider) {
			this.spriteProvider = spriteProvider;
		}

		public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
			VectorParticle vectorParticle = new VectorParticle(clientWorld, d, e, f, 0.0D, 0.0D, 0.0D, this.spriteProvider);
			vectorParticle.setColor(0.91F, 0.55F, 0.08F);
			vectorParticle.setVelocity(g * 0.01D / 2.0D, h * 0.01D, i * 0.01D / 2.0D);
			vectorParticle.setMaxAge(clientWorld.random.nextInt(30) + 10);
			return vectorParticle;
		}
	}

	@Environment(EnvType.CLIENT)
	public static class ArrowFactory implements ParticleFactory<DefaultParticleType> {
		private final SpriteProvider spriteProvider;

		public ArrowFactory(SpriteProvider spriteProvider) {
			this.spriteProvider = spriteProvider;
		}

		public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
			VectorParticle vectorParticle = new VectorParticle(clientWorld, d, e, f, 0.5D - VectorParticle.RANDOM.nextDouble(), h, 0.5D - VectorParticle.RANDOM.nextDouble(), this.spriteProvider);
			int color = clientWorld.random.nextInt(7);
			if (color == 0) vectorParticle.setColor(0.635F, 0.329F, 0.878F);
			else if (color == 1) vectorParticle.setColor(0.588F, 0.212F, 0.788F);
			else if (color == 2) vectorParticle.setColor(0.557F, 0.204F, 0.745F);
			else if (color == 3) vectorParticle.setColor(0.537F, 0.196F, 0.722F);
			else if (color == 4) vectorParticle.setColor(0.392F, 0.122F, 0.612F);
			else if (color == 5) vectorParticle.setColor(0.373F, 0.118F, 0.576F);
			else vectorParticle.setColor(0.353F, 0.114F, 0.553F);
			vectorParticle.velocityY *= 0.4D;
			if (g == 0.0D && i == 0.0D) {
				vectorParticle.velocityX *= 0.01D;
				vectorParticle.velocityZ *= 0.01D;
			}
			vectorParticle.setMaxAge((int) (8.0D / clientWorld.random.nextDouble() * 0.8D + 0.2D));
			return vectorParticle;
		}
	}
}