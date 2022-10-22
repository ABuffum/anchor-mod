package haven.mixins.client.rendering;

import haven.HavenMod;
import haven.blocks.MultifaceGrowthBlock;
import haven.blocks.sculk.SculkShriekerBlock;
import haven.events.HavenWorldEvents;
import haven.particles.SculkChargeParticleEffect;
import haven.particles.ShriekParticleEffect;
import haven.sounds.HavenSoundEvents;
import haven.util.ParticleUtils;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.resource.SynchronousResourceReloader;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;
import java.util.function.Supplier;

@Mixin(WorldRenderer.class)
public abstract class WorldRendererMixin implements SynchronousResourceReloader {
	@Shadow
	private ClientWorld world;

	@Inject(method="processWorldEvent", at = @At("HEAD"))
	public void ProcessWorldEvent(PlayerEntity source, int eventId, BlockPos pos, int data, CallbackInfo ci) {
		Random random = this.world.random;
		switch (eventId) {
			case HavenWorldEvents.SCULK_CHARGE: {
				int i = data >> 6;
				if (i > 0) {
					if (random.nextFloat() < 0.3f + (float)i * 0.1f) {
						float v = 0.15f + 0.02f * (float)i * (float)i * random.nextFloat();
						float w = 0.4f + 0.3f * (float)i * random.nextFloat();
						this.world.playSound(pos, HavenSoundEvents.BLOCK_SCULK_CHARGE, SoundCategory.BLOCKS, v, w, false);
					}
					byte b = (byte)(data & 0x3F);
					UniformIntProvider intProvider = UniformIntProvider.create(0, i);
					float ac = 0.005f;
					Supplier<Vec3d> supplier = () -> new Vec3d(MathHelper.nextDouble(random, -0.005f, 0.005f), MathHelper.nextDouble(random, -0.005f, 0.005f), MathHelper.nextDouble(random, -0.005f, 0.005f));
					if (b == 0) {
						for (Direction direction2 : Direction.values()) {
							float ad = direction2 == Direction.DOWN ? (float)Math.PI : 0.0f;
							double g = direction2.getAxis() == Direction.Axis.Y ? 0.65 : 0.57;
							ParticleUtils.spawnParticles(this.world, pos, new SculkChargeParticleEffect(ad), intProvider, direction2, supplier, g);
						}
					} else {
						for (Direction direction3 : MultifaceGrowthBlock.flagToDirections(b)) {
							float ae = direction3 == Direction.UP ? (float)Math.PI : 0.0f;
							double af = 0.35;
							ParticleUtils.spawnParticles(this.world, pos, new SculkChargeParticleEffect(ae), intProvider, direction3, supplier, 0.35);
						}
					}
				} else {
					this.world.playSound(pos, HavenSoundEvents.BLOCK_SCULK_CHARGE, SoundCategory.BLOCKS, 1.0f, 1.0f, false);
					boolean bl = this.world.getBlockState(pos).isFullCube(this.world, pos);
					int k = bl ? 40 : 20;
					float ac = bl ? 0.45f : 0.25f;
					float ag = 0.07f;
					for (int t = 0; t < k; ++t) {
						float ah = 2.0f * random.nextFloat() - 1.0f;
						float ae = 2.0f * random.nextFloat() - 1.0f;
						float ai = 2.0f * random.nextFloat() - 1.0f;
						this.world.addParticle(HavenMod.SCULK_CHARGE_POP_PARTICLE, (double)pos.getX() + 0.5 + (double)(ah * ac), (double)pos.getY() + 0.5 + (double)(ae * ac), (double)pos.getZ() + 0.5 + (double)(ai * ac), ah * 0.07f, ae * 0.07f, ai * 0.07f);
					}
				}
				break;
			}
			case HavenWorldEvents.SCULK_SHRIEKS: {
				for (int j = 0; j < 10; ++j) {
					this.world.addParticle(new ShriekParticleEffect(j * 5), false, (double)pos.getX() + 0.5, (double)pos.getY() + SculkShriekerBlock.TOP, (double)pos.getZ() + 0.5, 0.0, 0.0, 0.0);
				}
				this.world.playSound((double)pos.getX() + 0.5, (double)pos.getY() + SculkShriekerBlock.TOP, (double)pos.getZ() + 0.5, HavenSoundEvents.BLOCK_SCULK_SHRIEKER_SHRIEK, SoundCategory.BLOCKS, 2.0f, 0.6f + this.world.random.nextFloat() * 0.4f, false);
				break;
			}
		}
	}
}
