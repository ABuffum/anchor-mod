package haven.mixins.blocks;

import haven.HavenMod;
import haven.blocks.cake.CakeContainer;
import net.minecraft.block.AbstractCandleBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(AbstractCandleBlock.class)
public abstract class AbstractCandleBlockMixin extends Block {
	public AbstractCandleBlockMixin(Settings settings) { super(settings); }

	@Shadow
	protected abstract Iterable<Vec3d> getParticleOffsets(BlockState state);

	@Inject(method="randomDisplayTick", at = @At("HEAD"), cancellable = true)
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random, CallbackInfo ci) {
		Block block = state.getBlock();
		boolean isSoulCandle = block == HavenMod.SOUL_CANDLE.BLOCK || block == HavenMod.SOUL_CANDLE_CAKE;
		if (!isSoulCandle) {
			for(CakeContainer.Flavor flavor : CakeContainer.Flavor.FLAVORS) {
				if (block == flavor.getSoulCandleCake()) {
					isSoulCandle = true;
					break;
				}
			}
		}
		if (isSoulCandle) {
			if (state.get(AbstractCandleBlock.LIT)) {
				this.getParticleOffsets(state).forEach((offset) -> SpawnCandleParticles(world, offset.add(pos.getX(), pos.getY(), pos.getZ()), random));
			}
			ci.cancel();
		}
	}

	private static void SpawnCandleParticles(World world, Vec3d vec3d, Random random) {
		float f = random.nextFloat();
		if (f < 0.3F) {
			world.addParticle(ParticleTypes.SMOKE, vec3d.x, vec3d.y, vec3d.z, 0.0D, 0.0D, 0.0D);
			if (f < 0.17F) {
				world.playSound(vec3d.x + 0.5D, vec3d.y + 0.5D, vec3d.z + 0.5D, SoundEvents.BLOCK_CANDLE_AMBIENT, SoundCategory.BLOCKS, 1.0F + random.nextFloat(), random.nextFloat() * 0.7F + 0.3F, false);
			}
		}
		world.addParticle(HavenMod.SMALL_SOUL_FLAME_PARTICLE, vec3d.x, vec3d.y, vec3d.z, 0.0D, 0.0D, 0.0D);
	}
}
