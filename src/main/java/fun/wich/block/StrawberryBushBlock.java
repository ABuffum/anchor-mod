package fun.wich.block;

import fun.wich.ModBase;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Random;

public class StrawberryBushBlock extends PlantBlock implements Fertilizable {
	public static final int MAX_AGE = 3;
	public static final IntProperty AGE = Properties.AGE_3;
	private static final VoxelShape SMALL_SHAPE = Block.createCuboidShape(3, 0, 3, 13, 8, 13);
	private static final VoxelShape LARGE_SHAPE = Block.createCuboidShape(1, 0, 1, 15, 16, 15);

	public StrawberryBushBlock(Settings settings) {
		super(settings);
		this.setDefaultState(this.stateManager.getDefaultState().with(AGE, 0));
	}

	public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) { return new ItemStack(ModBase.STRAWBERRY); }

	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		if (state.get(AGE) == 0) return SMALL_SHAPE;
		else return state.get(AGE) < 3 ? LARGE_SHAPE : super.getOutlineShape(state, world, pos, context);
	}

	public boolean hasRandomTicks(BlockState state) { return state.get(AGE) < MAX_AGE; }

	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		int i = state.get(AGE);
		if (i < MAX_AGE && random.nextInt(5) == 0 && world.getBaseLightLevel(pos.up(), 0) >= 9) {
			world.setBlockState(pos, state.with(AGE, i + 1), Block.NOTIFY_LISTENERS);
		}
	}

	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		int i = state.get(AGE);
		boolean bl = i == MAX_AGE;
		if (!bl && player.getStackInHand(hand).isOf(Items.BONE_MEAL)) return ActionResult.PASS;
		else if (i > 2) {
			int j = 1 + world.random.nextInt(2);
			dropStack(world, pos, new ItemStack(ModBase.STRAWBERRY, j));
			world.playSound(null, pos, SoundEvents.BLOCK_SWEET_BERRY_BUSH_PICK_BERRIES, SoundCategory.BLOCKS, 1.0F, 0.8F + world.random.nextFloat() * 0.4F);
			world.setBlockState(pos, state.with(AGE, 2), Block.NOTIFY_LISTENERS);
			return ActionResult.success(world.isClient);
		}
		else return super.onUse(state, world, pos, player, hand, hit);
	}
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) { builder.add(AGE); }
	public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) { return state.get(AGE) < MAX_AGE; }
	public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) { return true; }
	public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
		int i = Math.min(MAX_AGE, state.get(AGE) + 1);
		world.setBlockState(pos, state.with(AGE, i), Block.NOTIFY_LISTENERS);
	}
}
