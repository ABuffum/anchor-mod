package fun.wich.block;

import fun.wich.entity.ModEntityType;
import fun.wich.entity.passive.frog.TadpoleEntity;
import fun.wich.sound.ModSoundEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

import java.util.Random;

public class FrogspawnBlock extends Block {
	private static final int MIN_TADPOLES = 2;
	private static final int MAX_TADPOLES = 5;
	private static final int MIN_HATCH_TIME = 3600;
	private static final int MAX_HATCH_TIME = 12000;
	protected static final VoxelShape SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 1.5, 16.0);
	private static int minHatchTime = 3600;
	private static int maxHatchTime = 12000;
	public FrogspawnBlock(Settings settings) { super(settings); }
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return SHAPE;
	}
	@Override
	public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
		return FrogspawnBlock.canLayAt(world, pos.down());
	}
	@Override
	public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
		world.getBlockTickScheduler().schedule(pos, this, FrogspawnBlock.getHatchTime(world.getRandom()));
	}
	private static int getHatchTime(Random random) {
		return random.nextInt(maxHatchTime - minHatchTime) + minHatchTime;
	}
	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		if (!this.canPlaceAt(state, world, pos)) {
			return Blocks.AIR.getDefaultState();
		}
		return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
	}
	@Override
	public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		if (!this.canPlaceAt(state, world, pos)) {
			this.breakWithoutDrop(world, pos);
			return;
		}
		this.hatch(world, pos, random);
	}
	@Override
	public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
		if (entity.getType().equals(EntityType.FALLING_BLOCK)) {
			this.breakWithoutDrop(world, pos);
		}
	}
	private static boolean canLayAt(BlockView world, BlockPos pos) {
		FluidState fluidState = world.getFluidState(pos);
		FluidState fluidState2 = world.getFluidState(pos.up());
		return fluidState.getFluid() == Fluids.WATER && fluidState2.getFluid() == Fluids.EMPTY;
	}
	private void hatch(ServerWorld world, BlockPos pos, Random random) {
		this.breakWithoutDrop(world, pos);
		world.playSound(null, pos, ModSoundEvents.BLOCK_FROGSPAWN_HATCH, SoundCategory.BLOCKS, 1.0f, 1.0f);
		this.spawnTadpoles(world, pos, random);
	}
	private void breakWithoutDrop(World world, BlockPos pos) { world.breakBlock(pos, false); }
	private void spawnTadpoles(ServerWorld world, BlockPos pos, Random random) {
		int i = random.nextInt(MAX_TADPOLES - MIN_TADPOLES) + MIN_TADPOLES;
		for (int j = 1; j <= i; ++j) {
			TadpoleEntity tadpoleEntity = ModEntityType.TADPOLE_ENTITY.create(world);
			double d = pos.getX() + this.getSpawnOffset(random);
			double e = pos.getZ() + this.getSpawnOffset(random);
			int k = random.nextInt(361);
			tadpoleEntity.refreshPositionAndAngles(d, (double)pos.getY() - 0.5, e, k, 0.0f);
			tadpoleEntity.setPersistent();
			world.spawnEntity(tadpoleEntity);
		}
	}
	private double getSpawnOffset(Random random) {
		double d = TadpoleEntity.WIDTH / 2.0f;
		return MathHelper.clamp(random.nextDouble(), d, 1.0 - d);
	}
	public static void setHatchTimeRange(int min, int max) {
		minHatchTime = min;
		maxHatchTime = max;
	}
	public static void resetHatchTimeRange() {
		minHatchTime = MIN_HATCH_TIME;
		maxHatchTime = MAX_HATCH_TIME;
	}
}
