package fun.mousewich.block;

import fun.mousewich.block.basic.ModLeavesBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Fertilizable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.Random;

public class BerryLeavesBlock extends ModLeavesBlock implements Fertilizable {
	protected ItemConvertible berry;
	public Item getBerry() { return berry.asItem(); }
	public BerryLeavesBlock(ItemConvertible berry, Block block) { this(berry, Settings.copy(block)); }
	public BerryLeavesBlock(ItemConvertible berry, Settings settings) {
		super(settings);
		this.setDefaultState(this.stateManager.getDefaultState().with(Properties.BERRIES, false).with(DISTANCE, 7).with(PERSISTENT, false).with(Properties.WATERLOGGED, false));
		this.berry = berry;
	}
	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (player.getStackInHand(hand).isOf(Items.BONE_MEAL)) return ActionResult.PASS;
		else if (state.get(Properties.BERRIES)) {
			int j = 1 + world.random.nextInt(2);
			dropStack(world, pos, new ItemStack(getBerry(), j));
			world.playSound(null, pos, SoundEvents.BLOCK_SWEET_BERRY_BUSH_PICK_BERRIES, SoundCategory.BLOCKS, 1.0f, 0.8f + world.random.nextFloat() * 0.4f);
			world.setBlockState(pos, state.with(Properties.BERRIES, false), NOTIFY_LISTENERS);
			world.emitGameEvent(player, GameEvent.BLOCK_CHANGE, pos);
			return ActionResult.success(world.isClient);
		}
		else return super.onUse(state, world, pos, player, hand, hit);
	}
	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) { builder.add(Properties.BERRIES, DISTANCE, PERSISTENT, Properties.WATERLOGGED); }
	@Override
	public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) { return !state.get(Properties.BERRIES); }
	@Override
	public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) { return !state.get(Properties.BERRIES); }
	@Override
	public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
		world.setBlockState(pos, state.with(Properties.BERRIES, true), NOTIFY_LISTENERS);
	}
}
