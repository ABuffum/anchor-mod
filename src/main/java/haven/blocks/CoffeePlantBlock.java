package haven.blocks;

import haven.*;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Property;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Random;

public class CoffeePlantBlock extends PlantBlock implements Fertilizable {
	public static final int MAX_AGE = 2;
	public static final IntProperty AGE = IntProperty.of("age", 0, MAX_AGE);
	private static final VoxelShape SMALL_SHAPE = Block.createCuboidShape(3.0, 0.0, 3.0, 13.0, 14.0, 13.0);
	private static final VoxelShape SHAPE = VoxelShapes.union(Block.createCuboidShape(0.0, 6.0, 0.0, 16.0, 16.0, 16.0), Block.createCuboidShape(6.0, 0.0, 6.0, 10.0, 8.0, 10.0));

	public CoffeePlantBlock(AbstractBlock.Settings settings) {
		super(settings);
		this.setDefaultState(this.stateManager.getDefaultState().with(AGE, 0));
	}

	public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) { return new ItemStack(HavenMod.COFFEE_CHERRY); }

	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return state.get(AGE) == 0 ? SMALL_SHAPE : SHAPE;
	}

	public boolean hasRandomTicks(BlockState state) { return state.get(AGE) < MAX_AGE; }

	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		int i = state.get(AGE);
		if (i < MAX_AGE && random.nextInt(5) == 0 && world.getBaseLightLevel(pos.up(), 0) >= 9) {
			world.setBlockState(pos, state.with(AGE, i + 1), 2);
		}
	}

	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		int i = state.get(AGE);
		boolean bl = i == MAX_AGE;
		if (!bl && player.getStackInHand(hand).isOf(Items.BONE_MEAL)) return ActionResult.PASS;
		else if (i > 1) {
			dropStack(world, pos, new ItemStack(HavenMod.COFFEE_CHERRY, 1 + world.random.nextInt(2)));
			world.playSound((PlayerEntity)null, pos, SoundEvents.BLOCK_CROP_BREAK, SoundCategory.BLOCKS, 1.0F, 0.8F + world.random.nextFloat() * 0.4F);
			world.setBlockState(pos, (BlockState)state.with(AGE, 1), 2);
			return ActionResult.success(world.isClient);
		}
		else return super.onUse(state, world, pos, player, hand, hit);
	}

	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) { builder.add(AGE); }

	public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) { return state.get(AGE) < MAX_AGE; }

	public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) { return true; }

	public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
		world.setBlockState(pos, state.with(AGE, Math.min(MAX_AGE, state.get(AGE) + 1)), 2);
	}
}
