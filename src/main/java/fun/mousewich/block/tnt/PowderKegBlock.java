package fun.mousewich.block.tnt;

import fun.mousewich.entity.tnt.PowderKegEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.explosion.Explosion;

public class PowderKegBlock extends ModTntBlock {
	public PowderKegBlock(Settings settings) {
		super(settings);
		this.setDefaultState(this.stateManager.getDefaultState().with(Properties.UNSTABLE, false).with(Properties.FACING, Direction.NORTH));
	}
	@Override
	public void onDestroyedByExplosion(World world, BlockPos pos, Explosion explosion) {
		if (!world.isClient) {
			BlockState state = world.getBlockState(pos);
			PowderKegEntity tntEntity = new PowderKegEntity(world, pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, explosion.getCausingEntity(), state);
			tntEntity.setBlockState(state);
			tntEntity.setFuse(5);
			world.spawnEntity(tntEntity);
		}
	}

	@Override
	protected void primeTnt(World world, BlockPos pos, LivingEntity igniter) {
		if (!world.isClient) {
			BlockState state = world.getBlockState(pos);
			PowderKegEntity tntEntity = new PowderKegEntity(world, pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, igniter, state);
			tntEntity.setBlockState(state);
			tntEntity.setBlockState(world.getBlockState(pos));
			tntEntity.setFuse(10);
			world.spawnEntity(tntEntity);
			world.playSound(null, tntEntity.getX(), tntEntity.getY(), tntEntity.getZ(), SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.0F);
			world.emitGameEvent(igniter, GameEvent.PRIME_FUSE, pos);
		}
	}
	public BlockState rotate(BlockState state, BlockRotation rotation) { return state.with(Properties.FACING, rotation.rotate(state.get(Properties.FACING))); }
	public BlockState mirror(BlockState state, BlockMirror mirror) { return state.rotate(mirror.getRotation(state.get(Properties.FACING))); }
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) { builder.add(Properties.UNSTABLE, Properties.FACING); }
	public BlockState getPlacementState(ItemPlacementContext ctx) { return this.getDefaultState().with(Properties.FACING, ctx.getPlayerLookDirection().getOpposite()); }
}
