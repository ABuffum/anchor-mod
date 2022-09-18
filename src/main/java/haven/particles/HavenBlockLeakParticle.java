package haven.particles;

import java.util.Random;

import haven.HavenMod;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class HavenBlockLeakParticle extends SpriteBillboardParticle {
	private final Fluid fluid;
	protected boolean obsidianTear;

	public HavenBlockLeakParticle(ClientWorld clientWorld, double d, double e, double f, Fluid fluid) {
		super(clientWorld, d, e, f);
		this.setBoundingBoxSpacing(0.01F, 0.01F);
		this.gravityStrength = 0.06F;
		this.fluid = fluid;
	}

	protected Fluid getFluid() {
		return this.fluid;
	}

	public ParticleTextureSheet getType() {
		return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
	}

	public int getBrightness(float tint) {
		return this.obsidianTear ? 240 : super.getBrightness(tint);
	}

	public void tick() {
		this.prevPosX = this.x;
		this.prevPosY = this.y;
		this.prevPosZ = this.z;
		this.updateAge();
		if (!this.dead) {
			this.velocityY -= (double)this.gravityStrength;
			this.move(this.velocityX, this.velocityY, this.velocityZ);
			this.updateVelocity();
			if (!this.dead) {
				this.velocityX *= 0.9800000190734863D;
				this.velocityY *= 0.9800000190734863D;
				this.velocityZ *= 0.9800000190734863D;
				BlockPos blockPos = new BlockPos(this.x, this.y, this.z);
				FluidState fluidState = this.world.getFluidState(blockPos);
				if (fluidState.getFluid() == this.fluid && this.y < (double)((float)blockPos.getY() + fluidState.getHeight(this.world, blockPos))) {
					this.markDead();
				}

			}
		}
	}

	protected void updateAge() {
		if (this.maxAge-- <= 0) {
			this.markDead();
		}

	}

	protected void updateVelocity() {
	}

	@Environment(EnvType.CLIENT)
	public static class LandingObsidianBloodFactory implements ParticleFactory<DefaultParticleType> {
		protected final SpriteProvider spriteProvider;

		public LandingObsidianBloodFactory(SpriteProvider spriteProvider) {
			this.spriteProvider = spriteProvider;
		}

		public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
			HavenBlockLeakParticle blockLeakParticle = new HavenBlockLeakParticle.Landing(clientWorld, d, e, f, Fluids.EMPTY);
			blockLeakParticle.obsidianTear = true;
			blockLeakParticle.maxAge = (int)(28.0D / (Math.random() * 0.8D + 0.2D));
			blockLeakParticle.setColor(1F, 0F, 0F);
			blockLeakParticle.setSprite(this.spriteProvider);
			return blockLeakParticle;
		}
	}

	@Environment(EnvType.CLIENT)
	public static class FallingObsidianBloodFactory implements ParticleFactory<DefaultParticleType> {
		protected final SpriteProvider spriteProvider;

		public FallingObsidianBloodFactory(SpriteProvider spriteProvider) {
			this.spriteProvider = spriteProvider;
		}

		public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
			HavenBlockLeakParticle blockLeakParticle = new HavenBlockLeakParticle.ContinuousFalling(clientWorld, d, e, f, Fluids.EMPTY, HavenMod.LANDING_OBSIDIAN_BLOOD);
			blockLeakParticle.obsidianTear = true;
			blockLeakParticle.gravityStrength = 0.01F;
			blockLeakParticle.setColor(1F, 0F, 0F);
			blockLeakParticle.setSprite(this.spriteProvider);
			return blockLeakParticle;
		}
	}

	@Environment(EnvType.CLIENT)
	public static class DrippingObsidianBloodFactory implements ParticleFactory<DefaultParticleType> {
		protected final SpriteProvider spriteProvider;

		public DrippingObsidianBloodFactory(SpriteProvider spriteProvider) {
			this.spriteProvider = spriteProvider;
		}

		public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
			HavenBlockLeakParticle.Dripping dripping = new HavenBlockLeakParticle.Dripping(clientWorld, d, e, f, Fluids.EMPTY, HavenMod.FALLING_OBSIDIAN_BLOOD);
			dripping.obsidianTear = true;
			dripping.gravityStrength *= 0.01F;
			dripping.maxAge = 100;
			dripping.setColor(1F, 0F, 0F);
			dripping.setSprite(this.spriteProvider);
			return dripping;
		}
	}

	@Environment(EnvType.CLIENT)
	public static class FallingDripstoneMudFactory implements ParticleFactory<DefaultParticleType> {
		protected final SpriteProvider spriteProvider;

		public FallingDripstoneMudFactory(SpriteProvider spriteProvider) {
			this.spriteProvider = spriteProvider;
		}

		public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
			HavenBlockLeakParticle blockLeakParticle = new HavenBlockLeakParticle.Dripping(clientWorld, d, e, f, HavenMod.STILL_MUD_FLUID, HavenMod.FALLING_DRIPSTONE_MUD);
			blockLeakParticle.setColor(0.25F, 0.125F, 0F);
			blockLeakParticle.setSprite(this.spriteProvider);
			return blockLeakParticle;
		}
	}

	@Environment(EnvType.CLIENT)
	public static class FallingMudFactory implements ParticleFactory<DefaultParticleType> {
		protected final SpriteProvider spriteProvider;

		public FallingMudFactory(SpriteProvider spriteProvider) {
			this.spriteProvider = spriteProvider;
		}

		public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
			HavenBlockLeakParticle blockLeakParticle = new HavenBlockLeakParticle.ContinuousFalling(clientWorld, d, e, f, HavenMod.STILL_MUD_FLUID, HavenMod.MUD_SPLASH);
			blockLeakParticle.setColor(0.25F, 0.125F, 0F);
			blockLeakParticle.setSprite(this.spriteProvider);
			return blockLeakParticle;
		}
	}
	@Environment(EnvType.CLIENT)
	public static class DrippingMudFactory implements ParticleFactory<DefaultParticleType> {
		protected final SpriteProvider spriteProvider;

		public DrippingMudFactory(SpriteProvider spriteProvider) {
			this.spriteProvider = spriteProvider;
		}

		public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
			HavenBlockLeakParticle blockLeakParticle = new HavenBlockLeakParticle.Dripping(clientWorld, d, e, f, HavenMod.STILL_MUD_FLUID, HavenMod.FALLING_MUD);
			blockLeakParticle.setColor(0.25F, 0.125F, 0F);
			blockLeakParticle.setSprite(this.spriteProvider);
			return blockLeakParticle;
		}
	}

	@Environment(EnvType.CLIENT)
	public static class FallingDripstoneBloodFactory implements ParticleFactory<DefaultParticleType> {
		protected final SpriteProvider spriteProvider;

		public FallingDripstoneBloodFactory(SpriteProvider spriteProvider) {
			this.spriteProvider = spriteProvider;
		}

		public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
			HavenBlockLeakParticle blockLeakParticle = new HavenBlockLeakParticle.Dripping(clientWorld, d, e, f, HavenMod.STILL_BLOOD_FLUID, HavenMod.FALLING_DRIPSTONE_BLOOD);
			blockLeakParticle.setColor(1F, 0F, 0F);
			blockLeakParticle.setSprite(this.spriteProvider);
			return blockLeakParticle;
		}
	}

	@Environment(EnvType.CLIENT)
	public static class FallingBloodFactory implements ParticleFactory<DefaultParticleType> {
		protected final SpriteProvider spriteProvider;

		public FallingBloodFactory(SpriteProvider spriteProvider) {
			this.spriteProvider = spriteProvider;
		}

		public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
			HavenBlockLeakParticle blockLeakParticle = new HavenBlockLeakParticle.ContinuousFalling(clientWorld, d, e, f, HavenMod.STILL_BLOOD_FLUID, HavenMod.BLOOD_SPLASH);
			blockLeakParticle.setColor(1F, 0F, 0F);
			blockLeakParticle.setSprite(this.spriteProvider);
			return blockLeakParticle;
		}
	}
	@Environment(EnvType.CLIENT)
	public static class DrippingBloodFactory implements ParticleFactory<DefaultParticleType> {
		protected final SpriteProvider spriteProvider;

		public DrippingBloodFactory(SpriteProvider spriteProvider) {
			this.spriteProvider = spriteProvider;
		}

		public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
			HavenBlockLeakParticle blockLeakParticle = new HavenBlockLeakParticle.Dripping(clientWorld, d, e, f, HavenMod.STILL_BLOOD_FLUID, HavenMod.FALLING_BLOOD);
			blockLeakParticle.setColor(1F, 0F, 0F);
			blockLeakParticle.setSprite(this.spriteProvider);
			return blockLeakParticle;
		}
	}

	@Environment(EnvType.CLIENT)
	private static class Landing extends HavenBlockLeakParticle {
		Landing(ClientWorld clientWorld, double d, double e, double f, Fluid fluid) {
			super(clientWorld, d, e, f, fluid);
			this.maxAge = (int)(16.0D / (Math.random() * 0.8D + 0.2D));
		}
	}

	@Environment(EnvType.CLIENT)
	static class Falling extends HavenBlockLeakParticle {
		Falling(ClientWorld clientWorld, double d, double e, double f, Fluid fluid) {
			this(clientWorld, d, e, f, fluid, (int)(64.0D / (Math.random() * 0.8D + 0.2D)));
		}

		Falling(ClientWorld clientWorld, double d, double e, double f, Fluid fluid, int i) {
			super(clientWorld, d, e, f, fluid);
			this.maxAge = i;
		}

		protected void updateVelocity() {
			if (this.onGround) {
				this.markDead();
			}

		}
	}

	@Environment(EnvType.CLIENT)
	private static class DripstoneLavaDrip extends HavenBlockLeakParticle.ContinuousFalling {
		DripstoneLavaDrip(ClientWorld clientWorld, double d, double e, double f, Fluid fluid, ParticleEffect particleEffect) {
			super(clientWorld, d, e, f, fluid, particleEffect);
		}

		protected void updateVelocity() {
			if (this.onGround) {
				this.markDead();
				this.world.addParticle(this.nextParticle, this.x, this.y, this.z, 0.0D, 0.0D, 0.0D);
				SoundEvent soundEvent = this.getFluid() == Fluids.LAVA ? SoundEvents.BLOCK_POINTED_DRIPSTONE_DRIP_LAVA : SoundEvents.BLOCK_POINTED_DRIPSTONE_DRIP_WATER;
				float f = MathHelper.nextBetween(this.random, 0.3F, 1.0F);
				this.world.playSound(this.x, this.y, this.z, soundEvent, SoundCategory.BLOCKS, f, 1.0F, false);
			}

		}
	}

	@Environment(EnvType.CLIENT)
	static class FallingHoney extends HavenBlockLeakParticle.ContinuousFalling {
		FallingHoney(ClientWorld clientWorld, double d, double e, double f, Fluid fluid, ParticleEffect particleEffect) {
			super(clientWorld, d, e, f, fluid, particleEffect);
		}

		protected void updateVelocity() {
			if (this.onGround) {
				this.markDead();
				this.world.addParticle(this.nextParticle, this.x, this.y, this.z, 0.0D, 0.0D, 0.0D);
				float f = MathHelper.nextBetween(this.random, 0.3F, 1.0F);
				this.world.playSound(this.x, this.y, this.z, SoundEvents.BLOCK_BEEHIVE_DRIP, SoundCategory.BLOCKS, f, 1.0F, false);
			}

		}
	}

	@Environment(EnvType.CLIENT)
	private static class ContinuousFalling extends HavenBlockLeakParticle.Falling {
		protected final ParticleEffect nextParticle;

		ContinuousFalling(ClientWorld clientWorld, double d, double e, double f, Fluid fluid, ParticleEffect particleEffect) {
			super(clientWorld, d, e, f, fluid);
			this.nextParticle = particleEffect;
		}

		protected void updateVelocity() {
			if (this.onGround) {
				this.markDead();
				this.world.addParticle(this.nextParticle, this.x, this.y, this.z, 0.0D, 0.0D, 0.0D);
			}

		}
	}

	@Environment(EnvType.CLIENT)
	private static class DrippingLava extends HavenBlockLeakParticle.Dripping {
		DrippingLava(ClientWorld clientWorld, double d, double e, double f, Fluid fluid, ParticleEffect particleEffect) {
			super(clientWorld, d, e, f, fluid, particleEffect);
		}

		protected void updateAge() {
			this.colorRed = 1.0F;
			this.colorGreen = 16.0F / (float)(40 - this.maxAge + 16);
			this.colorBlue = 4.0F / (float)(40 - this.maxAge + 8);
			super.updateAge();
		}
	}

	@Environment(EnvType.CLIENT)
	private static class Dripping extends HavenBlockLeakParticle {
		private final ParticleEffect nextParticle;

		Dripping(ClientWorld clientWorld, double d, double e, double f, Fluid fluid, ParticleEffect particleEffect) {
			super(clientWorld, d, e, f, fluid);
			this.nextParticle = particleEffect;
			this.gravityStrength *= 0.02F;
			this.maxAge = 40;
		}

		protected void updateAge() {
			if (this.maxAge-- <= 0) {
				this.markDead();
				this.world.addParticle(this.nextParticle, this.x, this.y, this.z, this.velocityX, this.velocityY, this.velocityZ);
			}

		}

		protected void updateVelocity() {
			this.velocityX *= 0.02D;
			this.velocityY *= 0.02D;
			this.velocityZ *= 0.02D;
		}
	}
}
