package fun.wich.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

@Environment(value= EnvType.CLIENT)
public class CherryLeavesParticle extends SpriteBillboardParticle {
	private float field_43369;
	private final float field_43370;
	private final float field_43371;

	public CherryLeavesParticle(ClientWorld clientWorld, double d, double e, double f, SpriteProvider spriteProvider) {
		super(clientWorld, d, e, f);
		float g;
		this.setSprite(spriteProvider.getSprite(this.random.nextInt(12), 12));
		this.field_43369 = (float)Math.toRadians(this.random.nextBoolean() ? -30.0 : 30.0);
		this.field_43370 = this.random.nextFloat();
		this.field_43371 = (float)Math.toRadians(this.random.nextBoolean() ? -5.0 : 5.0);
		this.maxAge = 300;
		this.gravityStrength = 7.5E-4f;
		this.scale = g = this.random.nextBoolean() ? 0.05f : 0.075f;
		this.setBoundingBoxSpacing(g, g);
		this.field_28786 = 1.0f;
	}

	public static SpriteBillboardParticle createCherryLeaves(DefaultParticleType ignoredType, ClientWorld world, double x, double y, double z, SpriteProvider spriteProvider) {
		return new CherryLeavesParticle(world, x, y, z, spriteProvider);
	}

	@Environment(value=EnvType.CLIENT)
	public static class Factory implements ParticleFactory<DefaultParticleType> {
		private final SpriteProvider spriteProvider;
		public Factory(SpriteProvider spriteProvider) { this.spriteProvider = spriteProvider; }
		@Override
		public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
			CherryLeavesParticle particle = new CherryLeavesParticle(clientWorld, d, e, f, this.spriteProvider);
			return particle;
		}
	}

	@Override
	public ParticleTextureSheet getType() { return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE; }

	@Override
	public void tick() {
		this.prevPosX = this.x;
		this.prevPosY = this.y;
		this.prevPosZ = this.z;
		if (this.maxAge-- <= 0) this.markDead();
		if (this.dead) return;
		float f = 300 - this.maxAge;
		float g = Math.min(f / 300.0f, 1.0f);
		double d = Math.cos(Math.toRadians(this.field_43370 * 60.0f)) * 2.0 * Math.pow(g, 1.25);
		double e = Math.sin(Math.toRadians(this.field_43370 * 60.0f)) * 2.0 * Math.pow(g, 1.25);
		this.velocityX += d * (double)0.0025f;
		this.velocityZ += e * (double)0.0025f;
		this.velocityY -= this.gravityStrength;
		this.field_43369 += this.field_43371 / 20.0f;
		this.prevAngle = this.angle;
		this.angle += this.field_43369 / 20.0f;
		this.move(this.velocityX, this.velocityY, this.velocityZ);
		if (this.onGround || this.maxAge < 299 && (this.velocityX == 0.0 || this.velocityZ == 0.0)) this.markDead();
		if (this.dead) return;
		this.velocityX *= this.field_28786;
		this.velocityY *= this.field_28786;
		this.velocityZ *= this.field_28786;
	}
}
