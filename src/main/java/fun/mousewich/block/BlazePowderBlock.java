package fun.mousewich.block;

import fun.mousewich.block.basic.ModFallingBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.BubbleColumnBlock;
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
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import java.util.Random;

public class BlazePowderBlock extends ModFallingBlock {
	protected static final int COLOR = 0xEF9B09;
	public BlazePowderBlock(BlockConvertible block) { super(block, COLOR); }
	public BlazePowderBlock(Block block) { super(block, COLOR); }
	public BlazePowderBlock(Settings settings) { super(settings, COLOR); }

	@Override
	public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
		if (!entity.isFireImmune() && entity instanceof LivingEntity && !EnchantmentHelper.hasFrostWalker((LivingEntity)entity)) {
			entity.damage(DamageSource.HOT_FLOOR, 1.0f);
		}
		super.onSteppedOn(world, pos, state, entity);
	}

	@Override
	public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		BubbleColumnBlock.update(world, pos.up(), state);
		super.scheduledTick(state, world, pos, random);
	}
	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		if (direction == Direction.UP && neighborState.isOf(Blocks.WATER)) world.getBlockTickScheduler().schedule(pos, this, 20);
		return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
	}
	@Override
	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		BlockPos blockPos = pos.up();
		if (world.getFluidState(pos).isIn(FluidTags.WATER)) {
			world.playSound(null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5f, 2.6f + (world.random.nextFloat() - world.random.nextFloat()) * 0.8f);
			world.spawnParticles(ParticleTypes.LARGE_SMOKE, blockPos.getX() + 0.5, blockPos.getY() + 0.25, blockPos.getZ() + 0.5, 8, 0.5, 0.25, 0.5, 0.0);
		}
	}
}
