package fun.mousewich.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

@Environment(value= EnvType.CLIENT)
public class ModSuspendParticle extends SpriteBillboardParticle {
	public ModSuspendParticle(ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
		super(clientWorld, d, e, f, g, h, i);
		float j;
		this.colorRed = j = this.random.nextFloat() * 0.1f + 0.2f;
		this.colorGreen = j;
		this.colorBlue = j;
		this.setBoundingBoxSpacing(0.02f, 0.02f);
		this.scale *= this.random.nextFloat() * 0.6f + 0.5f;
		this.velocityX *= 0.02f;
		this.velocityY *= 0.02f;
		this.velocityZ *= 0.02f;
		this.maxAge = (int)(20.0 / (Math.random() * 0.8 + 0.2));
	}

	@Override
	public ParticleTextureSheet getType() { return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE; }

	@Override
	public void move(double dx, double dy, double dz) {
		this.setBoundingBox(this.getBoundingBox().offset(dx, dy, dz));
		this.repositionFromBoundingBox();
	}

	@Override
	public void tick() {
		this.prevPosX = this.x;
		this.prevPosY = this.y;
		this.prevPosZ = this.z;
		if (this.maxAge-- <= 0) {
			this.markDead();
			return;
		}
		this.move(this.velocityX, this.velocityY, this.velocityZ);
		this.velocityX *= 0.99;
		this.velocityY *= 0.99;
		this.velocityZ *= 0.99;
	}

	@Environment(value= EnvType.CLIENT)
	public static class EggCrackParticleFactory implements ParticleFactory<DefaultParticleType> {
		private final SpriteProvider spriteProvider;

		public EggCrackParticleFactory(SpriteProvider spriteProvider) { this.spriteProvider = spriteProvider; }

		@Override
		public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
			ModSuspendParticle suspendParticle = new ModSuspendParticle(clientWorld, d, e, f, g, h, i);
			suspendParticle.setSprite(this.spriteProvider);
			suspendParticle.setColor(1.0f, 1.0f, 1.0f);
			return suspendParticle;
		}
	}
}