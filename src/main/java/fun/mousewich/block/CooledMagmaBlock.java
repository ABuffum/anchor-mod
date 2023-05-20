package fun.mousewich.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class CooledMagmaBlock extends Block {
	public CooledMagmaBlock(BlockConvertible block) { this(block.asBlock()); }
	public CooledMagmaBlock(Block block) { this(Settings.copy(block)); }
	public CooledMagmaBlock(Settings settings) { super(settings); }

	@Override
	public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
		if (!entity.isFireImmune() && entity instanceof LivingEntity && !EnchantmentHelper.hasFrostWalker((LivingEntity)entity)) {
			entity.damage(DamageSource.HOT_FLOOR, 1.0f);
		}
		super.onSteppedOn(world, pos, state, entity);
	}

	@Override
	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		BlockPos blockPos = pos.up();
		if (world.getFluidState(pos).isIn(FluidTags.WATER)) {
			world.playSound(null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5f, 2.6f + (world.random.nextFloat() - world.random.nextFloat()) * 0.8f);
			world.spawnParticles(ParticleTypes.SMOKE, (double)blockPos.getX() + 0.5, (double)blockPos.getY() + 0.25, (double)blockPos.getZ() + 0.5, 8, 0.5, 0.25, 0.5, 0.0);
		}
	}
}