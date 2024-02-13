package fun.wich.block;

import fun.wich.ModBase;
import fun.wich.entity.ModEntityType;
import fun.wich.entity.passive.sniffer.SnifferEntity;
import fun.wich.event.ModWorldEvents;
import fun.wich.gen.data.tag.ModBlockTags;
import fun.wich.sound.ModSoundEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.enums.SlabType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;

import java.util.Random;

public class SnifferEggBlock extends Block {
	public static final IntProperty AGE = Properties.AGE_2;
	private static final VoxelShape SHAPE = Block.createCuboidShape(1.0, 0.0, 2.0, 15.0, 16.0, 14.0);

	public SnifferEggBlock(Settings settings) {
		super(settings);
		this.setDefaultState(this.stateManager.getDefaultState().with(AGE, 0));
	}
	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) { builder.add(AGE); }
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) { return SHAPE; }
	@Override
	public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
		return world.getBlockState(pos).getFluidState().isEmpty();
	}
	@Override
	public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		if (state.get(AGE) != 2) {
			world.playSound(null, pos, ModSoundEvents.ENTITY_SNIFFER_EGG_CRACK, SoundCategory.BLOCKS, 0.7f, 0.9f + random.nextFloat() * 0.2f);
			world.setBlockState(pos, state.with(AGE, state.get(AGE) + 1), Block.NOTIFY_LISTENERS);
			return;
		}
		world.playSound(null, pos, ModSoundEvents.ENTITY_SNIFFER_EGG_HATCH, SoundCategory.BLOCKS, 0.7f, 0.9f + random.nextFloat() * 0.2f);
		world.breakBlock(pos, false);
		SnifferEntity snifferEntity = ModEntityType.SNIFFER_ENTITY.create(world);
		if (snifferEntity != null) {
			Vec3d vec3d = Vec3d.ofCenter(pos);
			snifferEntity.setBaby(true);
			snifferEntity.refreshPositionAndAngles(vec3d.getX(), vec3d.getY(), vec3d.getZ(), MathHelper.wrapDegrees(world.random.nextFloat() * 360.0f), 0.0f);
			world.spawnEntity(snifferEntity);
		}
	}

	@Override
	public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
		boolean bl = onHatchBoostBlock(world, pos);
		if (!world.isClient()) world.syncWorldEvent(ModWorldEvents.SNIFFER_EGG_PLACE, pos, bl ? 1 : 0);
		world.emitGameEvent(GameEvent.BLOCK_PLACE, pos);
		world.getBlockTickScheduler().schedule(pos, this, (bl ? 4000 : 8000) + world.random.nextInt(300));
	}

	public static boolean onHatchBoostBlock(BlockView blockView, BlockPos blockPos) {
		BlockState state = blockView.getBlockState(blockPos.down());
		return state.isIn(ModBlockTags.SNIFFER_EGG_HATCH_BOOST)
				|| (state.isOf(ModBase.MOSS_SLAB.asBlock()) && state.get(Properties.SLAB_TYPE) == SlabType.DOUBLE);
	}
}
