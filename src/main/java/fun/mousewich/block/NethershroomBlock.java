package fun.mousewich.block;

import fun.mousewich.ModBase;
import fun.mousewich.origins.power.PowersUtil;
import fun.mousewich.origins.power.SoftStepsPower;
import fun.mousewich.sound.ModSoundEvents;
import net.minecraft.block.*;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Random;

public class NethershroomBlock extends PlantBlock implements Fertilizable {
	public static final int MAX_COUNT = 4;
	public static final IntProperty COUNT = ModProperties.COUNT_4;
	protected static final VoxelShape ONE_SHAPE = Block.createCuboidShape(6, 0, 6, 10, 6, 10);
	protected static final VoxelShape TWO_SHAPE = Block.createCuboidShape(3, 0, 3, 13, 6, 13);
	protected static final VoxelShape THREE_SHAPE = Block.createCuboidShape(3, 0, 3, 13, 6, 13);
	protected static final VoxelShape FOUR_SHAPE = Block.createCuboidShape(2, 0, 2, 14, 6, 14);

	public NethershroomBlock(Settings settings) {
		super(settings);
		this.setDefaultState(this.stateManager.getDefaultState().with(COUNT, 1));
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		BlockState blockState = ctx.getWorld().getBlockState(ctx.getBlockPos());
		if (blockState.isOf(this)) {
			return blockState.with(COUNT, Math.min(MAX_COUNT, blockState.get(COUNT) + 1));
		}
		return super.getPlacementState(ctx);
	}

	@Override
	protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
		return floor.isIn(BlockTags.NYLIUM) || floor.isOf(Blocks.MYCELIUM) || floor.isOf(Blocks.SOUL_SOIL) || super.canPlantOnTop(floor, world, pos);
	}

	@Override
	public boolean canReplace(BlockState state, ItemPlacementContext context) {
		if (!context.shouldCancelInteraction() && context.getStack().isOf(this.asItem()) && state.get(COUNT) < MAX_COUNT) return true;
		return super.canReplace(state, context);
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		Integer count = state.get(COUNT);
		if (count == 1) return ONE_SHAPE;
		else if (count == 2) return TWO_SHAPE;
		else if (count == 3) return THREE_SHAPE;
		else return FOUR_SHAPE;
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) { builder.add(COUNT); }

	@Override
	public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) { return true; }

	@Override
	public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) { return state.get(COUNT) < MAX_COUNT; }

	@Override
	public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
		int count = state.get(COUNT);
		if (count < MAX_COUNT) world.setBlockState(pos, state.with(COUNT, count + 1));
		else dropStack(world, pos, new ItemStack(ModBase.DEATH_CAP_MUSHROOM.asItem()));
	}
	@Override
	public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
		if (shouldExplode(entity)) {
			CreatePoisonCloud(world, pos, state.get(COUNT));
			world.playSound(null, pos, ModSoundEvents.BLOCK_NETHERSHROOM_EXPLODE, SoundCategory.BLOCKS, 0.6F, 1F);
			world.setBlockState(pos, Blocks.AIR.getDefaultState(), NOTIFY_ALL);
		}
		else super.onEntityCollision(state, world, pos, entity);
	}
	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return VoxelShapes.empty();
	}

	@SuppressWarnings("RedundantIfStatement")
	public boolean shouldExplode(Entity entity) {
		if (!(entity instanceof LivingEntity living)) {
			if (entity instanceof PersistentProjectileEntity) return true;
			else return false;
		}
		else if (living.isSneaking()) return false;
		else if (living.isBaby()) return false;
		else if (entity instanceof PlayerEntity player) {
			if (player.getAbilities().creativeMode) return false;
		}
		else if (PowersUtil.Active(living, SoftStepsPower.class)) return living.getRandom().nextFloat() > 0.25;
		return true;
	}

	public static void CreatePoisonCloud(World world, BlockPos pos, int count) {
		if (!world.isClient) {
			AreaEffectCloudEntity areaEffectCloudEntity = new AreaEffectCloudEntity(world, pos.getX(), pos.getY(), pos.getZ());
			areaEffectCloudEntity.setRadius(1.25f * count);
			areaEffectCloudEntity.setRadiusOnUse(-0.5f);
			areaEffectCloudEntity.setWaitTime(10);
			areaEffectCloudEntity.setDuration(areaEffectCloudEntity.getDuration() / 2);
			areaEffectCloudEntity.setRadiusGrowth(-areaEffectCloudEntity.getRadius() / (float) areaEffectCloudEntity.getDuration());
			areaEffectCloudEntity.addEffect(new StatusEffectInstance(StatusEffects.POISON, 10, Math.max(0, count - 2)));
			world.spawnEntity(areaEffectCloudEntity);
		}
	}

	@Override
	public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) { return false; }
}
