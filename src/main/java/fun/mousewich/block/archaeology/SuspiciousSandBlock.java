package fun.mousewich.block.archaeology;

import fun.mousewich.block.ModProperties;
import fun.mousewich.block.dust.Brushable;
import fun.mousewich.block.dust.BrushableBlockEntity;
import fun.mousewich.mixins.block.entity.FallingBlockEntityAccessor;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldEvents;
import net.minecraft.world.event.GameEvent;

import java.util.Random;

public class SuspiciousSandBlock extends BlockWithEntity implements LandingBlock, Brushable {
	private final Block block;
	private final SoundEvent brushSound;
	private final SoundEvent brushCompleteSound;
	public SuspiciousSandBlock(Block block, AbstractBlock.Settings settings, SoundEvent soundEvent, SoundEvent soundEvent2) {
		super(settings);
		this.block = block;
		this.brushSound = soundEvent;
		this.brushCompleteSound = soundEvent2;
		this.setDefaultState(this.stateManager.getDefaultState().with(ModProperties.DUSTED, 0));
	}
	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) { builder.add(ModProperties.DUSTED); }
	@Override
	public BlockRenderType getRenderType(BlockState state) { return BlockRenderType.MODEL; }
	@Override
	public PistonBehavior getPistonBehavior(BlockState state) { return PistonBehavior.DESTROY; }
	@Override
	public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
		world.getBlockTickScheduler().schedule(pos, this, 2);
	}

	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		world.getBlockTickScheduler().schedule(pos, this, 2);
		return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
	}

	@Override
	public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		if (world.getBlockEntity(pos) instanceof BrushableBlockEntity entity) entity.scheduledTick();
		if (!FallingBlock.canFallThrough(world.getBlockState(pos.down())) || pos.getY() < world.getBottomY()) return;
		FallingBlockEntity fallingBlockEntity = spawnFromBlock(world, pos, state);
		((FallingBlockEntityAccessor)fallingBlockEntity).SetDestroyedOnLanding(true);
	}

	public static FallingBlockEntity spawnFromBlock(World world, BlockPos pos, BlockState state) {
		FallingBlockEntity fallingBlockEntity = new FallingBlockEntity(world, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, state.contains(Properties.WATERLOGGED) ? state.with(Properties.WATERLOGGED, false) : state);
		world.setBlockState(pos, state.getFluidState().getBlockState(), Block.NOTIFY_ALL);
		world.spawnEntity(fallingBlockEntity);
		return fallingBlockEntity;
	}

	@Override
	public void onDestroyedOnLanding(World world, BlockPos pos, FallingBlockEntity fallingBlockEntity) {
		Vec3d vec3d = fallingBlockEntity.getBoundingBox().getCenter();
		BlockPos pos2 = new BlockPos(MathHelper.floor(vec3d.x), MathHelper.floor(vec3d.y), MathHelper.floor(vec3d.z));
		world.syncWorldEvent(WorldEvents.BLOCK_BROKEN, pos2, Block.getRawIdFromState(fallingBlockEntity.getBlockState()));
		world.emitGameEvent(fallingBlockEntity, GameEvent.BLOCK_DESTROY, pos2);
	}

	@Override
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
		if (random.nextInt(16) == 0 && FallingBlock.canFallThrough(world.getBlockState(pos.down()))) {
			double d = (double)pos.getX() + random.nextDouble();
			double e = (double)pos.getY() - 0.05;
			double f = (double)pos.getZ() + random.nextDouble();
			world.addParticle(new BlockStateParticleEffect(ParticleTypes.FALLING_DUST, state), d, e, f, 0.0, 0.0, 0.0);
		}
	}

	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) { return new SuspiciousSandBlockEntity(pos, state); }
	@Override
	public BlockState getBrushedState(BlockState state) { return this.block.getDefaultState(); }
	@Override
	public SoundEvent getBrushSound() { return this.brushSound; }
	@Override
	public SoundEvent getBrushCompleteSound() { return this.brushCompleteSound; }
}
